package com.cheatbreaker.client.network.serverinfo;

import java.io.IOException;

public class WorldUpdatePacket extends com.cheatbreaker.client.network.PluginMessagePacket {
   private String world;

   @Override
   public void a(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      var1.a(this.world);
   }

   @Override
   public void b(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      this.world = var1.b();
   }

   @Override
   public void a(com.cheatbreaker.client.network.PacketHandler var1) {
      ((com.cheatbreaker.client.network.rules.ServerPacketHandler)var1).a(this);
   }

   public WorldUpdatePacket(String var1) {
      this.world = var1;
   }

   public WorldUpdatePacket() {
   }

   public String b() {
      return this.world;
   }
}
