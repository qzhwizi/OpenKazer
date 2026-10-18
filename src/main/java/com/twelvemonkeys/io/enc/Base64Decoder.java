package com.twelvemonkeys.io.enc;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class Base64Decoder implements Decoder {
   static final byte[] PEM_ARRAY = new byte[]{
      65,
      66,
      67,
      68,
      69,
      70,
      71,
      72,
      73,
      74,
      75,
      76,
      77,
      78,
      79,
      80,
      81,
      82,
      83,
      84,
      85,
      86,
      87,
      88,
      89,
      90,
      97,
      98,
      99,
      100,
      101,
      102,
      103,
      104,
      105,
      106,
      107,
      108,
      109,
      110,
      111,
      112,
      113,
      114,
      115,
      116,
      117,
      118,
      119,
      120,
      121,
      122,
      48,
      49,
      50,
      51,
      52,
      53,
      54,
      55,
      56,
      57,
      43,
      47
   };
   static final byte[] PEM_CONVERT_ARRAY = new byte[256];
   private byte[] decodeBuffer = new byte[4];

   protected static int readFully(InputStream var0, byte[] var1, int var2, int var3) throws IOException {
      for (int var4 = 0; var4 < var3; var4++) {
         int var5 = var0.read();
         if (var5 == -1) {
            return var4 != 0 ? var4 : -1;
         }

         var1[var4 + var2] = (byte)var5;
      }

      return var3;
   }

   protected boolean decodeAtom(InputStream var1, ByteBuffer var2, int var3) throws IOException {
      byte var4 = -1;
      byte var5 = -1;
      byte var6 = -1;
      byte var7 = -1;
      if (var3 < 2) {
         throw new IOException("BASE64Decoder: Not enough bytes for an atom.");
      } else {
         int var8;
         do {
            var8 = var1.read();
            if (var8 == -1) {
               return false;
            }
         } while (var8 == 10 || var8 == 13);

         this.decodeBuffer[0] = (byte)var8;
         var8 = readFully(var1, this.decodeBuffer, 1, var3 - 1);
         if (var8 == -1) {
            return false;
         } else {
            int var9 = var3;
            if (var3 > 3 && this.decodeBuffer[3] == 61) {
               var9 = 3;
            }

            if (var9 > 2 && this.decodeBuffer[2] == 61) {
               var9 = 2;
            }

            switch (var9) {
               case 4:
                  var7 = PEM_CONVERT_ARRAY[this.decodeBuffer[3] & 255];
               case 3:
                  var6 = PEM_CONVERT_ARRAY[this.decodeBuffer[2] & 255];
               case 2:
                  var5 = PEM_CONVERT_ARRAY[this.decodeBuffer[1] & 255];
                  var4 = PEM_CONVERT_ARRAY[this.decodeBuffer[0] & 255];
               default:
                  switch (var9) {
                     case 2:
                        var2.put((byte)(var4 << 2 & 252 | var5 >>> 4 & 3));
                        break;
                     case 3:
                        var2.put((byte)(var4 << 2 & 252 | var5 >>> 4 & 3));
                        var2.put((byte)(var5 << 4 & 240 | var6 >>> 2 & 15));
                        break;
                     case 4:
                        var2.put((byte)(var4 << 2 & 252 | var5 >>> 4 & 3));
                        var2.put((byte)(var5 << 4 & 240 | var6 >>> 2 & 15));
                        var2.put((byte)(var6 << 6 & 192 | var7 & 63));
                  }

                  return true;
            }
         }
      }
   }

   @Override
   public int decode(InputStream var1, ByteBuffer var2) throws IOException {
      byte var3;
      byte var4;
      do {
         var3 = 72;
         var4 = 0;

         while (var4 + 4 < var3 && this.decodeAtom(var1, var2, 4)) {
            var4 += 4;
         }
      } while (this.decodeAtom(var1, var2, var3 - var4) && var2.remaining() > 54);

      return var2.position();
   }

   static {
      for (int var0 = 0; var0 < 255; var0++) {
         PEM_CONVERT_ARRAY[var0] = -1;
      }

      for (int var1 = 0; var1 < PEM_ARRAY.length; var1++) {
         PEM_CONVERT_ARRAY[PEM_ARRAY[var1]] = (byte)var1;
      }
   }
}
