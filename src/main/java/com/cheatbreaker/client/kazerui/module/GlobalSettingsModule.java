package com.cheatbreaker.client.kazerui.module;

import cn.kazer.client189.input.FullscreenTransition;
import com.cheatbreaker.client.kazerui.input.WindowMode;
import com.cheatbreaker.client.kazerui.module.modules.visual.ModuleOverlay;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;

public final class GlobalSettingsModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static com.cheatbreaker.client.kazerui.module.GlobalSettingsModule instance;
   private final List<com.cheatbreaker.client.kazerui.module.NativeModuleAdapter.a> values = new ArrayList<>();
   private final Set<String> performance = new HashSet<>();
   private final com.cheatbreaker.client.settings.GlobalSettings nativeSettings;
   private long appliedRevision = Long.MIN_VALUE;
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting borderless = this.a("borderlessFullscreen", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting cloaks = this.a("showCloaks", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting headwear = this.a("showHeadwear", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting pets = this.a("showPets", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting tabIcon = this.a("showTabIcon", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting nametagIcon = this.a("showNametagIcon", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting disableSorting = this.a("disableModSorting", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting transparency = this.a("uiTransparency", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting previews = this.a("packPreviews", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting raw = this.a("rawInput", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting debug = this.a("hideDebug", true);
   private final com.cheatbreaker.client.kazerui.module.HudStyleApplier styles = new com.cheatbreaker.client.kazerui.module.HudStyleApplier(this);

   public GlobalSettingsModule(com.cheatbreaker.client.settings.GlobalSettings var1, Map<String, Object> var2) {
      super("global", true);
      this.nativeSettings = var1;
      instance = this;
      this.borderless.a(() -> "Borderless Fullscreen");
      this.borderless.a(() -> p());
      this.cloaks.a(() -> "Show Cloaks");
      this.headwear.a(() -> "Show Headwear");
      this.pets.a(() -> "Show Pets");
      this.tabIcon.a(() -> "Show Tab Icon");
      this.nametagIcon.a(() -> "Show Nametag Icon");
      this.disableSorting.a(() -> "Disable Mod Sorting");
      this.transparency.a(() -> "UI Transparency");
      this.previews.a(() -> "Pack Previews");
      this.raw.a(() -> "Raw Mouse Input");
      this.debug.a(() -> "Show HUD in F3");
      com.cheatbreaker.client.kazerui.gui.components.MenuModel var3 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.d();
      if (!"true".equals(var3.store.a("migration.badgeControls.v1", null))) {
         for (String var5 : var3.profiles) {
            String var6 = "profile." + var5 + ".";
            if ("true".equals(var3.store.a(var6 + "tab.enabled", "false")) && "false".equals(var3.store.a(var6 + "tab.tabLogo", "true"))) {
               var3.store.b(var6 + "nametags.legacy_show_kazer_icon_in_tab", "false");
            }
         }

         var3.store.b("migration.badgeControls.v1", "true");
      }

      boolean var10 = !"true".equals(var3.store.a("migration.globalSettings.v1", null));

      for (com.cheatbreaker.client.settings.Setting var12 : var1.settingsList) {
         if (var12 != var1.showHudInDebug && !var12.f().equalsIgnoreCase("label") && var12.d() != null && !(var12.d() instanceof String[])) {
            Object var7 = var2.get(var12.f());
            if (var7 == null) {
               var7 = var12.d();
            }

            com.cheatbreaker.client.kazerui.module.NativeModuleAdapter.a var8 = this.a(new com.cheatbreaker.client.kazerui.module.NativeModuleAdapter.a(var12, var7));
            var8.a(var12::g);
            var8.a((Runnable) var8::b);
            if (var12.e() != null) {
               var8.a(() -> !var12.j());
            }

            this.values.add(var8);
            String var9 = var12.f().toLowerCase(Locale.ROOT);
            if (var9.contains("fps") || var9.contains("chunk") || var9.contains("particle") || var9.contains("distance")) {
               this.performance.add(var8.key);
            }

            if (var10 && var3.store.a(var3.a("global", var8.key), null) == null) {
               var3.store.b(var3.a("global", var8.key), var8.c(var12.d()));
            }
         }
      }

      if (var10 && var3.store.a(var3.a("global", "hideDebug"), null) == null) {
         var3.store.b(var3.a("global", "hideDebug"), String.valueOf(!(Boolean)var1.showHudInDebug.d()));
      }

      var3.store.b("migration.globalSettings.v1", "true");
      ModuleOverlay.migrateLegacy(var1);
      var3.revision++;
   }

   public List<com.cheatbreaker.client.kazerui.gui.components.OptionTree.a> b(boolean var1) {
      com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var2 = new com.cheatbreaker.client.kazerui.module.OptionTreeBuilder();
      if (!var1) {
         var2.a("General", var1x -> {
            var1x.a(this.borderless, this.raw);
            this.a(var1x, "legacy_keep_inventory_centered", "legacy_disable_hotbar_scrolling");
         });
      }

      if (!var1) {
         var2.a("HUD Options", var1x -> {
            var1x.a(this.debug);
            this.a(var1x, "legacy_show_crosshair_in_third_person");
         });
      }

      if (!var1) {
         var2.a("Notification Options", var1x -> this.a(var1x, "legacy_show_achievements"));
      }

      if (!var1) {
         var2.a("Resource Pack Options", var1x -> {
            this.a(var1x, "legacy_transparent_background");
            var1x.a(this.previews);
         });
      }

      if (!var1) {
         var2.a("Cosmetics", var1x -> var1x.a(this.cloaks, this.headwear, this.pets, this.tabIcon, this.nametagIcon));
      }

      if (!var1) {
         var2.a("Mod Options", var1x -> var1x.a(this.disableSorting));
      }

      if (!var1) {
         var2.a("Interface", var1x -> this.a(var1x, "legacy_scrolling_speed_"));
      }

      if (!var1) {
         var2.a("Interface", var1x -> var1x.a(this.transparency));
      }

      if (!var1) {
         this.styles.a(var2);
      }

      var2.a(var1 ? "Performance" : "Other Options", var2x -> {
         for (com.cheatbreaker.client.kazerui.module.NativeModuleAdapter.a var4 : this.values) {
            if (this.performance.contains(var4.key) == var1 && !e(var4.key) && !d(var4.key)) {
               var2x.a(var4);
            }
         }
      });
      return var2.a();
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      for (com.cheatbreaker.client.kazerui.module.settings.AbstractSetting var3 : this.g()) {
         var1.a(var3);
      }
   }

   private void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1, String... var2) {
      for (String var6 : var2) {
         for (com.cheatbreaker.client.kazerui.module.NativeModuleAdapter.a var8 : this.values) {
            if (var8.key.equals(var6)) {
               var1.a(var8);
            }
         }
      }
   }

   private static boolean d(String var0) {
      return var0.equals("legacy_keep_inventory_centered")
         || var0.equals("legacy_disable_hotbar_scrolling")
         || var0.equals("legacy_show_crosshair_in_third_person")
         || var0.equals("legacy_show_achievements")
         || var0.equals("legacy_transparent_background")
         || var0.equals("legacy_scrolling_speed_");
   }

   private static void p() {
      FullscreenTransition.applyCurrent();
   }

   public static boolean a(com.cheatbreaker.client.kazerui.concurrent.CosmeticCategory var0) {
      com.cheatbreaker.client.kazerui.module.GlobalSettingsModule var1 = instance;
      return var1 == null
         || (var0 == com.cheatbreaker.client.kazerui.concurrent.CosmeticCategory.CLOAKS ? var1.cloaks : (var0 == com.cheatbreaker.client.kazerui.concurrent.CosmeticCategory.PETS ? var1.pets : var1.headwear))
            .b();
   }

   public static boolean n() {
      return instance == null || instance.previews.b();
   }

   private static boolean e(String var0) {
      String var1 = var0.replace("legacy_", "").replace("_", "").toLowerCase(Locale.ROOT);
      if (!var1.equals("mutekazersounds") && !var1.equals("mutecheatbreakersounds")) {
         if (com.cheatbreaker.client.kazerui.module.ModuleRegistry.a("overlay") == null
            || !var1.equals("enchantmentglint") && !var1.equals("clearglass") && !var1.equals("redstring")) {
            if (var1.equals("worldtime") && com.cheatbreaker.client.kazerui.module.ModuleRegistry.a("world_time") != null) {
               return true;
            } else {
               for (com.cheatbreaker.client.kazerui.module.KazerModule var3 : com.cheatbreaker.client.kazerui.module.ModuleRegistry.a()) {
                  if (!var3.id.equals("global") && var1.equals(var3.id.replace("_", "").toLowerCase(Locale.ROOT))) {
                     return true;
                  }
               }

               return var1.equals("fullbright")
                  || var1.equals("shinypots")
                  || var1.equals("guiblur")
                  || var1.equals("menublur")
                  || var1.equals("showchatbackground")
                  || var1.startsWith("crosshair") && com.cheatbreaker.client.kazerui.module.ModuleRegistry.a("crosshair") != null;
            }
         } else {
            return true;
         }
      } else {
         return true;
      }
   }

   public void o() {
      if (Display.isCreated() && Minecraft.getMinecraft().isFullScreen() && this.borderless.b() != WindowMode.active()) {
         p();
      }

      for (com.cheatbreaker.client.kazerui.module.NativeModuleAdapter.a var2 : this.values) {
         var2.b();
      }

      boolean var3 = !com.cheatbreaker.client.kazerui.module.ModuleRegistry.d().a("global", "hideDebug", true);
      if (!Boolean.valueOf(var3).equals(this.nativeSettings.showHudInDebug.d())) {
         this.nativeSettings.showHudInDebug.a(var3, false);
      }

      this.appliedRevision = com.cheatbreaker.client.kazerui.module.ModuleRegistry.d().revision;
   }

   @Override
   public void a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a var1) {
      if (var1.phase == com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b.START && this.appliedRevision != com.cheatbreaker.client.kazerui.module.ModuleRegistry.d().revision) {
         this.o();
      }
   }
}
