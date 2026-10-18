package com.cheatbreaker.client.kazerui.module.modules.hud;

public final class StopwatchTimer {
   private long accumulated;
   private long started;
   private boolean running;

   public long a(long var1) {
      return this.accumulated + (this.running ? Math.max(0L, var1 - this.started) : 0L);
   }

   public boolean a() {
      return this.running;
   }

   public void b(long var1) {
      if (this.running) {
         this.accumulated = this.a(var1);
         this.running = false;
      } else {
         this.started = var1;
         this.running = true;
      }
   }

   public void c(long var1) {
      if (this.running) {
         this.b(var1);
      }
   }

   public void d(long var1) {
      this.accumulated = 0L;
      this.started = var1;
      this.running = false;
   }

   public static String a(long var0, boolean var2) {
      long var3 = Math.max(0L, var0);
      long var5 = var3 / 1000L;
      long var7 = var5 / 3600L;
      long var9 = var5 / 60L % 60L;
      StringBuilder var11 = new StringBuilder(12);
      if (var7 > 0L) {
         var11.append(var7).append(':');
      }

      if (var9 < 10L) {
         var11.append('0');
      }

      var11.append(var9).append(':');
      if (var5 % 60L < 10L) {
         var11.append('0');
      }

      var11.append(var5 % 60L);
      if (var2) {
         var11.append('.').append(var3 / 100L % 10L);
      }

      return var11.toString();
   }
}
