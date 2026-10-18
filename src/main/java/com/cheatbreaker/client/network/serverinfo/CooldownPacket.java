package com.cheatbreaker.client.network.serverinfo;

import java.io.IOException;

public class CooldownPacket extends com.cheatbreaker.client.network.PluginMessagePacket {
   private String message;
   private long durationMs;
   private int iconId;

   @Override
   public void a(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      var1.a(this.message);
      var1.d().writeLong(this.durationMs);
      var1.d().writeInt(this.iconId);
   }

   @Override
   public void b(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      this.message = var1.b();
      this.durationMs = var1.d().readLong();
      this.iconId = var1.d().readInt();
   }

   @Override
   public void a(com.cheatbreaker.client.network.PacketHandler var1) {
      ((com.cheatbreaker.client.network.rules.ServerPacketHandler)var1).a(this);
   }

   public CooldownPacket(String var1, long var2, int var4) {
      this.message = var1;
      this.durationMs = var2;
      this.iconId = var4;
   }

   public CooldownPacket() {
   }

   public String b() {
      return this.message;
   }

   public long c() {
      return this.durationMs;
   }

   public int d() {
      return this.iconId;
   }
}
