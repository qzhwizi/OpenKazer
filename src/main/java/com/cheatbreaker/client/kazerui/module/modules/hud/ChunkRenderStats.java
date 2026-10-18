package com.cheatbreaker.client.kazerui.module.modules.hud;

import net.minecraft.client.Minecraft;

final class ChunkRenderStats {
   private static int visible;
   private static int total;

   static int a() {
      return visible;
   }

   static int b() {
      return total;
   }

   static void c() {
      Minecraft var0 = Minecraft.getMinecraft();
      if (var0.theWorld != null && var0.thePlayer != null && var0.renderGlobal != null) {
         String var1 = var0.renderGlobal.getDebugInfoRenders();
         int var2 = var1.indexOf(47);
         int var3 = var1.indexOf(58) + 1;
         if (var2 < var3) {
            total = 0;
            visible = 0;
         } else {
            visible = a(var1, var3, var2);
            total = a(var1, var2 + 1, var1.length());
         }
      } else {
         total = 0;
         visible = 0;
      }
   }

   private static int a(String var0, int var1, int var2) {
      int var3 = 0;
      boolean var4 = false;

      for (int var5 = var1; var5 < var2; var5++) {
         char var6 = var0.charAt(var5);
         if (var6 >= '0' && var6 <= '9') {
            var4 = true;
            var3 = var3 * 10 + var6 - 48;
         } else if (var4) {
            break;
         }
      }

      return var3;
   }
}
