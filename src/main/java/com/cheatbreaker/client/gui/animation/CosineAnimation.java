package com.cheatbreaker.client.gui.animation;

public class CosineAnimation extends com.cheatbreaker.client.gui.animation.LinearAnimation {
   public CosineAnimation(long var1) {
      super(var1, 0.0F);
   }

   @Override
   protected float a() {
      float var1 = super.a();
      float var2 = var1 * 2.0F - 1.0F;
      return (float)(Math.cos(var2 * Math.PI) + 1.0) / 2.0F;
   }
}
