package com.cheatbreaker.client.kazerui.concurrent;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;

public final class CosmeticAssetStore {
   private static final int MAX_PENDING = 8;
   private static final Map<String, String> INDEX = new HashMap<>();
   private static final Map<String, Future<byte[]>> PENDING = new LinkedHashMap<>(16, 0.75F, true);
   private static final ExecutorService POOL = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(32), var0 -> {
      Thread var1 = new Thread(var0, "Kazer cosmetic download");
      var1.setDaemon(true);
      return var1;
   });
   private static boolean loaded;
   private static boolean warming;

   private static synchronized void b() {
      if (!loaded) {
         loaded = true;

         String var2;
         try (BufferedReader var0 = new BufferedReader(
               new InputStreamReader(
                  com.cheatbreaker.client.kazerui.concurrent.CosmeticAssetStore.class.getResourceAsStream("/assets/kazerui/cosmetics/jit_index"), StandardCharsets.UTF_8
               )
            )) {
            while ((var2 = var0.readLine()) != null) {
               int var3 = var2.indexOf(32);
               if (var3 >= 1) {
                  String var4 = var2.substring(var3 + 1).trim();
                  if (var4.matches("[a-f0-9]{40}")) {
                     INDEX.put(var2.substring(0, var3), var4);
                  }
               }
            }
         } catch (RuntimeException | IOException var15) {
            LogManager.getLogger().warn("[KC] Cosmetic index unavailable", var15);
         }
      }
   }

   public static boolean a(String var0) {
      b();
      return INDEX.containsKey(var0);
   }

   public static synchronized void a() {
      if (!warming) {
         warming = true;
         Thread var0 = new Thread(() -> {
            com.cheatbreaker.client.kazerui.concurrent.CosmeticCatalog.a();
            b();
            Set var0x = a(com.cheatbreaker.client.kazerui.concurrent.CosmeticCatalog.a(), INDEX.keySet());
            LinkedHashSet var1 = new LinkedHashSet();

            for (String var3 : (Iterable<String>) var0x) {
               String var4 = INDEX.get(var3);
               if (var4 != null) {
                  var1.add(var4);
               }
            }

            for (String var6 : (Iterable<String>) var1) {
               if (Thread.currentThread().isInterrupted()) {
                  return;
               }

               Path var7 = c(var6);
               if (!Files.isRegularFile(var7)) {
                  d(var6);
               }
            }
         }, "Kazer cosmetic preload");
         var0.setDaemon(true);
         var0.start();
      }
   }

   private static Path c(String var0) {
      return new File(Minecraft.getMinecraft().mcDataDir, "kazer/cosmetic-assets/" + var0).toPath();
   }

   static Set<String> a(Collection<com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry> var0, Collection<String> var1) {
      HashSet var2 = new HashSet();
      TreeSet var3 = new TreeSet();

      for (com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var5 : var0) {
         String var6 = var5.resource;
         if (!var6.startsWith("builtin:")) {
            var2.add(var6);
            if (var5.type != com.cheatbreaker.client.kazerui.concurrent.CosmeticType.CLOAK) {
               String var7 = var6.substring(0, var6.lastIndexOf(47) + 1);
               int var8 = var7.indexOf("/textures/");
               if (var8 >= 0) {
                  var7 = var7.substring(0, var8 + 1);
               }

               if (!var7.isEmpty()) {
                  var3.add(var7);
               }
            }
         }
      }

      TreeSet var9 = new TreeSet();
      String var10 = null;

      for (String var13 : (Iterable<String>) var3) {
         if (var10 == null || !var13.startsWith(var10)) {
            var9.add(var13);
            var10 = var13;
         }
      }

      for (String var14 : var1) {
         String var15 = (String) var9.floor(var14);
         if (var15 != null && var14.startsWith(var15)) {
            var2.add(var14);
         }
      }

      return var2;
   }

   private static boolean a(String var0, byte[] var1) throws Exception {
      byte[] var2 = MessageDigest.getInstance("SHA-1").digest(var1);
      char[] var3 = "0123456789abcdef".toCharArray();

      for (int var4 = 0; var4 < var2.length; var4++) {
         if (var0.charAt(var4 * 2) != var3[var2[var4] >> 4 & 15] || var0.charAt(var4 * 2 + 1) != var3[var2[var4] & 15]) {
            return false;
         }
      }

      return true;
   }

   public static synchronized Future<byte[]> b(String var0) {
      b();
      String var1 = INDEX.get(var0);
      if (var1 == null) {
         return CompletableFuture.completedFuture(null);
      } else {
         Future var2 = PENDING.get(var1);
         if (var2 != null) {
            if (!var2.isDone()) {
               return var2;
            }

            try {
               if (var2.get() != null) {
                  return var2;
               }
            } catch (Exception var6) {
            }

            PENDING.remove(var1);
         }

         Iterator var3 = PENDING.entrySet().iterator();

         while (PENDING.size() >= 8 && var3.hasNext()) {
            Entry var4 = (Entry)var3.next();
            if (((Future)var4.getValue()).isDone()) {
               var3.remove();
            }
         }

         if (PENDING.size() >= 8) {
            return CompletableFuture.completedFuture(null);
         } else {
            try {
               Future var7 = POOL.submit(() -> d(var1));
               PENDING.put(var1, var7);
               return var7;
            } catch (RejectedExecutionException var5) {
               return CompletableFuture.completedFuture(null);
            }
         }
      }
   }

   private static byte[] d(String var0) {
      HttpURLConnection var1 = null;

      try {
         Path var2 = c(var0);
         if (Files.isRegularFile(var2) && Files.size(var2) <= 4194304L) {
            byte[] var48 = Files.readAllBytes(var2);
            if (a(var0, var48)) {
               return var48;
            }
         }

         var1 = (HttpURLConnection)new URL("https://textures.lunarclientcdn.com/file/" + var0).openConnection();
         var1.setInstanceFollowRedirects(false);
         var1.setConnectTimeout(4000);
         var1.setReadTimeout(6000);
         var1.setRequestProperty("User-Agent", "Kazer Client");
         if (var1.getResponseCode() != 200 || var1.getContentLengthLong() > 4194304L) {
            return null;
         } else {
            ByteArrayOutputStream var49 = new ByteArrayOutputStream();
            byte[] var4 = new byte[8192];

            int var7;
            try (InputStream var5 = var1.getInputStream()) {
               while ((var7 = var5.read(var4)) != -1) {
                  if (var49.size() + var7 > 4194304) {
                     return null;
                  }

                  var49.write(var4, 0, var7);
               }
            }

            byte[] var52 = var49.toByteArray();
            if (!a(var0, var52)) {
               return null;
            } else {
               try {
                  Files.createDirectories(var2.getParent());
                  Path var53 = Files.createTempFile(var2.getParent(), var0, ".part");

                  try {
                     Files.write(var53, var52);
                     Files.move(var53, var2, StandardCopyOption.REPLACE_EXISTING);
                  } finally {
                     Files.deleteIfExists(var53);
                  }
               } catch (IOException var43) {
               }

               return var52;
            }
         }
      } catch (Exception var46) {
         return null;
      } finally {
         if (var1 != null) {
            var1.disconnect();
         }
      }
   }

   private CosmeticAssetStore() {
   }
}
