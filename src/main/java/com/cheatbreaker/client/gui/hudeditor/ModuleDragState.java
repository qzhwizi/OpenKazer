package com.cheatbreaker.client.gui.hudeditor;

class ModuleDragState {
   protected com.cheatbreaker.client.module.Module module;
   protected float xTranslation;
   protected float yTranslation;
   protected float scale;
   protected float scaledWidth;
   protected float scaledHeight;
   protected int mouseY;
   protected int mouseX;
   protected com.cheatbreaker.client.gui.hudeditor.ResizeCorner unknown;
   protected com.cheatbreaker.client.gui.hudeditor.GuiAnchor anchor;
   final com.cheatbreaker.client.gui.hudeditor.HudEditorScreen parent;

   public ModuleDragState(com.cheatbreaker.client.gui.hudeditor.HudEditorScreen var1, com.cheatbreaker.client.module.Module var2, com.cheatbreaker.client.gui.hudeditor.ResizeCorner var3, int var4, int var5) {
      this.parent = var1;
      this.module = var2;
      this.xTranslation = var2.k();
      this.yTranslation = var2.l();
      this.scaledWidth = var2.width * (Float)var2.scale.d();
      this.scaledHeight = var2.height * (Float)var2.scale.d();
      this.mouseX = var4;
      this.mouseY = var5;
      this.unknown = var3;
      this.scale = (Float)var2.scale.d();
      this.anchor = var2.r();
   }
}
