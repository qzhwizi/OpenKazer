package com.cheatbreaker.client.module.modules;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class FpsModule extends com.cheatbreaker.client.module.Module {
   private final com.cheatbreaker.client.settings.Setting showBackground;
   private final com.cheatbreaker.client.settings.Setting reverseOrder;
   private final com.cheatbreaker.client.settings.Setting textShadow;
   private final com.cheatbreaker.client.settings.Setting textColor;
   private final com.cheatbreaker.client.settings.Setting backgroundColor;
   private final com.cheatbreaker.client.module.modules.LabelValueCache counterText = new com.cheatbreaker.client.module.modules.LabelValueCache("FPS");

   public FpsModule() {
      super("FPS");
      this.b(com.cheatbreaker.client.gui.hudeditor.GuiAnchor.MIDDLE_TOP);
      this.b(0.0F, 12.0F);
      this.c(true);
      this.reverseOrder = new com.cheatbreaker.client.settings.Setting(this, "Reverse Order").a(false);
      this.textShadow = new com.cheatbreaker.client.settings.Setting(this, "Text Shadow").a(true);
      this.showBackground = new com.cheatbreaker.client.settings.Setting(this, "Show Background").a(true);
      this.textColor = new com.cheatbreaker.client.settings.Setting(this, "Text Color").a(-1).a(Integer.MIN_VALUE, Integer.MAX_VALUE);
      this.backgroundColor = new com.cheatbreaker.client.settings.Setting(this, "Background Color").a(1862270976).a(Integer.MIN_VALUE, Integer.MAX_VALUE);
      this.a("[144 FPS]", 1.4F);
      this.a(com.cheatbreaker.client.event.event.RenderHudEvent.class, this::a);
   }

   private void a(com.cheatbreaker.client.event.event.RenderHudEvent var1) {
      if (this.q()) {
         boolean var2 = (Boolean)this.showBackground.d();
         String var3 = this.counterText.a(Minecraft.getDebugFPS(), var2, (Boolean)this.reverseOrder.d(), true);
         int var4 = this.minecraft.fontRendererObj.getStringWidth(var3);
         this.c(var2 ? 56.0F : var4, this.minecraft.fontRendererObj.FONT_HEIGHT + (var2 ? 4.0F : 0.0F));
         int var5 = this.a();
         GL11.glPushMatrix();
         this.a(var1.a());
         if (var2) {
            cn.kazer.client189.render.DrawRect.a(0.0F, 0.0F, this.width, this.height, this.backgroundColor.a());
            this.minecraft.fontRendererObj.drawString(var3, (int)(this.width / 2.0F - var4 / 2), var5, this.textColor.a());
         } else {
            this.minecraft.fontRendererObj.drawString(var3, 0.0F, var5, this.textColor.a(), (Boolean)this.textShadow.d());
         }

         GL11.glPopMatrix();
      }
   }

   float[] a(int var1, int var2) {
      return (Boolean) this.showBackground.d() ? new float[]{56.0F, var2 + 4.0F} : new float[]{var1, var2};
   }

   int a() {
      return (Boolean) this.showBackground.d() ? 3 : 0;
   }
}
