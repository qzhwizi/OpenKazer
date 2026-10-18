package com.twelvemonkeys.imageio.color;

import java.awt.color.ColorSpace;

final class CMYKColorSpace extends ColorSpace {
   static final ColorSpace INSTANCE = new CMYKColorSpace();
   final ColorSpace sRGB = getInstance(1000);

   private CMYKColorSpace() {
      super(9, 4);
   }

   public static ColorSpace getInstance() {
      return INSTANCE;
   }

   @Override
   public float[] toRGB(float[] var1) {
      return new float[]{(1.0F - var1[0]) * (1.0F - var1[3]), (1.0F - var1[1]) * (1.0F - var1[3]), (1.0F - var1[2]) * (1.0F - var1[3])};
   }

   @Override
   public float[] fromRGB(float[] var1) {
      float var2 = 1.0F - var1[0];
      float var3 = 1.0F - var1[1];
      float var4 = 1.0F - var1[2];
      float var5 = Math.min(var2, Math.min(var3, var4));
      return new float[]{var2 - var5, var3 - var5, var4 - var5, var5};
   }

   @Override
   public float[] toCIEXYZ(float[] var1) {
      return this.sRGB.toCIEXYZ(this.toRGB(var1));
   }

   @Override
   public float[] fromCIEXYZ(float[] var1) {
      return this.sRGB.fromCIEXYZ(this.fromRGB(var1));
   }
}
