package com.cheatbreaker.client.kazerui.gui.components;

public final class ScrollPhysics {
   public float offset;
   public double velocity;
   public float content;
   public float height;
   public boolean dragging;

   public boolean a(int var1) {
      if (var1 != 0 && !(this.content < this.height)) {
         this.velocity += var1 / 3.0F;
         return true;
      } else {
         return false;
      }
   }

   public void a(boolean var1) {
      if (!var1 && this.velocity != 0.0) {
         this.velocity = 0.0;
      }

      double var2 = Math.round(this.velocity / 5.0);
      this.velocity -= var2;
      if (this.velocity != 0.0) {
         this.offset = (float)(this.offset - var2);
      }

      this.a();
   }

   public void a() {
      if (this.content <= this.height) {
         if (this.offset != 0.0F) {
            this.offset = 0.0F;
         }
      } else {
         if (this.offset > this.content - this.height) {
            this.offset = this.content - this.height;
            this.velocity = 0.0;
         }

         if (this.offset < 0.0F) {
            this.offset = 0.0F;
            this.velocity = 0.0;
         }
      }
   }

   public boolean b() {
      return Math.round(this.velocity / 5.0) != 0L;
   }

   public boolean c() {
      return this.content > this.height;
   }

   public float d() {
      return this.content <= 0.0F
         ? this.height
         : Math.max(Math.min(this.height * this.height / this.content, this.height), Math.min(16.0F, this.height / 2.0F));
   }

   public float e() {
      return this.content <= this.height ? 0.0F : this.offset / (this.content - this.height) * (this.height - this.d());
   }

   public void a(float var1) {
      float var2 = this.d();
      float var3 = this.height - var2;
      float var4 = var3 <= 0.0F ? 0.0F : Math.max(0.0F, Math.min(1.0F, (var1 - var2 / 2.0F) / var3));
      this.offset = var4 * (this.content - this.height);
      this.a();
   }

   public void f() {
      this.offset = 0.0F;
      this.velocity = 0.0;
      this.dragging = false;
   }
}
