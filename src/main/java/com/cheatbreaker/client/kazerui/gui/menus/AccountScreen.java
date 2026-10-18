package com.cheatbreaker.client.kazerui.gui.menus;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;

public final class AccountScreen extends GuiScreen {
   private final com.cheatbreaker.client.kazerui.gui.menus.HomeScreen parent;
   private final com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics gfx = new com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics();
   private JsonArray accounts = new JsonArray();
   private boolean busy;
   private String status = "Sign in using your Microsoft account.";
   private float left;
   private float top;
   private float mx;
   private float my;
   private float k;
   private int scroll;
   private boolean autoLogin;

   public AccountScreen(com.cheatbreaker.client.kazerui.gui.menus.HomeScreen var1) {
      this(var1, false);
   }

   public AccountScreen(com.cheatbreaker.client.kazerui.gui.menus.HomeScreen var1, boolean var2) {
      this.parent = var1;
      this.autoLogin = var2;
   }

   public void initGui() {
      this.parent.setWorldAndResolution(this.mc, this.width, this.height);
      com.cheatbreaker.client.gui.screens.AccountService.a("/accounts", var1 -> {
         if (var1.has("accounts")) {
            this.accounts = var1.getAsJsonArray("accounts");
         }
      }, var1 -> this.status = var1);
      if (this.autoLogin) {
         this.autoLogin = false;
         this.a("/login");
      }
   }

   public void updateScreen() {
      HomeScreen.d();
   }

   public void drawScreen(int var1, int var2, float var3) {
      HomeScreen.a(this.parent, this, var3);
      drawRect(0, 0, this.width, this.height, Integer.MIN_VALUE);
      int var4 = new ScaledResolution(this.mc).getScaleFactor();
      this.gfx.dsf = Math.max(1.0F, var4 / 2.0F);
      this.k = this.gfx.dsf / var4;
      this.left = (this.mc.displayWidth / this.gfx.dsf - 440.0F) / 2.0F;
      this.top = (this.mc.displayHeight / this.gfx.dsf - 360.0F) / 2.0F;
      this.mx = Mouse.getX() / this.gfx.dsf;
      this.my = (this.mc.displayHeight - Mouse.getY() - 1) / this.gfx.dsf;
      GlStateManager.pushMatrix();
      GlStateManager.scale(this.k, this.k, 1.0F);
      GlStateManager.disableDepth();
      GlStateManager.disableAlpha();

      try {
         this.gfx.a(this.gfx.a(440.0F, 360.0F, 10.0F), this.left, this.top, -15526886);
         this.gfx.a(this.gfx.b("Accounts", true, 22.0F), this.left + 24.0F, this.top + 20.0F, -1184013);
         this.gfx.a(this.parent.a(), this.left + 24.0F, this.top + 63.0F, 40.0F, 40.0F, 8.0F, 0.125F, 8.0F / this.parent.b(), 0.25F, 16.0F / this.parent.b());
         this.gfx.a(this.parent.a(), this.left + 24.0F, this.top + 63.0F, 40.0F, 40.0F, 8.0F, 0.625F, 8.0F / this.parent.b(), 0.75F, 16.0F / this.parent.b());
         this.gfx.a(this.gfx.b(this.mc.getSession().getUsername(), true, 18.0F), this.left + 78.0F, this.top + 64.0F, -1184013);
         this.gfx.a(this.gfx.b("Current session", false, 14.0F), this.left + 78.0F, this.top + 88.0F, -5131328);
         this.a("Add Microsoft Account", this.left + 24.0F, this.top + 121.0F, 392.0F, !this.busy, -15584958);
         this.gfx.a(this.gfx.b("Saved Accounts", true, 14.0F), this.left + 24.0F, this.top + 170.0F, -5131328);

         for (int var5 = 0; var5 < 3 && var5 + this.scroll < this.accounts.size(); var5++) {
            JsonObject var6 = this.accounts.get(var5 + this.scroll).getAsJsonObject();
            String var7 = var6.get("username").getAsString();
            this.a(var7, this.left + 24.0F, this.top + 192.0F + var5 * 32, 392.0F, !this.busy, -14539988);
         }

         String var11 = this.status.length() > 170 ? this.status.substring(0, 170) : this.status;
         int var12 = Math.min(60, var11.length());
         this.gfx.a(this.gfx.b(var11.substring(0, var12), false, 12.0F), this.left + 24.0F, this.top + 294.0F, -5131328);
         if (var12 < var11.length()) {
            this.gfx.a(this.gfx.b(var11.substring(var12, Math.min(var11.length(), var12 + 60)), false, 12.0F), this.left + 24.0F, this.top + 310.0F, -5131328);
         }

         this.a("Back", this.left + 300.0F, this.top + 326.0F, 116.0F, true, -14539988);
      } finally {
         GlStateManager.enableAlpha();
         GlStateManager.enableDepth();
         GlStateManager.popMatrix();
      }
   }

   private boolean a(float var1, float var2, float var3, float var4) {
      return this.mx >= var1 && this.mx < var1 + var3 && this.my >= var2 && this.my < var2 + var4;
   }

   private void a(String var1, float var2, float var3, float var4, boolean var5, int var6) {
      this.gfx.a(this.gfx.a(var4, 28.0F, 6.0F), var2, var3, var5 && this.a(var2, var3, var4, 28.0F) ? -13684421 : var6);
      this.gfx.a(this.gfx.b(var1, true, 14.0F), var2 + (var4 - this.gfx.a(var1, true, 14.0F)) / 2.0F, var3 + 5.0F, var5 ? -1184013 : -8815737);
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      if (var3 == 0) {
         if (this.a(this.left + 300.0F, this.top + 326.0F, 116.0F, 28.0F)) {
            this.mc.displayGuiScreen(this.parent);
         } else if (!this.busy) {
            if (this.a(this.left + 24.0F, this.top + 121.0F, 392.0F, 28.0F)) {
               this.a("/login");
            } else {
               for (int var4 = 0; var4 < 3 && var4 + this.scroll < this.accounts.size(); var4++) {
                  if (this.a(this.left + 24.0F, this.top + 192.0F + var4 * 32, 392.0F, 28.0F)) {
                     String var5 = this.accounts.get(var4 + this.scroll).getAsJsonObject().get("id").getAsString();
                     if (!var5.equals(this.mc.getSession().getPlayerID())) {
                        this.a("/select/" + var5);
                     }

                     return;
                  }
               }
            }
         }
      }
   }

   private void a(String var1) {
      this.busy = true;
      this.status = "Complete sign-in in the Microsoft window.";
      com.cheatbreaker.client.gui.screens.AccountService.a(var1, var1x -> {
         this.busy = false;
         this.status = com.cheatbreaker.client.gui.screens.AccountService.a(var1x);
         if (this.mc.currentScreen == this) {
            this.mc.displayGuiScreen(this.parent);
         }
      }, var1x -> {
         this.busy = false;
         this.status = var1x;
      });
   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      int var1 = Mouse.getEventDWheel();
      if (var1 != 0) {
         this.scroll = Math.max(0, Math.min(Math.max(0, this.accounts.size() - 3), this.scroll + (var1 < 0 ? 1 : -1)));
      }
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if (var2 == 1) {
         this.mc.displayGuiScreen(this.parent);
      }
   }

   public void onGuiClosed() {
      this.gfx.b();
   }

   public boolean doesGuiPauseGame() {
      return false;
   }
}
