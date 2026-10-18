package com.cheatbreaker.client.gui.uielement.settings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

public class StringArraySettingElement extends com.cheatbreaker.client.gui.uielement.UIElement {
   private final com.cheatbreaker.client.settings.Setting setting;
   private final ResourceLocation leftIcon = new ResourceLocation("client/icons/left.png");
   private final ResourceLocation rightIcon = new ResourceLocation("client/icons/right.png");
   private int IlllIllIlIIIIlIIlIIllIIIl = 0;
   private float IlIlllIIIIllIllllIllIIlIl = 0.0F;
   private String llIIlllIIIIlllIllIlIlllIl;

   public StringArraySettingElement(com.cheatbreaker.client.settings.Setting var1, float var2) {
      super(var2);
      this.setting = var1;
      this.height = 12;
   }

   @Override
   public void a(int var1, int var2, float var3) {
      boolean var4 = var1 > (this.x + this.width - 92) * this.scale
         && var1 < (this.x + this.width - 48) * this.scale
         && var2 > (this.y + this.yOffset) * this.scale
         && var2 < (this.y + 14 + this.yOffset) * this.scale;
      boolean var5 = var1 > (this.x + this.width - 48) * this.scale
         && var1 < (this.x + this.width - 10) * this.scale
         && var2 > (this.y + this.yOffset) * this.scale
         && var2 < (this.y + 14 + this.yOffset) * this.scale;
      com.cheatbreaker.client.CheatBreakerClient.N()
         .ubuntuMedium16px
         .a(this.setting.g().toUpperCase(), (float)(this.x + 10), (float)(this.y + 4), !var4 && !var5 ? -1895825408 : -1090519040);
      boolean var6 = this.setting.f().toLowerCase().endsWith("color");
      String var7 = this.setting.d().toString();
      String[] var8;
      if ((var8 = var7.split(" ")).length > 1) {
         var7 = var8[1].substring(0, Math.min(var8[1].length(), 5)).trim() + "...";
      }

      if (!var6) {
         if (this.IlllIllIlIIIIlIIlIIllIIIl == 0) {
            float var10002 = this.x + this.width - 48;
            float var10003 = this.y + 4;
            com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.b(var7, var10002, var10003, -1895825408);
         } else {
            boolean var9 = this.IlllIllIlIIIIlIIlIIllIIIl == 1;
            float var15 = this.x + this.width - 48 - (var9 ? -this.IlIlllIIIIllIllllIllIIlIl : this.IlIlllIIIIllIllllIllIIlIl);
            float var22 = this.y + 4;
            com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.b(var7, var15, var22, -1895825408);
            if (var9) {
               var15 = this.x + this.width - 98 + this.IlIlllIIIIllIllllIllIIlIl;
               var22 = this.y + 4;
               com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.b(var7, var15, var22, -1895825408);
            } else {
               var15 = this.x + this.width + 2 - this.IlIlllIIIIllIllllIllIIlIl;
               var22 = this.y + 4;
               com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.b(var7, var15, var22, -1895825408);
            }

            if (this.IlIlllIIIIllIllllIllIIlIl >= 50.0F) {
               this.IlllIllIlIIIIlIIlIIllIIIl = 0;
               this.IlIlllIIIIllIllllIllIIlIl = 0.0F;
            } else {
               float var10 = com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.a(50.0F + this.IlIlllIIIIllIllllIllIIlIl * 15.0F);
               this.IlIlllIIIIllIllllIllIIlIl = this.IlIlllIIIIllIllllIllIIlIl + var10 >= 50.0F ? 50.0F : (this.IlIlllIIIIllIllllIllIIlIl += var10);
            }

            cn.kazer.client189.render.DrawRect.a(this.x + this.width - 130, this.y + 2, this.x + this.width - 72, this.y + 12, -723724);
            cn.kazer.client189.render.DrawRect.a(this.x + this.width - 22, this.y + 2, this.x + this.width + 4, this.y + 12, -723724);
         }
      } else if (this.IlllIllIlIIIIlIIlIIllIIIl == 0) {
         float var12 = com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.a(var7);
         float var18 = this.x + this.width - 47.5F - var12 / 2.0F;
         com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.b(var7, var18, this.y + 4.0F, -16777216);
         com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.b("§" + var7 + var7, this.x + this.width - 48 - var12 / 2.0F, (float)(this.y + 4), -16777216);
      } else {
         boolean var13 = this.IlllIllIlIIIIlIIlIIllIIIl == 1;
         float var19 = this.x + this.width - 48 - (var13 ? -this.IlIlllIIIIllIllllIllIIlIl : this.IlIlllIIIIllIllllIllIIlIl);
         float var25 = this.y + 4;
         com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.b(this.llIIlllIIIIlllIllIlIlllIl, var19, var25, -1895825408);
         float var14 = com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.a(var7);
         if (var13) {
            var19 = this.x + this.width - 97.5F - var14 / 2.0F + this.IlIlllIIIIllIllllIllIIlIl;
            com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.b(var7, var19, this.y + 4.0F, -16777216);
            com.cheatbreaker.client.CheatBreakerClient.N()
               .ubuntuMedium16px
               .b("§" + var7 + var7, this.x + this.width - 98 - var14 / 2.0F + this.IlIlllIIIIllIllllIllIIlIl, (float)(this.y + 4), -16777216);
         } else {
            var19 = this.x + this.width - 1.5F - var14 / 2.0F - this.IlIlllIIIIllIllllIllIIlIl;
            com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.b(var7, var19, this.y + 4.0F, -16777216);
            com.cheatbreaker.client.CheatBreakerClient.N()
               .ubuntuMedium16px
               .b("§" + var7 + var7, this.x + this.width - 2 - var14 / 2.0F - this.IlIlllIIIIllIllllIllIIlIl, (float)(this.y + 4), -16777216);
         }

         if (this.IlIlllIIIIllIllllIllIIlIl >= 50.0F) {
            this.IlllIllIlIIIIlIIlIIllIIIl = 0;
            this.IlIlllIIIIllIllllIllIIlIl = 0.0F;
         } else {
            float var11 = com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.a(50.0F + this.IlIlllIIIIllIllllIllIIlIl * 15.0F);
            this.IlIlllIIIIllIllllIllIIlIl = this.IlIlllIIIIllIllllIllIIlIl + var11 >= 50.0F ? 50.0F : (this.IlIlllIIIIllIllllIllIIlIl += var11);
         }

         cn.kazer.client189.render.DrawRect.a(this.x + this.width - 130, this.y + 2, this.x + this.width - 72, this.y + 12, -723724);
         cn.kazer.client189.render.DrawRect.a(this.x + this.width - 22, this.y + 2, this.x + this.width + 4, this.y + 12, -723724);
      }

      cn.kazer.client189.render.GlStateBridge.a(0.0F, 0.0F, 0.0F, var4 ? 0.8F : 0.45F);
      com.cheatbreaker.client.gui.render.RenderUtils.a(this.leftIcon, 4.0F, this.x + this.width - 82, this.y + 4);
      cn.kazer.client189.render.GlStateBridge.a(0.0F, 0.0F, 0.0F, var5 ? 0.8F : 0.45F);
      com.cheatbreaker.client.gui.render.RenderUtils.a(this.rightIcon, 4.0F, this.x + this.width - 22, this.y + 4);
   }

   @Override
   public void a(int var1, int var2, int var3) {
      boolean var4 = var1 > (this.x + this.width - 92) * this.scale
         && var1 < (this.x + this.width - 48) * this.scale
         && var2 > (this.y + this.yOffset) * this.scale
         && var2 < (this.y + 14 + this.yOffset) * this.scale;
      boolean var5 = var1 > (this.x + this.width - 48) * this.scale
         && var1 < (this.x + this.width - 10) * this.scale
         && var2 > (this.y + this.yOffset) * this.scale
         && var2 < (this.y + 14 + this.yOffset) * this.scale;
      if ((var4 || var5) && this.IlllIllIlIIIIlIIlIIllIIIl == 0) {
         Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));

         for (int var6 = 0; var6 < this.setting.h().length; var6++) {
            if (this.setting.h()[var6].toLowerCase().equalsIgnoreCase((String)this.setting.d())) {
               this.llIIlllIIIIlllIllIlIlllIl = (String)this.setting.d();
               if (var5) {
                  if (var6 + 1 >= this.setting.h().length) {
                     this.IlllIllIlIIIIlIIlIIllIIIl = 2;
                     this.setting.a(this.setting.h()[0]);
                  } else {
                     this.IlllIllIlIIIIlIIlIIllIIIl = 2;
                     this.setting.a(this.setting.h()[var6 + 1]);
                  }
               } else if (var6 - 1 < 0) {
                  this.IlllIllIlIIIIlIIlIIllIIIl = 1;
                  this.setting.a(this.setting.h()[this.setting.h().length - 1]);
               } else {
                  this.IlllIllIlIIIIlIIlIIllIIIl = 1;
                  this.setting.a(this.setting.h()[var6 - 1]);
               }
               break;
            }
         }

         if (this.setting == com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.clearGlass) {
            Minecraft.getMinecraft().renderGlobal.loadRenderers();
         }
      }
   }

   @Override
   public com.cheatbreaker.client.settings.Setting b() {
      return this.setting;
   }
}
