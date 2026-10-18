package com.twelvemonkeys.imageio.plugins.webp.lossless;

import java.awt.image.WritableRaster;

final class SubtractGreenTransform implements Transform {
   private static void addGreenToBlueAndRed(byte[] var0) {
      var0[0] = (byte)(var0[0] + var0[1] & 0xFF);
      var0[2] = (byte)(var0[2] + var0[1] & 0xFF);
   }

   @Override
   public void applyInverse(WritableRaster var1) {
      int var2 = var1.getWidth();
      int var3 = var1.getHeight();
      byte[] var4 = new byte[4];

      for (int var5 = 0; var5 < var3; var5++) {
         for (int var6 = 0; var6 < var2; var6++) {
            var1.getDataElements(var6, var5, var4);
            addGreenToBlueAndRed(var4);
            var1.setDataElements(var6, var5, var4);
         }
      }
   }
}
