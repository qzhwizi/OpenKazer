package com.cheatbreaker.client.network.serverinfo;

import java.io.IOException;
import java.util.UUID;

public class HologramRemovePacket extends com.cheatbreaker.client.network.PluginMessagePacket {
   private UUID uuid;

   @Override
   public void a(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      var1.a(this.uuid);
   }

   @Override
   public void b(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      this.uuid = var1.c();
   }

   @Override
   public void a(com.cheatbreaker.client.network.PacketHandler var1) {
      ((com.cheatbreaker.client.network.rules.ServerPacketHandler)var1).a(this);
   }

   public HologramRemovePacket(UUID var1) {
      this.uuid = var1;
   }

   public HologramRemovePacket() {
   }

   public UUID b() {
      return this.uuid;
   }
}
