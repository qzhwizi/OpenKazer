package com.cheatbreaker.client.kazerui.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public final class HudMoveScreen extends GuiScreen {
   private static final int GUIDE = -2540289;
   private static final int SELECTED = 1350565844;
   private static final int SELECT_LINE = -8388652;
   private static final int HOVERED = -2130706433;
   private static final int IDLE = 553648127;
   private static final int BOX_LINE = Integer.MIN_VALUE;
   private static final int BUTTON_FILL = 805306368;
   private static final int BUTTON_FILL_HOVER = 1342177280;
   private static final int BUTTON_LINE = 1076176165;
   private static final int BUTTON_LINE_HOVER = -1711276033;
   private static final int BUTTON_INNER = 553648127;
   private static final String COG = "ui/icons/mainmenu/cog-20x20.png";
   private static final String CLOSE = "ui/icons/exit-17x17-small.png";
   private static final String[][] SHORTCUTS = new String[][]{
      {"Mouse1", null, "hold", "addModsToRegion"},
      {"Mouse1", null, "hold", "selectDragMods"},
      {"Mouse2", null, "click", "resetToClosest"},
      {"CTRL", "Mouse1", null, "toggleModSelection"},
      {"CTRL", "Z", null, "undoMovements"},
      {"CTRL", "Y", null, "redoMovements"}
   };
   private final com.cheatbreaker.client.kazerui.hud.HudFeatureManager features;
   private final KeyBinding openKey;
   private final GuiScreen background;
   private final com.cheatbreaker.client.kazerui.gui.UiGraphics gfx = new com.cheatbreaker.client.kazerui.gui.UiGraphics("move");
   private ScaledResolution res;
   private long openedAt;
   private long horizontalFlashAt;
   private long verticalFlashAt;
   private long lastFrame;
   private final float[] buttonHover = new float[2];
   private final List<com.cheatbreaker.client.kazerui.hud.HudFeature> selected = new ArrayList<>();
   private final Map<com.cheatbreaker.client.kazerui.hud.HudFeature, float[]> grab = new HashMap<>();
   private com.cheatbreaker.client.kazerui.hud.HudFeature hovered;
   private com.cheatbreaker.client.kazerui.hud.HudFeature dragging;
   private com.cheatbreaker.client.kazerui.hud.HudFeature resizing;
   private int dragButton;
   private boolean moved;
   private long pressedAt;
   private float regionX;
   private float regionY;
   private boolean region;
   private long regionAt;
   private final List<com.cheatbreaker.client.kazerui.hud.HudFeature> inRegion = new ArrayList<>();
   private float pivotX;
   private float pivotY;
   private float perScaleX;
   private float perScaleY;
   private final List<float[]> guides = new ArrayList<>();
   private final List<com.cheatbreaker.client.kazerui.gui.HudMoveScreen.a> undo = new ArrayList<>();
   private final List<com.cheatbreaker.client.kazerui.gui.HudMoveScreen.a> redo = new ArrayList<>();
   private final List<com.cheatbreaker.client.kazerui.gui.HudMoveScreen.a> pending = new ArrayList<>();
   private int nudgeTicks;
   private boolean nudging;

   private static String a(String var0) {
      return com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("gui.movement", var0);
   }

   public HudMoveScreen(com.cheatbreaker.client.kazerui.hud.HudFeatureManager var1, KeyBinding var2) {
      this(var1, var2, null);
   }

   public HudMoveScreen(com.cheatbreaker.client.kazerui.hud.HudFeatureManager var1, KeyBinding var2, GuiScreen var3) {
      this.features = var1;
      this.openKey = var2;
      this.background = var3;
   }

   private void a() {
      this.mc.displayGuiScreen(this.mc.theWorld == null ? this.background : null);
   }

   public void initGui() {
      this.res = new ScaledResolution(this.mc);
      this.gfx.a(this.res.getScaleFactor());
      if (this.openedAt == 0L) {
         this.openedAt = System.currentTimeMillis();
      }
   }

   private com.cheatbreaker.client.kazerui.gui.components.MenuModel b() {
      return this.features.c();
   }

   private float c() {
      float var1 = Math.min(1.0F, (float)(System.currentTimeMillis() - this.openedAt) / 500.0F);
      return var1 < 0.5F ? 2.0F * var1 * var1 : -1.0F + (4.0F - 2.0F * var1) * var1;
   }

   private static float a(long var0) {
      float var2 = (float)(System.currentTimeMillis() - var0) / 1000.0F;
      return var0 != 0L && !(var2 >= 1.0F) ? (float)(Math.cos((var2 * 2.0F - 1.0F) * Math.PI) + 1.0) / 2.0F : 0.0F;
   }

   private float[] d() {
      return new float[]{this.width / 2.0F - 50.0F, this.height / 2.0F - 14.0F, 100.0F, 28.0F};
   }

   private float[] e() {
      return new float[]{4.0F, this.height - 28, 24.0F, 24.0F};
   }

   private float[][] f() {
      return new float[][]{this.d(), this.e()};
   }

   private static boolean a(float var0, float var1, float[] var2) {
      return var0 >= var2[0] && var1 >= var2[1] && var0 < var2[0] + var2[2] && var1 < var2[1] + var2[3];
   }

   private boolean a(float var1, float var2) {
      return a(var1, var2, this.d());
   }

   private float g() {
      return (float)(Mouse.getX() * this.width) / this.mc.displayWidth;
   }

   private float h() {
      return this.height - (float)(Mouse.getY() * this.height) / this.mc.displayHeight - 1.0F;
   }

   private List<com.cheatbreaker.client.kazerui.hud.HudFeature> i() {
      ArrayList var1 = new ArrayList();

      for (com.cheatbreaker.client.kazerui.hud.HudFeature var3 : this.features.d()) {
         if (this.features.b(var3)) {
            var1.add(var3);
         }
      }

      return var1;
   }

   private static boolean a(float[] var0) {
      return var0[2] > 16.0F && var0[3] > 10.0F;
   }

   private boolean[] a(com.cheatbreaker.client.kazerui.hud.HudFeature var1, float[] var2) {
      com.cheatbreaker.client.kazerui.hud.HudAnchor var3 = this.b(var1, var2);
      return var3.horizontal == com.cheatbreaker.client.kazerui.hud.HudAnchor.a.START
         ? new boolean[]{true, var3.vertical != com.cheatbreaker.client.kazerui.hud.HudAnchor.a.END}
         : new boolean[]{false, var3.vertical == com.cheatbreaker.client.kazerui.hud.HudAnchor.a.START};
   }

   private com.cheatbreaker.client.kazerui.hud.HudAnchor b(com.cheatbreaker.client.kazerui.hud.HudFeature var1, float[] var2) {
      com.cheatbreaker.client.kazerui.hud.HudAnchor var3 = this.features.e(var1.a());
      return var3 != null ? var3 : com.cheatbreaker.client.kazerui.hud.HudAnchor.a(var2[0] + var2[2] / 2.0F, var2[1] + var2[3] / 2.0F, this.width, this.height);
   }

   private float c(com.cheatbreaker.client.kazerui.hud.HudFeature var1, float[] var2) {
      if (a(var2)) {
         return this.a(var1, var2)[1] ? var2[1] + 2.0F : var2[1] + var2[3] - 10.0F;
      } else {
         boolean var3 = this.b(var1, var2).vertical != com.cheatbreaker.client.kazerui.hud.HudAnchor.a.START;
         if (var3) {
            return var2[1] - 10.0F >= 0.0F ? var2[1] - 10.0F : var2[1] + var2[3] + 2.0F;
         } else {
            return var2[1] + var2[3] + 12.0F <= this.height ? var2[1] + var2[3] + 2.0F : var2[1] - 10.0F;
         }
      }
   }

   private float[] d(com.cheatbreaker.client.kazerui.hud.HudFeature var1, float[] var2) {
      return new float[]{a(var2) ? var2[0] + 1.0F : var2[0] + var2[2] / 2.0F - 10.0F, this.c(var1, var2)};
   }

   private float[] e(com.cheatbreaker.client.kazerui.hud.HudFeature var1, float[] var2) {
      return new float[]{a(var2) ? var2[0] + var2[2] - 10.0F : var2[0] + var2[2] / 2.0F + 2.0F, this.c(var1, var2)};
   }

   private static boolean b(float var0, float var1, float[] var2) {
      return var0 > var2[0] - 2.0F && var1 > var2[1] - 2.0F && var0 < var2[0] + 10.0F && var1 < var2[1] + 10.25F;
   }

   private float[] f(com.cheatbreaker.client.kazerui.hud.HudFeature var1, float[] var2) {
      boolean[] var3 = this.a(var1, var2);
      return new float[]{var2[0] + (var3[0] ? var2[2] : 0.0F) - 2.5F, var2[1] + (var3[1] ? var2[3] : 0.0F) - 2.5F};
   }

   private static boolean c(float var0, float var1, float[] var2) {
      return var0 >= var2[0] - 2.0F && var0 <= var2[0] + 7.0F && var1 >= var2[1] - 2.0F && var1 <= var2[1] + 7.0F;
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.res = new ScaledResolution(this.mc);
      long var4 = System.currentTimeMillis();
      float var6 = this.lastFrame == 0L ? 0.0F : (float)(var4 - this.lastFrame) / 125.0F;
      this.lastFrame = var4;
      float var7 = this.g();
      float var8 = this.h();
      float var9 = this.c();
      if (this.mc.theWorld == null) {
         com.cheatbreaker.client.kazerui.gui.menus.HomeScreen.a(this.background, this, var3);
      }

      com.cheatbreaker.client.kazerui.gui.UiGraphics.b(
         1.5F, 1.5F, this.width - 3, this.height - 3, 0.5F, com.cheatbreaker.client.kazerui.gui.UiGraphics.a(-16711681, 0.8F * var9)
      );
      float var10 = a(this.horizontalFlashAt);
      float var11 = a(this.verticalFlashAt);
      if (var10 > 0.0F) {
         com.cheatbreaker.client.kazerui.gui.UiGraphics.a(
            0.0F, this.height / 2.0F - 0.25F, this.width, this.height / 2.0F + 0.25F, com.cheatbreaker.client.kazerui.gui.UiGraphics.a(-2540289, var10)
         );
      }

      if (var11 > 0.0F) {
         com.cheatbreaker.client.kazerui.gui.UiGraphics.a(
            this.width / 2.0F - 0.25F, 0.0F, this.width / 2.0F + 0.25F, this.height, com.cheatbreaker.client.kazerui.gui.UiGraphics.a(-2540289, var11)
         );
      }

      boolean var12 = this.region && var4 - this.regionAt >= 100L;
      if (var12) {
         float var13 = Math.min(var7, this.regionX);
         float var14 = Math.min(var8, this.regionY);
         float var15 = Math.abs(var7 - this.regionX);
         float var16 = Math.abs(var8 - this.regionY);
         com.cheatbreaker.client.kazerui.gui.UiGraphics.a(var13, var14, var13 + var15, var14 + var16, 1350565844);
         com.cheatbreaker.client.kazerui.gui.UiGraphics.b(var13, var14, var15, var16, 0.5F, -8388652);
      }

      this.guides.clear();
      if (this.resizing != null && Mouse.isButtonDown(0)) {
         this.d(var7, var8);
      } else if (this.dragging != null && Mouse.isButtonDown(this.dragButton)) {
         this.b(var7, var8);
      }

      List var20 = this.i();
      this.a(var20, var7, var8);
      this.inRegion.clear();

      for (com.cheatbreaker.client.kazerui.hud.HudFeature var26 : (Iterable<com.cheatbreaker.client.kazerui.hud.HudFeature>) var20) {
         float[] var30 = this.features.a(var26, this.res);
         if (var12 && a(var30, Math.min(var7, this.regionX), Math.min(var8, this.regionY), Math.max(var7, this.regionX), Math.max(var8, this.regionY))) {
            this.inRegion.add(var26);
         }

         this.features.c(var26, this.res);
         int var17 = !this.selected.contains(var26) && !this.inRegion.contains(var26) ? (var26 == this.hovered ? -2130706433 : 553648127) : 1350565844;
         com.cheatbreaker.client.kazerui.gui.UiGraphics.a(var30[0], var30[1], var30[0] + var30[2], var30[1] + var30[3], var17);
         com.cheatbreaker.client.kazerui.gui.UiGraphics.b(var30[0], var30[1], var30[2] - 0.5F, var30[3] - 0.5F, 0.5F, Integer.MIN_VALUE);
      }

      if (this.hovered != null && this.resizing == null) {
         float[] var23 = this.features.a(this.hovered, this.res);
         float[] var27 = this.f(this.hovered, var23);
         com.cheatbreaker.client.kazerui.gui.UiGraphics.a(var27[0], var27[1], var27[0] + 5.0F, var27[1] + 5.0F, -8388652);
         float[] var31 = this.d(this.hovered, var23);
         float[] var33 = this.e(this.hovered, var23);
         boolean var18 = b(var7, var8, var31);
         boolean var19 = b(var7, var8, var33);
         this.gfx.a("ui/icons/mainmenu/cog-20x20.png", var31[0] + 1.0F, var31[1] + 1.0F, 8.0F, 8.0F, var18 ? -16777216 : -1291845632);
         this.gfx.a("ui/icons/exit-17x17-small.png", var33[0] + 1.0F, var33[1] + 1.0F, 8.0F, 8.0F, var19 ? -16777216 : -1291845632);
         this.gfx.a("ui/icons/mainmenu/cog-20x20.png", var31[0], var31[1], 8.0F, 8.0F, var18 ? -1 : -2130706433);
         this.gfx.a("ui/icons/exit-17x17-small.png", var33[0], var33[1], 8.0F, 8.0F, var19 ? -52429 : -2130758861);
      } else if (this.resizing != null) {
         float[] var22 = this.f(this.resizing, this.features.a(this.resizing, this.res));
         com.cheatbreaker.client.kazerui.gui.UiGraphics.a(var22[0], var22[1], var22[0] + 5.0F, var22[1] + 5.0F, -8388652);
      }

      for (float[] var28 : this.guides) {
         if (var28[0] == 0.0F) {
            com.cheatbreaker.client.kazerui.gui.UiGraphics.a(var28[1] - 0.25F, 0.0F, var28[1] + 0.25F, this.height, -2540289);
         } else {
            com.cheatbreaker.client.kazerui.gui.UiGraphics.a(0.0F, var28[1] - 0.25F, this.width, var28[1] + 0.25F, -2540289);
         }
      }

      this.a(var9);
      float[][] var25 = this.f();
      boolean var29 = this.dragging != null || this.resizing != null || this.region;

      for (int var32 = 0; var32 < var25.length; var32++) {
         boolean var34 = !var29 && a(var7, var8, var25[var32]);
         this.buttonHover[var32] = Math.max(0.0F, Math.min(1.0F, this.buttonHover[var32] + (var34 ? var6 : -var6)));
      }

      this.a(this.d(), this.buttonHover[0], "MODS", null);
      this.a(this.e(), this.buttonHover[1], "?", null);
      if (!var29 && a(var7, var8, this.e())) {
         this.j();
      }

      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void a(float var1) {
      if (var1 > 0.2F) {
         float var2 = this.gfx.a("KAZER", "raleway-extrabold.ttf", 22.0F);
         float var3 = this.height / 2.0F - 36.0F;
         int var4 = com.cheatbreaker.client.kazerui.gui.UiGraphics.a(-16777216, 0.4F * var1);
         int var5 = com.cheatbreaker.client.kazerui.gui.UiGraphics.a(-1, var1);
         this.gfx.a("KAZER", "raleway-extrabold.ttf", 22.0F, this.width / 2.0F - var2 - 2.0F + 1.0F, var3 + 1.0F, var4);
         this.gfx.a("KAZER", "raleway-extrabold.ttf", 22.0F, this.width / 2.0F - var2 - 2.0F, var3, var5);
         this.gfx.a("CLIENT", "raleway-light.ttf", 22.0F, this.width / 2.0F + 2.0F + 1.0F, var3 + 1.0F, var4);
         this.gfx.a("CLIENT", "raleway-light.ttf", 22.0F, this.width / 2.0F + 2.0F, var3, var5);
      }

      this.gfx.a("ui/logo/kazer.png", this.width / 2.0F - 32.0F, this.height / 2.0F - 80.0F - 20.0F * var1, 64.0F, 64.0F, -1);
   }

   private void a(float[] var1, float var2, String var3, String var4) {
      int var5 = com.cheatbreaker.client.kazerui.gui.UiGraphics.a(805306368, 1342177280, var2);
      this.gfx.a(var1[0], var1[1], var1[2], var1[3], 5.0F, var5);
      this.gfx.a(var1[0], var1[1], var1[2], var1[3], 4.0F, 1.0F, com.cheatbreaker.client.kazerui.gui.UiGraphics.a(1076176165, -1711276033, var2));
      this.gfx.a(var1[0] + 1.0F, var1[1] + 1.0F, var1[2] - 2.0F, var1[3] - 2.0F, 2.75F, 1.0F, 553648127);
      if (var4 != null) {
         float var6 = Math.min(1.0F, (var5 >>> 24) / 255.0F * 2.5F);
         this.gfx.a(var4, var1[0] + var1[2] / 2.0F - 7.0F, var1[1] + var1[3] / 2.0F - 7.0F, 14.0F, 14.0F, com.cheatbreaker.client.kazerui.gui.UiGraphics.a(-1, var6));
      } else {
         float var10 = this.gfx.a(var3, "roboto-light.ttf", 22.0F);
         float var7 = this.gfx.a("roboto-light.ttf", 22.0F);
         float var8 = var1[0] + var1[2] / 2.0F - var10 / 2.0F;
         float var9 = var1[1] + var1[3] / 2.0F - var7 / 2.0F;
         this.gfx.a(var3, "roboto-light.ttf", 22.0F, var8 + 1.0F, var9 + 1.0F, 536870912);
         this.gfx.a(var3, "roboto-light.ttf", 22.0F, var8, var9, -1);
      }
   }

   private void j() {
      int var1 = SHORTCUTS.length;
      float var2 = this.height - 185;
      float var3 = 16 + var1 * 12 + 12;
      float var4 = var3 + 12.0F + 16.0F;
      GlStateManager.pushMatrix();
      GlStateManager.translate(0.0F, var2, 0.0F);
      com.cheatbreaker.client.kazerui.gui.UiGraphics.a(0.0F, 0.0F, 240.0F, var4, Integer.MIN_VALUE);
      this.gfx.a(a("shortcutsMovement"), "roboto-light.ttf", 16.0F, 4.0F, 2.0F, -1);
      com.cheatbreaker.client.kazerui.gui.UiGraphics.a(4.0F, 12.0F, 230.0F, 0.5F, -2130706433);
      float var5 = 16.0F;

      for (String[] var9 : SHORTCUTS) {
         this.a(var9[0], 6.0F, var5);
         if (var9[1] != null) {
            this.gfx.a("+", "roboto-medium.ttf", 13.0F, 30.0F, var5, -1);
            this.a(var9[1], 36.0F, var5);
         }

         float var10 = 80.0F;
         this.gfx.a("| ", "roboto-medium.ttf", 13.0F, var10, var5, -1);
         var10 += this.gfx.a("| ", "roboto-medium.ttf", 13.0F);
         if (var9[2] != null) {
            String var11 = a(var9[2]) + " ";
            this.gfx.a(var11, "roboto-medium.ttf", 13.0F, var10, var5, -11141121);
            var10 += this.gfx.a(var11, "roboto-medium.ttf", 13.0F);
         }

         this.gfx.a(a(var9[3]), "roboto-medium.ttf", 13.0F, var10, var5, -1);
         var5 += 12.0F;
      }

      this.a("Up", 31.0F, var3);
      this.a("Left", 6.0F, var3 + 12.0F);
      this.a("Down", 26.0F, var3 + 12.0F);
      this.a("Right", 51.0F, var3 + 12.0F);
      this.gfx.a("| " + a("moveWithPrecision"), "roboto-medium.ttf", 13.0F, 80.0F, var3 + 12.0F, -1);
      GlStateManager.popMatrix();
   }

   private void a(String var1, float var2, float var3) {
      float var4 = this.gfx.a(var1, "roboto-medium.ttf", 13.0F) + 4.0F;
      this.gfx.a(var2, var3, var4, 10.0F, 1.0F, -1);
      this.gfx.a(var1, "roboto-medium.ttf", 13.0F, var2 + 2.0F, var3, -16777216);
   }

   private static boolean a(float[] var0, float var1, float var2, float var3, float var4) {
      return var0[0] < var3 && var0[0] + var0[2] > var1 && var0[1] < var4 && var0[1] + var0[3] > var2;
   }

   private void a(List<com.cheatbreaker.client.kazerui.hud.HudFeature> var1, float var2, float var3) {
      if (this.dragging == null && this.resizing == null) {
         if (!this.region && !this.a(var2, var3)) {
            if (this.hovered != null && var1.contains(this.hovered)) {
               float[] var4 = this.features.a(this.hovered, this.res);
               if (a(var2, var3, var4)
                  || b(var2, var3, this.d(this.hovered, var4))
                  || b(var2, var3, this.e(this.hovered, var4))
                  || c(var2, var3, this.f(this.hovered, var4))) {
                  return;
               }
            }

            this.hovered = null;

            for (int var5 = var1.size() - 1; var5 >= 0; var5--) {
               if (a(var2, var3, this.features.a((com.cheatbreaker.client.kazerui.hud.HudFeature)var1.get(var5), this.res))) {
                  this.hovered = (com.cheatbreaker.client.kazerui.hud.HudFeature)var1.get(var5);
                  return;
               }
            }
         } else {
            this.hovered = null;
         }
      } else {
         this.hovered = this.dragging != null ? this.dragging : this.resizing;
      }
   }

   private void b(float var1, float var2) {
      float[] var3 = this.grab.get(this.dragging);
      if (var3 != null) {
         float[] var4 = this.features.a(this.dragging, this.res);
         float var5 = var1 - var3[0];
         float var6 = var2 - var3[1];
         if (!this.moved) {
            if (var5 == var4[0] && var6 == var4[1]) {
               return;
            }

            this.moved = true;
         }

         boolean var7 = true;
         boolean var8 = true;
         if (var5 <= 4.0F) {
            var5 = 2.0F;
            var7 = false;
            this.guides.add(new float[]{0.0F, 1.5F});
         } else if (var5 + var4[2] >= this.width - 4) {
            var5 = this.width - 2.0F - var4[2];
            var7 = false;
            this.guides.add(new float[]{0.0F, this.width - 1.5F});
         }

         if (var6 <= 4.0F) {
            var6 = 2.0F;
            var8 = false;
            this.guides.add(new float[]{1.0F, 1.0F});
         } else if (var6 + var4[3] >= this.height - 4) {
            var6 = this.height - 2.0F - var4[3];
            var8 = false;
            this.guides.add(new float[]{1.0F, this.height - 1.5F});
         }

         if (this.selected.size() <= 1) {
            if (this.dragButton == 0) {
               float[] var15 = this.a(this.dragging, var5, var6, var4[2], var4[3], var7, var8);
               var5 = var15[0];
               var6 = var15[1];
            }

            this.features.a(this.dragging, var5, var6, this.res);
         } else {
            float var9 = var5 - var4[0];
            float var10 = var6 - var4[1];

            for (com.cheatbreaker.client.kazerui.hud.HudFeature var12 : this.selected) {
               float[] var13 = this.features.a(var12, this.res);
               var9 = Math.max(var9, 2.0F - var13[0]);
               var9 = Math.min(var9, this.width - 2.0F - var13[0] - var13[2]);
               var10 = Math.max(var10, 2.0F - var13[1]);
               var10 = Math.min(var10, this.height - 2.0F - var13[1] - var13[3]);
            }

            for (com.cheatbreaker.client.kazerui.hud.HudFeature var18 : this.selected) {
               float[] var19 = this.features.a(var18, this.res);
               this.features.a(var18, var19[0] + var9, var19[1] + var10, this.res);
            }
         }
      }
   }

   private float[] a(com.cheatbreaker.client.kazerui.hud.HudFeature var1, float var2, float var3, float var4, float var5, boolean var6, boolean var7) {
      com.cheatbreaker.client.kazerui.hud.HudAnchor var8 = com.cheatbreaker.client.kazerui.hud.HudAnchor.a(var2 + var4 / 2.0F, var3 + var5 / 2.0F, this.width, this.height);
      if (var7 && var8.vertical == com.cheatbreaker.client.kazerui.hud.HudAnchor.a.MIDDLE && Math.abs(this.height / 2.0F - (var3 + var5 / 2.0F)) < 5.0F) {
         var3 = this.height / 2.0F - var5 / 2.0F;
         var7 = false;
         this.guides.add(new float[]{1.0F, this.height / 2.0F});
      }

      boolean var9 = var8.horizontal == com.cheatbreaker.client.kazerui.hud.HudAnchor.a.MIDDLE
         || var8 == com.cheatbreaker.client.kazerui.hud.HudAnchor.BOTTOM_CENTER_L
         || var8 == com.cheatbreaker.client.kazerui.hud.HudAnchor.BOTTOM_CENTER_R;
      if (var6 && var9 && Math.abs(this.width / 2.0F - (var2 + var4 / 2.0F)) < 5.0F) {
         var2 = this.width / 2.0F - var4 / 2.0F;
         var6 = false;
         this.guides.add(new float[]{0.0F, this.width / 2.0F});
      }

      final float var10 = var2 + var4 / 2.0F;
      final float var11 = var3 + var5 / 2.0F;
      List var12 = this.i();
      Collections.sort(
         var12,
         new Comparator<com.cheatbreaker.client.kazerui.hud.HudFeature>() {
            public int compare(com.cheatbreaker.client.kazerui.hud.HudFeature var1, com.cheatbreaker.client.kazerui.hud.HudFeature var2x) {
               return Float.compare(
                  com.cheatbreaker.client.kazerui.gui.HudMoveScreen.b(HudMoveScreen.this.features.a(var1, HudMoveScreen.this.res), var10, var11),
                  com.cheatbreaker.client.kazerui.gui.HudMoveScreen.b(HudMoveScreen.this.features.a(var2x, HudMoveScreen.this.res), var10, var11)
               );
            }
         }
      );

      for (com.cheatbreaker.client.kazerui.hud.HudFeature var14 : (Iterable<com.cheatbreaker.client.kazerui.hud.HudFeature>) var12) {
         if (!var6 && !var7) {
            break;
         }

         if (var14 != var1) {
            float[] var15 = this.features.a(var14, this.res);
            if (!(var15[2] < 1.0F) && !(var15[3] < 1.0F)) {
               if (var6) {
                  float[][] var16 = new float[][]{
                     {var15[0] - var2, var15[0]},
                     {var15[0] + var15[2] - (var2 + var4), var15[0] + var15[2]},
                     {var15[0] - (var2 + var4), var15[0]},
                     {var15[0] + var15[2] - var2, var15[0] + var15[2]}
                  };

                  for (float[] var20 : var16) {
                     if (Math.abs(var20[0]) <= 2.0F) {
                        var2 += var20[0];
                        var6 = false;
                        this.guides.add(new float[]{0.0F, var20[1]});
                        break;
                     }
                  }
               }

               if (var7) {
                  float[][] var21 = new float[][]{
                     {var15[1] - var3, var15[1] - 0.5F},
                     {var15[1] + var15[3] - (var3 + var5), var15[1] + var15[3]},
                     {var15[1] - (var3 + var5), var15[1]},
                     {var15[1] + var15[3] - var3, var15[1] + var15[3]}
                  };

                  for (float[] var25 : var21) {
                     if (Math.abs(var25[0]) <= 2.0F) {
                        var3 += var25[0];
                        var7 = false;
                        this.guides.add(new float[]{1.0F, var25[1]});
                        break;
                     }
                  }
               }
            }
         }
      }

      return new float[]{var2, var3};
   }

   private static float b(float[] var0, float var1, float var2) {
      float var3 = Math.max(Math.abs(var0[0] + var0[2] / 2.0F - var1) - var0[2] / 2.0F, 0.0F);
      float var4 = Math.max(Math.abs(var0[1] + var0[3] / 2.0F - var2) - var0[3] / 2.0F, 0.0F);
      return var3 * var3 + var4 * var4;
   }

   private void g(com.cheatbreaker.client.kazerui.hud.HudFeature var1, float[] var2) {
      boolean[] var3 = this.a(var1, var2);
      float var4 = this.features.d(var1.a());
      this.pivotX = var3[0] ? var2[0] : var2[0] + var2[2];
      this.pivotY = var3[1] ? var2[1] : var2[1] + var2[3];
      this.perScaleX = c((var3[0] ? var2[0] + var2[2] : var2[0]) - this.pivotX, var4);
      this.perScaleY = c((var3[1] ? var2[1] + var2[3] : var2[1]) - this.pivotY, var4);
      this.resizing = var1;
   }

   private static float c(float var0, float var1) {
      float var2 = var0 / var1;
      return Math.abs(var2) < 0.5F ? 0.0F : var2;
   }

   private void d(float var1, float var2) {
      float var3 = this.perScaleX == 0.0F ? Float.NEGATIVE_INFINITY : (var1 - this.pivotX) / this.perScaleX;
      float var4 = this.perScaleY == 0.0F ? Float.NEGATIVE_INFINITY : (var2 - this.pivotY) / this.perScaleY;
      float var5 = Math.max(0.5F, Math.min(3.0F, Math.round(Math.max(var3, var4) * 100.0F) / 100.0F));
      if (!Float.isInfinite(var5) && var5 != this.features.d(this.resizing.a())) {
         this.b().b(this.resizing.a(), "scale", "" + var5);
         float[] var6 = this.features.a(this.resizing, this.res);
         float var7 = this.perScaleX >= 0.0F ? this.pivotX : this.pivotX - var6[2];
         float var8 = this.perScaleY >= 0.0F ? this.pivotY : this.pivotY - var6[3];
         this.features.a(this.resizing, var7, var8, this.res);
      }
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      float var4 = this.g();
      float var5 = this.h();
      if (var3 == 0) {
         if (a(var4, var5, this.d())) {
            this.b(null);
            return;
         }

         if (a(var4, var5, this.e())) {
            return;
         }
      }

      if (var3 == 0 || var3 == 1) {
         long var6 = System.currentTimeMillis();
         if (this.hovered != null) {
            float[] var8 = this.features.a(this.hovered, this.res);
            if (var3 == 0 && b(var4, var5, this.d(this.hovered, var8))) {
               this.b(this.hovered.a());
               return;
            }

            if (var3 == 0 && b(var4, var5, this.e(this.hovered, var8))) {
               this.b(this.hovered, var6);
               this.b().b(this.hovered.a(), "enabled", "false");
               this.selected.remove(this.hovered);
               this.features.b();
               this.hovered = null;
               return;
            }

            if (var3 == 0 && c(var4, var5, this.f(this.hovered, var8))) {
               this.pending.clear();
               this.pending.add(this.a(this.hovered, var6));
               this.g(this.hovered, var8);
               return;
            }

            this.pending.clear();

            for (com.cheatbreaker.client.kazerui.hud.HudFeature var10 : this.features.d()) {
               this.pending.add(this.a(var10, var6));
            }

            this.pressedAt = var6;
            this.dragging = this.hovered;
            this.dragButton = var3;
            this.moved = false;
            if (!this.selected.contains(this.hovered)) {
               if (!isCtrlKeyDown()) {
                  this.selected.clear();
               }

               this.selected.add(this.hovered);
            }

            this.grab.clear();

            for (com.cheatbreaker.client.kazerui.hud.HudFeature var13 : this.selected) {
               float[] var11 = this.features.a(var13, this.res);
               this.grab.put(var13, new float[]{var4 - var11[0], var5 - var11[1]});
            }
         } else {
            if (!isCtrlKeyDown()) {
               this.selected.clear();
            }

            this.region = true;
            this.regionX = var4;
            this.regionY = var5;
            this.regionAt = var6;
         }
      }
   }

   protected void mouseReleased(int var1, int var2, int var3) {
      if (var3 == 0 || var3 == 1) {
         long var4 = System.currentTimeMillis();
         if (this.region) {
            this.region = false;

            for (com.cheatbreaker.client.kazerui.hud.HudFeature var7 : this.inRegion) {
               if (!this.selected.contains(var7)) {
                  this.selected.add(var7);
               }
            }

            this.inRegion.clear();
         }

         if (this.resizing != null) {
            this.features.b(this.resizing, this.res);
            this.k();
            this.resizing = null;
         }

         if (this.dragging != null) {
            if (this.moved) {
               for (com.cheatbreaker.client.kazerui.hud.HudFeature var9 : this.selected) {
                  this.features.b(var9, this.res);
               }
            }

            if (!this.moved && var3 == 1 && var4 - this.pressedAt <= 250L) {
               this.a(this.dragging);
            }

            this.k();
            this.dragging = null;
         }

         this.features.b();
      }
   }

   private void a(com.cheatbreaker.client.kazerui.hud.HudFeature var1) {
      this.features.b(var1, this.res);
      com.cheatbreaker.client.kazerui.hud.HudAnchor var2 = this.features.e(var1.a());
      float var3 = 0.0F;
      float var4 = 0.0F;
      if (!isShiftKeyDown()) {
         if (var2 == com.cheatbreaker.client.kazerui.hud.HudAnchor.TOP_CENTER
            || var2 == com.cheatbreaker.client.kazerui.hud.HudAnchor.BOTTOM_CENTER_L
            || var2 == com.cheatbreaker.client.kazerui.hud.HudAnchor.BOTTOM_CENTER_R) {
            this.verticalFlashAt = System.currentTimeMillis();
            var4 = this.b().a(var1.a(), "y", 0.0F);
         } else if (var2 != com.cheatbreaker.client.kazerui.hud.HudAnchor.MIDDLE_LEFT && var2 != com.cheatbreaker.client.kazerui.hud.HudAnchor.MIDDLE_RIGHT) {
            this.a(var2);
         } else {
            this.horizontalFlashAt = System.currentTimeMillis();
            var3 = this.b().a(var1.a(), "x", 0.0F);
         }
      } else {
         this.a(var2);
      }

      this.b().b(var1.a(), "x", "" + var3);
      this.b().b(var1.a(), "y", "" + var4);
   }

   private void a(com.cheatbreaker.client.kazerui.hud.HudAnchor var1) {
      if (var1.vertical == com.cheatbreaker.client.kazerui.hud.HudAnchor.a.MIDDLE) {
         this.horizontalFlashAt = System.currentTimeMillis();
      }

      if (var1.horizontal == com.cheatbreaker.client.kazerui.hud.HudAnchor.a.MIDDLE) {
         this.verticalFlashAt = System.currentTimeMillis();
      }
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if (var2 != 1 && var2 != this.openKey.getKeyCode()) {
         if (isCtrlKeyDown()) {
            if (var2 == 44) {
               this.a(this.undo, this.redo);
               return;
            }

            if (var2 == 21) {
               this.a(this.redo, this.undo);
               return;
            }

            if (var2 == 45 && !this.selected.isEmpty()) {
               long var7 = System.currentTimeMillis();

               for (com.cheatbreaker.client.kazerui.hud.HudFeature var6 : this.selected) {
                  this.b(var6, var7);
                  this.b().b(var6.a(), "enabled", "false");
               }

               this.selected.clear();
               this.features.b();
               return;
            }
         }

         int var3 = var2 == 203 ? -1 : (var2 == 205 ? 1 : 0);
         int var4 = var2 == 200 ? -1 : (var2 == 208 ? 1 : 0);
         this.a(var3, var4);
      } else {
         this.a();
      }
   }

   public void updateScreen() {
      if (this.mc.theWorld == null) {
         com.cheatbreaker.client.kazerui.gui.menus.HomeScreen.d();
      }

      if (this.selected.isEmpty()) {
         this.nudgeTicks = 0;
         this.nudging = false;
      } else {
         int var1 = 0;
         int var2 = 0;
         if (Keyboard.isKeyDown(203)) {
            var1--;
         }

         if (Keyboard.isKeyDown(205)) {
            var1++;
         }

         if (Keyboard.isKeyDown(200)) {
            var2--;
         }

         if (Keyboard.isKeyDown(208)) {
            var2++;
         }

         if (var1 == 0 && var2 == 0) {
            this.nudgeTicks = 0;
            if (this.nudging) {
               this.nudging = false;

               for (com.cheatbreaker.client.kazerui.hud.HudFeature var4 : this.selected) {
                  this.features.b(var4, this.res);
               }

               this.features.b();
            }
         } else {
            if (++this.nudgeTicks > 10) {
               this.a(this.nudgeTicks > 20 ? var1 * 2 : var1, this.nudgeTicks > 20 ? var2 * 2 : var2);
            }
         }
      }
   }

   private void a(int var1, int var2) {
      if ((var1 != 0 || var2 != 0) && !this.selected.isEmpty()) {
         float var3 = var1;
         float var4 = var2;

         for (com.cheatbreaker.client.kazerui.hud.HudFeature var6 : this.selected) {
            float[] var7 = this.features.a(var6, this.res);
            var3 = Math.max(var3, 2.0F - var7[0]);
            var3 = Math.min(var3, this.width - 2.0F - var7[0] - var7[2]);
            var4 = Math.max(var4, 2.0F - var7[1]);
            var4 = Math.min(var4, this.height - 2.0F - var7[1] - var7[3]);
         }

         if (var3 != 0.0F || var4 != 0.0F) {
            long var12 = System.currentTimeMillis();

            for (com.cheatbreaker.client.kazerui.hud.HudFeature var8 : this.selected) {
               if (!this.nudging) {
                  this.b(var8, var12);
               }

               float[] var9 = this.features.a(var8, this.res);
               this.features.a(var8, var9[0] + var3, var9[1] + var4, this.res);
            }

            this.nudging = true;
         }
      }
   }

   private void b(String var1) {
      this.mc.displayGuiScreen(new com.cheatbreaker.client.kazerui.gui.KazerSettingsScreen(this.features, this.openKey, this.background));
      if (var1 != null) {
         this.b().i("options:" + var1);
      }
   }

   private void c(String var1) {
      com.cheatbreaker.client.kazerui.gui.ToastNotifications.a(var1);
   }

   private com.cheatbreaker.client.kazerui.gui.HudMoveScreen.a a(com.cheatbreaker.client.kazerui.hud.HudFeature var1, long var2) {
      String var4 = var1.a();
      return new com.cheatbreaker.client.kazerui.gui.HudMoveScreen.a(
         var4,
         this.b().a(var4, "anchor", com.cheatbreaker.client.kazerui.gui.components.MenuModel.b(var4, "anchor")),
         this.b().a(var4, "x", com.cheatbreaker.client.kazerui.gui.components.MenuModel.b(var4, "x")),
         this.b().a(var4, "y", com.cheatbreaker.client.kazerui.gui.components.MenuModel.b(var4, "y")),
         this.b().a(var4, "scale", com.cheatbreaker.client.kazerui.gui.components.MenuModel.b(var4, "scale")),
         this.features.b(var4),
         var2
      );
   }

   private void b(com.cheatbreaker.client.kazerui.hud.HudFeature var1, long var2) {
      this.a(this.a(var1, var2));
   }

   private void a(com.cheatbreaker.client.kazerui.gui.HudMoveScreen.a var1) {
      this.redo.clear();
      this.undo.add(var1);

      while (this.undo.size() > 50) {
         this.undo.remove(0);
      }
   }

   private void k() {
      for (com.cheatbreaker.client.kazerui.gui.HudMoveScreen.a var2 : this.pending) {
         com.cheatbreaker.client.kazerui.hud.HudFeature var3 = this.d(var2.id);
         if (var3 != null) {
            com.cheatbreaker.client.kazerui.gui.HudMoveScreen.a var4 = this.a(var3, var2.time);
            if (!var4.anchor.equals(var2.anchor) || !var4.x.equals(var2.x) || !var4.y.equals(var2.y) || !var4.scale.equals(var2.scale)) {
               this.a(var2);
            }
         }
      }

      this.pending.clear();
   }

   private void a(List<com.cheatbreaker.client.kazerui.gui.HudMoveScreen.a> var1, List<com.cheatbreaker.client.kazerui.gui.HudMoveScreen.a> var2) {
      if (!var1.isEmpty()) {
         long var3 = ((com.cheatbreaker.client.kazerui.gui.HudMoveScreen.a)var1.get(var1.size() - 1)).time;
         Iterator var5 = var1.iterator();

         while (var5.hasNext()) {
            com.cheatbreaker.client.kazerui.gui.HudMoveScreen.a var6 = (com.cheatbreaker.client.kazerui.gui.HudMoveScreen.a)var5.next();
            if (var6.time == var3) {
               com.cheatbreaker.client.kazerui.hud.HudFeature var7 = this.d(var6.id);
               var5.remove();
               if (var7 != null) {
                  var2.add(this.a(var7, var3));
                  this.b().b(var6.id, "anchor", var6.anchor);
                  this.b().b(var6.id, "x", var6.x);
                  this.b().b(var6.id, "y", var6.y);
                  this.b().b(var6.id, "scale", var6.scale);
                  this.b().b(var6.id, "enabled", "" + var6.enabled);
               }
            }
         }

         this.features.b();
      }
   }

   private com.cheatbreaker.client.kazerui.hud.HudFeature d(String var1) {
      for (com.cheatbreaker.client.kazerui.hud.HudFeature var3 : this.features.d()) {
         if (var3.a().equals(var1)) {
            return var3;
         }
      }

      return null;
   }

   public void onGuiClosed() {
      this.features.b();
      this.gfx.a();
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   private static final class a {
      final String id;
      final String anchor;
      final String x;
      final String y;
      final String scale;
      final boolean enabled;
      final long time;

      a(String var1, String var2, String var3, String var4, String var5, boolean var6, long var7) {
         this.id = var1;
         this.anchor = var2;
         this.x = var3;
         this.y = var4;
         this.scale = var5;
         this.enabled = var6;
         this.time = var7;
      }
   }
}
