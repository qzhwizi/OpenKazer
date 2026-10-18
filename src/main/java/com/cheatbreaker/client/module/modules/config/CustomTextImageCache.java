package com.cheatbreaker.client.module.modules.config;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class CustomTextImageCache {
   static final long RECHECK_INTERVAL_MS = 1000L;
   private final Map<String, com.cheatbreaker.client.module.modules.config.CustomTextImageCache.aInner> entries = new HashMap<>();

   public BufferedImage a(String var1, long var2) {
      com.cheatbreaker.client.module.modules.config.CustomTextImageCache.aInner var4 = this.c(d(var1), var2);
      var4.refs++;
      return var4.image;
   }

   public BufferedImage b(String var1, long var2) {
      com.cheatbreaker.client.module.modules.config.CustomTextImageCache.aInner var4 = this.entries.get(d(var1));
      if (var4 == null) {
         return null;
      } else {
         this.a(var4, var2);
         return var4.image;
      }
   }

   private com.cheatbreaker.client.module.modules.config.CustomTextImageCache.aInner c(String var1, long var2) {
      com.cheatbreaker.client.module.modules.config.CustomTextImageCache.aInner var4 = this.entries.get(var1);
      if (var4 == null) {
         var4 = new com.cheatbreaker.client.module.modules.config.CustomTextImageCache.aInner();
         var4.path = var1;
         var4.lastChecked = var2;
         var4.fingerprint = e(var1);
         var4.image = com.cheatbreaker.client.module.modules.config.CustomTextImageLoader.a(new File(var1).toPath());
         this.entries.put(var1, var4);
      } else {
         this.a(var4, var2);
      }

      return var4;
   }

   private void a(com.cheatbreaker.client.module.modules.config.CustomTextImageCache.aInner var1, long var2) {
      if (var2 - var1.lastChecked >= 1000L) {
         var1.lastChecked = var2;
         long var4 = e(var1.path);
         if (var4 != var1.fingerprint) {
            var1.fingerprint = var4;
            var1.image = com.cheatbreaker.client.module.modules.config.CustomTextImageLoader.a(new File(var1.path).toPath());
            var1.textureId = -1;
         }
      }
   }

   public com.cheatbreaker.client.module.modules.config.CustomTextImageCache.aInner a(String var1) {
      String var2 = d(var1);
      com.cheatbreaker.client.module.modules.config.CustomTextImageCache.aInner var3 = this.entries.get(var2);
      if (var3 == null) {
         return null;
      } else if (--var3.refs <= 0) {
         this.entries.remove(var2);
         return var3;
      } else {
         return null;
      }
   }

   public boolean b(String var1) {
      return this.entries.containsKey(d(var1));
   }

   public com.cheatbreaker.client.module.modules.config.CustomTextImageCache.aInner c(String var1) {
      return this.entries.get(d(var1));
   }

   static String d(String var0) {
      try {
         return new File(var0).getCanonicalFile().getAbsolutePath().toLowerCase(Locale.ROOT);
      } catch (IOException var2) {
         return new File(var0).getAbsolutePath().toLowerCase(Locale.ROOT);
      }
   }

   private static long e(String var0) {
      File var1 = new File(var0);
      return !var1.isFile() ? -1L : var1.lastModified() * 31L + var1.length();
   }

   public static final class aInner {
      public String path;
      public long fingerprint;
      public long lastChecked;
      public BufferedImage image;
      public int refs;
      public int textureId = -1;
   }
}
