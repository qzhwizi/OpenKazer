package com.cheatbreaker.client.gui.screens;

import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.util.ResourceLocation;

public class KazerMainMenuScreen extends com.cheatbreaker.client.gui.screens.PanoramaScreen {
   private static final ResourceLocation KAZER_LOGO = new ResourceLocation("client/kazer_logo.png");
   private GuiButton singleplayer;
   private GuiButton multiplayer;
   private GuiButton options;
   private GuiButton quit;
   private ResourceLocation accountSkin;
   private String accountSkinId = "";

   @Override
   public void initGui() {
      super.initGui();
      com.cheatbreaker.client.gui.screens.AccountService.a();
      String var1 = this.mc.getSession().getPlayerID();
      if (!var1.equals(this.accountSkinId) && this.mc.getSession().getProfile().getId() != null) {
         this.accountSkinId = var1;
         this.accountSkin = null;
         this.mc.getSkinManager().loadProfileTextures(this.mc.getSession().getProfile(), (var2x, var3x, var4) -> {
            if (var2x == Type.SKIN && var1.equals(this.accountSkinId)) {
               this.accountSkin = var3x;
            }
         }, false);
      }

      int var2 = (int)this.f() / 2;
      int var3 = (int)this.g() / 2 - 32;
      this.singleplayer = new GuiButton(1, var2 - 100, var3, 200, 20, "Singleplayer");
      this.multiplayer = new GuiButton(2, var2 - 100, var3 + 24, 200, 20, "Multiplayer");
      this.options = new GuiButton(3, var2 - 100, var3 + 56, 98, 20, "Options...");
      this.quit = new GuiButton(4, var2 + 2, var3 + 56, 98, 20, "Quit Game");
   }

   @Override
   public void a(float var1, float var2) {
      super.a(var1, var2);
      int var3 = (int)this.f() / 2;
      int var4 = (int)this.g() / 2 - 32;
      int var5 = Math.max(36, Math.min(48, (int)this.g() / 6));
      float var6 = Math.max(10.0F, var4 - var5 - 10.0F);
      cn.kazer.client189.render.GlStateBridge.a(3553);
      cn.kazer.client189.render.GlStateBridge.a(3042);
      cn.kazer.client189.render.GlStateBridge.b(2896);
      cn.kazer.client189.render.GlStateBridge.b(2929);
      cn.kazer.client189.render.GlStateBridge.b(770, 771);
      cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
      com.cheatbreaker.client.gui.render.RenderUtils.a(KAZER_LOGO, var3 - var5 / 2.0F, var6, var5, var5);
      cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
      this.b(var1, var2);
      this.a(this.singleplayer, var1, var2);
      this.a(this.multiplayer, var1, var2);
      this.a(this.options, var1, var2);
      this.a(this.quit, var1, var2);
      this.mc.fontRendererObj.drawStringWithShadow("Kazer Client 1.8.9", 4.0F, (int)this.g() - 12, -1);
      String var7 = "Copyright Mojang AB. Do not distribute!";
      this.mc.fontRendererObj.drawStringWithShadow(var7, (int)this.f() - this.mc.fontRendererObj.getStringWidth(var7) - 4, (int)this.g() - 12, -1);
   }

   private void a(GuiButton var1, float var2, float var3) {
      var1.drawButton(this.mc, (int)var2, (int)var3);
   }

   private void b(float var1, float var2) {
      byte var3 = 10;
      byte var4 = 10;
      int var5 = this.i();
      byte var6 = 20;
      boolean var7 = var1 >= var3 && var1 < var3 + var5 && var2 >= var4 && var2 < var4 + var6;
      com.cheatbreaker.client.gui.render.RenderUtils.a((double)var3, (double)var4, (double)(var3 + var5), (double)(var4 + var6), 4.0, var7 ? -14671573 : -15658472);
      cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
      AccountManagerScreen.a(this.accountSkin, var3 + 6, var4 + 6, 8);
      String var8 = this.mc.getSession() == null ? "OFFLINE" : this.mc.getSession().getUsername();
      com.cheatbreaker.client.CheatBreakerClient.N().playRegular18px.b(var8, (double)(var3 + 17), (double)(var4 + 6), -4605242);
      com.cheatbreaker.client.gui.render.IconRenderer.a(com.cheatbreaker.client.gui.render.IconRenderer.a.DOWN, var3 + var5 - 13, var4 + 8, 6.0F, -4605242);
   }

   int i() {
      return Math.max(63, com.cheatbreaker.client.CheatBreakerClient.N().playRegular18px.b(this.mc.getSession().getUsername()) + 33);
   }

   ResourceLocation j() {
      return this.accountSkin;
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if (var2 != 1) {
         super.keyTyped(var1, var2);
      }
   }

   @Override
   public void a(float var1, float var2, int var3) {
      if (var3 == 0) {
         int var4 = (int)var1;
         int var5 = (int)var2;
         if (var4 >= 10 && var4 < 10 + this.i() && var5 >= 10 && var5 < 30) {
            this.mc.displayGuiScreen(new com.cheatbreaker.client.gui.screens.AccountManagerScreen(this));
         } else if (this.singleplayer.mousePressed(this.mc, var4, var5)) {
            this.singleplayer.playPressSound(this.mc.getSoundHandler());
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
         } else if (this.multiplayer.mousePressed(this.mc, var4, var5)) {
            this.multiplayer.playPressSound(this.mc.getSoundHandler());
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
         } else if (this.options.mousePressed(this.mc, var4, var5)) {
            this.options.playPressSound(this.mc.getSoundHandler());
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
         } else if (this.quit.mousePressed(this.mc, var4, var5)) {
            this.quit.playPressSound(this.mc.getSoundHandler());
            this.mc.shutdown();
         }
      }
   }

   @Override
   public void drawScreen(int var1, int var2, float var3) {
      super.drawScreen(var1, var2, var3);
   }
}
