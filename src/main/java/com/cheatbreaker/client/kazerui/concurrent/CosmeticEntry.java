package com.cheatbreaker.client.kazerui.concurrent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class CosmeticEntry {
   public final int id;
   public final String name;
   public final String resource;
   public final com.cheatbreaker.client.kazerui.concurrent.CosmeticType type;
   public final boolean animated;
   public final boolean geckolib;
   public final boolean scalable;
   public final List<String> colors;
   public final List<String> tags;
   public final String released;

   CosmeticEntry(
      int var1,
      String var2,
      String var3,
      com.cheatbreaker.client.kazerui.concurrent.CosmeticType var4,
      boolean var5,
      boolean var6,
      String var7,
      String var8,
      boolean var9,
      String var10
   ) {
      this.id = var1;
      this.name = var2.replace("Lunar", "Kazer").replace("LUNAR", "KAZER");
      this.type = var4;
      this.animated = var5;
      this.geckolib = var6;
      this.resource = var3.startsWith("lunar:") ? var3.substring(6) : var3;
      this.colors = a(var7);
      this.tags = a(var8);
      this.scalable = var9;
      this.released = var10;
   }

   private static List<String> a(String var0) {
      return var0.isEmpty() ? Collections.emptyList() : Arrays.asList(var0.split("\\|"));
   }

   public boolean a() {
      if (this.geckolib) {
         return com.cheatbreaker.client.kazerui.concurrent.rendercache.GeckoCosmeticRenderer.a(this);
      } else {
         return this.type != com.cheatbreaker.client.kazerui.concurrent.CosmeticType.CLOAK && this.type != com.cheatbreaker.client.kazerui.concurrent.CosmeticType.WINGS
            ? com.cheatbreaker.client.kazerui.concurrent.rendercache.MeshCosmeticRenderer.b(this)
            : true;
      }
   }

   public com.cheatbreaker.client.kazerui.concurrent.CosmeticType b() {
      return this.type;
   }

   @Override
   public String toString() {
      return this.id + ":" + this.name;
   }
}
