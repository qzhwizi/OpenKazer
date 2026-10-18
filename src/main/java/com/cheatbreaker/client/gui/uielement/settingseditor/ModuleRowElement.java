package com.cheatbreaker.client.gui.uielement.settingseditor;

import net.minecraft.util.ResourceLocation;

public class ModuleRowElement extends com.cheatbreaker.client.gui.uielement.UIElement {
   private final int IllIIIIIIIlIlIllllIIllIII;
   public final com.cheatbreaker.client.module.Module module;
   private final com.cheatbreaker.client.gui.uielement.ScrollableUIElement parent;
   private int IlllIllIlIIIIlIIlIIllIIIl = 0;
   private ResourceLocation rightIcon = new ResourceLocation("client/icons/right.png");

   public ModuleRowElement(com.cheatbreaker.client.gui.uielement.ScrollableUIElement var1, int var2, com.cheatbreaker.client.module.Module var3, float var4) {
      super(var4);
      this.parent = var1;
      this.IllIIIIIIIlIlIllllIIllIII = var2;
      this.module = var3;
   }

   @Override
   public void a(int var1, int var2, float var3) {
      boolean var4 = this.a(var1, var2);
      byte var5 = 75;
      cn.kazer.client189.render.DrawRect.a(this.x, this.y + this.height - 1, this.x + this.width, this.y + this.height, 791621423);
      if (this.parent.a(this.module)) {
         if (var4) {
            float var6 = com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.a(790.0F);
            if (this.IlllIllIlIIIIlIIlIIllIIIl + var6 < var5) {
               this.IlllIllIlIIIIlIIlIIllIIIl = (int)(this.IlllIllIlIIIIlIIlIIllIIIl + var6);
               if (this.IlllIllIlIIIIlIIlIIllIIIl > var5) {
                  this.IlllIllIlIIIIlIIlIIllIIIl = var5;
               }
            }
         } else if (this.IlllIllIlIIIIlIIlIIllIIIl > 0) {
            float var7 = com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.a(790.0F);
            this.IlllIllIlIIIIlIIlIIllIIIl = this.IlllIllIlIIIIlIIlIIllIIIl - var7 < 0.0F ? 0 : (int)(this.IlllIllIlIIIIlIIlIIllIIIl - var7);
         }

         if (this.IlllIllIlIIIIlIIlIIllIIIl > 0) {
            float var8 = (float)this.IlllIllIlIIIIlIIlIIllIIIl / var5 * 100.0F;
            cn.kazer.client189.render.DrawRect.a(
               this.x, (int)(this.y + (this.height - this.height * var8 / 100.0F)), this.x + this.width, this.y + this.height, this.IllIIIIIIIlIlIllllIIllIII
            );
         }
      }

      cn.kazer.client189.render.GlStateBridge.a(0.0F, 0.0F, 0.0F, 0.35F);
      com.cheatbreaker.client.gui.render.RenderUtils.a(this.rightIcon, 2.5F, this.x + 6, this.y + 6.0F);
      com.cheatbreaker.client.CheatBreakerClient.N()
         .playBold18px
         .a(this.module.h().toUpperCase(), this.x + 14.0F, this.y + 3.0F, this.parent.a(this.module) ? -818991313 : 791621423);
   }

   @Override
   public void a(int var1, int var2, int var3) {
      this.parent.b(this.module);
   }
}
