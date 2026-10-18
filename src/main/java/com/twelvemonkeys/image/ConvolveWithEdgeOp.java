package com.twelvemonkeys.image;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.Raster;
import java.awt.image.RasterOp;
import java.awt.image.WritableRaster;

public class ConvolveWithEdgeOp implements BufferedImageOp, RasterOp {
   public static final int EDGE_ZERO_FILL = 0;
   public static final int EDGE_NO_OP = 1;
   public static final int EDGE_REFLECT = 2;
   public static final int EDGE_WRAP = 3;
   private final Kernel kernel;
   private final int edgeCondition;
   private final ConvolveOp convolve;

   public ConvolveWithEdgeOp(Kernel var1, int var2, RenderingHints var3) {
      int var4;
      switch (var2) {
         case 2:
         case 3:
            var4 = 1;
            break;
         default:
            var4 = var2;
      }

      this.kernel = var1;
      this.edgeCondition = var2;
      this.convolve = new ConvolveOp(var1, var4, var3);
   }

   public ConvolveWithEdgeOp(Kernel var1) {
      this(var1, 0, null);
   }

   @Override
   public BufferedImage filter(BufferedImage var1, BufferedImage var2) {
      if (var1 == null) {
         throw new NullPointerException("source image is null");
      } else if (var1 == var2) {
         throw new IllegalArgumentException("source image cannot be the same as the destination image");
      } else {
         int var3 = this.kernel.getWidth() / 2;
         int var4 = this.kernel.getHeight() / 2;
         BufferedImage var5 = this.addBorder(var1, var3, var4);
         BufferedImage var6 = var2;
         if (var5.getType() == 5) {
            var6 = ImageUtil.createBuffered(var1.getWidth(), var1.getHeight(), var1.getType(), var1.getColorModel().getTransparency(), null);
         }

         var6 = this.convolve.filter(var5, var6);
         if (var1 != var5) {
            var6 = var6.getSubimage(var3, var4, var1.getWidth(), var1.getHeight());
         }

         return var6;
      }
   }

   private BufferedImage addBorder(BufferedImage var1, int var2, int var3) {
      if ((this.edgeCondition & 2) == 0) {
         return var1;
      } else {
         int var4 = var1.getWidth();
         int var5 = var1.getHeight();
         ColorModel var6 = var1.getColorModel();
         WritableRaster var7 = var6.createCompatibleWritableRaster(var4 + 2 * var2, var5 + 2 * var3);
         BufferedImage var8 = new BufferedImage(var6, var7, var6.isAlphaPremultiplied(), null);
         Graphics2D var9 = var8.createGraphics();

         try {
            var9.setComposite(AlphaComposite.Src);
            var9.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
            var9.drawImage(var1, var2, var3, null);
            switch (this.edgeCondition) {
               case 2:
                  var9.drawImage(var1, var2, 0, var2 + var4, var3, 0, 0, var4, 1, null);
                  var9.drawImage(var1, -var4 + var2, var3, var2, var5 + var3, 0, 0, 1, var5, null);
                  var9.drawImage(var1, var4 + var2, var3, 2 * var2 + var4, var5 + var3, var4 - 1, 0, var4, var5, null);
                  var9.drawImage(var1, var2, var3 + var5, var2 + var4, 2 * var3 + var5, 0, var5 - 1, var4, var5, null);
                  break;
               case 3:
                  var9.drawImage(var1, -var4 + var2, -var5 + var3, null);
                  var9.drawImage(var1, var2, -var5 + var3, null);
                  var9.drawImage(var1, var4 + var2, -var5 + var3, null);
                  var9.drawImage(var1, -var4 + var2, var3, null);
                  var9.drawImage(var1, var4 + var2, var3, null);
                  var9.drawImage(var1, -var4 + var2, var5 + var3, null);
                  var9.drawImage(var1, var2, var5 + var3, null);
                  var9.drawImage(var1, var4 + var2, var5 + var3, null);
                  break;
               default:
                  throw new IllegalArgumentException("Illegal edge operation " + this.edgeCondition);
            }
         } finally {
            var9.dispose();
         }

         return var8;
      }
   }

   public int getEdgeCondition() {
      return this.edgeCondition;
   }

   @Override
   public WritableRaster filter(Raster var1, WritableRaster var2) {
      return this.convolve.filter(var1, var2);
   }

   @Override
   public BufferedImage createCompatibleDestImage(BufferedImage var1, ColorModel var2) {
      return this.convolve.createCompatibleDestImage(var1, var2);
   }

   @Override
   public WritableRaster createCompatibleDestRaster(Raster var1) {
      return this.convolve.createCompatibleDestRaster(var1);
   }

   @Override
   public Rectangle2D getBounds2D(BufferedImage var1) {
      return this.convolve.getBounds2D(var1);
   }

   @Override
   public Rectangle2D getBounds2D(Raster var1) {
      return this.convolve.getBounds2D(var1);
   }

   @Override
   public Point2D getPoint2D(Point2D var1, Point2D var2) {
      return this.convolve.getPoint2D(var1, var2);
   }

   @Override
   public RenderingHints getRenderingHints() {
      return this.convolve.getRenderingHints();
   }

   public Kernel getKernel() {
      return this.convolve.getKernel();
   }
}
