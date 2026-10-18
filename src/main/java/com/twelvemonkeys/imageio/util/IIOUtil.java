package com.twelvemonkeys.imageio.util;

import com.twelvemonkeys.image.ImageUtil;
import com.twelvemonkeys.lang.Validate;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.TreeSet;
import javax.imageio.IIOParam;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageWriteParam;
import javax.imageio.spi.IIOServiceProvider;
import javax.imageio.spi.ServiceRegistry;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

public final class IIOUtil {
   private IIOUtil() {
   }

   public static InputStream createStreamAdapter(ImageInputStream var0) {
      return new BufferedInputStream(new IIOInputStreamAdapter(var0));
   }

   public static InputStream createStreamAdapter(ImageInputStream var0, long var1) {
      return new BufferedInputStream(new IIOInputStreamAdapter(var0, var1));
   }

   public static OutputStream createStreamAdapter(ImageOutputStream var0) {
      return new BufferedOutputStream(new IIOOutputStreamAdapter(var0));
   }

   public static void skipFully(ImageInputStream var0, long var1) throws IOException {
      Validate.isTrue(var1 >= 0L, "length must be >= 0");
      if (var1 > 0L) {
         var0.seek(var0.getStreamPosition() + var1 - 1L);
         var0.readByte();
      }
   }

   static void skipFully(DataInput var0, long var1) throws IOException {
      Validate.isTrue(var1 >= 0L, "length must be >= 0");

      while (var1 > 0L) {
         int var3 = var0.skipBytes((int)Math.min(2147483647L, var1));
         if (var3 <= 0) {
            throw new EOFException();
         }

         var1 -= var3;
      }
   }

   public static void skipFully(InputStream var0, long var1) throws IOException {
      Validate.isTrue(var1 >= 0L, "length must be >= 0");

      while (var1 > 0L) {
         long var3 = var0.skip(var1);
         if (var3 <= 0L) {
            throw new EOFException();
         }

         var1 -= var3;
      }
   }

   public static Image fakeSubsampling(Image var0, IIOParam var1) {
      if (var0 == null) {
         return null;
      } else {
         if (var1 != null) {
            int var2 = var1.getSourceXSubsampling();
            int var3 = var1.getSourceYSubsampling();
            if (var2 > 1 || var3 > 1) {
               int var4 = (ImageUtil.getWidth(var0) + var2 - 1) / var2;
               int var5 = (ImageUtil.getHeight(var0) + var3 - 1) / var3;
               return var0.getScaledInstance(var4, var5, 2);
            }
         }

         return var0;
      }
   }

   public static Rectangle getSourceRegion(IIOParam var0, int var1, int var2) {
      Rectangle var3 = new Rectangle(var1, var2);
      if (var0 != null) {
         Rectangle var4 = var0.getSourceRegion();
         if (var4 != null) {
            var3 = var3.intersection(var4);
         }

         int var5 = var0.getSubsamplingXOffset();
         int var6 = var0.getSubsamplingYOffset();
         var3.x += var5;
         var3.y += var6;
         var3.width -= var5;
         var3.height -= var6;
      }

      return var3;
   }

   public static BufferedImage fakeAOI(BufferedImage var0, Rectangle var1) {
      if (var0 == null) {
         return null;
      } else {
         return var1 == null || var1.x == 0 && var1.y == 0 && var1.width == var0.getWidth() && var1.height == var0.getHeight()
            ? var0
            : var0.getSubimage(var1.x, var1.y, var1.width, var1.height);
      }
   }

   public static <T> void deregisterProvider(ServiceRegistry var0, IIOServiceProvider var1, Class<T> var2) {
      var0.deregisterServiceProvider(var2.cast(var1), var2);
   }

   public static <T> T lookupProviderByName(ServiceRegistry var0, String var1, Class<T> var2) {
      Iterator var3 = var0.getServiceProviders(var2, true);

      while (var3.hasNext()) {
         Object var4 = var3.next();
         if (var4.getClass().getName().equals(var1)) {
            return (T)var4;
         }
      }

      return null;
   }

   public static String[] getNormalizedReaderFormatNames() {
      return normalizeNames(ImageIO.getReaderFormatNames());
   }

   public static String[] getNormalizedWriterFormatNames() {
      return normalizeNames(ImageIO.getWriterFormatNames());
   }

   private static String[] normalizeNames(String[] var0) {
      TreeSet var1 = new TreeSet();

      for (String var5 : var0) {
         var1.add(var5.toUpperCase());
      }

      return (String[]) var1.toArray(new String[0]);
   }

   public static void subsampleRow(byte[] var0, int var1, int var2, byte[] var3, int var4, int var5, int var6, int var7) {
      if (var7 == 1) {
         if (var0 != var3) {
            System.arraycopy(var0, var1, var3, var4, var2);
         }
      } else {
         Validate.isTrue(var7 > 1, "samplePeriod must be > 1");
         Validate.isTrue(var6 > 0 && var6 <= 8 && (var6 == 1 || var6 % 2 == 0), "bitsPerSample must be > 0 and <= 8 and a power of 2");
         Validate.isTrue(var5 > 0, "samplesPerPixel must be > 0");
         Validate.isTrue(var5 * var6 <= 8 || var5 * var6 % 8 == 0, "samplesPerPixel * bitsPerSample must be < 8 or a multiple of 8 ");
         if (var6 * var5 % 8 == 0) {
            int var8 = var6 * var5 / 8;

            for (int var9 = 0; var9 < var2 * var8; var9 += var7 * var8) {
               System.arraycopy(var0, var1 + var9, var3, var4 + var9 / var7, var8);
            }
         } else {
            int var18 = var6 * var5;
            int var19 = (1 << var18) - 1;

            for (int var10 = 0; var10 < var2; var10 += var7) {
               int var11 = (var4 + var10 / var7) * var18 / 8;
               int var12 = (var1 + var10) * var18 / 8;
               int var13 = 8 - var18 - var10 * var18 % 8;
               int var14 = var19 << var13;
               int var15 = 8 - var18 - var10 * var18 / var7 % 8;
               int var16 = ~(var19 << var15);
               int var17 = (var0[var12] & var14) >> var13;
               var3[var11] = (byte)(var3[var11] & var16 | var17 << var15);
            }
         }
      }
   }

   public static void subsampleRow(short[] var0, int var1, int var2, short[] var3, int var4, int var5, int var6, int var7) {
      if (var7 == 1) {
         if (var0 != var3) {
            System.arraycopy(var0, var1, var3, var4, var2);
         }
      } else {
         Validate.isTrue(var7 > 1, "samplePeriod must be > 1");
         Validate.isTrue(var6 > 0 && var6 <= 16 && (var6 == 1 || var6 % 2 == 0), "bitsPerSample must be > 0 and <= 16 and a power of 2");
         Validate.isTrue(var5 > 0, "samplesPerPixel must be > 0");
         Validate.isTrue(var5 * var6 <= 16 || var5 * var6 % 16 == 0, "samplesPerPixel * bitsPerSample must be < 16 or a multiple of 16");
         int var8 = var6 * var5 / 16;

         for (int var9 = 0; var9 < var2 * var8; var9 += var7 * var8) {
            System.arraycopy(var0, var1 + var9, var3, var4 + var9 / var7, var8);
         }
      }
   }

   public static void subsampleRow(int[] var0, int var1, int var2, int[] var3, int var4, int var5, int var6, int var7) {
      if (var7 == 1) {
         if (var0 != var3) {
            System.arraycopy(var0, var1, var3, var4, var2);
         }
      } else {
         Validate.isTrue(var7 > 1, "samplePeriod must be > 1");
         Validate.isTrue(var6 > 0 && var6 <= 32 && (var6 == 1 || var6 % 2 == 0), "bitsPerSample must be > 0 and <= 32 and a power of 2");
         Validate.isTrue(var5 > 0, "samplesPerPixel must be > 0");
         Validate.isTrue(var5 * var6 <= 32 || var5 * var6 % 32 == 0, "samplesPerPixel * bitsPerSample must be < 32 or a multiple of 32");
         int var8 = var6 * var5 / 32;

         for (int var9 = 0; var9 < var2 * var8; var9 += var7 * var8) {
            System.arraycopy(var0, var1 + var9, var3, var4 + var9 / var7, var8);
         }
      }
   }

   public static void subsampleRow(float[] var0, int var1, int var2, float[] var3, int var4, int var5, int var6, int var7) {
      Validate.isTrue(var7 > 1, "samplePeriod must be > 1");
      Validate.isTrue(var6 > 0 && var6 <= 32 && (var6 == 1 || var6 % 2 == 0), "bitsPerSample must be > 0 and <= 32 and a power of 2");
      Validate.isTrue(var5 > 0, "samplesPerPixel must be > 0");
      Validate.isTrue(var5 * var6 <= 32 || var5 * var6 % 32 == 0, "samplesPerPixel * bitsPerSample must be < 32 or a multiple of 32");
      int var8 = var6 * var5 / 32;

      for (int var9 = 0; var9 < var2 * var8; var9 += var7 * var8) {
         System.arraycopy(var0, var1 + var9, var3, var4 + var9 / var7, var8);
      }
   }

   public static void subsampleRow(double[] var0, int var1, int var2, double[] var3, int var4, int var5, int var6, int var7) {
      Validate.isTrue(var7 > 1, "samplePeriod must be > 1");
      Validate.isTrue(var6 > 0 && var6 <= 64 && (var6 == 1 || var6 % 2 == 0), "bitsPerSample must be > 0 and <= 64 and a power of 2");
      Validate.isTrue(var5 > 0, "samplesPerPixel must be > 0");
      Validate.isTrue(var5 * var6 <= 64 || var5 * var6 % 64 == 0, "samplesPerPixel * bitsPerSample must be < 64 or a multiple of 64");
      int var8 = var6 * var5 / 64;

      for (int var9 = 0; var9 < var2 * var8; var9 += var7 * var8) {
         System.arraycopy(var0, var1 + var9, var3, var4 + var9 / var7, var8);
      }
   }

   public static <T extends IIOParam> T copyStandardParams(IIOParam var0, T var1) {
      Objects.requireNonNull(var1);
      Validate.isTrue(var0 != var1, "source must be different from destination");
      if (var0 != null) {
         copyIIOParams(var0, var1);
         if (var0 instanceof ImageReadParam && var1 instanceof ImageReadParam) {
            copyImageReadParams((ImageReadParam)var0, (ImageReadParam)var1);
         }

         if (var0 instanceof ImageWriteParam && var1 instanceof ImageWriteParam) {
            copyImageWriteParams((ImageWriteParam)var0, (ImageWriteParam)var1);
         }
      }

      return (T)var1;
   }

   private static void copyImageWriteParams(ImageWriteParam var0, ImageWriteParam var1) {
      if (var0.canWriteCompressed() && var1.canWriteCompressed()) {
         int var2 = var0.getCompressionMode();
         var1.setCompressionMode(var2);
         if (var2 == 2 && var0.getCompressionType() != null && Arrays.asList(var1.getCompressionTypes()).contains(var0.getCompressionType())) {
            var1.setCompressionType(var0.getCompressionType());
            var1.setCompressionQuality(var0.getCompressionQuality());
         }
      }

      if (var0.canWriteProgressive() && var1.canWriteProgressive()) {
         var1.setProgressiveMode(var0.getProgressiveMode());
      }

      if (var0.canWriteTiles() && var1.canWriteTiles()) {
         int var4 = var0.getTilingMode();
         var1.setTilingMode(var4);
         if (var4 == 2) {
            boolean var3 = var0.canOffsetTiles() && var1.canOffsetTiles();
            var1.setTiling(var0.getTileWidth(), var0.getTileHeight(), var3 ? var0.getTileGridXOffset() : 0, var3 ? var0.getTileGridYOffset() : 0);
         }
      }
   }

   private static void copyImageReadParams(ImageReadParam var0, ImageReadParam var1) {
      var1.setDestination(var0.getDestination());
      var1.setDestinationBands(var0.getDestinationBands());
      if (var1.canSetSourceRenderSize()) {
         var1.setSourceRenderSize(var0.getSourceRenderSize());
      }

      var1.setSourceProgressivePasses(var0.getSourceMinProgressivePass(), var0.getSourceMaxProgressivePass());
   }

   private static void copyIIOParams(IIOParam var0, IIOParam var1) {
      var1.setController(var0.getController());
      var1.setSourceSubsampling(var0.getSourceXSubsampling(), var0.getSourceYSubsampling(), var0.getSubsamplingXOffset(), var0.getSubsamplingYOffset());
      var1.setSourceRegion(var0.getSourceRegion());
      var1.setSourceBands(var0.getSourceBands());
      var1.setDestinationOffset(var0.getDestinationOffset());
      var1.setDestinationType(var0.getDestinationType());
   }
}
