package com.twelvemonkeys.image;

import com.twelvemonkeys.lang.Validate;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.Icon;

public class BufferedImageIcon implements Icon {
   private final BufferedImage image;
   private final int width;
   private final int height;
   private final boolean fast;

   public BufferedImageIcon(BufferedImage var1) {
      this(var1, var1 != null ? var1.getWidth() : 0, var1 != null ? var1.getHeight() : 0);
   }

   public BufferedImageIcon(BufferedImage var1, int var2, int var3) {
      this(var1, var2, var3, var1.getWidth() == var2 && var1.getHeight() == var3);
   }

   public BufferedImageIcon(BufferedImage var1, int var2, int var3, boolean var4) {
      this.image = Validate.notNull(var1, "image");
      this.width = Validate.isTrue(var2 > 0, var2, "width must be positive: %d");
      this.height = Validate.isTrue(var3 > 0, var3, "height must be positive: %d");
      this.fast = var4;
   }

   @Override
   public int getIconHeight() {
      return this.height;
   }

   @Override
   public int getIconWidth() {
      return this.width;
   }

   @Override
   public void paintIcon(Component var1, Graphics var2, int var3, int var4) {
      if (!this.fast && var2 instanceof Graphics2D) {
         Graphics2D var5 = (Graphics2D)var2;
         AffineTransform var6 = AffineTransform.getTranslateInstance(var3, var4);
         var6.scale((double)this.width / this.image.getWidth(), (double)this.height / this.image.getHeight());
         var5.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
         var5.drawImage(this.image, var6, null);
      } else {
         var2.drawImage(this.image, var3, var4, this.width, this.height, null);
      }
   }
}
