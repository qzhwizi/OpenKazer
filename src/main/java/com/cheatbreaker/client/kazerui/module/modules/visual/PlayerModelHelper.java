package com.cheatbreaker.client.kazerui.module.modules.visual;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;

final class PlayerModelHelper {
   private static final EnumPlayerModelParts[] PARTS = new EnumPlayerModelParts[]{
      EnumPlayerModelParts.HAT,
      EnumPlayerModelParts.LEFT_PANTS_LEG,
      EnumPlayerModelParts.RIGHT_PANTS_LEG,
      EnumPlayerModelParts.LEFT_SLEEVE,
      EnumPlayerModelParts.RIGHT_SLEEVE,
      EnumPlayerModelParts.JACKET
   };

   static boolean a(AbstractClientPlayer var0) {
      return var0.getSkinType().equals("slim");
   }

   static boolean a(AbstractClientPlayer var0, int var1) {
      return var0.isWearing(PARTS[var1]);
   }

   static void a(ModelBiped var0, int var1) {
      if ((var1 & 1) != 0) {
         var0.bipedHeadwear.showModel = false;
      }

      if (var0 instanceof ModelPlayer) {
         ModelPlayer var2 = (ModelPlayer)var0;
         if ((var1 & 2) != 0) {
            var2.bipedLeftLegwear.showModel = false;
         }

         if ((var1 & 4) != 0) {
            var2.bipedRightLegwear.showModel = false;
         }

         if ((var1 & 8) != 0) {
            var2.bipedLeftArmwear.showModel = false;
         }

         if ((var1 & 16) != 0) {
            var2.bipedRightArmwear.showModel = false;
         }

         if ((var1 & 32) != 0) {
            var2.bipedBodyWear.showModel = false;
         }
      }
   }
}
