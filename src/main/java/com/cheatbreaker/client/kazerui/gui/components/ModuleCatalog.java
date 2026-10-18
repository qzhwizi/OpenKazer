package com.cheatbreaker.client.kazerui.gui.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ModuleCatalog {
   public static final List<String> PORTED = Collections.emptyList();
   public static final List<com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a> ALL;
   public static final List<String> CODE;

   public static boolean a(String var0) {
      com.cheatbreaker.client.kazerui.module.KazerModule var1 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.a(var0);
      return var1 != null && var1.d();
   }

   public static com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a b(String var0) {
      for (com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a var2 : ALL) {
         if (var2.id.equals(var0)) {
            return var2;
         }
      }

      throw new IllegalArgumentException("Unknown module: " + var0);
   }

   private ModuleCatalog() {
   }

   static {
      ArrayList var0 = new ArrayList();
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("armorstatus", "Armor Status", "HUD", "armorstatus"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("toggle_sneak", "ToggleSprint", "MECHANIC", "toggle_sneak"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("potion_effects", "Potion Effects", "HUD", "potion_effects"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("keystrokes", "Key Strokes", "HUD", "keystrokes"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("fps", "FPS", "HUD", "fps"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("cps", "CPS", "HUD", "cps"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("nametags", "Nametags", "MECHANIC", "nametag"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("bossbar", "Boss bar", "HUD", "bossbar"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("motion_blur", "Motion Blur", "MECHANIC", "motion_blur"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("scorebotard", "Scoreboard", "HUD", "scoreboard"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("chat", "Chat", "MECHANIC", "chat"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("clock", "Clock", "HUD", "clock"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("memory", "Memory", "HUD", "memory"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("day_counter", "Day Counter", "HUD", "day_counter"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("potion_counter", "Potion Counter", "HUD", "potion_counter"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("server_address", "Server Address", "HUD", "server_address"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("direction_hud", "Direction HUD", "HUD", "direction_hud"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("coordinates", "Coordinates", "HUD", "coordinates"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("combo", "Combo", "HUD", "combo"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("reach_display", "Reach Display", "HUD", "reach_display"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("freelook", "Freelook", "MECHANIC", "freelook"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("ping", "Ping", "HUD", "ping"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("saturation", "Saturation", "HUD", "saturation"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("3d_skins", "3D Skins", "MECHANIC", "3d_skins"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("2d_items", "2D Items", "MECHANIC", "2d_items"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("overlay", "Overlay", "MECHANIC", "overlay"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("particle_changer", "Particle Changer", "MECHANIC", "particle_changer"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("glint_colorizer", "Glint Colorizer", "MECHANIC", "glint_colorizer"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("scrollable_tooltips", "Scrollable Tooltips", "MECHANIC", "scrollable_tooltips"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("stopwatch", "Stopwatch", "HUD", "stopwatch"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("item_physics", "Item Physics", "MECHANIC", "item_physics"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("light_overlay", "Light Overlay", "MECHANIC", "light_overlay"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("weather_changer", "Weather Changer", "MECHANIC", "weather_changer"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("shiny_pots", "Shiny Pots", "MECHANIC", "shiny_pots"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("menu_blur", "Menu Blur", "MECHANIC", "menu_blur"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("lighting", "Lighting", "MECHANIC", "lighting"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("damage_tint", "Damage Tint", "MECHANIC", "damage_tint"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("hit_color", "Hit Color", "MECHANIC", "hit_color"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("fov", "Fov Changer", "MECHANIC", "fov"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("zoom", "Zoom", "MECHANIC", "zoom"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("color_saturation", "Color Saturation", "MECHANIC", "color_saturation"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("block_outline", "Block Outline", "MECHANIC", "block_outline"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("hitbox", "Hitbox", "MECHANIC", "hitbox"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("pvp_info", "PvP Info", "HUD", "pvp_info"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("tab", "Tab Editor", "MECHANIC", "tab"));
      var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("titles", "Title", "HUD", "titles"));
      if ("1.8.9".equals("1.8.9")) {
         var0.add(new com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a("one_seven_visuals", "1.7 Visuals", "MECHANIC", "one_seven_visuals"));
      }

      ALL = Collections.unmodifiableList(var0);
      ArrayList var1 = new ArrayList();

      for (com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a var3 : (Iterable<com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a>) var0) {
         var1.add(var3.id);
      }

      CODE = Collections.unmodifiableList(var1);
   }

   public static final class a {
      public final String id;
      public final String name;
      public final String category;
      public final String icon;
      public final boolean implemented = true;

      a(String var1, String var2, String var3, String var4) {
         this.id = var1;
         this.name = var2;
         this.category = var3;
         this.icon = var4;
      }
   }
}
