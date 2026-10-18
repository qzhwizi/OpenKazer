package com.cheatbreaker.client.kazerui.concurrent.rendercache;

public final class AnimationClock {
   private boolean initialized;
   private boolean advanced;
   private double source;
   private double time;
   private double delta;

   public double a(double var1) {
      this.advanced = false;
      this.delta = 0.0;
      if (!Double.isFinite(var1)) {
         return this.time;
      } else if (!this.initialized) {
         this.initialized = true;
         this.source = var1;
         this.advanced = true;
         return this.time;
      } else {
         double var3 = var1 - this.source;
         if (var3 < -0.25) {
            this.source = var1;
            return this.time;
         } else if (var3 <= 0.0) {
            return this.time;
         } else {
            this.source = var1;
            this.delta = Math.min(var3, 0.1);
            this.time = this.time + this.delta;
            this.advanced = true;
            return this.time;
         }
      }
   }

   public boolean a() {
      return this.advanced;
   }

   public double b() {
      return this.delta;
   }

   public double c() {
      return this.time;
   }

   public static double a(double var0, double var2) {
      return var2 < 0.0 ? var0 : 1.0 - Math.pow(1.0 - Math.max(0.0, Math.min(1.0, var0)), Math.min(0.1, Math.max(0.0, var2)) * 60.0);
   }
}
