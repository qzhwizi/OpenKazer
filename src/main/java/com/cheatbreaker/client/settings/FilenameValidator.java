package com.cheatbreaker.client.settings;

import java.util.Locale;

public final class FilenameValidator {
   private static final String FORBIDDEN_CHARACTERS = "<>:\"/\\|?*";

   private FilenameValidator() {
   }

   public static boolean a(String var0) {
      if (var0 != null && var0.length() != 0 && !var0.equals(".") && !var0.equals("..") && !var0.endsWith(" ") && !var0.endsWith(".")) {
         for (int var1 = 0; var1 < var0.length(); var1++) {
            char var2 = var0.charAt(var1);
            if (var2 < ' ' || Character.isSurrogate(var2) || "<>:\"/\\|?*".indexOf(var2) >= 0) {
               return false;
            }
         }

         String var3 = var0;
         int var5 = var0.indexOf(46);
         if (var5 >= 0) {
            var3 = var0.substring(0, var5);
         }

         if (!var3.endsWith(" ") && !var3.endsWith(".")) {
            var3 = var3.toUpperCase(Locale.ROOT);
            return !var3.equals("CON") && !var3.equals("PRN") && !var3.equals("AUX") && !var3.equals("NUL") && !a(var3, "COM") && !a(var3, "LPT");
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private static boolean a(String var0, String var1) {
      if (var0.length() == 4 && var0.startsWith(var1)) {
         char var2 = var0.charAt(3);
         return var2 >= '1' && var2 <= '9' || var2 == 185 || var2 == 178 || var2 == 179;
      } else {
         return false;
      }
   }
}
