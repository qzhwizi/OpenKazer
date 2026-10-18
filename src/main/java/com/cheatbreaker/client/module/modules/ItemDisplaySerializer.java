package com.cheatbreaker.client.module.modules;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.HashMap;

public final class ItemDisplaySerializer {
   private static final Gson GSON = new Gson();

   private ItemDisplaySerializer() {
   }

   public static String a(com.cheatbreaker.client.module.modules.ItemDisplayConfig var0) {
      return GSON.toJson(var0 == null ? new com.cheatbreaker.client.module.modules.ItemDisplayConfig() : var0);
   }

   public static com.cheatbreaker.client.module.modules.ItemDisplayConfig a(String var0) {
      com.cheatbreaker.client.module.modules.ItemDisplayConfig var1 = new com.cheatbreaker.client.module.modules.ItemDisplayConfig();
      if (var0 != null && !var0.trim().isEmpty()) {
         try {
            JsonElement var2 = new JsonParser().parse(var0);
            if (!var2.isJsonObject()) {
               return var1;
            } else {
               JsonObject var3 = var2.getAsJsonObject();
               if (var3.has("version") && var3.get("version").getAsInt() == 1 && var3.has("children") && var3.get("children").isJsonArray()) {
                  com.cheatbreaker.client.module.modules.ItemDisplayConfig var4 = (com.cheatbreaker.client.module.modules.ItemDisplayConfig)GSON.fromJson(var3, com.cheatbreaker.client.module.modules.ItemDisplayConfig.class);
                  HashMap var5 = new HashMap();
                  if (var4.children != null) {
                     for (com.cheatbreaker.client.module.modules.ItemDisplayEntry var7 : var4.children) {
                        if (com.cheatbreaker.client.module.modules.TrackableItem.a(var7.id) != null) {
                           var5.put(var7.id.toLowerCase(), var7);
                        }
                     }
                  }

                  var1.children.clear();

                  for (com.cheatbreaker.client.module.modules.TrackableItem var9 : com.cheatbreaker.client.module.modules.TrackableItem.values()) {
                     com.cheatbreaker.client.module.modules.ItemDisplayEntry var10 = (com.cheatbreaker.client.module.modules.ItemDisplayEntry)var5.get(var9.a());
                     var1.children.add(var10 == null ? new com.cheatbreaker.client.module.modules.ItemDisplayEntry(var9) : a(var10, var9));
                  }

                  return var1;
               } else {
                  return var1;
               }
            }
         } catch (Exception var11) {
            return var1;
         }
      } else {
         return var1;
      }
   }

   private static com.cheatbreaker.client.module.modules.ItemDisplayEntry a(com.cheatbreaker.client.module.modules.ItemDisplayEntry var0, com.cheatbreaker.client.module.modules.TrackableItem var1) {
      var0.id = var1.a();
      if (var0.anchor == null) {
         var0.anchor = (new com.cheatbreaker.client.module.modules.ItemDisplayEntry(var1)).anchor;
      }

      if (var0.scale <= 0.0F || Float.isNaN(var0.scale) || Float.isInfinite(var0.scale)) {
         var0.scale = 1.0F;
      }

      com.cheatbreaker.client.module.modules.ItemDisplayMode var2 = com.cheatbreaker.client.module.modules.ItemLabelUtil.a(var1);
      if (var0.displayMode == null) {
         var0.displayMode = var2.name();
      } else {
         try {
            com.cheatbreaker.client.module.modules.ItemDisplayMode.valueOf(var0.displayMode);
         } catch (IllegalArgumentException var4) {
            var0.displayMode = var2.name();
         }
      }

      if (var0.textColor == null) {
         var0.textColor = -1;
      }

      if (var0.showBackground == null) {
         var0.showBackground = false;
      }

      if (var0.backgroundColor == null) {
         var0.backgroundColor = 1862270976;
      }

      return var0;
   }
}
