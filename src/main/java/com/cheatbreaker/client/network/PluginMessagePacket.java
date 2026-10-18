package com.cheatbreaker.client.network;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.netty.buffer.Unpooled;
import java.io.IOException;

public abstract class PluginMessagePacket {
   private static final BiMap<Class<? extends com.cheatbreaker.client.network.PluginMessagePacket>, Integer> REGISTRY = HashBiMap.create();
   private Object attachment;

   public abstract void a(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException;

   public abstract void b(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException;

   public abstract void a(com.cheatbreaker.client.network.PacketHandler var1);

   public static com.cheatbreaker.client.network.PluginMessagePacket a(com.cheatbreaker.client.network.PacketHandler var0, byte[] var1) {
      return a(var0, var1, null);
   }

   public static com.cheatbreaker.client.network.PluginMessagePacket a(com.cheatbreaker.client.network.PacketHandler var0, byte[] var1, Object var2) {
      com.cheatbreaker.client.network.PluginMessageBuffer var3 = new com.cheatbreaker.client.network.PluginMessageBuffer(Unpooled.wrappedBuffer(var1));
      int var4 = var3.a();
      Class var5 = (Class)REGISTRY.inverse().get(var4);
      if (var5 != null) {
         try {
            com.cheatbreaker.client.network.PluginMessagePacket var6 = (com.cheatbreaker.client.network.PluginMessagePacket)var5.newInstance();
            if (var2 != null) {
               var6.a(var2);
            }

            var6.b(var3);
            var6.a(var0);
            return var6;
         } catch (IllegalAccessException | InstantiationException | IOException var7) {
            var7.printStackTrace();
         }
      }

      return null;
   }

   public static byte[] a(com.cheatbreaker.client.network.PluginMessagePacket var0) {
      com.cheatbreaker.client.network.PluginMessageBuffer var1 = new com.cheatbreaker.client.network.PluginMessageBuffer(Unpooled.buffer());
      var1.a((Integer)REGISTRY.get(var0.getClass()));

      try {
         var0.a(var1);
      } catch (IOException var3) {
         var3.printStackTrace();
      }

      return var1.d().array();
   }

   private static void a(int var0, Class<? extends com.cheatbreaker.client.network.PluginMessagePacket> var1) {
      if (REGISTRY.containsKey(var1)) {
         throw new IllegalArgumentException("Duplicate packet class (" + var1.getSimpleName() + "), already used by " + REGISTRY.get(var1));
      } else if (REGISTRY.containsValue(var0)) {
         throw new IllegalArgumentException("Duplicate packet ID (" + var0 + "), already used by " + REGISTRY.inverse().get(var0));
      } else {
         REGISTRY.put(var1, var0);
      }
   }

   protected void a(com.cheatbreaker.client.network.PluginMessageBuffer var1, byte[] var2) {
      var1.d().writeShort(var2.length);
      var1.d().writeBytes(var2);
   }

   protected byte[] c(com.cheatbreaker.client.network.PluginMessageBuffer var1) {
      short var2 = var1.d().readShort();
      if (var2 < 0) {
         System.out.println("Key was smaller than nothing!  Weird key!");
         return null;
      } else {
         byte[] var3 = new byte[var2];
         var1.d().readBytes(var3);
         return var3;
      }
   }

   public void a(Object var1) {
      this.attachment = var1;
   }

   public Object a() {
      return this.attachment;
   }

   static {
      a(3, com.cheatbreaker.client.network.serverinfo.CooldownPacket.class);
      a(4, com.cheatbreaker.client.network.serverinfo.NotificationPacket.class);
      a(5, com.cheatbreaker.client.network.serverinfo.StaffModuleStatePacket.class);
      a(6, com.cheatbreaker.client.network.serverinfo.NametagsPacket.class);
      a(7, com.cheatbreaker.client.network.serverinfo.TeamInfoPacket.class);
      a(8, com.cheatbreaker.client.network.serverinfo.NametagUpdatePacket.class);
      a(9, com.cheatbreaker.client.network.serverinfo.HologramSpawnPacket.class);
      a(10, com.cheatbreaker.client.network.serverinfo.HologramUpdatePacket.class);
      a(11, com.cheatbreaker.client.network.serverinfo.HologramRemovePacket.class);
      a(12, com.cheatbreaker.client.network.serverinfo.TitleMessagePacket.class);
      a(14, com.cheatbreaker.client.network.serverinfo.GameModeRulePacket.class);
      a(23, com.cheatbreaker.client.network.serverinfo.WorldUpdatePacket.class);
      a(24, com.cheatbreaker.client.network.serverinfo.ServerSwitchPacket.class);
      a(25, com.cheatbreaker.client.network.serverinfo.WorldBorderCreatePacket.class);
      a(26, com.cheatbreaker.client.network.serverinfo.WorldBorderUpdatePacket.class);
      a(27, com.cheatbreaker.client.network.serverinfo.WorldBorderRemovePacket.class);
   }
}
