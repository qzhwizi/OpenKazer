package com.cheatbreaker.client.kazerui.module.modulerender;

import java.util.List;

public abstract class RichTextHudBase extends com.cheatbreaker.client.kazerui.module.modulerender.HudElement {
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
   public final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modulerender.RichTextHudBase.a> alignment;
   public final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showHudIcons;
   public final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showText;
   private String line;
   private float textWidth;
   private List<String> block;
   private float blockHeight;
   private String measuredLine;
   private boolean measuredBrackets;
   private long measuredResources = Long.MIN_VALUE;

   public static int[] a(int var0, int var1, int var2, int var3, int var4, int var5) {
      return new int[]{var0, var1, var2, var3, var4, var5};
   }

   protected RichTextHudBase(com.cheatbreaker.client.kazerui.module.KazerModule var1, float var2, float var3, com.cheatbreaker.client.kazerui.hud.HudAnchor var4, int[] var5) {
      this(var1, var2, var3, var4, var5, false, false, true);
   }

   protected RichTextHudBase(
      com.cheatbreaker.client.kazerui.module.KazerModule var1,
      float var2,
      float var3,
      com.cheatbreaker.client.kazerui.hud.HudAnchor var4,
      int[] var5,
      boolean var6,
      boolean var7,
      boolean var8
   ) {
      this(var1, var2, var3, var4, var5, var6, var7, var8, true);
   }

   protected RichTextHudBase(
      com.cheatbreaker.client.kazerui.module.KazerModule var1,
      float var2,
      float var3,
      com.cheatbreaker.client.kazerui.hud.HudAnchor var4,
      int[] var5,
      boolean var6,
      boolean var7,
      boolean var8,
      boolean var9
   ) {
      super(var1, var2, var3, var4);
      this.background = var1.a("background", var8);
      this.brackets = var1.a("brackets", true);
      this.staticBackgroundWidth = var1.a("staticBackgroundWidth", var9);
      this.staticBackgroundHeight = var1.a("staticBackgroundHeight", true);
      this.backgroundWidth = var1.a("backgroundWidth", var5[4], var5[3], var5[5]);
      this.backgroundHeight = var1.a("backgroundHeight", var5[1], var5[0], var5[2]);
      this.border = var1.a("border", false);
      this.textShadow = var1.a("textShadow", true);
      this.textColor = var1.a("textColor", -1);
      this.bracketColor = var1.a("bracketColor", -1);
      this.backgroundColor = var1.a("backgroundColor", 1862270976);
      this.borderColor = var1.a("borderColor", -1627389952);
      this.borderThickness = var1.a("borderThickness", 0.5F, 0.5F, 3.0F);
      this.alignment = var6 ? var1.a("alignment", com.cheatbreaker.client.kazerui.module.modulerender.RichTextHudBase.a.CENTER) : null;
      this.showHudIcons = var7 ? var1.a("showHudIcons", true) : null;
      this.showText = var7 ? var1.a("showText", true) : null;
   }

   protected abstract String c(boolean var1);

   protected List<String> d(boolean var1) {
      return null;
   }

   protected void a(float var1, float var2, boolean var3, boolean var4) {
   }

   @Override
   public boolean a(boolean var1) {
      this.block = this.d(var1);
      if (this.block != null) {
         this.measuredLine = null;
         if (this.block.isEmpty()) {
            return false;
         } else {
            boolean var6 = this.q();
            this.line = null;
            this.textWidth = 0.0F;
            this.blockHeight = 0.0F;

            for (String var8 : this.block) {
               float var5 = this.a(this.r() ? "[" + var8 + "]" : var8);
               if (var5 > this.textWidth || this.line == null) {
                  this.textWidth = var5;
                  this.line = var8;
               }

               this.blockHeight = this.blockHeight + (this.v() + 1.0F);
            }

            this.a(this.e(var6), this.f(var6));
            return true;
         }
      } else {
         this.line = this.c(var1);
         if (this.line != null && !this.line.isEmpty()) {
            boolean var2 = this.q();
            boolean var3 = this.r();
            long var4 = this.m();
            if (!this.line.equals(this.measuredLine) || var3 != this.measuredBrackets || var4 != this.measuredResources) {
               this.textWidth = this.a(var3 ? "[" + this.line + "]" : this.line);
               this.measuredLine = this.line;
               this.measuredBrackets = var3;
               this.measuredResources = var4;
            }

            this.a(this.e(var2), this.f(var2));
            return true;
         } else {
            return false;
         }
      }
   }

   protected float a(String var1) {
      return com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var1);
   }

   protected long m() {
      return com.cheatbreaker.client.kazerui.RenderEpoch.a() * 2L + (com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a().getUnicodeFlag() ? 1 : 0);
   }

   protected float e(boolean var1) {
      Boolean var2 = this.b(this.line);
      boolean var3 = var1 && (var2 != null ? var2 : this.staticBackgroundWidth.b());
      return var3 ? this.backgroundWidth.c() : this.textWidth + (var1 ? 8 : 0);
   }

   protected Boolean b(String var1) {
      return null;
   }

   protected float f(boolean var1) {
      Boolean var2 = this.n();
      boolean var3 = var1 && (var2 != null ? var2 : this.staticBackgroundHeight.b());
      return var3 ? this.backgroundHeight.c() : (this.block != null ? this.blockHeight : this.v()) + (var1 ? 8 : 0);
   }

   protected Boolean n() {
      return null;
   }

   protected Boolean o() {
      return null;
   }

   protected Boolean p() {
      return null;
   }

   protected boolean q() {
      Boolean var1 = this.p();
      return var1 != null ? var1 : this.background.b();
   }

   protected boolean r() {
      Boolean var1 = this.o();
      return !this.q() && (var1 != null ? var1 : this.brackets.b());
   }

   protected boolean s() {
      return false;
   }

   protected boolean t() {
      return false;
   }

   protected boolean u() {
      return false;
   }

   protected float v() {
      return com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.b();
   }

   @Override
   public void b(boolean var1) {
      boolean var2 = this.q();
      boolean var3 = this.r();
      float var4 = this.f();
      float var5 = this.g();
      if (var2) {
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(this.backgroundColor, 0.0F, 0.0F, var4, var5);
         if (this.border.b()) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(this.borderColor, 0.0F, 0.0F, var4, var5, this.borderThickness.b());
         }
      }

      this.a(0.0F, 0.0F, var1, false);
      if (this.block != null) {
         float var10 = var5 / 2.0F - this.blockHeight / 2.0F + 1.0F;

         for (String var8 : this.block) {
            float var9 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var3 ? "[" + var8 + "]" : var8);
            this.a(var8, this.a(0.0F, var4, var9, var2), var10, var3, this.textShadow.b());
            var10 += this.v() + 1.0F;
         }

         this.a(0.0F, 0.0F, var1, true);
      } else {
         float var6 = var5 / 1.88F - this.v() / 2.0F + 0.5F;
         float var7 = this.a(0.0F, var4, this.textWidth, var2);
         this.a(this.line, var7, var6, var3, this.textShadow.b());
         this.a(0.0F, 0.0F, var1, true);
      }
   }

   protected void a(String var1, float var2, float var3, boolean var4, boolean var5) {
      if (var4) {
         var2 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(this.bracketColor, "[", var2, var3, var5);
      }

      var2 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(this.textColor, var1, var2, var3, var5);
      if (var4) {
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(this.bracketColor, "]", var2, var3, var5);
      }
   }

   protected float a(float var1, float var2, float var3, boolean var4) {
      com.cheatbreaker.client.kazerui.module.modulerender.RichTextHudBase.a var5 = this.alignment != null ? this.alignment.j() : this.w();
      switch (var5) {
         case LEFT:
            return var1 + (var4 ? 4 : 0);
         case RIGHT:
            return var1 + var2 - (var4 ? 4 : 0) - var3;
         default:
            return var1 + var2 / 2.0F - var3 / 2.0F;
      }
   }

   protected com.cheatbreaker.client.kazerui.module.modulerender.RichTextHudBase.a w() {
      return com.cheatbreaker.client.kazerui.module.modulerender.RichTextHudBase.a.CENTER;
   }

   protected boolean x() {
      return false;
   }

   @Override
   public void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      super.a(var1);
      var1.a("generalOptions", var1x -> {
         var1x.a(this.textShadow).a(this::t);
         var1x.a(this.brackets).a(() -> this.background.b() || this.o() != null);
         var1x.a(this.background, var1xx -> {
            var1xx.a(this.staticBackgroundWidth).a(() -> this.b(null) != null);
            var1xx.a(this.staticBackgroundHeight).a(() -> !this.background.b() || this.n() != null);
            var1xx.a(this.backgroundWidth).a(() -> {
               Boolean var1xxx = this.b(null);
               return var1xxx != null ? !var1xxx : !this.staticBackgroundWidth.b();
            });
            var1xx.a(this.backgroundHeight).a(() -> {
               Boolean var1xxx = this.n();
               return var1xxx != null ? !var1xxx : !this.staticBackgroundHeight.b();
            });
            var1xx.a(this.border, var1xxx -> var1xxx.a(this.borderThickness));
         }).a(() -> this.p() != null || this.s());
         if (this.alignment != null) {
            var1x.a(this.alignment);
         }

         if (this.showHudIcons != null) {
            var1x.a(this.showHudIcons);
         }

         if (this.showText != null) {
            var1x.a(this.showText).a(() -> !this.x());
         }
      });
      var1.a("colorOptions", var1x -> var1x.a(this.textColor).a(this::u));
      a(var1, this.brackets, this.bracketColor);
      a(var1, this.background, this.backgroundColor);
      a(var1, this.border, this.borderColor);
   }

   private static void a(
      com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var0, com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<?> var1, com.cheatbreaker.client.kazerui.module.settings.ColorSetting var2
   ) {
      var0.a(var1, var2);
   }

   public static enum a implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
      LEFT("left"),
      CENTER("center"),
      RIGHT("right");

      private final String id;

      private a(String var3) {
         this.id = var3;
      }

      @Override
      public String a() {
         return this.id;
      }
   }
}
