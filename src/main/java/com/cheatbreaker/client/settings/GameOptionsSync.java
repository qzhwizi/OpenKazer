package com.cheatbreaker.client.settings;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class GameOptionsSync {
   private GameOptionsSync() {
   }

   private static Path c(File var0) {
      return var0.toPath().resolve("config/KazerClient/game-options.cfg");
   }

   public static void a(File var0) {
      a(var0, null);
   }

   public static void a(File var0, String var1) {
      try {
         Map var2 = a(c(var0));
         if (var2.isEmpty()) {
            return;
         }

         for (String var6 : new String[]{"options.txt", "optionsof.txt"}) {
            Path var7 = var0.toPath().resolve(var6);
            Map var8 = a(var7);

            for (Entry var10 : (Iterable<Entry>) var2.entrySet()) {
               String var11 = var6.equals("optionsof.txt") ? "of/" : "mc/";
               if (((String)var10.getKey()).startsWith(var11)) {
                  var8.put(((String)var10.getKey()).substring(var11.length()), var10.getValue());
               }
            }

            if (var6.equals("options.txt")) {
               if ("1.7.10".equals(var1)) {
                  if (var2.containsKey("mc/renderClouds")) {
                     var8.put("clouds", String.valueOf(!"false".equals(var2.get("mc/renderClouds"))));
                  }

                  if (var2.containsKey("mc/modelPart_cape")) {
                     var8.put("showCape", var2.get("mc/modelPart_cape"));
                  }
               } else if ("1.8.9".equals(var1)) {
                  if (!var2.containsKey("mc/renderClouds") && var2.containsKey("mc/clouds")) {
                     var8.put("renderClouds", var2.get("mc/clouds"));
                  }

                  if (!var2.containsKey("mc/modelPart_cape") && var2.containsKey("mc/showCape")) {
                     var8.put("modelPart_cape", var2.get("mc/showCape"));
                  }
               }
            }

            if (Files.exists(var7) && !Files.exists(var0.toPath().resolve(var6 + ".kazer-sync-backup"))) {
               Files.copy(var7, var0.toPath().resolve(var6 + ".kazer-sync-backup"));
            }

            a(var7, var8);
         }
      } catch (IOException var12) {
         System.err.println("[Kazer] Could not load shared game settings: " + var12.getMessage());
      }
   }

   public static void b(File var0) {
      b(var0, null);
   }

   public static void b(File var0, String var1) {
      try {
         Map var2 = a(c(var0));

         for (String var6 : new String[]{"options.txt", "optionsof.txt"}) {
            String var7 = var6.equals("optionsof.txt") ? "of/" : "mc/";

            for (Entry var9 : a(var0.toPath().resolve(var6)).entrySet()) {
               if (!((String)var9.getKey()).equals("version")) {
                  var2.put(var7 + (String)var9.getKey(), var9.getValue());
               }
            }
         }

         Map var11 = a(var0.toPath().resolve("options.txt"));
         if (!"1.7.10".equals(var1)) {
            if ("1.8.9".equals(var1)) {
               if (var11.containsKey("renderClouds")) {
                  var2.put("mc/clouds", String.valueOf(!"false".equals(var11.get("renderClouds"))));
               }

               if (var11.containsKey("modelPart_cape")) {
                  var2.put("mc/showCape", var11.get("modelPart_cape"));
               }
            }
         } else {
            if (var11.containsKey("clouds")) {
               String var12 = (String)var11.get("clouds");
               var2.put("mc/renderClouds", "true".equals(var12) && "fast".equals(var2.get("mc/renderClouds")) ? "fast" : var12);
            }

            if (var11.containsKey("showCape")) {
               var2.put("mc/modelPart_cape", var11.get("showCape"));
            }
         }

         a(c(var0), var2);
      } catch (IOException var10) {
         System.err.println("[Kazer] Could not save shared game settings: " + var10.getMessage());
      }
   }

   private static Map<String, String> a(Path var0) throws IOException {
      LinkedHashMap var1 = new LinkedHashMap();
      if (Files.isRegularFile(var0)) {
         for (String var3 : Files.readAllLines(var0, StandardCharsets.UTF_8)) {
            int var4 = var3.indexOf(58);
            if (var4 > 0 && !var3.startsWith("#")) {
               var1.put(var3.substring(0, var4), var3.substring(var4 + 1));
            }
         }
      }

      return var1;
   }

   private static void a(Path var0, Map<String, String> var1) throws IOException {
      Files.createDirectories(var0.toAbsolutePath().getParent());
      ArrayList var2 = new ArrayList();

      for (Entry var4 : var1.entrySet()) {
         var2.add((String)var4.getKey() + ":" + (String)var4.getValue());
      }

      Path var5 = var0.resolveSibling(var0.getFileName() + ".kazer.tmp");
      Files.write(var5, var2, StandardCharsets.UTF_8);
      Files.move(var5, var0, StandardCopyOption.REPLACE_EXISTING);
   }
}
