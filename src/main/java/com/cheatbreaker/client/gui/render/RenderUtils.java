package com.cheatbreaker.client.gui.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderUtils {
   protected static float lIIIIlIIllIIlIIlIIIlIIllI = 0.0F;
   private static final double lIIIIIIIIIlIllIIllIlIIlIl = 30.0;

   public static void a(float var0, float var1, float var2, float var3, int var4, int var5) {
      float var6 = 0.00390625F;
      cn.kazer.client189.render.ImmediateTessellator var7 = cn.kazer.client189.render.ImmediateTessellator.instance;
      var7.a();
      var7.a(var0, var1 + var5, lIIIIlIIllIIlIIlIIIlIIllI, var2 * var6, (var3 + var5) * var6);
      var7.a(var0 + var4, var1 + var5, lIIIIlIIllIIlIIlIIIlIIllI, (var2 + var4) * var6, (var3 + var5) * var6);
      var7.a(var0 + var4, var1, lIIIIlIIllIIlIIlIIIlIIllI, (var2 + var4) * var6, var3 * var6);
      var7.a(var0, var1, lIIIIlIIllIIlIIlIIIlIIllI, var2 * var6, var3 * var6);
      var7.b();
   }

   public static void a(int var0, int var1, int var2, int var3, ScaledResolution var4) {
      int var5 = var4.getScaleFactor();
      int var6 = var3 - var1;
      int var7 = var2 - var0;
      int var8 = var4.getScaledHeight() - var3;
      GL11.glScissor(var0 * var5, var8 * var5, var7 * var5, var6 * var5);
   }

   public static void a(ResourceLocation var0, float var1, float var2, float var3) {
      float var4 = var1 * 2.0F;
      float var5 = var1 * 2.0F;
      float var6 = 0.0F;
      float var7 = 0.0F;
      cn.kazer.client189.render.GlStateBridge.a(3042);
      Minecraft.getMinecraft().getTextureManager().bindTexture(var0);
      GL11.glBegin(7);
      GL11.glTexCoord2d(var6 / var1, var7 / var1);
      GL11.glVertex2d(var2, var3);
      GL11.glTexCoord2d(var6 / var1, (var7 + var1) / var1);
      GL11.glVertex2d(var2, var3 + var5);
      GL11.glTexCoord2d((var6 + var1) / var1, (var7 + var1) / var1);
      GL11.glVertex2d(var2 + var4, var3 + var5);
      GL11.glTexCoord2d((var6 + var1) / var1, var7 / var1);
      GL11.glVertex2d(var2 + var4, var3);
      GL11.glEnd();
      cn.kazer.client189.render.GlStateBridge.b(3042);
   }

   public static void b(ResourceLocation var0, float var1, float var2, float var3) {
      float var4 = var1 * 2.0F;
      float var5 = var1 * 2.0F;
      float var6 = 0.0F;
      float var7 = 0.0F;
      cn.kazer.client189.render.GlStateBridge.a(3042);
      Minecraft.getMinecraft().getTextureManager().bindTexture(var0);
      GL11.glBegin(7);
      GL11.glTexCoord2d(var6 / var1, var7 / var1);
      GL11.glVertex2d(var2, var3);
      GL11.glTexCoord2d(var6 / var1, (var7 + var1) / var1);
      GL11.glVertex2d(var2, var3 + var5);
      GL11.glTexCoord2d((var6 + var1) / var1, (var7 + var1) / var1);
      GL11.glVertex2d(var2 + var4, var3 + var5);
      GL11.glTexCoord2d((var6 + var1) / var1, var7 / var1);
      GL11.glVertex2d(var2 + var4, var3);
      GL11.glEnd();
      cn.kazer.client189.render.GlStateBridge.b(3042);
   }

   public static void a(ResourceLocation var0, float var1, float var2, float var3, float var4) {
      float var5 = var3 / 2.0F;
      float var6 = 0.0F;
      float var7 = 0.0F;
      cn.kazer.client189.render.GlStateBridge.a(3042);
      Minecraft.getMinecraft().getTextureManager().bindTexture(var0);
      GL11.glBegin(7);
      GL11.glTexCoord2d(var6 / var5, var7 / var5);
      GL11.glVertex2d(var1, var2);
      GL11.glTexCoord2d(var6 / var5, (var7 + var5) / var5);
      GL11.glVertex2d(var1, var2 + var4);
      GL11.glTexCoord2d((var6 + var5) / var5, (var7 + var5) / var5);
      GL11.glVertex2d(var1 + var3, var2 + var4);
      GL11.glTexCoord2d((var6 + var5) / var5, var7 / var5);
      GL11.glVertex2d(var1 + var3, var2);
      GL11.glEnd();
      cn.kazer.client189.render.GlStateBridge.b(3042);
   }

   public static void a(int var0, int var1, int var2, int var3, float var4, int var5) {
      int var6 = var3 - var1;
      int var7 = var2 - var0;
      int var8 = var5 - var3;
      GL11.glScissor((int)(var0 * var4), (int)(var8 * var4), (int)(var7 * var4), (int)(var6 * var4));
   }

   public static void a(double var0, double var2, double var4, double var6, double var8, int var10) {
      float var12 = (var10 >> 24 & 0xFF) / 255.0F;
      float var13 = (var10 >> 16 & 0xFF) / 255.0F;
      float var14 = (var10 >> 8 & 0xFF) / 255.0F;
      float var15 = (var10 & 0xFF) / 255.0F;
      cn.kazer.client189.render.GlStateBridge.c(0);
      GL11.glScaled(0.5, 0.5, 0.5);
      var0 *= 2.0;
      var2 *= 2.0;
      var4 *= 2.0;
      var6 *= 2.0;
      cn.kazer.client189.render.GlStateBridge.a(3042);
      cn.kazer.client189.render.GlStateBridge.b(3553);
      cn.kazer.client189.render.GlStateBridge.a(var13, var14, var15, var12);
      cn.kazer.client189.render.GlStateBridge.a(2848);
      GL11.glBegin(9);

      for (byte var11 = 0; var11 <= 90; var11 += 3) {
         GL11.glVertex2d(var0 + var8 + Math.sin(var11 * Math.PI / 180.0) * (var8 * -1.0), var2 + var8 + Math.cos(var11 * Math.PI / 180.0) * (var8 * -1.0));
      }

      for (byte var20 = 90; var20 <= 180; var20 += 3) {
         GL11.glVertex2d(var0 + var8 + Math.sin(var20 * Math.PI / 180.0) * (var8 * -1.0), var6 - var8 + Math.cos(var20 * Math.PI / 180.0) * (var8 * -1.0));
      }

      for (byte var21 = 0; var21 <= 90; var21 += 3) {
         GL11.glVertex2d(var4 - var8 + Math.sin(var21 * Math.PI / 180.0) * var8, var6 - var8 + Math.cos(var21 * Math.PI / 180.0) * var8);
      }

      for (byte var22 = 90; var22 <= 180; var22 += 3) {
         GL11.glVertex2d(var4 - var8 + Math.sin(var22 * Math.PI / 180.0) * var8, var2 + var8 + Math.cos(var22 * Math.PI / 180.0) * var8);
      }

      GL11.glEnd();
      cn.kazer.client189.render.GlStateBridge.a(3553);
      cn.kazer.client189.render.GlStateBridge.b(3042);
      cn.kazer.client189.render.GlStateBridge.b(2848);
      cn.kazer.client189.render.GlStateBridge.b(3042);
      cn.kazer.client189.render.GlStateBridge.a(3553);
      GL11.glScaled(2.0, 2.0, 2.0);
      cn.kazer.client189.render.GlStateBridge.a();
   }

   public static void a(double var0, double var2, double var4) {
      cn.kazer.client189.render.GlStateBridge.a(3042);
      cn.kazer.client189.render.GlStateBridge.b(3553);
      cn.kazer.client189.render.GlStateBridge.b(770, 771);
      cn.kazer.client189.render.ImmediateTessellator var6 = cn.kazer.client189.render.ImmediateTessellator.instance;
      var6.a(6);
      var6.a(var0, var2, (double)lIIIIlIIllIIlIIlIIIlIIllI);
      double var7 = Math.PI * 2;
      double var9 = var7 / 30.0;

      for (double var11 = -var9; var11 < var7; var11 += var9) {
         var6.a(var0 + var4 * Math.cos(-var11), var2 + var4 * Math.sin(-var11), (double)lIIIIlIIllIIlIIlIIIlIIllI);
      }

      var6.b();
      cn.kazer.client189.render.GlStateBridge.a(3553);
      cn.kazer.client189.render.GlStateBridge.b(3042);
   }

   public static void a(double var0, double var2, double var4, double var6, double var8, int var10, double var11) {
      cn.kazer.client189.render.GlStateBridge.a(3042);
      cn.kazer.client189.render.GlStateBridge.b(3553);
      cn.kazer.client189.render.GlStateBridge.a(2848);
      var8 = (var8 + var10) % var10;
      cn.kazer.client189.render.ImmediateTessellator var13 = cn.kazer.client189.render.ImmediateTessellator.instance;

      for (double var14 = 360.0 / var10 * var8; var14 < 360.0 / var10 * (var8 + var11); var14++) {
         double var16 = var14 * Math.PI / 180.0;
         double var18 = (var14 - 1.0) * Math.PI / 180.0;
         double[] var20 = new double[]{Math.cos(var16) * var4, -Math.sin(var16) * var4, Math.cos(var18) * var4, -Math.sin(var18) * var4};
         double[] var21 = new double[]{Math.cos(var16) * var6, -Math.sin(var16) * var6, Math.cos(var18) * var6, -Math.sin(var18) * var6};
         var13.a(7);
         var13.a(var0 + var21[0], var2 + var21[1], 0.0);
         var13.a(var0 + var21[2], var2 + var21[3], 0.0);
         var13.a(var0 + var20[2], var2 + var20[3], 0.0);
         var13.a(var0 + var20[0], var2 + var20[1], 0.0);
         var13.b();
      }

      cn.kazer.client189.render.GlStateBridge.a(3553);
      cn.kazer.client189.render.GlStateBridge.b(3042);
      cn.kazer.client189.render.GlStateBridge.b(2848);
      cn.kazer.client189.render.GlStateBridge.b(3042);
      cn.kazer.client189.render.GlStateBridge.a(3553);
   }

   public static void a(float var0, float var1, float var2, int var3) {
      if (var1 < var0) {
         float var4 = var0;
         var0 = var1;
         var1 = var4;
      }

      cn.kazer.client189.render.DrawRect.a(var0, var2, var1 + 1.0F, var2 + 1.0F, var3);
   }

   public static void b(float var0, float var1, float var2, int var3) {
      if (var2 < var1) {
         float var4 = var1;
         var1 = var2;
         var2 = var4;
      }

      cn.kazer.client189.render.DrawRect.a(var0, var1 + 1.0F, var0 + 1.0F, var2, var3);
   }

   public static void b(float var0, float var1, float var2, float var3, int var4, int var5) {
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      float var6;
      float var7;
      float var8;
      float var9;
      cn.kazer.client189.render.DrawRect.a((var6 = var0 * 2.0F) + 1.0F, (var7 = var1 * 2.0F) + 1.0F, (var8 = var2 * 2.0F) - 1.0F, (var9 = var3 * 2.0F) - 1.0F, var5);
      b(var6, var7 + 1.0F, var9 - 2.0F, var4);
      b(var8 - 1.0F, var7 + 1.0F, var9 - 2.0F, var4);
      a(var6 + 2.0F, var8 - 3.0F, var7, var4);
      a(var6 + 2.0F, var8 - 3.0F, var9 - 1.0F, var4);
      a(var6 + 1.0F, var6 + 1.0F, var7 + 1.0F, var4);
      a(var8 - 2.0F, var8 - 2.0F, var7 + 1.0F, var4);
      a(var8 - 2.0F, var8 - 2.0F, var9 - 2.0F, var4);
      a(var6 + 1.0F, var6 + 1.0F, var9 - 2.0F, var4);
      GL11.glScalef(2.0F, 2.0F, 2.0F);
   }

   public static void a(float var0, float var1, float var2, float var3, int var4, int var5, int var6) {
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      float var7;
      float var8;
      float var9;
      float var10;
      cn.kazer.client189.render.DrawRect.a((var7 = var0 * 2.0F) + 1.0F, (var8 = var1 * 2.0F) + 1.0F, (var9 = var2 * 2.0F) - 1.0F, (var10 = var3 * 2.0F) - 1.0F, var5, var6);
      b(var7, var8 + 1.0F, var10 - 2.0F, var4);
      b(var9 - 1.0F, var8 + 1.0F, var10 - 2.0F, var4);
      a(var7 + 2.0F, var9 - 3.0F, var8, var4);
      a(var7 + 2.0F, var9 - 3.0F, var10 - 1.0F, var4);
      a(var7 + 1.0F, var7 + 1.0F, var8 + 1.0F, var4);
      a(var9 - 2.0F, var9 - 2.0F, var8 + 1.0F, var4);
      a(var9 - 2.0F, var9 - 2.0F, var10 - 2.0F, var4);
      a(var7 + 1.0F, var7 + 1.0F, var10 - 2.0F, var4);
      GL11.glScalef(2.0F, 2.0F, 2.0F);
   }
}
