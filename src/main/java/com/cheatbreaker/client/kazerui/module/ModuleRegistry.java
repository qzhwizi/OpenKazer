package com.cheatbreaker.client.kazerui.module;

import com.cheatbreaker.client.kazerui.module.modules.hud.CombatHud;
import com.cheatbreaker.client.kazerui.module.modules.hud.ModuleTitles;
import com.cheatbreaker.client.kazerui.module.modules.mechanic.ModuleTab;
import com.cheatbreaker.client.kazerui.module.modules.mechanic.ModuleWeatherChanger;
import com.cheatbreaker.client.kazerui.module.modules.mechanic.ModuleZoom;
import com.cheatbreaker.client.kazerui.module.modules.visual.ModuleGlintColorizer;
import com.cheatbreaker.client.kazerui.module.modules.visual.ModuleHitColor;
import com.cheatbreaker.client.kazerui.module.modules.visual.ModuleHitbox;
import com.cheatbreaker.client.kazerui.module.modules.visual.ModuleItems2d;
import com.cheatbreaker.client.kazerui.module.modules.visual.ModuleOverlay;
import com.cheatbreaker.client.kazerui.module.modules.visual.ModuleParticleChanger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class ModuleRegistry {
   private static final Map<String, com.cheatbreaker.client.kazerui.module.KazerModule> BY_KEY = new LinkedHashMap<>();
   private static final List<com.cheatbreaker.client.kazerui.module.KazerModule> MODULES = new ArrayList<>();
   private static com.cheatbreaker.client.kazerui.gui.components.MenuModel model;
   public static com.cheatbreaker.client.kazerui.module.ModuleRegistry.a hudListener;

   public static void a(com.cheatbreaker.client.kazerui.gui.components.MenuModel var0) {
      model = var0;
      if (MODULES.isEmpty()) {
         c(new com.cheatbreaker.client.kazerui.module.modules.hud.FpsHudModule());
         c(new com.cheatbreaker.client.kazerui.module.modules.hud.CpsModule());
         c(new com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule());
         c(new com.cheatbreaker.client.kazerui.module.modules.hud.KeystrokesModule());
         c(new com.cheatbreaker.client.kazerui.module.modules.hud.PotionEffectsModule());
         c(new com.cheatbreaker.client.kazerui.module.modules.hud.ScoreboardHudModule());
         c(new com.cheatbreaker.client.kazerui.module.modules.hud.BossBarHudModule());
         c(new com.cheatbreaker.client.kazerui.module.modules.visual.MotionBlurFxModule());
         c(new com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakModule());
         c(new com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule());
         c(new com.cheatbreaker.client.kazerui.module.modules.hud.ClockHudModule());
         c(new com.cheatbreaker.client.kazerui.module.modules.hud.MemoryModule());
         c(new com.cheatbreaker.client.kazerui.module.modules.hud.DayCounterModule());
         c(new com.cheatbreaker.client.kazerui.module.modules.hud.PotionCounterModule());
         c(new com.cheatbreaker.client.kazerui.module.modules.hud.ServerAddressModule());
         c(new com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule());
         c(new com.cheatbreaker.client.kazerui.module.modules.hud.CoordinatesHudModule());
         c(new com.cheatbreaker.client.kazerui.module.modules.hud.PingModule());
         c(new com.cheatbreaker.client.kazerui.module.modules.hud.SaturationHudModule());
         c(new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayers3DModule());
         c(new ModuleItems2d());
         c(new ModuleOverlay());
         c(new ModuleParticleChanger());
         c(new ModuleGlintColorizer());
         c(new com.cheatbreaker.client.kazerui.module.modules.visual.ScrollableTooltipsModule());
         c(new com.cheatbreaker.client.kazerui.module.modules.hud.StopwatchModule());
         c(new com.cheatbreaker.client.kazerui.module.modules.visual.ItemPhysicsModule());
         c(new com.cheatbreaker.client.kazerui.module.modules.visual.LightOverlayModule());
         c(new ModuleWeatherChanger());
         c(new com.cheatbreaker.client.kazerui.module.LegacyToggleModule("SHINY_POTS", "legacy_shiny_pots", false));
         c(new com.cheatbreaker.client.kazerui.module.LegacyToggleModule("MENU_BLUR", "legacy_gui_blur", false));
         c(new com.cheatbreaker.client.kazerui.module.modules.mechanic.FullbrightModule());
         c(new com.cheatbreaker.client.kazerui.module.modules.mechanic.DamageTintModule());
         c(new ModuleHitColor());
         c(new com.cheatbreaker.client.kazerui.module.modules.mechanic.FovModule());
         c(new ModuleZoom());
         c(new com.cheatbreaker.client.kazerui.module.modules.visual.ColorSaturationModule());
         c(new com.cheatbreaker.client.kazerui.module.modules.visual.BlockOutlineModule());
         c(new ModuleHitbox());
         c(new com.cheatbreaker.client.kazerui.module.modules.hud.PvpInfoModule());
         c(new ModuleTab());
         c(new ModuleTitles());
         com.cheatbreaker.client.kazerui.module.modules.hud.ComboModule var1 = new com.cheatbreaker.client.kazerui.module.modules.hud.ComboModule();
         com.cheatbreaker.client.kazerui.module.modules.hud.ReachDisplayModule var2 = new com.cheatbreaker.client.kazerui.module.modules.hud.ReachDisplayModule();
         c(var1);
         c(var2);
         CombatHud.a(var1, var2);
         com.cheatbreaker.client.kazerui.gui.components.OptionTree.provider = var0x -> {
            com.cheatbreaker.client.kazerui.module.KazerModule var1x = a(var0x);
            return var1x == null ? null : var1x.k();
         };
         com.cheatbreaker.client.kazerui.gui.components.OptionTree.buttons = (var0x, var1x) -> {
            com.cheatbreaker.client.kazerui.module.KazerModule var2x = a(var0x);
            if (var2x != null) {
               for (com.cheatbreaker.client.kazerui.module.settings.AbstractSetting var4 : var2x.g()) {
                  if (var4.key.equals(var1x) && var4 instanceof com.cheatbreaker.client.kazerui.module.settings.ButtonSetting) {
                     ((com.cheatbreaker.client.kazerui.module.settings.ButtonSetting)var4).b();
                  }
               }
            }
         };
      }
   }

   private static void c(com.cheatbreaker.client.kazerui.module.KazerModule var0) {
      MODULES.add(var0);
      d(var0);
   }

   public static void a(com.cheatbreaker.client.module.Module var0) {
      String var1 = com.cheatbreaker.client.kazerui.notifications.LegacyConfigMigration.a(var0.h());
      if (var1 != null && a(var1) == null) {
         c(new com.cheatbreaker.client.kazerui.module.NativeModuleAdapter(var0));
      }
   }

   public static void a(com.cheatbreaker.client.settings.GlobalSettings var0, Map<String, Object> var1) {
      if (a("global") == null) {
         c(new com.cheatbreaker.client.kazerui.module.GlobalSettingsModule(var0, var1));
      }
   }

   private static void d(com.cheatbreaker.client.kazerui.module.KazerModule var0) {
      BY_KEY.put(var0.b(), var0);

      for (com.cheatbreaker.client.kazerui.module.KazerModule var2 : var0.i()) {
         d(var2);
      }
   }

   static void a(com.cheatbreaker.client.kazerui.module.KazerModule var0) {
      d(var0);
      if (hudListener != null) {
         for (com.cheatbreaker.client.kazerui.module.modulerender.HudElement var2 : var0.h()) {
            hudListener.a(var2);
         }
      }
   }

   static void b(com.cheatbreaker.client.kazerui.module.KazerModule var0) {
      for (com.cheatbreaker.client.kazerui.module.KazerModule var2 : var0.i()) {
         b(var2);
      }

      BY_KEY.remove(var0.b());
      if (hudListener != null) {
         for (com.cheatbreaker.client.kazerui.module.modulerender.HudElement var4 : var0.h()) {
            hudListener.b(var4);
         }
      }
   }

   public static List<com.cheatbreaker.client.kazerui.module.KazerModule> a() {
      return Collections.unmodifiableList(MODULES);
   }

   public static com.cheatbreaker.client.kazerui.module.KazerModule a(String var0) {
      return BY_KEY.get(var0.toLowerCase(Locale.ROOT));
   }

   public static void b() {
      for (com.cheatbreaker.client.kazerui.module.KazerModule var1 : BY_KEY.values()) {
         var1.a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.START);
      }

      for (com.cheatbreaker.client.kazerui.module.KazerModule var3 : BY_KEY.values()) {
         var3.a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.END);
      }
   }

   public static List<com.cheatbreaker.client.kazerui.module.modulerender.HudElement> c() {
      ArrayList var0 = new ArrayList();

      for (com.cheatbreaker.client.kazerui.module.KazerModule var2 : BY_KEY.values()) {
         var0.addAll(var2.h());
      }

      return var0;
   }

   public static com.cheatbreaker.client.kazerui.module.modulerender.HudElement b(String var0) {
      com.cheatbreaker.client.kazerui.module.KazerModule var1 = a(var0);
      return var1 != null && !var1.h().isEmpty() ? var1.h().get(0) : null;
   }

   public static com.cheatbreaker.client.kazerui.gui.components.MenuModel d() {
      return model;
   }

   public static String a(String var0, String var1, String var2) {
      return model == null ? var2 : model.a(var0, var1, var2);
   }

   public static void b(String var0, String var1, String var2) {
      if (model != null) {
         model.b(var0, var1, var2);
      }
   }

   public static void a(String var0, String var1) {
      if (model != null) {
         model.store.a(model.a(var0, var1));
         model.revision++;
         b(var0, var1);
      }
   }

   public static void b(String var0, String var1) {
      com.cheatbreaker.client.kazerui.module.KazerModule var2 = a(var0);
      if (var2 != null) {
         for (com.cheatbreaker.client.kazerui.module.settings.AbstractSetting var4 : var2.g()) {
            if (var4.key.equals(var1)) {
               var4.l();
            }
         }
      }
   }

   private ModuleRegistry() {
   }

   public interface a {
      void a(com.cheatbreaker.client.kazerui.module.modulerender.HudElement var1);

      void b(com.cheatbreaker.client.kazerui.module.modulerender.HudElement var1);
   }
}
