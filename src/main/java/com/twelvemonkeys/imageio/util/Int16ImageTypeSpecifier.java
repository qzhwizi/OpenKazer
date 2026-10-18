package com.twelvemonkeys.imageio.util;

import com.twelvemonkeys.imageio.color.Int16ComponentColorModel;
import java.awt.color.ColorSpace;
import java.awt.image.PixelInterleavedSampleModel;
import javax.imageio.ImageTypeSpecifier;

final class Int16ImageTypeSpecifier extends ImageTypeSpecifier {
   Int16ImageTypeSpecifier(ColorSpace var1, int[] var2, boolean var3, boolean var4) {
      super(
         new Int16ComponentColorModel(var1, var3, var4),
         new PixelInterleavedSampleModel(2, 1, 1, var1.getNumComponents() + (var3 ? 1 : 0), var1.getNumComponents() + (var3 ? 1 : 0), var2)
      );
   }
}
