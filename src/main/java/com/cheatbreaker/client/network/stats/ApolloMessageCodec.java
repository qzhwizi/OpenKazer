package com.cheatbreaker.client.network.stats;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public final class ApolloMessageCodec {
   public static final String CHANNEL = "lunar:apollo";
   public static final String JSON_CHANNEL = "apollo:json";
   private static final String PREFIX = "type.googleapis.com/lunarclient.apollo.";

   private ApolloMessageCodec() {
   }

   public static byte[] a() {
      byte[] var0 = new ApolloProtobufMessage.b().a(1, "V1_8").a();
      byte[] var1 = new ApolloProtobufMessage.b().a(1, "kazer").a(3, "0.1.39").a();
      return a("player.v1.PlayerHandshakeMessage", new ApolloProtobufMessage.b().a(1, var0).a(2, var1).a(4, 1L).a());
   }

   static byte[] a(String var0, byte[] var1) {
      return new ApolloProtobufMessage.b().a(1, "type.googleapis.com/lunarclient.apollo." + var0).a(2, var1).a();
   }

   public static List<com.cheatbreaker.client.network.stats.ApolloMessageCodec.a> b(String var0, byte[] var1) {
      if (var1 != null && var1.length <= 32767) {
         JsonObject var2;
         if ("apollo:json".equals(var0)) {
            var2 = a(new String(var1, StandardCharsets.UTF_8)).getAsJsonObject();
         } else {
            if (!"lunar:apollo".equals(var0)) {
               return Collections.emptyList();
            }

            com.cheatbreaker.client.network.stats.ApolloProtobufMessage var3 = new com.cheatbreaker.client.network.stats.ApolloProtobufMessage(var1);
            var2 = c(var3.g(1), var3.f(2));
            if (var2 == null) {
               return Collections.emptyList();
            }
         }

         String var21 = a(var2, "@type", "");
         if (!var21.startsWith("type.googleapis.com/lunarclient.apollo.")) {
            return Collections.emptyList();
         } else {
            String var4 = var21.substring("type.googleapis.com/lunarclient.apollo.".length());
            com.cheatbreaker.client.network.stats.ApolloMessageCodec.a var5 = new com.cheatbreaker.client.network.stats.ApolloMessageCodec.a();
            switch (var4) {
               case "cosmetic.v1.EquipNpcCosmeticsMessage":
               case "cosmetic.v1.UnequipNpcCosmeticsMessage":
               case "cosmetic.v1.ResetNpcCosmeticsMessage":
                  var5.player = a(a(var2, "npc_uuid"));
                  if (var5.player.getLeastSignificantBits() != 0L) {
                     throw new IllegalArgumentException("Cosmetic target is not an NPC");
                  }

                  var5.kind = var4.contains("Unequip")
                     ? com.cheatbreaker.client.network.stats.ApolloMessageCodec.bInner.NPC_UNEQUIP
                     : (var4.contains("Reset") ? com.cheatbreaker.client.network.stats.ApolloMessageCodec.bInner.NPC_RESET : com.cheatbreaker.client.network.stats.ApolloMessageCodec.bInner.NPC_EQUIP);
                  var5.copyLocalCosmetics = a(var2, "copy_local_cosmetics") != null && a(var2, "copy_local_cosmetics").getAsBoolean();
                  var5.cosmeticIds = new ArrayList<>();
                  JsonElement var8 = a(var2, var5.kind == com.cheatbreaker.client.network.stats.ApolloMessageCodec.bInner.NPC_UNEQUIP ? "cosmetic_ids" : "cosmetics");
                  int var9 = 0;
                  if (var8 != null) {
                     for (JsonElement var24 : var8.getAsJsonArray()) {
                        if (++var9 > 8) {
                           throw new IllegalArgumentException("Too many NPC cosmetics");
                        }

                        if (!var24.isJsonObject() || var24.getAsJsonObject().entrySet().size() == 1) {
                           String var26 = (var24.isJsonObject() ? var24.getAsJsonObject().get("id") : var24).getAsString();
                           if (!var26.matches("[0-9]{1,10}")) {
                              throw new IllegalArgumentException("Invalid cosmetic ID");
                           }

                           long var27 = Long.parseLong(var26);
                           if (var27 <= 0L || var27 > 2147483647L) {
                              throw new IllegalArgumentException("Invalid cosmetic ID");
                           }

                           var5.cosmeticIds.add((int)var27);
                        }
                     }
                  }
                  break;
               case "nametag.v1.OverrideNametagMessage":
                  var5.kind = com.cheatbreaker.client.network.stats.ApolloMessageCodec.bInner.NAMETAG;
                  var5.player = a(a(var2, "player_uuid"));
                  var5.lines = new ArrayList<>();
                  JsonElement var10 = a(var2, "adventure_json_lines");
                  if (var10 != null) {
                     for (JsonElement var25 : var10.getAsJsonArray()) {
                        if (var5.lines.size() >= 64) {
                           throw new IllegalArgumentException("Too many nametag lines");
                        }

                        var5.lines.add(com.cheatbreaker.client.network.stats.LegacyChatSerializer.a(var25.getAsString()));
                     }
                  }
                  break;
               case "nametag.v1.ResetNametagMessage":
                  var5.kind = com.cheatbreaker.client.network.stats.ApolloMessageCodec.bInner.RESET_NAMETAG;
                  var5.player = a(a(var2, "player_uuid"));
                  break;
               case "nametag.v1.ResetNametagsMessage":
                  var5.kind = com.cheatbreaker.client.network.stats.ApolloMessageCodec.bInner.RESET_NAMETAGS;
                  break;
               case "title.v1.DisplayTitleMessage":
                  var5.kind = com.cheatbreaker.client.network.stats.ApolloMessageCodec.bInner.TITLE;
                  JsonElement var11 = a(var2, "title_type");
                  String var12 = var11 == null ? "0" : var11.getAsString();
                  var5.titleType = var12.equals("TITLE_TYPE_TITLE") ? 1 : (var12.equals("TITLE_TYPE_SUBTITLE") ? 2 : Integer.parseInt(var12));
                  if (var5.titleType != 1 && var5.titleType != 2) {
                     return Collections.emptyList();
                  }

                  var5.text = com.cheatbreaker.client.network.stats.LegacyChatSerializer.a(a(var2, "adventure_json_message", "\"\""));
                  var5.scale = b(var2, "scale");
                  var5.interpolationScale = b(var2, "interpolation_scale");
                  var5.interpolationRate = b(var2, "interpolation_rate");
                  var5.fadeIn = b(a(var2, "fade_in_time"));
                  var5.display = b(a(var2, "display_time"));
                  var5.fadeOut = b(a(var2, "fade_out_time"));
                  break;
               case "title.v1.ResetTitlesMessage":
                  var5.kind = com.cheatbreaker.client.network.stats.ApolloMessageCodec.bInner.RESET_TITLES;
                  break;
               case "configurable.v1.OverrideConfigurableSettingsMessage":
                  ArrayList var13 = new ArrayList();
                  JsonElement var14 = a(var2, "configurable_settings");
                  if (var14 != null) {
                     for (JsonElement var16 : var14.getAsJsonArray()) {
                        JsonObject var17 = var16.getAsJsonObject();
                        String var18 = a(var17, "apollo_module", "");
                        if (var18.equals("title") || var18.equals("nametag") || var18.equals("cosmetic")) {
                           com.cheatbreaker.client.network.stats.ApolloMessageCodec.a var19 = new com.cheatbreaker.client.network.stats.ApolloMessageCodec.a();
                           var19.kind = com.cheatbreaker.client.network.stats.ApolloMessageCodec.bInner.CONFIG;
                           var19.module = var18;
                           var19.enabled = a(var17, "enable") != null && a(var17, "enable").getAsBoolean();
                           JsonElement var20 = a(var17, "properties");
                           var19.clearOnSwitch = var20 != null
                              && var20.isJsonObject()
                              && var20.getAsJsonObject().has("clear-title-on-server-switch")
                              && var20.getAsJsonObject().get("clear-title-on-server-switch").getAsBoolean();
                           var13.add(var19);
                        }
                     }
                  }

                  return var13;
               default:
                  return Collections.emptyList();
            }

            return Collections.singletonList(var5);
         }
      } else {
         throw new IllegalArgumentException("Invalid Apollo payload");
      }
   }

   static JsonElement a(String var0) {
      int var1 = 0;
      boolean var2 = false;
      boolean var3 = false;

      for (int var4 = 0; var4 < var0.length(); var4++) {
         char var5 = var0.charAt(var4);
         if (var3) {
            var3 = false;
         } else if (var2 && var5 == '\\') {
            var3 = true;
         } else {
            if (var5 == '"') {
               var2 = !var2;
            }

            if (!var2 && (var5 == '{' || var5 == '[')) {
               if (++var1 > 40) {
                  throw new IllegalArgumentException("JSON nesting limit");
               }
            }

            if (!var2 && (var5 == '}' || var5 == ']')) {
               var1--;
            }
         }
      }

      return new JsonParser().parse(var0);
   }

   private static JsonElement a(JsonObject var0, String var1) {
      if (var0.has(var1)) {
         return var0.get(var1);
      } else {
         StringBuilder var2 = new StringBuilder();
         boolean var3 = false;

         for (char var7 : var1.toCharArray()) {
            if (var7 == '_') {
               var3 = true;
            } else {
               var2.append(var3 ? Character.toUpperCase(var7) : var7);
               var3 = false;
            }
         }

         return var0.get(var2.toString());
      }
   }

   private static String a(JsonObject var0, String var1, String var2) {
      JsonElement var3 = a(var0, var1);
      return var3 == null ? var2 : var3.getAsString();
   }

   private static UUID a(JsonElement var0) {
      if (var0 != null && var0.isJsonObject()) {
         JsonObject var1 = var0.getAsJsonObject();
         return new UUID(Long.parseUnsignedLong(a(var1, "high64", "0")), Long.parseUnsignedLong(a(var1, "low64", "0")));
      } else {
         throw new IllegalArgumentException("Missing player UUID");
      }
   }

   private static float b(JsonObject var0, String var1) {
      JsonElement var2 = a(var0, var1);
      float var3 = var2 == null ? 0.0F : var2.getAsFloat();
      if (!Float.isNaN(var3) && !Float.isInfinite(var3) && !(var3 < 0.0F) && !(var3 > 100.0F)) {
         return var3;
      } else {
         throw new IllegalArgumentException("Invalid title scale");
      }
   }

   private static long b(JsonElement var0) {
      if (var0 == null) {
         return 0L;
      } else {
         double var1;
         if (var0.isJsonObject()) {
            JsonObject var3 = var0.getAsJsonObject();
            var1 = Double.parseDouble(a(var3, "seconds", "0")) * 1000.0 + Double.parseDouble(a(var3, "nanos", "0")) / 1000000.0;
         } else {
            String var4 = var0.getAsString();
            if (!var4.endsWith("s")) {
               throw new IllegalArgumentException("Invalid Duration");
            }

            var1 = Double.parseDouble(var4.substring(0, var4.length() - 1)) * 1000.0;
         }

         if (!Double.isNaN(var1) && !Double.isInfinite(var1) && !(var1 < 0.0) && !(var1 > 8.64E7)) {
            return Math.round(var1);
         } else {
            throw new IllegalArgumentException("Invalid title duration");
         }
      }
   }

   private static JsonObject c(String var0, byte[] var1) {
      if (!var0.startsWith("type.googleapis.com/lunarclient.apollo.")) {
         return null;
      } else {
         String var2 = var0.substring("type.googleapis.com/lunarclient.apollo.".length());
         if (!var2.equals("nametag.v1.OverrideNametagMessage")
            && !var2.equals("nametag.v1.ResetNametagMessage")
            && !var2.equals("nametag.v1.ResetNametagsMessage")
            && !var2.equals("title.v1.DisplayTitleMessage")
            && !var2.equals("title.v1.ResetTitlesMessage")
            && !var2.equals("configurable.v1.OverrideConfigurableSettingsMessage")
            && !var2.equals("cosmetic.v1.EquipNpcCosmeticsMessage")
            && !var2.equals("cosmetic.v1.UnequipNpcCosmeticsMessage")
            && !var2.equals("cosmetic.v1.ResetNpcCosmeticsMessage")) {
            return null;
         } else {
            com.cheatbreaker.client.network.stats.ApolloProtobufMessage var3 = new com.cheatbreaker.client.network.stats.ApolloProtobufMessage(var1);
            JsonObject var4 = new JsonObject();
            var4.addProperty("@type", var0);
            if (var2.startsWith("cosmetic.")) {
               if (!var3.b(1)) {
                  throw new IllegalArgumentException("Missing NPC UUID");
               }

               com.cheatbreaker.client.network.stats.ApolloProtobufMessage var5 = var3.h(1);
               JsonObject var6 = new JsonObject();
               var6.addProperty("high64", Long.toUnsignedString(var5.d(1)));
               var6.addProperty("low64", Long.toUnsignedString(var5.d(2)));
               var4.add("npc_uuid", var6);
               JsonArray var7 = new JsonArray();
               if (var2.contains("EquipNpc")) {
                  for (Object var9 : var3.a(2)) {
                     com.cheatbreaker.client.network.stats.ApolloProtobufMessage var10 = new com.cheatbreaker.client.network.stats.ApolloProtobufMessage((byte[])var9);
                     JsonObject var11 = new JsonObject();
                     var11.addProperty("id", var10.d(1));
                     if (var10.b(2) || var10.b(3) || var10.b(4) || var10.b(5)) {
                        var11.addProperty("unsupported_options", true);
                     }

                     var7.add(var11);
                  }

                  var4.add("cosmetics", var7);
                  var4.addProperty("copy_local_cosmetics", var3.d(3) != 0L);
               } else if (var2.contains("UnequipNpc")) {
                  for (Long var28 : var3.c(2)) {
                     var7.add(new JsonPrimitive(var28));
                  }

                  var4.add("cosmetic_ids", var7);
               }
            } else if (var2.startsWith("nametag.") && !var2.endsWith("ResetNametagsMessage")) {
               if (!var3.b(1)) {
                  throw new IllegalArgumentException("Missing player UUID");
               }

               com.cheatbreaker.client.network.stats.ApolloProtobufMessage var16 = var3.h(1);
               JsonObject var19 = new JsonObject();
               var19.addProperty("high64", Long.toUnsignedString(var16.d(1)));
               var19.addProperty("low64", Long.toUnsignedString(var16.d(2)));
               var4.add("player_uuid", var19);
               JsonArray var22 = new JsonArray();
               if (var3.b(3)) {
                  for (Object var31 : var3.a(3)) {
                     var22.add(new JsonPrimitive(new String((byte[])var31, StandardCharsets.UTF_8)));
                  }
               } else {
                  for (Object var30 : var3.a(2)) {
                     var22.add(new JsonPrimitive(a(new com.cheatbreaker.client.network.stats.ApolloProtobufMessage((byte[])var30), 0).toString()));
                  }
               }

               var4.add("adventure_json_lines", var22);
            } else if (var2.equals("title.v1.DisplayTitleMessage")) {
               var4.addProperty("title_type", var3.d(1));
               var4.addProperty("scale", var3.e(3));
               var4.addProperty("adventure_json_message", var3.b(7) ? var3.g(7) : a(var3.h(2), 0).toString());
               var4.addProperty("interpolation_scale", var3.e(8));
               var4.addProperty("interpolation_rate", var3.e(9));
               String[] var14 = new String[]{"fade_in_time", "display_time", "fade_out_time"};

               for (int var17 = 0; var17 < var14.length; var17++) {
                  com.cheatbreaker.client.network.stats.ApolloProtobufMessage var20 = var3.h(4 + var17);
                  JsonObject var24 = new JsonObject();
                  var24.addProperty("seconds", var20.d(1));
                  var24.addProperty("nanos", var20.d(2));
                  var4.add(var14[var17], var24);
               }
            } else if (var2.equals("configurable.v1.OverrideConfigurableSettingsMessage")) {
               JsonArray var15 = new JsonArray();

               for (Object var21 : var3.a(1)) {
                  com.cheatbreaker.client.network.stats.ApolloProtobufMessage var25 = new com.cheatbreaker.client.network.stats.ApolloProtobufMessage((byte[])var21);
                  JsonObject var29 = new JsonObject();
                  var29.addProperty("apollo_module", var25.g(2));
                  var29.addProperty("enable", var25.d(3) != 0L);
                  JsonObject var32 = new JsonObject();

                  for (Object var12 : var25.h(4).a(1)) {
                     com.cheatbreaker.client.network.stats.ApolloProtobufMessage var13 = new com.cheatbreaker.client.network.stats.ApolloProtobufMessage((byte[])var12);
                     if (var13.g(1).equals("clear-title-on-server-switch")) {
                        var32.addProperty(var13.g(1), var13.h(2).d(4) != 0L);
                     }
                  }

                  var29.add("properties", var32);
                  var15.add(var29);
               }

               var4.add("configurable_settings", var15);
            }

            return var4;
         }
      }
   }

   private static JsonObject a(com.cheatbreaker.client.network.stats.ApolloProtobufMessage var0, int var1) {
      if (var1 > 32) {
         throw new IllegalArgumentException("Component nesting limit");
      } else {
         JsonObject var2 = new JsonObject();
         var2.addProperty("text", var0.g(1));
         if (var0.b(2)) {
            var2.addProperty("color", String.format("#%06x", var0.h(2).d(1) & 16777215L));
         }

         String[] var3 = new String[]{"", "obfuscated", "bold", "strikethrough", "underlined", "italic"};

         for (Object var5 : var0.a(3)) {
            if (var5 instanceof Long) {
               int var12 = ((Long)var5).intValue();
               if (var12 > 0 && var12 < var3.length) {
                  var2.addProperty(var3[var12], true);
               }
            } else {
               for (byte var9 : (byte[])var5) {
                  if (var9 > 0 && var9 < var3.length) {
                     var2.addProperty(var3[var9], true);
                  }
               }
            }
         }

         JsonArray var10 = new JsonArray();

         for (Object var13 : var0.a(4)) {
            var10.add(a(new com.cheatbreaker.client.network.stats.ApolloProtobufMessage((byte[])var13), var1 + 1));
         }

         var2.add("extra", var10);
         return var2;
      }
   }

   public static final class a {
      public com.cheatbreaker.client.network.stats.ApolloMessageCodec.bInner kind;
      public UUID player;
      public List<String> lines;
      public String text;
      public String module;
      public int titleType;
      public float scale;
      public float interpolationScale;
      public float interpolationRate;
      public long fadeIn;
      public long display;
      public long fadeOut;
      public boolean enabled;
      public boolean clearOnSwitch;
      public boolean copyLocalCosmetics;
      public List<Integer> cosmeticIds;
   }

   public static enum bInner {
      NAMETAG,
      RESET_NAMETAG,
      RESET_NAMETAGS,
      TITLE,
      RESET_TITLES,
      CONFIG,
      NPC_EQUIP,
      NPC_UNEQUIP,
      NPC_RESET;
   }
}
