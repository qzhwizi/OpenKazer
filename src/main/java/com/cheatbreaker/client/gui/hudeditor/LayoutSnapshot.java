package com.cheatbreaker.client.gui.hudeditor;

import java.util.ArrayList;
import java.util.List;

class LayoutSnapshot {
   protected List<com.cheatbreaker.client.module.Module> modules;
   List<com.cheatbreaker.client.gui.hudeditor.GuiAnchor> anchors;
   List<Float> xTranslations;
   List<Float> yTranslations;
   List<Float> scales;
   final com.cheatbreaker.client.gui.hudeditor.HudEditorScreen parent;

   LayoutSnapshot(com.cheatbreaker.client.gui.hudeditor.HudEditorScreen var1, List<com.cheatbreaker.client.gui.hudeditor.ModulePosition> var2) {
      this.parent = var1;
      ArrayList var3 = new ArrayList();
      ArrayList var4 = new ArrayList();
      ArrayList var5 = new ArrayList();
      ArrayList var6 = new ArrayList();
      ArrayList var7 = new ArrayList();

      for (com.cheatbreaker.client.gui.hudeditor.ModulePosition var9 : var2) {
         if (var9.module.r() != null) {
            var3.add(var9.module);
            var4.add(var9.module.r());
            var5.add(var9.module.k());
            var6.add(var9.module.l());
            var7.add((Float)var9.module.scale.d());
         }
      }

      this.modules = var3;
      this.anchors = var4;
      this.xTranslations = var5;
      this.yTranslations = var6;
      this.scales = var7;
   }
}
