package com.cheatbreaker.client.module.modules.options;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ArmourStatusModule extends com.cheatbreaker.client.module.Module {
   private com.cheatbreaker.client.settings.Setting generalOptionsLabel;
   public static com.cheatbreaker.client.settings.Setting listMode;
   public static com.cheatbreaker.client.settings.Setting itemName;
   public static com.cheatbreaker.client.settings.Setting itemCount;
   public static com.cheatbreaker.client.settings.Setting showWhileTying;
   public static com.cheatbreaker.client.settings.Setting equippedItem;
   private com.cheatbreaker.client.settings.Setting damageOptionsLabel;
   public static com.cheatbreaker.client.settings.Setting damageOverlay;
   public static com.cheatbreaker.client.settings.Setting showItemDamage;
   public static com.cheatbreaker.client.settings.Setting showArmourDamage;
   public static com.cheatbreaker.client.settings.Setting showMaxDamage;
   private com.cheatbreaker.client.settings.Setting damageDisplay;
   public static com.cheatbreaker.client.settings.Setting damageDisplayType;
   public static com.cheatbreaker.client.settings.Setting damageThreshold;
   public static com.cheatbreaker.client.settings.Setting protectionPercentage;
   public static com.cheatbreaker.client.settings.Setting projectileProtectionPercentage;
   public static com.cheatbreaker.client.settings.Setting protectionPosition;
   public static cn.kazer.client189.render.RenderItemBridge renderItem = new cn.kazer.client189.render.RenderItemBridge();
   public static final List<com.cheatbreaker.client.module.modules.options.DamageColorThreshold> damageColors = new ArrayList<>();
   private static List<com.cheatbreaker.client.module.modules.options.ArmourItemDisplay> items = new ArrayList<>();
   private static ScaledResolution lIIIIIllllIIIIlIlIIIIlIlI;

   public ArmourStatusModule() {
      super("Armor Status");
      this.b(com.cheatbreaker.client.gui.hudeditor.GuiAnchor.LEFT_BOTTOM);
      this.b(1.0F, 0.0F);
      this.c(true);
      this.generalOptionsLabel = new com.cheatbreaker.client.settings.Setting(this, "label").a("General Options");
      new com.cheatbreaker.client.settings.Setting(this, "Protection").a(false);
      new com.cheatbreaker.client.settings.Setting(this, "Armor Bars").a(false);
      new com.cheatbreaker.client.settings.Setting(this, "Show Helmet").a(true);
      new com.cheatbreaker.client.settings.Setting(this, "Show Chestplate").a(true);
      new com.cheatbreaker.client.settings.Setting(this, "Show Leggings").a(true);
      new com.cheatbreaker.client.settings.Setting(this, "Show Boots").a(true);
      new com.cheatbreaker.client.settings.Setting(this, "Move Armor Pieces Individually").a(false);
      new com.cheatbreaker.client.settings.Setting(this, "Vanilla Mode").a(false);
      listMode = new com.cheatbreaker.client.settings.Setting(this, "List Mode").a("horizontal").a("vertical", "horizontal");
      itemName = new com.cheatbreaker.client.settings.Setting(this, "Item Name").a(false);
      itemCount = new com.cheatbreaker.client.settings.Setting(this, "Item Count").a(true);
      equippedItem = new com.cheatbreaker.client.settings.Setting(this, "Show Held Item").a(true);
      showWhileTying = new com.cheatbreaker.client.settings.Setting(this, "Show While Typing").a(false);
      new com.cheatbreaker.client.settings.Setting(this, "Text Shadow").a(true);
      new com.cheatbreaker.client.settings.Setting(this, "Show Background").a(false);
      this.damageOptionsLabel = new com.cheatbreaker.client.settings.Setting(this, "label").a("Damage Options");
      damageOverlay = new com.cheatbreaker.client.settings.Setting(this, "Damage Overlay").a(true);
      showItemDamage = new com.cheatbreaker.client.settings.Setting(this, "Show Item Damage").a(true);
      showArmourDamage = new com.cheatbreaker.client.settings.Setting(this, "Show Armor Damage").a(true);
      showMaxDamage = new com.cheatbreaker.client.settings.Setting(this, "Show Max Damage").a(false);
      this.damageDisplay = new com.cheatbreaker.client.settings.Setting(this, "label").a("Damage Display");
      damageDisplayType = new com.cheatbreaker.client.settings.Setting(this, "Damage Display Type").a("value").a("value", "percent", "none");
      damageThreshold = new com.cheatbreaker.client.settings.Setting(this, "Damage Threshold Type").a("percent").a("percent", "value");
      protectionPercentage = new com.cheatbreaker.client.settings.Setting(this, "Protection Percentage").a(true);
      projectileProtectionPercentage = new com.cheatbreaker.client.settings.Setting(this, "Projectile Protection Percentage").a(true).a(protectionPercentage);
      protectionPosition = new com.cheatbreaker.client.settings.Setting(this, "Protection Position").a("BR").a("BL", "BR", "TR", "TL").a(protectionPercentage);
      damageColors.add(new com.cheatbreaker.client.module.modules.options.DamageColorThreshold(10, "4"));
      damageColors.add(new com.cheatbreaker.client.module.modules.options.DamageColorThreshold(25, "c"));
      damageColors.add(new com.cheatbreaker.client.module.modules.options.DamageColorThreshold(40, "6"));
      damageColors.add(new com.cheatbreaker.client.module.modules.options.DamageColorThreshold(60, "e"));
      damageColors.add(new com.cheatbreaker.client.module.modules.options.DamageColorThreshold(80, "7"));
      damageColors.add(new com.cheatbreaker.client.module.modules.options.DamageColorThreshold(100, "f"));
      this.a(new ResourceLocation("client/icons/mods/diamond_chestplate.png"), 34, 34);
      this.a(com.cheatbreaker.client.event.event.RenderHudEditorEvent.class, this::a);
      this.a(com.cheatbreaker.client.event.event.RenderHudEvent.class, this::b);
   }

   private void a(com.cheatbreaker.client.event.event.RenderHudEvent var1) {
      if (this.q()) {
         boolean var2 = this.minecraft.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.ModulePlacementScreen;
         ArrayList var3 = new ArrayList();

         for (int var4 = 3; var4 >= 0; var4--) {
            ItemStack var5 = this.minecraft.thePlayer.inventory.armorInventory[var4];
            if (var5 != null) {
               var3.add(new com.cheatbreaker.client.module.modules.options.ArmourItemDisplay(var5, 16, 16, 2, true));
            }
         }

         if (var3.isEmpty() && var2) {
            var3.add(new com.cheatbreaker.client.module.modules.options.ArmourItemDisplay(new ItemStack(Item.getItemById(310)), 16, 16, 2, true));
            var3.add(new com.cheatbreaker.client.module.modules.options.ArmourItemDisplay(new ItemStack(Item.getItemById(311)), 16, 16, 2, true));
            var3.add(new com.cheatbreaker.client.module.modules.options.ArmourItemDisplay(new ItemStack(Item.getItemById(312)), 16, 16, 2, true));
            var3.add(new com.cheatbreaker.client.module.modules.options.ArmourItemDisplay(new ItemStack(Item.getItemById(313)), 16, 16, 2, true));
         }

         if ((Boolean)equippedItem.d() && this.minecraft.thePlayer.getCurrentEquippedItem() != null) {
            var3.add(new com.cheatbreaker.client.module.modules.options.ArmourItemDisplay(this.minecraft.thePlayer.getCurrentEquippedItem(), 16, 16, 2, false));
         } else if ((Boolean)equippedItem.d() && var2) {
            var3.add(new com.cheatbreaker.client.module.modules.options.ArmourItemDisplay(new ItemStack(Item.getItemById(276)), 16, 16, 2, false));
         }

         if (!var3.isEmpty()) {
            GL11.glPushMatrix();
            cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
            lIIIIIllllIIIIlIlIIIIlIlI = var1.a();
            this.b(var3);
            this.a(lIIIIIllllIIIIlIlIIIIlIlI);
            this.a(this.minecraft, var3);
            cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
         }
      }
   }

   private void b(com.cheatbreaker.client.event.event.RenderHudEvent var1) {
      if (this.q()) {
         if (!(this.minecraft.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.HudEditorScreen)
            && !(this.minecraft.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.ModulePlacementScreen)
            && (!(this.minecraft.currentScreen instanceof GuiChat) || (Boolean)showWhileTying.d())) {
            this.b(this.minecraft);
            if (!items.isEmpty()) {
               GL11.glPushMatrix();
               cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
               lIIIIIllllIIIIlIlIIIIlIlI = var1.a();
               this.b(items);
               this.a(lIIIIIllllIIIIlIlIIIIlIlI);
               this.a(this.minecraft, items);
               cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
               GL11.glPopMatrix();
            }
         }
      }
   }

   private void b(Minecraft var1) {
      items.clear();

      for (int var2 = 3; var2 >= -1; var2--) {
         ItemStack var3 = null;
         if (var2 == -1 && (Boolean)equippedItem.d()) {
            var3 = var1.thePlayer.getCurrentEquippedItem();
         } else if (var2 != -1) {
            var3 = var1.thePlayer.inventory.armorInventory[var2];
         }

         if (var3 != null) {
            items.add(new com.cheatbreaker.client.module.modules.options.ArmourItemDisplay(var3, 16, 16, 2, var2 > -1));
         }
      }
   }

   private void b(List<com.cheatbreaker.client.module.modules.options.ArmourItemDisplay> var1) {
      if (!var1.isEmpty()) {
         int var2 = (Boolean) itemName.d() ? 18 : 16;
         int var3 = 0;
         int var4 = 0;
         if (((String)listMode.d()).equalsIgnoreCase("vertical")) {
            var4 = var2 * var1.size();

            for (com.cheatbreaker.client.module.modules.options.ArmourItemDisplay var6 : var1) {
               if (var6.a() > var3) {
                  var3 = var6.a();
               }
            }
         } else if (((String)listMode.d()).equalsIgnoreCase("horizontal")) {
            for (com.cheatbreaker.client.module.modules.options.ArmourItemDisplay var8 : var1) {
               var3 += var8.a();
               if (var8.b() > var4) {
                  var4 = var8.b();
               }
            }
         }

         this.width = var3;
         this.height = var4;
      }
   }

   private void a(Minecraft var1, List<com.cheatbreaker.client.module.modules.options.ArmourItemDisplay> var2) {
      if (var2.size() > 0) {
         int var3 = (Boolean) itemName.d() ? 18 : 16;
         if (((String)listMode.d()).equalsIgnoreCase("vertical")) {
            int var4 = 0;
            int var5 = 0;
            boolean var6 = com.cheatbreaker.client.gui.hudeditor.AnchorHelper.c(this.r()) == com.cheatbreaker.client.gui.hudeditor.AnchorAlignment.RIGHT;

            for (com.cheatbreaker.client.module.modules.options.ArmourItemDisplay var8 : var2) {
               var8.a(var6 ? this.width : 0.0F, var4);
               var4 += var3;
               if (var8.a() > var5) {
                  var5 = var8.a();
               }
            }

            this.height = var4;
            this.width = var5;
         } else if (((String)listMode.d()).equalsIgnoreCase("horizontal")) {
            boolean var10 = false;
            int var11 = 0;
            int var12 = 0;
            boolean var13 = com.cheatbreaker.client.gui.hudeditor.AnchorHelper.c(this.r()) == com.cheatbreaker.client.gui.hudeditor.AnchorAlignment.RIGHT;

            for (com.cheatbreaker.client.module.modules.options.ArmourItemDisplay var9 : var2) {
               if (var13) {
                  var11 += var9.a();
               }

               var9.a(var11, 0.0F);
               if (!var13) {
                  var11 += var9.a();
               }

               if (var9.b() > var12) {
                  var12 += var9.b();
               }
            }

            this.height = var12;
            this.width = var11;
         }
      }
   }

   public static void a(Minecraft var0) {
      boolean var1 = (Boolean)protectionPercentage.d();
      boolean var2 = (Boolean)projectileProtectionPercentage.d();
      if (var1 || var2) {
         if (var0.thePlayer != null) {
            double var3 = a(var0, false);
            double var5 = a(var0, true);
            String var7 = com.cheatbreaker.client.module.modules.options.ArmourStatusUtil.a(var3, var5, var1, var2);
            if (var7 != null) {
               ScaledResolution var8 = new ScaledResolution(var0);
               int var9 = var0.fontRendererObj.getStringWidth(var7);
               com.cheatbreaker.client.module.modules.options.ArmourStatusUtil.aInner var10 = com.cheatbreaker.client.module.modules.options.ArmourStatusUtil.a(
                  (String)protectionPosition.d(), var8.getScaledWidth(), var8.getScaledHeight(), var9
               );
               var0.fontRendererObj.drawStringWithShadow(var7, var10.x, var10.y, 16777215);
            }
         }
      }
   }

   private static double a(Minecraft var0, boolean var1) {
      int var2 = 0;
      int var3 = 0;
      int var4 = 0;
      if (var0.thePlayer.isPotionActive(Potion.resistance)) {
         var4 = var0.thePlayer.getActivePotionEffect(Potion.resistance).getAmplifier() + 1;
      }

      for (ItemStack var8 : var0.thePlayer.inventory.armorInventory) {
         if (var8 != null) {
            if (var8.getItem() instanceof ItemArmor) {
               var2 += ((ItemArmor)var8.getItem()).damageReduceAmount;
            }

            if (var8.isItemEnchanted()) {
               var3 += a(EnchantmentHelper.getEnchantmentLevel(0, var8));
               if (var1) {
                  var3 += a(EnchantmentHelper.getEnchantmentLevel(4, var8));
               }
            }
         }
      }

      return com.cheatbreaker.client.module.modules.options.ArmourStatusUtil.a(var2, var3, var4);
   }

   private static int a(int var0) {
      return var0 == 0 ? 0 : (int)Math.floor((6 + var0 * var0) * 0.75 / 3.0);
   }
}
