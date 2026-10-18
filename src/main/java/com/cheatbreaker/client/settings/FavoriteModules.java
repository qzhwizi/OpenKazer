package com.cheatbreaker.client.settings;

import java.util.LinkedHashSet;
import java.util.Set;

public final class FavoriteModules {
   private final Set<String> names = new LinkedHashSet<>();

   public boolean a(String var1) {
      return this.names.contains(var1);
   }

   public void b(String var1) {
      if (!this.names.remove(var1)) {
         this.names.add(var1);
      }
   }

   public String a() {
      return String.join(",", this.names);
   }

   public void c(String var1) {
      this.names.clear();

      for (String var5 : var1.split(",")) {
         if (!var5.trim().isEmpty()) {
            this.names.add(var5.trim());
         }
      }
   }
}
