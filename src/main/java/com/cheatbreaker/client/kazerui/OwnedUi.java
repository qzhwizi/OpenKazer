package com.cheatbreaker.client.kazerui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.boss.BossStatus;

public final class OwnedUi {
   private final com.cheatbreaker.client.CheatBreakerClient core;
   private final com.cheatbreaker.client.kazerui.hud.HudFeatureManager features;
   private final List<com.cheatbreaker.client.module.Module> replaced = new ArrayList<>();
   private long appliedRevision = Long.MIN_VALUE;
   private long cosmeticPermissionRevision = Long.MIN_VALUE;
   private final com.cheatbreaker.client.kazerui.input.RawMouseInput mouse = new com.cheatbreaker.client.kazerui.input.RawMouseInput();
   private static final boolean NATIVE_1710 = "1.8.9".equals("1.7.10");
   private final com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusBarsChildModule armorBars;
   private com.cheatbreaker.client.kazerui.gui.menus.HomeScreen home;

   public static Map<String, Object> a(com.cheatbreaker.client.CheatBreakerClient var0) {
      HashMap var1 = new HashMap();

      for (com.cheatbreaker.client.settings.Setting var3 : var0.globalSettings.settingsList) {
         var1.put(var3.f(), var3.d());
      }

      return var1;
   }

   public OwnedUi(com.cheatbreaker.client.CheatBreakerClient var1, Map<String, Object> var2) {
      this.core = var1;
      Minecraft.getMinecraft().mouseHelper = this.mouse;
      ((IReloadableResourceManager)Minecraft.getMinecraft().getResourceManager()).registerReloadListener(var0 -> {
         com.cheatbreaker.client.kazerui.RenderEpoch.b();
         com.cheatbreaker.client.kazerui.module.modules.visual.MotionBlurFxModule.o();
      });
      this.features = new com.cheatbreaker.client.kazerui.hud.HudFeatureManager(new File(com.cheatbreaker.client.settings.ConfigManager.configDir, "owned-ui.properties"));
      com.cheatbreaker.client.kazerui.concurrent.EquippedCosmetics.a(this.features.c().store);
      com.cheatbreaker.client.kazerui.concurrent.CosmeticAssetStore.a();
      this.features.a(var1);
      com.cheatbreaker.client.kazerui.module.ModuleRegistry.a(var1.globalSettings, var2);
      this.armorBars = (com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusBarsChildModule)com.cheatbreaker.client.kazerui.module.ModuleRegistry.a("armorstatus_bars_child");

      for (com.cheatbreaker.client.module.Module var4 : var1.moduleManager.modules) {
         String var5 = com.cheatbreaker.client.kazerui.notifications.LegacyConfigMigration.a(var4.h());
         com.cheatbreaker.client.kazerui.module.KazerModule var6 = var5 == null ? null : com.cheatbreaker.client.kazerui.module.ModuleRegistry.a(var5);
         if (var6 != null && !(var6 instanceof com.cheatbreaker.client.kazerui.module.NativeModuleAdapter)) {
            var4.d();
            this.replaced.add(var4);
         }
      }

      String var13 = this.features.a("menuKey", null);
      if (var13 != null) {
         try {
            int var14 = Integer.parseInt(var13);
            if (var14 >= 0 && var14 < 256) {
               var1.globalSettings.openMenu.setKeyCode(var14);
               KeyBinding.resetKeyBindingArrayAndHash();
            }
         } catch (NumberFormatException var12) {
         }
      }

      for (String var7 : new String[]{"freelook", "zoom"}) {
         KeyBinding var8 = var1.globalSettings.keyBindings.a(var7.equals("freelook") ? "Freelook" : "Zoom");
         String var9 = this.features.a(var7 + "Key", null);
         if (var8 != null && var9 != null) {
            try {
               int var10 = Integer.parseInt(var9);
               if (var10 >= -100 && var10 < 256) {
                  var8.setKeyCode(var10);
               }
            } catch (NumberFormatException var11) {
            }
         }
      }

      KeyBinding.resetKeyBindingArrayAndHash();
      var1.eventBus.a(com.cheatbreaker.client.event.event.ClientTickEvent.class, var1x -> this.f());
      var1.eventBus.a(com.cheatbreaker.client.event.event.RenderHudEvent.class, var1x -> {
         com.cheatbreaker.client.kazerui.module.modules.mechanic.DamageTintModule.a(var1x.a());
         if (NATIVE_1710 && this.features.b("bossbar") && BossStatus.statusBarTime > 0) {
            BossStatus.statusBarTime--;
         }

         if (!(Minecraft.getMinecraft().currentScreen instanceof com.cheatbreaker.client.kazerui.gui.HudMoveScreen)) {
            this.features.a(var1x.a());
         }

         if (Minecraft.getMinecraft().currentScreen == null) {
            com.cheatbreaker.client.kazerui.gui.ToastNotifications.a();
         }
      });
      var1.eventBus.a(com.cheatbreaker.client.event.event.MouseClickEvent.class, var1x -> {
         com.cheatbreaker.client.kazerui.cps.CpsTracker.a(var1x.a(), Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.isUsingItem());
         this.a(var1x.a() - 100);
      });
      var1.eventBus.a(com.cheatbreaker.client.event.event.KeyPressEvent.class, var1x -> this.a(var1x.a()));
      this.f();
      this.features.b();
   }

   private void a(int var1) {
      if (var1 != 0 && Minecraft.getMinecraft().currentScreen == null) {
         for (com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a var3 : com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.ALL) {
            String var4 = this.features.c().a(var3.id, "toggleKeybind", "NONE");
            if (!var4.equals("NONE") && com.cheatbreaker.client.kazerui.module.settings.KeybindSetting.e(var4) == var1) {
               this.features.c(var3.id);
            }
         }
      }
   }

   private void f() {
      if (this.home != null && Minecraft.getMinecraft().theWorld != null) {
         this.home.e();
         this.home = null;
      }

      com.cheatbreaker.client.kazerui.concurrent.EquippedCosmetics.c();
      long var1 = com.cheatbreaker.client.kazerui.concurrent.CosmeticEntitlements.revision;
      if (this.cosmeticPermissionRevision != var1) {
         this.cosmeticPermissionRevision = var1;
         this.features.c().revision++;
      }

      com.cheatbreaker.client.kazerui.module.ModuleRegistry.b();
      if (this.appliedRevision != this.features.c().revision) {
         this.mouse.a(this.features.c().a("global", "rawInput", false));

         for (com.cheatbreaker.client.module.Module var4 : this.replaced) {
            String var5 = com.cheatbreaker.client.kazerui.notifications.LegacyConfigMigration.a(var4.h());
            var4.b(this.features.b(var5));
         }

         this.appliedRevision = this.features.c().revision;
      }
   }

   public com.cheatbreaker.client.kazerui.hud.HudFeatureManager a() {
      return this.features;
   }

   public static boolean a(GuiScreen var0) {
      return var0 instanceof com.cheatbreaker.client.kazerui.gui.menus.HomeScreen
         || var0 instanceof com.cheatbreaker.client.kazerui.gui.menus.AccountScreen
         || var0 instanceof com.cheatbreaker.client.kazerui.gui.KazerSettingsScreen
         || var0 instanceof com.cheatbreaker.client.kazerui.gui.HudMoveScreen;
   }

   public boolean b() {
      if (!this.features.b("bossbar")) {
         return false;
      } else {
         if (BossStatus.statusBarTime > 0) {
            BossStatus.statusBarTime--;
         }

         return true;
      }
   }

   public boolean c() {
      return this.armorBars != null && this.armorBars.a();
   }

   public boolean a(ScaledResolution var1, int var2) {
      if (!this.c()) {
         return false;
      } else {
         com.cheatbreaker.client.kazerui.overlay.OverlayLayout.left_height = var2;
         com.cheatbreaker.client.kazerui.overlay.OverlayEvents.b var3 = new com.cheatbreaker.client.kazerui.overlay.OverlayEvents.b(com.cheatbreaker.client.kazerui.overlay.OverlayEvents.aInner.ARMOR, var1);
         this.armorBars.a(var3);
         return var3.a();
      }
   }

   public GuiScreen d() {
      if (this.home == null) {
         this.home = new com.cheatbreaker.client.kazerui.gui.menus.HomeScreen(this.features, this.core.globalSettings.openMenu);
      }

      return this.home;
   }

   public GuiScreen b(GuiScreen var1) {
      return new com.cheatbreaker.client.kazerui.gui.KazerSettingsScreen(this.features, this.core.globalSettings.openMenu, var1);
   }

   public GuiScreen c(GuiScreen var1) {
      return new com.cheatbreaker.client.kazerui.gui.HudMoveScreen(this.features, this.core.globalSettings.openMenu, var1);
   }

   public void e() {
      this.features.b();
   }
}
