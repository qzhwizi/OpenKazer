package com.cheatbreaker.client.network.serverinfo;

import java.io.IOException;

public class TitleMessagePacket extends com.cheatbreaker.client.network.PluginMessagePacket {
   private String type;
   private String message;
   private float scale;
   private long displayTimeMs;
   private long fadeInTimeMs;
   private long fadeOutTimeMs;

   public TitleMessagePacket(String var1, String var2, long var3, long var5, long var7) {
      this(var1, var2, 1.0F, var3, var5, var7);
   }

   @Override
   public void a(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      var1.a(this.type);
      var1.a(this.message);
      var1.d().writeFloat(this.scale);
      var1.d().writeLong(this.displayTimeMs);
      var1.d().writeLong(this.fadeInTimeMs);
      var1.d().writeLong(this.fadeOutTimeMs);
   }

   @Override
   public void b(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      this.type = var1.b();
      this.message = var1.b();
      this.scale = var1.d().readFloat();
      this.displayTimeMs = var1.d().readLong();
      this.fadeInTimeMs = var1.d().readLong();
      this.fadeOutTimeMs = var1.d().readLong();
   }

   @Override
   public void a(com.cheatbreaker.client.network.PacketHandler var1) {
      ((com.cheatbreaker.client.network.rules.ServerPacketHandler)var1).a(this);
   }

   public TitleMessagePacket(String var1, String var2, float var3, long var4, long var6, long var8) {
      this.type = var1;
      this.message = var2;
      this.scale = var3;
      this.displayTimeMs = var4;
      this.fadeInTimeMs = var6;
      this.fadeOutTimeMs = var8;
   }

   public TitleMessagePacket() {
   }

   public String b() {
      return this.type;
   }

   public String c() {
      return this.message;
   }

   public float d() {
      return this.scale;
   }

   public long e() {
      return this.displayTimeMs;
   }

   public long f() {
      return this.fadeInTimeMs;
   }

   public long g() {
      return this.fadeOutTimeMs;
   }
}
