package com.cheatbreaker.client.kazerui.module.modules.mechanic;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public final class DamageTintModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static com.cheatbreaker.client.kazerui.module.modules.mechanic.DamageTintModule instance;
   private static final ResourceLocation VIGNETTE = new ResourceLocation("textures/misc/vignette.png");
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting color = this.a("vignetteColor", -65536);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting intensity = this.a("vignetteIntensity", 1.0F, 0.0F, 1.0F);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting threshold = this.a("showVignetteBelow", 50, 0, 100);

   public DamageTintModule() {
      super("DAMAGE_TINT", false);
      instance = this;
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(this.color, this.intensity, this.threshold);
   }

   public static void a(ScaledResolution var0) {
      com.cheatbreaker.client.kazerui.module.modules.mechanic.DamageTintModule var1 = instance;
      if (var1 != null && var1.a() && m().thePlayer != null && m().currentScreen == null && !m().thePlayer.capabilities.isCreativeMode) {
         float var2 = var1.threshold.b() / 100.0F;
         float var3 = m().thePlayer.getHealth() / m().thePlayer.getMaxHealth();
         if (!(var2 <= 0.0F) && !(var3 <= 0.0F) && !(var3 >= var2)) {
            float var4 = (1.0F - var3 / var2) * var1.intensity.b();
            int var5 = var1.color.a(0.0F);
            cn.kazer.client189.render.GlStateBridge.c(1048575);

            try {
               m().getTextureManager().bindTexture(VIGNETTE);
               GlStateManager.enableBlend();
               GlStateManager.disableDepth();
               GlStateManager.depthMask(false);
               GlStateManager.tryBlendFuncSeparate(0, 769, 0, 769);
               GlStateManager.color(
                  1.0F - (var5 >> 16 & 0xFF) / 255.0F * var4, 1.0F - (var5 >> 8 & 0xFF) / 255.0F * var4, 1.0F - (var5 & 0xFF) / 255.0F * var4, 1.0F
               );
               WorldRenderer var6 = Tessellator.getInstance().getWorldRenderer();
               int var7 = var0.getScaledWidth();
               int var8 = var0.getScaledHeight();
               var6.begin(7, DefaultVertexFormats.POSITION_TEX);
               var6.pos(0.0, var8, 0.0).tex(0.0, 1.0).endVertex();
               var6.pos(var7, var8, 0.0).tex(1.0, 1.0).endVertex();
               var6.pos(var7, 0.0, 0.0).tex(1.0, 0.0).endVertex();
               var6.pos(0.0, 0.0, 0.0).tex(0.0, 0.0).endVertex();
               Tessellator.getInstance().draw();
            } finally {
               cn.kazer.client189.render.GlStateBridge.a();
            }
         }
      }
   }
}
