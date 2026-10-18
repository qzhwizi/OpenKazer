package com.cheatbreaker.client.module.modules.config;

import java.util.ArrayList;
import java.util.List;

public final class CustomTextSerializer {
   private CustomTextSerializer() {
   }

   public static String a(com.cheatbreaker.client.module.modules.config.CustomTextRegistry var0) {
      StringBuilder var1 = new StringBuilder();

      for (com.cheatbreaker.client.module.modules.config.CustomTextItem var3 : var0.items) {
         var1.append(c(var3.id))
            .append("~")
            .append(c(var3.content))
            .append("~")
            .append(var3.color)
            .append("~")
            .append(var3.showBackground ? 1 : 0)
            .append("~")
            .append(var3.backgroundColor)
            .append("~")
            .append(var3.scale)
            .append("~")
            .append(c(var3.anchor))
            .append("~")
            .append(var3.xTranslation)
            .append("~")
            .append(var3.yTranslation)
            .append("~")
            .append(var3.enabled ? 1 : 0)
            .append("~")
            .append(var3.renderHud ? 1 : 0)
            .append("|");
      }

      var1.append("next=").append(var0.b());
      return var1.toString();
   }

   public static com.cheatbreaker.client.module.modules.config.CustomTextRegistry a(String var0) {
      com.cheatbreaker.client.module.modules.config.CustomTextRegistry var1 = new com.cheatbreaker.client.module.modules.config.CustomTextRegistry();
      if (var0 != null && !var0.isEmpty()) {
         for (String var3 : a(var0, '|')) {
            if (var3.startsWith("next=")) {
               var1.a(a(var3.substring(5), 1));
            } else {
               List var4 = a(var3, '~');
               if (var4.size() >= 2 && var1.items.size() < 32) {
                  com.cheatbreaker.client.module.modules.config.CustomTextItem var5 = var1.a();
                  if (var5 != null) {
                     var5.id = d((String)var4.get(0));
                     var5.a(d((String)var4.get(1)));
                     if (var4.size() > 2) {
                        var5.color = b((String)var4.get(2));
                     }

                     if (var4.size() > 3) {
                        var5.showBackground = "1".equals(var4.get(3));
                     }

                     if (var4.size() > 4) {
                        var5.backgroundColor = b((String)var4.get(4));
                     }

                     if (var4.size() > 5) {
                        var5.scale = a((String)var4.get(5), 1.0F);
                     }

                     if (var4.size() > 6) {
                        var5.anchor = d((String)var4.get(6));
                     }

                     if (var4.size() > 7) {
                        var5.xTranslation = a((String)var4.get(7), 0.0F);
                     }

                     if (var4.size() > 8) {
                        var5.yTranslation = a((String)var4.get(8), 0.0F);
                     }

                     if (var4.size() > 9) {
                        var5.enabled = !"0".equals(var4.get(9));
                     }

                     if (var4.size() > 10) {
                        var5.renderHud = !"0".equals(var4.get(10));
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

   private static int b(String var0) {
      try {
         return var0.startsWith("0x") ? (int)Long.parseLong(var0.substring(2), 16) : (int)Long.parseLong(var0);
      } catch (NumberFormatException var2) {
         return -1;
      }
   }

   private static int a(String var0, int var1) {
      try {
         return Integer.parseInt(var0);
      } catch (NumberFormatException var3) {
         return var1;
      }
   }

   private static float a(String var0, float var1) {
      try {
         return Float.parseFloat(var0);
      } catch (NumberFormatException var3) {
         return var1;
      }
   }

   private static String c(String var0) {
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

   private static String d(String var0) {
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
