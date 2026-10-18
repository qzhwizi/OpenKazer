package com.cheatbreaker.client.gui.render.font;

import java.util.ArrayList;
import java.util.List;

public final class UnicodeTextSplitter {
   private UnicodeTextSplitter() {
   }

   public static boolean a(char var0) {
      return var0 >= 256 && !Character.isSurrogate(var0);
   }

   public static List<com.cheatbreaker.client.gui.render.font.UnicodeTextSplitter.a> a(String var0) {
      ArrayList var1 = new ArrayList();
      if (var0 != null && !var0.isEmpty()) {
         StringBuilder var2 = new StringBuilder();
         boolean var3 = a(var0.charAt(0));

         for (int var4 = 0; var4 < var0.length(); var4++) {
            char var5 = var0.charAt(var4);
            boolean var6 = a(var5);
            if (var6 != var3 && var2.length() > 0) {
               var1.add(new com.cheatbreaker.client.gui.render.font.UnicodeTextSplitter.a(var2.toString(), var3));
               var2.setLength(0);
               var3 = var6;
            }

            var2.append(var5);
         }

         if (var2.length() > 0) {
            var1.add(new com.cheatbreaker.client.gui.render.font.UnicodeTextSplitter.a(var2.toString(), var3));
         }

         return var1;
      } else {
         return var1;
      }
   }

   public static final class a {
      public final String text;
      public final boolean unicode;

      a(String var1, boolean var2) {
         this.text = var1;
         this.unicode = var2;
      }
   }
}
