package com.cheatbreaker.client.gui.uielement;

import net.minecraft.client.Minecraft;

public abstract class UIElement {
   public float scale;
   public int yOffset = 0;
   public int x;
   protected int y;
   protected int width;
   protected int height;

   public UIElement(float var1) {
      this.scale = var1;
   }

   public void a(int var1, int var2, int var3, int var4) {
      this.x = var1;
      this.y = var2;
      this.width = var3;
      this.height = var4;
   }

   public abstract void a(int var1, int var2, float var3);

   public abstract void a(int var1, int var2, int var3);

   public boolean a(char var1, int var2) {
      return false;
   }

   public void b(int var1, int var2, int var3) {
      this.a();
   }

   public void a() {
   }

   public boolean a(int var1, int var2) {
      boolean var3 = var1 > this.x * this.scale;
      boolean var4 = var1 < (this.x + this.width) * this.scale;
      boolean var5 = var2 > (this.y + this.yOffset) * this.scale;
      boolean var6 = var2 < (this.y + this.height + this.yOffset) * this.scale;
      return var3 && var4 && var5 && var6 && Minecraft.getMinecraft().currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.HudEditorScreen;
   }

   public void a(int var1) {
   }

   public com.cheatbreaker.client.settings.Setting b() {
      return null;
   }

   public int c() {
      return this.height;
   }
}
