package com.cheatbreaker.client.kazerui.module.modules.hud;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;

public final class CoordinateChildModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   final com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule.b kind;
   private final com.cheatbreaker.client.kazerui.module.modules.hud.CoordinatesHudModule parent;
   private final String tag;
   private final Supplier<String> value;
   private final String sample;
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showLabel = this.a("showLabel", true);
   final com.cheatbreaker.client.kazerui.module.settings.ColorSetting labelColor = this.a("labelColor", -1);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting cardinalDirection;
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting directionAffect;
   final com.cheatbreaker.client.kazerui.module.settings.ColorSetting directionAffectXColor;
   final com.cheatbreaker.client.kazerui.module.settings.ColorSetting directionAffectZColor;
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting presetBiomeColor;
   final com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule.a hud;
   private String cached;

   CoordinateChildModule(
      com.cheatbreaker.client.kazerui.module.modules.hud.CoordinatesHudModule var1,
      com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule.b var2,
      String var3,
      Supplier<String> var4,
      String var5
   ) {
      super("COORDINATES_" + var2.name() + "_CHILD", true);
      this.parent = var1;
      this.kind = var2;
      this.tag = var3;
      this.value = var4;
      this.sample = var5;
      boolean var6 = var2 == com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule.b.DIRECTION;
      this.cardinalDirection = var6 ? this.a("cardinalDirection", true) : null;
      this.directionAffect = var6 ? this.a("directionAffect", true) : null;
      this.directionAffectXColor = var6 ? this.a("directionAffectXColor", -1) : null;
      this.directionAffectZColor = var6 ? this.a("directionAffectZColor", -1) : null;
      this.presetBiomeColor = var2 == com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule.b.BIOME ? this.a("presetBiomeColor", true) : null;
      this.hud = this.a(new com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule.a());
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a("generalOptions", var1x -> {
         var1x.a(this.showLabel);
         if (this.cardinalDirection != null) {
            var1x.a(this.cardinalDirection, this.directionAffect);
         }
      });
      var1.a("colorOptions", var1x -> {
         var1x.a(this.labelColor);
         if (this.directionAffectXColor != null) {
            var1x.a(this.directionAffectXColor, this.directionAffectZColor).a(() -> !this.directionAffect.b());
         }

         if (this.presetBiomeColor != null) {
            var1x.a(this.presetBiomeColor);
         }
      });
   }

   com.cheatbreaker.client.kazerui.module.settings.ColorSetting n() {
      return this.hud.textColor;
   }

   void o() {
      String var1 = this.value.get();
      if (!var1.equals(this.cached)) {
         this.cached = var1;
      }
   }

   String b(boolean var1) {
      if (var1) {
         return this.sample;
      } else {
         return Minecraft.getMinecraft().thePlayer != null && this.cached != null ? this.cached : this.sample;
      }
   }

   final class a extends com.cheatbreaker.client.kazerui.module.modulerender.RichTextHudBase {
      a() {
         super(CoordinateChildModule.this, 0.0F, 0.0F, com.cheatbreaker.client.kazerui.hud.HudAnchor.TOP_LEFT, a(10, 10, 10, 0, 0, 0));
      }

      @Override
      protected boolean s() {
         return !CoordinateChildModule.this.parent.moveChildrenIndividually.b();
      }

      @Override
      protected boolean t() {
         return !CoordinateChildModule.this.parent.moveChildrenIndividually.b();
      }

      @Override
      public boolean l() {
         return CoordinateChildModule.this.parent.moveChildrenIndividually.b();
      }

      @Override
      protected String c(boolean var1) {
         return CoordinateChildModule.this.b(var1);
      }

      @Override
      public boolean a(boolean var1) {
         if (!CoordinateChildModule.this.a() || !CoordinateChildModule.this.parent.a() || !CoordinateChildModule.this.parent.moveChildrenIndividually.b()) {
            this.a(0.0F, 0.0F);
            return false;
         } else if (!CoordinateChildModule.this.parent.showWhileTyping.b() && Minecraft.getMinecraft().ingameGUI.getChatGUI().getChatOpen()) {
            return false;
         } else {
            this.h(var1);
            return this.f() > 0.0F;
         }
      }

      private float y() {
         return this.background.b() ? 4.0F : 2.0F;
      }

      private boolean g(boolean var1) {
         return var1 && !this.background.b() && this.brackets.b();
      }

      private void h(boolean var1) {
         String var2 = CoordinateChildModule.this.b(var1);
         float var3 = this.y();
         if (CoordinateChildModule.this.kind == com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule.b.DIRECTION) {
            boolean var9 = CoordinateChildModule.this.cardinalDirection.b();
            boolean var10 = CoordinateChildModule.this.directionAffect.b();
            if (!var9 && !var10) {
               this.a(0.0F, 0.0F);
            } else {
               int var6 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.b();
               float var7 = 0.0F;
               if (var10) {
                  var7 += var6 * 2 + 2;
               }

               if (var9) {
                  var7 += var6;
                  if (var10) {
                     var7 += var3;
                  }
               }

               float var8 = var9 ? com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var2) : com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a("+");
               if (var9 && CoordinateChildModule.this.showLabel.b()) {
                  var8 += com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(CoordinateChildModule.this.tag + ": ");
               }

               if (this.g(var9)) {
                  var8 += 9.0F;
               }

               this.a(var8 + var3 * 2.0F, var7 + var3 * 2.0F);
            }
         } else {
            String var4 = CoordinateChildModule.this.showLabel.b() ? CoordinateChildModule.this.tag + ": " + var2 : var2;
            float var5 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var4) + (this.g(true) ? 9 : 0);
            this.a(var5 + var3 * 2.0F, com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.b() + var3 * 2.0F);
         }
      }

      @Override
      public void b(boolean var1) {
         this.h(var1);
         if (!(this.f() <= 0.0F)) {
            boolean var2 = this.background.b();
            boolean var3 = this.textShadow.b();
            if (var2) {
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(this.backgroundColor, 0.0F, 0.0F, this.f(), this.g());
               if (this.border.b()) {
                  com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(this.borderColor, 0.0F, 0.0F, this.f(), this.g(), this.borderThickness.b());
               }
            }

            String var4 = CoordinateChildModule.this.b(var1);
            float var5 = this.y();
            if (CoordinateChildModule.this.kind == com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule.b.DIRECTION) {
               this.a(var4, var5, var3);
            } else {
               boolean var6 = this.g(true);
               float var7 = var5;
               float var8 = this.g() / 1.88F - com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.b() / 2.0F + 0.5F;
               if (var6) {
                  var7 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(this.bracketColor, "[", var5, var8, var3);
               }

               if (CoordinateChildModule.this.showLabel.b()) {
                  var7 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(CoordinateChildModule.this.labelColor, CoordinateChildModule.this.tag + ": ", var7, var8, var3);
               }

               int var9 = CoordinateChildModule.this.kind == com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule.b.BIOME && CoordinateChildModule.this.presetBiomeColor.b()
                  ? CoordinateChildModule.this.parent.n()
                  : this.textColor.a(0.0F);
               var7 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var4, var7, var8, var9, var3);
               if (var6) {
                  com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(this.bracketColor, "]", var7, var8, var3);
               }
            }
         }
      }

      private void a(String var1, float var2, boolean var3) {
         int var4 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.b();
         boolean var5 = CoordinateChildModule.this.cardinalDirection.b();
         boolean var6 = CoordinateChildModule.this.directionAffect.b();
         boolean var7 = var5 && CoordinateChildModule.this.showLabel.b();
         boolean var8 = this.g(var5);
         float var9 = var2;
         float var10 = var2;
         float var11 = this.f() / 2.0F;
         if (var6) {
            if (var1.contains("W") || var1.contains("E")) {
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.b(CoordinateChildModule.this.directionAffectXColor, var1.contains("W") ? "-" : "+", var11, var2, var3);
            }

            var10 = var2 + (var4 + var2);
         }

         if (var5) {
            if (var8) {
               var9 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(this.bracketColor, "[", var2, var10, var3);
            }

            if (var7) {
               var9 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(CoordinateChildModule.this.labelColor, CoordinateChildModule.this.tag + ": ", var9, var10, var3);
            }

            var9 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(this.textColor, var1, var9, var10, var3);
            if (var8) {
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(this.bracketColor, "]", var9, var10, var3);
            }

            var10 += var4 + var2;
         }

         if (var6 && (var1.contains("N") || var1.contains("S"))) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.b(CoordinateChildModule.this.directionAffectXColor, var1.contains("N") ? "-" : "+", var11, var10, var3);
         }
      }
   }

   public static enum b {
      X,
      Y,
      Z,
      C,
      DIRECTION,
      BIOME;
   }
}
