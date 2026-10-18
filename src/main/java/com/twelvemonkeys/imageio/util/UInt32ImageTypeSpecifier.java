package com.twelvemonkeys.imageio.util;

import com.twelvemonkeys.imageio.color.UInt32ColorModel;
import java.awt.color.ColorSpace;
import java.awt.image.BandedSampleModel;
import java.awt.image.PixelInterleavedSampleModel;
import java.awt.image.SampleModel;
import javax.imageio.ImageTypeSpecifier;

final class UInt32ImageTypeSpecifier extends ImageTypeSpecifier {
   private UInt32ImageTypeSpecifier(ColorSpace var1, boolean var2, boolean var3, SampleModel var4) {
      super(new UInt32ColorModel(var1, var2, var3), var4);
   }

   static ImageTypeSpecifier createInterleaved(ColorSpace var0, int[] var1, boolean var2, boolean var3) {
      return new UInt32ImageTypeSpecifier(
         var0, var2, var3, new PixelInterleavedSampleModel(3, 1, 1, var0.getNumComponents() + (var2 ? 1 : 0), var0.getNumComponents() + (var2 ? 1 : 0), var1)
      );
   }

   static ImageTypeSpecifier createBanded(ColorSpace var0, int[] var1, int[] var2, boolean var3, boolean var4) {
      return new UInt32ImageTypeSpecifier(var0, var3, var4, new BandedSampleModel(3, 1, 1, 1, var1, var2));
   }

   @Override
   public boolean equals(Object var1) {
      if (!(var1 instanceof UInt32ImageTypeSpecifier)) {
         return false;
      } else {
         UInt32ImageTypeSpecifier var2 = (UInt32ImageTypeSpecifier)var1;
         return this.colorModel.equals(var2.colorModel) && this.sampleModel.equals(var2.sampleModel);
      }
   }
}
