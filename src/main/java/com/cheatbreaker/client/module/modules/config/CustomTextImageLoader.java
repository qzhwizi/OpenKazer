package com.cheatbreaker.client.module.modules.config;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import javax.imageio.ImageIO;

public final class CustomTextImageLoader {
   static final long MAX_FILE_BYTES = 4194304L;
   static final int MAX_DIMENSION = 512;
   private static final String[] IMAGE_EXTENSIONS = new String[]{".png", ".jpg", ".jpeg", ".bmp", ".gif", ".webp", ".svg"};
   private static final String[] DECODABLE_EXTENSIONS = new String[]{".png", ".jpg", ".jpeg", ".bmp", ".gif"};

   private CustomTextImageLoader() {
   }

   public static boolean a(String var0) {
      if (var0 != null && !var0.isEmpty() && var0.length() <= 1024) {
         String var1 = var0.toLowerCase(Locale.ROOT);
         if (!c(var1)) {
            return false;
         } else {
            return var1.contains("://") ? true : var1.matches("^[a-z]:[\\\\/].*") || var1.startsWith("/") || var1.startsWith("\\\\");
         }
      } else {
         return false;
      }
   }

   private static boolean c(String var0) {
      for (String var4 : IMAGE_EXTENSIONS) {
         if (var0.endsWith(var4)) {
            return true;
         }
      }

      return false;
   }

   private static boolean d(String var0) {
      for (String var4 : DECODABLE_EXTENSIONS) {
         if (var0.endsWith(var4)) {
            return true;
         }
      }

      return false;
   }

   public static BufferedImage a(Path var0) {
      File var1 = var0.toFile();
      if (!var1.isFile() || var1.length() > 4194304L) {
         return null;
      } else if (!d(var1.getName().toLowerCase(Locale.ROOT))) {
         return null;
      } else {
         BufferedImage var2;
         try {
            var2 = ImageIO.read(var1);
         } catch (Exception var4) {
            return null;
         }

         return var2 != null && var2.getWidth() <= 512 && var2.getHeight() <= 512 && var2.getWidth() > 0 && var2.getHeight() > 0 ? var2 : null;
      }
   }

   public static Path b(String var0) {
      return Paths.get(var0);
   }
}
