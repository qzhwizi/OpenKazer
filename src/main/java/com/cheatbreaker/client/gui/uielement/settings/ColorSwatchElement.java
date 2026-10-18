package com.cheatbreaker.client.gui.uielement.settings;

public class ColorSwatchElement extends com.cheatbreaker.client.gui.uielement.UIElement {
   public int color;

   public ColorSwatchElement(float var1, int var2) {
      super(var1);
      this.color = var2;
   }

   @Override
   public void a(int var1, int var2, float var3) {
      cn.kazer.client189.render.DrawRect.a(this.x, this.y, this.x + this.width, this.y + this.height, -1358954496);
      cn.kazer.client189.render.DrawRect.a(this.x + 1, this.y + 1, this.x + this.width - 1, this.y + this.height - 1, this.color | 0xFF000000);
   }

   @Override
   public void a(int var1, int var2, int var3) {
   }
}
