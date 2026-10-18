package com.cheatbreaker.client.kazerui.gui.components;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

public final class OptionTree {
   public static BiConsumer<String, String> buttons;
   public static Function<String, com.cheatbreaker.client.kazerui.gui.components.OptionTree.b> provider;
   private static final Map<String, com.cheatbreaker.client.kazerui.gui.components.OptionTree.b> PAGES = new HashMap<>();
   private static final com.cheatbreaker.client.kazerui.gui.components.OptionTree.b EMPTY = new com.cheatbreaker.client.kazerui.gui.components.OptionTree.b(false, false, Collections.emptyList());

   private static com.cheatbreaker.client.kazerui.gui.components.OptionTree.b c(String var0) {
      return provider == null ? null : provider.apply(var0.toLowerCase(Locale.ROOT));
   }

   private static synchronized void a() {
      if (PAGES.isEmpty()) {
         try (InputStream var0 = com.cheatbreaker.client.kazerui.gui.components.OptionTree.class.getResourceAsStream("/assets/kazerui/ui/option_tree.json")) {
            ByteArrayOutputStream var2 = new ByteArrayOutputStream();
            byte[] var3 = new byte[65536];

            int var4;
            while ((var4 = var0.read(var3)) > 0) {
               var2.write(var3, 0, var4);
            }

            Map var22 = (Map)com.cheatbreaker.client.kazerui.gui.components.MiniJsonParser.a(new String(var2.toByteArray(), StandardCharsets.UTF_8));

            for (Entry var6 : (Iterable<Entry>) var22.entrySet()) {
               Map var7 = (Map)var6.getValue();
               ArrayList var8 = new ArrayList();

               for (Object var10 : (List)var7.get("options")) {
                  var8.add(new com.cheatbreaker.client.kazerui.gui.components.OptionTree.a((Map<String, Object>)var10));
               }

               PAGES.put(
                  (String)var6.getKey(),
                  new com.cheatbreaker.client.kazerui.gui.components.OptionTree.b(
                     Boolean.TRUE.equals(var7.get("hud")), Boolean.TRUE.equals(var7.get("keybind")), Collections.unmodifiableList(var8)
                  )
               );
            }
         } catch (Exception var21) {
            throw new IllegalStateException("Cannot load the option tree", var21);
         }
      }
   }

   public static com.cheatbreaker.client.kazerui.gui.components.OptionTree.b a(String var0) {
      com.cheatbreaker.client.kazerui.gui.components.OptionTree.b var1 = c(var0);
      if (var1 != null) {
         return var1;
      } else {
         a();
         com.cheatbreaker.client.kazerui.gui.components.OptionTree.b var2 = PAGES.get(var0.toUpperCase(Locale.ROOT));
         return var2 == null ? EMPTY : var2;
      }
   }

   public static List<com.cheatbreaker.client.kazerui.gui.components.OptionTree.a> b(String var0) {
      com.cheatbreaker.client.kazerui.gui.components.OptionTree.b var1 = c(var0);
      if (var1 != null) {
         return var1.options;
      } else {
         a();
         String var2 = var0.toUpperCase(Locale.ROOT);
         if (PAGES.containsKey(var2)) {
            return PAGES.get(var2).options;
         } else {
            for (com.cheatbreaker.client.kazerui.gui.components.OptionTree.b var4 : PAGES.values()) {
               com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var5 = a(var4.options, var2);
               if (var5 != null) {
                  return var5.children;
               }
            }

            return Collections.emptyList();
         }
      }
   }

   private static com.cheatbreaker.client.kazerui.gui.components.OptionTree.a a(List<com.cheatbreaker.client.kazerui.gui.components.OptionTree.a> var0, String var1) {
      for (com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var3 : var0) {
         if (var3.type.equals("child") && var3.key.equals(var1)) {
            return var3;
         }

         if (!var3.type.equals("child")) {
            com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var4 = a(var3.children, var1);
            if (var4 != null) {
               return var4;
            }
         }
      }

      return null;
   }

   public static com.cheatbreaker.client.kazerui.gui.components.OptionTree.a a(String var0, String var1) {
      for (com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var3 : a(b(var0))) {
         if (var3.key.equals(var1)) {
            return var3;
         }
      }

      return null;
   }

   public static List<com.cheatbreaker.client.kazerui.gui.components.OptionTree.a> a(List<com.cheatbreaker.client.kazerui.gui.components.OptionTree.a> var0) {
      ArrayList var1 = new ArrayList();

      for (com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var3 : var0) {
         if (var3.type.equals("option")) {
            var1.add(var3);
         }

         if (!var3.type.equals("child")) {
            var1.addAll(a(var3.children));
         }
      }

      return var1;
   }

   private OptionTree() {
   }

   public static final class a {
      public final String type;
      public final String key;
      public final String kind;
      public final String value;
      public final float min;
      public final float max;
      public final boolean integer;
      public final boolean alpha;
      public final boolean chroma;
      public final boolean chromaOn;
      public final boolean transient_;
      public final boolean hud;
      public final String hide;
      public final String text;
      public final com.cheatbreaker.client.kazerui.gui.components.MenuWidget widget;
      public final float width;
      public final int maxLength;
      public final int roundTo;
      public final List<String[]> values;
      public final List<com.cheatbreaker.client.kazerui.gui.components.OptionTree.a> children;
      public final BooleanSupplier hideWhen;

      a(Map<String, Object> var1) {
         this(var1, null, null);
      }

      public static com.cheatbreaker.client.kazerui.gui.components.OptionTree.a a(
         Map<String, Object> var0, List<com.cheatbreaker.client.kazerui.gui.components.OptionTree.a> var1, BooleanSupplier var2
      ) {
         return new com.cheatbreaker.client.kazerui.gui.components.OptionTree.a(var0, var1, var2);
      }

      private a(Map<String, Object> var1, List<com.cheatbreaker.client.kazerui.gui.components.OptionTree.a> var2, BooleanSupplier var3) {
         this.hideWhen = var3;
         this.type = (String)var1.get("type");
         this.key = (String)var1.get("key");
         this.kind = var1.containsKey("kind") ? (String)var1.get("kind") : "";
         this.value = var1.get("default") == null ? "" : String.valueOf(var1.get("default"));
         this.min = a(var1.get("min"));
         this.max = a(var1.get("max"));
         this.integer = Boolean.TRUE.equals(var1.get("integer"));
         this.alpha = !Boolean.FALSE.equals(var1.get("alpha"));
         this.chroma = !Boolean.FALSE.equals(var1.get("chroma"));
         this.chromaOn = Boolean.TRUE.equals(var1.get("chromaOn"));
         this.transient_ = Boolean.TRUE.equals(var1.get("transient"));
         this.hud = Boolean.TRUE.equals(var1.get("hud"));
         this.hide = (String)var1.get("hide");
         this.width = a(var1.get("width"));
         this.widget = var1.get("widget") instanceof com.cheatbreaker.client.kazerui.gui.components.MenuWidget
            ? (com.cheatbreaker.client.kazerui.gui.components.MenuWidget)var1.get("widget")
            : null;
         this.text = (String)var1.get("text");
         this.maxLength = var1.containsKey("maxLength") ? Integer.parseInt(String.valueOf(var1.get("maxLength"))) : 0;
         this.roundTo = var1.containsKey("roundTo") ? Integer.parseInt(String.valueOf(var1.get("roundTo"))) : 0;
         ArrayList var4 = new ArrayList();
         if (var1.get("values") instanceof List) {
            for (Object var6 : (List)var1.get("values")) {
               List var7 = (List)var6;
               var4.add(new String[]{(String)var7.get(0), (String)var7.get(1)});
            }
         }

         this.values = Collections.unmodifiableList(var4);
         ArrayList var8 = var2 == null ? new ArrayList() : new ArrayList(var2);
         if (var2 == null && var1.get("children") instanceof List) {
            for (Object var10 : (List)var1.get("children")) {
               var8.add(new com.cheatbreaker.client.kazerui.gui.components.OptionTree.a((Map<String, Object>)var10));
            }
         }

         this.children = Collections.unmodifiableList(var8);
      }

      private static float a(Object var0) {
         try {
            return var0 == null ? Float.NaN : Float.parseFloat(String.valueOf(var0));
         } catch (NumberFormatException var2) {
            return Float.NaN;
         }
      }
   }

   public static final class b {
      public final boolean hud;
      public final boolean keybind;
      public final List<com.cheatbreaker.client.kazerui.gui.components.OptionTree.a> options;

      public b(boolean var1, boolean var2, List<com.cheatbreaker.client.kazerui.gui.components.OptionTree.a> var3) {
         this.hud = var1;
         this.keybind = var2;
         this.options = var3;
      }
   }
}
