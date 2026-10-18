package com.cheatbreaker.client.gui.uielement;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public abstract class ScrollableUIElement extends com.cheatbreaker.client.gui.uielement.UIElement {
   public int lIIIIllIIlIlIllIIIlIllIlI = 0;
   public int IlIlllIIIIllIllllIllIIlIl;
   public boolean lIIlIlIllIIlIIIlIIIlllIII = false;
   protected double IllIIIIIIIlIlIllllIIllIII = 0.0;
   protected int IlllIllIlIIIIlIIlIIllIIIl = 0;
   protected int llIIlllIIIIlllIllIlIlllIl;
   private boolean hovered = false;
   private float IIIlllIIIllIllIlIIIIIIlII;

   public ScrollableUIElement(float var1, int var2, int var3, int var4, int var5) {
      super(var1);
      this.IlIlllIIIIllIllllIllIIlIl = var2;
      this.llIIlllIIIIlllIllIlIlllIl = var3;
      this.x = var2;
      this.y = var3;
      this.width = var4;
      this.height = var5;
   }

   @Override
   public void a(int var1, int var2, float var3) {
   }

   @Override
   public void a(int var1, int var2, int var3) {
   }

   public void b(int var1, int var2) {
      if (this.a(var1, var2)) {
         double var3 = Math.round(this.IllIIIIIIIlIlIllllIIllIII / 25.0);
         this.IllIIIIIIIlIlIllllIIllIII -= var3;
         if (this.IllIIIIIIIlIlIllllIIllIII != 0.0) {
            this.lIIIIllIIlIlIllIIIlIllIlI = (int)(this.lIIIIllIIlIlIllIIIlIllIlI + var3);
         }
      } else {
         this.IllIIIIIIIlIlIllllIIllIII = 0.0;
      }

      if (this.lIIlIlIllIIlIIIlIIIlllIII) {
         if (this.lIIIIllIIlIlIllIIIlIllIlI < -this.IlllIllIlIIIIlIIlIIllIIIl + this.height) {
            this.lIIIIllIIlIlIllIIIlIllIlI = -this.IlllIllIlIIIIlIIlIIllIIIl + this.height;
            this.IllIIIIIIIlIlIllllIIllIII = 0.0;
         }

         if (this.lIIIIllIIlIlIllIIIlIllIlI > 0) {
            this.lIIIIllIIlIlIllIIIlIllIlI = 0;
            this.IllIIIIIIIlIlIllllIIllIII = 0.0;
         }
      }

      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, this.lIIIIllIIlIlIllIIIlIllIlI, 0.0F);
   }

   public void c(int var1, int var2) {
      this.lIIlIlIllIIlIIIlIIIlllIII = true;
      GL11.glPopMatrix();
      boolean var3 = this.IlllIllIlIIIIlIIlIIllIIIl > this.height;
      if (this.hovered && (!Mouse.isButtonDown(0) || !this.a(var1, var2))) {
         this.hovered = false;
      }

      double var5 = this.height - 10;
      double var7 = this.IlllIllIlIIIIlIIlIIllIIIl;
      double var9 = var5 / var7 * 100.0;
      double var11 = var5 / 100.0 * var9;
      double var13 = this.lIIIIllIIlIlIllIIIlIllIlI / 100.0 * var9;
      if (var3) {
         int var16 = this.height;
         boolean var17 = var1 > (this.x + this.width - 9) * this.scale
            && var1 < (this.x + this.width - 3) * this.scale
            && var2 > (this.y + 11 - var13) * this.scale
            && var2 < (this.y + 8 + var11 - var13) * this.scale;
         boolean var15 = var1 > (this.x + this.width - 9) * this.scale
            && var1 < (this.x + this.width - 3) * this.scale
            && var2 > (this.y + 11) * this.scale
            && var2 < (this.y + 6 + var5 - 3.0) * this.scale;
         if (Mouse.isButtonDown(0) && !this.hovered && var15) {
            this.hovered = true;
         }

         if (this.hovered) {
            if (this.lIIIIllIIlIlIllIIIlIllIlI != this.IIIlllIIIllIllIlIIIIIIlII
               && this.IIIlllIIIllIllIlIIIIIIlII != var11 / 2.0
               && this.IIIlllIIIllIllIlIIIIIIlII != var11 / 2.0 + -this.IlllIllIlIIIIlIIlIIllIIIl + var16) {
               if (var2 > (this.y + 11 + var11 - var11 / 4.0 - var13) * this.scale) {
                  this.lIIIIllIIlIlIllIIIlIllIlI = (int)(this.lIIIIllIIlIlIllIIIlIllIlI - var7 / 7.0);
               } else if (var2 < (this.y + 11 + var11 / 4.0 - var13) * this.scale) {
                  this.lIIIIllIIlIlIllIIIlIllIlI = (int)(this.lIIIIllIIlIlIllIIIlIllIlI + var7 / 7.0);
               }

               this.IIIlllIIIllIllIlIIIIIIlII = this.lIIIIllIIlIlIllIIIlIllIlI;
            } else if (var2 > (this.y + 11 + var11 - var11 / 4.0 - var13) * this.scale || var2 < (this.y + 11 + var11 / 4.0 - var13) * this.scale) {
               this.IIIlllIIIllIllIlIIIIIIlII = 1.0F;
            }
         }

         if (this.lIIIIllIIlIlIllIIIlIllIlI < -this.IlllIllIlIIIIlIIlIIllIIIl + var16) {
            this.lIIIIllIIlIlIllIIIlIllIlI = -this.IlllIllIlIIIIlIIlIIllIIIl + var16;
            this.IllIIIIIIIlIlIllllIIllIII = 0.0;
         }

         if (this.lIIIIllIIlIlIllIIIlIllIlI > 0) {
            this.lIIIIllIIlIlIllIIIlIllIlI = 0;
            this.IllIIIIIIIlIlIllllIIllIII = 0.0;
         }

         com.cheatbreaker.client.gui.render.RenderUtils.a(
            (double)(this.x + this.width - 6),
            (double)(this.y + 11),
            (double)(this.x + this.width - 4),
            this.y + 6 + var5 - 3.0,
            2.0,
            var15 && !var17 ? 1862270976 : 1056964608
         );
         com.cheatbreaker.client.gui.render.RenderUtils.a(
            (double)(this.x + this.width - 7),
            this.y + 11 - var13,
            (double)(this.x + this.width - 3),
            this.y + 8 + var11 - var13,
            4.0,
            !var17 && !this.hovered ? -12418828 : -16629505
         );
      }

      if (!var3 && this.lIIIIllIIlIlIllIIIlIllIlI != 0) {
         this.lIIIIllIIlIlIllIIIlIllIlI = 0;
      }
   }

   @Override
   public void a(int var1) {
      if (var1 != 0 && this.IlllIllIlIIIIlIIlIIllIIIl >= this.height) {
         this.IllIIIIIIIlIlIllllIIllIII += var1 / 2;
      }
   }

   public abstract boolean a(com.cheatbreaker.client.module.Module var1);

   public abstract void b(com.cheatbreaker.client.module.Module var1);
}
