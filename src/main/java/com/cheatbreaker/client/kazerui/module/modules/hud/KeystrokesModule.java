package com.cheatbreaker.client.kazerui.module.modules.hud;

import java.util.EnumMap;
import java.util.Map;
import net.minecraft.client.renderer.GlStateManager;

public final class KeystrokesModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   public final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.hud.KeystrokesModule.b> keystrokesMode = this.a(
      "keystrokesMode", com.cheatbreaker.client.kazerui.module.modules.hud.KeystrokesModule.b.GROUPED
   );
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting keyStrokesClicks = this.a("keyStrokesClicks", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting leftCPS = this.a("leftCPS", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting rightCPS = this.a("rightCPS", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting keyStrokesMovement = this.a("keyStrokesMovement", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting keyStrokesSpacebar = this.a("keyStrokesSpacebar", true);
   public final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting useArrows = this.a("useArrows", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting textShadow = this.a("textShadow", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting border = this.a("border", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting innerBorder = this.a("innerBorder", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting borderColor = this.a("borderColor", -1);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting textColor = this.a("textColor", -1);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting textPressedColor = this.a("textPressedColor", -16777216);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting backgroundColor = this.a("backgroundColor", 1862270976);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting backgroundPressedColor = this.a("backgroundPressedColor", 1879048191);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting boxSize = this.a("boxSize", 18.0F, 10.0F, 32.0F);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting borderThickness = this.a("borderThickness", 1.0F, 0.5F, 3.0F);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting keyFadeDelay = this.a("keyFadeDelay", 75, 0, 500);
   public final com.cheatbreaker.client.kazerui.module.settings.SliderSetting spacebarThickness = this.a("spacebarThickness", 1.0F, 1.0F, 4.25F);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting animate = this.a("animate", false);
   private final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.y> animationType = this.a(
      "animationType", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.y.STACKED
   );
   private final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.v> timerType = this.a(
      "timerType", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.v.HALF
   );
   private final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.aInner> animation = this.a(
      "animation", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.aInner.RIPPLE
   );
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting animateColor = this.a("animateColor", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting animationStartColor = this.a("animationStartColor", -1056964609);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting animationCenterColor = this.a("animationCenterColor", 1879048191);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting animationEndColor = this.a("animationEndColor", 1879048191);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting duration = this.a("duration", 0.5F, 0.1F, 1.0F);
   private final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.w> timingFunction = this.a(
      "timingFunction", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.w.LINEAR
   );
   private final Map<com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner, com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule> keys = new EnumMap<>(
      com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.class
   );
   private final com.cheatbreaker.client.kazerui.module.modules.hud.KeystrokesModule.a group;
   private float groupWidth;
   private float groupHeight;
   private final com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.c style = new com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.c() {
      @Override
      public boolean a() {
         return KeystrokesModule.this.textShadow.b();
      }

      @Override
      public boolean b() {
         return false;
      }

      @Override
      public com.cheatbreaker.client.kazerui.module.settings.ColorSetting c() {
         return KeystrokesModule.this.borderColor;
      }

      @Override
      public com.cheatbreaker.client.kazerui.module.settings.ColorSetting d() {
         return KeystrokesModule.this.textColor;
      }

      @Override
      public com.cheatbreaker.client.kazerui.module.settings.ColorSetting e() {
         return KeystrokesModule.this.textPressedColor;
      }

      @Override
      public com.cheatbreaker.client.kazerui.module.settings.ColorSetting f() {
         return KeystrokesModule.this.backgroundColor;
      }

      @Override
      public com.cheatbreaker.client.kazerui.module.settings.ColorSetting g() {
         return KeystrokesModule.this.backgroundPressedColor;
      }

      @Override
      public float h() {
         return KeystrokesModule.this.boxSize.b();
      }

      @Override
      public float i() {
         return KeystrokesModule.this.borderThickness.b();
      }

      @Override
      public long j() {
         return KeystrokesModule.this.keyFadeDelay.c();
      }

      @Override
      public boolean k() {
         return KeystrokesModule.this.animate.b();
      }

      @Override
      public com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.y l() {
         return KeystrokesModule.this.animationType.j();
      }

      @Override
      public com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.v m() {
         return KeystrokesModule.this.timerType.j();
      }

      @Override
      public com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.aInner n() {
         return KeystrokesModule.this.animation.j();
      }

      @Override
      public boolean o() {
         return KeystrokesModule.this.animateColor.b();
      }

      @Override
      public com.cheatbreaker.client.kazerui.module.settings.ColorSetting p() {
         return KeystrokesModule.this.animationStartColor;
      }

      @Override
      public com.cheatbreaker.client.kazerui.module.settings.ColorSetting q() {
         return KeystrokesModule.this.animationCenterColor;
      }

      @Override
      public com.cheatbreaker.client.kazerui.module.settings.ColorSetting r() {
         return KeystrokesModule.this.animationEndColor;
      }

      @Override
      public float s() {
         return KeystrokesModule.this.duration.b();
      }

      @Override
      public com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.w t() {
         return KeystrokesModule.this.timingFunction.j();
      }

      @Override
      public boolean a(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner var1) {
         return var1 == com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.MOUSE1
            ? KeystrokesModule.this.leftCPS.b()
            : var1 == com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.MOUSE2 && KeystrokesModule.this.rightCPS.b();
      }
   };

   public KeystrokesModule() {
      super("KEYSTROKES", false);
      this.group = this.a(new com.cheatbreaker.client.kazerui.module.modules.hud.KeystrokesModule.a());
      this.childRowsLast = true;

      for (com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner var4 : com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.values()) {
         this.keys.put(var4, this.a(new com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule(this, var4), null));
      }
   }

   public boolean n() {
      return this.keystrokesMode.a(com.cheatbreaker.client.kazerui.module.modules.hud.KeystrokesModule.b.INDIVIDUAL);
   }

   private boolean p() {
      return this.keystrokesMode.a(com.cheatbreaker.client.kazerui.module.modules.hud.KeystrokesModule.b.GROUPED);
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(
         "generalOptions",
         var1x -> {
            var1x.a(this.keystrokesMode);
            var1x.a(this.group.scale).a(() -> !this.p());
            var1x.a(this.keyStrokesClicks, var1xx -> var1xx.a(this.leftCPS, this.rightCPS)).a(() -> !this.p());
            var1x.a(this.useArrows);
            var1x.a(this.keyStrokesMovement, this.keyStrokesSpacebar, this.textShadow).a(() -> !this.p());
            var1x.a(this.border, var1xx -> var1xx.a(this.innerBorder)).a(() -> !this.p());
            var1x.a(this.animate, var1xx -> {
               var1xx.a(this.animationType, this.timerType, this.animation, this.duration, this.timingFunction);
               var1xx.a(this.animateColor, var1xxx -> var1xxx.a(this.animationStartColor, this.animationCenterColor, this.animationEndColor));
            }).a(() -> !this.p());
            var1x.a(
                  this.borderThickness,
                  this.boxSize,
                  this.borderColor,
                  this.textColor,
                  this.textPressedColor,
                  this.backgroundColor,
                  this.backgroundPressedColor
               )
               .a(() -> !this.p());
            var1x.a(this.keyFadeDelay).a(this.animate::b);
            var1x.a(this.spacebarThickness);
         }
      );
   }

   private boolean a(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner var1) {
      if (var1.b() && !this.keyStrokesMovement.b()) {
         return false;
      } else {
         return var1.a() && !this.keyStrokesClicks.b()
            ? false
            : var1 != com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.SPACE || this.keyStrokesSpacebar.b();
      }
   }

   private float b(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner var1) {
      return this.p() ? this.boxSize.b() : this.keys.get(var1).boxSize.b();
   }

   private boolean c(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner var1) {
      return this.p() ? this.a(var1) : this.keys.get(var1).a();
   }

   private float d(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner var1) {
      if (var1 == com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.SPACE) {
         return this.keys.get(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.A).b(this.b(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.A))
            + 1.0F
            + this.keys.get(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.S).b(this.b(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.S))
            + 1.0F
            + this.keys.get(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.D).b(this.b(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.D));
      } else {
         return var1.a()
            ? (
                  this.keys
                        .get(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.SPACE)
                        .b(this.b(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.SPACE))
                     - 1.0F
               )
               / 2.0F
            : this.b(var1);
      }
   }

   private float e(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner var1) {
      return this.keys.get(var1).a(this.b(var1), this.c(var1));
   }

   public void o() {
      for (com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule var2 : this.keys.values()) {
         com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner var3 = var2.key;
         float var4 = 0.0F;
         float var5 = 0.0F;
         if (var3 == com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.W) {
            var4 += this.d(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.A) + 1.0F;
         }

         if (var3 == com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.A
            || var3 == com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.S
            || var3 == com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.D
            || var3 == com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.SPACE
            || var3.a()) {
            var5 += this.e(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.W) + 1.0F;
            if (var3 == com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.S || var3 == com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.D) {
               var4 += this.d(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.A) + 1.0F;
            }

            if (var3 == com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.D) {
               var4 += this.d(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.S) + 1.0F;
            }

            if (var3 == com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.SPACE || var3.a()) {
               var5 += Math.max(
                     this.e(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.A),
                     Math.max(
                        this.e(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.S), this.e(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.D)
                     )
                  )
                  + 1.0F;
               if (var3.a()) {
                  if (this.c(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.SPACE)) {
                     var5 += this.e(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.SPACE) + 1.0F;
                  }

                  if (var3 == com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.MOUSE2) {
                     var4 += this.d(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.MOUSE1) + 1.0F;
                  }
               }
            }
         }

         var2.layoutX = var4;
         var2.layoutY = var5;
      }
   }

   private final class a extends com.cheatbreaker.client.kazerui.module.modulerender.HudElement {
      a() {
         super(KeystrokesModule.this, 0.0F, 0.0F, com.cheatbreaker.client.kazerui.hud.HudAnchor.TOP_LEFT);
      }

      @Override
      public void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      }

      @Override
      public boolean l() {
         return KeystrokesModule.this.p();
      }

      @Override
      public boolean a(boolean var1) {
         if (KeystrokesModule.this.n()) {
            this.a(0.0F, 0.0F);
            return false;
         } else {
            this.m();
            return KeystrokesModule.this.keyStrokesMovement.b() || KeystrokesModule.this.keyStrokesClicks.b() || KeystrokesModule.this.keyStrokesSpacebar.b();
         }
      }

      private void m() {
         KeystrokesModule.this.o();
         float var1 = Float.MAX_VALUE;
         float var2 = Float.MAX_VALUE;
         float var3 = 0.0F;
         float var4 = 0.0F;

         for (com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule var6 : KeystrokesModule.this.keys.values()) {
            float var7 = KeystrokesModule.this.b(var6.key);
            float var8 = var6.b(var7);
            float var9 = var6.a(var7, KeystrokesModule.this.a(var6.key));
            var1 = Math.min(var1, var6.layoutX);
            var2 = Math.min(var2, var6.layoutY);
            var3 = Math.max(var3, var6.layoutX + var8);
            var4 = Math.max(var4, var6.layoutY + var9);
         }

         KeystrokesModule.this.groupWidth = var3 - var1;
         KeystrokesModule.this.groupHeight = var4 - var2;
         this.a(KeystrokesModule.this.groupWidth, KeystrokesModule.this.groupHeight);
      }

      @Override
      public void b(boolean var1) {
         this.m();
         float var2 = KeystrokesModule.this.boxSize.b();
         float var3 = KeystrokesModule.this.borderThickness.b();

         for (com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule var5 : KeystrokesModule.this.keys.values()) {
            if (KeystrokesModule.this.a(var5.key)) {
               GlStateManager.pushMatrix();
               GlStateManager.translate(var5.layoutX, var5.layoutY, 0.0F);
               var5.a(KeystrokesModule.this.style, var5.b(var2), var5.a(var2, true), var5.layoutX + var5.layoutY, true);
               GlStateManager.popMatrix();
            }
         }

         boolean var11 = KeystrokesModule.this.border.b();
         boolean var12 = KeystrokesModule.this.innerBorder.b();
         if (var11 && !var12) {
            float var6 = 0.0F;
            float var7 = KeystrokesModule.this.groupHeight;
            boolean var8 = KeystrokesModule.this.keyStrokesClicks.b();
            boolean var9 = KeystrokesModule.this.keyStrokesSpacebar.b();
            if (KeystrokesModule.this.keyStrokesMovement.b()) {
               if (!var8 && !var9) {
                  var7 -= 2.0F;
               } else if (!var8) {
                  var7--;
               }

               var6 += var2 + 1.0F;
               var7 -= var2 + 1.0F;
            } else {
               var6 += (!var8 || !var9) && !var9 ? 3.0F : 2.0F;
               var7 -= var8 && var9 ? 2.0F : 3.0F;
            }

            float var10 = KeystrokesModule.this.groupWidth;
            if (!KeystrokesModule.this.keyStrokesMovement.b()) {
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(KeystrokesModule.this.borderColor, 0.0F, var6, var10, var7, var3);
            } else {
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(KeystrokesModule.this.borderColor, -var3, var6, var3, var7);
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(KeystrokesModule.this.borderColor, var10, var6, var3, var7);
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(KeystrokesModule.this.borderColor, -var3, var6 + var7, var10 + var3 * 2.0F, var3);
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(KeystrokesModule.this.borderColor, -var3, var6 - var3, var2 + var3 / 2.0F + 1.0F, var3);
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(KeystrokesModule.this.borderColor, var10 - var2 - 1.0F, var6 - var3, var2 + var3 + 1.0F, var3);
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(KeystrokesModule.this.borderColor, var2 - var3 + 1.0F, -var3, var3, var2 + 1.0F + var3);
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(KeystrokesModule.this.borderColor, var2 * 2.0F + 1.0F, -var3, var3, var2 + 1.0F + var3);
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(KeystrokesModule.this.borderColor, var2 + 1.0F, -var3, var2 + 1.0F, var3);
            }
         }

         if (var11 && var12) {
            for (com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule var16 : KeystrokesModule.this.keys.values()) {
               if (KeystrokesModule.this.a(var16.key)) {
                  com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(KeystrokesModule.this.borderColor, var16.layoutX, var16.layoutY, var16.b(var2), var16.a(var2, true), var3);
               }
            }
         }
      }
   }

   public static enum b implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
      GROUPED("grouped"),
      INDIVIDUAL("individual");

      private final String id;

      private b(String var3) {
         this.id = var3;
      }

      @Override
      public String a() {
         return this.id;
      }
   }
}
