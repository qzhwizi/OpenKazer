package com.cheatbreaker.client.network;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.event.HoverEvent;
import net.minecraft.event.HoverEvent.Action;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ChatComponentStyle;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import org.apache.logging.log4j.LogManager;

public class ServerDataHandler implements com.cheatbreaker.client.network.rules.ServerPacketHandler, com.cheatbreaker.client.network.PacketHandler {
   private String world = "";
   private boolean competitiveGamemode = false;
   private boolean lIIIIIIIIIlIllIIllIlIIlIl = false;
   private Map<UUID, List<String>> nametagsMap = new HashMap<>();
   private final com.cheatbreaker.client.network.stats.ApolloStateManager apollo = new com.cheatbreaker.client.network.stats.ApolloStateManager();

   public void a(boolean var1) {
      if (var1) {
         this.apollo.c();
      } else {
         this.apollo.d();
      }

      this.f();
   }

   public void a() {
      this.apollo.c();
      this.nametagsMap.clear();
      this.b();
   }

   public List<String> a(UUID var1) {
      return this.apollo.a().containsKey(var1) ? this.apollo.a().get(var1) : this.nametagsMap.get(var1);
   }

   private void f() {
      this.competitiveGamemode = false;
      this.world = "";
      this.nametagsMap = new HashMap<>();

      for (com.cheatbreaker.client.module.Module var2 : com.cheatbreaker.client.CheatBreakerClient.N().k().staffModules) {
         ((com.cheatbreaker.client.module.module.KeybindModule)var2).z();
      }

      com.cheatbreaker.client.CheatBreakerClient.N().D().a();
      com.cheatbreaker.client.util.hologram.Hologram.g().clear();
      this.b();
   }

   public void b() {
      com.cheatbreaker.client.CheatBreakerClient.N().C().a().clear();
   }

   public void a(com.cheatbreaker.client.event.event.PluginMessageEvent var1) {
      try {
         if (!var1.b().equals("lunar:apollo") && !var1.b().equals("apollo:json")) {
            if (var1.b().equals("REGISTER")) {
               String var6 = new String(var1.a(), Charsets.UTF_8);
               this.lIIIIIIIIIlIllIIllIlIIlIl = var6.contains(com.cheatbreaker.client.CheatBreakerClient.N().f());
               PacketBuffer var8 = new PacketBuffer(Unpooled.buffer());
               var8.writeBytes(com.cheatbreaker.client.CheatBreakerClient.N().f().getBytes(Charsets.UTF_8));
               if (Minecraft.getMinecraft().getNetHandler() != null && this.lIIIIIIIIIlIllIIllIlIIlIl) {
                  Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C17PacketCustomPayload("REGISTER", var8));
               }
            } else if (var1.b().equals(com.cheatbreaker.client.CheatBreakerClient.N().f())) {
               com.cheatbreaker.client.network.PluginMessagePacket var7 = com.cheatbreaker.client.network.PluginMessagePacket.a(this, var1.a());
               if (com.cheatbreaker.client.CheatBreakerClient.N().j().isDebug) {
                  ChatComponentText var9 = new ChatComponentText("[Kazer] ");
                  ChatComponentText var4 = new ChatComponentText("Received: " + var7.getClass().getSimpleName());
                  var4.getChatStyle().setChatHoverEvent(new HoverEvent(Action.SHOW_TEXT, new ChatComponentText(new Gson().toJson(var7))));
                  var9.appendSibling(var4);
                  Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(var9);
               }
            }
         } else {
            for (com.cheatbreaker.client.network.stats.ApolloMessageCodec.a var3 : com.cheatbreaker.client.network.stats.ApolloMessageCodec.b(var1.b(), var1.a())) {
               this.apollo.a(var3);
            }
         }
      } catch (Exception | AssertionError var5) {
         LogManager.getLogger().debug("Ignored invalid plugin message on {}", new Object[]{var1.b(), var5});
      }
   }

   @Override
   public void a(com.cheatbreaker.client.network.serverinfo.CooldownPacket var1) {
      com.cheatbreaker.client.module.modules.render.CooldownsModule.a(var1.b(), var1.c(), var1.d());
   }

   @Override
   public void a(com.cheatbreaker.client.network.serverinfo.NotificationPacket var1) {
      com.cheatbreaker.client.CheatBreakerClient.N().k().notifications.a(var1.d(), var1.d(), var1.c());
   }

   @Override
   public void a(com.cheatbreaker.client.network.serverinfo.StaffModuleStatePacket var1) {
      for (com.cheatbreaker.client.module.Module var3 : com.cheatbreaker.client.CheatBreakerClient.N().k().staffModules) {
         if (var3.h().equals(var1.b().replaceAll("_", "").toLowerCase())) {
            var3.a(var1.c());
         }
      }
   }

   @Override
   public void a(com.cheatbreaker.client.network.serverinfo.NametagsPacket var1) {
   }

   @Override
   public void a(com.cheatbreaker.client.network.serverinfo.TeamInfoPacket var1) {
   }

   @Override
   public void a(com.cheatbreaker.client.network.serverinfo.NametagUpdatePacket var1) {
      if (var1.c() == null) {
         this.nametagsMap.remove(var1.b());
      } else {
         this.nametagsMap.put(var1.b(), Collections.unmodifiableList(new ArrayList<>(var1.c())));
      }
   }

   @Override
   public void a(com.cheatbreaker.client.network.serverinfo.HologramSpawnPacket var1) {
      com.cheatbreaker.client.util.hologram.Hologram var2 = new com.cheatbreaker.client.util.hologram.Hologram(var1.b(), var1.c(), var1.d(), var1.e());
      com.cheatbreaker.client.util.hologram.Hologram.g().add(var2);
      var2.a(var1.f().toArray(new String[0]));
   }

   @Override
   public void a(com.cheatbreaker.client.network.serverinfo.HologramUpdatePacket var1) {
      com.cheatbreaker.client.util.hologram.Hologram.g().stream().filter(var1x -> var1x.b().equals(var1.b())).forEach(var1x -> var1x.a(var1.c().toArray(new String[0])));
   }

   @Override
   public void a(com.cheatbreaker.client.network.serverinfo.HologramRemovePacket var1) {
      com.cheatbreaker.client.util.hologram.Hologram.g().removeIf(var1x -> var1x.b().equals(var1.b()));
   }

   @Override
   public void a(com.cheatbreaker.client.network.serverinfo.TitleMessagePacket var1) {
      com.cheatbreaker.client.util.renderer.TitleMessage.aInner var2 = com.cheatbreaker.client.util.renderer.TitleMessage.aInner.title;
      if (var1.b().toLowerCase().equals("subtitle")) {
         var2 = com.cheatbreaker.client.util.renderer.TitleMessage.aInner.subtitle;
      }

      com.cheatbreaker.client.CheatBreakerClient.N().C().a().add(new com.cheatbreaker.client.util.renderer.TitleMessage(var1.c(), var2, var1.d(), var1.e(), var1.f(), var1.g()));
   }

   @Override
   public void a(com.cheatbreaker.client.network.serverinfo.GameModeRulePacket var1) {
      switch (var1.b()) {
         case COMPETITIVE_GAMEMODE:
            this.competitiveGamemode = var1.e();
      }
   }

   @Override
   public void a(com.cheatbreaker.client.network.serverinfo.WorldUpdatePacket var1) {
      System.out.println("[KC] World Update: " + var1.b());
      this.world = var1.b();
   }

   @Override
   public void a(com.cheatbreaker.client.network.serverinfo.ServerSwitchPacket var1) {
      System.out.println("[KC] Retrieved " + var1.b());
   }

   @Override
   public void a(com.cheatbreaker.client.network.serverinfo.WorldBorderCreatePacket var1) {
      com.cheatbreaker.client.CheatBreakerClient.N().D().a(var1.b(), var1.c(), var1.f(), var1.g(), var1.h(), var1.i(), var1.j(), var1.e(), var1.d());
   }

   @Override
   public void a(com.cheatbreaker.client.network.serverinfo.WorldBorderUpdatePacket var1) {
      com.cheatbreaker.client.CheatBreakerClient.N().D().a(var1.b(), var1.c(), var1.d(), var1.e(), var1.f(), var1.g());
   }

   @Override
   public void a(com.cheatbreaker.client.network.serverinfo.WorldBorderRemovePacket var1) {
      com.cheatbreaker.client.CheatBreakerClient.N().D().a(var1.b());
   }

   public void a(com.cheatbreaker.client.network.PluginMessagePacket var1) {
      if (var1 != null && com.cheatbreaker.client.CheatBreakerClient.N().j().isDebug) {
         ChatComponentText var2 = new ChatComponentText(
            EnumChatFormatting.RED + "[C" + EnumChatFormatting.WHITE + "B" + EnumChatFormatting.RED + "] " + EnumChatFormatting.RESET
         );
         ChatComponentText var3 = new ChatComponentText(EnumChatFormatting.GRAY + "Sent: " + EnumChatFormatting.WHITE + var1.getClass().getSimpleName());
         var3.getChatStyle().setChatHoverEvent(new HoverEvent(Action.SHOW_TEXT, new ChatComponentText(new Gson().toJson(var1))));
         ((ChatComponentStyle)var2).appendSibling(var3);
         Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage((IChatComponent)var2);
      }

      C17PacketCustomPayload var4 = new C17PacketCustomPayload(
         com.cheatbreaker.client.CheatBreakerClient.N().f(), new PacketBuffer(Unpooled.wrappedBuffer(com.cheatbreaker.client.network.PluginMessagePacket.a(var1)))
      );
      Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue((Packet)var4);
   }

   public String c() {
      return this.world;
   }

   public Map<UUID, List<String>> d() {
      return this.nametagsMap;
   }

   public com.cheatbreaker.client.network.stats.ApolloStateManager e() {
      return this.apollo;
   }
}
