package com.cheatbreaker.client.kazerui.module.modules.visual;

public final class LightOverlayCache {
   public static final int CAPACITY = 4096;
   public static final int TICK_BUDGET = 128;
   private final int[][] xs = new int[2][4096];
   private final int[][] ys = new int[2][4096];
   private final int[][] zs = new int[2][4096];
   private final int[][] lights = new int[2][4096];
   private Object world;
   private int front;
   private int count;
   private int pending;
   private int index;
   private int cx;
   private int cy;
   private int cz;
   private int radius;
   private boolean sky;
   private boolean scanning;
   public int checkedLastTick;

   public void a() {
      this.world = null;
      this.count = this.pending = this.index = 0;
      this.scanning = false;
   }

   public int b() {
      return this.count;
   }

   public int a(int var1) {
      return this.xs[this.front][var1];
   }

   public int b(int var1) {
      return this.ys[this.front][var1];
   }

   public int c(int var1) {
      return this.zs[this.front][var1];
   }

   public int d(int var1) {
      return this.lights[this.front][var1];
   }

   public void a(Object var1, int var2, int var3, int var4, int var5, boolean var6, com.cheatbreaker.client.kazerui.module.modules.visual.LightOverlayCache.a var7) {
      this.checkedLastTick = 0;
      if (var1 == null) {
         this.a();
      } else {
         var5 = Math.max(1, Math.min(16, var5));
         boolean var8 = this.world != var1
            || this.radius != var5
            || this.sky != var6
            || Math.abs(this.cx - var2) > Math.max(1, var5 / 2)
            || Math.abs(this.cz - var4) > Math.max(1, var5 / 2)
            || Math.abs(this.cy - var3) > 2;
         if (var8) {
            if (this.world != var1 || this.radius != var5 || this.sky != var6) {
               this.count = 0;
            }

            this.world = var1;
            this.cx = var2;
            this.cy = var3;
            this.cz = var4;
            this.radius = var5;
            this.sky = var6;
            this.index = this.pending = 0;
            this.scanning = true;
         }

         if (!this.scanning) {
            this.index = this.pending = 0;
            this.scanning = true;
         }

         int var9 = this.radius * 2 + 1;
         int var10 = var9 * var9;
         int var11 = var10 * 9;
         int var12 = 1 - this.front;

         for (int var13 = 0; var13 < 128 && this.index < var11; this.index++) {
            int var14 = this.cx - this.radius + this.index % var9;
            int var15 = this.cz - this.radius + this.index / var9 % var9;
            int var16 = this.cy - 4 + this.index / var10;
            this.checkedLastTick++;
            if (var16 >= 1 && var16 <= 254) {
               int var17 = var7.light(var14, var16, var15, this.sky);
               if (var17 >= 0 && this.pending < 4096) {
                  this.xs[var12][this.pending] = var14;
                  this.ys[var12][this.pending] = var16;
                  this.zs[var12][this.pending] = var15;
                  this.lights[var12][this.pending++] = Math.min(15, var17);
               }
            }

            var13++;
         }

         if (this.index >= var11) {
            this.front = var12;
            this.count = this.pending;
            this.scanning = false;
         }
      }
   }

   public interface a {
      int light(int var1, int var2, int var3, boolean var4);
   }
}
