package com.cheatbreaker.client.kazerui.concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public enum CosmeticType {
   CLOAK("cloak", null, com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a.CLOAK, com.cheatbreaker.client.kazerui.concurrent.CosmeticDisplayPose.CLOAK, "cloak-40x40"),
   HEADWEAR("headwearui", null, com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a.HAT, com.cheatbreaker.client.kazerui.concurrent.CosmeticDisplayPose.HAT, "hat-40x40"),
   HAT("hat", HEADWEAR, com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a.HAT, com.cheatbreaker.client.kazerui.concurrent.CosmeticDisplayPose.HAT, "hat-40x40"),
   BANDANNA("bandanna", HEADWEAR, com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a.HAT, com.cheatbreaker.client.kazerui.concurrent.CosmeticDisplayPose.HAT, "bandana-28x28"),
   GLASSES("glasses", HEADWEAR, com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a.HAT, com.cheatbreaker.client.kazerui.concurrent.CosmeticDisplayPose.HAT, "glasses-40x40"),
   MASK("mask", HEADWEAR, com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a.HAT, com.cheatbreaker.client.kazerui.concurrent.CosmeticDisplayPose.HAT, "mask-28x28"),
   PET("pet", null, com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a.GECKOLIB, null, "pet-40x40"),
   COMPANION("companion", null, com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a.GECKOLIB, null, "companion-40x40"),
   BODYWEAR_UI("bodywearui", null, com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a.BODYWEAR, com.cheatbreaker.client.kazerui.concurrent.CosmeticDisplayPose.BUST, "bodywear-40x40"),
   BACKPACK("backpack", BODYWEAR_UI, com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a.BODYWEAR, com.cheatbreaker.client.kazerui.concurrent.CosmeticDisplayPose.CLOAK, "backpack-40x40"),
   BODYWEAR("bodywear", BODYWEAR_UI, com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a.BODYWEAR, com.cheatbreaker.client.kazerui.concurrent.CosmeticDisplayPose.BUST, "bodywear-40x40"),
   NECKWEAR("neckwear", BODYWEAR_UI, com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a.BODYWEAR, com.cheatbreaker.client.kazerui.concurrent.CosmeticDisplayPose.BUST, "necklace-64x64"),
   WINGS("wings", BODYWEAR_UI, com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a.WINGS, com.cheatbreaker.client.kazerui.concurrent.CosmeticDisplayPose.WING, "wings-40x40"),
   BELTS("belts", BODYWEAR_UI, com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a.BODYWEAR, com.cheatbreaker.client.kazerui.concurrent.CosmeticDisplayPose.BELT, "belts-40x40"),
   SHOES("shoes", BODYWEAR_UI, com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a.BODYWEAR, com.cheatbreaker.client.kazerui.concurrent.CosmeticDisplayPose.SHOES, "shoes-40x40"),
   WRISTWEAR("wristwear", BACKPACK, com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a.GECKOLIB, null, "wristwear-40x40"),
   SHIELDS("shields", BACKPACK, com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a.GECKOLIB, null, "shields-40x40"),
   AURAS("auras", null, com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a.BODYWEAR, com.cheatbreaker.client.kazerui.concurrent.CosmeticDisplayPose.DEFAULT, "auras-40x40"),
   SUITS("suits", null, com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a.BODYWEAR, com.cheatbreaker.client.kazerui.concurrent.CosmeticDisplayPose.SUITS, "suits-40x40"),
   ITEM("items", null, com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a.GECKOLIB, com.cheatbreaker.client.kazerui.concurrent.CosmeticDisplayPose.ITEM, "item-40x40"),
   SWORD("sword", ITEM, com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a.GECKOLIB, com.cheatbreaker.client.kazerui.concurrent.CosmeticDisplayPose.ITEM, "item-40x40"),
   PICKAXE("pickaxe", ITEM, com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a.GECKOLIB, com.cheatbreaker.client.kazerui.concurrent.CosmeticDisplayPose.ITEM, "item-40x40"),
   AXE("axe", ITEM, com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a.GECKOLIB, com.cheatbreaker.client.kazerui.concurrent.CosmeticDisplayPose.ITEM, "item-40x40"),
   SHOVEL("shovel", ITEM, com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a.GECKOLIB, com.cheatbreaker.client.kazerui.concurrent.CosmeticDisplayPose.ITEM, "item-40x40"),
   HOE("hoe", ITEM, com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a.GECKOLIB, com.cheatbreaker.client.kazerui.concurrent.CosmeticDisplayPose.ITEM, "item-40x40"),
   HAND("hand", ITEM, com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a.GECKOLIB, com.cheatbreaker.client.kazerui.concurrent.CosmeticDisplayPose.ITEM, "item-40x40");

   public final String id;
   public final com.cheatbreaker.client.kazerui.concurrent.CosmeticType parent;
   public final com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a render;
   public final com.cheatbreaker.client.kazerui.concurrent.CosmeticDisplayPose display;
   public final String icon;
   private final List<com.cheatbreaker.client.kazerui.concurrent.CosmeticType> children = new ArrayList<>(0);

   private CosmeticType(
      String var3, com.cheatbreaker.client.kazerui.concurrent.CosmeticType var4, com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a var5, com.cheatbreaker.client.kazerui.concurrent.CosmeticDisplayPose var6, String var7
   ) {
      this.id = var3;
      this.parent = var4;
      this.render = var5;
      this.display = var6;
      this.icon = var7;
      if (var4 != null) {
         var4.children.add(this);
      }
   }

   public List<com.cheatbreaker.client.kazerui.concurrent.CosmeticType> a() {
      return Collections.unmodifiableList(this.children);
   }

   public String b() {
      return this.name().replace("_UI", "").replace("_", " ");
   }

   public boolean a(com.cheatbreaker.client.kazerui.concurrent.CosmeticType var1) {
      for (com.cheatbreaker.client.kazerui.concurrent.CosmeticType var2 = var1; var2 != null; var2 = var2.parent) {
         if (var2 == this) {
            return true;
         }
      }

      return false;
   }

   public static com.cheatbreaker.client.kazerui.concurrent.CosmeticType a(String var0) {
      if (var0 == null) {
         return null;
      } else if (var0.equalsIgnoreCase("dragon_wings")) {
         return WINGS;
      } else {
         String var1 = var0.toLowerCase(Locale.ROOT);

         for (com.cheatbreaker.client.kazerui.concurrent.CosmeticType var5 : values()) {
            if (var5.id.equalsIgnoreCase(var1)) {
               return var5;
            }
         }

         return null;
      }
   }

   public static List<com.cheatbreaker.client.kazerui.concurrent.CosmeticType> c() {
      ArrayList var0 = new ArrayList();

      for (com.cheatbreaker.client.kazerui.concurrent.CosmeticType var4 : values()) {
         if (var4.parent == null && var4 != COMPANION && var4 != ITEM) {
            var0.add(var4);
         }
      }

      return var0;
   }

   public static enum a {
      HAT,
      CLOAK,
      BODYWEAR,
      WINGS,
      GECKOLIB;
   }
}
