package com.twelvemonkeys.io.enc;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public final class PackBitsEncoder implements Encoder {
   private final byte[] buffer = new byte[128];

   @Override
   public void encode(OutputStream var1, ByteBuffer var2) throws IOException {
      this.encode(var1, var2.array(), var2.arrayOffset() + var2.position(), var2.remaining());
      ((Buffer)var2).position(var2.remaining());
   }

   private void encode(OutputStream var1, byte[] var2, int var3, int var4) throws IOException {
      int var5 = var3;
      int var6 = var3 + var4 - 1;
      int var7 = var6 - 1;

      while (var5 <= var6) {
         int var8 = 1;

         byte var9;
         for (var9 = var2[var5]; var8 < 127 && var5 < var6 && var2[var5] == var2[var5 + 1]; var8++) {
            var5++;
         }

         if (var8 > 1) {
            var5++;
            var1.write(-(var8 - 1));
            var1.write(var9);
         }

         var8 = 0;

         while (var8 < 128 && (var5 < var6 && var2[var5] != var2[var5 + 1] || var5 < var7 && var2[var5] != var2[var5 + 2])) {
            this.buffer[var8++] = var2[var5++];
         }

         if (var5 == var6 && var8 > 0 && var8 < 128) {
            this.buffer[var8++] = var2[var5++];
         }

         if (var8 > 0) {
            var1.write(var8 - 1);
            var1.write(this.buffer, 0, var8);
         }

         if (var5 == var6 && (var8 <= 0 || var8 >= 128)) {
            var1.write(0);
            var1.write(var2[var5++]);
         }
      }
   }
}
