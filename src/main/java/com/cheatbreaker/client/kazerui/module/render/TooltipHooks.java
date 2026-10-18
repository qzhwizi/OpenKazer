package com.cheatbreaker.client.kazerui.module.render;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

public final class TooltipHooks {
   private static final List<TooltipHooks.a> COMPONENTS = new ArrayList<>();
   private static final char BASE = '\ue000';

   private TooltipHooks() {
   }

   public static void a(ItemStack var0, List<String> var1) {
      COMPONENTS.clear();
      com.cheatbreaker.client.kazerui.module.KazerModule var2 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.a("saturation");
      if (var2 instanceof com.cheatbreaker.client.kazerui.module.modules.hud.SaturationHudModule) {
         ((com.cheatbreaker.client.kazerui.module.modules.hud.SaturationHudModule)var2).a(var0, var1);
      }
   }

   public static void a(List<String> var0, int var1, int var2, int var3, TooltipHooks.a var4) {
      int var5 = COMPONENTS.size();
      if (var5 < 16) {
         COMPONENTS.add(var4);
         StringBuilder var6 = new StringBuilder();

         for (int var7 = 0; var7 < (var2 + 3) / 4; var7++) {
            var6.append(' ');
         }

         int var10 = Math.max(1, (var3 + 9) / 10);
         var1 = Math.min(var1, var0.size());
         var0.add(var1, var6 + "§" + (char)(57344 + var5));

         for (int var8 = 1; var8 < var10; var8++) {
            var0.add(var1 + var8, var6.toString());
         }
      }
   }

   public static int drawLine(Object var0, String var1, float var2, float var3, int var4) {
      int var5 = var1.lastIndexOf(167);
      if (var5 >= 0 && var5 + 1 < var1.length()) {
         int var6 = var1.charAt(var5 + 1) - '\ue000';
         if (var6 >= 0 && var6 < COMPONENTS.size()) {
            COMPONENTS.get(var6).draw((int)var2, (int)var3);
            return (int)var2;
         }
      }

      return ((FontRenderer)var0).drawStringWithShadow(var1, var2, var3, var4);
   }

   public interface a {
      void draw(int var1, int var2);
   }
}
