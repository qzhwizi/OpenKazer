package com.cheatbreaker.client.gui.uielement.fileconfig;


import java.io.File;
import java.util.Collections;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ProfileRowElement extends com.cheatbreaker.client.gui.uielement.UIElement {
   private final int IllIIIIIIIlIlIllllIIllIII;
   public final com.cheatbreaker.client.settings.Profile profile;
   private final com.cheatbreaker.client.gui.uielement.ScrollableUIElement parent;
   private int IlllIllIlIIIIlIIlIIllIIIl = 0;
   private final ResourceLocation deleteIcon = new ResourceLocation("client/icons/delete-64.png");
   private final ResourceLocation arrowIcon = new ResourceLocation("client/icons/right.png");
   private final ResourceLocation pencilIcon = new ResourceLocation("client/icons/pencil-64.png");

   public ProfileRowElement(com.cheatbreaker.client.gui.uielement.ScrollableUIElement var1, int var2, com.cheatbreaker.client.settings.Profile var3, float var4) {
      super(var4);
      this.parent = var1;
      this.IllIIIIIIIlIlIllllIIllIII = var2;
      this.profile = var3;
   }

   @Override
   public void a(int var1, int var2, float var3) {
      boolean var7 = var1 > this.x + 12 && this.a(var1, var2);
      byte var8 = 75;
      cn.kazer.client189.render.DrawRect.a(this.x, this.y + this.height - 1, this.x + this.width, this.y + this.height, 791621423);
      if (var7) {
         if (this.IlllIllIlIIIIlIIlIIllIIIl < var8) {
            float var6 = com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.a(790.0F);
            this.IlllIllIlIIIIlIIlIIllIIIl = (int)(this.IlllIllIlIIIIlIIlIIllIIIl + var6);
            if (this.IlllIllIlIIIIlIIlIIllIIIl > var8) {
               this.IlllIllIlIIIIlIIlIIllIIIl = var8;
            }
         }
      } else if (this.IlllIllIlIIIIlIIlIIllIIIl > 0) {
         float var15 = com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.a(790.0F);
         this.IlllIllIlIIIIlIIlIIllIIIl = this.IlllIllIlIIIIlIIlIIllIIIl - var15 < 0.0F ? 0 : (int)(this.IlllIllIlIIIIlIIlIIllIIIl - var15);
      }

      if (this.IlllIllIlIIIIlIIlIIllIIIl > 0) {
         float var16 = (float)this.IlllIllIlIIIIlIIlIIllIIIl / var8 * 100.0F;
         cn.kazer.client189.render.DrawRect.a(
            this.x + 12,
            (int)(this.y + (this.height - this.height * var16 / 100.0F)),
            this.x + this.width - (this.profile.b() ? 0 : 30),
            this.y + this.height,
            this.IllIIIIIIIlIlIllllIIllIII
         );
      }

      boolean var9 = var1 > this.x * this.scale
         && var1 < (this.x + 12) * this.scale
         && var2 >= (this.y + this.yOffset) * this.scale
         && var2 <= (this.y + this.height / 2 + this.yOffset) * this.scale;
      boolean var10 = var1 > this.x * this.scale
         && var1 < (this.x + 12) * this.scale
         && var2 > (this.y + this.height / 2 + this.yOffset) * this.scale
         && var2 < (this.y + this.height + this.yOffset) * this.scale;
      cn.kazer.client189.render.GlStateBridge.a(0.0F, 0.0F, 0.0F, 0.35F);
      float var11 = 2.5F;
      if (this.profile.b()) {
         boolean var5 = false;
         boolean var4 = false;
         com.cheatbreaker.client.gui.uielement.fileconfig.ProfileListElement var12 = (com.cheatbreaker.client.gui.uielement.fileconfig.ProfileListElement)this.parent;
         if (var12.lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this) != 0 && var12.lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this) > 1) {
            var5 = true;
            GL11.glPushMatrix();
            if (var9) {
               cn.kazer.client189.render.GlStateBridge.a(0.0F, 0.0F, 0.0F, 0.65F);
            }

            GL11.glTranslatef(this.x + 6 - var11, this.y + 5.0F, 0.0F);
            GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
            com.cheatbreaker.client.gui.render.RenderUtils.a(this.arrowIcon, var11, -1.0F, 0.0F);
            GL11.glPopMatrix();
            cn.kazer.client189.render.GlStateBridge.a(0.0F, 0.0F, 0.0F, 0.35F);
         }

         if (var12.lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this) != var12.lIIIIlIIllIIlIIlIIIlIIllI.size() - 1) {
            var4 = true;
            GL11.glPushMatrix();
            if (var10) {
               cn.kazer.client189.render.GlStateBridge.a(0.0F, 0.0F, 0.0F, 0.65F);
            }

            GL11.glTranslatef(this.x + 6 + var11, this.y + 7.0F, 0.0F);
            GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            com.cheatbreaker.client.gui.render.RenderUtils.a(this.arrowIcon, var11, 2.0F, 0.0F);
            GL11.glPopMatrix();
         }

         if (!var5 && !var4) {
            com.cheatbreaker.client.gui.render.RenderUtils.a(this.arrowIcon, 2.5F, this.x + 4, this.y + 6.0F);
         }
      } else {
         com.cheatbreaker.client.gui.render.RenderUtils.a(this.arrowIcon, 2.5F, this.x + 4, this.y + 6.0F);
      }

      if (com.cheatbreaker.client.CheatBreakerClient.N().activeProfile == this.profile) {
         com.cheatbreaker.client.CheatBreakerClient.N().playBold18px.a(this.profile.c().toUpperCase(), this.x + 16.0F, (float)(this.y + 4), -818991313);
      } else {
         com.cheatbreaker.client.CheatBreakerClient.N().playRegular16px.a(this.profile.c().toUpperCase(), this.x + 16.0F, this.y + 4.0F, -818991313);
      }

      if (com.cheatbreaker.client.CheatBreakerClient.N().activeProfile == this.profile) {
         com.cheatbreaker.client.CheatBreakerClient.N()
            .playRegular14px
            .a(" (Active)", this.x + 17.0F + com.cheatbreaker.client.CheatBreakerClient.N().playBold18px.a(this.profile.c().toUpperCase()), this.y + 7.0F, 1865363247);
      }

      if (this.profile.b()) {
         boolean var14 = var1 > (this.x + this.width - 30) * this.scale
            && var1 < (this.x + this.width - 13) * this.scale
            && var2 > (this.y + this.yOffset) * this.scale
            && var2 < (this.y + this.height + this.yOffset) * this.scale;
         cn.kazer.client189.render.GlStateBridge.a(var14 ? 0.0F : 0.25F, var14 ? 0.0F : 0.25F, var14 ? 0.5F : 0.25F, 0.65F);
         com.cheatbreaker.client.gui.render.RenderUtils.a(this.pencilIcon, 5.0F, this.x + this.width - 26, this.y + 3.5F);
         boolean var13 = var1 > (this.x + this.width - 17) * this.scale
            && var1 < (this.x + this.width - 2) * this.scale
            && var2 > (this.y + this.yOffset) * this.scale
            && var2 < (this.y + this.height + this.yOffset) * this.scale;
         cn.kazer.client189.render.GlStateBridge.a(var13 ? 0.8F : 0.25F, var13 ? 0.0F : 0.25F, var13 ? 0.0F : 0.25F, 0.65F);
         com.cheatbreaker.client.gui.render.RenderUtils.a(this.deleteIcon, 5.0F, this.x + this.width - 13, this.y + 3.5F);
      }
   }

   @Override
   public void a(int var1, int var2, int var3) {
      boolean var4 = var1 > (this.x + this.width - 17) * this.scale
         && var1 < (this.x + this.width - 2) * this.scale
         && var2 > (this.y + this.yOffset) * this.scale
         && var2 < (this.y + this.height + this.yOffset) * this.scale;
      boolean var5 = var1 > (this.x + this.width - 30) * this.scale
         && var1 < (this.x + this.width - 13) * this.scale
         && var2 > (this.y + this.yOffset) * this.scale
         && var2 < (this.y + this.height + this.yOffset) * this.scale;
      boolean var6 = var1 > this.x * this.scale
         && var1 < (this.x + 12) * this.scale
         && var2 >= (this.y + this.yOffset) * this.scale
         && var2 <= (this.y + this.height / 2 + this.yOffset) * this.scale;
      boolean var7 = var1 > this.x * this.scale
         && var1 < (this.x + 12) * this.scale
         && var2 > (this.y + this.height / 2 + this.yOffset) * this.scale
         && var2 < (this.y + this.height + this.yOffset) * this.scale;
      com.cheatbreaker.client.gui.uielement.fileconfig.ProfileListElement var8 = (com.cheatbreaker.client.gui.uielement.fileconfig.ProfileListElement)this.parent;
      if (!this.profile.b() || !var6 && !var7) {
         if (this.profile.b() && var4) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            if (com.cheatbreaker.client.CheatBreakerClient.N().activeProfile == this.profile) {
               com.cheatbreaker.client.CheatBreakerClient.N().activeProfile = com.cheatbreaker.client.CheatBreakerClient.N().profiles.get(0);
               com.cheatbreaker.client.CheatBreakerClient.N().configManager.a(com.cheatbreaker.client.CheatBreakerClient.N().activeProfile.c());
               com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.keyStrokes.a();
            }

            File var9;
            if (this.profile.b()
               && (com.cheatbreaker.client.settings.ConfigManager.profilesDir.exists() || com.cheatbreaker.client.settings.ConfigManager.profilesDir.mkdirs())
               && (var9 = new File(com.cheatbreaker.client.settings.ConfigManager.profilesDir, this.profile.c() + ".cfg")).exists()
               && var9.delete()) {
               com.cheatbreaker.client.CheatBreakerClient.N().profiles.removeIf(var1x -> var1x == this.profile);
               var8.lIIIIlIIllIIlIIlIIIlIIllI.removeIf(var1x -> var1x == this);
            }
         } else if (this.profile.b() && var5) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            Minecraft.getMinecraft()
               .displayGuiScreen(
                  new com.cheatbreaker.client.gui.hudeditor.ProfileEditScreen(
                     this.profile,
                     com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance,
                     (com.cheatbreaker.client.gui.uielement.fileconfig.ProfileListElement)this.parent,
                     this.IllIIIIIIIlIlIllllIIllIII,
                     this.scale
                  )
               );
         } else if (com.cheatbreaker.client.CheatBreakerClient.N().activeProfile != this.profile) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            com.cheatbreaker.client.CheatBreakerClient.N().configManager.b(com.cheatbreaker.client.CheatBreakerClient.N().activeProfile.c());
            com.cheatbreaker.client.CheatBreakerClient.N().activeProfile = this.profile;
            com.cheatbreaker.client.CheatBreakerClient.N().configManager.a(com.cheatbreaker.client.CheatBreakerClient.N().activeProfile.c());
            com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.keyStrokes.a();
         }
      } else {
         if (var6
            && ((com.cheatbreaker.client.gui.uielement.fileconfig.ProfileListElement)this.parent).lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this) != 0
            && ((com.cheatbreaker.client.gui.uielement.fileconfig.ProfileListElement)this.parent).lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this) > 1) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            this.profile.a(var8.lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this) - 1);
            var8.lIIIIlIIllIIlIIlIIIlIIllI.get(var8.lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this) - 1).profile.a(var8.lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this));
            Collections.swap(var8.lIIIIlIIllIIlIIlIIIlIIllI, var8.lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this), var8.lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this) - 1);
         }

         if (var7 && var8.lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this) != var8.lIIIIlIIllIIlIIlIIIlIIllI.size() - 1) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            this.profile.a(var8.lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this) + 1);
            var8.lIIIIlIIllIIlIIlIIIlIIllI.get(var8.lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this) + 1).profile.a(var8.lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this));
            Collections.swap(var8.lIIIIlIIllIIlIIlIIIlIIllI, var8.lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this), var8.lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this) + 1);
         }
      }
   }
}
