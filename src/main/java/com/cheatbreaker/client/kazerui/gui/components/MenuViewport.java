package com.cheatbreaker.client.kazerui.gui.components;

public final class MenuViewport {
   public float left;
   public float top;
   public float scale;

   public void a(int var1, int var2) {
      this.scale = Math.max(0.1F, Math.min(1.0F, Math.min((var1 - 16.0F) / 492.0F, (var2 - 16.0F) / 303.0F)));
      this.left = (var1 - 492.0F * this.scale) / 2.0F;
      this.top = (var2 - 303.0F * this.scale) / 2.0F;
   }

   public float a(float var1) {
      return (var1 - this.left) / this.scale;
   }

   public float b(float var1) {
      return (var1 - this.top) / this.scale;
   }
}
