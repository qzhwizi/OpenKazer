package com.cheatbreaker.client.network.serverinfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NametagUpdatePacket extends com.cheatbreaker.client.network.PluginMessagePacket {
   private UUID player;
   private List<String> tags;

   @Override
   public void a(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      var1.a(this.player);
      var1.a(this.tags, var1x -> {
         var1.a(var1x.size());
         var1x.forEach(var1::a);
      });
   }

   @Override
   public void b(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      this.player = var1.c();
      this.tags = var1.a(() -> {
         int var1x = var1.a();
         ArrayList var2 = new ArrayList();

         for (int var3 = 0; var3 < var1x; var3++) {
            var2.add(var1.b());
         }

         return var2;
      });
   }

   @Override
   public void a(com.cheatbreaker.client.network.PacketHandler var1) {
      ((com.cheatbreaker.client.network.rules.ServerPacketHandler)var1).a(this);
   }

   public NametagUpdatePacket(UUID var1, List<String> var2) {
      this.player = var1;
      this.tags = var2;
   }

   public NametagUpdatePacket() {
   }

   public UUID b() {
      return this.player;
   }

   public List<String> c() {
      return this.tags;
   }
}
