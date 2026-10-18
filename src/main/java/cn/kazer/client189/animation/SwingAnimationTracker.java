package cn.kazer.client189.animation;

public final class SwingAnimationTracker {
   private float previous;
   private float progress;
   private int ticks;
   private boolean swinging;

   public void a() {
      this.previous = this.progress = 0.0F;
      this.ticks = 0;
      this.swinging = false;
   }

   public void a(boolean var1, int var2) {
      this.previous = this.progress;
      if (var1 && (!this.swinging || this.ticks >= var2 >> 1 || this.ticks < 0)) {
         this.swinging = true;
         this.ticks = -1;
      }

      if (this.swinging) {
         this.ticks++;
         if (this.ticks >= var2) {
            this.ticks = 0;
            this.swinging = false;
         }
      } else {
         this.ticks = 0;
      }

      this.progress = (float)this.ticks / var2;
   }

   public float a(float var1, float var2) {
      if (!this.swinging) {
         return var2;
      } else {
         float var3 = this.progress - this.previous;
         if (var3 < 0.0F) {
            var3++;
         }

         return this.previous + var3 * var1;
      }
   }

   public boolean b() {
      return this.swinging;
   }
}
