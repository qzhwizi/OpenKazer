package com.cheatbreaker.client.module.modules;

import java.util.ArrayList;
import java.util.List;

public class ItemDisplayConfig {
   public int version = 1;
   public List<com.cheatbreaker.client.module.modules.ItemDisplayEntry> children = a();

   public static List<com.cheatbreaker.client.module.modules.ItemDisplayEntry> a() {
      ArrayList var0 = new ArrayList();

      for (com.cheatbreaker.client.module.modules.TrackableItem var4 : com.cheatbreaker.client.module.modules.TrackableItem.values()) {
         var0.add(new com.cheatbreaker.client.module.modules.ItemDisplayEntry(var4));
      }

      return var0;
   }
}
