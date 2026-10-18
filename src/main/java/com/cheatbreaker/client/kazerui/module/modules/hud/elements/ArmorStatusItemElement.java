package com.cheatbreaker.client.kazerui.module.modules.hud.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public final class ArmorStatusItemElement {
   private final com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule status;
   public final com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.f slot;
   public final ItemStack item;
   private final boolean armor;
   private String name = "";
   private String damage = "";
   private com.cheatbreaker.client.kazerui.module.settings.ColorSetting damageColor;
   private int nameWidth;
   private int damageWidth;
   private int width;
   private int height;
   private int itemX;
   private int itemY;
   private int nameX;
   private int nameY;
   private int damageX;
   private int damageY;

   public ArmorStatusItemElement(
      com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule var1, com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.f var2, ItemStack var3, boolean var4
   ) {
      this.status = var1;
      this.slot = var2;
      this.item = var3;
      this.armor = !var2.a();
      if (var4) {
         this.c();
      }
   }

   private void c() {
      this.name = this.item != null && this.status.itemName.b() ? this.item.getDisplayName() : "";
      this.nameWidth = (int)com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(EnumChatFormatting.getTextWithoutFormattingCodes(this.name));
      String[] var1 = new String[1];
      this.damageColor = this.a(this.status.maxDamage.b(), var1);
      this.damage = var1[0];
      this.damageWidth = (int)com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(EnumChatFormatting.getTextWithoutFormattingCodes(this.damage));
      this.a(this.status.n());
   }

   public com.cheatbreaker.client.kazerui.module.settings.ColorSetting a(boolean var1, String[] var2) {
      boolean var3 = this.armor ? this.status.armorDamage.b() : this.status.itemDamage.b();
      if (var3
         && this.item != null
         && this.item.getItem().isDamageable()
         && this.item.getItem().getMaxDamage() > 0
         && (!this.item.hasTagCompound() || !this.item.getTagCompound().getBoolean("Unbreakable") || !this.status.hideUnbreakableDurability.b())) {
         int var4 = this.item.getMaxDamage();
         int var5 = var4 - this.item.getItemDamage();
         int var6 = var5 * 100 / var4;
         switch ((com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.aInner)this.status.damageDisplay.j()) {
            case VALUE:
               var2[0] = var5 + (var1 ? "/" + var4 : "");
               break;
            case PERCENT:
               var2[0] = var6 + "%";
               break;
            default:
               var2[0] = "";
         }

         return this.status.a(var6);
      } else {
         var2[0] = "";
         return this.status.a(100);
      }
   }

   public void a(com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.d var1) {
      if (this.item == null) {
         this.width = 0;
         this.height = 0;
      } else {
         if (var1 != com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.d.TOP && var1 != com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.d.BOTTOM) {
            this.a(var1 == com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.d.LEFT);
         } else {
            this.b(var1 == com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.d.TOP);
         }
      }
   }

   private void a(boolean var1) {
      int var2 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.b();
      int var3 = Math.max(this.nameWidth, this.damageWidth);
      int var4 = var2 * ((this.name.isEmpty() ? 0 : 1) + (this.damage.isEmpty() ? 0 : 1));
      this.width = 16 + (var3 == 0 ? 0 : 2 + var3);
      this.height = Math.max(16, var4);
      if (var1) {
         this.itemX = this.width - 16;
         this.nameX = var3 - this.nameWidth;
         this.damageX = var3 - this.damageWidth;
      } else {
         this.itemX = 0;
         this.nameX = this.width - var3;
         this.damageX = this.width - var3;
      }

      this.itemY = 0;
      this.nameY = a(this.height, var4);
      this.damageY = this.nameY + (this.name.isEmpty() ? 0 : var2);
   }

   private void b(boolean var1) {
      int var2 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.b();
      int var3 = 16 + (this.nameWidth == 0 ? 0 : 2 + this.nameWidth);
      int var4 = this.damage.isEmpty() ? 0 : 2 + var2;
      this.width = Math.max(var3, this.damageWidth);
      this.height = 16 + var4;
      this.itemX = a(this.width, var3);
      this.itemY = var1 ? var4 : 0;
      this.nameX = this.itemX + 16 + 2;
      this.nameY = this.itemY + a(16, var2);
      this.damageX = 0;
      this.damageY = var1 ? 3 : 16;
   }

   private static int a(int var0, int var1) {
      return Math.round((var0 - var1) / 2.0F);
   }

   public int a() {
      return this.width;
   }

   public int b() {
      return this.height;
   }

   public void a(float var1, float var2) {
      if (this.item != null) {
         this.b(var1 + this.itemX, var2 + this.itemY);
         boolean var3 = this.status.textShadow.b();
         if (!this.name.isEmpty()) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(this.status.nameTextColor, this.name, var1 + this.nameX, var2 + this.nameY, var3);
         }

         if (this.damageColor != null && !this.damage.isEmpty()) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(this.damageColor, this.damage, var1 + this.damageX, var2 + this.damageY, var3);
         }
      }
   }

   public void b(float var1, float var2) {
      com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(this.item, var1, var2);
      a(this.item, (int)var1, (int)var2, this.status.damageOverlay.b(), this.status.itemCount.b());
   }

   public static void a(ItemStack var0, int var1, int var2, boolean var3, boolean var4) {
      if (var0 != null && (var3 || var4)) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, 0.0F, 500.0F);
         if (var3 && var0.isItemDamaged()) {
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            double var5 = (double)var0.getItemDamage() / var0.getMaxDamage();
            int var7 = (int)Math.round(13.0 - var5 * 13.0);
            int var8 = (int)Math.round(255.0 - var5 * 255.0);
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var1 + 2, var2 + 14, 13.0F, 2.0F, -16777216);
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var1 + 2, var2 + 14, 12.0F, 1.0F, 0xFF000000 | (255 - var8) / 4 << 16 | 16128);
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var1 + 2, var2 + 14, var7, 1.0F, 0xFF000000 | 255 - var8 << 16 | var8 << 8);
            GlStateManager.enableDepth();
         }

         if (var4) {
            int var9 = a(var0);
            if (var9 > 1) {
               GlStateManager.disableDepth();
               String var6 = String.valueOf(var9);
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var6, var1 + 17 - com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var6), var2 + 9, -1, true);
               GlStateManager.enableDepth();
            }
         }

         GlStateManager.popMatrix();
      }
   }

   private static int a(ItemStack var0) {
      EntityPlayerSP var1 = Minecraft.getMinecraft().thePlayer;
      if (var1 == null) {
         return 0;
      } else {
         Item var2 = var0.getItem();
         int var3;
         Item var4;
         if (var0.getMaxStackSize() > 1) {
            var4 = var2;
            var3 = var0.getItemDamage();
         } else {
            if (var2 != Items.bow) {
               return 0;
            }

            var4 = Items.arrow;
            var3 = -1;
         }

         int var5 = 0;

         for (ItemStack var9 : var1.inventory.mainInventory) {
            if (var9 != null && var9.getItem() == var4 && (var3 == -1 || var9.getItemDamage() == var3)) {
               var5 += var9.stackSize;
            }
         }

         ItemStack var10 = var1.inventory.getItemStack();
         if (var10 != null && var10.getItem() == var4 && (var3 == -1 || var10.getItemDamage() == var3)) {
            var5 += var10.stackSize;
         }

         return var5;
      }
   }
}
