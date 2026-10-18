package cn.kazer.client189.runtime;

public final class ChunkLoadBudget {
   private ChunkLoadBudget() {
   }

   public static long a(long var0, long var2, boolean var4, int var5) {
      if (var4 && var5 > 0 && var2 > var0) {
         int var6 = Math.max(0, Math.min(100, var5));
         long var7 = 250000L + (100 - var6) * 17500L;
         return Math.min(var2, var0 + var7);
      } else {
         return var2;
      }
   }
}
