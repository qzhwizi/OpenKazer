package com.cheatbreaker.client.gui.hudeditor;

import cn.kazer.client189.input.WindowsImeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public final class KazerMenuScreen extends GuiScreen {
   private static final int PANEL_W = 500;
   private static final int PANEL_H = 310;
   private static final int SIDEBAR_W = 108;
   private static final int HEADER_H = 40;
   private static final int CARD_W = 118;
   private static final int CARD_H = 108;
   private static final int GAP = 7;
   private static final int PANEL = -1475209966;
   private static final int CARD = 1578177297;
   private static final int CARD_HOVER = 1982015011;
   private static final int CARD_BAR = -2142219696;
   private static final int LINE = 1885825639;
   private static final int TEXT = -855310;
   private static final int MUTED = -6645094;
   private static final int ON = -14309019;
   private static final int OFF = -4776117;
   private static final int TRACK = -11052200;
   private final ResourceLocation logo = new ResourceLocation("client/kazer_logo.png");
   private GuiTextField search;
   private int searchX;
   private int searchY;
   private int searchW;
   private int searchH;
   private com.cheatbreaker.client.module.Module selectedModule;
   private com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.b page = com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.b.MODS;
   private com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.c settingsCategory = com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.c.GENERAL;
   private int left;
   private int top;
   private int moduleScroll;
   private int settingsScroll;
   private int globalScroll;
   private boolean draggingGlobalScrollbar;
   private final Map<com.cheatbreaker.client.settings.Setting, KeyBinding> referenceBindings = new LinkedHashMap<>();
   private com.cheatbreaker.client.settings.Setting recordingBinding;
   private com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.a modCategory = com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.a.ALL;
   private boolean creatingProfile;
   private GuiTextField profileName;
   private String profileError;
   private com.cheatbreaker.client.settings.Setting draggingSetting;
   private int draggingSettingX;
   private int draggingSettingW;
   private com.cheatbreaker.client.settings.Setting colorEditor;
   private int draggingColorChannel = -1;
   private boolean draggingModuleScrollbar;
   private boolean draggingSettingsScrollbar;
   private int scrollbarGrabOffset;
   private com.cheatbreaker.client.settings.Setting editingNumericSetting;
   private String editingNumericText = "";
   private boolean replaceNumericText;
   private com.cheatbreaker.client.settings.Setting editingStringSetting;
   private String editingStringText = "";
   private boolean replaceStringText;
   private final GuiScreen parent;

   public KazerMenuScreen() {
      this(new com.cheatbreaker.client.gui.hudeditor.HudEditorScreen());
   }

   public KazerMenuScreen(GuiScreen var1) {
      this.parent = var1;
   }

   public KazerMenuScreen(GuiScreen var1, com.cheatbreaker.client.module.Module var2) {
      this.parent = var1;
      this.selectedModule = var2;
      this.page = com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.b.MODS;
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      this.left = (this.width - 500) / 2;
      this.top = (this.height - 310) / 2;
      this.searchX = this.left + 500 - 150;
      this.searchY = this.top + 40 + 7;
      this.searchW = 140;
      this.searchH = 14;
      this.search = new GuiTextField(0, this.fontRendererObj, this.searchX, this.searchY, this.searchW, this.searchH);
      this.search.setMaxStringLength(48);
      this.search.setEnableBackgroundDrawing(false);
      this.search.setTextColor(-920591);
      this.profileName = new GuiTextField(0, this.fontRendererObj, this.left + 250 - 80, this.top + 155 - 3, 160, 18);
      this.profileName.setMaxStringLength(24);
      this.profileName.setEnableBackgroundDrawing(false);
      this.profileName.setTextColor(-855310);
   }

   public void onGuiClosed() {
      this.l();
      this.n();
      Keyboard.enableRepeatEvents(false);
      if (com.cheatbreaker.client.CheatBreakerClient.N().configManager != null) {
         com.cheatbreaker.client.CheatBreakerClient.N().configManager.a();
      }
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public void updateScreen() {
      this.search.updateCursorCounter();
      if (this.profileName != null && this.creatingProfile) {
         this.profileName.updateCursorCounter();
      }
   }

   public void drawScreen(int var1, int var2, float var3) {
      if (this.draggingGlobalScrollbar && Mouse.isButtonDown(0)) {
         this.e(var2);
      }

      if (this.draggingModuleScrollbar && Mouse.isButtonDown(0)) {
         this.c(var2);
      }

      if (this.draggingSettingsScrollbar && Mouse.isButtonDown(0)) {
         this.d(var2);
      }

      if (this.draggingSetting != null && Mouse.isButtonDown(0)) {
         this.b(this.draggingSetting, var1, this.draggingSettingX, this.draggingSettingW);
      }

      if (this.colorEditor != null && this.draggingColorChannel >= 0 && Mouse.isButtonDown(0)) {
         this.d(this.draggingColorChannel, var1);
      }

      cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
      this.a();
      this.e(var1, var2);
      this.f(var1, var2);
      if (this.selectedModule != null) {
         this.j(var1, var2);
      } else if (this.page == com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.b.MODS) {
         this.g(var1, var2);
      } else {
         this.k(var1, var2);
      }

      if (this.creatingProfile) {
         this.a(var1, var2);
      }

      if (this.colorEditor != null) {
         this.b(var1, var2);
      }

      super.drawScreen(var1, var2, var3);
   }

   private void a(int var1, int var2) {
      int var3 = this.left + 250 - 100;
      int var4 = this.top + 155 - 38;
      cn.kazer.client189.render.DrawRect.a(this.left, this.top, this.left + 500, this.top + 310, -2013265920);
      cn.kazer.client189.render.DrawRect.a(var3, var4, var3 + 200, var4 + 82, -266723043);
      cn.kazer.client189.render.DrawRect.a(var3, var4, var3 + 200, var4 + 1, -10889271);
      this.fontRendererObj.drawString("NEW PROFILE", var3 + 10, var4 + 10, -855310);
      this.profileName.drawTextBox();
      this.a("SAVE", var3 + 10, var4 + 55, 84, 18, var1, var2);
      this.a("CANCEL", var3 + 106, var4 + 55, 84, 18, var1, var2);
      if (this.profileError != null) {
         this.fontRendererObj.drawString(this.profileError, var3 + 10, var4 + 38, -38037);
      }
   }

   private void b(int var1, int var2) {
      int var3 = this.left + 250 - 105;
      int var4 = this.top + 155 - 62;
      cn.kazer.client189.render.DrawRect.a(this.left, this.top, this.left + 500, this.top + 310, -1728053248);
      com.cheatbreaker.client.gui.render.RenderUtils.a((double)var3, (double)var4, (double)(var3 + 210), (double)(var4 + 124), 4.0, -266855399);
      cn.kazer.client189.render.DrawRect.a(var3, var4, var3 + 210, var4 + 1, -10889271);
      this.d().a(this.colorEditor.g().toUpperCase(Locale.ROOT), (float)(var3 + 10), (float)(var4 + 10), -855310);
      int var5 = ((Number)this.colorEditor.d()).intValue();
      String[] var6 = new String[]{"A", "R", "G", "B"};

      for (int var7 = 0; var7 < 4; var7++) {
         int var8 = c(var5, var7);
         int var9 = var4 + 28 + var7 * 18;
         this.d().a(var6[var7], (float)(var3 + 10), (float)(var9 + 5), -6645094);
         cn.kazer.client189.render.DrawRect.a(var3 + 28, var9 + 5, var3 + 174, var9 + 8, -11183530);
         int var10 = var3 + 28 + Math.round(var8 / 255.0F * 146.0F);
         cn.kazer.client189.render.DrawRect.a(var10 - 1, var9 + 2, var10 + 2, var9 + 11, -10889271);
         this.d().a(String.valueOf(var8), (float)(var3 + 181), (float)(var9 + 5), -855310);
      }

      this.a("DONE", var3 + 116, var4 + 101, 84, 16, var1, var2);
   }

   private static int c(int var0, int var1) {
      return var0 >> (3 - var1) * 8 & 0xFF;
   }

   private static int a(int var0, int var1, int var2) {
      int var3 = (3 - var1) * 8;
      return var0 & ~(255 << var3) | (var2 & 0xFF) << var3;
   }

   private void d(int var1, int var2) {
      int var3 = this.left + 250 - 105;
      int var4 = Math.max(0, Math.min(255, Math.round((var2 - (var3 + 28)) / 146.0F * 255.0F)));
      this.colorEditor.a(a(((Number)this.colorEditor.d()).intValue(), var1, var4));
      this.e(this.colorEditor);
   }

   private void a() {
      cn.kazer.client189.render.DrawRect.a(this.left, this.top, this.left + 500, this.top + 310, -1475209966);
      cn.kazer.client189.render.DrawRect.a(this.left, this.top + 40 - 1, this.left + 500, this.top + 40, 1885825639);
   }

   private void e(int var1, int var2) {
      cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
      com.cheatbreaker.client.gui.render.RenderUtils.a(this.logo, this.left + 9, this.top + 7, 25.0F, 25.0F);
      float var10002 = this.left + 37;
      float var10003 = this.top + 12;
      com.cheatbreaker.client.CheatBreakerClient.N().playRegular18px.a("KAZER CLIENT", var10002, var10003, -855310);
      this.a("MODS", this.left + 126, this.top + 9, 54, 22, this.page == com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.b.MODS, var1, var2);
      this.a("SETTINGS", this.left + 188, this.top + 9, 68, 22, this.page == com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.b.SETTINGS, var1, var2);
      boolean var3 = a(var1, var2, this.left + 500 - 29, this.top + 8, 20, 20);
      com.cheatbreaker.client.gui.render.RenderUtils.a(
         (double)(this.left + 500 - 29), (double)(this.top + 8), (double)(this.left + 500 - 9), (double)(this.top + 28), 3.0, var3 ? -680825237 : -1269474987
      );
      this.c().b("X", (float)(this.left + 500 - 19), (float)(this.top + 13), -855310);
   }

   private void a(String var1, int var2, int var3, int var4, int var5, boolean var6, int var7, int var8) {
      boolean var9 = a(var7, var8, var2, var3, var4, var5);
      com.cheatbreaker.client.gui.render.RenderUtils.a(
         (double)var2, (double)var3, (double)(var2 + var4), (double)(var3 + var5), 3.0, var6 ? -1453235103 : (var9 ? -1974447024 : 1681933376)
      );
      this.a(this.d(), var1, var2, var3, var4, var5, var6 ? -855310 : -6645094);
   }

   private void f(int var1, int var2) {
      this.d().a("PROFILES", (float)(this.left + 10), (float)(this.top + 49), -2434342);
      int var3 = this.top + 62;

      for (com.cheatbreaker.client.settings.Profile var6 : com.cheatbreaker.client.CheatBreakerClient.N().profiles == null ? Collections.<com.cheatbreaker.client.settings.Profile>emptyList() : com.cheatbreaker.client.CheatBreakerClient.N().profiles) {
         if (var3 + 20 >= this.top + 310 - 54) {
            break;
         }

         boolean var7 = var6 == com.cheatbreaker.client.CheatBreakerClient.N().activeProfile;
         boolean var8 = a(var1, var2, this.left + 7, var3, 94, 20);
         cn.kazer.client189.render.DrawRect.a(this.left + 7, var3, this.left + 108 - 7, var3 + 20, var7 ? -1974447280 : (var8 ? 1430604358 : 571807509));
         String var9 = var6.c();
         this.a(this.d(), var9, this.left + 7, var3, 94, 20, -855310);
         if (var6.b()) {
            this.d().b("X", (float)(this.left + 108 - 14), (float)(var3 + 7), a(var1, var2, this.left + 108 - 20, var3 + 4, 12, 12) ? -35981 : -6645094);
         }

         var3 += 22;
      }

      this.a("SAVE NEW PROFILE", this.left + 7, this.top + 310 - 47, 94, 16, var1, var2);
      this.a("EDIT HUD LAYOUT", this.left + 7, this.top + 310 - 25, 94, 17, var1, var2);
   }

   private void a(String var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      boolean var8 = a(var6, var7, var2, var3, var4, var5);
      com.cheatbreaker.client.gui.render.RenderUtils.a((double)var2, (double)var3, (double)(var2 + var4), (double)(var3 + var5), 2.0, var8 ? -1034067362 : 1950500675);
      com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer var9 = this.d().a(var1) <= var4 - 6 ? this.d() : this.b();
      this.a(var9, var1, var2, var3, var4, var5, -855310);
   }

   private com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer b() {
      return com.cheatbreaker.client.CheatBreakerClient.N().playRegular14px;
   }

   private com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer c() {
      return com.cheatbreaker.client.CheatBreakerClient.N().playBold18px;
   }

   private com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer d() {
      return com.cheatbreaker.client.CheatBreakerClient.N().robotoBold14px;
   }

   private void g(int var1, int var2) {
      int var3 = this.left + 108 + 9;
      this.a("ALL", var3, this.top + 40 + 7, 30, this.modCategory == com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.a.ALL);
      this.a("NEW", var3 + 34, this.top + 40 + 7, 36, this.modCategory == com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.a.NEW);
      this.a("HUD", var3 + 74, this.top + 40 + 7, 38, this.modCategory == com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.a.HUD);
      this.a("SERVER", var3 + 116, this.top + 40 + 7, 48, this.modCategory == com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.a.SERVER);
      this.a("MECHANIC", var3 + 168, this.top + 40 + 7, 60, this.modCategory == com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.a.MECHANIC);
      this.e();
      List var4 = this.k();
      this.moduleScroll = Math.min(this.moduleScroll, this.a(var4.size()));
      int var5 = this.left + 108 + 9;
      int var6 = this.top + 40 + 28;
      int var7 = this.top + 310 - 10;
      int var8 = this.top + 40 + 31 - this.moduleScroll;
      ScaledResolution var9 = new ScaledResolution(this.mc);
      int var10 = var9.getScaleFactor();
      cn.kazer.client189.render.GlStateBridge.a(3089);
      GL11.glScissor(var5 * var10, this.mc.displayHeight - var7 * var10, (this.left + 500 - 8 - var5) * var10, (var7 - var6) * var10);

      for (int var11 = 0; var11 < var4.size(); var11++) {
         int var12 = var11 % 3;
         int var13 = var11 / 3;
         int var14 = var5 + var12 * 125;
         int var15 = var8 + var13 * 115;
         this.a((com.cheatbreaker.client.module.Module)var4.get(var11), var14, var15, var1, var2);
      }

      cn.kazer.client189.render.GlStateBridge.b(3089);
      int var16 = this.a(var4.size());
      if (var16 > 0) {
         int var17 = var6 + 2;
         int var18 = var7 - var6 - 4;
         int var19 = Math.max(18, var18 * var18 / (var18 + var16));
         int var20 = var17 + (var18 - var19) * this.moduleScroll / var16;
         cn.kazer.client189.render.DrawRect.a(this.left + 500 - 6, var17, this.left + 500 - 3, var17 + var18, 1429486134);
         cn.kazer.client189.render.DrawRect.a(this.left + 500 - 6, var20, this.left + 500 - 3, var20 + var19, -866593080);
      }
   }

   private void a(String var1, int var2, int var3, int var4, boolean var5) {
      com.cheatbreaker.client.gui.render.RenderUtils.a((double)var2, (double)var3, (double)(var2 + var4), (double)(var3 + 14), 2.0, var5 ? -11694616 : -968202614);
      this.a(this.d(), var1, var2, var3, var4, 14, -855310);
   }

   private int a(int var1) {
      int var2 = (var1 + 2) / 3;
      int var3 = var2 == 0 ? 0 : var2 * 108 + (var2 - 1) * 7;
      short var4 = 228;
      return Math.max(0, var3 - var4);
   }

   private int b(int var1) {
      if (this.selectedModule instanceof cn.kazer.client189.animation.LegacyVisualsModule) {
         return Math.max(0, this.f().height - 196);
      } else {
         int var2 = (var1 + 1) / 2;
         return Math.max(0, var2 * 21 - 196);
      }
   }

   private void h(int var1, int var2) {
      int var3 = this.b(var1);
      if (var3 > 0) {
         int var4 = var2 + 56;
         int var5 = this.top + 310 - 8 - var4;
         int var6 = Math.max(18, var5 * var5 / (var5 + var3));
         int var7 = var4 + (var5 - var6) * this.settingsScroll / var3;
         cn.kazer.client189.render.DrawRect.a(this.left + 500 - 6, var4, this.left + 500 - 3, var4 + var5, 1429486134);
         cn.kazer.client189.render.DrawRect.a(this.left + 500 - 6, var7, this.left + 500 - 3, var7 + var6, -866593080);
      }
   }

   private boolean i(int var1, int var2) {
      if (var1 < this.left + 500 - 9 || var1 > this.left + 500 - 1) {
         return false;
      } else if (this.page == com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.b.SETTINGS && this.selectedModule == null) {
         int var10 = this.i();
         int var12 = this.g();
         int var14 = this.h();
         if (var10 > 0 && var2 >= var12 && var2 <= var12 + var14) {
            int var16 = Math.max(18, var14 * var14 / (var14 + var10));
            int var18 = var12 + (var14 - var16) * this.globalScroll / var10;
            this.scrollbarGrabOffset = var2 >= var18 && var2 <= var18 + var16 ? var2 - var18 : var16 / 2;
            this.draggingGlobalScrollbar = true;
            this.e(var2);
            return true;
         } else {
            return false;
         }
      } else if (this.page == com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.b.MODS && this.selectedModule == null) {
         int var9 = this.a(this.k().size());
         if (var9 <= 0) {
            return false;
         } else {
            int var11 = this.top + 40 + 30;
            int var13 = this.top + 310 - 12 - var11;
            if (var2 >= var11 && var2 <= var11 + var13) {
               int var15 = Math.max(18, var13 * var13 / (var13 + var9));
               int var17 = var11 + (var13 - var15) * this.moduleScroll / var9;
               this.scrollbarGrabOffset = var2 >= var17 && var2 <= var17 + var15 ? var2 - var17 : var15 / 2;
               this.draggingModuleScrollbar = true;
               this.c(var2);
               return true;
            } else {
               return false;
            }
         }
      } else if (this.selectedModule != null) {
         int var3 = this.b(this.c(this.selectedModule).size());
         if (var3 <= 0) {
            return false;
         } else {
            int var4 = this.top + 40 + 8;
            int var5 = var4 + 56;
            int var6 = this.top + 310 - 8 - var5;
            if (var2 >= var5 && var2 <= var5 + var6) {
               int var7 = Math.max(18, var6 * var6 / (var6 + var3));
               int var8 = var5 + (var6 - var7) * this.settingsScroll / var3;
               this.scrollbarGrabOffset = var2 >= var8 && var2 <= var8 + var7 ? var2 - var8 : var7 / 2;
               this.draggingSettingsScrollbar = true;
               this.d(var2);
               return true;
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   private void c(int var1) {
      int var2 = this.a(this.k().size());
      if (var2 > 0) {
         int var3 = this.top + 40 + 30;
         int var4 = this.top + 310 - 12 - var3;
         int var5 = Math.max(18, var4 * var4 / (var4 + var2));
         int var6 = var4 - var5;
         int var7 = Math.max(var3, Math.min(var3 + var6, var1 - this.scrollbarGrabOffset));
         this.moduleScroll = var6 <= 0 ? 0 : Math.round((float)((var7 - var3) * var2) / var6);
      }
   }

   private void d(int var1) {
      if (this.selectedModule != null) {
         int var2 = this.b(this.c(this.selectedModule).size());
         if (var2 > 0) {
            int var3 = this.top + 40 + 64;
            int var4 = this.top + 310 - 8 - var3;
            int var5 = Math.max(18, var4 * var4 / (var4 + var2));
            int var6 = var4 - var5;
            int var7 = Math.max(var3, Math.min(var3 + var6, var1 - this.scrollbarGrabOffset));
            this.settingsScroll = var6 <= 0 ? 0 : Math.round((float)((var7 - var3) * var2) / var6);
         }
      }
   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      int var1 = Mouse.getEventDWheel();
      if (var1 != 0) {
         int var2 = Mouse.getEventX() * this.width / this.mc.displayWidth;
         int var3 = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
         int var4 = Math.max(1, Math.round(24.0F * ((Number)com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.scrollingSpeed.d()).floatValue() / 100.0F));
         if (this.page == com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.b.SETTINGS && this.selectedModule == null) {
            if (a(var2, var3, this.left + 108, this.g(), 392, this.h())) {
               this.l();
               this.n();
               this.globalScroll = Math.max(0, Math.min(this.i(), this.globalScroll + (var1 > 0 ? -var4 : var4)));
            }
         } else if (this.selectedModule != null) {
            if (a(var2, var3, this.left + 108, this.top + 40, 392, 270)) {
               int var6 = this.b(this.c(this.selectedModule).size());
               this.settingsScroll = Math.max(0, Math.min(var6, this.settingsScroll + (var1 > 0 ? -var4 : var4)));
            }
         } else if (this.page == com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.b.MODS) {
            if (a(var2, var3, this.left + 108, this.top + 40, 392, 270)) {
               int var5 = var1 > 0 ? -1 : 1;
               this.moduleScroll = Math.max(0, Math.min(this.a(this.k().size()), this.moduleScroll + var5 * var4));
            }
         }
      }
   }

   private void e() {
      int var1 = this.searchX;
      int var2 = this.searchY;
      com.cheatbreaker.client.gui.render.RenderUtils.a(
         (double)(var1 - 1), (double)(var2 - 1), (double)(var1 + this.searchW + 1), (double)(var2 + this.searchH + 1), 3.0, -1284804756
      );
      com.cheatbreaker.client.gui.render.RenderUtils.a((double)var1, (double)var2, (double)(var1 + this.searchW), (double)(var2 + this.searchH), 3.0, -1187760332);
      if (this.search.getText().isEmpty()) {
         cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, this.search.isFocused() ? 1.0F : 0.72F);
         com.cheatbreaker.client.gui.render.IconRenderer.a(com.cheatbreaker.client.gui.render.IconRenderer.a.SEARCH, var1 + 5, var2 + 1, 12.0F, -855310);
         cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
      } else {
         String var3 = this.search.getText() + (this.search.isFocused() && (System.currentTimeMillis() / 500L & 1L) == 0L ? "_" : "");
         com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer var4 = this.b();

         while (var4.b(var3) > this.searchW - 14 && var3.length() > 1) {
            var3 = var3.substring(1);
         }

         var4.a(var3, var1 + 7, var2, this.searchW - 14, this.searchH, -855310);
      }

      if (this.search.isFocused()) {
         WindowsImeSupport.a(var1 + this.searchW / 2 + this.b().b(this.search.getText()) / 2, var2 + 2, 11);
      }
   }

   private void a(com.cheatbreaker.client.module.Module var1, int var2, int var3, int var4, int var5) {
      boolean var6 = var1.g() && (var1.r() == null || var1.q());
      boolean var7 = a(var4, var5, var2, var3, 118, 108);
      com.cheatbreaker.client.gui.render.RenderUtils.a((double)var2, (double)var3, (double)(var2 + 118), (double)(var3 + 108), 5.0, -1334281352);
      com.cheatbreaker.client.gui.render.RenderUtils.a(
         (double)(var2 + 1), (double)(var3 + 1), (double)(var2 + 118 - 1), (double)(var3 + 108 - 1), 4.0, var7 ? 1982015011 : 1578177297
      );
      cn.kazer.client189.render.DrawRect.a(var2 + 1, var3 + 108 - 39, var2 + 118 - 1, var3 + 108 - 38, -1334083973);
      cn.kazer.client189.render.DrawRect.a(var2 + 1, var3 + 108 - 38, var2 + 118 - 1, var3 + 108 - 19, -2142219696);
      cn.kazer.client189.render.DrawRect.a(var2 + 118 - 21, var3 + 108 - 38, var2 + 118 - 20, var3 + 108 - 19, -1433891960);
      com.cheatbreaker.client.gui.render.RenderUtils.a(
         (double)(var2 + 1), (double)(var3 + 108 - 19), (double)(var2 + 118 - 1), (double)(var3 + 108 - 1), 4.0, var6 ? -14309019 : -4776117
      );
      cn.kazer.client189.render.DrawRect.a(var2 + 1, var3 + 108 - 19, var2 + 118 - 1, var3 + 108 - 5, var6 ? -14309019 : -4776117);
      this.a(var1, var2 + 59, var3 + 31);
      boolean var8 = com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.favoriteModules.a(var1.h());
      if (var8 || var7) {
         com.cheatbreaker.client.gui.render.IconRenderer.a(
            com.cheatbreaker.client.gui.render.IconRenderer.a.STAR, var2 + 5, var3 + 5, 12.0F, var8 ? -11413 : (a(var4, var5, var2 + 3, var3 + 3, 16, 16) ? -855310 : -6972779)
         );
      }

      this.b().b(var1.h(), var2 + 59.0F, (float)(var3 + 55), -6645094);
      this.a(this.d(), "OPTIONS", var2 + 1, var3 + 108 - 38, 96, 19, -855310);
      cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
      com.cheatbreaker.client.gui.render.RenderUtils.a(new ResourceLocation("client/icons/cog-64.png"), var2 + 118 - 17, var3 + 108 - 35, 12.0F, 12.0F);
      this.a(this.d(), var6 ? "ENABLED" : "DISABLED", var2 + 1, var3 + 108 - 19, 116, 18, -855310);
   }

   private void a(com.cheatbreaker.client.module.Module var1, int var2, int var3) {
      if (var1 instanceof com.cheatbreaker.client.module.modules.ChatModule) {
         com.cheatbreaker.client.gui.render.IconRenderer.a(com.cheatbreaker.client.gui.render.IconRenderer.a.CHAT, var2 - 12, var3 - 12, 24.0F, -855310);
      } else if (var1 instanceof cn.kazer.client189.animation.LegacyVisualsModule) {
         com.cheatbreaker.client.gui.render.IconRenderer.a(com.cheatbreaker.client.gui.render.IconRenderer.a.VISUALS, var2 - 16, var3 - 16, 32.0F, -855310);
      } else {
         cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
         ResourceLocation var4 = this.a(var1);
         if (var4 != null) {
            com.cheatbreaker.client.gui.render.RenderUtils.a(var4, var2 - 16, var3 - 16, 32.0F, 32.0F);
         }
      }
   }

   private ResourceLocation a(com.cheatbreaker.client.module.Module var1) {
      String var2;
      if (var1 instanceof com.cheatbreaker.client.module.modules.options.ArmourStatusModule) {
         var2 = "armor_status";
      } else if (var1 instanceof com.cheatbreaker.client.module.modules.togglesprint.ToggleSprintModule) {
         var2 = "toggle_sprint";
      } else if (var1 == com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.potionStatus) {
         var2 = "potion_effects";
      } else if (var1 instanceof com.cheatbreaker.client.module.modules.keystrokes.KeyStrokesModule) {
         var2 = "key_strokes";
      } else if (var1 == com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.fpsModule) {
         var2 = "fps";
      } else if (var1 == com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.cpsModule) {
         var2 = "cps";
      } else if (var1 instanceof com.cheatbreaker.client.module.modules.nametag.NametagsModule) {
         var2 = "nametags";
      } else if (var1 instanceof com.cheatbreaker.client.module.modules.hud.BossBarModule) {
         var2 = "boss_bar";
      } else if (var1 instanceof com.cheatbreaker.client.module.modules.shader.MotionBlurModule) {
         var2 = "motion_blur";
      } else if (var1 == com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.scoreboard) {
         var2 = "scoreboard";
      } else {
         if (!(var1 instanceof com.cheatbreaker.client.module.modules.ChatModule)) {
            return null;
         }

         var2 = "chat";
      }

      return new ResourceLocation("client/icons/newui/" + var2 + ".png");
   }

   private void j(int var1, int var2) {
      int var3 = this.left + 108 + 10;
      int var4 = this.top + 40 + 8;
      com.cheatbreaker.client.gui.render.RenderUtils.a(
         (double)var3, (double)var4, (double)(var3 + 18), (double)(var4 + 18), 9.0, a(var1, var2, var3, var4, 18, 18) ? -1335596956 : -2143008700
      );
      this.c().b("<", (float)(var3 + 9), (float)(var4 + 6), -855310);
      this.c().a(this.selectedModule.h().toUpperCase(Locale.ROOT), (float)(var3 + 26), (float)(var4 + 7), -855310);
      this.a("RESET", this.left + 500 - 75, var4, 63, 18, var1, var2);
      this.d().a(this.d(this.selectedModule), (float)var3, (float)(var4 + 27), -6645094);
      cn.kazer.client189.render.DrawRect.a(var3, var4 + 37, this.left + 500 - 10, var4 + 38, 1885825639);
      boolean var5 = this.selectedModule instanceof cn.kazer.client189.animation.LegacyVisualsModule;
      if (!var5) {
         this.d().a("GENERAL", (float)(var3 + 2), (float)(var4 + 45), -2631721);
      }

      List var6 = this.c(this.selectedModule);
      int var7 = this.top + 310 - 8 - (var4 + 43);
      int var8 = this.b(var6.size());
      this.settingsScroll = Math.min(this.settingsScroll, var8);
      int var9 = var4 + 58 - this.settingsScroll;
      short var10 = 181;
      ScaledResolution var11 = new ScaledResolution(this.mc);
      int var12 = var11.getScaleFactor();
      cn.kazer.client189.render.GlStateBridge.a(3089);
      GL11.glScissor(var3 * var12, this.mc.displayHeight - (this.top + 310 - 8) * var12, (this.left + 500 - 10 - var3) * var12, var7 * var12);
      if (var5) {
         this.a(var3, var9, var1, var2);
      } else {
         for (int var13 = 0; var13 < var6.size(); var13++) {
            int var14 = var13 % 2;
            int var15 = var13 / 2;
            int var16 = var3 + var14 * (var10 + 8);
            int var17 = var9 + var15 * 21;
            this.a((com.cheatbreaker.client.settings.Setting)var6.get(var13), var16, var17, var10, var1, var2);
         }
      }

      cn.kazer.client189.render.GlStateBridge.b(3089);
      this.h(var6.size(), var4);
   }

   private cn.kazer.client189.animation.LegacyVisualsLayout f() {
      cn.kazer.client189.animation.LegacyVisualsModule var1 = (cn.kazer.client189.animation.LegacyVisualsModule)this.selectedModule;
      return new cn.kazer.client189.animation.LegacyVisualsLayout(Boolean.TRUE.equals(var1.animations.d()), Boolean.TRUE.equals(var1.items.d()));
   }

   private com.cheatbreaker.client.settings.Setting a(String var1) {
      for (com.cheatbreaker.client.settings.Setting var3 : this.selectedModule.m()) {
         if (var1.equals(var3.f())) {
            return var3;
         }
      }

      throw new IllegalStateException("Unknown visual preference: " + var1);
   }

   private void a(int var1, int var2, int var3, int var4) {
      for (cn.kazer.client189.animation.LegacyVisualsLayout.a var6 : this.f().rows) {
         int var7 = var1 + var6.column * 189;
         int var8 = var2 + var6.y;
         int var9 = var6.wide ? 370 : 181;
         if (var6.heading) {
            this.d().a(var6.label, (float)(var7 + 4), (float)(var8 + 4), -2631721);
         } else {
            com.cheatbreaker.client.settings.Setting var10 = this.a(var6.label);
            if (var10.l() != com.cheatbreaker.client.settings.Setting.SettingType.STRING_ARRAY) {
               this.a(var10, var7, var8, var9, var3, var4);
            } else {
               cn.kazer.client189.render.DrawRect.a(var7, var8, var7 + var9, var8 + 20, 373244479);
               boolean var11 = "1.7".equals(var10.d());
               int var12 = var7 + 4;
               int var13 = var8 + 5;
               int var14 = var12 + (var11 ? 10 : 1);
               com.cheatbreaker.client.gui.render.RenderUtils.a((double)var12, (double)var13, (double)(var12 + 30), (double)(var13 + 11), 2.0, -8880775);
               com.cheatbreaker.client.gui.render.RenderUtils.a((double)(var12 + 1), (double)(var13 + 1), (double)(var12 + 29), (double)(var13 + 10), 2.0, -12235705);
               com.cheatbreaker.client.gui.render.RenderUtils.a(
                  (double)var14, (double)(var13 + 1), (double)(var14 + 19), (double)(var13 + 10), 2.0, var11 ? -14309019 : -11376519
               );
               this.a(this.d(), String.valueOf(var10.d()), var14, var13 + 1, 19, 9, -855310);
               this.b().a(this.a(var10.g(), var9 - 42), (float)(var7 + 38), (float)(var8 + 6), -6645094);
            }
         }
      }
   }

   private void k(int var1, int var2) {
      int var3 = this.left + 108 + 12;
      int var4 = this.top + 40 + 10;
      this.c().a("KAZER CLIENT SETTINGS", (float)var3, (float)(var4 + 3), -855310);
      this.d().b("NEW", (double)(var3 + 276), (double)(var4 + 3), -1456536);
      this.d().b("EXISTING", (double)(var3 + 307), (double)(var4 + 3), -10956862);
      cn.kazer.client189.render.DrawRect.a(var3, var4 + 14, this.left + 500 - 12, var4 + 15, 1885825639);
      int var5 = var3 + 4;
      byte var6 = 116;
      byte var7 = 6;
      this.a("GENERAL", com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.c.GENERAL, var5, var4 + 21, var6, var1, var2);
      this.a("PERFORMANCE", com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.c.PERFORMANCE, var5 + var6 + var7, var4 + 21, var6, var1, var2);
      this.a("CONTROLS", com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.c.CONTROLS, var5 + (var6 + var7) * 2, var4 + 21, var6, var1, var2);
      this.globalScroll = Math.min(this.globalScroll, this.i());
      int var8 = 0;
      ScaledResolution var9 = new ScaledResolution(this.mc);
      int var10 = var9.getScaleFactor();
      cn.kazer.client189.render.GlStateBridge.a(3089);
      GL11.glScissor((var3 - 7) * var10, this.mc.displayHeight - (this.top + 310 - 8) * var10, (this.left + 500 - var3 + 2) * var10, this.h() * var10);

      for (com.cheatbreaker.client.settings.Setting var12 : this.j()) {
         int var13 = var8 % 2;
         int var14 = this.g() + var8 / 2 * 24 - this.globalScroll;
         this.a(var12, var3 + var13 * 188, var14, 180, var1, var2);
         cn.kazer.client189.render.DrawRect.a(var3 + var13 * 188 - 5, var14 + 8, var3 + var13 * 188 - 2, var14 + 11, this.a(var12) ? -1456536 : -10956862);
         var8++;
      }

      cn.kazer.client189.render.GlStateBridge.b(3089);
      int var15 = this.i();
      if (var15 > 0) {
         int var16 = this.h();
         int var17 = Math.max(18, var16 * var16 / (var16 + var15));
         int var18 = this.g() + (var16 - var17) * this.globalScroll / var15;
         cn.kazer.client189.render.DrawRect.a(this.left + 500 - 6, this.g(), this.left + 500 - 3, this.g() + var16, 1429486134);
         cn.kazer.client189.render.DrawRect.a(this.left + 500 - 6, var18, this.left + 500 - 3, var18 + var17, -866593080);
      }
   }

   private int g() {
      return this.top + 40 + 60;
   }

   private int h() {
      return this.top + 310 - 8 - this.g();
   }

   private int i() {
      return Math.max(0, (this.j().size() + 1) / 2 * 24 - this.h());
   }

   private void e(int var1) {
      int var2 = this.i();
      int var3 = this.h();
      int var4 = Math.max(18, var3 * var3 / (var3 + var2));
      int var5 = var3 - var4;
      this.globalScroll = var5 <= 0 ? 0 : Math.round((float)(Math.max(0, Math.min(var5, var1 - this.scrollbarGrabOffset - this.g())) * var2) / var5);
   }

   private boolean a(com.cheatbreaker.client.settings.Setting var1) {
      com.cheatbreaker.client.settings.GlobalSettings var2 = com.cheatbreaker.client.CheatBreakerClient.N().globalSettings;
      return var1 == var2.particleAmount
         || var1 == var2.limitUnfocusedFps
         || var1 == var2.maxUnfocusedFps
         || var1 == var2.maxMainMenuFps
         || var1 == var2.entityDistance
         || var1 == var2.tileEntityDistance
         || var1 == var2.keepInventoryCentered
         || var1 == var2.disableHotbarScrolling
         || var1 == var2.showAchievements
         || var1 == var2.showCrosshairThirdPerson
         || var1 == var2.scrollingSpeed
         || var1 == com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.nametagModule.showNametagIcon;
   }

   private void a(String var1, com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.c var2, int var3, int var4, int var5, int var6, int var7) {
      boolean var8 = this.settingsCategory == var2;
      boolean var9 = a(var6, var7, var3, var4, var5, 20);
      com.cheatbreaker.client.gui.render.RenderUtils.a(
         (double)var3, (double)var4, (double)(var3 + var5), (double)(var4 + 20), 3.0, var8 ? -582119960 : (var9 ? -2008855481 : 1429486391)
      );
      this.a(this.d(), var1, var3, var4, var5, 20, var8 ? -855310 : -6645094);
   }

   private List<com.cheatbreaker.client.settings.Setting> j() {
      ArrayList var1 = new ArrayList();
      com.cheatbreaker.client.settings.GlobalSettings var2 = com.cheatbreaker.client.CheatBreakerClient.N().globalSettings;

      for (com.cheatbreaker.client.settings.Setting var4 : com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.settingsList) {
         if (!"label".equalsIgnoreCase(var4.f()) && var4.j() && var4 != com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.showChatBackground) {
            boolean var5 = var4 == com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.enableFpsBoost
               || var4 == com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.slowChunkLoading
               || var4 == com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.fullBright
               || var4 == com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.enchantmentGlint
               || var4 == com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.clearGlass
               || var4 == com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.redString
               || var4 == var2.particleAmount
               || var4 == var2.limitUnfocusedFps
               || var4 == var2.maxUnfocusedFps
               || var4 == var2.maxMainMenuFps
               || var4 == var2.entityDistance
               || var4 == var2.tileEntityDistance;
            boolean var6 = var4 == com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.snapModules
               || var4 == com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.showChatBackground
               || var4 == com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.showPotionInfo
               || var4 == com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.customCrosshair
               || var4 == com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.crosshairOutline
               || var4 == com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.crosshairColor
               || var4 == com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.crosshairThickness
               || var4 == com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.crosshairSize
               || var4 == com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.crosshairGap
               || var4 == var2.disableHotbarScrolling;
            if (this.settingsCategory == com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.c.PERFORMANCE && var5
               || this.settingsCategory == com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.c.CONTROLS && var6
               || this.settingsCategory == com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.c.GENERAL && !var5 && !var6) {
               var1.add(var4);
            }
         }
      }

      if (this.settingsCategory == com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.c.GENERAL) {
         var1.add(com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.nametagModule.showBadgeInTab);
         var1.add(com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.nametagModule.showNametagIcon);
      }

      if (this.settingsCategory == com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.c.CONTROLS) {
         if (this.referenceBindings.isEmpty()) {
            this.referenceBindings.put(new com.cheatbreaker.client.settings.Setting("Mod Menu").a(0, false), var2.openMenu);
            this.referenceBindings.put(new com.cheatbreaker.client.settings.Setting("Freelook").a(0, false), var2.dragLook);
            this.referenceBindings.put(new com.cheatbreaker.client.settings.Setting("Zoom").a(0, false), cn.kazer.client189.render.ClientBridge.c());
         }

         var1.addAll(this.referenceBindings.keySet());
      }

      return var1;
   }

   private void a(com.cheatbreaker.client.settings.Setting var1, int var2, int var3, int var4, int var5, int var6) {
      cn.kazer.client189.render.DrawRect.a(var2, var3, var2 + var4, var3 + 20, 373244479);
      String var7 = var1.g();
      if (this.referenceBindings.containsKey(var1)) {
         this.b().b(var7, (double)(var2 + 4), (double)(var3 + 6), -6645094);
         this.a(
            this.recordingBinding == var1 ? "PRESS KEY" : GameSettings.getKeyDisplayString(this.referenceBindings.get(var1).getKeyCode()),
            var2 + var4 / 2,
            var3 + 2,
            var4 / 2 - 3,
            16,
            var5,
            var6
         );
      } else if ("label".equalsIgnoreCase(var1.f())) {
         this.d().a(String.valueOf(var1.d()).toUpperCase(Locale.ROOT), (float)(var2 + 3), (float)(var3 + 7), -3092272);
      } else {
         if (var1.l() == com.cheatbreaker.client.settings.Setting.SettingType.BOOLEAN) {
            this.a((Boolean)var1.d(), var2 + 4, var3 + 5);
            this.b().a(this.a(var7, var4 - 42), (float)(var2 + 38), (float)(var3 + 6), -6645094);
         } else if (var1.l() == com.cheatbreaker.client.settings.Setting.SettingType.STRING_ARRAY) {
            int var8 = var2 + var4 / 2;
            this.b().a(this.a(var7, var8 - var2 - 8), (float)(var2 + 4), (float)(var3 + 6), -6645094);
            this.a(var8 + 5, var3 + 10, false);
            this.a(var2 + var4 - 6, var3 + 10, true);
            this.a(this.b(), this.a(String.valueOf(var1.d()), var4 / 2 - 26), var8 + 14, var3, var4 / 2 - 28, 20, -6645094);
         } else if (var1.l() == com.cheatbreaker.client.settings.Setting.SettingType.STRING) {
            int var15 = var2 + var4 / 2;
            this.b().a(this.a(var7, var15 - var2 - 8), (float)(var2 + 4), (float)(var3 + 6), -6645094);
            boolean var9 = var1 == this.editingStringSetting;
            String var10 = var9 ? this.editingStringText : String.valueOf(var1.d());
            this.a(var15, var3 + 2, var4 / 2 - 3, 16, var9, a(var5, var6, var15, var3, var4 / 2, 20));
            if (var9 && (System.currentTimeMillis() / 500L & 1L) == 0L) {
               var10 = var10 + "|";
            }

            var10 = this.a(var10, var4 / 2 - 10);
            this.a(this.b(), var10, var15 + 3, var3 + 2, var4 / 2 - 9, 16, -855310);
         } else if (var1.l() == com.cheatbreaker.client.settings.Setting.SettingType.FLOAT
            || var1.l() == com.cheatbreaker.client.settings.Setting.SettingType.DOUBLE
            || var1.l() == com.cheatbreaker.client.settings.Setting.SettingType.INTEGER) {
            if (var1.f().toLowerCase(Locale.ROOT).contains("color")) {
               this.b().a(this.a(var7, var4 - 32), (float)(var2 + 4), (float)(var3 + 6), -6645094);
               com.cheatbreaker.client.gui.render.RenderUtils.a((double)(var2 + var4 - 19), (double)(var3 + 4), (double)(var2 + var4 - 5), (double)(var3 + 16), 2.0, -8946824);
               com.cheatbreaker.client.gui.render.RenderUtils.a((double)(var2 + var4 - 18), (double)(var3 + 5), (double)(var2 + var4 - 6), (double)(var3 + 15), 1.0, var1.a());
            } else {
               int var16 = this.a(var1, var2, var4);
               int var17 = this.b(var1, var2, var4);
               this.b().a(this.a(var7, var16 - var2 - 8), (float)(var2 + 4), (float)(var3 + 6), -6645094);
               Number var19 = var1.b();
               Number var11 = var1.c();
               if (var19 != null && var11 != null && var11.doubleValue() > var19.doubleValue()) {
                  double var12 = (((Number)var1.d()).doubleValue() - var19.doubleValue()) / (var11.doubleValue() - var19.doubleValue());
                  var12 = Math.max(0.0, Math.min(1.0, var12));
                  com.cheatbreaker.client.gui.render.RenderUtils.a((double)var16, (double)(var3 + 9), (double)(var16 + var17), (double)(var3 + 12), 1.5, -12367033);
                  int var14 = var16 + (int)Math.round(var17 * var12);
                  if (var14 > var16) {
                     com.cheatbreaker.client.gui.render.RenderUtils.a((double)var16, (double)(var3 + 9), (double)var14, (double)(var3 + 12), 1.5, -13201787);
                  }

                  com.cheatbreaker.client.gui.render.RenderUtils.a((double)(var14 - 3), (double)(var3 + 6), (double)(var14 + 3), (double)(var3 + 15), 2.0, -10956862);
               }

               boolean var21 = var1 == this.editingNumericSetting;
               String var13 = var21 ? this.editingNumericText : this.b(var1);
               this.a(var2 + var4 - 39, var3 + 2, 36, 16, var21, a(var5, var6, var2 + var4 - 42, var3, 42, 20));
               if (var21 && (System.currentTimeMillis() / 500L & 1L) == 0L) {
                  var13 = var13 + "|";
               }

               var13 = this.a(var13, 32);
               this.a(this.b(), var13, var2 + var4 - 39, var3 + 2, 36, 16, -855310);
            }
         }
      }
   }

   private String a(String var1, int var2) {
      if (var1 == null) {
         return "";
      } else if (var2 <= 0) {
         return "";
      } else if (this.b().a(var1) <= var2) {
         return var1;
      } else {
         String var3 = "...";
         int var4 = this.b().a(var3);
         if (var4 >= var2) {
            return "";
         } else {
            String var5 = var1;

            while (var5.length() > 0 && this.b().a(var5) + var4 > var2) {
               var5 = var5.substring(0, var5.length() - 1);
            }

            return var5 + var3;
         }
      }
   }

   private int a(com.cheatbreaker.client.settings.Setting var1, int var2, int var3) {
      return var2 + Math.max(72, var3 / 2);
   }

   private int b(com.cheatbreaker.client.settings.Setting var1, int var2, int var3) {
      int var4 = this.a(var1, var2, var3);
      return Math.max(24, var2 + var3 - 43 - var4);
   }

   private String b(com.cheatbreaker.client.settings.Setting var1) {
      return var1.l() == com.cheatbreaker.client.settings.Setting.SettingType.INTEGER
         ? String.valueOf(var1.d())
         : String.format(Locale.ROOT, "%.2f", ((Number)var1.d()).doubleValue()).replaceAll("0+$", "").replaceAll("\\.$", "");
   }

   private void a(boolean var1, int var2, int var3) {
      com.cheatbreaker.client.gui.render.RenderUtils.a((double)var2, (double)var3, (double)(var2 + 30), (double)(var3 + 11), 2.0, -8880775);
      com.cheatbreaker.client.gui.render.RenderUtils.a((double)(var2 + 1), (double)(var3 + 1), (double)(var2 + 29), (double)(var3 + 10), 2.0, -12235705);
      int var4 = var2 + (var1 ? 10 : 1);
      com.cheatbreaker.client.gui.render.RenderUtils.a((double)var4, (double)(var3 + 1), (double)(var4 + 19), (double)(var3 + 10), 2.0, var1 ? -14309019 : -4776117);
      this.a(this.d(), var1 ? "ON" : "OFF", var4, var3 + 1, 19, 9, -855310);
   }

   private void a(int var1, int var2, int var3, int var4, boolean var5, boolean var6) {
      com.cheatbreaker.client.gui.render.RenderUtils.a(
         (double)var1, (double)var2, (double)(var1 + var3), (double)(var2 + var4), 2.0, var5 ? -11300984 : (var6 ? -1721405336 : 1716081229)
      );
      com.cheatbreaker.client.gui.render.RenderUtils.a(
         (double)(var1 + 1), (double)(var2 + 1), (double)(var1 + var3 - 1), (double)(var2 + var4 - 1), 1.0, var5 ? -282841301 : -1525014242
      );
   }

   private void a(int var1, int var2, boolean var3) {
      for (int var4 = 0; var4 < 4; var4++) {
         int var5 = var3 ? var4 : -var4;
         cn.kazer.client189.render.DrawRect.a(var1 + var5, var2 - 4 + var4, var1 + var5 + 2, var2 - 2 + var4, -13064267);
         cn.kazer.client189.render.DrawRect.a(var1 + var5, var2 + 2 - var4, var1 + var5 + 2, var2 + 4 - var4, -13064267);
      }
   }

   private void a(com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer var1, String var2, int var3, int var4, int var5, int var6, int var7) {
      var1.a(var2, var3, var4, var5, var6, var7);
   }

   private List<com.cheatbreaker.client.module.Module> k() {
      String var1 = this.search == null ? "" : this.search.getText().trim().toLowerCase(Locale.ROOT);
      ArrayList<com.cheatbreaker.client.module.Module> var2 = new ArrayList<>();

      for (com.cheatbreaker.client.module.Module var4 : com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.modules) {
         if (this.b(var4) && (var1.isEmpty() || var4.h().toLowerCase(Locale.ROOT).contains(var1))) {
            var2.add(var4);
         }
      }

      var2.sort(
         (var0, var1x) -> Boolean.compare(
            com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.favoriteModules.a(var1x.h()), com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.favoriteModules.a(var0.h())
         )
      );
      return var2;
   }

   private boolean b(com.cheatbreaker.client.module.Module var1) {
      switch (this.modCategory) {
         case NEW:
            return var1 instanceof com.cheatbreaker.client.module.modules.nametag.NametagsModule
               || var1 instanceof com.cheatbreaker.client.module.modules.shader.MotionBlurModule
               || var1 instanceof com.cheatbreaker.client.module.modules.ChatModule;
         case SERVER:
            return var1 instanceof com.cheatbreaker.client.module.modules.hud.BossBarModule || var1 == com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.scoreboard;
         case MECHANIC:
            return var1 instanceof com.cheatbreaker.client.module.modules.togglesprint.ToggleSprintModule
               || var1 instanceof com.cheatbreaker.client.module.modules.shader.MotionBlurModule
               || var1 instanceof com.cheatbreaker.client.module.modules.ChatModule
               || var1 instanceof cn.kazer.client189.animation.LegacyVisualsModule;
         case HUD:
            return !(var1 instanceof com.cheatbreaker.client.module.modules.togglesprint.ToggleSprintModule)
               && !(var1 instanceof com.cheatbreaker.client.module.modules.shader.MotionBlurModule)
               && !(var1 instanceof cn.kazer.client189.animation.LegacyVisualsModule);
         default:
            return true;
      }
   }

   private List<com.cheatbreaker.client.settings.Setting> c(com.cheatbreaker.client.module.Module var1) {
      ArrayList var2 = new ArrayList();

      for (com.cheatbreaker.client.settings.Setting var4 : var1.m()) {
         if (var4.j() && a(var1, var4)) {
            var2.add(var4);
         }
      }

      return var2;
   }

   static boolean a(com.cheatbreaker.client.module.Module var0, com.cheatbreaker.client.settings.Setting var1) {
      String var2 = var1.f();
      if (var0 instanceof cn.kazer.client189.animation.LegacyVisualsModule && "Scale".equals(var2)) {
         return false;
      } else if ("label".equalsIgnoreCase(var2) || "Scale".equalsIgnoreCase(var2)) {
         return true;
      } else if (var0 instanceof com.cheatbreaker.client.module.modules.options.ArmourStatusModule) {
         return !a(
            var2,
            "Protection",
            "Armor Bars",
            "Show Helmet",
            "Show Chestplate",
            "Show Leggings",
            "Show Boots",
            "Move Armor Pieces Individually",
            "Vanilla Mode",
            "Text Shadow",
            "Show Background"
         );
      } else if (var0 == com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.potionStatus) {
         return a(var2, "Show While Typing", "Show Effect Name", "Blink", "Blink Duration", "Name Color", "Duration Color");
      } else if (var0 instanceof com.cheatbreaker.client.module.modules.keystrokes.KeyStrokesModule) {
         return !a(var2, "Mode", "Text Shadow", "Border", "Animate", "Border Thickness");
      } else if (var0 instanceof com.cheatbreaker.client.module.modules.nametag.NametagsModule) {
         return !a(var2, "Display Toggle Nametags Message", "Replace Own Nametag Color");
      } else if (var0 == com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.scoreboard) {
         return !a(var2, "Border Thickness", "Border", "Display Toggle Message");
      } else {
         return var0 instanceof com.cheatbreaker.client.module.modules.hud.BossBarModule ? !a(var2, "Use Custom Boss Bar", "Use Minecraft GUI Scale") : true;
      }
   }

   private static boolean a(String var0, String... var1) {
      for (String var5 : var1) {
         if (var5.equalsIgnoreCase(var0)) {
            return true;
         }
      }

      return false;
   }

   private String d(com.cheatbreaker.client.module.Module var1) {
      if (var1 instanceof com.cheatbreaker.client.module.modules.options.ArmourStatusModule) {
         return "Displays equipped armor and the held item on the HUD.";
      } else if (var1 instanceof com.cheatbreaker.client.module.modules.hud.BossBarModule) {
         return "Hide, move or customize the Minecraft boss bar.";
      } else if (var1 instanceof com.cheatbreaker.client.module.modules.nametag.NametagsModule) {
         return "Customize player nametags.";
      } else if (var1 instanceof com.cheatbreaker.client.module.modules.shader.MotionBlurModule) {
         return "Blur your surroundings while moving.";
      } else if (var1 instanceof cn.kazer.client189.animation.LegacyVisualsModule) {
         return "Sk1er OldAnimations: 1.7 item positions, blocking and consuming animations.";
      } else if (var1 instanceof com.cheatbreaker.client.module.modules.togglesprint.ToggleSprintModule) {
         return "Make sprinting and sneaking toggleable.";
      } else if (var1 instanceof com.cheatbreaker.client.module.modules.ChatModule) {
         return "Customize chat visibility and copy notifications.";
      } else if (var1 == com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.potionStatus) {
         return "Displays active potion effects on the HUD.";
      } else {
         return var1 == com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.scoreboard
            ? "Hide, move or customize the scoreboard."
            : "Configure " + var1.h() + " and its HUD appearance.";
      }
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      super.mouseClicked(var1, var2, var3);
      if (this.colorEditor != null) {
         int var8 = this.left + 250 - 105;
         int var9 = this.top + 155 - 62;

         for (int var6 = 0; var6 < 4; var6++) {
            int var7 = var9 + 28 + var6 * 18;
            if (a(var1, var2, var8 + 24, var7, 154, 14)) {
               this.draggingColorChannel = var6;
               this.d(var6, var1);
               return;
            }
         }

         if (a(var1, var2, var8 + 116, var9 + 101, 84, 16)) {
            this.colorEditor = null;
            this.draggingColorChannel = -1;
         }
      } else if (this.creatingProfile) {
         this.profileName.mouseClicked(var1, var2, var3);
         int var4 = this.left + 250 - 100;
         int var5 = this.top + 155 - 38;
         if (a(var1, var2, var4 + 10, var5 + 55, 84, 18)) {
            this.q();
         } else if (a(var1, var2, var4 + 106, var5 + 55, 84, 18)) {
            this.p();
         }
      } else {
         if (this.editingNumericSetting != null) {
            this.l();
         }

         if (this.editingStringSetting != null) {
            this.n();
         }

         if (var3 != 0 || !this.i(var1, var2)) {
            if (this.page == com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.b.MODS && this.selectedModule == null) {
               this.search.mouseClicked(var1, var2, var3);
            } else {
               this.search.setFocused(false);
            }

            if (a(var1, var2, this.left + 500 - 29, this.top + 8, 20, 20)) {
               this.mc.displayGuiScreen(this.parent);
            } else if (a(var1, var2, this.left + 126, this.top + 9, 54, 22)) {
               this.page = com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.b.MODS;
               this.selectedModule = null;
            } else if (a(var1, var2, this.left + 188, this.top + 9, 68, 22)) {
               this.page = com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.b.SETTINGS;
               this.selectedModule = null;
            } else if (a(var1, var2, this.left + 7, this.top + 310 - 25, 94, 17)) {
               this.mc.displayGuiScreen(this.parent);
            } else if (a(var1, var2, this.left + 7, this.top + 310 - 47, 94, 16)) {
               this.creatingProfile = true;
               this.profileError = null;
               this.profileName.setText("");
               this.profileName.setFocused(true);
               this.search.setFocused(false);
            } else if (!this.m(var1, var2)) {
               if (this.page != com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.b.MODS || this.selectedModule != null || !this.l(var1, var2)) {
                  if (this.selectedModule != null) {
                     this.o(var1, var2);
                  } else if (this.page == com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.b.MODS) {
                     this.n(var1, var2);
                  } else {
                     this.p(var1, var2);
                  }
               }
            }
         }
      }
   }

   private boolean l(int var1, int var2) {
      int var3 = this.left + 108 + 9;
      int var4 = this.top + 40 + 7;
      com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.a var5 = null;
      if (a(var1, var2, var3, var4, 30, 14)) {
         var5 = com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.a.ALL;
      } else if (a(var1, var2, var3 + 34, var4, 36, 14)) {
         var5 = com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.a.NEW;
      } else if (a(var1, var2, var3 + 74, var4, 38, 14)) {
         var5 = com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.a.HUD;
      } else if (a(var1, var2, var3 + 116, var4, 48, 14)) {
         var5 = com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.a.SERVER;
      } else if (a(var1, var2, var3 + 168, var4, 60, 14)) {
         var5 = com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.a.MECHANIC;
      }

      if (var5 == null) {
         return false;
      } else {
         this.modCategory = var5;
         this.moduleScroll = 0;
         return true;
      }
   }

   private boolean m(int var1, int var2) {
      int var3 = this.top + 62;

      for (com.cheatbreaker.client.settings.Profile var6 : new ArrayList<>(com.cheatbreaker.client.CheatBreakerClient.N().profiles)) {
         if (var3 + 20 >= this.top + 310 - 54) {
            break;
         }

         if (var6.b() && a(var1, var2, this.left + 108 - 20, var3 + 4, 12, 12)) {
            this.b(var6);
            return true;
         }

         if (a(var1, var2, this.left + 7, var3, 94, 20)) {
            this.a(var6);
            return true;
         }

         var3 += 22;
      }

      return false;
   }

   private void a(com.cheatbreaker.client.settings.Profile var1) {
      if (var1 != null && var1 != com.cheatbreaker.client.CheatBreakerClient.N().activeProfile) {
         com.cheatbreaker.client.settings.Profile var2 = com.cheatbreaker.client.CheatBreakerClient.N().activeProfile;
         if (var2 != null && var2.b()) {
            com.cheatbreaker.client.CheatBreakerClient.N().configManager.b(var2.c());
         }

         com.cheatbreaker.client.CheatBreakerClient.N().activeProfile = var1;
         com.cheatbreaker.client.CheatBreakerClient.N().configManager.a(var1.c());
         com.cheatbreaker.client.CheatBreakerClient.N().configManager.c();
      }
   }

   private void b(com.cheatbreaker.client.settings.Profile var1) {
      if (var1 != null && var1.b()) {
         boolean var2 = var1 == com.cheatbreaker.client.CheatBreakerClient.N().activeProfile;
         if (com.cheatbreaker.client.CheatBreakerClient.N().configManager.a(var1)) {
            com.cheatbreaker.client.CheatBreakerClient.N().profiles.remove(var1);

            for (int var3 = 0; var3 < com.cheatbreaker.client.CheatBreakerClient.N().profiles.size(); var3++) {
               com.cheatbreaker.client.CheatBreakerClient.N().profiles.get(var3).a(var3);
            }

            if (var2) {
               com.cheatbreaker.client.settings.Profile var4 = com.cheatbreaker.client.CheatBreakerClient.N().profiles.get(0);
               com.cheatbreaker.client.CheatBreakerClient.N().activeProfile = var4;
               com.cheatbreaker.client.CheatBreakerClient.N().configManager.a(var4.c());
            }

            com.cheatbreaker.client.CheatBreakerClient.N().configManager.c();
         }
      }
   }

   private void n(int var1, int var2) {
      if (var2 >= this.top + 40 + 28 && var2 < this.top + 310 - 7) {
         List var3 = this.k();
         int var4 = this.left + 108 + 9;
         int var5 = this.top + 40 + 31 - this.moduleScroll;

         for (int var6 = 0; var6 < var3.size(); var6++) {
            int var7 = var4 + var6 % 3 * 125;
            int var8 = var5 + var6 / 3 * 115;
            if (a(var1, var2, var7, var8, 118, 108)) {
               com.cheatbreaker.client.module.Module var9 = (com.cheatbreaker.client.module.Module)var3.get(var6);
               if (a(var1, var2, var7 + 3, var8 + 3, 16, 16)) {
                  com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.favoriteModules.b(var9.h());
                  com.cheatbreaker.client.CheatBreakerClient.N().configManager.c();
               } else if (a(var1, var2, var7, var8 + 108 - 19, 118, 19)) {
                  boolean var10 = com.cheatbreaker.client.module.ModuleVisibilityUtil.a(var9.g(), var9.r() != null, var9.q());
                  var9.a(var10);
                  if (var9.r() != null) {
                     var9.f(var10);
                  }
               } else {
                  this.selectedModule = var9;
                  this.settingsScroll = 0;
                  this.search.setFocused(false);
               }

               return;
            }
         }
      }
   }

   private void o(int var1, int var2) {
      int var3 = this.left + 108 + 10;
      int var4 = this.top + 40 + 8;
      if (a(var1, var2, var3, var4, 18, 18)) {
         this.selectedModule = null;
      } else if (a(var1, var2, this.left + 500 - 75, var4, 63, 18)) {
         this.selectedModule.o();
         this.settingsScroll = 0;
         com.cheatbreaker.client.CheatBreakerClient.N().configManager.a();
      } else {
         List var5 = this.c(this.selectedModule);
         if (var2 >= var4 + 54 && var2 < this.top + 310 - 8) {
            int var6 = var4 + 58 - this.settingsScroll;
            short var7 = 181;
            if (this.selectedModule instanceof cn.kazer.client189.animation.LegacyVisualsModule) {
               for (cn.kazer.client189.animation.LegacyVisualsLayout.a var15 : this.f().rows) {
                  int var16 = var3 + var15.column * 189;
                  int var11 = var6 + var15.y;
                  int var12 = var15.wide ? 370 : 181;
                  if (!var15.heading && a(var1, var2, var16, var11, var12, 20)) {
                     com.cheatbreaker.client.settings.Setting var13 = this.a(var15.label);
                     if (var13.l() == com.cheatbreaker.client.settings.Setting.SettingType.STRING_ARRAY) {
                        var13.a("1.7".equals(var13.d()) ? "1.8" : "1.7");
                        this.e(var13);
                     } else {
                        this.a(var13, var1, var16, var12);
                     }

                     return;
                  }
               }
            } else {
               for (int var8 = 0; var8 < var5.size(); var8++) {
                  int var9 = var3 + var8 % 2 * (var7 + 8);
                  int var10 = var6 + var8 / 2 * 21;
                  if (a(var1, var2, var9, var10, var7, 20)) {
                     this.a((com.cheatbreaker.client.settings.Setting)var5.get(var8), var1, var9, var7);
                     return;
                  }
               }
            }
         }
      }
   }

   private void p(int var1, int var2) {
      int var3 = this.left + 108 + 12;
      int var4 = this.top + 40 + 10;
      int var5 = var3 + 4;
      byte var6 = 116;
      byte var7 = 6;
      if (a(var1, var2, var5, var4 + 21, var6, 20)) {
         this.settingsCategory = com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.c.GENERAL;
         this.globalScroll = 0;
         this.recordingBinding = null;
      } else if (a(var1, var2, var5 + var6 + var7, var4 + 21, var6, 20)) {
         this.settingsCategory = com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.c.PERFORMANCE;
         this.globalScroll = 0;
         this.recordingBinding = null;
      } else if (a(var1, var2, var5 + (var6 + var7) * 2, var4 + 21, var6, 20)) {
         this.settingsCategory = com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.c.CONTROLS;
         this.globalScroll = 0;
         this.recordingBinding = null;
      } else if (var2 >= this.g() && var2 < this.top + 310 - 8) {
         int var8 = this.g() - this.globalScroll;
         int var9 = 0;

         for (com.cheatbreaker.client.settings.Setting var11 : this.j()) {
            int var12 = var3 + var9 % 2 * 188;
            int var13 = var8 + var9 / 2 * 24;
            if (a(var1, var2, var12, var13, 180, 20)) {
               this.a(var11, var1, var12, 180);
               return;
            }

            var9++;
         }
      }
   }

   private void a(com.cheatbreaker.client.settings.Setting var1, int var2, int var3, int var4) {
      if (this.referenceBindings.containsKey(var1)) {
         this.recordingBinding = var1;
      } else {
         if (var1.l() == com.cheatbreaker.client.settings.Setting.SettingType.BOOLEAN) {
            var1.a(!(Boolean)var1.d());
            this.e(var1);
         } else if (var1.l() == com.cheatbreaker.client.settings.Setting.SettingType.STRING_ARRAY) {
            String[] var8 = var1.h();
            if (var8 == null || var8.length == 0) {
               return;
            }

            int var9 = 0;

            for (int var7 = 0; var7 < var8.length; var7++) {
               if (var8[var7].equals(var1.d())) {
                  var9 = var7;
               }
            }

            byte var10;
            if (var2 >= var3 + var4 / 2 && var2 < var3 + var4 / 2 + 18) {
               var10 = -1;
            } else {
               if (var2 < var3 + var4 - 18) {
                  return;
               }

               var10 = 1;
            }

            var1.a(var8[(var9 + var10 + var8.length) % var8.length]);
            this.e(var1);
         } else if (var1.l() == com.cheatbreaker.client.settings.Setting.SettingType.STRING) {
            this.d(var1);
         } else if ((
               var1.l() == com.cheatbreaker.client.settings.Setting.SettingType.FLOAT
                  || var1.l() == com.cheatbreaker.client.settings.Setting.SettingType.DOUBLE
                  || var1.l() == com.cheatbreaker.client.settings.Setting.SettingType.INTEGER
            )
            && var1.b() != null
            && var1.c() != null
            && var1.f().toLowerCase(Locale.ROOT).contains("color")) {
            this.colorEditor = var1;
         } else if ((
               var1.l() == com.cheatbreaker.client.settings.Setting.SettingType.FLOAT
                  || var1.l() == com.cheatbreaker.client.settings.Setting.SettingType.DOUBLE
                  || var1.l() == com.cheatbreaker.client.settings.Setting.SettingType.INTEGER
            )
            && var1.b() != null
            && var1.c() != null) {
            int var5 = this.a(var1, var3, var4);
            int var6 = this.b(var1, var3, var4);
            if (var2 >= var3 + var4 - 42) {
               this.c(var1);
               return;
            }

            if (var2 < var5 - 3 || var2 > var5 + var6 + 3) {
               return;
            }

            this.draggingSetting = var1;
            this.draggingSettingX = var5;
            this.draggingSettingW = var6;
            this.b(var1, var2, var5, var6);
         }
      }
   }

   private void b(com.cheatbreaker.client.settings.Setting var1, int var2, int var3, int var4) {
      double var5 = Math.max(0.0, Math.min(1.0, (double)(var2 - var3) / var4));
      double var7 = var1.b().doubleValue();
      double var9 = var1.c().doubleValue();
      double var11 = var7 + (var9 - var7) * var5;
      if (var1.l() == com.cheatbreaker.client.settings.Setting.SettingType.INTEGER) {
         var1.a((int)Math.round(var11));
      } else if (var1.l() == com.cheatbreaker.client.settings.Setting.SettingType.FLOAT) {
         var1.a(Float.valueOf((float)(Math.round(var11 * 100.0) / 100.0)));
      } else {
         var1.a(Math.round(var11 * 100.0) / 100.0);
      }

      this.e(var1);
   }

   private void c(com.cheatbreaker.client.settings.Setting var1) {
      if (this.editingStringSetting != null) {
         this.n();
      }

      this.editingNumericSetting = var1;
      this.editingNumericText = this.b(var1);
      this.replaceNumericText = true;
      this.draggingSetting = null;
   }

   private void l() {
      if (this.editingNumericSetting != null) {
         Number var1 = a(this.editingNumericSetting, this.editingNumericText);
         if (var1 != null) {
            this.editingNumericSetting.a(var1);
            this.e(this.editingNumericSetting);
         }

         this.editingNumericSetting = null;
         this.editingNumericText = "";
         this.replaceNumericText = false;
      }
   }

   private void m() {
      this.editingNumericSetting = null;
      this.editingNumericText = "";
      this.replaceNumericText = false;
   }

   private void d(com.cheatbreaker.client.settings.Setting var1) {
      if (this.editingNumericSetting != null) {
         this.l();
      }

      this.editingStringSetting = var1;
      this.editingStringText = String.valueOf(var1.d());
      this.replaceStringText = true;
      this.draggingSetting = null;
      this.search.setFocused(false);
   }

   private void n() {
      if (this.editingStringSetting != null) {
         this.editingStringSetting.a(this.editingStringText);
         this.e(this.editingStringSetting);
         this.editingStringSetting = null;
         this.editingStringText = "";
         this.replaceStringText = false;
      }
   }

   private void o() {
      this.editingStringSetting = null;
      this.editingStringText = "";
      this.replaceStringText = false;
   }

   static Number a(com.cheatbreaker.client.settings.Setting var0, String var1) {
      if (var0 != null && var1 != null && !var1.trim().isEmpty()) {
         try {
            double var2 = Double.parseDouble(var1.trim());
            if (!Double.isFinite(var2)) {
               return null;
            } else {
               double var4 = var0.b() == null ? -Double.MAX_VALUE : var0.b().doubleValue();
               double var6 = var0.c() == null ? Double.MAX_VALUE : var0.c().doubleValue();
               var2 = Math.max(var4, Math.min(var6, var2));
               if (var0.l() == com.cheatbreaker.client.settings.Setting.SettingType.INTEGER) {
                  return (int)Math.round(var2);
               } else {
                  return (Number)(var0.l() == com.cheatbreaker.client.settings.Setting.SettingType.FLOAT ? (float)var2 : var2);
               }
            }
         } catch (NumberFormatException var8) {
            return null;
         }
      } else {
         return null;
      }
   }

   private void e(com.cheatbreaker.client.settings.Setting var1) {
      if (this.selectedModule instanceof com.cheatbreaker.client.module.modules.keystrokes.KeyStrokesModule && com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.keyStrokes != null) {
         com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.keyStrokes.a();
      }

      if ((var1 == com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.clearGlass || var1 == com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.redString)
         && this.mc.renderGlobal != null) {
         this.mc.renderGlobal.loadRenderers();
      }

      if (var1 == com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.worldTime && this.mc.theWorld != null) {
         this.mc.theWorld.setWorldTime(((Number)var1.d()).longValue());
      }

      if (var1 == com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.guiBlur) {
         if ((Boolean)var1.d()) {
            cn.kazer.client189.render.ClientBridge.d();
         } else {
            this.mc.entityRenderer.stopUseShader();
         }
      }

      if (var1 == com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.defaultColor) {
         int var2 = ((Number)var1.d()).intValue();

         for (com.cheatbreaker.client.module.Module var4 : com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.modules) {
            for (com.cheatbreaker.client.settings.Setting var6 : var4.m()) {
               String var7 = var6.f().toLowerCase(Locale.ROOT);
               if (var6.l() == com.cheatbreaker.client.settings.Setting.SettingType.INTEGER && (var7.equals("text color") || var7.endsWith(" text color"))) {
                  var6.a(var2);
               }
            }
         }
      }
   }

   protected void mouseReleased(int var1, int var2, int var3) {
      super.mouseReleased(var1, var2, var3);
      if (var3 == 0) {
         this.draggingSetting = null;
         this.draggingColorChannel = -1;
         this.draggingModuleScrollbar = false;
         this.draggingSettingsScrollbar = false;
         this.draggingGlobalScrollbar = false;
      }
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if (this.recordingBinding != null) {
         KeyBinding var5 = this.referenceBindings.get(this.recordingBinding);
         var5.setKeyCode(var2 == 1 ? 0 : var2);
         KeyBinding.resetKeyBindingArrayAndHash();
         com.cheatbreaker.client.CheatBreakerClient.N().configManager.c();
         this.recordingBinding = null;
      } else if (this.creatingProfile) {
         if (var2 == 1) {
            this.p();
         } else if (var2 == 28) {
            this.q();
         } else {
            this.profileName.textboxKeyTyped(var1, var2);
         }
      } else if (this.editingNumericSetting != null) {
         if (var2 == 1) {
            this.m();
         } else if (var2 != 28 && var2 != 156 && var2 != 15) {
            if (var2 != 30 || !Keyboard.isKeyDown(29) && !Keyboard.isKeyDown(157)) {
               if (var2 != 14 && var2 != 211) {
                  if (var1 >= '0' && var1 <= '9' || var1 == '-' || var1 == '.') {
                     if (this.replaceNumericText) {
                        this.editingNumericText = "";
                     }

                     if (this.editingNumericText.length() < 16) {
                        this.editingNumericText = this.editingNumericText + var1;
                     }

                     this.replaceNumericText = false;
                  }
               } else {
                  if (this.replaceNumericText) {
                     this.editingNumericText = "";
                  } else if (!this.editingNumericText.isEmpty()) {
                     this.editingNumericText = this.editingNumericText.substring(0, this.editingNumericText.length() - 1);
                  }

                  this.replaceNumericText = false;
               }
            } else {
               this.replaceNumericText = true;
            }
         } else {
            this.l();
         }
      } else if (this.editingStringSetting == null) {
         if (var2 == 1) {
            if (this.colorEditor != null) {
               this.colorEditor = null;
            } else if (this.selectedModule != null) {
               this.selectedModule = null;
            } else {
               this.mc.displayGuiScreen(this.parent);
            }
         } else {
            if (this.page == com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.b.MODS && this.selectedModule == null) {
               this.search.textboxKeyTyped(var1, var2);
            }
         }
      } else {
         boolean var3 = Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
         if (var2 == 1) {
            this.o();
         } else if (var2 == 28 || var2 == 156 || var2 == 15) {
            this.n();
         } else if (var2 == 30 && var3) {
            this.replaceStringText = true;
         } else if (var2 == 47 && var3) {
            String var4 = getClipboardString();
            if (var4 != null) {
               if (this.replaceStringText) {
                  this.editingStringText = "";
               }

               this.editingStringText = this.editingStringText + var4.replaceAll("[\\r\\n]", " ");
               if (this.editingStringText.length() > 128) {
                  this.editingStringText = this.editingStringText.substring(0, 128);
               }

               this.replaceStringText = false;
            }
         } else if (var2 != 14 && var2 != 211) {
            if (var1 >= ' ' && var1 != 127 && this.editingStringText.length() < 128) {
               if (this.replaceStringText) {
                  this.editingStringText = "";
               }

               this.editingStringText = this.editingStringText + var1;
               this.replaceStringText = false;
            }
         } else {
            if (this.replaceStringText) {
               this.editingStringText = "";
            } else if (!this.editingStringText.isEmpty()) {
               this.editingStringText = this.editingStringText.substring(0, this.editingStringText.length() - 1);
            }

            this.replaceStringText = false;
         }
      }
   }

   private void p() {
      this.creatingProfile = false;
      this.profileName.setFocused(false);
      this.profileError = null;
   }

   private void q() {
      String var1 = this.profileName.getText().trim();
      if (var1.isEmpty()) {
         this.profileError = "Enter at least 1 character";
      } else if (!var1.equalsIgnoreCase("default") && com.cheatbreaker.client.settings.FilenameValidator.a(var1)) {
         for (com.cheatbreaker.client.settings.Profile var3 : com.cheatbreaker.client.CheatBreakerClient.N().profiles) {
            if (var3.c().equalsIgnoreCase(var1)) {
               this.profileError = "That profile already exists";
               return;
            }
         }

         com.cheatbreaker.client.settings.Profile var4 = new com.cheatbreaker.client.settings.Profile(var1, true);
         var4.a(com.cheatbreaker.client.CheatBreakerClient.N().profiles.size());
         com.cheatbreaker.client.CheatBreakerClient.N().profiles.add(var4);
         com.cheatbreaker.client.CheatBreakerClient.N().activeProfile = var4;
         com.cheatbreaker.client.CheatBreakerClient.N().configManager.b(var1);
         com.cheatbreaker.client.CheatBreakerClient.N().configManager.c();
         this.p();
      } else {
         this.profileError = "Choose another profile name";
      }
   }

   private static boolean a(int var0, int var1, int var2, int var3, int var4, int var5) {
      return var0 >= var2 && var0 < var2 + var4 && var1 >= var3 && var1 < var3 + var5;
   }

   private static enum a {
      ALL,
      NEW,
      HUD,
      SERVER,
      MECHANIC;
   }

   private static enum b {
      MODS,
      SETTINGS;
   }

   private static enum c {
      GENERAL,
      PERFORMANCE,
      CONTROLS;
   }
}
