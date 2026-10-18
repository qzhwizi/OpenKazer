package cn.kazer.client189.runtime;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.ProgressManager.ProgressBar;

public final class StartupSplashHooks {
   private static final IdentityHashMap<ProgressBar, StartupSplashHooks.a> adapters = new IdentityHashMap<>();

   public static boolean takeOverFromFml() {
      return com.cheatbreaker.client.kazerui.splash.KazerSplash.a();
   }

   public static boolean onVanillaScreen() {
      return com.cheatbreaker.client.kazerui.splash.KazerSplash.c();
   }

   public static void onProgress() {
      if (!com.cheatbreaker.client.kazerui.splash.KazerSplash.b()) {
         ArrayList var0 = new ArrayList();
         IdentityHashMap var1 = new IdentityHashMap();
         Iterator var2 = ProgressManager.barIterator();

         while (var2.hasNext()) {
            ProgressBar var3 = (ProgressBar)var2.next();
            StartupSplashHooks.a var4 = adapters.get(var3);
            if (var4 == null) {
               var4 = new StartupSplashHooks.a(var3);
            }

            var1.put(var3, var4);
            var0.add(var4);
         }

         adapters.clear();
         adapters.putAll(var1);
         com.cheatbreaker.client.kazerui.splash.LoadingProgress.a(var0);
         com.cheatbreaker.client.kazerui.splash.KazerSplash.d();
      }
   }

   public static void finish() {
      com.cheatbreaker.client.kazerui.splash.KazerSplash.e();
      adapters.clear();
   }

   private StartupSplashHooks() {
   }

   private static final class a implements com.cheatbreaker.client.kazerui.splash.LoadingProgress.b {
      final ProgressBar bar;

      a(ProgressBar var1) {
         this.bar = var1;
      }

      @Override
      public int a() {
         return this.bar.getSteps();
      }

      @Override
      public int b() {
         return this.bar.getStep();
      }

      @Override
      public String c() {
         return this.bar.getMessage();
      }

      @Override
      public String d() {
         return this.bar.getTitle();
      }
   }
}
