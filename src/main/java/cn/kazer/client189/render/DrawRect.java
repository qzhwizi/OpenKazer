package cn.kazer.client189.render;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public final class DrawRect {
   private DrawRect() {
   }

   public static void a(float var0, float var1, float var2, float var3, float var4, int var5, int var6) {
      b(var0, var1, var2, var3, var4, var5, var6);
   }

   public static void b(float var0, float var1, float var2, float var3, float var4, int var5, int var6) {
      a(var0, var1, var2, var1 + var4, var5);
      a(var0, var3 - var4, var2, var3, var5);
      a(var0, var1 + var4, var0 + var4, var3 - var4, var5);
      a(var2 - var4, var1 + var4, var2, var3 - var4, var5);
      a(var0 + var4, var1 + var4, var2 - var4, var3 - var4, var6);
   }

   public static void a(float var0, float var1, float var2, float var3, int var4, int var5) {
      GlStateManager.disableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GL11.glShadeModel(7425);
      GL11.glBegin(7);
      GL11.glColor4f((var4 >> 16 & 0xFF) / 255.0F, (var4 >> 8 & 0xFF) / 255.0F, (var4 & 0xFF) / 255.0F, (var4 >>> 24) / 255.0F);
      GL11.glVertex2f(var2, var1);
      GL11.glVertex2f(var0, var1);
      GL11.glColor4f((var5 >> 16 & 0xFF) / 255.0F, (var5 >> 8 & 0xFF) / 255.0F, (var5 & 0xFF) / 255.0F, (var5 >>> 24) / 255.0F);
      GL11.glVertex2f(var0, var3);
      GL11.glVertex2f(var2, var3);
      GL11.glEnd();
      GL11.glShadeModel(7424);
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.enableTexture2D();
      GlStateManager.resetColor();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void a(float var0, float var1, float var2, float var3, int var4) {
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color((var4 >> 16 & 0xFF) / 255.0F, (var4 >> 8 & 0xFF) / 255.0F, (var4 & 0xFF) / 255.0F, (var4 >>> 24) / 255.0F);
      GL11.glBegin(7);
      GL11.glVertex2f(var0, var3);
      GL11.glVertex2f(var2, var3);
      GL11.glVertex2f(var2, var1);
      GL11.glVertex2f(var0, var1);
      GL11.glEnd();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }
}
