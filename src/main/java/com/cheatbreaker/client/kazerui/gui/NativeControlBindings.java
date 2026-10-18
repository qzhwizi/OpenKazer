package com.cheatbreaker.client.kazerui.gui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public final class NativeControlBindings {
   public static KeyBinding[] visible(KeyBinding[] var0) {
      ArrayList var1 = new ArrayList();

      for (KeyBinding var5 : var0) {
         String var6 = var5.getKeyCategory().replace(" ", "").toLowerCase(Locale.ROOT);
         if (!var6.equals("kazerclient") && !var6.equals("cheatbreaker") && !var6.equals("key.categories.kazer") && !var6.equals("key.categories.cheatbreaker")
            )
          {
            com.cheatbreaker.client.CheatBreakerClient var7 = com.cheatbreaker.client.CheatBreakerClient.N();
            if (var7 == null || var7.globalSettings == null || !var7.globalSettings.keyBindings.a(var5)) {
               var1.add(var5);
            }
         }
      }

      return (KeyBinding[]) var1.toArray(new KeyBinding[var1.size()]);
   }

   private NativeControlBindings() {
   }

   public static Set<String> categories() {
      HashSet var0 = new HashSet();

      for (KeyBinding var4 : visible(Minecraft.getMinecraft().gameSettings.keyBindings)) {
         var0.add(var4.getKeyCategory());
      }

      return var0;
   }
}
