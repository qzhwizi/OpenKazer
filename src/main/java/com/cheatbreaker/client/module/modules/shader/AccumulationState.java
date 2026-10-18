package com.cheatbreaker.client.module.modules.shader;

public final class AccumulationState {
   private int framebufferWidth = -1;
   private int framebufferHeight = -1;
   private boolean resetPending = true;

   public void a() {
      this.resetPending = true;
   }

   public void b() {
      this.resetPending = true;
   }

   public void c() {
      this.resetPending = true;
   }

   public boolean a(int var1, int var2) {
      if (var1 == this.framebufferWidth && var2 == this.framebufferHeight) {
         return false;
      } else {
         this.framebufferWidth = var1;
         this.framebufferHeight = var2;
         this.resetPending = true;
         return true;
      }
   }

   public boolean d() {
      return this.resetPending;
   }

   public void e() {
      this.resetPending = false;
   }
}
