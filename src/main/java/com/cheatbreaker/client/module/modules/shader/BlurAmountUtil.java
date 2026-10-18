package com.cheatbreaker.client.module.modules.shader;

public final class BlurAmountUtil {
   public static final int MIN_PERCENT = 0;
   public static final int MAX_PERCENT = 100;
   public static final int DEFAULT_PERCENT = 50;

   private BlurAmountUtil() {
   }

   public static int a(int var0) {
      return Math.max(0, Math.min(100, var0));
   }

   public static boolean b(int var0) {
      return a(var0) > 0;
   }

   public static float c(int var0) {
      return 0.69F + 0.3F * a(var0) / 100.0F;
   }

   public static float d(int var0) {
      float var1 = 1.1F - a(var0) / 100.0F;
      return Math.max(0.1F, Math.min(1.0F, var1));
   }

   public static float e(int var0) {
      return a(var0) / 20.0F;
   }

   public static float f(int var0) {
      return Math.min(0.1F + a(var0) * 0.01F, 0.9F);
   }
}
