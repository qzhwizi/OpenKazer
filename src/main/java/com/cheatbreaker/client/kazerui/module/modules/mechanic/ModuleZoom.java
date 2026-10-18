package com.cheatbreaker.client.kazerui.module.modules.mechanic;

import net.minecraft.client.settings.KeyBinding;

public final class ModuleZoom extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static ModuleZoom instance;
   private final com.cheatbreaker.client.kazerui.module.settings.KeybindSetting key = this.a("zoomKeybind", "C");
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting toggle = this.a("toggleKeyZoom", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting smoothCamera = this.a("smoothCamera", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting smoothZoom = this.a("smoothZoom", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting variable = this.a("variableZoom", false);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting scrollSpeed = this.a("zoomScrollSpeed", 1.0F, 0.25F, 5.0F);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting divisor = this.a("zoomDivisor", 4, 2, 10);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting sensitivity = this.a("cameraSensitivity", 1.0F, 0.1F, 2.0F);
   private boolean active;
   private boolean down;
   private boolean savedSmooth;
   private float variableFactor = 1.0F;
   private float previous = 1.0F;
   private float current = 1.0F;

   public ModuleZoom() {
      super("ZOOM", true);
      instance = this;
      String var1 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.d().store.a("zoomKeyName", null);
      if (var1 != null
         && com.cheatbreaker.client.kazerui.module.ModuleRegistry.d().store.a(com.cheatbreaker.client.kazerui.module.ModuleRegistry.d().a("zoom", "zoomKeybind"), null) == null) {
         com.cheatbreaker.client.kazerui.module.ModuleRegistry.d().store.b(com.cheatbreaker.client.kazerui.module.ModuleRegistry.d().a("zoom", "zoomKeybind"), var1);
      }

      this.key.a(this::o);
   }

   private void o() {
      com.cheatbreaker.client.CheatBreakerClient var1 = com.cheatbreaker.client.CheatBreakerClient.N();
      if (var1 != null) {
         KeyBinding var2 = var1.globalSettings.keyBindings.a("Zoom");
         if (var2 != null) {
            var2.setKeyCode(this.key.c());
            KeyBinding.resetKeyBindingArrayAndHash();
         }

         com.cheatbreaker.client.kazerui.module.ModuleRegistry.d().store.b("zoomKeyName", this.key.j());
         com.cheatbreaker.client.kazerui.module.ModuleRegistry.d().store.b("zoomKey", Integer.toString(this.key.c()));
      }
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(this.key, this.toggle, this.smoothCamera, this.smoothZoom, this.variable, this.scrollSpeed, this.divisor, this.sensitivity);
   }

   @Override
   protected void f() {
      this.p();
   }

   private void p() {
      if (this.active) {
         m().gameSettings.smoothCamera = this.savedSmooth;
      }

      this.active = false;
      this.down = false;
      this.variableFactor = this.previous = this.current = 1.0F;
   }

   @Override
   public void a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a var1) {
      if (var1.phase == com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b.START) {
         boolean var2 = this.a() && m().theWorld != null && m().currentScreen == null && m().inGameHasFocus;
         boolean var3 = var2 && this.key.b();
         boolean var4 = this.toggle.b() ? (var3 && !this.down ? !this.active : this.active) : var3;
         if (!var2) {
            var4 = false;
         }

         this.down = var3;
         if (var4 != this.active) {
            if (var4) {
               this.savedSmooth = m().gameSettings.smoothCamera;
            } else {
               m().gameSettings.smoothCamera = this.savedSmooth;
               this.variableFactor = 1.0F;
            }

            this.active = var4;
         }

         if (this.active) {
            m().gameSettings.smoothCamera = this.smoothCamera.b() || this.savedSmooth;
         }

         this.previous = this.current;
         float var5 = this.active ? 1.0F / (this.divisor.b() * (this.variable.b() ? this.variableFactor : 1.0F)) : 1.0F;
         this.current = this.smoothZoom.b() ? this.current + (var5 - this.current) * 0.75F : var5;
      }
   }

   public static boolean n() {
      return instance != null;
   }

   public static boolean a(int var0) {
      ModuleZoom var1 = instance;
      if (var1 != null && var1.a() && var1.active && var1.variable.b() && var0 != 0) {
         var1.variableFactor = Math.max(1.4F / var1.divisor.b(), Math.min(10.0F, var1.variableFactor + Math.signum((float)var0) * var1.scrollSpeed.b() * 0.5F));
         return true;
      } else {
         return false;
      }
   }

   public static float a(float var0, float var1) {
      ModuleZoom var2 = instance;
      return var2 != null && var2.a() ? var0 * (var2.previous + (var2.current - var2.previous) * var1) : var0;
   }

   public static float sensitivity(float var0) {
      ModuleZoom var1 = instance;
      return var1 != null && var1.a() && var1.active ? var0 * var1.sensitivity.b() : var0;
   }
}
