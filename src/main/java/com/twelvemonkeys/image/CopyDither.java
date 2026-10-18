package com.twelvemonkeys.image;

import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Float;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.Raster;
import java.awt.image.RasterOp;
import java.awt.image.WritableRaster;

public class CopyDither implements BufferedImageOp, RasterOp {
   protected IndexColorModel indexColorModel = null;

   public CopyDither(IndexColorModel var1) {
      this.indexColorModel = var1;
   }

   public CopyDither() {
   }

   @Override
   public final BufferedImage createCompatibleDestImage(BufferedImage var1, ColorModel var2) {
      if (var2 == null) {
         return new BufferedImage(var1.getWidth(), var1.getHeight(), 13, this.indexColorModel);
      } else if (var2 instanceof IndexColorModel) {
         return new BufferedImage(var1.getWidth(), var1.getHeight(), 13, (IndexColorModel)var2);
      } else {
         throw new ImageFilterException("Only IndexColorModel allowed.");
      }
   }

   @Override
   public final WritableRaster createCompatibleDestRaster(Raster var1) {
      return this.createCompatibleDestRaster(var1, this.getICM(var1));
   }

   public final WritableRaster createCompatibleDestRaster(Raster var1, IndexColorModel var2) {
      return var2.createCompatibleWritableRaster(var1.getWidth(), var1.getHeight());
   }

   @Override
   public final Rectangle2D getBounds2D(BufferedImage var1) {
      return this.getBounds2D(var1.getRaster());
   }

   @Override
   public final Rectangle2D getBounds2D(Raster var1) {
      return var1.getBounds();
   }

   @Override
   public final Point2D getPoint2D(Point2D var1, Point2D var2) {
      if (var2 == null) {
         var2 = new Float();
      }

      var2.setLocation(var1.getX(), var1.getY());
      return (Point2D)var2;
   }

   @Override
   public final RenderingHints getRenderingHints() {
      return null;
   }

   private static int toIntARGB(int[] var0) {
      return 0xFF000000 | var0[0] << 16 | var0[1] << 8 | var0[2];
   }

   @Override
   public final BufferedImage filter(BufferedImage var1, BufferedImage var2) {
      if (var2 == null) {
         var2 = this.createCompatibleDestImage(var1, this.getICM(var1));
      } else if (!(var2.getColorModel() instanceof IndexColorModel)) {
         throw new ImageFilterException("Only IndexColorModel allowed.");
      }

      this.filter(var1.getRaster(), var2.getRaster(), (IndexColorModel)var2.getColorModel());
      return var2;
   }

   @Override
   public final WritableRaster filter(Raster var1, WritableRaster var2) {
      return this.filter(var1, var2, this.getICM(var1));
   }

   private IndexColorModel getICM(BufferedImage var1) {
      return this.indexColorModel != null ? this.indexColorModel : IndexImage.getIndexColorModel(var1, 256, 131584);
   }

   private IndexColorModel getICM(Raster var1) {
      return this.indexColorModel != null ? this.indexColorModel : this.createIndexColorModel(var1);
   }

   private IndexColorModel createIndexColorModel(Raster var1) {
      BufferedImage var2 = new BufferedImage(var1.getWidth(), var1.getHeight(), 2);
      var2.setData(var1);
      return IndexImage.getIndexColorModel(var2, 256, 131584);
   }

   public final WritableRaster filter(Raster var1, WritableRaster var2, IndexColorModel var3) {
      int var4 = var1.getWidth();
      int var5 = var1.getHeight();
      if (var2 == null) {
         var2 = this.createCompatibleDestRaster(var1, var3);
      }

      int[] var6 = new int[4];
      Object var7 = null;

      for (int var8 = 0; var8 < var5; var8++) {
         for (int var9 = 0; var9 < var4; var9++) {
            var1.getPixel(var9, var8, var6);
            var7 = var3.getDataElements(toIntARGB(var6), var7);
            var2.setDataElements(var9, var8, var7);
         }
      }

      return var2;
   }
}
