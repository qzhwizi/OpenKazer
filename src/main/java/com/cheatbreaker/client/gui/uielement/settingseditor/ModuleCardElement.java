package com.cheatbreaker.client.gui.uielement.settingseditor;

import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ModuleCardElement extends com.cheatbreaker.client.gui.uielement.UIElement {
   private final com.cheatbreaker.client.module.Module module;
   private final com.cheatbreaker.client.gui.uielement.settingseditor.SettingsButtonElement optionsButton;
   private final com.cheatbreaker.client.gui.uielement.settingseditor.SettingsButtonElement toggleOrHideFromHud;
   private final com.cheatbreaker.client.gui.uielement.settingseditor.SettingsButtonElement toggle;
   private final com.cheatbreaker.client.gui.uielement.ScrollableUIElement IlIlllIIIIllIllllIllIIlIl;

   public ModuleCardElement(com.cheatbreaker.client.gui.uielement.ScrollableUIElement var1, com.cheatbreaker.client.module.Module var2, float var3) {
      super(var3);
      this.module = var2;
      this.IlIlllIIIIllIllllIllIIlIl = var1;
      com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer var4 = com.cheatbreaker.client.CheatBreakerClient.N().playBold18px;
      com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer var5 = com.cheatbreaker.client.CheatBreakerClient.N().playRegular14px;
      this.optionsButton = new com.cheatbreaker.client.gui.uielement.settingseditor.SettingsButtonElement(
         var4, null, "Options", this.x + 4, this.y + this.height - 20, this.x + this.width - 4, this.y + this.height - 6, -12418828, var3
      );
      this.toggleOrHideFromHud = new com.cheatbreaker.client.gui.uielement.settingseditor.SettingsButtonElement(
         var5,
         null,
         this.f(),
         this.x + 4,
         this.y + this.height - 38,
         this.e() ? this.x + this.width / 2 - 2 : this.x + this.width - 4,
         this.y + this.height - 24,
         this.g(),
         var3
      );
      this.toggleOrHideFromHud.a(var2 != com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.notifications);
      this.toggle = new com.cheatbreaker.client.gui.uielement.settingseditor.SettingsButtonElement(
         var5,
         null,
         var2.g() ? "Disable" : "Enable",
         this.x + this.width / 2 + 2,
         this.y + this.height - 38,
         this.x + this.width - 4,
         this.y + this.height - 24,
         var2.g() ? -5756117 : -13916106,
         var3
      );
   }

   @Override
   public void a(int var1, int var2, float var3) {
      if (this.d()) {
         cn.kazer.client189.render.DrawRect.a(this.x, this.y, this.x + this.width, this.y + this.height, -13916106);
      } else {
         cn.kazer.client189.render.DrawRect.a(this.x, this.y, this.x + this.width, this.y + this.height, -1347374928);
      }

      com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer var6 = com.cheatbreaker.client.CheatBreakerClient.N().playBold18px;
      GL11.glPushMatrix();
      byte var7 = 0;
      byte var8 = 0;
      if (this.module == com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.armourStatus) {
         var7 = -10;
         String var5 = "329/329";
         float var4 = Minecraft.getMinecraft().fontRendererObj.getStringWidth(var5);
         String var10001 = var5;
         float var10002 = (int)(this.x + 1 + this.width / 2 - var4 / 2.0F);
         Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(var10001, var10002, this.y + this.height / 2 - 18, -1);
      } else if (this.module == com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.potionStatus) {
         var8 = -30;
         float var24 = this.x + 8 + this.width / 2 - 20;
         Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("Speed II", var24, this.y + this.height / 2 - 36, -1);
         var24 = this.x + 8 + this.width / 2 - 20;
         Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("0:42", var24, this.y + this.height / 2 - 26, -1);
      } else if (this.module == com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.scoreboard) {
         cn.kazer.client189.render.DrawRect.a(this.x + 20, this.y + this.height / 2.0F - 44.0F, this.x + this.width - 20, this.y + this.height / 2 - 6, 1862270976);
         int var26 = this.x + this.width / 2;
         Minecraft.getMinecraft().fontRendererObj.drawString("Score", var26, this.y + this.height / 2 - 40, -1);
         float var27 = this.x + 24;
         Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("Steve", var27, this.y + this.height / 2 - 28, -1);
         float var28 = this.x + 24;
         Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("Alex", var28, this.y + this.height / 2 - 18, -1);
         Minecraft.getMinecraft().fontRendererObj.drawString(EnumChatFormatting.RED + "0", this.x + this.width - 26, this.y + this.height / 2 - 18, -1);
         Minecraft.getMinecraft().fontRendererObj.drawString(EnumChatFormatting.RED + "1", this.x + this.width - 26, this.y + this.height / 2 - 28, -1);
      }

      if (this.module == com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.cooldowns) {
         com.cheatbreaker.client.module.modules.render.CooldownEntry var16 = new com.cheatbreaker.client.module.modules.render.CooldownEntry("EnderPearl", 368, 9000L);
         com.cheatbreaker.client.module.modules.render.CooldownEntry var10000 = var16;
         float var29 = this.x + this.width / 2 - 18;
         var10000.a(com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.cooldowns.colorTheme, var29, this.y + this.height / 2 - 26 - 18, -1);
      } else if ((this.module.t() == null || this.module.t() == com.cheatbreaker.client.module.Module.aInner.LABEL)
         && this.module != com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.scoreboard) {
         String var17 = "";
         float var15;
         if (this.module.t() == null) {
            var15 = 2.0F;

            for (String var12 : this.module.h().split(" ")) {
               String var13 = var12.substring(0, 1);
               var17 = var17 + (Objects.equals(var17, "") ? var13 : var13.toLowerCase());
            }
         } else {
            var15 = this.module.y();
            var17 = this.module.x();
         }

         GL11.glScalef(var15, var15, var15);
         float var19 = Minecraft.getMinecraft().fontRendererObj.getStringWidth(var17) * var15;
         if (this.module.t() == null) {
            String var22 = var17;
            int var30 = (int)((this.x + 1 + this.width / 2 - var19 / 2.0F) / var15);
            Minecraft.getMinecraft().fontRendererObj.drawString(var22, var30, (int)((this.y + this.height / 2 - 32) / var15), -13750738);
         } else {
            String var23 = var17;
            float var31 = (int)((this.x + 1 + this.width / 2 - var19 / 2.0F) / var15);
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(var23, var31, (int)((this.y + this.height / 2 - 32) / var15), -1);
         }
      } else if (this.module.t() == com.cheatbreaker.client.module.Module.aInner.ICON) {
         float var9 = this.module.v();
         float var14 = this.module.w();
         cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
         com.cheatbreaker.client.gui.render.RenderUtils.a(
            this.module.u(), this.x + this.width / 2 - var9 / 2.0F + var8, this.y + var7 + this.height / 2 - 26 - var14 / 2.0F, var9, var14
         );
      }

      GL11.glPopMatrix();
      float var20 = this.y + this.height / 2.0F;
      var6.b(this.module.h(), this.x + this.width / 2 - 0.5F, var20 + 1.0F, 1593835520);
      var6.b(this.module.h(), this.x + this.width / 2 - 1.5F, var20, -1);
      this.toggle.displayString = this.module.g() ? "Disable" : "Enable";
      this.toggle.yOffset = this.yOffset;
      int var21 = this.toggle.lIIIIlIIllIIlIIlIIIlIIllI = this.module.g() ? -5756117 : -13916106;
      this.toggleOrHideFromHud.displayString = this.f();
      this.toggleOrHideFromHud.lIIIIlIIllIIlIIlIIIlIIllI = this.g();
      this.optionsButton.a(this.x + 4, this.y + this.height - 20, this.width - 8, 16);
      this.optionsButton.yOffset = this.yOffset;
      this.optionsButton.a(var1, var2, var3);
      this.toggleOrHideFromHud
         .a(this.x + 4, this.y + this.height - 38, this.e() ? this.width / 2 + 2 : this.width - 8, this.y + this.height - 24 - (this.y + this.height - 38));
      this.toggleOrHideFromHud.yOffset = this.yOffset;
      this.toggleOrHideFromHud.a(var1, var2, var3);
      if (this.e()) {
         this.toggle.a(this.x + this.width / 2 + 8, this.y + this.height - 38, this.width / 2 - 12, this.y + this.height - 24 - (this.y + this.height - 38));
         this.toggle.a(var1, var2, var3);
      }
   }

   @Override
   public void a(int var1, int var2, int var3) {
      if (this.optionsButton.a(var1, var2)) {
         Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
         ((com.cheatbreaker.client.gui.uielement.settingseditor.ModuleSettingsPanel)com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance.IIIIllIIllIIIIllIllIIIlIl).llIlIIIlIIIIlIlllIlIIIIll = false;
         ((com.cheatbreaker.client.gui.uielement.settingseditor.ModuleSettingsPanel)com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance.IIIIllIIllIIIIllIllIIIlIl).scrollable = this.IlIlllIIIIllIllllIllIIlIl;
         ((com.cheatbreaker.client.gui.uielement.settingseditor.ModuleSettingsPanel)com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance.IIIIllIIllIIIIllIllIIIlIl).module = this.module;
         com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance.currentScrollableElement = com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance.IIIIllIIllIIIIllIllIIIlIl;
      } else if (this.e() && this.toggle.a(var1, var2)) {
         Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
         this.module.a(!this.module.g());
         this.toggle.displayString = this.module.g() ? "Disable" : "Enable";
         int var4 = this.toggle.lIIIIlIIllIIlIIlIIIlIIllI = this.module.g() ? -5756117 : -13916106;
         if (this.module.g()) {
            this.h();
            this.module.a(true);
         }
      } else if (this.toggleOrHideFromHud.IlllIllIlIIIIlIIlIIllIIIl && this.toggleOrHideFromHud.a(var1, var2)) {
         Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
         if (this.module.r() == null) {
            this.module.a(!this.module.g());
            if (this.module.g()) {
               this.h();
            }
         } else {
            switch (a(this.module.g(), this.module.q())) {
               case PLACE:
                  this.module.f(true);
                  this.h();
                  Minecraft.getMinecraft().displayGuiScreen(new com.cheatbreaker.client.gui.hudeditor.ModulePlacementScreen(com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance, this.module));
                  break;
               case HIDE:
                  this.module.f(false);
                  if (com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance != null) {
                     com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance.b(this.module);
                  }
                  break;
               case RESTORE:
                  this.module.f(true);
                  if (!this.module.g()) {
                     this.module.a(true);
                  }
            }
         }

         this.toggleOrHideFromHud.displayString = this.f();
         this.toggleOrHideFromHud.lIIIIlIIllIIlIIlIIIlIIllI = this.g();
      }
   }

   static com.cheatbreaker.client.gui.uielement.settingseditor.ModuleCardElement.a a(boolean var0, boolean var1) {
      if (!var1) {
         return com.cheatbreaker.client.gui.uielement.settingseditor.ModuleCardElement.a.RESTORE;
      } else {
         return var0 ? com.cheatbreaker.client.gui.uielement.settingseditor.ModuleCardElement.a.HIDE : com.cheatbreaker.client.gui.uielement.settingseditor.ModuleCardElement.a.PLACE;
      }
   }

   static boolean a(boolean var0, boolean var1, boolean var2) {
      return var0 ? var1 && var2 : var1;
   }

   private boolean d() {
      return a(this.module.r() != null, this.module.g(), this.module.q());
   }

   private boolean e() {
      return !this.module.isEditable && this.module.r() != null;
   }

   private String f() {
      return a(this.module.r() != null, this.module.g(), this.module.q(), this.module instanceof com.cheatbreaker.client.module.ModuleGroup);
   }

   static String a(boolean var0, boolean var1, boolean var2, boolean var3) {
      if (!var0) {
         return var1 ? "Disable" : "Enable";
      } else {
         boolean var4 = a(var0, var1, var2);
         if (var4) {
            return "Hide from HUD";
         } else {
            return var3 ? "Show in HUD" : "Add to HUD";
         }
      }
   }

   private int g() {
      return this.d() ? -5756117 : -13916106;
   }

   private void h() {
      if (this.module != com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.llIIlllIIIIlllIllIlIlllIl) {
         for (com.cheatbreaker.client.settings.Setting var2 : this.module.m()) {
            if (var2.l() == com.cheatbreaker.client.settings.Setting.SettingType.INTEGER
               && var2.f().toLowerCase().contains("color")
               && !var2.f().toLowerCase().contains("background")
               && !var2.f().toLowerCase().contains("pressed")) {
               Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
               var2.a(com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.defaultColor.d());
            }
         }
      }
   }

   static enum a {
      PLACE,
      HIDE,
      RESTORE;
   }
}
