package com.cheatbreaker.client.kazerui.cps;

import java.util.ArrayDeque;
import java.util.Deque;

public final class CpsTracker {
   private static final Deque<Long> LEFT = new ArrayDeque<>();
   private static final Deque<Long> CLEAN = new ArrayDeque<>();
   private static final Deque<Long> RIGHT = new ArrayDeque<>();

   public static void a(int var0, boolean var1) {
      long var2 = System.currentTimeMillis();
      if (var0 == 0) {
         a(LEFT, var2);
         if (!var1) {
            a(CLEAN, var2);
         }
      } else if (var0 == 1) {
         a(RIGHT, var2);
      }
   }

   private static void a(Deque<Long> var0, long var1) {
      a(var0);
      if (var0.size() >= 1024) {
         var0.removeFirst();
      }

      var0.addLast(var1);
   }

   private static int a(Deque<Long> var0) {
      long var1 = System.currentTimeMillis() - 1000L;

      while (!var0.isEmpty() && var0.peekFirst() < var1) {
         var0.removeFirst();
      }

      return var0.size();
   }

   public static int a(boolean var0) {
      return a(var0 ? CLEAN : LEFT);
   }

   public static int a() {
      return a(RIGHT);
   }

   public static int a(boolean var0, boolean var1) {
      return var0 ? a(var1) : a();
   }

   public static void b() {
      RIGHT.clear();
   }

   private CpsTracker() {
   }
}
