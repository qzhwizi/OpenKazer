package com.cheatbreaker.client.module.modules;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class DirectionHudModule extends com.cheatbreaker.client.module.Module {
   private final com.cheatbreaker.client.settings.Setting markerColor;
   private final com.cheatbreaker.client.settings.Setting directionColor;
   private final com.cheatbreaker.client.settings.Setting showWhileTyping;
   private final ResourceLocation texture = new ResourceLocation("textures/gui/compass.png");

   public DirectionHudModule() {
      super("Direction HUD");
      this.b(com.cheatbreaker.client.gui.hudeditor.GuiAnchor.MIDDLE_TOP);
      this.b(0.0F, 0.0F);
      this.c(true);
      this.showWhileTyping = new com.cheatbreaker.client.settings.Setting(this, "Show While Typing").a(true);
      this.markerColor = new com.cheatbreaker.client.settings.Setting(this, "Marker Color").a(-43691).a(Integer.MIN_VALUE, Integer.MAX_VALUE);
      this.directionColor = new com.cheatbreaker.client.settings.Setting(this, "Direction Color").a(-1).a(Integer.MIN_VALUE, Integer.MAX_VALUE);
      this.a(new ResourceLocation("client/icons/mods/dirhud.png"), 65, 12);
      this.a(com.cheatbreaker.client.event.event.RenderHudEvent.class, this::a);
   }

   private void a(com.cheatbreaker.client.event.event.RenderHudEvent var1) {
      if (this.q()) {
         int var2 = -14606047;
         GL11.glPushMatrix();
         cn.kazer.client189.render.GlStateBridge.a(3042);
         float[] var3 = a();
         this.c(var3[0], var3[1]);
         this.a(var1.a());
         if (!(this.minecraft.currentScreen instanceof GuiChat) || (Boolean)this.showWhileTyping.d()) {
            cn.kazer.client189.render.GlStateBridge.a((var2 >> 16 & 0xFF) / 255.0F, (var2 >> 8 & 0xFF) / 255.0F, (var2 & 0xFF) / 255.0F, (var2 >> 24 & 0xFF) / 255.0F);
            this.b(var1.a());
            cn.kazer.client189.render.GlStateBridge.a((var2 >> 16 & 0xFF) / 255.0F, (var2 >> 8 & 0xFF) / 255.0F, (var2 & 0xFF) / 255.0F, (var2 >> 24 & 0xFF) / 255.0F);
         }

         cn.kazer.client189.render.GlStateBridge.b(3042);
         GL11.glPopMatrix();
      }
   }

   static float[] a() {
      return new float[]{65.0F, 12.0F};
   }

   private void b(ScaledResolution var1) {
      int var2 = MathHelper.floor_double(this.minecraft.thePlayer.rotationYaw * 256.0F / 360.0F + 0.5) & 0xFF;
      byte var3 = 0;
      byte var4 = 0;
      int var5 = -14606047;
      if ((Integer)this.directionColor.d() != 4095) {
         int var6 = this.directionColor.a();
         this.minecraft.getTextureManager().bindTexture(this.texture);
         if (var2 < 128) {
            cn.kazer.client189.render.GlStateBridge.a((var5 >> 16 & 0xFF) / 255.0F, (var5 >> 8 & 0xFF) / 255.0F, (var5 & 0xFF) / 255.0F, (var5 >> 24 & 0xFF) / 255.0F);
            com.cheatbreaker.client.gui.render.ItemRenderUtils.a(var4, var3, var2, 0, 65, 12, -100.0F);
         } else {
            com.cheatbreaker.client.gui.render.ItemRenderUtils.a(var4, var3, var2 - 128, 12, 65, 12, -100.0F);
         }

         cn.kazer.client189.render.GlStateBridge.a((var6 >> 16 & 0xFF) / 255.0F, (var6 >> 8 & 0xFF) / 255.0F, (var6 & 0xFF) / 255.0F, 1.0F);
         if (var2 < 128) {
            com.cheatbreaker.client.gui.render.ItemRenderUtils.a(var4, var3, var2, 24, 65, 12, -100.0F);
         } else {
            com.cheatbreaker.client.gui.render.ItemRenderUtils.a(var4, var3, var2 - 128, 36, 65, 12, -100.0F);
         }
      } else {
         this.minecraft.getTextureManager().bindTexture(this.texture);
         if (var2 < 128) {
            com.cheatbreaker.client.gui.render.ItemRenderUtils.a(var4, var3, var2, 0, 65, 12, -100.0F);
         } else {
            com.cheatbreaker.client.gui.render.ItemRenderUtils.a(var4, var3, var2 - 128, 12, 65, 12, -100.0F);
         }
      }

      this.minecraft.fontRendererObj.drawString("|", var4 + 32, var3 + 1, this.markerColor.a());
      this.minecraft.fontRendererObj.drawString("|§r", var4 + 32, var3 + 5, this.markerColor.a());
   }
}
