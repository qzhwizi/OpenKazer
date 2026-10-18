package com.twelvemonkeys.image;

import java.awt.image.IndexColorModel;

public class GrayColorModel extends IndexColorModel {
   private static final byte[] sGrays = createGrayScale();

   public GrayColorModel() {
      super(8, sGrays.length, sGrays, sGrays, sGrays);
   }

   private static byte[] createGrayScale() {
      byte[] var0 = new byte[256];

      for (int var1 = 0; var1 < 256; var1++) {
         var0[var1] = (byte)var1;
      }

      return var0;
   }
}
