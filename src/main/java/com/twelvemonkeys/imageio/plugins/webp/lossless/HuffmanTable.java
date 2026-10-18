package com.twelvemonkeys.imageio.plugins.webp.lossless;

import com.twelvemonkeys.imageio.plugins.webp.LSBBitReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.IIOException;

final class HuffmanTable {
   private static final int LEVEL1_BITS = 8;
   private static final int[] L_CODE_ORDER = new int[]{17, 18, 0, 1, 2, 3, 4, 5, 16, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
   private final int[] level1 = new int[256];
   private final List<int[]> level2 = new ArrayList<>();

   public HuffmanTable(LSBBitReader var1, int var2) throws IOException {
      boolean var3 = var1.readBit() == 1;
      if (var3) {
         int var4 = var1.readBit() + 1;
         boolean var5 = var1.readBit() == 1;
         short var6 = (short)var1.readBits(var5 ? 8 : 1);
         if (var4 == 2) {
            short var7 = (short)var1.readBits(8);

            for (byte var8 = 0; var8 < 256; var8 += 2) {
               this.level1[var8] = 65536 | var6;
               this.level1[var8 + 1] = 65536 | var7;
            }
         } else {
            Arrays.fill(this.level1, var6);
         }
      } else {
         int var9 = (int)(var1.readBits(4) + 4L);
         short[] var10 = new short[L_CODE_ORDER.length];
         int var11 = 0;

         for (int var12 = 0; var12 < var9; var12++) {
            short var14 = (short)var1.readBits(3);
            var10[L_CODE_ORDER[var12]] = var14;
            if (var14 > 0) {
               var11++;
            }
         }

         short[] var13 = readCodeLengths(var1, var10, var2, var11);
         this.buildFromLengths(var13);
      }
   }

   private HuffmanTable(short[] var1, int var2) {
      this.buildFromLengths(var1, var2);
   }

   private void buildFromLengths(short[] var1) {
      int var2 = 0;

      for (short var6 : var1) {
         if (var6 != 0) {
            var2++;
         }
      }

      this.buildFromLengths(var1, var2);
   }

   private void buildFromLengths(short[] var1, int var2) {
      int[] var3 = new int[var2];
      int var4 = 0;

      for (int var5 = 0; var5 < var1.length; var5++) {
         if (var1[var5] != 0) {
            var3[var4++] = var1[var5] << 16 | var5;
         }
      }

      if (var2 == 1) {
         Arrays.fill(this.level1, var3[0] & 65535);
      } else {
         Arrays.sort(var3);
         int[] var16 = new int[16];

         for (int var9 : var3) {
            var16[var9 >>> 16]++;
         }

         int var17 = 0;
         byte var18 = 2;
         var4 = 0;

         for (int var20 = 1; var20 <= 8; var18 <<= 1) {
            while (var16[var20] > 0) {
               int var22 = var3[var4++];

               for (int var10 = var17; var10 < this.level1.length; var10 += var18) {
                  this.level1[var10] = var22;
               }

               var17 = this.nextCode(var17, var20);
               var16[var20]--;
            }

            var20++;
         }

         short var21 = 255;
         int var23 = -1;
         int[] var24 = null;
         var18 = 2;

         for (int var11 = 9; var11 <= 15; var18 <<= 1) {
            while (var16[var11] > 0) {
               int var12 = var3[var4++];
               if ((var17 & var21) != var23) {
                  int var13 = nextTableBitSize(var16, var11, 8);
                  int var14 = 1 << var13;
                  var24 = new int[var14];
                  var23 = var17 & var21;
                  this.level2.add(var24);
                  this.level1[var23] = 8 + var13 << 16 | this.level2.size() - 1;
               }

               int var25 = var11 - 8 << 16 | var12 & 65535;

               for (int var26 = var17 >>> 8; var26 < var24.length; var26 += var18) {
                  var24[var26] = var25;
               }

               var17 = this.nextCode(var17, var11);
               var16[var11]--;
            }

            var11++;
         }
      }
   }

   private static int nextTableBitSize(int[] var0, int var1, int var2) {
      int var3 = 1 << var1 - var2;

      while (var1 < 15) {
         var3 -= var0[var1];
         if (var3 <= 0) {
            break;
         }

         var1++;
         var3 <<= 1;
      }

      return var1 - var2;
   }

   private int nextCode(int var1, int var2) {
      int var3 = ~var1 & (1 << var2) - 1;
      int var4 = Integer.highestOneBit(var3);
      return var1 & var4 - 1 | var4;
   }

   private static short[] readCodeLengths(LSBBitReader var0, short[] var1, int var2, int var3) throws IOException {
      HuffmanTable var4 = new HuffmanTable(var1, var3);
      int var5;
      if (var0.readBit() == 1) {
         int var6 = (int)(2L + 2L * var0.readBits(3));
         var5 = (int)(2L + var0.readBits(var6));
      } else {
         var5 = var2;
      }

      short[] var14 = new short[var2];
      short var7 = 8;

      for (int var8 = 0; var8 < var2 && var5 > 0; var5--) {
         short var9 = var4.readSymbol(var0);
         if (var9 < 16) {
            var14[var8] = var9;
            if (var9 != 0) {
               var7 = var9;
            }
         } else {
            short var10 = 0;
            byte var11;
            byte var12;
            switch (var9) {
               case 16:
                  var10 = var7;
                  var11 = 2;
                  var12 = 3;
                  break;
               case 17:
                  var11 = 3;
                  var12 = 3;
                  break;
               case 18:
                  var11 = 7;
                  var12 = 11;
                  break;
               default:
                  throw new IIOException("Huffman: Unreachable: Decoded Code Length > 18.");
            }

            int var13 = (int)(var0.readBits(var11) + var12);
            if (var8 + var13 > var2) {
               throw new IIOException(
                  String.format("Huffman: Code length repeat count overflows alphabet: Start index: %d, count: %d, alphabet size: %d", var8, var13, var2)
               );
            }

            Arrays.fill(var14, var8, var8 + var13, var10);
            var8 += var13 - 1;
         }

         var8++;
      }

      return var14;
   }

   public short readSymbol(LSBBitReader var1) throws IOException {
      int var2 = (int)var1.peekBits(8);
      int var3 = this.level1[var2];
      int var4 = var3 >>> 16;
      if (var4 > 8) {
         var1.readBits(8);
         int var5 = (int)var1.peekBits(var4 - 8);
         var3 = this.level2.get(var3 & 65535)[var5];
         var4 = var3 >>> 16;
      }

      var1.readBits(var4);
      return (short)(var3 & 65535);
   }
}
