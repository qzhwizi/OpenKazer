package com.cheatbreaker.client.event.event;

public class RenderTickEvent extends com.cheatbreaker.client.event.EventBus.aInner {
   private final float partialTicks;

   public float a() {
      return this.partialTicks;
   }

   public RenderTickEvent(float var1) {
      this.partialTicks = var1;
   }
}
