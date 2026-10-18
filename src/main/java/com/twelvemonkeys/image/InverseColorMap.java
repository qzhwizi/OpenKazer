package com.twelvemonkeys.image;

class InverseColorMap {
   static final int QUANTBITS = 5;
   static final int TRUNCBITS = 3;
   static final int QUANTMASK_BLUE = 31;
   static final int QUANTMASK_GREEN = 992;
   static final int QUANTMASK_RED = 31744;
   static final int MAXQUANTVAL = 32;
   byte[] rgbMapByte;
   int[] rgbMapInt;
   int numColors;
   int maxColor;
   byte[] inverseRGB;
   int transparentIndex = -1;

   InverseColorMap(byte[] var1) {
      this(var1, -1);
   }

   InverseColorMap(int[] var1) {
      this(var1, -1);
   }

   InverseColorMap(byte[] var1, int var2) {
      this.rgbMapByte = var1;
      this.numColors = this.rgbMapByte.length / 4;
      this.transparentIndex = var2;
      this.inverseRGB = new byte[32768];
      this.initIRGB(new int[32768]);
   }

   InverseColorMap(int[] var1, int var2) {
      this.rgbMapInt = var1;
      this.numColors = this.rgbMapInt.length;
      this.transparentIndex = var2;
      this.inverseRGB = new byte[32768];
      this.initIRGB(new int[32768]);
   }

   void initIRGB(int[] var1) {
      for (int var5 = 0; var5 < this.numColors; var5++) {
         if (var5 != this.transparentIndex) {
            int var6;
            int var11;
            int var16;
            if (this.rgbMapByte != null) {
               var6 = this.rgbMapByte[var5 * 4] & 255;
               var11 = this.rgbMapByte[var5 * 4 + 1] & 255;
               var16 = this.rgbMapByte[var5 * 4 + 2] & 255;
            } else {
               if (this.rgbMapInt == null) {
                  throw new IllegalStateException("colormap == null");
               }

               var6 = this.rgbMapInt[var5] >> 16 & 0xFF;
               var11 = this.rgbMapInt[var5] >> 8 & 0xFF;
               var16 = this.rgbMapInt[var5] & 0xFF;
            }

            int var8 = var6 - 4;
            int var13 = var11 - 4;
            int var18 = var16 - 4;
            var8 = var8 * var8 + var13 * var13 + var18 * var18;
            int var9 = 2 * (64 - (var6 << 3));
            int var14 = 2 * (64 - (var11 << 3));
            int var19 = 2 * (64 - (var16 << 3));
            int var21 = 0;
            int var7 = 0;

            for (int var10 = var9; var7 < 32; var10 += 128) {
               int var12 = 0;
               var13 = var8;

               for (int var15 = var14; var12 < 32; var15 += 128) {
                  int var17 = 0;
                  var18 = var13;

                  for (int var20 = var19; var17 < 32; var20 += 128) {
                     if (var5 == 0 || var1[var21] > var18) {
                        var1[var21] = var18;
                        this.inverseRGB[var21] = (byte)var5;
                     }

                     var18 += var20;
                     var17++;
                     var21++;
                  }

                  var13 += var15;
                  var12++;
               }

               var8 += var10;
               var7++;
            }
         }
      }
   }

   public final int getIndexNearest(int var1) {
      return this.inverseRGB[(var1 >> 9 & 31744) + (var1 >> 6 & 992) + (var1 >> 3 & 31)] & 0xFF;
   }

   public final int getIndexNearest(int var1, int var2, int var3) {
      return this.inverseRGB[(var1 << 7 & 31744) + (var2 << 2 & 992) + (var3 >> 3 & 31)] & 0xFF;
   }
}
