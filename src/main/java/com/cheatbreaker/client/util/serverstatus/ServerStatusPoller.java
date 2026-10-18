package com.cheatbreaker.client.util.serverstatus;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

public class ServerStatusPoller extends Thread {
   @Override
   public void run() {
      try {
         URL var1 = new URL(com.cheatbreaker.client.CheatBreakerClient.N().j().mojangStatusURL);
         URLConnection var2 = var1.openConnection();
         BufferedReader var3 = new BufferedReader(new InputStreamReader(var2.getInputStream()));
         JsonParser var4 = new JsonParser();
         JsonElement var5 = var4.parse(var3);
         JsonArray var6 = var5.getAsJsonArray();

         for (int var7 = 0; var7 < var6.size(); var7++) {
            JsonElement var8 = var6.get(var7);

            for (Entry var10 : var8.getAsJsonObject().entrySet()) {
               for (com.cheatbreaker.client.util.ServerStatusEntry var12 : com.cheatbreaker.client.CheatBreakerClient.N().statusServers) {
                  Object var13 = null;
                  if (((com.cheatbreaker.client.util.ServerStatusEntry.a)var13).b().equalsIgnoreCase((String)var10.getKey())
                     && (var13 = com.cheatbreaker.client.util.ServerStatusEntry.a.a(((JsonElement)var10.getValue()).getAsString())) != null) {
                     if (var13 != var12.a()
                        && var12.a() != com.cheatbreaker.client.util.ServerStatusEntry.a.UNKNOWN
                        && var12.a() != com.cheatbreaker.client.util.ServerStatusEntry.a.BUSY
                        && (var13 == com.cheatbreaker.client.util.ServerStatusEntry.a.DOWN || var13 == com.cheatbreaker.client.util.ServerStatusEntry.a.UP)) {
                        EnumChatFormatting var15 = var13 == com.cheatbreaker.client.util.ServerStatusEntry.a.UP ? EnumChatFormatting.GREEN : EnumChatFormatting.RED;
                        String var14 = var13 == com.cheatbreaker.client.util.ServerStatusEntry.a.UP ? "online" : "offline";
                        if (Minecraft.getMinecraft().theWorld != null) {
                           com.cheatbreaker.client.CheatBreakerClient.N()
                              .k()
                              .notifications
                              .a("info", "Minecraft " + var12.b().toLowerCase() + " server is now " + var14 + ".", 7500L);
                           System.out.println("Minecraft servers are now " + var15 + var14 + ".");
                        }
                     }

                     var12.a((com.cheatbreaker.client.util.ServerStatusEntry.a)var13);
                  }
               }
            }
         }

         var3.close();
      } catch (Exception var17) {
         var17.printStackTrace();
      }
   }
}
