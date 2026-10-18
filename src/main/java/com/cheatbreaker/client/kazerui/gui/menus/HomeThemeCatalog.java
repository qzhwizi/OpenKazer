package com.cheatbreaker.client.kazerui.gui.menus;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraft.util.ResourceLocation;

final class HomeThemeCatalog {
   private static final Month[] NONE = new Month[0];
   static final com.cheatbreaker.client.kazerui.gui.menus.HomeThemeCatalog.a KAZER = new com.cheatbreaker.client.kazerui.gui.menus.HomeThemeCatalog.a(
      "kazer", "Kazer", "backgrounds/panorama", NONE, null, false
   );
   static final List<com.cheatbreaker.client.kazerui.gui.menus.HomeThemeCatalog.a> THEMES = Collections.unmodifiableList(
      Arrays.asList(
         new com.cheatbreaker.client.kazerui.gui.menus.HomeThemeCatalog.a("christmas", "Christmas", "backgrounds/christmas/panorama", new Month[]{Month.DECEMBER}, null, false),
         new com.cheatbreaker.client.kazerui.gui.menus.HomeThemeCatalog.a("anniversary", "Anniversary", "backgrounds/spring/panorama", NONE, new int[]{4, 6}, false),
         new com.cheatbreaker.client.kazerui.gui.menus.HomeThemeCatalog.a(
            "spring", "Spring", "backgrounds/spring/panorama", new Month[]{Month.MARCH, Month.APRIL, Month.MAY, Month.JUNE}, null, false
         ),
         new com.cheatbreaker.client.kazerui.gui.menus.HomeThemeCatalog.a("japan", "Japan", "backgrounds/japan/panorama", new Month[]{Month.JULY}, null, false),
         new com.cheatbreaker.client.kazerui.gui.menus.HomeThemeCatalog.a(
            "summer", "Summer", "backgrounds/spring/panorama", new Month[]{Month.JULY, Month.AUGUST, Month.SEPTEMBER}, null, false
         ),
         new com.cheatbreaker.client.kazerui.gui.menus.HomeThemeCatalog.a("vanilla", "Vanilla", "minecraft:textures/gui/title/background/panorama", NONE, null, true),
         new com.cheatbreaker.client.kazerui.gui.menus.HomeThemeCatalog.a("classic", "Classic", "backgrounds/classic/panorama", NONE, null, true)
      )
   );
   private final com.cheatbreaker.client.kazerui.gui.components.SettingsStore store;

   HomeThemeCatalog(com.cheatbreaker.client.kazerui.gui.components.SettingsStore var1) {
      this.store = var1;
   }

   com.cheatbreaker.client.kazerui.gui.components.SettingsStore a() {
      return this.store;
   }

   com.cheatbreaker.client.kazerui.gui.menus.HomeThemeCatalog.a b() {
      String var1 = this.store.a("homeTheme.selected", "");

      for (com.cheatbreaker.client.kazerui.gui.menus.HomeThemeCatalog.a var3 : THEMES) {
         if (var3.id.equals(var1)) {
            return var3;
         }
      }

      return THEMES.get(THEMES.size() - 1);
   }

   List<com.cheatbreaker.client.kazerui.gui.menus.HomeThemeCatalog.a> c() {
      LocalDateTime var1 = LocalDateTime.now();
      ArrayList var2 = new ArrayList();

      for (com.cheatbreaker.client.kazerui.gui.menus.HomeThemeCatalog.a var4 : THEMES) {
         if (var4.b(var1)) {
            var2.add(var4);
         }
      }

      return var2;
   }

   void a(String var1) {
      this.store.b("homeTheme.selected", var1);
      this.store.b();
   }

   static final class a {
      private final ResourceLocation[] faces = new ResourceLocation[6];
      final String id;
      final String name;
      final String panoramaBase;
      final Month[] months;
      final int[] monthDay;
      final boolean alwaysSelectable;

      a(String var1, String var2, String var3, Month[] var4, int[] var5, boolean var6) {
         this.id = var1;
         this.name = var2;
         this.panoramaBase = var3;
         this.months = var4;
         this.monthDay = var5;
         this.alwaysSelectable = var6;
      }

      boolean a(LocalDateTime var1) {
         if (this.monthDay == null) {
            for (Month var5 : this.months) {
               if (var5 == var1.getMonth()) {
                  return true;
               }
            }

            return false;
         } else {
            return var1.getMonthValue() == this.monthDay[0] && var1.getDayOfMonth() == this.monthDay[1];
         }
      }

      boolean b(LocalDateTime var1) {
         return this.alwaysSelectable || this.a(var1);
      }

      ResourceLocation a(int var1) {
         return this.faces[var1] != null
            ? this.faces[var1]
            : (
               this.faces[var1] = this.panoramaBase.startsWith("minecraft:")
                  ? new ResourceLocation(this.panoramaBase.substring(10) + "_" + var1 + ".png")
                  : new ResourceLocation("kazerui", "ui/" + this.panoramaBase + "_" + var1 + ".png")
            );
      }
   }
}
