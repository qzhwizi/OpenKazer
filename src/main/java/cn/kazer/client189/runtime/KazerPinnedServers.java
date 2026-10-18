package cn.kazer.client189.runtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.ServerListEntryNormal;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public final class KazerPinnedServers {
   private KazerPinnedServers() {
   }

   public static boolean isPinned(ServerData var0) {
      return var0 instanceof KazerPinnedServers.c;
   }

   public static void loaded(Object var0) {
      ServerList var1 = (ServerList)var0;
      List var2 = ReflectionHelper.getPrivateValue(ServerList.class, var1, "servers", "field_78858_b");
      List var3 = com.cheatbreaker.client.CheatBreakerClient.N() == null
         ? Collections.singletonList(new String[]{"Kazer Network", "kazer.cn"})
         : com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.pinnedServers;
      ArrayList var4 = new ArrayList();

      for (String[] var6 : (Iterable<String[]>) var3) {
         if (var6 != null && var6.length >= 2) {
            var2.removeIf(var1x -> ((ServerData)var1x).serverIP.equalsIgnoreCase(var6[1]));
            var4.add(new KazerPinnedServers.c(var6[0], var6[1]));
         }
      }

      var2.addAll(0, var4);
   }

   public static Object serverNbt(Object var0) {
      return isPinned((ServerData)var0) ? null : ((ServerData)var0).getNBTCompound();
   }

   public static void appendNbt(Object var0, Object var1) {
      if (var1 != null) {
         ((NBTTagList)var0).appendTag((NBTBase)var1);
      }
   }

   public static boolean blockSwap(Object var0, int var1, int var2) {
      ServerList var3 = (ServerList)var0;
      return isPinned(var3.getServerData(var1)) || isPinned(var3.getServerData(var2));
   }

   public static Object entry(Object var0, Object var1) {
      return isPinned((ServerData)var1)
         ? new KazerPinnedServers.a((GuiMultiplayer)var0, (ServerData)var1)
         : new KazerPinnedServers.b((GuiMultiplayer)var0, (ServerData)var1);
   }

   public static void selected(Object var0, int var1) {
      GuiMultiplayer var2 = (GuiMultiplayer)var0;
      if (var1 >= 0 && var1 < var2.getServerList().countServers() && isPinned(var2.getServerList().getServerData(var1))) {
         GuiButton var3 = ReflectionHelper.getPrivateValue(GuiMultiplayer.class, var2, "btnEditServer", "field_146810_r");
         GuiButton var4 = ReflectionHelper.getPrivateValue(GuiMultiplayer.class, var2, "btnDeleteServer", "field_146808_t");
         var3.enabled = false;
         var4.enabled = false;
      }
   }

   public static boolean canMove(Object var0, Object var1, int var2, boolean var3) {
      ServerList var4 = ((GuiMultiplayer)var0).getServerList();
      if (var2 >= 0 && var2 < var4.countServers() && !isPinned(var4.getServerData(var2))) {
         int var5 = var2 + (var3 ? -1 : 1);
         return var5 >= 0 && var5 < var4.countServers() && !isPinned(var4.getServerData(var5));
      } else {
         return false;
      }
   }

   private static final class a extends ServerListEntryNormal {
      private final GuiMultiplayer gui;
      private long lastClick;

      a(GuiMultiplayer var1, ServerData var2) {
         super(var1, var2);
         this.gui = var1;
      }

      public void drawEntry(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
         super.drawEntry(var1, var2, var3, var4, var5, var6, var7, var8);
         com.cheatbreaker.client.gui.render.IconRenderer.a(com.cheatbreaker.client.gui.render.IconRenderer.a.STAR, var2 - 13, var3 + 10, 9.0F, -11699);
      }

      public boolean mousePressed(int var1, int var2, int var3, int var4, int var5, int var6) {
         this.gui.selectServer(var1);
         long var7 = Minecraft.getSystemTime();
         if (var5 < 32 || var7 - this.lastClick < 250L) {
            this.gui.connectToSelected();
         }

         this.lastClick = var7;
         return var5 < 32;
      }
   }

   private static final class b extends ServerListEntryNormal {
      private final boolean micet;

      b(GuiMultiplayer var1, ServerData var2) {
         super(var1, var2);
         this.micet = com.cheatbreaker.client.gui.serverlist.ServerAddressValidator.a(var2.serverIP);
      }

      public void drawEntry(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
         super.drawEntry(var1, var2, var3, var4, var5, var6, var7, var8);
         if (this.micet) {
            com.cheatbreaker.client.gui.render.IconRenderer.a(com.cheatbreaker.client.gui.render.IconRenderer.a.STAR, var2 - 13, var3 + 10, 9.0F, -11699);
         }
      }
   }

   private static final class c extends ServerData {
      c(String var1, String var2) {
         super(var1, var2, false);
      }
   }
}
