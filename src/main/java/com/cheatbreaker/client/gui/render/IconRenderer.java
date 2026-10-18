package com.cheatbreaker.client.gui.render;

import org.lwjgl.opengl.GL11;

public final class IconRenderer {
   private IconRenderer() {
   }

   public static void a(com.cheatbreaker.client.gui.render.IconRenderer.a var0, float var1, float var2, float var3, int var4) {
      if (var0 != com.cheatbreaker.client.gui.render.IconRenderer.a.CHAT && var0 != com.cheatbreaker.client.gui.render.IconRenderer.a.VISUALS && var0 != com.cheatbreaker.client.gui.render.IconRenderer.a.STAR) {
         cn.kazer.client189.render.GlStateBridge.c(1048575);
         GL11.glPushMatrix();

         try {
            GL11.glTranslatef(var1, var2, 0.0F);
            GL11.glScalef(var3 / 24.0F, var3 / 24.0F, 1.0F);
            cn.kazer.client189.render.GlStateBridge.b(3553);
            cn.kazer.client189.render.GlStateBridge.b(2884);
            cn.kazer.client189.render.GlStateBridge.b(2929);
            cn.kazer.client189.render.GlStateBridge.a(false);
            cn.kazer.client189.render.GlStateBridge.b(3008);
            cn.kazer.client189.render.GlStateBridge.a(3042);
            cn.kazer.client189.render.GlStateBridge.b(770, 771);
            cn.kazer.client189.render.GlStateBridge.a((var4 >> 16 & 0xFF) / 255.0F, (var4 >> 8 & 0xFF) / 255.0F, (var4 & 0xFF) / 255.0F, (var4 >>> 24) / 255.0F);
            switch (var0) {
               case VISUALS:
                  a(12.0F, 12.0F, 10.0F, 1.6F);

                  for (int var14 = 0; var14 < 3; var14++) {
                     double var16 = var14 * Math.PI * 2.0 / 3.0;
                     float var8 = 12.0F + (float)Math.cos(var16) * 10.0F;
                     float var18 = 12.0F + (float)Math.sin(var16) * 10.0F;
                     a(var8, var18, var8 - 4.0F * (float)Math.cos(var16 - 0.7), var18 - 4.0F * (float)Math.sin(var16 - 0.7), 2.0F);
                  }

                  a(12.0F, 12.0F, 4.0F, 1.6F);
                  a(12.0F, 12.0F, 1.3F);
                  a(5.0F, 12.0F, 9.0F, 9.0F, 1.4F);
                  a(5.0F, 12.0F, 9.0F, 15.0F, 1.4F);
                  a(19.0F, 12.0F, 15.0F, 9.0F, 1.4F);
                  a(19.0F, 12.0F, 15.0F, 15.0F, 1.4F);
                  break;
               case SEARCH:
                  a(9.5F, 9.5F, 7.1F, 2.4F);
                  a(15.0F, 15.0F, 21.0F, 21.0F, 2.7F);
                  break;
               case CHAT:
                  a(new float[][]{{2.0F, 3.0F}, {22.0F, 3.0F}, {22.0F, 18.0F}, {9.0F, 18.0F}, {4.0F, 22.0F}, {4.0F, 18.0F}, {2.0F, 18.0F}}, 0.7F);
                  cn.kazer.client189.render.GlStateBridge.a(0.25F, 0.27F, 0.25F, 1.0F);

                  for (int var13 = 0; var13 < 3; var13++) {
                     a(7 + var13 * 5, 10.5F, 1.2F);
                  }
                  break;
               case STAR:
                  float[][] var5 = new float[10][2];

                  for (int var15 = 0; var15 < 10; var15++) {
                     double var17 = (-Math.PI / 2) + var15 * Math.PI / 5.0;
                     float var9 = var15 % 2 == 0 ? 10.0F : 4.6F;
                     var5[var15][0] = 12.0F + (float)Math.cos(var17) * var9;
                     var5[var15][1] = 12.0F + (float)Math.sin(var17) * var9;
                  }

                  a(var5, 0.8F);
                  break;
               case PLUS:
                  a(5.0F, 12.0F, 19.0F, 12.0F, 3.0F);
                  a(12.0F, 5.0F, 12.0F, 19.0F, 3.0F);
                  break;
               case DOWN:
                  a(6.0F, 9.0F, 12.0F, 15.0F, 2.0F);
                  a(12.0F, 15.0F, 18.0F, 9.0F, 2.0F);
                  break;
               case LINK:
                  a(8.0F, 15.0F, 5.0F, 2.0F);
                  a(16.0F, 7.0F, 5.0F, 2.0F);
                  a(8.0F, 15.0F, 16.0F, 7.0F, 2.0F);
                  break;
               case GEAR:
                  a(12.0F, 12.0F, 6.0F, 3.0F);

                  for (int var6 = 0; var6 < 8; var6++) {
                     double var7 = var6 * Math.PI / 4.0;
                     a(
                        12.0F + (float)Math.cos(var7) * 6.0F,
                        12.0F + (float)Math.sin(var7) * 6.0F,
                        12.0F + (float)Math.cos(var7) * 9.0F,
                        12.0F + (float)Math.sin(var7) * 9.0F,
                        3.0F
                     );
                  }
            }
         } finally {
            GL11.glPopMatrix();
            cn.kazer.client189.render.GlStateBridge.a();
         }
      } else {
         com.cheatbreaker.client.gui.render.GlyphTextureRenderer.a(var0.name(), var1, var2, var3, var4);
      }
   }

   private static void a(float var0, float var1, float var2) {
      GL11.glBegin(6);
      GL11.glVertex2f(var0, var1);

      for (int var3 = 0; var3 <= 48; var3++) {
         GL11.glVertex2d(var0 + Math.cos(var3 * Math.PI / 24.0) * var2, var1 + Math.sin(var3 * Math.PI / 24.0) * var2);
      }

      GL11.glEnd();
   }

   private static void a(float var0, float var1, float var2, float var3) {
      GL11.glBegin(8);

      for (int var4 = 0; var4 <= 64; var4++) {
         double var5 = var4 * Math.PI / 32.0;
         GL11.glVertex2d(var0 + Math.cos(var5) * var2, var1 + Math.sin(var5) * var2);
         GL11.glVertex2d(var0 + Math.cos(var5) * (var2 - var3), var1 + Math.sin(var5) * (var2 - var3));
      }

      GL11.glEnd();
   }

   private static void a(float var0, float var1, float var2, float var3, float var4) {
      double var5 = Math.hypot(var2 - var0, var3 - var1);
      float var7 = (float)((var3 - var1) / var5 * var4 / 2.0);
      float var8 = (float)((var2 - var0) / var5 * var4 / 2.0);
      GL11.glBegin(7);
      GL11.glVertex2f(var0 - var7, var1 + var8);
      GL11.glVertex2f(var2 - var7, var3 + var8);
      GL11.glVertex2f(var2 + var7, var3 - var8);
      GL11.glVertex2f(var0 + var7, var1 - var8);
      GL11.glEnd();
      a(var0, var1, var4 / 2.0F);
      a(var2, var3, var4 / 2.0F);
   }

   private static void a(float[][] var0, float var1) {
      GL11.glBegin(6);
      GL11.glVertex2f(12.0F, 12.0F);

      for (int var2 = 0; var2 <= var0.length; var2++) {
         float[] var3 = var0[(var2 + var0.length - 1) % var0.length];
         float[] var4 = var0[var2 % var0.length];
         float[] var5 = var0[(var2 + 1) % var0.length];
         float var6 = (float)Math.hypot(var3[0] - var4[0], var3[1] - var4[1]);
         float var7 = (float)Math.hypot(var5[0] - var4[0], var5[1] - var4[1]);
         float var8 = var4[0] + (var3[0] - var4[0]) * var1 / var6;
         float var9 = var4[1] + (var3[1] - var4[1]) * var1 / var6;
         float var10 = var4[0] + (var5[0] - var4[0]) * var1 / var7;
         float var11 = var4[1] + (var5[1] - var4[1]) * var1 / var7;

         for (int var12 = 0; var12 <= 6; var12++) {
            float var13 = var12 / 6.0F;
            float var14 = 1.0F - var13;
            GL11.glVertex2f(
               var14 * var14 * var8 + 2.0F * var14 * var13 * var4[0] + var13 * var13 * var10,
               var14 * var14 * var9 + 2.0F * var14 * var13 * var4[1] + var13 * var13 * var11
            );
         }
      }

      GL11.glEnd();
   }

   public static enum a {
      SEARCH,
      CHAT,
      STAR,
      LINK,
      PLUS,
      GEAR,
      DOWN,
      VISUALS;
   }
}
