package com.cheatbreaker.client.kazerui.module.modules.hud;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;

public final class PotionEffectsModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule instance;
   private static final ResourceLocation INVENTORY = new ResourceLocation("textures/gui/container/inventory.png");
   private static final ResourceLocation INVENTORY_LEGACY = new ResourceLocation("kazerui", "textures/misc/potions_inventory_legacy.png");
   private static final Map<Integer, Integer> COLORS = new HashMap<>();
   private final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.d> potionEffectsMode = this.a(
      "potionEffectsMode", com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.d.NORMAL
   );
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting minimalModeHorizontal = this.a("minimalModeHorizontal", false);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting minimalModeTilesPerLine = this.a("minimalModeTilesPerLine", 4, 1, 10);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting vanillaGroupEffects = this.a("vanillaGroupEffects", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting vanillaModeHorizontal = this.a("vanillaModeHorizontal", true);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting vanillaModeTilesPerLine = this.a("vanillaModeTilesPerLine", 10, 1, 10);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting vanillaSpacing = this.a("vanillaSpacing", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting effectName = this.a("effectName", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting effectDuration = this.a("effectDuration", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting effectAmplifier = this.a("effectAmplifier", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showInInventory = this.a("showInInventory", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showWhileTyping = this.a("showWhileTyping", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting potionBlink = this.a("potionBlink", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting potionBlinkIcon = this.a("potionBlinkIcon", false);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting blinkDuration = this.a("blinkDuration", 10, 2, 20);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting uppercasePotionNames = this.a("uppercasePotionNames", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting reversedText = this.a("reversedText", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting formattedDurations = this.a("formattedDurations", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting hidePotionStatus = this.a("hidePotionStatus", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting background = this.a("background", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting backgroundColor = this.a("backgroundColor", 1862270976);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting border = this.a("border", false);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting borderThickness = this.a("borderThickness", 0.5F, 0.5F, 3.0F);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting borderColor = this.a("borderColor", -1627389952);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting textShadow = this.a("textShadow", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting colorNameBasedOnEffect = this.a("colorNameBasedOnEffect", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting colorInfoBasedOnEffect = this.a("colorInfoBasedOnEffect", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting textColor = this.a("textColor", -1);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting infoColor = this.a("infoColor", -1);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting excludePerm = this.a("excludePerm", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting effectBars = this.a("effectBars", false);
   private final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.a> effectBarPosition = this.a(
      "effectBarPosition", com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.a.BOTTOM
   );
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting effectBarVanillaHud = this.a("effectBarVanillaHud", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting effectBarInventory = this.a("effectBarInventory", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting effectBarKazerHud = this.a("effectBarKazerHud", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting effectBarGradient = this.a("effectBarGradient", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting effectBarCustomColor = this.a("effectBarCustomColor", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting effectBarColor = this.a("effectBarColor", -1).b();
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showEffectBackground = this.a("showEffectBackground", true);
   private final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.b> durationPosition = this.a(
      "durationPosition", com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.b.BOTTOM_LEFT
   );
   private final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.b> amplifierPosition = this.a(
      "amplifierPosition", com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.b.TOP_RIGHT
   );
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting vanillaIconScale = this.a("vanillaIconScale", 1.0F, 0.5F, 1.25F);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting vanillaTextScale = this.a("vanillaTextScale", 1.0F, 0.25F, 2.0F);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting useMinecraftGUIScale = this.a("useMinecraftGUIScale", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting vanillaBlinkColor = this.a("vanillaBlinkColor", -1);
   private static final boolean HIDE_AMBIENT_DURATION = false;
   private static final boolean EFFECT_BAR_HIDE_AMBIENT = true;
   private final Map<Integer, com.cheatbreaker.client.kazerui.module.settings.ToggleSetting> excludes = new LinkedHashMap<>();
   private final com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.c hud;
   private final Map<Integer, int[]> maxDurations = new HashMap<>();
   private final Map<Integer, PotionEffect> trackedEffects = new HashMap<>();

   public PotionEffectsModule() {
      super("POTION_EFFECTS", true);
      instance = this;
      String[] var1 = new String[]{
         "excludeSpeed",
         "excludeSlowness",
         "excludeHaste",
         "excludeMiningFatigue",
         "excludeStrength",
         "excludeInstantHealth",
         "excludeInstantDamage",
         "excludeJumpBoost",
         "excludeNausea",
         "excludeRegen",
         "excludeResistance",
         "excludeFireRes",
         "excludeWaterBreathing",
         "excludeInvis",
         "excludeBlindness",
         "excludeNightVision",
         "excludeHunger",
         "excludeWeakness",
         "excludePoison",
         "excludeWither",
         "excludeHealthBoost",
         "excludeAbsorption",
         "excludeSaturation"
      };

      for (int var2 = 0; var2 < var1.length; var2++) {
         this.excludes.put(var2 + 1, this.a(var1[var2], false));
      }

      this.hud = this.a(new com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.c());
   }

   private boolean a(com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.d var1) {
      return this.potionEffectsMode.j() == var1;
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(
         "generalOptions",
         var1x -> {
            var1x.a(this.potionEffectsMode);
            var1x.a(this.vanillaGroupEffects, this.vanillaModeHorizontal, this.vanillaModeTilesPerLine, this.vanillaSpacing)
               .a(() -> !this.a(com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.d.VANILLA));
            var1x.a(this.minimalModeHorizontal, this.minimalModeTilesPerLine).a(() -> !this.a(com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.d.MINIMAL));
            var1x.a(this.effectName).a(() -> !this.a(com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.d.NORMAL));
            var1x.a(this.effectDuration);
            var1x.a(this.effectAmplifier).a(() -> this.a(com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.d.MINIMAL));
            var1x.a(this.formattedDurations).a(() -> this.a(com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.d.VANILLA));
            var1x.a(this.uppercasePotionNames, this.reversedText).a(() -> !this.a(com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.d.NORMAL));
            var1x.a(this.showWhileTyping);
            var1x.a(this.potionBlink, var1xx -> {
               var1xx.a(this.potionBlinkIcon, this.blinkDuration);
               var1xx.a(this.vanillaBlinkColor).a(() -> !this.a(com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.d.VANILLA));
            });
            var1x.a(this.showInInventory);
            var1x.a(this.hidePotionStatus);
            var1x.a(this.effectBars, var1xx -> {
               var1xx.a(this.effectBarPosition, this.effectBarVanillaHud, this.effectBarInventory, this.effectBarKazerHud);
               var1xx.a(this.effectBarGradient).a(() -> this.effectBarCustomColor.b() && this.effectBarColor.f());
               var1xx.a(this.effectBarCustomColor, var1xxx -> var1xxx.a(this.effectBarColor));
            });
         }
      );
      var1.a(
         "hudOptions",
         var1x -> {
            var1x.a(this.durationPosition, this.amplifierPosition).a(() -> !this.a(com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.d.VANILLA));
            var1x.a(this.background, var1xx -> var1xx.a(this.backgroundColor));
            var1x.a(this.border, var1xx -> var1xx.a(this.borderThickness, this.borderColor));
            var1x.a(this.textShadow);
            var1x.a(this.colorNameBasedOnEffect).a(() -> !this.a(com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.d.NORMAL));
            var1x.a(this.colorInfoBasedOnEffect);
            var1x.a(this.showEffectBackground).a(() -> !this.a(com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.d.VANILLA));
            var1x.a(this.textColor).a(() -> this.colorNameBasedOnEffect.b() || !this.a(com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.d.NORMAL));
            var1x.a(this.infoColor).a(this.colorInfoBasedOnEffect::b);
            var1x.a(this.vanillaIconScale, this.vanillaTextScale, this.useMinecraftGUIScale)
               .a(() -> !this.a(com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.d.VANILLA));
            var1x.a(this.hud.scale).a(this::r);
         }
      );
      var1.a("excludePotionEffects", var1x -> {
         var1x.a(this.excludePerm);
         ArrayList var2 = new ArrayList<>(this.excludes.values());
         Collections.sort(var2, Comparator.<com.cheatbreaker.client.kazerui.module.settings.ToggleSetting, String>comparing(var0 -> com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("settings", var0.key)));

         for (com.cheatbreaker.client.kazerui.module.settings.ToggleSetting var4 : (Iterable<com.cheatbreaker.client.kazerui.module.settings.ToggleSetting>) var2) {
            var1x.a(var4);
         }
      });
   }

   private boolean r() {
      return this.a(com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.d.VANILLA) && this.useMinecraftGUIScale.b();
   }

   private boolean s() {
      return this.effectBars.b() && this.effectBarKazerHud.b();
   }

   private boolean d(PotionEffect var1) {
      if (this.excludePerm.b() && a(var1)) {
         return true;
      } else {
         com.cheatbreaker.client.kazerui.module.settings.ToggleSetting var2 = this.excludes.get(var1.getPotionID());
         return var2 != null && var2.b();
      }
   }

   static boolean a(PotionEffect var0) {
      return var0.getIsPotionDurationMax() || var0.getDuration() == -1 || var0.getDuration() > 72000;
   }

   static int b(PotionEffect var0) {
      Integer var1 = COLORS.get(var0.getPotionID());
      if (var1 != null) {
         return var1;
      } else {
         Potion var2 = e(var0);
         return var2 != null && var2.isBadEffect() ? -7335920 : -15691760;
      }
   }

   private static Potion e(PotionEffect var0) {
      int var1 = var0.getPotionID();
      return var1 >= 0 && var1 < Potion.potionTypes.length ? Potion.potionTypes[var1] : null;
   }

   @Override
   public void a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a var1) {
      if (var1.phase == com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b.END) {
         this.t();
         if (this.a()) {
            this.hud.m();
         }
      }
   }

   private void t() {
      Minecraft var1 = Minecraft.getMinecraft();
      if (var1.thePlayer == null) {
         this.maxDurations.clear();
         this.trackedEffects.clear();
      } else {
         HashMap var2 = new HashMap();

         for (PotionEffect var4 : var1.thePlayer.getActivePotionEffects()) {
            int var5 = var4.getPotionID();
            var2.put(var5, var4);
            int[] var6 = this.maxDurations.get(var5);
            if (var6 == null || this.trackedEffects.get(var5) != var4 || var4.getAmplifier() > var6[1]) {
               var6 = new int[]{0, var4.getAmplifier()};
               this.maxDurations.put(var5, var6);
            }

            var6[0] = Math.max(var6[0], var4.getDuration());
            var6[1] = var4.getAmplifier();
         }

         this.trackedEffects.clear();
         this.trackedEffects.putAll(var2);
         this.maxDurations.keySet().retainAll(var2.keySet());
      }
   }

   private int f(PotionEffect var1) {
      int[] var2 = this.maxDurations.get(var1.getPotionID());
      return var2 != null && this.trackedEffects.get(var1.getPotionID()) == var1 ? var2[0] : 0;
   }

   public static boolean n() {
      return instance != null && instance.a() && !instance.showInInventory.b();
   }

   public static void a(InventoryEffectRenderer var0, int var1, int var2, int var3, int var4, int var5, int var6, PotionEffect var7) {
      var0.drawTexturedModalRect(var1, var2, var3, var4, var5, var6);
      com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule var8 = instance;
      if (var8 != null && var8.a() && var8.effectBars.b() && var8.effectBarInventory.b() && var7 != null) {
         var8.a(var7, var1 + 3, var2 + 3, 114.0F, 26.0F);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         Minecraft.getMinecraft().getTextureManager().bindTexture(INVENTORY);
      }
   }

   private void a(PotionEffect var1, float var2, float var3, float var4, float var5) {
      if (!var1.getIsAmbient() && !a(var1)) {
         float var6 = this.g(var1);
         if (!(var6 <= 0.0F)) {
            float var7 = 1.0F;
            switch ((com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.a)this.effectBarPosition.j()) {
               case LEFT:
                  this.b(var1, var2, var3, var7, var5, var6);
                  break;
               case RIGHT:
                  this.b(var1, var2 + var4 - var7, var3, var7, var5, var6);
                  break;
               case TOP:
                  this.a(var1, var2, var3, var4, var7, var6);
                  break;
               case BOTTOM:
                  this.a(var1, var2, var3 + var5 - var7, var4, var7, var6);
                  break;
               case BORDER:
                  this.a(var1, var2, var3, var4, var5, var7, var6);
            }
         }
      }
   }

   private float g(PotionEffect var1) {
      float var2 = this.f(var1);
      return var2 <= 0.0F ? 1.0F : Math.max(0.0F, Math.min(1.0F, var1.getDuration() / var2));
   }

   private void a(PotionEffect var1, float var2, float var3, float var4, float var5, float var6) {
      this.a(var1, var2, var3, var4 * var6, var5, com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.e.LEFT, var6, 0.0F);
   }

   private void b(PotionEffect var1, float var2, float var3, float var4, float var5, float var6) {
      float var7 = var5 * var6;
      this.a(var1, var2, var3 + var5 - var7, var4, var7, com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.e.BOTTOM, var6, 0.0F);
   }

   private void a(PotionEffect var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      float var8 = var5 - var6 * 2.0F;
      float var9 = (var4 + var8) * 2.0F;
      float var10 = var4 / var9;
      float var11 = (var4 + var8) / var9;
      float var12 = (var4 * 2.0F + var8) / var9;
      float var13 = var7 * var9;
      float var14 = Math.min(var13, var4);
      if (var14 > 0.0F) {
         this.a(var1, var2, var3 + var5 - var6, var14, var6, com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.e.LEFT, var14 / var9, 0.0F);
      }

      var13 -= var4;
      if ((var14 = Math.min(var13, var8)) > 0.0F) {
         this.a(
            var1,
            var2 + var4 - var6,
            var3 + var5 - var6 - var14,
            var6,
            var14,
            com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.e.BOTTOM,
            var10 + var14 / var9,
            var10
         );
      }

      var13 -= var8;
      if ((var14 = Math.min(var13, var4)) > 0.0F) {
         this.a(var1, var2 + var4 - var14, var3, var14, var6, com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.e.RIGHT, var11 + var14 / var9, var11);
      }

      var13 -= var4;
      if ((var14 = Math.min(var13, var8)) > 0.0F) {
         this.a(var1, var2, var3 + var6, var6, var14, com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.e.TOP, var12 + var14 / var9, var12);
      }
   }

   private void a(
      PotionEffect var1, float var2, float var3, float var4, float var5, com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.e var6, float var7, float var8
   ) {
      int var9 = b(var1);
      boolean var10 = this.effectBarCustomColor.b();
      boolean var11 = var10 && this.effectBarColor.f();
      boolean var12 = !var11 && this.effectBarGradient.b();
      if (!var11 && !var12) {
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var2, var3, var4, var5, var10 ? this.effectBarColor.e() : var9);
      } else {
         boolean var13 = !var6.a();
         float var14 = var13 ? var4 : var5;
         float var15 = var13 ? var5 : var4;
         float var16 = var13 ? var2 : var3;
         float var17 = var13 ? var3 : var2;
         float var18 = var17 + var15 / 2.0F;
         boolean var19 = var6 == com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.e.LEFT
            || var6 == com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.e.TOP;
         float var20 = var19 ? var8 : var7;
         float var21 = var19 ? var7 : var8;
         int var22 = var10 ? this.effectBarColor.a(var18 + var16) : var9;
         int var23 = var12 ? a(var22, var20) : var22;
         float var24 = var16;

         for (int var25 = 1; var25 <= 6; var25++) {
            float var26 = var25 / 6.0F;
            float var27 = var24;
            var24 = var16 + var14 * var26;
            int var28 = var11 ? this.effectBarColor.a(var18 + var24) : var22;
            if (var12) {
               var28 = a(var28, var20 + (var21 - var20) * var26);
            }

            if (var13) {
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var27, var17, var24, var17 + var15, var23, var28, true);
            } else {
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var17, var27, var17 + var15, var24, var23, var28, false);
            }

            var23 = var28;
         }
      }
   }

   private static int a(int var0, float var1) {
      double var2 = var1 + System.nanoTime() / 2.5E9;
      float var4 = (float)(var2 - Math.floor(var2));
      var4 = 0.4F + 0.6F * (var4 < 0.5F ? var4 * 2.0F : 2.0F - var4 * 2.0F);
      if (var4 >= 1.0F) {
         return var0;
      } else {
         return var4 <= 0.0F
            ? var0 & 0xFF000000
            : var0 & 0xFF000000 | (int)((var0 >> 16 & 0xFF) * var4) << 16 | (int)((var0 >> 8 & 0xFF) * var4) << 8 | (int)((var0 & 0xFF) * var4);
      }
   }

   static {
      int[] var0 = new int[]{
         -11141121,
         -10851199,
         -2506685,
         -11910633,
         -7134173,
         -515037,
         -5675670,
         -1,
         -11199158,
         -3318613,
         -7387667,
         -1795526,
         -13741415,
         -8420462,
         -14737629,
         -14737503,
         -10979757,
         -12038840,
         -11627727,
         -13293017,
         -492253,
         -14331227,
         -515037,
         -7036831,
         -3211265,
         -10895098,
         -4152243
      };

      for (int var1 = 0; var1 < var0.length; var1++) {
         COLORS.put(var1 + 1, var0[var1]);
      }
   }

   public static enum a implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
      LEFT("left"),
      RIGHT("right"),
      TOP("top"),
      BOTTOM("bottom"),
      BORDER("border");

      private final String id;

      private a(String var3) {
         this.id = var3;
      }

      @Override
      public String a() {
         return this.id;
      }
   }

   public static enum b implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
      TOP_LEFT("topLeft"),
      BOTTOM_LEFT("bottomLeft"),
      TOP_RIGHT("topRight"),
      BOTTOM_RIGHT("bottomRight");

      private final String id;

      private b(String var3) {
         this.id = var3;
      }

      @Override
      public String a() {
         return this.id;
      }
   }

   private final class c extends com.cheatbreaker.client.kazerui.module.modulerender.HudElement {
      private List<PotionEffect> samples;
      private final List<PotionEffect> effects = new ArrayList<>();
      private final int[] blinkTicks = new int[3];
      private final List<PotionEffect> good = new ArrayList<>();
      private final List<PotionEffect> bad = new ArrayList<>();
      private boolean preview;
      private final com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.c.a[] textCache = new com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.c.a[Potion.potionTypes.length];

      private com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.c.a a(PotionEffect var1) {
         int var2 = var1.getPotionID();
         if (var2 >= 0 && var2 < this.textCache.length) {
            com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.c.a var3 = this.textCache[var2];
            if (var3 == null) {
               this.textCache[var2] = var3 = new com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.c.a();
            }

            return var3;
         } else {
            return null;
         }
      }

      private String a(PotionEffect var1, boolean var2, boolean var3) {
         com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.c.a var4 = this.a(var1);
         long var5 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.d().revision;
         long var7 = com.cheatbreaker.client.kazerui.RenderEpoch.a();
         String var9 = var1.getEffectName();
         int var10 = var1.getAmplifier();
         String var11 = var4 == null ? null : var4.label.a(var10, 0, var5, var7, var9);
         if (var11 != null) {
            return var11;
         } else {
            StringBuilder var12 = new StringBuilder();
            if (var2) {
               String var13 = I18n.format(var9, new Object[0]);
               var12.append(PotionEffectsModule.this.uppercasePotionNames.b() ? var13.toUpperCase(Locale.ROOT) : var13);
               if (var3) {
                  var12.append(" ");
               }
            }

            if (var3) {
               var12.append(this.d(var1));
            }

            String var14 = var12.toString();
            if (var4 != null) {
               var4.label.a(var10, 0, var5, var7, var9, var14);
            }

            return var14;
         }
      }

      c() {
         super(PotionEffectsModule.this, 0.0F, 0.0F, com.cheatbreaker.client.kazerui.hud.HudAnchor.MIDDLE_LEFT);
      }

      private List<PotionEffect> n() {
         if (this.samples == null) {
            this.samples = Arrays.asList(new PotionEffect(1, 1200, 3), new PotionEffect(5, 30, 3));
         }

         return this.samples;
      }

      @Override
      public void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      }

      @Override
      public float h() {
         return PotionEffectsModule.this.r() ? 1.0F : super.h();
      }

      void m() {
         this.effects.clear();
         Minecraft var1 = Minecraft.getMinecraft();
         if (var1.thePlayer != null) {
            for (PotionEffect var3 : var1.thePlayer.getActivePotionEffects()) {
               if (!PotionEffectsModule.this.d(var3)) {
                  this.effects.add(var3);
               }
            }
         }

         this.preview = var1.currentScreen instanceof com.cheatbreaker.client.kazerui.gui.HudMoveScreen;
         if (this.effects.isEmpty() && this.preview) {
            this.effects.addAll(this.n());
         }

         this.blinkTicks[PotionEffectsModule.this.potionEffectsMode.j().ordinal()]++;
         if (PotionEffectsModule.this.a(com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.d.VANILLA)) {
            this.p();
         }
      }

      @Override
      public boolean a(boolean var1) {
         if (PotionEffectsModule.this.hidePotionStatus.b()) {
            this.a(0.0F, 0.0F);
            return false;
         } else {
            Minecraft var2 = Minecraft.getMinecraft();
            if (var2.thePlayer != null && !PotionEffectsModule.this.showWhileTyping.b() && var2.ingameGUI.getChatGUI().getChatOpen()) {
               this.a(0.0F, 0.0F);
               return false;
            } else if (!var1 && this.effects.isEmpty()) {
               this.a(0.0F, 0.0F);
               return false;
            } else {
               List var3 = this.effects.isEmpty() && var1 ? this.n() : this.effects;
               if (PotionEffectsModule.this.a(com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.d.VANILLA) && var3 == this.samples) {
                  this.a(var3);
               }

               this.a(var3, false);
               return true;
            }
         }
      }

      @Override
      public void b(boolean var1) {
         this.a(this.effects.isEmpty() && var1 ? this.n() : this.effects, true);
      }

      private void a(List<PotionEffect> var1, boolean var2) {
         switch ((com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.d)PotionEffectsModule.this.potionEffectsMode.j()) {
            case NORMAL:
               this.b(var1, var2);
               break;
            case MINIMAL:
               this.c(var1, var2);
               break;
            case VANILLA:
               this.c(var2);
         }
      }

      private void a(float var1, float var2, float var3, float var4) {
         if (PotionEffectsModule.this.background.b()) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(PotionEffectsModule.this.backgroundColor, var1, var2, var3, var4);
         }

         if (PotionEffectsModule.this.border.b()) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(PotionEffectsModule.this.borderColor, var1, var2, var3, var4, PotionEffectsModule.this.borderThickness.b());
         }
      }

      private void a(PotionEffect var1, float var2, float var3) {
         Potion var4 = com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.e(var1);
         if (var4 != null && var4.hasStatusIcon()) {
            int var5 = var4.getStatusIconIndex();
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(
               com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.INVENTORY,
               var2,
               var3,
               var5 % 8 * 18,
               198 + var5 / 8 * 18,
               18.0F,
               18.0F,
               256.0F,
               256.0F,
               (int)(this.g(var1) * 255.0F) << 24 | 16777215
            );
         }
      }

      private void a(PotionEffect var1, String var2, float var3, float var4, com.cheatbreaker.client.kazerui.module.settings.ColorSetting var5, boolean var6) {
         if (var6) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var2, var3, var4, com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.b(var1), PotionEffectsModule.this.textShadow.b());
         } else {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var5, var2, var3, var4, PotionEffectsModule.this.textShadow.b());
         }
      }

      private String b(PotionEffect var1) {
         com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.c.a var2 = this.a(var1);
         long var3 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.d().revision;
         int var5 = var1.getDuration() / 20;
         int var6 = (com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.a(var1) ? 1 : 0) | (var1.getIsPotionDurationMax() ? 2 : 0);
         String var7 = var2 == null ? null : var2.duration.a(var5, var6, var3, 0L, null);
         if (var7 != null) {
            return var7;
         } else {
            String var8 = this.c(var1);
            if (var2 != null) {
               var2.duration.a(var5, var6, var3, 0L, null, var8);
            }

            return var8;
         }
      }

      private String c(PotionEffect var1) {
         if (PotionEffectsModule.this.a(com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.d.VANILLA)) {
            if (com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.a(var1)) {
               return "∞";
            } else {
               int var2 = var1.getDuration() / 20;
               if (var2 >= 3600) {
                  return var2 / 3600 + "h";
               } else if (var2 >= 600) {
                  return var2 / 60 + "m";
               } else {
                  return var2 >= 60 ? var2 / 60 + ":" + (var2 % 60 < 10 ? "0" : "") + var2 % 60 : Integer.toString(var2);
               }
            }
         } else if (PotionEffectsModule.this.formattedDurations.b()) {
            return com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.a(var1) ? "**:**" : this.a((long)(var1.getDuration() / 20.0F) * 1000L);
         } else {
            return var1.getIsPotionDurationMax() ? "**:**" : StringUtils.ticksToElapsedTime(var1.getDuration());
         }
      }

      private String a(long var1) {
         if (var1 < 0L) {
            return "now";
         } else {
            String var3 = " " + DurationFormatUtils.formatDuration(var1, "d'd 'H'h 'm'm 's's'");
            String var4 = org.apache.commons.lang3.StringUtils.replaceOnce(var3, " 0d", "");
            if (var4.length() != var3.length()) {
               var3 = var4;
               var4 = org.apache.commons.lang3.StringUtils.replaceOnce(var4, " 0h", "");
               if (var4.length() != var4.length()) {
                  var3 = var4;
                  var4 = org.apache.commons.lang3.StringUtils.replaceOnce(var4, " 0m", "");
                  if (var4.length() != var4.length()) {
                     var3 = org.apache.commons.lang3.StringUtils.replaceOnce(var4, " 0s", "");
                  }
               }
            }

            var3 = var3.trim();
            return var3.isEmpty() ? "0s" : var3;
         }
      }

      private String d(PotionEffect var1) {
         int var2 = var1.getAmplifier();
         switch (var2) {
            case 1:
               return "II";
            case 2:
               return "III";
            case 3:
               return "IV";
            case 4:
               return "V";
            case 5:
               return "VI";
            case 6:
               return "VII";
            case 7:
               return "VIII";
            case 8:
               return "IX";
            case 9:
               return "X";
            default:
               return var2 > 9 ? String.valueOf(var2 + 1) : "";
         }
      }

      private boolean e(PotionEffect var1) {
         if (!this.f(var1)) {
            return true;
         } else {
            int var2 = PotionEffectsModule.this.potionEffectsMode.j().ordinal();
            if (this.blinkTicks[var2] > 20) {
               this.blinkTicks[var2] = 0;
            }

            return this.blinkTicks[var2] <= 10;
         }
      }

      private boolean f(PotionEffect var1) {
         if (PotionEffectsModule.this.potionBlink.b() && !var1.getIsAmbient()) {
            int var2 = var1.getDuration();
            return var2 >= 0 && var2 <= PotionEffectsModule.this.blinkDuration.c() * 20;
         } else {
            return false;
         }
      }

      private float g(PotionEffect var1) {
         if (PotionEffectsModule.this.potionBlinkIcon.b() && this.f(var1)) {
            int var2 = var1.getDuration();
            int var3 = PotionEffectsModule.this.blinkDuration.c() * 20;
            float var4 = this.a(var2 * 2.0F / var3, 0.0F, 0.5F);
            float var5 = this.a((1.0F - (float)var2 / var3) * 0.25F, 0.0F, 0.25F);
            return this.a(var4 + (float)Math.sin(var2 * Math.PI / 5.0) * var5, 0.0F, 1.0F);
         } else {
            return 1.0F;
         }
      }

      private float a(float var1, float var2, float var3) {
         return Math.max(var2, Math.min(var3, var1));
      }

      private com.cheatbreaker.client.kazerui.hud.HudAnchor.a o() {
         return this.c().horizontal;
      }

      private void b(List<PotionEffect> var1, boolean var2) {
         float var3 = 0.0F;
         float var4 = 0.0F;
         if (var2) {
            this.a(var3, var4, this.f(), this.g());
         }

         com.cheatbreaker.client.kazerui.hud.HudAnchor.a var5 = this.o();
         if (PotionEffectsModule.this.reversedText.b()) {
            if (var5 == com.cheatbreaker.client.kazerui.hud.HudAnchor.a.START) {
               var5 = com.cheatbreaker.client.kazerui.hud.HudAnchor.a.END;
            } else if (var5 == com.cheatbreaker.client.kazerui.hud.HudAnchor.a.END) {
               var5 = com.cheatbreaker.client.kazerui.hud.HudAnchor.a.START;
            }
         }

         float var6 = var3;
         if (var5 != com.cheatbreaker.client.kazerui.hud.HudAnchor.a.MIDDLE) {
            var6 = var3 + (var5 == com.cheatbreaker.client.kazerui.hud.HudAnchor.a.END ? -2.0F : 3.0F);
         }

         float var7 = var4 + 3.0F;
         float var8 = 0.0F;
         float var9 = 0.0F;

         for (PotionEffect var11 : var1) {
            if (var2 && PotionEffectsModule.this.s() && this.e(var11)) {
               PotionEffectsModule.this.a(var11, var3, var4 + var9, this.f(), 22.0F);
            }

            float var12 = 0.0F;
            boolean var13 = PotionEffectsModule.this.effectName.b();
            boolean var14 = PotionEffectsModule.this.effectAmplifier.b();
            if (var13 || var14) {
               String var15 = this.a(var11, var13, var14);
               var12 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var15) + 20.0F;
               if (var2) {
                  this.a(var11, var15, var6 + this.a(var5, var12), var7 + var9, PotionEffectsModule.this.textColor, PotionEffectsModule.this.colorNameBasedOnEffect.b());
               }

               var8 = Math.max(var8, var12);
            }

            if (PotionEffectsModule.this.effectDuration.b()) {
               String var17 = this.b(var11);
               float var16 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var17) + 20.0F;
               if (var2 && this.e(var11)) {
                  this.a(
                     var11, var17, var6 + this.a(var5, var16), var7 + var9 + (!var13 && !var14 ? 5 : 10), PotionEffectsModule.this.infoColor, PotionEffectsModule.this.colorInfoBasedOnEffect.b()
                  );
               }

               var8 = Math.max(var8, var16);
            }

            if (var2) {
               this.a(var11, var6 + this.b(var5, var12), var7 + var9);
            }

            var9 += 23.0F;
         }

         if (!var2) {
            this.a(Math.max(var8, 20.0F) + 7.0F, var9 - 1.0F);
         }
      }

      private float a(com.cheatbreaker.client.kazerui.hud.HudAnchor.a var1, float var2) {
         switch (var1) {
            case END:
               return this.f() - var2;
            case MIDDLE:
               return this.f() / 2.0F - var2 / 2.0F + 20.0F;
            default:
               return 20.0F;
         }
      }

      private float b(com.cheatbreaker.client.kazerui.hud.HudAnchor.a var1, float var2) {
         switch (var1) {
            case END:
               return this.f() - 20.0F;
            case MIDDLE:
               return this.f() / 2.0F - var2 / 2.0F;
            default:
               return 0.0F;
         }
      }

      private void c(List<PotionEffect> var1, boolean var2) {
         float var3 = PotionEffectsModule.this.border.b() ? PotionEffectsModule.this.borderThickness.b() * 2.0F + 3.0F : 3.0F;
         float var4 = Math.max(0.0F, var3 - 6.0F);
         float var5 = Math.max(0.0F, var3 - 6.0F);
         boolean var6 = PotionEffectsModule.this.minimalModeHorizontal.b();
         com.cheatbreaker.client.kazerui.hud.HudAnchor var7 = this.c();
         boolean var8 = var6 ? var7.vertical == com.cheatbreaker.client.kazerui.hud.HudAnchor.a.END : var7.horizontal == com.cheatbreaker.client.kazerui.hud.HudAnchor.a.END;
         int var9 = !PotionEffectsModule.this.background.b() && !PotionEffectsModule.this.border.b() ? 30 : 41;
         int var10 = PotionEffectsModule.this.minimalModeTilesPerLine.c();
         float var11 = var9 + var3;
         int var12 = var1.size();
         float var13 = var8 && var12 > var10 ? var12 / var10 * var11 : 0.0F;
         if (!var2) {
            int var19 = (var12 - 1) / var10 + 1;
            int var20 = Math.min(var12, var10);
            float var21 = var9 + (var20 - 1) * var11 + var3 - 3.0F;
            float var22 = var9 + (var19 - 1) * var11 + var3 - 3.0F;
            if (var6) {
               this.a(var21, var22);
            } else {
               this.a(var22, var21);
            }
         } else {
            for (int var14 = 0; var14 < var12; var14++) {
               int var15 = var14 / var10;
               int var16 = var14 % var10;
               float var17 = var16 * var11;
               float var18 = var13 + (var8 ? -var15 : var15) * var11;
               this.a((PotionEffect)var1.get(var14), var4 + (var6 ? var17 : var18), var5 + (var6 ? var18 : var17), var9);
            }
         }
      }

      private void a(PotionEffect var1, float var2, float var3, int var4) {
         this.a(var2, var3, var4, var4);
         if (this.e(var1)) {
            if (PotionEffectsModule.this.s()) {
               PotionEffectsModule.this.a(var1, var2, var3, (float)var4, (float)var4);
            }

            if (PotionEffectsModule.this.effectDuration.b()) {
               String var5 = this.b(var1);
               float var6 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var5);
               this.a(var1, var5, var2 + var4 / 2.0F - var6 / 2.0F, var3 + var4 - var4 / 3.0F + 1.0F, PotionEffectsModule.this.infoColor, PotionEffectsModule.this.colorInfoBasedOnEffect.b());
            }
         }

         this.a(var1, var2 + var4 / 2.0F - 9.5F, var3 + var4 / 2.0F - 13.0F);
      }

      private void p() {
         this.a(this.effects);
      }

      private void a(List<PotionEffect> var1) {
         this.good.clear();
         this.bad.clear();
         boolean var2 = PotionEffectsModule.this.vanillaGroupEffects.b();

         for (PotionEffect var4 : var1) {
            Potion var5 = com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.e(var4);
            (var2 && var5 != null && var5.isBadEffect() ? this.bad : this.good).add(var4);
         }

         this.good.sort(this::a);
         this.bad.sort(this::a);
      }

      private void c(boolean var1) {
         if (this.good.isEmpty() && this.bad.isEmpty()) {
            if (!var1) {
               this.a(0.0F, 0.0F);
            }
         } else {
            boolean var2 = PotionEffectsModule.this.vanillaModeHorizontal.b();
            int var3 = PotionEffectsModule.this.vanillaModeTilesPerLine.c();
            int var4 = this.a(this.good.size(), var3);
            int var5 = this.a(this.bad.size(), var3);
            int var6 = Math.max(Math.min(this.good.size(), var3), Math.min(this.bad.size(), var3));
            float var7 = this.a(var6);
            float var8 = this.b(var4 + var5);
            float var9 = var2 ? var7 : var8;
            float var10 = var2 ? var8 : var7;
            if (!var1) {
               this.a(var9, var10);
            } else {
               this.a(0.0F, 0.0F, var9, var10);
               this.a(this.good, var2, var3, 0, var7);
               this.a(this.bad, var2, var3, var4, var7);
            }
         }
      }

      private void a(List<PotionEffect> var1, boolean var2, int var3, int var4, float var5) {
         for (int var6 = 0; var6 < var1.size(); var6++) {
            int var7 = var6 / var3;
            int var8 = var6 % var3;
            int var9 = Math.min(var1.size() - var7 * var3, var3);
            float var10 = this.a(var2, var8, var9, var5);
            float var11 = (var4 + var7) * this.r();
            this.b((PotionEffect)var1.get(var6), var2 ? var10 : var11, var2 ? var11 : var10);
         }
      }

      private float a(boolean var1, int var2, int var3, float var4) {
         float var5 = this.a(var3);
         if (var1) {
            float var7;
            switch (this.c().horizontal) {
               case MIDDLE:
                  var7 = (var4 + var5) / 2.0F;
                  break;
               case START:
                  var7 = var5;
                  break;
               default:
                  var7 = var4;
            }

            return var7 - 24.0F - var2 * this.q();
         } else {
            float var6;
            switch (this.c().vertical) {
               case MIDDLE:
                  var6 = (var4 - var5) / 2.0F;
                  break;
               case START:
                  var6 = 0.0F;
                  break;
               default:
                  var6 = var4 - var5;
            }

            return var6 + var2 * this.q();
         }
      }

      private int q() {
         return PotionEffectsModule.this.vanillaSpacing.b() ? 25 : 27;
      }

      private int r() {
         return PotionEffectsModule.this.vanillaSpacing.b() ? 26 : 27;
      }

      private float a(int var1) {
         return var1 * this.q() - (this.q() - 24);
      }

      private float b(int var1) {
         return var1 * this.r() - (this.r() - 24);
      }

      private int a(int var1, int var2) {
         return (var1 + var2 - 1) / var2;
      }

      private void b(PotionEffect var1, float var2, float var3) {
         boolean var4 = var1.getIsAmbient();
         if (PotionEffectsModule.this.showEffectBackground.b()) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(
               com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.INVENTORY_LEGACY,
               var2,
               var3,
               var4 ? 165.0F : 141.0F,
               166.0F,
               24.0F,
               24.0F,
               256.0F,
               256.0F,
               -1
            );
         }

         float var5 = PotionEffectsModule.this.vanillaIconScale.b();
         GlStateManager.pushMatrix();
         GlStateManager.translate(var2 + 12.0F - 9.0F * var5, var3 + 12.0F - 9.0F * var5, 0.0F);
         GlStateManager.scale(var5, var5, 1.0F);
         this.a(var1, 0.0F, 0.0F);
         GlStateManager.popMatrix();
         if (this.e(var1)) {
            if (PotionEffectsModule.this.s()) {
               PotionEffectsModule.this.a(var1, var2 + 2.0F, var3 + 2.0F, 20.0F, 20.0F);
            }

            if (PotionEffectsModule.this.effectDuration.b()) {
               boolean var6 = !var4 && this.f(var1);
               this.a(
                  var1,
                  this.b(var1),
                  PotionEffectsModule.this.durationPosition.j(),
                  var2,
                  var3,
                  var6 ? PotionEffectsModule.this.vanillaBlinkColor : PotionEffectsModule.this.infoColor,
                  !var6 && PotionEffectsModule.this.colorInfoBasedOnEffect.b()
               );
            }
         }

         String var7;
         if (PotionEffectsModule.this.effectAmplifier.b() && !(var7 = this.d(var1)).isEmpty()) {
            this.a(var1, var7, PotionEffectsModule.this.amplifierPosition.j(), var2, var3, PotionEffectsModule.this.infoColor, PotionEffectsModule.this.colorInfoBasedOnEffect.b());
         }
      }

      private void a(
         PotionEffect var1,
         String var2,
         com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.b var3,
         float var4,
         float var5,
         com.cheatbreaker.client.kazerui.module.settings.ColorSetting var6,
         boolean var7
      ) {
         float var8 = this.s();
         float var9 = PotionEffectsModule.this.vanillaTextScale.b() * 0.5F;
         if (var8 > 0.0F) {
            var9 = Math.max(1, Math.round(var9 * var8)) / var8;
         }

         float var10 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var2) * var9;
         float var11 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.b() * var9;
         boolean var12 = var3 == com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.b.TOP_RIGHT
            || var3 == com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.b.BOTTOM_RIGHT;
         boolean var13 = var3 == com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.b.BOTTOM_LEFT
            || var3 == com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.b.BOTTOM_RIGHT;
         float var14 = var4 + (var12 ? 21.0F - var10 : 3.25F);
         float var15 = var5 + (var13 ? 22.0F - var11 : 3.0F);
         GlStateManager.pushMatrix();
         GlStateManager.translate(var8 > 0.0F ? Math.round(var14 * var8) / var8 : var14, var8 > 0.0F ? Math.round(var15 * var8) / var8 : var15, 0.0F);
         GlStateManager.scale(var9, var9, 1.0F);
         this.a(var1, var2, 0.0F, 0.0F, var6, var7);
         GlStateManager.popMatrix();
      }

      private float s() {
         return PotionEffectsModule.this.r() ? new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor() : 0.0F;
      }

      private int a(PotionEffect var1, PotionEffect var2) {
         boolean var3 = var1.getIsAmbient();
         boolean var4 = var2.getIsAmbient();
         int var5 = var1.getDuration();
         int var6 = var2.getDuration();
         if ((var5 <= 32147 || var6 <= 32147) && (!var3 || !var4)) {
            int var8 = Boolean.compare(var4, var3);
            if (var8 == 0) {
               var8 = Boolean.compare(var6 < 0, var5 < 0);
            }

            if (var8 == 0) {
               var8 = Integer.compare(var6, var5);
            }

            if (var8 == 0) {
               var8 = Integer.compare(this.h(var2), this.h(var1));
            }

            return var8;
         } else {
            int var7 = Boolean.compare(var4, var3);
            return var7 != 0 ? var7 : Integer.compare(this.h(var2), this.h(var1));
         }
      }

      private int h(PotionEffect var1) {
         Integer var2 = com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule.COLORS.get(var1.getPotionID());
         return var2 != null ? var2 & 16777215 : 0;
      }

      private final class a {
         final com.cheatbreaker.client.kazerui.module.modulerender.HudTextCache label = new com.cheatbreaker.client.kazerui.module.modulerender.HudTextCache();
         final com.cheatbreaker.client.kazerui.module.modulerender.HudTextCache duration = new com.cheatbreaker.client.kazerui.module.modulerender.HudTextCache();

         private a() {
         }
      }
   }

   public static enum d implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
      NORMAL("normal"),
      MINIMAL("minimal"),
      VANILLA("vanilla");

      private final String id;

      private d(String var3) {
         this.id = var3;
      }

      @Override
      public String a() {
         return this.id;
      }
   }

   private static enum e {
      TOP,
      BOTTOM,
      RIGHT,
      LEFT;

      boolean a() {
         return this.ordinal() < 2;
      }
   }
}
