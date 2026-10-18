package com.twelvemonkeys.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentSampleModel;
import java.awt.image.ConvolveOp;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferUShort;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.IndexColorModel;
import java.awt.image.Kernel;
import java.awt.image.PackedColorModel;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.util.Hashtable;

public final class ImageUtil {
   public static final int ROTATE_90_CCW = -90;
   public static final int ROTATE_90_CW = 90;
   public static final int ROTATE_180 = 180;
   public static final int FLIP_VERTICAL = -1;
   public static final int FLIP_HORIZONTAL = 1;
   public static final int EDGE_ZERO_FILL = 0;
   public static final int EDGE_NO_OP = 1;
   public static final int EDGE_REFLECT = 2;
   public static final int EDGE_WRAP = 3;
   public static final int DITHER_DEFAULT = 0;
   public static final int DITHER_NONE = 1;
   public static final int DITHER_DIFFUSION = 2;
   public static final int DITHER_DIFFUSION_ALTSCANS = 3;
   public static final int COLOR_SELECTION_DEFAULT = 0;
   public static final int COLOR_SELECTION_FAST = 256;
   public static final int COLOR_SELECTION_QUALITY = 512;
   public static final int TRANSPARENCY_DEFAULT = 0;
   public static final int TRANSPARENCY_OPAQUE = 65536;
   public static final int TRANSPARENCY_BITMASK = 131072;
   protected static final int TRANSPARENCY_TRANSLUCENT = 196608;
   private static final int BI_TYPE_ANY = -1;
   private static boolean VM_SUPPORTS_ACCELERATION = true;
   private static final float[] SHARPEN_MATRIX = new float[]{0.0F, -0.3F, 0.0F, -0.3F, 2.2F, -0.3F, 0.0F, -0.3F, 0.0F};
   private static final Kernel SHARPEN_KERNEL = new Kernel(3, 3, SHARPEN_MATRIX);
   private static final Component NULL_COMPONENT = new Component() {};
   private static MediaTracker sTracker = new MediaTracker(NULL_COMPONENT);
   protected static final AffineTransform IDENTITY_TRANSFORM = new AffineTransform();
   protected static final Point LOCATION_UPPER_LEFT = new Point(0, 0);
   private static final GraphicsConfiguration DEFAULT_CONFIGURATION = getDefaultGraphicsConfiguration();

   private static GraphicsConfiguration getDefaultGraphicsConfiguration() {
      try {
         GraphicsEnvironment var0 = GraphicsEnvironment.getLocalGraphicsEnvironment();
         if (!var0.isHeadlessInstance()) {
            return var0.getDefaultScreenDevice().getDefaultConfiguration();
         }
      } catch (LinkageError var1) {
         VM_SUPPORTS_ACCELERATION = false;
      }

      return null;
   }

   private ImageUtil() {
   }

   public static BufferedImage toBuffered(RenderedImage var0) {
      if (var0 instanceof BufferedImage) {
         return (BufferedImage)var0;
      } else if (var0 == null) {
         throw new IllegalArgumentException("original == null");
      } else {
         String[] var2 = var0.getPropertyNames();
         Hashtable var1;
         if (var2 != null && var2.length > 0) {
            var1 = new Hashtable(var2.length);

            for (String var6 : var2) {
               var1.put(var6, var0.getProperty(var6));
            }
         } else {
            var1 = null;
         }

         Raster var7 = var0.getData();
         WritableRaster var8;
         if (var7 instanceof WritableRaster) {
            var8 = (WritableRaster)var7;
         } else {
            var8 = var7.createCompatibleWritableRaster();
            var8 = var0.copyData(var8);
         }

         ColorModel var10 = var0.getColorModel();
         return new BufferedImage(var10, var8, var10.isAlphaPremultiplied(), var1);
      }
   }

   public static BufferedImage toBuffered(RenderedImage var0, int var1) {
      if (var0 instanceof BufferedImage && ((BufferedImage)var0).getType() == var1) {
         return (BufferedImage)var0;
      } else if (var0 == null) {
         throw new IllegalArgumentException("original == null");
      } else {
         BufferedImage var2 = createBuffered(var0.getWidth(), var0.getHeight(), var1, 3);
         Graphics2D var3 = var2.createGraphics();

         try {
            var3.setComposite(AlphaComposite.Src);
            var3.drawRenderedImage(var0, IDENTITY_TRANSFORM);
         } finally {
            var3.dispose();
         }

         return var2;
      }
   }

   public static BufferedImage toBuffered(BufferedImage var0, int var1) {
      return toBuffered((RenderedImage)var0, var1);
   }

   public static BufferedImage toBuffered(Image var0) {
      if (var0 instanceof BufferedImage) {
         return (BufferedImage)var0;
      } else if (var0 == null) {
         throw new IllegalArgumentException("original == null");
      } else {
         BufferedImageFactory var1 = new BufferedImageFactory(var0);
         return var1.getBufferedImage();
      }
   }

   public static BufferedImage createCopy(BufferedImage var0) {
      if (var0 == null) {
         throw new IllegalArgumentException("image == null");
      } else {
         ColorModel var1 = var0.getColorModel();
         BufferedImage var2 = new BufferedImage(var1, var1.createCompatibleWritableRaster(var0.getWidth(), var0.getHeight()), var1.isAlphaPremultiplied(), null);
         drawOnto(var2, var0);
         return var2;
      }
   }

   static WritableRaster createRaster(int var0, int var1, Object var2, ColorModel var3) {
      Object var4 = null;
      WritableRaster var5 = null;
      int var6;
      if (var2 instanceof int[]) {
         int[] var7 = (int[])var2;
         var4 = new DataBufferInt(var7, var7.length);
         var6 = var3.getNumComponents();
      } else if (var2 instanceof short[]) {
         short[] var9 = (short[])var2;
         var4 = new DataBufferUShort(var9, var9.length);
         var6 = var9.length / (var0 * var1);
      } else if (var2 instanceof byte[]) {
         byte[] var10 = (byte[])var2;
         var4 = new DataBufferByte(var10, var10.length);
         if (var3 instanceof IndexColorModel) {
            var6 = 1;
         } else {
            var6 = var10.length / (var0 * var1);
         }
      } else {
         var6 = -1;
         var5 = var3.createCompatibleWritableRaster(var0, var1);
         var5.setDataElements(0, 0, var0, var1, var2);
      }

      if (var5 == null) {
         if (var3 instanceof IndexColorModel && isIndexedPacked((IndexColorModel)var3)) {
            var5 = Raster.createPackedRaster((DataBuffer)var4, var0, var1, var3.getPixelSize(), LOCATION_UPPER_LEFT);
         } else if (var3 instanceof PackedColorModel) {
            PackedColorModel var11 = (PackedColorModel)var3;
            var5 = Raster.createPackedRaster((DataBuffer)var4, var0, var1, var0, var11.getMasks(), LOCATION_UPPER_LEFT);
         } else {
            int[] var12 = new int[var6];
            int var8 = 0;

            while (var8 < var6) {
               var12[var8++] = var6 - var8;
            }

            var5 = Raster.createInterleavedRaster((DataBuffer)var4, var0, var1, var0 * var6, var6, var12, LOCATION_UPPER_LEFT);
         }
      }

      return var5;
   }

   private static boolean isIndexedPacked(IndexColorModel var0) {
      return var0.getPixelSize() == 1 || var0.getPixelSize() == 2 || var0.getPixelSize() == 4;
   }

   static WritableRaster createCompatibleWritableRaster(BufferedImage var0, ColorModel var1, int var2, int var3) {
      if (var1 != null && !equals(var0.getColorModel(), var1)) {
         return var1.createCompatibleWritableRaster(var2, var3);
      } else {
         switch (var0.getType()) {
            case 0:
               SampleModel var5 = var0.getRaster().getSampleModel();
               if (var5 instanceof ComponentSampleModel) {
                  int[] var7 = ((ComponentSampleModel)var5).getBandOffsets();
                  return Raster.createInterleavedRaster(var5.getDataType(), var2, var3, var2 * var7.length, var7.length, var7, null);
               }
            case 1:
            case 2:
            case 3:
            case 4:
            default:
               return var0.getColorModel().createCompatibleWritableRaster(var2, var3);
            case 5:
               int[] var6 = new int[]{2, 1, 0};
               return Raster.createInterleavedRaster(0, var2, var3, var2 * 3, 3, var6, null);
            case 6:
            case 7:
               int[] var4 = new int[]{3, 2, 1, 0};
               return Raster.createInterleavedRaster(0, var2, var3, var2 * 4, 4, var4, null);
         }
      }
   }

   public static BufferedImage toBuffered(Image var0, int var1) {
      return toBuffered(var0, var1, null);
   }

   private static BufferedImage toBuffered(Image var0, int var1, IndexColorModel var2) {
      if (!(var0 instanceof BufferedImage) || ((BufferedImage)var0).getType() != var1 || var2 != null && !equals(((BufferedImage)var0).getColorModel(), var2)) {
         if (var0 == null) {
            throw new IllegalArgumentException("original == null");
         } else {
            BufferedImage var3;
            if (var2 == null) {
               var3 = createBuffered(getWidth(var0), getHeight(var0), var1, 3);
            } else {
               var3 = new BufferedImage(getWidth(var0), getHeight(var0), var1, var2);
            }

            drawOnto(var3, var0);
            return var3;
         }
      } else {
         return (BufferedImage)var0;
      }
   }

   static void drawOnto(BufferedImage var0, Image var1) {
      Graphics2D var2 = var0.createGraphics();

      try {
         var2.setComposite(AlphaComposite.Src);
         var2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
         var2.drawImage(var1, 0, 0, null);
      } finally {
         var2.dispose();
      }
   }

   public static BufferedImage createFlipped(Image var0, int var1) {
      switch (var1) {
         case -1:
         case 1:
            BufferedImage var2 = toBuffered(var0);
            AffineTransform var3;
            if (var1 == 1) {
               var3 = AffineTransform.getTranslateInstance(0.0, var2.getHeight());
               var3.scale(1.0, -1.0);
            } else {
               var3 = AffineTransform.getTranslateInstance(var2.getWidth(), 0.0);
               var3.scale(-1.0, 1.0);
            }

            AffineTransformOp var4 = new AffineTransformOp(var3, 1);
            return var4.filter(var2, null);
         default:
            throw new IllegalArgumentException("Illegal direction: " + var1);
      }
   }

   public static BufferedImage createRotated(Image var0, int var1) {
      switch (var1) {
         case -90:
         case 90:
         case 180:
            return createRotated(var0, Math.toRadians(var1));
         default:
            throw new IllegalArgumentException("Illegal direction: " + var1);
      }
   }

   public static BufferedImage createRotated(Image var0, double var1) {
      return createRotated0(toBuffered(var0), var1);
   }

   private static BufferedImage createRotated0(BufferedImage var0, double var1) {
      if (Math.abs(Math.toDegrees(var1)) % 360.0 == 0.0) {
         return var0;
      } else {
         boolean var3 = Math.abs(Math.toDegrees(var1)) % 90.0 == 0.0;
         int var4 = var0.getWidth();
         int var5 = var0.getHeight();
         double var6 = Math.abs(Math.sin(var1));
         double var8 = Math.abs(Math.cos(var1));
         int var10 = (int)Math.floor(var4 * var8 + var5 * var6);
         int var11 = (int)Math.floor(var5 * var8 + var4 * var6);
         AffineTransform var12 = AffineTransform.getTranslateInstance((var10 - var4) / 2.0, (var11 - var5) / 2.0);
         var12.rotate(var1, var4 / 2.0, var5 / 2.0);
         BufferedImage var13;
         if (var3) {
            ColorModel var14 = var0.getColorModel();
            var13 = new BufferedImage(var14, var14.createCompatibleWritableRaster(var10, var11), var14.isAlphaPremultiplied(), null);
         } else {
            var13 = createTransparent(var10, var11);
         }

         Graphics2D var18 = var13.createGraphics();

         try {
            var18.transform(var12);
            if (!var3) {
               var18.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
               var18.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
               var18.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
               var18.setPaint(new TexturePaint(var0, new Float(0.0F, 0.0F, var0.getWidth(), var0.getHeight())));
               var18.fillRect(0, 0, var0.getWidth(), var0.getHeight());
            } else {
               var18.drawImage(var0, 0, 0, null);
            }
         } finally {
            var18.dispose();
         }

         return var13;
      }
   }

   public static BufferedImage createScaled(Image var0, int var1, int var2, int var3) {
      int var5 = -1;
      ColorModel var4;
      if (var0 instanceof RenderedImage) {
         var4 = ((RenderedImage)var0).getColorModel();
         if (var0 instanceof BufferedImage) {
            var5 = ((BufferedImage)var0).getType();
         }
      } else {
         BufferedImageFactory var6 = new BufferedImageFactory(var0);
         var4 = var6.getColorModel();
      }

      BufferedImage var9 = createResampled(var0, var1, var2, var3);
      if (var5 != var9.getType() && var5 != -1 || !equals(var9.getColorModel(), var4)) {
         WritableRaster var7;
         if (var0 instanceof BufferedImage) {
            var7 = createCompatibleWritableRaster((BufferedImage)var0, var4, var1, var2);
         } else {
            var7 = var4.createCompatibleWritableRaster(var1, var2);
         }

         BufferedImage var8 = new BufferedImage(var4, var7, var4.isAlphaPremultiplied(), null);
         if (var4 instanceof IndexColorModel && var3 == 4) {
            new DiffusionDither((IndexColorModel)var4).filter(var9, var8);
         } else {
            drawOnto(var8, var9);
         }

         var9 = var8;
      }

      return var9;
   }

   private static boolean equals(ColorModel var0, ColorModel var1) {
      if (var0 == var1) {
         return true;
      } else if (!var0.equals(var1)) {
         return false;
      } else if (var0 instanceof IndexColorModel) {
         IndexColorModel var2 = (IndexColorModel)var0;
         IndexColorModel var3 = (IndexColorModel)var1;
         int var4 = var2.getMapSize();
         int var5 = var3.getMapSize();
         if (var4 != var5) {
            return false;
         } else {
            for (int var6 = 0; var6 < var4; var6++) {
               if (var2.getRGB(var6) != var3.getRGB(var6)) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return true;
      }
   }

   public static BufferedImage createResampled(Image var0, int var1, int var2, int var3) {
      BufferedImage var4 = var0 instanceof BufferedImage ? (BufferedImage)var0 : toBuffered(var0, 6);
      return createResampled(var4, var1, var2, var3);
   }

   public static BufferedImage createResampled(RenderedImage var0, int var1, int var2, int var3) {
      BufferedImage var4 = var0 instanceof BufferedImage ? (BufferedImage)var0 : toBuffered(var0, var0.getColorModel().hasAlpha() ? 6 : 5);
      return createResampled(var4, var1, var2, var3);
   }

   public static BufferedImage createResampled(BufferedImage var0, int var1, int var2, int var3) {
      return new ResampleOp(var1, var2, convertAWTHints(var3)).filter(var0, null);
   }

   private static int convertAWTHints(int var0) {
      switch (var0) {
         case 2:
         case 8:
            return 1;
         case 4:
            return 13;
         case 16:
            return 2;
         default:
            return 9;
      }
   }

   public static IndexColorModel getIndexColorModel(Image var0, int var1, int var2) {
      return IndexImage.getIndexColorModel(var0, var1, var2);
   }

   public static BufferedImage createIndexed(Image var0) {
      return IndexImage.getIndexedImage(toBuffered(var0), 256, Color.black, 0);
   }

   public static BufferedImage createIndexed(Image var0, int var1, Color var2, int var3) {
      return IndexImage.getIndexedImage(toBuffered(var0), var1, var2, var3);
   }

   public static BufferedImage createIndexed(Image var0, IndexColorModel var1, Color var2, int var3) {
      return IndexImage.getIndexedImage(toBuffered(var0), var1, var2, var3);
   }

   public static BufferedImage createIndexed(Image var0, Image var1, Color var2, int var3) {
      return IndexImage.getIndexedImage(toBuffered(var0), IndexImage.getIndexColorModel(var1, 255, var3), var2, var3);
   }

   public static BufferedImage sharpen(BufferedImage var0) {
      return convolve(var0, SHARPEN_KERNEL, 2);
   }

   public static BufferedImage sharpen(BufferedImage var0, float var1) {
      if (var1 == 0.0F) {
         return var0;
      } else {
         float[] var2 = new float[]{0.0F, -var1, 0.0F, -var1, 4.0F * var1 + 1.0F, -var1, 0.0F, -var1, 0.0F};
         return convolve(var0, new Kernel(3, 3, var2), 2);
      }
   }

   public static BufferedImage blur(BufferedImage var0) {
      return blur(var0, 1.5F);
   }

   public static BufferedImage blur(BufferedImage var0, float var1) {
      if (var1 <= 1.0F) {
         return var0;
      } else {
         Kernel var2 = makeKernel(var1);
         Kernel var3 = new Kernel(var2.getHeight(), var2.getWidth(), var2.getKernelData(null));
         BufferedImage var4 = addBorder(var0, var2.getWidth() / 2, var3.getHeight() / 2, 2);
         var4 = convolve(var4, var2, 1);
         var4 = convolve(var4, var3, 1);
         return var4.getSubimage(var2.getWidth() / 2, var3.getHeight() / 2, var0.getWidth(), var0.getHeight());
      }
   }

   private static Kernel makeKernel(float var0) {
      int var1 = (int)Math.ceil(var0);
      int var2 = var1 * 2 + 1;
      float[] var3 = new float[var2];
      float var4 = var0 / 3.0F;
      float var5 = 2.0F * var4 * var4;
      float var6 = (float)((Math.PI * 2) * var4);
      float var7 = (float)Math.sqrt(var6);
      float var8 = var0 * var0;
      float var9 = 0.0F;
      int var10 = 0;

      for (int var11 = -var1; var11 <= var1; var11++) {
         float var12 = var11 * var11;
         if (var12 > var8) {
            var3[var10] = 0.0F;
         } else {
            var3[var10] = (float)Math.exp(-var12 / var5) / var7;
         }

         var9 += var3[var10];
         var10++;
      }

      for (int var13 = 0; var13 < var2; var13++) {
         var3[var13] /= var9;
      }

      return new Kernel(var2, 1, var3);
   }

   public static BufferedImage convolve(BufferedImage var0, Kernel var1, int var2) {
      BufferedImage var3;
      switch (var2) {
         case 2:
         case 3:
            var3 = addBorder(var0, var1.getWidth() / 2, var1.getHeight() / 2, var2);
            break;
         default:
            var3 = var0;
      }

      ConvolveOp var4 = new ConvolveOp(var1, var2, null);
      BufferedImage var5 = null;
      if (var3.getType() == 5) {
         var5 = createBuffered(var0.getWidth(), var0.getHeight(), var0.getType(), var0.getColorModel().getTransparency());
      }

      BufferedImage var6 = var4.filter(var3, var5);
      if (var0 != var3) {
         var6 = var6.getSubimage(var1.getWidth() / 2, var1.getHeight() / 2, var0.getWidth(), var0.getHeight());
      }

      return var6;
   }

   private static BufferedImage addBorder(BufferedImage var0, int var1, int var2, int var3) {
      int var4 = var0.getWidth();
      int var5 = var0.getHeight();
      ColorModel var6 = var0.getColorModel();
      WritableRaster var7 = var6.createCompatibleWritableRaster(var4 + 2 * var1, var5 + 2 * var2);
      BufferedImage var8 = new BufferedImage(var6, var7, var6.isAlphaPremultiplied(), null);
      Graphics2D var9 = var8.createGraphics();

      try {
         var9.setComposite(AlphaComposite.Src);
         var9.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
         var9.drawImage(var0, var1, var2, null);
         switch (var3) {
            case 2:
               var9.drawImage(var0, var1, 0, var1 + var4, var2, 0, 0, var4, 1, null);
               var9.drawImage(var0, -var4 + var1, var2, var1, var5 + var2, 0, 0, 1, var5, null);
               var9.drawImage(var0, var4 + var1, var2, 2 * var1 + var4, var5 + var2, var4 - 1, 0, var4, var5, null);
               var9.drawImage(var0, var1, var2 + var5, var1 + var4, 2 * var2 + var5, 0, var5 - 1, var4, var5, null);
               break;
            case 3:
               var9.drawImage(var0, -var4 + var1, -var5 + var2, null);
               var9.drawImage(var0, var1, -var5 + var2, null);
               var9.drawImage(var0, var4 + var1, -var5 + var2, null);
               var9.drawImage(var0, -var4 + var1, var2, null);
               var9.drawImage(var0, var4 + var1, var2, null);
               var9.drawImage(var0, -var4 + var1, var5 + var2, null);
               var9.drawImage(var0, var1, var5 + var2, null);
               var9.drawImage(var0, var4 + var1, var5 + var2, null);
               break;
            default:
               throw new IllegalArgumentException("Illegal edge operation " + var3);
         }
      } finally {
         var9.dispose();
      }

      return var8;
   }

   public static Image contrast(Image var0) {
      return contrast(var0, 0.3F);
   }

   public static Image contrast(Image var0, float var1) {
      if (var1 == 0.0F) {
         return var0;
      } else {
         BrightnessContrastFilter var2 = new BrightnessContrastFilter(0.0F, var1);
         return filter(var0, var2);
      }
   }

   public static Image brightness(Image var0, float var1) {
      if (var1 == 0.0F) {
         return var0;
      } else {
         BrightnessContrastFilter var2 = new BrightnessContrastFilter(var1, 0.0F);
         return filter(var0, var2);
      }
   }

   public static Image grayscale(Image var0) {
      GrayFilter var1 = new GrayFilter();
      return filter(var0, var1);
   }

   public static Image filter(Image var0, ImageFilter var1) {
      FilteredImageSource var2 = new FilteredImageSource(var0.getSource(), var1);
      return Toolkit.getDefaultToolkit().createImage(var2);
   }

   public static BufferedImage accelerate(Image var0) {
      return accelerate(var0, null, DEFAULT_CONFIGURATION);
   }

   public static BufferedImage accelerate(Image var0, GraphicsConfiguration var1) {
      return accelerate(var0, null, var1);
   }

   static BufferedImage accelerate(Image var0, Color var1, GraphicsConfiguration var2) {
      if (var0 instanceof BufferedImage) {
         BufferedImage var3 = (BufferedImage)var0;
         if (var3.getType() != 0 && equals(var3.getColorModel(), var2.getColorModel(var3.getTransparency()))) {
            return var3;
         }
      }

      if (var0 == null) {
         throw new IllegalArgumentException("image == null");
      } else {
         int var6 = getWidth(var0);
         int var4 = getHeight(var0);
         BufferedImage var5 = createClear(var6, var4, -1, getTransparency(var0), var1, var2);
         drawOnto(var5, var0);
         return var5;
      }
   }

   private static int getTransparency(Image var0) {
      if (var0 instanceof BufferedImage) {
         BufferedImage var1 = (BufferedImage)var0;
         return var1.getTransparency();
      } else {
         return 1;
      }
   }

   public static BufferedImage createTransparent(int var0, int var1) {
      return createTransparent(var0, var1, -1);
   }

   public static BufferedImage createTransparent(int var0, int var1, int var2) {
      BufferedImage var3 = createBuffered(var0, var1, var2, 3);
      Graphics2D var4 = var3.createGraphics();

      try {
         var4.setComposite(AlphaComposite.Clear);
         var4.fillRect(0, 0, var0, var1);
      } finally {
         var4.dispose();
      }

      return var3;
   }

   public static BufferedImage createClear(int var0, int var1, Color var2) {
      return createClear(var0, var1, -1, var2);
   }

   public static BufferedImage createClear(int var0, int var1, int var2, Color var3) {
      return createClear(var0, var1, var2, 1, var3, DEFAULT_CONFIGURATION);
   }

   static BufferedImage createClear(int var0, int var1, int var2, int var3, Color var4, GraphicsConfiguration var5) {
      int var6 = var4 != null ? var4.getTransparency() : var3;
      BufferedImage var7 = createBuffered(var0, var1, var2, var6, var5);
      if (var4 != null) {
         Graphics2D var8 = var7.createGraphics();

         try {
            var8.setComposite(AlphaComposite.Src);
            var8.setColor(var4);
            var8.fillRect(0, 0, var0, var1);
         } finally {
            var8.dispose();
         }
      }

      return var7;
   }

   private static BufferedImage createBuffered(int var0, int var1, int var2, int var3) {
      return createBuffered(var0, var1, var2, var3, DEFAULT_CONFIGURATION);
   }

   static BufferedImage createBuffered(int var0, int var1, int var2, int var3, GraphicsConfiguration var4) {
      if (VM_SUPPORTS_ACCELERATION && var2 == -1) {
         GraphicsEnvironment var5 = GraphicsEnvironment.getLocalGraphicsEnvironment();
         if (supportsAcceleration(var5)) {
            return getConfiguration(var4).createCompatibleImage(var0, var1, var3);
         }
      }

      return new BufferedImage(var0, var1, getImageType(var2, var3));
   }

   private static GraphicsConfiguration getConfiguration(GraphicsConfiguration var0) {
      return var0 != null ? var0 : DEFAULT_CONFIGURATION;
   }

   private static int getImageType(int var0, int var1) {
      if (var0 != -1) {
         return var0;
      } else {
         switch (var1) {
            case 1:
               return 1;
            case 2:
            case 3:
               return 2;
            default:
               throw new IllegalArgumentException("Unknown transparency type: " + var1);
         }
      }
   }

   private static boolean supportsAcceleration(GraphicsEnvironment var0) {
      try {
         return !var0.isHeadlessInstance();
      } catch (LinkageError var2) {
         VM_SUPPORTS_ACCELERATION = false;
         return false;
      }
   }

   public static int getWidth(Image var0) {
      int var1 = var0.getWidth(NULL_COMPONENT);
      if (var1 < 0) {
         if (!waitForImage(var0)) {
            return -1;
         }

         var1 = var0.getWidth(NULL_COMPONENT);
      }

      return var1;
   }

   public static int getHeight(Image var0) {
      int var1 = var0.getHeight(NULL_COMPONENT);
      if (var1 < 0) {
         if (!waitForImage(var0)) {
            return -1;
         }

         var1 = var0.getHeight(NULL_COMPONENT);
      }

      return var1;
   }

   public static boolean waitForImage(Image var0) {
      return waitForImages(new Image[]{var0}, -1L);
   }

   public static boolean waitForImage(Image var0, long var1) {
      return waitForImages(new Image[]{var0}, var1);
   }

   public static boolean waitForImages(Image[] var0) {
      return waitForImages(var0, -1L);
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static boolean waitForImages(Image[] var0, long var1) {
      boolean var3 = true;
      int var4 = var0.length == 1 ? System.identityHashCode(var0[0]) : System.identityHashCode(var0);

      for (Image var8 : var0) {
         sTracker.addImage(var8, var4);
         if (sTracker.checkID(var4, false)) {
            sTracker.removeImage(var8, var4);
         }
      }

      boolean var16 = false /* VF: Semaphore variable */;

      label138: {
         try {
            var16 = true;
            if (var1 < 0L) {
               sTracker.waitForID(var4);
               var16 = false;
            } else {
               var3 = sTracker.waitForID(var4, var1);
               var16 = false;
            }
            break label138;
         } catch (InterruptedException var17) {
            var3 = false;
            var16 = false;
         } finally {
            if (var16) {
               for (Image var13 : var0) {
                  sTracker.removeImage(var13, var4);
               }
            }
         }

         for (Image var25 : var0) {
            sTracker.removeImage(var25, var4);
         }

         return var3 && !sTracker.isErrorID(var4);
      }

      for (Image var26 : var0) {
         sTracker.removeImage(var26, var4);
      }

      return var3 && !sTracker.isErrorID(var4);
   }

   public static boolean hasTransparentPixels(RenderedImage var0, boolean var1) {
      if (var0 == null) {
         return false;
      } else {
         ColorModel var2 = var0.getColorModel();
         if (!var2.hasAlpha()) {
            return false;
         } else if (var2.getTransparency() != 2 && var2.getTransparency() != 3) {
            return false;
         } else {
            Object var3 = null;

            for (int var4 = var0.getMinTileY(); var4 < var0.getNumYTiles(); var4++) {
               for (int var5 = var0.getMinTileX(); var5 < var0.getNumXTiles(); var5++) {
                  Raster var6 = var0.getTile(var5, var4);
                  int var7 = var1 ? Math.max(var6.getWidth() / 10, 1) : 1;
                  int var8 = var1 ? Math.max(var6.getHeight() / 10, 1) : 1;

                  for (int var9 = 0; var9 < var6.getHeight(); var9 += var8) {
                     for (int var10 = 0; var10 < var6.getWidth(); var10 += var7) {
                        var3 = var6.getDataElements(var10, var9, var3);
                        if (var2.getAlpha(var3) != 255) {
                           return true;
                        }
                     }
                  }
               }
            }

            return false;
         }
      }
   }

   public static Color createTranslucent(Color var0, int var1) {
      return new Color((var1 & 0xFF) << 24 | var0.getRGB() & 16777215, true);
   }

   static int blend(int var0, int var1) {
      return (((var0 ^ var1) & -16843010) >> 1) + (var0 & var1);
   }

   public static Color blend(Color var0, Color var1) {
      return new Color(blend(var0.getRGB(), var1.getRGB()), true);
   }

   public static Color blend(Color var0, Color var1, float var2) {
      float var3 = 1.0F - var2;
      return new Color(
         clamp(var0.getRed() * var3 + var1.getRed() * var2),
         clamp(var0.getGreen() * var3 + var1.getGreen() * var2),
         clamp(var0.getBlue() * var3 + var1.getBlue() * var2),
         clamp(var0.getAlpha() * var3 + var1.getAlpha() * var2)
      );
   }

   private static int clamp(float var0) {
      return (int)var0;
   }
}
