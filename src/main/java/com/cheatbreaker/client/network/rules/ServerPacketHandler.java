package com.cheatbreaker.client.network.rules;

public interface ServerPacketHandler extends com.cheatbreaker.client.network.PacketHandler {
   void a(com.cheatbreaker.client.network.serverinfo.CooldownPacket var1);

   void a(com.cheatbreaker.client.network.serverinfo.NotificationPacket var1);

   void a(com.cheatbreaker.client.network.serverinfo.StaffModuleStatePacket var1);

   void a(com.cheatbreaker.client.network.serverinfo.NametagsPacket var1);

   void a(com.cheatbreaker.client.network.serverinfo.TeamInfoPacket var1);

   void a(com.cheatbreaker.client.network.serverinfo.NametagUpdatePacket var1);

   void a(com.cheatbreaker.client.network.serverinfo.HologramSpawnPacket var1);

   void a(com.cheatbreaker.client.network.serverinfo.HologramUpdatePacket var1);

   void a(com.cheatbreaker.client.network.serverinfo.HologramRemovePacket var1);

   void a(com.cheatbreaker.client.network.serverinfo.TitleMessagePacket var1);

   void a(com.cheatbreaker.client.network.serverinfo.GameModeRulePacket var1);

   void a(com.cheatbreaker.client.network.serverinfo.WorldUpdatePacket var1);

   void a(com.cheatbreaker.client.network.serverinfo.ServerSwitchPacket var1);

   void a(com.cheatbreaker.client.network.serverinfo.WorldBorderCreatePacket var1);

   void a(com.cheatbreaker.client.network.serverinfo.WorldBorderUpdatePacket var1);

   void a(com.cheatbreaker.client.network.serverinfo.WorldBorderRemovePacket var1);
}
