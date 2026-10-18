package com.cheatbreaker.client.network.serverinfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

public class NametagsPacket extends com.cheatbreaker.client.network.PluginMessagePacket {
   private Map<UUID, List<String>> playersMap;

   @Override
   public void a(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      var1.a(this.playersMap == null ? -1 : this.playersMap.size());
      if (this.playersMap != null) {
         for (Entry var3 : this.playersMap.entrySet()) {
            UUID var4 = (UUID)var3.getKey();
            List<String> var5 = (List<String>)var3.getValue();
            var1.a(var4);
            var1.a(var5.size());
            var5.forEach(var1::a);
         }
      }
   }

   @Override
   public void b(com.cheatbreaker.client.network.PluginMessageBuffer var1) throws IOException {
      int var2 = var1.a();
      if (var2 == -1) {
         this.playersMap = null;
      } else {
         this.playersMap = new HashMap<>();

         for (int var3 = 0; var3 < var2; var3++) {
            UUID var4 = var1.c();
            int var5 = var1.a();
            ArrayList var6 = new ArrayList();

            for (int var7 = 0; var7 < var5; var7++) {
               var6.add(var1.b());
            }

            this.playersMap.put(var4, var6);
         }
      }
   }

   @Override
   public void a(com.cheatbreaker.client.network.PacketHandler var1) {
      ((com.cheatbreaker.client.network.rules.ServerPacketHandler)var1).a(this);
   }

   public NametagsPacket(Map<UUID, List<String>> var1) {
      this.playersMap = var1;
   }

   public NametagsPacket() {
   }

   public Map<UUID, List<String>> b() {
      return this.playersMap;
   }
}
