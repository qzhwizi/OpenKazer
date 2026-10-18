package com.cheatbreaker.client.gui.uielement.settings;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class ColorPickerSettingElement extends com.cheatbreaker.client.gui.uielement.UIElement {
   private com.cheatbreaker.client.settings.Setting setting;
   private List<com.cheatbreaker.client.gui.uielement.settings.ColorSwatchElement> colors;
   private boolean lIIIIllIIlIlIllIIIlIllIlI = false;
   private boolean IlllIllIlIIIIlIIlIIllIIIl = false;
   private boolean IlIlllIIIIllIllllIllIIlIl = false;
   private boolean llIIlllIIIIlllIllIlIlllIl = false;
   private com.cheatbreaker.client.gui.uielement.settings.ColorSwatchElement colorPickerColorElement;
   private float IIIlllIIIllIllIlIIIIIIlII = 1.0F;
   private float llIlIIIlIIIIlIlllIlIIIIll = 1.0F;
   private float pickerX;
   private float pickerY;
   private float lIIIIIllllIIIIlIlIIIIlIlI;
   private float IIIIIIlIlIlIllllllIlllIlI;
   private float pickerWidth;
   private float pickerHeight;
   private int lIIlIIllIIIIIlIllIIIIllII;
   private int lIIlllIIlIlllllllllIIIIIl;
   private int lIllIllIlIIllIllIlIlIIlIl;
   private int llIlIIIllIIIIlllIlIIIIIlI;

   public ColorPickerSettingElement(com.cheatbreaker.client.settings.Setting var1, float var2) {
      super(var2);
      this.setting = var1;
      this.colorPickerColorElement = new com.cheatbreaker.client.gui.uielement.settings.ColorSwatchElement(var2, (Integer)var1.d());
      this.colors = new ArrayList<>();

      for (int var3 = 0; var3 < 16; var3++) {
         int var4 = cn.kazer.client189.render.ClientBridge.a(var3);
         this.colors.add(new com.cheatbreaker.client.gui.uielement.settings.ColorSwatchElement(var2, var4));
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.height = this.lIIIIllIIlIlIllIIIlIllIlI ? 130 : 18;
      this.pickerX = this.x + 56;
      this.lIIIIIllllIIIIlIlIIIIlIlI = this.x + 176;
      this.pickerY = this.y + 25;
      this.IIIIIIlIlIlIllllllIlllIlI = this.y + 119;
      this.pickerWidth = this.lIIIIIllllIIIIlIlIIIIlIlI - this.pickerX;
      this.pickerHeight = this.IIIIIIlIlIlIllllllIlllIlI - this.pickerY;
      com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.a(this.setting.g().toUpperCase(), (float)(this.x + 10), (float)(this.y + 4), -1895825408);
      this.colorPickerColorElement.color = this.setting.a();
      this.colorPickerColorElement.a(this.x + 160, this.y + 3, 14, 14);
      this.colorPickerColorElement.yOffset = this.yOffset;
      this.colorPickerColorElement.a(var1, var2, var3);
      cn.kazer.client189.render.DrawRect.a(this.x + 186, this.y + 16, this.x + this.width - 16, this.y + 17, 2130706432);
      float var10002 = this.x + 188;
      float var10003 = this.y + 4;
      com.cheatbreaker.client.CheatBreakerClient.N().playBold18px.a("#", var10002, var10003, -1358954496);
      com.cheatbreaker.client.CheatBreakerClient.N().playBold18px.a(Integer.toHexString(this.setting.a()), (float)(this.x + 194), (float)(this.y + 4), -1358954496);
      boolean var5 = var1 > (this.x + this.width - 40) * this.scale
         && var1 < (this.x + this.width - 12) * this.scale
         && var2 > (this.y + this.yOffset) * this.scale
         && var2 < (this.y + 18 + this.yOffset) * this.scale;
      String var4 = var5 ? "(Favorite)" : "(+)";
      if (com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.a((Integer)this.setting.d())) {
         var4 = var5 ? "(Un-favorite)" : "(-)";
      }

      com.cheatbreaker.client.CheatBreakerClient.N()
         .playBold18px
         .a(var4, (float)(this.x + this.width - 16 - com.cheatbreaker.client.CheatBreakerClient.N().playBold18px.a(var4)), (float)(this.y + 4), var5 ? -822083584 : -1358954496);
      if (this.lIIIIllIIlIlIllIIIlIllIlI) {
         if (this.IlllIllIlIIIIlIIlIIllIIIl && !Mouse.isButtonDown(0)) {
            this.IlllIllIlIIIIlIIlIIllIIIl = false;
            this.d();
         }

         if (this.IlIlllIIIIllIllllIllIIlIl && !Mouse.isButtonDown(0)) {
            this.IlIlllIIIIllIllllIllIIlIl = false;
            this.d();
         }

         if (this.llIIlllIIIIlllIllIlIlllIl && !Mouse.isButtonDown(0)) {
            this.llIIlllIIIIlllIllIlIlllIl = false;
            this.d();
         }

         cn.kazer.client189.render.DrawRect.a(this.x + 55, this.y + 24, this.x + 177, this.y + 120, -822083584);
         cn.kazer.client189.render.ImmediateTessellator var6 = cn.kazer.client189.render.ImmediateTessellator.instance;
         cn.kazer.client189.render.GlStateBridge.b(3553);
         var6.a();
         cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
         var6.a((double)this.pickerX, (double)this.IIIIIIlIlIlIllllllIlllIlI, 0.0);
         var6.a((double)this.lIIIIIllllIIIIlIlIIIIlIlI, (double)this.IIIIIIlIlIlIllllllIlllIlI, 0.0);
         var6.a((double)this.lIIIIIllllIIIIlIlIIIIlIlI, (double)this.pickerY, 0.0);
         var6.a((double)this.pickerX, (double)this.pickerY, 0.0);
         var6.b();
         int[] var7 = null;

         for (int var8 = 0; var8 < this.pickerWidth; var8++) {
            for (int var9 = 0; var9 < this.pickerHeight; var9++) {
               float var11 = var8 / this.pickerWidth;
               float var12 = 1.0F - var9 / this.pickerHeight;
               int var13 = (int)this.llIlIIIlIIIIlIlllIlIIIIll << 24 | Color.HSBtoRGB(this.IIIlllIIIllIllIlIIIIIIlII, var11, var12);
               boolean var14 = var1 >= (this.pickerX + var8) * this.scale && var1 <= (this.pickerX + var8 + 1.0F) * this.scale;
               boolean var15 = var2 <= (this.pickerY + var9 + 1.0F + this.yOffset) * this.scale && var2 > (this.pickerY + var9 + this.yOffset) * this.scale;
               boolean var16 = var14 && var15;
               boolean var17 = var8 == 0 && var1 < this.pickerX * this.scale && var15;
               boolean var18 = var9 == 0 && var2 < (this.pickerY + this.yOffset) * this.scale && var14;
               boolean var19 = var8 == this.pickerWidth - 1.0F && var1 > (this.pickerX + this.pickerWidth) * this.scale && var15;
               boolean var10 = var9 == this.pickerHeight - 1.0F && var2 > (this.pickerY + this.pickerHeight + this.yOffset) * this.scale && var14;
               if (this.IlllIllIlIIIIlIIlIIllIIIl && (var16 || var17 || var18 || var19 || var10)) {
                  this.setting.a(var13);
                  this.setting.colorArray = new int[]{var8, var9};
               }

               if (this.setting.colorArray != null) {
                  var7 = this.setting.colorArray;
               } else if (var13 == (Integer)this.setting.d()) {
                  var7 = new int[]{var8, var9};
               }

               var6.a();
               cn.kazer.client189.render.GlStateBridge.a((var13 >> 16 & 0xFF) / 255.0F, (var13 >> 8 & 0xFF) / 255.0F, (var13 & 0xFF) / 255.0F, 1.0F);
               var6.a((double)(this.pickerX + var8), (double)(this.pickerY + var9 + 1.0F), 0.0);
               var6.a((double)(this.pickerX + var8 + 1.0F), (double)(this.pickerY + var9 + 1.0F), 0.0);
               var6.a((double)(this.pickerX + var8 + 1.0F), (double)(this.pickerY + var9), 0.0);
               var6.a((double)(this.pickerX + var8), (double)(this.pickerY + var9), 0.0);
               var6.b();
            }
         }

         if (var7 != null) {
            GL11.glPushMatrix();
            cn.kazer.client189.render.GlStateBridge.a(0.0F, 0.0F, 0.0F, 0.75F);
            com.cheatbreaker.client.gui.render.RenderUtils.a(this.pickerX + var7[0] + 1.115F, this.pickerY + var7[1] + 1.115F, 4.0);
            cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
            com.cheatbreaker.client.gui.render.RenderUtils.a(this.pickerX + var7[0] + 1.115F, this.pickerY + var7[1] + 1.115F, 2.7F);
            GL11.glPopMatrix();
         }

         cn.kazer.client189.render.DrawRect.a(this.pickerX - 51.0F, this.pickerY + 1.0F, this.pickerX - 43.0F, this.pickerY + 9.0F, -16777216);
         cn.kazer.client189.render.DrawRect.a(this.pickerX - 50.0F, this.pickerY + 2.0F, this.pickerX - 44.0F, this.pickerY + 8.0F, this.setting.rainbow ? -13369549 : -1);
         var10002 = this.pickerX - 40.0F;
         com.cheatbreaker.client.CheatBreakerClient.N().playRegular16px.a("CHROMA", var10002, this.pickerY, -1358954496);
         this.b(var1, var2);
         this.b(var2);
         this.lIIlIIllIIIIIlIllIIIIllII = (int)(this.pickerX + this.pickerWidth + 64.0F);
         this.lIIlllIIlIlllllllllIIIIIl = (int)this.pickerY;
         this.a(
            com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.IlIIlIIlIllIIIIllIIllIlIl,
            this.lIIlIIllIIIIIlIllIIIIllII,
            this.lIIlllIIlIlllllllllIIIIIl,
            var1,
            var2,
            (int)var3
         );
         this.lIllIllIlIIllIllIlIlIIlIl = (int)(this.pickerX + this.pickerWidth + 94.0F);
         this.llIlIIIllIIIIlllIlIIIIIlI = (int)this.pickerY;
         this.a(
            com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.favouriteColors, this.lIllIllIlIIllIllIlIlIIlIl, this.llIlIIIllIIIIlllIlIIIIIlI, var1, var2, (int)var3
         );
         this.a(this.colors, (int)(this.pickerX + this.pickerWidth + 34.0F), (int)this.pickerY, var1, var2, (int)var3);
      }
   }

   private void d() {
      if (com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.IlIIlIIlIllIIIIllIIllIlIl.size() >= 16) {
         com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.IlIIlIIlIllIIIIllIIllIlIl.remove(0);
      }

      com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.IlIIlIIlIllIIIIllIIllIlIl.add(new com.cheatbreaker.client.gui.uielement.settings.ColorSwatchElement(this.scale, (Integer)this.setting.d()));
      Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
   }

   private void b(int var1) {
      cn.kazer.client189.render.DrawRect.a(
         this.pickerX + this.pickerWidth + 18.0F,
         this.pickerY - 1.0F,
         this.pickerX + this.pickerWidth + 28.0F,
         this.pickerY + 1.0F + this.pickerHeight,
         -822083584
      );
      this.e();

      for (int var2 = 0; var2 < this.pickerHeight; var2++) {
         int var3 = (Integer)this.setting.d();
         int var4 = new Color(var3 >> 16 & 0xFF, var3 >> 8 & 0xFF, var3 & 0xFF, Math.round(255.0F - var2 / this.pickerHeight * 255.0F)).getRGB();
         if (this.llIIlllIIIIlllIllIlIlllIl
            && var1 >= (this.yOffset + this.pickerY + var2) * this.scale
            && var1 <= (this.yOffset + this.pickerY + var2 + 1.0F) * this.scale) {
            this.llIlIIIlIIIIlIlllIlIIIIll = var2 / this.pickerHeight;
            this.setting.a(var4);
         }

         cn.kazer.client189.render.DrawRect.a(
            this.pickerX + this.pickerWidth + 19.0F, this.pickerY + var2, this.pickerX + this.pickerWidth + 27.0F, this.pickerY + var2 + 1.0F, var4
         );
      }

      float var5 = -1.0F + this.pickerHeight * this.llIlIIIlIIIIlIlllIlIIIIll;
      cn.kazer.client189.render.DrawRect.a(
         this.pickerX + this.pickerWidth + 18.0F, this.pickerY + var5, this.pickerX + this.pickerWidth + 28.0F, this.pickerY + var5 + 3.0F, -822083584
      );
      cn.kazer.client189.render.DrawRect.a(
         this.pickerX + this.pickerWidth + 18.0F, this.pickerY + var5 + 1.0F, this.pickerX + this.pickerWidth + 28.0F, this.pickerY + var5 + 2.0F, -805306369
      );
   }

   private void b(int var1, int var2) {
      cn.kazer.client189.render.DrawRect.a(
         this.pickerX + this.pickerWidth + 4.0F,
         this.pickerY - 1.0F,
         this.pickerX + this.pickerWidth + 14.0F,
         this.pickerY + 1.0F + this.pickerHeight,
         -822083584
      );

      for (int var3 = 0; var3 < this.pickerHeight; var3++) {
         if (this.IlIlllIIIIllIllllIllIIlIl
            && var2 >= (this.yOffset + this.pickerY + var3) * this.scale
            && var2 <= (this.yOffset + this.pickerY + var3 + 1.0F) * this.scale) {
            int var4 = (Integer)this.setting.d();
            float[] var5 = Color.RGBtoHSB(var4 >> 16 & 0xFF, var4 >> 8 & 0xFF, var4 & 0xFF, null);
            this.setting.a(Color.HSBtoRGB(this.IIIlllIIIllIllIlIIIIIIlII, var5[1], var5[2]));
            this.IIIlllIIIllIllIlIIIIIIlII = var3 / this.pickerHeight;
         }

         int var6 = Color.HSBtoRGB(var3 / this.pickerHeight, 1.0F, 1.0F);
         cn.kazer.client189.render.DrawRect.a(
            this.pickerX + this.pickerWidth + 5.0F, this.pickerY + var3, this.pickerX + this.pickerWidth + 13.0F, this.pickerY + var3 + 1.0F, var6
         );
      }

      float var7 = -1.0F + this.pickerHeight * this.IIIlllIIIllIllIlIIIIIIlII;
      cn.kazer.client189.render.DrawRect.a(
         this.pickerX + this.pickerWidth + 4.0F, this.pickerY + var7, this.pickerX + this.pickerWidth + 14.0F, this.pickerY + var7 + 3.0F, -822083584
      );
      cn.kazer.client189.render.DrawRect.a(
         this.pickerX + this.pickerWidth + 4.0F, this.pickerY + var7 + 1.0F, this.pickerX + this.pickerWidth + 14.0F, this.pickerY + var7 + 2.0F, -805306369
      );
   }

   private void e() {
      boolean var1 = true;

      for (byte var2 = 2; var2 < this.pickerHeight - 4.0F; var2 += 4) {
         if (!var1) {
            cn.kazer.client189.render.DrawRect.a(
               this.pickerX + this.pickerWidth + 19.0F, this.pickerY + var2, this.pickerX + this.pickerWidth + 23.0F, this.pickerY + var2 + 4.0F, -1
            );
            cn.kazer.client189.render.DrawRect.a(
               this.pickerX + this.pickerWidth + 23.0F, this.pickerY + var2 + 4.0F, this.pickerX + this.pickerWidth + 27.0F, this.pickerY + var2 + 8.0F, -1
            );
            cn.kazer.client189.render.DrawRect.a(
               this.pickerX + this.pickerWidth + 23.0F, this.pickerY + var2, this.pickerX + this.pickerWidth + 27.0F, this.pickerY + var2 + 4.0F, -7303024
            );
            cn.kazer.client189.render.DrawRect.a(
               this.pickerX + this.pickerWidth + 19.0F,
               this.pickerY + var2 + 4.0F,
               this.pickerX + this.pickerWidth + 23.0F,
               this.pickerY + var2 + 8.0F,
               -7303024
            );
         }

         var1 = !var1;
      }
   }

   private void a(List<com.cheatbreaker.client.gui.uielement.settings.ColorSwatchElement> var1, int var2, int var3, int var4, int var5, int var6) {
      int var7 = 0;
      int var8 = 0;
      byte var9 = 8;

      for (com.cheatbreaker.client.gui.uielement.settings.ColorSwatchElement var11 : var1) {
         var11.scale = this.scale;
         if (var7 == var9) {
            var8++;
            var7 = 0;
         }

         if (var1 == this.colors) {
            int var12 = var9 * 2 / 8 * 12;
            int var13 = var2 + var12 - var8 * 12 - 12;
            int var14 = var3 + var7 * var12 - var7 * 12;
            var11.yOffset = this.yOffset;
            var11.a(var13, var14, 10, 10);
            String var15 = "0123456789abcdefklmnor";
            int var16 = var7 + var8 * var9;
            String var17 = var15.substring(var16, var16 + 1);
            if (var11.a(var4, var5)) {
               cn.kazer.client189.render.DrawRect.a(var13 + 12, var14 - 1, var13 + 26, var14 + 11, -1087492562);
               com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.a("&" + var17, (float)(var13 + 14), (float)var14, -1);
            }
         } else {
            int var18 = var2 + var8 * 12;
            int var19 = var3 + var7 * 12;
            var11.yOffset = this.yOffset;
            var11.a(var18, var19, 10, 10);
         }

         var11.a(var4, var5, (float)var6);
         var7++;
      }
   }

   private void a(List<com.cheatbreaker.client.gui.uielement.settings.ColorSwatchElement> var1, int var2, int var3, int var4, int var5) {
      int var6 = 0;
      int var7 = 0;
      byte var8 = 8;

      for (com.cheatbreaker.client.gui.uielement.settings.ColorSwatchElement var10 : var1) {
         if (var1 != this.colors) {
            if (var6 == var8) {
               var7++;
               var6 = 0;
            }

            int var12 = var2 + var7 * 12;
            int var11 = var3 + var6 * 12;
            var10.yOffset = this.yOffset;
            var10.a(var12, var11, 10, 10);
         }

         if (var10.a(var4, var5)) {
            if (var1 == this.colors) {
               this.setting.a(new Color(var10.color).getRGB());
            } else {
               this.setting.a(new Color(var10.color, true).getRGB());
            }

            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            float[] var15 = Color.RGBtoHSB(var10.color >> 16 & 0xFF, var10.color >> 8 & 0xFF, var10.color & 0xFF, null);
            this.IIIlllIIIllIllIlIIIIIIlII = var15[0];
            int var14 = (int)(var15[1] * this.pickerWidth);
            int var13 = (int)(this.pickerHeight - var15[2] * this.pickerHeight);
            this.setting.colorArray = new int[]{var14, var13};
         }

         var6++;
      }
   }

   @Override
   public void a(int var1, int var2, int var3) {
      boolean var5 = var1 > (this.x + this.width - 40) * this.scale
         && var1 < (this.x + this.width - 12) * this.scale
         && var2 > (this.y + this.yOffset) * this.scale
         && var2 < (this.y + 18 + this.yOffset) * this.scale;
      boolean var4 = var1 > this.x * this.scale
         && var1 < (this.x + this.width - 40) * this.scale
         && var2 > (this.y + this.yOffset) * this.scale
         && var2 < (this.y + 18 + this.yOffset) * this.scale;
      if (var4) {
         Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
         float[] var7 = Color.RGBtoHSB((Integer)this.setting.d() >> 16 & 0xFF, (Integer)this.setting.d() >> 8 & 0xFF, (Integer)this.setting.d() & 0xFF, null);
         this.IIIlllIIIllIllIlIIIIIIlII = var7[0];
         int var8 = (int)(var7[1] * this.pickerWidth);
         int var9 = (int)(this.pickerHeight - var7[2] * this.pickerHeight);
         this.setting.colorArray = new int[]{var8, var9};
         this.lIIIIllIIlIlIllIIIlIllIlI = !this.lIIIIllIIlIlIllIIIlIllIlI;
      } else if (var5) {
         if (com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.a((Integer)this.setting.d())) {
            com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.b((Integer)this.setting.d());
         } else {
            if (com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.favouriteColors.size() >= 16) {
               com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.favouriteColors.remove(0);
            }

            com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.favouriteColors.add(new com.cheatbreaker.client.gui.uielement.settings.ColorSwatchElement(this.scale, (Integer)this.setting.d()));
         }

         Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
      } else if (this.lIIIIllIIlIlIllIIIlIllIlI) {
         this.a(this.colors, 0, 0, var1, var2);
         this.a(
            com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.IlIIlIIlIllIIIIllIIllIlIl, this.lIIlIIllIIIIIlIllIIIIllII, this.lIIlllIIlIlllllllllIIIIIl, var1, var2
         );
         this.a(com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.favouriteColors, this.lIllIllIlIIllIllIlIlIIlIl, this.llIlIIIllIIIIlllIlIIIIIlI, var1, var2);
         boolean var10 = var1 > (this.pickerX - 51.0F) * this.scale
            && var2 > (this.pickerY + 1.0F + this.yOffset) * this.scale
            && var1 < (this.pickerX - 43.0F) * this.scale
            && var2 < (this.pickerY + 9.0F + this.yOffset) * this.scale;
         if (var1 > this.pickerX * this.scale
            && var1 < (this.pickerX + this.pickerWidth) * this.scale
            && var2 > (this.pickerY + this.yOffset) * this.scale
            && var2 < (this.pickerY + this.pickerHeight + this.yOffset) * this.scale) {
            this.IlllIllIlIIIIlIIlIIllIIIl = true;
         }

         if (var1 > (this.pickerX + this.pickerWidth + 4.0F) * this.scale
            && var1 < (this.pickerX + this.pickerWidth + 14.0F) * this.scale
            && var2 > (this.pickerY - 1.0F + this.yOffset) * this.scale
            && var2 < (this.pickerY + 1.0F + this.pickerHeight + this.yOffset) * this.scale) {
            this.IlIlllIIIIllIllllIllIIlIl = true;
         }

         if (var1 > (this.pickerX + this.pickerWidth + 18.0F) * this.scale
            && var1 < (this.pickerX + this.pickerWidth + 28.0F) * this.scale
            && var2 > (this.pickerY - 1.0F + this.yOffset) * this.scale
            && var2 < (this.pickerY + 1.0F + this.pickerHeight + this.yOffset) * this.scale) {
            this.llIIlllIIIIlllIllIlIlllIl = true;
         }

         if (var10) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            this.setting.rainbow = !this.setting.rainbow;
         }
      }
   }

   @Override
   public com.cheatbreaker.client.settings.Setting b() {
      return this.setting;
   }
}
