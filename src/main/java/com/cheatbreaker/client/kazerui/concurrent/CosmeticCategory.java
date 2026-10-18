package com.cheatbreaker.client.kazerui.concurrent;

public enum CosmeticCategory {
   CLOAKS("Cloaks", 1),
   HEADWEAR("Headwear", 2),
   PETS("Pets", 4),
   BODYWEAR("Bodywear", 8),
   AURAS("Auras", 16);

   public final String label;
   public final int bit;
   private static final com.cheatbreaker.client.kazerui.concurrent.CosmeticCategory[] VISIBLE = new com.cheatbreaker.client.kazerui.concurrent.CosmeticCategory[]{CLOAKS, HEADWEAR, PETS};

   private CosmeticCategory(String var3, int var4) {
      this.label = var3;
      this.bit = var4;
   }

   public static com.cheatbreaker.client.kazerui.concurrent.CosmeticCategory[] a() {
      return (com.cheatbreaker.client.kazerui.concurrent.CosmeticCategory[])VISIBLE.clone();
   }

   public static com.cheatbreaker.client.kazerui.concurrent.CosmeticCategory a(com.cheatbreaker.client.kazerui.concurrent.CosmeticType var0) {
      if (var0 == com.cheatbreaker.client.kazerui.concurrent.CosmeticType.CLOAK) {
         return CLOAKS;
      } else if (com.cheatbreaker.client.kazerui.concurrent.CosmeticType.HEADWEAR.a(var0)) {
         return HEADWEAR;
      } else if (var0 == com.cheatbreaker.client.kazerui.concurrent.CosmeticType.PET || var0 == com.cheatbreaker.client.kazerui.concurrent.CosmeticType.COMPANION) {
         return PETS;
      } else {
         return var0 == com.cheatbreaker.client.kazerui.concurrent.CosmeticType.AURAS ? AURAS : BODYWEAR;
      }
   }
}
