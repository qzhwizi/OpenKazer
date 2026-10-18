package com.cheatbreaker.client.kazerui.module.modules.visual;

final class SkinBuildRateLimiter {
   private boolean started;
   private long start;
   private int used;

   boolean a(long var1) {
      if (!this.started || var1 - this.start >= 50000000L) {
         this.started = true;
         this.start = var1;
         this.used = 0;
      }

      if (this.used >= 2) {
         return false;
      } else {
         this.used++;
         return true;
      }
   }

   void a() {
      this.started = false;
      this.used = 0;
   }
}
