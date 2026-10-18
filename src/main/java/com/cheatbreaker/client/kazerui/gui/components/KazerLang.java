package com.cheatbreaker.client.kazerui.gui.components;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class KazerLang {
   private static final Pattern ARG = Pattern.compile("(?<raw>[$%](?<id>\\d+)?(?<i18n>\\$[sdf])?(\\{(?<name>[a-zA-Z0-9]+)})?)");
   private static Map<String, Object> root;
   private static final Map<String, Supplier<String>> NAMES = new HashMap<>();

   private KazerLang() {
   }

   private static synchronized Map<String, Object> a() {
      if (root == null) {
         try (InputStream var0 = com.cheatbreaker.client.kazerui.gui.components.KazerLang.class.getResourceAsStream("/assets/kazerui/lang/kazer/en_US.json")) {
            ByteArrayOutputStream var2 = new ByteArrayOutputStream();
            byte[] var3 = new byte[8192];

            int var4;
            while ((var4 = var0.read(var3)) > 0) {
               var2.write(var3, 0, var4);
            }

            root = (Map<String, Object>)com.cheatbreaker.client.kazerui.gui.components.MiniJsonParser.a(new String(var2.toByteArray(), StandardCharsets.UTF_8));
         } catch (Exception var15) {
            throw new IllegalStateException("Cannot load Kazer's en_US.json", var15);
         }
      }

      return root;
   }

   public static String a(String var0, String var1, Object... var2) {
      Map var3 = a();

      for (String var7 : var0.split("\\.")) {
         if (!var7.isEmpty()) {
            Object var8 = var3.get(var7);
            if (var8 instanceof Map) {
               var3 = (Map)var8;
            }
         }
      }

      Object var9 = var3.get(var1);
      return var9 instanceof String && !((String)var9).isEmpty() ? a((String)var9, var2) : var1;
   }


   static String a(String var0, Object... var1) {
      if (!var0.contains("$")) {
         return var0;
      } else {
         Matcher var2 = ARG.matcher(var0);
         String var3 = var0;

         while (var2.find()) {
            if (var2.group("id") != null) {
               int var4 = Integer.parseInt(var2.group("id")) - (var2.group("i18n") != null ? 1 : 0);
               if (var4 >= 0 && var4 < var1.length) {
                  var3 = var3.replace(var2.group("raw"), String.valueOf(var1[var4]));
               }
            }
         }

         return var3;
      }
   }

   public static void a(String var0, Supplier<String> var1) {
      NAMES.put(var0.toUpperCase(Locale.ROOT), var1);
   }

   public static String b(String var0, String var1) {
      Supplier var2 = NAMES.get(var0.toUpperCase(Locale.ROOT));
      if (var2 != null) {
         return (String)var2.get();
      } else {
         String var3 = a("features." + var0.toUpperCase(Locale.ROOT) + ".details", "name");
         return var3.equals("name") ? var1 : var3;
      }
   }

   public static String a(String var0) {
      if (var0.equalsIgnoreCase("particle_changer")) {
         return "Adjust particle size, density, visible types and color without changing combat.";
      } else if (var0.equalsIgnoreCase("glint_colorizer")) {
         return "Set item and armor glint colors; respect Overlay visibility and native depth coverage.";
      } else if (var0.equalsIgnoreCase("scrollable_tooltips")) {
         return "Scroll long item tips. Shift + Wheel moves horizontally; Ctrl + Wheel changes scale.";
      } else if (var0.equalsIgnoreCase("stopwatch")) {
         return "A movable stopwatch or countdown HUD with start, pause and reset controls.";
      } else if (var0.equalsIgnoreCase("item_physics")) {
         return "Dropped-item model rotation and ground display; takes priority over 2D Items without changing item physics.";
      } else if (var0.equalsIgnoreCase("light_overlay")) {
         return "Show nearby floor brightness markers using bounded checks of loaded blocks. Markers are light hints, not exact mob-spawn predictions.";
      } else if (var0.equalsIgnoreCase("weather_changer")) {
         return "Choose local clear, rain or snow visuals and weather sounds. Server weather stays unchanged.";
      } else if (var0.equalsIgnoreCase("overlay")) {
         return "Adjust texture overlays, first-person effects and visible entity layers.";
      } else {
         String var1 = a("features." + var0.toUpperCase(Locale.ROOT) + ".details", "description");
         return var1.equals("description") ? null : var1;
      }
   }
}
