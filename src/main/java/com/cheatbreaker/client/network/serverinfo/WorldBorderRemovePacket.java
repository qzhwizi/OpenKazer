package com.cheatbreaker.client.network.serverinfo;

import java.io.IOException;

public class WorldBorderRemovePacket extends com.cheatbreaker.client.network.PluginMessagePacket {
   private String id;

   @Override
   public void a(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      var1.a(this.id);
   }

   @Override
   public void b(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      this.id = var1.b();
   }

   @Override
   public void a(com.cheatbreaker.client.network.PacketHandler var1) {
      ((com.cheatbreaker.client.network.rules.ServerPacketHandler)var1).a(this);
   }

   public WorldBorderRemovePacket(String var1) {
      this.id = var1;
   }

   public WorldBorderRemovePacket() {
   }

   public String b() {
      return this.id;
   }
}
