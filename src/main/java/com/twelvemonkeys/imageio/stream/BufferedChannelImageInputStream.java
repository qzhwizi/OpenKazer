package com.twelvemonkeys.imageio.stream;

import com.twelvemonkeys.lang.Validate;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import javax.imageio.stream.ImageInputStreamImpl;

final class BufferedChannelImageInputStream extends ImageInputStreamImpl {
   private static final Closeable CLOSEABLE_STUB = new Closeable() {
      @Override
      public void close() {
      }
   };
   static final int DEFAULT_BUFFER_SIZE = 8192;
   private ByteBuffer byteBuffer = ByteBuffer.allocate(8192);
   private byte[] buffer = this.byteBuffer.array();
   private int bufferPos;
   private int bufferLimit;
   private final ByteBuffer integralCache = ByteBuffer.allocate(8);
   private final byte[] integralCacheArray = this.integralCache.array();
   private SeekableByteChannel channel;
   private Closeable closeable;

   public BufferedChannelImageInputStream(File var1) throws IOException {
      this(Validate.notNull(var1, "file").toPath());
   }

   public BufferedChannelImageInputStream(Path var1) throws IOException {
      this(FileChannel.open(Validate.notNull(var1, "file"), StandardOpenOption.READ), true);
   }

   public BufferedChannelImageInputStream(RandomAccessFile var1) {
      this(Validate.notNull(var1, "file").getChannel(), true);
   }

   public BufferedChannelImageInputStream(FileInputStream var1) {
      this(Validate.notNull(var1, "inputStream").getChannel(), false);
   }

   public BufferedChannelImageInputStream(SeekableByteChannel var1) {
      this(Validate.notNull(var1, "channel"), false);
   }

   BufferedChannelImageInputStream(Cache var1) {
      this(Validate.notNull(var1, "cache"), true);
   }

   private BufferedChannelImageInputStream(SeekableByteChannel var1, boolean var2) {
      this.channel = Validate.notNull(var1, "channel");
      this.closeable = (Closeable)(var2 ? this.channel : CLOSEABLE_STUB);
   }

   private boolean fillBuffer() throws IOException {
      ((Buffer)this.byteBuffer).rewind();
      int var1 = this.channel.read(this.byteBuffer);
      this.bufferPos = 0;
      this.bufferLimit = Math.max(var1, 0);
      return this.bufferLimit > 0;
   }

   private boolean bufferEmpty() {
      return this.bufferPos >= this.bufferLimit;
   }

   @Override
   public void setByteOrder(ByteOrder var1) {
      super.setByteOrder(var1);
      this.integralCache.order(var1);
   }

   @Override
   public int read() throws IOException {
      this.checkClosed();
      if (this.bufferEmpty() && !this.fillBuffer()) {
         return -1;
      } else {
         this.bitOffset = 0;
         this.streamPos++;
         return this.buffer[this.bufferPos++] & 0xFF;
      }
   }

   @Override
   public int read(byte[] var1, int var2, int var3) throws IOException {
      this.checkClosed();
      this.bitOffset = 0;
      if (this.bufferEmpty()) {
         if (var3 >= this.buffer.length) {
            return this.readDirect(var1, var2, var3);
         }

         if (!this.fillBuffer()) {
            return -1;
         }
      }

      int var4 = this.readBuffered(var1, var2, var3);
      return var3 > var4 ? var4 + Math.max(0, this.readDirect(var1, var2 + var4, var3 - var4)) : var4;
   }

   private int readDirect(byte[] var1, int var2, int var3) throws IOException {
      this.bufferLimit = 0;
      ByteBuffer var4 = ByteBuffer.wrap(var1, var2, var3);
      int var5 = 0;

      while (var4.hasRemaining()) {
         int var6 = this.channel.read(var4);
         if (var6 == -1) {
            if (var5 == 0) {
               return -1;
            }
            break;
         }

         var5 += var6;
      }

      this.streamPos += var5;
      return var5;
   }

   private int readBuffered(byte[] var1, int var2, int var3) {
      int var4 = Math.min(this.bufferLimit - this.bufferPos, var3);
      if (var4 > 0) {
         System.arraycopy(this.buffer, this.bufferPos, var1, var2, var4);
         this.bufferPos += var4;
         this.streamPos += var4;
      }

      return var4;
   }

   @Override
   public long length() {
      try {
         this.checkClosed();
         return this.channel.size();
      } catch (IOException var2) {
         return -1L;
      }
   }

   @Override
   public void close() throws IOException {
      super.close();
      this.buffer = null;
      this.byteBuffer = null;
      this.channel = null;

      try {
         this.closeable.close();
      } finally {
         this.closeable = null;
      }
   }

   @Override
   public short readShort() throws IOException {
      this.readFully(this.integralCacheArray, 0, 2);
      return this.integralCache.getShort(0);
   }

   @Override
   public int readInt() throws IOException {
      this.readFully(this.integralCacheArray, 0, 4);
      return this.integralCache.getInt(0);
   }

   @Override
   public long readLong() throws IOException {
      this.readFully(this.integralCacheArray, 0, 8);
      return this.integralCache.getLong(0);
   }

   @Override
   public void seek(long var1) throws IOException {
      this.checkClosed();
      if (var1 < this.flushedPos) {
         throw new IndexOutOfBoundsException("position < flushedPos!");
      } else {
         this.bitOffset = 0;
         if (this.streamPos != var1) {
            long var3 = this.bufferPos + var1 - this.streamPos;
            if (var3 >= 0L && var3 < this.bufferLimit) {
               this.bufferPos = (int)var3;
            } else {
               this.bufferLimit = 0;
               this.channel.position(var1);
            }

            this.streamPos = var1;
         }
      }
   }

   @Override
   public void flushBefore(long var1) throws IOException {
      super.flushBefore(var1);
      if (this.channel instanceof Cache) {
         ((Cache)this.channel).flushBefore(var1);
      }
   }
}
