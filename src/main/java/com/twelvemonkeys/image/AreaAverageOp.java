package com.twelvemonkeys.image;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Point2D.Float;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.RasterOp;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class AreaAverageOp implements BufferedImageOp, RasterOp {
   private final int width;
   private final int height;
   private Rectangle sourceRegion;

   public AreaAverageOp(int var1, int var2) {
      this.width = var1;
      this.height = var2;
   }

   public Rectangle getSourceRegion() {
      return this.sourceRegion == null ? null : new Rectangle(this.sourceRegion);
   }

   public void setSourceRegion(Rectangle var1) {
      if (var1 == null) {
         this.sourceRegion = null;
      } else if (this.sourceRegion == null) {
         this.sourceRegion = new Rectangle(var1);
      } else {
         this.sourceRegion.setBounds(var1);
      }
   }

   @Override
   public BufferedImage filter(BufferedImage var1, BufferedImage var2) {
      BufferedImage var3 = var2 != null ? var2 : this.createCompatibleDestImage(var1, null);
      this.filterImpl(var1.getRaster(), var3.getRaster());
      return var3;
   }

   private void resample(BufferedImage var1, BufferedImage var2, AffineTransform var3) {
      Graphics2D var4 = var2.createGraphics();
      var4.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

      try {
         var4.drawImage(var1, var3, null);
      } finally {
         var4.dispose();
      }
   }

   @Override
   public WritableRaster filter(Raster var1, WritableRaster var2) {
      WritableRaster var3 = var2 != null ? var2 : this.createCompatibleDestRaster(var1);
      return this.filterImpl(var1, var3);
   }

   private WritableRaster filterImpl(Raster var1, WritableRaster var2) {
      if (this.sourceRegion != null) {
         int var3 = this.sourceRegion.x;
         int var4 = this.sourceRegion.y;
         int var5 = this.sourceRegion.width;
         int var6 = this.sourceRegion.height;
         boolean var7 = var1 == var2;
         var2 = var2.createWritableChild(var3, var4, var5, var6, 0, 0, null);
         var1 = var7 ? var2 : var1.createChild(var3, var4, var5, var6, 0, 0, null);
      }

      int var34 = var1.getWidth();
      int var35 = var1.getHeight();
      int var36 = (var34 + this.width - 1) / this.width;
      int var37 = (var35 + this.height - 1) / this.height;
      boolean var38 = var34 % this.width != 0;
      boolean var8 = var35 % this.height != 0;
      int var9 = var1.getNumDataElements();
      int var10 = var1.getNumBands();
      int var11 = var1.getTransferType();
      Object var12 = null;
      int[] var15 = null;
      int[] var16 = null;
      if (var1.getTransferType() == 1) {
         if (var1.getSampleModel() instanceof SinglePixelPackedSampleModel) {
            SinglePixelPackedSampleModel var17 = (SinglePixelPackedSampleModel)var1.getSampleModel();
            var15 = var17.getBitMasks();
            var16 = var17.getBitOffsets();
         } else {
            var15 = new int[]{65535};
            var16 = new int[]{0};
         }
      }

      for (int var39 = 0; var39 < this.height; var39++) {
         int var14;
         if (var8 && var39 >= this.height) {
            var14 = var35 - var39 * var37;
         } else {
            var14 = var37;
         }

         for (int var18 = 0; var18 < this.width; var18++) {
            int var13;
            if (var38 && var18 >= this.width) {
               var13 = var34 - var18 * var36;
            } else {
               var13 = var36;
            }

            int var19 = var13 * var14;
            int var20 = var19 * var9;

            try {
               var12 = var1.getDataElements(var18 * var36, var39 * var37, var13, var14, var12);
            } catch (IndexOutOfBoundsException var33) {
               continue;
            }

            double var21 = 0.0;
            double var23 = 0.0;
            double var25 = 0.0;
            double var27 = 0.0;
            switch (var11) {
               case 0:
                  byte[] var29 = (byte[])var12;

                  for (int var49 = 0; var49 < var20; var49 += var9) {
                     var21 += var29[var49] & 255;
                     if (var10 > 1) {
                        var23 += var29[var49 + 1] & 255;
                        var25 += var29[var49 + 2] & 255;
                        if (var10 > 3) {
                           var27 += var29[var49 + 3] & 255;
                        }
                     }
                  }

                  var21 /= var19;
                  if (var10 > 1) {
                     var23 /= var19;
                     var25 /= var19;
                     if (var10 > 3) {
                        var27 /= var19;
                     }
                  }

                  var29[0] = (byte)clamp((int)var21);
                  if (var10 > 1) {
                     var29[1] = (byte)clamp((int)var23);
                     var29[2] = (byte)clamp((int)var25);
                     if (var10 > 3) {
                        var29[3] = (byte)clamp((int)var27);
                     }
                  }
                  break;
               case 1:
                  if (var15 != null) {
                     short[] var50 = (short[])var12;

                     for (int var32 = 0; var32 < var20; var32 += var9) {
                        var21 += (var50[var32] & var15[0]) >> var16[0];
                        if (var15.length > 1) {
                           var23 += (var50[var32] & var15[1]) >> var16[1];
                           var25 += (var50[var32] & var15[2]) >> var16[2];
                           if (var15.length > 3) {
                              var27 += (var50[var32] & var15[3]) >> var16[3];
                           }
                        }
                     }

                     var21 /= var19;
                     var23 /= var19;
                     var25 /= var19;
                     var27 /= var19;
                     var50[0] = (short)((int)var21 << var16[0] & var15[0]);
                     if (var15.length > 1) {
                        var50[0] |= (short)((int)var23 << var16[1] & var15[1]);
                        var50[0] |= (short)((int)var25 << var16[2] & var15[2]);
                        if (var15.length > 3) {
                           var50[0] |= (short)((int)var27 << var16[3] & var15[3]);
                        }
                     }
                     break;
                  }

                  throw new IllegalArgumentException("TransferType not supported: " + var11);
               case 2:
               default:
                  throw new IllegalArgumentException("TransferType not supported: " + var11);
               case 3:
                  int[] var30 = (int[])var12;

                  for (int var31 = 0; var31 < var20; var31 += var9) {
                     var21 += (var30[var31] & 0xFF000000) >> 24;
                     var23 += (var30[var31] & 0xFF0000) >> 16;
                     var25 += (var30[var31] & 0xFF00) >> 8;
                     var27 += var30[var31] & 0xFF;
                  }

                  var21 /= var19;
                  var23 /= var19;
                  var25 /= var19;
                  var27 /= var19;
                  var30[0] = clamp((int)var21) << 24;
                  var30[0] |= clamp((int)var23) << 16;
                  var30[0] |= clamp((int)var25) << 8;
                  var30[0] |= clamp((int)var27);
            }

            var2.setDataElements(var18, var39, 1, 1, var12);
         }
      }

      return var2;
   }

   private static int clamp(int var0) {
      return var0 > 255 ? 255 : var0;
   }

   @Override
   public RenderingHints getRenderingHints() {
      return null;
   }

   @Override
   public BufferedImage createCompatibleDestImage(BufferedImage var1, ColorModel var2) {
      ColorModel var3 = var2 != null ? var2 : var1.getColorModel();
      return new BufferedImage(var3, ImageUtil.createCompatibleWritableRaster(var1, var3, this.width, this.height), var3.isAlphaPremultiplied(), null);
   }

   @Override
   public WritableRaster createCompatibleDestRaster(Raster var1) {
      return var1.createCompatibleWritableRaster(this.width, this.height);
   }

   @Override
   public Rectangle2D getBounds2D(Raster var1) {
      return new Rectangle(this.width, this.height);
   }

   @Override
   public Rectangle2D getBounds2D(BufferedImage var1) {
      return new Rectangle(this.width, this.height);
   }

   @Override
   public Point2D getPoint2D(Point2D var1, Point2D var2) {
      if (var2 == null) {
         if (var1 instanceof Double) {
            var2 = new Double();
         } else {
            var2 = new Float();
         }
      }

      var2.setLocation(var1);
      return (Point2D)var2;
   }

   public static void main(String[] var0) throws IOException {
      BufferedImage var1 = ImageIO.read(new File("2006-Lamborghini-Gallardo-Spyder-Y-T-1600x1200.png"));
      int var2 = 0;

      while (var2 < 100) {
         var2++;
      }

      long var8 = System.currentTimeMillis();
      var1 = new AreaAverageOp(500, 600).filter(var1, null);
      long var4 = System.currentTimeMillis() - var8;
      System.out.println("time: " + var4 + " ms");
      JFrame var6 = new JFrame("Test");
      var6.setDefaultCloseOperation(3);
      var6.setContentPane(new JScrollPane(new JLabel(new BufferedImageIcon(var1))));
      var6.pack();
      var6.setVisible(true);
   }
}
