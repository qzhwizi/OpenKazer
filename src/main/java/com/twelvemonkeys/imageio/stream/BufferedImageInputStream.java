package com.twelvemonkeys.imageio.stream;

import com.twelvemonkeys.lang.Validate;
import java.io.EOFException;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageInputStreamImpl;

@Deprecated
public final class BufferedImageInputStream extends ImageInputStreamImpl implements ImageInputStream {
   static final int DEFAULT_BUFFER_SIZE = 8192;
   private ImageInputStream stream;
   private ByteBuffer buffer;
   private final ByteBuffer integralCache = ByteBuffer.allocate(8);
   private final byte[] integralCacheArray = this.integralCache.array();

   public BufferedImageInputStream(ImageInputStream var1) throws IOException {
      this(var1, 8192);
   }

   private BufferedImageInputStream(ImageInputStream var1, int var2) throws IOException {
      this.stream = Validate.notNull(var1, "stream");
      this.streamPos = var1.getStreamPosition();
      this.buffer = ByteBuffer.allocate(var2);
      ((Buffer)this.buffer).limit(0);
   }

   private void fillBuffer() throws IOException {
      ((Buffer)this.buffer).clear();
      int var1 = this.stream.read(this.buffer.array(), 0, this.buffer.capacity());
      if (var1 >= 0) {
         ((Buffer)this.buffer).position(var1);
         ((Buffer)this.buffer).flip();
      } else {
         ((Buffer)this.buffer).limit(0);
      }
   }

   @Override
   public void setByteOrder(ByteOrder var1) {
      super.setByteOrder(var1);
      this.integralCache.order(var1);
   }

   @Override
   public int read() throws IOException {
      this.checkClosed();
      if (!this.buffer.hasRemaining()) {
         this.fillBuffer();
         if (!this.buffer.hasRemaining()) {
            return -1;
         }
      }

      this.bitOffset = 0;
      this.streamPos++;
      return this.buffer.get() & 0xFF;
   }

   @Override
   public int read(byte[] var1, int var2, int var3) throws IOException {
      this.checkClosed();
      this.bitOffset = 0;
      if (!this.buffer.hasRemaining()) {
         if (var3 >= this.buffer.capacity()) {
            return this.readDirect(var1, var2, var3);
         }

         this.fillBuffer();
      }

      return this.readBuffered(var1, var2, var3);
   }

   private int readDirect(byte[] var1, int var2, int var3) throws IOException {
      ((Buffer)this.buffer).limit(0);
      int var4 = this.stream.read(var1, var2, var3);
      if (var4 > 0) {
         this.streamPos += var4;
      }

      return var4;
   }

   private int readBuffered(byte[] var1, int var2, int var3) {
      if (!this.buffer.hasRemaining()) {
         return -1;
      } else {
         int var4 = Math.min(this.buffer.remaining(), var3);
         if (var4 > 0) {
            int var5 = this.buffer.position();
            System.arraycopy(this.buffer.array(), var5, var1, var2, var4);
            ((Buffer)this.buffer).position(var5 + var4);
         }

         this.streamPos += var4;
         return var4;
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
   public int readBit() throws IOException {
      this.checkClosed();
      if (!this.buffer.hasRemaining()) {
         this.fillBuffer();
         if (!this.buffer.hasRemaining()) {
            throw new EOFException();
         }
      }

      int var1 = this.bitOffset + 1 & 7;
      int var2 = this.buffer.get() & 255;
      if (var1 != 0) {
         ((Buffer)this.buffer).position(this.buffer.position() - 1);
         var2 >>= 8 - var1;
      } else {
         this.streamPos++;
      }

      this.bitOffset = var1;
      return var2 & 1;
   }

   @Override
   public long readBits(int var1) throws IOException {
      this.checkClosed();
      if (var1 >= 0 && var1 <= 64) {
         if (var1 == 0) {
            return 0L;
         } else {
            int var2 = var1 + this.bitOffset;
            int var3 = this.bitOffset + var1 & 7;

            long var4;
            for (var4 = 0L; var2 > 0; var2 -= 8) {
               if (!this.buffer.hasRemaining()) {
                  this.fillBuffer();
                  if (!this.buffer.hasRemaining()) {
                     throw new EOFException();
                  }
               }

               int var6 = this.buffer.get() & 255;
               this.streamPos++;
               var4 <<= 8;
               var4 |= var6;
            }

            if (var3 != 0) {
               ((Buffer)this.buffer).position(this.buffer.position() - 1);
               this.streamPos--;
            }

            this.bitOffset = var3;
            var4 >>>= -var2;
            return var4 & -1L >>> 64 - var1;
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   @Override
   public void seek(long var1) throws IOException {
      this.checkClosed();
      this.bitOffset = 0;
      if (this.streamPos != var1) {
         long var3 = this.buffer.position() + var1 - this.streamPos;
         if (var3 >= 0L && var3 <= this.buffer.limit()) {
            ((Buffer)this.buffer).position((int)var3);
         } else {
            ((Buffer)this.buffer).limit(0);
            this.stream.seek(var1);
         }

         this.streamPos = var1;
      }
   }

   @Override
   public void flushBefore(long var1) throws IOException {
      this.checkClosed();
      this.stream.flushBefore(var1);
   }

   @Override
   public long getFlushedPosition() {
      return this.stream.getFlushedPosition();
   }

   @Override
   public boolean isCached() {
      return this.stream.isCached();
   }

   @Override
   public boolean isCachedMemory() {
      return this.stream.isCachedMemory();
   }

   @Override
   public boolean isCachedFile() {
      return this.stream.isCachedFile();
   }

   @Override
   public void close() throws IOException {
      if (this.stream != null) {
         this.stream = null;
         this.buffer = null;
      }

      super.close();
   }

   @Override
   protected void finalize() throws Throwable {
      super.finalize();
   }

   @Override
   public long length() {
      try {
         return this.stream.length();
      } catch (IOException var2) {
         return -1L;
      }
   }
}
