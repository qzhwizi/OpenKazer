package com.cheatbreaker.client.kazerui.gui.components;

public final class SliderMath {
   public static final float HEIGHT = 14.0F;
   private static final long DURATION = 500L;

   private SliderMath() {
   }

   public static float a(float var0) {
      return a(var0, 14.0F);
   }

   public static float a(float var0, float var1) {
      return var0 - var1 / 3.0F * 2.0F;
   }

   public static float b(float var0) {
      return b(var0, 14.0F);
   }

   public static float b(float var0, float var1) {
      return (var0 - a(var0, var1)) / 2.0F;
   }

   public static double a(float var0, float var1, float var2, float var3, float var4, boolean var5, boolean var6) {
      return a(var0, var1, var2, 14.0F, var3, var4, var5, var6);
   }

   public static double a(float var0, float var1, float var2, float var3, float var4, float var5, boolean var6, boolean var7) {
      float var8 = (var0 - var1 - b(var2, var3)) / a(var2, var3);
      double var9 = 0.01;
      double var11;
      if (var8 <= 0.01) {
         var11 = var4;
      } else if (var8 >= 0.99) {
         var11 = var5;
      } else {
         var11 = var4 + var8 * (var5 - var4);
         if (var7) {
            double var13 = Math.log10(var5 - var4) + 0.001;
            if (var13 < 0.0) {
               var13 = 0.0;
            }

            var9 = Math.pow(10.0, (int)var13) / 10.0;
         }
      }

      var11 = Math.round(var11 / var9) * var9;
      return var6 ? Math.round(var11) : (float)var11;
   }

   public static int[] a(float var0, float var1, boolean var2, int var3) {
      int var4 = 1;
      int var5 = 0;
      if (Math.round((var1 - var0) * 100.0F) % 100 == 0) {
         if (var3 >= 2) {
            var4 = Math.round(var1 - var0);
            var5 = var3;
         } else if (var2) {
            var4 = Math.round(var1 - var0);
         }
      }

      if (var4 > 20 || var4 < 1) {
         var4 = 1;
      }

      return new int[]{var4, var5};
   }

   public static final class a {
      double from;
      double to;
      long start;
      float fade;
      private long fadeTime;

      public a(double var1) {
         this.from = var1;
         this.to = var1;
      }

      float a(long var1) {
         if (this.start == 0L) {
            return 0.0F;
         } else if (this.start + 500L - var1 <= 0L) {
            return 1.0F;
         } else {
            float var3 = (float)(var1 - this.start) / 500.0F;
            return var3 < 0.5F ? 2.0F * var3 * var3 : -1.0F + (4.0F - 2.0F * var3) * var3;
         }
      }

      boolean b(long var1) {
         return this.start + 500L - var1 <= 0L;
      }

      void a(double var1, long var3) {
         float var5 = this.a(var3);
         float var6 = var5;
         if (this.b(var3)) {
            this.start = var3;
            this.from = this.to;
            this.to = var1;
         } else {
            if (var5 > 0.5F) {
               this.start = var3 - 250L;
               var5 = 0.5F;
            }

            double var7 = this.from + (this.to - this.from) * var6;
            this.from = (var7 - var1 * var5) / (1.0F - var5);
            this.to = var1;
         }
      }

      public float a(double var1, float var3, float var4, long var5) {
         if (var4 <= var3) {
            return 0.0F;
         } else {
            if (Math.abs(var1 - this.to) > 0.01F) {
               this.a(var1, var5);
            }

            float var7 = (float)((var1 - var3) / (var4 - var3));
            float var8 = (float)((this.from - var3) / (var4 - var3));
            return var8 - (var8 - var7) * this.a(var5);
         }
      }

      public boolean c(long var1) {
         return this.start != 0L && !this.b(var1);
      }

      public int a(boolean var1, long var2) {
         if (this.fadeTime != 0L) {
            this.fade = Math.max(0.0F, Math.min(255.0F, this.fade + (float)((var1 ? 1 : -1) * 40 * (var2 - this.fadeTime)) / 50.0F));
         }

         this.fadeTime = var2;
         return (int)this.fade;
      }
   }
}
