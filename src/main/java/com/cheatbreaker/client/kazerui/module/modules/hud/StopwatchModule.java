package com.cheatbreaker.client.kazerui.module.modules.hud;

public final class StopwatchModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private final com.cheatbreaker.client.kazerui.module.modules.hud.StopwatchTimer clock = new com.cheatbreaker.client.kazerui.module.modules.hud.StopwatchTimer();
   private final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.hud.StopwatchModule.b> mode = this.a(
      "timerMode", com.cheatbreaker.client.kazerui.module.modules.hud.StopwatchModule.b.STOPWATCH
   );
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting seconds = this.a("countdownSeconds", 60, 1, 86400);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting tenths = this.a("showTenths", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting pauseInMenu = this.a("pauseInMenu", false);
   private final com.cheatbreaker.client.kazerui.module.settings.KeybindSetting startKey = this.a("startPauseKey");
   private final com.cheatbreaker.client.kazerui.module.settings.KeybindSetting resetKey = this.a("resetKey");
   private final com.cheatbreaker.client.kazerui.module.settings.ButtonSetting start = this.a(
      new com.cheatbreaker.client.kazerui.module.settings.ButtonSetting("startPause", () -> this.clock.b(System.nanoTime())).a(100.0F)
   );
   private final com.cheatbreaker.client.kazerui.module.settings.ButtonSetting reset = this.a(
      new com.cheatbreaker.client.kazerui.module.settings.ButtonSetting("resetTimer", () -> this.clock.d(System.nanoTime())).a(90.0F)
   );
   private String displayed = "00:00.0";
   private long displayedUnit = Long.MIN_VALUE;
   private String profile;

   public StopwatchModule() {
      super("STOPWATCH", false);
      this.n();
      this.mode.a(() -> this.clock.d(System.nanoTime()));
      this.a(new com.cheatbreaker.client.kazerui.module.modules.hud.StopwatchModule.a());
   }

   private void n() {
      for (com.cheatbreaker.client.kazerui.module.settings.AbstractSetting var2 : this.g()) {
         String var3 = var2.key;
         switch (var3) {
            case "timerMode":
               var2.a(() -> "Timer Mode");
               break;
            case "countdownSeconds":
               var2.a(() -> "Countdown Seconds");
               break;
            case "showTenths":
               var2.a(() -> "Show Tenths");
               break;
            case "pauseInMenu":
               var2.a(() -> "Pause in Menus");
               break;
            case "startPauseKey":
               var2.a(() -> "Start / Pause Key");
               break;
            case "resetKey":
               var2.a(() -> "Reset Key");
               break;
            case "startPause":
               var2.a(() -> "Start / Pause");
               break;
            case "resetTimer":
               var2.a(() -> "Reset Timer");
         }
      }
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(this.mode, this.tenths, this.pauseInMenu);
      var1.a(this.seconds).a(() -> this.mode.j() != com.cheatbreaker.client.kazerui.module.modules.hud.StopwatchModule.b.COUNTDOWN);
      var1.a(this.startKey, this.resetKey, this.start, this.reset);
   }

   @Override
   protected void f() {
      this.clock.c(System.nanoTime());
   }

   @Override
   public void a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a var1) {
      if (var1.phase == com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b.END) {
         boolean var2 = this.a() && m().theWorld != null && m().currentScreen == null;
         boolean var3 = this.startKey.a(var2);
         boolean var4 = this.resetKey.a(var2);
         long var5 = System.nanoTime();
         String var7 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.d().a();
         if (!var7.equals(this.profile)) {
            this.clock.d(var5);
            this.displayedUnit = Long.MIN_VALUE;
            this.profile = var7;
         }

         if (!this.a()) {
            this.clock.c(var5);
         } else {
            if (m().theWorld == null) {
               this.clock.c(var5);
            }

            if (var3) {
               this.clock.b(var5);
            }

            if (var4) {
               this.clock.d(var5);
            }

            if (this.pauseInMenu.b() && m().currentScreen != null) {
               this.clock.c(var5);
            }

            long var8 = this.clock.a(var5) / 1000000L;
            long var10 = this.mode.j() == com.cheatbreaker.client.kazerui.module.modules.hud.StopwatchModule.b.COUNTDOWN
               ? Math.max(0L, this.seconds.c() * 1000L - var8)
               : var8;
            if (this.mode.j() == com.cheatbreaker.client.kazerui.module.modules.hud.StopwatchModule.b.COUNTDOWN && var10 == 0L) {
               this.clock.c(var5);
            }

            long var12 = this.tenths.b() ? var10 / 100L : var10 / 1000L;
            long var14 = var12 * 2L + (this.tenths.b() ? 1 : 0);
            if (var14 != this.displayedUnit) {
               this.displayedUnit = var14;
               this.displayed = com.cheatbreaker.client.kazerui.module.modules.hud.StopwatchTimer.a(var10, this.tenths.b());
            }
         }
      }
   }

   private final class a extends com.cheatbreaker.client.kazerui.module.modulerender.RichTextHudBase {
      a() {
         super(StopwatchModule.this, 0.0F, 40.0F, com.cheatbreaker.client.kazerui.hud.HudAnchor.TOP_RIGHT, a(9, 18, 30, 40, 72, 110));
      }

      @Override
      protected String c(boolean var1) {
         return var1 ? "01:23.4" : StopwatchModule.this.displayed;
      }

      @Override
      protected Boolean b(String var1) {
         return false;
      }

      @Override
      protected Boolean n() {
         return false;
      }
   }

   public static enum b implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
      STOPWATCH,
      COUNTDOWN;

      @Override
      public String a() {
         return null;
      }
   }
}
