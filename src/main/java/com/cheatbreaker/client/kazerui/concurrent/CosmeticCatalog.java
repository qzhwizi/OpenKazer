package com.cheatbreaker.client.kazerui.concurrent;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.logging.log4j.LogManager;

public final class CosmeticCatalog {
   private static List<com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry> all;
   private static Map<Integer, com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry> byId;

   private CosmeticCatalog() {
   }

   public static synchronized List<com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry> a() {
      if (all == null) {
         b();
      }

      return all;
   }

   public static com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry a(int var0) {
      a();
      return byId.get(var0);
   }

   private static void b() {
      ArrayList var0 = new ArrayList();
      HashMap var1 = new HashMap();
      com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var2 = new com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry(
         900001, "Kazer", "builtin:cape", com.cheatbreaker.client.kazerui.concurrent.CosmeticType.CLOAK, false, false, "", "", false, ""
      );
      var0.add(var2);
      var1.put(var2.id, var2);
      var2 = new com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry(
         900003, "Micet", "builtin:micet", com.cheatbreaker.client.kazerui.concurrent.CosmeticType.CLOAK, false, false, "", "", false, ""
      );
      var0.add(var2);
      var1.put(var2.id, var2);
      var2 = new com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry(
         900002, "Dragon Wings", "builtin:wings", com.cheatbreaker.client.kazerui.concurrent.CosmeticType.WINGS, false, false, "", "", false, ""
      );
      var0.add(var2);
      var1.put(var2.id, var2);

      try (InputStreamReader var3 = new InputStreamReader(
            com.cheatbreaker.client.kazerui.concurrent.CosmeticCatalog.class.getResourceAsStream("/assets/kazerui/cosmetics/cosmetics.json"), StandardCharsets.UTF_8
         )) {
         for (JsonElement var6 : new JsonParser().parse(var3).getAsJsonArray()) {
            JsonArray var7 = var6.getAsJsonArray();
            com.cheatbreaker.client.kazerui.concurrent.CosmeticType var8 = com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a(var7.get(3).getAsString());
            if (var8 != null && var8 != com.cheatbreaker.client.kazerui.concurrent.CosmeticType.ITEM && var8.parent != com.cheatbreaker.client.kazerui.concurrent.CosmeticType.ITEM) {
               com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var9 = new com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry(
                  var7.get(0).getAsInt(),
                  var7.get(1).getAsString(),
                  var7.get(2).getAsString(),
                  var8,
                  var7.get(4).getAsInt() != 0,
                  var7.get(5).getAsInt() != 0,
                  var7.get(6).getAsString(),
                  var7.get(7).getAsString(),
                  var7.get(8).getAsInt() != 0,
                  var7.get(9).getAsString()
               );
               if (!var9.name.toLowerCase(Locale.ROOT).contains("kazer")) {
                  var0.add(var9);
                  var1.put(var9.id, var9);
               }
            }
         }
      } catch (Exception var20) {
         LogManager.getLogger("Kazer/Cosmetics").error("Could not read the bundled cosmetics.json", var20);
      }

      all = Collections.unmodifiableList(var0);
      byId = var1;
   }

   public static int a(com.cheatbreaker.client.kazerui.concurrent.CosmeticType var0) {
      int var1 = 0;

      for (com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var3 : a()) {
         if (var0.a(var3.type)) {
            var1++;
         }
      }

      return var1;
   }
}
