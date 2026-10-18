package com.cheatbreaker.client.kazerui.module.modules.mechanic;

final class ChatLayoutMath {
   static final int PITCH = 9;

   static float a(float var0, float var1, float var2, float var3) {
      return Math.max(0.0F, Math.min(var2 - var3, var0 + var1));
   }

   static int a(float var0, int var1, float var2, float var3) {
      return (int)Math.floor((var0 / var1 - var2) / var3);
   }

   static int a(int var0, int var1, int var2, int var3, int var4, int var5) {
      if (var0 >= 0 && var0 < var2 && var1 >= 0 && var1 < var3 * 9) {
         int var6 = var1 / 9 + var4;
         return var6 >= 0 && var6 < var5 ? var6 : -1;
      } else {
         return -1;
      }
   }

   private ChatLayoutMath() {
   }
}
