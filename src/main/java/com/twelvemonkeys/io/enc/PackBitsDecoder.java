package com.twelvemonkeys.io.enc;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public final class PackBitsDecoder implements Decoder {
   private final boolean disableNoOp;
   private final byte[] sample;
   private boolean reachedEOF;

   public PackBitsDecoder() {
      this(1, false);
   }

   public PackBitsDecoder(boolean var1) {
      this(1, var1);
   }

   public PackBitsDecoder(int var1, boolean var2) {
      this.sample = new byte[var1];
      this.disableNoOp = var2;
   }

   @Override
   public int decode(InputStream var1, ByteBuffer var2) throws IOException {
      if (this.reachedEOF) {
         return -1;
      } else {
         int var3 = var1.read();
         if (var3 < 0) {
            this.reachedEOF = true;
            return 0;
         } else {
            byte var4 = (byte)var3;

            try {
               if (var4 >= 0) {
                  readFully(var1, var2, this.sample.length * (var4 + 1));
               } else if (this.disableNoOp || var4 != -128) {
                  for (int var5 = 0; var5 < this.sample.length; var5++) {
                     this.sample[var5] = readByte(var1);
                  }

                  for (int var7 = -var4 + 1; var7 > 0; var7--) {
                     var2.put(this.sample);
                  }
               }
            } catch (IndexOutOfBoundsException var6) {
               throw new DecodeException("Error in PackBits decompression, data seems corrupt", var6);
            }

            return var2.position();
         }
      }
   }

   static byte readByte(InputStream var0) throws IOException {
      int var1 = var0.read();
      if (var1 < 0) {
         throw new EOFException("Unexpected end of PackBits stream");
      } else {
         return (byte)var1;
      }
   }

   static void readFully(InputStream var0, ByteBuffer var1, int var2) throws IOException {
      if (var2 < 0) {
         throw new IndexOutOfBoundsException(String.format("Negative length: %d", var2));
      } else {
         int var3 = 0;

         while (var3 < var2) {
            int var4 = var0.read(var1.array(), var1.arrayOffset() + var1.position() + var3, var2 - var3);
            if (var4 < 0) {
               throw new EOFException("Unexpected end of PackBits stream");
            }

            var3 += var4;
         }

         ((Buffer)var1).position(var1.position() + var3);
      }
   }
}
