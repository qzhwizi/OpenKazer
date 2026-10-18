package com.cheatbreaker.client.module.modules.nametag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class NametagLine {
   public final int left;
   public final int right;
   public final int textX;
   public final int badgeX;
   public static final int BADGE_SIZE = 9;
   public static final int LINE_HEIGHT = 12;
   public static final float WORLD_SCALE = 0.025F;
   public static final float INVENTORY_SCALE = 0.025F;
   public static final float BACKGROUND_ALPHA = 0.25F;

   public static float a(int var0) {
      return var0 < 3 ? 0.35F : 0.2F;
   }

   public static float b(int var0) {
      int var1 = Math.max(1, var0 - 1) * 12 + 9;
      return Math.min(0.025F, 0.7F / var1);
   }

   public NametagLine(int var1, boolean var2) {
      int var3 = var2 ? 11 : 0;
      int var4 = Math.max(0, var1) + var3;
      this.left = -var4 / 2;
      this.right = this.left + var4;
      this.badgeX = this.left;
      this.textX = this.left + var3;
   }

   public static float a(float var0) {
      return Math.max(0.0F, Math.min(1.0F, (Math.abs(var0) - 45.0F) / 45.0F)) * 20.0F;
   }

   public static List<String> a(List<String> var0, String var1, String var2) {
      if (var0 == null) {
         return new ArrayList<>(Collections.singletonList(var2));
      } else {
         ArrayList var3 = new ArrayList(var0);
         Collections.reverse(var3);
         if (!var3.isEmpty()) {
            int var4 = a(var3, var1);
            if (a((String)var3.get(var4), var1)) {
               if (var4 > 0) {
                  var3.add(0, var3.remove(var4));
               }

               for (int var5 = 1; var5 < var3.size(); var5++) {
                  if ("[Mod Mode]".equalsIgnoreCase(a((String)var3.get(var5)).trim())) {
                     if (var5 > 1) {
                        var3.add(1, var3.remove(var5));
                     }
                     break;
                  }
               }
            }
         }

         return var3;
      }
   }

   public static boolean a(String var0, String var1) {
      if (var0 != null && var1 != null && !var1.isEmpty()) {
         String var2 = a(var0);

         for (int var3 = 0; var3 <= var2.length() - var1.length(); var3++) {
            int var4 = var3 + var1.length();
            if (var2.regionMatches(true, var3, var1, 0, var1.length())
               && (var3 == 0 || !a(var2.charAt(var3 - 1)))
               && (var4 == var2.length() || !a(var2.charAt(var4)))) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private static boolean a(char var0) {
      return var0 >= 'a' && var0 <= 'z' || var0 >= 'A' && var0 <= 'Z' || var0 >= '0' && var0 <= '9' || var0 == '_';
   }

   private static String a(String var0) {
      if (var0.indexOf(167) < 0) {
         return var0;
      } else {
         StringBuilder var1 = new StringBuilder(var0.length());

         for (int var2 = 0; var2 < var0.length(); var2++) {
            if (var0.charAt(var2) == 167 && var2 + 1 < var0.length()) {
               var2++;
            } else {
               var1.append(var0.charAt(var2));
            }
         }

         return var1.toString();
      }
   }

   public static int a(List<String> var0, String var1) {
      for (int var2 = 0; var2 < var0.size(); var2++) {
         if (a((String)var0.get(var2)).trim().equalsIgnoreCase(var1)) {
            return var2;
         }
      }

      for (int var3 = 0; var3 < var0.size(); var3++) {
         if (a((String)var0.get(var3), var1)) {
            return var3;
         }
      }

      return Math.max(0, var0.size() - 1);
   }
}
