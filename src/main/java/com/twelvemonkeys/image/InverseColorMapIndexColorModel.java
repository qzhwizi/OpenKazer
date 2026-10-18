package com.twelvemonkeys.image;

import com.twelvemonkeys.lang.StringUtil;
import com.twelvemonkeys.lang.Validate;
import java.awt.Image;
import java.awt.image.IndexColorModel;

public class InverseColorMapIndexColorModel extends IndexColorModel {
   protected int[] rgbs;
   protected int mapSize;
   protected InverseColorMap inverseMap = null;
   private static final int ALPHA_THRESHOLD = 128;
   private int whiteIndex = -1;
   private static final int WHITE = 16777215;
   private static final int RGB_MASK = 16777215;

   public InverseColorMapIndexColorModel(IndexColorModel var1) {
      this(Validate.notNull(var1, "color model"), getRGBs(var1));
   }

   private InverseColorMapIndexColorModel(IndexColorModel var1, int[] var2) {
      super(var1.getComponentSize()[0], var1.getMapSize(), var2, 0, var1.getTransferType(), var1.getValidPixels());
      this.rgbs = var2;
      this.mapSize = this.rgbs.length;
      this.inverseMap = new InverseColorMap(this.rgbs);
      this.whiteIndex = this.getWhiteIndex();
   }

   private static int[] getRGBs(IndexColorModel var0) {
      int[] var1 = new int[var0.getMapSize()];
      var0.getRGBs(var1);
      return var1;
   }

   public InverseColorMapIndexColorModel(int var1, int var2, int[] var3, int var4, boolean var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.rgbs = getRGBs(this);
      this.mapSize = this.rgbs.length;
      this.inverseMap = new InverseColorMap(this.rgbs, var6);
      this.whiteIndex = this.getWhiteIndex();
   }

   public InverseColorMapIndexColorModel(int var1, int var2, byte[] var3, byte[] var4, byte[] var5, int var6) {
      super(var1, var2, var3, var4, var5, var6);
      this.rgbs = getRGBs(this);
      this.mapSize = this.rgbs.length;
      this.inverseMap = new InverseColorMap(this.rgbs, var6);
      this.whiteIndex = this.getWhiteIndex();
   }

   public InverseColorMapIndexColorModel(int var1, int var2, byte[] var3, byte[] var4, byte[] var5) {
      super(var1, var2, var3, var4, var5);
      this.rgbs = getRGBs(this);
      this.mapSize = this.rgbs.length;
      this.inverseMap = new InverseColorMap(this.rgbs);
      this.whiteIndex = this.getWhiteIndex();
   }

   private int getWhiteIndex() {
      for (int var1 = 0; var1 < this.rgbs.length; var1++) {
         int var2 = this.rgbs[var1];
         if ((var2 & 16777215) == 16777215) {
            return var1;
         }
      }

      return -1;
   }

   public static IndexColorModel create(Image var0, int var1, int var2) {
      IndexColorModel var3 = IndexImage.getIndexColorModel(var0, var1, var2);
      InverseColorMapIndexColorModel var4;
      if (var3 instanceof InverseColorMapIndexColorModel) {
         var4 = (InverseColorMapIndexColorModel)var3;
      } else {
         var4 = new InverseColorMapIndexColorModel(var3);
      }

      return var4;
   }

   @Override
   public Object getDataElements(int var1, Object var2) {
      int var3 = var1 >>> 24;
      int var4;
      if (var3 < 128 && this.getTransparentPixel() != -1) {
         var4 = this.getTransparentPixel();
      } else {
         int var5 = var1 & 16777215;
         if (var5 == 16777215 && this.whiteIndex != -1) {
            var4 = this.whiteIndex;
         } else {
            var4 = this.inverseMap.getIndexNearest(var5);
         }
      }

      return this.installpixel(var2, var4);
   }

   private Object installpixel(Object var1, int var2) {
      switch (this.transferType) {
         case 0:
            byte[] var4;
            if (var1 == null) {
               var1 = var4 = new byte[1];
            } else {
               var4 = (byte[])var1;
            }

            var4[0] = (byte)var2;
            break;
         case 1:
            short[] var5;
            if (var1 == null) {
               var1 = var5 = new short[1];
            } else {
               var5 = (short[])var1;
            }

            var5[0] = (short)var2;
            break;
         case 2:
         default:
            throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
         case 3:
            int[] var3;
            if (var1 == null) {
               var1 = var3 = new int[1];
            } else {
               var3 = (int[])var1;
            }

            var3[0] = var2;
      }

      return var1;
   }

   @Override
   public String toString() {
      return StringUtil.replace(super.toString(), "IndexColorModel: ", this.getClass().getName() + ": ");
   }
}
