package com.cheatbreaker.client.kazerui.module.modules.hud.elements;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public final class HotbarStripRenderer {
   private static final ResourceLocation WIDGETS = new ResourceLocation("minecraft", "textures/gui/widgets.png");

   private HotbarStripRenderer() {
   }

   public static float a(boolean var0, int var1) {
      return 2 + (var0 ? 1 : var1) * 20;
   }

   public static float b(boolean var0, int var1) {
      return 2 + (var0 ? var1 : 1) * 20;
   }

   public static void a(float var0, float var1, boolean var2, int var3, boolean var4, float var5) {
      GlStateManager.pushMatrix();
      if (var2) {
         GlStateManager.translate(var0 + 22.0F, var1, 0.0F);
         GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
      } else {
         GlStateManager.translate(var0, var1, 0.0F);
      }

      int var6 = (int)(var5 * 255.0F) << 24 | 16777215;
      a(0.0F, 0.0F, 0.0F, var3, var4, var6);
      a(0.0F, 1.0F, var3, 1.0F, 20.0F, var6);
      a(0.0F, 21.0F, 21.0F, var3, var4, var6);
      if (var4) {
         a(var3, var6);
      }

      GlStateManager.popMatrix();
   }

   private static void a(float var0, float var1, float var2, float var3, float var4, float var5, int var6) {
      com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(WIDGETS, var0, var1, var2, var3, var4, var5, 256.0F, 256.0F, var6);
   }

   private static void a(float var0, float var1, float var2, int var3, boolean var4, int var5) {
      if (!var4) {
         a(var0, var1, var3, var2, 1.0F, var5);
      } else {
         float var6 = (var3 - 1) * 20;
         a(var0 + 1.0F, var1, 1.0F, var2, var6, 1.0F, var5);
         a(var0 + 1.0F + var6, var1, 161.0F, var2, 20.0F, 1.0F, var5);
      }
   }

   private static void a(float var0, float var1, int var2, float var3, float var4, int var5) {
      float var6 = 1 + (var2 - 1) * 20;
      float var7 = 21.0F;
      a(var0, var1, 0.0F, var3, var6, var4, var5);
      a(var0 + var6, var1, 182.0F - var7, var3, var7, var4, var5);
   }

   private static void a(int var0, int var1) {
      float var2 = a(false, var0) - 2.0F;
      a(1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, var1);
      a(var2, 1.0F, 181.0F, 0.0F, 1.0F, 1.0F, var1);
      a(1.0F, 20.0F, 0.0F, 21.0F, 1.0F, 1.0F, var1);
      a(var2, 20.0F, 181.0F, 21.0F, 1.0F, 1.0F, var1);
   }
}
