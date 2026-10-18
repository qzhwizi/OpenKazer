package com.cheatbreaker.client.gui.serverlist;

public final class ServerAddressValidator {
   private ServerAddressValidator() {
   }

   public static boolean a(String var0) {
      if (var0 == null) {
         return false;
      } else {
         String var1 = var0.trim();
         int var2 = var1.indexOf(58);
         if (var2 >= 0) {
            try {
               int var3 = Integer.parseInt(var1.substring(var2 + 1));
               if (var3 < 1 || var3 > 65535) {
                  return false;
               }
            } catch (NumberFormatException var4) {
               return false;
            }

            var1 = var1.substring(0, var2);
         }

         if (var1.endsWith(".")) {
            var1 = var1.substring(0, var1.length() - 1);
         }

         return var1.equalsIgnoreCase("micet.cc");
      }
   }
}
