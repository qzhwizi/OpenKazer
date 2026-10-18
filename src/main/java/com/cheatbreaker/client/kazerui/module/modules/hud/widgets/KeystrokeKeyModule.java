package com.cheatbreaker.client.kazerui.module.modules.hud.widgets;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public final class KeystrokeKeyModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   public final com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner key;
   private final com.cheatbreaker.client.kazerui.module.modules.hud.KeystrokesModule parent;
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showCps = this.a("showCps", false);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting textShadow = this.a("textShadow", false);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting border = this.a("border", false);
   final com.cheatbreaker.client.kazerui.module.settings.ColorSetting borderColor = this.a("borderColor", -1);
   final com.cheatbreaker.client.kazerui.module.settings.ColorSetting textColor = this.a("textColor", -1);
   final com.cheatbreaker.client.kazerui.module.settings.ColorSetting textPressedColor = this.a("textPressedColor", -16777216);
   final com.cheatbreaker.client.kazerui.module.settings.ColorSetting backgroundColor = this.a("backgroundColor", 1862270976);
   final com.cheatbreaker.client.kazerui.module.settings.ColorSetting backgroundPressedColor = this.a("backgroundPressedColor", 1879048191);
   public final com.cheatbreaker.client.kazerui.module.settings.SliderSetting boxSize = this.a("boxSize", 18.0F, 10.0F, 32.0F);
   final com.cheatbreaker.client.kazerui.module.settings.SliderSetting borderThickness = this.a("borderThickness", 1.0F, 0.5F, 3.0F);
   final com.cheatbreaker.client.kazerui.module.settings.SliderSetting keyFadeDelay = this.a("keyFadeDelay", 75, 0, 500);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting animate = this.a("animate", true);
   final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.y> animationType = this.a(
      "animationType", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.y.STACKED
   );
   final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.v> timerType = this.a(
      "timerType", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.v.HALF
   );
   final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.aInner> animation = this.a(
      "animation", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.aInner.RIPPLE
   );
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting animateColor = this.a("animateColor", false);
   final com.cheatbreaker.client.kazerui.module.settings.ColorSetting animationStartColor = this.a("animationStartColor", -1056964609);
   final com.cheatbreaker.client.kazerui.module.settings.ColorSetting animationCenterColor = this.a("animationCenterColor", 1879048191);
   final com.cheatbreaker.client.kazerui.module.settings.ColorSetting animationEndColor = this.a("animationEndColor", 1879048191);
   final com.cheatbreaker.client.kazerui.module.settings.SliderSetting duration = this.a("duration", 0.5F, 0.1F, 1.0F);
   final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.w> timingFunction = this.a(
      "timingFunction", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.w.LINEAR
   );
   private final com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.c own = new com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.c() {
      @Override
      public boolean a() {
         return KeystrokeKeyModule.this.textShadow.b();
      }

      @Override
      public boolean b() {
         return KeystrokeKeyModule.this.border.b();
      }

      @Override
      public com.cheatbreaker.client.kazerui.module.settings.ColorSetting c() {
         return KeystrokeKeyModule.this.borderColor;
      }

      @Override
      public com.cheatbreaker.client.kazerui.module.settings.ColorSetting d() {
         return KeystrokeKeyModule.this.textColor;
      }

      @Override
      public com.cheatbreaker.client.kazerui.module.settings.ColorSetting e() {
         return KeystrokeKeyModule.this.textPressedColor;
      }

      @Override
      public com.cheatbreaker.client.kazerui.module.settings.ColorSetting f() {
         return KeystrokeKeyModule.this.backgroundColor;
      }

      @Override
      public com.cheatbreaker.client.kazerui.module.settings.ColorSetting g() {
         return KeystrokeKeyModule.this.backgroundPressedColor;
      }

      @Override
      public float h() {
         return KeystrokeKeyModule.this.boxSize.b();
      }

      @Override
      public float i() {
         return KeystrokeKeyModule.this.borderThickness.b();
      }

      @Override
      public long j() {
         return KeystrokeKeyModule.this.keyFadeDelay.c();
      }

      @Override
      public boolean k() {
         return KeystrokeKeyModule.this.animate.b();
      }

      @Override
      public com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.y l() {
         return KeystrokeKeyModule.this.animationType.j();
      }

      @Override
      public com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.v m() {
         return KeystrokeKeyModule.this.timerType.j();
      }

      @Override
      public com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.aInner n() {
         return KeystrokeKeyModule.this.animation.j();
      }

      @Override
      public boolean o() {
         return KeystrokeKeyModule.this.animateColor.b();
      }

      @Override
      public com.cheatbreaker.client.kazerui.module.settings.ColorSetting p() {
         return KeystrokeKeyModule.this.animationStartColor;
      }

      @Override
      public com.cheatbreaker.client.kazerui.module.settings.ColorSetting q() {
         return KeystrokeKeyModule.this.animationCenterColor;
      }

      @Override
      public com.cheatbreaker.client.kazerui.module.settings.ColorSetting r() {
         return KeystrokeKeyModule.this.animationEndColor;
      }

      @Override
      public float s() {
         return KeystrokeKeyModule.this.duration.b();
      }

      @Override
      public com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.w t() {
         return KeystrokeKeyModule.this.timingFunction.j();
      }

      @Override
      public boolean a(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner var1) {
         return KeystrokeKeyModule.this.showCps.b();
      }
   };
   public final com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.a hud;
   public float layoutX;
   public float layoutY;
   private int labelKey = Integer.MIN_VALUE;
   private int labelCps = Integer.MIN_VALUE;
   private boolean labelArrows;
   private boolean labelSmall;
   private long labelResources = Long.MIN_VALUE;
   private long cpsResources = Long.MIN_VALUE;
   private String labelText;
   private String cpsText;
   private boolean wasPressed;
   private com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.p synced;
   private com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.s syncedShape;
   private final List<Object[]> stacked = new LinkedList<>();
   private long fadeStart;
   private boolean fadeState;

   public KeystrokeKeyModule(com.cheatbreaker.client.kazerui.module.modules.hud.KeystrokesModule var1, com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner var2) {
      super("KEYSTROKE_KEY_" + var2.name(), true);
      this.parent = var1;
      this.key = var2;
      this.hud = this.a(new com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.a());
      this.a((java.util.function.BooleanSupplier) () -> var1.n());
      com.cheatbreaker.client.kazerui.gui.components.KazerLang.a(this.id, () -> com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("features.KEYSTROKES.info", var2.nameId));
   }

   public com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.c n() {
      return this.own;
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      if (this.key.a()) {
         var1.a(this.showCps);
      }

      var1.a(this.textShadow, this.border);
      var1.a(this.animate, var1x -> {
         var1x.a(this.animationType, this.timerType, this.animation, this.duration, this.timingFunction);
         var1x.a(this.animateColor, var1xx -> var1xx.a(this.animationStartColor, this.animationCenterColor, this.animationEndColor));
      });
      var1.a(this.borderThickness, this.boxSize, this.borderColor, this.textColor, this.textPressedColor, this.backgroundColor, this.backgroundPressedColor);
      var1.a(this.keyFadeDelay).a(this.animate::b);
   }

   String a(float var1) {
      Minecraft var2 = Minecraft.getMinecraft();
      int var3 = this.key.a() ? 0 : this.key.a(var2.gameSettings).getKeyCode();
      boolean var4 = this.key.b() && this.parent.useArrows.b();
      boolean var5 = var1 < 14.0F;
      long var6 = com.cheatbreaker.client.kazerui.RenderEpoch.a();
      if (this.labelText != null && var3 == this.labelKey && var4 == this.labelArrows && var5 == this.labelSmall && var6 == this.labelResources) {
         return this.labelText;
      } else {
         String var8;
         if (this.key == com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.MOUSE1) {
            var8 = com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("features.KEYSTROKES.info", "lmb");
         } else if (this.key == com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.MOUSE2) {
            var8 = com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("features.KEYSTROKES.info", "rmb");
         } else {
            var8 = GameSettings.getKeyDisplayString(var3).toUpperCase(Locale.ROOT);
         }

         if (var4) {
            switch (this.key) {
               case W:
                  var8 = "▲";
                  break;
               case A:
                  var8 = "◀";
                  break;
               case S:
                  var8 = "▼";
                  break;
               default:
                  var8 = "▶";
            }
         }

         if (var1 < 14.0F && var8.length() > 1) {
            var8 = var8.substring(0, 1);
         }

         this.labelKey = var3;
         this.labelArrows = var4;
         this.labelSmall = var5;
         this.labelResources = var6;
         this.labelText = var8;
         return var8;
      }
   }

   boolean o() {
      Minecraft var1 = Minecraft.getMinecraft();
      if (var1.currentScreen != null) {
         return false;
      } else {
         int var2 = this.key.a(var1.gameSettings).getKeyCode();
         if (var2 == 0) {
            return false;
         } else {
            return var2 < 0 ? Mouse.isButtonDown(var2 + 100) : Keyboard.isKeyDown(var2);
         }
      }
   }

   int p() {
      return com.cheatbreaker.client.kazerui.cps.CpsTracker.a(this.key == com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.MOUSE1, false);
   }

   private void a(boolean var1, com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.c var2) {
      if (var2.k()) {
         if (var2.l() == com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.y.SYNCED) {
            if (this.synced == null || this.synced.e()) {
               this.synced = new com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.l(var2.s());
               this.syncedShape = var2.n().b();
            }

            if (var1) {
               this.synced.c();
            } else {
               this.synced.d();
            }
         } else if (var1) {
            this.stacked.add(new Object[]{var2.m().a(var2.s()), var2.n().b(), var2.t()});
         }
      }
   }

   private static int a(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.c var0, float var1, float var2) {
      if (!var0.o()) {
         return var0.g().a(var2);
      } else {
         return var1 < 1.0F
            ? a(var0.p().a(var2), var0.q().a(var2), Math.max(0.0F, Math.min(1.0F, var1)))
            : a(var0.q().a(var2), var0.r().a(var2), Math.max(0.0F, Math.min(1.0F, var1 - 1.0F)));
      }
   }

   private static int a(int var0, int var1, float var2) {
      float[] var3 = Color.RGBtoHSB(var0 >> 16 & 0xFF, var0 >> 8 & 0xFF, var0 & 0xFF, null);
      float[] var4 = Color.RGBtoHSB(var1 >> 16 & 0xFF, var1 >> 8 & 0xFF, var1 & 0xFF, null);
      float var5 = var3[0];
      float var6 = var4[0];
      if (var6 - var5 > 0.5F) {
         var5++;
      } else if (var6 - var5 < -0.5F) {
         var6++;
      }

      float var7 = (var5 + (var6 - var5) * var2) % 1.0F;
      float var8 = var3[1] + (var4[1] - var3[1]) * var2;
      float var9 = var3[2] + (var4[2] - var3[2]) * var2;
      int var10 = Color.HSBtoRGB(var7, var8, var9);
      int var11 = Math.round((var0 >>> 24) + ((var1 >>> 24) - (var0 >>> 24)) * var2);
      return var11 << 24 | var10 & 16777215;
   }

   private int a(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.c var1, boolean var2, float var3) {
      long var4 = System.currentTimeMillis();
      if (var2 != this.fadeState) {
         this.fadeState = var2;
         this.fadeStart = var4;
      }

      long var6 = var1.j();
      if (this.fadeStart != 0L && var4 - this.fadeStart < var6) {
         float var8 = (float)(var4 - this.fadeStart) / (float)var6;
         int var9 = (this.fadeState ? var1.f() : var1.g()).a(var3);
         int var10 = (this.fadeState ? var1.g() : var1.f()).a(var3);
         int var11 = 0;

         for (byte var12 = 0; var12 < 32; var12 += 8) {
            int var13 = (int)Math.abs(var8 * (var10 >>> var12 & 0xFF) + (1.0F - var8) * (var9 >>> var12 & 0xFF));
            var11 |= (var13 & 0xFF) << var12;
         }

         return var11;
      } else {
         return (var2 ? var1.g() : var1.f()).a(var3);
      }
   }

   public void a(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.c var1, float var2, float var3, float var4, boolean var5) {
      boolean var6 = this.o();
      if (var6 != this.wasPressed) {
         this.wasPressed = var6;
         this.a(var6, var1);
      }

      if (var1.k()) {
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(0.0F, 0.0F, var2, var3, var1.f().a(var4));
      } else {
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(0.0F, 0.0F, var2, var3, this.a(var1, var6, var4));
      }

      Iterator var7 = this.stacked.iterator();

      while (var7.hasNext()) {
         Object[] var8 = (Object[])var7.next();
         com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.p var9 = (com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.p)var8[0];
         if (var9.e()) {
            var7.remove();
         } else {
            float var10 = ((com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.w)var8[2]).a(Math.max(0.0F, Math.min(1.0F, var9.a())));
            ((com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.s)var8[1]).a(var2, var3, var10, a(var1, var9.b(), var4));
         }
      }

      if (var1.l() == com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.y.SYNCED && this.synced != null && this.syncedShape != null) {
         float var18 = Math.max(0.0F, Math.min(1.0F, this.synced.a()));
         if (var18 != 0.0F) {
            this.syncedShape.a(var2, var3, var18, a(var1, this.synced.b(), var4));
         }
      }

      com.cheatbreaker.client.kazerui.module.settings.ColorSetting var19 = var6 ? var1.e() : var1.d();
      boolean var20 = var1.a();
      GlStateManager.enableBlend();
      if (this.key == com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.SPACE) {
         float var21 = this.parent.spacebarThickness.b();
         int var11 = var19.a(var2 / 2.0F + 3.0F);
         float var12 = var2 / 2.0F - var2 / 6.0F;
         float var13 = var2 / 3.0F;
         if (var20) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var12 + 1.0F, 4.0F, var13, var21, com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.b(var11));
         }

         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var12, 3.0F, var13, var21, var11);
      } else {
         float var22 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.b();
         float var23 = var2 / 2.0F;
         float var25;
         if (!var1.a(this.key)) {
            var25 = var3 / 2.0F - var22 / 2.0F + 1.0F;
         } else {
            var25 = var3 - var22 + 2.0F;
            float var26 = 0.6F;
            GlStateManager.pushMatrix();
            GlStateManager.scale(var26, var26, 1.0F);
            int var14 = this.p();
            long var15 = com.cheatbreaker.client.kazerui.RenderEpoch.a();
            if (this.cpsText == null || var14 != this.labelCps || var15 != this.cpsResources) {
               this.cpsText = var14 + " " + com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("shared_info", "cps");
               this.labelCps = var14;
               this.cpsResources = var15;
            }

            String var17 = this.cpsText;
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(
               var17,
               var23 / var26 - com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var17) / 2.0F,
               var25 / var26,
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var19.a(var23 + var25)),
               var20
            );
            GlStateManager.popMatrix();
            var25 = var3 / 4.0F - var22 / 4.0F;
         }

         String var27 = this.a(var1.h());
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(
            var27,
            var23 - com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var27) / 2.0F,
            var25,
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var19.a(var23 + var25)),
            var20
         );
      }

      if (!var5 && var1.b()) {
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var1.c(), 0.0F, 0.0F, var2, var3, var1.i());
      }
   }

   public float b(float var1) {
      if (this.key == com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.SPACE) {
         return var1 * 3.0F + 2.0F;
      } else {
         return this.key.a() ? (var1 * 3.0F + 1.0F) / 2.0F : var1;
      }
   }

   public float a(float var1, boolean var2) {
      if (!var2) {
         return 0.0F;
      } else {
         return this.key == com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner.SPACE ? var1 / 2.0F : var1;
      }
   }

   public final class a extends com.cheatbreaker.client.kazerui.module.modulerender.HudElement {
      a() {
         super(KeystrokeKeyModule.this, 0.0F, 0.0F, com.cheatbreaker.client.kazerui.hud.HudAnchor.TOP_LEFT);
      }

      @Override
      public float d() {
         KeystrokeKeyModule.this.parent.o();
         return KeystrokeKeyModule.this.layoutX;
      }

      @Override
      public float e() {
         KeystrokeKeyModule.this.parent.o();
         return KeystrokeKeyModule.this.layoutY;
      }

      @Override
      public boolean a(boolean var1) {
         if (KeystrokeKeyModule.this.a() && KeystrokeKeyModule.this.parent.n()) {
            float var2 = KeystrokeKeyModule.this.boxSize.b();
            this.a(KeystrokeKeyModule.this.b(var2), KeystrokeKeyModule.this.a(var2, true));
            return true;
         } else {
            this.a(0.0F, 0.0F);
            return false;
         }
      }

      @Override
      public boolean l() {
         return KeystrokeKeyModule.this.parent.n();
      }

      @Override
      public void b(boolean var1) {
         KeystrokeKeyModule.this.a(KeystrokeKeyModule.this.own, this.f(), this.g(), 0.0F, false);
      }
   }

   public static enum bInner {
      W("forward"),
      A("left"),
      S("back"),
      D("right"),
      SPACE("jump"),
      MOUSE1("attack"),
      MOUSE2("use");

      final String nameId;

      private bInner(String var3) {
         this.nameId = var3;
      }

      public boolean a() {
         return this == MOUSE1 || this == MOUSE2;
      }

      public boolean b() {
         return this == W || this == A || this == S || this == D;
      }

      KeyBinding a(GameSettings var1) {
         switch (this) {
            case W:
               return var1.keyBindForward;
            case A:
               return var1.keyBindLeft;
            case S:
               return var1.keyBindBack;
            case D:
               return var1.keyBindRight;
            case SPACE:
               return var1.keyBindJump;
            case MOUSE1:
               return var1.keyBindAttack;
            default:
               return var1.keyBindUseItem;
         }
      }
   }

   public interface c {
      boolean a();

      boolean b();

      com.cheatbreaker.client.kazerui.module.settings.ColorSetting c();

      com.cheatbreaker.client.kazerui.module.settings.ColorSetting d();

      com.cheatbreaker.client.kazerui.module.settings.ColorSetting e();

      com.cheatbreaker.client.kazerui.module.settings.ColorSetting f();

      com.cheatbreaker.client.kazerui.module.settings.ColorSetting g();

      float h();

      float i();

      long j();

      boolean k();

      com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.y l();

      com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.v m();

      com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.aInner n();

      boolean o();

      com.cheatbreaker.client.kazerui.module.settings.ColorSetting p();

      com.cheatbreaker.client.kazerui.module.settings.ColorSetting q();

      com.cheatbreaker.client.kazerui.module.settings.ColorSetting r();

      float s();

      com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.w t();

      boolean a(com.cheatbreaker.client.kazerui.module.modules.hud.widgets.KeystrokeKeyModule.bInner var1);
   }
}
