package com.cheatbreaker.client.kazerui.module.modules.mechanic;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

final class ChatTextUtils {
   private static final Pattern CODE = Pattern.compile("(?i)§[0-9A-FK-OR]");

   private ChatTextUtils() {
   }

   static String a(IChatComponent var0) {
      return com.cheatbreaker.client.kazerui.module.HypixelUtils.a(var0.getUnformattedText());
   }

   static boolean a(IChatComponent var0, String... var1) {
      String var2 = a(var0);

      for (String var6 : var1) {
         if (var6 != null && !var6.isEmpty() && var2.contains(var6)) {
            return true;
         }
      }

      return false;
   }

   static boolean b(IChatComponent var0, String... var1) {
      String var2 = a(var0);

      for (String var6 : var1) {
         if (var6 != null && !var6.isEmpty()) {
            for (int var7 = var2.indexOf(var6); var7 >= 0; var7 = var2.indexOf(var6, var7 + 1)) {
               if ((var7 == 0 || !a(var2.charAt(var7 - 1))) && (var7 + var6.length() == var2.length() || !a(var2.charAt(var7 + var6.length())))) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   private static boolean a(char var0) {
      return var0 == '_' || var0 >= '0' && var0 <= '9' || var0 >= 'A' && var0 <= 'Z' || var0 >= 'a' && var0 <= 'z';
   }

   static IChatComponent a(IChatComponent var0, Pattern var1, com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTextUtils.a var2) {
      if (!var1.matcher(a(var0)).find()) {
         return var0;
      } else {
         boolean[] var3 = new boolean[]{false};
         IChatComponent var4 = a(var0, var1, var2, var3);
         return var3[0] ? var4 : var0;
      }
   }

   private static IChatComponent a(IChatComponent var0, Pattern var1, com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTextUtils.a var2, boolean[] var3) {
      IChatComponent var4;
      if (var0 instanceof ChatComponentText) {
         String var5 = ((ChatComponentText)var0).getChatComponentText_TextValue();
         var4 = a(var5, var0.getChatStyle(), var1, var2, var3);
      } else {
         var4 = var0.createCopy();
         var4.getSiblings().clear();
      }

      for (Object var6 : var0.getSiblings()) {
         var4.appendSibling(a((IChatComponent)var6, var1, var2, var3));
      }

      return var4;
   }

   private static IChatComponent a(String var0, ChatStyle var1, Pattern var2, com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTextUtils.a var3, boolean[] var4) {
      StringBuilder var5 = new StringBuilder();
      ArrayList var6 = new ArrayList();

      for (int var7 = 0; var7 < var0.length(); var7++) {
         char var8 = var0.charAt(var7);
         if (var8 == 167 && var7 + 1 < var0.length()) {
            var7++;
         } else {
            var5.append(var8);
            var6.add(var7);
         }
      }

      Matcher var15 = var2.matcher(var5);
      ArrayList var16 = new ArrayList();

      while (var15.find()) {
         if (var15.end() > var15.start()) {
            var16.add(new int[]{(Integer)var6.get(var15.start()), (Integer)var6.get(var15.end() - 1) + 1, var15.start(), var15.end()});
         }
      }

      ChatComponentText var9 = new ChatComponentText(var16.isEmpty() ? var0 : "");
      var9.setChatStyle(var1.createShallowCopy());
      if (var16.isEmpty()) {
         return var9;
      } else {
         var4[0] = true;
         int var10 = 0;

         for (int[] var12 : (Iterable<int[]>) var16) {
            String var13 = var0.substring(var10, var12[0]);
            String var14 = a(var0.substring(0, var12[0]));
            if (!var13.isEmpty()) {
               var9.appendSibling(new ChatComponentText(a(var0.substring(0, var10)) + var13));
            }

            var9.appendSibling(var3.apply(var5.substring(var12[2], var12[3]), var14));
            var10 = var12[1];
         }

         if (var10 < var0.length()) {
            var9.appendSibling(new ChatComponentText(a(var0.substring(0, var10)) + var0.substring(var10)));
         }

         return var9;
      }
   }

   static String a(String var0) {
      StringBuilder var1 = new StringBuilder();
      Matcher var2 = CODE.matcher(var0);

      while (var2.find()) {
         char var3 = Character.toLowerCase(var2.group().charAt(1));
         if ("0123456789abcdef".indexOf(var3) >= 0 || var3 == 'r') {
            var1.setLength(0);
         }

         if (var3 != 'r') {
            var1.append(var2.group());
         }
      }

      return var1.toString();
   }

   static ChatStyle b(String var0) {
      ChatStyle var1 = new ChatStyle();
      Matcher var2 = CODE.matcher(var0);

      while (var2.find()) {
         char var3 = Character.toLowerCase(var2.group().charAt(1));
         int var4 = "0123456789abcdef".indexOf(var3);
         if (var4 >= 0) {
            var1 = new ChatStyle();
            var1.setColor(EnumChatFormatting.values()[var4]);
         } else if (var3 == 'l') {
            var1.setBold(true);
         } else if (var3 == 'o') {
            var1.setItalic(true);
         } else if (var3 == 'n') {
            var1.setUnderlined(true);
         } else if (var3 == 'm') {
            var1.setStrikethrough(true);
         } else if (var3 == 'k') {
            var1.setObfuscated(true);
         }
      }

      return var1;
   }

   static IChatComponent a(IChatComponent var0, Pattern var1, String var2) {
      return a(var0, var1, (var1x, var2x) -> {
         StringBuilder var3 = new StringBuilder();

         for (int var4 = 0; var4 < var1x.length(); var4++) {
            var3.append(var2);
         }

         return new ChatComponentText(var2x + var3);
      });
   }

   static IChatComponent a(String var0, Function<ChatStyle, ChatStyle> var1) {
      ChatComponentText var2 = new ChatComponentText(var0);
      var2.setChatStyle((ChatStyle)var1.apply(new ChatStyle()));
      return var2;
   }

   interface a {
      IChatComponent apply(String var1, String var2);
   }
}
