package com.twelvemonkeys.image;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public final class GraphicsUtil {
   public static void enableAA(Graphics var0) {
      ((Graphics2D)var0).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
   }

   public static void setAlpha(Graphics var0, float var1) {
      ((Graphics2D)var0).setComposite(AlphaComposite.getInstance(3, var1));
   }
}
