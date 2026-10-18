package com.cheatbreaker.client.util;

import java.io.InputStream;
import java.util.Properties;

public final class KazerBuildInfo {
   private static final Properties BUILD = load();

   private KazerBuildInfo() {
   }

   private static Properties load() {
      Properties var0 = new Properties();

      try (InputStream var1 = KazerBuildInfo.class.getResourceAsStream("/client/kazer-build.properties")) {
         if (var1 != null) {
            var0.load(var1);
         }
      } catch (Exception var14) {
      }

      return var0;
   }

   public static String release() {
      return "v" + BUILD.getProperty("version", "dev") + "-" + BUILD.getProperty("buildId", "0000");
   }

   public static String windowTitle(String var0) {
      return "Kazer Client " + var0 + " (" + release() + ")";
   }

   public static String footer(String var0) {
      return "Version(" + var0 + " " + release() + ")";
   }
}
