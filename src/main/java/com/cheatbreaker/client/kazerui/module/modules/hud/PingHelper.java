package com.cheatbreaker.client.kazerui.module.modules.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;

final class PingHelper {
   static int a() {
      Minecraft var0 = Minecraft.getMinecraft();
      if (var0.thePlayer != null && var0.getNetHandler() != null) {
         NetworkPlayerInfo var1 = var0.getNetHandler().getPlayerInfo(var0.thePlayer.getUniqueID());
         return var1 == null ? -1 : var1.getResponseTime();
      } else {
         return -1;
      }
   }

   private PingHelper() {
   }
}
