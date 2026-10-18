package com.cheatbreaker.client.kazerui.gui.menus;

import com.cheatbreaker.client.util.KazerBuildInfo;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.SkinManager.SkinAvailableCallback;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

public final class HomeScreen extends GuiScreen {
   private static final int SPACE_1 = -15526886;
   private static final int SPACE_2 = -15066335;
   private static final int SPACE_3 = -14539988;
   private static final int SPACE_5 = -13684421;
   private static final int SPACE_11 = -5131328;
   private static final int SPACE_12 = -1184013;
   private static final int LEGACY_TEXT = -4275267;
   private static final int FOOTER = 1558439933;
   private static final int TOOLTIP_BG = -15987438;
   private static final int TOOLTIP_SUB = -8815737;
   private static final int SHADOW = 1107954186;
   private static final int GHOST = 348834805;
   private static final float FACE_ATLAS_HEIGHT = 64.0F;
   private static final ResourceLocation LOGO = a("logo/kazer.png");
   private static final ResourceLocation ICON_SINGLE = a("home/singleplayer.png");
   private static final ResourceLocation ICON_MULTI = a("home/multiplayer.png");
   private static final ResourceLocation ICON_STATUS = a("home/account-status.png");
   private static final ResourceLocation ICON_CHEVRON = a("home/account-chevron.png");
   private static final ResourceLocation ICON_BRUSH = a("home/brush.png");
   private static final ResourceLocation ICON_CLOSE = a("home/close.png");
   private static final ResourceLocation NAV_LOGO = LOGO;
   private static final ResourceLocation NAV_COG = a("home/nav-cog.png");
   private static final ResourceLocation TYPE_KAZER = a("home/tooltip-kazer-type-icon.png");
   private static final ResourceLocation TYPE_VANILLA = a("home/tooltip-vanilla-type-icon.png");
   private static final ResourceLocation TYPE_EXTERNAL = a("home/tooltip-external-type-icon.png");
   private static int panoramaTimer;
   private final com.cheatbreaker.client.kazerui.hud.HudFeatureManager features;
   private final KeyBinding menuKey;
   private final com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics gfx = new com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics();
   private final com.cheatbreaker.client.kazerui.gui.menus.HomeThemeCatalog themes;
   private final com.cheatbreaker.client.kazerui.gui.menus.ThemePicker picker;
   private final Map<String, HomeScreen.a> fades = new HashMap<>();
   private final HomeScreen.c[] stars = new HomeScreen.c[8];
   private final long opened = System.currentTimeMillis();
   private static DynamicTexture viewportTexture;
   private static ResourceLocation backgroundTexture;
   private ResourceLocation face;
   private float cssW;
   private float cssH;
   private float mouseCssX = -1.0F;
   private float mouseCssY = -1.0F;
   private final List<HomeScreen.b> hits = new ArrayList<>();
   private final GuiButton singleButton = new GuiButton(1, 0, 0, "Singleplayer");
   private final GuiButton multiButton = new GuiButton(2, 0, 0, "Multiplayer");
   private final GuiButton optionsButton = new GuiButton(3, 0, 0, 98, 20, "Options...");
   private final GuiButton quitButton = new GuiButton(4, 0, 0, 98, 20, "Quit Game");
   private boolean accountOpen;
   private JsonArray savedAccounts = new JsonArray();
   private boolean accountBusy;
   private String accountStatus = "Switch Account";
   private int accountScroll;
   private long accountClosedAt;
   private final HomeScreen.a accountFade = new HomeScreen.a(150.0F);
   private float popX;
   private float popY;
   private float popW;
   private float popH;
   private float accountW;
   private String faceId = "";

   private static ResourceLocation a(String var0) {
      return new ResourceLocation("kazerui", "ui/" + var0);
   }

   public HomeScreen(com.cheatbreaker.client.kazerui.hud.HudFeatureManager var1, KeyBinding var2) {
      this.features = var1;
      this.menuKey = var2;
      this.themes = new com.cheatbreaker.client.kazerui.gui.menus.HomeThemeCatalog(var1);
      this.picker = new com.cheatbreaker.client.kazerui.gui.menus.ThemePicker(this.gfx, this.themes);

      for (int var3 = 0; var3 < this.stars.length; var3++) {
         this.stars[var3] = new HomeScreen.c();
      }
   }

   public void initGui() {
      com.cheatbreaker.client.gui.screens.AccountManagerScreen.a();
      if (viewportTexture == null) {
         viewportTexture = new DynamicTexture(256, 256);
         backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", viewportTexture);
      }

      if (this.face == null || !this.mc.getSession().getPlayerID().equals(this.faceId)) {
         this.f();
      }
   }

   public ResourceLocation a() {
      return this.face;
   }

   public float b() {
      return 64.0F;
   }

   public float c() {
      return 2.0F * this.gfx.dsf / new ScaledResolution(this.mc).getScaleFactor();
   }

   private void f() {
      final String var1 = this.mc.getSession().getPlayerID();
      this.faceId = var1;
      GameProfile var2 = this.mc.getSession().getProfile();
      this.face = DefaultPlayerSkin.getDefaultSkin(var2.getId() == null ? UUID.randomUUID() : var2.getId());
      if (var2.getId() != null) {
         this.mc.getSkinManager().loadProfileTextures(var2, new SkinAvailableCallback() {
            public void skinAvailable(Type var1x, ResourceLocation var2x, MinecraftProfileTexture var3) {
               if (var1x == Type.SKIN && var1.equals(HomeScreen.this.faceId)) {
                  HomeScreen.this.face = var2x;
               }
            }
         }, true);
      }
   }

   public void updateScreen() {
      panoramaTimer++;
   }

   public static void d() {
      panoramaTimer++;
   }

   public static void a(GuiScreen var0, GuiScreen var1, float var2) {
      if (var0 instanceof com.cheatbreaker.client.kazerui.gui.menus.HomeScreen) {
         com.cheatbreaker.client.kazerui.gui.menus.HomeScreen var3 = (com.cheatbreaker.client.kazerui.gui.menus.HomeScreen)var0;
         var3.width = var1.width;
         var3.height = var1.height;
         if (viewportTexture != null) {
            cn.kazer.client189.render.GlStateBridge.c(1048575);

            try {
               com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.a();
               GlStateManager.disableAlpha();
               var3.b(var2);
            } finally {
               cn.kazer.client189.render.GlStateBridge.a();
            }

            return;
         }
      }

      var1.drawBackground(0);
   }

   public void drawScreen(int var1, int var2, float var3) {
      cn.kazer.client189.render.GlStateBridge.c(1048575);

      try {
         com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.a();
         GlStateManager.disableAlpha();
         this.b(var3);
         GlStateManager.enableAlpha();
         int var4 = new ScaledResolution(this.mc).getScaleFactor();
         this.gfx.dsf = Math.max(1.0F, var4 / 2.0F);
         this.cssW = this.mc.displayWidth / this.gfx.dsf;
         this.cssH = this.mc.displayHeight / this.gfx.dsf;
         this.mouseCssX = Mouse.getX() / this.gfx.dsf;
         this.mouseCssY = (this.mc.displayHeight - Mouse.getY() - 1) / this.gfx.dsf;
         this.hits.clear();
         this.i();
         GlStateManager.pushMatrix();
         float var5 = this.gfx.dsf / var4;
         GlStateManager.scale(var5, var5, 1.0F);
         GlStateManager.disableDepth();
         GlStateManager.disableAlpha();
         this.h();
         this.a(this.cssW - 115.0F, ICON_BRUSH, "brush", new Runnable() {
            @Override
            public void run() {
               HomeScreen.this.picker.a();
            }
         });
         this.a(this.cssW - 63.0F, ICON_CLOSE, "close", new Runnable() {
            @Override
            public void run() {
               HomeScreen.this.mc.shutdown();
            }
         });
         this.m();
         this.n();
         this.a(2);
         this.j();
         this.picker.a(this.cssW, this.cssH);
         this.picker.a(this.cssW, this.cssH, this.mouseCssX, this.mouseCssY);
         GlStateManager.enableAlpha();
         GlStateManager.enableDepth();
         GlStateManager.popMatrix();
      } finally {
         cn.kazer.client189.render.GlStateBridge.a();
      }
   }

   private boolean g() {
      return this.accountOpen || System.currentTimeMillis() - this.accountClosedAt < 200L;
   }

   private boolean a(float var1, float var2) {
      return this.g() && var1 >= this.popX && var2 >= this.popY && var1 < this.popX + this.popW && var2 < this.popY + this.popH;
   }

   private boolean a(float var1, float var2, float var3, float var4) {
      return !this.picker.c() && !this.a(this.mouseCssX, this.mouseCssY) && this.b(var1, var2, var3, var4);
   }

   private boolean b(float var1, float var2, float var3, float var4) {
      return this.mouseCssX >= var1 && this.mouseCssY >= var2 && this.mouseCssX < var1 + var3 && this.mouseCssY < var2 + var4;
   }

   private float a(String var1, boolean var2) {
      HomeScreen.a var3 = this.fades.get(var1);
      if (var3 == null) {
         var3 = new HomeScreen.a();
         this.fades.put(var1, var3);
      }

      return var3.a(var2);
   }

   private void h() {
      String var1 = this.mc.getSession().getUsername();
      boolean var2 = false;
      float var3 = var2 ? this.gfx.a(var1, 14.0F) : this.gfx.a(var1, false, 16.0F);
      float var4 = this.accountW = Math.max(144.0F, var3 + 96.0F);
      float var5 = 20.0F;
      float var6 = 20.0F;
      boolean var7 = this.a(var5, var6, var4, 40.0F);
      float var8 = this.a("account", var7);
      if (var2) {
         this.a(var5, var6, var4, 40.0F, 8.0F, var8);
      } else {
         this.gfx.a(this.gfx.a(var4, 40.0F, 8.0F), var5, var6, a(-15526886, -15066335, var8));
      }

      int var9 = var2 ? a(-4275267, -1, var8) : a(-5131328, -1184013, var8);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.gfx.a(this.face, 32.0F, 32.0F, 16.0F, 16.0F, 5.0F, 0.125F, 0.125F, 0.25F, 0.25F);
      this.gfx.a(this.face, 32.0F, 32.0F, 16.0F, 16.0F, 5.0F, 0.625F, 0.125F, 0.75F, 0.25F);
      if (var2) {
         com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner var10 = this.gfx.b(var1, 14.0F);
         float var11 = var6 + (40.0F - var10.height) / 2.0F;
         this.gfx.a(var10, 53.0F, var11 + 1.0F, 536870912);
         this.gfx.a(var10, 52.0F, var11, var9);
      } else {
         this.gfx.a(LOGO, 53.0F, 31.0F, 18.0F, 18.0F, 0xFF000000 | com.cheatbreaker.client.CheatBreakerClient.N().L().b());
         this.gfx.a(this.gfx.b(var1, false, 16.0F), 77.0F, 29.5F, var9);
      }

      this.gfx.a(ICON_CHEVRON, 85.0F + var3, 32.0F, 16.0F, 16.0F, var9);
   }

   private void i() {
      float var1 = this.gfx.a(this.mc.getSession().getUsername(), false, 18.0F);
      this.popW = Math.max(240.0F, var1 + 80.0F);
      this.popH = 92 + Math.min(4, this.savedAccounts.size()) * 36;
      this.popX = 20.0F;
      this.popY = 68.0F - 10.0F * (1.0F - this.accountFade.a(this.accountOpen));
   }

   private void j() {
      if (this.g()) {
         float var1 = this.accountFade.a(this.accountOpen);
         float var2 = this.popX;
         float var3 = this.popY;
         float var4 = this.popW;
         float var5 = this.popH;
         float var6 = com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.a(24.0F);
         this.gfx.a(this.gfx.a(var4, var5, 8.0F, 8.0F, 24.0F, 0.0F), var2 - var6, var3 - var6, a(1292503562, var1));
         this.gfx
            .a(
               this.gfx.a(var4, var5, 8.0F, 2.0F, 8.0F, 0.0F),
               var2 - com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.a(8.0F),
               var3 - com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.a(8.0F),
               a(638192138, var1)
            );
         this.gfx.a(this.gfx.a(var4, var5, 8.0F), var2, var3, a(-15526886, var1));
         float var7 = var2 + 16.0F;
         float var8 = var3 + 16.0F;
         this.gfx.a(this.gfx.b(this.accountBusy ? "Switching account..." : this.accountStatus, true, 14.0F), var7, var8, a(-5131328, var1));

         for (int var9 = 0; var9 < 4 && var9 + this.accountScroll < this.savedAccounts.size(); var9++) {
            JsonObject var10 = this.savedAccounts.get(var9 + this.accountScroll).getAsJsonObject();
            String var11 = var10.get("username").getAsString();
            float var12 = var8 + 26.0F + var9 * 36;
            boolean var13 = var10.get("id").getAsString().equals(this.mc.getSession().getPlayerID());
            this.gfx.a(this.gfx.a(var4 - 32.0F, 32.0F, 7.0F), var7, var12, a(this.b(var7, var12, var4 - 32.0F, 32.0F) ? -13684421 : -14539988, var1));
            this.gfx.a(this.gfx.b(var11, false, 16.0F), var7 + 12.0F, var12 + 6.0F, a(a(var10) ? -36227 : (var13 ? -15284243 : -1184013), var1));
            if (var13) {
               this.gfx.a(this.gfx.b("Active", false, 12.0F), var7 + var4 - 82.0F, var12 + 8.0F, a(-5131328, var1));
            }
         }

         if (this.accountBusy) {
            GlStateManager.disableTexture2D();
            GlStateManager.color(1.0F, 1.0F, 1.0F, var1);
            GL11.glLineWidth(2.0F);
            GL11.glBegin(3);
            double var14 = System.nanoTime() / 1.0E9 * 5.0;

            for (int var16 = 0; var16 <= 20; var16++) {
               double var17 = var14 + var16 * Math.PI / 16.0;
               GL11.glVertex2d(var2 + var4 - 24.0F + Math.cos(var17) * 6.0, var8 + 8.0F + Math.sin(var17) * 6.0);
            }

            GL11.glEnd();
            GlStateManager.enableTexture2D();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         }

         float var15 = var3 + var5 - 48.0F;
         this.gfx.a(this.gfx.a(var4 - 32.0F, 32.0F, 7.0F), var7, var15, a(this.b(var7, var15, var4 - 32.0F, 32.0F) ? -15252390 : -15584958, var1));
         this.gfx.a(this.gfx.b("Add Account", true, 14.0F), var7 + 12.0F, var15 + 7.0F, a(-15284243, var1));
      }
   }

   private void k() {
      if (this.accountOpen) {
         this.l();
      } else {
         this.accountOpen = true;
         this.accountStatus = "Switch Account";
         com.cheatbreaker.client.gui.screens.AccountService.a("/accounts", var1 -> {
            if (var1.has("accounts")) {
               this.savedAccounts = var1.getAsJsonArray("accounts");
            }
         }, var1 -> this.accountStatus = "Accounts unavailable");
      }
   }

   private void b(String var1) {
      if (!this.accountBusy) {
         this.accountBusy = true;
         com.cheatbreaker.client.gui.screens.AccountService.a(var1, var1x -> {
            this.accountBusy = false;
            com.cheatbreaker.client.gui.screens.AccountService.a(var1x);
            this.l();
            this.f();
         }, var1x -> {
            this.accountBusy = false;
            this.accountStatus = "Sign-in cancelled or failed";
            this.accountOpen = true;
            com.cheatbreaker.client.gui.screens.AccountService.a("/accounts", var1xx -> {
               if (var1xx.has("accounts")) {
                  this.savedAccounts = var1xx.getAsJsonArray("accounts");
               }
            }, var0 -> {});
         });
      }
   }

   private static boolean a(JsonObject var0) {
      return var0.has("needsVerification") && var0.get("needsVerification").getAsBoolean();
   }

   private void l() {
      if (this.accountOpen) {
         this.accountOpen = false;
         this.accountClosedAt = System.currentTimeMillis();
      }
   }

   private static int a(int var0, float var1) {
      return Math.round((var0 >>> 24) * var1) << 24 | var0 & 16777215;
   }

   private void a(float var1, ResourceLocation var2, String var3, Runnable var4) {
      float var5 = this.a(var3, this.a(var1, 20.0F, 42.0F, 40.0F));
      this.gfx.a(this.gfx.a(42.0F, 40.0F, 8.0F), var1, 20.0F, a(-15526886, -15066335, var5));
      this.gfx.a(var2, var1 + 12.0F, 31.0F, 18.0F, 18.0F, a(-5131328, -1184013, var5));
      this.hits.add(new HomeScreen.b(var1, 20.0F, 42.0F, 40.0F, var4));
   }

   private void m() {
      float var1 = this.cssH / 2.0F - 44.0F;
      float var2 = this.cssW / 2.0F - 200.0F;
      float var3 = this.cssW / 2.0F - 62.0F;
      float var4 = var1 - 152.0F;
      this.gfx.a(LOGO, var3, var4, 124.0F, 124.0F, -1);
      Runnable var5 = new Runnable() {
         @Override
         public void run() {
            HomeScreen.this.mc.displayGuiScreen(new GuiSelectWorld(HomeScreen.this));
         }
      };
      Runnable var6 = new Runnable() {
         @Override
         public void run() {
            HomeScreen.this.mc.displayGuiScreen(new GuiMultiplayer(HomeScreen.this));
         }
      };
      Runnable var7 = () -> this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
      Runnable var8 = () -> this.mc.shutdown();
      if (this.picker.e()) {
         this.a(this.singleButton, var2, var1, var5);
         this.a(this.multiButton, var2, var1 + 48.0F, var6);
         this.a(this.optionsButton, var2, var1 + 112.0F, var7);
         this.a(this.quitButton, var2 + 204.0F, var1 + 112.0F, var8);
      } else {
         this.a(var2, var1, "Singleplayer", ICON_SINGLE, var5);
         this.a(var2, var1 + 48.0F, "Multiplayer", ICON_MULTI, var6);
      }
   }

   private void a(GuiButton var1, float var2, float var3, Runnable var4) {
      var1.xPosition = (int)(var2 / 2.0F);
      var1.yPosition = (int)(var3 / 2.0F);
      GlStateManager.pushMatrix();

      try {
         GlStateManager.scale(2.0F, 2.0F, 1.0F);
         var1.drawButton(this.mc, (int)(this.mouseCssX / 2.0F), (int)(this.mouseCssY / 2.0F));
      } finally {
         GlStateManager.popMatrix();
      }

      this.hits.add(new HomeScreen.b(var2, var3, var1.width * 2, var1.height * 2, var4));
   }

   private void a(float var1, float var2, String var3, Runnable var4) {
      float var5 = 400.0F;
      float var6 = 34.0F;
      float var7 = 8.0F;
      float var8 = this.a("legacy:" + var3, this.a(var1, var2, var5, var6));
      this.a(var1, var2, var5, var6, var7, var8);
      String var9 = var3.replace("", " ").trim();
      com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner var10 = this.gfx.b(var9, 14.0F);
      float var11 = var1 + (var5 - this.gfx.a(var9, 14.0F)) / 2.0F;
      float var12 = var2 + (var6 - var10.height) / 2.0F;
      this.gfx.a(var10, var11 + 1.0F, var12 + 1.0F, 536870912);
      this.gfx.a(var10, var11, var12, -4275267);
      this.hits.add(new HomeScreen.b(var1, var2, var5, var6, var4));
   }

   private void a(float var1, float var2, float var3, float var4, float var5, float var6) {
      this.gfx.a(this.gfx.a(var3, var4, var5), var1, var2, a(553648127, 1174405119, var6));
      this.gfx.a(this.gfx.a(var3, var4, var5, 0.0F, 1.0F), var1, var2, 1076176165);
      this.gfx.a(this.gfx.a(var3, var4, var5, 1.0F, 1.0F), var1, var2, 553648127);
   }

   private void a(float var1, float var2, String var3, ResourceLocation var4, Runnable var5) {
      float var6 = this.a(var3, this.a(var1, var2, 400.0F, 40.0F));
      this.gfx.a(this.gfx.a(400.0F, 40.0F, 8.0F), var1, var2, a(-15526886, -15066335, var6));
      float var7 = this.gfx.a(var3, false, 18.0F);
      float var8 = var1 + 200.0F - (24.0F + var7) / 2.0F;
      this.gfx.a(var4, var8, var2 + 12.0F, 16.0F, 16.0F, -5131328);
      this.gfx.a(this.gfx.b(var3, false, 18.0F), var8 + 24.0F, var2 + 8.0F, -1184013);
      this.hits.add(new HomeScreen.b(var1, var2, 400.0F, 40.0F, var5));
   }

   private void a(int var1) {
      float var2 = var1 * 38 + (var1 - 1) * 8;
      float var3 = (this.cssW - 30.0F - var2 - 16.0F) / 2.0F;
      List var4 = this.a(KazerBuildInfo.windowTitle("1.8.9"), var3);

      for (int var5 = 0; var5 < var4.size(); var5++) {
         this.gfx.a(this.gfx.b((String)var4.get(var5), false, 16.0F), 15.0F, this.cssH - 20.0F - 21 * (var4.size() - var5), 1558439933);
      }

      List var8 = this.a("Not affiliated with Mojang or Microsoft. Do not distribute!", var3);

      for (int var6 = 0; var6 < var8.size(); var6++) {
         String var7 = (String)var8.get(var6);
         this.gfx
            .a(this.gfx.b(var7, false, 16.0F), this.cssW - 15.0F - this.gfx.a(var7, false, 16.0F), this.cssH - 20.0F - 21 * (var8.size() - var6), 1558439933);
      }
   }

   private List<String> a(String var1, float var2) {
      ArrayList var3 = new ArrayList();
      String var4 = "";

      for (String var8 : var1.split(" ")) {
         String var9 = var4.isEmpty() ? var8 : var4 + " " + var8;
         if (!(this.gfx.a(var9, false, 16.0F) <= var2) && (!var4.isEmpty() || !(var2 <= 0.0F))) {
            if (!var4.isEmpty()) {
               var3.add(var4);
            }

            var4 = var8;

            while (var2 > 0.0F && this.gfx.a(var4, false, 16.0F) > var2 && var4.length() > 1) {
               int var10 = var4.length() - 1;

               while (var10 > 1 && this.gfx.a(var4.substring(0, var10), false, 16.0F) > var2) {
                  var10--;
               }

               var3.add(var4.substring(0, var10));
               var4 = var4.substring(var10);
            }
         } else {
            var4 = var9;
         }
      }

      if (!var4.isEmpty()) {
         var3.add(var4);
      }

      return var3;
   }

   private void n() {
      String[][] var1 = new String[][]{{"Kazer Settings", "Kazer"}, {"Minecraft Settings", "Minecraft"}};
      ResourceLocation[] var2 = new ResourceLocation[]{NAV_LOGO, NAV_COG};
      ResourceLocation[] var3 = new ResourceLocation[]{TYPE_KAZER, TYPE_VANILLA};
      Runnable[] var4 = new Runnable[]{new Runnable() {
         @Override
         public void run() {
            HomeScreen.this.mc.displayGuiScreen(new com.cheatbreaker.client.kazerui.gui.HudMoveScreen(HomeScreen.this.features, HomeScreen.this.menuKey, HomeScreen.this));
         }
      }, new Runnable() {
         @Override
         public void run() {
            HomeScreen.this.mc.displayGuiScreen(new GuiOptions(HomeScreen.this, HomeScreen.this.mc.gameSettings));
         }
      }};
      float var5 = var1.length * 38 + (var1.length - 1) * 8;
      float var6 = this.cssW / 2.0F - var5 / 2.0F;
      float var7 = this.cssH - 58.0F;
      int var8 = -1;

      for (int var9 = 0; var9 < var1.length; var9++) {
         float var10 = var6 + var9 * 46;
         boolean var11 = this.a(var10, var7, 38.0F, 38.0F);
         if (var11) {
            var8 = var9;
         }

         float var12 = this.a("nav" + var9, var11);
         this.gfx.a(this.gfx.a(38.0F, 38.0F, 8.0F), var10, var7, a(-15526886, -15066335, var12));
         this.gfx.a(var2[var9], var10 + 11.0F, var7 + 11.0F, 16.0F, 16.0F, -1493172225);
         this.hits.add(new HomeScreen.b(var10, var7, 38.0F, 38.0F, var4[var9]));
      }

      if (var8 >= 0) {
         this.a(var6 + var8 * 46 + 19.0F, var7 - 10.0F, var1[var8][0], var1[var8][1], var3[var8]);
      }
   }

   private void a(float var1, float var2, String var3, String var4, ResourceLocation var5) {
      float var6 = this.gfx.a(var3, true, 16.0F);
      float var7 = 18.0F + this.gfx.a(var4, false, 14.0F);
      float var8 = Math.max(var6, var7) + 24.0F;
      float var9 = 58.0F;
      float var10 = var1 - var8 / 2.0F;
      float var11 = var2 - var9;
      float var12 = com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.a(15.9F);
      this.gfx.a(this.gfx.a(var8, var9, 8.0F, 7.0F, 15.9F, -5.0F), var10 - var12, var11 - var12, 1107954186);
      this.gfx.a(this.gfx.a(var8, var9, 8.0F), var10, var11, -15987438);
      this.gfx.a(this.gfx.b(var3, true, 16.0F), var10 + 12.0F, var11 + 9.0F, -5131328);
      this.gfx.a(var5, var10 + 12.0F, var11 + 34.0F, 14.0F, 14.0F, -8815737);
      this.gfx.a(this.gfx.b(var4, false, 14.0F), var10 + 30.0F, var11 + 32.0F, -8815737);
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      if (var3 == 0) {
         if (this.picker.c()) {
            this.picker.b(this.mouseCssX, this.mouseCssY);
         } else if (this.a(this.mouseCssX, this.mouseCssY)) {
            if (this.accountOpen && this.b(this.popX + 16.0F, this.popY + this.popH - 48.0F, this.popW - 32.0F, 32.0F)) {
               this.l();
               this.b("/login");
            }

            if (this.accountOpen && !this.accountBusy) {
               for (int var7 = 0; var7 < 4 && var7 + this.accountScroll < this.savedAccounts.size(); var7++) {
                  if (this.b(this.popX + 16.0F, this.popY + 42.0F + var7 * 36, this.popW - 32.0F, 32.0F)) {
                     JsonObject var8 = this.savedAccounts.get(var7 + this.accountScroll).getAsJsonObject();
                     String var6 = var8.get("id").getAsString();
                     if (a(var8)) {
                        this.b("/login");
                     } else if (!var6.equals(this.mc.getSession().getPlayerID())) {
                        this.b("/select/" + var6);
                     }

                     return;
                  }
               }
            }
         } else if (this.b(20.0F, 20.0F, this.accountW, 40.0F)) {
            this.k();
         } else {
            this.l();

            for (HomeScreen.b var5 : this.hits) {
               if (this.mouseCssX >= var5.x && this.mouseCssY >= var5.y && this.mouseCssX < var5.x + var5.w && this.mouseCssY < var5.y + var5.h) {
                  this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                  var5.action.run();
                  return;
               }
            }
         }
      }
   }

   protected void keyTyped(char var1, int var2) {
      if (!this.picker.c() && var2 == this.menuKey.getKeyCode() && !Keyboard.isRepeatEvent()) {
         this.mc.displayGuiScreen(com.cheatbreaker.client.CheatBreakerClient.N().ownedUi.c(this));
      } else {
         this.picker.a(var1, var2);
      }
   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      int var1 = Mouse.getEventDWheel();
      if (this.accountOpen && var1 != 0) {
         this.accountScroll = Math.max(0, Math.min(Math.max(0, this.savedAccounts.size() - 4), this.accountScroll + (var1 < 0 ? 1 : -1)));
      } else {
         if (var1 != 0) {
            this.picker.a(Integer.signum(var1));
         }
      }
   }

   public void onGuiClosed() {
      this.l();
   }

   public void e() {
      this.gfx.b();
      this.picker.b();
      if (backgroundTexture != null) {
         this.mc.getTextureManager().deleteTexture(backgroundTexture);
      }

      viewportTexture = null;
      backgroundTexture = null;
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   private void a(float var1) {
      com.cheatbreaker.client.kazerui.gui.menus.HomeThemeCatalog.a var2 = this.themes.b();
      Tessellator var3 = Tessellator.getInstance();
      WorldRenderer var4 = var3.getWorldRenderer();
      GlStateManager.matrixMode(5889);
      GlStateManager.pushMatrix();
      GlStateManager.loadIdentity();
      Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
      GlStateManager.matrixMode(5888);
      GlStateManager.pushMatrix();
      GlStateManager.loadIdentity();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.disableCull();
      GlStateManager.depthMask(false);
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      byte var5 = 8;

      for (int var6 = 0; var6 < var5 * var5; var6++) {
         GlStateManager.pushMatrix();
         float var7 = ((float)(var6 % var5) / var5 - 0.5F) / 64.0F;
         float var8 = ((float)(var6 / var5) / var5 - 0.5F) / 64.0F;
         GlStateManager.translate(var7, var8, 0.0F);
         GlStateManager.rotate(MathHelper.sin((panoramaTimer + var1) / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(-(panoramaTimer + var1) * 0.1F, 0.0F, 1.0F, 0.0F);

         for (int var9 = 0; var9 < 6; var9++) {
            GlStateManager.pushMatrix();
            if (var9 == 1) {
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            }

            if (var9 == 2) {
               GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            }

            if (var9 == 3) {
               GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
            }

            if (var9 == 4) {
               GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            }

            if (var9 == 5) {
               GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            }

            this.mc.getTextureManager().bindTexture(var2.a(var9));
            var4.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            int var10 = 255 / (var6 + 1);
            var4.pos(-1.0, -1.0, 1.0).tex(0.0, 0.0).color(255, 255, 255, var10).endVertex();
            var4.pos(1.0, -1.0, 1.0).tex(1.0, 0.0).color(255, 255, 255, var10).endVertex();
            var4.pos(1.0, 1.0, 1.0).tex(1.0, 1.0).color(255, 255, 255, var10).endVertex();
            var4.pos(-1.0, 1.0, 1.0).tex(0.0, 1.0).color(255, 255, 255, var10).endVertex();
            var3.draw();
            GlStateManager.popMatrix();
         }

         GlStateManager.popMatrix();
         GlStateManager.colorMask(true, true, true, false);
      }

      var4.setTranslation(0.0, 0.0, 0.0);
      GlStateManager.colorMask(true, true, true, true);
      GlStateManager.matrixMode(5889);
      GlStateManager.popMatrix();
      GlStateManager.matrixMode(5888);
      GlStateManager.popMatrix();
      GlStateManager.depthMask(true);
      GlStateManager.enableCull();
      GlStateManager.enableDepth();
   }

   private void o() {
      this.mc.getTextureManager().bindTexture(backgroundTexture);
      GL11.glTexParameteri(3553, 10241, 9729);
      GL11.glTexParameteri(3553, 10240, 9729);
      GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.colorMask(true, true, true, false);
      Tessellator var1 = Tessellator.getInstance();
      WorldRenderer var2 = var1.getWorldRenderer();
      var2.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      GlStateManager.disableAlpha();
      byte var3 = 3;

      for (int var4 = 0; var4 < var3; var4++) {
         float var5 = 1.0F / (var4 + 1);
         int var6 = this.width;
         int var7 = this.height;
         float var8 = (var4 - var3 / 2) / 256.0F;
         var2.pos(var6, var7, this.zLevel).tex(0.0F + var8, 1.0).color(1.0F, 1.0F, 1.0F, var5).endVertex();
         var2.pos(var6, 0.0, this.zLevel).tex(1.0F + var8, 1.0).color(1.0F, 1.0F, 1.0F, var5).endVertex();
         var2.pos(0.0, 0.0, this.zLevel).tex(1.0F + var8, 0.0).color(1.0F, 1.0F, 1.0F, var5).endVertex();
         var2.pos(0.0, var7, this.zLevel).tex(0.0F + var8, 0.0).color(1.0F, 1.0F, 1.0F, var5).endVertex();
      }

      var1.draw();
      GlStateManager.enableAlpha();
      GlStateManager.colorMask(true, true, true, true);
   }

   private void b(float var1) {
      this.mc.getFramebuffer().unbindFramebuffer();
      GlStateManager.viewport(0, 0, 256, 256);
      this.a(var1);

      for (int var2 = 0; var2 < 7; var2++) {
         this.o();
      }

      this.mc.getFramebuffer().bindFramebuffer(true);
      GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
      float var9 = this.width > this.height ? 120.0F / this.width : 120.0F / this.height;
      float var3 = this.height * var9 / 256.0F;
      float var4 = this.width * var9 / 256.0F;
      int var5 = this.width;
      int var6 = this.height;
      Tessellator var7 = Tessellator.getInstance();
      WorldRenderer var8 = var7.getWorldRenderer();
      var8.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      var8.pos(0.0, var6, this.zLevel).tex(0.5F - var3, 0.5F + var4).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      var8.pos(var5, var6, this.zLevel).tex(0.5F - var3, 0.5F - var4).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      var8.pos(var5, 0.0, this.zLevel).tex(0.5F + var3, 0.5F - var4).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      var8.pos(0.0, 0.0, this.zLevel).tex(0.5F + var3, 0.5F + var4).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      var7.draw();
   }

   static int a(int var0, int var1, float var2) {
      int var3 = 0;

      for (byte var4 = 0; var4 < 32; var4 += 8) {
         var3 |= Math.round((var0 >>> var4 & 0xFF) * (1.0F - var2) + (var1 >>> var4 & 0xFF) * var2) << var4;
      }

      return var3;
   }

   static float a(float var0, float var1, float var2, float var3, float var4) {
      if (var4 <= 0.0F) {
         return 0.0F;
      } else if (var4 >= 1.0F) {
         return 1.0F;
      } else {
         float var5 = var4;

         for (int var6 = 0; var6 < 8; var6++) {
            float var7 = a(var0, var2, var5) - var4;
            float var8 = b(var0, var2, var5);
            if (Math.abs(var7) < 1.0E-5F || Math.abs(var8) < 1.0E-6F) {
               break;
            }

            var5 -= var7 / var8;
         }

         if (var5 < 0.0F || var5 > 1.0F || Math.abs(a(var0, var2, var5) - var4) > 0.001F) {
            float var9 = 0.0F;
            float var10 = 1.0F;
            var5 = var4;

            for (int var11 = 0; var11 < 30; var11++) {
               if (a(var0, var2, var5) < var4) {
                  var9 = var5;
               } else {
                  var10 = var5;
               }

               var5 = (var9 + var10) / 2.0F;
            }
         }

         return a(var1, var3, var5);
      }
   }

   private static float a(float var0, float var1, float var2) {
      float var3 = 1.0F - var2;
      return 3.0F * var3 * var3 * var2 * var0 + 3.0F * var3 * var2 * var2 * var1 + var2 * var2 * var2;
   }

   private static float b(float var0, float var1, float var2) {
      float var3 = 1.0F - var2;
      return 3.0F * var3 * var3 * var0 + 6.0F * var3 * var2 * (var1 - var0) + 3.0F * var2 * var2 * (1.0F - var1);
   }

   private static final class a {
      boolean on;
      float from;
      long start;
      final float ms;

      a() {
         this(200.0F);
      }

      a(float var1) {
         this.ms = var1;
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
         float var4 = HomeScreen.a(0.25F, 0.1F, 0.25F, 1.0F, var3);
         return this.from + ((this.on ? 1 : 0) - this.from) * var4;
      }
   }

   private static final class b {
      final float x;
      final float y;
      final float w;
      final float h;
      final Runnable action;

      b(float var1, float var2, float var3, float var4, Runnable var5) {
         this.x = var1;
         this.y = var2;
         this.w = var3;
         this.h = var4;
         this.action = var5;
      }
   }

   private static final class c {
      final float duration = (float)(Math.random() * 6.0 + 3.0);
      final float delay = (float)(Math.random() / 2.0);

      private c() {
      }

      float a(float var1) {
         float var2 = var1 - this.delay;
         if (var2 <= 0.0F) {
            return 1.0F;
         } else {
            float var3 = var2 % this.duration / this.duration;
            float var4 = var3 < 0.5F ? var3 * 2.0F : (var3 - 0.5F) * 2.0F;
            float var5 = HomeScreen.a(0.42F, 0.0F, 0.58F, 1.0F, var4);
            return var3 < 0.5F ? 1.0F + -0.85F * var5 : 0.15F + 0.85F * var5;
         }
      }
   }
}
