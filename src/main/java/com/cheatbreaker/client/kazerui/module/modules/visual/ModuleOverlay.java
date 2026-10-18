package com.cheatbreaker.client.kazerui.module.modules.visual;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOre;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;

public final class ModuleOverlay extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static ModuleOverlay instance;
   private long appliedRevision = Long.MIN_VALUE;
   private int blockSignature;
   private int textureSignature;
   private boolean blocksPending;
   private boolean palettePending;
   private long changedAt;
   private final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<ModuleOverlay.b> glint = this.a("glint", ModuleOverlay.b.ALL);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting minimalBob = this.a("minimalBob", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting mapBob = this.a("noMapBob", false);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting fireHeight = this.a("fireHeight", 0.0F, 0.0F, 1.0F);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting blockFireHeight = this.a("blockFireHeight", 0.0F, 0.0F, 1.0F);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting pumpkin = this.a("pumpkinOpacity", 0.0F, 0.0F, 1.0F);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting foliage = this.a("hideFoliage", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting shadows = this.a("entityShadows", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting groundArrows = this.a("groundArrows", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting stuckArrows = this.a("stuckArrows", true);
private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting skulls = this.a("hideSkulls", false);
   private final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<ModuleOverlay.c> portals = this.a("hideEndPortals", ModuleOverlay.c.NEVER);
   private final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<ModuleOverlay.a> glass = this.a("clearGlass", ModuleOverlay.a.OFF);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting coloredString = this.a("coloredString", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting stringColor = this.a("stringColor", -65536).b().c();
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting oreOutlines = this.a("oreOutlines", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting barrierOutlines = this.a("barrierOutlines", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting barrierColor = this.a("barrierColor", -1).b().c();
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting oreColor = this.a("oreColor", -1).b().c();
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting xpCustom = this.a("customXpColor", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting fishingCustom = this.a("customFishingLine", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting guiCustom = this.a("customGuiColors", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting xpColor = this.a("xpColor", -8323296).b().c();
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting fishingColor = this.a("fishingColor", -16777216).b();
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting guiColor = this.a("guiColor", -1).b().c();
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting headless = this.a("headlessHorse", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting death = this.a("disableDeathAnimation", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting damage = this.a("disableDamageOverlay", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting fire = this.a("disableFireOverlay", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting helmet = this.a("hideHelmet", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting chest = this.a("hideChestplate", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting legs = this.a("hideLeggings", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting boots = this.a("hideBoots", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting self = this.a("armorSelfOnly", true);

   public ModuleOverlay() {
      super("OVERLAY", false);
      instance = this;
      this.glint.a(() -> "Show Enchantment Glint");
      this.minimalBob.a(() -> "Minimal View Bobbing");
      this.mapBob.a(() -> "No Map Bobbing");
      this.fireHeight.a(() -> "Fire Height (Overlay)");
      this.blockFireHeight.a(() -> "Fire Height (Block)");
      this.pumpkin.a(() -> "Pumpkin Overlay");
      this.foliage.a(() -> "Hide Foliage");
      this.shadows.a(() -> "Entity Shadows");
      this.groundArrows.a(() -> "Show Ground Arrows");
      this.stuckArrows.a(() -> "Show Stuck Arrows");
      this.skulls.a(() -> "Hide Placed Skulls");
      this.portals.a(() -> "Hide Endportals");
      this.glass.a(() -> "Clear Glass");
      this.coloredString.a(() -> "Colored String");
      this.stringColor.a(() -> "String Color");
      this.oreOutlines.a(() -> "Ore Outlines");
      this.oreColor.a(() -> "Outline Color");
      this.oreOutlines.a(ModuleOverlay::n);
      this.oreColor.a(ModuleOverlay::n);
      this.barrierOutlines.a(() -> "Barrier Outlines");
      this.barrierColor.a(() -> "Barrier Color");
      this.barrierOutlines.a(ModuleOverlay::n);
      this.barrierColor.a(ModuleOverlay::n);
      this.xpCustom.a(() -> "Custom XP Color");
      this.xpColor.a(() -> "XP Bar / Level Color");
      this.fishingCustom.a(() -> "Custom Fishing Line");
      this.fishingColor.a(() -> "Fishing Line Color");
      this.guiCustom.a(() -> "Custom GUI Colors");
      this.guiColor.a(() -> "Container Texture Color");
      this.xpCustom.a(ModuleOverlay::o);
      this.xpColor.a(ModuleOverlay::o);
      this.guiCustom.a(ModuleOverlay::o);
      this.guiColor.a(ModuleOverlay::o);
      this.headless.a(() -> "Headless Horse");
      this.death.a(() -> "Disable Death Animation");
      this.damage.a(() -> "Disable Damage Overlay");
      this.fire.a(() -> "Disable Fire Overlay");
      this.helmet.a(() -> "Hide Helmet");
      this.chest.a(() -> "Hide Chestplate");
      this.legs.a(() -> "Hide Leggings");
      this.boots.a(() -> "Hide Boots");
      this.self.a(() -> "Only Hide for Self");
      this.foliage.a(ModuleOverlay::n);
      this.glass.a(ModuleOverlay::n);
      this.blockFireHeight.a(ModuleOverlay::n);
      this.coloredString.a(ModuleOverlay::n);
      this.stringColor.a(ModuleOverlay::n);
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a("First Person Options", var1x -> var1x.a(this.glint, this.minimalBob, this.mapBob, this.fireHeight, this.blockFireHeight, this.pumpkin));
      var1.a("World Options", var1x -> var1x.a(this.foliage, this.shadows, this.groundArrows, this.stuckArrows, this.skulls, this.portals));
      var1.a("Texture Options", var1x -> {
         var1x.a(this.glass);
         var1x.a(this.oreOutlines, var1xx -> var1xx.a(this.oreColor));
         var1x.a(this.coloredString, var1xx -> var1xx.a(this.stringColor));
         if ("1.8.9".equals("1.8.9")) {
            var1x.a(this.barrierOutlines, var1xx -> var1xx.a(this.barrierColor));
         }

         var1x.a(this.xpCustom, var1xx -> var1xx.a(this.xpColor));
         var1x.a(this.fishingCustom, var1xx -> var1xx.a(this.fishingColor));
         var1x.a(this.guiCustom, var1xx -> var1xx.a(this.guiColor));
      });
      var1.a("Entity Render Options", var1x -> var1x.a(this.headless, this.death, this.damage, this.fire));
      var1.a("Armor Options", var1x -> var1x.a(this.helmet, this.chest, this.legs, this.boots, this.self));
   }

   public static void migrateLegacy(com.cheatbreaker.client.settings.GlobalSettings var0) {
      if (instance != null) {
         com.cheatbreaker.client.kazerui.gui.components.MenuModel var1 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.d();
         String var2 = "migration.overlay.v1." + var1.a();
         if (!"true".equals(var1.store.a(var2, null))) {
a(var1, "glint", (Boolean) var0.enchantmentGlint.d() ? "ALL" : "NONE");
            a(var1, "clearGlass", String.valueOf(var0.clearGlass.d()));
            a(var1, "coloredString", String.valueOf(var0.redString.d()));
            var1.store.b(var2, "true");
            var1.revision++;
         }
      }
   }

   private static void a(com.cheatbreaker.client.kazerui.gui.components.MenuModel var0, String var1, String var2) {
      if (var0.a("overlay", var1, null) == null) {
         var0.b("overlay", var1, var2);
      }
   }

   @Override
   protected void e() {
      n();
      o();
   }

   @Override
   protected void f() {
      n();
      o();
   }

   private static void n() {
      if (instance != null) {
         instance.blocksPending = true;
         instance.changedAt = System.nanoTime();
      }
   }

   private static void o() {
      if (instance != null) {
         instance.palettePending = true;
         instance.changedAt = System.nanoTime();
      }
   }

   @Override
   public void a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a var1) {
      if (var1.phase == com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b.START) {
         long var2 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.d().revision;
         if (var2 != this.appliedRevision) {
            this.appliedRevision = var2;
            int var4 = this.p();
            int var5 = this.q();
            if (var4 != this.blockSignature) {
               this.blockSignature = var4;
               n();
            }

            if (var5 != this.textureSignature) {
               this.textureSignature = var5;
               o();
            }
         }

         if ((this.blocksPending || this.palettePending) && m() != null && (m().currentScreen == null || System.nanoTime() - this.changedAt >= 150000000L)) {
            if (this.blocksPending) {
               this.blocksPending = false;
               if (m().theWorld != null && m().renderGlobal != null) {
                  m().renderGlobal.loadRenderers();
               }
            }

            if (this.palettePending) {
               this.palettePending = false;
               OverlayTexturePalette.reload();
            }
         }
      }
   }

   private int p() {
      if (!this.a()) {
         return 0;
      } else {
         int var1 = 1;
         var1 = var1 * 31 + (this.foliage.b() ? 1 : 0);
         var1 = var1 * 31 + this.glass.j().ordinal();
         var1 = var1 * 31 + Float.floatToIntBits(this.blockFireHeight.b());
         var1 = var1 * 31 + (this.coloredString.b() ? this.stringColor.e() : 16777215);
         var1 = var1 * 31 + (this.oreOutlines.b() ? this.oreColor.e() : 0);
         return var1 * 31 + (this.barrierOutlines.b() ? this.barrierColor.e() : 0);
      }
   }

   private int q() {
      return !this.a() ? 0 : (this.xpCustom.b() ? this.xpColor.e() : 0) * 31 + (this.guiCustom.b() ? this.guiColor.e() : 0);
   }

   public static void paletteApplied() {
      if (instance != null) {
         instance.textureSignature = instance.q();
      }
   }

   private static ModuleOverlay r() {
      ModuleOverlay var0 = instance;
      return var0 != null && var0.a() ? var0 : null;
   }

   public static boolean glint(boolean var0, boolean var1) {
      ModuleOverlay var2 = r();
      if (var2 == null) {
         return ModuleGlintColorizer.visible(var0);
      } else {
         ModuleOverlay.b var3 = var2.glint.j();
         return ModuleGlintColorizer.visible(var3 == ModuleOverlay.b.ALL || (var1 ? var3 == ModuleOverlay.b.ARMOR : var3 == ModuleOverlay.b.HELD));
      }
   }

   public static float bobScale() {
      ModuleOverlay var0 = r();
      if (var0 == null) {
         return 1.0F;
      } else {
         ItemStack var1 = m().thePlayer == null ? null : m().thePlayer.getHeldItem();
         return var0.mapBob.b() && var1 != null && var1.getItem() instanceof ItemMap ? 0.0F : (var0.minimalBob.b() ? 0.2F : 1.0F);
      }
   }

   public static float fireOffset() {
      ModuleOverlay var0 = r();
      return var0 == null ? 0.0F : var0.fireHeight.b();
   }

   public static float blockFireOffset() {
      ModuleOverlay var0 = r();
      return var0 == null ? 0.0F : var0.blockFireHeight.b();
   }

   public static boolean blockTweaks() {
      ModuleOverlay var0 = r();
      return var0 != null && (var0.blockFireHeight.b() > 0.0F || var0.oreOutlines.b());
   }

   public static boolean outlines(Block var0) {
      ModuleOverlay var1 = r();
      return var1 != null && var1.oreOutlines.b() && (var0 instanceof BlockOre || var0 == Blocks.lit_redstone_ore || var0 == Blocks.redstone_ore);
   }

   public static int outlineColor() {
      ModuleOverlay var0 = r();
      return var0 == null ? -1 : var0.oreColor.a(0.0F);
   }

   public static boolean barriers() {
      ModuleOverlay var0 = r();
      return var0 != null && var0.barrierOutlines.b();
   }

   public static int barrierColor() {
      ModuleOverlay var0 = r();
      return var0 == null ? -1 : var0.barrierColor.a(0.0F);
   }

   public static boolean customXp() {
      ModuleOverlay var0 = r();
      return var0 != null && var0.xpCustom.b();
   }

   public static int xpColor(int var0) {
      ModuleOverlay var1 = r();
      return var1 != null && var1.xpCustom.b() ? var1.xpColor.a(0.0F) : var0;
   }

   public static boolean customGui() {
      ModuleOverlay var0 = r();
      return var0 != null && var0.guiCustom.b();
   }

   public static int guiColor() {
      ModuleOverlay var0 = r();
      return var0 == null ? -1 : var0.guiColor.a(0.0F);
   }

   public static int fishingColor(int var0) {
      ModuleOverlay var1 = r();
      return var1 != null && var1.fishingCustom.b() ? var1.fishingColor.a(0.0F) : var0;
   }

   public static float pumpkinAlpha() {
      ModuleOverlay var0 = r();
      return var0 == null ? 1.0F : var0.pumpkin.b();
   }

   public static boolean shadows() {
      ModuleOverlay var0 = r();
      return var0 == null || var0.shadows.b();
   }

   public static boolean groundArrows() {
      ModuleOverlay var0 = r();
      return var0 == null || var0.groundArrows.b();
   }

   public static boolean stuckArrows() {
      ModuleOverlay var0 = r();
      return var0 == null || var0.stuckArrows.b();
   }

   public static boolean hideSkulls() {
      ModuleOverlay var0 = r();
      return var0 != null && var0.skulls.b();
   }

   public static boolean hidePortals() {
      ModuleOverlay var0 = r();
      return var0 != null
         && (
            var0.portals.j() == ModuleOverlay.c.ALWAYS
               || var0.portals.j() == ModuleOverlay.c.OVERWORLD && m().theWorld != null && m().theWorld.provider.getDimensionId() == 0
         );
   }

   public static boolean hideBlock(Block var0) {
      ModuleOverlay var1 = r();
      return var1 != null
         && var1.foliage.b()
         && (var0 instanceof BlockLeaves || var0 == Blocks.tallgrass || var0 == Blocks.double_plant || var0 == Blocks.vine || var0 == Blocks.deadbush);
   }

   public static String glass(String var0) {
      ModuleOverlay var1 = r();
      return var1 == null ? var0 : var1.glass.j().name();
   }

   public static int stringColor(int var0) {
      ModuleOverlay var1 = r();
      return var1 == null ? var0 : (var1.coloredString.b() ? var1.stringColor.a(0.0F) : 16777215);
   }

   public static boolean headless() {
      ModuleOverlay var0 = r();
      return var0 != null && var0.headless.b();
   }

   public static boolean death() {
      ModuleOverlay var0 = r();
      return var0 != null && var0.death.b();
   }

   public static boolean damage() {
      ModuleOverlay var0 = r();
      return var0 != null && var0.damage.b();
   }

   public static boolean fire() {
      ModuleOverlay var0 = r();
      return var0 != null && var0.fire.b();
   }

   public static boolean hideArmor(Entity var0, int var1) {
      ModuleOverlay var2 = r();
      return var2 != null && var1 >= 0 && var1 <= 3 && (!var2.self.b() || var0 == m().thePlayer)
         ? (var1 == 0 ? var2.helmet : (var1 == 1 ? var2.chest : (var1 == 2 ? var2.legs : var2.boots))).b()
         : false;
   }

   public static int renderDeathTime(EntityLivingBase var0) {
      return death() ? 0 : var0.deathTime;
   }

   public static int renderDamageDeathTime(EntityLivingBase var0) {
      return damage() ? 0 : var0.deathTime;
   }

   public static int renderHurtTime(EntityLivingBase var0) {
      return damage() ? 0 : var0.hurtTime;
   }

   public static enum a implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
      OFF("Off"),
      REGULAR("Regular"),
      ALL("All");

      private final String label;

      private a(String var3) {
         this.label = var3;
      }

      @Override
      public String a() {
         return this.label;
      }
   }

   public static enum b implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
      ALL("All"),
      HELD("Items"),
      ARMOR("Armor"),
      NONE("None");

      private final String label;

      private b(String var3) {
         this.label = var3;
      }

      @Override
      public String a() {
         return this.label;
      }
   }

   public static enum c implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
      NEVER("Never"),
      ALWAYS("Always"),
      OVERWORLD("Overworld");

      private final String label;

      private c(String var3) {
         this.label = var3;
      }

      @Override
      public String a() {
         return this.label;
      }
   }
}
