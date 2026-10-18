package com.twelvemonkeys.imageio.plugins.webp.vp8;

import java.io.IOException;

final class SubBlock {
   private final SubBlock above;
   private int[][] dest;
   private int[][] recon;
   private int[][] diff;
   private boolean hasNoZeroToken;
   private final SubBlock left;
   private final MacroBlock macroBlock;
   private int mode;
   private final SubBlock.Plane plane;
   private int[][] predict;
   private int[] tokens = new int[16];

   SubBlock(MacroBlock var1, SubBlock var2, SubBlock var3, SubBlock.Plane var4) {
      this.macroBlock = var1;
      this.plane = var4;
      this.above = var2;
      this.left = var3;
      this.mode = 0;

      for (int var5 = 0; var5 < 16; var5++) {
         this.tokens[var5] = 0;
      }
   }

   public static int planeToType(SubBlock.Plane var0, boolean var1) {
      switch (var0) {
         case Y2:
            return 1;
         case Y1:
            return var1 ? 0 : 3;
         case U:
         case V:
            return 2;
         default:
            return -1;
      }
   }

   private int DCTextra(BoolDecoder var1, int[] var2) throws IOException {
      int var3 = 0;
      int var4 = 0;

      do {
         var3 += var3 + var1.readBool(var2[var4]);
      } while (var2[++var4] > 0);

      return var3;
   }

   public void decodeSubBlock(BoolDecoder var1, int[][][][] var2, int var3, int var4, boolean var5) throws IOException {
      SubBlock var6 = this;
      byte var7 = 0;
      if (var5) {
         var7 = 1;
      }

      int var8 = var3;
      int var9 = 0;
      int var10 = 1;

      for (boolean var11 = false; var10 != 11 && var9 + var7 < 16; var9++) {
         var10 = var1.readTree(Globals.vp8CoefTree, var2[var4][Globals.vp8CoefBands[var9 + var7]][var8], var11 ? 1 : 0);
         int var12 = this.decodeToken(var1, var10);
         var8 = 0;
         var11 = false;
         if (var12 == 1 || var12 == -1) {
            var8 = 1;
         } else if (var12 > 1 || var12 < -1) {
            var8 = 2;
         } else if (var12 == 0) {
            var11 = true;
         }

         int[] var13 = var6.getTokens();
         if (var10 != 11) {
            var13[Globals.vp8defaultZigZag1d[var9 + var7]] = var12;
         }
      }

      this.hasNoZeroToken = false;

      for (int var14 = 0; var14 < 16; var14++) {
         if (this.tokens[var14] != 0) {
            this.hasNoZeroToken = true;
         }
      }
   }

   private int decodeToken(BoolDecoder var1, int var2) throws IOException {
      int var3 = var2;
      if (var2 == 5) {
         var3 = 5 + this.DCTextra(var1, Globals.Pcat1);
      }

      if (var2 == 6) {
         var3 = 7 + this.DCTextra(var1, Globals.Pcat2);
      }

      if (var2 == 7) {
         var3 = 11 + this.DCTextra(var1, Globals.Pcat3);
      }

      if (var2 == 8) {
         var3 = 19 + this.DCTextra(var1, Globals.Pcat4);
      }

      if (var2 == 9) {
         var3 = 35 + this.DCTextra(var1, Globals.Pcat5);
      }

      if (var2 == 10) {
         var3 = 67 + this.DCTextra(var1, Globals.Pcat6);
      }

      if (var2 != 0 && var2 != 11 && var1.readBit() > 0) {
         var3 = -var3;
      }

      return var3;
   }

   public void dequantSubBlock(VP8Frame var1, Integer var2) {
      SubBlock var3 = this;
      int[] var4 = new int[16];

      for (int var5 = 0; var5 < 16; var5++) {
         int var6;
         if (this.plane != SubBlock.Plane.U && this.plane != SubBlock.Plane.V) {
            var6 = var1.getSegmentQuants().getSegQuants()[this.getMacroBlock().getSegmentId()].getY1ac();
            if (var5 == 0) {
               var6 = var1.getSegmentQuants().getSegQuants()[this.getMacroBlock().getSegmentId()].getY1dc();
            }
         } else {
            var6 = var1.getSegmentQuants().getSegQuants()[this.getMacroBlock().getSegmentId()].getUvac_delta_q();
            if (var5 == 0) {
               var6 = var1.getSegmentQuants().getSegQuants()[this.getMacroBlock().getSegmentId()].getUvdc_delta_q();
            }
         }

         int var7 = var3.getTokens()[var5];
         var4[var5] = var7 * var6;
      }

      if (var2 != null) {
         var4[0] = var2;
      }

      int[][] var8 = IDCT.idct4x4llm(var4);
      var3.setDiff(var8);
   }

   public void drawDebug() {
      if (this.dest != null) {
         this.dest[0][0] = 128;
         this.dest[1][0] = 128;
         this.dest[2][0] = 128;
         this.dest[3][0] = 128;
         this.dest[0][0] = 128;
         this.dest[0][1] = 128;
         this.dest[0][2] = 128;
         this.dest[0][3] = 128;
      }
   }

   public void drawDebugH() {
      if (this.dest != null) {
         this.dest[0][0] = 0;
         this.dest[1][0] = 0;
         this.dest[2][0] = 0;
         this.dest[3][0] = 0;
      }
   }

   public void drawDebugV() {
      if (this.dest != null) {
         this.dest[0][0] = 0;
         this.dest[0][1] = 0;
         this.dest[0][2] = 0;
         this.dest[0][3] = 0;
      }
   }

   public SubBlock getAbove() {
      return this.above;
   }

   public String getDebugString() {
      String var1 = "";
      var1 = var1 + "  " + this.plane;
      if (this.getMacroBlock().getYMode() == 4 && this.plane == SubBlock.Plane.Y1) {
         var1 = var1 + "\n  " + Globals.getSubBlockModeAsString(this.mode);
      }

      return var1;
   }

   public int[][] getDest() {
      return this.dest != null ? this.dest : new int[4][4];
   }

   public int[][] getDiff() {
      return this.diff;
   }

   public SubBlock getLeft() {
      return this.left;
   }

   public MacroBlock getMacroBlock() {
      return this.macroBlock;
   }

   public int[][] getRecon() {
      return this.recon != null ? this.recon : new int[4][4];
   }

   public boolean hasRecon() {
      return this.recon != null;
   }

   public int[][] getReconAbove() {
      return this.recon != null ? this.recon : borderSamples(false);
   }

   public int[][] getReconLeft() {
      return this.recon != null ? this.recon : borderSamples(true);
   }

   public int[][] getReconAboveLeft(boolean var1) {
      return this.recon != null ? this.recon : borderSamples(var1);
   }

   public int[][] getMacroBlockReconPredict(int var1) {
      return this.recon != null ? this.recon : borderSamples(var1 == 2);
   }

   public int[][] getReconPredict(int var1, boolean var2) {
      if (this.recon != null) {
         return this.recon;
      } else {
         return this.predict != null ? this.predict : borderSamples(var2 && isLeftBorder129(var1));
      }
   }

   private static boolean isLeftBorder129(int var0) {
      return var0 == 1 || var0 == 0 || var0 == 2 || var0 == 3 || var0 == 6 || var0 == 5 || var0 == 8;
   }

   private static int[][] borderSamples(boolean var0) {
      int var1 = var0 ? 129 : 127;
      int[][] var2 = new int[4][4];

      for (int var3 = 0; var3 < 4; var3++) {
         for (int var4 = 0; var4 < 4; var4++) {
            var2[var4][var3] = var1;
         }
      }

      return var2;
   }

   public int[][] getMacroBlockPredict(int var1) {
      if (this.dest != null) {
         return this.dest;
      } else {
         short var2 = 127;
         if (var1 == 2) {
            var2 = 129;
         }

         int[][] var3 = new int[4][4];

         for (int var4 = 0; var4 < 4; var4++) {
            for (int var5 = 0; var5 < 4; var5++) {
               var3[var5][var4] = var2;
            }
         }

         return var3;
      }
   }

   public int getMode() {
      return this.mode;
   }

   public SubBlock.Plane getPlane() {
      return this.plane;
   }

   public int[][] getPredict() {
      return this.predict != null ? this.predict : this.getPredict(0, false);
   }

   public int[][] getPredict(int var1, boolean var2) {
      if (this.dest != null) {
         return this.dest;
      } else if (this.predict != null) {
         return this.predict;
      } else {
         short var3 = 127;
         if ((var1 == 1 || var1 == 0 || var1 == 2 || var1 == 3 || var1 == 6 || var1 == 5 || var1 == 8) && var2) {
            var3 = 129;
         }

         int[][] var4 = new int[4][4];

         for (int var5 = 0; var5 < 4; var5++) {
            for (int var6 = 0; var6 < 4; var6++) {
               var4[var6][var5] = var3;
            }
         }

         return var4;
      }
   }

   int[] getTokens() {
      return this.tokens;
   }

   public boolean hasNoZeroToken() {
      return this.hasNoZeroToken;
   }

   public boolean isDest() {
      return this.dest != null;
   }

   public void predict(VP8Frame var1) {
      SubBlock var3 = var1.getAboveSubBlock(this, this.getPlane());
      SubBlock var4 = var1.getLeftSubBlock(this, this.getPlane());
      int[] var5 = new int[4];
      int[] var6 = new int[4];
      var5[0] = var3.getReconPredict(this.getMode(), false)[0][3];
      var5[1] = var3.getReconPredict(this.getMode(), false)[1][3];
      var5[2] = var3.getReconPredict(this.getMode(), false)[2][3];
      var5[3] = var3.getReconPredict(this.getMode(), false)[3][3];
      var6[0] = var4.getReconPredict(this.getMode(), true)[3][0];
      var6[1] = var4.getReconPredict(this.getMode(), true)[3][1];
      var6[2] = var4.getReconPredict(this.getMode(), true)[3][2];
      var6[3] = var4.getReconPredict(this.getMode(), true)[3][3];
      SubBlock var7 = var1.getLeftSubBlock(var3, this.getPlane());
      int var8;
      if (!var4.hasRecon() && !var3.hasRecon()) {
         var8 = var7.getReconPredict(this.getMode(), false)[3][3];
      } else if (!var3.hasRecon()) {
         var8 = var7.getReconPredict(this.getMode(), false)[3][3];
      } else {
         var8 = var7.getReconPredict(this.getMode(), true)[3][3];
      }

      SubBlock var9 = var1.getAboveRightSubBlock(this, this.plane);
      int[] var10 = new int[]{
         var9.getReconPredict(this.getMode(), false)[0][3],
         var9.getReconPredict(this.getMode(), false)[1][3],
         var9.getReconPredict(this.getMode(), false)[2][3],
         var9.getReconPredict(this.getMode(), false)[3][3]
      };
      int[][] var11 = new int[4][4];
      switch (this.getMode()) {
         case 0:
            int var13 = 0;

            for (int var23 = 0; var23 < 4; var23++) {
               var13 += var5[var23];
               var13 += var6[var23];
            }

            var13 = var13 + 4 >> 3;

            for (int var24 = 0; var24 < 4; var24++) {
               for (int var27 = 0; var27 < 4; var27++) {
                  var11[var27][var24] = var13;
               }
            }
            break;
         case 1:
            for (int var22 = 0; var22 < 4; var22++) {
               for (int var26 = 0; var26 < 4; var26++) {
                  int var29 = var5[var26] - var8 + var6[var22];
                  if (var29 < 0) {
                     var29 = 0;
                  }

                  if (var29 > 255) {
                     var29 = 255;
                  }

                  var11[var26][var22] = var29;
               }
            }
            break;
         case 2:
            int[] var14 = new int[]{
               var8 + 2 * var5[0] + var5[1] + 2 >> 2,
               var5[0] + 2 * var5[1] + var5[2] + 2 >> 2,
               var5[1] + 2 * var5[2] + var5[3] + 2 >> 2,
               var5[2] + 2 * var5[3] + var10[0] + 2 >> 2
            };

            for (int var25 = 0; var25 < 4; var25++) {
               for (int var28 = 0; var28 < 4; var28++) {
                  var11[var28][var25] = var14[var28];
               }
            }
            break;
         case 3:
            int[] var15 = new int[]{
               var8 + 2 * var6[0] + var6[1] + 2 >> 2,
               var6[0] + 2 * var6[1] + var6[2] + 2 >> 2,
               var6[1] + 2 * var6[2] + var6[3] + 2 >> 2,
               var6[2] + 2 * var6[3] + var6[3] + 2 >> 2
            };

            for (int var16 = 0; var16 < 4; var16++) {
               for (int var17 = 0; var17 < 4; var17++) {
                  var11[var17][var16] = var15[var16];
               }
            }
            break;
         case 4:
            var11[0][0] = var5[0] + var5[1] * 2 + var5[2] + 2 >> 2;
            var11[1][0] = var11[0][1] = var5[1] + var5[2] * 2 + var5[3] + 2 >> 2;
            var11[2][0] = var11[1][1] = var11[0][2] = var5[2] + var5[3] * 2 + var10[0] + 2 >> 2;
            var11[3][0] = var11[2][1] = var11[1][2] = var11[0][3] = var5[3] + var10[0] * 2 + var10[1] + 2 >> 2;
            var11[3][1] = var11[2][2] = var11[1][3] = var10[0] + var10[1] * 2 + var10[2] + 2 >> 2;
            var11[3][2] = var11[2][3] = var10[1] + var10[2] * 2 + var10[3] + 2 >> 2;
            var11[3][3] = var10[2] + var10[3] * 2 + var10[3] + 2 >> 2;
            break;
         case 5:
            int[] var19 = new int[]{var6[3], var6[2], var6[1], var6[0], var8, var5[0], var5[1], var5[2], var5[3]};
            var11[0][3] = var19[0] + var19[1] * 2 + var19[2] + 2 >> 2;
            var11[1][3] = var11[0][2] = var19[1] + var19[2] * 2 + var19[3] + 2 >> 2;
            var11[2][3] = var11[1][2] = var11[0][1] = var19[2] + var19[3] * 2 + var19[4] + 2 >> 2;
            var11[3][3] = var11[2][2] = var11[1][1] = var11[0][0] = var19[3] + var19[4] * 2 + var19[5] + 2 >> 2;
            var11[3][2] = var11[2][1] = var11[1][0] = var19[4] + var19[5] * 2 + var19[6] + 2 >> 2;
            var11[3][1] = var11[2][0] = var19[5] + var19[6] * 2 + var19[7] + 2 >> 2;
            var11[3][0] = var19[6] + var19[7] * 2 + var19[8] + 2 >> 2;
            break;
         case 6:
            int[] var18 = new int[]{var6[3], var6[2], var6[1], var6[0], var8, var5[0], var5[1], var5[2], var5[3]};
            var11[0][3] = var18[1] + var18[2] * 2 + var18[3] + 2 >> 2;
            var11[0][2] = var18[2] + var18[3] * 2 + var18[4] + 2 >> 2;
            var11[1][3] = var11[0][1] = var18[3] + var18[4] * 2 + var18[5] + 2 >> 2;
            var11[1][2] = var11[0][0] = var18[4] + var18[5] + 1 >> 1;
            var11[2][3] = var11[1][1] = var18[4] + var18[5] * 2 + var18[6] + 2 >> 2;
            var11[2][2] = var11[1][0] = var18[5] + var18[6] + 1 >> 1;
            var11[3][3] = var11[2][1] = var18[5] + var18[6] * 2 + var18[7] + 2 >> 2;
            var11[3][2] = var11[2][0] = var18[6] + var18[7] + 1 >> 1;
            var11[3][1] = var18[6] + var18[7] * 2 + var18[8] + 2 >> 2;
            var11[3][0] = var18[7] + var18[8] + 1 >> 1;
            break;
         case 7:
            var11[0][0] = var5[0] + var5[1] + 1 >> 1;
            var11[0][1] = var5[0] + var5[1] * 2 + var5[2] + 2 >> 2;
            var11[0][2] = var11[1][0] = var5[1] + var5[2] + 1 >> 1;
            var11[1][1] = var11[0][3] = var5[1] + var5[2] * 2 + var5[3] + 2 >> 2;
            var11[1][2] = var11[2][0] = var5[2] + var5[3] + 1 >> 1;
            var11[1][3] = var11[2][1] = var5[2] + var5[3] * 2 + var10[0] + 2 >> 2;
            var11[3][0] = var11[2][2] = var5[3] + var10[0] + 1 >> 1;
            var11[3][1] = var11[2][3] = var5[3] + var10[0] * 2 + var10[1] + 2 >> 2;
            var11[3][2] = var10[0] + var10[1] * 2 + var10[2] + 2 >> 2;
            var11[3][3] = var10[1] + var10[2] * 2 + var10[3] + 2 >> 2;
            break;
         case 8:
            int[] var12 = new int[]{var6[3], var6[2], var6[1], var6[0], var8, var5[0], var5[1], var5[2], var5[3]};
            var11[0][3] = var12[0] + var12[1] + 1 >> 1;
            var11[1][3] = var12[0] + var12[1] * 2 + var12[2] + 2 >> 2;
            var11[0][2] = var11[2][3] = var12[1] + var12[2] + 1 >> 1;
            var11[1][2] = var11[3][3] = var12[1] + var12[2] * 2 + var12[3] + 2 >> 2;
            var11[2][2] = var11[0][1] = var12[2] + var12[3] + 1 >> 1;
            var11[3][2] = var11[1][1] = var12[2] + var12[3] * 2 + var12[4] + 2 >> 2;
            var11[2][1] = var11[0][0] = var12[3] + var12[4] + 1 >> 1;
            var11[3][1] = var11[1][0] = var12[3] + var12[4] * 2 + var12[5] + 2 >> 2;
            var11[2][0] = var12[4] + var12[5] * 2 + var12[6] + 2 >> 2;
            var11[3][0] = var12[5] + var12[6] * 2 + var12[7] + 2 >> 2;
            break;
         case 9:
            var11[0][0] = var6[0] + var6[1] + 1 >> 1;
            var11[1][0] = var6[0] + var6[1] * 2 + var6[2] + 2 >> 2;
            var11[2][0] = var11[0][1] = var6[1] + var6[2] + 1 >> 1;
            var11[3][0] = var11[1][1] = var6[1] + var6[2] * 2 + var6[3] + 2 >> 2;
            var11[2][1] = var11[0][2] = var6[2] + var6[3] + 1 >> 1;
            var11[3][1] = var11[1][2] = var6[2] + var6[3] * 2 + var6[3] + 2 >> 2;
            var11[2][2] = var11[3][2] = var11[0][3] = var11[1][3] = var11[2][3] = var11[3][3] = var6[3];
            break;
         default:
            throw new AssertionError("TODO mode: " + this.getMode());
      }

      this.setPredict(var11);
   }

   public void reconstruct() {
      int[][] var4 = this.getPredict(1, false);
      int[][] var5 = new int[4][4];
      int[][] var6 = this.getDiff();

      for (int var2 = 0; var2 < 4; var2++) {
         for (int var3 = 0; var3 < 4; var3++) {
            int var7 = var6[var2][var3] + var4[var2][var3];
            if (var7 < 0) {
               var7 = 0;
            }

            if (var7 > 255) {
               var7 = 255;
            }

            var5[var2][var3] = var7;
         }
      }

      this.setDest(var5);
      if (!this.getMacroBlock().isKeepDebugInfo()) {
         this.diff = (int[][])null;
         this.predict = (int[][])null;
         this.tokens = null;
      }
   }

   public void setDest(int[][] var1) {
      this.dest = var1;
      this.recon = new int[][]{
         {var1[0][0], var1[0][1], var1[0][2], var1[0][3]},
         {var1[1][0], var1[1][1], var1[1][2], var1[1][3]},
         {var1[2][0], var1[2][1], var1[2][2], var1[2][3]},
         {var1[3][0], var1[3][1], var1[3][2], var1[3][3]}
      };
   }

   public void setDiff(int[][] var1) {
      this.diff = var1;
   }

   public void setMode(int var1) {
      this.mode = var1;
   }

   public void setPixel(int var1, int var2, int var3) {
      if (this.dest == null) {
         this.dest = new int[4][4];
      }

      this.dest[var1][var2] = var3;
   }

   public void setPredict(int[][] var1) {
      this.predict = var1;
   }

   @Override
   public String toString() {
      String var1 = "[";

      for (int var2 = 0; var2 < 16; var2++) {
         var1 = var1 + this.tokens[var2] + " ";
      }

      return var1 + "]";
   }

   public static enum Plane {
      U,
      V,
      Y1,
      Y2;
   }
}
