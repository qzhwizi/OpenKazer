package com.twelvemonkeys.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public final class FileSeekableStream extends SeekableInputStream {
   final RandomAccessFile mRandomAccess;

   public FileSeekableStream(File var1) throws FileNotFoundException {
      this(new RandomAccessFile(var1, "r"));
   }

   public FileSeekableStream(RandomAccessFile var1) {
      this.mRandomAccess = var1;
   }

   @Override
   public boolean isCached() {
      return false;
   }

   @Override
   public boolean isCachedFile() {
      return false;
   }

   @Override
   public boolean isCachedMemory() {
      return false;
   }

   @Override
   public int available() throws IOException {
      long var1 = this.mRandomAccess.length() - this.position;
      return var1 > 2147483647L ? Integer.MAX_VALUE : (int)var1;
   }

   @Override
   public void closeImpl() throws IOException {
      this.mRandomAccess.close();
   }

   @Override
   public int read() throws IOException {
      this.checkOpen();
      int var1 = this.mRandomAccess.read();
      if (var1 >= 0) {
         this.position++;
      }

      return var1;
   }

   @Override
   public int read(byte[] var1, int var2, int var3) throws IOException {
      this.checkOpen();
      int var4 = this.mRandomAccess.read(var1, var2, var3);
      if (var4 > 0) {
         this.position += var4;
      }

      return var4;
   }

   @Override
   protected void flushBeforeImpl(long var1) {
   }

   @Override
   protected void seekImpl(long var1) throws IOException {
      this.mRandomAccess.seek(var1);
   }
}
