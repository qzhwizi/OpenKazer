package com.twelvemonkeys.imageio.stream;

import com.twelvemonkeys.lang.Validate;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.NonWritableChannelException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.util.ArrayList;
import java.util.List;

final class MemoryCache implements Cache {
   static final int BLOCK_SIZE = 8192;
   private static final byte[] NULL_BLOCK = new byte[0];
   private final List<byte[]> cache = new ArrayList<>();
   private final ReadableByteChannel channel;
   private int maxBlock = Integer.MAX_VALUE;
   private long length;
   private long position;
   private long start;

   MemoryCache(InputStream var1) {
      this(Channels.newChannel(Validate.notNull(var1, "stream")));
   }

   public MemoryCache(ReadableByteChannel var1) {
      this.channel = Validate.notNull(var1, "channel");
   }

   byte[] fetchBlock() throws IOException {
      long var1 = this.position;
      long var3 = var1 / 8192L;
      if (var3 >= 2147483647L) {
         throw new IOException("Memory cache max size exceeded");
      } else if (var3 > this.maxBlock) {
         return NULL_BLOCK;
      } else {
         while (var3 >= this.cache.size()) {
            byte[] var5;
            try {
               var5 = new byte[8192];
            } catch (OutOfMemoryError var7) {
               throw new IOException("No more memory for cache: " + this.cache.size() * 8192);
            }

            this.cache.add(var5);
            int var6 = this.readBlock(var5);
            this.length += var6;
            if (var6 < 8192) {
               this.maxBlock = (int)var3;
               return var5;
            }
         }

         return this.cache.get((int)var3);
      }
   }

   private int readBlock(byte[] var1) throws IOException {
      ByteBuffer var2 = ByteBuffer.wrap(var1);

      while (var2.hasRemaining()) {
         int var3 = this.channel.read(var2);
         if (var3 == -1) {
            break;
         }
      }

      return var2.position();
   }

   @Override
   public boolean isOpen() {
      return this.channel.isOpen();
   }

   @Override
   public void close() throws IOException {
      this.cache.clear();
   }

   @Override
   public int read(ByteBuffer var1) throws IOException {
      byte[] var2 = this.fetchBlock();
      if (this.position >= this.length) {
         return -1;
      } else {
         int var3 = (int)(this.position % 8192L);
         int var4 = Math.min(var1.remaining(), (int)Math.min((long)(8192 - var3), this.length - this.position));
         var1.put(var2, var3, var4);
         this.position += var4;
         return var4;
      }
   }

   @Override
   public long position() throws IOException {
      return this.position;
   }

   @Override
   public SeekableByteChannel position(long var1) throws IOException {
      if (var1 < this.start) {
         throw new IOException("Seek before flush position");
      } else {
         this.position = var1;
         return this;
      }
   }

   @Override
   public long size() throws IOException {
      return -1L;
   }

   @Override
   public int write(ByteBuffer var1) {
      throw new NonWritableChannelException();
   }

   @Override
   public SeekableByteChannel truncate(long var1) {
      throw new NonWritableChannelException();
   }

   @Override
   public void flushBefore(long var1) {
      if (var1 < this.start) {
         throw new IndexOutOfBoundsException("pos < flushed position");
      } else if (var1 > this.position) {
         throw new IndexOutOfBoundsException("pos > current position");
      } else {
         int var3 = (int)(var1 / 8192L);

         for (int var4 = 0; var4 < var3; var4++) {
            this.cache.set(var4, null);
         }

         this.start = var1;
      }
   }
}
