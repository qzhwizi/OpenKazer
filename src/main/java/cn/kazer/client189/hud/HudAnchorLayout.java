package cn.kazer.client189.hud;

import java.util.Locale;

public final class HudAnchorLayout {
   private HudAnchorLayout() {
   }

   public static float[] a(String var0, int var1, int var2, float var3, float var4, float var5, float var6, float var7) {
      float var8 = var3 * var5;
      float var9 = var4 * var5;
      String var12 = var0 == null ? "LEFT_TOP" : var0.toUpperCase(Locale.ROOT);
      float var10;
      float var11;
      switch (var12) {
         case "LEFT_MIDDLE":
            var10 = 2.0F;
            var11 = var2 / 2.0F - var9 / 2.0F;
            break;
         case "LEFT_BOTTOM":
            var10 = 2.0F;
            var11 = var2 - var9 - 2.0F;
            break;
         case "MIDDLE_TOP":
            var10 = var1 / 2.0F - var8 / 2.0F;
            var11 = 2.0F;
            break;
         case "MIDDLE_MIDDLE":
            var10 = var1 / 2.0F - var8 / 2.0F;
            var11 = var2 / 2.0F - var9 / 2.0F;
            break;
         case "MIDDLE_BOTTOM_LEFT":
            var10 = var1 / 2.0F - var8;
            var11 = var2 - var9 - 2.0F;
            break;
         case "MIDDLE_BOTTOM_RIGHT":
            var10 = var1 / 2.0F;
            var11 = var2 - var9 - 2.0F;
            break;
         case "RIGHT_TOP":
            var10 = var1 - var8 - 2.0F;
            var11 = 2.0F;
            break;
         case "RIGHT_MIDDLE":
            var10 = var1 - var8;
            var11 = var2 / 2.0F - var9 / 2.0F;
            break;
         case "RIGHT_BOTTOM":
            var10 = var1 - var8;
            var11 = var2 - var9;
            break;
         case "LEFT_TOP":
         default:
            var10 = 2.0F;
            var11 = 2.0F;
      }

      return new float[]{var10 + var6, var11 + var7};
   }
}
