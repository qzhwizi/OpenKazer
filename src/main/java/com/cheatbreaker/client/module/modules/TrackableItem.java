package com.cheatbreaker.client.module.modules;

public enum TrackableItem {
   HEALING_POT("healing_pot"),
   MUSHROOM_SOUP("mushroom_soup"),
   ARROW("arrow"),
   GOLDEN_APPLE("golden_apple"),
   ENCHANTED_GOLDEN_APPLE("enchanted_golden_apple");

   private final String id;

   private TrackableItem(String var3) {
      this.id = var3;
   }

   public String a() {
      return this.id;
   }

   public static com.cheatbreaker.client.module.modules.TrackableItem a(String var0) {
      for (com.cheatbreaker.client.module.modules.TrackableItem var4 : values()) {
         if (var4.id.equalsIgnoreCase(var0)) {
            return var4;
         }
      }

      return null;
   }
}
