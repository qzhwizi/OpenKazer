package com.cheatbreaker.client.kazerui.module.modules.mechanic;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;

final class PlayerSkinLookup {
   static boolean a(String var0) {
      return Minecraft.getMinecraft().getNetHandler() != null && Minecraft.getMinecraft().getNetHandler().getPlayerInfo(var0) != null;
   }

   static ResourceLocation b(String var0) {
      if (Minecraft.getMinecraft().getNetHandler() == null) {
         return null;
      } else {
         NetworkPlayerInfo var1 = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(var0);
         return var1 == null ? null : var1.getLocationSkin();
      }
   }
}
