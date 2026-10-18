package com.cheatbreaker.client.gui.uielement.settingseditor;

import java.util.ArrayList;
import java.util.List;

public class ModuleGridPanel extends com.cheatbreaker.client.gui.uielement.ScrollableUIElement {
   private static final int COLUMNS = 3;
   private static final int ROW_HEIGHT = 112;
   private final List<com.cheatbreaker.client.gui.uielement.settingseditor.ModuleCardElement> elements = new ArrayList<>();

   public ModuleGridPanel(float var1, int var2, int var3, int var4, int var5) {
      super(var1, var2, var3, var4, var5);

      for (com.cheatbreaker.client.module.Module var7 : com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.modules) {
         if (var7 != com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.notifications) {
            com.cheatbreaker.client.gui.uielement.settingseditor.ModuleCardElement var8 = new com.cheatbreaker.client.gui.uielement.settingseditor.ModuleCardElement(this, var7, var1);
            this.elements.add(var8);
         }
      }
   }

   @Override
   public boolean a(com.cheatbreaker.client.module.Module var1) {
      return false;
   }

   @Override
   public void b(com.cheatbreaker.client.module.Module var1) {
   }

   @Override
   public void a(int var1, int var2, float var3) {
      super.a(var1, var2, var3);
      com.cheatbreaker.client.gui.render.RenderUtils.a((double)this.x, (double)this.y, (double)(this.x + this.width), (double)(this.y + this.height + 2), 8.0, -657931);
      this.b(var1, var2);
      int var4 = 0;
      int var5 = 0;

      for (com.cheatbreaker.client.gui.uielement.settingseditor.ModuleCardElement var7 : this.elements) {
         var7.yOffset = this.lIIIIllIIlIlIllIIIlIllIlI;
         var7.a(this.x + 4 + var4 * 120, this.y + 4 + var5 * 112, 116, 108);
         var7.a(var1, var2, var3);
         if (++var4 == 3) {
            var5++;
            var4 = 0;
         }
      }

      this.IlllIllIlIIIIlIIlIIllIIIl = b(this.elements.size());
      this.c(var1, var2);
   }

   static int b(int var0) {
      int var1 = (var0 + 3 - 1) / 3;
      return 4 + var1 * 112;
   }

   @Override
   public void a(int var1, int var2, int var3) {
      super.a(var1, var2, var3);

      for (com.cheatbreaker.client.gui.uielement.settingseditor.ModuleCardElement var5 : this.elements) {
         if (var5.a(var1, var2)) {
            var5.a(var1, var2, var3);
         }
      }
   }
}
