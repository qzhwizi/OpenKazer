package com.cheatbreaker.client.kazerui.splash;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class LoadingProgress {
   private static List<com.cheatbreaker.client.kazerui.splash.LoadingProgress.b> bars = Collections.emptyList();
   private static final com.cheatbreaker.client.kazerui.splash.LoadingProgress.a nativeBar = new com.cheatbreaker.client.kazerui.splash.LoadingProgress.a();

   public static Iterator<com.cheatbreaker.client.kazerui.splash.LoadingProgress.b> a() {
      return bars.iterator();
   }

   public static void a(List<com.cheatbreaker.client.kazerui.splash.LoadingProgress.b> var0) {
      bars = var0;
   }

   public static void a(String var0, int var1, int var2) {
      nativeBar.message = var0;
      nativeBar.step = var1;
      nativeBar.total = Math.max(1, var2);
      bars = Collections.singletonList(nativeBar);
      com.cheatbreaker.client.kazerui.splash.KazerSplash.d();
   }

   public static void b() {
      bars = Collections.emptyList();
   }

   private LoadingProgress() {
   }

   private static final class a implements com.cheatbreaker.client.kazerui.splash.LoadingProgress.b {
      String message = "Initializing Minecraft Engine";
      int step;
      int total = 1;

      private a() {
      }

      @Override
      public int a() {
         return this.total;
      }

      @Override
      public int b() {
         return this.step;
      }

      @Override
      public String c() {
         return this.message;
      }

      @Override
      public String d() {
         return "Minecraft";
      }
   }

   public interface b {
      int a();

      int b();

      String c();

      String d();
   }
}
