package com.cheatbreaker.client.event.event;

import net.minecraft.client.multiplayer.WorldClient;

public class WorldLoadEvent extends com.cheatbreaker.client.event.EventBus.aInner {
   private final WorldClient world;

   public WorldLoadEvent(WorldClient var1) {
      this.world = var1;
   }

   public WorldClient a() {
      return this.world;
   }
}
