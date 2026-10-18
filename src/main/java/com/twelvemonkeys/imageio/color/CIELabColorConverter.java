package com.twelvemonkeys.imageio.color;

import com.twelvemonkeys.lang.Validate;

public final class CIELabColorConverter {
   private final float[] whitePoint;

   public CIELabColorConverter(CIELabColorConverter.Illuminant var1) {
      this.whitePoint = Validate.notNull(var1, "illuminant").getWhitePoint();
   }

   private float clamp(float var1) {
      if (var1 < 0.0F) {
         return 0.0F;
      } else {
         return var1 > 255.0F ? 255.0F : var1;
      }
   }

   public void toRGB(float var1, float var2, float var3, float[] var4) {
      this.XYZtoRGB(this.LABtoXYZ(var1, var2, var3, var4), var4);
   }

   private float[] LABtoXYZ(float var1, float var2, float var3, float[] var4) {
      float var5 = (var1 + 16.0F) / 116.0F;
      float var6 = var5 * var5 * var5;
      float var7 = var2 / 500.0F + var5;
      float var8 = var7 * var7 * var7;
      float var9 = var5 - var3 / 200.0F;
      float var10 = var9 * var9 * var9;
      if (var6 > 0.008856F) {
         var5 = var6;
      } else {
         var5 = (var5 - 0.13793103F) / 7.787F;
      }

      if (var8 > 0.008856F) {
         var7 = var8;
      } else {
         var7 = (var7 - 0.13793103F) / 7.787F;
      }

      if (var10 > 0.008856F) {
         var9 = var10;
      } else {
         var9 = (var9 - 0.13793103F) / 7.787F;
      }

      var4[0] = var7 * this.whitePoint[0];
      var4[1] = var5 * this.whitePoint[1];
      var4[2] = var9 * this.whitePoint[2];
      return var4;
   }

   private float[] XYZtoRGB(float[] var1, float[] var2) {
      return this.XYZtoRGB(var1[0], var1[1], var1[2], var2);
   }

   private float[] XYZtoRGB(float var1, float var2, float var3, float[] var4) {
      float var5 = var1 / 100.0F;
      float var6 = var2 / 100.0F;
      float var7 = var3 / 100.0F;
      float var8 = var5 * 3.2406F + var6 * -1.5372F + var7 * -0.4986F;
      float var9 = var5 * -0.9689F + var6 * 1.8758F + var7 * 0.0415F;
      float var10 = var5 * 0.0557F + var6 * -0.204F + var7 * 1.057F;
      if (var8 > 0.0031308F) {
         var8 = 1.055F * (float)pow(var8, 0.4166666666666667) - 0.055F;
      } else {
         var8 *= 12.92F;
      }

      if (var9 > 0.0031308F) {
         var9 = 1.055F * (float)pow(var9, 0.4166666666666667) - 0.055F;
      } else {
         var9 *= 12.92F;
      }

      if (var10 > 0.0031308F) {
         var10 = 1.055F * (float)pow(var10, 0.4166666666666667) - 0.055F;
      } else {
         var10 *= 12.92F;
      }

      var4[0] = this.clamp(var8 * 255.0F);
      var4[1] = this.clamp(var9 * 255.0F);
      var4[2] = this.clamp(var10 * 255.0F);
      return var4;
   }

   static double pow(double var0, double var2) {
      long var4 = Double.doubleToLongBits(var0);
      long var6 = (long)(var2 * (var4 - 4606921280493453312L)) + 4606921280493453312L;
      return Double.longBitsToDouble(var6);
   }

   public static enum Illuminant {
      D50(new float[]{96.4212F, 100.0F, 82.5188F}),
      D65(new float[]{95.0429F, 100.0F, 108.89F});

      private final float[] whitePoint;

      private Illuminant(float[] var3) {
         this.whitePoint = Validate.isTrue(var3 != null && var3.length == 3, var3, "Bad white point definition: %s");
      }

      public float[] getWhitePoint() {
         return this.whitePoint;
      }
   }
}
