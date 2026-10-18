package com.cheatbreaker.client.network.serverinfo;

import java.io.IOException;

public class NotificationPacket extends com.cheatbreaker.client.network.PluginMessagePacket {
   private String message;
   private long durationMs;
   private String level;

   @Override
   public void a(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      var1.a(this.message);
      var1.d().writeLong(this.durationMs);
      var1.a(this.level);
   }

   @Override
   public void b(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      this.message = var1.b();
      this.durationMs = var1.d().readLong();
      this.level = var1.b();
   }

   @Override
   public void a(com.cheatbreaker.client.network.PacketHandler var1) {
      ((com.cheatbreaker.client.network.rules.ServerPacketHandler)var1).a(this);
   }

   public NotificationPacket(String var1, long var2, String var4) {
      this.message = var1;
      this.durationMs = var2;
      this.level = var4;
   }

   public NotificationPacket() {
   }

   public String b() {
      return this.message;
   }

   public long c() {
      return this.durationMs;
   }

   public String d() {
      return this.level;
   }
}
