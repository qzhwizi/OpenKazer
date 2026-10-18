package com.twelvemonkeys.imageio.plugins.webp.vp8;

final class LoopFilter {
   private static int clamp(int var0) {
      return Math.max(Math.min(var0, 127), -128);
   }

   private static int common_adjust(boolean var0, Segment var1) {
      int var2 = u2s(var1.P1);
      int var3 = u2s(var1.P0);
      int var4 = u2s(var1.Q0);
      int var5 = u2s(var1.Q1);
      int var6 = clamp((var0 ? clamp(var2 - var5) : 0) + 3 * (var4 - var3));
      int var7 = clamp(var6 + 3) >> 3;
      var6 = clamp(var6 + 4) >> 3;
      var1.Q0 = s2u(var4 - var6);
      var1.P0 = s2u(var3 + var7);
      return var6;
   }

   private static boolean filter_yes(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      return Math.abs(var5 - var6) * 2 + Math.abs(var4 - var7) / 2 <= var1
         && Math.abs(var2 - var3) <= var0
         && Math.abs(var3 - var4) <= var0
         && Math.abs(var4 - var5) <= var0
         && Math.abs(var9 - var8) <= var0
         && Math.abs(var8 - var7) <= var0
         && Math.abs(var7 - var6) <= var0;
   }

   private static Segment getSegH(SubBlock var0, SubBlock var1, int var2) {
      Segment var3 = new Segment();
      int[][] var4 = var0.getDest();
      int[][] var5 = var1.getDest();
      var3.P0 = var5[3][var2];
      var3.P1 = var5[2][var2];
      var3.P2 = var5[1][var2];
      var3.P3 = var5[0][var2];
      var3.Q0 = var4[0][var2];
      var3.Q1 = var4[1][var2];
      var3.Q2 = var4[2][var2];
      var3.Q3 = var4[3][var2];
      return var3;
   }

   private static Segment getSegV(SubBlock var0, SubBlock var1, int var2) {
      Segment var3 = new Segment();
      int[][] var4 = var0.getDest();
      int[][] var5 = var1.getDest();
      var3.P0 = var5[var2][3];
      var3.P1 = var5[var2][2];
      var3.P2 = var5[var2][1];
      var3.P3 = var5[var2][0];
      var3.Q0 = var4[var2][0];
      var3.Q1 = var4[var2][1];
      var3.Q2 = var4[var2][2];
      var3.Q3 = var4[var2][3];
      return var3;
   }

   private static boolean hev(int var0, int var1, int var2, int var3, int var4) {
      return Math.abs(var1 - var2) > var0 || Math.abs(var4 - var3) > var0;
   }

   static void loopFilterBlock(MacroBlock var0, MacroBlock var1, MacroBlock var2, int var3, boolean var4, int var5) {
      if (var4) {
         loopFilterSimpleBlock(var0, var1, var2, var5);
      } else {
         loopFilterUVBlock(var0, var1, var2, var5, var3);
         loopFilterYBlock(var0, var1, var2, var5, var3);
      }
   }

   static void loopFilterSimpleBlock(MacroBlock var0, MacroBlock var1, MacroBlock var2, int var3) {
      int var4 = var0.getFilterLevel();
      if (var4 != 0) {
         int var5 = var0.getFilterLevel();
         if (var3 > 0) {
            var5 >>= var3 > 4 ? 2 : 1;
            if (var5 > 9 - var3) {
               var5 = 9 - var3;
            }
         }

         if (var5 == 0) {
            var5 = 1;
         }

         int var6 = var4 * 2 + var5;
         if (var6 < 1) {
            var6 = 1;
         }

         int var7 = var6 + 4;
         if (var1 != null) {
            for (int var8 = 0; var8 < 4; var8++) {
               SubBlock var9 = var0.getSubBlock(SubBlock.Plane.Y1, 0, var8);
               SubBlock var10 = var1.getSubBlock(SubBlock.Plane.Y1, 3, var8);

               for (int var11 = 0; var11 < 4; var11++) {
                  Segment var12 = getSegH(var9, var10, var11);
                  simple_segment(var7, var12);
                  setSegH(var9, var10, var12, var11);
               }
            }
         }

         if (!var0.isSkip_inner_lf()) {
            for (int var14 = 1; var14 < 4; var14++) {
               for (int var17 = 0; var17 < 4; var17++) {
                  SubBlock var20 = var0.getSubBlock(SubBlock.Plane.Y1, var14 - 1, var17);
                  SubBlock var23 = var0.getSubBlock(SubBlock.Plane.Y1, var14, var17);

                  for (int var26 = 0; var26 < 4; var26++) {
                     Segment var13 = getSegH(var23, var20, var26);
                     simple_segment(var6, var13);
                     setSegH(var23, var20, var13, var26);
                  }
               }
            }
         }

         if (var2 != null) {
            for (int var15 = 0; var15 < 4; var15++) {
               SubBlock var18 = var2.getSubBlock(SubBlock.Plane.Y1, var15, 3);
               SubBlock var21 = var0.getSubBlock(SubBlock.Plane.Y1, var15, 0);

               for (int var24 = 0; var24 < 4; var24++) {
                  Segment var27 = getSegV(var21, var18, var24);
                  simple_segment(var7, var27);
                  setSegV(var21, var18, var27, var24);
               }
            }
         }

         if (!var0.isSkip_inner_lf()) {
            for (int var16 = 1; var16 < 4; var16++) {
               for (int var19 = 0; var19 < 4; var19++) {
                  SubBlock var22 = var0.getSubBlock(SubBlock.Plane.Y1, var19, var16 - 1);
                  SubBlock var25 = var0.getSubBlock(SubBlock.Plane.Y1, var19, var16);

                  for (int var28 = 0; var28 < 4; var28++) {
                     Segment var29 = getSegV(var25, var22, var28);
                     simple_segment(var6, var29);
                     setSegV(var25, var22, var29, var28);
                  }
               }
            }
         }
      }
   }

   static void loopFilterUVBlock(MacroBlock var0, MacroBlock var1, MacroBlock var2, int var3, int var4) {
      int var5 = var0.getFilterLevel();
      if (var5 != 0) {
         int var6 = var0.getFilterLevel();
         if (var3 > 0) {
            var6 >>= var3 > 4 ? 2 : 1;
            if (var6 > 9 - var3) {
               var6 = 9 - var3;
            }
         }

         if (var6 == 0) {
            var6 = 1;
         }

         byte var7 = 0;
         if (var4 == 0) {
            if (var5 >= 40) {
               var7 = 2;
            } else if (var5 >= 15) {
               var7 = 1;
            }
         } else if (var5 >= 40) {
            var7 = 3;
         } else if (var5 >= 20) {
            var7 = 2;
         } else if (var5 >= 15) {
            var7 = 1;
         }

         int var8 = (var5 + 2) * 2 + var6;
         int var9 = var5 * 2 + var6;
         if (var1 != null) {
            for (int var10 = 0; var10 < 2; var10++) {
               SubBlock var11 = var0.getSubBlock(SubBlock.Plane.U, 0, var10);
               SubBlock var12 = var1.getSubBlock(SubBlock.Plane.U, 1, var10);
               SubBlock var13 = var0.getSubBlock(SubBlock.Plane.V, 0, var10);
               SubBlock var14 = var1.getSubBlock(SubBlock.Plane.V, 1, var10);

               for (int var15 = 0; var15 < 4; var15++) {
                  Segment var16 = getSegH(var11, var12, var15);
                  MBfilter(var7, var6, var8, var16);
                  setSegH(var11, var12, var16, var15);
                  var16 = getSegH(var13, var14, var15);
                  MBfilter(var7, var6, var8, var16);
                  setSegH(var13, var14, var16, var15);
               }
            }
         }

         if (!var0.isSkip_inner_lf()) {
            for (int var18 = 1; var18 < 2; var18++) {
               for (int var21 = 0; var21 < 2; var21++) {
                  SubBlock var24 = var0.getSubBlock(SubBlock.Plane.U, var18 - 1, var21);
                  SubBlock var27 = var0.getSubBlock(SubBlock.Plane.U, var18, var21);
                  SubBlock var30 = var0.getSubBlock(SubBlock.Plane.V, var18 - 1, var21);
                  SubBlock var33 = var0.getSubBlock(SubBlock.Plane.V, var18, var21);

                  for (int var37 = 0; var37 < 4; var37++) {
                     Segment var17 = getSegH(var27, var24, var37);
                     subblock_filter(var7, var6, var9, var17);
                     setSegH(var27, var24, var17, var37);
                     var17 = getSegH(var33, var30, var37);
                     subblock_filter(var7, var6, var9, var17);
                     setSegH(var33, var30, var17, var37);
                  }
               }
            }
         }

         if (var2 != null) {
            for (int var19 = 0; var19 < 2; var19++) {
               SubBlock var22 = var2.getSubBlock(SubBlock.Plane.U, var19, 1);
               SubBlock var25 = var0.getSubBlock(SubBlock.Plane.U, var19, 0);
               SubBlock var28 = var2.getSubBlock(SubBlock.Plane.V, var19, 1);
               SubBlock var31 = var0.getSubBlock(SubBlock.Plane.V, var19, 0);

               for (int var34 = 0; var34 < 4; var34++) {
                  Segment var38 = getSegV(var25, var22, var34);
                  MBfilter(var7, var6, var8, var38);
                  setSegV(var25, var22, var38, var34);
                  var38 = getSegV(var31, var28, var34);
                  MBfilter(var7, var6, var8, var38);
                  setSegV(var31, var28, var38, var34);
               }
            }
         }

         if (!var0.isSkip_inner_lf()) {
            for (int var20 = 1; var20 < 2; var20++) {
               for (int var23 = 0; var23 < 2; var23++) {
                  SubBlock var26 = var0.getSubBlock(SubBlock.Plane.U, var23, var20 - 1);
                  SubBlock var29 = var0.getSubBlock(SubBlock.Plane.U, var23, var20);
                  SubBlock var32 = var0.getSubBlock(SubBlock.Plane.V, var23, var20 - 1);
                  SubBlock var35 = var0.getSubBlock(SubBlock.Plane.V, var23, var20);

                  for (int var40 = 0; var40 < 4; var40++) {
                     Segment var42 = getSegV(var29, var26, var40);
                     subblock_filter(var7, var6, var9, var42);
                     setSegV(var29, var26, var42, var40);
                     var42 = getSegV(var35, var32, var40);
                     subblock_filter(var7, var6, var9, var42);
                     setSegV(var35, var32, var42, var40);
                  }
               }
            }
         }
      }
   }

   static void loopFilterYBlock(MacroBlock var0, MacroBlock var1, MacroBlock var2, int var3, int var4) {
      int var5 = var0.getFilterLevel();
      if (var5 != 0) {
         int var6 = var0.getFilterLevel();
         if (var3 > 0) {
            var6 >>= var3 > 4 ? 2 : 1;
            if (var6 > 9 - var3) {
               var6 = 9 - var3;
            }
         }

         if (var6 == 0) {
            var6 = 1;
         }

         byte var7 = 0;
         if (var4 == 0) {
            if (var5 >= 40) {
               var7 = 2;
            } else if (var5 >= 15) {
               var7 = 1;
            }
         } else if (var5 >= 40) {
            var7 = 3;
         } else if (var5 >= 20) {
            var7 = 2;
         } else if (var5 >= 15) {
            var7 = 1;
         }

         int var8 = (var5 + 2) * 2 + var6;
         int var9 = var5 * 2 + var6;
         if (var1 != null) {
            for (int var10 = 0; var10 < 4; var10++) {
               SubBlock var11 = var0.getSubBlock(SubBlock.Plane.Y1, 0, var10);
               SubBlock var12 = var1.getSubBlock(SubBlock.Plane.Y1, 3, var10);

               for (int var13 = 0; var13 < 4; var13++) {
                  Segment var14 = getSegH(var11, var12, var13);
                  MBfilter(var7, var6, var8, var14);
                  setSegH(var11, var12, var14, var13);
               }
            }
         }

         if (!var0.isSkip_inner_lf()) {
            for (int var16 = 1; var16 < 4; var16++) {
               for (int var19 = 0; var19 < 4; var19++) {
                  SubBlock var22 = var0.getSubBlock(SubBlock.Plane.Y1, var16 - 1, var19);
                  SubBlock var25 = var0.getSubBlock(SubBlock.Plane.Y1, var16, var19);

                  for (int var28 = 0; var28 < 4; var28++) {
                     Segment var15 = getSegH(var25, var22, var28);
                     subblock_filter(var7, var6, var9, var15);
                     setSegH(var25, var22, var15, var28);
                  }
               }
            }
         }

         if (var2 != null) {
            for (int var17 = 0; var17 < 4; var17++) {
               SubBlock var20 = var2.getSubBlock(SubBlock.Plane.Y1, var17, 3);
               SubBlock var23 = var0.getSubBlock(SubBlock.Plane.Y1, var17, 0);

               for (int var26 = 0; var26 < 4; var26++) {
                  Segment var29 = getSegV(var23, var20, var26);
                  MBfilter(var7, var6, var8, var29);
                  setSegV(var23, var20, var29, var26);
               }
            }
         }

         if (!var0.isSkip_inner_lf()) {
            for (int var18 = 1; var18 < 4; var18++) {
               for (int var21 = 0; var21 < 4; var21++) {
                  SubBlock var24 = var0.getSubBlock(SubBlock.Plane.Y1, var21, var18 - 1);
                  SubBlock var27 = var0.getSubBlock(SubBlock.Plane.Y1, var21, var18);

                  for (int var30 = 0; var30 < 4; var30++) {
                     Segment var31 = getSegV(var27, var24, var30);
                     subblock_filter(var7, var6, var9, var31);
                     setSegV(var27, var24, var31, var30);
                  }
               }
            }
         }
      }
   }

   private static void MBfilter(int var0, int var1, int var2, Segment var3) {
      int var4 = u2s(var3.P3);
      int var5 = u2s(var3.P2);
      int var6 = u2s(var3.P1);
      int var7 = u2s(var3.P0);
      int var8 = u2s(var3.Q0);
      int var9 = u2s(var3.Q1);
      int var10 = u2s(var3.Q2);
      int var11 = u2s(var3.Q3);
      if (filter_yes(var1, var2, var11, var10, var9, var8, var7, var6, var5, var4)) {
         if (!hev(var0, var6, var7, var8, var9)) {
            int var12 = clamp(clamp(var6 - var9) + 3 * (var8 - var7));
            int var13 = 27 * var12 + 63 >> 7;
            var3.Q0 = s2u(var8 - var13);
            var3.P0 = s2u(var7 + var13);
            var13 = 18 * var12 + 63 >> 7;
            var3.Q1 = s2u(var9 - var13);
            var3.P1 = s2u(var6 + var13);
            var13 = 9 * var12 + 63 >> 7;
            var3.Q2 = s2u(var10 - var13);
            var3.P2 = s2u(var5 + var13);
         } else {
            common_adjust(true, var3);
         }
      }
   }

   private static int s2u(int var0) {
      return clamp(var0) + 128;
   }

   private static void setSegH(SubBlock var0, SubBlock var1, Segment var2, int var3) {
      int[][] var4 = var0.getDest();
      int[][] var5 = var1.getDest();
      var5[3][var3] = var2.P0;
      var5[2][var3] = var2.P1;
      var5[1][var3] = var2.P2;
      var5[0][var3] = var2.P3;
      var4[0][var3] = var2.Q0;
      var4[1][var3] = var2.Q1;
      var4[2][var3] = var2.Q2;
      var4[3][var3] = var2.Q3;
   }

   private static void setSegV(SubBlock var0, SubBlock var1, Segment var2, int var3) {
      int[][] var4 = var0.getDest();
      int[][] var5 = var1.getDest();
      var5[var3][3] = var2.P0;
      var5[var3][2] = var2.P1;
      var5[var3][1] = var2.P2;
      var5[var3][0] = var2.P3;
      var4[var3][0] = var2.Q0;
      var4[var3][1] = var2.Q1;
      var4[var3][2] = var2.Q2;
      var4[var3][3] = var2.Q3;
   }

   private static void simple_segment(int var0, Segment var1) {
      if (Math.abs(var1.P0 - var1.Q0) * 2 + Math.abs(var1.P1 - var1.Q1) / 2 <= var0) {
         common_adjust(true, var1);
      }
   }

   private static void subblock_filter(int var0, int var1, int var2, Segment var3) {
      int var4 = u2s(var3.P3);
      int var5 = u2s(var3.P2);
      int var6 = u2s(var3.P1);
      int var7 = u2s(var3.P0);
      int var8 = u2s(var3.Q0);
      int var9 = u2s(var3.Q1);
      int var10 = u2s(var3.Q2);
      int var11 = u2s(var3.Q3);
      if (filter_yes(var1, var2, var11, var10, var9, var8, var7, var6, var5, var4)) {
         boolean var12 = hev(var0, var6, var7, var8, var9);
         int var13 = common_adjust(var12, var3) + 1 >> 1;
         if (!var12) {
            var3.Q1 = s2u(var9 - var13);
            var3.P1 = s2u(var6 + var13);
         }
      }
   }

   private static int u2s(int var0) {
      return var0 - 128;
   }
}
