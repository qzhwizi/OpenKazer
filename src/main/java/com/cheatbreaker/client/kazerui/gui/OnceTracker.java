package com.cheatbreaker.client.kazerui.gui;

import java.util.WeakHashMap;

public final class OnceTracker {
   private static final WeakHashMap<Object, Boolean> SENT = new WeakHashMap<>();

   private OnceTracker() {
   }

   public static void a(Object var0) {
      SENT.remove(var0);
   }

   public static boolean b(Object var0) {
      return SENT.put(var0, Boolean.TRUE) == null;
   }
}
