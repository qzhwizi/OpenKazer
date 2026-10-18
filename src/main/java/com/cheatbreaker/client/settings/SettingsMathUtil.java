package com.cheatbreaker.client.settings;

public final class SettingsMathUtil {
   private SettingsMathUtil() {
   }

   public static boolean a(int var0, int var1) {
      return Math.max(0, Math.min(100, var0)) > Math.floorMod(var1, 100);
   }

   public static double a(int var0) {
      return var0 <= 0 ? Double.POSITIVE_INFINITY : (double)var0 * var0;
   }

   public static int a(int var0, boolean var1, boolean var2, int var3) {
      return !var1 && var2 ? Math.min(var0, Math.max(5, var3)) : var0;
   }
}
