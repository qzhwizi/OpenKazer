package com.cheatbreaker.client.module.modules;

public final class VisibilityState {
   private boolean hidden;

   public void a() {
      this.hidden = !this.hidden;
   }

   public boolean b() {
      return this.hidden;
   }

   public void c() {
      this.hidden = false;
   }

   public boolean a(boolean var1) {
      return !this.hidden || var1;
   }
}
