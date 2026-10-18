package com.cheatbreaker.client.module.modules.keybinds;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class HotkeyTrigger {
   private final Set<String> wasFiredUp = new HashSet<>();

   public List<com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry> a(List<com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry> var1, Set<Integer> var2) {
      ArrayList var3 = new ArrayList();
      HashSet var4 = new HashSet();

      for (com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry var6 : var1) {
         if (!var6.keys.isEmpty()) {
            boolean var7 = true;

            for (Integer var9 : var6.keys) {
               if (!var2.contains(var9)) {
                  var7 = false;
                  break;
               }
            }

            if (var7) {
               if (!this.wasFiredUp.contains(var6.id)) {
                  var3.add(var6);
               }

               var4.add(var6.id);
            }
         }
      }

      this.wasFiredUp.clear();
      this.wasFiredUp.addAll(var4);
      return var3;
   }
}
