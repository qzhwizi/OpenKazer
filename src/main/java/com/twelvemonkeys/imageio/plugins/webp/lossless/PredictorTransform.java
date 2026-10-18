package com.twelvemonkeys.imageio.plugins.webp.lossless;

import java.awt.image.Raster;
import java.awt.image.WritableRaster;

final class PredictorTransform implements Transform {
   private final Raster data;
   private final byte bits;

   public PredictorTransform(Raster var1, byte var2) {
      this.data = var1;
      this.bits = var2;
   }

   @Override
   public void applyInverse(WritableRaster var1) {
      int var2 = var1.getWidth();
      int var3 = var1.getHeight();
      byte[] var4 = new byte[4];
      var1.getDataElements(0, 0, var4);
      var4[3] += -1;
      var1.setDataElements(0, 0, var4);
      byte[] var5 = new byte[4];
      byte[] var6 = new byte[4];
      byte[] var7 = new byte[4];

      for (int var8 = 1; var8 < var2; var8++) {
         var1.getDataElements(var8, 0, var4);
         var1.getDataElements(var8 - 1, 0, var5);
         addPixels(var4, var5);
         var1.setDataElements(var8, 0, var4);
      }

      for (int var15 = 1; var15 < var3; var15++) {
         var1.getDataElements(0, var15, var4);
         var1.getDataElements(0, var15 - 1, var5);
         addPixels(var4, var5);
         var1.setDataElements(0, var15, var4);
      }

      for (int var16 = 1; var16 < var3; var16++) {
         for (int var9 = 1; var9 < var2; var9++) {
            int var10 = this.data.getSample(var9 >> this.bits, var16 >> this.bits, 1);
            var1.getDataElements(var9, var16, var4);
            int var11 = var9 - 1;
            int var12 = var16 - 1;
            int var13 = var9 == var2 - 1 ? 0 : var9 + 1;
            int var14 = var9 == var2 - 1 ? var16 : var12;
            switch (var10) {
               case 0:
                  var4[3] += -1;
                  break;
               case 1:
                  var1.getDataElements(var11, var16, var5);
                  addPixels(var4, var5);
                  break;
               case 2:
                  var1.getDataElements(var9, var12, var5);
                  addPixels(var4, var5);
                  break;
               case 3:
                  var1.getDataElements(var13, var14, var5);
                  addPixels(var4, var5);
                  break;
               case 4:
                  var1.getDataElements(var11, var12, var5);
                  addPixels(var4, var5);
                  break;
               case 5:
                  var1.getDataElements(var11, var16, var5);
                  var1.getDataElements(var13, var14, var6);
                  average2(var5, var6);
                  var1.getDataElements(var9, var12, var6);
                  average2(var5, var6);
                  addPixels(var4, var5);
                  break;
               case 6:
                  var1.getDataElements(var11, var16, var5);
                  var1.getDataElements(var11, var12, var6);
                  average2(var5, var6);
                  addPixels(var4, var5);
                  break;
               case 7:
                  var1.getDataElements(var11, var16, var5);
                  var1.getDataElements(var9, var12, var6);
                  average2(var5, var6);
                  addPixels(var4, var5);
                  break;
               case 8:
                  var1.getDataElements(var11, var12, var5);
                  var1.getDataElements(var9, var12, var6);
                  average2(var5, var6);
                  addPixels(var4, var5);
                  break;
               case 9:
                  var1.getDataElements(var9, var12, var5);
                  var1.getDataElements(var13, var14, var6);
                  average2(var5, var6);
                  addPixels(var4, var5);
                  break;
               case 10:
                  var1.getDataElements(var11, var16, var5);
                  var1.getDataElements(var11, var12, var6);
                  average2(var5, var6);
                  var1.getDataElements(var9, var12, var6);
                  var1.getDataElements(var13, var14, var7);
                  average2(var6, var7);
                  average2(var5, var6);
                  addPixels(var4, var5);
                  break;
               case 11:
                  var1.getDataElements(var11, var16, var5);
                  var1.getDataElements(var9, var12, var6);
                  var1.getDataElements(var11, var12, var7);
                  addPixels(var4, select(var5, var6, var7));
                  break;
               case 12:
                  var1.getDataElements(var11, var16, var5);
                  var1.getDataElements(var9, var12, var6);
                  var1.getDataElements(var11, var12, var7);
                  clampAddSubtractFull(var5, var6, var7);
                  addPixels(var4, var5);
                  break;
               case 13:
                  var1.getDataElements(var11, var16, var5);
                  var1.getDataElements(var9, var12, var6);
                  average2(var5, var6);
                  var1.getDataElements(var11, var12, var6);
                  clampAddSubtractHalf(var5, var6);
                  addPixels(var4, var5);
            }

            var1.setDataElements(var9, var16, var4);
         }
      }
   }

   private static byte[] select(byte[] var0, byte[] var1, byte[] var2) {
      int var3 = addSubtractFull(var0[3], var1[3], var2[3]);
      int var4 = addSubtractFull(var0[0], var1[0], var2[0]);
      int var5 = addSubtractFull(var0[1], var1[1], var2[1]);
      int var6 = addSubtractFull(var0[2], var1[2], var2[2]);
      int var7 = manhattanDistance(var0, var3, var4, var5, var6);
      int var8 = manhattanDistance(var1, var3, var4, var5, var6);
      return var7 < var8 ? var0 : var1;
   }

   private static int manhattanDistance(byte[] var0, int var1, int var2, int var3, int var4) {
      return Math.abs(var1 - (var0[3] & 0xFF)) + Math.abs(var2 - (var0[0] & 0xFF)) + Math.abs(var3 - (var0[1] & 0xFF)) + Math.abs(var4 - (var0[2] & 0xFF));
   }

   private static void average2(byte[] var0, byte[] var1) {
      var0[0] = (byte)(((var0[0] & 255) + (var1[0] & 255)) / 2);
      var0[1] = (byte)(((var0[1] & 255) + (var1[1] & 255)) / 2);
      var0[2] = (byte)(((var0[2] & 255) + (var1[2] & 255)) / 2);
      var0[3] = (byte)(((var0[3] & 255) + (var1[3] & 255)) / 2);
   }

   private static int clamp(int var0) {
      return Math.max(0, Math.min(var0, 255));
   }

   private static void clampAddSubtractFull(byte[] var0, byte[] var1, byte[] var2) {
      var0[0] = (byte)clamp(addSubtractFull(var0[0], var1[0], var2[0]));
      var0[1] = (byte)clamp(addSubtractFull(var0[1], var1[1], var2[1]));
      var0[2] = (byte)clamp(addSubtractFull(var0[2], var1[2], var2[2]));
      var0[3] = (byte)clamp(addSubtractFull(var0[3], var1[3], var2[3]));
   }

   private static void clampAddSubtractHalf(byte[] var0, byte[] var1) {
      var0[0] = (byte)clamp(addSubtractHalf(var0[0], var1[0]));
      var0[1] = (byte)clamp(addSubtractHalf(var0[1], var1[1]));
      var0[2] = (byte)clamp(addSubtractHalf(var0[2], var1[2]));
      var0[3] = (byte)clamp(addSubtractHalf(var0[3], var1[3]));
   }

   private static int addSubtractFull(byte var0, byte var1, byte var2) {
      return (var0 & 0xFF) + (var1 & 0xFF) - (var2 & 0xFF);
   }

   private static int addSubtractHalf(byte var0, byte var1) {
      return (var0 & 0xFF) + ((var0 & 0xFF) - (var1 & 0xFF)) / 2;
   }

   private static void addPixels(byte[] var0, byte[] var1) {
      var0[0] += var1[0];
      var0[1] += var1[1];
      var0[2] += var1[2];
      var0[3] += var1[3];
   }
}
