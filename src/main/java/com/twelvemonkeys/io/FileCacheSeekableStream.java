package com.twelvemonkeys.io;

import com.twelvemonkeys.lang.Validate;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;

public final class FileCacheSeekableStream extends AbstractCachedSeekableStream {
   private byte[] buffer = new byte[1024];

   public FileCacheSeekableStream(InputStream var1) throws IOException {
      this(var1, "iocache", null);
   }

   public FileCacheSeekableStream(InputStream var1, String var2) throws IOException {
      this(var1, var2, null);
   }

   public FileCacheSeekableStream(InputStream var1, String var2, File var3) throws IOException {
      this(Validate.notNull(var1, "stream"), createTempFile(var2, var3));
   }

   static File createTempFile(String var0, File var1) throws IOException {
      Validate.notNull(var0, "tempBaseName");
      File var2 = (var1 == null ? Files.createTempFile(var0, null) : Files.createTempFile(var1.toPath(), var0, null)).toFile();
      var2.deleteOnExit();
      return var2;
   }

   FileCacheSeekableStream(InputStream var1, File var2) throws FileNotFoundException {
      super(var1, new FileCacheSeekableStream.FileCache(var2));
   }

   @Override
   public final boolean isCachedMemory() {
      return false;
   }

   @Override
   public final boolean isCachedFile() {
      return true;
   }

   @Override
   protected void closeImpl() throws IOException {
      super.closeImpl();
      this.buffer = null;
   }

   @Override
   public int read() throws IOException {
      this.checkOpen();
      int var1;
      if (this.position == this.streamPosition) {
         var1 = this.readAhead(this.buffer, 0, this.buffer.length);
         if (var1 >= 0) {
            var1 = this.buffer[0] & 255;
         }
      } else {
         this.syncPosition();
         var1 = this.getCache().read();
      }

      if (var1 != -1) {
         this.position++;
      }

      return var1;
   }

   @Override
   public int read(byte[] var1, int var2, int var3) throws IOException {
      this.checkOpen();
      int var4;
      if (this.position == this.streamPosition) {
         var4 = this.readAhead(var1, var2, var3);
      } else {
         this.syncPosition();
         var4 = this.getCache().read(var1, var2, (int)Math.min((long)var3, this.streamPosition - this.position));
      }

      if (var4 > 0) {
         this.position += var4;
      }

      return var4;
   }

   private int readAhead(byte[] var1, int var2, int var3) throws IOException {
      int var4 = this.stream.read(var1, var2, var3);
      if (var4 > 0) {
         this.streamPosition += var4;
         this.getCache().write(var1, var2, var4);
      }

      return var4;
   }

   static final class FileCache extends AbstractCachedSeekableStream.StreamCache {
      private RandomAccessFile cacheFile;

      public FileCache(File var1) throws FileNotFoundException {
         Validate.notNull(var1, "file");
         this.cacheFile = new RandomAccessFile(var1, "rw");
      }

      @Override
      public void write(int var1) throws IOException {
         this.cacheFile.write(var1);
      }

      @Override
      public void write(byte[] var1, int var2, int var3) throws IOException {
         this.cacheFile.write(var1, var2, var3);
      }

      @Override
      public int read() throws IOException {
         return this.cacheFile.read();
      }

      @Override
      public int read(byte[] var1, int var2, int var3) throws IOException {
         return this.cacheFile.read(var1, var2, var3);
      }

      @Override
      public void seek(long var1) throws IOException {
         this.cacheFile.seek(var1);
      }

      @Override
      public long getPosition() throws IOException {
         return this.cacheFile.getFilePointer();
      }

      @Override
      void close() throws IOException {
         this.cacheFile.close();
      }
   }
}
