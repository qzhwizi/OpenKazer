package com.cheatbreaker.client.module.modules;

public final class LabelValueCache {
   private final String label;
   private int lastValue;
   private int lastFlags;
   private String cached;

   public LabelValueCache(String var1) {
      this.label = var1;
   }

   public String a(int var1, boolean var2, boolean var3, boolean var4) {
      int var5 = (var2 ? 1 : 0) | (var3 ? 2 : 0) | (var4 ? 4 : 0);
      if (this.cached == null || this.lastValue != var1 || this.lastFlags != var5) {
         String var6 = var4 ? this.label : "";
         String var7 = (var3 ? var6 + " " + var1 : var1 + " " + var6).trim();
         this.cached = var2 ? var7 : "[" + var7 + "]";
         this.lastValue = var1;
         this.lastFlags = var5;
      }

      return this.cached;
   }
}
