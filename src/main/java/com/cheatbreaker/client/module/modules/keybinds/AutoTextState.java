package com.cheatbreaker.client.module.modules.keybinds;

import java.util.ArrayList;
import java.util.List;

public final class AutoTextState {
   public static final int MAX_ITEMS = 32;
   public final List<com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry> items = new ArrayList<>();
   private int nextId = 1;

   public com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry a() {
      if (this.items.size() >= 32) {
         return null;
      } else {
         com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry var1 = new com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry();
         var1.id = "ath" + this.nextId++;
         this.items.add(var1);
         return var1;
      }
   }

   public boolean a(String var1) {
      com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry var2 = this.b(var1);
      return var2 != null && this.items.remove(var2);
   }

   public com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry b(String var1) {
      for (com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry var3 : this.items) {
         if (var3.id.equals(var1)) {
            return var3;
         }
      }

      return null;
   }

   public void a(String var1, int var2) {
      com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry var3 = this.b(var1);
      if (var3 != null) {
         this.items.remove(var3);
         int var4 = Math.max(0, Math.min(var2, this.items.size()));
         this.items.add(var4, var3);
      }
   }

   int b() {
      return this.nextId;
   }

   void a(int var1) {
      this.nextId = var1;
   }
}
