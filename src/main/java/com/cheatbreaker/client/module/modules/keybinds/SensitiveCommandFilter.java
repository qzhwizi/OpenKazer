package com.cheatbreaker.client.module.modules.keybinds;

public final class SensitiveCommandFilter {
   private SensitiveCommandFilter() {
   }

   public static boolean a(String var0) {
      if (var0 == null) {
         return false;
      } else {
         String var1 = var0.trim();
         String var2 = var1.toLowerCase();
         return var2.startsWith("/login ") || var2.startsWith("/register ");
      }
   }
}
