package com.cheatbreaker.client.kazerui.module.modules.hud;

import net.minecraft.client.Minecraft;

public final class FpsHudModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting reverseOrder = this.a("reverseOrder", false);
   private int lastFps = Integer.MIN_VALUE;
   private boolean lastReverse;
   private String cachedText;

   public FpsHudModule() {
      super("FPS", false);
      this.a(
         new com.cheatbreaker.client.kazerui.module.modulerender.RichTextHudBase(
            this, 0.0F, 0.0F, com.cheatbreaker.client.kazerui.hud.HudAnchor.TOP_LEFT, com.cheatbreaker.client.kazerui.module.modulerender.RichTextHudBase.a(10, 18, 22, 50, 56, 62)
         ) {
            @Override
            protected String c(boolean var1) {
               int var2 = Minecraft.getDebugFPS();
               boolean var3 = FpsHudModule.this.reverseOrder.b();
               if (FpsHudModule.this.cachedText == null || var2 != FpsHudModule.this.lastFps || var3 != FpsHudModule.this.lastReverse) {
                  FpsHudModule.this.cachedText = FpsHudModule.this.a(var3 ? "reverse" : "fps", new Object[]{var2});
                  FpsHudModule.this.lastFps = var2;
                  FpsHudModule.this.lastReverse = var3;
               }

               return FpsHudModule.this.cachedText;
            }
         }
      );
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(this.reverseOrder);
   }
}
