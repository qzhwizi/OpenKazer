package com.twelvemonkeys.image;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.ImagingOpException;
import java.awt.image.Raster;
import java.awt.image.RasterOp;
import java.awt.image.WritableRaster;

public class AffineTransformOp implements BufferedImageOp, RasterOp {
   final java.awt.image.AffineTransformOp delegate;
   public static final int TYPE_NEAREST_NEIGHBOR = 1;
   public static final int TYPE_BILINEAR = 2;
   public static final int TYPE_BICUBIC = 3;

   public AffineTransformOp(AffineTransform var1, RenderingHints var2) {
      this.delegate = new java.awt.image.AffineTransformOp(var1, var2);
   }

   public AffineTransformOp(AffineTransform var1, int var2) {
      this.delegate = new java.awt.image.AffineTransformOp(var1, var2);
   }

   @Override
   public BufferedImage filter(BufferedImage var1, BufferedImage var2) {
      try {
         return this.delegate.filter(var1, var2);
      } catch (ImagingOpException var11) {
         if (var2 == null) {
            var2 = this.createCompatibleDestImage(var1, var1.getColorModel());
         }

         Graphics2D var4 = var2.createGraphics();

         BufferedImage var12;
         try {
            int var5 = this.delegate.getInterpolationType();
            if (var5 > 0) {
               Object var6 = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
               switch (var5) {
                  case 2:
                     var6 = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
                     break;
                  case 3:
                     var6 = RenderingHints.VALUE_INTERPOLATION_BICUBIC;
               }

               var4.setRenderingHint(RenderingHints.KEY_INTERPOLATION, var6);
            } else if (this.getRenderingHints() != null) {
               var4.setRenderingHints(this.getRenderingHints());
            }

            var4.drawImage(var1, this.delegate.getTransform(), null);
            var12 = var2;
         } finally {
            var4.dispose();
         }

         return var12;
      }
   }

   @Override
   public Rectangle2D getBounds2D(BufferedImage var1) {
      return this.delegate.getBounds2D(var1);
   }

   @Override
   public BufferedImage createCompatibleDestImage(BufferedImage var1, ColorModel var2) {
      return this.delegate.createCompatibleDestImage(var1, var2);
   }

   @Override
   public WritableRaster filter(Raster var1, WritableRaster var2) {
      return this.delegate.filter(var1, var2);
   }

   @Override
   public Rectangle2D getBounds2D(Raster var1) {
      return this.delegate.getBounds2D(var1);
   }

   @Override
   public WritableRaster createCompatibleDestRaster(Raster var1) {
      return this.delegate.createCompatibleDestRaster(var1);
   }

   @Override
   public Point2D getPoint2D(Point2D var1, Point2D var2) {
      return this.delegate.getPoint2D(var1, var2);
   }

   @Override
   public RenderingHints getRenderingHints() {
      return this.delegate.getRenderingHints();
   }
}
