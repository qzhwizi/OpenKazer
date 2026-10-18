package com.twelvemonkeys.io.enc;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public final class DecoderStream extends FilterInputStream {
   private final ByteBuffer buffer;
   private final Decoder decoder;

   public DecoderStream(InputStream var1, Decoder var2) {
      this(var1, var2, 1024);
   }

   public DecoderStream(InputStream var1, Decoder var2, int var3) {
      super(var1);
      this.decoder = var2;
      this.buffer = ByteBuffer.allocate(var3);
      ((Buffer)this.buffer).flip();
   }

   @Override
   public int available() throws IOException {
      return this.buffer.remaining();
   }

   @Override
   public int read() throws IOException {
      return !this.buffer.hasRemaining() && this.fill() < 0 ? -1 : this.buffer.get() & 0xFF;
   }

   @Override
   public int read(byte[] var1, int var2, int var3) throws IOException {
      if (var1 == null) {
         throw new NullPointerException();
      } else if (var2 < 0 || var2 > var1.length || var3 < 0 || var2 + var3 > var1.length || var2 + var3 < 0) {
         throw new IndexOutOfBoundsException("bytes.length=" + var1.length + " offset=" + var2 + " length=" + var3);
      } else if (var3 == 0) {
         return 0;
      } else if (!this.buffer.hasRemaining() && this.fill() < 0) {
         return -1;
      } else {
         int var4 = 0;
         int var5 = var2;

         while (var3 > var4 && (this.buffer.hasRemaining() || this.fill() >= 0)) {
            int var6 = Math.min(var3 - var4, this.buffer.remaining());
            this.buffer.get(var1, var5, var6);
            var5 += var6;
            var4 += var6;
         }

         return var4;
      }
   }

   @Override
   public long skip(long var1) throws IOException {
      if (!this.buffer.hasRemaining() && this.fill() < 0) {
         return 0L;
      } else {
         long var3 = 0L;

         while (var3 < var1 && (this.buffer.hasRemaining() || this.fill() >= 0)) {
            int var5 = (int)Math.min(var1 - var3, (long)this.buffer.remaining());
            ((Buffer)this.buffer).position(this.buffer.position() + var5);
            var3 += var5;
         }

         return var3;
      }
   }

   private int fill() throws IOException {
      ((Buffer)this.buffer).clear();
      int var1 = this.decoder.decode(this.in, this.buffer);
      if (var1 > this.buffer.capacity()) {
         throw new AssertionError(
            String.format("Decode beyond buffer (%d): %d (using %s decoder)", this.buffer.capacity(), var1, this.decoder.getClass().getName())
         );
      } else {
         ((Buffer)this.buffer).flip();
         return var1 == 0 ? -1 : var1;
      }
   }
}
