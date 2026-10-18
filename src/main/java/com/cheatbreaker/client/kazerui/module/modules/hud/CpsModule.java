package com.cheatbreaker.client.kazerui.module.modules.hud;

public final class CpsModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting rightClick = this.a("rightClick", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showCPSText = this.a("showCPSText", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting reverseText = this.a("reverseText", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting ignoreCancelledClicks = this.a("ignoreCancelledClicks", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting lineColor = this.a("lineColor", -14671840);
   private final com.cheatbreaker.client.kazerui.module.modules.hud.CpsModule.a hud;
   private int lastLeft = -1;
   private int lastRight = -1;
   private int lastOptions = -1;
   private String cachedText;

   public CpsModule() {
      super("CPS", false);
      this.hud = this.a(new com.cheatbreaker.client.kazerui.module.modules.hud.CpsModule.a());
   }

   @Override
   protected void f() {
      com.cheatbreaker.client.kazerui.cps.CpsTracker.b();
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a("settings", var1x -> {
         var1x.a(this.rightClick, var1xx -> var1xx.a(this.lineColor));
         var1x.a(this.showCPSText);
         var1x.a(this.reverseText);
         var1x.a(this.ignoreCancelledClicks);
      });
   }

   static String n() {
      return com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("shared_info", "cps");
   }

   private final class a extends com.cheatbreaker.client.kazerui.module.modulerender.RichTextHudBase {
      a() {
         super(CpsModule.this, 0.0F, 0.0F, com.cheatbreaker.client.kazerui.hud.HudAnchor.TOP_LEFT, a(10, 18, 22, 40, 56, 62));
      }

      @Override
      protected String c(boolean var1) {
         boolean var2 = CpsModule.this.rightClick.b();
         boolean var3 = CpsModule.this.showCPSText.b();
         boolean var4 = CpsModule.this.reverseText.b();
         int var5 = com.cheatbreaker.client.kazerui.cps.CpsTracker.a(CpsModule.this.ignoreCancelledClicks.b());
         int var6 = var2 ? com.cheatbreaker.client.kazerui.cps.CpsTracker.a() : 0;
         int var7 = (var2 ? 1 : 0) | (var3 ? 2 : 0) | (var4 ? 4 : 0);
         if (CpsModule.this.cachedText != null && var5 == CpsModule.this.lastLeft && var6 == CpsModule.this.lastRight && var7 == CpsModule.this.lastOptions) {
            return CpsModule.this.cachedText;
         } else {
            String var8 = var2 ? "  " : "";
            String var9 = var3 ? " " : "";
            String var10 = Integer.toString(var5);
            String var11 = var2 ? Integer.toString(var6) : "";
            String var12 = var3 ? com.cheatbreaker.client.kazerui.module.modules.hud.CpsModule.n() : "";
            CpsModule.this.cachedText = var4 ? var12 + var9 + var10 + var8 + var11 : var10 + var8 + var11 + var9 + var12;
            CpsModule.this.lastLeft = var5;
            CpsModule.this.lastRight = var6;
            CpsModule.this.lastOptions = var7;
            return CpsModule.this.cachedText;
         }
      }

      @Override
      protected void a(float var1, float var2, boolean var3, boolean var4) {
         if (var4 && CpsModule.this.rightClick.b()) {
            boolean var5 = CpsModule.this.ignoreCancelledClicks.b();
            float var6 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(this.c(var3));
            float var7 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(com.cheatbreaker.client.kazerui.cps.CpsTracker.a(var5) + " ");
            float var8;
            if (CpsModule.this.reverseText.b()) {
               float var9 = CpsModule.this.showCPSText.b()
                  ? com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(com.cheatbreaker.client.kazerui.module.modules.hud.CpsModule.n() + " ")
                  : 0.0F;
               var8 = var1 + this.f() / 2.04F - var6 / 2.0F + var9 + var7;
            } else {
               var8 = var1 + this.f() / 2.04F - var6 / 2.0F + var7;
            }

            if (this.textShadow.b()) {
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var8 + 0.25F, var2 + this.g() / 2.0F - 4.0F, 1.0F, 9.0F, 1862270976);
            }

            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var8 - 0.5F, var2 + this.g() / 2.0F - 4.5F, 1.0F, 9.0F, CpsModule.this.lineColor.a(var8 + var2));
         }
      }
   }
}
