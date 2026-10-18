package com.cheatbreaker.client.network.stats;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public final class LegacyChatSerializer {
   private static final String[] NAMES = new String[]{
      "black",
      "dark_blue",
      "dark_green",
      "dark_aqua",
      "dark_red",
      "dark_purple",
      "gold",
      "gray",
      "dark_gray",
      "blue",
      "green",
      "aqua",
      "red",
      "light_purple",
      "yellow",
      "white"
   };
   private static final int[] RGB = new int[]{
      0, 170, 43520, 43690, 11141120, 11141290, 16755200, 11184810, 5592405, 5592575, 5635925, 5636095, 16733525, 16733695, 16777045, 16777215
   };
   private static final String[] FLAGS = new String[]{"obfuscated", "bold", "strikethrough", "underlined", "italic"};

   private LegacyChatSerializer() {
   }

   public static String a(String var0) {
      if (var0.length() > 8192) {
         throw new IllegalArgumentException("Oversized component");
      } else {
         StringBuilder var1 = new StringBuilder();
         a(com.cheatbreaker.client.network.stats.ApolloMessageCodec.a(var0), new com.cheatbreaker.client.network.stats.LegacyChatSerializer.aInner(), var1, 0);
         if (var1.length() > 8192) {
            throw new IllegalArgumentException("Oversized text");
         } else {
            return var1.toString();
         }
      }
   }

   static char b(String var0) {
      for (int var1 = 0; var1 < NAMES.length; var1++) {
         if (NAMES[var1].equalsIgnoreCase(var0)) {
            return "0123456789abcdef".charAt(var1);
         }
      }

      if (var0 != null && var0.matches("#[0-9a-fA-F]{6}")) {
         int var9 = Integer.parseInt(var0.substring(1), 16);
         int var2 = 15;
         int var3 = Integer.MAX_VALUE;

         for (int var4 = 0; var4 < RGB.length; var4++) {
            int var5 = (var9 >> 16 & 0xFF) - (RGB[var4] >> 16 & 0xFF);
            int var6 = (var9 >> 8 & 0xFF) - (RGB[var4] >> 8 & 0xFF);
            int var7 = (var9 & 0xFF) - (RGB[var4] & 0xFF);
            int var8 = var5 * var5 + var6 * var6 + var7 * var7;
            if (var8 < var3) {
               var3 = var8;
               var2 = var4;
            }
         }

         return "0123456789abcdef".charAt(var2);
      } else {
         return 'f';
      }
   }

   private static void a(JsonElement var0, com.cheatbreaker.client.network.stats.LegacyChatSerializer.aInner var1, StringBuilder var2, int var3) {
      if (var3 > 32 || var2.length() > 8192) {
         throw new IllegalArgumentException("Component nesting limit");
      } else if (var0 != null && !var0.isJsonNull()) {
         if (var0.isJsonArray()) {
            JsonArray var9 = var0.getAsJsonArray();
            com.cheatbreaker.client.network.stats.LegacyChatSerializer.aInner var10 = var1;

            for (int var11 = 0; var11 < var9.size(); var11++) {
               JsonElement var13 = var9.get(var11);
               a(var13, var10, var2, var3 + 1);
               if (var11 == 0 && var13.isJsonObject()) {
                  var10 = var1.a(var13.getAsJsonObject());
               }
            }
         } else {
            com.cheatbreaker.client.network.stats.LegacyChatSerializer.aInner var4 = var1;
            String var5 = "";
            JsonObject var6 = null;
            if (var0.isJsonPrimitive()) {
               var5 = var0.getAsString();
            } else {
               var6 = var0.getAsJsonObject();
               var4 = var1.a(var6);
               if (var6.has("text")) {
                  var5 = var6.get("text").getAsString();
               } else if (var6.has("translate")) {
                  var5 = var6.has("fallback") ? var6.get("fallback").getAsString() : var6.get("translate").getAsString();
               } else if (var6.has("keybind")) {
                  var5 = var6.get("keybind").getAsString();
               } else if (var6.has("score") && var6.getAsJsonObject("score").has("value")) {
                  var5 = var6.getAsJsonObject("score").get("value").getAsString();
               }
            }

            if (!var5.isEmpty()) {
               var2.append('§').append('r').append('§').append(var4.color);

               for (int var7 = 0; var7 < var4.flags.length; var7++) {
                  if (var4.flags[var7]) {
                     var2.append('§').append("klmno".charAt(var7));
                  }
               }

               var2.append(var5);
            }

            if (var6 != null && var6.has("extra")) {
               for (JsonElement var8 : var6.getAsJsonArray("extra")) {
                  a(var8, var4, var2, var3 + 1);
               }
            }
         }
      }
   }

   private static class aInner {
      char color = 'f';
      boolean[] flags = new boolean[5];

      private aInner() {
      }

      com.cheatbreaker.client.network.stats.LegacyChatSerializer.aInner a(JsonObject var1) {
         com.cheatbreaker.client.network.stats.LegacyChatSerializer.aInner var2 = new com.cheatbreaker.client.network.stats.LegacyChatSerializer.aInner();
         var2.color = var1.has("color") ? com.cheatbreaker.client.network.stats.LegacyChatSerializer.b(var1.get("color").getAsString()) : this.color;
         var2.flags = (boolean[])this.flags.clone();

         for (int var3 = 0; var3 < com.cheatbreaker.client.network.stats.LegacyChatSerializer.FLAGS.length; var3++) {
            if (var1.has(com.cheatbreaker.client.network.stats.LegacyChatSerializer.FLAGS[var3])) {
               var2.flags[var3] = var1.get(com.cheatbreaker.client.network.stats.LegacyChatSerializer.FLAGS[var3]).getAsBoolean();
            }
         }

         return var2;
      }
   }
}
