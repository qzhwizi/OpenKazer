package com.twelvemonkeys.image;

import java.awt.image.RGBImageFilter;

public class BrightnessContrastFilter extends RGBImageFilter {
   private final int[] LUT;

   public BrightnessContrastFilter() {
      this(0.3F, 0.3F);
   }

   public BrightnessContrastFilter(float var1, float var2) {
      this.canFilterIndexColorModel = true;
      this.LUT = createLUT(var1, var2);
   }

   private static int[] createLUT(float var0, float var1) {
      int[] var2 = new int[256];
      double var3 = var1 > 0.0F ? Math.pow(var1, 7.0) * 127.0 : var1;
      double var5 = var0 + 1.0;

      for (int var7 = 0; var7 < 256; var7++) {
         var2[var7] = clamp((int)(127.5 * var5 + (var7 - 127) * (var3 + 1.0)));
      }

      if (var1 == 1.0F) {
         var2[127] = var2[126];
      }

      return var2;
   }

   private static int clamp(int var0) {
      if (var0 < 0) {
         return 0;
      } else {
         return var0 > 255 ? 255 : var0;
      }
   }

   @Override
   public int filterRGB(int var1, int var2, int var3) {
      int var4 = var3 >> 16 & 0xFF;
      int var5 = var3 >> 8 & 0xFF;
      int var6 = var3 & 0xFF;
      var4 = this.LUT[var4];
      var5 = this.LUT[var5];
      var6 = this.LUT[var6];
      return var3 & 0xFF000000 | var4 << 16 | var5 << 8 | var6;
   }
}
