package com.twelvemonkeys.imageio.stream;

import com.twelvemonkeys.lang.Validate;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.imageio.stream.ImageInputStreamImpl;

@Deprecated
public final class BufferedFileImageInputStream extends ImageInputStreamImpl {
   static final int DEFAULT_BUFFER_SIZE = 8192;
   private byte[] buffer = new byte[8192];
   private int bufferPos;
   private int bufferLimit;
   private final ByteBuffer integralCache = ByteBuffer.allocate(8);
   private final byte[] integralCacheArray = this.integralCache.array();
   private RandomAccessFile raf;

   public BufferedFileImageInputStream(File var1) throws FileNotFoundException {
      this(new RandomAccessFile(Validate.notNull(var1, "file"), "r"));
   }

   public BufferedFileImageInputStream(RandomAccessFile var1) {
      this.raf = Validate.notNull(var1, "raf");
   }

   private boolean fillBuffer() throws IOException {
      int var1 = this.raf.read(this.buffer, 0, this.buffer.length);
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
      int var4 = this.raf.read(var1, var2, var3);
      if (var4 > 0) {
         this.streamPos += var4;
      }

      return var4;
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
         return this.raf.length();
      } catch (IOException var2) {
         return -1L;
      }
   }

   @Override
   public void close() throws IOException {
      super.close();
      this.buffer = null;
      this.raf.close();
      this.raf = null;
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
               this.raf.seek(var1);
            }

            this.streamPos = var1;
         }
      }
   }
}
