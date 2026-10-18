package com.twelvemonkeys.imageio.plugins.webp;

import com.twelvemonkeys.lang.Validate;
import java.io.EOFException;
import java.io.IOException;
import javax.imageio.stream.ImageInputStream;

public final class LSBBitReader {
   private final ImageInputStream imageInput;
   private int bitOffset = 64;
   private long streamPosition = -1L;
   private long buffer;

   public LSBBitReader(ImageInputStream var1) {
      this.imageInput = Validate.notNull(var1);
   }

   public long readBits(int var1) throws IOException {
      return this.readBits(var1, false);
   }

   public long peekBits(int var1) throws IOException {
      if (var1 > 56) {
         throw new IllegalArgumentException("Tried peeking over 56");
      } else {
         return this.readBits(var1, true);
      }
   }

   private long readBits(int var1, boolean var2) throws IOException {
      if (var1 <= 56) {
         if (this.streamPosition != this.imageInput.getStreamPosition()) {
            this.resetBuffer();
         }

         long var5 = this.buffer >>> this.bitOffset & (1L << var1) - 1L;
         if (!var2) {
            this.bitOffset += var1;
            if (this.bitOffset >= 8) {
               this.refillBuffer();
            }
         }

         return var5;
      } else {
         long var3 = this.readBits(56);
         return this.readBits(var1 - 56) << 56 | var3;
      }
   }

   private void refillBuffer() throws IOException {
      this.imageInput.readLong();

      for (; this.bitOffset >= 8; this.bitOffset -= 8) {
         try {
            byte var1 = this.imageInput.readByte();
            this.buffer = (long)var1 << 56 | this.buffer >>> 8;
            this.streamPosition++;
         } catch (EOFException var2) {
            this.imageInput.seek(this.streamPosition);
            return;
         }
      }

      this.imageInput.seek(this.streamPosition);
   }

   private void resetBuffer() throws IOException {
      long var1 = this.imageInput.getStreamPosition();

      try {
         this.buffer = this.imageInput.readLong();
         this.bitOffset = 0;
         this.streamPosition = var1;
         this.imageInput.seek(var1);
      } catch (EOFException var4) {
         this.streamPosition = var1 - 8L;
         this.bitOffset = 64;
         this.refillBuffer();
      }
   }

   public int readBit() throws IOException {
      return (int)this.readBits(1);
   }
}
