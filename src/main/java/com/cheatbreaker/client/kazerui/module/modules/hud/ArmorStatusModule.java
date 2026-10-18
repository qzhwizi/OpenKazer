package com.cheatbreaker.client.kazerui.module.modules.hud;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public final class ArmorStatusModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   public final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting itemName = this.a("itemName", false);
   public final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting itemCount = this.a("itemCount", true);
   public final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showWhileTyping = this.a("showWhileTyping", false);
   public final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting moveArmorIndividually = this.a("moveArmorIndividually", false);
   public final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting textShadow = this.a("textShadow", true);
   public final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting damageOverlay = this.a("damageOverlay", true);
   public final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting itemDamage = this.a("itemDamage", true);
   public final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting armorDamage = this.a("armorDamage", true);
   public final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting maxDamage = this.a("maxDamage", false);
   public final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting hideUnbreakableDurability = this.a("hideUnbreakableDurability", true);
   private final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.d> durabilityPosition = this.a(
      "durabilityPosition", com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.d.RIGHT
   );
   private final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.c> listMode = this.a(
      "listMode", com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.c.VERTICAL
   );
   public final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.aInner> damageDisplay = this.a(
      "damageDisplay", com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.aInner.VALUE
   );
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting background = this.a("background", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting border = this.a("border", false);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting borderThickness = this.a("borderThickness", 0.5F, 0.5F, 3.0F);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting backgroundColor = this.a("backgroundColor", 1862270976);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting borderColor = this.a("borderColor", -1627389952);
   public final com.cheatbreaker.client.kazerui.module.settings.ColorSetting nameTextColor = this.a("nameTextColor", -1);
   public final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting vanillaMode = this.a("vanillaMode", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting hotbarAnchor = this.a("hotbarAnchor", true);
   private final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.e> hotbarPosition = this.a(
      "hotbarPosition", com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.e.LEFT
   );
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting hideEmptySlots = this.a("hideEmptySlots", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting roundedCorners = this.a("roundedCorners", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting lowDurabilityIndicator = this.a("lowDurabilityIndicator", true);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting lowDurabilityThreshold = this.a("lowDurabilityThreshold", 10, 1, 100);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting staticDamageColors = this.a("staticDamageColors", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting lowestColor = this.a("lowestColor", -5636096);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting lowColor = this.a("lowColor", -43691);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting mediumLowColor = this.a("mediumLowColor", -22016);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting mediumColor = this.a("mediumColor", -171);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting highColor = this.a("highColor", -11141291);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting highestColor = this.a("highestColor", -1);
   private final Map<com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.f, com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusSlotChildModule> slots = new EnumMap<>(
      com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.f.class
   );
   private final Map<com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.f, com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusItemElement> elements = new EnumMap<>(
      com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.f.class
   );
   private final Map<com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.f, com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusItemElement> previewElements = new EnumMap<>(
      com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.f.class
   );
   private boolean previewStale = true;
   private final com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.b hud = this.a(new com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.b());

   public ArmorStatusModule() {
      super("ARMORSTATUS", true);
      this.a(new com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusProtectionChildModule(), "generalOptions");
      this.a(new com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusBarsChildModule(this), "generalOptions");

      for (com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.f var4 : com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.f.values()) {
         this.slots.put(var4, this.a(new com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusSlotChildModule(this, var4), "hudOptions"));
      }
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a("generalOptions", var0 -> {});
      var1.a("hudOptions", var1x -> {
         var1x.a(this.moveArmorIndividually).a(this.vanillaMode::b);
         var1x.a(this.vanillaMode);
         var1x.a(this.listMode).a(() -> this.moveArmorIndividually.b() && !this.vanillaMode.b());
         var1x.a(this.hotbarPosition).a(() -> !this.vanillaMode.b() || !this.hotbarAnchor.b());
         var1x.a(this.hotbarAnchor, this.roundedCorners, this.hideEmptySlots).a(() -> !this.vanillaMode.b());
         var1x.a(this.itemName).a(this.vanillaMode::b);
         var1x.a(this.itemCount, this.showWhileTyping, this.textShadow);
         var1x.a(this.background, var1xx -> {
            var1xx.a(this.backgroundColor);
            var1xx.a(this.border, var1xxx -> var1xxx.a(this.borderThickness, this.borderColor));
         }).a(() -> this.moveArmorIndividually.b() || this.vanillaMode.b());
         var1x.a(this.hud.scale).a(() -> this.moveArmorIndividually.b() || this.vanillaMode.b() && this.hotbarAnchor.b());
      });
      var1.a(
         "damageOptions",
         var1x -> {
            var1x.a(this.damageDisplay).a(() -> !this.itemDamage.b() && !this.armorDamage.b());
            var1x.a(this.durabilityPosition)
               .a(
                  () -> this.listMode.a(com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.c.HORIZONTAL)
                     || this.p()
                     || this.moveArmorIndividually.b()
                     || this.vanillaMode.b()
               );
            var1x.a(this.damageOverlay);
            var1x.a(this.lowDurabilityIndicator, var1xx -> var1xx.a(this.lowDurabilityThreshold)).a(() -> !this.vanillaMode.b());
            var1x.a(this.itemDamage, this.armorDamage).a(() -> this.damageDisplay.a(com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.aInner.NONE));
            var1x.a(this.maxDamage)
               .a(() -> this.damageDisplay.a(com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.aInner.PERCENT) || this.p() || this.vanillaMode.b());
            var1x.a(this.hideUnbreakableDurability).a(this::p);
         }
      );
      var1.a("colorOptions", var1x -> {
         var1x.a(this.nameTextColor).a(() -> !this.itemName.b() || this.vanillaMode.b());
         var1x.a(this.highestColor);
         var1x.a(this.highColor, this.mediumColor, this.mediumLowColor, this.lowColor, this.lowestColor).a(() -> this.staticDamageColors.b() || this.p());
         var1x.a(this.staticDamageColors).a(this::p);
      });
   }

   private boolean p() {
      return this.damageDisplay.a(com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.aInner.NONE) || !this.itemDamage.b() && !this.armorDamage.b();
   }

   public com.cheatbreaker.client.kazerui.module.settings.ColorSetting a(int var1) {
      if (this.staticDamageColors.b()) {
         return this.highestColor;
      } else if (var1 <= 10) {
         return this.lowestColor;
      } else if (var1 <= 25) {
         return this.lowColor;
      } else if (var1 <= 40) {
         return this.mediumLowColor;
      } else if (var1 <= 60) {
         return this.mediumColor;
      } else {
         return var1 <= 80 ? this.highColor : this.highestColor;
      }
   }

   public static boolean a(ItemStack var0, int var1) {
      int var2 = var0 == null ? 0 : var0.getMaxDamage();
      return var2 > 0 && var0.isItemStackDamageable() ? (var2 - var0.getItemDamage()) * 100 / var2 <= var1 : false;
   }

   public static String b(String var0, String var1) {
      return com.cheatbreaker.client.kazerui.module.ModuleRegistry.a(var0, var1, null);
   }

   public static com.cheatbreaker.client.kazerui.hud.HudAnchor a(String var0, com.cheatbreaker.client.kazerui.hud.HudAnchor var1) {
      com.cheatbreaker.client.kazerui.hud.HudAnchor var2 = com.cheatbreaker.client.kazerui.hud.HudAnchor.a(
         com.cheatbreaker.client.kazerui.module.ModuleRegistry.a(var0, "anchor", com.cheatbreaker.client.kazerui.gui.components.MenuModel.b(var0, "anchor"))
      );
      return var2 == null ? var1 : var2;
   }

   public com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.d n() {
      return b(this.b(), "durabilityPosition") == null && a(this.b(), this.hud.anchor).horizontal == com.cheatbreaker.client.kazerui.hud.HudAnchor.a.END
         ? com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.d.LEFT
         : this.durabilityPosition.j();
   }

   private static ItemStack a(EntityPlayerSP var0, com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.f var1) {
      return var1.a() ? var0.getCurrentEquippedItem() : var0.inventory.armorInventory[var1.armorSlot];
   }

   private static ItemStack b(com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.f var0) {
      Object var1;
      switch (var0) {
         case HELD_ITEM:
            var1 = Items.diamond_sword;
            break;
         case HELMET:
            var1 = Items.diamond_helmet;
            break;
         case CHESTPLATE:
            var1 = Items.diamond_chestplate;
            break;
         case LEGGINGS:
            var1 = Items.diamond_leggings;
            break;
         default:
            var1 = Items.diamond_boots;
      }

      return new ItemStack((Item)var1);
   }

   @Override
   public void a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a var1) {
      if (var1.phase == com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b.END) {
         this.elements.clear();
         this.previewStale = true;
         EntityPlayerSP var2 = Minecraft.getMinecraft().thePlayer;
         if (var2 != null && this.a()) {
            boolean var3 = this.vanillaMode.b();

            for (com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusSlotChildModule var5 : this.slots.values()) {
               if (var5.a()) {
                  ItemStack var6 = a(var2, var5.slot);
                  if (var6 != null || var3) {
                     this.elements.put(var5.slot, new com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusItemElement(this, var5.slot, var6, !var3));
                  }
               }
            }
         }
      }
   }

   public Map<com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.f, com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusItemElement> b(boolean var1) {
      if (!var1) {
         return this.elements;
      } else {
         if (this.previewStale) {
            this.previewElements.clear();
            EntityPlayerSP var2 = Minecraft.getMinecraft().thePlayer;
            boolean var3 = this.vanillaMode.b();

            for (com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusSlotChildModule var5 : this.slots.values()) {
               if (var5.a()) {
                  ItemStack var6 = var5.slot.a() && var2 != null ? a(var2, var5.slot) : null;
                  if (var6 == null) {
                     var6 = b(var5.slot);
                  }

                  this.previewElements.put(var5.slot, new com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusItemElement(this, var5.slot, var6, !var3));
               }
            }

            this.previewStale = false;
         }

         return this.previewElements;
      }
   }

   private static ResourceLocation c(com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.f var0) {
      return var0.a() ? null : new ResourceLocation("minecraft", "textures/items/empty_armor_slot_" + var0.langId + ".png");
   }

   public List<com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusSlotChildModule> o() {
      return new ArrayList<>(this.slots.values());
   }

   public static enum aInner implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
      VALUE("value"),
      PERCENT("percent"),
      NONE("none");

      private final String id;

      private aInner(String var3) {
         this.id = var3;
      }

      @Override
      public String a() {
         return this.id;
      }
   }

   private final class b extends com.cheatbreaker.client.kazerui.module.modulerender.HudElement {
      private final com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.b.aInner vanilla = new com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.b.aInner();

      b() {
         super(ArmorStatusModule.this, 0.0F, 0.0F, com.cheatbreaker.client.kazerui.hud.HudAnchor.BOTTOM_RIGHT);
      }

      @Override
      public void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      }

      @Override
      public boolean l() {
         return ArmorStatusModule.this.vanillaMode.b() ? !ArmorStatusModule.this.hotbarAnchor.b() : !ArmorStatusModule.this.moveArmorIndividually.b();
      }

      @Override
      public boolean i() {
         return ArmorStatusModule.this.vanillaMode.b() && ArmorStatusModule.this.hotbarAnchor.b();
      }

      @Override
      public boolean a(boolean var1) {
         Minecraft var2 = Minecraft.getMinecraft();
         boolean var3 = var2.ingameGUI != null && var2.ingameGUI.getChatGUI().getChatOpen();
         if (!ArmorStatusModule.this.showWhileTyping.b() && var3) {
            return false;
         } else if (ArmorStatusModule.this.vanillaMode.b()) {
            return this.vanilla.a(ArmorStatusModule.this.b(var1).values());
         } else if (ArmorStatusModule.this.moveArmorIndividually.b()) {
            this.a(0.0F, 0.0F);
            return false;
         } else {
            Collection var4 = ArmorStatusModule.this.b(var1).values();
            if (var4.isEmpty()) {
               return false;
            } else {
               this.a(var4);
               return true;
            }
         }
      }

      private com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.d a(Collection<com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusItemElement> var1) {
         boolean var2 = ArmorStatusModule.this.listMode.a(com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.c.VERTICAL);
         com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.d var3 = var2
            ? ArmorStatusModule.this.n()
            : (
               com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.a(ArmorStatusModule.this.b(), this.anchor).horizontal == com.cheatbreaker.client.kazerui.hud.HudAnchor.a.END
                  ? com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.d.LEFT
                  : com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.d.RIGHT
            );
         float var4 = 0.0F;
         float var5 = 0.0F;

         for (com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusItemElement var7 : var1) {
            var7.a(var3);
            if (var2) {
               var4 = Math.max(var4, (float)var7.a());
               var5 += (var5 == 0.0F ? 0 : 2) + var7.b();
            } else {
               var4 += (var4 == 0.0F ? 0 : 2) + var7.a();
               var5 = Math.max(var5, (float)var7.b());
            }
         }

         this.a(var4 + 4.0F, var5 + 4.0F);
         return var3;
      }

      @Override
      public void b(boolean var1) {
         Collection var2 = ArmorStatusModule.this.b(var1).values();
         if (ArmorStatusModule.this.vanillaMode.b()) {
            this.vanilla.b(var2);
         } else {
            com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.d var3 = this.a(var2);
            boolean var4 = ArmorStatusModule.this.listMode.a(com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.c.VERTICAL);
            float var5 = this.f() - 4.0F;
            if (ArmorStatusModule.this.background.b()) {
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(ArmorStatusModule.this.backgroundColor, 0.0F, 0.0F, this.f(), this.g());
               if (ArmorStatusModule.this.border.b()) {
                  com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(ArmorStatusModule.this.borderColor, 0.0F, 0.0F, this.f(), this.g(), ArmorStatusModule.this.borderThickness.b());
               }
            }

            float var6 = 2.0F;
            float var7 = 3.0F;
            float var8 = 0.0F;

            for (com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusItemElement var10 : (Iterable<com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusItemElement>) var2) {
               if (var4) {
                  float var11;
                  switch (var3) {
                     case LEFT:
                        var11 = var5 - var10.a();
                        break;
                     case RIGHT:
                        var11 = 0.0F;
                        break;
                     default:
                        var11 = ArmorStatusModule.this.itemName.b() ? 0.0F : (var5 - var10.a()) / 2.0F;
                  }

                  var10.a(var6 + var11, var7 + var8);
                  var8 += var10.b() + 2;
               } else {
                  var10.a(var6 + var8, var7);
                  var8 += var10.a() + 2;
               }
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         }
      }

      private final class aInner {
         private boolean vertical;
         private boolean attached;
         private boolean leftSide;
         private int count;
         private float textScale;
         private float stripWidth;
         private float stripHeight;
         private float above;

         private aInner() {
         }

         boolean a(Collection<com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusItemElement> var1) {
            this.vertical = ArmorStatusModule.this.listMode.a(com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.c.VERTICAL);
            this.attached = ArmorStatusModule.this.hotbarAnchor.b();
            this.leftSide = this.attached
               ? ArmorStatusModule.this.hotbarPosition.a(com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.e.LEFT)
               : com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.a(ArmorStatusModule.this.b(), b.this.anchor).horizontal == com.cheatbreaker.client.kazerui.hud.HudAnchor.a.END;
            boolean var2 = ArmorStatusModule.this.hideEmptySlots.b();
            boolean var3 = false;
            boolean var4 = false;
            float var5 = 0.0F;
            this.count = 0;

            for (com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusItemElement var7 : var1) {
               if (!var2 || var7.item != null) {
                  this.count++;
                  if (!this.vertical) {
                     String[] var8 = new String[1];
                     var7.a(false, var8);
                     var5 = Math.max(var5, com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var8[0]));
                     var3 |= !var8[0].isEmpty();
                     var4 |= this.a(var7.item);
                  }
               }
            }

            this.stripWidth = com.cheatbreaker.client.kazerui.module.modules.hud.elements.HotbarStripRenderer.a(this.vertical, this.count);
            this.stripHeight = com.cheatbreaker.client.kazerui.module.modules.hud.elements.HotbarStripRenderer.b(this.vertical, this.count);
            this.textScale = 1.0F;
            this.above = 0.0F;
            if (!this.vertical) {
               this.textScale = var5 <= 20.0F ? 1.0F : (var5 * 0.75F <= 20.0F ? 0.75F : 0.5F);
               if (var3) {
                  this.above = this.above + Math.round(com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.b() * this.textScale);
               }

               if (var4) {
                  this.above += 8.0F;
               }
            }

            if (this.count != 0 && !this.attached) {
               b.this.a(this.stripWidth, this.stripHeight + this.above);
            } else {
               b.this.a(0.0F, 0.0F);
            }

            return this.count > 0;
         }

         void b(Collection<com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusItemElement> var1) {
            if (this.a(var1)) {
               GlStateManager.pushMatrix();
               if (this.attached) {
                  this.a();
               } else {
                  GlStateManager.translate(0.0F, this.above, 0.0F);
               }

               com.cheatbreaker.client.kazerui.module.modules.hud.elements.HotbarStripRenderer.a(0.0F, 0.0F, this.vertical, this.count, ArmorStatusModule.this.roundedCorners.b(), 1.0F);
               int var2 = 0;

               for (com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusItemElement var4 : var1) {
                  if (var4.item != null || !ArmorStatusModule.this.hideEmptySlots.b()) {
                     int var5 = var2++ * 20;
                     int var6 = 3 + (this.vertical ? 0 : var5);
                     int var7 = 3 + (this.vertical ? var5 : 0);
                     if (var4.item == null) {
                        ResourceLocation var8 = com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.c(var4.slot);
                        if (var8 != null) {
                           com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var8, (float)var6, (float)var7, 16.0F, 16.0F, -1);
                        }
                     } else {
                        var4.b(var6, var7);
                        this.a(var4, var6, var7);
                     }
                  }
               }

               GlStateManager.popMatrix();
               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            }
         }

         private void a() {
            ScaledResolution var1 = new ScaledResolution(Minecraft.getMinecraft());
            float var2 = 1.0F;
            int var3 = Math.round(var1.getScaledWidth() / var2);
            int var4 = Math.round(var1.getScaledHeight() / var2);
            float var5 = var3 / 2 - 91;
            float var6 = var4 - 22;
            float var7 = this.leftSide ? var5 - 7.0F - this.stripWidth : var5 + 182.0F + 7.0F;
            GlStateManager.scale(var2, var2, 1.0F);
            GlStateManager.translate(var7, var6 + 22.0F - this.stripHeight, 0.0F);
         }

         private boolean a(ItemStack var1) {
            return ArmorStatusModule.this.lowDurabilityIndicator.b() && com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.a(var1, ArmorStatusModule.this.lowDurabilityThreshold.c());
         }

         private void a(com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusItemElement var1, int var2, int var3) {
            String[] var4 = new String[1];
            com.cheatbreaker.client.kazerui.module.settings.ColorSetting var5 = var1.a(false, var4);
            String var6 = var4[0];
            boolean var7 = this.a(var1.item);
            if (!var6.isEmpty() || var7) {
               float var8 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var6) * this.textScale;
               float var9 = var6.isEmpty() ? 0.0F : Math.round(com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.b() * this.textScale);
               if (!this.vertical) {
                  this.a(var5, var6, var2 + this.a(var8), -var9);
                  if (var7) {
                     this.a(var2 + this.a(7.0F) - 1.0F, -var9 - 1.0F - 7.0F);
                  }
               } else {
                  float var10 = this.leftSide ? -var8 - 1.0F : this.stripWidth + 2.0F;
                  float var11 = this.leftSide ? -var8 - 7.0F - 3.0F : this.stripWidth + var8 + 3.0F;
                  this.a(var5, var6, var10, var3 + this.a(var9));
                  if (var7) {
                     this.a(var11, var3 + this.a(7.0F) - 0.5F);
                  }
               }
            }
         }

         private float a(float var1) {
            return Math.round((16.0F - var1) / 2.0F);
         }

         private void a(com.cheatbreaker.client.kazerui.module.settings.ColorSetting var1, String var2, float var3, float var4) {
            if (!var2.isEmpty()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(this.textScale, this.textScale, 1.0F);
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(
                  var1, var2, Math.round(var3 / this.textScale), Math.round(var4 / this.textScale), ArmorStatusModule.this.textShadow.b()
               );
               GlStateManager.popMatrix();
            }
         }

         private void a(float var1, float var2) {
            int var3 = -12641766;
            int var4 = -38037;

            for (int var5 = 0; var5 < 6; var5++) {
               float var6 = Math.min(7, 3 + var5 / 2 * 2);
               float var7 = var1 + (7.0F - var6) / 2.0F;
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var7 + 1.0F, var2 + var5 + 1.0F, var6, 1.0F, var3);
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var7, var2 + var5, var6, 1.0F, var4);
            }

            float var8 = var1 + 3.5F;
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var8 - 0.5F, var2 + 1.0F, 1.0F, 2.0F, var3);
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var8 - 0.5F, var2 + 4.0F, 1.0F, 1.0F, var3);
         }
      }
   }

   public static enum c implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
      VERTICAL("vertical"),
      HORIZONTAL("horizontal");

      private final String id;

      private c(String var3) {
         this.id = var3;
      }

      @Override
      public String a() {
         return this.id;
      }
   }

   public static enum d implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
      TOP("top"),
      BOTTOM("bottom"),
      RIGHT("right"),
      LEFT("left");

      private final String id;

      private d(String var3) {
         this.id = var3;
      }

      @Override
      public String a() {
         return this.id;
      }
   }

   public static enum e implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
      RIGHT("right"),
      LEFT("left");

      private final String id;

      private e(String var3) {
         this.id = var3;
      }

      @Override
      public String a() {
         return this.id;
      }
   }

   public static enum f {
      HELD_ITEM(-1, "heldItem"),
      HELMET(3, "helmet"),
      CHESTPLATE(2, "chestplate"),
      LEGGINGS(1, "leggings"),
      BOOTS(0, "boots");

      public final int armorSlot;
      public final String langId;

      private f(int var3, String var4) {
         this.armorSlot = var3;
         this.langId = var4;
      }

      public boolean a() {
         return this == HELD_ITEM;
      }
   }
}
