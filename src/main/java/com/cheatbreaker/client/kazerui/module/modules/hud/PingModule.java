package com.cheatbreaker.client.kazerui.module.modules.hud;

import java.util.Locale;

public final class PingModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting interval = this.a("updateIntervalSec", 1, 1, 120);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting averageSamples = this.a("averageSamples", 3, 2, 20);
   private final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.hud.PingModule.b> mode = this.a(
      "pingMode", com.cheatbreaker.client.kazerui.module.modules.hud.PingModule.b.LATEST
   );
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showMs = this.a("pingShowMs", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting prefix = this.a("showPingPrefix", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting dynamic = this.a("dynamicPingColor", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting low = this.a("lowPingNumberColor", -11141291);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting medium = this.a("mediumPingNumberColor", -171);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting high = this.a("highPingNumberColor", -43691);
   private final int[] samples = new int[20];
   private int cursor;
   private int count;
   private int ping = -1;
   private long nextSample;
   private String line = "Ping: ...";

   public PingModule() {
      super("PING", false);
      this.a(new com.cheatbreaker.client.kazerui.module.modules.hud.PingModule.a());
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(this.interval, this.mode, this.averageSamples, this.prefix, this.showMs, this.dynamic);
      var1.a("colorOptions", var1x -> var1x.a(this.low, this.medium, this.high).a(() -> !this.dynamic.b()));
   }

   @Override
   public void a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a var1) {
      if (var1.phase == com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b.END && this.a()) {
         long var2 = System.currentTimeMillis();
         if (var2 >= this.nextSample) {
            this.nextSample = var2 + this.interval.c() * 1000L;
            int var4 = com.cheatbreaker.client.kazerui.module.modules.hud.PingHelper.a();
            if (var4 < 0) {
               this.count = this.cursor = 0;
               this.ping = -1;
            } else {
               this.samples[this.cursor++ % this.samples.length] = var4;
               this.count = Math.min(this.count + 1, this.samples.length);
               this.ping = var4;
               if (this.mode.j() == com.cheatbreaker.client.kazerui.module.modules.hud.PingModule.b.AVERAGE) {
                  int var5 = Math.min(this.count, this.averageSamples.c());
                  int var6 = 0;

                  for (int var7 = 0; var7 < var5; var7++) {
                     var6 += this.samples[(this.cursor - 1 - var7 + this.samples.length) % this.samples.length];
                  }

                  this.ping = var6 / var5;
               }
            }

            this.line = (this.prefix.b() ? "Ping: " : "") + (this.ping < 0 ? "..." : Integer.toString(this.ping)) + (this.showMs.b() ? " ms" : "");
         }
      }
   }

   private final class a extends com.cheatbreaker.client.kazerui.module.modulerender.TextHudBase {
      a() {
         super(PingModule.this, 0.0F, 40.0F, com.cheatbreaker.client.kazerui.hud.HudAnchor.TOP_RIGHT, com.cheatbreaker.client.kazerui.module.modulerender.RichTextHudBase.a(10, 18, 32, 40, 72, 160));
      }

      @Override
      protected String c(boolean var1) {
         return var1 && PingModule.this.ping < 0 ? "Ping: 42 ms" : (com.cheatbreaker.client.kazerui.module.modules.hud.PingModule.m().theWorld == null ? "" : PingModule.this.line);
      }

      @Override
      protected com.cheatbreaker.client.kazerui.module.settings.ColorSetting m() {
         return !PingModule.this.dynamic.b() ? this.textColor : (PingModule.this.ping < 65 ? PingModule.this.low : (PingModule.this.ping < 120 ? PingModule.this.medium : PingModule.this.high));
      }
   }

   public static enum b implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
      LATEST,
      AVERAGE;

      @Override
      public String a() {
         return this.name().toLowerCase(Locale.ROOT);
      }
   }
}
