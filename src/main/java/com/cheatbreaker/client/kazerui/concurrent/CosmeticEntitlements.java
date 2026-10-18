package com.cheatbreaker.client.kazerui.concurrent;

import java.util.Locale;
import net.minecraft.util.Session;

public final class CosmeticEntitlements {
   private static volatile String subject = "";
   private static volatile int grants = 1;
   public static volatile long revision;
   private static Session session;
   private static String localId = "";

   public static int a() {
      return 31;
   }

   public static boolean a(com.cheatbreaker.client.kazerui.concurrent.CosmeticCategory var0) {
      return (a() & var0.bit) != 0;
   }

   public static boolean a(com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var0) {
      return a(com.cheatbreaker.client.kazerui.concurrent.CosmeticCategory.a(var0.type));
   }

   public static void a(String var0, int var1) {
      String var2 = var0.replace("-", "").toLowerCase(Locale.ROOT);
      int var3 = var1 & 31 | 1;
      if (!var2.equals(subject) || var3 != grants) {
         subject = var2;
         grants = var3;
         revision++;
      }
   }

   private CosmeticEntitlements() {
   }
}
