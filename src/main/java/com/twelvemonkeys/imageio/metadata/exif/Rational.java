package com.twelvemonkeys.imageio.metadata.exif;

@Deprecated
public final class Rational extends Number implements Comparable<Rational> {
   private final com.twelvemonkeys.imageio.metadata.tiff.Rational delegate;

   public Rational(long var1) {
      this(new com.twelvemonkeys.imageio.metadata.tiff.Rational(var1, 1L));
   }

   public Rational(long var1, long var3) {
      this(new com.twelvemonkeys.imageio.metadata.tiff.Rational(var1, var3));
   }

   private Rational(com.twelvemonkeys.imageio.metadata.tiff.Rational var1) {
      this.delegate = var1;
   }

   public long numerator() {
      return this.delegate.numerator();
   }

   public long denominator() {
      return this.delegate.denominator();
   }

   @Override
   public byte byteValue() {
      return this.delegate.byteValue();
   }

   @Override
   public short shortValue() {
      return this.delegate.shortValue();
   }

   @Override
   public int intValue() {
      return this.delegate.intValue();
   }

   @Override
   public long longValue() {
      return this.delegate.longValue();
   }

   @Override
   public float floatValue() {
      return this.delegate.floatValue();
   }

   @Override
   public double doubleValue() {
      return this.delegate.doubleValue();
   }

   public int compareTo(Rational var1) {
      return this.delegate.compareTo(var1.delegate);
   }

   @Override
   public int hashCode() {
      return this.delegate.hashCode();
   }

   @Override
   public boolean equals(Object var1) {
      return var1 == this || var1 instanceof Rational && this.delegate.equals(((Rational)var1).delegate);
   }

   @Override
   public String toString() {
      return this.delegate.toString();
   }
}
