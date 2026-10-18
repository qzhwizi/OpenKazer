package com.cheatbreaker.client.module.modules;

import java.time.Clock;
import org.lwjgl.opengl.GL11;

public final class ClockModule extends com.cheatbreaker.client.module.Module {
   private final com.cheatbreaker.client.module.modules.ClockFormatter clockText;
   private final com.cheatbreaker.client.settings.Setting twentyFourHour;
   private final com.cheatbreaker.client.settings.Setting showSeconds;
   private final com.cheatbreaker.client.settings.Setting brackets;
   private final com.cheatbreaker.client.settings.Setting showBackground;
   private final com.cheatbreaker.client.settings.Setting textColor;
   private final com.cheatbreaker.client.settings.Setting backgroundColor;

   public ClockModule() {
      this(Clock.systemDefaultZone());
   }

   ClockModule(Clock var1) {
      super("Clock");
      this.clockText = new com.cheatbreaker.client.module.modules.ClockFormatter(var1);
      this.b(com.cheatbreaker.client.gui.hudeditor.GuiAnchor.RIGHT_TOP);
      this.b(-1.0F, 0.0F);
      this.c(true);
      this.twentyFourHour = new com.cheatbreaker.client.settings.Setting(this, "24 Hour").a(true);
      this.showSeconds = new com.cheatbreaker.client.settings.Setting(this, "Show Seconds").a(true);
      this.brackets = new com.cheatbreaker.client.settings.Setting(this, "Brackets").a(true);
      this.showBackground = new com.cheatbreaker.client.settings.Setting(this, "Show Background").a(false);
      this.textColor = new com.cheatbreaker.client.settings.Setting(this, "Text Color").a(-1).a(Integer.MIN_VALUE, Integer.MAX_VALUE);
      this.backgroundColor = new com.cheatbreaker.client.settings.Setting(this, "Background Color").a(1862270976).a(Integer.MIN_VALUE, Integer.MAX_VALUE);
      this.a("[13:05:09]", 1.5F);
      this.a(com.cheatbreaker.client.event.event.RenderHudEvent.class, this::a);
      this.a(com.cheatbreaker.client.event.event.RenderHudEditorEvent.class, this::a);
   }

   String a() {
      String var1 = this.clockText.a((Boolean)this.twentyFourHour.d(), (Boolean)this.showSeconds.d());
      return (Boolean) this.brackets.d() ? "[" + var1 + "]" : var1;
   }

   float[] a(int var1, int var2) {
      boolean var3 = (Boolean)this.showBackground.d();
      return new float[]{var1 + (var3 ? 4.0F : 0.0F), var3 ? var2 + 4.0F : var2};
   }

   int z() {
      return (Boolean) this.showBackground.d() ? 3 : 0;
   }

   private void a(com.cheatbreaker.client.event.event.RenderHudEvent var1) {
      if (this.q()
         && this.minecraft.thePlayer != null
         && !(this.minecraft.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.HudEditorScreen)
         && !(this.minecraft.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.ModulePlacementScreen)) {
         this.b(var1);
      }
   }

   private void a(com.cheatbreaker.client.event.event.RenderHudEditorEvent var1) {
      if (this.q()) {
         if (this.minecraft.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.ModulePlacementScreen || this.minecraft.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.HudEditorScreen) {
            this.b(var1);
         }
      }
   }

   private void b(com.cheatbreaker.client.event.event.RenderHudEvent var1) {
      String var2 = this.a();
      boolean var3 = (Boolean)this.showBackground.d();
      int var4 = this.minecraft.fontRendererObj.getStringWidth(var2);
      float[] var5 = this.a(var4, this.minecraft.fontRendererObj.FONT_HEIGHT);
      float var6 = var5[0];
      float var7 = var5[1];
      this.c(var6, var7);
      float var8 = var3 ? 2.0F : 0.0F;
      int var9 = this.z();
      boolean var10 = GL11.glIsEnabled(2929);
      GL11.glPushMatrix();
      cn.kazer.client189.render.GlStateBridge.b(2929);

      try {
         this.a(var1.a());
         if (var3) {
            cn.kazer.client189.render.DrawRect.a(0.0F, 0.0F, var6, var7, this.backgroundColor.a());
            this.minecraft.fontRendererObj.drawString(var2, (int)var8, var9, this.textColor.a());
         } else {
            this.minecraft.fontRendererObj.drawStringWithShadow(var2, 0.0F, var9, this.textColor.a());
         }
      } finally {
         GL11.glPopMatrix();
         if (var10) {
            cn.kazer.client189.render.GlStateBridge.a(2929);
         }
      }
   }
}
