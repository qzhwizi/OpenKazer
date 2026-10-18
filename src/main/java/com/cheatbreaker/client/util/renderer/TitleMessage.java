package com.cheatbreaker.client.util.renderer;

public class TitleMessage {
   private final com.cheatbreaker.client.util.renderer.TitleMessage.aInner titleEnum;
   private final String message;
   private final float scale;
   private final long displayTimeMs;
   private final long fadeInTimeMs;
   private final long fadeOutTimeMs;
   private float interpolationScale;
   private float interpolationRate;
   protected final long currentTimeMillis = System.currentTimeMillis();

   public TitleMessage(String var1, com.cheatbreaker.client.util.renderer.TitleMessage.aInner var2, float var3, long var4, long var6, long var8) {
      this.message = var1;
      this.scale = var3;
      this.titleEnum = var2;
      this.displayTimeMs = var4 == 0L ? 5000L : var4;
      this.fadeInTimeMs = var6;
      this.fadeOutTimeMs = var8;
   }

   public boolean a() {
      return System.currentTimeMillis() < this.currentTimeMillis + this.fadeInTimeMs;
   }

   public static com.cheatbreaker.client.util.renderer.TitleMessage a(
      String var0, com.cheatbreaker.client.util.renderer.TitleMessage.aInner var1, float var2, long var3, long var5, long var7, float var9, float var10
   ) {
      com.cheatbreaker.client.util.renderer.TitleMessage var11 = new com.cheatbreaker.client.util.renderer.TitleMessage(var0, var1, var2, Math.max(1L, var5 + var3 + var7), var5, var7);
      var11.interpolationScale = var9;
      var11.interpolationRate = var10;
      return var11;
   }

   public boolean a(long var1) {
      return var1 - this.currentTimeMillis >= this.displayTimeMs;
   }

   public float b(long var1) {
      long var3 = Math.max(0L, var1 - this.currentTimeMillis);
      if (var3 >= this.displayTimeMs) {
         return 0.0F;
      } else {
         float var5 = this.fadeInTimeMs > 0L ? Math.min(1.0F, (float)var3 / (float)this.fadeInTimeMs) : 1.0F;
         if (this.fadeOutTimeMs > 0L) {
            var5 = Math.min(var5, (float)(this.displayTimeMs - var3) / (float)this.fadeOutTimeMs);
         }

         return Math.max(0.0F, var5);
      }
   }

   public float c(long var1) {
      if (this.interpolationRate <= 0.0F) {
         return this.scale;
      } else {
         float var3 = (float)Math.max(0L, var1 - this.currentTimeMillis) / 50.0F * this.interpolationRate;
         return this.scale < this.interpolationScale
            ? Math.min(this.interpolationScale, this.scale + var3)
            : Math.max(this.interpolationScale, this.scale - var3);
      }
   }

   public boolean b() {
      return System.currentTimeMillis() > this.currentTimeMillis + this.displayTimeMs - this.fadeOutTimeMs;
   }

   public com.cheatbreaker.client.util.renderer.TitleMessage.aInner c() {
      return this.titleEnum;
   }

   public String d() {
      return this.message;
   }

   public float e() {
      return this.scale;
   }

   public long f() {
      return this.displayTimeMs;
   }

   public long g() {
      return this.fadeInTimeMs;
   }

   public long h() {
      return this.fadeOutTimeMs;
   }

   static com.cheatbreaker.client.util.renderer.TitleMessage.aInner a(com.cheatbreaker.client.util.renderer.TitleMessage var0) {
      return var0.titleEnum;
   }

   static float b(com.cheatbreaker.client.util.renderer.TitleMessage var0) {
      return var0.scale;
   }

   static long c(com.cheatbreaker.client.util.renderer.TitleMessage var0) {
      return var0.fadeInTimeMs;
   }

   static long d(com.cheatbreaker.client.util.renderer.TitleMessage var0) {
      return var0.displayTimeMs;
   }

   static long e(com.cheatbreaker.client.util.renderer.TitleMessage var0) {
      return var0.fadeOutTimeMs;
   }

   static String f(com.cheatbreaker.client.util.renderer.TitleMessage var0) {
      return var0.message;
   }

   public static enum aInner {
      subtitle,
      title;
   }
}
