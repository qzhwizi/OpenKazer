package com.cheatbreaker.client.network.serverinfo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

public class TeamInfoPacket extends com.cheatbreaker.client.network.PluginMessagePacket {
   private UUID leader;
   private long lastMs;
   private Map<UUID, Map<String, Double>> players;

   @Override
   public void a(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      var1.d().writeBoolean(this.leader != null);
      if (this.leader != null) {
         var1.a(this.leader);
      }

      var1.d().writeLong(this.lastMs);
      var1.a(this.players.values().size());

      for (Entry var3 : this.players.entrySet()) {
         var1.a((UUID)var3.getKey());
         var1.a(((Map)var3.getValue()).values().size());

         for (Entry var5 : (Iterable<Entry>) ((Map)var3.getValue()).entrySet()) {
            var1.a((String)var5.getKey());
            var1.d().writeDouble((Double)var5.getValue());
         }
      }
   }

   @Override
   public void b(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      boolean var2 = var1.d().readBoolean();
      if (var2) {
         this.leader = var1.c();
      }

      this.lastMs = var1.d().readLong();
      int var3 = var1.a();
      this.players = new HashMap<>();

      for (int var4 = 0; var4 < var3; var4++) {
         UUID var5 = var1.c();
         int var6 = var1.a();
         HashMap var7 = new HashMap();

         for (int var8 = 0; var8 < var6; var8++) {
            String var9 = var1.b();
            double var10 = var1.d().readDouble();
            var7.put(var9, var10);
         }

         this.players.put(var5, var7);
      }
   }

   @Override
   public void a(com.cheatbreaker.client.network.PacketHandler var1) {
      ((com.cheatbreaker.client.network.rules.ServerPacketHandler)var1).a(this);
   }

   public TeamInfoPacket(UUID var1, long var2, Map<UUID, Map<String, Double>> var4) {
      this.leader = var1;
      this.lastMs = var2;
      this.players = var4;
   }

   public TeamInfoPacket() {
   }

   public UUID b() {
      return this.leader;
   }

   public long c() {
      return this.lastMs;
   }

   public Map<UUID, Map<String, Double>> d() {
      return this.players;
   }
}
