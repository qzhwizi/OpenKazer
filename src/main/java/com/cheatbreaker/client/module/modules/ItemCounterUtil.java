package com.cheatbreaker.client.module.modules;

import java.util.List;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public final class ItemCounterUtil {
   private ItemCounterUtil() {
   }

   public static int a(com.cheatbreaker.client.module.modules.TrackableItem var0, ItemStack[] var1) {
      int var2 = 0;
      if (var1 == null) {
         return 0;
      } else {
         for (ItemStack var6 : var1) {
            if (a(var0, var6)) {
               var2 += var6.stackSize;
            }
         }

         return var2;
      }
   }

   public static boolean a(com.cheatbreaker.client.module.modules.TrackableItem var0, ItemStack var1) {
      if (var1 != null && var1.getItem() != null) {
         switch (var0) {
            case HEALING_POT:
               return a(var1);
            case MUSHROOM_SOUP:
               return var1.getItem() == Items.mushroom_stew;
            case ARROW:
               return var1.getItem() == Items.arrow;
            case GOLDEN_APPLE:
               return var1.getItem() == Items.golden_apple && var1.getItemDamage() == 0;
            case ENCHANTED_GOLDEN_APPLE:
               return var1.getItem() == Items.golden_apple && var1.getItemDamage() == 1;
            default:
               return false;
         }
      } else {
         return false;
      }
   }

   private static boolean a(ItemStack var0) {
      if (var0.getItem() == Items.potionitem && ItemPotion.isSplash(var0.getItemDamage())) {
         List var1 = Items.potionitem.getEffects(var0);
         if (var1 != null && !var1.isEmpty()) {
            for (Object var3 : var1) {
               PotionEffect var4 = (PotionEffect)var3;
               if (var4.getPotionID() != Potion.heal.id || var4.getAmplifier() < 0 || var4.getAmplifier() > 1) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }
}
