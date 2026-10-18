package com.cheatbreaker.client.module.modules.keybinds;

import org.lwjgl.input.Keyboard;

public final class KeyNameFormatter {
   private KeyNameFormatter() {
   }

   public static String a(int var0) {
      return var0 < 0 ? "BUTTON " + (var0 + 101) : Keyboard.getKeyName(var0).toUpperCase();
   }

   public static String a(int[] var0) {
      StringBuilder var1 = new StringBuilder();

      for (int var5 : var0) {
         if (var1.length() > 0) {
            var1.append('+');
         }

         var1.append(a(var5));
      }

      return var1.toString();
   }

   public static String a(int[] var0, boolean var1) {
      if (var1) {
         return "...";
      } else {
         return var0.length == 0 ? "BIND" : a(var0);
      }
   }
}
