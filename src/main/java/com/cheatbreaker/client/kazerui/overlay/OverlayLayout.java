package com.cheatbreaker.client.kazerui.overlay;

import net.minecraft.client.gui.ScaledResolution;

public final class OverlayLayout {
   public static int left_height;
   public static int extent;

   public static void a(ScaledResolution var0) {
      extent = var0.getScaledWidth() + var0.getScaledHeight();
   }

   private OverlayLayout() {
   }
}
