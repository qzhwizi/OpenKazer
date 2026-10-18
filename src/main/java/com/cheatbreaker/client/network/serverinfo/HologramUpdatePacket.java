package com.cheatbreaker.client.network.serverinfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HologramUpdatePacket extends com.cheatbreaker.client.network.PluginMessagePacket {
   private UUID uuid;
   private List<String> lines;

   @Override
   public void a(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      var1.a(this.uuid);
      var1.a(this.lines.size());
      this.lines.forEach(var1::a);
   }

   @Override
   public void b(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      this.uuid = var1.c();
      int var2 = var1.a();
      this.lines = new ArrayList<>();

      for (int var3 = 0; var3 < var2; var3++) {
         this.lines.add(var1.b());
      }
   }

   @Override
   public void a(com.cheatbreaker.client.network.PacketHandler var1) {
      ((com.cheatbreaker.client.network.rules.ServerPacketHandler)var1).a(this);
   }

   public HologramUpdatePacket(UUID var1, List<String> var2) {
      this.uuid = var1;
      this.lines = var2;
   }

   public HologramUpdatePacket() {
   }

   public UUID b() {
      return this.uuid;
   }

   public List<String> c() {
      return this.lines;
   }
}
