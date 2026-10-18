package com.cheatbreaker.client.kazerui.module.modulerender;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public final class HudRenderUtils {
   private HudRenderUtils() {
   }

   public static FontRenderer a() {
      return Minecraft.getMinecraft().fontRendererObj;
   }

   public static int b() {
      return a().FONT_HEIGHT;
   }

   public static float a(String var0) {
      return a().getStringWidth(var0);
   }

   public static void a(float var0, float var1, float var2, float var3, int var4) {
      if (!(var2 <= 0.0F) && !(var3 <= 0.0F)) {
         com.cheatbreaker.client.kazerui.gui.UiGraphics.a(var0, var1, var0 + var2, var1 + var3, var4);
      }
   }

   public static void a(com.cheatbreaker.client.kazerui.module.settings.ColorSetting var0, float var1, float var2, float var3, float var4) {
      a(var1, var2, var3, var4, var0.a(var1 + var2));
   }

   public static void a(com.cheatbreaker.client.kazerui.module.settings.ColorSetting var0, float var1, float var2, float var3, float var4, float var5) {
      int var6 = var0.a(var1 + var2);
      a(var1 - var5, var2 - var5, var5, var4 + var5 * 2.0F, var6);
      a(var1 + var3, var2 - var5, var5, var4 + var5 * 2.0F, var6);
      a(var1, var2 - var5, var3, var5, var6);
      a(var1, var2 + var4, var3, var5, var6);
   }

   public static float a(String var0, float var1, float var2, int var3, boolean var4) {
      GlStateManager.enableBlend();
      return a().drawString(var0, var1, var2, var3, var4);
   }

   public static float a(com.cheatbreaker.client.kazerui.module.settings.ColorSetting var0, String var1, float var2, float var3, boolean var4) {
      return a(var1, var2, var3, var0.a(var2 + var3), var4);
   }

   public static void b(com.cheatbreaker.client.kazerui.module.settings.ColorSetting var0, String var1, float var2, float var3, boolean var4) {
      a(var0, var1, var2 - a(var1) / 2.0F, var3, var4);
   }

   public static void a(float var0, float var1, float var2, float var3, int var4, int var5, boolean var6) {
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.disableAlpha();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.shadeModel(7425);
      WorldRenderer var7 = Tessellator.getInstance().getWorldRenderer();
      var7.begin(7, DefaultVertexFormats.POSITION_COLOR);
      int var9 = var6 ? var5 : var4;
      int var11 = var6 ? var4 : var5;
      a(var7, var2, var1, var9);
      a(var7, var0, var1, var4);
      a(var7, var0, var3, var11);
      a(var7, var2, var3, var5);
      Tessellator.getInstance().draw();
      GlStateManager.shadeModel(7424);
      GlStateManager.enableAlpha();
      GlStateManager.enableTexture2D();
   }

   private static void a(WorldRenderer var0, float var1, float var2, int var3) {
      var0.pos(var1, var2, 0.0).color(var3 >> 16 & 0xFF, var3 >> 8 & 0xFF, var3 & 0xFF, var3 >>> 24).endVertex();
   }

   public static void a(float var0, float var1, float var2, float var3, float var4, float var5, int var6) {
      c(var6);
      WorldRenderer var7 = Tessellator.getInstance().getWorldRenderer();
      var7.begin(4, DefaultVertexFormats.POSITION);
      var7.pos(var0, var1, 0.0).endVertex();
      var7.pos(var2, var3, 0.0).endVertex();
      var7.pos(var4, var5, 0.0).endVertex();
      Tessellator.getInstance().draw();
      c();
   }

   public static void a(float var0, float var1, float var2, float var3, float var4, int var5) {
      if (!(var2 <= 0.0F) && !(var3 <= 0.0F)) {
         var4 = Math.max(0.0F, Math.min(var4, Math.min(var2, var3) / 2.0F));
         c(var5);
         WorldRenderer var6 = Tessellator.getInstance().getWorldRenderer();
         var6.begin(9, DefaultVertexFormats.POSITION);
         a(var6, var0 + var4, var1 + var4, var4, 180);
         a(var6, var0 + var2 - var4, var1 + var4, var4, 270);
         a(var6, var0 + var2 - var4, var1 + var3 - var4, var4, 0);
         a(var6, var0 + var4, var1 + var3 - var4, var4, 90);
         Tessellator.getInstance().draw();
         c();
      }
   }

   private static void a(WorldRenderer var0, float var1, float var2, float var3, int var4) {
      for (int var5 = var4; var5 <= var4 + 90; var5 += 10) {
         double var6 = Math.toRadians(var5);
         var0.pos(var1 + Math.cos(var6) * var3, var2 + Math.sin(var6) * var3, 0.0).endVertex();
      }
   }

   private static void c(int var0) {
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color((var0 >> 16 & 0xFF) / 255.0F, (var0 >> 8 & 0xFF) / 255.0F, (var0 & 0xFF) / 255.0F, (var0 >>> 24) / 255.0F);
   }

   private static void c() {
      GlStateManager.enableTexture2D();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void a(ResourceLocation var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      Minecraft.getMinecraft().getTextureManager().bindTexture(var0);
      GlStateManager.enableBlend();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      WorldRenderer var9 = Tessellator.getInstance().getWorldRenderer();
      var9.begin(7, DefaultVertexFormats.POSITION_TEX);
      var9.pos(var1, var2 + var4, 0.0).tex(var5, var8).endVertex();
      var9.pos(var1 + var3, var2 + var4, 0.0).tex(var7, var8).endVertex();
      var9.pos(var1 + var3, var2, 0.0).tex(var7, var6).endVertex();
      var9.pos(var1, var2, 0.0).tex(var5, var6).endVertex();
      Tessellator.getInstance().draw();
   }

   public static void a(ItemStack var0, float var1, float var2) {
      if (var0 != null) {
         Minecraft var3 = Minecraft.getMinecraft();
         GlStateManager.pushMatrix();
         GlStateManager.translate(var1, var2, 0.0F);
         GlStateManager.enableRescaleNormal();
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         RenderHelper.enableGUIStandardItemLighting();
         var3.getRenderItem().renderItemAndEffectIntoGUI(var0, 0, 0);
         RenderHelper.disableStandardItemLighting();
         GlStateManager.disableRescaleNormal();
         GlStateManager.popMatrix();
         GlStateManager.enableBlend();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   public static void a(ResourceLocation var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9) {
      if (!(var5 <= 0.0F) && !(var6 <= 0.0F)) {
         Minecraft.getMinecraft().getTextureManager().bindTexture(var0);
         GlStateManager.enableBlend();
         GlStateManager.enableTexture2D();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.color((var9 >> 16 & 0xFF) / 255.0F, (var9 >> 8 & 0xFF) / 255.0F, (var9 & 0xFF) / 255.0F, (var9 >>> 24) / 255.0F);
         WorldRenderer var10 = Tessellator.getInstance().getWorldRenderer();
         var10.begin(7, DefaultVertexFormats.POSITION_TEX);
         var10.pos(var1, var2 + var6, 0.0).tex(var3 / var7, (var4 + var6) / var8).endVertex();
         var10.pos(var1 + var5, var2 + var6, 0.0).tex((var3 + var5) / var7, (var4 + var6) / var8).endVertex();
         var10.pos(var1 + var5, var2, 0.0).tex((var3 + var5) / var7, var4 / var8).endVertex();
         var10.pos(var1, var2, 0.0).tex(var3 / var7, var4 / var8).endVertex();
         Tessellator.getInstance().draw();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   public static int a(int var0) {
      return var0 >>> 24 < 4 ? 67108864 | var0 & 16777215 : var0;
   }

   public static int b(int var0) {
      return (var0 & 16579836) >> 2 | var0 & 0xFF000000;
   }

   public static void a(ResourceLocation var0, float var1, float var2, float var3, float var4, int var5) {
      Minecraft.getMinecraft().getTextureManager().bindTexture(var0);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color((var5 >> 16 & 0xFF) / 255.0F, (var5 >> 8 & 0xFF) / 255.0F, (var5 & 0xFF) / 255.0F, (var5 >>> 24) / 255.0F);
      WorldRenderer var6 = Tessellator.getInstance().getWorldRenderer();
      var6.begin(7, DefaultVertexFormats.POSITION_TEX);
      var6.pos(var1, var2 + var4, 0.0).tex(0.0, 1.0).endVertex();
      var6.pos(var1 + var3, var2 + var4, 0.0).tex(1.0, 1.0).endVertex();
      var6.pos(var1 + var3, var2, 0.0).tex(1.0, 0.0).endVertex();
      var6.pos(var1, var2, 0.0).tex(0.0, 0.0).endVertex();
      Tessellator.getInstance().draw();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }
}
