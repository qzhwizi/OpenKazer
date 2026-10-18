package com.cheatbreaker.client.module.modules.options;

public final class ArmourStatusUtil {
   private ArmourStatusUtil() {
   }

   public static com.cheatbreaker.client.module.modules.options.ArmourStatusUtil.aInner a(String var0, int var1, int var2, int var3) {
      String var4 = var0 == null ? "" : var0.trim().toUpperCase();
      if (var4.equals("LEFT BOTTOM")) {
         var4 = "BL";
      } else if (var4.equals("RIGHT BOTTOM")) {
         var4 = "BR";
      } else if (var4.equals("RIGHT TOP")) {
         var4 = "TR";
      } else if (var4.equals("LEFT TOP")) {
         var4 = "TL";
      }

      boolean var5 = var4.equals("BR") || var4.equals("TR");
      boolean var6 = var4.equals("TR") || var4.equals("TL");
      int var7 = var5 ? var1 - var3 - 10 : 10;
      int var8 = var6 ? 10 : var2 - 16;
      return new com.cheatbreaker.client.module.modules.options.ArmourStatusUtil.aInner(var7, var8);
   }

   public static double a(int var0, int var1, int var2) {
      double var3 = var0 * 0.04;
      var1 = Math.min(var1, 25);
      double var5 = a(var1);
      double var7 = var3 + (1.0 - var3) * var5 * 0.04;
      var7 += (1.0 - var7) * var2 * 0.2;
      var7 = Math.min(var7, 1.0);
      return a(var7 * 100.0);
   }

   private static double a(int var0) {
      double var1 = 0.0;

      for (int var3 = 50; var3 <= 100; var3++) {
         var1 += Math.min(Math.ceil(var0 * var3 / 100.0), 20.0);
      }

      return var1 / 51.0;
   }

   private static double a(double var0) {
      return var0 == 0.0 ? var0 : (int)(var0 * 100.0) / 100.0;
   }

   public static String a(double var0, double var2, boolean var4, boolean var5) {
      if (!var4 && !var5) {
         return null;
      } else if (var0 == 0.0 && var2 == 0.0) {
         return null;
      } else if (var4 && !var5) {
         return var0 + "%";
      } else if (!var4) {
         return var2 + "%";
      } else {
         return var0 == var2 ? var0 + "%" : var0 + "% | " + var2 + "%";
      }
   }

   public static final class aInner {
      public final int x;
      public final int y;

      aInner(int var1, int var2) {
         this.x = var1;
         this.y = var2;
      }
   }
}
