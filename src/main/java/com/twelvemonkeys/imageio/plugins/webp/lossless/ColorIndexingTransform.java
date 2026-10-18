package com.twelvemonkeys.imageio.plugins.webp.lossless;

import java.awt.image.WritableRaster;

final class ColorIndexingTransform implements Transform {
   private final byte[] colorTable;
   private final byte bits;

   public ColorIndexingTransform(byte[] var1, byte var2) {
      this.colorTable = var1;
      this.bits = var2;
   }

   @Override
   public void applyInverse(WritableRaster var1) {
      int var2 = var1.getWidth();
      int var3 = var1.getHeight();
      byte[] var4 = new byte[4];

      for (int var5 = 0; var5 < var3; var5++) {
         for (int var6 = var2 - 1; var6 >= 0; var6--) {
            int var7 = 8 >> this.bits;
            int var8 = 1 << this.bits;
            int var9 = var6 / var8;
            int var10 = var7 * (var6 % var8);
            int var11 = var1.getSample(var9, var5, 1);
            int var12 = var11 >> var10 & (1 << var7) - 1;
            System.arraycopy(this.colorTable, var12 * 4, var4, 0, 4);
            var1.setDataElements(var6, var5, var4);
         }
      }
   }
}
