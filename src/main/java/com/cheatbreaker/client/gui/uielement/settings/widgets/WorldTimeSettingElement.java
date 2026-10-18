package com.cheatbreaker.client.gui.uielement.settings.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public class WorldTimeSettingElement extends com.cheatbreaker.client.gui.uielement.UIElement {
   private com.cheatbreaker.client.settings.Setting setting;
   private float time = -1.0F;
   private float lIIIIllIIlIlIllIIIlIllIlI;
   private boolean IlllIllIlIIIIlIIlIIllIIIl = false;
   private ResourceLocation sunIcon = new ResourceLocation("client/icons/sun-64.png");
   private ResourceLocation moonIcon = new ResourceLocation("client/icons/moon-64.png");

   public WorldTimeSettingElement(com.cheatbreaker.client.settings.Setting var1, float var2) {
      super(var2);
      this.setting = var1;
      this.height = 22;
      this.time = Float.parseFloat("" + var1.d());
   }

   @Override
   public void a(int var1, int var2, int var3) {
      short var5 = 170;
      boolean var4 = var1 > (this.x + 170) * this.scale
         && var1 < (this.x + 170 + var5 - 2) * this.scale
         && var2 > (this.y + 4 + this.yOffset) * this.scale
         && var2 < (this.y + 20 + this.yOffset) * this.scale;
      if (var3 == 0 && var4) {
         this.IlllIllIlIIIIlIIlIIllIIIl = true;
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      short var6 = 148;
      com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.a(this.setting.f().toUpperCase(), (float)(this.x + 8), (float)(this.y + 8), -1895825408);
      if (this.IlllIllIlIIIIlIIlIIllIIIl && !Mouse.isButtonDown(0)) {
         this.IlllIllIlIIIIlIIlIIllIIIl = false;
      }

      float var10002 = this.x + 172 + var6 / 2.0F;
      float var10003 = this.y - 2;
      com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.a("SERVER", var10002, var10003, -1895825408);
      cn.kazer.client189.render.DrawRect.a(this.x + 172 + var6 / 2 - 0.5F, this.y + 8, this.x + 172 + var6 / 2 + 0.5F, this.y + 14, 1862270976);
      com.cheatbreaker.client.gui.render.RenderUtils.a(this.moonIcon, this.x + 180 - 3.25F, this.y + 3, 7.5F, 7.5F);
      cn.kazer.client189.render.DrawRect.a(this.x + 180 - 0.5F, this.y + 12, this.x + 180 + 0.5F, this.y + 14, 1862270976);
      com.cheatbreaker.client.gui.render.RenderUtils.a(this.sunIcon, this.x + 170 + var6 - 10 - 5.0F, this.y + 2, 10.0F, 10.0F);
      cn.kazer.client189.render.DrawRect.a(this.x + 170 + var6 - 10 - 0.5F, this.y + 12, this.x + 170 + var6 - 10 + 0.5F, this.y + 14, 1862270976);
      boolean var7 = var1 > (this.x + 170) * this.scale
         && var1 < (this.x + 170 + var6 - 2) * this.scale
         && var2 > (this.y + 4 + this.yOffset) * this.scale
         && var2 < (this.y + 20 + this.yOffset) * this.scale;
      com.cheatbreaker.client.gui.render.RenderUtils.a(
         (double)(this.x + 174), (double)(this.y + 16), (double)(this.x + 170 + var6 - 4), (double)(this.y + 18), 1.0, var7 ? -1895825408 : 1862270976
      );
      double var8 = var6 - 18;
      float var10 = Float.parseFloat("" + this.setting.b());
      float var11 = Float.parseFloat("" + this.setting.c());
      if (this.IlllIllIlIIIIlIIlIIllIIIl) {
         this.lIIIIllIIlIlIllIIIlIllIlI = (float)Math.round((var10 + (var1 - (this.x + 180) * this.scale) * ((var11 - var10) / (var8 * this.scale))) * 100.0)
            / 100.0F;
         if (this.lIIIIllIIlIlIllIIIlIllIlI < -13490.0F && this.lIIIIllIIlIlIllIIIlIllIlI > -15490.0F) {
            this.lIIIIllIIlIlIllIIIlIllIlI = -14490.0F;
         }

         if (this.setting.l().equals(com.cheatbreaker.client.settings.Setting.SettingType.INTEGER)) {
            this.lIIIIllIIlIlIllIIIlIllIlI = Math.round(this.lIIIIllIIlIlIllIIIlIllIlI);
         }

         if (this.lIIIIllIIlIlIllIIIlIllIlI < var10) {
            this.lIIIIllIIlIlIllIIIlIllIlI = var10;
         } else if (this.lIIIIllIIlIlIllIIIlIllIlI > var11) {
            this.lIIIIllIIlIlIllIIIlIllIlI = var11;
         }

         Minecraft.getMinecraft().theWorld.setWorldTime(((Integer)com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.worldTime.d()).intValue());
         switch (this.setting.l()) {
            case INTEGER:
               this.setting.a(Integer.parseInt((int)this.lIIIIllIIlIlIllIIIlIllIlI + ""));
               break;
            case FLOAT:
               this.setting.a(Float.valueOf(this.lIIIIllIIlIlIllIIIlIllIlI));
               break;
            case DOUBLE:
               this.setting.a(Double.parseDouble(this.lIIIIllIIlIlIllIIIlIllIlI + ""));
         }
      }

      float var5;
      float var15;
      var5 = (var5 = Float.parseFloat(this.setting.d() + "")) < this.time ? this.time - var5 : (var15 = var5 - this.time);
      float var12 = ((var11 - var10) / 20.0F + var5 * 8.0F) / (Minecraft.getDebugFPS() + 1);
      if (var12 < 1.0E-4) {
         var12 = 1.0E-4F;
      }

      float var4;
      if (this.time < (var4 = Float.parseFloat(this.setting.d() + ""))) {
         this.time = this.time + var12 <= var4 ? (this.time += var12) : var4;
      } else if (this.time > var4) {
         this.time = this.time - var12 >= var4 ? (this.time -= var12) : var4;
      }

      double var13 = 100.0F * ((this.time - var10) / (var11 - var10));
      com.cheatbreaker.client.gui.render.RenderUtils.a((double)(this.x + 174), (double)(this.y + 16), this.x + 180 + var8 * var13 / 100.0, (double)(this.y + 18), 4.0, -12418828);
      cn.kazer.client189.render.GlStateBridge.a(0.25F, 0.45F, 1.0F, 1.0F);
      com.cheatbreaker.client.gui.render.RenderUtils.a(this.x + 181.25F + var8 * var13 / 100.0, this.y + 17.25F, 4.5);
      if (this.time == -14490.0F) {
         cn.kazer.client189.render.GlStateBridge.a(0.25F, 0.45F, 1.0F, 1.0F);
      } else {
         cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
      }

      com.cheatbreaker.client.gui.render.RenderUtils.a(this.x + 181.25F + var8 * var13 / 100.0, this.y + 17.25F, 2.7F);
   }
}
