package com.twelvemonkeys.imageio.color;

import java.awt.color.ColorSpace;
import java.awt.image.ComponentColorModel;

public final class Int16ComponentColorModel extends ComponentColorModel {
   private final ComponentColorModel delegate;

   public Int16ComponentColorModel(ColorSpace var1, boolean var2, boolean var3) {
      super(var1, var2, var3, var2 ? 3 : 1, 2);
      this.delegate = new ComponentColorModel(var1, var2, var3, var2 ? 3 : 1, 1);
   }

   private void remap(short[] var1, int var2) {
      short var3 = var1[var2];
      if (var3 < 0) {
         var1[var2] = (short)(var3 - -32768);
      } else {
         var1[var2] = (short)(var3 + -32768);
      }
   }

   @Override
   public int getRed(Object var1) {
      this.remap((short[])var1, 0);
      return this.delegate.getRed(var1);
   }

   @Override
   public int getGreen(Object var1) {
      this.remap((short[])var1, 1);
      return this.delegate.getGreen(var1);
   }

   @Override
   public int getBlue(Object var1) {
      this.remap((short[])var1, 2);
      return this.delegate.getBlue(var1);
   }
}
