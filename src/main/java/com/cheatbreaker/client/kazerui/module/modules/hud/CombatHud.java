package com.cheatbreaker.client.kazerui.module.modules.hud;

import net.minecraft.entity.Entity;

public final class CombatHud {
   private static ComboModule combo;
   private static com.cheatbreaker.client.kazerui.module.modules.hud.ReachDisplayModule reach;

   public static void a(ComboModule var0, com.cheatbreaker.client.kazerui.module.modules.hud.ReachDisplayModule var1) {
      combo = var0;
      reach = var1;
   }

   public static void a(Entity var0) {
      if (combo != null) {
         combo.a(var0);
      }

      if (reach != null) {
         reach.a(var0);
      }

      com.cheatbreaker.client.kazerui.module.modules.hud.PvpInfoModule.a(var0);
   }

   public static void status(Entity var0, byte var1) {
      if (combo != null) {
         combo.a(var0, var1);
      }

      if (reach != null) {
         reach.a(var0, var1);
      }

      com.cheatbreaker.client.kazerui.module.modules.hud.PvpInfoModule.a(var0, var1);
   }

   private CombatHud() {
   }
}
