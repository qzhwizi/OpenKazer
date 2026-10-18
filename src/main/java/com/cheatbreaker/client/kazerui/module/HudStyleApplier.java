package com.cheatbreaker.client.kazerui.module;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

final class HudStyleApplier {
   private final com.cheatbreaker.client.kazerui.module.KazerModule owner;
   private final Map<String, com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<?>> styles = new LinkedHashMap<>();
   private final Map<String, com.cheatbreaker.client.kazerui.module.settings.ButtonSetting> buttons = new LinkedHashMap<>();

   HudStyleApplier(com.cheatbreaker.client.kazerui.module.KazerModule var1) {
      this.owner = var1;
      this.a("textColor", "Text Color", -1);
      this.a("borderColor", "Border Color", -1);
      this.a("bracketColor", "Bracket Color", -1);
      this.a("backgroundColor", "Background Color", 1862270976);
      this.a("backgroundHeight", "Background Height", 18.0F, 9.0F, 100.0F, true);
      this.a("backgroundWidth", "Background Width", 56.0F, 9.0F, 400.0F, true);
      this.a("borderThickness", "Border Thickness", 0.5F, 0.5F, 3.0F, false);
      this.a("background", "Show Background", false);
      this.a("brackets", "Show Brackets", false);
      this.a("border", "Border", false);
      this.a("textShadow", "Text Shadow", true);
   }

   private void a(String var1, String var2, com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<?> var3) {
      var3.a(() -> var2);
      this.styles.put(var1, var3);
      com.cheatbreaker.client.kazerui.module.settings.ButtonSetting var4 = this.owner.a(new com.cheatbreaker.client.kazerui.module.settings.ButtonSetting("apply_" + var1, () -> this.a(var1)));
      var4.a(() -> "Apply to All: " + var2);
      this.buttons.put(var1, var4);
   }

   private void a(String var1, String var2, int var3) {
      this.a(var1, var2, this.owner.a("style_" + var1, var3).c());
   }

   private void a(String var1, String var2, float var3, float var4, float var5, boolean var6) {
      this.a(var1, var2, var6 ? this.owner.a("style_" + var1, (int)var3, (int)var4, (int)var5) : this.owner.a("style_" + var1, var3, var4, var5));
   }

   private void a(String var1, String var2, boolean var3) {
      this.a(var1, var2, this.owner.a("style_" + var1, var3));
   }

   void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a("Global Color", var1x -> this.a(var1x, "textColor", "borderColor", "bracketColor", "backgroundColor"));
      var1.a("Global Background Options", var1x -> this.a(var1x, "backgroundHeight", "backgroundWidth", "borderThickness"));
      var1.a("Global Toggle", var1x -> this.a(var1x, "background", "brackets", "border", "textShadow"));
   }

   private void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1, String... var2) {
      for (String var6 : var2) {
         var1.a(this.styles.get(var6), this.buttons.get(var6));
      }
   }

   private void a(String var1) {
      com.cheatbreaker.client.kazerui.module.settings.AbstractSetting var2 = this.styles.get(var1);
      String var3 = var2.a(Collections.emptyList(), null).value;
      String var4 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.a("global", var2.key, var3);
      int var5 = 0;

      for (com.cheatbreaker.client.kazerui.module.KazerModule var7 : com.cheatbreaker.client.kazerui.module.ModuleRegistry.a()) {
         if (var7 != this.owner) {
            var5 += a(var7, var1, var4);

            for (com.cheatbreaker.client.kazerui.module.KazerModule var9 : var7.i()) {
               var5 += a(var9, var1, var4);
            }
         }
      }

      com.cheatbreaker.client.kazerui.module.ModuleRegistry.d().store.b();
      com.cheatbreaker.client.kazerui.module.ModuleRegistry.d().f("Updated " + var5 + " compatible HUD settings.");
   }

   private static int a(com.cheatbreaker.client.kazerui.module.KazerModule var0, String var1, String var2) {
      if (var0.h().isEmpty() && !(var0 instanceof com.cheatbreaker.client.kazerui.module.NativeModuleAdapter)) {
         return 0;
      } else {
         for (com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var4 : com.cheatbreaker.client.kazerui.gui.components.OptionTree.a(var0.k().options)) {
            String var5 = var4.key.replace("legacy_", "").replace("_", "").toLowerCase(Locale.ROOT);
            String var6 = var1.toLowerCase(Locale.ROOT);
            if (var5.equals(var6) || var1.equals("background") && var5.equals("showbackground")) {
               com.cheatbreaker.client.kazerui.module.ModuleRegistry.b(var0.b(), var4.key, var2);
               return 1;
            }
         }

         return 0;
      }
   }
}
