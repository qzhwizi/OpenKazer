package com.cheatbreaker.client.settings;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.settings.KeyBinding;

public final class KeybindRegistry {
   private final List<com.cheatbreaker.client.settings.KeybindRegistry.KeybindEntry> entries = new ArrayList<>();

   public void a(String var1, KeyBinding var2) {
      this.a(var1, var2, new String[0]);
   }

   public void a(String var1, KeyBinding var2, String... var3) {
      if (var1 != null && var2 != null) {
         String[] var4 = new String[var3.length + 1];
         var4[0] = var2.getKeyDescription();
         System.arraycopy(var3, 0, var4, 1, var3.length);
         this.entries.add(new com.cheatbreaker.client.settings.KeybindRegistry.KeybindEntry(var1, var2, var4));
      }
   }

   public boolean a(KeyBinding var1) {
      if (var1 == null) {
         return false;
      } else {
         for (com.cheatbreaker.client.settings.KeybindRegistry.KeybindEntry var3 : this.entries) {
            if (var3.binding == var1) {
               return true;
            }
         }

         return false;
      }
   }

   public KeyBinding a(String var1) {
      for (com.cheatbreaker.client.settings.KeybindRegistry.KeybindEntry var3 : this.entries) {
         if (var3.configName.equals(var1)) {
            return var3.binding;
         }
      }

      return null;
   }

   public void a(List<String> var1, List<String> var2) {
      for (com.cheatbreaker.client.settings.KeybindRegistry.KeybindEntry var4 : this.entries) {
         var4.binding.setKeyCode(com.cheatbreaker.client.settings.KeybindConfigHelper.a(var1, var2, var4.configName, var4.optionNames, var4.binding.getKeyCode()));
      }

      KeyBinding.resetKeyBindingArrayAndHash();
   }

   public List<String> a() {
      ArrayList var1 = new ArrayList(this.entries.size());

      for (com.cheatbreaker.client.settings.KeybindRegistry.KeybindEntry var3 : this.entries) {
         var1.add(com.cheatbreaker.client.settings.KeybindConfigHelper.a(var3.configName, var3.binding.getKeyCode()));
      }

      return var1;
   }

   private static final class KeybindEntry {
      private final String configName;
      private final KeyBinding binding;
      private final String[] optionNames;

      private KeybindEntry(String var1, KeyBinding var2, String[] var3) {
         this.configName = var1;
         this.binding = var2;
         this.optionNames = var3;
      }
   }
}
