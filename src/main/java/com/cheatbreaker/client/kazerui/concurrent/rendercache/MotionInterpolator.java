package com.cheatbreaker.client.kazerui.concurrent.rendercache;

public final class MotionInterpolator {
   private int tick = Integer.MIN_VALUE;
   private double px;
   private double py;
   private double pz;
   private double x;
   private double y;
   private double z;

   public void a(int var1, double var2, double var4, double var6) {
      if (var1 != this.tick) {
         boolean var8 = this.tick == Integer.MIN_VALUE || var1 != this.tick + 1 || Math.abs(var2) > 160.0 || Math.abs(var4) > 160.0 || Math.abs(var6) > 160.0;
         this.px = var8 ? var2 : this.x;
         this.py = var8 ? var4 : this.y;
         this.pz = var8 ? var6 : this.z;
         this.x = var2;
         this.y = var4;
         this.z = var6;
         this.tick = var1;
      }
   }

   public double a(float var1) {
      return a(this.px, this.x, var1);
   }

   public double b(float var1) {
      return a(this.py, this.y, var1);
   }

   public double c(float var1) {
      return a(this.pz, this.z, var1);
   }

   private static double a(double var0, double var2, float var4) {
      return var0 + (var2 - var0) * Math.max(0.0F, Math.min(1.0F, var4));
   }

   public static float a(float var0, float var1, float var2) {
      float var3 = (var1 - var0) % 360.0F;
      if (var3 >= 180.0F) {
         var3 -= 360.0F;
      }

      if (var3 < -180.0F) {
         var3 += 360.0F;
      }

      return var0 + var3 * Math.max(0.0F, Math.min(1.0F, var2));
   }
}
