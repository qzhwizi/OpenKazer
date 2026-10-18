package com.cheatbreaker.client.gui.screens;

public final class StartupProgressScreen extends com.cheatbreaker.client.gui.ScaledGuiScreen {
   private final int capacity;
   private int step;

   public StartupProgressScreen(int var1) {
      this.capacity = Math.max(1, var1);
   }

   public void a(String var1) {
      com.cheatbreaker.client.kazerui.splash.KazerSplash.a(var1);
      this.step++;
   }

   @Override
   public void a(float var1, float var2) {
      com.cheatbreaker.client.kazerui.splash.KazerSplash.c();
   }

   @Override
   protected void a(float var1, float var2, int var3) {
   }

   @Override
   public void b(float var1, float var2, int var3) {
   }
}
