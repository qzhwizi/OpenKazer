package com.cheatbreaker.client.module.modules;

public final class ItemLabelUtil {
   private ItemLabelUtil() {
   }

   public static String a(com.cheatbreaker.client.module.modules.TrackableItem var0, int var1) {
      String var2 = b(var0);
      return var1 + " " + var2 + (var1 != 0 && var1 != 1 ? "s" : "");
   }

   public static String a(int var0) {
      return String.valueOf(var0);
   }

   public static com.cheatbreaker.client.module.modules.ItemDisplayMode a(com.cheatbreaker.client.module.modules.TrackableItem var0) {
      return var0 != com.cheatbreaker.client.module.modules.TrackableItem.HEALING_POT && var0 != com.cheatbreaker.client.module.modules.TrackableItem.MUSHROOM_SOUP
         ? com.cheatbreaker.client.module.modules.ItemDisplayMode.ICON
         : com.cheatbreaker.client.module.modules.ItemDisplayMode.CLASSIC;
   }

   private static String b(com.cheatbreaker.client.module.modules.TrackableItem var0) {
      switch (var0) {
         case HEALING_POT:
            return "Pot";
         case MUSHROOM_SOUP:
            return "Soup";
         case ARROW:
            return "Arrow";
         case GOLDEN_APPLE:
            return "Gapple";
         case ENCHANTED_GOLDEN_APPLE:
            return "Enchanted Gapple";
         default:
            return "Item";
      }
   }

   public static float[] a(com.cheatbreaker.client.module.modules.ItemDisplayMode var0, int var1, int var2, int var3, boolean var4) {
      if (var0 == com.cheatbreaker.client.module.modules.ItemDisplayMode.ICON) {
         return new float[]{var1, var1 + 2.0F};
      } else {
         return var4 ? new float[]{var2 + 4.0F, var3 + 4.0F} : new float[]{var2, var3};
      }
   }
}
