package com.cheatbreaker.client.module.modules.animation;

public final class SwingTracker {
   public static final long SWING_WINDOW_MS = 300L;
   public static final long DEBOUNCE_MS = 200L;
   public static final double MIN_DISTANCE = 3.0;
   public static final double DEFAULT_DISTANCE = 4.0;
   public static final double MAX_DISTANCE = 6.0;
   public static final double MIN_FACING_DOT = 0.5;
   private static final long NEVER = Long.MIN_VALUE;
   private long lastSwingTime = Long.MIN_VALUE;
   private long lastCueTime = Long.MIN_VALUE;

   public void a(double var1, double var3, float var5, double var6, double var8, long var10, double var12) {
      double var14 = var6 - var1;
      double var16 = var8 - var3;
      double var18 = var14 * var14 + var16 * var16;
      if (!(var18 > var12 * var12)) {
         double var20 = Math.toRadians(var5);
         double var22 = -Math.sin(var20);
         double var24 = Math.cos(var20);
         double var26 = var14 * var22 + var16 * var24;
         double var28 = var26 / Math.sqrt(var18);
         if (!(var18 > 0.0) || !(var28 < 0.5)) {
            this.lastSwingTime = var10;
         }
      }
   }

   public boolean a(boolean var1, double var2, double var4, long var6) {
      if (!var1) {
         return false;
      } else if (this.lastSwingTime == Long.MIN_VALUE || var6 - this.lastSwingTime > 300L) {
         return false;
      } else if (this.lastCueTime != Long.MIN_VALUE && var6 - this.lastCueTime < 200L) {
         return false;
      } else {
         this.lastCueTime = var6;
         return true;
      }
   }

   public void a() {
      this.lastSwingTime = Long.MIN_VALUE;
      this.lastCueTime = Long.MIN_VALUE;
   }

   public static double a(float var0) {
      double var1 = Math.max(3.0, Math.min(6.0, (double)var0));
      return Math.round(var1 * 10.0) / 10.0;
   }

   public static float a(int var0) {
      if (var0 <= 0) {
         return 0.0F;
      } else {
         return var0 >= 100 ? 1.0F : var0 / 100.0F;
      }
   }
}
