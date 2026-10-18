package com.cheatbreaker.client.network.stats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class ApolloStateManager {
   private final Map<UUID, List<String>> nametags = new LinkedHashMap<>();
   private final List<com.cheatbreaker.client.util.renderer.TitleMessage> titles = new ArrayList<>();
   private final Map<UUID, List<com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry>> npcCosmetics = new LinkedHashMap<>();
   private boolean cosmeticsEnabled = true;
   private final Map<UUID, List<String>> nametagsView = Collections.unmodifiableMap(this.nametags);
   private final List<com.cheatbreaker.client.util.renderer.TitleMessage> titlesView = Collections.unmodifiableList(this.titles);
   private boolean nametagsEnabled = true;
   private boolean titlesEnabled = true;
   private boolean clearTitlesOnSwitch = false;

   public List<com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry> a(UUID var1) {
      return this.npcCosmetics.get(var1);
   }

   public Map<UUID, List<String>> a() {
      return this.nametagsView;
   }

   public List<com.cheatbreaker.client.util.renderer.TitleMessage> b() {
      return this.titlesView;
   }

   public void c() {
      this.nametags.clear();
      this.titles.clear();
      this.nametagsEnabled = this.titlesEnabled = true;
      this.clearTitlesOnSwitch = false;
      this.npcCosmetics.clear();
      this.cosmeticsEnabled = true;
   }

   public void d() {
      this.nametags.clear();
      this.npcCosmetics.clear();
      if (this.clearTitlesOnSwitch) {
         this.titles.clear();
      }
   }

   public void a(long var1) {
      this.titles.removeIf(var2 -> var2.a(var1));
   }

   public void a(com.cheatbreaker.client.network.stats.ApolloMessageCodec.a var1) {
      switch (var1.kind) {
         case NPC_EQUIP:
            if (this.cosmeticsEnabled) {
               LinkedHashMap var7 = new LinkedHashMap();
               List var10 = this.npcCosmetics.get(var1.player);
               if (var10 != null) {
                  for (com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var14 : (Iterable<com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry>) var10) {
                     var7.put(var14.b(), var14);
                  }
               }

               if (var1.copyLocalCosmetics) {
                  for (com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var15 : com.cheatbreaker.client.kazerui.concurrent.EquippedCosmetics.a()) {
                     var7.put(var15.b(), var15);
                  }
               }

               for (Integer var16 : var1.cosmeticIds) {
                  com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var6 = com.cheatbreaker.client.kazerui.concurrent.CosmeticCatalog.a(var16);
                  if (var6 != null && var6.a() && (var7.containsKey(var6.b()) || var7.size() < 8)) {
                     var7.put(var6.b(), var6);
                  }
               }

               if (this.npcCosmetics.size() >= 256 && !this.npcCosmetics.containsKey(var1.player)) {
                  this.npcCosmetics.remove(this.npcCosmetics.keySet().iterator().next());
               }

               this.npcCosmetics.put(var1.player, Collections.unmodifiableList(new ArrayList<>(var7.values())));
            }
            break;
         case NPC_UNEQUIP:
            List var2 = this.npcCosmetics.get(var1.player);
            if (var2 != null) {
               ArrayList var9 = new ArrayList();

               for (com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var5 : (Iterable<com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry>) var2) {
                  if (!var1.cosmeticIds.contains(var5.id)) {
                     var9.add(var5);
                  }
               }

               this.npcCosmetics.put(var1.player, Collections.unmodifiableList(var9));
            }
            break;
         case NPC_RESET:
            this.npcCosmetics.remove(var1.player);
            break;
         case NAMETAG:
            if (this.nametagsEnabled) {
               ArrayList var8 = new ArrayList<>(var1.lines);
               if (this.nametags.size() >= 4096 && !this.nametags.containsKey(var1.player)) {
                  this.nametags.remove(this.nametags.keySet().iterator().next());
               }

               this.nametags.put(var1.player, Collections.unmodifiableList(var8));
            }
            break;
         case RESET_NAMETAG:
            this.nametags.remove(var1.player);
            break;
         case RESET_NAMETAGS:
            this.nametags.clear();
            break;
         case TITLE:
            if (this.titlesEnabled) {
               com.cheatbreaker.client.util.renderer.TitleMessage.aInner var3 = var1.titleType == 1
                  ? com.cheatbreaker.client.util.renderer.TitleMessage.aInner.title
                  : com.cheatbreaker.client.util.renderer.TitleMessage.aInner.subtitle;
               this.titles.removeIf(var1x -> var1x.c() == var3);
               this.titles
                  .add(
                     com.cheatbreaker.client.util.renderer.TitleMessage.a(
                        var1.text, var3, var1.scale, var1.display, var1.fadeIn, var1.fadeOut, var1.interpolationScale, var1.interpolationRate
                     )
                  );
            }
            break;
         case RESET_TITLES:
            this.titles.clear();
            break;
         case CONFIG:
            if (var1.module.equals("cosmetic")) {
               this.cosmeticsEnabled = var1.enabled;
               if (!var1.enabled) {
                  this.npcCosmetics.clear();
               }
            }

            if (var1.module.equals("nametag")) {
               this.nametagsEnabled = var1.enabled;
               if (!var1.enabled) {
                  this.nametags.clear();
               }
            }

            if (var1.module.equals("title")) {
               this.titlesEnabled = var1.enabled;
               this.clearTitlesOnSwitch = var1.clearOnSwitch;
               if (!var1.enabled) {
                  this.titles.clear();
               }
            }
      }
   }
}
