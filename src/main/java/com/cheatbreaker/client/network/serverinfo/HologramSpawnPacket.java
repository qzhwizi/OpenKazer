package com.cheatbreaker.client.network.serverinfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HologramSpawnPacket extends com.cheatbreaker.client.network.PluginMessagePacket {
   private UUID uuid;
   private double x;
   private double y;
   private double z;
   private List<String> lines;

   @Override
   public void a(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      var1.a(this.uuid);
      var1.d().writeDouble(this.x);
      var1.d().writeDouble(this.y);
      var1.d().writeDouble(this.z);
      var1.a(this.lines.size());
      this.lines.forEach(var1::a);
   }

   @Override
   public void b(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      this.uuid = var1.c();
      this.x = var1.d().readDouble();
      this.y = var1.d().readDouble();
      this.z = var1.d().readDouble();
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

   public HologramSpawnPacket(UUID var1, double var2, double var4, double var6, List<String> var8) {
      this.uuid = var1;
      this.x = var2;
      this.y = var4;
      this.z = var6;
      this.lines = var8;
   }

   public HologramSpawnPacket() {
   }

   public UUID b() {
      return this.uuid;
   }

   public double c() {
      return this.x;
   }

   public double d() {
      return this.y;
   }

   public double e() {
      return this.z;
   }

   public List<String> f() {
      return this.lines;
   }
}
