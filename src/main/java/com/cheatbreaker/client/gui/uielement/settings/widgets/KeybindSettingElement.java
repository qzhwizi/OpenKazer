package com.cheatbreaker.client.gui.uielement.settings.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

public class KeybindSettingElement extends com.cheatbreaker.client.gui.uielement.UIElement {
   private com.cheatbreaker.client.settings.Setting lIIIIlIIllIIlIIlIIIlIIllI;
   private com.cheatbreaker.client.gui.uielement.settingseditor.SettingsButtonElement IllIIIIIIIlIlIllllIIllIII;
   private boolean lIIIIllIIlIlIllIIIlIllIlI = false;

   public KeybindSettingElement(com.cheatbreaker.client.settings.Setting var1, float var2) {
      super(var2);
      this.lIIIIlIIllIIlIIlIIIlIIllI = var1;
      this.height = 12;
      this.IllIIIIIIIlIlIllllIIllIII = new com.cheatbreaker.client.gui.uielement.settingseditor.SettingsButtonElement(
         com.cheatbreaker.client.CheatBreakerClient.N().playBold18px, null, Keyboard.getKeyName((Integer)var1.d()), this.x + this.width - 100, this.y, 96, 18, -9442858, var2
      );
   }

   @Override
   public void a(int var1, int var2, float var3) {
      boolean var4 = var1 > (this.x + this.width - 48) * this.scale
         && var1 < (this.x + this.width - 10) * this.scale
         && var2 > (this.y + this.yOffset) * this.scale
         && var2 < (this.y + 10 + this.yOffset) * this.scale;
      boolean var5 = var1 > (this.x + this.width - 92) * this.scale
         && var1 < (this.x + this.width - 48) * this.scale
         && var2 > (this.y + this.yOffset) * this.scale
         && var2 < (this.y + 10 + this.yOffset) * this.scale;
      com.cheatbreaker.client.CheatBreakerClient.N()
         .ubuntuMedium16px
         .a(this.lIIIIlIIllIIlIIlIIIlIIllI.f().toUpperCase(), (float)(this.x + 10), (float)(this.y + 4), !var5 && !var4 ? -1895825408 : -1090519040);
      if (this.lIIIIllIIlIlIllIIIlIllIlI && Keyboard.getEventKeyState()) {
         Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
         this.lIIIIlIIllIIlIIlIIIlIIllI.a(Keyboard.getEventKey());
         this.IllIIIIIIIlIlIllllIIllIII.displayString = Keyboard.getKeyName((Integer)this.lIIIIlIIllIIlIIlIIIlIIllI.d());
         this.lIIIIllIIlIlIllIIIlIllIlI = false;
      }

      this.IllIIIIIIIlIlIllllIIllIII.yOffset = this.yOffset;
      this.IllIIIIIIIlIlIllllIIllIII.a(this.x + this.width - 100, this.y, 96, 18);
      this.IllIIIIIIIlIlIllllIIllIII.a(var1, var2, var3);
   }

   @Override
   public void a(int var1, int var2, int var3) {
      if (this.IllIIIIIIIlIlIllllIIllIII.a(var1, var2)) {
         Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
         this.lIIIIllIIlIlIllIIIlIllIlI = true;
         this.IllIIIIIIIlIlIllllIIllIII.displayString = "<PRESS ANY KEY>";
      }
   }
}
