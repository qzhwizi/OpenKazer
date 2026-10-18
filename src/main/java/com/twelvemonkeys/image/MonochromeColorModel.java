package com.twelvemonkeys.image;

import java.awt.image.IndexColorModel;

public class MonochromeColorModel extends IndexColorModel {
   private static final int[] MONO_PALETTE = new int[]{0, 16777215};
   private static MonochromeColorModel sInstance = new MonochromeColorModel();

   private MonochromeColorModel() {
      super(1, 2, MONO_PALETTE, 0, false, -1, 0);
   }

   public static IndexColorModel getInstance() {
      return sInstance;
   }

   @Override
   public synchronized Object getDataElements(int var1, Object var2) {
      int var3 = var1 >> 16 & 0xFF;
      int var4 = var1 >> 8 & 0xFF;
      int var5 = var1 & 0xFF;
      int var6 = (222 * var3 + 707 * var4 + 71 * var5) / 1000;
      byte[] var7;
      if (var2 != null) {
         var7 = (byte[])var2;
      } else {
         var7 = new byte[1];
      }

      if (var6 <= 128) {
         var7[0] = 0;
      } else {
         var7[0] = 1;
      }

      return var7;
   }
}
