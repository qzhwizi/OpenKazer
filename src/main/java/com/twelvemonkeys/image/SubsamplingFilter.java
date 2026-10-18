package com.twelvemonkeys.image;

import java.awt.image.ReplicateScaleFilter;

public class SubsamplingFilter extends ReplicateScaleFilter {
   private int xSub;
   private int ySub;

   public SubsamplingFilter(int var1, int var2) {
      super(1, 1);
      if (var1 >= 1 && var2 >= 1) {
         this.xSub = var1;
         this.ySub = var2;
      } else {
         throw new IllegalArgumentException("Subsampling factors must be positive.");
      }
   }

   @Override
   public void setDimensions(int var1, int var2) {
      this.destWidth = (var1 + this.xSub - 1) / this.xSub;
      this.destHeight = (var2 + this.ySub - 1) / this.ySub;
      super.setDimensions(var1, var2);
   }
}
