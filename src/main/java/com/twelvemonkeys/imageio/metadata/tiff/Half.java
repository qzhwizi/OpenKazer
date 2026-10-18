package com.twelvemonkeys.imageio.metadata.tiff;

public final class Half extends Number implements Comparable<Half> {
   public static final int SIZE = 16;
   private final short shortBits;
   private final transient float floatValue;

   public Half(short var1) {
      this.shortBits = var1;
      this.floatValue = shortBitsToFloat(var1);
   }

   @Override
   public int intValue() {
      return (int)this.floatValue;
   }

   @Override
   public long longValue() {
      return (long)this.floatValue;
   }

   @Override
   public float floatValue() {
      return this.floatValue;
   }

   @Override
   public double doubleValue() {
      return this.floatValue;
   }

   @Override
   public int hashCode() {
      return this.shortBits;
   }

   @Override
   public boolean equals(Object var1) {
      return var1 instanceof Half && ((Half)var1).shortBits == this.shortBits;
   }

   public int compareTo(Half var1) {
      return Float.compare(this.floatValue, var1.floatValue);
   }

   @Override
   public String toString() {
      return Float.toString(this.floatValue);
   }

   public static Half valueOf(String var0) throws NumberFormatException {
      return new Half(parseHalf(var0));
   }

   public static short parseHalf(String var0) throws NumberFormatException {
      return floatToShortBits(Float.parseFloat(var0));
   }

   public static float shortBitsToFloat(short var0) {
      int var1 = var0 & 1023;
      int var2 = var0 & 31744;
      if (var2 == 31744) {
         var2 = 261120;
      } else if (var2 != 0) {
         var2 += 114688;
      } else if (var1 != 0) {
         var2 = 115712;

         do {
            var1 <<= 1;
            var2 -= 1024;
         } while ((var1 & 1024) == 0);

         var1 &= 1023;
      }

      return Float.intBitsToFloat((var0 & '耀') << 16 | (var2 | var1) << 13);
   }

   public static short floatToShortBits(float var0) {
      return (short)floatTo16Bits(var0);
   }

   private static int floatTo16Bits(float var0) {
      int var1 = Float.floatToIntBits(var0);
      int var2 = var1 >>> 16 & 32768;
      int var3 = (var1 & 2147483647) + 4096;
      if (var3 >= 1199570944) {
         if ((var1 & 2147483647) >= 1199570944) {
            return var3 < 2139095040 ? var2 | 31744 : var2 | 31744 | (var1 & 8388607) >>> 13;
         } else {
            return var2 | 31743;
         }
      } else if (var3 >= 947912704) {
         return var2 | var3 - 939524096 >>> 13;
      } else if (var3 < 855638016) {
         return var2;
      } else {
         var3 = (var1 & 2147483647) >>> 23;
         return var2 | (var1 & 8388607 | 8388608) + (8388608 >>> var3 - 102) >>> 126 - var3;
      }
   }

   private Object readResolve() {
      return new Half(this.shortBits);
   }
}
