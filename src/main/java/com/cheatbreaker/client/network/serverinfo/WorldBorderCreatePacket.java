package com.cheatbreaker.client.network.serverinfo;

import java.io.IOException;

public class WorldBorderCreatePacket extends com.cheatbreaker.client.network.PluginMessagePacket {
   private String id;
   private String world;
   private boolean cancelsExit;
   private boolean canShrinkExpand;
   private int color;
   private double minX;
   private double minZ;
   private double maxX;
   private double maxZ;

   @Override
   public void a(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      var1.a(this.id, var1::a);
      var1.a(this.world);
      var1.d().writeBoolean(this.cancelsExit);
      var1.d().writeBoolean(this.canShrinkExpand);
      var1.d().writeInt(this.color);
      var1.d().writeDouble(this.minX);
      var1.d().writeDouble(this.minZ);
      var1.d().writeDouble(this.maxX);
      var1.d().writeDouble(this.maxZ);
   }

   @Override
   public void b(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      this.id = var1.a(var1::b);
      this.world = var1.b();
      this.cancelsExit = var1.d().readBoolean();
      this.canShrinkExpand = var1.d().readBoolean();
      this.color = var1.d().readInt();
      this.minX = var1.d().readDouble();
      this.minZ = var1.d().readDouble();
      this.maxX = var1.d().readDouble();
      this.maxZ = var1.d().readDouble();
   }

   @Override
   public void a(com.cheatbreaker.client.network.PacketHandler var1) {
      ((com.cheatbreaker.client.network.rules.ServerPacketHandler)var1).a(this);
   }

   public WorldBorderCreatePacket(String var1, String var2, boolean var3, boolean var4, int var5, double var6, double var8, double var10, double var12) {
      this.id = var1;
      this.world = var2;
      this.cancelsExit = var3;
      this.canShrinkExpand = var4;
      this.color = var5;
      this.minX = var6;
      this.minZ = var8;
      this.maxX = var10;
      this.maxZ = var12;
   }

   public WorldBorderCreatePacket() {
   }

   public String b() {
      return this.id;
   }

   public String c() {
      return this.world;
   }

   public boolean d() {
      return this.cancelsExit;
   }

   public boolean e() {
      return this.canShrinkExpand;
   }

   public int f() {
      return this.color;
   }

   public double g() {
      return this.minX;
   }

   public double h() {
      return this.minZ;
   }

   public double i() {
      return this.maxX;
   }

   public double j() {
      return this.maxZ;
   }
}
