package com.cheatbreaker.client.gui.animation;

public class LinearAnimation extends com.cheatbreaker.client.gui.animation.AbstractAnimation {
   public LinearAnimation(long var1) {
      super(var1, 1.0F);
   }

   public LinearAnimation(long var1, float var3) {
      super(var1, var3);
   }

   @Override
   protected float a() {
      return (float)(this.IlllIIIlIlllIllIlIIlllIlI - this.l()) / (float)this.IlllIIIlIlllIllIlIIlllIlI;
   }
}
