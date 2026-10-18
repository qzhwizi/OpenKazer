package com.cheatbreaker.client.network.serverinfo;

import java.io.IOException;

public class ServerSwitchPacket extends com.cheatbreaker.client.network.PluginMessagePacket {
   private String server;

   @Override
   public void a(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      var1.a(this.server);
   }

   @Override
   public void b(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      this.server = var1.b();
   }

   @Override
   public void a(com.cheatbreaker.client.network.PacketHandler var1) {
      ((com.cheatbreaker.client.network.rules.ServerPacketHandler)var1).a(this);
   }

   public ServerSwitchPacket(String var1) {
      this.server = var1;
   }

   public ServerSwitchPacket() {
   }

   public String b() {
      return this.server;
   }
}
