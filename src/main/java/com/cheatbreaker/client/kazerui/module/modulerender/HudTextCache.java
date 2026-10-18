package com.cheatbreaker.client.kazerui.module.modulerender;

import java.util.Objects;

public final class HudTextCache {
   private int first;
   private int second;
   private long revision;
   private long resources;
   private String identity;
   private String value;

   public String a(int var1, int var2, long var3, long var5, String var7) {
      return this.value != null
            && this.first == var1
            && this.second == var2
            && this.revision == var3
            && this.resources == var5
            && Objects.equals(this.identity, var7)
         ? this.value
         : null;
   }

   public void a(int var1, int var2, long var3, long var5, String var7, String var8) {
      this.first = var1;
      this.second = var2;
      this.revision = var3;
      this.resources = var5;
      this.identity = var7;
      this.value = var8;
   }
}
