package com.cheatbreaker.client.module.modules;

import java.util.List;
import java.util.regex.Pattern;

public final class PackDisplayUtil {
   public static final String DEFAULT_NAME = "Default";
   public static final int ICON_SIZE = 32;
   public static final int ICON_TEXT_GAP = 4;
   public static final int TEXT_PADDING = 2;
   public static final int TEXT_BOX_HEIGHT = 13;
   public static final int TEXT_BOX_TEXT_Y = 3;
   private static final Pattern ZIP_SUFFIX = Pattern.compile("\\.zip$", 2);

   private PackDisplayUtil() {
   }

   public static <T> T a(List<T> var0) {
      return (T)(var0.isEmpty() ? null : var0.get(var0.size() - 1));
   }

   public static String a(String var0) {
      return var0 == null ? "Default" : ZIP_SUFFIX.matcher(var0).replaceAll("");
   }

   public static boolean a(boolean var0, boolean var1) {
      return var1 || !var0;
   }

   public static float[] a(boolean var0, boolean var1, int var2, int var3) {
      return a(var0, var1, true, var2, var3);
   }

   public static float[] a(boolean var0, boolean var1, boolean var2, int var3, int var4) {
      if (!var0) {
         return var2 ? new float[]{var3 + 4.0F, 13.0F} : new float[]{var3, var4};
      } else {
         return var2 ? new float[]{36 + var3 + 2, 32.0F} : new float[]{36 + var3, 32.0F};
      }
   }

   public static int b(boolean var0, boolean var1) {
      if (var0) {
         return 36;
      } else {
         return var1 ? 2 : 0;
      }
   }

   public static int a(boolean var0, int var1, boolean var2) {
      if (var0) {
         return (32 - var1) / 2;
      } else {
         return var2 ? 3 : 0;
      }
   }
}
