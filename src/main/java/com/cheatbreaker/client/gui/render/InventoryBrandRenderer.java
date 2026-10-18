package com.cheatbreaker.client.gui.render;

import com.cheatbreaker.client.kazerui.gui.ContainerBounds;
import com.cheatbreaker.client.util.KazerBuildInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public final class InventoryBrandRenderer {
   private static com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer bold;
   private static com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer regular;
   private static final ResourceLocation LOGO = new ResourceLocation("client/kazer_logo.png");

   private InventoryBrandRenderer() {
   }

   public static void a(int var0, int var1) {
      Minecraft var2 = Minecraft.getMinecraft();
      if (var2.currentScreen instanceof GuiContainer) {
         String var3 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.d() == null
            ? "above"
            : com.cheatbreaker.client.kazerui.module.ModuleRegistry.d().a("global", "inventoryBrand", "above");
         if (!var3.equals("hidden")) {
            if (bold == null) {
               bold = new com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer(new ResourceLocation("client/font/Ubuntu-B.ttf"), 20.0F);
               regular = new com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer(new ResourceLocation("client/font/Ubuntu-R.ttf"), 20.0F);
            }

            float var4 = bold.b("KAZER");
            float var5 = regular.b(" CLIENT");
            float var6 = var0 - var4 - var5 - 8.0F;
            float var7 = var1 - 22;
            if (var3.equals("above") && var2.currentScreen instanceof ContainerBounds) {
               ContainerBounds var8 = (ContainerBounds)var2.currentScreen;
               var6 = Math.max(29.0F, Math.min(var0 - var4 - var5 - 8.0F, var8.kazerLeft() + (var8.kazerWidth() - var4 - var5 - 29.0F) / 2.0F + 29.0F));
               int var9 = var2.currentScreen instanceof GuiContainerCreative ? 28 : 0;
               var7 = Math.max(8, var8.kazerTop() - var9 - 24);
            }

            cn.kazer.client189.render.GlStateBridge.c(1048575);
            GL11.glPushMatrix();

            try {
               cn.kazer.client189.render.GlStateBridge.b(2929);
               cn.kazer.client189.render.GlStateBridge.b(2896);
               cn.kazer.client189.render.GlStateBridge.a(false);
               cn.kazer.client189.render.GlStateBridge.a(3042);
               cn.kazer.client189.render.GlStateBridge.b(770, 771);
               cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
               RenderUtils.a(LOGO, var6 - 29.0F, var7 - 7.0F, 25.0F, 25.0F);
               bold.b("KAZER", (double)var6, (double)var7, -1);
               regular.b(" CLIENT", (double)(var6 + var4), (double)var7, -1);
               com.cheatbreaker.client.CheatBreakerClient.N().playRegular14px.b(KazerBuildInfo.footer("1.8.9"), 8.0, (double)(var1 - 12), -5592406);
            } finally {
               GL11.glPopMatrix();
               cn.kazer.client189.render.GlStateBridge.a();
            }
         }
      }
   }
}
