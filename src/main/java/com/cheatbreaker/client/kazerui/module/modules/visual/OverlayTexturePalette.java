package com.cheatbreaker.client.kazerui.module.modules.visual;

import java.awt.image.BufferedImage;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;

public final class OverlayTexturePalette {
   private static final ResourceLocation[] GUI;

   public static BufferedImage tint(BufferedImage var0, ResourceLocation var1) {
      if (var0 != null && var1 != null && var1.getResourceDomain().equals("minecraft")) {
         String var2 = var1.getResourcePath();
         if (var2.equals("textures/gui/icons.png") && ModuleOverlay.customXp()) {
            int var3 = var0.getWidth();
            int var4 = var0.getHeight();
            a(var0, 0, var4 * 64 / 256, var3 * 182 / 256, var4 * 74 / 256, ModuleOverlay.xpColor(8453920));
         } else if (var2.startsWith("textures/gui/container/") && ModuleOverlay.customGui() && (ModuleOverlay.guiColor() & 16777215) != 16777215) {
            a(var0, 0, 0, var0.getWidth(), var0.getHeight(), ModuleOverlay.guiColor());
         }

         return var0;
      } else {
         return var0;
      }
   }

   private static void a(BufferedImage var0, int var1, int var2, int var3, int var4, int var5) {
      int var6 = var5 >> 16 & 0xFF;
      int var7 = var5 >> 8 & 0xFF;
      int var8 = var5 & 0xFF;

      for (int var9 = var2; var9 < var4; var9++) {
         for (int var10 = var1; var10 < var3; var10++) {
            int var11 = var0.getRGB(var10, var9);
            int var12 = Math.max(var11 >> 16 & 0xFF, Math.max(var11 >> 8 & 0xFF, var11 & 0xFF));
            var0.setRGB(var10, var9, var11 & 0xFF000000 | var6 * var12 / 255 << 16 | var7 * var12 / 255 << 8 | var8 * var12 / 255);
         }
      }
   }

   public static void reload() {
      ModuleOverlay.paletteApplied();
      Minecraft var0 = Minecraft.getMinecraft();
      if (var0 != null && var0.getTextureManager() != null) {
         for (ResourceLocation var4 : GUI) {
            ITextureObject var5 = var0.getTextureManager().getTexture(var4);
            if (var5 != null) {
               try {
                  var5.loadTexture(var0.getResourceManager());
               } catch (IOException var7) {
                  LogManager.getLogger().warn("Could not reload Overlay GUI texture " + var4, var7);
               }
            }
         }
      }
   }

   private OverlayTexturePalette() {
   }

   static {
      String[] var0 = new String[]{
         "inventory", "generic_54", "furnace", "dispenser", "crafting_table", "anvil", "enchanting_table", "brewing_stand", "beacon", "hopper", "horse"
      };
      GUI = new ResourceLocation[var0.length + 1];
      GUI[0] = new ResourceLocation("textures/gui/icons.png");

      for (int var1 = 0; var1 < var0.length; var1++) {
         GUI[var1 + 1] = new ResourceLocation("textures/gui/container/" + var0[var1] + ".png");
      }
   }
}
