package com.cheatbreaker.client.module.modules.hud;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.boss.BossStatus;
import org.lwjgl.opengl.GL11;

public class BossBarModule extends com.cheatbreaker.client.module.Module {
   private final com.cheatbreaker.client.settings.Setting renderHealth;
   private final com.cheatbreaker.client.settings.Setting renderText;
   private final com.cheatbreaker.client.settings.Setting customBossBar;
   private final com.cheatbreaker.client.settings.Setting minecraftGuiScale;

   public BossBarModule() {
      super("Boss bar");
      this.b(com.cheatbreaker.client.gui.hudeditor.GuiAnchor.MIDDLE_TOP);
      this.b(-1.0F, 0.0F);
      this.renderHealth = new com.cheatbreaker.client.settings.Setting(this, "Render Health").a(true);
      this.renderText = new com.cheatbreaker.client.settings.Setting(this, "Render Text").a(true);
      this.customBossBar = new com.cheatbreaker.client.settings.Setting(this, "Use Custom Boss Bar").a(false);
      this.minecraftGuiScale = new com.cheatbreaker.client.settings.Setting(this, "Use Minecraft GUI Scale").a(true);
      this.a(com.cheatbreaker.client.event.event.RenderHudEvent.class, this::a);
      this.a(com.cheatbreaker.client.event.event.RenderHudEditorEvent.class, this::a);
      this.a("Boss Bar", 1.0F);
      this.c(false);
   }

   public void a(com.cheatbreaker.client.event.event.RenderHudEditorEvent var1) {
      if (this.q()) {
         GL11.glPushMatrix();
         this.a(var1.a());
         if (BossStatus.bossName == null || BossStatus.statusBarTime <= 0) {
            this.minecraft.getTextureManager().bindTexture(Gui.icons);
            FontRenderer var2 = this.minecraft.fontRendererObj;
            short var3 = 182;
            byte var4 = 0;
            float var5 = 1.0F;
            int var6 = (int)(var5 * (var3 + 1));
            byte var7 = 13;
            if ((Boolean)this.renderHealth.d()) {
               com.cheatbreaker.client.gui.render.RenderUtils.a((float)var4, (float)var7, 0.0F, 74.0F, var3, 5);
            }

            if ((Boolean)this.renderHealth.d() && var6 > 0) {
               com.cheatbreaker.client.gui.render.RenderUtils.a((float)var4, (float)var7, 0.0F, 79.0F, var6, 5);
            }

            String var8 = "Wither";
            if ((Boolean)this.renderText.d()) {
               var2.drawStringWithShadow(var8, this.width / 2.0F - var2.getStringWidth(var8) / 2, var7 - 10, 16777215);
            }

            cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
            this.minecraft.getTextureManager().bindTexture(Gui.icons);
            this.c(182.0F, 20.0F);
         }

         GL11.glPopMatrix();
      }
   }

   public void a(com.cheatbreaker.client.event.event.RenderHudEvent var1) {
      if (this.q()) {
         GL11.glPushMatrix();
         this.a(var1.a());
         if (BossStatus.bossName != null || BossStatus.statusBarTime > 0) {
            this.minecraft.getTextureManager().bindTexture(Gui.icons);
            FontRenderer var2 = this.minecraft.fontRendererObj;
            short var3 = 182;
            byte var4 = 0;
            float var5 = 1.0F;
            int var6 = (int)(var5 * (var3 + 1));
            byte var7 = 13;
            if ((Boolean)this.renderHealth.d()) {
               com.cheatbreaker.client.gui.render.RenderUtils.a((float)var4, (float)var7, 0.0F, 74.0F, var3, 5);
            }

            if ((Boolean)this.renderHealth.d() && var6 > 0) {
               com.cheatbreaker.client.gui.render.RenderUtils.a((float)var4, (float)var7, 0.0F, 79.0F, var6, 5);
            }

            String var8 = BossStatus.bossName;
            if ((Boolean)this.renderText.d()) {
               var2.drawStringWithShadow(var8, this.width / 2.0F - var2.getStringWidth(var8) / 2, var7 - 10, 16777215);
            }

            cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
            this.minecraft.getTextureManager().bindTexture(Gui.icons);
            this.c(182.0F, 20.0F);
         }

         GL11.glPopMatrix();
      }
   }
}
