package com.twelvemonkeys.imageio.plugins.webp.lossless;

import com.twelvemonkeys.imageio.plugins.webp.LSBBitReader;
import com.twelvemonkeys.imageio.util.RasterUtils;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.IIOException;
import javax.imageio.ImageReadParam;
import javax.imageio.stream.ImageInputStream;

public final class VP8LDecoder {
   private static final byte[] DISTANCES = new byte[]{
      24,
      7,
      23,
      25,
      40,
      6,
      39,
      41,
      22,
      26,
      38,
      42,
      56,
      5,
      55,
      57,
      21,
      27,
      54,
      58,
      37,
      43,
      72,
      4,
      71,
      73,
      20,
      28,
      53,
      59,
      70,
      74,
      36,
      44,
      88,
      69,
      75,
      52,
      60,
      3,
      87,
      89,
      19,
      29,
      86,
      90,
      35,
      45,
      68,
      76,
      85,
      91,
      51,
      61,
      104,
      2,
      103,
      105,
      18,
      30,
      102,
      106,
      34,
      46,
      84,
      92,
      67,
      77,
      101,
      107,
      50,
      62,
      120,
      1,
      119,
      121,
      83,
      93,
      17,
      31,
      100,
      108,
      66,
      78,
      118,
      122,
      33,
      47,
      117,
      123,
      49,
      63,
      99,
      109,
      82,
      94,
      0,
      116,
      124,
      65,
      79,
      16,
      32,
      98,
      110,
      48,
      115,
      125,
      81,
      95,
      64,
      114,
      126,
      97,
      111,
      80,
      113,
      127,
      96,
      112
   };
   private final ImageInputStream imageInput;
   private final LSBBitReader lsbBitReader;

   public VP8LDecoder(ImageInputStream var1, boolean var2) {
      this.imageInput = var1;
      this.lsbBitReader = new LSBBitReader(var1);
   }

   public void readVP8Lossless(WritableRaster var1, boolean var2, ImageReadParam var3, int var4, int var5) throws IOException {
      if (var2) {
         this.imageInput.seek(this.imageInput.getStreamPosition() + 5L);
      }

      int var6 = var4;
      ArrayList var7 = new ArrayList();

      while (var2 && this.lsbBitReader.readBit() == 1) {
         var6 = this.readTransform(var6, var5, var7);
      }

      int var8 = 0;
      if (this.lsbBitReader.readBit() == 1) {
         var8 = (int)this.lsbBitReader.readBits(4);
         if (var8 < 1 || var8 > 11) {
            throw new IIOException("Corrupt WebP stream, colorCacheBits < 1 || > 11: " + var8);
         }
      }

      HuffmanInfo var9 = this.readHuffmanCodes(var6, var5, var8, var2);
      ColorCache var10 = null;
      if (var8 > 0) {
         var10 = new ColorCache(var8);
      }

      WritableRaster var11;
      WritableRaster var12;
      if (var2) {
         Rectangle var13 = new Rectangle(var4, var5);
         var11 = this.createDecodeRaster(var1, var3, var13);
         var12 = var11.createWritableChild(0, 0, var6, var5, 0, 0, null);
      } else {
         var11 = var1;
         var12 = var1;
      }

      this.decodeImage(var12, var9, var10);

      for (Transform var14 : (Iterable<Transform>) var7) {
         var14.applyInverse(var11);
      }

      if (var11 != var1) {
         copyIntoRasterWithParams(var11, var1, var3);
      }
   }

   private WritableRaster createDecodeRaster(WritableRaster var1, ImageReadParam var2, Rectangle var3) {
      boolean var4 = false;
      if (var2 != null) {
         if (var2.getSourceRegion() != null && !var2.getSourceRegion().contains(var3) || var2.getSourceXSubsampling() != 1 || var2.getSourceYSubsampling() != 1
            )
          {
            return createCompatibleRaster(var1, var3.width, var3.height);
         }

         var3.setLocation(var2.getDestinationOffset());
         var4 = true;
      }

      if (!var1.getBounds().contains(var3)) {
         return createCompatibleRaster(var1, var3.width, var3.height);
      } else {
         return var4 ? var1.createWritableChild(var3.x, var3.y, var3.width, var3.height, 0, 0, null) : var1;
      }
   }

   private static WritableRaster createCompatibleRaster(WritableRaster var0, int var1, int var2) {
      SampleModel var3 = var0.getSampleModel().createCompatibleSampleModel(var1, var2);
      return Raster.createWritableRaster(var3, var3.createDataBuffer(), null);
   }

   public static void copyIntoRasterWithParams(Raster var0, WritableRaster var1, ImageReadParam var2) {
      Rectangle var3 = var2 != null && var2.getSourceRegion() != null ? var2.getSourceRegion() : var0.getBounds();
      int var4 = var2 != null ? var2.getSourceXSubsampling() : 1;
      int var5 = var2 != null ? var2.getSourceYSubsampling() : 1;
      int var6 = var2 != null ? var2.getSubsamplingXOffset() : 0;
      int var7 = var2 != null ? var2.getSubsamplingYOffset() : 0;
      Point var8 = var2 != null ? var2.getDestinationOffset() : new Point(0, 0);
      if (var4 == 1 && var5 == 1) {
         var1.setRect(var8.x, var8.y, var0.createChild(var3.x, var3.y, var3.width, var3.height, 0, 0, null));
      } else {
         byte[] var9 = new byte[4];
         int var10 = var1.getWidth() + var1.getMinX();
         int var11 = var1.getHeight() + var1.getMinY();
         int var12 = var8.y;

         for (int var13 = var3.y + var7; var12 < var11; var13 += var5) {
            int var14 = var8.x;

            for (int var15 = var3.x + var6; var14 < var10; var15 += var4) {
               var0.getDataElements(var15, var13, var9);
               var1.setDataElements(var14, var12, var9);
               var14++;
            }

            var12++;
         }
      }
   }

   private void decodeImage(WritableRaster var1, HuffmanInfo var2, ColorCache var3) throws IOException {
      int var4 = var1.getWidth();
      int var5 = var1.getHeight();
      int var6 = var2.metaCodeBits == 0 ? -1 : (1 << var2.metaCodeBits) - 1;
      HuffmanCodeGroup var7 = var2.huffmanGroups[0];
      byte[] var8 = new byte[4];

      for (int var9 = 0; var9 < var5; var9++) {
         for (int var10 = 0; var10 < var4; var10++) {
            if ((var10 & var6) == 0 && var2.huffmanMetaCodes != null) {
               int var11 = var2.huffmanMetaCodes.getSample(var10 >> var2.metaCodeBits, var9 >> var2.metaCodeBits, 0);
               var7 = var2.huffmanGroups[var11];
            }

            short var15 = var7.mainCode.readSymbol(this.lsbBitReader);
            if (var15 < 256) {
               this.decodeLiteral(var1, var3, var7, var8, var9, var10, var15);
            } else if (var15 < 280) {
               int var12 = this.decodeBwRef(var1, var3, var4, var7, var8, var15, var10, var9);
               var10--;
               var9 += (var10 + var12) / var4;
               var10 = (var10 + var12) % var4;
               if (var9 < var5 && var10 < var4 && var2.huffmanMetaCodes != null) {
                  int var13 = var2.huffmanMetaCodes.getSample(var10 >> var2.metaCodeBits, var9 >> var2.metaCodeBits, 0);
                  var7 = var2.huffmanGroups[var13];
               }
            } else {
               this.decodeCached(var1, var3, var8, var9, var10, var15);
            }
         }
      }
   }

   private void decodeCached(WritableRaster var1, ColorCache var2, byte[] var3, int var4, int var5, short var6) {
      int var7 = var2.lookup(var6 - 256 - 24);
      var3[0] = (byte)(var7 >> 16 & 0xFF);
      var3[1] = (byte)(var7 >> 8 & 0xFF);
      var3[2] = (byte)(var7 & 0xFF);
      var3[3] = (byte)(var7 >>> 24);
      var1.setDataElements(var5, var4, var3);
   }

   private void decodeLiteral(WritableRaster var1, ColorCache var2, HuffmanCodeGroup var3, byte[] var4, int var5, int var6, short var7) throws IOException {
      byte var8 = (byte)var3.redCode.readSymbol(this.lsbBitReader);
      byte var9 = (byte)var3.blueCode.readSymbol(this.lsbBitReader);
      byte var10 = (byte)var3.alphaCode.readSymbol(this.lsbBitReader);
      var4[0] = var8;
      var4[1] = (byte)var7;
      var4[2] = var9;
      var4[3] = var10;
      var1.setDataElements(var6, var5, var4);
      if (var2 != null) {
         var2.insert((var10 & 255) << 24 | (var8 & 255) << 16 | (var7 & 255) << 8 | var9 & 255);
      }
   }

   private int decodeBwRef(WritableRaster var1, ColorCache var2, int var3, HuffmanCodeGroup var4, byte[] var5, short var6, int var7, int var8) throws IOException {
      int var9 = this.lz77decode(var6 - 256);
      int var10 = var3 * var1.getHeight() - (var8 * var3 + var7);
      if (var9 > var10) {
         throw new IIOException(
            "Corrupt WebP stream, backward reference exceeds image bounds: length=" + var9 + ", remaining=" + var10 + ", x=" + var7 + ", y=" + var8
         );
      } else {
         short var11 = var4.distanceCode.readSymbol(this.lsbBitReader);
         int var12 = this.lz77decode(var11);
         int var13;
         int var14;
         if (var12 > 120) {
            int var15 = var12 - 120;
            var14 = var8 - var15 / var3;
            var13 = var7 - var15 % var3;
         } else {
            var13 = var7 - (8 - (DISTANCES[var12 - 1] & 15));
            var14 = var8 - (DISTANCES[var12 - 1] >> 4);
         }

         if (var13 < 0) {
            var14--;
            var13 += var3;
         } else if (var13 >= var3) {
            var13 -= var3;
            var14++;
         }

         if (var14 >= 0 && var14 < var1.getHeight()) {
            for (int var16 = var9; var16 > 0; var16--) {
               if (var7 == var3) {
                  var7 = 0;
                  var8++;
               }

               var1.getDataElements(var13++, var14, var5);
               var1.setDataElements(var7, var8, var5);
               if (var13 == var3) {
                  var13 = 0;
                  var14++;
               }

               if (var2 != null) {
                  var2.insert((var5[3] & 255) << 24 | (var5[0] & 255) << 16 | (var5[1] & 255) << 8 | var5[2] & 255);
               }

               var7++;
            }

            return var9;
         } else {
            throw new IIOException(
               "Corrupt WebP stream, backward reference outside image: distance="
                  + var12
                  + ", x="
                  + var7
                  + ", y="
                  + var8
                  + ", xSrc="
                  + var13
                  + ", ySrc="
                  + var14
            );
         }
      }
   }

   private int lz77decode(int var1) throws IOException {
      if (var1 < 4) {
         return var1 + 1;
      } else {
         int var2 = var1 - 2 >> 1;
         int var3 = 2 + (var1 & 1) << var2;
         return var3 + (int)this.lsbBitReader.readBits(var2) + 1;
      }
   }

   private int readTransform(int var1, int var2, List<Transform> var3) throws IOException {
      int var4 = (int)this.lsbBitReader.readBits(2);
      switch (var4) {
         case 0:
         case 1:
            byte var9 = (byte)(this.lsbBitReader.readBits(3) + 2L);
            int var10 = subSampleSize(var1, var9);
            int var11 = subSampleSize(var2, var9);
            WritableRaster var13 = Raster.createInterleavedRaster(0, var10, var11, 4 * var10, 4, new int[]{0, 1, 2, 3}, null);
            this.readVP8Lossless(var13, false, null, var10, var11);
            if (var4 == 0) {
               var3.add(0, new PredictorTransform(var13, var9));
            } else {
               var3.add(0, new ColorTransform(var13, var9));
            }
            break;
         case 2:
            var3.add(0, new SubtractGreenTransform());
            break;
         case 3:
            int var5 = (int)this.lsbBitReader.readBits(8) + 1;
            int var6 = var5 > 16 ? 256 : (var5 > 4 ? 16 : (var5 > 2 ? 4 : 2));
            byte[] var7 = new byte[var6 * 4];
            this.readVP8Lossless(
               Raster.createInterleavedRaster(new DataBufferByte(var7, var5 * 4), var5, 1, var5 * 4, 4, new int[]{0, 1, 2, 3}, null), false, null, var5, 1
            );

            for (int var8 = 4; var8 < var7.length; var8++) {
               var7[var8] += var7[var8 - 4];
            }

            byte var12 = (byte)(var5 > 16 ? 0 : (var5 > 4 ? 1 : (var5 > 2 ? 2 : 3)));
            var1 = subSampleSize(var1, var12);
            var3.add(0, new ColorIndexingTransform(var7, var12));
            break;
         default:
            throw new AssertionError("Invalid transformType: " + var4);
      }

      return var1;
   }

   private HuffmanInfo readHuffmanCodes(int var1, int var2, int var3, boolean var4) throws IOException {
      int var5 = 1;
      int var8 = 0;
      WritableRaster var9 = null;
      if (var4 && this.lsbBitReader.readBit() == 1) {
         var8 = (int)this.lsbBitReader.readBits(3) + 2;
         int var6 = subSampleSize(var1, var8);
         int var7 = subSampleSize(var2, var8);
         WritableRaster var10 = Raster.createPackedRaster(3, var6, var7, new int[]{65280, 255, -16777216, 16711680}, null);
         this.readVP8Lossless(RasterUtils.asByteRaster(var10), false, null, var6, var7);
         int[] var11 = ((DataBufferInt)var10.getDataBuffer()).getData();
         int var12 = Integer.MIN_VALUE;

         for (int var16 : var11) {
            var12 = Math.max(var12, var16 & 65535);
         }

         var5 = var12 + 1;
         var9 = Raster.createPackedRaster(var10.getDataBuffer(), var6, var7, var6, new int[]{65535}, null);
      }

      HuffmanCodeGroup[] var17 = new HuffmanCodeGroup[var5];

      for (int var18 = 0; var18 < var17.length; var18++) {
         var17[var18] = new HuffmanCodeGroup(this.lsbBitReader, var3);
      }

      return new HuffmanInfo(var9, var8, var17);
   }

   private static int subSampleSize(int var0, int var1) {
      return var0 + (1 << var1) - 1 >> var1;
   }
}
