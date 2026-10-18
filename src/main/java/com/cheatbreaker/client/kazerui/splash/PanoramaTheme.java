package com.cheatbreaker.client.kazerui.splash;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public final class PanoramaTheme {
   public static String a(String var0) {
      if ("vanilla".equals(var0)) {
         return "/assets/minecraft/textures/gui/title/background/panorama_0.png";
      } else {
         String var1;
         if ("christmas".equals(var0)) {
            var1 = "christmas";
         } else if ("spring".equals(var0) || "summer".equals(var0) || "anniversary".equals(var0)) {
            var1 = "spring";
         } else if ("japan".equals(var0)) {
            var1 = "japan";
         } else {
            var1 = "classic";
         }

         return "/assets/kazerui/ui/backgrounds/" + var1 + "/panorama_0.png";
      }
   }

   public static String a(File var0) {
      File var1 = new File(var0, "config/KazerClient/owned-ui.properties");
      Properties var2 = new Properties();
      if (var1.isFile() && var1.length() <= 1048576L) {
         try (InputStreamReader var3 = new InputStreamReader(new FileInputStream(var1), StandardCharsets.UTF_8)) {
            var2.load(var3);
         } catch (IllegalArgumentException | IOException var16) {
         }
      }

      return a(var2.getProperty("ui.homeTheme.selected", "classic"));
   }

   private PanoramaTheme() {
   }
}
