package cn.kazer.client189.input;

public final class ImeCaretPosition {
   private final int x;
   private final int top;
   private final int height;

   private ImeCaretPosition(int var1, int var2, int var3) {
      this.x = var1;
      this.top = var2;
      this.height = var3;
   }

   public static cn.kazer.client189.input.ImeCaretPosition a(int var0, int var1, int var2, int var3, int var4) {
      int var5 = Math.max(0, Math.min(3, var2));
      return a(var0 / 2, 70 + var5 * 10, var3, var4);
   }

   public static cn.kazer.client189.input.ImeCaretPosition b(int var0, int var1, int var2, int var3, int var4) {
      return new cn.kazer.client189.input.ImeCaretPosition(var0 + var2, var1 + (Math.max(1, var3) - 1) * var4, var4);
   }

   public static cn.kazer.client189.input.ImeCaretPosition a(int var0, int var1, int var2, int var3) {
      return new cn.kazer.client189.input.ImeCaretPosition(var0 + var2 / 2, var1, var3);
   }

   public int a() {
      return this.x;
   }

   public int b() {
      return this.top;
   }

   public int c() {
      return this.height;
   }
}
