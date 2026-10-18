package com.cheatbreaker.client.gui.screens;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public final class AccountManagerScreen extends GuiScreen {
   private static final int X = 10;
   private static final int Y = 34;
   private static final int W = 169;
   private final GuiScreen parent;
   private JsonArray accounts = new JsonArray();
   private final Map<String, ResourceLocation> skins = new HashMap<>();
   private boolean busy;
   private boolean managing;
   private boolean settings;
   private String status = "";
   private int scroll;

   public static void a() {
      com.cheatbreaker.client.gui.screens.AccountService.a();
   }

   public AccountManagerScreen(GuiScreen var1) {
      this(var1, false);
   }

   public AccountManagerScreen(GuiScreen var1, boolean var2) {
      this.parent = var1;
      this.settings = var2;
   }

   private float b() {
      if (this.parent instanceof com.cheatbreaker.client.gui.screens.KazerMainMenuScreen) {
         return ((com.cheatbreaker.client.gui.screens.KazerMainMenuScreen)this.parent).d();
      } else {
         return this.parent instanceof com.cheatbreaker.client.kazerui.gui.menus.HomeScreen
            ? ((com.cheatbreaker.client.kazerui.gui.menus.HomeScreen)this.parent).c()
            : com.cheatbreaker.client.CheatBreakerClient.N().e();
      }
   }

   private ResourceLocation c() {
      if (this.parent instanceof com.cheatbreaker.client.gui.screens.KazerMainMenuScreen) {
         return ((com.cheatbreaker.client.gui.screens.KazerMainMenuScreen)this.parent).j();
      } else {
         return this.parent instanceof com.cheatbreaker.client.kazerui.gui.menus.HomeScreen ? ((com.cheatbreaker.client.kazerui.gui.menus.HomeScreen)this.parent).a() : null;
      }
   }

   public void initGui() {
      com.cheatbreaker.client.gui.screens.AccountService.a();
      this.parent.setWorldAndResolution(this.mc, this.width, this.height);
      this.d();
   }

   private void d() {
      com.cheatbreaker.client.gui.screens.AccountService.a("/accounts", var1 -> {
         this.accounts = var1.getAsJsonArray("accounts");

         for (int var2 = 0; var2 < this.accounts.size(); var2++) {
            JsonObject var3 = this.accounts.get(var2).getAsJsonObject();
            String var4 = var3.get("id").getAsString();
            GameProfile var5 = new Session(var3.get("username").getAsString(), var4, "", "mojang").getProfile();
            this.mc.getSkinManager().loadProfileTextures(var5, (var2x, var3x, var4x) -> {
               if (var2x == Type.SKIN) {
                  this.skins.put(var4, var3x);
               }
            }, false);
         }
      }, var1 -> this.status = var1);
   }

   private List<JsonObject> e() {
      ArrayList var1 = new ArrayList();

      for (int var2 = 0; var2 < this.accounts.size(); var2++) {
         JsonObject var3 = this.accounts.get(var2).getAsJsonObject();
         if (this.managing || !var3.get("id").getAsString().equals(this.mc.getSession().getPlayerID())) {
            var1.add(var3);
         }
      }

      return var1;
   }

   private int f() {
      return Math.max(1, Math.min(4, (int)(this.height / this.b() - 34.0F - 154.0F) / 22));
   }

   private int g() {
      return 175 + Math.max(0, Math.min(this.f(), this.e().size()) - 1) * 22;
   }

   private int h() {
      return this.g() + 28 + (this.status.isEmpty() ? 0 : 31);
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.parent.drawScreen(-100, -100, var3);
      float var4 = this.b();
      var1 = (int)(var1 / var4);
      var2 = (int)(var2 / var4);
      cn.kazer.client189.render.GlStateBridge.c(1048575);
      GL11.glPushMatrix();

      try {
         GL11.glScalef(var4, var4, 1.0F);
         com.cheatbreaker.client.gui.render.RenderUtils.a(10.0, 34.0, 179.0, (double)this.h(), 4.0, -15658472);
         ResourceLocation var5 = this.skins.get(this.mc.getSession().getPlayerID());
         if (var5 == null) {
            var5 = this.c();
         }

         a(var5, 18, 42);
         com.cheatbreaker.client.gui.render.RenderUtils.a(new ResourceLocation("client/kazer_logo.png"), 70.0F, 55.0F, 9.0F, 9.0F);
         com.cheatbreaker.client.CheatBreakerClient.N().playRegular18px.b(this.mc.getSession().getUsername(), 81.0, 55.0, -855306);
         this.i().b(this.accounts.size() > 0 ? "Microsoft account" : "Current session", 70.0, 68.0, -5329221);
         this.a("Account Settings", 18, 94, 153, 20, var1, var2, -14539987, -4736825);
         com.cheatbreaker.client.gui.render.IconRenderer.a(com.cheatbreaker.client.gui.render.IconRenderer.a.GEAR, 57.0F, 100.0F, 8.0F, -4736825);
         cn.kazer.client189.render.DrawRect.a(18.0F, 121.0F, 171.0F, 121.5F, -15000541);
         this.i().b(this.settings ? "Account Details" : (this.managing ? "Manage Accounts" : "Other Accounts"), 18.0, 132.0, -921099);
         if (this.settings) {
            this.i().b("Signed in as " + this.mc.getSession().getUsername(), 18.0, 150.0, -4670777);
         } else {
            com.cheatbreaker.client.gui.render.IconRenderer.a(com.cheatbreaker.client.gui.render.IconRenderer.a.PLUS, 144.0F, 132.0F, 7.0F, -4736825);
            this.i().b(this.busy ? "..." : "Add", 155.0, 132.0, -4736825);
            List var6 = this.e();

            for (int var7 = 0; var7 < this.f() && this.scroll + var7 < var6.size(); var7++) {
               JsonObject var8 = (JsonObject)var6.get(this.scroll + var7);
               int var9 = 148 + var7 * 22;
               if (a(var1, var2, 18, var9, 153, 20)) {
                  com.cheatbreaker.client.gui.render.RenderUtils.a(18.0, (double)var9, 171.0, (double)(var9 + 20), 3.0, -14539987);
               }

               a(this.skins.get(var8.get("id").getAsString()), 22, var9 + 6, 8);
               this.i().b(var8.get("username").getAsString(), 32.0, (double)(var9 + 5), -4736825);
            }
         }

         this.a(!this.settings && !this.managing ? "Manage Accounts" : "Back", 18, this.g(), 153, 20, var1, var2, -15715773, -15284236);
         if (!this.settings && !this.managing) {
            com.cheatbreaker.client.gui.render.IconRenderer.a(com.cheatbreaker.client.gui.render.IconRenderer.a.PLUS, 47.0F, this.g() + 6, 8.0F, -15284236);
         }

         String var15 = this.status;

         for (int var16 = 0; var16 < 3 && !var15.isEmpty(); var16++) {
            int var17 = var15.length();

            while (var17 > 1 && this.i().b(var15.substring(0, var17)) > 153) {
               var17--;
            }

            this.i().b(var15.substring(0, var17), 18.0, (double)(this.g() + 25 + var16 * 9), -4736825);
            var15 = var15.substring(var17);
         }
      } finally {
         GL11.glPopMatrix();
         cn.kazer.client189.render.GlStateBridge.a();
      }
   }

   private com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer i() {
      return com.cheatbreaker.client.CheatBreakerClient.N().playRegular14px;
   }

   private void a(String var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      com.cheatbreaker.client.gui.render.RenderUtils.a(
         (double)var2, (double)var3, (double)(var2 + var4), (double)(var3 + var5), 4.0, a(var6, var7, var2, var3, var4, var5) ? -14079178 : var8
      );
      this.i().a(var1, var2, var3, var4, var5, var9);
   }

   private static boolean a(int var0, int var1, int var2, int var3, int var4, int var5) {
      return var0 >= var2 && var0 < var2 + var4 && var1 >= var3 && var1 < var3 + var5;
   }

   public static void a(ResourceLocation var0, int var1, int var2, int var3) {
      Minecraft var4 = Minecraft.getMinecraft();
      cn.kazer.client189.render.GlStateBridge.c(1048575);

      try {
         var4.getTextureManager().bindTexture(var0 == null ? new ResourceLocation("textures/entity/steve.png") : var0);
         int var5 = GL11.glGetTexParameteri(3553, 10241);
         int var6 = GL11.glGetTexParameteri(3553, 10240);

         try {
            cn.kazer.client189.render.GlStateBridge.a(3553);
            cn.kazer.client189.render.GlStateBridge.a(3042);
            cn.kazer.client189.render.GlStateBridge.b(770, 771);
            cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTexParameteri(3553, 10241, 9728);
            GL11.glTexParameteri(3553, 10240, 9728);
            Gui.drawScaledCustomSizeModalRect(var1, var2, 8.0F, 8.0F, 8, 8, var3, var3, 64.0F, 64.0F);
            if (var0 != null) {
               Gui.drawScaledCustomSizeModalRect(var1, var2, 40.0F, 8.0F, 8, 8, var3, var3, 64.0F, 64.0F);
            }
         } finally {
            GL11.glTexParameteri(3553, 10241, var5);
            GL11.glTexParameteri(3553, 10240, var6);
         }
      } finally {
         cn.kazer.client189.render.GlStateBridge.a();
      }
   }

   private static void a(ResourceLocation var0, int var1, int var2) {
      com.cheatbreaker.client.gui.render.RenderUtils.a((double)var1, (double)var2, (double)(var1 + 44), (double)(var2 + 44), 44.0, -14539989);
      a(var0, var1 + 11, var2 + 5, 22);
      Minecraft.getMinecraft().getTextureManager().bindTexture(var0 == null ? new ResourceLocation("textures/entity/steve.png") : var0);
      int var3 = GL11.glGetTexParameteri(3553, 10240);

      try {
         cn.kazer.client189.render.GlStateBridge.a(3553);
         cn.kazer.client189.render.GlStateBridge.a(3042);
         cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glTexParameteri(3553, 10240, 9728);
         a(var1, var2, 13, 27, 18, 17, 20, 20, 8, 8);
         a(var1, var2, 5, 27, 8, 14, 44, 20, 4, 8);
         a(var1, var2, 31, 27, 8, 14, 44, 20, 4, 8);
      } finally {
         GL11.glTexParameteri(3553, 10240, var3);
      }
   }

   private static void a(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      cn.kazer.client189.render.ImmediateTessellator var10 = cn.kazer.client189.render.ImmediateTessellator.instance;
      var10.a();

      for (int var11 = 0; var11 < var5; var11++) {
         double var12 = Math.sqrt(Math.max(0.0, 484.0 - Math.pow(var3 + var11 + 0.5 - 22.0, 2.0)));
         double var14 = Math.max((double)var2, 22.0 - var12);
         double var16 = Math.min((double)(var2 + var4), 22.0 + var12);
         if (!(var16 <= var14)) {
            double var18 = (var6 + (var14 - var2) * var8 / var4) / 64.0;
            double var20 = (var6 + (var16 - var2) * var8 / var4) / 64.0;
            double var22 = (var7 + (double)var11 * var9 / var5) / 64.0;
            double var24 = (var7 + (double)(var11 + 1) * var9 / var5) / 64.0;
            var10.a(var0 + var14, var1 + var3 + var11 + 1, 0.0, var18, var24);
            var10.a(var0 + var16, var1 + var3 + var11 + 1, 0.0, var20, var24);
            var10.a(var0 + var16, var1 + var3 + var11, 0.0, var20, var22);
            var10.a(var0 + var14, var1 + var3 + var11, 0.0, var18, var22);
         }
      }

      var10.b();
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      if (var3 == 0) {
         var1 = (int)(var1 / this.b());
         var2 = (int)(var2 / this.b());
         if (!a(var1, var2, 10, 34, 169, this.h() - 34)) {
            if (!this.busy) {
               this.mc.displayGuiScreen(this.parent);
            }
         } else if (!this.busy) {
            if (a(var1, var2, 18, 94, 153, 20)) {
               this.settings = !this.settings;
            } else if (a(var1, var2, 18, this.g(), 153, 20)) {
               if (!this.settings && !this.managing) {
                  this.managing = true;
               } else {
                  this.settings = false;
                  this.managing = false;
               }

               this.scroll = 0;
            } else if (!this.settings) {
               String var4 = null;
               if (a(var1, var2, 142, 128, 29, 17)) {
                  var4 = "/login";
               }

               List var5 = this.e();

               for (int var6 = 0; var4 == null && var6 < this.f() && this.scroll + var6 < var5.size(); var6++) {
                  if (a(var1, var2, 18, 148 + var6 * 22, 153, 20)) {
                     String var7 = ((JsonObject)var5.get(this.scroll + var6)).get("id").getAsString();
                     if (var7.equals(this.mc.getSession().getPlayerID())) {
                        return;
                     }

                     var4 = "/select/" + var7;
                  }
               }

               if (var4 != null) {
                  this.busy = true;
                  this.status = "Complete sign-in in the Microsoft window.";
                  com.cheatbreaker.client.gui.screens.AccountService.a(var4, var1x -> {
                     this.busy = false;
                     this.status = com.cheatbreaker.client.gui.screens.AccountService.a(var1x);
                     this.parent.initGui();
                     this.d();
                  }, var1x -> {
                     this.busy = false;
                     this.status = var1x;
                  });
               }
            }
         }
      }
   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      int var1 = Mouse.getEventDWheel();
      if (var1 != 0) {
         this.scroll = Math.max(0, Math.min(Math.max(0, this.e().size() - this.f()), this.scroll + (var1 < 0 ? 1 : -1)));
      }
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if (var2 == 1) {
         this.mc.displayGuiScreen(this.parent);
      }
   }
}
