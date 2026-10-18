package com.cheatbreaker.client.gui.animation;

import java.awt.Color;

public class ColorAnimation extends com.cheatbreaker.client.gui.animation.EaseOutAnimation {
   private int IllIIIIIIIlIlIllllIIllIII;
   private int lIIIIllIIlIlIllIIIlIllIlI;
   private boolean IlllIllIlIIIIlIIlIIllIIIl;
   private Color IlIlllIIIIllIllllIllIIlIl;
   private Color llIIlllIIIIlllIllIlIlllIl;

   public ColorAnimation(long var1, int var3, int var4) {
      super(var1);
      this.IllIIIIIIIlIlIllllIIllIII = var3;
      this.lIIIIllIIlIlIllIIIlIllIlI = var4;
   }

   public ColorAnimation(int var1, int var2) {
      this(175L, var1, var2);
   }

   public Color b(boolean var1) {
      Color var2 = new Color(var1 ? this.lIIIIllIIlIlIllIIIlIllIlI : this.IllIIIIIIIlIlIllllIIllIII, true);
      if (var1 && !this.IlllIllIlIIIIlIIlIIllIIIl) {
         this.IlllIllIlIIIIlIIlIIllIIIl = true;
         this.IlIlllIIIIllIllllIllIIlIl = new Color(this.IllIIIIIIIlIlIllllIIllIII, true);
         this.llIIlllIIIIlllIllIlIlllIl = new Color(this.lIIIIllIIlIlIllIIIlIllIlI, true);
         this.b();
      } else if (this.IlllIllIlIIIIlIIlIIllIIIl && !var1) {
         this.IlllIllIlIIIIlIIlIIllIIIl = false;
         this.IlIlllIIIIllIllllIllIIlIl = new Color(this.lIIIIllIIlIlIllIIIlIllIlI, true);
         this.llIIlllIIIIlllIllIlIlllIl = new Color(this.IllIIIIIIIlIlIllllIIllIII, true);
         this.b();
      }

      if (this.g()) {
         float var3 = super.h();
         int var4 = (int)Math.abs(var3 * this.llIIlllIIIIlllIllIlIlllIl.getRed() + (1.0F - var3) * this.IlIlllIIIIllIllllIllIIlIl.getRed());
         int var5 = (int)Math.abs(var3 * this.llIIlllIIIIlllIllIlIlllIl.getGreen() + (1.0F - var3) * this.IlIlllIIIIllIllllIllIIlIl.getGreen());
         int var6 = (int)Math.abs(var3 * this.llIIlllIIIIlllIllIlIlllIl.getBlue() + (1.0F - var3) * this.IlIlllIIIIllIllllIllIIlIl.getBlue());
         int var7 = (int)Math.abs(var3 * this.llIIlllIIIIlllIllIlIlllIl.getAlpha() + (1.0F - var3) * this.IlIlllIIIIllIllllIllIIlIl.getAlpha());
         var2 = new Color(var4, var5, var6, var7);
      }

      return var2;
   }

   public void b(int var1) {
      this.IllIIIIIIIlIlIllllIIllIII = var1;
   }

   public void c(int var1) {
      this.lIIIIllIIlIlIllIIIlIllIlI = var1;
   }
}
