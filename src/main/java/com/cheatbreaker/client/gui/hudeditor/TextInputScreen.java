package com.cheatbreaker.client.gui.hudeditor;

import java.io.IOException;
import java.util.function.Consumer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class TextInputScreen extends GuiScreen {
   private static final int CONTENT_MAX_LENGTH = 256;
   private static final String DEFAULT_LABEL = "Edit: ";
   private final GuiScreen previous;
   private final String label;
   private final String initial;
   private final Consumer<String> onConfirm;
   private final float scaleFactor;
   private final int maxLength;
   private final Runnable onCancel;
   private final Runnable afterClose;
   private String errorMessage = "";
   private GuiTextField textField;

   public TextInputScreen(GuiScreen var1, String var2, String var3, Consumer<String> var4) {
      this(var1, var2, var3, var4, 1.0F);
   }

   public TextInputScreen(GuiScreen var1, String var2, String var3, Consumer<String> var4, float var5) {
      this(var1, var2, var3, var4, var5, 256);
   }

   public TextInputScreen(GuiScreen var1, String var2, String var3, Consumer<String> var4, float var5, int var6) {
      this(var1, var2, var3, var4, var5, var6, null, null);
   }

   public TextInputScreen(GuiScreen var1, String var2, String var3, Consumer<String> var4, float var5, int var6, Runnable var7, Runnable var8) {
      this.previous = var1;
      this.label = var2 == null ? "Edit: " : var2;
      this.initial = var3 == null ? "" : var3;
      this.onConfirm = var4;
      this.scaleFactor = var5;
      this.maxLength = var6;
      this.onCancel = var7;
      this.afterClose = var8;
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      this.textField = new GuiTextField(0, this.mc.fontRendererObj, this.width / 2 - 70, this.height / 2 - 6, 140, 10);
      this.textField.setText(this.initial);
      this.textField.setFocused(true);
      this.textField.setMaxStringLength(this.maxLength);
   }

   public void updateScreen() {
      this.textField.updateCursorCounter();
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
   }

   public void drawScreen(int var1, int var2, float var3) {
      if (this.previous != null) {
         this.previous.drawScreen(var1, var2, var3);
      }

      cn.kazer.client189.render.DrawRect.a(0.0F, 0.0F, this.width, this.height, -1072689136);
      float var4 = this.width / 2.0F - 73.0F;
      float var5 = this.height / 2.0F - 19.0F;
      float var6 = this.width / 2.0F + 73.0F;
      float var7 = this.height / 2.0F + (this.errorMessage.isEmpty() ? 8 : 22);
      cn.kazer.client189.render.DrawRect.a(var4, var5, var6, var7, -11250604);
      cn.kazer.client189.render.DrawRect.a(var4 + 1.0F, var5 + 1.0F, var6 - 1.0F, var7 - 1.0F, -3881788);
      if (!this.errorMessage.isEmpty()) {
         cn.kazer.client189.render.DrawRect.a(var4 + 1.0F, this.height / 2.0F + 8.0F, var6 - 1.0F, var7 - 1.0F, 1711276032);
      }

      GL11.glPushMatrix();
      GL11.glScalef(this.scaleFactor, this.scaleFactor, this.scaleFactor);
      int var8 = (int)(this.width / this.scaleFactor);
      int var9 = (int)(this.height / this.scaleFactor);
      float var10002 = var8 / 2 - 70.0F / this.scaleFactor;
      float var10003 = var9 / 2 - 17.0F / this.scaleFactor;
      com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.a(this.label, var10002, var10003, 1862270976);
      if (!this.errorMessage.isEmpty()) {
         var10002 = var8 / 2 - 72.0F / this.scaleFactor;
         var10003 = var9 / 2 + 8.0F / this.scaleFactor;
         com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.a(this.errorMessage, var10002, var10003, -1358954496);
      }

      GL11.glPopMatrix();
      this.textField.drawTextBox();
   }

   public void mouseClicked(int var1, int var2, int var3) throws IOException {
      super.mouseClicked(var1, var2, var3);
      this.textField.mouseClicked(var1, var2, var3);
   }

   public void keyTyped(char var1, int var2) throws IOException {
      switch (var2) {
         case 1:
            if (this.onCancel != null) {
               this.onCancel.run();
            }

            this.a();
            break;
         case 28:
         case 156:
            String var3 = this.textField.getText();
            if (this.onConfirm != null) {
               this.onConfirm.accept(var3);
            }

            this.a();
            break;
         default:
            this.textField.textboxKeyTyped(var1, var2);
      }
   }

   protected void a() {
      this.mc.displayGuiScreen(this.previous);
      if (this.afterClose != null) {
         this.afterClose.run();
      }
   }
}
