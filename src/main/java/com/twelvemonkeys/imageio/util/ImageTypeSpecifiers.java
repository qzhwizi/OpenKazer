package com.twelvemonkeys.imageio.util;

import com.twelvemonkeys.imageio.color.DiscreteAlphaIndexColorModel;
import com.twelvemonkeys.lang.Validate;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.MultiPixelPackedSampleModel;
import java.awt.image.RenderedImage;
import javax.imageio.ImageTypeSpecifier;

public final class ImageTypeSpecifiers {
   private static final ImageTypeSpecifier TYPE_INT_RGB = createPackedOddBits(ColorSpace.getInstance(1000), 24, 16711680, 65280, 255, 0, 3, false);
   private static final ImageTypeSpecifier TYPE_INT_BGR = createPackedOddBits(ColorSpace.getInstance(1000), 24, 255, 65280, 16711680, 0, 3, false);
   private static final ImageTypeSpecifier TYPE_USHORT_565_RGB = createPackedOddBits(ColorSpace.getInstance(1000), 16, 63488, 2016, 31, 0, 1, false);
   private static final ImageTypeSpecifier TYPE_USHORT_555_RGB = createPackedOddBits(ColorSpace.getInstance(1000), 15, 31744, 992, 31, 0, 1, false);

   private ImageTypeSpecifiers() {
   }

   public static ImageTypeSpecifier createFromBufferedImageType(int var0) {
      switch (var0) {
         case 1:
            return TYPE_INT_RGB;
         case 2:
         case 3:
         case 5:
         case 6:
         case 7:
         default:
            return ImageTypeSpecifier.createFromBufferedImageType(var0);
         case 4:
            return TYPE_INT_BGR;
         case 8:
            return TYPE_USHORT_565_RGB;
         case 9:
            return TYPE_USHORT_555_RGB;
      }
   }

   public static ImageTypeSpecifier createPacked(ColorSpace var0, int var1, int var2, int var3, int var4, int var5, boolean var6) {
      int var7 = calculateRequiredBits(var1 | var2 | var3 | var4);
      return var7 != 32
         ? createPackedOddBits(var0, var7, var1, var2, var3, var4, var5, var6)
         : ImageTypeSpecifier.createPacked(var0, var1, var2, var3, var4, var5, var6);
   }

   private static int calculateRequiredBits(int var0) {
      int var1 = 1;

      while ((var0 >>>= 1) != 0) {
         var1++;
      }

      return var1;
   }

   static ImageTypeSpecifier createPackedOddBits(ColorSpace var0, int var1, int var2, int var3, int var4, int var5, int var6, boolean var7) {
      Validate.notNull(var0, "colorSpace");
      Validate.isTrue(var0.getType() == 5, var0, "ColorSpace must be TYPE_RGB");
      Validate.isTrue(var2 != 0 || var3 != 0 || var4 != 0 || var5 != 0, "No mask has at least 1 bit set");
      DirectColorModel var8 = new DirectColorModel(var0, var1, var2, var3, var4, var5, var7, var6);
      return new ImageTypeSpecifier(var8, var8.createCompatibleSampleModel(1, 1));
   }

   public static ImageTypeSpecifier createInterleaved(ColorSpace var0, int[] var1, int var2, boolean var3, boolean var4) {
      return var2 == 3
         ? UInt32ImageTypeSpecifier.createInterleaved(var0, var1, var3, var4)
         : ImageTypeSpecifier.createInterleaved(var0, var1, var2, var3, var4);
   }

   public static ImageTypeSpecifier createBanded(ColorSpace var0, int[] var1, int[] var2, int var3, boolean var4, boolean var5) {
      return var3 == 3
         ? UInt32ImageTypeSpecifier.createBanded(var0, var1, var2, var4, var5)
         : ImageTypeSpecifier.createBanded(var0, var1, var2, var3, var4, var5);
   }

   public static ImageTypeSpecifier createGrayscale(int var0, int var1) {
      if (var0 == 16 && var1 == 2) {
         return new Int16ImageTypeSpecifier(ColorSpace.getInstance(1003), new int[]{0}, false, false);
      } else if (var0 == 32 && var1 == 3) {
         return UInt32ImageTypeSpecifier.createInterleaved(ColorSpace.getInstance(1003), new int[]{0}, false, false);
      } else {
         return var1 != 4 && var1 != 5
            ? ImageTypeSpecifier.createGrayscale(var0, var1, false)
            : ImageTypeSpecifier.createInterleaved(ColorSpace.getInstance(1003), new int[]{0}, var1, false, false);
      }
   }

   public static ImageTypeSpecifier createGrayscale(int var0, int var1, boolean var2) {
      if (var0 == 16 && var1 == 2) {
         return new Int16ImageTypeSpecifier(ColorSpace.getInstance(1003), new int[]{0, 1}, true, var2);
      } else if (var0 == 32 && var1 == 3) {
         return UInt32ImageTypeSpecifier.createInterleaved(ColorSpace.getInstance(1003), new int[]{0, 1}, true, var2);
      } else {
         return var1 != 4 && var1 != 5
            ? ImageTypeSpecifier.createGrayscale(var0, var1, false, var2)
            : ImageTypeSpecifier.createInterleaved(ColorSpace.getInstance(1003), new int[]{0, 1}, var1, true, var2);
      }
   }

   public static ImageTypeSpecifier createPackedGrayscale(ColorSpace var0, int var1, int var2) {
      Validate.notNull(var0, "colorSpace");
      Validate.isTrue(var0.getType() == 6, var0, "ColorSpace must be TYPE_GRAY");
      Validate.isTrue(var1 == 1 || var1 == 2 || var1 == 4, var1, "bits must be 1, 2, or 4: %s");
      Validate.isTrue(var2 == 0, var2, "dataType must be TYPE_BYTE: %s");
      int var3 = 1 << var1;
      IndexColorModel var4;
      if (ColorSpace.getInstance(1003).equals(var0)) {
         byte[] var5 = new byte[var3];

         for (int var6 = 0; var6 < var3; var6++) {
            var5[var6] = (byte)(var6 * 255 / (var3 - 1));
         }

         var4 = new IndexColorModel(var1, var3, var5, var5, var5);
      } else {
         byte[] var11 = new byte[var3];
         byte[] var13 = new byte[var3];
         byte[] var7 = new byte[var3];

         for (int var8 = 0; var8 < var3; var8++) {
            float[] var9 = new float[]{(float)var8 / (var3 - 1)};
            float[] var10 = var0.toRGB(var9);
            var11[var8] = (byte)Math.round(var10[0] * 255.0F);
            var13[var8] = (byte)Math.round(var10[1] * 255.0F);
            var7[var8] = (byte)Math.round(var10[2] * 255.0F);
         }

         var4 = new IndexColorModel(var1, var3, var11, var13, var7);
      }

      MultiPixelPackedSampleModel var12 = new MultiPixelPackedSampleModel(var2, 1, 1, var1);
      return new ImageTypeSpecifier(var4, var12);
   }

   public static ImageTypeSpecifier createIndexed(byte[] var0, byte[] var1, byte[] var2, byte[] var3, int var4, int var5) {
      return ImageTypeSpecifier.createIndexed(var0, var1, var2, var3, var4, var5);
   }

   public static ImageTypeSpecifier createIndexed(int[] var0, boolean var1, int var2, int var3, int var4) {
      return createFromIndexColorModel(new IndexColorModel(var3, var0.length, var0, 0, var1, var2, var4));
   }

   public static ImageTypeSpecifier createFromIndexColorModel(IndexColorModel var0) {
      return new IndexedImageTypeSpecifier(var0);
   }

   public static ImageTypeSpecifier createDiscreteAlphaIndexedFromIndexColorModel(IndexColorModel var0) {
      DiscreteAlphaIndexColorModel var1 = new DiscreteAlphaIndexColorModel(var0);
      return new ImageTypeSpecifier(var1, var1.createCompatibleSampleModel(1, 1));
   }

   public static ImageTypeSpecifier createDiscreteExtraSamplesIndexedFromIndexColorModel(IndexColorModel var0, int var1, boolean var2) {
      DiscreteAlphaIndexColorModel var3 = new DiscreteAlphaIndexColorModel(var0, var1, var2);
      return new ImageTypeSpecifier(var3, var3.createCompatibleSampleModel(1, 1));
   }

   public static ImageTypeSpecifier createFromRenderedImage(RenderedImage var0) {
      if (var0 == null) {
         throw new IllegalArgumentException("image == null!");
      } else {
         if (var0 instanceof BufferedImage) {
            int var1 = ((BufferedImage)var0).getType();
            if (var1 != 0 && var1 != 12 && var1 != 13) {
               return createFromBufferedImageType(var1);
            }
         }

         return new ImageTypeSpecifier(var0);
      }
   }
}
