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
import java.util.Random;

public class DiffusionDither implements BufferedImageOp, RasterOp {
   private static final int FS_SCALE = 256;
   private static final Random RANDOM = new Random();
   protected final IndexColorModel indexColorModel;
   private boolean alternateScans = true;

   public DiffusionDither(IndexColorModel var1) {
      this.indexColorModel = var1;
   }

   public DiffusionDither() {
      this(null);
   }

   public void setAlternateScans(boolean var1) {
      this.alternateScans = var1;
   }

   @Override
   public final BufferedImage createCompatibleDestImage(BufferedImage var1, ColorModel var2) {
      if (var2 == null) {
         return new BufferedImage(var1.getWidth(), var1.getHeight(), 13, this.getICM(var1));
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

   private static int[] toRGBArray(int var0, int[] var1) {
      var1[0] = (var0 & 0xFF0000) >> 16;
      var1[1] = (var0 & 0xFF00) >> 8;
      var1[2] = var0 & 0xFF;
      return var1;
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
      return this.indexColorModel != null ? this.indexColorModel : IndexImage.getIndexColorModel(var1, 256, 131072);
   }

   private IndexColorModel getICM(Raster var1) {
      return this.indexColorModel != null ? this.indexColorModel : this.createIndexColorModel(var1);
   }

   private IndexColorModel createIndexColorModel(Raster var1) {
      BufferedImage var2 = new BufferedImage(var1.getWidth(), var1.getHeight(), 2);
      var2.setData(var1);
      return IndexImage.getIndexColorModel(var2, 256, 131072);
   }

   public final WritableRaster filter(Raster var1, WritableRaster var2, IndexColorModel var3) {
      int var4 = var1.getWidth();
      int var5 = var1.getHeight();
      if (var2 == null) {
         var2 = this.createCompatibleDestRaster(var1, var3);
      }

      int[][] var6 = new int[var4 + 2][3];
      int[][] var7 = new int[var4 + 2][3];

      for (int var8 = 0; var8 < var4 + 2; var8++) {
         var6[var8][0] = RANDOM.nextInt(512) - 256;
         var6[var8][1] = RANDOM.nextInt(512) - 256;
         var6[var8][2] = RANDOM.nextInt(512) - 256;
      }

      int[] var17 = new int[3];
      int[] var9 = new int[4];
      int[] var10 = new int[4];
      Object var11 = null;
      boolean var12 = true;

      for (int var13 = 0; var13 < var5; var13++) {
         for (int var14 = var7.length; --var14 >= 0; var7[var14][2] = 0) {
            var7[var14][0] = 0;
            var7[var14][1] = 0;
         }

         int var15;
         int var18;
         if (var12) {
            var18 = 0;
            var15 = var4;
         } else {
            var18 = var4 - 1;
            var15 = -1;
         }

         while (true) {
            var1.getPixel(var18, var13, var9);

            for (int var16 = 0; var16 < 3; var16++) {
               var9[var16] = (var9[var16] << 4) + var6[var18 + 1][var16] + 8 >> 4;
               if (var9[var16] > 255) {
                  var9[var16] = 255;
               } else if (var9[var16] < 0) {
                  var9[var16] = 0;
               }
            }

            var11 = var3.getDataElements(toIntARGB(var9), var11);
            var2.setDataElements(var18, var13, var11);
            var2.getPixel(var18, var13, var10);
            toRGBArray(var3.getRGB(var10[0]), var10);
            var17[0] = var9[0] - var10[0];
            var17[1] = var9[1] - var10[1];
            var17[2] = var9[2] - var10[2];
            if (var12) {
               var6[var18 + 2][0] = var6[var18 + 2][0] + var17[0] * 7;
               var6[var18 + 2][1] = var6[var18 + 2][1] + var17[1] * 7;
               var6[var18 + 2][2] = var6[var18 + 2][2] + var17[2] * 7;
               var7[var18][0] = var7[var18][0] + var17[0] * 3;
               var7[var18][1] = var7[var18][1] + var17[1] * 3;
               var7[var18][2] = var7[var18][2] + var17[2] * 3;
               var7[var18 + 1][0] = var7[var18 + 1][0] + var17[0] * 5;
               var7[var18 + 1][1] = var7[var18 + 1][1] + var17[1] * 5;
               var7[var18 + 1][2] = var7[var18 + 1][2] + var17[2] * 5;
               var7[var18 + 2][0] = var7[var18 + 2][0] + var17[0];
               var7[var18 + 2][1] = var7[var18 + 2][1] + var17[1];
               var7[var18 + 2][2] = var7[var18 + 2][2] + var17[2];
               if (++var18 >= var15) {
                  break;
               }
            } else {
               var6[var18][0] = var6[var18][0] + var17[0] * 7;
               var6[var18][1] = var6[var18][1] + var17[1] * 7;
               var6[var18][2] = var6[var18][2] + var17[2] * 7;
               var7[var18 + 2][0] = var7[var18 + 2][0] + var17[0] * 3;
               var7[var18 + 2][1] = var7[var18 + 2][1] + var17[1] * 3;
               var7[var18 + 2][2] = var7[var18 + 2][2] + var17[2] * 3;
               var7[var18 + 1][0] = var7[var18 + 1][0] + var17[0] * 5;
               var7[var18 + 1][1] = var7[var18 + 1][1] + var17[1] * 5;
               var7[var18 + 1][2] = var7[var18 + 1][2] + var17[2] * 5;
               var7[var18][0] = var7[var18][0] + var17[0];
               var7[var18][1] = var7[var18][1] + var17[1];
               var7[var18][2] = var7[var18][2] + var17[2];
               if (--var18 <= var15) {
                  break;
               }
            }
         }

         int[][] var19 = var6;
         var6 = var7;
         var7 = var19;
         if (this.alternateScans) {
            var12 = !var12;
         }
      }

      return var2;
   }
}
