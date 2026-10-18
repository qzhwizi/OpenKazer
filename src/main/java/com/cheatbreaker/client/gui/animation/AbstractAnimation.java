package com.cheatbreaker.client.gui.animation;

public abstract class AbstractAnimation {
   protected long lIIIIlIIllIIlIIlIIIlIIllI;
   protected long lIIIIIIIIIlIllIIllIlIIlIl;
   protected long IlllIIIlIlllIllIlIIlllIlI;
   protected boolean IIIIllIlIIIllIlllIlllllIl = true;
   protected float IIIIllIIllIIIIllIllIIIlIl;
   protected long IlIlIIIlllIIIlIlllIlIllIl;
   protected final float IIIllIllIlIlllllllIlIlIII;
   private boolean IllIIIIIIIlIlIllllIIllIII;
   private int lIIIIllIIlIlIllIIIlIllIlI = 1;
   private int IlllIllIlIIIIlIIlIIllIIIl = 1;
   private long IlIlllIIIIllIllllIllIIlIl;
   private boolean llIIlllIIIIlllIllIlIlllIl;

   public AbstractAnimation(long var1, float var3) {
      this.IlllIIIlIlllIllIlIIlllIlI = var1;
      this.IIIllIllIlIlllllllIlIlIII = var3;
   }

   protected abstract float a();

   public void b() {
      this.lIIIIlIIllIIlIIlIIIlIIllI = System.currentTimeMillis();
      this.IIIIllIlIIIllIlllIlllllIl = true;
      this.IlIlllIIIIllIllllIllIIlIl = 0L;
   }

   public void a(float var1) {
      this.lIIIIlIIllIIlIIlIIIlIIllI = System.currentTimeMillis();
      this.IlIlllIIIIllIllllIllIIlIl = var1 == 0.0F ? 0L : (long)((float)this.IlllIIIlIlllIllIlIIlllIlI * (1.0F - var1));
      this.IIIIllIlIIIllIlllIlllllIl = true;
   }

   public void c() {
      this.IllIIIIIIIlIlIllllIIllIII = true;
   }

   public boolean d() {
      return this.lIIIIlIIllIIlIIlIIIlIIllI != 0L;
   }

   public boolean e() {
      return this.l() <= 0L && this.IIIIllIlIIIllIlllIlllllIl;
   }

   public void f() {
      this.lIIIIlIIllIIlIIlIIIlIIllI = 0L;
      this.lIIIIllIIlIlIllIIIlIllIlI = 1;
   }

   public float a(boolean var1) {
      if (var1 && !this.llIIlllIIIIlllIllIlIlllIl) {
         this.llIIlllIIIIlllIllIlIlllIl = true;
         this.a(this.u());
      } else if (this.llIIlllIIIIlllIllIlIlllIl && !var1) {
         this.llIIlllIIIIlllIllIlIlllIl = false;
         this.a(this.u());
      }

      if (this.lIIIIlIIllIIlIIlIIIlIIllI == 0L) {
         return 0.0F;
      } else {
         float var2 = this.u();
         return this.llIIlllIIIIlllIllIlIlllIl ? var2 : 1.0F - var2;
      }
   }

   public boolean g() {
      return this.lIIIIlIIllIIlIIlIIIlIIllI != 0L && this.l() > 0L;
   }

   private float u() {
      if (this.lIIIIlIIllIIlIIlIIIlIIllI == 0L) {
         return 0.0F;
      } else {
         return this.l() <= 0L ? 1.0F : this.a();
      }
   }

   public float h() {
      if (this.lIIIIlIIllIIlIIlIIIlIIllI == 0L) {
         return 0.0F;
      } else if (!this.e()) {
         return this.IIIIllIlIIIllIlllIlllllIl ? this.a() : this.IIIIllIIllIIIIllIllIIIlIl;
      } else {
         if (this.IllIIIIIIIlIlIllllIIllIII || this.IlllIllIlIIIIlIIlIIllIIIl >= 1 && this.lIIIIllIIlIlIllIIIlIllIlI < this.IlllIllIlIIIIlIIlIIllIIIl) {
            this.b();
            this.lIIIIllIIlIlIllIIIlIllIlI++;
         }

         return this.IIIllIllIlIlllllllIlIlIII;
      }
   }

   public void i() {
      this.IIIIllIlIIIllIlllIlllllIl = false;
      this.IIIIllIIllIIIIllIllIIIlIl = this.a();
      this.IlIlIIIlllIIIlIlllIlIllIl = System.currentTimeMillis() - this.lIIIIlIIllIIlIIlIIIlIIllI;
   }

   public void j() {
      this.lIIIIlIIllIIlIIlIIIlIIllI = System.currentTimeMillis() - this.IlIlIIIlllIIIlIlllIlIllIl;
      this.IIIIllIlIIIllIlllIlllllIl = true;
   }

   public long k() {
      long var1 = this.IIIIllIlIIIllIlllIlllllIl
         ? this.l()
         : System.currentTimeMillis() - this.IlIlIIIlllIIIlIlllIlIllIl + this.IlllIIIlIlllIllIlIIlllIlI - System.currentTimeMillis();
      return Math.min(this.IlllIIIlIlllIllIlIIlllIlI, Math.max(0L, var1));
   }

   protected long l() {
      return this.lIIIIlIIllIIlIIlIIIlIIllI + this.IlllIIIlIlllIllIlIIlllIlI - this.IlIlllIIIIllIllllIllIIlIl - System.currentTimeMillis();
   }

   public long m() {
      return this.lIIIIlIIllIIlIIlIIIlIIllI;
   }

   public long n() {
      return this.lIIIIIIIIIlIllIIllIlIIlIl;
   }

   public long o() {
      return this.IlllIIIlIlllIllIlIIlllIlI;
   }

   public boolean p() {
      return this.IIIIllIlIIIllIlllIlllllIl;
   }

   public float q() {
      return this.IIIIllIIllIIIIllIllIIIlIl;
   }

   public long r() {
      return this.IlIlIIIlllIIIlIlllIlIllIl;
   }

   public float s() {
      return this.IIIllIllIlIlllllllIlIlIII;
   }

   public void a(int var1) {
      this.IlllIllIlIIIIlIIlIIllIIIl = var1;
   }

   public boolean t() {
      return this.llIIlllIIIIlllIllIlIlllIl;
   }
}
