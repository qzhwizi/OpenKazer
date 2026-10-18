package com.twelvemonkeys.image;

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
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;

public class ResampleOp implements BufferedImageOp {
   public static final int FILTER_UNDEFINED = 0;
   public static final int FILTER_POINT = 1;
   public static final int FILTER_BOX = 2;
   public static final int FILTER_TRIANGLE = 3;
   public static final int FILTER_HERMITE = 4;
   public static final int FILTER_HANNING = 5;
   public static final int FILTER_HAMMING = 6;
   public static final int FILTER_BLACKMAN = 7;
   public static final int FILTER_GAUSSIAN = 8;
   public static final int FILTER_QUADRATIC = 9;
   public static final int FILTER_CUBIC = 10;
   public static final int FILTER_CATROM = 11;
   public static final int FILTER_MITCHELL = 12;
   public static final int FILTER_LANCZOS = 13;
   public static final int FILTER_BLACKMAN_BESSEL = 14;
   public static final int FILTER_BLACKMAN_SINC = 15;
   public static final RenderingHints.Key KEY_RESAMPLE_INTERPOLATION = new ResampleOp.Key("ResampleInterpolation");
   public static final Object VALUE_INTERPOLATION_POINT = new ResampleOp.Value(KEY_RESAMPLE_INTERPOLATION, "Point", 1);
   public static final Object VALUE_INTERPOLATION_BOX = new ResampleOp.Value(KEY_RESAMPLE_INTERPOLATION, "Box", 2);
   public static final Object VALUE_INTERPOLATION_TRIANGLE = new ResampleOp.Value(KEY_RESAMPLE_INTERPOLATION, "Triangle", 3);
   public static final Object VALUE_INTERPOLATION_HERMITE = new ResampleOp.Value(KEY_RESAMPLE_INTERPOLATION, "Hermite", 4);
   public static final Object VALUE_INTERPOLATION_HANNING = new ResampleOp.Value(KEY_RESAMPLE_INTERPOLATION, "Hanning", 5);
   public static final Object VALUE_INTERPOLATION_HAMMING = new ResampleOp.Value(KEY_RESAMPLE_INTERPOLATION, "Hamming", 6);
   public static final Object VALUE_INTERPOLATION_BLACKMAN = new ResampleOp.Value(KEY_RESAMPLE_INTERPOLATION, "Blackman", 7);
   public static final Object VALUE_INTERPOLATION_GAUSSIAN = new ResampleOp.Value(KEY_RESAMPLE_INTERPOLATION, "Gaussian", 8);
   public static final Object VALUE_INTERPOLATION_QUADRATIC = new ResampleOp.Value(KEY_RESAMPLE_INTERPOLATION, "Quadratic", 9);
   public static final Object VALUE_INTERPOLATION_CUBIC = new ResampleOp.Value(KEY_RESAMPLE_INTERPOLATION, "Cubic", 10);
   public static final Object VALUE_INTERPOLATION_CATROM = new ResampleOp.Value(KEY_RESAMPLE_INTERPOLATION, "Catrom", 11);
   public static final Object VALUE_INTERPOLATION_MITCHELL = new ResampleOp.Value(KEY_RESAMPLE_INTERPOLATION, "Mitchell", 12);
   public static final Object VALUE_INTERPOLATION_LANCZOS = new ResampleOp.Value(KEY_RESAMPLE_INTERPOLATION, "Lanczos", 13);
   public static final Object VALUE_INTERPOLATION_BLACKMAN_BESSEL = new ResampleOp.Value(KEY_RESAMPLE_INTERPOLATION, "Blackman-Bessel", 14);
   public static final Object VALUE_INTERPOLATION_BLACKMAN_SINC = new ResampleOp.Value(KEY_RESAMPLE_INTERPOLATION, "Blackman-Sinc", 15);
   private final int width;
   private final int height;
   private final int filterType;
   private static final double B = 0.3333333333333333;
   private static final double C = 0.3333333333333333;
   private static final double P0 = 0.8888888888888888;
   private static final double P2 = -2.0;
   private static final double P3 = 1.1666666666666667;
   private static final double Q0 = 1.7777777777777777;
   private static final double Q1 = -3.3333333333333335;
   private static final double Q2 = 2.0;
   private static final double Q3 = -0.3888888888888889;

   public ResampleOp(int var1, int var2) {
      this(var1, var2, 0);
   }

   public ResampleOp(int var1, int var2, RenderingHints var3) {
      this(var1, var2, getFilterType(var3));
   }

   public ResampleOp(int var1, int var2, int var3) {
      if (var1 > 0 && var2 > 0) {
         this.width = var1;
         this.height = var2;
         this.filterType = validateFilterType(var3);
      } else {
         throw new IllegalArgumentException("width and height must be positive");
      }
   }

   private static int validateFilterType(int var0) {
      switch (var0) {
         case 0:
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
            return var0;
         default:
            throw new IllegalArgumentException("Unknown filter type: " + var0);
      }
   }

   private static int getFilterType(RenderingHints var0) {
      if (var0 == null) {
         return 0;
      } else if (var0.containsKey(KEY_RESAMPLE_INTERPOLATION)) {
         Object var1 = var0.get(KEY_RESAMPLE_INTERPOLATION);
         if (!KEY_RESAMPLE_INTERPOLATION.isCompatibleValue(var1)) {
            throw new IllegalArgumentException(var1 + " incompatible with key " + KEY_RESAMPLE_INTERPOLATION);
         } else {
            return var1 != null ? ((ResampleOp.Value)var1).getFilterType() : 0;
         }
      } else if (!RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR.equals(var0.get(RenderingHints.KEY_INTERPOLATION))
         && (
            var0.containsKey(RenderingHints.KEY_INTERPOLATION)
               || !RenderingHints.VALUE_RENDER_SPEED.equals(var0.get(RenderingHints.KEY_RENDERING))
                  && !RenderingHints.VALUE_COLOR_RENDER_SPEED.equals(var0.get(RenderingHints.KEY_COLOR_RENDERING))
         )) {
         if (RenderingHints.VALUE_INTERPOLATION_BILINEAR.equals(var0.get(RenderingHints.KEY_INTERPOLATION))) {
            return 3;
         } else if (RenderingHints.VALUE_INTERPOLATION_BICUBIC.equals(var0.get(RenderingHints.KEY_INTERPOLATION))) {
            return 9;
         } else {
            return !RenderingHints.VALUE_RENDER_QUALITY.equals(var0.get(RenderingHints.KEY_RENDERING))
                  && !RenderingHints.VALUE_COLOR_RENDER_QUALITY.equals(var0.get(RenderingHints.KEY_COLOR_RENDERING))
               ? 0
               : 12;
         }
      } else {
         return 1;
      }
   }

   @Override
   public final BufferedImage filter(BufferedImage var1, BufferedImage var2) {
      if (var1 == null) {
         throw new NullPointerException("Input == null");
      } else if (var1 == var2) {
         throw new IllegalArgumentException("Output image cannot be the same as the input image");
      } else {
         switch (this.filterType) {
            case 1:
               if (var1.getType() != 0) {
                  return fastResample(var1, this.createOutputIfNeeded(var1, var2), this.width, this.height, 1);
               }
            case 3:
               if (var1.getType() != 0) {
                  return fastResample(var1, this.createOutputIfNeeded(var1, var2), this.width, this.height, 2);
               }
            case 9:
               if (var1.getType() != 0) {
                  return fastResample(var1, this.createOutputIfNeeded(var1, var2), this.width, this.height, 3);
               }
         }

         ResampleOp.InterpolationFilter var3 = createFilter(this.filterType);
         if (!(Math.min(var1.getWidth(), var1.getHeight()) <= var3.support()) && !(Math.min(this.width, this.height) <= var3.support())) {
            BufferedImage var4;
            ColorModel var5;
            if (this.filterType != 1 && this.filterType != 2 && (var5 = var1.getColorModel()) instanceof IndexColorModel) {
               var4 = ImageUtil.toBuffered(var1, var5.hasAlpha() ? 6 : 5);
            } else {
               var4 = var1;
            }

            BufferedImage var6 = var2 != null && var4.getType() != 0 ? ImageUtil.toBuffered(var2, var4.getType()) : this.createCompatibleDestImage(var4, null);
            this.resample(var4, var6, var3);
            if (var2 != null && var2 != var6) {
               ImageUtil.drawOnto(var2, var6);
               var6 = var2;
            }

            return var6;
         } else {
            return fastResample(var1, this.createOutputIfNeeded(var1, var2), this.width, this.height, 2);
         }
      }
   }

   private BufferedImage createOutputIfNeeded(BufferedImage var1, BufferedImage var2) {
      return var2 != null ? var2 : this.createCompatibleDestImage(var1, null);
   }

   private static BufferedImage fastResample(BufferedImage var0, BufferedImage var1, int var2, int var3, int var4) {
      BufferedImage var5 = var0;
      if (var4 > 1 && (var2 < var0.getWidth() || var3 < var0.getHeight())) {
         int var12 = var2;
         int var13 = var3;

         while (var12 < var0.getWidth() / 2) {
            var12 *= 2;
         }

         while (var13 < var0.getHeight() / 2) {
            var13 *= 2;
         }

         double var6 = (double)var12 / var0.getWidth();
         double var8 = (double)var13 / var0.getHeight();
         AffineTransform var10 = AffineTransform.getScaleInstance(var6, var8);
         AffineTransformOp var11 = new AffineTransformOp(var10, 2);
         var5 = var11.filter(var0, null);
      }

      AffineTransformOp var20 = null;
      double var14 = (double)var2 / var5.getWidth();
      double var15 = (double)var3 / var5.getHeight();
      if (var4 > 1) {
         for (; var14 < 0.5 || var15 < 0.5; var5 = var20.filter(var5, null)) {
            if (var14 >= 0.5) {
               AffineTransform var16 = AffineTransform.getScaleInstance(1.0, 0.5);
               var20 = new AffineTransformOp(var16, 2);
               var15 *= 2.0;
            } else if (var15 >= 0.5) {
               AffineTransform var17 = AffineTransform.getScaleInstance(0.5, 1.0);
               var20 = new AffineTransformOp(var17, 2);
               var14 *= 2.0;
            } else {
               var14 *= 2.0;
               var15 *= 2.0;
            }

            if (var20 == null) {
               AffineTransform var18 = AffineTransform.getScaleInstance(0.5, 0.5);
               var20 = new AffineTransformOp(var18, 2);
            }
         }
      }

      AffineTransform var19 = AffineTransform.getScaleInstance(var14, var15);
      var20 = new AffineTransformOp(var19, var4);
      return var20.filter(var5, var1);
   }

   public int getFilterType() {
      return this.filterType;
   }

   private static ResampleOp.InterpolationFilter createFilter(int var0) {
      if (var0 == 0) {
         var0 = 13;
      }

      switch (var0) {
         case 1:
            return new ResampleOp.PointFilter();
         case 2:
            return new ResampleOp.BoxFilter();
         case 3:
            return new ResampleOp.TriangleFilter();
         case 4:
            return new ResampleOp.HermiteFilter();
         case 5:
            return new ResampleOp.HanningFilter();
         case 6:
            return new ResampleOp.HammingFilter();
         case 7:
            return new ResampleOp.BlacmanFilter();
         case 8:
            return new ResampleOp.GaussianFilter();
         case 9:
            return new ResampleOp.QuadraticFilter();
         case 10:
            return new ResampleOp.CubicFilter();
         case 11:
            return new ResampleOp.CatromFilter();
         case 12:
            return new ResampleOp.MitchellFilter();
         case 13:
            return new ResampleOp.LanczosFilter();
         case 14:
            return new ResampleOp.BlackmanBesselFilter();
         case 15:
            return new ResampleOp.BlackmanSincFilter();
         default:
            throw new IllegalStateException("Unknown filter type: " + var0);
      }
   }

   @Override
   public final BufferedImage createCompatibleDestImage(BufferedImage var1, ColorModel var2) {
      if (var1 == null) {
         throw new NullPointerException("pInput == null");
      } else {
         ColorModel var3 = var2 != null ? var2 : var1.getColorModel();
         return new BufferedImage(var3, ImageUtil.createCompatibleWritableRaster(var1, var3, this.width, this.height), var3.isAlphaPremultiplied(), null);
      }
   }

   @Override
   public RenderingHints getRenderingHints() {
      Object var1;
      switch (this.filterType) {
         case 0:
            return null;
         case 1:
            var1 = VALUE_INTERPOLATION_POINT;
            break;
         case 2:
            var1 = VALUE_INTERPOLATION_BOX;
            break;
         case 3:
            var1 = VALUE_INTERPOLATION_TRIANGLE;
            break;
         case 4:
            var1 = VALUE_INTERPOLATION_HERMITE;
            break;
         case 5:
            var1 = VALUE_INTERPOLATION_HANNING;
            break;
         case 6:
            var1 = VALUE_INTERPOLATION_HAMMING;
            break;
         case 7:
            var1 = VALUE_INTERPOLATION_BLACKMAN;
            break;
         case 8:
            var1 = VALUE_INTERPOLATION_GAUSSIAN;
            break;
         case 9:
            var1 = VALUE_INTERPOLATION_QUADRATIC;
            break;
         case 10:
            var1 = VALUE_INTERPOLATION_CUBIC;
            break;
         case 11:
            var1 = VALUE_INTERPOLATION_CATROM;
            break;
         case 12:
            var1 = VALUE_INTERPOLATION_MITCHELL;
            break;
         case 13:
            var1 = VALUE_INTERPOLATION_LANCZOS;
            break;
         case 14:
            var1 = VALUE_INTERPOLATION_BLACKMAN_BESSEL;
            break;
         case 15:
            var1 = VALUE_INTERPOLATION_BLACKMAN_SINC;
            break;
         default:
            throw new IllegalStateException("Unknown filter type: " + this.filterType);
      }

      return new RenderingHints(KEY_RESAMPLE_INTERPOLATION, var1);
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

         var2.setLocation(var1);
      }

      return (Point2D)var2;
   }

   private static double sinc(double var0) {
      var0 *= Math.PI;
      return var0 != 0.0 ? Math.sin(var0) / var0 : 1.0;
   }

   private static double j1(double var0) {
      double[] var2 = new double[]{
         5.811993540016061E20,
         -6.672106568924916E19,
         2.3164335806340024E18,
         -3.588817569910106E16,
         2.9087952638347756E14,
         -1.3229834803321265E12,
         3.4132341823017006E9,
         -4695753.530642996,
         2701.1227108923235
      };
      double[] var3 = new double[]{
         1.1623987080032122E21,
         1.185770712190321E19,
         6.092061398917522E16,
         2.0816612213076075E14,
         5.2437102621676495E11,
         1.013863514358674E9,
         1501793.5949985855,
         1606.9315734814877,
         1.0
      };
      double var4 = var2[8];
      double var6 = var3[8];

      for (int var8 = 7; var8 >= 0; var8--) {
         var4 = var4 * var0 * var0 + var2[var8];
         var6 = var6 * var0 * var0 + var3[var8];
      }

      return var4 / var6;
   }

   private static double p1(double var0) {
      double[] var2 = new double[]{35224.66491336798, 62758.84524716128, 31353.963110915956, 4985.4832060594335, 211.15291828539623, 1.2571716929145342};
      double[] var3 = new double[]{35224.66491336798, 62694.34695935605, 31240.406381904104, 4930.396490181089, 203.07751891347593, 1.0};
      double var4 = var2[5];
      double var6 = var3[5];

      for (int var8 = 4; var8 >= 0; var8--) {
         var4 = var4 * (8.0 / var0) * (8.0 / var0) + var2[var8];
         var6 = var6 * (8.0 / var0) * (8.0 / var0) + var3[var8];
      }

      return var4 / var6;
   }

   private static double q1(double var0) {
      double[] var2 = new double[]{351.17519143035526, 721.0391804904475, 425.98730116544425, 83.18989576738508, 4.568171629551227, 0.03532840052740124};
      double[] var3 = new double[]{7491.737417180912, 15414.177339265098, 9152.231701516992, 1811.1867005523513, 103.81875854621337, 1.0};
      double var4 = var2[5];
      double var6 = var3[5];

      for (int var8 = 4; var8 >= 0; var8--) {
         var4 = var4 * (8.0 / var0) * (8.0 / var0) + var2[var8];
         var6 = var6 * (8.0 / var0) * (8.0 / var0) + var3[var8];
      }

      return var4 / var6;
   }

   static double besselOrderOne(double var0) {
      if (var0 == 0.0) {
         return 0.0;
      } else {
         double var2 = var0;
         if (var0 < 0.0) {
            var0 = -var0;
         }

         if (var0 < 8.0) {
            return var2 * j1(var0);
         } else {
            double var4 = Math.sqrt(2.0 / (Math.PI * var0))
               * (
                  p1(var0) * (1.0 / Math.sqrt(2.0) * (Math.sin(var0) - Math.cos(var0)))
                     - 8.0 / var0 * q1(var0) * (-1.0 / Math.sqrt(2.0) * (Math.sin(var0) + Math.cos(var0)))
               );
            if (var2 < 0.0) {
               var4 = -var4;
            }

            return var4;
         }
      }
   }

   private static double bessel(double var0) {
      return var0 == 0.0 ? Math.PI / 4 : besselOrderOne(Math.PI * var0) / (2.0 * var0);
   }

   private static double blackman(double var0) {
      return 0.42 + 0.5 * Math.cos(Math.PI * var0) + 0.08 * Math.cos((Math.PI * 2) * var0);
   }

   static int round(double var0) {
      int var2 = (int)var0;
      double var3 = var0 - var2;
      if (var3 < 0.0) {
         var3 = -var3;
      }

      if (var3 >= 0.5) {
         if (var0 < 0.0) {
            var2--;
         } else {
            var2++;
         }
      }

      return var2;
   }

   private ResampleOp.ContributorList calcXContrib(double var1, double var3, int var5, ResampleOp.InterpolationFilter var6, int var7) {
      ResampleOp.ContributorList var16 = new ResampleOp.ContributorList();
      if (var1 < 1.0) {
         double var8 = var3 / var1;
         double var10 = 1.0 / var1;
         if (var8 <= 0.5) {
            var8 = 0.500001;
            var10 = 1.0;
         }

         var16.p = new ResampleOp.Contributor[(int)(var8 * 2.0 + 1.0 + 0.5)];
         double var12 = var7 / var1;
         int var17 = (int)Math.ceil(var12 - var8);
         int var18 = (int)Math.floor(var12 + var8);
         double var19 = 0.0;

         for (int var21 = var17; var21 <= var18; var21++) {
            double var14 = var12 - var21;
            var14 = var6.filter(var14 / var10) / var10;
            int var22;
            if (var21 < 0) {
               var22 = -var21;
            } else if (var21 >= var5) {
               var22 = var5 - var21 + var5 - 1;
            } else {
               var22 = var21;
            }

            if (var22 >= var5) {
               var22 %= var5;
            } else if (var22 < 0) {
               var22 = var5 - 1;
            }

            int var23 = var16.n++;
            var16.p[var23] = new ResampleOp.Contributor();
            var16.p[var23].pixel = var22;
            var16.p[var23].weight = var14;
            var19 += var14;
         }

         if (var19 != 0.0 && var19 != 1.0) {
            var19 = 1.0 / var19;

            for (int var32 = 0; var32 < var16.n; var32++) {
               var16.p[var32].weight *= var19;
            }
         }
      } else {
         var16.p = new ResampleOp.Contributor[(int)(var3 * 2.0 + 1.0 + 0.5)];
         double var24 = var7 / var1;
         int var28 = (int)Math.ceil(var24 - var3);
         int var29 = (int)Math.floor(var24 + var3);

         for (int var31 = var28; var31 <= var29; var31++) {
            double var26 = var24 - var31;
            var26 = var6.filter(var26);
            int var20;
            if (var31 < 0) {
               var20 = -var31;
            } else if (var31 >= var5) {
               var20 = var5 - var31 + var5 - 1;
            } else {
               var20 = var31;
            }

            if (var20 >= var5) {
               var20 %= var5;
            } else if (var20 < 0) {
               var20 = var5 - 1;
            }

            int var33 = var16.n++;
            var16.p[var33] = new ResampleOp.Contributor();
            var16.p[var33].pixel = var20;
            var16.p[var33].weight = var26;
         }
      }

      return var16;
   }

   private BufferedImage resample(BufferedImage var1, BufferedImage var2, ResampleOp.InterpolationFilter var3) {
      int var4 = var2.getWidth();
      int var5 = var2.getHeight();
      int var6 = var1.getWidth();
      int var7 = var1.getHeight();
      ColorModel var8 = var1.getColorModel();
      WritableRaster var9 = ImageUtil.createCompatibleWritableRaster(var1, var8, 1, var7);
      double var10 = (double)var4 / var6;
      double var12 = (double)var5 / var7;
      ResampleOp.ContributorList[] var14 = new ResampleOp.ContributorList[var5];

      for (int var15 = 0; var15 < var14.length; var15++) {
         var14[var15] = new ResampleOp.ContributorList();
      }

      double var33 = var3.support();
      if (var12 < 1.0) {
         double var17 = var33 / var12;
         double var19 = 1.0 / var12;
         if (var17 <= 0.5) {
            var17 = 0.500001;
            var19 = 1.0;
         }

         for (int var21 = 0; var21 < var5; var21++) {
            var14[var21].p = new ResampleOp.Contributor[(int)(var17 * 2.0 + 1.0 + 0.5)];
            double var22 = var21 / var12;
            int var24 = (int)Math.ceil(var22 - var17);
            int var25 = (int)Math.floor(var22 + var17);
            double var26 = 0.0;

            for (int var28 = var24; var28 <= var25; var28++) {
               double var29 = var22 - var28;
               var29 = var3.filter(var29 / var19) / var19;
               int var31;
               if (var28 < 0) {
                  var31 = -var28;
               } else if (var28 >= var7) {
                  var31 = var7 - var28 + var7 - 1;
               } else {
                  var31 = var28;
               }

               if (var31 >= var7) {
                  var31 %= var7;
               } else if (var31 < 0) {
                  var31 = var7 - 1;
               }

               int var32 = var14[var21].n++;
               var14[var21].p[var32] = new ResampleOp.Contributor();
               var14[var21].p[var32].pixel = var31;
               var14[var21].p[var32].weight = var29;
               var26 += var29;
            }

            if (var26 != 0.0 && var26 != 1.0) {
               var26 = 1.0 / var26;

               for (int var56 = 0; var56 < var14[var21].n; var56++) {
                  var14[var21].p[var56].weight *= var26;
               }
            }
         }
      } else {
         for (int var34 = 0; var34 < var5; var34++) {
            var14[var34].p = new ResampleOp.Contributor[(int)(var33 * 2.0 + 1.0 + 0.5)];
            double var18 = var34 / var12;
            double var20 = Math.ceil(var18 - var33);
            double var41 = Math.floor(var18 + var33);

            for (int var44 = (int)var20; var44 <= var41; var44++) {
               double var47 = var18 - var44;
               var47 = var3.filter(var47);
               int var27;
               if (var44 < 0) {
                  var27 = -var44;
               } else if (var44 >= var7) {
                  var27 = var7 - var44 + var7 - 1;
               } else {
                  var27 = var44;
               }

               if (var27 >= var7) {
                  var27 %= var7;
               } else if (var27 < 0) {
                  var27 = var7 - 1;
               }

               int var57 = var14[var34].n++;
               var14[var34].p[var57] = new ResampleOp.Contributor();
               var14[var34].p[var57].pixel = var27;
               var14[var34].p[var57].weight = var47;
            }
         }
      }

      WritableRaster var35 = var1.getRaster();
      WritableRaster var36 = var2.getRaster();
      int var37 = var35.getNumBands();
      int[] var38 = new int[var37];

      for (int var39 = 0; var39 < var37; var39++) {
         var38[var39] = (1 << var1.getColorModel().getComponentSize(var39)) - 1;
      }

      for (int var40 = 0; var40 < var4; var40++) {
         ResampleOp.ContributorList var42 = this.calcXContrib(var10, var33, var6, var3, var40);

         for (int var23 = 0; var23 < var7; var23++) {
            for (int var45 = 0; var45 < var37; var45++) {
               double var49 = 0.0;
               boolean var54 = false;
               double var58 = var35.getSample(var42.p[0].pixel, var23, var45);

               for (int var30 = 0; var30 < var42.n; var30++) {
                  double var62 = var30 == 0 ? var58 : var35.getSample(var42.p[var30].pixel, var23, var45);
                  if (var62 != var58) {
                     var54 = true;
                  }

                  var49 += var62 * var42.p[var30].weight;
               }

               var49 = var54 ? round(var49) : var58;
               if (var49 < 0.0) {
                  var49 = 0.0;
               } else if (var49 > var38[var45]) {
                  var49 = var38[var45];
               }

               var9.setSample(0, var23, var45, var49);
            }
         }

         for (int var43 = 0; var43 < var5; var43++) {
            for (int var46 = 0; var46 < var37; var46++) {
               double var51 = 0.0;
               boolean var55 = false;
               double var59 = var9.getSample(0, var14[var43].p[0].pixel, var46);

               for (int var61 = 0; var61 < var14[var43].n; var61++) {
                  double var63 = var61 == 0 ? var59 : var9.getSample(0, var14[var43].p[var61].pixel, var46);
                  if (var63 != var59) {
                     var55 = true;
                  }

                  var51 += var63 * var14[var43].p[var61].weight;
               }

               var51 = var55 ? round(var51) : var59;
               if (var51 < 0.0) {
                  var51 = 0.0;
               } else if (var51 > var38[var46]) {
                  var51 = var38[var46];
               }

               var36.setSample(var40, var43, var46, var51);
            }
         }
      }

      return var2;
   }

   static class BlackmanBesselFilter implements ResampleOp.InterpolationFilter {
      @Override
      public final double filter(double var1) {
         return ResampleOp.blackman(var1 / this.support()) * ResampleOp.bessel(var1);
      }

      @Override
      public final double support() {
         return 3.2383;
      }
   }

   static class BlackmanSincFilter implements ResampleOp.InterpolationFilter {
      @Override
      public final double filter(double var1) {
         return ResampleOp.blackman(var1 / this.support()) * ResampleOp.sinc(var1);
      }

      @Override
      public final double support() {
         return 4.0;
      }
   }

   static class BlacmanFilter implements ResampleOp.InterpolationFilter {
      @Override
      public final double filter(double var1) {
         return ResampleOp.blackman(var1);
      }

      @Override
      public final double support() {
         return 1.0;
      }
   }

   static class BoxFilter implements ResampleOp.InterpolationFilter {
      private final double mSupport;

      public BoxFilter() {
         this.mSupport = 0.5;
      }

      protected BoxFilter(double var1) {
         this.mSupport = var1;
      }

      @Override
      public final double filter(double var1) {
         return var1 >= -0.5 && var1 < 0.5 ? 1.0 : 0.0;
      }

      @Override
      public final double support() {
         return this.mSupport;
      }
   }

   static class CatromFilter implements ResampleOp.InterpolationFilter {
      @Override
      public final double filter(double var1) {
         if (var1 < 0.0) {
            var1 = -var1;
         }

         if (var1 < 1.0) {
            return 0.5 * (2.0 + var1 * var1 * (-5.0 + var1 * 3.0));
         } else {
            return var1 < 2.0 ? 0.5 * (4.0 + var1 * (-8.0 + var1 * (5.0 - var1))) : 0.0;
         }
      }

      @Override
      public final double support() {
         return 2.0;
      }
   }

   static class Contributor {
      int pixel;
      double weight;
   }

   static class ContributorList {
      int n;
      ResampleOp.Contributor[] p;
   }

   static class CubicFilter implements ResampleOp.InterpolationFilter {
      @Override
      public final double filter(double var1) {
         if (var1 < 0.0) {
            var1 = -var1;
         }

         if (var1 < 1.0) {
            double var3 = var1 * var1;
            return 0.5 * var3 * var1 - var3 + 0.6666666666666666;
         } else if (var1 < 2.0) {
            var1 = 2.0 - var1;
            return 0.16666666666666666 * (var1 * var1 * var1);
         } else {
            return 0.0;
         }
      }

      @Override
      public final double support() {
         return 2.0;
      }
   }

   static class GaussianFilter implements ResampleOp.InterpolationFilter {
      @Override
      public final double filter(double var1) {
         return Math.exp(-2.0 * var1 * var1) * Math.sqrt(0.6366197723675814);
      }

      @Override
      public final double support() {
         return 1.25;
      }
   }

   static class HammingFilter implements ResampleOp.InterpolationFilter {
      @Override
      public final double filter(double var1) {
         return 0.54 + 0.46 * Math.cos(Math.PI * var1);
      }

      @Override
      public final double support() {
         return 1.0;
      }
   }

   static class HanningFilter implements ResampleOp.InterpolationFilter {
      @Override
      public final double filter(double var1) {
         return 0.5 + 0.5 * Math.cos(Math.PI * var1);
      }

      @Override
      public final double support() {
         return 1.0;
      }
   }

   static class HermiteFilter implements ResampleOp.InterpolationFilter {
      @Override
      public final double filter(double var1) {
         if (var1 < 0.0) {
            var1 = -var1;
         }

         return var1 < 1.0 ? (2.0 * var1 - 3.0) * var1 * var1 + 1.0 : 0.0;
      }

      @Override
      public final double support() {
         return 1.0;
      }
   }

   interface InterpolationFilter {
      double filter(double var1);

      double support();
   }

   static class Key extends RenderingHints.Key {
      static int sIndex = 10000;
      private final String name;

      public Key(String var1) {
         super(sIndex++);
         this.name = var1;
      }

      @Override
      public boolean isCompatibleValue(Object var1) {
         return var1 instanceof ResampleOp.Value && ((ResampleOp.Value)var1).isCompatibleKey(this);
      }

      @Override
      public String toString() {
         return this.name;
      }
   }

   static class LanczosFilter implements ResampleOp.InterpolationFilter {
      @Override
      public final double filter(double var1) {
         if (var1 < 0.0) {
            var1 = -var1;
         }

         return var1 < 3.0 ? ResampleOp.sinc(var1) * ResampleOp.sinc(var1 / 3.0) : 0.0;
      }

      @Override
      public final double support() {
         return 3.0;
      }
   }

   static class MitchellFilter implements ResampleOp.InterpolationFilter {
      @Override
      public final double filter(double var1) {
         if (var1 < -2.0) {
            return 0.0;
         } else if (var1 < -1.0) {
            return 1.7777777777777777 - var1 * (-3.3333333333333335 - var1 * (2.0 - var1 * -0.3888888888888889));
         } else if (var1 < 0.0) {
            return 0.8888888888888888 + var1 * var1 * (-2.0 - var1 * 1.1666666666666667);
         } else if (var1 < 1.0) {
            return 0.8888888888888888 + var1 * var1 * (-2.0 + var1 * 1.1666666666666667);
         } else {
            return var1 < 2.0 ? 1.7777777777777777 + var1 * (-3.3333333333333335 + var1 * (2.0 + var1 * -0.3888888888888889)) : 0.0;
         }
      }

      @Override
      public final double support() {
         return 2.0;
      }
   }

   static class PointFilter extends ResampleOp.BoxFilter {
      public PointFilter() {
         super(0.0);
      }
   }

   static class QuadraticFilter implements ResampleOp.InterpolationFilter {
      @Override
      public final double filter(double var1) {
         if (var1 < 0.0) {
            var1 = -var1;
         }

         if (var1 < 0.5) {
            return 0.75 - var1 * var1;
         } else {
            return var1 < 1.5 ? 0.5 * (--var1 * var1) : 0.0;
         }
      }

      @Override
      public final double support() {
         return 1.5;
      }
   }

   static class TriangleFilter implements ResampleOp.InterpolationFilter {
      @Override
      public final double filter(double var1) {
         if (var1 < 0.0) {
            var1 = -var1;
         }

         return var1 < 1.0 ? 1.0 - var1 : 0.0;
      }

      @Override
      public final double support() {
         return 1.0;
      }
   }

   static final class Value {
      private final RenderingHints.Key key;
      private final String name;
      private final int type;

      public Value(RenderingHints.Key var1, String var2, int var3) {
         this.key = var1;
         this.name = var2;
         this.type = ResampleOp.validateFilterType(var3);
      }

      public boolean isCompatibleKey(ResampleOp.Key var1) {
         return var1 == this.key;
      }

      public int getFilterType() {
         return this.type;
      }

      @Override
      public String toString() {
         return this.name;
      }
   }
}
