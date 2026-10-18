package com.twelvemonkeys.imageio.util;

import com.twelvemonkeys.lang.Validate;
import java.awt.Point;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.PixelInterleavedSampleModel;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.WritableRaster;

public final class RasterUtils {
   private RasterUtils() {
   }

   public static Raster asByteRaster(Raster var0) {
      return asByteRaster0(var0);
   }

   public static WritableRaster asByteRaster(WritableRaster var0) {
      return (WritableRaster)asByteRaster0(var0);
   }

   private static Raster asByteRaster0(Raster var0) {
      switch (var0.getTransferType()) {
         case 0:
            return var0;
         case 3:
            SampleModel var1 = var0.getSampleModel();
            if (!(var1 instanceof SinglePixelPackedSampleModel)) {
               throw new IllegalArgumentException(String.format("Requires SinglePixelPackedSampleModel, %s not supported", var1.getClass().getSimpleName()));
            }

            final DataBufferInt var3 = (DataBufferInt)var0.getDataBuffer();
            int var4 = var0.getWidth();
            int var5 = var0.getHeight();
            int var6 = var3.getSize();
            return new WritableRaster(
               new PixelInterleavedSampleModel(0, var4, var5, 4, var4 * 4, createBandOffsets((SinglePixelPackedSampleModel)var1)),
               new DataBuffer(0, var6 * 4) {
                  final int[] MASKS = new int[]{-256, -65281, -16711681, 16777215};

                  @Override
                  public int getElem(int var1, int var2) {
                     int var3x = var2 / 4;
                     int var4x = var2 % 4 * 8;
                     return var3.getElem(var3x) >>> var4x & 0xFF;
                  }

                  @Override
                  public void setElem(int var1, int var2, int var3x) {
                     int var4x = var2 / 4;
                     int var5x = var2 % 4;
                     int var6x = var5x * 8;
                     int var7 = var3.getElem(var4x) & this.MASKS[var5x] | (var3x & 0xFF) << var6x;
                     var3.setElem(var4x, var7);
                  }
               },
               new Point()
            ) {};
         default:
            throw new IllegalArgumentException(String.format("Raster type %d not supported", var0.getTransferType()));
      }
   }

   private static int[] createBandOffsets(SinglePixelPackedSampleModel var0) {
      Validate.notNull(var0, "sampleModel");
      int[] var1 = var0.getBitMasks();
      int[] var2 = new int[var1.length];

      for (int var3 = 0; var3 < var1.length; var3++) {
         int var4 = var1[var3];
         int var5 = 0;
         if (var4 != 0) {
            while ((var4 & 0xFF) == 0) {
               var4 >>>= 8;
               var5++;
            }
         }

         var2[var3] = var5;
      }

      return var2;
   }
}
