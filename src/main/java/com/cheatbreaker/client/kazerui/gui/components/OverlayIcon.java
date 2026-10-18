package com.cheatbreaker.client.kazerui.gui.components;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D.Float;
import java.awt.image.BufferedImage;

public final class OverlayIcon {
   public static BufferedImage a(int var0) {
      BufferedImage var1 = new BufferedImage(var0, var0, 2);
      Graphics2D var2 = var1.createGraphics();
      var2.scale(var0 / 52.0, var0 / 52.0);
      var2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      var2.setColor(new Color(-1907490, true));

      for (int var3 = 2; var3 >= 0; var3--) {
         float var4 = 13 + var3 * 11;
         Float var5 = new Float();
         var5.moveTo(5.0F, var4);
         var5.lineTo(26.0F, var4 - 10.0F);
         var5.lineTo(47.0F, var4);
         var5.quadTo(50.0F, var4 + 2.0F, 47.0F, var4 + 4.0F);
         var5.lineTo(26.0F, var4 + 14.0F);
         var5.lineTo(5.0F, var4 + 4.0F);
         var5.quadTo(2.0F, var4 + 2.0F, 5.0F, var4);
         var2.fill(var5);
         if (var3 > 0) {
            var2.setComposite(AlphaComposite.Clear);
            var2.fill(new java.awt.geom.Rectangle2D.Float(0.0F, var4 - 15.0F, 52.0F, 5.0F));
            var2.setComposite(AlphaComposite.SrcOver);
         }
      }

      var2.dispose();
      return var1;
   }

   private OverlayIcon() {
   }
}
