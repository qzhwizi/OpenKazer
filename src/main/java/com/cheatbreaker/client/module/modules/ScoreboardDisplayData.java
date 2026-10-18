package com.cheatbreaker.client.module.modules;

import java.util.function.ToIntFunction;

public final class ScoreboardDisplayData {
   public final String title;
   public final String[] names;
   public final String[] numbers;
   public final int[] numberWidths;
   public final int width;
   public final int right;
   public final int titleWidth;

   public ScoreboardDisplayData(String var1, String[] var2, String[] var3, ToIntFunction<String> var4) {
      this.title = var1;
      this.names = var2;
      this.numbers = var3;
      this.numberWidths = new int[var3.length];
      this.titleWidth = var4.applyAsInt(var1);
      int var5 = this.titleWidth;

      for (int var6 = 0; var6 < var2.length; var6++) {
         var5 = Math.max(var5, var4.applyAsInt(var2[var6] + ": " + var3[var6]));
         this.numberWidths[var6] = var4.applyAsInt(var3[var6]);
      }

      this.width = var5;
      this.right = Math.max(this.width + 9, this.titleWidth + 16);
   }
}
