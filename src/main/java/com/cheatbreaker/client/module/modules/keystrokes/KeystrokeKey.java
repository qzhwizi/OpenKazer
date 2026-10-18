package com.cheatbreaker.client.module.modules.keystrokes;


import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class KeystrokeKey {
   private final String displayString;
   private int keyCode;
   private final float width;
   private final float height;
   private float fadeTime;
   private boolean pressed;
   private long lastPressed;
   private Color pressedColor;
   private Color unpressedColor;

   public KeystrokeKey(String var1, int var2, float var3, float var4, float var5) {
      this.displayString = var1;
      this.keyCode = var2;
      this.width = var3;
      this.height = var4;
      this.fadeTime = var5;
   }

   public void a(float var1, float var2, int var3, int var4, int var5, int var6) {
      Minecraft var7 = Minecraft.getMinecraft();
      int var8 = this.keyCode != -99 && this.keyCode != -100 ? -1 : (this.keyCode == -99 ? 1 : 0);
      boolean var9 = (var7.currentScreen == null || var7.currentScreen instanceof GuiContainer || var7.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.HudEditorScreen)
         && (var8 != -1 ? Mouse.isButtonDown(var8) : Keyboard.isKeyDown(this.keyCode));
      if (var9 && !this.pressed) {
         this.pressed = true;
         this.lastPressed = System.currentTimeMillis();
         this.pressedColor = new Color(var5, true);
         this.unpressedColor = new Color(var6, true);
      } else if (this.pressed && !var9) {
         this.pressed = false;
         this.lastPressed = System.currentTimeMillis();
         this.pressedColor = new Color(var6, true);
         this.unpressedColor = new Color(var5, true);
      }

      int var10;
      if ((float)(System.currentTimeMillis() - this.lastPressed) < this.fadeTime) {
         float var11 = (float)(System.currentTimeMillis() - this.lastPressed) / this.fadeTime;
         var10 = new Color(
               (int)Math.abs(var11 * this.unpressedColor.getRed() + (1.0F - var11) * this.pressedColor.getRed()),
               (int)Math.abs(var11 * this.unpressedColor.getGreen() + (1.0F - var11) * this.pressedColor.getGreen()),
               (int)Math.abs(var11 * this.unpressedColor.getBlue() + (1.0F - var11) * this.pressedColor.getBlue()),
               (int)Math.abs(var11 * this.unpressedColor.getAlpha() + (1.0F - var11) * this.pressedColor.getAlpha())
            )
            .getRGB();
      } else {
         var10 = var9 ? var6 : var5;
      }

      cn.kazer.client189.render.DrawRect.a(var1, var2, var1 + this.width, var2 + this.height, var10);
      if (this.keyCode == var7.gameSettings.keyBindJump.getKeyCode()) {
         cn.kazer.client189.render.DrawRect.a(
            var1 + this.width / 2.0F - this.width / 8.0F,
            var2 + this.height / 2.0F,
            var1 + this.width / 2.0F + this.width / 8.0F,
            var2 + this.height / 2.0F + 1.0F,
            0xFF000000 | (var9 ? var4 : var3)
         );
      } else {
         int var13 = var7.fontRendererObj.getStringWidth(this.displayString);
         int var12 = this.displayString.length() == 1 ? 1 : (this.displayString.contains("R") ? 2 : 1);
         var7.fontRendererObj
            .drawString(
               this.displayString,
               (int)(var1 + this.width / 2.0F) - var13 / 2 + var12,
               (int)(var2 + this.height / 2.0F - var7.fontRendererObj.FONT_HEIGHT / 2 + 1.0F),
               var9 ? var4 : var3
            );
      }
   }

   public void a(int var1) {
      this.keyCode = var1;
   }

   public String a() {
      return this.displayString;
   }

   public float b() {
      return this.width;
   }

   public float c() {
      return this.height;
   }

   public int d() {
      return this.keyCode;
   }
}
