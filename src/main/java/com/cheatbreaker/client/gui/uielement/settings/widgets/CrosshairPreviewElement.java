package com.cheatbreaker.client.gui.uielement.settings.widgets;

import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class CrosshairPreviewElement extends com.cheatbreaker.client.gui.uielement.UIElement {
   public CrosshairPreviewElement(float var1) {
      super(var1);
      this.height = 50;
   }

   @Override
   public void a(int var1, int var2, float var3) {
      cn.kazer.client189.render.DrawRect.a(this.x + (this.width / 2 - 15) - 41, this.y + 4, this.x + (this.width / 2 - 15) + 41, this.y + 51, -16777216);
      cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
      com.cheatbreaker.client.gui.render.RenderUtils.a(new ResourceLocation("client/defaults/crosshair.png"), this.x + (this.width / 2 - 15) - 40, this.y + 5, 80.0F, 45.0F);
      com.cheatbreaker.client.settings.GlobalSettings var4 = com.cheatbreaker.client.CheatBreakerClient.N().globalSettings;
      GL11.glPushMatrix();
      float var5 = 1.0F / com.cheatbreaker.client.CheatBreakerClient.N().e();
      GL11.glScalef(var5, var5, var5);
      float var6 = (Float)var4.crosshairSize.d();
      float var7 = (Float)var4.crosshairGap.d();
      float var8 = (Float)var4.crosshairThickness.d();
      int var9 = var4.crosshairColor.a();
      boolean var10 = (Boolean)var4.crosshairOutline.d();
      int var11 = this.x + this.width / 2 - 15;
      int var12 = this.y + this.height / 2 + 3;
      if (var10) {
         cn.kazer.client189.render.DrawRect.a(var11 - var7 - var6, var12 - var8 / 2.0F, var11 - var7, var12 + var8 / 2.0F, 0.5F, -1358954496, var9);
         cn.kazer.client189.render.DrawRect.a(var11 + var7, var12 - var8 / 2.0F, var11 + var7 + var6, var12 + var8 / 2.0F, 0.5F, -1358954496, var9);
         cn.kazer.client189.render.DrawRect.a(var11 - var8 / 2.0F, var12 - var7 - var6, var11 + var8 / 2.0F, var12 - var7, 0.5F, -1358954496, var9);
         cn.kazer.client189.render.DrawRect.a(var11 - var8 / 2.0F, var12 + var7, var11 + var8 / 2.0F, var12 + var7 + var6, 0.5F, -1358954496, var9);
      } else {
         cn.kazer.client189.render.DrawRect.a(var11 - var7 - var6, var12 - var8 / 2.0F, var11 - var7, var12 + var8 / 2.0F, var9);
         cn.kazer.client189.render.DrawRect.a(var11 + var7, var12 - var8 / 2.0F, var11 + var7 + var6, var12 + var8 / 2.0F, var9);
         cn.kazer.client189.render.DrawRect.a(var11 - var8 / 2.0F, var12 - var7 - var6, var11 + var8 / 2.0F, var12 - var7, var9);
         cn.kazer.client189.render.DrawRect.a(var11 - var8 / 2.0F, var12 + var7, var11 + var8 / 2.0F, var12 + var7 + var6, var9);
      }

      cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }

   @Override
   public void a(int var1, int var2, int var3) {
   }
}
