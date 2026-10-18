package com.twelvemonkeys.imageio.plugins.webp.vp8;

import java.io.IOException;
import javax.imageio.stream.ImageInputStream;

final class BoolDecoder {
   private int bitCount;
   ImageInputStream data;
   private long offset;
   private int range;
   private int value;

   BoolDecoder(ImageInputStream var1, long var2) throws IOException {
      this.data = var1;
      this.offset = var2;
      this.initBoolDecoder();
   }

   private void initBoolDecoder() throws IOException {
      this.value = 0;
      this.data.seek(this.offset);
      this.value = this.data.readUnsignedByte() << 8;
      this.offset++;
      this.range = 255;
      this.bitCount = 0;
   }

   public int readBit() throws IOException {
      return this.readBool(128);
   }

   public int readBool(int var1) throws IOException {
      byte var2 = 0;
      int var5 = this.range;
      int var6 = this.value;
      int var3 = 1 + ((var5 - 1) * var1 >> 8);
      int var4 = var3 << 8;
      var5 = var3;
      if (var6 >= var4) {
         var5 = this.range - var3;
         var6 -= var4;
         var2 = 1;
      }

      int var7 = this.bitCount;
      int var8 = Globals.vp8dxBitreaderNorm[var5];
      var5 <<= var8;
      var6 <<= var8;
      var7 -= var8;
      if (var7 <= 0) {
         var6 |= this.data.readUnsignedByte() << -var7;
         this.offset++;
         var7 += 8;
      }

      this.bitCount = var7;
      this.value = var6;
      this.range = var5;
      return var2;
   }

   public int readLiteral(int var1) throws IOException {
      int var2 = 0;

      while (var1-- > 0) {
         var2 = (var2 << 1) + this.readBool(128);
      }

      return var2;
   }

   int readTree(int[] var1, int[] var2, int var3) throws IOException {
      int var4 = var3 * 2;

      while ((var4 = var1[var4 + this.readBool(var2[var4 >> 1])]) > 0) {
      }

      return -var4;
   }

   public void seek() throws IOException {
      this.data.seek(this.offset);
   }

   @Override
   public String toString() {
      return "bc: " + this.value;
   }
}
