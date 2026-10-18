package cn.kazer.client189.input;

public final class ImeCandidatePlacement {
   private final int x;
   private final int top;
   private final int bottom;
   private final int clientWidth;
   private final int clientHeight;
   private final boolean above;

   private ImeCandidatePlacement(int var1, int var2, int var3, int var4, int var5, boolean var6) {
      this.x = var1;
      this.top = var2;
      this.bottom = var3;
      this.clientWidth = var4;
      this.clientHeight = var5;
      this.above = var6;
   }

   public static cn.kazer.client189.input.ImeCandidatePlacement a(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      return a(var0, var1, var2, var3, var4, var5, var6, false);
   }

   public static cn.kazer.client189.input.ImeCandidatePlacement b(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      return a(var0, var1, var2, var3, var4, var5, var6, true);
   }

   private static cn.kazer.client189.input.ImeCandidatePlacement a(int var0, int var1, int var2, int var3, int var4, int var5, int var6, boolean var7) {
      return new cn.kazer.client189.input.ImeCandidatePlacement(a(var0, var3, var5), a(var1, var4, var6), a(var1 + var2, var4, var6), var5, var6, var7);
   }

   private static int a(int var0, int var1, int var2) {
      int var3 = (int)((long)var0 * var2 / var1);
      return Math.max(0, Math.min(var2 - 1, var3));
   }

   public int a() {
      return this.x;
   }

   public int b() {
      return this.top;
   }

   public int c() {
      return this.bottom;
   }

   public int d() {
      return this.above ? this.top : this.bottom;
   }

   public int e() {
      return this.above ? 0 : this.x;
   }

   public int f() {
      return this.top;
   }

   public int g() {
      return this.above ? this.clientWidth : this.x + 1;
   }

   public int h() {
      return this.above ? this.clientHeight : this.bottom;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof cn.kazer.client189.input.ImeCandidatePlacement)) {
         return false;
      } else {
         cn.kazer.client189.input.ImeCandidatePlacement var2 = (cn.kazer.client189.input.ImeCandidatePlacement)var1;
         return this.x == var2.x
            && this.top == var2.top
            && this.bottom == var2.bottom
            && this.clientWidth == var2.clientWidth
            && this.clientHeight == var2.clientHeight
            && this.above == var2.above;
      }
   }

   @Override
   public int hashCode() {
      int var1 = this.x;
      var1 = 31 * var1 + this.top;
      var1 = 31 * var1 + this.bottom;
      var1 = 31 * var1 + this.clientWidth;
      var1 = 31 * var1 + this.clientHeight;
      return 31 * var1 + (this.above ? 1 : 0);
   }
}
