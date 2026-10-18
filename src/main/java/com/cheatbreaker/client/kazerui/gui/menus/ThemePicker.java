package com.cheatbreaker.client.kazerui.gui.menus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

final class ThemePicker {
   private static final int SPACE_1 = -15526886;
   private static final int SPACE_3 = -14539988;
   private static final int SPACE_5 = -13684421;
   private static final int SPACE_11 = -5131328;
   private static final int SPACE_12 = -1184013;
   private static final int PLACEHOLDER = -9079435;
   private static final int DOT_ON_OUTER = 772013333;
   private static final int DOT_ON_INNER = -16256159;
   private static final int GHOST = 348834805;
   private static final int GHOST_HOVER = 651484414;
   private static final ResourceLocation LOGO = new ResourceLocation("kazerui", "ui/logo/kazer.png");
   private static final ResourceLocation CLOSE = a("picker-close");
   private static final ResourceLocation SEARCH = a("picker-search");
   private static final ResourceLocation SORT = a("picker-sort-arrows");
   private static final ResourceLocation RESET = a("picker-reset");
   private static final ResourceLocation AZ = a("sort-az");
   private static final ResourceLocation ZA = a("sort-za");
   private static final ResourceLocation QUESTION = a("picker-question");
   private static final ResourceLocation ERASER = a("picker-eraser");
   private static final ResourceLocation CURTAINS = a("curtains");
   private static final ResourceLocation TRANSITION_SOUND = new ResourceLocation("kazerui", "transition");
   private final com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics gfx;
   private final com.cheatbreaker.client.kazerui.gui.menus.HomeThemeCatalog themes;
   private final Map<String, com.cheatbreaker.client.kazerui.gui.menus.ThemePicker.a> anims = new HashMap<>();
   boolean open;
   private long openedAt;
   private String search = "";
   private boolean searchFocused;
   private boolean searchSelected;
   private boolean sortOpen;
   private boolean sortWasOpen;
   private boolean sortAscending;
   private long curtainStart = -1L;
   private String pendingTheme;
   private boolean swapped;
   private final List<com.cheatbreaker.client.kazerui.gui.menus.ThemePicker.b> hits = new ArrayList<>();
   private float mx;
   private float my;
   private float vw;
   private float vh;
   private float scroll;
   private float maxScroll;
   private float gridTop;
   private float gridBottom;
   private static final float[] EASE = new float[]{0.25F, 0.1F, 0.25F, 1.0F};
   private static final float[] EASE_IN_OUT = new float[]{0.42F, 0.0F, 0.58F, 1.0F};
   private boolean warmed;

   private static ResourceLocation a(String var0) {
      return new ResourceLocation("kazerui", "ui/home/" + var0 + ".png");
   }

   ThemePicker(com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics var1, com.cheatbreaker.client.kazerui.gui.menus.HomeThemeCatalog var2) {
      this.gfx = var1;
      this.themes = var2;
      this.sortAscending = !"alpha-desc".equals(var2.a().a("homeTheme.sort", "alpha-asc"));
   }

   private float a(String var1, boolean var2, float var3, float[] var4) {
      com.cheatbreaker.client.kazerui.gui.menus.ThemePicker.a var5 = this.anims.get(var1);
      if (var5 == null) {
         var5 = new com.cheatbreaker.client.kazerui.gui.menus.ThemePicker.a(var3, var4);
         this.anims.put(var1, var5);
      }

      return var5.a(var2);
   }

   void a() {
      this.open = true;
      this.openedAt = System.currentTimeMillis();
      this.search = "";
      this.searchFocused = this.searchSelected = false;
      this.sortOpen = false;
   }

   void b() {
      this.warmed = false;
   }

   void a(float var1, float var2) {
      if (!this.warmed) {
         this.warmed = true;
         float var3 = this.mx;
         float var4 = this.my;
         this.vw = var1;
         this.vh = var2;
         this.mx = this.my = -10000.0F;
         GlStateManager.pushMatrix();
         GlStateManager.translate(100000.0F, 100000.0F, 0.0F);

         try {
            this.f();
         } finally {
            GlStateManager.popMatrix();
            this.mx = var3;
            this.my = var4;
            this.hits.clear();
         }
      }
   }

   boolean c() {
      return this.open || this.d();
   }

   boolean d() {
      return this.curtainStart >= 0L;
   }

   private boolean b(float var1, float var2, float var3, float var4) {
      return this.mx >= var1 && this.my >= var2 && this.mx < var1 + var3 && this.my < var2 + var4;
   }

   void a(float var1, float var2, float var3, float var4) {
      this.vw = var1;
      this.vh = var2;
      this.mx = var3;
      this.my = var4;
      this.hits.clear();
      if (this.open) {
         this.f();
      }

      if (this.d()) {
         this.i();
      }
   }

   private void f() {
      float var1 = Math.min(1.0F, (float)(System.currentTimeMillis() - this.openedAt) / 200.0F);
      float var2 = HomeScreen.a(0.42F, 0.0F, 0.58F, 1.0F, var1);
      this.gfx.a(0.0F, 0.0F, this.vw, this.vh, Math.round(0.5F * var2 * 255.0F) << 24);
      this.hits.add(new com.cheatbreaker.client.kazerui.gui.menus.ThemePicker.b(0.0F, 0.0F, this.vw, this.vh, new Runnable() {
         @Override
         public void run() {
            ThemePicker.this.h();
         }
      }));
      List var3 = this.g();
      com.cheatbreaker.client.kazerui.gui.menus.HomeThemeCatalog.a var4 = this.themes.b();
      float var5 = Math.min(880.0F, this.vw * 0.9F);
      float var6 = var5 - 40.0F;
      boolean var7 = var3.isEmpty();
      float var8 = 205.0F;
      float var9 = this.vh * 0.9F - var8;
      boolean var10 = false;
      int var11 = 1;
      int var12 = 1;
      float var13 = 0.0F;
      float var14 = 0.0F;

      for (int var15 = 0; var15 < 2; var15++) {
         float var16 = var6 - 12.0F - (var10 ? 16 : 0);
         int var17 = Math.max(1, (int)Math.floor((var16 + 24.0F) / 199.0F));
         var11 = Math.max(1, Math.min(var17, var3.size()));
         var13 = (var16 - (var11 - 1) * 24) / var11;
         var12 = (var3.size() + var11 - 1) / var11;
         var14 = var7 ? 286.0F : 12 + var12 * 189 + (var12 - 1) * 24;
         if (!var7 && var14 > var9) {
            var10 = true;
         }
      }

      float var36 = var10 ? Math.max(0.0F, var9) : var14;
      this.scroll = var10 ? Math.max(0.0F, Math.min(this.scroll, var14 - var36)) : 0.0F;
      this.maxScroll = var10 ? var14 - var36 : 0.0F;
      float var37 = var8 + var36;
      float var38 = (this.vw - var5) / 2.0F;
      float var18 = (this.vh - var37) / 2.0F + 30.0F * (1.0F - var2);
      float var19 = var38 + 20.0F;
      float var20 = com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.a(60.0F);
      if (!this.e()) {
         this.gfx.a(this.gfx.a(var5, var37, 12.0F, 30.0F, 60.0F, -10.0F), var38 - var20, var18 - var20, -2146825718);
      }

      this.gfx.a(this.gfx.a(var5, var37, 12.0F), var38, var18, -15526886);
      this.hits.add(new com.cheatbreaker.client.kazerui.gui.menus.ThemePicker.b(var38, var18, var5, var37, new Runnable() {
         @Override
         public void run() {
            ThemePicker.this.sortOpen = false;
            ThemePicker.this.searchFocused = false;
         }
      }));
      this.gfx.a(LOGO, var19, var18 + 22.0F, 24.0F, 24.0F, -1);
      this.gfx.a(this.gfx.b("Select Theme", true, 24.0F), var38 + 52.0F, var18 + 20.0F, -1184013);
      boolean var21 = this.b(var38 + var5 - 52.0F, var18 + 21.5F, 32.0F, 29.0F);
      this.gfx.a(CLOSE, var38 + var5 - 48.0F, var18 + 21.5F, 24.0F, 25.0F, HomeScreen.a(-5131328, -1184013, this.a("close", var21, 200.0F, EASE)));
      this.hits.add(new com.cheatbreaker.client.kazerui.gui.menus.ThemePicker.b(var38 + var5 - 52.0F, var18 + 21.5F, 32.0F, 29.0F, new Runnable() {
         @Override
         public void run() {
            ThemePicker.this.h();
         }
      }));
      float var22 = var18 + 68.0F;
      this.gfx.a(this.gfx.a(var6, 36.0F, 10.0F), var19, var22, -14539988);
      this.gfx.a(SEARCH, var19 + 12.0F, var22 + 10.0F, 16.0F, 16.0F, -5131328);
      boolean var23 = System.currentTimeMillis() / 530L % 2L == 0L;
      if (this.searchFocused && this.searchSelected && !this.search.isEmpty()) {
         this.gfx.a(var19 + 36.0F, var22 + 9.0F, Math.min(var6 - 42.0F, this.gfx.a(this.search, false, 14.0F)), 18.0F, -2140956195);
      }

      if (this.search.isEmpty()) {
         this.gfx.a(this.gfx.b("Search...", false, 14.0F), var19 + 36.0F, var22 + 8.8F, -9079435);
      } else {
         this.gfx.a(this.gfx.b(this.search, false, 14.0F), var19 + 36.0F, var22 + 8.8F, -5131328);
      }

      if (this.searchFocused && !this.searchSelected && var23) {
         this.gfx.a(this.gfx.b(var19 + 36.0F + (this.search.isEmpty() ? 0.0F : this.gfx.a(this.search, false, 14.0F))), var22 + 10.0F, 1.0F, 16.0F, -5131328);
      }

      this.hits.add(new com.cheatbreaker.client.kazerui.gui.menus.ThemePicker.b(var19, var22, var6, 36.0F, new Runnable() {
         @Override
         public void run() {
            ThemePicker.this.searchFocused = true;
            ThemePicker.this.sortOpen = false;
         }
      }));
      float var24 = var18 + 117.5F;
      String var25 = "Themes (" + this.themes.c().size() + ")";
      float var26 = this.gfx.a(var25, false, 16.0F);
      boolean var27 = this.b(var19, var24, var26 + 24.0F, 21.0F);
      int var28 = HomeScreen.a(-5131328, -1184013, this.a("sort", var27, 200.0F, EASE));
      this.gfx.a(this.gfx.b(var25, false, 16.0F), var19, var24, var28);
      this.gfx.a(SORT, var19 + var26 + 8.0F, var24 + 2.5F, 16.0F, 16.0F, var28);
      this.hits.add(new com.cheatbreaker.client.kazerui.gui.menus.ThemePicker.b(var19, var24, var26 + 24.0F, 21.0F, new Runnable() {
         @Override
         public void run() {
            ThemePicker.this.sortOpen = !ThemePicker.this.sortWasOpen;
            ThemePicker.this.searchFocused = false;
         }
      }));
      float var29 = var18 + 152.0F;
      this.gridTop = var29;
      this.gridBottom = var29 + var36;
      if (var7) {
         this.a(var19, var29, var6);
      } else {
         if (var10) {
            this.c(var19, var29, var6, var36);
         }

         for (int var30 = 0; var30 < var3.size(); var30++) {
            com.cheatbreaker.client.kazerui.gui.menus.HomeThemeCatalog.a var31 = (com.cheatbreaker.client.kazerui.gui.menus.HomeThemeCatalog.a)var3.get(var30);
            float var32 = var19 + 6.0F + var30 % var11 * (var13 + 24.0F);
            float var33 = var29 + 6.0F + var30 / var11 * 213 - this.scroll;
            if (!(var33 + 189.0F < var29) && !(var33 > var29 + var36)) {
               this.a(var31, var31.id.equals(var4.id), var32, var33, var13);
            }
         }

         if (var10) {
            GL11.glDisable(3089);
            float var39 = Math.max(36.0F, var36 * var36 / var14);
            float var41 = var29 + (var36 - var39) * (this.scroll / this.maxScroll);
            boolean var43 = this.b(var19 + var6 - 16.0F, var41, 16.0F, var39);
            this.gfx.a(this.gfx.a(6.0F, var39 - 10.0F, 3.0F), var19 + var6 - 11.0F, var41 + 5.0F, var43 ? 651484414 : 348834805);
         }
      }

      float var40 = var29 + var36 + 12.0F;
      this.gfx.a(this.gfx.b("Active:", false, 16.0F), var19, var40, -5131328);
      this.gfx.a(this.gfx.b(var4.name, false, 16.0F), var19 + this.gfx.a("Active:", false, 16.0F) + 8.0F, var40, -1184013);
      this.c(var19 + var6 / 2.0F, var40 - 3.5F);
      float var42 = this.gfx.a("Reset to default", false, 16.0F);
      float var44 = var19 + var6 - var42;
      boolean var45 = this.b(var44 - 20.0F, var40, var42 + 20.0F, 21.0F);
      int var34 = HomeScreen.a(-5131328, -1184013, this.a("reset", var45, 200.0F, EASE));
      this.gfx.a(RESET, var44 - 20.0F, var40 + 2.5F, 16.0F, 16.0F, var34);
      this.gfx.a(this.gfx.b("Reset to default", false, 16.0F), var44, var40, var34);
      this.hits.add(new com.cheatbreaker.client.kazerui.gui.menus.ThemePicker.b(var44 - 20.0F, var40, var42 + 20.0F, 21.0F, new Runnable() {
         @Override
         public void run() {
            ThemePicker.this.b("");
         }
      }));
      this.d(var19, var24 + 27.5F);
   }

   boolean e() {
      return "legacy".equals(this.themes.a().a("homeTheme.buttons", "modern"));
   }

   private void c(float var1, float var2) {
      String var3 = "Buttons";
      String[] var4 = new String[]{"Modern", "Legacy"};
      float var5 = this.gfx.a(var3, false, 16.0F);
      float var6 = 76.0F;
      float var7 = var6 * 2.0F + 4.0F;
      float var8 = var5 + 12.0F + var7;
      float var9 = var1 - var8 / 2.0F;
      float var10 = var9 + var5 + 12.0F;
      this.gfx.a(this.gfx.b(var3, false, 16.0F), var9, var2 + 3.5F, -5131328);
      this.gfx.a(this.gfx.a(var7, 28.0F, 8.0F), var10, var2, -14539988);

      for (int var11 = 0; var11 < 2; var11++) {
         final boolean var12 = var11 == 1;
         boolean var13 = var12 == this.e();
         float var14 = var10 + 2.0F + var11 * var6;
         boolean var15 = this.b(var14, var2 + 2.0F, var6, 24.0F);
         if (var13) {
            this.gfx.a(this.gfx.a(var6, 24.0F, 6.0F), var14, var2 + 2.0F, -13684421);
         }

         int var16 = var13 ? -1184013 : HomeScreen.a(-5131328, -1184013, this.a("style" + var11, var15, 200.0F, EASE));
         float var17 = this.gfx.a(var4[var11], false, 14.0F);
         this.gfx.a(this.gfx.b(var4[var11], false, 14.0F), var14 + (var6 - var17) / 2.0F, var2 + 5.0F, var16);
         this.hits.add(new com.cheatbreaker.client.kazerui.gui.menus.ThemePicker.b(var14, var2 + 2.0F, var6, 24.0F, new Runnable() {
            @Override
            public void run() {
               ThemePicker.this.themes.a().b("homeTheme.buttons", var12 ? "legacy" : "modern");
               ThemePicker.this.themes.a().b();
            }
         }));
      }
   }

   private void a(final com.cheatbreaker.client.kazerui.gui.menus.HomeThemeCatalog.a var1, boolean var2, float var3, float var4, float var5) {
      boolean var6 = this.b(var3, var4, var5, 160.0F);
      boolean var7 = this.b(var3, var4, var5, 189.0F);
      float var8 = 1.0F + 0.1F * this.a("zoom:" + var1.id, var6, 500.0F, EASE);
      float var9 = Math.min(1.0F, 160.0F / var5) / var8;
      float var10 = Math.min(1.0F, var5 / 160.0F) / var8;
      Minecraft.getMinecraft().getTextureManager().bindTexture(var1.a(0));
      GL11.glTexParameteri(3553, 10241, 9728);
      GL11.glTexParameteri(3553, 10240, 9728);
      this.gfx.a(var1.a(0), var3, var4, var5, 160.0F, 8.0F, 0.5F - var10 / 2.0F, 0.5F - var9 / 2.0F, 0.5F + var10 / 2.0F, 0.5F + var9 / 2.0F);
      GL11.glTexParameteri(3553, 10241, 9729);
      GL11.glTexParameteri(3553, 10240, 9729);
      int var11 = HomeScreen.a(-5131328, -1184013, this.a("label:" + var1.id, var7, 200.0F, EASE));
      this.gfx.a(this.gfx.b(var1.name, false, 16.0F), var3, var4 + 168.0F, var11);
      this.gfx.a(this.gfx.a(20.0F, 20.0F, 10.0F), var3 + var5 - 20.0F, var4 + 168.0F, var2 ? 772013333 : -14539988);
      this.gfx.a(this.gfx.a(10.0F, 10.0F, 5.0F), var3 + var5 - 15.0F, var4 + 173.0F, var2 ? -16256159 : -5131328);
      float var12 = Math.max(var4, this.gridTop);
      float var13 = Math.min(var4 + 189.0F, this.gridBottom);
      if (var13 > var12) {
         this.hits.add(new com.cheatbreaker.client.kazerui.gui.menus.ThemePicker.b(var3, var12, var5, var13 - var12, new Runnable() {
            @Override
            public void run() {
               ThemePicker.this.b(var1.id);
            }
         }));
      }
   }

   private void c(float var1, float var2, float var3, float var4) {
      Minecraft var5 = Minecraft.getMinecraft();
      GL11.glEnable(3089);
      GL11.glScissor(
         Math.round(var1 * this.gfx.dsf),
         Math.round(var5.displayHeight - (var2 + var4) * this.gfx.dsf),
         Math.round(var3 * this.gfx.dsf),
         Math.round(var4 * this.gfx.dsf)
      );
   }

   void a(int var1) {
      if (this.open && this.maxScroll > 0.0F) {
         this.scroll = Math.max(0.0F, Math.min(this.maxScroll, this.scroll - var1 * 100));
      }
   }

   private void a(float var1, float var2, float var3) {
      float var4 = var1 + var3 / 2.0F;
      this.gfx.a(QUESTION, var4 - 60.0F, var2, 120.0F, 120.0F, -5131328);
      String var5 = "Couldn't find ‘" + this.search + "’";
      this.gfx.a(this.gfx.b(var5, false, 20.0F), var4 - this.gfx.a(var5, false, 20.0F) / 2.0F, var2 + 144.0F, -1184013);
      String var6 = "Are you sure you spelt it correctly?";
      this.gfx.a(this.gfx.b(var6, false, 16.0F), var4 - this.gfx.a(var6, false, 16.0F) / 2.0F, var2 + 178.0F, -5131328);
      float var7 = 36.0F + this.gfx.a("Clear search", false, 16.0F) + 12.0F;
      float var8 = var4 - var7 / 2.0F;
      float var9 = var2 + 223.0F;
      boolean var10 = this.b(var8, var9, var7, 39.0F);
      float var11 = this.a("clear", var10, 200.0F, EASE);
      this.gfx.a(this.gfx.a(var7, 39.0F, 8.0F), var8, var9, HomeScreen.a(348834805, 651484414, var11));
      int var12 = HomeScreen.a(-5131328, -1184013, var11);
      this.gfx.a(ERASER, var8 + 12.0F, var9 + 11.5F, 16.0F, 16.0F, var12);
      this.gfx.a(this.gfx.b("Clear search", false, 16.0F), var8 + 36.0F, var9 + 10.0F, var12);
      this.hits.add(new com.cheatbreaker.client.kazerui.gui.menus.ThemePicker.b(var8, var9, var7, 39.0F, new Runnable() {
         @Override
         public void run() {
            ThemePicker.this.search = "";
         }
      }));
   }

   private void d(float var1, float var2) {
      float var3 = this.a("sortMenu", this.sortOpen, 150.0F, EASE);
      if (!(var3 <= 0.001F)) {
         int var4 = Math.round(var3 * 255.0F);
         var2 -= 10.0F * (1.0F - var3);
         float var5 = com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.a(24.0F);
         this.gfx.a(this.gfx.a(200.0F, 88.0F, 8.0F, 8.0F, 24.0F, 0.0F), var1 - var5, var2 - var5, a(1292503562, var3));
         this.gfx
            .a(
               this.gfx.a(200.0F, 88.0F, 8.0F, 2.0F, 8.0F, 0.0F),
               var1 - com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.a(8.0F),
               var2 - com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.a(8.0F),
               a(638192138, var3)
            );
         this.gfx.a(this.gfx.a(200.0F, 88.0F, 8.0F), var1, var2, var4 << 24 | 2237228);
         String[] var6 = new String[]{"Alphabetical (A-Z)", "Alphabetical (Z-A)"};
         ResourceLocation[] var7 = new ResourceLocation[]{AZ, ZA};

         for (int var8 = 0; var8 < 2; var8++) {
            final boolean var9 = var8 == 0;
            float var10 = var2 + 8.0F + var8 * 38;
            boolean var11 = this.sortAscending == var9;
            boolean var12 = this.sortOpen && this.b(var1 + 8.0F, var10, 184.0F, 34.0F);
            float var13 = this.a("opt" + var8, var12 || var11, 200.0F, EASE_IN_OUT);
            if (var13 > 0.0F) {
               this.gfx.a(this.gfx.a(184.0F, 34.0F, 8.0F), var1 + 8.0F, var10, a(-13684421, var13 * var3));
            }

            int var14 = a(HomeScreen.a(-5131328, -1184013, var13), var3);
            this.gfx.a(var7[var8], var1 + 16.0F, var10 + 9.0F, 16.0F, 16.0F, var14);
            this.gfx.a(this.gfx.b(var6[var8], false, 14.0F), var1 + 36.0F, var10 + 8.0F, var14);
            if (this.sortOpen) {
               this.hits.add(new com.cheatbreaker.client.kazerui.gui.menus.ThemePicker.b(var1 + 8.0F, var10, 184.0F, 34.0F, new Runnable() {
                  @Override
                  public void run() {
                     ThemePicker.this.sortAscending = var9;
                     ThemePicker.this.sortOpen = false;
                     ThemePicker.this.themes.a().b("homeTheme.sort", var9 ? "alpha-asc" : "alpha-desc");
                     ThemePicker.this.themes.a().b();
                  }
               }, true));
            }
         }
      }
   }

   private static int a(int var0, float var1) {
      return Math.round((var0 >>> 24 & 0xFF) * var1) << 24 | var0 & 16777215;
   }

   private List<com.cheatbreaker.client.kazerui.gui.menus.HomeThemeCatalog.a> g() {
      ArrayList var1 = new ArrayList();

      for (com.cheatbreaker.client.kazerui.gui.menus.HomeThemeCatalog.a var3 : this.themes.c()) {
         if (var3.name.toLowerCase(Locale.ROOT).contains(this.search.toLowerCase(Locale.ROOT))) {
            var1.add(var3);
         }
      }

      Collections.sort(var1, new Comparator<com.cheatbreaker.client.kazerui.gui.menus.HomeThemeCatalog.a>() {
         public int compare(com.cheatbreaker.client.kazerui.gui.menus.HomeThemeCatalog.a var1, com.cheatbreaker.client.kazerui.gui.menus.HomeThemeCatalog.a var2) {
            int var3x = var1.name.compareToIgnoreCase(var2.name);
            return ThemePicker.this.sortAscending ? var3x : -var3x;
         }
      });
      return var1;
   }

   private void h() {
      Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
      this.open = false;
   }

   private void b(String var1) {
      if (!this.d()) {
         Minecraft var2 = Minecraft.getMinecraft();
         var2.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
         var2.getSoundHandler().playSound(PositionedSoundRecord.create(TRANSITION_SOUND, 1.0F));
         this.pendingTheme = var1;
         this.swapped = false;
         this.curtainStart = System.currentTimeMillis();
      }
   }

   boolean b(float var1, float var2) {
      if (this.open && !this.d()) {
         this.mx = var1;
         this.my = var2;

         for (int var3 = this.hits.size() - 1; var3 >= 0; var3--) {
            com.cheatbreaker.client.kazerui.gui.menus.ThemePicker.b var4 = this.hits.get(var3);
            if (var1 >= var4.x && var2 >= var4.y && var1 < var4.x + var4.w && var2 < var4.y + var4.h) {
               this.sortWasOpen = this.sortOpen;
               if (!var4.menuOption) {
                  this.sortOpen = false;
               }

               var4.run.run();
               return true;
            }
         }

         return true;
      } else {
         return this.c();
      }
   }

   boolean a(char var1, int var2) {
      if (!this.open) {
         return this.d();
      } else if (var2 == 1) {
         if (this.sortOpen) {
            this.sortOpen = false;
         } else {
            this.h();
         }

         return true;
      } else if (!this.searchFocused) {
         return true;
      } else if (GuiScreen.isCtrlKeyDown()) {
         if (var2 == 30) {
            this.searchSelected = true;
            return true;
         } else if (var2 != 46 && var2 != 45) {
            if (var2 == 47) {
               String var3 = (this.searchSelected ? "" : this.search) + ChatAllowedCharacters.filterAllowedCharacters(GuiScreen.getClipboardString());
               this.search = var3.substring(0, Math.min(64, var3.length()));
               this.searchSelected = false;
            }

            return true;
         } else {
            if (this.searchSelected) {
               GuiScreen.setClipboardString(this.search);
               if (var2 == 45) {
                  this.search = "";
                  this.searchSelected = false;
               }
            }

            return true;
         }
      } else if (var2 != 28 && var2 != 156) {
         if (var2 != 14 && var2 != 211) {
            if (ChatAllowedCharacters.isAllowedCharacter(var1)) {
               if (this.searchSelected) {
                  this.search = "";
               }

               if (this.search.length() < 64) {
                  this.search = this.search + var1;
               }

               this.searchSelected = false;
            }
         } else {
            if (this.searchSelected) {
               this.search = "";
            } else if (!this.search.isEmpty()) {
               this.search = this.search.substring(0, this.search.length() - 1);
            }

            this.searchSelected = false;
         }

         return true;
      } else {
         this.searchFocused = this.searchSelected = false;
         return true;
      }
   }

   private void i() {
      long var1 = System.currentTimeMillis() - this.curtainStart;
      float var3 = Math.min(1.0F, (float)var1 / 1200.0F);
      float var4;
      if (var1 < 1200L) {
         var4 = HomeScreen.a(0.37F, 0.33F, 0.15F, 1.0F, var3);
      } else {
         if (!this.swapped) {
            this.swapped = true;
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(TRANSITION_SOUND, 1.0F));
            this.themes.a(this.pendingTheme);
         }

         float var5 = Math.min(1.0F, (float)(var1 - 1200L) / 1200.0F);
         var4 = 1.0F - HomeScreen.a(0.37F, 0.33F, 0.15F, 1.0F, var5);
         if (var5 >= 1.0F) {
            this.curtainStart = -1L;
            return;
         }
      }

      float var15 = this.vw / 2.0F;
      float var6 = this.vh * 1.5F;

      for (int var7 = 0; var7 < 2; var7++) {
         float var8 = var7 == 0 ? -1.0F : 1.0F;
         float var9 = b(var8 * 1.25F * var15, -var8 * 20.0F, var4);
         float var10 = b(-0.1F * var6, 0.0F, var4);
         float var11 = b(-var8 * 20.0F, var8 * 3.0F, var4);
         GlStateManager.pushMatrix();
         GlStateManager.translate(var7 * var15 + var15 / 2.0F + var9, var6 / 2.0F + var10, 0.0F);
         GlStateManager.rotate(var11, 0.0F, 0.0F, 1.0F);
         Minecraft.getMinecraft().getTextureManager().bindTexture(CURTAINS);
         GL11.glTexParameteri(3553, 10242, 10497);
         GL11.glTexParameteri(3553, 10241, 9729);
         GL11.glTexParameteri(3553, 10240, 9729);
         GlStateManager.enableBlend();
         GlStateManager.enableTexture2D();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         float var12 = 1.1764705F;
         Tessellator var13 = Tessellator.getInstance();
         WorldRenderer var14 = var13.getWorldRenderer();
         var14.begin(7, DefaultVertexFormats.POSITION_TEX);
         var14.pos(-var15 / 2.0F, var6 / 2.0F, 0.0).tex(0.0, 1.0).endVertex();
         var14.pos(var15 / 2.0F, var6 / 2.0F, 0.0).tex(var12, 1.0).endVertex();
         var14.pos(var15 / 2.0F, -var6 / 2.0F, 0.0).tex(var12, 0.0).endVertex();
         var14.pos(-var15 / 2.0F, -var6 / 2.0F, 0.0).tex(0.0, 0.0).endVertex();
         var13.draw();
         GlStateManager.popMatrix();
      }
   }

   private static float b(float var0, float var1, float var2) {
      return var0 + (var1 - var0) * var2;
   }

   private static final class a {
      boolean on;
      float from;
      long start;
      final float ms;
      final float[] curve;

      a(float var1, float[] var2) {
         this.ms = var1;
         this.curve = var2;
      }

      float a(boolean var1) {
         long var2 = System.currentTimeMillis();
         float var4 = this.a(var2);
         if (var1 != this.on) {
            this.from = var4;
            this.on = var1;
            this.start = var2;
            var4 = this.a(var2);
         }

         return var4;
      }

      private float a(long var1) {
         float var3 = Math.min(1.0F, (float)(var1 - this.start) / this.ms);
         return this.from + ((this.on ? 1 : 0) - this.from) * HomeScreen.a(this.curve[0], this.curve[1], this.curve[2], this.curve[3], var3);
      }
   }

   private static final class b {
      final float x;
      final float y;
      final float w;
      final float h;
      final Runnable run;
      final boolean menuOption;

      b(float var1, float var2, float var3, float var4, Runnable var5) {
         this(var1, var2, var3, var4, var5, false);
      }

      b(float var1, float var2, float var3, float var4, Runnable var5, boolean var6) {
         this.x = var1;
         this.y = var2;
         this.w = var3;
         this.h = var4;
         this.run = var5;
         this.menuOption = var6;
      }
   }
}
