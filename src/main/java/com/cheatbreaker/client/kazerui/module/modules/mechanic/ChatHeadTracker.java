package com.cheatbreaker.client.kazerui.module.modules.mechanic;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.util.ResourceLocation;

final class ChatHeadTracker {
   private static final Pattern NAME = Pattern.compile("[A-Za-z0-9_]{3,16}");
   private static final String SEPARATORS = "»›:>";
   private final LinkedHashMap<Integer, String> byMessage = new LinkedHashMap<>();

   void a() {
      this.byMessage.clear();
   }

   void a(com.cheatbreaker.client.kazerui.module.ChatMessageEvent var1) {
      String var2 = this.a(var1.plain);
      if (var2 != null) {
         this.byMessage.put(var1.id, var2);
         Iterator var3 = this.byMessage.keySet().iterator();

         while (this.byMessage.size() > 1200 && var3.hasNext()) {
            var3.next();
            var3.remove();
         }
      }
   }

   private String a(String var1) {
      Matcher var2 = NAME.matcher(var1);

      while (var2.find()) {
         if (var2.start() > 40) {
            return null;
         }

         if ((b(var1, var2) || a(var1, var2) != 0) && com.cheatbreaker.client.kazerui.module.modules.mechanic.PlayerSkinLookup.a(var2.group())) {
            return var2.group();
         }
      }

      return null;
   }

   ResourceLocation a(int var1) {
      String var2 = this.byMessage.get(var1);
      return var2 == null ? null : com.cheatbreaker.client.kazerui.module.modules.mechanic.PlayerSkinLookup.b(var2);
   }

   private static char a(String var0, Matcher var1) {
      int var2 = a(var0, var1.end());
      if (var2 < var0.length() && var0.charAt(var2) == '[') {
         int var3 = var0.indexOf(93, var2);
         if (var3 == -1) {
            return '\u0000';
         }

         var2 = a(var0, var3 + 1);
      }

      return var2 < var0.length() && "»›:>".indexOf(var0.charAt(var2)) != -1 ? var0.charAt(var2) : '\u0000';
   }

   private static boolean b(String var0, Matcher var1) {
      return var1.start() > 0 && var0.charAt(var1.start() - 1) == '<' && var1.end() < var0.length() && var0.charAt(var1.end()) == '>';
   }

   private static int a(String var0, int var1) {
      while (var1 < var0.length() && var0.charAt(var1) == ' ') {
         var1++;
      }

      return var1;
   }
}
