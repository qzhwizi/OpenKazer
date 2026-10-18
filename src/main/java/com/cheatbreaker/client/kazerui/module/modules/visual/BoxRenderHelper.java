package com.cheatbreaker.client.kazerui.module.modules.visual;

import org.lwjgl.opengl.GL11;

final class BoxRenderHelper {
   static void a(int var0) {
      GL11.glColor4f((var0 >> 16 & 0xFF) / 255.0F, (var0 >> 8 & 0xFF) / 255.0F, (var0 & 0xFF) / 255.0F, (var0 >>> 24) / 255.0F);
   }

   static void a(double var0, double var2, double var4, double var6, double var8, double var10) {
      GL11.glBegin(1);
      c(var0, var2, var4, var6, var2, var4);
      c(var6, var2, var4, var6, var2, var10);
      c(var6, var2, var10, var0, var2, var10);
      c(var0, var2, var10, var0, var2, var4);
      c(var0, var8, var4, var6, var8, var4);
      c(var6, var8, var4, var6, var8, var10);
      c(var6, var8, var10, var0, var8, var10);
      c(var0, var8, var10, var0, var8, var4);
      c(var0, var2, var4, var0, var8, var4);
      c(var6, var2, var4, var6, var8, var4);
      c(var6, var2, var10, var6, var8, var10);
      c(var0, var2, var10, var0, var8, var10);
      GL11.glEnd();
   }

   private static void c(double var0, double var2, double var4, double var6, double var8, double var10) {
      GL11.glVertex3d(var0, var2, var4);
      GL11.glVertex3d(var6, var8, var10);
   }

   static void b(double var0, double var2, double var4, double var6, double var8, double var10) {
      GL11.glBegin(7);
      GL11.glVertex3d(var0, var2, var4);
      GL11.glVertex3d(var6, var2, var4);
      GL11.glVertex3d(var6, var8, var4);
      GL11.glVertex3d(var0, var8, var4);
      GL11.glVertex3d(var6, var2, var10);
      GL11.glVertex3d(var0, var2, var10);
      GL11.glVertex3d(var0, var8, var10);
      GL11.glVertex3d(var6, var8, var10);
      GL11.glVertex3d(var0, var2, var10);
      GL11.glVertex3d(var0, var2, var4);
      GL11.glVertex3d(var0, var8, var4);
      GL11.glVertex3d(var0, var8, var10);
      GL11.glVertex3d(var6, var2, var4);
      GL11.glVertex3d(var6, var2, var10);
      GL11.glVertex3d(var6, var8, var10);
      GL11.glVertex3d(var6, var8, var4);
      GL11.glVertex3d(var0, var8, var4);
      GL11.glVertex3d(var6, var8, var4);
      GL11.glVertex3d(var6, var8, var10);
      GL11.glVertex3d(var0, var8, var10);
      GL11.glVertex3d(var0, var2, var10);
      GL11.glVertex3d(var6, var2, var10);
      GL11.glVertex3d(var6, var2, var4);
      GL11.glVertex3d(var0, var2, var4);
      GL11.glEnd();
   }

   private BoxRenderHelper() {
   }
}
