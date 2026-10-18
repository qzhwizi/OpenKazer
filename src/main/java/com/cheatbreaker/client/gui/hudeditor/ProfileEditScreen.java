package com.cheatbreaker.client.gui.hudeditor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class ProfileEditScreen extends GuiScreen {
   private final GuiScreen guiScreen;
   private final float IlllIIIlIlllIllIlIIlllIlI;
   private final int IIIIllIlIIIllIlllIlllllIl;
   private final com.cheatbreaker.client.gui.uielement.fileconfig.ProfileListElement parent;
   private GuiTextField lIIIIIIIIIlIllIIllIlIIlIl = null;
   private String IlIlIIIlllIIIlIlllIlIllIl = "";
   private boolean IIIllIllIlIlllllllIlIlIII = false;
   private com.cheatbreaker.client.settings.Profile profile;

   public ProfileEditScreen(com.cheatbreaker.client.settings.Profile var1, GuiScreen var2, com.cheatbreaker.client.gui.uielement.fileconfig.ProfileListElement var3, int var4, float var5) {
      this(var2, var3, var4, var5);
      this.profile = var1;
   }

   public ProfileEditScreen(GuiScreen var1, com.cheatbreaker.client.gui.uielement.fileconfig.ProfileListElement var2, int var3, float var4) {
      this.guiScreen = var1;
      this.IlllIIIlIlllIllIlIIlllIlI = var4;
      this.parent = var2;
      this.IIIIllIlIIIllIlllIlllllIl = var3;
      this.IIIllIllIlIlllllllIlIlIII = true;
   }

   public void updateScreen() {
      this.lIIIIIIIIIlIllIIllIlIIlIl.updateCursorCounter();
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      if (!this.IIIllIllIlIlllllllIlIlIII) {
         this.mc.displayGuiScreen(this.guiScreen);
         ((com.cheatbreaker.client.gui.hudeditor.HudEditorScreen)this.guiScreen).currentScrollableElement = ((com.cheatbreaker.client.gui.hudeditor.HudEditorScreen)this.guiScreen).profilesElement;
      } else {
         this.IIIllIllIlIlllllllIlIlIII = false;
         this.lIIIIIIIIIlIllIIllIlIIlIl = new GuiTextField(0, this.mc.fontRendererObj, this.width / 2 - 70, this.height / 2 - 6, 140, 10);
         if (this.profile != null) {
            this.lIIIIIIIIIlIllIIllIlIIlIl.setText(this.profile.c());
         }

         this.lIIIIIIIIIlIllIIllIlIIlIl.setFocused(true);
      }
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.guiScreen.drawScreen(var1, var2, var3);
      cn.kazer.client189.render.DrawRect.a(0.0F, 0.0F, this.width, this.height, -1072689136);
      float var4 = this.width / 2.0F - 73.0F;
      float var5 = this.height / 2.0F - 19.0F;
      float var6 = this.width / 2.0F + 73.0F;
      float var7 = this.height / 2.0F + (this.IlIlIIIlllIIIlIlllIlIllIl.isEmpty() ? 8 : 22);
      cn.kazer.client189.render.DrawRect.a(var4, var5, var6, var7, -11250604);
      cn.kazer.client189.render.DrawRect.a(var4 + 1.0F, var5 + 1.0F, var6 - 1.0F, var7 - 1.0F, -3881788);
      if (!this.IlIlIIIlllIIIlIlllIlIllIl.isEmpty()) {
         cn.kazer.client189.render.DrawRect.a(var4 + 1.0F, this.height / 2.0F + 8.0F, var6 - 1.0F, var7 - 1.0F, 1711276032);
      }

      GL11.glPushMatrix();
      GL11.glScalef(this.IlllIIIlIlllIllIlIIlllIlI, this.IlllIIIlIlllIllIlIIlllIlI, this.IlllIIIlIlllIllIlIIlllIlI);
      int var8 = (int)(this.width / this.IlllIIIlIlllIllIlIIlllIlI);
      int var9 = (int)(this.height / this.IlllIIIlIlllIllIlIIlllIlI);
      float var10002 = var8 / 2 - 70.0F / this.IlllIIIlIlllIllIlIIlllIlI;
      float var10003 = var9 / 2 - 17.0F / this.IlllIIIlIlllIllIlIIlllIlI;
      com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.a("Profile Name: ", var10002, var10003, 1862270976);
      var10002 = var8 / 2 - 72.0F / this.IlllIIIlIlllIllIlIIlllIlI;
      var10003 = var9 / 2 + 8.0F / this.IlllIIIlIlllIllIlIIlllIlI;
      com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.a(this.IlIlIIIlllIIIlIlllIlIllIl, var10002, var10003, -1358954496);
      GL11.glPopMatrix();
      this.lIIIIIIIIIlIllIIllIlIIlIl.drawTextBox();
   }

   public void mouseClicked(int var1, int var2, int var3) throws IOException {
      super.mouseClicked(var1, var2, var3);
      this.lIIIIIIIIIlIllIIllIlIIlIl.mouseClicked(var1, var2, var3);
   }

   public void mouseReleased(int var1, int var2, int var3) {
      super.mouseReleased(var1, var2, var3);
   }

   public void keyTyped(char var1, int var2) throws IOException {
      switch (var2) {
         case 1:
            this.mc.displayGuiScreen(this.guiScreen);
            ((com.cheatbreaker.client.gui.hudeditor.HudEditorScreen)this.guiScreen).currentScrollableElement = ((com.cheatbreaker.client.gui.hudeditor.HudEditorScreen)this.guiScreen).profilesElement;
            break;
         case 28:
         case 156:
            String var3 = this.lIIIIIIIIIlIllIIllIlIIlIl.getText().trim();
            if (var3.length() < 3) {
               this.IlIlIIIlllIIIlIlllIlIllIl = EnumChatFormatting.RED + "Name must be at least 3 characters long.";
            } else if (var3.equalsIgnoreCase("default")) {
               this.IlIlIIIlllIIIlIlllIlIllIl = EnumChatFormatting.RED + "That name is already in use.";
            } else if (!com.cheatbreaker.client.settings.FilenameValidator.a(var3)) {
               this.IlIlIIIlllIIIlIlllIlIllIl = EnumChatFormatting.RED + "Illegal characters in name.";
            } else {
               for (com.cheatbreaker.client.settings.Profile var5 : com.cheatbreaker.client.CheatBreakerClient.N().profiles) {
                  if (var5 != this.profile && var5.c().equalsIgnoreCase(var3)) {
                     this.IlIlIIIlllIIIlIlllIlIllIl = EnumChatFormatting.RED + "That name is already in use.";
                     return;
                  }
               }

               if (this.profile != null && this.profile.b()) {
                  File var9 = new File(com.cheatbreaker.client.settings.ConfigManager.profilesDir, this.profile.c() + ".cfg");
                  File var10 = new File(com.cheatbreaker.client.settings.ConfigManager.profilesDir, var3 + ".cfg");

                  try {
                     if (!var9.exists()) {
                        com.cheatbreaker.client.CheatBreakerClient.N().configManager.b(this.profile.c());
                     }

                     if (!var9.exists()) {
                        this.IlIlIIIlllIIIlIlllIlIllIl = EnumChatFormatting.RED + "Could not save profile.";
                     } else {
                        Files.move(var9.toPath(), var10.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        this.profile.a(var3);
                        com.cheatbreaker.client.CheatBreakerClient.N().configManager.a();
                        this.mc.displayGuiScreen(this.guiScreen);
                        ((com.cheatbreaker.client.gui.hudeditor.HudEditorScreen)this.guiScreen).currentScrollableElement = ((com.cheatbreaker.client.gui.hudeditor.HudEditorScreen)this.guiScreen).profilesElement;
                     }
                  } catch (Exception var7) {
                     this.IlIlIIIlllIIIlIlllIlIllIl = EnumChatFormatting.RED + "Could not save profile.";
                     var7.printStackTrace();
                  }
               } else {
                  com.cheatbreaker.client.CheatBreakerClient.N().configManager.b(com.cheatbreaker.client.CheatBreakerClient.N().activeProfile.c());
                  com.cheatbreaker.client.settings.Profile var8 = new com.cheatbreaker.client.settings.Profile(var3, true);
                  com.cheatbreaker.client.CheatBreakerClient.N().profiles.add(var8);
                  com.cheatbreaker.client.CheatBreakerClient.N().activeProfile = var8;
                  this.parent
                     .lIIIIlIIllIIlIIlIIIlIIllI
                     .add(new com.cheatbreaker.client.gui.uielement.fileconfig.ProfileRowElement(this.parent, this.IIIIllIlIIIllIlllIlllllIl, var8, this.IlllIIIlIlllIllIlIIlllIlI));
                  com.cheatbreaker.client.CheatBreakerClient.N().configManager.b(com.cheatbreaker.client.CheatBreakerClient.N().activeProfile.c());
                  this.mc.displayGuiScreen(this.guiScreen);
                  ((com.cheatbreaker.client.gui.hudeditor.HudEditorScreen)this.guiScreen).currentScrollableElement = ((com.cheatbreaker.client.gui.hudeditor.HudEditorScreen)this.guiScreen).profilesElement;
               }
            }
            break;
         default:
            this.lIIIIIIIIIlIllIIllIlIIlIl.textboxKeyTyped(var1, var2);
      }
   }
}
