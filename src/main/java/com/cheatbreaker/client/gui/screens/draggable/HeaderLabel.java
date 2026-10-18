package com.cheatbreaker.client.gui.screens.draggable;

public class HeaderLabel extends com.cheatbreaker.client.gui.screens.GuiElement {
   private final String IIIllIllIlIlllllllIlIlIII;
   private final com.cheatbreaker.client.gui.animation.ColorAnimation IllIIIIIIIlIlIllllIIllIII;

   public HeaderLabel(String var1) {
      this.IIIllIllIlIlllllllIlIlIII = var1;
      this.IllIIIIIIIlIlIllllIIllIII = new com.cheatbreaker.client.gui.animation.ColorAnimation(-1879048193, -1);
   }

   @Override
   protected void b(float var1, float var2, boolean var3) {
      float var10002 = this.x + 6.0F;
      float var10003 = this.y + 6.0F;
      com.cheatbreaker.client.CheatBreakerClient.N()
         .robotoBold14px
         .a(this.IIIllIllIlIlllllllIlIlIII, var10002, var10003, this.IllIIIIIIIlIlIllllIIllIII.b(this.a(var1, var2) && var3).getRGB());
   }
}
