package com.cheatbreaker.client.network.serverinfo;

import java.io.IOException;

public class GameModeRulePacket extends com.cheatbreaker.client.network.PluginMessagePacket {
   private com.cheatbreaker.client.network.gamemode.GameModeRule rule;
   private int intValue;
   private float floatValue;
   private boolean enabled;
   private String stringValue;

   public GameModeRulePacket() {
      this.stringValue = "";
   }

   public GameModeRulePacket(com.cheatbreaker.client.network.gamemode.GameModeRule var1, float var2) {
      this(var1);
      this.floatValue = var2;
   }

   public GameModeRulePacket(com.cheatbreaker.client.network.gamemode.GameModeRule var1, boolean var2) {
      this(var1);
      this.enabled = var2;
   }

   public GameModeRulePacket(com.cheatbreaker.client.network.gamemode.GameModeRule var1, int var2) {
      this(var1);
      this.intValue = var2;
   }

   public GameModeRulePacket(com.cheatbreaker.client.network.gamemode.GameModeRule var1, String var2) {
      this(var1);
      this.stringValue = var2;
   }

   private GameModeRulePacket(com.cheatbreaker.client.network.gamemode.GameModeRule var1) {
      this.rule = var1;
      this.stringValue = "";
   }

   @Override
   public void a(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      var1.a(this.rule.a());
      var1.d().writeBoolean(this.enabled);
      var1.d().writeInt(this.intValue);
      var1.d().writeFloat(this.floatValue);
      var1.a(this.stringValue);
   }

   @Override
   public void b(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      this.rule = com.cheatbreaker.client.network.gamemode.GameModeRule.a(var1.b());
      this.enabled = var1.d().readBoolean();
      this.intValue = var1.d().readInt();
      this.floatValue = var1.d().readFloat();
      this.stringValue = var1.b();
   }

   @Override
   public void a(com.cheatbreaker.client.network.PacketHandler var1) {
      ((com.cheatbreaker.client.network.rules.ServerPacketHandler)var1).a(this);
   }

   public com.cheatbreaker.client.network.gamemode.GameModeRule b() {
      return this.rule;
   }

   public int c() {
      return this.intValue;
   }

   public float d() {
      return this.floatValue;
   }

   public boolean e() {
      return this.enabled;
   }

   public String f() {
      return this.stringValue;
   }
}
