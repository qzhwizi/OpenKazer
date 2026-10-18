package com.cheatbreaker.client.gui.screens;

public class GradientButton extends com.cheatbreaker.client.gui.screens.GuiElement {
   private String IIIllIllIlIlllllllIlIlIII;
   private final com.cheatbreaker.client.gui.animation.ColorAnimation IllIIIIIIIlIlIllllIIllIII;
   private final com.cheatbreaker.client.gui.animation.ColorAnimation lIIIIllIIlIlIllIIIlIllIlI;
   private final com.cheatbreaker.client.gui.animation.ColorAnimation IlllIllIlIIIIlIIlIIllIIIl;
   private int[] IlIlllIIIIllIllllIllIIlIl;

   public GradientButton(String var1) {
      this.IIIllIllIlIlllllllIlIlIII = var1;
      this.IllIIIIIIIlIlIllllIIllIII = new com.cheatbreaker.client.gui.animation.ColorAnimation(-14277082, -11493284);
      this.lIIIIllIIlIlIllIIIlIllIlI = new com.cheatbreaker.client.gui.animation.ColorAnimation(-13487566, -10176146);
      this.IlllIllIlIIIIlIIlIIllIIIl = new com.cheatbreaker.client.gui.animation.ColorAnimation(-14013910, -11164318);
   }

   public void h() {
      this.a(new int[]{-11119018, -11493284, -10329502, -10176146, -11579569, -11164318});
   }

   public void i() {
      this.a(new int[]{-11493284, -11493284, -10176146, -10176146, -11164318, -11164318});
   }

   public void j() {
      this.a(new int[]{-14277082, -11493284, -13487566, -10176146, -14013910, -11164318});
   }

   private void a(int[] var1) {
      this.IlIlllIIIIllIllllIllIIlIl = var1;
   }

   @Override
   protected void b(float var1, float var2, boolean var3) {
      boolean var4 = var3 && this.a(var1, var2);
      if (this.IlIlllIIIIllIllllIllIIlIl != null && this.IllIIIIIIIlIlIllllIIllIII.e()) {
         this.IllIIIIIIIlIlIllllIIllIII.b(this.IlIlllIIIIllIllllIllIIlIl[0]);
         this.IllIIIIIIIlIlIllllIIllIII.c(this.IlIlllIIIIllIllllIllIIlIl[1]);
         this.lIIIIllIIlIlIllIIIlIllIlI.b(this.IlIlllIIIIllIllllIllIIlIl[2]);
         this.lIIIIllIIlIlIllIIIlIllIlI.c(this.IlIlllIIIIllIllllIllIIlIl[3]);
         this.IlllIllIlIIIIlIIlIIllIIIl.b(this.IlIlllIIIIllIllllIllIIlIl[4]);
         this.IlllIllIlIIIIlIIlIIllIIIl.c(this.IlIlllIIIIllIllllIllIIlIl[5]);
         this.IlIlllIIIIllIllllIllIIlIl = null;
      }

      com.cheatbreaker.client.gui.render.RenderUtils.a(
         this.x,
         this.y,
         this.x + this.width,
         this.y + this.height,
         this.IllIIIIIIIlIlIllllIIllIII.b(var4).getRGB(),
         this.lIIIIllIIlIlIllIIIlIllIlI.b(var4).getRGB(),
         this.IlllIllIlIIIIlIIlIIllIIIl.b(var4).getRGB()
      );
      float var10002 = this.x + this.width / 2.0F;
      float var10003 = this.y + 2.0F;
      com.cheatbreaker.client.CheatBreakerClient.N().robotoRegular13px.b(this.IIIllIllIlIlllllllIlIlIII, var10002, var10003, -1);
   }

   public void c(float var1, float var2, boolean var3) {
      this.b(var1, var2, var3);
   }

   public void d(float var1, float var2, boolean var3) {
      this.b(var1, var2, var3);
   }

   @Override
   public boolean a(float var1, float var2, int var3, boolean var4) {
      return !var4 ? false : false;
   }

   public String k() {
      return this.IIIllIllIlIlllllllIlIlIII;
   }

   public void a(String var1) {
      this.IIIllIllIlIlllllllIlIlIII = var1;
   }
}
