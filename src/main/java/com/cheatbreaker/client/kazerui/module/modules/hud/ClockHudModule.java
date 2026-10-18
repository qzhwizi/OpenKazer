package com.cheatbreaker.client.kazerui.module.modules.hud;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class ClockHudModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private final SimpleDateFormat hourMinute = new SimpleDateFormat("h:mm", Locale.ENGLISH);
   private final SimpleDateFormat hourMinuteAmPm = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
   private final SimpleDateFormat military = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting militaryTime = this.a("militaryTime", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showAmPm = this.a("showAmPm", true).a(this::n);
   private final Date date = new Date();
   private String text;
   private long second = -1L;

   public ClockHudModule() {
      super("CLOCK", false);
      this.a(new com.cheatbreaker.client.kazerui.module.modules.hud.ClockHudModule.a());
      this.militaryTime.a(this::n);
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a("settings", var1x -> {
         var1x.a(this.militaryTime);
         var1x.a(this.showAmPm).a(this.militaryTime::b);
      });
   }

   private void n() {
      this.date.setTime(System.currentTimeMillis());
      this.text = this.militaryTime.b()
         ? this.military.format(this.date)
         : (this.showAmPm.b() ? this.hourMinuteAmPm.format(this.date) : this.hourMinute.format(this.date));
   }

   @Override
   public void a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a var1) {
      if (var1.phase == com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b.END && this.a()) {
         long var2 = System.currentTimeMillis() / 1000L;
         if (var2 != this.second) {
            this.second = var2;
            this.n();
         }
      }
   }

   private final class a extends com.cheatbreaker.client.kazerui.module.modulerender.RichTextHudBase {
      a() {
         super(ClockHudModule.this, 0.0F, 0.0F, com.cheatbreaker.client.kazerui.hud.HudAnchor.TOP_RIGHT, a(10, 18, 22, 46, 56, 62));
      }

      @Override
      protected String c(boolean var1) {
         return ClockHudModule.this.text == null ? "" : ClockHudModule.this.text;
      }
   }
}
