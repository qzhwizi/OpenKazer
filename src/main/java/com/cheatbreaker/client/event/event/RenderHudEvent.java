package com.cheatbreaker.client.event.event;

import net.minecraft.client.gui.ScaledResolution;

public class RenderHudEvent extends com.cheatbreaker.client.event.EventBus.aInner {
   private final ScaledResolution resolution;

   public RenderHudEvent(ScaledResolution var1) {
      this.resolution = var1;
   }

   public ScaledResolution a() {
      return this.resolution;
   }
}
