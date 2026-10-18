package com.cheatbreaker.client.gui.hudeditor;

public final class ModuleIconUtils {
   public static final float ICON_SIZE = 6.0F;
   public static final float GEAR_LOCAL_X = 2.0F;
   public static final float CLOSE_RIGHT_INSET = 8.0F;
   public static final float ICON_TOP_OFFSET = 7.5F;
   public static final int DRAG_THRESHOLD_PX = 4;
   public static final long HOLD_DRAG_MS = 1000L;

   private ModuleIconUtils() {
   }

   public static boolean a(float var0, float var1, float var2, float var3, float var4, int var5, int var6) {
      return c(var0, var1, var2, 2.0F, var4 - 7.5F, var5, var6);
   }

   public static boolean b(float var0, float var1, float var2, float var3, float var4, int var5, int var6) {
      return c(var0, var1, var2, var3 - 8.0F, var4 - 7.5F, var5, var6);
   }

   private static boolean c(float var0, float var1, float var2, float var3, float var4, int var5, int var6) {
      return var5 >= (var0 + var3) * var2 && var5 <= (var0 + var3 + 6.0F) * var2 && var6 >= (var1 + var4) * var2 && var6 <= (var1 + var4 + 6.0F) * var2;
   }

   public static boolean a(int var0, int var1, int var2, int var3) {
      int var4 = var2 - var0;
      int var5 = var3 - var1;
      return var4 * var4 + var5 * var5 > 16;
   }

   public static boolean a(long var0, long var2) {
      return var2 - var0 > 1000L;
   }

   public static com.cheatbreaker.client.gui.hudeditor.ModuleIconUtils.b a(long var0, long var2, int var4, int var5, int var6, int var7, boolean var8) {
      return var8 && !a(var0, var2) && !a(var4, var5, var6, var7) ? com.cheatbreaker.client.gui.hudeditor.ModuleIconUtils.b.EXECUTE : com.cheatbreaker.client.gui.hudeditor.ModuleIconUtils.b.CANCEL;
   }

   public static enum a {
      GEAR,
      CLOSE;
   }

   public static enum b {
      EXECUTE,
      CANCEL;
   }
}
