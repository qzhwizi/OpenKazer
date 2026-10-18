package com.twelvemonkeys.imageio.plugins.webp.lossless;

import com.twelvemonkeys.lang.Validate;

final class ColorCache {
   private final int[] colors;
   private final int hashShift;
   private static final long K_HASH_MUL = 506832829L;

   private static int hashPix(int var0, int var1) {
      return (int)((var0 * 506832829L & 4294967295L) >> var1);
   }

   ColorCache(int var1) {
      Validate.isTrue(var1 > 0, "hasBits must > 0");
      int var2 = 1 << var1;
      this.colors = new int[var2];
      this.hashShift = 32 - var1;
   }

   int lookup(int var1) {
      return this.colors[var1];
   }

   void set(int var1, int var2) {
      this.colors[var1] = var2;
   }

   void insert(int var1) {
      this.colors[this.index(var1)] = var1;
   }

   int index(int var1) {
      return hashPix(var1, this.hashShift);
   }

   int contains(int var1) {
      int var2 = this.index(var1);
      return this.colors[var2] == var1 ? var2 : -1;
   }
}
