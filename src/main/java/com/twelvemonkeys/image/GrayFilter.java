package com.twelvemonkeys.image;

import java.awt.image.RGBImageFilter;

public class GrayFilter extends RGBImageFilter {
   private int low;
   private float range;

   public GrayFilter() {
      this.canFilterIndexColorModel = true;
      this.low = 0;
      this.range = 1.0F;
   }

   public GrayFilter(float var1, float var2) {
      this.canFilterIndexColorModel = true;
      this.low = 0;
      this.range = 1.0F;
      if (var1 > var2) {
         var1 = 0.0F;
      }

      if (var1 < 0.0F) {
         var1 = 0.0F;
      } else if (var1 > 1.0F) {
         var1 = 1.0F;
      }

      if (var2 < 0.0F) {
         var2 = 0.0F;
      } else if (var2 > 1.0F) {
         var2 = 1.0F;
      }

      this.low = (int)(var1 * 255.0F);
      this.range = var2 - var1;
   }

   public GrayFilter(int var1, int var2) {
      this(var1 / 255.0F, var2 / 255.0F);
   }

   @Override
   public int filterRGB(int var1, int var2, int var3) {
      int var4 = var3 >> 16 & 0xFF;
      int var5 = var3 >> 8 & 0xFF;
      int var6 = var3 & 0xFF;
      int var7 = (222 * var4 + 707 * var5 + 71 * var6) / 1000;
      if (this.range != 1.0F) {
         var7 = this.low + (int)(var7 * this.range);
      }

      return var3 & 0xFF000000 | var7 << 16 | var7 << 8 | var7;
   }
}
