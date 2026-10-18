package com.twelvemonkeys.imageio.color;

import java.awt.color.ColorSpace;
import java.awt.image.ComponentColorModel;

public final class UInt32ColorModel extends ComponentColorModel {
   public UInt32ColorModel(ColorSpace var1, boolean var2, boolean var3) {
      super(var1, var2, var3, var2 ? 3 : 1, 3);
   }

   @Override
   public float[] getNormalizedComponents(Object var1, float[] var2, int var3) {
      int var4 = this.getNumComponents();
      if (var2 == null) {
         var2 = new float[var4 + var3];
      }

      int[] var5 = (int[])var1;
      int var6 = 0;

      for (int var7 = var3; var6 < var4; var7++) {
         var2[var7] = (float)(var5[var6] & 4294967295L) / (float)((1L << this.getComponentSize(var6)) - 1L);
         var6++;
      }

      var6 = this.getNumColorComponents();
      if (this.hasAlpha() && this.isAlphaPremultiplied()) {
         float var11 = var2[var6 + var3];
         if (var11 != 0.0F) {
            float var8 = 1.0F / var11;

            for (int var9 = var3; var9 < var6 + var3; var9++) {
               var2[var9] *= var8;
            }
         }
      }

      return var2;
   }
}
