package com.twelvemonkeys.imageio.util;

import com.twelvemonkeys.lang.Validate;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import javax.imageio.ImageTypeSpecifier;

final class IndexedImageTypeSpecifier extends ImageTypeSpecifier {
   IndexedImageTypeSpecifier(ColorModel var1) {
      super(Validate.notNull(var1, "colorModel"), var1.createCompatibleSampleModel(1, 1));
   }

   @Override
   public BufferedImage createBufferedImage(int var1, int var2) {
      try {
         WritableRaster var3 = this.colorModel.createCompatibleWritableRaster(var1, var2);
         return new BufferedImage(this.colorModel, var3, this.colorModel.isAlphaPremultiplied(), null);
      } catch (NegativeArraySizeException var4) {
         throw new IllegalArgumentException("Array size > Integer.MAX_VALUE!");
      }
   }
}
