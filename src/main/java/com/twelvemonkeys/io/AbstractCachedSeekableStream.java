package com.twelvemonkeys.io;

import com.twelvemonkeys.lang.Validate;
import java.io.IOException;
import java.io.InputStream;

abstract class AbstractCachedSeekableStream extends SeekableInputStream {
   protected final InputStream stream;
   protected long streamPosition;
   private AbstractCachedSeekableStream.StreamCache cache;

   protected AbstractCachedSeekableStream(InputStream var1, AbstractCachedSeekableStream.StreamCache var2) {
      Validate.notNull(var1, "stream");
      Validate.notNull(var2, "cache");
      this.stream = var1;
      this.cache = var2;
   }

   protected final AbstractCachedSeekableStream.StreamCache getCache() {
      return this.cache;
   }

   @Override
   public int available() throws IOException {
      long var1 = this.streamPosition - this.position + this.stream.available();
      return var1 > 2147483647L ? Integer.MAX_VALUE : (int)var1;
   }

   @Override
   public int read() throws IOException {
      this.checkOpen();
      int var1;
      if (this.position == this.streamPosition) {
         var1 = this.stream.read();
         if (var1 >= 0) {
            this.streamPosition++;
            this.cache.write(var1);
         }
      } else {
         this.syncPosition();
         var1 = this.cache.read();
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
         var4 = this.stream.read(var1, var2, var3);
         if (var4 > 0) {
            this.streamPosition += var4;
            this.cache.write(var1, var2, var4);
         }
      } else {
         this.syncPosition();
         var4 = this.cache.read(var1, var2, var3);
      }

      if (var4 > 0) {
         this.position += var4;
      }

      return var4;
   }

   protected final void syncPosition() throws IOException {
      if (this.cache.getPosition() != this.position) {
         this.cache.seek(this.position);
      }
   }

   @Override
   public final boolean isCached() {
      return true;
   }

   @Override
   public abstract boolean isCachedMemory();

   @Override
   public abstract boolean isCachedFile();

   @Override
   protected void seekImpl(long var1) throws IOException {
      if (this.streamPosition < var1) {
         if (this.cache.getPosition() != this.streamPosition) {
            this.cache.seek(this.streamPosition);
         }

         long var3 = var1 - this.streamPosition;
         int var5 = var3 > 1024L ? 1024 : (int)var3;
         byte[] var6 = new byte[var5];

         while (var3 > 0L) {
            int var7 = var6.length < var3 ? var6.length : (int)var3;
            int var8 = this.stream.read(var6, 0, var7);
            if (var8 > 0) {
               this.cache.write(var6, 0, var8);
               this.streamPosition += var8;
               var3 -= var8;
            } else if (var8 < 0) {
               break;
            }
         }
      } else {
         this.cache.seek(var1);
      }
   }

   @Override
   protected void flushBeforeImpl(long var1) {
      this.cache.flush(var1);
   }

   @Override
   protected void closeImpl() throws IOException {
      this.cache.close();
      this.cache = null;
      this.stream.close();
   }

   abstract static class StreamCache {
      abstract void write(int var1) throws IOException;

      void write(byte[] var1, int var2, int var3) throws IOException {
         for (int var4 = 0; var4 < var3; var4++) {
            this.write(var1[var2 + var4]);
         }
      }

      abstract int read() throws IOException;

      int read(byte[] var1, int var2, int var3) throws IOException {
         int var4 = 0;

         for (int var5 = 0; var5 < var3; var5++) {
            int var6 = this.read();
            if (var6 < 0) {
               break;
            }

            var1[var2 + var5] = (byte)var6;
            var4++;
         }

         return var4;
      }

      abstract void seek(long var1) throws IOException;

      void flush(long var1) {
      }

      abstract long getPosition() throws IOException;

      abstract void close() throws IOException;
   }
}
