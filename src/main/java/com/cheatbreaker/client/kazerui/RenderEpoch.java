package com.cheatbreaker.client.kazerui;

public final class RenderEpoch {
   private static long generation;

   public static long a() {
      return generation;
   }

   public static void b() {
      generation++;
   }

   private RenderEpoch() {
   }
}
