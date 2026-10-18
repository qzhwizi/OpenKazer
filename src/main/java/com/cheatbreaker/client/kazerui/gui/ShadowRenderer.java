package com.cheatbreaker.client.kazerui.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D.Float;
import java.awt.image.BufferedImage;

public final class ShadowRenderer {
   private ShadowRenderer() {
   }

   public static float a(float var0) {
      return (float)Math.ceil(var0 * 1.5F) + 2.0F;
   }

   public static BufferedImage a(float var0, float var1, float var2, float var3, float var4, float var5, float var6) {
      float var7 = var4 / 2.0F * var6;
      float var8 = a(var4) * var6;
      int var9 = (int)Math.ceil(var0 * var6 + var8 * 2.0F + Math.max(0.0F, var5) * var6);
      int var10 = (int)Math.ceil(var1 * var6 + var8 * 2.0F + Math.max(0.0F, var3 + var5) * var6);
      float[] var11 = new float[var9 * var10];
      BufferedImage var12 = new BufferedImage(var9, var10, 2);
      Graphics2D var13 = var12.createGraphics();
      var13.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      var13.setColor(Color.WHITE);
      float var14 = var8 - var5 * var6;
      float var15 = var8 - var5 * var6 + var3 * var6;
      float var16 = (var0 + var5 * 2.0F) * var6;
      float var17 = (var1 + var5 * 2.0F) * var6;
      float var18 = Math.max(0.0F, var2 + var5) * var6 * 2.0F;
      var13.fill(new Float(var14, var15, var16, var17, var18, var18));
      var13.dispose();

      for (int var19 = 0; var19 < var11.length; var19++) {
         var11[var19] = (var12.getRGB(var19 % var9, var19 / var9) >>> 24) / 255.0F;
      }

      var11 = a(a(var11, var9, var10, var7, true), var9, var10, var7, false);
      BufferedImage var22 = new BufferedImage(var9, var10, 2);

      for (int var20 = 0; var20 < var11.length; var20++) {
         var22.setRGB(var20 % var9, var20 / var9, Math.min(255, Math.round(var11[var20] * 255.0F)) << 24 | 16777215);
      }

      return var22;
   }

   private static float[] a(float[] var0, int var1, int var2, float var3, boolean var4) {
      if (var3 <= 0.0F) {
         return var0;
      } else {
         int var5 = (int)Math.ceil(var3 * 3.0F);
         float[] var6 = new float[var5 * 2 + 1];
         float var7 = 0.0F;

         for (int var8 = -var5; var8 <= var5; var8++) {
            var6[var8 + var5] = (float)Math.exp(-(var8 * var8) / (2.0F * var3 * var3));
            var7 += var6[var8 + var5];
         }

         for (int var15 = 0; var15 < var6.length; var15++) {
            var6[var15] /= var7;
         }

         float[] var16 = new float[var0.length];

         for (int var9 = 0; var9 < var2; var9++) {
            for (int var10 = 0; var10 < var1; var10++) {
               float var11 = 0.0F;

               for (int var12 = -var5; var12 <= var5; var12++) {
                  int var13 = var4 ? var10 + var12 : var10;
                  int var14 = var4 ? var9 : var9 + var12;
                  if (var13 >= 0 && var14 >= 0 && var13 < var1 && var14 < var2) {
                     var11 += var0[var14 * var1 + var13] * var6[var12 + var5];
                  }
               }

               var16[var9 * var1 + var10] = var11;
            }
         }

         return var16;
      }
   }
}
