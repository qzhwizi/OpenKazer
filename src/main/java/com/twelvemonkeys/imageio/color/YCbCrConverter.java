package com.twelvemonkeys.imageio.color;

public final class YCbCrConverter {
   private static final int SCALEBITS = 16;
   private static final int MAXJSAMPLE = 255;
   private static final int CENTERJSAMPLE = 128;
   private static final int ONE_HALF = 32768;

   public static void convertYCbCr2RGB(byte[] var0, byte[] var1, double[] var2, double[] var3, int var4) {
      double var5;
      double var7;
      double var9;
      if (var3 == null) {
         var5 = var0[var4] & 255;
         var7 = (var0[var4 + 1] & 255) - 128;
         var9 = (var0[var4 + 2] & 255) - 128;
      } else {
         var5 = ((var0[var4] & 255) - var3[0]) * 255.0 / (var3[1] - var3[0]);
         var7 = ((var0[var4 + 1] & 255) - var3[2]) * 127.0 / (var3[3] - var3[2]);
         var9 = ((var0[var4 + 2] & 255) - var3[4]) * 127.0 / (var3[5] - var3[4]);
      }

      double var11 = var2[0];
      double var13 = var2[1];
      double var15 = var2[2];
      int var17 = (int)Math.round(var9 * (2.0 - 2.0 * var11) + var5);
      int var18 = (int)Math.round(var7 * (2.0 - 2.0 * var15) + var5);
      int var19 = (int)Math.round((var5 - var11 * var17 - var15 * var18) / var13);
      var1[var4] = clamp(var17);
      var1[var4 + 2] = clamp(var18);
      var1[var4 + 1] = clamp(var19);
   }

   public static void convertJPEGYCbCr2RGB(byte[] var0, byte[] var1, int var2) {
      int var3 = var0[var2] & 255;
      int var4 = var0[var2 + 1] & 255;
      int var5 = var0[var2 + 2] & 255;
      var1[var2] = clamp(var3 + YCbCrConverter.JPEG.Cr_R_LUT[var5]);
      var1[var2 + 1] = clamp(var3 + (YCbCrConverter.JPEG.Cb_G_LUT[var4] + YCbCrConverter.JPEG.Cr_G_LUT[var5] >> 16));
      var1[var2 + 2] = clamp(var3 + YCbCrConverter.JPEG.Cb_B_LUT[var4]);
   }

   public static void convertRec601YCbCr2RGB(byte[] var0, byte[] var1, int var2) {
      int var3 = var0[var2] & 255;
      int var4 = var0[var2 + 1] & 255;
      int var5 = var0[var2 + 2] & 255;
      var1[var2] = clamp(YCbCrConverter.ITU_R_601.Y_LUT[var3] + YCbCrConverter.ITU_R_601.Cr_R_LUT[var5]);
      var1[var2 + 1] = clamp(YCbCrConverter.ITU_R_601.Y_LUT[var3] + (YCbCrConverter.ITU_R_601.Cr_G_LUT[var5] + YCbCrConverter.ITU_R_601.Cb_G_LUT[var4] >> 16));
      var1[var2 + 2] = clamp(YCbCrConverter.ITU_R_601.Y_LUT[var3] + YCbCrConverter.ITU_R_601.Cb_B_LUT[var4]);
   }

   private static byte clamp(int var0) {
      return (byte)Math.max(0, Math.min(255, var0));
   }

   private static final class ITU_R_601 {
      private static final int[] Cr_R_LUT = new int[256];
      private static final int[] Cb_B_LUT = new int[256];
      private static final int[] Cr_G_LUT = new int[256];
      private static final int[] Cb_G_LUT = new int[256];
      private static final int[] Y_LUT = new int[256];

      private static void buildYCCtoRGBtable() {
         if (ColorSpaces.DEBUG) {
            System.err.println("Building ITU-R REC.601 YCbCr conversion table");
         }

         int var0 = 0;

         for (int var1 = -128; var0 <= 255; var1++) {
            Cr_R_LUT[var0] = 104597 * var1 + 32768 >> 16;
            Cb_B_LUT[var0] = 132201 * var1 + 32768 >> 16;
            Cr_G_LUT[var0] = -53279 * var1;
            Cb_G_LUT[var0] = -25674 * var1 + 32768;
            Y_LUT[var0] = 76309 * (var0 - 16) + 32768 >> 16;
            var0++;
         }
      }

      static {
         buildYCCtoRGBtable();
      }
   }

   private static final class JPEG {
      private static final int[] Cr_R_LUT = new int[256];
      private static final int[] Cb_B_LUT = new int[256];
      private static final int[] Cr_G_LUT = new int[256];
      private static final int[] Cb_G_LUT = new int[256];

      private static void buildYCCtoRGBtable() {
         if (ColorSpaces.DEBUG) {
            System.err.println("Building JPEG YCbCr conversion table");
         }

         int var0 = 0;

         for (int var1 = -128; var0 <= 255; var1++) {
            Cr_R_LUT[var0] = (int)(91881.972 * var1 + 32768.0) >> 16;
            Cb_B_LUT[var0] = (int)(116130.292 * var1 + 32768.0) >> 16;
            Cr_G_LUT[var0] = -46802 * var1;
            Cb_G_LUT[var0] = -22554 * var1 + 32768;
            var0++;
         }
      }

      static {
         buildYCCtoRGBtable();
      }
   }
}
