package com.cheatbreaker.client.kazerui.module.modules.hud.elements;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ResourceLocation;

public final class ArmorStatusBarsChildModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static final ResourceLocation ICONS = new ResourceLocation("minecraft", "textures/gui/icons.png");
   private static final ResourceLocation GLINT = new ResourceLocation("minecraft", "textures/misc/enchanted_item_glint.png");
   private static final Map<ArmorMaterial, Integer> DEFAULTS = new EnumMap<>(ArmorMaterial.class);
   private final com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule status;
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting smartArmorColors = this.a("smartArmorColors", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showGlint = this.a("showGlint", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting lowDurabilityIndicator = this.a("lowDurabilityIndicator", true);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting lowDurabilityThreshold = this.a("lowDurabilityThreshold", 10, 1, 100);
   private final com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusBarsChildModule.b[] points = new com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusBarsChildModule.b[20];
   private final Map<ArmorMaterial, Integer> colors = new EnumMap<>(ArmorMaterial.class);
   private boolean lastSmart;
   private int iconBrightness = -1;
   private int splitX = 4;
   private boolean[] iconPixels = new boolean[81];

   public ArmorStatusBarsChildModule(com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule var1) {
      super("ARMORSTATUS_BARS_CHILD", false);
      this.status = var1;
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(this.smartArmorColors, this.showGlint);
      var1.a(this.lowDurabilityIndicator, var1x -> var1x.a(this.lowDurabilityThreshold));
   }

   @Override
   public void a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a var1) {
      if (var1.phase == com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b.END) {
         Arrays.fill(this.points, null);
         EntityPlayerSP var2 = Minecraft.getMinecraft().thePlayer;
         if (var2 != null && this.a()) {
            boolean var3 = this.smartArmorColors.b();
            if (var3 != this.lastSmart) {
               this.colors.clear();
               this.lastSmart = var3;
            }

            int var4 = Math.min(this.points.length, var2.getTotalArmorValue());
            int var5 = 0;

            for (int var6 = 3; var6 >= 0; var6--) {
               ItemStack var7 = var2.inventory.armorInventory[var6];
               if (var7 != null && var7.getItem() instanceof ItemArmor) {
                  ItemArmor var8 = (ItemArmor)var7.getItem();
                  int var9 = var8.damageReduceAmount;
                  if (var9 > 0) {
                     com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusBarsChildModule.b var10 = new com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusBarsChildModule.b(
                        this.a(var8.getArmorMaterial(), var3),
                        this.showGlint.b() && var7.isItemEnchanted(),
                        this.lowDurabilityIndicator.b() && com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.a(var7, this.lowDurabilityThreshold.c())
                     );

                     for (int var11 = var9; var11 > 0 && var5 < var4; var11--) {
                        this.points[var5++] = var10;
                     }
                  }
               }
            }

            com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusBarsChildModule.b var12 = new com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusBarsChildModule.b(-1, false, false);

            while (var5 < var4) {
               this.points[var5++] = var12;
            }
         }
      }
   }

   public void a(com.cheatbreaker.client.kazerui.overlay.OverlayEvents.b var1) {
      if (var1.type == com.cheatbreaker.client.kazerui.overlay.OverlayEvents.aInner.ARMOR && this.a() && this.points[0] != null) {
         var1.a(true);
         ScaledResolution var2 = var1.resolution;
         int var3 = var2.getScaledWidth() / 2 - 91;
         int var4 = var2.getScaledHeight() - com.cheatbreaker.client.kazerui.overlay.OverlayLayout.left_height;
         GlStateManager.enableBlend();

         for (int var5 = 0; var5 < 10; var5++) {
            this.a(var5, var3 + var5 * 8, var4);
         }

         com.cheatbreaker.client.kazerui.overlay.OverlayLayout.left_height += 10;
         Minecraft.getMinecraft().getTextureManager().bindTexture(ICONS);
      }
   }

   private void a(int var1, int var2, int var3) {
      com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusBarsChildModule.b var4 = this.points[var1 * 2];
      com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusBarsChildModule.b var5 = var1 * 2 + 1 < this.points.length ? this.points[var1 * 2 + 1] : null;
      if (var4 != null && !var4.equals(var5)) {
         this.a(var2, var3, com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusBarsChildModule.aInner.LEFT, var4);
         this.a(var2, var3, com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusBarsChildModule.aInner.RIGHT, var5);
      } else {
         this.a(var2, var3, com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusBarsChildModule.aInner.WHOLE, var4);
      }
   }

   private void a(
      int var1, int var2, com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusBarsChildModule.aInner var3, com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusBarsChildModule.b var4
   ) {
      this.n();
      int var5 = var3 == com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusBarsChildModule.aInner.RIGHT ? this.splitX : 0;
      int var6 = var3 == com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusBarsChildModule.aInner.LEFT ? this.splitX : 9;
      com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(ICONS, var1 + var5, var2, 16 + var5, 9.0F, var6 - var5, 9.0F, 256.0F, 256.0F, -1);
      if (var4 != null) {
         int var7 = var4.color;
         float var8 = 1.0F;
         if (var4.warning) {
            double var9 = System.currentTimeMillis() % 1200L / 1200.0 * Math.PI * 2.0;
            var8 = (float)Math.sin(var9) * 0.5F + 0.5F;
            var7 = (int)((var7 >>> 24) * var8) << 24 | var7 & 16777215;
         }

         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(ICONS, var1 + var5, var2, 34 + var5, 9.0F, var6 - var5, 9.0F, 256.0F, 256.0F, this.a(var7));
         if (var4.glint) {
            this.a(var1, var2, var5, var6, var8);
         }
      }
   }

   private int a(int var1) {
      int var2 = var1 >>> 24;
      if (this.iconBrightness <= 0) {
         return var2 << 24 | 16777215;
      } else {
         int var3 = var1 >> 16 & 0xFF;
         int var4 = var1 >> 8 & 0xFF;
         int var5 = var1 & 0xFF;
         int var6 = Math.max(var3, Math.max(var4, var5));
         float var7 = Math.min(255.0F / this.iconBrightness, 255.0F / Math.max(1, var6));
         return var2 << 24 | (int)(var3 * var7) << 16 | (int)(var4 * var7) << 8 | (int)(var5 * var7);
      }
   }

   private void a(int var1, int var2, int var3, int var4, float var5) {
      float var6 = var5 >= 1.0F ? 1.0F : (float)Math.sqrt(Math.max(0.0F, var5));
      int var7 = var5 >= 1.0F ? -7309112 : 0xFF000000 | (int)(144.0F * var6) << 16 | (int)(120.0F * var6) << 8 | (int)(200.0F * var6);
      long var8 = Minecraft.getSystemTime();
      float var10 = (float)Math.floorMod(var8, 13750L) / 13750.0F * 96.0F;
      float var11 = (float)Math.floorMod(var8, 3750L) / 3750.0F * 96.0F;
      GlStateManager.blendFunc(768, 1);

      for (int var12 = 0; var12 < 9; var12++) {
         int var13 = -1;

         for (int var14 = var3; var14 <= var4; var14++) {
            boolean var15 = var14 < var4 && this.iconPixels[var14 + var12 * 9];
            if (var15 && var13 < 0) {
               var13 = var14;
            } else if (!var15 && var13 >= 0) {
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(
                  GLINT, var1 + var13, var2 + var12, var13 + var10, var12 + 96 - var11, var14 - var13, 1.0F, 96.0F, 96.0F, var7
               );
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(
                  GLINT, var1 + var13, var2 + var12, var13 + 96 - var10, var12 + var11, var14 - var13, 1.0F, 96.0F, 96.0F, var7
               );
               var13 = -1;
            }
         }
      }

      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
   }

   private void n() {
      if (this.iconBrightness < 0) {
         this.iconBrightness = 0;
         BufferedImage var1 = a(ICONS);
         if (var1 != null) {
            int var2 = Math.max(1, var1.getWidth() / 256);
            int var3 = 34 * var2;
            int var4 = 9 * var2;
            this.iconBrightness = a(var1, var3, var4, 9 * var2);
            if (this.iconBrightness != 0) {
               int var5 = 9;
               int var6 = -1;

               for (int var7 = 0; var7 < 9; var7++) {
                  for (int var8 = 0; var8 < 9; var8++) {
                     int var9 = var3 + var7 * var2;
                     int var10 = var4 + var8 * var2;
                     boolean var11 = var9 < var1.getWidth() && var10 < var1.getHeight() && var1.getRGB(var9, var10) >>> 24 >= 128;
                     this.iconPixels[var7 + var8 * 9] = var11;
                     if (var11) {
                        var5 = Math.min(var5, var7);
                        var6 = Math.max(var6, var7);
                     }
                  }
               }

               this.splitX = var5 <= var6 ? (var5 + var6 + 1) / 2 : 4;
            }
         }
      }
   }

   private static int a(BufferedImage var0, int var1, int var2, int var3) {
      int var4 = Math.min(var1 + var3, var0.getWidth());
      int var5 = Math.min(var2 + var3, var0.getHeight());
      int[] var6 = new int[Math.max(0, var4 - var1) * Math.max(0, var5 - var2)];
      int var7 = 0;

      for (int var8 = var1; var8 < var4; var8++) {
         for (int var9 = var2; var9 < var5; var9++) {
            int var10 = var0.getRGB(var8, var9);
            if (var10 >>> 24 >= 128) {
               var6[var7++] = Math.max(var10 >> 16 & 0xFF, Math.max(var10 >> 8 & 0xFF, var10 & 0xFF));
            }
         }
      }

      if (var7 == 0) {
         return 0;
      } else {
         Arrays.sort(var6, 0, var7);
         return Math.max(1, var6[Math.round((var7 - 1) * 0.8F)]);
      }
   }

   private int a(ArmorMaterial var1, boolean var2) {
      Integer var3 = this.colors.get(var1);
      if (var3 != null) {
         return var3;
      } else {
         int var4 = var2 ? this.a(var1) : (DEFAULTS.containsKey(var1) ? DEFAULTS.get(var1) : 0);
         this.colors.put(var1, var4);
         return var4;
      }
   }

   private int a(ArmorMaterial var1) {
      String var2 = var1 == ArmorMaterial.CHAIN ? "chainmail" : var1.name().toLowerCase(Locale.ROOT);

      for (String var6 : new String[]{"chestplate", "helmet", "leggings", "boots"}) {
         BufferedImage var7 = a(new ResourceLocation("minecraft", "textures/items/" + var2 + "_" + var6 + ".png"));
         int var8;
         if (var7 != null && (var8 = a(var7)) != 0) {
            if (var1 == ArmorMaterial.LEATHER) {
               var8 = 0xFF000000 | (var8 >> 16 & 0xFF) * 138 / 255 << 16 | (var8 >> 8 & 0xFF) * 87 / 255 << 8 | (var8 & 0xFF) * 48 / 255;
            }

            Integer var9 = DEFAULTS.get(var1);
            if (var9 != null && var9 != 0) {
               float var10 = Math.max(var9 >> 16 & 0xFF, Math.max(var9 >> 8 & 0xFF, var9 & 0xFF)) / 255.0F;
               float[] var11 = Color.RGBtoHSB(var8 >> 16 & 0xFF, var8 >> 8 & 0xFF, var8 & 0xFF, null);
               return Color.HSBtoRGB(var11[0], var11[1], var10);
            }

            return var8;
         }
      }

      return DEFAULTS.containsKey(var1) ? DEFAULTS.get(var1) : 0;
   }

   private static int a(BufferedImage var0) {
      int var1 = var0.getWidth();
      int var2 = var0.getHeight();
      int var3 = 0;
      float[] var4 = new float[var1 * var2];
      long var5 = 0L;
      long var7 = 0L;
      long var9 = 0L;
      long var11 = 0L;

      for (int var13 = 0; var13 < var1; var13++) {
         for (int var14 = 0; var14 < var2; var14++) {
            int var15 = var0.getRGB(var13, var14);
            if (var15 >>> 24 >= 128) {
               int var16 = var15 >> 16 & 0xFF;
               int var17 = var15 >> 8 & 0xFF;
               int var18 = var15 & 0xFF;
               int var19 = Math.max(var16, Math.max(var17, var18));
               var4[var3++] = var19 / 255.0F;
               int var20 = 1 + var19 - Math.min(var16, Math.min(var17, var18));
               var5 += (long)var16 * var20;
               var7 += (long)var17 * var20;
               var9 += (long)var18 * var20;
               var11 += var20;
            }
         }
      }

      if (var11 == 0L) {
         return 0;
      } else {
         float[] var21 = Color.RGBtoHSB((int)(var5 / var11), (int)(var7 / var11), (int)(var9 / var11), null);
         Arrays.sort(var4, 0, var3);
         return Color.HSBtoRGB(var21[0], Math.min(1.0F, var21[1] * 1.25F), var4[Math.round((var3 - 1) * 0.8F)]);
      }
   }

   private static BufferedImage a(ResourceLocation var0) {
      try (InputStream var1 = Minecraft.getMinecraft().getResourceManager().getResource(var0).getInputStream()) {
         return ImageIO.read(var1);
      } catch (Exception var15) {
         return null;
      }
   }

   static {
      DEFAULTS.put(ArmorMaterial.LEATHER, -7644629);
      DEFAULTS.put(ArmorMaterial.CHAIN, -9079435);
      DEFAULTS.put(ArmorMaterial.IRON, -2565928);
      DEFAULTS.put(ArmorMaterial.GOLD, -1062853);
      DEFAULTS.put(ArmorMaterial.DIAMOND, -11608621);
   }

   private static enum aInner {
      LEFT,
      RIGHT,
      WHOLE;
   }

   private static final class b {
      final int color;
      final boolean glint;
      final boolean warning;

      b(int var1, boolean var2, boolean var3) {
         this.color = var1;
         this.glint = var2;
         this.warning = var3;
      }

      @Override
      public boolean equals(Object var1) {
         if (!(var1 instanceof com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusBarsChildModule.b)) {
            return false;
         } else {
            com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusBarsChildModule.b var2 = (com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusBarsChildModule.b)var1;
            return var2.color == this.color && var2.glint == this.glint && var2.warning == this.warning;
         }
      }

      @Override
      public int hashCode() {
         return this.color * 31 + (this.glint ? 2 : 0) + (this.warning ? 1 : 0);
      }
   }
}
