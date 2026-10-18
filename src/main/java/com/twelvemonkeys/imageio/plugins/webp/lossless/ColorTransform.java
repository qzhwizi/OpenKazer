package com.twelvemonkeys.imageio.plugins.webp.lossless;

import java.awt.image.Raster;
import java.awt.image.WritableRaster;

final class ColorTransform implements Transform {
   private final Raster data;
   private final byte bits;

   public ColorTransform(Raster var1, byte var2) {
      this.data = var1;
      this.bits = var2;
   }

   @Override
   public void applyInverse(WritableRaster var1) {
      int var2 = var1.getWidth();
      int var3 = var1.getHeight();
      byte[] var4 = new byte[4];

      for (int var5 = 0; var5 < var3; var5++) {
         for (int var6 = 0; var6 < var2; var6++) {
            this.data.getDataElements(var6 >> this.bits, var5 >> this.bits, var4);
            ColorTransform.ColorTransformElement var7 = new ColorTransform.ColorTransformElement(var4);
            var1.getDataElements(var6, var5, var4);
            var7.inverseTransform(var4);
            var1.setDataElements(var6, var5, var4);
         }
      }
   }

   private static void colorTransform(int var0, int var1, int var2, ColorTransform.ColorTransformElement var3, int[] var4) {
      int var5 = var0 + colorTransformDelta((byte)var3.green_to_red, (byte)var2);
      int var6 = var1 + colorTransformDelta((byte)var3.green_to_blue, (byte)var2);
      var6 += colorTransformDelta((byte)var3.red_to_blue, (byte)var0);
      var4[0] = var5 & 0xFF;
      var4[1] = var6 & 0xFF;
   }

   private static byte colorTransformDelta(byte var0, byte var1) {
      return (byte)(var0 * var1 >> 5);
   }

   private static final class ColorTransformElement {
      final int green_to_red;
      final int green_to_blue;
      final int red_to_blue;

      ColorTransformElement(byte[] var1) {
         this.green_to_red = var1[2];
         this.green_to_blue = var1[1];
         this.red_to_blue = var1[0];
      }

      private void inverseTransform(byte[] var1) {
         int var2 = var1[0];
         int var3 = var1[2];
         var2 += ColorTransform.colorTransformDelta((byte)this.green_to_red, var1[1]);
         var3 += ColorTransform.colorTransformDelta((byte)this.green_to_blue, var1[1]);
         var3 += ColorTransform.colorTransformDelta((byte)this.red_to_blue, (byte)var2);
         var1[0] = (byte)(var2 & 0xFF);
         var1[2] = (byte)(var3 & 0xFF);
      }
   }
}
