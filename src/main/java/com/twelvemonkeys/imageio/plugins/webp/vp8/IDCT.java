package com.twelvemonkeys.imageio.plugins.webp.vp8;

final class IDCT {
   private static final int cospi8sqrt2minus1 = 20091;
   private static final int sinpi8sqrt2 = 35468;

   public static int[][] idct4x4llm(int[] var0) {
      int[] var5 = new int[16];
      int var8 = 0;

      for (int var9 = 0; var8 < 4; var8++) {
         int var1 = var0[var9] + var0[var9 + 8];
         int var2 = var0[var9] - var0[var9 + 8];
         int var6 = var0[var9 + 4] * 35468 >> 16;
         int var7 = var0[var9 + 12] + (var0[var9 + 12] * 20091 >> 16);
         int var3 = var6 - var7;
         var6 = var0[var9 + 4] + (var0[var9 + 4] * 20091 >> 16);
         var7 = var0[var9 + 12] * 35468 >> 16;
         int var4 = var6 + var7;
         var5[var9] = var1 + var4;
         var5[var9 + 12] = var1 - var4;
         var5[var9 + 4] = var2 + var3;
         var5[var9 + 8] = var2 - var3;
         var9++;
      }

      var8 = 0;
      int[][] var23 = new int[4][4];
      int var10 = 0;

      for (int var11 = 0; var10 < 4; var10++) {
         int var12 = var5[var11 * 4] + var5[var11 * 4 + 2];
         int var13 = var5[var11 * 4] - var5[var11 * 4 + 2];
         int var17 = var5[var11 * 4 + 1] * 35468 >> 16;
         int var20 = var5[var11 * 4 + 3] + (var5[var11 * 4 + 3] * 20091 >> 16);
         int var14 = var17 - var20;
         var17 = var5[var11 * 4 + 1] + (var5[var11 * 4 + 1] * 20091 >> 16);
         var20 = var5[var11 * 4 + 3] * 35468 >> 16;
         int var15 = var17 + var20;
         var5[var11 * 4] = var12 + var15 + 4 >> 3;
         var5[var11 * 4 + 3] = var12 - var15 + 4 >> 3;
         var5[var11 * 4 + 1] = var13 + var14 + 4 >> 3;
         var5[var11 * 4 + 2] = var13 - var14 + 4 >> 3;
         var23[0][var8] = var12 + var15 + 4 >> 3;
         var23[3][var8] = var12 - var15 + 4 >> 3;
         var23[1][var8] = var13 + var14 + 4 >> 3;
         var23[2][var8] = var13 - var14 + 4 >> 3;
         var11++;
         var8++;
      }

      return var23;
   }

   public static int[][] iwalsh4x4(int[] var0) {
      int[] var9 = new int[16];
      int[][] var10 = new int[4][4];
      int var11 = 0;

      for (int var12 = 0; var11 < 4; var11++) {
         int var1 = var0[var12] + var0[var12 + 12];
         int var2 = var0[var12 + 4] + var0[var12 + 8];
         int var3 = var0[var12 + 4] - var0[var12 + 8];
         int var4 = var0[var12] - var0[var12 + 12];
         var9[var12] = var1 + var2;
         var9[var12 + 4] = var3 + var4;
         var9[var12 + 8] = var1 - var2;
         var9[var12 + 12] = var4 - var3;
         var12++;
      }

      var11 = 0;

      for (byte var18 = 0; var11 < 4; var11++) {
         int var13 = var9[var18] + var9[var18 + 3];
         int var14 = var9[var18 + 1] + var9[var18 + 2];
         int var15 = var9[var18 + 1] - var9[var18 + 2];
         int var16 = var9[var18] - var9[var18 + 3];
         int var5 = var13 + var14;
         int var6 = var15 + var16;
         int var7 = var13 - var14;
         int var8 = var16 - var15;
         var9[var18] = var5 + 3 >> 3;
         var9[var18 + 1] = var6 + 3 >> 3;
         var9[var18 + 2] = var7 + 3 >> 3;
         var9[var18 + 3] = var8 + 3 >> 3;
         var10[0][var11] = var5 + 3 >> 3;
         var10[1][var11] = var6 + 3 >> 3;
         var10[2][var11] = var7 + 3 >> 3;
         var10[3][var11] = var8 + 3 >> 3;
         var18 += 4;
      }

      return var10;
   }
}
