package com.cheatbreaker.client.kazerui.module.modules.hud;

public final class DayCounterModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting useWorldType = this.a("useWorldType", false);

   public DayCounterModule() {
      super("DAY_COUNTER", false);
      this.a(new com.cheatbreaker.client.kazerui.module.modules.hud.DayCounterModule.a());
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a("settings", var1x -> var1x.a(this.useWorldType));
   }

   private final class a extends com.cheatbreaker.client.kazerui.module.modulerender.RichTextHudBase {
      private long lastDay = Long.MIN_VALUE;
      private String cachedDay;

      a() {
         super(DayCounterModule.this, 0.0F, 0.0F, com.cheatbreaker.client.kazerui.hud.HudAnchor.TOP_CENTER, a(10, 18, 22, 46, 56, 62));
      }

      @Override
      protected Boolean b(String var1) {
         return Boolean.FALSE;
      }

      @Override
      protected String c(boolean var1) {
         if (com.cheatbreaker.client.kazerui.module.modules.hud.DayCounterModule.m().theWorld != null
            && com.cheatbreaker.client.kazerui.module.modules.hud.DayCounterModule.m().theWorld.getWorldInfo() != null) {
            long var2 = DayCounterModule.this.useWorldType.b()
               ? com.cheatbreaker.client.kazerui.module.modules.hud.DayCounterModule.m().theWorld.getWorldTime() / 24000L
               : com.cheatbreaker.client.kazerui.module.modules.hud.DayCounterModule.m().theWorld.getWorldInfo().getWorldTotalTime() / 24000L;
            if (var2 != this.lastDay || this.cachedDay == null) {
               this.lastDay = var2;
               this.cachedDay = DayCounterModule.this.a(var2 != 1L ? "days" : "day", new Object[]{var2});
            }

            return this.cachedDay;
         } else {
            return var1 ? DayCounterModule.this.a("days", new Object[]{21}) : null;
         }
      }
   }
}
