package com.twelvemonkeys.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public final class MemoryCacheSeekableStream extends AbstractCachedSeekableStream {
   public MemoryCacheSeekableStream(InputStream var1) {
      super(var1, new MemoryCacheSeekableStream.MemoryCache());
   }

   @Override
   public final boolean isCachedMemory() {
      return true;
   }

   @Override
   public final boolean isCachedFile() {
      return false;
   }

   static final class MemoryCache extends AbstractCachedSeekableStream.StreamCache {
      static final int BLOCK_SIZE = 8192;
      private final List<byte[]> cache = new ArrayList<>();
      private long length;
      private long position;
      private long start;

      private byte[] getBlock() throws IOException {
         long var1 = this.position - this.start;
         if (var1 < 0L) {
            throw new IOException("StreamCache flushed before read position");
         } else {
            long var3 = var1 / 8192L;
            if (var3 >= 2147483647L) {
               throw new IOException("Memory cache max size exceeded");
            } else {
               if (var3 >= this.cache.size()) {
                  try {
                     this.cache.add(new byte[8192]);
                  } catch (OutOfMemoryError var6) {
                     throw new IOException("No more memory for cache: " + this.cache.size() * 8192);
                  }
               }

               return this.cache.get((int)var3);
            }
         }
      }

      @Override
      public void write(int var1) throws IOException {
         byte[] var2 = this.getBlock();
         int var3 = (int)(this.position % 8192L);
         var2[var3] = (byte)var1;
         this.position++;
         if (this.position > this.length) {
            this.length = this.position;
         }
      }

      @Override
      public void write(byte[] var1, int var2, int var3) throws IOException {
         byte[] var4 = this.getBlock();

         for (int var5 = 0; var5 < var3; var5++) {
            int var6 = (int)this.position % 8192;
            if (var6 == 0) {
               var4 = this.getBlock();
            }

            var4[var6] = var1[var2 + var5];
            this.position++;
         }

         if (this.position > this.length) {
            this.length = this.position;
         }
      }

      @Override
      public int read() throws IOException {
         if (this.position >= this.length) {
            return -1;
         } else {
            byte[] var1 = this.getBlock();
            int var2 = (int)(this.position % 8192L);
            this.position++;
            return var1[var2] & 0xFF;
         }
      }

      @Override
      public int read(byte[] var1, int var2, int var3) throws IOException {
         if (this.position >= this.length) {
            return -1;
         } else {
            byte[] var4 = this.getBlock();
            int var5 = (int)(this.position % 8192L);
            int var6 = (int)Math.min((long)Math.min(var3, var4.length - var5), this.length - this.position);

            int var7;
            for (var7 = 0; var7 < var6; var7++) {
               var1[var2 + var7] = var4[var5 + var7];
            }

            this.position += var7;
            return var7;
         }
      }

      @Override
      public void seek(long var1) throws IOException {
         if (var1 < this.start) {
            throw new IOException("Seek before flush position");
         } else {
            this.position = var1;
         }
      }

      @Override
      public void flush(long var1) {
         int var3 = (int)(var1 / 8192L) - 1;

         for (int var4 = 0; var4 < var3; var4++) {
            this.cache.remove(0);
         }

         this.start = var1;
      }

      @Override
      void close() throws IOException {
         this.cache.clear();
      }

      @Override
      public long getPosition() {
         return this.position;
      }
   }
}
