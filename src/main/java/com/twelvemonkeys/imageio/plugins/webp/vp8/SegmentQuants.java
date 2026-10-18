package com.twelvemonkeys.imageio.plugins.webp.vp8;

import java.io.IOException;

final class SegmentQuants {
   private int qIndex;
   private final SegmentQuant[] segQuants = new SegmentQuant[4];

   public SegmentQuants() {
      for (int var1 = 0; var1 < 4; var1++) {
         this.segQuants[var1] = new SegmentQuant();
      }
   }

   private static DeltaQ get_delta_q(BoolDecoder var0, int var1) throws IOException {
      DeltaQ var2 = new DeltaQ();
      var2.v = 0;
      var2.update = false;
      if (var0.readBit() > 0) {
         var2.v = var0.readLiteral(4);
         if (var0.readBit() > 0) {
            var2.v = -var2.v;
         }
      }

      if (var2.v != var1) {
         var2.update = true;
      }

      return var2;
   }

   public int getqIndex() {
      return this.qIndex;
   }

   public SegmentQuant[] getSegQuants() {
      return this.segQuants;
   }

   public void parse(BoolDecoder var1, boolean var2, boolean var3) throws IOException {
      this.qIndex = var1.readLiteral(7);
      boolean var4 = false;
      DeltaQ var5 = get_delta_q(var1, 0);
      int var6 = var5.v;
      var4 = var4 || var5.update;
      var5 = get_delta_q(var1, 0);
      int var7 = var5.v;
      var4 = var4 || var5.update;
      var5 = get_delta_q(var1, 0);
      int var8 = var5.v;
      var4 = var4 || var5.update;
      var5 = get_delta_q(var1, 0);
      int var9 = var5.v;
      var4 = var4 || var5.update;
      var5 = get_delta_q(var1, 0);
      int var10 = var5.v;
      if (!var4 && !var5.update) {
         boolean var23 = false;
      } else {
         boolean var10000 = true;
      }

      for (SegmentQuant var14 : this.segQuants) {
         if (!var2) {
            var14.setQindex(this.qIndex);
         } else if (!var3) {
            var14.setQindex(var14.getQindex() + this.qIndex);
         }

         var14.setY1dc(var6);
         var14.setY2dc(var7);
         var14.setY2ac_delta_q(var8);
         var14.setUvdc_delta_q(var9);
         var14.setUvac_delta_q(var10);
      }
   }
}
