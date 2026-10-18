package com.cheatbreaker.client.kazerui.module.modules.hud;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public final class PotionCounterModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.hud.PotionCounterModule.b> potionCounter = this.a(
      "potionCounter", com.cheatbreaker.client.kazerui.module.modules.hud.PotionCounterModule.b.POTION
   );
   private int count;

   public PotionCounterModule() {
      super("POTION_COUNTER", false);
      this.a(new com.cheatbreaker.client.kazerui.module.modules.hud.PotionCounterModule.a());
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a("generalOptions", var1x -> var1x.a(this.potionCounter));
   }

   @Override
   public void a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a var1) {
      if (var1.phase == com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b.END && this.a()) {
         this.count = this.n();
      }
   }

   private int n() {
      EntityPlayerSP var1 = Minecraft.getMinecraft().thePlayer;
      if (var1 == null) {
         return 0;
      } else {
         InventoryPlayer var2 = var1.inventory;
         int var3 = 0;

         for (ItemStack var7 : var2.mainInventory) {
            var3 += this.a(var7);
         }

         return var3 + this.a(var2.getItemStack());
      }
   }

   private int a(ItemStack var1) {
      if (var1 == null || var1.getItem() == null) {
         return 0;
      } else if (this.potionCounter.a(com.cheatbreaker.client.kazerui.module.modules.hud.PotionCounterModule.b.SOUP)) {
         return var1.getItem() == Items.mushroom_stew ? var1.stackSize : 0;
      } else if (var1.getItem() instanceof ItemPotion && ItemPotion.isSplash(var1.getMetadata())) {
         List var2 = ((ItemPotion)var1.getItem()).getEffects(var1);
         if (var2 == null) {
            return 0;
         } else {
            for (PotionEffect var4 : (Iterable<PotionEffect>) var2) {
               if (var4.getPotionID() == 6) {
                  return var1.stackSize;
               }
            }

            return 0;
         }
      } else {
         return 0;
      }
   }

   private final class a extends com.cheatbreaker.client.kazerui.module.modulerender.RichTextHudBase {
      private int shownCount = Integer.MIN_VALUE;
      private boolean shownSoup;
      private String shownText;

      a() {
         super(PotionCounterModule.this, 0.0F, 62.0F, com.cheatbreaker.client.kazerui.hud.HudAnchor.TOP_CENTER, a(10, 18, 22, 40, 56, 62));
      }

      @Override
      protected String c(boolean var1) {
         int var2 = Minecraft.getMinecraft().thePlayer == null ? 0 : PotionCounterModule.this.count;
         boolean var3 = PotionCounterModule.this.potionCounter.a(com.cheatbreaker.client.kazerui.module.modules.hud.PotionCounterModule.b.SOUP);
         if (this.shownCount != var2 || this.shownSoup != var3) {
            this.shownCount = var2;
            this.shownSoup = var3;
            String var4 = var3 ? "soup" : "pot";
            this.shownText = var2 + " " + (var2 == 1 ? var4 : var4 + "s");
         }

         return this.shownText;
      }
   }

   public static enum b implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
      POTION("potion"),
      SOUP("soup");

      private final String id;

      private b(String var3) {
         this.id = var3;
      }

      @Override
      public String a() {
         return this.id;
      }
   }
}
