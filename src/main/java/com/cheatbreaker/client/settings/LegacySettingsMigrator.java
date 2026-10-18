package com.cheatbreaker.client.settings;

import java.util.List;

public final class LegacySettingsMigrator {
   private static final String VIGNETTE = "Vignette";
   private static final String VIGNETTE_STRENGTH = "Vignette Strength";
   private static final String LEGACY_VIGNETTE = "Container Vignette";
   private static final String LEGACY_VIGNETTE_STRENGTH = "Container Vignette Strength";

   private LegacySettingsMigrator() {
   }

   static void a(List<String> var0, List<com.cheatbreaker.client.settings.Setting> var1) {
      boolean var2 = a(var0, "Vignette");
      boolean var3 = a(var0, "Vignette Strength");

      for (String var5 : var0) {
         String[] var6 = var5.split("=", 2);
         if (var6.length == 2) {
            if ("Container Vignette".equalsIgnoreCase(var6[0])) {
               if (!var2) {
                  a("Vignette=" + var6[1], var1);
               }
            } else if ("Container Vignette Strength".equalsIgnoreCase(var6[0])) {
               if (!var3) {
                  String var7 = "70".equals(var6[1]) ? "50" : var6[1];
                  a("Vignette Strength=" + var7, var1);
               }
            } else {
               a(var5, var1);
            }
         }
      }
   }

   private static boolean a(List<String> var0, String var1) {
      for (String var3 : var0) {
         String[] var4 = var3.split("=", 2);
         if (var4.length == 2 && var1.equalsIgnoreCase(var4[0])) {
            return true;
         }
      }

      return false;
   }

   static void a(String var0, List<com.cheatbreaker.client.settings.Setting> var1) {
      String[] var2 = var0.split("=", 2);
      if (var2.length == 2) {
         for (com.cheatbreaker.client.settings.Setting var4 : var1) {
            if (!var4.f().equalsIgnoreCase("label") && var4.f().equalsIgnoreCase(var2[0])) {
               switch (var4.l()) {
                  case BOOLEAN:
                     var4.a(Boolean.parseBoolean(var2[1]));
                     break;
                  case INTEGER:
                     String var5 = var2[1];
                     var4.rainbow = var5.contains("rainbow");
                     int var6 = Integer.parseInt(var5.split(";", 2)[0]);
                     if ((var4.b() == null || var6 >= var4.b().intValue()) && (var4.c() == null || var6 <= var4.c().intValue())) {
                        var4.a(var6);
                     }
                     break;
                  case FLOAT:
                     float var7 = Float.parseFloat(var2[1]);
                     if ((var4.b() == null || !(var7 < var4.b().floatValue())) && (var4.c() == null || !(var7 > var4.c().floatValue()))) {
                        var4.a(Float.valueOf(var7));
                     }
                     break;
                  case DOUBLE:
                     double var8 = Double.parseDouble(var2[1]);
                     if ((var4.b() == null || !(var8 < var4.b().doubleValue())) && (var4.c() == null || !(var8 > var4.c().doubleValue()))) {
                        var4.a(var8);
                     }
                     break;
                  case STRING_ARRAY:
                     for (String var13 : var4.h()) {
                        if (var13.equalsIgnoreCase(var2[1])) {
                           var4.a(var2[1]);
                           break;
                        }
                     }
               }
            }
         }
      }
   }
}
