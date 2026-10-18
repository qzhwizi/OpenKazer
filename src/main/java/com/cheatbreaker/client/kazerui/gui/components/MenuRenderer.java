package com.cheatbreaker.client.kazerui.gui.components;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;

public final class MenuRenderer implements com.cheatbreaker.client.kazerui.gui.components.MenuCanvas {
   public static final int WIDTH = 492;
   public static final int HEIGHT = 303;
   public static final int PAD = 2;
   private static final int WHITE = -1;
   private final Font bold;
   private final Font light;
   private final Font medium;
   private static final Map<String, BufferedImage> images = Collections.synchronizedMap(new LinkedHashMap<String, BufferedImage>(64, 0.75F, true) {
      @Override
      protected boolean removeEldestEntry(Entry<String, BufferedImage> var1) {
         return this.size() > 256;
      }
   });
   private static final Map<String, Font> FONTS = new ConcurrentHashMap<>();
   public final List<com.cheatbreaker.client.kazerui.gui.components.MenuRenderer.b> hits = new ArrayList<>();
   public float maxScroll;
   public float contentHeight;
   public final List<com.cheatbreaker.client.kazerui.gui.components.MenuWidget> shownWidgets = new ArrayList<>();
   public boolean animating;
   public String caretOwner;
   public float caretX;
   public float caretY;
   public float caretHeight;
   private final Map<String, long[]> fades = new HashMap<>();
   private final Map<String, long[]> slides = new HashMap<>();
   private String builtPage = null;
   public float scrollTop = 62.5F;
   public float scrollHeight = 235.0F;
   private Graphics2D g;
   private BufferedImage canvas;
   private com.cheatbreaker.client.kazerui.gui.components.MenuModel model;
   private float mx;
   private float my;
   private boolean suppressHover;
   private boolean blink;
   private String cosmeticSearch;
   private com.cheatbreaker.client.kazerui.concurrent.CosmeticCategory cosmeticCategory;
   private final List<com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry> cosmeticChoices = new ArrayList<>();
   private static final float PX = 110.0F;
   private static final float PY = 42.5F;
   private static final float PW = 377.0F;
   private static final float PH = 255.0F;
   private float clipTop = 62.5F;
   private final Map<String, com.cheatbreaker.client.kazerui.gui.components.SliderMath.a> knobs = new HashMap<>();
   private long confirmSince;
   private String confirmShown = "";
   private static final int[] PRESETS = new int[]{
      -5636096, -43691, -22016, -171, -16733696, -11141291, -11141121, -16733526, -16777046, -11184641, -43521, -5635926
   };
   private boolean settingsContent;
   private static final Map<com.cheatbreaker.client.kazerui.gui.components.MenuRenderer.c, Area> rings = Collections.synchronizedMap(
      new LinkedHashMap<com.cheatbreaker.client.kazerui.gui.components.MenuRenderer.c, Area>(128, 0.75F, true) {
         @Override
         protected boolean removeEldestEntry(Entry<com.cheatbreaker.client.kazerui.gui.components.MenuRenderer.c, Area> var1) {
            return this.size() > 128;
         }
      }
   );
   private static final Map<String, com.cheatbreaker.client.kazerui.gui.components.MenuRenderer.a> glyphs = new ConcurrentHashMap<>();
   private int rasterScale = 2;

   private void a(String var1, float var2, float var3, float var4) {
      this.caretOwner = var1;
      this.caretX = var2;
      this.caretY = var3;
      this.caretHeight = var4;
   }

   public MenuRenderer() {
      this.bold = this.b("roboto-bold.ttf");
      this.light = this.b("roboto-light.ttf");
      this.medium = this.b("roboto-medium.ttf");
   }

   private Font b(String var1) {
      Font var2 = FONTS.get(var1);
      if (var2 != null) {
         return var2;
      } else {
         try (InputStream var3 = this.c("fonts/" + var1)) {
            Font var5 = Font.createFont(0, var3);
            FONTS.put(var1, var5);
            return var5;
         } catch (Exception var18) {
            throw new IllegalStateException("Missing original Kazer font: " + var1, var18);
         }
      }
   }

   private InputStream c(String var1) {
      return com.cheatbreaker.client.kazerui.gui.components.MenuRenderer.class.getResourceAsStream("/assets/kazerui/ui/" + var1);
   }

   public BufferedImage a(com.cheatbreaker.client.kazerui.gui.components.MenuModel var1, float var2, float var3, int var4, boolean var5) {
      if (var4 >= 1 && var4 <= 4) {
         this.rasterScale = var4;
         this.model = var1;
         this.mx = var2;
         this.my = var3;
         this.blink = var5;
         this.hits.clear();
         this.maxScroll = 0.0F;
         this.contentHeight = 0.0F;
         this.animating = false;
         this.shownWidgets.clear();
         this.caretOwner = null;
         this.scrollTop = 62.5F;
         this.scrollHeight = 235.0F;
         int var6 = 496 * var4;
         int var7 = 307 * var4;
         if (this.canvas == null || this.canvas.getWidth() != var6 || this.canvas.getHeight() != var7) {
            this.canvas = new BufferedImage(var6, var7, 2);
         }

         BufferedImage var8 = this.canvas;
         this.g = var8.createGraphics();
         this.g.setComposite(AlphaComposite.Clear);
         this.g.fillRect(0, 0, var6, var7);
         this.g.setComposite(AlphaComposite.SrcOver);
         this.g.scale(var4, var4);
         this.g.translate(2, 2);
         this.g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         this.g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
         this.g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
         this.g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
         String var9 = this.model.tab + "|" + this.model.selected + "|" + this.model.settingsTab;
         if (!var9.equals(this.builtPage)) {
            this.slides.clear();
            this.knobs.clear();
            this.builtPage = var9;
         }

         this.suppressHover = !this.model.keybindPopup.isEmpty();
         if (!this.model.editor.isEmpty()) {
            if (!this.model.confirm.isEmpty()) {
               this.g();
            } else {
               this.f();
            }
         } else {
            this.b();
            if (!this.model.selected.isEmpty()) {
               this.e();
            } else if (this.model.tab.equals("MODS")) {
               this.d();
            } else if (this.model.tab.equals("COSMETICS")) {
               this.c();
            } else {
               this.h();
            }
         }

         this.g.dispose();
         this.g = null;
         return var8;
      } else {
         throw new IllegalArgumentException("UI raster scale must be 1..4");
      }
   }

   private void b() {
      this.b(-1.0F, 0.0F, 494.0F, 303.0F, 4.0F, 1612586526);
      this.b(0.0F, 1.0F, 492.0F, 301.0F, 3.25F, 1075715614);
      this.a(0.0F, 1.0F, 492.0F, 301.0F, 5.0F, this.model.a("global", "uiTransparency", true) ? -1306912744 : -15067112);
      this.a(0.0F, 1.0F, 492.0F, 32.0F, 5.0F, 905969664, true, true, false, false);
      this.a(0.0F, 32.5F, 492.0F, 0.5F, 553648127);
      this.a(0.0F, 283.0F, 105.0F, 19.0F, 7.0F, 536870912, false, true, true, false);
      this.a(0.0F, 42.0F, 105.0F, 233.0F, 8.0F, 536870912, false, true, false, true);
      this.a("logo/kazer.png", 9.0F, 6.0F, 20.0F, 20.0F, 1.0F);
      this.b("KAZER", this.bold, 10.0F, 32.0F, 10.0F, -1);
      this.b("CLIENT", this.light, 10.0F, 66.0F, 10.0F, -1);
      String[] var1 = new String[]{"MODS", "SETTINGS", "COSMETICS"};
      float var2 = 0.0F;

      for (String var6 : var1) {
         var2 += this.a(this.h(d(var6)), this.bold, 7.0F) + 16.0F + 8.0F;
      }

      float var15 = Math.max(128.0F, 246.0F - (var2 - 8.0F) / 2.0F);

      for (String var7 : var1) {
         float var8 = this.a(this.h(d(var7)), this.bold, 7.0F) + 16.0F;
         this.a(var15, 8.0F, var8, 16.0F, this.h(d(var7)), "tab:" + var7, this.model.tab.equals(var7), 0, "");
         var15 += var8 + 8.0F;
      }

      boolean var17 = this.b(468.0F, 8.0F, 16.0F, 16.0F);
      this.a(468.0F, 8.0F, 16.0F, 16.0F, 4.0F, 1076176165, 553648127, this.a("close", var17, 553648127, 1358954495));
      this.b("icons/mainmenu/exit-17x17.png", 472.0F, 12.0F, 8.0F, 8.0F, this.a("closeIcon", var17, 1358954495, -1342177281));
      this.b(468.0F, 8.0F, 16.0F, 16.0F, "close", "Close");
      int var19 = 0;
      this.model.profileOffset = Math.max(0, Math.min(this.model.profileOffset, Math.max(0, this.model.profiles.size() - 13)));

      for (int var21 = this.model.profileOffset; var21 < Math.min(this.model.profiles.size(), this.model.profileOffset + 13); var21++) {
         String var23 = this.model.profiles.get(var21);
         float var25 = 43 + var19 * 16;
         boolean var9 = var19++ == 0;
         boolean var10 = var23.equals(this.model.a());
         if (var10) {
            this.a(0.0F, var25 - 1.0F, 105.0F, 16.0F, 8.0F, 553648127, false, var9, false, false);
         }

         String var11 = this.model.a(var23);
         this.b(0.0F, var25, 104.0F, 14.0F, 5.0F, 553648127, false, var9, false, false);
         boolean var12 = this.b(95.0F, var25, 9.0F, 14.0F);
         this.a("icons/pencil-64.png", 95.0F, var25 + 3.5F, 7.0F, 7.0F, (var12 ? 92 : 51) / 255.0F);
         String var13 = this.model.b(var23);
         float var14 = 8.0F;
         if (!var13.isEmpty()) {
            this.a("icons/profiles/" + var13 + ".png", 2.0F, var25 + 3.5F, 8.0F, 8.0F, 0.6509804F);
            var14 += 6.0F;
         }

         this.b(this.b(this.h(var11) + " ", this.bold, 6.0F, 93.0F - var14), this.bold, 6.0F, var14, var25 + 3.5F, -1342177281);
         this.b(0.0F, var25 - 1.0F, 95.0F, 16.0F, "profile:" + var23, "");
         this.b(95.0F, var25, 9.0F, 14.0F, "editProfile:" + var23, "");
      }

      this.a(
         8.0F,
         266.0F,
         88.0F,
         15.0F,
         "+ NEW PROFILE",
         "newProfile",
         false,
         1,
         "Copy current settings into a new profile. Scroll the profile list with the mouse wheel."
      );
      boolean var22 = this.b(8.0F, 287.5F, 88.0F, 11.0F);
      this.a(8.0F, 287.5F, 88.0F, 11.0F, 4.0F, 1621271202, 1621271202, this.a("editHud", var22, -11040558, -11561732));
      float var24 = 293.0F - this.b(this.bold, 5.0F);
      String var26 = this.h(com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("gui.components", "editHudLayout"));
      this.c(var26, this.bold, 5.0F, 53.0F, var24, 536870912);
      this.c(var26, this.bold, 5.0F, 52.0F, var24 - 1.0F, -1);
      this.b(8.0F, 287.5F, 88.0F, 11.0F, "editHud", "Drag and resize your enabled HUD modules");
   }

   private void c() {
      this.b("CLOAKS & ACCESSORIES", this.bold, 7.0F, 116.0F, 47.0F, -1);
      this.a(253.0F, 43.5F, 90.0F, 14.0F, "UNEQUIP ALL", "cosmeticsClear", false, 1, "");
      this.a(353.0F, 43.5F, 130.0F, 14.0F);
      float var1 = 114.0F;

      for (com.cheatbreaker.client.kazerui.concurrent.CosmeticCategory var5 : com.cheatbreaker.client.kazerui.concurrent.CosmeticCategory.a()) {
         this.a(var1, 63.0F, 117.0F, 14.0F, var5.label, "cosmeticCategory:" + var5.name(), this.model.cosmeticCategory == var5, 1, "");
         var1 += 123.0F;
      }

      if (!this.model.search.equals(this.cosmeticSearch) || this.cosmeticCategory != this.model.cosmeticCategory) {
         this.cosmeticCategory = this.model.cosmeticCategory;
         this.cosmeticSearch = this.model.search;
         this.cosmeticChoices.clear();
         String var10 = a(this.model.search);

         for (com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var14 : com.cheatbreaker.client.kazerui.concurrent.CosmeticCatalog.a()) {
            if (com.cheatbreaker.client.kazerui.concurrent.CosmeticCategory.a(var14.type) == this.cosmeticCategory
               && var14.a()
               && (var10.isEmpty() || a(var14.name + " " + var14.type.b()).contains(var10))) {
               this.cosmeticChoices.add(var14);
            }
         }

         this.model.cosmeticPreview = this.cosmeticChoices.isEmpty() ? -1 : this.cosmeticChoices.get(0).id;
      }

      this.scrollTop = 82.5F;
      this.scrollHeight = 215.0F;
      com.cheatbreaker.client.kazerui.gui.components.ScrollPhysics var11 = this.model.scroller;
      var11.height = this.scrollHeight;
      var11.content = 28.0F * this.cosmeticChoices.size() + 6.0F;
      var11.a();
      this.contentHeight = var11.content;
      this.maxScroll = Math.max(0.0F, var11.content - var11.height);
      Shape var13 = this.g.getClip();
      this.g.clip(new Float(108.0F, this.scrollTop, 249.0F, this.scrollHeight));
      this.clipTop = this.scrollTop;
      this.settingsContent = true;

      for (int var15 = Math.max(0, (int)(var11.offset / 28.0F) - 1); var15 < this.cosmeticChoices.size(); var15++) {
         float var17 = 85.5F + var15 * 28 - var11.offset;
         if (var17 > 297.5F) {
            break;
         }

         com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var6 = this.cosmeticChoices.get(var15);
         boolean var7 = com.cheatbreaker.client.kazerui.concurrent.EquippedCosmetics.a(var6);
         boolean var8 = com.cheatbreaker.client.kazerui.concurrent.CosmeticEntitlements.a(var6);
         this.a(114.0F, var17, 239.0F, 24.0F, 4.0F, var7 ? 1077197435 : 553648127);
         boolean var9 = this.model.cosmeticPreview == var6.id;
         this.b(this.b(var6.name, this.medium, 8.0F, 105.0F), this.medium, 8.0F, 121.0F, var17 + 4.0F, -1);
         this.b(var6.type.b(), this.light, 6.0F, 121.0F, var17 + 15.0F, -5131328);
         this.c(114.0F, var17, 116.0F, 24.0F, "previewCosmetic:" + var6.id, "Preview this item");
         this.a(233.0F, var17 + 5.0F, 40.0F, 14.0F, var9 ? "VIEWING" : "VIEW", "previewCosmetic:" + var6.id, var9, 1, "Preview without equipping");
         this.a(
            279.0F,
            var17 + 5.0F,
            65.0F,
            14.0F,
            var7 ? "EQUIPPED" : (var8 ? "EQUIP" : "LOCKED"),
            (var8 ? "cosmetic:" : "previewCosmetic:") + var6.id,
            var7,
            1,
            var8 ? "" : "UUID authorization required"
         );
      }

      this.settingsContent = false;
      this.clipTop = 62.5F;
      this.g.setClip(var13);
      this.a(355.0F);
      this.a(364.0F, 84.0F, 119.0F, 213.0F, 6.0F, 536870912);
      com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var16 = com.cheatbreaker.client.kazerui.concurrent.CosmeticCatalog.a(this.model.cosmeticPreview);
      if (var16 != null) {
         this.c(this.b(var16.name, this.medium, 7.0F, 109.0F), this.medium, 7.0F, 423.5F, 264.0F, -1);
         this.c(
            com.cheatbreaker.client.kazerui.concurrent.EquippedCosmetics.a(var16)
               ? "Equipped"
               : (com.cheatbreaker.client.kazerui.concurrent.CosmeticEntitlements.a(var16) ? "Preview" : "UUID authorization required"),
            this.light,
            6.0F,
            423.5F,
            279.0F,
            -5131328
         );
      }
   }

   private void d() {
      float var1 = 110.0F;

      for (String var5 : new String[]{"ALL", "HUD", "SERVER", "MECHANIC"}) {
         float var6 = this.a(this.h(d(var5)), this.bold, 7.0F) + 16.0F;
         this.a(var1, 43.5F, var6, 14.0F, this.h(d(var5)), "category:" + var5, this.model.category.equals(var5), 1, "");
         var1 += var6 + 4.0F;
      }

      this.a(var1, 43.5F, 14.0F, 14.0F, this.model.d() ? "icons/large-menu-24x24.png" : "icons/compact-menu-24x24.png", "compact", "");
      var1 += 16.0F;
      this.a(var1, 43.5F, 14.0F, 14.0F, "icons/sort_icons/" + com.cheatbreaker.client.kazerui.gui.components.MenuModel.SORTS[this.model.e()] + ".png", "sort", "");
      var1 += 16.0F;
      this.a(var1, 43.5F, 485.0F - var1, 14.0F);
      List var12 = this.model.f();
      float var13 = this.model.d() ? 22.0F : 112.0F;
      int var14 = Math.max(0, (var12.size() - 1) / 3);
      com.cheatbreaker.client.kazerui.gui.components.ScrollPhysics var15 = this.model.scroller;
      var15.height = 235.0F;
      var15.content = var14 == 0 ? 119.0F : 4.0F + var13 + (var13 + 8.0F) * var14;
      var15.a();
      this.scrollTop = 62.5F;
      this.scrollHeight = 235.0F;
      this.contentHeight = var15.content;
      this.maxScroll = Math.max(0.0F, var15.content - var15.height);
      Shape var16 = this.g.getClip();
      this.g.clip(new Float(108.0F, 62.5F, 381.0F, 240.0F));

      for (int var7 = 0; var7 < var12.size(); var7++) {
         float var8 = 110 + var7 % 3 * 123;
         float var9 = 64.5F + var7 / 3 * (var13 + 8.0F) - var15.offset;
         if (!(var9 + var13 < 62.5F) && !(var9 > 297.5F)) {
            if (this.model.d()) {
               this.b((com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a)var12.get(var7), var8, var9);
            } else {
               this.a((com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a)var12.get(var7), var8, var9);
            }
         }
      }

      this.g.setClip(var16);
      this.a(481.0F);
   }

   private static String d(String var0) {
      return com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("gui.components", var0.toLowerCase(Locale.ROOT)).toUpperCase(Locale.ROOT);
   }

   private void a(com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a var1, float var2, float var3) {
      boolean var4 = this.b(var2, var3, 115.0F, 112.0F) && this.my >= 62.5F && this.my < 302.5F;
      this.a(var2, var3, 115.0F, 112.0F, 7.0F, this.a("card:" + var1.id, var4, 542594903, 1348953959));
      this.b(var2, var3, 115.0F, 112.0F, 6.0F, 551740130);
      this.a("icons/features/" + var1.icon + "-52x52.png", var2 + 44.5F, var3 + 16.0F, 26.0F, 26.0F, 0.8509804F);
      this.c(com.cheatbreaker.client.kazerui.gui.components.KazerLang.b(var1.id, var1.name), this.light, 11.0F, var2 + 57.5F, var3 + 53.0F, -8487555);
      boolean var5 = this.b(var2, var3 + 72.0F, 115.0F, 20.0F) && var4;
      this.a(var2, var3 + 72.0F, 115.0F, 20.0F, this.a("options:" + var1.id, var5, 553648127, 1358954495));
      this.a(var2, var3 + 73.0F, 1.0F, 18.0F, 553648127);
      this.a(var2 + 114.0F, var3 + 73.0F, 1.0F, 18.0F, 553648127);
      this.a(var2 + 94.0F, var3 + 73.0F, 1.0F, 18.0F, 553648127);
      this.a(var2, var3 + 72.0F, 115.0F, 1.0F, 553648127);
      this.a(var2, var3 + 91.0F, 115.0F, 1.0F, 553648127);
      this.a("icons/mainmenu/cog-20x20.png", var2 + 100.0F, var3 + 77.5F, 9.0F, 9.0F, 1.0F);
      this.c(this.h(d("options")), this.bold, 7.0F, var2 + 47.0F, var3 + 78.0F, -1);
      this.c(var2, var3 + 72.0F, 115.0F, 20.0F, "options:" + var1.id, "Options");
      boolean var6 = this.model.a(var1);
      boolean var7 = this.b(var2, var3 + 92.0F, 115.0F, 20.0F) && var4;
      this.a(
         var2,
         var3 + 92.0F,
         115.0F,
         20.0F,
         10.0F,
         var6 ? this.a("toggle:" + var1.id, var7, -1356212614, -14035334) : this.a("toggle:" + var1.id, var7, -1344396974, -2219694),
         false,
         false,
         true,
         true
      );
      this.b(var2 + 1.0F, var3 + 93.0F, 113.0F, 18.0F, 5.0F, 905969663, false, false, true, true);
      this.c(this.h(d(var6 ? "enabled" : "disabled")), this.bold, 7.0F, var2 + 57.5F, var3 + 98.0F, -1);
      float var10002 = var3 + 92.0F;
      String var10005 = "toggle:" + var1.id;
      var1.getClass();
      this.c(var2, var10002, 115.0F, 20.0F, var10005, "Toggle " + var1.name);
      if (var4 || this.model.b(var1)) {
         boolean var8 = this.b(var2 + 3.0F, var3 + 3.0F, 11.0F, 11.0F);
         int var9 = this.model.b(var1) ? (var8 ? -8392 : -14270) : (var8 ? -1 : -5131328);
         this.b("icons/" + (this.model.b(var1) ? "star-filled-64x64.png" : "star-64x64.png"), var2 + 4.0F, var3 + 4.0F, 9.0F, 9.0F, var9);
         this.c(var2 + 3.0F, var3 + 3.0F, 11.0F, 11.0F, "favorite:" + var1.id, this.model.b(var1) ? "Remove favorite" : "Favorite");
      }
   }

   private void b(com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a var1, float var2, float var3) {
      boolean var4 = this.b(var2, var3, 115.0F, 22.0F) && this.my >= 62.5F && this.my < 302.5F;
      this.a(var2, var3, 115.0F, 22.0F, this.a("card:" + var1.id, var4, 542594903, 1348953959));
      this.b(
         var2,
         var3,
         115.0F,
         22.0F,
         3.0F,
         this.model.a(var1) ? this.a("ring:" + var1.id, var4, 1881790074, -1339435398) : this.a("ring:" + var1.id, var4, 1893605714, -1327619758)
      );
      this.a(
         "icons/features/" + var1.icon + "-20x20.png", var2 + 6.0F, var3 + 6.0F, 10.0F, 10.0F, (a(96, 175, this.a("icon:" + var1.id, var4)) & 0xFF) / 255.0F
      );
      this.b(com.cheatbreaker.client.kazerui.gui.components.KazerLang.b(var1.id, var1.name), this.light, 7.0F, var2 + 20.0F, var3 + 7.0F, -4868683);
      String var10005 = "toggle:" + var1.id;
      var1.getClass();
      this.c(var2, var3, 93.0F, 22.0F, var10005, "Toggle " + var1.name);
      boolean var5 = this.b(var2 + 93.0F, var3, 22.0F, 22.0F) && var4;
      this.a("icons/mainmenu/cog-20x20.png", var2 + 99.0F, var3 + 6.0F, 9.0F, 9.0F, (a(160, 255, this.a("options:" + var1.id, var5)) & 0xFF) / 255.0F);
      this.a(var2 + 93.0F, var3 + 6.0F, 0.5F, 10.0F, 536870911);
      this.c(var2 + 93.0F, var3, 22.0F, 22.0F, "options:" + var1.id, "Options");
   }

   private void e() {
      com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a var1 = null;

      for (com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a var3 : com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.ALL) {
         if (var3.id.equals(this.model.selected)) {
            var1 = var3;
         }
      }

      if (var1 == null) {
         this.model.selected = "";
      } else {
         com.cheatbreaker.client.kazerui.gui.components.OptionTree.b var10 = com.cheatbreaker.client.kazerui.gui.components.OptionTree.a(var1.id);
         this.a(110.0F, 42.5F, 377.0F, 25.0F, 8.0F, 889192448, true, true, false, false);
         this.a(110.0F, 67.5F, 377.0F, 230.0F, 12.0F, 536870912, false, false, true, true);
         this.a(110.0F, 66.5F, 377.0F, 1.0F, 553648127);
         this.b(this.h(com.cheatbreaker.client.kazerui.gui.components.KazerLang.b(var1.id, var1.name).toUpperCase(Locale.ROOT)), this.bold, 9.0F, 140.0F, 49.5F, -1);
         this.a("icons/settings/arrow-left-32x32.png", 117.0F, 46.5F, 16.0F, 16.0F, 1.0F);
         this.b(114.0F, 45.5F, 21.0F, 18.0F, "back", "");
         float var11 = 465.0F;
         this.a(var11, 48.5F, 14.0F, 14.0F, "icons/reset-settings-24x24.png", "resetModule", "");
         if (var10.hud) {
            var11 -= 18.0F;
            this.a(var11, 48.5F, 14.0F, 14.0F, "icons/reset-position-24x24.png", "resetPosition", "");
         }

         if (var10.keybind) {
            var11 -= 18.0F;
            this.a(var11, 48.5F, 14.0F, 14.0F, "icons/keybind-24x24.png", "keybindPopup:" + var1.id, "");
         }

         this.a(var11 - 4.0F - 100.0F, 48.5F, 100.0F, 14.0F);
         float var4 = 0.0F;
         String var5 = com.cheatbreaker.client.kazerui.gui.components.KazerLang.a(var1.id);
         if (var5 != null) {
            List var6 = this.a(var5, this.light, 6.0F, 365.0F);

            for (int var7 = 0; var7 < var6.size(); var7++) {
               this.b((String)var6.get(var7), this.light, 6.0F, 116.0F, 70.5F + var7 * 8 + 0.5F, -1);
            }

            var4 = var6.size() * 8 + 8;
            this.a(110.0F, 66.5F, 377.0F, var4, 352321536);
            this.a(110.0F, 67.5F + var6.size() * 8 + 8.0F, 377.0F, 1.0F, 553648127);
         }

         float var12 = 69.5F + var4;
         this.scrollTop = var12;
         this.scrollHeight = 215.0F - var4;
         List var13 = this.b(var10.options, var1.id);
         float var8 = this.a(var13, var1.id, 110.0F, var12, 347.0F, 0.0F, 0.0F, true, false) - var12 + 4.0F;
         this.contentHeight = var8;
         this.maxScroll = Math.max(0.0F, var8 - this.scrollHeight);
         this.model.scroller.content = var8;
         this.model.scroller.height = this.scrollHeight;
         this.model.scroller.a();
         Shape var9 = this.g.getClip();
         this.g.clip(new Float(110.0F, 68.5F + var4, 377.0F, 229.0F - var4));
         this.clipTop = 68.5F + var4;
         this.a(var13, var1.id, 110.0F, var12 - this.model.scroller.offset, 347.0F, 0.0F, 0.0F, true, true);
         this.clipTop = 62.5F;
         this.g.setClip(var9);
         this.a(481.0F);
         if (!this.model.keybindPopup.isEmpty()) {
            this.a(var1);
         }
      }
   }

   private List<com.cheatbreaker.client.kazerui.gui.components.OptionTree.a> b(List<com.cheatbreaker.client.kazerui.gui.components.OptionTree.a> var1, String var2) {
      if (this.model.search.isEmpty()) {
         return var1;
      } else {
         String var3 = a(this.model.search);
         ArrayList var4 = new ArrayList();

         for (com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var6 : var1) {
            if (var6.type.equals("section")) {
               if (!this.b(var6.children, var2).isEmpty()) {
                  var4.add(var6);
               }
            } else if (this.a(var6, var3)) {
               var4.add(var6);
            }
         }

         return var4;
      }
   }

   private boolean a(com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var1, String var2) {
      String var3 = this.a(var1).toLowerCase(Locale.ROOT);
      if (var3.contains(var2)) {
         return true;
      } else {
         for (String var7 : var3.split(" ")) {
            if (a(var7).startsWith(var2)) {
               return true;
            }
         }

         for (com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var9 : var1.children) {
            if (!var9.type.equals("section") && this.a(var9, var2)) {
               return true;
            }
         }

         return false;
      }
   }

   static String a(String var0) {
      return Normalizer.normalize(var0, Form.NFD).replaceAll("[^A-Za-z0-9.]", "").toLowerCase(Locale.ROOT);
   }

   private String a(com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var1) {
      if (var1.type.equals("child")) {
         return com.cheatbreaker.client.kazerui.gui.components.KazerLang.b(var1.key, var1.key);
      } else if (var1.type.equals("section")) {
         return com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("settings.labels", var1.key);
      } else {
         return var1.text != null ? var1.text : com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("settings", var1.key);
      }
   }

   private String b(com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var1, String var2) {
      return var1.key.toLowerCase(Locale.ROOT);
   }

   private String a(String var1, com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var2) {
      return this.model.a(var1, var2.key, var2.value);
   }

   private boolean c(com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var1, String var2) {
      if (var1.transient_) {
         return true;
      } else if (var1.hideWhen != null && var1.hideWhen.getAsBoolean()) {
         return true;
      } else if (var1.type.equals("child")) {
         return false;
      } else if (var1.type.equals("section")) {
         for (com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var4 : var1.children) {
            if (!this.c(var4, var2)) {
               return false;
            }
         }

         return true;
      } else {
         return var1.hide != null && new com.cheatbreaker.client.kazerui.gui.components.MenuRenderer.d(var1.hide, var2).a();
      }
   }

   private static com.cheatbreaker.client.kazerui.gui.components.OptionTree.a c(List<com.cheatbreaker.client.kazerui.gui.components.OptionTree.a> var0, String var1) {
      for (com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var3 : var0) {
         if (var1.equals(var3.key) && var3.type.equals("option")) {
            return var3;
         }

         if (!var3.type.equals("child")) {
            com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var4 = c(var3.children, var1);
            if (var4 != null) {
               return var4;
            }
         }
      }

      return null;
   }

   private boolean d(com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var1, String var2) {
      if (var1.type.equals("child")) {
         return !this.model.expanded.contains(var2 + "." + var1.key);
      } else {
         return var1.type.equals("option") && var1.kind.equals("bool")
            ? var1.children.isEmpty() || !this.model.expanded.contains(var2 + "." + var1.key)
            : false;
      }
   }

   private float a(
      List<com.cheatbreaker.client.kazerui.gui.components.OptionTree.a> var1, String var2, float var3, float var4, float var5, float var6, float var7, boolean var8, boolean var9
   ) {
      int var10 = 0;
      float var11 = 0.0F;
      ArrayList var12 = new ArrayList();

      for (com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var14 : var1) {
         if (!this.c(var14, var2) && (!var14.type.equals("option") || b(var14))) {
            var12.add(var14);
         }
      }

      for (int var19 = 0; var19 < var12.size(); var19++) {
         com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var20 = (com.cheatbreaker.client.kazerui.gui.components.OptionTree.a)var12.get(var19);
         boolean var15 = this.d(var20, var2);
         if (var10 == 2) {
            var10 = 0;
            var4 += var11;
            var11 = 0.0F;
         }

         if (!var15) {
            var10 = 0;
            var4 += var11;
            var11 = 0.0F;
         }

         float var16 = var3 + (var8 ? (var20.type.equals("section") ? 0 : 15) : var6) + var10 * (var5 / 2.0F);
         float var17 = (var15 ? var5 / 2.0F : var5) - var7;
         float var18 = this.a(var20, var2, var16, var4, var17, var9);
         var11 = Math.max(var11, var18);
         if (!var15) {
            var4 += var11;
            var11 = 0.0F;
         } else {
            if (var19 == var12.size() - 1) {
               var4 += var11;
            }

            var10++;
         }
      }

      return var4;
   }

   private static boolean b(com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var0) {
      return var0.kind.equals("number")
         ? !java.lang.Float.isNaN(var0.min) && !java.lang.Float.isNaN(var0.max)
         : var0.kind.equals("bool")
            || var0.kind.equals("color")
            || var0.kind.equals("enum")
            || var0.kind.equals("key")
            || var0.kind.equals("keycombo")
            || var0.kind.equals("string")
            || var0.kind.equals("button")
            || var0.kind.equals("widget") && var0.widget != null;
   }

   private float a(com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var1, String var2, float var3, float var4, float var5, boolean var6) {
      if (var1.type.equals("section")) {
         return this.b(var1, var2, var3, var4, var5, var6);
      } else if (var1.type.equals("child")) {
         return this.e(var1, var2, var3, var4, var5, var6);
      } else if (var1.kind.equals("bool")) {
         return var1.children.isEmpty() ? this.c(var1, var2, var3, var4, var5, var6) : this.d(var1, var2, var3, var4, var5, var6);
      } else if (var1.kind.equals("number")) {
         if (var6) {
            this.a(var1, var2, var3, var4, var5);
         }

         return 14.0F;
      } else if (var1.kind.equals("color")) {
         return this.f(var1, var2, var3, var4, var5, var6);
      } else if (var1.kind.equals("enum")) {
         if (var6) {
            this.b(var1, var2, var3, var4, var5);
         }

         return 14.0F;
      } else if (!var1.kind.equals("key") && !var1.kind.equals("keycombo")) {
         if (var1.kind.equals("string")) {
            if (var6) {
               this.d(var1, var2, var3, var4, var5);
            }

            return 14.0F;
         } else if (var1.kind.equals("button")) {
            if (var6) {
               this.a(var1, var2, var3, var4);
            }

            return 14.0F;
         } else {
            return var1.kind.equals("widget") ? this.a(var1, var3, var4, var5, var6) : 0.0F;
         }
      } else {
         if (var6) {
            this.c(var1, var2, var3, var4, var5);
         }

         return 14.0F;
      }
   }

   private float b(com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var1, String var2, float var3, float var4, float var5, boolean var6) {
      var3 += 14.0F;
      if (var6) {
         this.b(this.a(var1).toUpperCase(Locale.ROOT), this.bold, 6.0F, var3 - 6.0F, var4 + 3.0F, -4079426);
      }

      float var7 = this.a(var1.children, var2, var3, var4 + 8.0F + 3.0F, var5, 0.0F, 4.0F, false, var6);
      return var7 - var4;
   }

   private float c(com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var1, String var2, float var3, float var4, float var5, boolean var6) {
      if (!var6) {
         return 14.0F;
      } else {
         boolean var7 = Boolean.parseBoolean(this.a(var2, var1));
         if (var2.equals("global") && var1.key.equals("hideDebug")) {
            var7 = !var7;
         }

         this.b(this.b(this.a(var1), this.light, 9.0F, var5 - 37.0F), this.light, 9.0F, var3 + 36.0F, var4 + 1.5F, -4079426);
         this.a(var3, var4, var7, var2 + "." + var1.key, this.b(var3, var4, var5, 14.0F));
         this.c(var3, var4, var5, 14.0F, "bool:" + var2 + ":" + var1.key + ":" + var1.value, "");
         return 14.0F;
      }
   }

   private float d(com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var1, String var2, float var3, float var4, float var5, boolean var6) {
      String var7 = var2 + "." + var1.key;
      boolean var8 = this.model.expanded.contains(var7);
      float var9 = var8 ? this.a(var1.children, var2, var3, var4 + 14.0F + 3.0F, var5, 4.0F, 10.0F, false, false) - (var4 + 17.0F) : 0.0F;
      float var10 = var8 ? var9 + 14.0F + 7.0F : 14.0F;
      if (!var6) {
         return var10;
      } else {
         boolean var11 = Boolean.parseBoolean(this.a(var2, var1));
         float var12 = var8 ? var5 / 2.0F : var5;
         float var13 = var12 - 48.0F;
         String var14 = this.a(var1);
         if (this.a(var14, this.light, 9.0F) <= var13) {
            this.b(var14, this.light, 9.0F, var3 + 36.0F, var4 + 1.5F, -4079426);
         } else if (this.a(var14, this.light, 8.0F) <= var13) {
            this.b(var14, this.light, 8.0F, var3 + 36.0F, var4 + 2.0F, -4079426);
         } else {
            this.b(var14, this.light, 7.0F, var3 + 36.0F, var4 + 2.5F, -4079426);
         }

         this.a(var3, var4, var11, var7, this.b(var3, var4, 34.0F, 20.0F));
         boolean var15 = this.b(var3 + 34.0F, var4, var5 - 34.0F, 20.0F);
         this.c(var3 + var12 - 11.0F, var4 + 7.0F, this.a("chevron:" + var7, var8, 200L), var15 ? -1 : -1711276033);
         this.c(var3, var4, 32.0F, 14.0F, "boolOpen:" + var2 + ":" + var1.key + ":" + var1.value, "");
         this.c(var3 + 32.0F, var4, var5 - 32.0F, 14.0F, "expand:" + var7, "");
         if (var8) {
            this.a(var3, var4 + 15.0F, var5 - 2.0F, Math.max(0.0F, var10 - 16.0F), 6.0F, 301989888);
            this.a(var1.children, var2, var3, var4 + 14.0F + 3.0F, var5, 4.0F, 10.0F, false, true);
         }

         return var10;
      }
   }

   private float e(com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var1, String var2, float var3, float var4, float var5, boolean var6) {
      String var7 = this.b(var1, var2);
      String var8 = var2 + "." + var1.key;
      boolean var9 = false;

      for (com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var11 : var1.children) {
         if (!this.c(var11, var7)) {
            var9 = true;
            break;
         }
      }

      boolean var16 = var9 && this.model.expanded.contains(var8);
      float var17 = var16 ? this.a(var1.children, var7, var3, var4 + 18.0F + 4.0F, var5, 4.0F, 30.0F, false, false) - (var4 + 22.0F) : 0.0F;
      float var12 = var16 ? var17 + 18.0F + 10.0F : 18.0F;
      if (!var6) {
         return var12;
      } else {
         boolean var13 = this.model.a(var7, "enabled", Boolean.parseBoolean(var1.value));
         this.b(this.b(this.a(var1), this.light, 9.0F, var5 - 60.0F), this.light, 9.0F, var3 + 34.0F, var4 + 4.5F, -4079426);
         this.a(var3, var4 + 3.0F, var13, var7 + ".enabled", this.b(var3, var4, 34.0F, 20.0F));
         this.c(var3, var4, 32.0F, 18.0F, "bool:" + var7 + ":enabled:" + var1.value, "");
         if (var9) {
            boolean var14 = this.b(var3 + 34.0F, var4, var5 - 34.0F, 20.0F);
            float var15 = var14 ? (float)(System.currentTimeMillis() % 4000L) / 4000.0F * 4.0F : 0.0F;
            if (var14) {
               this.animating = true;
            }

            this.a(var3 + var5 - 11.0F, var4 + 10.0F, var15, var14 ? -1 : -1711276033, "icons/settings/cog-16x16.png");
            this.c(var3 + 32.0F, var4, var5 - 32.0F, 18.0F, "expand:" + var8, "");
         }

         if (var16) {
            this.a(var3, var4 + 19.0F, var5 - 2.0F, Math.max(0.0F, var12 - 20.0F), 6.0F, 301989888);
            this.a(var1.children, var7, var3, var4 + 18.0F + 4.0F, var5, 4.0F, 30.0F, false, true);
         }

         return var12;
      }
   }

   private float a(String var1, boolean var2, long var3) {
      long var5 = System.currentTimeMillis();
      long[] var7 = this.slides.get(var1);
      if (var7 == null) {
         var7 = new long[]{var2 ? 1L : 0L, 0L};
         this.slides.put(var1, var7);
      }

      if (var7[0] == 1L != var2) {
         var7[0] = var2 ? 1L : 0L;
         var7[1] = var5;
      }

      float var8 = Math.min(1.0F, (float)(var5 - var7[1]) / (float)var3);
      if (var8 < 1.0F) {
         this.animating = true;
      }

      return var2 ? var8 : 1.0F - var8;
   }

   private void a(com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var1, String var2, float var3, float var4, float var5) {
      float var6 = var1.min;
      float var7 = var1.max;
      float var8 = a(var1.value, var6);
      float var9 = Math.max(var6, Math.min(var7, this.model.a(var2, var1.key, var8)));
      String var10 = this.a(var1);
      String var11 = a(var1, var9);
      String var12 = var2 + "." + var1.key;
      this.b(var10, this.light, 9.0F, var3, var4 + 1.5F, -4079426);
      float var13 = Math.max(this.a(a(var1, var6), this.bold, 5.0F), this.a(a(var1, var7), this.bold, 5.0F));
      float var14 = this.a(var10, this.light, 9.0F) + var13 + 6.0F;
      float var15 = var5 - var5 / 3.0F;
      float var16 = var3 + var5 / 3.0F;
      if (var15 > var5 - (var14 + 15.0F)) {
         var15 = var5 - (var14 + 15.0F);
         var16 = var3 + var14 + 5.0F;
      }

      float var17 = 14.0F;
      float var18 = var16 + 4.0F;
      float var19 = var15 - 10.0F - 8.0F;
      this.b(
         var11, this.bold, 5.0F, var18 - 8.0F - this.a(var11, this.bold, 5.0F), var4 + var17 / 2.0F - (int)(this.b(this.bold, 5.0F) / 2.0F) - 2.0F, -1879048193
      );
      float var20 = com.cheatbreaker.client.kazerui.gui.components.SliderMath.b(var19);
      float var21 = com.cheatbreaker.client.kazerui.gui.components.SliderMath.a(var19);
      this.a(var18 + var20, var4 + var17 / 2.0F - var17 / 8.0F, var21, var17 / 4.0F, -15066341);
      com.cheatbreaker.client.kazerui.gui.components.SliderMath.a var22 = this.knobs.get(var12);
      if (var22 == null) {
         var22 = new com.cheatbreaker.client.kazerui.gui.components.SliderMath.a(var9);
         this.knobs.put(var12, var22);
      }

      long var23 = System.currentTimeMillis();
      int var25 = var22.a(var12.equals(this.model.sliderHeld), var23);
      if (var25 > 0 && var25 < 255) {
         this.animating = true;
      }

      if (var25 > 50) {
         int[] var26 = com.cheatbreaker.client.kazerui.gui.components.SliderMath.a(var6, var7, c(var1), var1.roundTo);
         float var27 = var21 / var26[0];

         for (int var28 = 0; var28 <= var26[0]; var28++) {
            float var29 = var18 + var20 + var28 * var27;
            this.a(var29, var4, var17 / 4.0F + 2.0F, var25);

            for (int var30 = 1; var30 < var26[1]; var30++) {
               this.a(var29 + var27 * var30 / var26[1], var4, var17 / 4.0F, var25);
            }
         }
      }

      float var31 = var22.a(var9, var6, var7, var23);
      if (var22.c(var23)) {
         this.animating = true;
      }

      float var32 = var18 + var20 + var21 * var31;
      float var33 = var4 + var17 / 2.0F;
      float var34 = var17 / 3.0F;
      this.g.setColor(new Color(-11561732, true));
      this.g.fill(new java.awt.geom.Ellipse2D.Float(var32 - var34, var33 - var34, var34 * 2.0F, var34 * 2.0F));
      Area var35 = new Area(new java.awt.geom.Ellipse2D.Float(var32 - var34, var33 - var34, var34 * 2.0F, var34 * 2.0F));
      var35.subtract(new Area(new java.awt.geom.Ellipse2D.Float(var32 - var34 + 1.0F, var33 - var34 + 1.0F, var34 * 2.0F - 2.0F, var34 * 2.0F - 2.0F)));
      this.g.setColor(new Color(620756991, true));
      this.g.fill(var35);
      this.c(var18, var4, var19, var17, "slider:" + var2 + ":" + var1.key + ":" + var6 + ":" + var7 + (var1.integer ? ":int:" : ":float:") + var17, "");
      if (Math.abs(var9 - var8) >= 1.0E-4) {
         this.a(var3 + var5 - 9.0F, var4 + 3.0F, "set:" + var2 + ":" + var1.key + ":" + var1.value);
      }
   }

   private void a(float var1, float var2, float var3, int var4) {
      this.a(var1, var2 + 7.0F - var3 / 2.0F, 1.0F, var3, var4 << 24 | 3684416);
   }

   private static boolean c(com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var0) {
      return var0.integer || var0.roundTo == 1;
   }

   private static String a(com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var0, float var1) {
      return c(var0) ? "" + Math.round(var1) : String.format(Locale.ROOT, "%.2f", var1);
   }

   private static float a(String var0, float var1) {
      try {
         return java.lang.Float.parseFloat(var0);
      } catch (NumberFormatException var3) {
         return var1;
      }
   }

   private void a(float var1, float var2, String var3) {
      boolean var4 = this.b(var1, var2, 8.0F, 8.0F);
      this.b("icons/settings/reset-16x16.png", var1 + 1.0F, var2 + 1.0F, 6.0F, 6.0F, (int)((var4 ? 0.9F : 0.65F) * 255.0F) << 24 | 16777215);
      this.c(var1, var2, 8.0F, 8.0F, var3, "");
   }

   private void b(com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var1, String var2, float var3, float var4, float var5) {
      this.b(this.a(var1), this.light, 9.0F, var3, var4 + 1.5F, -4079426);
      String var6 = this.a(var2, var1);
      this.a("icons/settings/arrow-left-18x18.png", var3 + var5 - 115.0F, var4, "enum:" + var2 + ":" + var1.key + ":prev");
      this.a("icons/settings/arrow-right-18x18.png", var3 + var5 - 30.0F, var4, "enum:" + var2 + ":" + var1.key + ":next");
      this.c(e(var1, var6), this.medium, 6.5F, var3 + var5 - 64.0F, var4 + 2.5F, -1346256706);
      if (!var6.equals(var1.value)) {
         this.a(var3 + var5 - 9.0F, var4 + 3.0F, "set:" + var2 + ":" + var1.key + ":" + var1.value);
      }
   }

   private static String e(com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var0, String var1) {
      for (String[] var3 : var0.values) {
         if (var3[0].equals(var1)) {
            return var3[1] == null ? var3[0] : com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("settings", var3[1]);
         }
      }

      return var1;
   }

   private void c(com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var1, String var2, float var3, float var4, float var5) {
      this.a(var1, var2, var3, var4, var5, this.a(var1));
   }

   private void a(com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var1, String var2, float var3, float var4, float var5, String var6) {
      this.b(var6, this.light, 9.0F, var3, var4 + 1.5F, -4079426);
      String var7 = var2 + ":" + var1.key;
      boolean var8 = this.model.keyListening.equals(var7);
      float var9 = var3 + var5 - 82.0F;
      float var10 = var4 + 2.0F;
      boolean var11 = this.b(var9, var10, 70.0F, 12.0F);
      this.a(var9, var10, 70.0F, 12.0F, 4.0F, 1076176165, 553648127, this.a("key:" + var7, var11 || var8, 553648127, 1174405119));
      String var12 = this.a(var2, var1);
      String var13 = var8
         ? "?"
         : this.h(com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("gui.components", var12.equals("NONE") ? "none" : var12).toUpperCase(Locale.ROOT));
      float var14 = var10 + 6.0F - this.b(this.bold, 7.0F);
      this.c(var13, this.bold, 7.0F, var9 + 35.0F + 1.0F, var14 + 1.0F, 536870912);
      this.c(var13, this.bold, 7.0F, var9 + 35.0F, var14, -4275267);
      this.c(var9, var10, 70.0F, 12.0F, "listenKey:" + var7, "");
      if (!var12.equals(var1.value)) {
         this.a(var3 + var5 - 9.0F, var4 + 3.0F, "set:" + var2 + ":" + var1.key + ":" + var1.value);
      }
   }

   private void d(com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var1, String var2, float var3, float var4, float var5) {
      this.b(this.a(var1), this.light, 9.0F, var3, var4 + 1.5F, -4079426);
      String var6 = var2 + ":" + var1.key;
      float var7 = var3 + var5 - 90.0F;
      float var8 = var4 + 2.0F;
      this.a(var7, var8, 90.0F, 12.0F, 5.0F, 0, 553648127, 544831865);
      boolean var9 = this.model.textEditing.equals(var6);
      String var10 = var9 ? this.model.input : this.a(var2, var1);
      if (var9) {
         this.a(this.model.textEditing, Math.min(var7 + 86.0F, var7 + 4.0F + this.a(var10, this.bold, 7.0F)), var8 + 1.0F, 10.0F);
      }

      Shape var11 = this.g.getClip();
      this.g.clip(new Float(var7 + 2.0F, var8, 86.0F, 12.0F));
      this.b(var10, this.bold, 7.0F, var7 + 4.0F, var8 + 2.0F, -1862270977);
      if (var9 && this.blink) {
         this.b("_", this.bold, 7.0F, var7 + 4.0F + this.a(var10, this.bold, 7.0F), var8 + 2.0F, -1862270977);
      }

      this.g.setClip(var11);
      this.c(var7, var8, 90.0F, 12.0F, "editText:" + var6 + ":" + var1.maxLength, "");
   }

   private void a(float var1, float var2, float var3, float var4, String var5, int var6) {
      this.a(var1, var2 + 24.0F, var3, 0.5F, 553648127);
      this.a(var1, var2 + 1.0F, var3, 23.0F, 5.0F, 620756992, true, true, false, false);
      this.a(var1, var2 + var4 - 30.0F, var3, 0.5F, 553648127);
      this.a(var1, var2 + var4 - 29.5F, var3, 28.5F, 5.0F, 1157627904, false, false, true, false);
      this.b(var1 - 1.0F, var2, var3 + 2.0F, var4, 4.0F, 1073741824);
      this.b(var1, var2 + 1.0F, var3, var4 - 2.0F, 3.0F, 553648127);
      this.a(var1, var2 + 1.0F, var3, var4 - 2.0F, 5.0F, var6);
      this.b(var5, this.light, 11.0F, var1 + 8.0F, var2 + 6.0F, -1);
   }

   private void a(float var1, float var2, float var3, float var4, String var5, String var6, boolean var7) {
      boolean var8 = var7 || this.b(var1, var2, var3, var4);
      this.a(var1, var2, var3, var4, 4.0F, 1076176165, 553648127, this.a("lbutton:" + var6, var8, 553648127, 1174405119));
      String var9 = this.h(com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("gui.components", var5).toUpperCase(Locale.ROOT));
      float var10 = var2 + var4 / 2.0F - this.b(this.bold, 7.0F);
      this.b(var9, this.bold, 7.0F, var1 + var3 / 2.0F + 1.0F - this.a(var9, this.bold, 7.0F) / 2.0F, var10 + 1.0F, 536870912);
      this.b(var9, this.bold, 7.0F, var1 + var3 / 2.0F - this.a(var9, this.bold, 7.0F) / 2.0F, var10, -4275267);
      this.b(var1, var2, var3, var4, var6, "");
   }

   private void a(com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var1, String var2, float var3, float var4) {
      float var5 = java.lang.Float.isNaN(var1.width) ? 70.0F : var1.width;
      float var6 = 12.0F;
      String var7 = "button:" + var2 + ":" + var1.key;
      boolean var8 = this.b(var3, var4, var5, var6);
      this.a(var3, var4, var5, var6, 4.0F, 1076176165, 553648127, this.a("lbutton:" + var7, var8, 553648127, 1174405119));
      String var9 = var1.text != null ? var1.text : com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("settings.buttons", var1.key);
      String var10 = this.b(this.h(var9.toUpperCase(Locale.ROOT)), this.bold, 7.0F, var5 - 8.0F);
      float var11 = var4 + var6 / 2.0F - this.b(this.bold, 7.0F);
      this.b(var10, this.bold, 7.0F, var3 + var5 / 2.0F + 1.0F - this.a(var10, this.bold, 7.0F) / 2.0F, var11 + 1.0F, 536870912);
      this.b(var10, this.bold, 7.0F, var3 + var5 / 2.0F - this.a(var10, this.bold, 7.0F) / 2.0F, var11, -4275267);
      this.b(var3, var4, var5, var6, var7, "");
   }

   private float a(com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var1, float var2, float var3, float var4, boolean var5) {
      com.cheatbreaker.client.kazerui.gui.components.MenuWidget var6 = var1.widget;
      float var7 = var6.a(var4);
      if (var5 && !(var7 <= 0.0F)) {
         var6.a(this, var2, var3, var4, var7, this.mx, this.my);
         this.c(var2, var3, var4, var7, "widget:" + this.shownWidgets.size(), "");
         this.shownWidgets.add(var6);
         if (var6.a()) {
            this.animating = true;
         }

         return var7;
      } else {
         return var7;
      }
   }

   private Font e(String var1) {
      return var1.equals("roboto-bold.ttf") ? this.bold : (var1.equals("roboto-medium.ttf") ? this.medium : this.light);
   }

   @Override
   public void a(String var1, String var2, float var3, float var4, float var5, int var6) {
      this.b(var1, this.e(var2), var3 / 2.0F, var4, var5, var6);
   }

   @Override
   public float a(String var1, String var2, float var3) {
      return this.a(var1, this.e(var2), var3 / 2.0F);
   }

   @Override
   public void a(String var1, float var2, float var3, float var4, float var5, int var6) {
      this.b(var1, var2, var3, var4, var5, var6);
   }

   @Override
   public void a(BufferedImage var1, float var2, float var3, float var4, float var5, int var6) {
      Object var7 = this.g.getRenderingHint(RenderingHints.KEY_INTERPOLATION);
      this.g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
      Composite var8 = this.g.getComposite();
      this.g.setComposite(AlphaComposite.getInstance(3, (var6 >>> 24) / 255.0F));
      AffineTransform var9 = new AffineTransform();
      var9.translate(var2, var3);
      var9.scale(var4 / var1.getWidth(), var5 / var1.getHeight());
      this.g.drawImage(var1, var9, null);
      this.g.setComposite(var8);
      this.g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, var7);
   }

   @Override
   public void a(String var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      this.a(var1, -1000.0F, -1000.0F, 1.0F, 1.0F, 0.0F);
      BufferedImage var10 = images.get(var1);
      int var11 = Math.round(var6 * var10.getWidth());
      int var12 = Math.round(var7 * var10.getHeight());
      int var13 = Math.round(var8 * var10.getWidth());
      int var14 = Math.round(var9 * var10.getHeight());
      this.g.drawImage(var10, Math.round(var2), Math.round(var3), Math.round(var2 + var4), Math.round(var3 + var5), var11, var12, var13, var14, null);
   }

   @Override
   public Graphics2D a() {
      return this.g;
   }

   private void a(float var1, float var2, float var3, float var4, String var5, String var6) {
      boolean var7 = this.b(var1, var2, var3, var4);
      this.a(var1, var2, var3, var4, 4.0F, 1076176165, 553648127, this.a("ibox:" + var6, var7, 553648127, 1358954495));
      this.b(var5, this.b(var1 + var3 / 2.0F - 4.0F), this.b(var2 + var4 / 2.0F - 4.0F), 8.0F, 8.0F, -1711276033);
      this.b(var1, var2, var3, var4, var6, "");
   }

   private void a(float var1, float var2, float var3, float var4, String var5, String var6, boolean var7, boolean var8, int var9, String var10) {
      this.a(var1, var2, var3, var4, 5.0F, var8 ? -65536 : 0, 553648127, var7 ? 905969663 : 553648127);
      float var11 = var1 + 4.0F;
      float var12 = var2 + (var4 - 8.0F) / 2.0F;
      if (var7) {
         this.a(var10, Math.min(var1 + var3 - 4.0F, var11 + this.a(var5, this.medium, 6.5F)), var12, 10.0F);
      }

      Shape var13 = this.g.getClip();
      this.g.clip(new Float(var1, var2, var3, var4));
      if (!var5.isEmpty()) {
         this.b(var5, this.medium, 6.5F, var11, var12, -1862270977);
      } else if (!var7) {
         this.b(var6, this.medium, 6.5F, var11, var12, 822083583);
      }

      if (var7 && this.blink) {
         float var14 = var11 + this.a(var5, this.medium, 6.5F);
         if (var5.length() >= var9) {
            this.a(var14 + 0.5F, var12 + 1.0F, 0.5F, 2.0F + this.b(this.medium, 6.5F), -3092272);
         } else {
            this.b("_", this.medium, 6.5F, var14, var12, -1862270977);
         }
      }

      this.g.setClip(var13);
      this.b(var1, var2, var3, var4, var10, "");
   }

   private void a(String var1, float var2, float var3) {
      this.b(var1.toUpperCase(Locale.ROOT), this.bold, 6.0F, var2 + 8.0F, var3 + 3.0F, -4079426);
   }

   private void f() {
      float var1 = 350.0F;
      float var2 = 152.0F;
      float var3 = 246.0F - var1 / 2.0F;
      float var4 = 75.5F;
      String var5 = this.model.editor;
      if (!this.model.editorIcon.isEmpty()) {
         this.a("icons/profiles/" + this.model.editorIcon + ".png", var3 + var1 - 36.0F, var4 + 50.0F, 10.0F, 10.0F, 1.0F);
      }

      this.a(
         var3,
         var4,
         var1,
         var2,
         this.model.creatingProfile ? "NEW PROFILE" : com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("gui.components", "profileEditor"),
         Integer.MIN_VALUE
      );
      this.a(com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("gui.profile", "shortDescriptionChars", 20 - this.model.editorName.length()), var3, var4 + 32.0F);
      this.a(
         var3 + 8.0F,
         var4 + 48.0F,
         var1 - 112.0F,
         16.0F,
         this.model.editorName,
         this.model.a(var5),
         this.model.editorFocus.equals("name"),
         this.model.editorNameError,
         20,
         "editorName"
      );
      this.a(com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("gui.profile", "lblServer"), var3, var4 + 64.0F);
      String var6 = this.model.c(var5);
      this.a(
         var3 + 8.0F,
         var4 + 80.0F,
         var1 - 112.0F,
         16.0F,
         this.model.editorServer,
         var6.isEmpty() ? "Unknown" : var6,
         this.model.editorFocus.equals("server"),
         this.model.editorServerError,
         60,
         "editorServer"
      );
      this.a(com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("settings.labels", "icon"), var3 + var1 - 29.0F, var4 + 32.0F);
      this.a(var3 + var1 - 24.0F, var4 + 48.0F, 14.0F, 14.0F, "icons/assets/arrow-down-17x17.png", "editorIconMenu");
      float var7 = 0.0F;
      float var8;
      this.a(var3 + var1 - (var8 = var7 + 56.0F), var4 + var2 - 24.0F, 50.0F, 18.0F, "save", "editorSave", false);
      if (!this.model.creatingProfile && this.model.e(var5)) {
         this.a(var3 + var1 - (var8 += 56.0F), var4 + var2 - 24.0F, 50.0F, 18.0F, "delete", "editorDelete", false);
      }

      if (!this.model.creatingProfile && this.model.inWorld) {
         this.a(var3 + var1 - (var7 = var8 + 56.0F), var4 + var2 - 24.0F, 50.0F, 18.0F, "reset", "editorReset", false);
      }

      this.a(var3 + 6.0F, var4 + var2 - 24.0F, 18.0F, 18.0F, "icons/back-folder-64x64.png", "editorBack");
      if (this.model.iconMenu != null) {
         this.b(this.model.iconMenu[0], this.model.iconMenu[1]);
      }
   }

   private void b(float var1, float var2) {
      this.hits.clear();
      float var3 = var1 + 2.0F;
      float var4 = var2;
      float var5 = 100.0F;
      float var6 = com.cheatbreaker.client.kazerui.gui.components.MenuModel.PROFILE_ICONS.length * 10 + 8;
      this.a(var3, var2, var5, var6, 6.0F, 1076176165, 553648127, -820175587);
      float var7 = this.b(this.light, 6.0F);

      for (int var8 = 0; var8 < com.cheatbreaker.client.kazerui.gui.components.MenuModel.PROFILE_ICONS.length; var8++) {
         float var9 = var1 + 3.0F;
         float var10 = var4 + 4.0F + var8 * 10;
         float var11 = var5 - 2.0F;
         this.a(var9, var10, var11, 10.0F, this.a("icon:" + var8, this.b(var9, var10, var11, 10.0F), 0, 1157627904));
         String var12 = com.cheatbreaker.client.kazerui.gui.components.MenuModel.PROFILE_ICONS[var8][1];
         this.b(var12, this.medium, 6.5F, var9 + 5.0F, var10 + 5.0F - var7, 536870912);
         this.b(var12, this.medium, 6.5F, var9 + 4.0F, var10 + 4.0F - var7, -538778910);
         this.b(var9, var10, var11, 10.0F, "editorIcon:" + com.cheatbreaker.client.kazerui.gui.components.MenuModel.PROFILE_ICONS[var8][0], "");
      }
   }

   private void g() {
      if (!this.model.confirm.equals(this.confirmShown)) {
         this.confirmShown = this.model.confirm;
         this.confirmSince = System.currentTimeMillis();
      }

      float var1 = 246.0F;
      float var2 = 151.5F;
      float var3 = var1 - 160.0F;
      float var4 = var2 - 60.0F;
      float var5 = 320.0F;
      float var6 = 118.0F;
      this.a(var3, var4 + 24.0F, var5, 0.5F, 553648127);
      this.a(var3, var4 + 1.0F, var5, 23.0F, 5.0F, 620756992, true, true, false, false);
      this.b(var3 - 1.0F, var4, var5 + 2.0F, var6, 4.0F, 1073741824);
      this.b(var3, var4 + 1.0F, var5, var6 - 2.0F, 3.0F, 553648127);
      this.a(var3, var4 + 1.0F, var5, var6 - 2.0F, 5.0F, Integer.MIN_VALUE);
      long var7 = (System.currentTimeMillis() - this.confirmSince) % 4000L;
      float var9 = var7 < 2000L ? (float)var7 / 2000.0F : 2.0F - (float)var7 / 2000.0F;
      this.animating = true;
      String var10 = "gui.profile." + this.model.confirm;
      this.c(com.cheatbreaker.client.kazerui.gui.components.KazerLang.a(var10, "warning"), this.light, 11.0F, var1, var2 - 54.0F, a(-1, -52429, var9));
      this.c(com.cheatbreaker.client.kazerui.gui.components.KazerLang.a(var10, "lineOne"), this.medium, 6.5F, var1, var2 - 24.0F, -1);
      if (!com.cheatbreaker.client.kazerui.gui.components.KazerLang.a(var10, "lineTwo").equals("lineTwo")) {
         this.c(com.cheatbreaker.client.kazerui.gui.components.KazerLang.a(var10, "lineTwo"), this.medium, 6.5F, var1, var2 - 14.0F, -1);
      }

      this.a(var1 - 100.0F, var2 + 10.0F, 200.0F, 16.0F, "understood", "confirmYes", false);
      this.a(var1 - 100.0F, var2 + 30.0F, 200.0F, 16.0F, "cancel", "confirmNo", true);
   }

   private void a(com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a var1) {
      this.hits.clear();
      this.suppressHover = false;
      this.a(-2.0F, -2.0F, 496.0F, 307.0F, -805306368);
      String var2 = com.cheatbreaker.client.kazerui.gui.components.KazerLang.b(var1.id, var1.name);
      float var3 = Math.max(180.0F, this.a(var2, this.light, 9.0F) + 112.0F);
      float var4 = 80.0F;
      float var5 = 246.0F - var3 / 2.0F;
      float var6 = 111.5F;
      this.a(var5, var6, var3, var4, com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("gui.components", "toggleKeybind"), -268435456);
      this.a(new com.cheatbreaker.client.kazerui.gui.components.OptionTree.a(f(var1.id)), var1.id, var5 + 8.0F, var6 + 34.0F, var3 - 16.0F, var2);
      this.a(var5 + var3 - 56.0F, var6 + var4 - 24.0F, 50.0F, 18.0F, "done", "keybindPopupDone", false);
   }

   private static Map<String, Object> f(String var0) {
      HashMap var1 = new HashMap();
      var1.put("type", "option");
      var1.put("key", "toggleKeybind");
      var1.put("kind", "key");
      var1.put("default", "NONE");
      return var1;
   }

   private float f(com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var1, String var2, float var3, float var4, float var5, boolean var6) {
      String var7 = var2 + ":" + var1.key;
      boolean var8 = this.model.expanded.contains("color|" + var2 + "." + var1.key);
      if (!var6) {
         return var8 ? 80.0F : 14.0F;
      } else {
         String var9 = var1.value.toUpperCase(Locale.ROOT);
         String var10 = this.model.c(var2, var1.key, var9);
         int var11 = (int)Long.parseLong(var10, 16);
         this.b(this.a(var1), this.light, 8.0F, var3, var4 + 1.5F, -4079426);
         if (this.model.hexEditing.equals(var7)) {
            this.b(this.model.input.toUpperCase(Locale.ROOT), this.light, 8.0F, var3 + var5 - 68.0F, var4 + 2.5F, -1862270977);
            if (this.blink) {
               this.b(
                  "_", this.light, 8.0F, var3 + var5 - 68.0F + this.a(this.model.input.toUpperCase(Locale.ROOT), this.light, 8.0F), var4 + 2.5F, -1862270977
               );
            }
         } else {
            this.b("#" + var10.toLowerCase(Locale.ROOT), this.light, 8.0F, var3 + var5 - 68.0F, var4 + 2.5F, -2134785858);
         }

         this.a(var3 + var5 - 84.0F, var4 + 2.0F, 10.0F, 10.0F, 8.0F, var11);
         this.b(var3 + var5 - 83.0F, var4 + 3.0F, 8.0F, 8.0F, 3.0F, -1342177281);
         boolean var12 = this.model.l("favoriteColors").contains(var10);
         boolean var13 = this.b(var3 + var5 - 20.0F, var4, 10.0F, 14.0F);
         boolean var14 = this.b(var3 + var5 - 10.0F, var4, 10.0F, 14.0F);
         if (!var10.equalsIgnoreCase(var9)) {
            this.b("icons/settings/reset-16x16.png", var3 + var5 - 8.0F, var4 + 4.0F, 6.0F, 6.0F, (int)((var14 ? 0.9F : 0.65F) * 255.0F) << 24 | 16777215);
         }

         this.b(
            "icons/stars/star-64x64.png", var3 + var5 - 18.0F, var4 + 4.0F, 6.0F, 6.0F, (int)((var13 ? 0.8F : 0.5F) * 255.0F) << 24 | (var12 ? 13421568 : 0)
         );
         this.c(var3 + var5 - 10.0F, var4, 10.0F, 14.0F, "swatch:" + var7 + ":" + var9, "");
         this.c(var3 + var5 - 20.0F, var4, 10.0F, 14.0F, "colorFav:" + var7 + ":" + var9, "");
         this.c(var3 + var5 - 68.0F, var4, 48.0F, 14.0F, "hex:" + var7 + ":" + (var1.alpha ? "a" : "-"), "");
         this.c(var3, var4, var5 - 68.0F, 14.0F, "colorOpen:" + var7, "");
         if (!var8) {
            return 14.0F;
         } else {
            float var15 = var4 + 14.0F;
            float var16 = 60.0F;
            if (var1.chroma) {
               this.a(var3, var15, 112.0F, var16, 4.0F, 536870912);
               boolean var17 = this.model.a(var2, var1.key + "Chroma", var1.chromaOn);
               boolean var18 = this.b(var3 + 7.0F, var4 + 17.0F, 110.0F, 12.0F);
               this.a(var3 + 7.0F, var4 + 19.0F, 8.0F, 8.0F, 4.0F, var18 ? -11561732 : -1437625092);
               this.b(var3 + 8.0F, var4 + 20.0F, 6.0F, 6.0F, 2.0F, 905969663);
               if (var17) {
                  this.a("icons/settings/checked-14x14.png", var3 + 8.0F, var4 + 19.5F, 6.0F, 6.0F, 1.0F);
               }

               this.b(com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("settings", "chroma"), this.light, 8.0F, var3 + 20.0F, var4 + 18.5F, -4079426);
               this.c(var3 + 7.0F, var4 + 17.0F, 110.0F, 12.0F, "bool:" + var2 + ":" + var1.key + "Chroma:" + var1.chromaOn, "");
               float var19 = this.model.a(var2, var1.key + "ChromaSpeed", 40.0F);
               this.b(com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("settings", "chromaSpeed"), this.light, 8.0F, var3 + 7.0F, var4 + 31.0F, -4079426);
               String var20 = "" + Math.round(var19);
               this.b(var20, this.light, 8.0F, var3 + 59.0F - this.a(var20, this.light, 8.0F), var4 + 31.0F, -4079426);
               this.a(var3 + 61.0F, var4 + 30.0F, 43.0F, 12.0F, (var19 - 1.0F) / 99.0F, "slider:" + var2 + ":" + var1.key + "ChromaSpeed:1:100:int");
               this.b(com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("settings", "chromaType"), this.light, 8.0F, var3 + 7.0F, var4 + 44.5F, -4079426);
               String var21 = this.model.a(var2, var1.key + "ChromaType", "WAVE");
               this.a("icons/settings/arrow-left-18x18.png", var3 + 7.0F + 50.0F, var4 + 43.0F, "chromaType:" + var7 + ":prev");
               this.c(
                  com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("settings", var21.equals("SHIFT") ? "shift" : "wave"),
                  this.medium,
                  6.5F,
                  var3 + 7.0F + 76.0F,
                  var4 + 45.5F,
                  -1346256706
               );
               this.a("icons/settings/arrow-right-18x18.png", var3 + 7.0F + 88.0F, var4 + 43.0F, "chromaType:" + var7);
            }

            float var26 = this.model.a(var2, var1.key, var11);
            float[] var27 = Color.RGBtoHSB(var11 >> 16 & 0xFF, var11 >> 8 & 0xFF, var11 & 0xFF, null);
            float var28 = var3 + 118.0F;
            this.a(var28, var15, 114.0F, var16, 4.0F, -1);
            this.g.setPaint(new GradientPaint(var28 + 1.0F, 0.0F, Color.WHITE, var28 + 113.0F, 0.0F, Color.getHSBColor(var26, 1.0F, 1.0F)));
            this.g.fill(new Float(var28 + 1.0F, var15 + 1.0F, 112.0F, var16 - 2.0F));
            this.g.setPaint(new GradientPaint(0.0F, var15 + 1.0F, new Color(0, 0, 0, 0), 0.0F, var15 + var16 - 1.0F, Color.BLACK));
            this.g.fill(new Float(var28 + 1.0F, var15 + 1.0F, 112.0F, var16 - 2.0F));
            float var29 = var28 + 1.0F + 112.0F * var27[1];
            float var30 = var15 + 1.0F + (var16 - 2.0F) * (1.0F - var27[2]);
            this.g.setColor(Color.BLACK);
            this.g.fill(new java.awt.geom.Ellipse2D.Float(var29 - 2.0F, var30 - 2.0F, 4.0F, 4.0F));
            this.g.setColor(Color.WHITE);
            this.g.fill(new java.awt.geom.Ellipse2D.Float(var29 - 1.0F, var30 - 1.0F, 2.0F, 2.0F));
            this.c(var28 + 1.0F, var15 + 1.0F, 112.0F, var16 - 2.0F, "sb:" + var7, "");
            this.a(var3 + 238.0F, var15, 9.0F, var16, 4.0F, -1);
            this.a("components/hue_selector.png", var3 + 239.0F, var15 + 1.0F, 7.0F, var16 - 2.0F, 1.0F);
            this.b(var3 + 244.5F, var15 + 1.0F - 1.75F + (var16 - 2.0F) * var26, 3.5F, -1);
            this.c(var3 + 239.0F, var15 + 1.0F, 7.0F, var16 - 2.0F, "hue:" + var7, "");
            if (var1.alpha) {
               this.a(var3 + 252.0F, var15, 9.0F, var16, 4.0F, -1);
               this.a("components/alpha_selector.png", var3 + 253.0F, var15 + 1.0F, 7.0F, var16 - 2.0F, 1.0F);
               this.b(var3 + 258.5F, var15 + 1.0F - 1.75F + (var16 - 2.0F) * (1.0F - (var11 >>> 24) / 255.0F), 3.5F, -52429);
               this.c(var3 + 253.0F, var15 + 1.0F, 7.0F, var16 - 2.0F, "alpha:" + var7, "");
            }

            this.a(var3 + 266.0F, var15, 0.5F, var16, 547529378);
            this.a(var3 + 314.0F, var15, 0.5F, var16, 547529378);
            this.a(var3 + 295.0F, var15, 0.5F, var16, 547529378);

            for (int var22 = 0; var22 < PRESETS.length; var22++) {
               this.a(var3 + 271.5F + 10 * (var22 % 2), var4 + 15.0F + 10 * (var22 / 2), PRESETS[var22], var7);
            }

            List var31 = this.model.l("recentColors");
            List var23 = this.model.l("favoriteColors");

            for (int var24 = 0; var24 < var31.size() && var24 < 6; var24++) {
               this.a(var3 + 301.0F, var4 + 15.0F + 10 * var24, (int)Long.parseLong((String)var31.get(var24), 16), var7);
            }

            for (int var32 = 0; var32 < var23.size() && var32 < 12; var32++) {
               this.a(var3 + 318.5F + 10 * (var32 % 2), var4 + 15.0F + 10 * (var32 / 2), (int)Long.parseLong((String)var23.get(var32), 16), var7);
            }

            for (int var33 = 0; var33 < 6; var33++) {
               this.a(var3 + 301.0F, var4 + 15.0F + 10 * var33, 8.0F, 8.0F, 4.0F, 889192448);
            }

            for (int var34 = 0; var34 < 6; var34++) {
               for (int var25 = 0; var25 < 2; var25++) {
                  this.a(var3 + 318.5F + 10 * var25, var4 + 15.0F + 10 * var34, 8.0F, 8.0F, 4.0F, 889192448);
               }
            }

            return 80.0F;
         }
      }
   }

   private void a(float var1, float var2, int var3, String var4) {
      this.a(var1, var2, 8.0F, 8.0F, 5.0F, var3);
      this.b(var1 + 1.0F, var2 + 1.0F, 6.0F, 6.0F, 2.5F, -1342177281);
      this.c(var1, var2, 8.0F, 8.0F, "swatch:" + var4 + ":" + String.format(Locale.ROOT, "%08X", var3), "");
   }

   private void b(float var1, float var2, float var3, int var4) {
      java.awt.geom.Path2D.Float var5 = new java.awt.geom.Path2D.Float();
      var5.moveTo(var1 + var3 / 2.0F, var2);
      var5.lineTo(var1 + var3 / 2.0F, var2 + var3);
      var5.lineTo(var1 - var3 / 2.0F, var2 + var3 / 2.0F);
      var5.closePath();
      this.g.setColor(new Color(var4, true));
      this.g.fill(var5);
   }

   private void a(float var1, float var2, float var3, float var4, float var5, String var6) {
      float var7 = var4 / 3.0F;
      float var8 = var3 - var4 * 2.0F / 3.0F;
      this.a(var1 + var7, var2 + var4 / 2.0F - var4 / 8.0F, var8, var4 / 4.0F, -15066341);
      float var9 = var1 + var7 + var8 * Math.max(0.0F, Math.min(1.0F, var5));
      float var10 = var2 + var4 / 2.0F;
      float var11 = var4 / 3.0F;
      this.g.setColor(new Color(-11561732, true));
      this.g.fill(new java.awt.geom.Ellipse2D.Float(var9 - var11, var10 - var11, var11 * 2.0F, var11 * 2.0F));
      this.c(var1, var2, var3, var4, var6 + ":" + var4, "");
   }

   private void c(float var1, float var2, float var3, int var4) {
      this.a(var1, var2, var3, var4, "icons/settings/arrow-right-18x18.png");
   }

   private void a(float var1, float var2, float var3, int var4, String var5) {
      AffineTransform var6 = this.g.getTransform();
      this.g.translate((double)var1, (double)var2);
      this.g.rotate(Math.toRadians(90.0F * var3));
      this.b(var5, -4.0F, -4.0F, 8.0F, 8.0F, var4);
      this.g.setTransform(var6);
   }

   private List<String> a(String var1, Font var2, float var3, float var4) {
      ArrayList var5 = new ArrayList();
      String var6 = "";

      for (String var10 : var1.split(" ")) {
         String var11 = var6.isEmpty() ? var10 : var6 + " " + var10;
         if (!var6.isEmpty() && this.a(var11, var2, var3) > var4) {
            var5.add(var6);
            var6 = var10;
         } else {
            var6 = var11;
         }
      }

      if (!var6.isEmpty()) {
         var5.add(var6);
      }

      return var5;
   }

   private void h() {
      float var1 = 110.0F;

      for (String var5 : new String[]{"GENERAL", "PERFORMANCE", "CONTROLS"}) {
         float var6 = this.a(this.h(var5), this.bold, 7.0F) + 16.0F;
         this.a(var1, 43.5F, var6, 14.0F, this.h(var5), "settings:" + var5, this.model.settingsTab.equals(var5), 1, "");
         var1 += var6 + 4.0F;
      }

      this.a(383.0F, 43.5F, 100.0F, 14.0F);
      this.scrollTop = 62.5F;
      this.scrollHeight = 235.0F;
      float var13 = this.i();
      com.cheatbreaker.client.kazerui.module.KazerModule var14 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.a("global");
      List var15 = Collections.emptyList();
      if (!this.model.settingsTab.equals("CONTROLS") && var14 instanceof com.cheatbreaker.client.kazerui.module.GlobalSettingsModule) {
         var15 = this.b(((com.cheatbreaker.client.kazerui.module.GlobalSettingsModule)var14).b(this.model.settingsTab.equals("PERFORMANCE")), "global");
      }

      float var16 = 69.5F + var13 + 6.0F;
      this.contentHeight = var13 + 13.0F + this.a(var15, "global", 110.0F, var16, 347.0F, 0.0F, 0.0F, true, false) - var16;
      this.maxScroll = Math.max(0.0F, this.contentHeight - this.scrollHeight);
      this.model.scroller.content = this.contentHeight;
      this.model.scroller.height = this.scrollHeight;
      this.model.scroller.a();
      Shape var17 = this.g.getClip();
      this.g.clip(new Float(110.0F, this.scrollTop, 377.0F, this.scrollHeight));
      this.settingsContent = true;
      float var7 = 69.5F - this.model.scroller.offset;
      float var8 = 125.0F;
      float var9 = 347.0F;
      if (this.model.settingsTab.equals("GENERAL")) {
         var7 = this.a(var15, "global", 110.0F, var7, 347.0F, 0.0F, 0.0F, true, true) + 6.0F;
         this.b("Interface Options", var7);
         var7 += 12.0F;
         if (this.g("Dim Menu Background")) {
            this.a("Dim Menu Background", "global", "menuDim", true, var8, var7, var9);
            var7 += 14.0F;
         }

         if (this.g("Inventory Branding")) {
            String var10 = this.model.a("global", "inventoryBrand", "above");
            this.a(
               "Inventory Branding",
               var10.equals("hidden") ? "Hidden" : (var10.equals("bottomRight") ? "Bottom Right" : "Above Menu"),
               "inventoryBrand",
               var8,
               var7,
               var9
            );
            var7 += 14.0F;
         }

         this.b(com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("settings.labels", "modOptions"), var7);
         var7 += 12.0F;
         if (this.g("Menu Layout")) {
            this.a("Menu Layout", this.model.d() ? "Compact" : "Large", "compact", var8, var7, var9);
            var7 += 14.0F;
         }

         String var23 = com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("settings", "sortingOptions");
         String var11 = com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("settings", "searchOptions");
         if (this.g(var23)) {
            this.a(var23, com.cheatbreaker.client.kazerui.gui.components.MenuModel.b(this.model.e()), "sort", var8, var7, var9);
            var7 += 14.0F;
         }

         if (this.g(var11)) {
            this.b(this.b(var11, this.light, 9.0F, var9 - 38.0F), this.light, 9.0F, var8 + 36.0F, var7 + 1.5F, -4079426);
            boolean var12 = Boolean.parseBoolean(this.model.store.a("searchOptions", "true"));
            this.a(var8, var7, var12, "searchOptions", this.b(var8, var7, var9, 14.0F));
            this.b(var8, var7, var9, 14.0F, "searchOptions", "");
            var7 += 14.0F;
         }
      } else if (this.model.settingsTab.equals("CONTROLS")) {
         this.b("Keybinds", var7);
         var7 += 12.0F;
         if (this.g("Mod Menu")) {
            this.a("Mod Menu", "menu", "RSHIFT", "rebind", var8, var7, var9);
            var7 += 14.0F;
         }

         if (this.g("Freelook")) {
            this.a("Freelook", "freelook", "LMENU", "rebindFreelook", var8, var7, var9);
            var7 += 14.0F;
         }

         if (this.g("Zoom")) {
            this.a("Zoom", "zoom", "C", "rebindZoom", var8, var7, var9);
            var7 += 14.0F;
         }

         this.a("Minecraft Controls", "Open", "gameControls", var8, var7, var9, "Open existing game keybindings");
         var7 += 14.0F;
      } else {
         this.b("Performance", var7);
         var7 += 12.0F;
         this.a("Video Settings", "Open", "videoSettings", var8, var7, var9, "Open Minecraft video settings");
         var7 += 14.0F;
      }

      if (!this.model.settingsTab.equals("GENERAL")) {
         this.a(var15, "global", 110.0F, var7 + 6.0F, 347.0F, 0.0F, 0.0F, true, true);
      }

      this.settingsContent = false;
      this.g.setClip(var17);
      this.a(481.0F);
   }

   private float i() {
      if (this.model.settingsTab.equals("GENERAL")) {
         float var6 = 24.0F;

         for (String var10 : new String[]{
            "Dim Menu Background",
            "Inventory Branding",
            "Menu Layout",
            com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("settings", "sortingOptions"),
            com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("settings", "searchOptions")
         }) {
            if (this.g(var10)) {
               var6 += 14.0F;
            }
         }

         return var6;
      } else if (this.model.settingsTab.equals("CONTROLS")) {
         float var1 = 26.0F;

         for (String var5 : new String[]{"Mod Menu", "Freelook", "Zoom"}) {
            if (this.g(var5)) {
               var1 += 14.0F;
            }
         }

         return var1;
      } else {
         return 26.0F;
      }
   }

   private void b(String var1, float var2) {
      this.b(var1.toUpperCase(Locale.ROOT), this.bold, 6.0F, 118.0F, var2 + 3.0F, -4079426);
   }

   private boolean g(String var1) {
      return var1.toLowerCase(Locale.ROOT).contains(this.model.search.toLowerCase(Locale.ROOT));
   }

   private void a(String var1, String var2, String var3, boolean var4, float var5, float var6, float var7) {
      this.b(this.b(var1, this.light, 9.0F, var7 - 38.0F), this.light, 9.0F, var5 + 36.0F, var6 + 1.5F, -4079426);
      this.a(var5, var6, this.model.a(var2, var3, var4), var2 + "." + var3, this.b(var5, var6, var7, 14.0F));
      this.b(var5, var6, var7, 14.0F, "bool:" + var2 + ":" + var3 + ":" + var4, "");
   }

   private void a(String var1, String var2, String var3, float var4, float var5, float var6) {
      this.b(var1, this.light, 9.0F, var4, var5 + 1.5F, -4079426);
      this.a("icons/settings/arrow-left-18x18.png", var4 + var6 - 115.0F, var5, var3 + ":prev");
      this.a("icons/settings/arrow-right-18x18.png", var4 + var6 - 30.0F, var5, var3);
      this.c(var2, this.medium, 6.5F, var4 + var6 - 64.0F, var5 + 2.5F, -1346256706);
   }

   private void a(String var1, float var2, float var3, String var4) {
      this.a(var2, var3, 13.0F, 13.0F, 100876083);
      int var5 = 128 + (int)(this.a("arrow:" + var4, this.b(var2, var3, 13.0F, 13.0F)) * 127.0F);
      this.b(var1, var2 + 2.25F, var3 + 2.25F, 9.0F, 9.0F, var5 << 24 | 65535);
      this.b(var2, var3, 13.0F, 13.0F, var4, "");
   }

   private void a(String var1, String var2, String var3, float var4, float var5, float var6, String var7) {
      this.b(var1, this.light, 9.0F, var4, var5 + 1.5F, -4079426);
      this.c(var2, this.medium, 6.5F, var4 + var6 - 64.0F, var5 + 2.5F, this.b(var4, var5, var6, 14.0F) ? -1 : -1346256706);
      this.b(var4, var5, var6, 14.0F, var3, var7);
   }

   private void a(String var1, String var2, String var3, String var4, float var5, float var6, float var7) {
      this.b(var1, this.light, 9.0F, var5, var6 + 1.5F, -4079426);
      float var8 = var5 + var7 - 82.0F;
      float var9 = var6 + 2.0F;
      boolean var10 = this.model.captureKey && this.model.captureTarget.equals(var2);
      this.a(var8, var9, 70.0F, 12.0F, 4.0F, 1076176165, 553648127, !this.b(var8, var9, 70.0F, 12.0F) && !var10 ? 553648127 : 1174405119);
      String var11 = this.model.store.a(var2 + "KeyName", var3);
      String var12 = var10 ? "?" : this.h(var11.toUpperCase(Locale.ROOT));
      this.c(this.b(var12, this.bold, 7.0F, 66.0F), this.bold, 7.0F, var8 + 35.0F, var9 + 6.0F - this.b(this.bold, 7.0F), -4275267);
      this.c(var8, var9, 70.0F, 12.0F, var4, "Click to change the key");
      this.a(var5 + var7 - 9.0F, var6 + 3.0F, "resetBinding:" + var2);
   }

   private void a(float var1, float var2, boolean var3, String var4, boolean var5) {
      this.a(var1, var2 + 2.0F, 30.0F, 10.0F, 5.0F, var5 ? 1088611042 : 551740130);
      this.b(var1 + 1.0F, var2 + 3.0F, 28.0F, 8.0F, 2.5F, 905969663);
      float var6 = var1 + 10.0F * this.b("switch:" + var4, var3);
      this.a(var6, var2 + 2.0F, 20.0F, 10.0F, 5.0F, var3 ? -1356212614 : -1344396974);
      this.b(var6 + 1.0F, var2 + 3.0F, 18.0F, 8.0F, 2.5F, 905969663);
      this.c(com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("gui.components", var3 ? "on" : "off"), this.bold, 6.0F, var6 + 10.0F, var2 + 3.0F, -1);
   }

   private void a(float var1, float var2, float var3, float var4) {
      this.a(var1, var2, var3, var4, 5.0F, 0, 553648127, this.model.searchFocused ? 905969663 : 553648127);
      this.b("icons/assets/magnifying-glass-12x12.png", var1 + 7.0F, var2 + 4.5F, 5.5F, 5.5F, 872415231);
      Shape var5 = this.g.getClip();
      this.g.clip(new Float(var1 + 15.0F, var2, var3 - 18.0F, var4));
      float var6 = var2 + (var4 - 8.0F) / 2.0F;
      if (this.model.searchFocused) {
         this.a("search", Math.min(var1 + var3 - 4.0F, var1 + 17.5F + this.a(this.model.search, this.medium, 6.5F)), var6, 10.0F);
      }

      if (this.model.searchFocused && this.model.searchSelected && !this.model.search.isEmpty()) {
         this.a(var1 + 16.0F, var6, this.a(this.model.search, this.medium, 6.5F), 8.0F, -2140956195);
      }

      if (!this.model.search.isEmpty()) {
         this.b(this.model.search, this.medium, 6.5F, var1 + 16.0F, var6, -1862270977);
      } else if (!this.model.searchFocused) {
         this.b(com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("gui.components", "searchPlaceholder"), this.medium, 6.5F, var1 + 16.0F, var6, 822083583);
      }

      if (this.model.searchFocused && !this.model.searchSelected && this.blink) {
         this.a(Math.min(var1 + var3 - 4.0F, var1 + 17.5F + this.a(this.model.search, this.medium, 6.5F)), var6 + 1.0F, 0.5F, 8.0F, -3092272);
      }

      this.g.setClip(var5);
      this.b(var1, var2, var3, var4, "search", "");
   }

   private void a(float var1) {
      com.cheatbreaker.client.kazerui.gui.components.ScrollPhysics var2 = this.model.scroller;
      if (var2.c()) {
         float var3 = var2.d();
         float var4 = this.scrollTop + var2.e();
         boolean var5 = this.mx >= var1 && this.mx <= var1 + 4.0F && this.my > var4 && this.my < var4 + var3;
         this.a(var1, this.scrollTop, 4.0F, this.scrollHeight, 540884285);
         this.a(var1, var4, 4.0F, var3, !var5 && !var2.dragging ? 551740130 : 1625481954);
         this.b(var1, this.scrollTop, 4.0F, this.scrollHeight, "scrollbar", "");
      }
   }

   public com.cheatbreaker.client.kazerui.gui.components.MenuRenderer.b a(float var1, float var2) {
      for (int var3 = this.hits.size() - 1; var3 >= 0; var3--) {
         if (this.hits.get(var3).rect.contains(var1, var2)) {
            return this.hits.get(var3);
         }
      }

      return null;
   }

   private void b(float var1, float var2, float var3, float var4, String var5, String var6) {
      if (this.settingsContent) {
         float var7 = Math.min(297.5F, var2 + var4);
         var2 = Math.max(this.clipTop, var2);
         var4 = var7 - var2;
         if (var4 <= 0.0F) {
            return;
         }
      }

      this.hits.add(new com.cheatbreaker.client.kazerui.gui.components.MenuRenderer.b(var1, var2, var3, var4, var5, var6));
   }

   private void c(float var1, float var2, float var3, float var4, String var5, String var6) {
      float var7 = Math.max(this.clipTop, var2);
      float var8 = Math.min(297.5F, var2 + var4);
      if (var8 > var7) {
         this.b(var1, var7, var3, var8 - var7, var5, var6);
      }
   }

   private boolean b(float var1, float var2, float var3, float var4) {
      return !this.suppressHover
         && (!this.settingsContent || this.my >= this.clipTop && this.my < 297.5F)
         && this.mx >= var1
         && this.mx < var1 + var3
         && this.my >= var2
         && this.my < var2 + var4;
   }

   private void a(float var1, float var2, float var3, float var4, String var5, String var6, boolean var7, int var8, String var9) {
      boolean var10 = this.b(var1, var2, var3, var4);
      boolean var11 = var7 || var10;
      String var12 = "button:" + var6;
      float var13 = var4 <= 12.0F ? 5.0F : 7.0F;
      float var14 = var4 == 12.0F ? 1.0F : 0.0F;
      float var15 = var2 + var4 / 2.0F - this.b(this.bold, var13) - var14;
      if (var8 == 0) {
         this.a(var1, var2, var3, var4, 4.0F, 0, 547529378, this.a(var12, var11, 540752699, 553648127));
         this.a(this.b(var5, this.bold, var13, var3 - 4.0F), this.bold, var13, var1 + var3 / 2.0F, var15, var10 ? -4275267 : -1765882947);
      } else {
         int var16 = var8 == 2 ? -2142268416 : -2141746990;
         int var17 = var8 == 2 ? -11561984 : -11561732;
         if (var8 == 3) {
            var16 = -11040558;
         }

         this.a(var1, var2, var3, var4, 4.0F, 0, var11 ? 1621271202 : 547529378, this.a(var12, var11, var16, var17));
         this.a(this.b(var5, this.bold, var13, var3 - 4.0F), this.bold, var13, var1 + var3 / 2.0F, var15, !var11 && var8 != 3 ? -1342177281 : -1);
      }

      this.b(var1, var2, var3, var4, var6, var9);
   }

   private void a(float var1, float var2, float var3, float var4, String var5, String var6, String var7) {
      this.a(var5, var1 + 1.0F, var2 + 1.0F, var3 - 2.0F, var4 - 2.0F, 0.65F + 0.25F * this.a("icon:" + var6, this.b(var1, var2, var3, var4)));
      this.b(var1, var2, var3, var4, var6, var7);
   }

   private void a(String var1, float var2, float var3, float var4, float var5, float var6) {
      BufferedImage var7 = images.get(var1);
      if (var7 == null) {
         if (var1.startsWith("icons/features/overlay-")) {
            var7 = com.cheatbreaker.client.kazerui.gui.components.OverlayIcon.a(52);
            images.put(var1, var7);
         } else {
            try (InputStream var8 = this.c(var1)) {
               if (var8 == null) {
                  throw new IOException(var1);
               }

               var7 = ImageIO.read(var8);
               images.put(var1, var7);
            } catch (IOException var21) {
               throw new IllegalStateException("Missing Kazer UI asset: " + var1, var21);
            }
         }
      }

      Composite var22 = this.g.getComposite();
      this.g.setComposite(AlphaComposite.getInstance(3, var6));
      AffineTransform var23 = new AffineTransform();
      var23.translate(var2, var3);
      var23.scale(var4 / var7.getWidth(), var5 / var7.getHeight());
      this.g.drawImage(var7, var23, null);
      this.g.setComposite(var22);
   }

   private void b(String var1, float var2, float var3, float var4, float var5, int var6) {
      String var7 = var1 + "#" + Integer.toHexString(var6 | 0xFF000000);
      if (!images.containsKey(var7)) {
         this.a(var1, -1000.0F, -1000.0F, 1.0F, 1.0F, 0.0F);
         BufferedImage var8 = images.get(var1);
         BufferedImage var9 = new BufferedImage(var8.getWidth(), var8.getHeight(), 2);

         for (int var10 = 0; var10 < var8.getHeight(); var10++) {
            for (int var11 = 0; var11 < var8.getWidth(); var11++) {
               int var12 = var8.getRGB(var11, var10);
               int var13 = (var12 >> 16 & 0xFF) * (var6 >> 16 & 0xFF) / 255;
               int var14 = (var12 >> 8 & 0xFF) * (var6 >> 8 & 0xFF) / 255;
               int var15 = (var12 & 0xFF) * (var6 & 0xFF) / 255;
               var9.setRGB(var11, var10, var12 & 0xFF000000 | var13 << 16 | var14 << 8 | var15);
            }
         }

         images.put(var7, var9);
      }

      this.a(var7, var2, var3, var4, var5, (var6 >>> 24 & 0xFF) / 255.0F);
   }

   private float a(String var1, boolean var2) {
      long var3 = System.currentTimeMillis();
      long[] var5 = this.fades.get(var1);
      if (var5 == null) {
         var5 = new long[]{var2 ? 1L : 0L, 0L};
         this.fades.put(var1, var5);
      }

      if (var5[0] == 1L != var2) {
         var5[0] = var2 ? 1L : 0L;
         var5[1] = var3;
      }

      float var6 = Math.min(1.0F, (float)(var3 - var5[1]) / 125.0F);
      if (var6 < 1.0F) {
         this.animating = true;
      }

      return var2 ? var6 : 1.0F - var6;
   }

   private float b(String var1, boolean var2) {
      long var3 = System.currentTimeMillis();
      long[] var5 = this.slides.get(var1);
      if (var5 == null) {
         var5 = new long[]{var2 ? 1L : 0L, 0L};
         this.slides.put(var1, var5);
      }

      if (var5[0] == 1L != var2) {
         var5[0] = var2 ? 1L : 0L;
         var5[1] = var3;
      }

      float var6 = Math.min(1.0F, (float)(var3 - var5[1]) / 100.0F);
      if (var6 < 1.0F) {
         this.animating = true;
      }

      float var7 = var6 * var6;
      return var2 ? var7 : 1.0F - var7;
   }

   private int a(String var1, boolean var2, int var3, int var4) {
      return a(var3, var4, this.a(var1, var2));
   }

   private static int a(int var0, int var1, float var2) {
      int var3 = 0;

      for (byte var4 = 0; var4 < 32; var4 += 8) {
         var3 |= Math.round((var0 >>> var4 & 0xFF) * (1.0F - var2) + (var1 >>> var4 & 0xFF) * var2) << var4;
      }

      return var3;
   }

   private static Shape a(float var0, float var1, float var2, float var3, float var4, boolean var5, boolean var6, boolean var7, boolean var8) {
      java.awt.geom.Path2D.Float var9 = new java.awt.geom.Path2D.Float();
      var9.moveTo(var0 + (var5 ? var4 : 0.0F), var1);
      var9.lineTo(var0 + var2 - (var6 ? var4 : 0.0F), var1);
      if (var6) {
         var9.append(new java.awt.geom.Arc2D.Float(var0 + var2 - 2.0F * var4, var1, 2.0F * var4, 2.0F * var4, 90.0F, -90.0F, 0), true);
      }

      var9.lineTo(var0 + var2, var1 + var3 - (var8 ? var4 : 0.0F));
      if (var8) {
         var9.append(new java.awt.geom.Arc2D.Float(var0 + var2 - 2.0F * var4, var1 + var3 - 2.0F * var4, 2.0F * var4, 2.0F * var4, 0.0F, -90.0F, 0), true);
      }

      var9.lineTo(var0 + (var7 ? var4 : 0.0F), var1 + var3);
      if (var7) {
         var9.append(new java.awt.geom.Arc2D.Float(var0, var1 + var3 - 2.0F * var4, 2.0F * var4, 2.0F * var4, 270.0F, -90.0F, 0), true);
      }

      var9.lineTo(var0, var1 + (var5 ? var4 : 0.0F));
      if (var5) {
         var9.append(new java.awt.geom.Arc2D.Float(var0, var1, 2.0F * var4, 2.0F * var4, 180.0F, -90.0F, 0), true);
      }

      var9.closePath();
      return var9;
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, float var5, int var6, boolean var7, boolean var8, boolean var9, boolean var10) {
      this.g.setColor(new Color(var6, true));
      this.g.fill(a(var1, var2, var3, var4, Math.min(var5, Math.min(var3, var4)) / 2.0F, var7, var8, var9, var10));
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, float var5, int var6) {
      this.a(var1, var2, var3, var4, var5, var6, true, true, true, true);
   }

   private void b(float var1, float var2, float var3, float var4, float var5, int var6, boolean var7, boolean var8, boolean var9, boolean var10) {
      if (var6 >>> 24 != 0) {
         com.cheatbreaker.client.kazerui.gui.components.MenuRenderer.c var11 = new com.cheatbreaker.client.kazerui.gui.components.MenuRenderer.c(
            var3, var4, var5, (var7 ? 1 : 0) | (var8 ? 2 : 0) | (var9 ? 4 : 0) | (var10 ? 8 : 0)
         );
         Area var12 = rings.get(var11);
         if (var12 == null) {
            var12 = new Area(a(-1.0F, -1.0F, var3 + 2.0F, var4 + 2.0F, var5, var7, var8, var9, var10));
            var12.subtract(new Area(a(0.0F, 0.0F, var3, var4, Math.max(0.0F, var5 - 1.0F), var7, var8, var9, var10)));
            rings.put(var11, var12);
         }

         this.g.setColor(new Color(var6, true));
         this.g.translate((double)var1, (double)var2);

         try {
            this.g.fill(var12);
         } finally {
            this.g.translate((double)(-var1), (double)(-var2));
         }
      }
   }

   @Override
   public void b(float var1, float var2, float var3, float var4, float var5, int var6) {
      this.b(var1, var2, var3, var4, var5, var6, true, true, true, true);
   }

   private void a(float var1, float var2, float var3, float var4, float var5, int var6, int var7, int var8) {
      this.a(var1, var2, var3, var4, var5 + 1.0F, var8);
      this.b(var1, var2, var3, var4, var5, var6);
      this.b(var1 + 1.0F, var2 + 1.0F, var3 - 2.0F, var4 - 2.0F, var5 - 1.25F, var7);
   }

   private void a(String var1, Font var2, float var3, float var4, float var5, int var6) {
      this.c(var1, var2, var3, var4 + 1.0F, var5 + 1.0F, 536870912);
      this.c(var1, var2, var3, var4, var5, var6);
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, int var5) {
      this.g.setColor(new Color(var5, true));
      this.g.fill(new Float(var1, var2, var3, var4));
   }

   private com.cheatbreaker.client.kazerui.gui.components.MenuRenderer.a a(Font var1, float var2) {
      String var3 = var1.getFontName() + "@" + var2;
      com.cheatbreaker.client.kazerui.gui.components.MenuRenderer.a var4 = glyphs.get(var3);
      if (var4 == null) {
         var4 = new com.cheatbreaker.client.kazerui.gui.components.MenuRenderer.a(var1, var2 * 2.0F);
         glyphs.put(var3, var4);
      }

      return var4;
   }

   private float b(Font var1, float var2) {
      return this.a(var1, var2).height;
   }

   private float a(String var1, Font var2, float var3) {
      com.cheatbreaker.client.kazerui.gui.components.MenuRenderer.a var4 = this.a(var2, var3);
      int var5 = 0;

      for (int var6 = 0; var6 < var1.length(); var6++) {
         char var7 = var1.charAt(var6);
         if (var7 < 256) {
            var5 += var4.advance[var7];
         }
      }

      return var5 / 2.0F;
   }

   private float b(float var1) {
      return (float)Math.round(var1 * this.rasterScale) / this.rasterScale;
   }

   private void b(String var1, Font var2, float var3, float var4, float var5, int var6) {
      com.cheatbreaker.client.kazerui.gui.components.MenuRenderer.a var7 = this.a(var2, var3);
      if ((var6 & -67108864) == 0) {
         var6 |= -16777216;
      }

      float var8 = this.b(var4 - 1.0F) * 2.0F;
      float var9 = this.b(var5) * 2.0F;
      AffineTransform var10 = this.g.getTransform();
      this.g.scale(0.5, 0.5);
      this.g.setFont(var7.font);
      this.g.setColor(new Color(var6, true));

      for (int var11 = 0; var11 < var1.length(); var11++) {
         char var12 = var1.charAt(var11);
         if (var12 < 256) {
            this.g.drawString(String.valueOf(var12), var8 + 2.0F, var9 + var7.ascent);
            var8 += var7.advance[var12];
         }
      }

      this.g.setTransform(var10);
   }

   private void c(String var1, Font var2, float var3, float var4, float var5, int var6) {
      this.b(var1, var2, var3, var4 - this.a(var1, var2, var3) / 2.0F, var5, var6);
   }

   private String b(String var1, Font var2, float var3, float var4) {
      if (this.a(var1, var2, var3) <= var4) {
         return var1;
      } else {
         while (!var1.isEmpty() && this.a(var1 + "...", var2, var3) > var4) {
            var1 = var1.substring(0, var1.length() - 1);
         }

         return var1 + "...";
      }
   }

   private String h(String var1) {
      return var1.replace("", " ").trim();
   }

   private static final class a {
      final Font font;
      final int[] advance = new int[256];
      final int ascent;
      final int height;

      a(Font var1, float var2) {
         this.font = var1.deriveFont(var2);
         BufferedImage var3 = new BufferedImage(1, 1, 2);
         Graphics2D var4 = var3.createGraphics();
         var4.setFont(this.font);
         var4.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
         var4.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
         var4.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         FontMetrics var5 = var4.getFontMetrics();
         int var6 = -1;

         for (int var7 = 0; var7 < 256; var7++) {
            Rectangle var8 = var5.getStringBounds(String.valueOf((char)var7), var4).getBounds();
            this.advance[var7] = var8.width;
            var6 = Math.max(var6, var8.height);
         }

         this.ascent = var5.getAscent();
         this.height = (var6 - 8) / 2;
         var4.dispose();
      }
   }

   public static final class b {
      public final Float rect;
      public final String action;
      public final String tooltip;

      b(float var1, float var2, float var3, float var4, String var5, String var6) {
         this.rect = new Float(var1, var2, var3, var4);
         this.action = var5;
         this.tooltip = var6;
      }
   }

   private static final class c {
      final int w;
      final int h;
      final int r;
      final int corners;

      c(float var1, float var2, float var3, int var4) {
         this.w = java.lang.Float.floatToIntBits(var1);
         this.h = java.lang.Float.floatToIntBits(var2);
         this.r = java.lang.Float.floatToIntBits(var3);
         this.corners = var4;
      }

      @Override
      public int hashCode() {
         return ((this.w * 31 + this.h) * 31 + this.r) * 31 + this.corners;
      }

      @Override
      public boolean equals(Object var1) {
         if (!(var1 instanceof com.cheatbreaker.client.kazerui.gui.components.MenuRenderer.c)) {
            return false;
         } else {
            com.cheatbreaker.client.kazerui.gui.components.MenuRenderer.c var2 = (com.cheatbreaker.client.kazerui.gui.components.MenuRenderer.c)var1;
            return this.w == var2.w && this.h == var2.h && this.r == var2.r && this.corners == var2.corners;
         }
      }
   }

   private final class d {
      private final String[] t;
      private int i;
      private final String module;

      d(String var2, String var3) {
         ArrayList var4 = new ArrayList();
         Matcher var5 = Pattern.compile("[A-Za-z][A-Za-z0-9]*(?:[=~][A-Z0-9_]+)?|[!()|&]").matcher(var2);

         while (var5.find()) {
            var4.add(var5.group());
         }

         this.t = (String[]) var4.toArray(new String[0]);
         this.module = var3;
      }

      boolean a() {
         return this.b();
      }

      private boolean b() {
         boolean var1;
         for (var1 = this.c(); this.i < this.t.length && this.t[this.i].equals("|"); var1 |= this.c()) {
            this.i++;
         }

         return var1;
      }

      private boolean c() {
         boolean var1;
         for (var1 = this.d(); this.i < this.t.length && this.t[this.i].equals("&"); var1 &= this.d()) {
            this.i++;
         }

         return var1;
      }

      private boolean d() {
         String var1 = this.t[this.i++];
         if (var1.equals("!")) {
            return !this.d();
         } else if (var1.equals("(")) {
            boolean var4 = this.b();
            this.i++;
            return var4;
         } else if (var1.equals("true")) {
            return true;
         } else if (var1.equals("false")) {
            return false;
         } else {
            int var2 = Math.max(var1.indexOf(61), var1.indexOf(126));
            if (var2 < 0) {
               return Boolean.parseBoolean(this.a(var1));
            } else {
               boolean var3 = this.a(var1.substring(0, var2)).equals(var1.substring(var2 + 1));
               return var1.charAt(var2) == '=' ? var3 : !var3;
            }
         }
      }

      private String a(String var1) {
         com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var2 = com.cheatbreaker.client.kazerui.gui.components.MenuRenderer.c(
            com.cheatbreaker.client.kazerui.gui.components.OptionTree.a(this.module).options, var1
         );
         return MenuRenderer.this.model.a(this.module, var1, var2 == null ? "" : var2.value);
      }
   }
}
