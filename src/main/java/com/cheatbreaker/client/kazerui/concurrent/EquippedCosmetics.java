package com.cheatbreaker.client.kazerui.concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public final class EquippedCosmetics {
   private static com.cheatbreaker.client.kazerui.gui.components.SettingsStore store;
   private static volatile List<com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry> equipped = Collections.emptyList();
   private static volatile String wire = "";
   private static int permissionMask = -1;

   public static void a(com.cheatbreaker.client.kazerui.gui.components.SettingsStore var0) {
      store = var0;
      permissionMask = -1;
      c();
   }

   public static List<com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry> a() {
      return equipped;
   }

   public static String b() {
      return wire;
   }

   public static void c() {
      int var0 = CosmeticEntitlements.a();
      if (permissionMask != var0 && store != null) {
         permissionMask = var0;
         ArrayList var1 = new ArrayList();

         for (com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var3 : a(store.a("cosmetics.equipped", ""))) {
            if (CosmeticEntitlements.a(var3)) {
               var1.add(var3);
            }
         }

         equipped = Collections.unmodifiableList(var1);
         wire = a(var1);
      }
   }

   public static boolean a(com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var0) {
      for (com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var2 : equipped) {
         if (var2.id == var0.id) {
            return true;
         }
      }

      return false;
   }

   public static void b(com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var0) {
      c();
      if (store != null && var0.a() && CosmeticEntitlements.a(var0)) {
         ArrayList var1 = new ArrayList();
         boolean var2 = a(var0);

         for (com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var4 : equipped) {
            if (var4.b() != var0.b()) {
               var1.add(var4);
            }
         }

         if (!var2 && var1.size() < 8) {
            var1.add(var0);
         }

         equipped = Collections.unmodifiableList(var1);
         wire = a(var1);
         store.b("cosmetics.equipped", wire);
      }
   }

   public static void d() {
      equipped = Collections.emptyList();
      wire = "";
      if (store != null) {
         store.b("cosmetics.equipped", "");
      }
   }

   public static List<com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry> a(String var0) {
      if (var0 != null && var0.length() <= 128) {
         LinkedHashMap var1 = new LinkedHashMap();

         for (String var5 : var0.split(",")) {
            try {
               if (var1.size() >= 8) {
                  break;
               }

               com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var6 = com.cheatbreaker.client.kazerui.concurrent.CosmeticCatalog.a(Integer.parseInt(var5));
               if (var6 != null && var6.a()) {
                  var1.put(var6.b(), var6);
               }
            } catch (NumberFormatException var7) {
            }
         }

         return Collections.unmodifiableList(new ArrayList<>(var1.values()));
      } else {
         return Collections.emptyList();
      }
   }

   private static String a(List<com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry> var0) {
      StringBuilder var1 = new StringBuilder();

      for (com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var3 : var0) {
         if (var1.length() > 0) {
            var1.append(',');
         }

         var1.append(var3.id);
      }

      return var1.toString();
   }

   private EquippedCosmetics() {
   }
}
