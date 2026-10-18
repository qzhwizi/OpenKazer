package com.cheatbreaker.client.kazerui.module.modules.mechanic;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.IChatComponent;
import org.lwjgl.input.Keyboard;

public final class ModuleTab extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static ModuleTab instance;
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting logo = this.a("tabLogo", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting toggle = this.a("toggleKeyTab", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting highlight = this.a("highlightOwnName", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting shadow = this.a("nameShadow", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting hidePing = this.a("hidePing", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting numbers = this.a("displayPingAsNumber", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting dynamic = this.a("dynamicPingColor", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting noHeader = this.a("disableHeader", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting noFooter = this.a("disableFooter", false);
   private final com.cheatbreaker.client.kazerui.module.settings.KeybindSetting key = this.a("tabKeybind", n());
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting background = this.a("backgroundColor", Integer.MIN_VALUE);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting rows = this.a("rowsColor", 553648127);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting own = this.a("nameColor", -43691);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting pingColor = this.a("pingNumberColor", -171);
   private static final String[] PINGS = new String[10000];
   private boolean held;
   private boolean active;
   private boolean customKey;
   private long keyRevision = Long.MIN_VALUE;

   private static String n() {
      Minecraft var0 = m();
      if (var0 != null && var0.gameSettings != null) {
         int var1 = var0.gameSettings.keyBindPlayerList.getKeyCode();
         String var2 = var1 < 0 ? "MOUSE" + (var1 + 101) : Keyboard.getKeyName(var1);
         return var2 == null ? "NONE" : var2;
      } else {
         return "TAB";
      }
   }

   public ModuleTab() {
      super("TAB", false);
      instance = this;
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(this.key, this.toggle, this.noHeader, this.noFooter);
      var1.a("colorOptions", var1x -> var1x.a(this.background, this.rows));
      var1.a(this.highlight, var1x -> var1x.a(this.own));
      var1.a(this.shadow, this.hidePing);
      var1.a(this.numbers, var1x -> var1x.a(this.dynamic, this.pingColor));
   }

   public static boolean logoEnabled(boolean var0) {
      ModuleTab var1 = instance;
      return var1 != null && var1.a() ? var1.logo.b() : var0;
   }

   @Override
   public void a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a var1) {
      if (var1.phase == com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b.START) {
         com.cheatbreaker.client.kazerui.gui.components.MenuModel var2 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.d();
         if (this.keyRevision != var2.revision) {
            this.customKey = var2.a(this.b(), this.key.key, null) != null;
            this.keyRevision = var2.revision;
         }

         boolean var3 = this.a() && m().currentScreen == null && (this.customKey ? this.key.b() : m().gameSettings.keyBindPlayerList.isKeyDown());
         if (var3 && !this.held) {
            this.active = !this.active;
         }

         this.held = var3;
         if (m().currentScreen != null || !this.a()) {
            this.active = false;
         }
      }
   }

   public static boolean listKey(boolean var0) {
      ModuleTab var1 = instance;
      return var1 != null && var1.a() ? (var1.toggle.b() ? var1.active : (var1.customKey ? var1.held : var0)) : var0;
   }

   public static int boxColor(int var0) {
      ModuleTab var1 = instance;
      return var1 != null && var1.a() ? (var0 == Integer.MIN_VALUE ? var1.background.a(0.0F) : (var0 == 553648127 ? var1.rows.a(0.0F) : var0)) : var0;
   }

   public static boolean highlighting() {
      return instance != null && instance.a() && instance.highlight.b();
   }

   public static int nameColor(int var0, boolean var1) {
      ModuleTab var2 = instance;
      return var2 != null && var2.a() && var1 && var2.highlight.b() ? var2.own.a(0.0F) : var0;
   }

   public static boolean nameShadow(boolean var0) {
      ModuleTab var1 = instance;
      return var1 != null && var1.a() ? var1.shadow.b() : var0;
   }

   public static IChatComponent header(IChatComponent var0) {
      ModuleTab var1 = instance;
      return var1 != null && var1.a() && var1.noHeader.b() ? null : var0;
   }

   public static IChatComponent footer(IChatComponent var0) {
      ModuleTab var1 = instance;
      return var1 != null && var1.a() && var1.noFooter.b() ? null : var0;
   }

   public static boolean drawPing(int var0, int var1, int var2, int var3) {
      ModuleTab var4 = instance;
      if (var4 == null || !var4.a()) {
         return false;
      } else if (var4.hidePing.b() || var3 < 0) {
         return true;
      } else if (!var4.numbers.b()) {
         return false;
      } else {
         String var5 = var3 >= 10000 ? ">9999" : PINGS[var3];
         if (var5 == null) {
            PINGS[var3] = var5 = Integer.toString(var3);
         }

         int var6 = var4.dynamic.b() ? (var3 < 65 ? -11141291 : (var3 < 120 ? -171 : (var3 < 250 ? -43691 : -5636096))) : var4.pingColor.a(0.0F);
         GlStateManager.pushMatrix();

         try {
            GlStateManager.translate(var0 + var2 - 12, var1 + 2, 0.0F);
            GlStateManager.scale(0.5, 0.5, 1.0);
            com.cheatbreaker.client.kazerui.module.modulerender.HudTextUtils.a(var5, 24 - com.cheatbreaker.client.kazerui.module.modulerender.HudTextUtils.a(var5), 0.0F, var6, false);
         } finally {
            GlStateManager.popMatrix();
         }

         return true;
      }
   }
}
