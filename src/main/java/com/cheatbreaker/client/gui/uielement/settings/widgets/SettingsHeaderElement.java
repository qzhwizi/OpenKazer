package com.cheatbreaker.client.gui.uielement.settings.widgets;

import net.minecraft.util.ResourceLocation;

public class SettingsHeaderElement extends com.cheatbreaker.client.gui.uielement.UIElement {
   private final int lIIIIlIIllIIlIIlIIIlIIllI;
   private final com.cheatbreaker.client.gui.uielement.ScrollableUIElement IllIIIIIIIlIlIllllIIllIII;
   private int lIIIIllIIlIlIllIIIlIllIlI = 0;
   private ResourceLocation IlllIllIlIIIIlIIlIIllIIIl = new ResourceLocation("client/icons/right.png");

   public SettingsHeaderElement(com.cheatbreaker.client.gui.uielement.ScrollableUIElement var1, int var2, float var3) {
      super(var3);
      this.IllIIIIIIIlIlIllllIIllIII = var1;
      this.lIIIIlIIllIIlIIlIIIlIIllI = var2;
   }

   @Override
   public void a(int var1, int var2, float var3) {
      boolean var4 = this.a(var1, var2);
      byte var5 = 75;
      cn.kazer.client189.render.DrawRect.a(this.x, this.y + this.height - 1, this.x + this.width, this.y + this.height, 791621423);
      float var6 = com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.a(790.0F);
      if (var4) {
         if (this.lIIIIllIIlIlIllIIIlIllIlI < var5) {
            this.lIIIIllIIlIlIllIIIlIllIlI = (int)(this.lIIIIllIIlIlIllIIIlIllIlI + var6);
            if (this.lIIIIllIIlIlIllIIIlIllIlI > var5) {
               this.lIIIIllIIlIlIllIIIlIllIlI = var5;
            }
         }
      } else if (this.lIIIIllIIlIlIllIIIlIllIlI > 0) {
         this.lIIIIllIIlIlIllIIIlIllIlI = this.lIIIIllIIlIlIllIIIlIllIlI - var6 < 0.0F ? 0 : (int)(this.lIIIIllIIlIlIllIIIlIllIlI - var6);
      }

      if (this.lIIIIllIIlIlIllIIIlIllIlI > 0) {
         float var7 = (float)this.lIIIIllIIlIlIllIIIlIllIlI / var5 * 100.0F;
         cn.kazer.client189.render.DrawRect.a(
            this.x, (int)(this.y + (this.height - this.height * var7 / 100.0F)), this.x + this.width, this.y + this.height, this.lIIIIlIIllIIlIIlIIIlIIllI
         );
      }

      cn.kazer.client189.render.GlStateBridge.a(0.0F, 0.0F, 0.0F, 0.35F);
      com.cheatbreaker.client.gui.render.RenderUtils.a(this.IlllIllIlIIIIlIIlIIllIIIl, 2.5F, this.x + 6, this.y + 6.0F);
      com.cheatbreaker.client.CheatBreakerClient.N().playBold18px.a("Kazer Client Settings".toUpperCase(), this.x + 14.0F, this.y + 3.0F, -818991313);
   }

   @Override
   public void a(int var1, int var2, int var3) {
   }
}
