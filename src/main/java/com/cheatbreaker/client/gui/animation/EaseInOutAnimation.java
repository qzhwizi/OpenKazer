package com.cheatbreaker.client.gui.animation;

public class EaseInOutAnimation extends com.cheatbreaker.client.gui.animation.LinearAnimation {
   public EaseInOutAnimation(long var1) {
      super(var1);
   }

   @Override
   protected float a() {
      float var1 = super.a();
      return var1 < 0.5 ? 2.0F * var1 * var1 : -1.0F + (4.0F - 2.0F * var1) * var1;
   }
}
