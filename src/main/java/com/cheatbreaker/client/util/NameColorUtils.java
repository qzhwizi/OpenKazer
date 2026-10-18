package com.cheatbreaker.client.util;

import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public final class NameColorUtils {
   public static final UUID CANRX_UUID = UUID.fromString("9df8fe7d-81c4-423e-aaa5-96921b9b017f");
   public static final int WHITE = 16777215;
   public static final int CANRX_DEFAULT = 11141120;
   private static final int[] MC = new int[]{
      0, 170, 43520, 43690, 11141120, 11141290, 16755200, 11184810, 5592405, 5592575, 5635925, 5636095, 16733525, 16733695, 16777045, 16777215
   };

   private NameColorUtils() {
   }

   public static int a(UUID var0) {
      return CANRX_UUID.equals(var0) ? 11141120 : 16777215;
   }

   public static Integer a(String var0) {
      if (var0 == null) {
         return null;
      } else {
         String var1 = var0.trim();
         if (var1.length() != 2 || var1.charAt(0) != '&' && var1.charAt(0) != 167) {
            if (var1.startsWith("#")) {
               var1 = var1.substring(1);
            }

            return !var1.matches("[0-9a-fA-F]{6}") ? null : Integer.parseInt(var1, 16);
         } else {
            int var2 = Character.digit(var1.charAt(1), 16);
            return var2 < 0 ? null : MC[var2];
         }
      }
   }

   public static String b(String var0) {
      if (var0 == null) {
         return null;
      } else {
         String var1 = var0.replace("-", "").trim().toLowerCase(Locale.ROOT);
         return var1.matches("[0-9a-f]{32}") ? var1 : null;
      }
   }

   public static void a(Map<String, Integer> var0, String var1) {
      String[] var2 = var1.split("\\t", -1);
      String var3 = b(var2[0]);
      if (var3 != null && var2.length <= 2) {
         UUID var4 = UUID.fromString(
            var3.substring(0, 8) + "-" + var3.substring(8, 12) + "-" + var3.substring(12, 16) + "-" + var3.substring(16, 20) + "-" + var3.substring(20)
         );
         Integer var5 = var2.length == 2 ? a(var2[1]) : a(var4);
         if (var5 != null) {
            var0.put(var3, var5);
         }
      }
   }
}
