package com.cheatbreaker.client.gui.animation;

public class EaseOutAnimation extends com.cheatbreaker.client.gui.animation.LinearAnimation {
   public EaseOutAnimation(long var1) {
      super(var1);
   }

   @Override
   public float a() {
      float var1 = super.a();
      return (float)Math.pow(var1 * (2.0F - var1), 1.0);
   }
}
