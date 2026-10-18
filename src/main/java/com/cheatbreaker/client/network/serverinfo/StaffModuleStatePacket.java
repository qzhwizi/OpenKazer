package com.cheatbreaker.client.network.serverinfo;

import java.io.IOException;

public class StaffModuleStatePacket extends com.cheatbreaker.client.network.PluginMessagePacket {
   private String mod;
   private boolean state;

   @Override
   public void a(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      var1.a(this.mod);
      var1.d().writeBoolean(this.state);
   }

   @Override
   public void b(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      this.mod = var1.b();
      this.state = var1.d().readBoolean();
   }

   @Override
   public void a(com.cheatbreaker.client.network.PacketHandler var1) {
      ((com.cheatbreaker.client.network.rules.ServerPacketHandler)var1).a(this);
   }

   public StaffModuleStatePacket(String var1, boolean var2) {
      this.mod = var1;
      this.state = var2;
   }

   public StaffModuleStatePacket() {
   }

   public String b() {
      return this.mod;
   }

   public boolean c() {
      return this.state;
   }
}
