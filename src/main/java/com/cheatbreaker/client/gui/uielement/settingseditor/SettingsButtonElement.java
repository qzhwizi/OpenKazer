package com.cheatbreaker.client.gui.uielement.settingseditor;

import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class SettingsButtonElement extends com.cheatbreaker.client.gui.uielement.UIElement {
   public int lIIIIlIIllIIlIIlIIIlIIllI;
   public String displayString;
   public final com.cheatbreaker.client.gui.uielement.ScrollableUIElement lIIIIllIIlIlIllIIIlIllIlI;
   private final com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer fontRenderer;
   public boolean IlllIllIlIIIIlIIlIIllIIIl = true;
   private int llIIlllIIIIlllIllIlIlllIl = 0;
   private int hoverCornerRadius = 0;
   private boolean kazerLandingStyle;
   private float opacity = 1.0F;

   public com.cheatbreaker.client.gui.uielement.settingseditor.SettingsButtonElement d() {
      this.kazerLandingStyle = true;
      return this;
   }

   public com.cheatbreaker.client.gui.uielement.settingseditor.SettingsButtonElement a(float var1) {
      this.opacity = Math.max(0.0F, Math.min(1.0F, var1));
      return this;
   }

   private int c(int var1) {
      int var2 = Math.round((var1 >>> 24) * this.opacity);
      return var1 & 16777215 | var2 << 24;
   }

   public com.cheatbreaker.client.gui.uielement.settingseditor.SettingsButtonElement b(int var1) {
      this.hoverCornerRadius = var1;
      return this;
   }

   static float[] a(float var0, float var1, int var2, int var3) {
      return new float[]{var0 - 2.0F, var1 - 2.0F, var0 + var2 + 2.0F, var1 + var3 + 2.0F};
   }

   public int e() {
      return this.hoverCornerRadius;
   }

   public SettingsButtonElement(com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer var1, com.cheatbreaker.client.gui.uielement.ScrollableUIElement var2, String var3, int var4, int var5, int var6, int var7, int var8, float var9) {
      super(var9);
      this.displayString = var3;
      this.a(var4, var5, var6, var7);
      this.lIIIIlIIllIIlIIlIIIlIIllI = var8;
      this.lIIIIllIIlIlIllIIIlIllIlI = var2;
      this.fontRenderer = var1;
   }

   public SettingsButtonElement(com.cheatbreaker.client.gui.uielement.ScrollableUIElement var1, String var2, int var3, int var4, int var5, int var6, int var7, float var8) {
      this(com.cheatbreaker.client.CheatBreakerClient.N().playBold22px, var1, var2, var3, var4, var5, var6, var7, var8);
   }

   @Override
   public void a(int var1, int var2, float var3) {
      boolean var5 = this.a(var1, var2);
      if (this.kazerLandingStyle) {
         com.cheatbreaker.client.gui.render.RenderUtils.a(
            (double)this.x, (double)this.y, (double)(this.x + this.width), (double)(this.y + this.height), 5.0, this.c(var5 ? -865703321 : -1437970869)
         );
         com.cheatbreaker.client.gui.render.RenderUtils.a(
            (double)(this.x + 1),
            (double)(this.y + 1),
            (double)(this.x + this.width - 1),
            (double)(this.y + this.height - 1),
            4.0,
            this.c(var5 ? -953536467 : -1340794345)
         );
         this.fontRenderer.a(this.displayString.toUpperCase(), this.x, this.y, this.width, this.height, this.c(-1));
      } else {
         byte var6 = 120;
         if (var5 && this.IlllIllIlIIIIlIIlIIllIIIl) {
            float[] var7 = a((float)this.x, (float)this.y, this.width, this.height);
            if (this.hoverCornerRadius > 0) {
               com.cheatbreaker.client.gui.render.RenderUtils.a((double)var7[0], (double)var7[1], (double)var7[2], (double)var7[3], (double)this.hoverCornerRadius, -854025);
            } else {
               cn.kazer.client189.render.DrawRect.a(var7[0], var7[1], var7[2], var7[3], -854025);
            }

            float var8 = com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.a(790.0F);
            this.llIIlllIIIIlllIllIlIlllIl = this.llIIlllIIIIlllIllIlIlllIl + var8 < var6 ? (int)(this.llIIlllIIIIlllIllIlIlllIl + var8) : var6;
         } else if (this.llIIlllIIIIlllIllIlIlllIl > 0) {
            float var4 = com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.a(790.0F);
            this.llIIlllIIIIlllIllIlIlllIl = this.llIIlllIIIIlllIllIlIlllIl - var4 < 0.0F ? 0 : (int)(this.llIIlllIIIIlllIllIlIlllIl - var4);
         }

         if (this.IlllIllIlIIIIlIIlIIllIIIl) {
            cn.kazer.client189.render.DrawRect.a(this.x, this.y, this.x + this.width, this.y + this.height, -723724);
         } else {
            cn.kazer.client189.render.DrawRect.a(this.x, this.y, this.x + this.width, this.y + this.height, -1611336460);
         }

         if (this.llIIlllIIIIlllIllIlIlllIl > 0) {
            float var9 = (float)this.llIIlllIIIIlllIllIlIlllIl / var6 * 100.0F;
            cn.kazer.client189.render.DrawRect.a(
               this.x, (int)(this.y + (this.height - this.height * var9 / 100.0F)), this.x + this.width, this.y + this.height, this.lIIIIlIIllIIlIIlIIIlIIllI
            );
         }

         if (this.displayString.contains(".png")) {
            GL11.glPushMatrix();
            cn.kazer.client189.render.GlStateBridge.a(0.0F, 0.0F, 0.0F, 0.45F);
            com.cheatbreaker.client.gui.render.RenderUtils.a(new ResourceLocation("client/icons/" + this.displayString), 8.0F, this.x + 6, this.y + 6);
            GL11.glPopMatrix();
         } else {
            float var10 = this.fontRenderer == com.cheatbreaker.client.CheatBreakerClient.N().playBold22px ? 2.0F : 0.5F;
            this.fontRenderer
               .b(this.displayString.toUpperCase(), (float)(this.x + this.width / 2), this.y + this.height / 2 - this.fontRenderer.a() + var10, 1862270976);
         }
      }
   }

   @Override
   public void a(int var1, int var2, int var3) {
   }

   public int f() {
      return this.width;
   }

   public void a(boolean var1) {
      this.IlllIllIlIIIIlIIlIIllIIIl = var1;
   }
}
