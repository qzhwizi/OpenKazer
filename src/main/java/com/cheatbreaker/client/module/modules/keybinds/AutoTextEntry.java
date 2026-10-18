package com.cheatbreaker.client.module.modules.keybinds;

import java.util.LinkedList;
import java.util.List;

public final class AutoTextEntry {
   public String id;
   public String content = "Text";
   public boolean enabled = true;
   public boolean hidden = false;
   public final List<Integer> keys = new LinkedList<>();

   public void a(String var1) {
      this.content = b(var1);
   }

   static String b(String var0) {
      return var0 != null && !var0.isEmpty() ? var0.replace("\r", "").replace("\n", "") : "Text";
   }

   @Override
   public String toString() {
      return this.id + "(" + this.content + ")";
   }
}
