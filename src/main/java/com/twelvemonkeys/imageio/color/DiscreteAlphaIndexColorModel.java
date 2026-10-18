package com.twelvemonkeys.imageio.color;

import com.twelvemonkeys.lang.Validate;
import java.awt.Point;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.PixelInterleavedSampleModel;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;

public final class DiscreteAlphaIndexColorModel extends ColorModel {
   private final IndexColorModel icm;
   private final int extraSamples;
   private final int samples;

   public DiscreteAlphaIndexColorModel(IndexColorModel var1) {
      this(var1, 1, true);
   }

   public DiscreteAlphaIndexColorModel(IndexColorModel var1, int var2, boolean var3) {
      super(
         Validate.notNull(var1, "IndexColorModel").getPixelSize() * (1 + var2),
         new int[]{var1.getPixelSize(), var1.getPixelSize(), var1.getPixelSize(), var1.getPixelSize()},
         var1.getColorSpace(),
         var3,
         false,
         var3 ? 3 : 1,
         var1.getTransferType()
      );
      this.icm = var1;
      this.extraSamples = var2;
      this.samples = 1 + var2;
   }

   @Override
   public int getNumComponents() {
      return this.getNumColorComponents() + this.extraSamples;
   }

   @Override
   public int getRed(int var1) {
      return this.icm.getRed(var1);
   }

   @Override
   public int getGreen(int var1) {
      return this.icm.getGreen(var1);
   }

   @Override
   public int getBlue(int var1) {
      return this.icm.getBlue(var1);
   }

   @Override
   public int getAlpha(int var1) {
      return this.hasAlpha() ? (int)((float)var1 / ((1 << this.getComponentSize(3)) - 1) * 255.0F + 0.5F) : 255;
   }

   private int getSample(Object var1, int var2) {
      int var3;
      switch (this.transferType) {
         case 0:
            byte[] var4 = (byte[])var1;
            var3 = var4[var2] & 255;
            break;
         case 1:
            short[] var5 = (short[])var1;
            var3 = var5[var2] & '\uffff';
            break;
         case 2:
         default:
            throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
         case 3:
            int[] var6 = (int[])var1;
            var3 = var6[var2];
      }

      return var3;
   }

   @Override
   public int getRed(Object var1) {
      return this.getRed(this.getSample(var1, 0));
   }

   @Override
   public int getGreen(Object var1) {
      return this.getGreen(this.getSample(var1, 0));
   }

   @Override
   public int getBlue(Object var1) {
      return this.getBlue(this.getSample(var1, 0));
   }

   @Override
   public int getAlpha(Object var1) {
      return this.hasAlpha() ? this.getAlpha(this.getSample(var1, 1)) : 255;
   }

   @Override
   public SampleModel createCompatibleSampleModel(int var1, int var2) {
      return new PixelInterleavedSampleModel(this.transferType, var1, var2, this.samples, var1 * this.samples, this.createOffsets(this.samples));
   }

   private int[] createOffsets(int var1) {
      int[] var2 = new int[var1];
      int var3 = 0;

      while (var3 < var1) {
         var2[var3] = var3++;
      }

      return var2;
   }

   @Override
   public boolean isCompatibleSampleModel(SampleModel var1) {
      return var1 instanceof PixelInterleavedSampleModel && var1.getNumBands() == this.samples;
   }

   @Override
   public WritableRaster createCompatibleWritableRaster(int var1, int var2) {
      return Raster.createWritableRaster(this.createCompatibleSampleModel(var1, var2), new Point(0, 0));
   }

   @Override
   public boolean isCompatibleRaster(Raster var1) {
      int var2 = var1.getSampleModel().getSampleSize(0);
      return var1.getTransferType() == this.transferType && var1.getNumBands() == this.samples && 1 << var2 >= this.icm.getMapSize();
   }

   @Override
   public boolean equals(Object var1) {
      return this == var1 || var1 != null && this.getClass() == var1.getClass() && this.icm.equals(((DiscreteAlphaIndexColorModel)var1).icm);
   }

   @Override
   public String toString() {
      return "DiscreteAlphaIndexColorModel: #pixelBits = "
         + this.pixel_bits
         + " numComponents = "
         + this.getNumComponents()
         + " color space = "
         + this.getColorSpace()
         + " transparency = "
         + this.getTransparency()
         + " has alpha = "
         + this.hasAlpha()
         + " isAlphaPre = "
         + this.isAlphaPremultiplied();
   }
}
