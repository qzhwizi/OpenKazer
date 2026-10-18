package com.cheatbreaker.client.module.modules.keybinds;

import java.util.ArrayList;
import java.util.List;

public final class AutoTextSerializer {
   private AutoTextSerializer() {
   }

   public static String a(com.cheatbreaker.client.module.modules.keybinds.AutoTextState var0) {
      StringBuilder var1 = new StringBuilder();

      for (com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry var3 : var0.items) {
         var1.append(b(var3.id))
            .append("~")
            .append(b(var3.content))
            .append("~")
            .append(var3.enabled ? 1 : 0)
            .append("~")
            .append(var3.hidden ? 1 : 0)
            .append("~")
            .append(a(var3.keys))
            .append("|");
      }

      var1.append("next=").append(var0.b());
      return var1.toString();
   }

   public static com.cheatbreaker.client.module.modules.keybinds.AutoTextState a(String var0) {
      com.cheatbreaker.client.module.modules.keybinds.AutoTextState var1 = new com.cheatbreaker.client.module.modules.keybinds.AutoTextState();
      if (var0 != null && !var0.isEmpty()) {
         for (String var3 : a(var0, '|')) {
            if (var3.startsWith("next=")) {
               var1.a(a(var3.substring(5), 1));
            } else {
               List var4 = a(var3, '~');
               if (var4.size() >= 2 && var1.items.size() < 32) {
                  com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry var5 = var1.a();
                  if (var5 != null) {
                     var5.id = c((String)var4.get(0));
                     var5.a(c((String)var4.get(1)));
                     if (var4.size() > 2) {
                        var5.enabled = !"0".equals(var4.get(2));
                     }

                     if (var4.size() > 3) {
                        var5.hidden = "1".equals(var4.get(3));
                     }

                     if (var4.size() > 4) {
                        a((String)var4.get(4), var5.keys);
                     }
                  }
               }
            }
         }

         return var1;
      } else {
         return var1;
      }
   }

   private static String a(List<Integer> var0) {
      StringBuilder var1 = new StringBuilder();

      for (Integer var3 : var0) {
         if (var1.length() > 0) {
            var1.append(',');
         }

         var1.append(var3.intValue());
      }

      return var1.toString();
   }

   static void a(String var0, List<Integer> var1) {
      if (var0 != null && !var0.isEmpty()) {
         ArrayList var2 = new ArrayList();

         for (String var6 : var0.split(",")) {
            try {
               var2.add(Integer.parseInt(var6));
            } catch (NumberFormatException var8) {
               return;
            }
         }

         var1.addAll(var2);
      }
   }

   private static int a(String var0, int var1) {
      try {
         return Integer.parseInt(var0);
      } catch (NumberFormatException var3) {
         return var1;
      }
   }

   private static String b(String var0) {
      StringBuilder var1 = new StringBuilder();

      for (int var2 = 0; var2 < var0.length(); var2++) {
         char var3 = var0.charAt(var2);
         if (var3 == '\\' || var3 == '~' || var3 == '|') {
            var1.append('\\');
         }

         var1.append(var3);
      }

      return var1.toString();
   }

   private static String c(String var0) {
      StringBuilder var1 = new StringBuilder();
      boolean var2 = false;

      for (int var3 = 0; var3 < var0.length(); var3++) {
         char var4 = var0.charAt(var3);
         if (var2) {
            var1.append(var4);
            var2 = false;
         } else if (var4 == '\\') {
            var2 = true;
         } else {
            var1.append(var4);
         }
      }

      return var1.toString();
   }

   private static List<String> a(String var0, char var1) {
      ArrayList var2 = new ArrayList();
      StringBuilder var3 = new StringBuilder();
      boolean var4 = false;

      for (int var5 = 0; var5 < var0.length(); var5++) {
         char var6 = var0.charAt(var5);
         if (var4) {
            var3.append(var6);
            var4 = false;
         } else if (var6 == '\\') {
            var3.append(var6);
            var4 = true;
         } else if (var6 == var1) {
            var2.add(var3.toString());
            var3.setLength(0);
         } else {
            var3.append(var6);
         }
      }

      var2.add(var3.toString());
      return var2;
   }
}
