package com.cheatbreaker.client.kazerui.module.modules.visual;

import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public final class OverlayWhiteSprite extends TextureAtlasSprite {
   public static final String NAME = "kazerui:overlay_white";
   public static com.cheatbreaker.client.kazerui.module.modules.visual.OverlayWhiteSprite current;

   public OverlayWhiteSprite() {
      super("kazerui:overlay_white");
      current = this;
   }

   public boolean hasCustomLoader(IResourceManager var1, ResourceLocation var2) {
      return true;
   }

   public boolean load(IResourceManager var1, ResourceLocation var2) {
      this.setIconWidth(16);
      this.setIconHeight(16);
      int[][] var3 = new int[5][];

      for (int var4 = 0; var4 < 5; var4++) {
         int var5 = 16 >> var4;
         var3[var4] = new int[var5 * var5];
         Arrays.fill(var3[var4], -1);
      }

      ArrayList var6 = new ArrayList(1);
      var6.add(var3);
      this.setFramesTextureData(var6);
      return false;
   }
}
