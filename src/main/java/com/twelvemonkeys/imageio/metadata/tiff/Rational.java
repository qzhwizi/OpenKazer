package com.twelvemonkeys.imageio.metadata.tiff;

public final class Rational extends Number implements Comparable<Rational> {
   static final Rational ZERO = new Rational(0L, 1L);
   static final Rational NaN = new Rational();
   private final long numerator;
   private final long denominator;

   private Rational() {
      this.numerator = 0L;
      this.denominator = 0L;
   }

   public Rational(long var1) {
      this(var1, 1L);
   }

   public Rational(long var1, long var3) {
      if (var3 == 0L) {
         throw new IllegalArgumentException("denominator == 0");
      } else if (var1 != Long.MIN_VALUE && var3 != Long.MIN_VALUE) {
         long var5 = gcd(var1, var3);
         long var7 = var1 / var5;
         long var9 = var3 / var5;
         this.numerator = var3 >= 0L ? var7 : -var7;
         this.denominator = var3 >= 0L ? var9 : -var9;
      } else {
         throw new IllegalArgumentException("value == Long.MIN_VALUE");
      }
   }

   private static long gcd(long var0, long var2) {
      if (var0 < 0L) {
         return gcd(var2, -var0);
      } else {
         return var2 == 0L ? var0 : gcd(var2, var0 % var2);
      }
   }

   private static long lcm(long var0, long var2) {
      return var0 < 0L ? lcm(var2, -var0) : var0 * (var2 / gcd(var0, var2));
   }

   public long numerator() {
      return this.numerator;
   }

   public long denominator() {
      return this.denominator;
   }

   @Override
   public int intValue() {
      return (int)this.doubleValue();
   }

   @Override
   public long longValue() {
      return (long)this.doubleValue();
   }

   @Override
   public float floatValue() {
      return (float)this.doubleValue();
   }

   @Override
   public double doubleValue() {
      return this == NaN ? Double.NaN : (double)this.numerator / this.denominator;
   }

   public int compareTo(Rational var1) {
      double var2 = this.doubleValue();
      double var4 = var1.doubleValue();
      return Double.compare(var2, var4);
   }

   @Override
   public int hashCode() {
      return Float.floatToIntBits(this.floatValue());
   }

   @Override
   public boolean equals(Object var1) {
      return var1 == this || var1 instanceof Rational && this.compareTo((Rational)var1) == 0;
   }

   @Override
   public String toString() {
      if (this == NaN) {
         return "NaN";
      } else {
         return this.denominator == 1L ? Long.toString(this.numerator) : String.format("%s/%s", this.numerator, this.denominator);
      }
   }

   public Rational times(Rational var1) {
      if (!this.equals(ZERO) && !var1.equals(ZERO)) {
         Rational var2 = new Rational(this.numerator, var1.denominator);
         Rational var3 = new Rational(var1.numerator, this.denominator);
         return new Rational(var2.numerator * var3.numerator, var2.denominator * var3.denominator);
      } else {
         return ZERO;
      }
   }

   public Rational plus(Rational var1) {
      if (this.equals(ZERO)) {
         return var1;
      } else if (var1.equals(ZERO)) {
         return this;
      } else {
         long var2 = gcd(this.numerator, var1.numerator);
         long var4 = gcd(this.denominator, var1.denominator);
         return new Rational(
            (this.numerator / var2 * (var1.denominator / var4) + var1.numerator / var2 * (this.denominator / var4)) * var2,
            lcm(this.denominator, var1.denominator)
         );
      }
   }

   public Rational negate() {
      return new Rational(-this.numerator, this.denominator);
   }

   public Rational minus(Rational var1) {
      return this.plus(var1.negate());
   }

   public Rational reciprocal() {
      return new Rational(this.denominator, this.numerator);
   }

   public Rational divides(Rational var1) {
      if (var1.equals(ZERO)) {
         throw new ArithmeticException("/ by zero");
      } else {
         return this.times(var1.reciprocal());
      }
   }
}
