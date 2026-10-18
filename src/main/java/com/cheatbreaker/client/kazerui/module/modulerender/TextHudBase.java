package com.cheatbreaker.client.kazerui.module.modulerender;

import java.util.List;

public abstract class TextHudBase extends com.cheatbreaker.client.kazerui.module.modulerender.HudElement {
   public final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting background;
   public final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting brackets;
   public final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting staticBackgroundWidth;
   public final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting staticBackgroundHeight;
   public final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting border;
   public final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting textShadow;
   public final com.cheatbreaker.client.kazerui.module.settings.SliderSetting backgroundWidth;
   public final com.cheatbreaker.client.kazerui.module.settings.SliderSetting backgroundHeight;
   public final com.cheatbreaker.client.kazerui.module.settings.SliderSetting borderThickness;
   public final com.cheatbreaker.client.kazerui.module.settings.ColorSetting textColor;
   public final com.cheatbreaker.client.kazerui.module.settings.ColorSetting bracketColor;
   public final com.cheatbreaker.client.kazerui.module.settings.ColorSetting backgroundColor;
   public final com.cheatbreaker.client.kazerui.module.settings.ColorSetting borderColor;
   private String line;
   private String measuredLine;
   private List<String> block;
   private List<String> measuredBlock;
   private long epoch = Long.MIN_VALUE;
   private boolean measuredBrackets;
   private float contentWidth;
   private float contentHeight;
   private float step;
   private boolean bg;
   private boolean withBrackets;
   private boolean shadow;
   private float padding;
   private float edge;

   protected TextHudBase(com.cheatbreaker.client.kazerui.module.KazerModule var1, float var2, float var3, com.cheatbreaker.client.kazerui.hud.HudAnchor var4, int[] var5) {
      super(var1, var2, var3, var4);
      this.background = var1.a("background", true);
      this.brackets = var1.a("brackets", true);
      this.staticBackgroundWidth = var1.a("staticBackgroundWidth", false);
      this.staticBackgroundHeight = var1.a("staticBackgroundHeight", false);
      this.border = var1.a("border", false);
      this.textShadow = var1.a("textShadow", true);
      this.backgroundWidth = var1.a("backgroundWidth", var5[4], var5[3], var5[5]);
      this.backgroundHeight = var1.a("backgroundHeight", var5[1], var5[0], var5[2]);
      this.borderThickness = var1.a("borderThickness", 0.5F, 0.5F, 3.0F);
      this.textColor = var1.a("textColor", -1);
      this.bracketColor = var1.a("bracketColor", -1);
      this.backgroundColor = var1.a("backgroundColor", 1862270976);
      this.borderColor = var1.a("borderColor", -1627389952);
   }

   protected abstract String c(boolean var1);

   protected List<String> d(boolean var1) {
      return null;
   }

   @Override
   public boolean a(boolean var1) {
      this.block = this.d(var1);
      this.line = this.block == null ? this.c(var1) : null;
      if (this.block == null ? this.line != null && !this.line.isEmpty() : !this.block.isEmpty()) {
         this.bg = this.background.b();
         this.withBrackets = !this.bg && this.brackets.b();
         this.padding = this.bg ? 4.0F : 0.0F;
         this.edge = this.bg && this.border.b() ? this.borderThickness.b() : 0.0F;
         int var2 = this.backgroundColor.a(0.0F);
         boolean var3 = this.bg && (var2 >> 16 & 0xFF) * 0.299F + (var2 >> 8 & 0xFF) * 0.587F + (var2 & 0xFF) * 0.114F > 160.0F;
         this.shadow = this.textShadow.b() && !var3;
         long var4 = com.cheatbreaker.client.kazerui.RenderEpoch.a() * 2L + (com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a().getUnicodeFlag() ? 1 : 0);
         if (var4 != this.epoch
            || this.block != this.measuredBlock
            || this.line != null && !this.line.equals(this.measuredLine)
            || this.withBrackets != this.measuredBrackets) {
            this.contentWidth = 0.0F;
            int var6 = this.block == null ? 1 : this.block.size();
            this.step = HudTextUtils.a() + 2;
            if (this.block == null) {
               this.contentWidth = HudTextUtils.a(this.line);
            } else {
               for (String var8 : this.block) {
                  this.contentWidth = Math.max(this.contentWidth, (float)HudTextUtils.a(var8));
               }
            }

            if (this.withBrackets) {
               this.contentWidth = this.contentWidth + (HudTextUtils.a("[") + HudTextUtils.a("]"));
            }

            this.contentHeight = var6 * this.step - 2.0F;
            this.epoch = var4;
            this.measuredBlock = this.block;
            this.measuredLine = this.line;
            this.measuredBrackets = this.withBrackets;
         }

         float var9 = this.contentWidth + (this.shadow ? 1 : 0) + this.padding * 2.0F;
         float var10 = this.contentHeight + (this.shadow ? 1 : 0) + this.padding * 2.0F;
         if (this.bg && this.staticBackgroundWidth.b()) {
            var9 = Math.max(var9, this.backgroundWidth.b());
         }

         if (this.bg && this.staticBackgroundHeight.b()) {
            var10 = Math.max(var10, this.backgroundHeight.b());
         }

         this.a(var9 + this.edge * 2.0F, var10 + this.edge * 2.0F);
         return true;
      } else {
         return false;
      }
   }

   protected com.cheatbreaker.client.kazerui.module.settings.ColorSetting m() {
      return this.textColor;
   }

   protected void a(String var1, float var2, float var3) {
      if (this.withBrackets) {
         var2 = HudTextUtils.a("[", var2, var3, this.bracketColor.a(var2 + var3), this.shadow);
      }

      var2 = HudTextUtils.a(var1, var2, var3, this.m().a(var2 + var3), this.shadow);
      if (this.withBrackets) {
         HudTextUtils.a("]", var2, var3, this.bracketColor.a(var2 + var3), this.shadow);
      }
   }

   @Override
   public void b(boolean var1) {
      if (this.bg) {
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(this.backgroundColor, this.edge, this.edge, this.f() - this.edge * 2.0F, this.g() - this.edge * 2.0F);
         if (this.edge > 0.0F) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(
               this.borderColor, this.edge, this.edge, this.f() - this.edge * 2.0F, this.g() - this.edge * 2.0F, this.edge
            );
         }
      }

      float var2 = this.edge + this.padding;
      if (this.block == null) {
         this.a(this.line, this.edge + this.padding, var2);
      } else {
         for (String var4 : this.block) {
            this.a(var4, this.edge + this.padding, var2);
            var2 += this.step;
         }
      }
   }

   @Override
   public void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      super.a(var1);
      var1.a(this.textShadow, this.brackets);
      var1.a(this.background, var1x -> {
         var1x.a(this.staticBackgroundWidth, this.staticBackgroundHeight, this.backgroundWidth, this.backgroundHeight);
         var1x.a(this.border, var1xx -> var1xx.a(this.borderThickness, this.borderColor));
         var1x.a(this.backgroundColor);
      });
      var1.a("colorOptions", var1x -> var1x.a(this.textColor, this.bracketColor));
   }
}
