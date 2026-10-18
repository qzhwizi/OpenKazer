package com.cheatbreaker.client.network.serverinfo;

import java.io.IOException;

public class WorldBorderUpdatePacket extends com.cheatbreaker.client.network.PluginMessagePacket {
   private String id;
   private double minX;
   private double minZ;
   private double maxX;
   private double maxZ;
   private int durationTicks;

   @Override
   public void a(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      var1.a(this.id);
      var1.d().writeDouble(this.minX);
      var1.d().writeDouble(this.minZ);
      var1.d().writeDouble(this.maxX);
      var1.d().writeDouble(this.maxZ);
      var1.d().writeInt(this.durationTicks);
   }

   @Override
   public void b(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      this.id = var1.b();
      this.minX = var1.d().readDouble();
      this.minZ = var1.d().readDouble();
      this.maxX = var1.d().readDouble();
      this.maxZ = var1.d().readDouble();
      this.durationTicks = var1.d().readInt();
   }

   @Override
   public void a(com.cheatbreaker.client.network.PacketHandler var1) {
      ((com.cheatbreaker.client.network.rules.ServerPacketHandler)var1).a(this);
   }

   public WorldBorderUpdatePacket(String var1, double var2, double var4, double var6, double var8, int var10) {
      this.id = var1;
      this.minX = var2;
      this.minZ = var4;
      this.maxX = var6;
      this.maxZ = var8;
      this.durationTicks = var10;
   }

   public WorldBorderUpdatePacket() {
   }

   public String b() {
      return this.id;
   }

   public double c() {
      return this.minX;
   }

   public double d() {
      return this.minZ;
   }

   public double e() {
      return this.maxX;
   }

   public double f() {
      return this.maxZ;
   }

   public int g() {
      return this.durationTicks;
   }
}
