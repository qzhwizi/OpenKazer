package com.cheatbreaker.client.settings;

import java.util.List;

final class KeybindConfigHelper {
   private static final String SUFFIX = " Key";
   private static final String OPTIONS_PREFIX = "key_";

   private KeybindConfigHelper() {
   }

   static int a(List<String> var0, String var1, int var2) {
      String var3 = var1 + " Key";

      for (String var5 : var0) {
         String[] var6 = var5.split("=", 2);
         if (var6.length == 2 && var3.equalsIgnoreCase(var6[0].trim())) {
            return b(var6[1], var2);
         }
      }

      return var2;
   }

   static String a(String var0, int var1) {
      return var0 + " Key" + "=" + var1;
   }

   static int a(List<String> var0, List<String> var1, String var2, String[] var3, int var4) {
      int var5 = var4;

      for (String var9 : var3) {
         int var10 = b(var1, var9, Integer.MIN_VALUE);
         if (var10 != Integer.MIN_VALUE) {
            var5 = var10;
            break;
         }
      }

      return a(var0, var2, var5);
   }

   private static int b(List<String> var0, String var1, int var2) {
      String var3 = "key_" + var1;

      for (String var5 : var0) {
         int var6 = var5.indexOf(58);
         if (var6 > 0 && var3.equals(var5.substring(0, var6))) {
            return b(var5.substring(var6 + 1), var2);
         }
      }

      return var2;
   }

   private static int b(String var0, int var1) {
      try {
         return Integer.parseInt(var0.trim());
      } catch (NumberFormatException var3) {
         return var1;
      }
   }
}
