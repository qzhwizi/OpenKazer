package com.cheatbreaker.client.kazerui.module.modules.hud;

import com.cheatbreaker.client.kazerui.module.render.TooltipHooks;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;

public final class SaturationHudModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static final ResourceLocation ICONS = new ResourceLocation("textures/gui/icons.png");
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showSaturationOverlay = this.a("showSaturationOverlay", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showHeldItemHunger = this.a("showHeldItemHunger", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showHeldItemSaturation = this.a("showHeldItemSaturation", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showAppleskinTooltip = this.a("showAppleskinTooltip", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting useInverseColor = this.a("useInverseColor", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting saturationColor = this.a("saturationColor", -14848);
   private final com.cheatbreaker.client.kazerui.module.modules.hud.SaturationHudModule.b outline = new com.cheatbreaker.client.kazerui.module.modules.hud.SaturationHudModule.b();
   private float flash;
   private float flashAlpha;
   private byte flashDirection = 1;
   private static Field alwaysEdible;
   private static Field potionId;
   private long outlineEpoch = Long.MIN_VALUE;

   public SaturationHudModule() {
      super("SATURATION", true);
      this.a(new com.cheatbreaker.client.kazerui.module.modules.hud.SaturationHudModule.a(), null);
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a("appleSkinOptions", var1x -> {
         var1x.a(this.showSaturationOverlay, this.showHeldItemHunger, this.showHeldItemSaturation, this.showAppleskinTooltip, this.useInverseColor);
         var1x.a(this.saturationColor).a(this.useInverseColor::b);
      });
   }

   private static boolean a(ItemStack var0, EntityPlayer var1) {
      return var0 != null && var0.getItem() instanceof ItemFood && var1.canEat(a((ItemFood)var0.getItem()));
   }

   private static Field a(String... var0) {
      for (String var4 : var0) {
         try {
            Field var5 = ItemFood.class.getDeclaredField(var4);
            var5.setAccessible(true);
            return var5;
         } catch (NoSuchFieldException var6) {
         }
      }

      throw new IllegalStateException("Food metadata unavailable");
   }

   private static boolean a(ItemFood var0) {
      try {
         if (alwaysEdible == null) {
            alwaysEdible = a("alwaysEdible", "alwaysEdible");
         }

         return alwaysEdible.getBoolean(var0);
      } catch (Exception var2) {
         return false;
      }
   }

   private static int a(ItemStack var0) {
      return var0.getItem() instanceof ItemFood ? ((ItemFood)var0.getItem()).getHealAmount(var0) : 0;
   }

   private static float b(ItemStack var0) {
      if (!(var0.getItem() instanceof ItemFood)) {
         return 0.0F;
      } else {
         ItemFood var1 = (ItemFood)var0.getItem();
         return var1.getHealAmount(var0) * var1.getSaturationModifier(var0) * 2.0F;
      }
   }

   private static boolean c(ItemStack var0) {
      if (!(var0.getItem() instanceof ItemFood)) {
         return false;
      } else {
         try {
            if (potionId == null) {
               potionId = a("potionId", "potionId");
            }

            int var1 = potionId.getInt(var0.getItem());
            return var1 > 0 && Potion.potionTypes[var1] != null && Potion.potionTypes[var1].isBadEffect();
         } catch (Exception var2) {
            return false;
         }
      }
   }

   @Override
   public void a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a var1) {
      if (var1.phase == com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b.END && this.a()) {
         this.flash = this.flash + this.flashDirection * 0.125F;
         if (this.flash >= 1.5F) {
            this.flashDirection = -1;
         } else if (this.flash <= -0.5F) {
            this.flashDirection = 1;
         }

         this.flashAlpha = Math.max(0.0F, Math.min(1.0F, this.flash)) * 0.85F;
      }
   }

   private void o() {
      this.flash = 0.0F;
      this.flashAlpha = 0.0F;
      this.flashDirection = 1;
   }

   public void a(ScaledResolution var1, boolean var2) {
      if (this.a()) {
         if (!var2) {
            this.o();
         } else {
            EntityPlayerSP var3 = Minecraft.getMinecraft().thePlayer;
            if (var3 != null) {
               this.p();
               int var4 = var1.getScaledHeight() - 39;
               int var5 = var1.getScaledWidth() / 2 + 91;
               cn.kazer.client189.render.GlStateBridge.c(1048575);

               try {
                  GlStateManager.enableBlend();
                  GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                  if (this.showSaturationOverlay.b()) {
                     this.a(var3, 0.0F, var5, var4, 1.0F);
                  }

                  ItemStack var6 = var3.getHeldItem();
                  if (var6 == null || !a(var6, var3)) {
                     this.o();
                     return;
                  }

                  int var7 = a(var6);
                  float var8 = b(var6);
                  if (this.showHeldItemHunger.b()) {
                     this.a(var3, var7, var5, var4, this.flashAlpha, c(var6));
                  }

                  if (this.showHeldItemSaturation.b()) {
                     FoodStats var9 = var3.getFoodStats();
                     int var10 = var9.getFoodLevel() + var7;
                     float var11 = var9.getSaturationLevel() + var8;
                     float var12 = var11 > var10 ? var10 - var9.getSaturationLevel() : var8;
                     this.a(var3, var12, var5, var4, this.flashAlpha);
                  }

                  GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
               } finally {
                  cn.kazer.client189.render.GlStateBridge.a();
               }
            }
         }
      }
   }

   private void a(EntityPlayer var1, float var2, int var3, int var4, float var5) {
      FoodStats var6 = var1.getFoodStats();
      float var7 = var6.getSaturationLevel();
      if (!(var7 + var2 < 0.0F)) {
         int var8 = var2 != 0.0F ? (int)Math.max(var7 / 2.0F, 0.0F) : 0;
         float var9 = Math.max(0.0F, Math.min(20.0F, var7 + var2));
         int var10 = (int)Math.ceil(var9 / 2.0F);
         if (var8 < var10) {
            boolean var11 = var1.isPotionActive(Potion.hunger);

            for (int var12 = var8; var12 < var10; var12++) {
               float var13 = var3 - var12 * 8 - 9;
               float var14 = this.a(var4, var6);
               int var15 = a(this.a(var11, var13 + var14), var5);
               this.outline.a(var11, var13, var14, var9 / 2.0F - var12, var15);
            }
         }
      }
   }

   private void a(EntityPlayer var1, int var2, int var3, int var4, float var5, boolean var6) {
      if (var2 > 0) {
         FoodStats var7 = var1.getFoodStats();
         int var8 = var7.getFoodLevel();
         int var9 = Math.max(0, Math.min(20, var8 + var2));
         int var10 = Math.max(0, var8 / 2);
         int var11 = (int)Math.ceil(var9 / 2.0F);

         for (int var12 = var10; var12 < var11; var12++) {
            float var13 = var3 - var12 * 8 - 9;
            float var14 = this.a(var4, var7);
            boolean var15 = var12 * 2 + 1 == var9;
            a(var6 ? 133 : 25, var13, var14, (int)(var5 * 0.25F * 255.0F) << 24 | 16777215);
            a(var15 ? (var6 ? 97 : 61) : (var6 ? 88 : 52), var13, var14, (int)(var5 * 255.0F) << 24 | 16777215);
         }
      }
   }

   private float a(float var1, FoodStats var2) {
      int var3 = Minecraft.getMinecraft().ingameGUI.getUpdateCounter();
      if (var2.getSaturationLevel() <= 0.0F && var3 % (var2.getFoodLevel() * 3 + 1) == 0) {
         var1 += ThreadLocalRandom.current().nextInt(3) - 1;
      }

      return var1;
   }

   int a(boolean var1, float var2) {
      return this.useInverseColor.b() ? this.outline.a(var1) : this.saturationColor.a(var2);
   }

   private static int a(int var0, float var1) {
      if (var1 == 1.0F) {
         return var0;
      } else {
         int var2 = Math.max(0, Math.min(255, (int)((var0 >>> 24) * var1)));
         return var2 << 24 | var0 & 16777215;
      }
   }

   private static void a(int var0, float var1, float var2, int var3) {
      Minecraft.getMinecraft().getTextureManager().bindTexture(ICONS);
      com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(ICONS, var1, var2, var0, 27.0F, 9.0F, 9.0F, 256.0F, 256.0F, var3);
   }

   public void a(ItemStack var1, List<String> var2) {
      if (this.a() && this.showAppleskinTooltip.b() && var1 != null && !var2.isEmpty()) {
         EntityPlayerSP var3 = Minecraft.getMinecraft().thePlayer;
         this.p();
         if (var3 != null && var1.getItem() instanceof ItemFood && !d(var1)) {
            int var4 = a(var1);
            int var5 = (int)Math.ceil(Math.abs(var4) / 2.0F);
            if (var5 != 0) {
               float var6 = b(var1);
               int var7 = (int)Math.ceil(Math.abs(var6) / 2.0F);
               String var8 = null;
               String var9 = null;
               float var10;
               if (var5 > 10) {
                  var8 = "x" + (var4 < 0 ? -var5 : var5);
                  var10 = 10.0F + com.cheatbreaker.client.kazerui.module.modulerender.HudTextUtils.a(var8);
               } else {
                  var10 = var5 * 9;
               }

               float var11;
               if (var7 <= 10 && var7 != 0) {
                  var11 = var7 * 9;
               } else {
                  var9 = "x" + (var6 < 0.0F ? -var7 : var7);
                  var11 = 10.0F + com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var9);
               }

               boolean var12 = c(var1);
               int var13 = var5 > 10 ? 1 : var5;
               int var14 = var7 > 10 ? 1 : var7;
               String var15 = var8;
               String var16 = var9;
               TooltipHooks.a(
                  var2,
                  1,
                  (int)Math.ceil(Math.max(var10, var11)),
                  20,
                  (var9x, var10x) -> this.a(var9x, var10x, var12, var13, var14, var15, var16, var4, var4, var6)
               );
            }
         }
      }
   }

   private static boolean d(ItemStack var0) {
      return var0.hasTagCompound() && var0.getTagCompound().hasKey("display", 10) && var0.getTagCompound().getCompoundTag("display").hasKey("Lore", 9);
   }

   private void a(int var1, int var2, boolean var3, int var4, int var5, String var6, String var7, int var8, int var9, float var10) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(var1, var2, 0.0F);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      int var11 = (var4 - 1) * 9;

      for (byte var12 = 0; var12 < var4 * 2; var12 += 2) {
         a(var8 < 0 ? 43 : (var3 ? 133 : 16), var11, 0.0F, -1);
         int var13 = (var3 ? 106 : 70) + (var9 - 1 == var12 ? 9 : 0);
         a(var13, var11, 0.0F, 1090519039);
         if (var8 > var12) {
            boolean var14 = var8 - 1 == var12;
            a(var14 ? (var3 ? 97 : 61) : (var3 ? 88 : 52), var11, 0.0F, -1);
         }

         var11 -= 9;
      }

      if (var6 != null) {
         a(var11 + 18, 1, var6, 2);
      }

      var11 = (var5 - 1) * 9;
      float var17 = Math.abs(var10);

      for (byte var18 = 0; var18 < var5 * 2; var18 += 2) {
         int var19 = var10 < 0.0F ? -5565952 : this.a(var3, var11 + 10);
         float var15 = var17 <= var18 ? 0.5F : 1.0F;
         a(var3 ? 88 : 52, var11, 10.0F, a(-14145496, var15));
         this.outline.a(var3, var11, 10.0F, 1.0F, a(-16119286, var15));
         this.outline.a(var3, var11, 10.0F, (var17 - var18) / 2.0F, a(var19, var15));
         var11 -= 9;
      }

      if (var7 != null) {
         a(var11 + 18, 11, var7, 1);
      }

      GlStateManager.popMatrix();
   }

   private static void a(int var0, int var1, String var2, int var3) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(var0, var1, 0.0F);
      GlStateManager.scale(0.75F, 0.75F, 1.0F);
      com.cheatbreaker.client.kazerui.module.modulerender.HudTextUtils.a(var2, 3.0F, var3, -5592406, false);
      GlStateManager.popMatrix();
   }

   private void p() {
      long var1 = com.cheatbreaker.client.kazerui.RenderEpoch.a();
      if (this.outlineEpoch != var1) {
         this.outline.a();
         this.outlineEpoch = var1;
      }
   }

   static final class a extends com.cheatbreaker.client.kazerui.module.KazerModule {
      a() {
         super("SATURATION_HUD_CHILD", false);
         this.a(
            new com.cheatbreaker.client.kazerui.module.modulerender.TextHudBase(
               this, 0.0F, 0.0F, com.cheatbreaker.client.kazerui.hud.HudAnchor.TOP_RIGHT, com.cheatbreaker.client.kazerui.module.modulerender.RichTextHudBase.a(10, 18, 22, 40, 56, 62)
            ) {
               @Override
               protected String c(boolean var1) {
                  if (var1) {
                     return "0";
                  } else {
                     EntityPlayerSP var2 = Minecraft.getMinecraft().thePlayer;
                     return var2 != null && var2.getFoodStats() != null ? "" + (int)Math.ceil(var2.getFoodStats().getSaturationLevel()) : null;
                  }
               }
            }
         );
      }
   }

   private static final class b {
      private com.cheatbreaker.client.kazerui.module.modules.hud.SaturationHudModule.b.a plain;
      private com.cheatbreaker.client.kazerui.module.modules.hud.SaturationHudModule.b.a hunger;

      private b() {
      }

      void a() {
         com.cheatbreaker.client.kazerui.module.modules.hud.SaturationHudModule.c.a(this.plain);
         com.cheatbreaker.client.kazerui.module.modules.hud.SaturationHudModule.c.a(this.hunger);
         this.plain = this.hunger = null;
      }

      int a(boolean var1) {
         return this.b(var1).inverse;
      }

      private com.cheatbreaker.client.kazerui.module.modules.hud.SaturationHudModule.b.a b(boolean var1) {
         if (var1) {
            if (this.hunger == null) {
               this.hunger = this.c(true);
            }

            return this.hunger;
         } else {
            if (this.plain == null) {
               this.plain = this.c(false);
            }

            return this.plain;
         }
      }

      void a(boolean var1, float var2, float var3, float var4, int var5) {
         if (!(var4 <= 0.0F)) {
            com.cheatbreaker.client.kazerui.module.modules.hud.SaturationHudModule.b.a var6 = this.b(var1);
            if (!var6.empty) {
               int var7 = var6.resolution;
               int var8 = var4 >= 1.0F ? 3 : Math.max(0, Math.min(2, (int)Math.ceil(var4 * 4.0F) - 1));
               float var9 = 9.0F / var7;
               GlStateManager.pushMatrix();
               GlStateManager.translate(var2, var3, 0.0F);
               GlStateManager.scale(var9, var9, 1.0F);
               Minecraft.getMinecraft().getTextureManager().bindTexture(var6.location);
               GlStateManager.color((var5 >> 16 & 0xFF) / 255.0F, (var5 >> 8 & 0xFF) / 255.0F, (var5 & 0xFF) / 255.0F, (var5 >>> 24) / 255.0F);
               float var10 = (float)(var8 * var7) / (var7 * 4);
               float var11 = (float)((var8 + 1) * var7) / (var7 * 4);
               Tessellator var12 = Tessellator.getInstance();
               WorldRenderer var13 = var12.getWorldRenderer();
               var13.begin(7, DefaultVertexFormats.POSITION_TEX);
               var13.pos(0.0, var7, 0.0).tex(var10, 1.0).endVertex();
               var13.pos(var7, var7, 0.0).tex(var11, 1.0).endVertex();
               var13.pos(var7, 0.0, 0.0).tex(var11, 0.0).endVertex();
               var13.pos(0.0, 0.0, 0.0).tex(var10, 0.0).endVertex();
               var12.draw();
               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
               GlStateManager.popMatrix();
            }
         }
      }

      private com.cheatbreaker.client.kazerui.module.modules.hud.SaturationHudModule.b.a c(boolean var1) {
         ResourceLocation var2 = new ResourceLocation("kazerui", var1 ? "saturation_outline_hunger" : "saturation_outline");
         BufferedImage var3 = a(com.cheatbreaker.client.kazerui.module.modules.hud.SaturationHudModule.ICONS);
         int var4 = var1 ? 88 : 52;
         int var5 = var1 ? 133 : 16;
         int var6 = var3 == null ? 9 : Math.max(9, Math.min(36, Math.round(9.0F * (var3.getWidth() / 256.0F))));
         BitSet var7 = new BitSet(var6 * var6);
         int var8 = a(var7, var3, var5, var6);
         int var9 = a(var7, var3, var4, var6);
         int var10 = (int)Math.ceil(var6 / 9.0F);
         int var11 = a(var8, var9);
         ArrayList var12 = new ArrayList();

         for (int var13 = 0; var13 < var6; var13++) {
            for (int var14 = 0; var14 < var6; var14++) {
               if (a(var7, var6, var10, var14, var13)) {
                  var12.add(new int[]{var14, var13});
               }
            }
         }

         if (var12.isEmpty()) {
            return new com.cheatbreaker.client.kazerui.module.modules.hud.SaturationHudModule.b.a(var2, true, var6, var11);
         } else {
            BufferedImage var21 = new BufferedImage(var6 * 4, var6, 2);

            for (int[] var15 : (Iterable<int[]>) var12) {
               float var16 = var15[1] + 0.25F * (var15[0] - var6 / 2.0F);
               int var17 = Math.round(255.0F - 115.0F * Math.max(0.0F, Math.min(1.0F, var16 / var6)));
               int var18 = 0xFF000000 | var17 << 16 | var17 << 8 | var17;
               float var19 = (var6 - var15[0] - 0.25F * (var15[1] - var6 / 2.0F)) / var6;

               for (int var20 = Math.max(0, Math.min(3, (int)Math.ceil(var19 * 4.0F) - 1)); var20 < 4; var20++) {
                  var21.setRGB(var20 * var6 + var15[0], var15[1], var18);
               }
            }

            Minecraft.getMinecraft().getTextureManager().loadTexture(var2, new DynamicTexture(var21));
            return new com.cheatbreaker.client.kazerui.module.modules.hud.SaturationHudModule.b.a(var2, false, var6, var11);
         }
      }

      private static boolean a(BitSet var0, int var1, int var2, int var3) {
         return var2 >= 0 && var3 >= 0 && var2 < var1 && var3 < var1 && var0.get(var2 + var3 * var1);
      }

      private static boolean a(BitSet var0, int var1, int var2, int var3, int var4) {
         if (!a(var0, var1, var3, var4)) {
            return false;
         } else {
            for (int var5 = -var2; var5 <= var2; var5++) {
               for (int var6 = -var2; var6 <= var2; var6++) {
                  if (var6 * var6 + var5 * var5 <= var2 * var2 && !a(var0, var1, var3 + var6, var4 + var5)) {
                     return true;
                  }
               }
            }

            return false;
         }
      }

      private static int a(BitSet var0, BufferedImage var1, int var2, int var3) {
         if (var1 == null) {
            return -1;
         } else {
            float var4 = var1.getWidth() / 256.0F;
            float var5 = 9.0F * var4;
            float var6 = var2 * var4;
            float var7 = 27.0F * var4;
            int var8 = 0;
            int var9 = 0;
            int var10 = 0;
            int var11 = 0;

            for (int var12 = 0; var12 < var3; var12++) {
               for (int var13 = 0; var13 < var3; var13++) {
                  int var14 = (int)(var6 + (var13 + 0.5F) * var5 / var3);
                  int var15 = (int)(var7 + (var12 + 0.5F) * var5 / var3);
                  if (var14 < var1.getWidth() && var15 < var1.getHeight()) {
                     int var16 = var1.getRGB(var14, var15);
                     if (var16 >>> 24 >= 128) {
                        var0.set(var13 + var12 * var3);
                        var8 += var16 >> 16 & 0xFF;
                        var9 += var16 >> 8 & 0xFF;
                        var10 += var16 & 0xFF;
                        var11++;
                     }
                  }
               }
            }

            return var11 == 0 ? -1 : 0xFF000000 | 255 - var8 / var11 << 16 | 255 - var9 / var11 << 8 | 255 - var10 / var11;
         }
      }

      private static int a(int var0, int var1) {
         int var2 = (int)((var0 >> 16 & 0xFF) * 0.5F + (var1 >> 16 & 0xFF) * 0.5F);
         int var3 = (int)((var0 >> 8 & 0xFF) * 0.5F + (var1 >> 8 & 0xFF) * 0.5F);
         int var4 = (int)((var0 & 0xFF) * 0.5F + (var1 & 0xFF) * 0.5F);
         return 0xFF000000 | var2 << 16 | var3 << 8 | var4;
      }

      private static BufferedImage a(ResourceLocation var0) {
         try (InputStream var1 = Minecraft.getMinecraft().getResourceManager().getResource(var0).getInputStream()) {
            return ImageIO.read(var1);
         } catch (Exception var15) {
            return null;
         }
      }

      private static final class a {
         final ResourceLocation location;
         final boolean empty;
         final int resolution;
         final int inverse;

         a(ResourceLocation var1, boolean var2, int var3, int var4) {
            this.location = var1;
            this.empty = var2;
            this.resolution = var3;
            this.inverse = var4;
         }
      }
   }

   private static final class c {
      static void a(com.cheatbreaker.client.kazerui.module.modules.hud.SaturationHudModule.b.a var0) {
         if (var0 != null && !var0.empty) {
            Minecraft.getMinecraft().getTextureManager().deleteTexture(var0.location);
         }
      }
   }
}
