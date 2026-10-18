package com.cheatbreaker.client.module.modules;

import java.util.Locale;
import net.minecraft.util.FoodStats;

public final class SaturationFormatter {
   private SaturationFormatter() {
   }

   public static String a(boolean var0) {
      return var0 ? "20.0" : "20";
   }

   public static String a(FoodStats var0, boolean var1) {
      if (var0 == null) {
         return null;
      } else {
         return var1 ? String.format(Locale.ROOT, "%.1f", var0.getSaturationLevel()) : Integer.toString(Math.round(var0.getSaturationLevel()));
      }
   }
}
