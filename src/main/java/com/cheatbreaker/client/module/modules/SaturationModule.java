package com.cheatbreaker.client.module.modules;

import net.minecraft.util.FoodStats;
import org.lwjgl.opengl.GL11;

public final class SaturationModule extends com.cheatbreaker.client.module.Module {
   private static final float SAMPLE_SATURATION = 5.76F;
   private final com.cheatbreaker.client.settings.Setting showDecimals;
   private final com.cheatbreaker.client.settings.Setting showBackground;
   private final com.cheatbreaker.client.settings.Setting textColor;
   private final com.cheatbreaker.client.settings.Setting backgroundColor;

   public SaturationModule() {
super("Saturation");
      this.b(com.cheatbreaker.client.gui.hudeditor.GuiAnchor.RIGHT_BOTTOM);
      this.b(-240.70001F, -13.600006F);
      this.c(true);
      this.scale.b(0.6F);
      this.showDecimals = new com.cheatbreaker.client.settings.Setting(this, "Show Decimals").a(false);
      this.showBackground = new com.cheatbreaker.client.settings.Setting(this, "Show Background").a(false);
      this.textColor = new com.cheatbreaker.client.settings.Setting(this, "Text Color").a(-11141121).a(Integer.MIN_VALUE, Integer.MAX_VALUE);
      this.backgroundColor = new com.cheatbreaker.client.settings.Setting(this, "Background Color").a(1862270976).a(Integer.MIN_VALUE, Integer.MAX_VALUE);
      this.a("6", 2.0F);
      this.a(com.cheatbreaker.client.event.event.RenderHudEvent.class, this::a);
      this.a(com.cheatbreaker.client.event.event.RenderHudEditorEvent.class, this::a);
   }

   private void a(com.cheatbreaker.client.event.event.RenderHudEvent var1) {
      if (this.q()
         && this.minecraft.thePlayer != null
         && !(this.minecraft.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.HudEditorScreen)
         && !(this.minecraft.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.ModulePlacementScreen)) {
         this.a(var1, com.cheatbreaker.client.module.modules.SaturationFormatter.a(this.minecraft.thePlayer.getFoodStats(), (Boolean)this.showDecimals.d()));
      }
   }

   private void a(com.cheatbreaker.client.event.event.RenderHudEditorEvent var1) {
      if (this.q()) {
         boolean var2 = this.minecraft.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.ModulePlacementScreen;
         FoodStats var3;
         if (var2) {
            var3 = new FoodStats();
            var3.setFoodSaturationLevel(5.76F);
         } else {
            if (!(this.minecraft.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.HudEditorScreen) || this.minecraft.thePlayer == null) {
               return;
            }

            var3 = this.minecraft.thePlayer.getFoodStats();
         }

         this.a(var1, com.cheatbreaker.client.module.modules.SaturationFormatter.a(var3, (Boolean)this.showDecimals.d()));
      }
   }

   float[] a(int var1, int var2) {
      boolean var3 = (Boolean)this.showBackground.d();
      return new float[]{var1 + (var3 ? 4.0F : 0.0F), var3 ? var2 + 4.0F : var2};
   }

   int a() {
return (Boolean) this.showBackground.d() ? 3 : 0;
   }

   float b(int var1, int var2) {
      return this.a(var1, var2)[0];
   }

   @Override
   protected float p() {
      String var1 = com.cheatbreaker.client.module.modules.SaturationFormatter.a((Boolean)this.showDecimals.d());
      int var2 = this.minecraft.fontRendererObj.getStringWidth(var1);
      return this.b(var2, this.minecraft.fontRendererObj.FONT_HEIGHT);
   }

   private void a(com.cheatbreaker.client.event.event.RenderHudEvent var1, String var2) {
      if (var2 != null) {
         boolean var3 = (Boolean)this.showBackground.d();
         int var4 = this.minecraft.fontRendererObj.getStringWidth(var2);
         float[] var5 = this.a(var4, this.minecraft.fontRendererObj.FONT_HEIGHT);
         float var6 = var5[0];
         float var7 = var5[1];
         this.c(var6, var7);
         float var8 = var3 ? 2.0F : 0.0F;
         int var9 = this.a();
         boolean var10 = GL11.glIsEnabled(2929);
         GL11.glPushMatrix();
         cn.kazer.client189.render.GlStateBridge.b(2929);
         this.a(var1.a());
         if (var3) {
            cn.kazer.client189.render.DrawRect.a(0.0F, 0.0F, var6, var7, this.backgroundColor.a());
         }

         this.minecraft.fontRendererObj.drawStringWithShadow(var2, (int)var8, var9, this.textColor.a());
         GL11.glPopMatrix();
         if (var10) {
            cn.kazer.client189.render.GlStateBridge.a(2929);
         }
      }
   }
}
