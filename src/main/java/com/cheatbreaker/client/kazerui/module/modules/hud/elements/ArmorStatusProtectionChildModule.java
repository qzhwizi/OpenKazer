package com.cheatbreaker.client.kazerui.module.modules.hud.elements;

import java.util.Locale;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public final class ArmorStatusProtectionChildModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting onlyShowInInventory = this.a("onlyShowInInventory", false);
   private String protection = "";

   public ArmorStatusProtectionChildModule() {
      super("ARMORSTATUS_PROTECTION_CHILD", false);
      this.a(
         new com.cheatbreaker.client.kazerui.module.modulerender.RichTextHudBase(
            this, 0.0F, 0.0F, com.cheatbreaker.client.kazerui.hud.HudAnchor.BOTTOM_RIGHT, com.cheatbreaker.client.kazerui.module.modulerender.RichTextHudBase.a(18, 20, 28, 34, 50, 62)
         ) {
            @Override
            protected String c(boolean var1) {
               if (var1) {
                  return "80%";
               } else {
                  return ArmorStatusProtectionChildModule.this.onlyShowInInventory.b() && !com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusProtectionChildModule.o() ? null : ArmorStatusProtectionChildModule.this.protection;
               }
            }
         }
      );
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(this.onlyShowInInventory);
   }

   private static boolean o() {
      GuiScreen var0 = Minecraft.getMinecraft().currentScreen;
      return var0 instanceof GuiInventory || var0 instanceof GuiContainerCreative;
   }

   @Override
   public void a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a var1) {
      if (var1.phase == com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b.END && this.a()) {
         this.p();
      }
   }

   private void p() {
      EntityPlayerSP var1 = Minecraft.getMinecraft().thePlayer;
      if (var1 == null) {
         this.protection = "";
      } else {
         int var2 = 0;
         int var3 = 0;

         for (ItemStack var7 : var1.inventory.armorInventory) {
            if (var7 != null) {
               if (var7.getItem() instanceof ItemArmor) {
                  var2 += ((ItemArmor)var7.getItem()).damageReduceAmount;
               }

               Map var8 = EnchantmentHelper.getEnchantments(var7);
               Integer var9 = (Integer)var8.get(Enchantment.protection.effectId);
               if (var9 != null) {
                  var3 += (int)Math.floor((6 + var9 * var9) / 3.0F * 0.75F);
               }
            }
         }

         float var10 = Math.min((float)var2, 20.0F) / 25.0F;
         float var11 = Math.min(Math.min(var3, 25) * 0.75F, 20.0F) / 25.0F;
         float var12 = 1.0F - (1.0F - var10) * (1.0F - var11);
         this.protection = String.format(Locale.ROOT, "%3.1f%%", var12 * 100.0F);
      }
   }
}
