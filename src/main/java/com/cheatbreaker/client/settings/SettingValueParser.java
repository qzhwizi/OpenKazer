package com.cheatbreaker.client.settings;

final class SettingValueParser {
   private SettingValueParser() {
   }

   static void a(com.cheatbreaker.client.settings.Setting var0, String var1) {
      try {
         if (var0.l() == com.cheatbreaker.client.settings.Setting.SettingType.INTEGER) {
            boolean var6 = var1.contains("rainbow");
            int var3 = Integer.parseInt(var1.split(";", 2)[0]);
            if (var0.b() != null && var3 < var0.b().intValue() || var0.c() != null && var3 > var0.c().intValue()) {
               return;
            }

            var0.rainbow = var6;
            var0.a(var3);
         } else if (var0.l() == com.cheatbreaker.client.settings.Setting.SettingType.FLOAT) {
            float var5 = Float.parseFloat(var1);
            if (var0.b() != null && var5 < var0.b().floatValue() || var0.c() != null && var5 > var0.c().floatValue()) {
               return;
            }

            var0.a(Float.valueOf(var5));
         } else if (var0.l() == com.cheatbreaker.client.settings.Setting.SettingType.DOUBLE) {
            double var2 = Double.parseDouble(var1);
            if (var0.b() != null && var2 < var0.b().doubleValue() || var0.c() != null && var2 > var0.c().doubleValue()) {
               return;
            }

            var0.a(var2);
         }
      } catch (NumberFormatException var4) {
      }
   }
}
