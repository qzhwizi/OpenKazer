package com.cheatbreaker.client.gui.screens.draggable;


import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class VerticalScrollbar extends com.cheatbreaker.client.gui.screens.GuiElement {
   protected final com.cheatbreaker.client.gui.screens.GuiElement parent;
   protected double internalScrollAmount;
   protected float translateY;
   protected float scrollAmount;
   protected boolean hovered;
   protected boolean alwaysTrueApparently;
   protected float oldTranslateY;
   private float IIIlllIIIllIllIlIIIIIIlII;
   private boolean dragClick;

   public VerticalScrollbar(com.cheatbreaker.client.gui.screens.GuiElement var1) {
      this.parent = var1;
   }

   @Override
   public boolean a(float var1, float var2) {
      return var1 >= this.x && var1 <= this.x + this.width && var2 > this.y && var2 < this.y + this.height;
   }

   @Override
   public void b(float var1, float var2, boolean var3) {
      this.alwaysTrueApparently = true;
      GL11.glPopMatrix();
      boolean var5 = this.i();
      if (this.hovered && (!Mouse.isButtonDown(0) || !this.a(var1, var2) || !var3)) {
         this.hovered = false;
      }

      if (this.dragClick && !Mouse.isButtonDown(0)) {
         this.dragClick = false;
      }

      float var6 = this.height;
      float var7 = this.scrollAmount;
      float var8 = var6 / var7 * 100.0F;
      float var9 = var6 / 100.0F * var8;
      float var10 = this.translateY / 100.0F * var8;
      if (Mouse.isButtonDown(0) && this.dragClick) {
         float var4 = var2 - this.y;
         float var11 = var4 / this.height;
         this.translateY = -(this.scrollAmount * var11) + var9 / 2.0F;
      }

      if (var5) {
         float var14 = this.height;
         boolean var12 = var1 >= this.x && var1 <= this.x + this.width && var2 > this.y - var10 && var2 < this.y + var9 - var10;
         boolean var15 = var1 >= this.x && var1 <= this.x + this.width && var2 > this.y && var2 < this.y + var6 - 3.0F;
         if (Mouse.isButtonDown(0) && this.hovered && var15) {
         }

         if (this.hovered) {
            if (this.translateY != this.oldTranslateY && this.oldTranslateY != var9 / 2.0F && this.oldTranslateY != var9 / 2.0F + -this.scrollAmount + var14) {
               if (var2 > this.y + var9 - var9 / 4.0F - var10) {
                  this.translateY -= var7 / 7.0F;
               } else if (var2 < this.y + var9 / 4.0F - var10) {
                  this.translateY += var7 / 7.0F;
               }

               this.oldTranslateY = this.translateY;
            } else if (var2 > this.y + var9 - var9 / 4.0F - var10 || var2 < this.y + var9 / 4.0F - var10) {
               this.oldTranslateY = 1.0F;
            }
         }

         if (this.translateY < -this.scrollAmount + var14) {
            this.translateY = -this.scrollAmount + var14;
            this.internalScrollAmount = 0.0;
         }

         if (this.translateY > 0.0F) {
            this.translateY = 0.0F;
            this.internalScrollAmount = 0.0;
         }

         cn.kazer.client189.render.DrawRect.a(this.x, this.y, this.x + this.width, this.y + this.height, -13158601);
         cn.kazer.client189.render.DrawRect.a(this.x, this.y - var10, this.x + this.width, this.y + var9 - var10, !var12 && !this.hovered ? -4180940 : -52429);
      }

      if (!var5 && this.translateY != 0.0F) {
         this.translateY = 0.0F;
      }
   }

   public void c(float var1, float var2, boolean var3) {
      if (var3 && (this.parent == null || this.parent.a(var1, var2)) && this.internalScrollAmount != 0.0) {
         this.translateY = (float)(this.translateY + this.internalScrollAmount / 8.0);
         this.internalScrollAmount = 0.0;
      }

      if (this.alwaysTrueApparently) {
         if (this.translateY < -this.scrollAmount + this.height) {
            this.translateY = -this.scrollAmount + this.height;
            this.internalScrollAmount = 0.0;
         }

         if (this.translateY > 0.0F) {
            this.translateY = 0.0F;
            this.internalScrollAmount = 0.0;
         }
      }

      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, this.translateY, 0.0F);
   }

   public void d(float var1, float var2, boolean var3) {
      this.alwaysTrueApparently = true;
      GL11.glPopMatrix();
      boolean var5 = this.i();
      if (this.hovered && (!Mouse.isButtonDown(0) || !this.a(var1, var2) || !var3)) {
         this.hovered = false;
      }

      if (this.dragClick && !Mouse.isButtonDown(0)) {
         this.dragClick = false;
      }

      float var6 = this.height;
      float var7 = this.scrollAmount;
      float var8 = var6 / var7 * 100.0F;
      float var9 = var6 / 100.0F * var8;
      float var10 = this.translateY / 100.0F * var8;
      if (Mouse.isButtonDown(0) && this.dragClick) {
         float var4 = var2 - this.y;
         float var11 = var4 / this.height;
         this.translateY = -(this.scrollAmount - this.height / 2.0F) + this.scrollAmount * var11 + var9 / 2.0F;
      }

      if (var5) {
         float var13 = this.height;
         boolean var14 = var1 >= this.x && var1 <= this.x + this.width && var2 > this.y - var10 && var2 < this.y + var9 - var10;
         if (var1 >= this.x && var1 <= this.x + this.width && var2 > this.y && var2 < this.height + var6 - 3.0F) {
            boolean var15 = true;
         } else {
            boolean var10000 = false;
         }

         if (this.hovered) {
            if (this.translateY != this.oldTranslateY && this.oldTranslateY != var9 / 2.0F && this.oldTranslateY != var9 / 2.0F + -this.scrollAmount + var13) {
               if (var2 > this.y + this.height - var9 - var9 / 4.0F + var10) {
                  this.translateY = var7 / 7.0F;
               } else if (var2 < this.y + this.height - var9 / 4.0F + var10) {
                  this.translateY += var7 / 7.0F;
               }

               this.oldTranslateY = this.translateY;
            } else if (var2 > this.y + this.height - var9 - var9 / 4.0F + var10 || var2 < this.y + this.height - var9 / 4.0F - var10) {
               this.oldTranslateY = 1.0F;
            }
         }

         if (this.translateY < -this.scrollAmount + var13) {
            this.translateY = -this.scrollAmount + var13;
            this.internalScrollAmount = 0.0;
         }

         if (this.translateY > 0.0F) {
            this.translateY = 0.0F;
            this.internalScrollAmount = 0.0;
         }

         cn.kazer.client189.render.DrawRect.a(this.x, this.y, this.x + this.width, this.y + this.height, -13158601);
         cn.kazer.client189.render.DrawRect.a(this.x, this.y + this.height + var10, this.x + this.width, this.y + this.height - var9 + var10, !var14 && !this.hovered ? -4180940 : -52429);
      }

      if (!var5 && this.translateY != 0.0F) {
         this.translateY = 0.0F;
      }
   }

   public void e(float var1, float var2, boolean var3) {
      if (var3 && (this.parent == null || this.parent.a(var1, var2)) && this.internalScrollAmount != 0.0) {
         this.translateY = (float)(this.translateY - this.internalScrollAmount / 8.0);
         this.internalScrollAmount = 0.0;
      }

      if (this.alwaysTrueApparently) {
         if (this.translateY < -this.scrollAmount + this.height) {
            this.translateY = -this.scrollAmount + this.height;
            this.internalScrollAmount = 0.0;
         }

         if (this.translateY > 0.0F) {
            this.translateY = 0.0F;
            this.internalScrollAmount = 0.0;
         }
      }

      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, -this.translateY, 0.0F);
   }

   public float h() {
      return this.translateY;
   }

   public boolean i() {
      return this.scrollAmount > this.height;
   }

   @Override
   public boolean a(float var1, float var2, int var3, boolean var4) {
      if (this.a(var1, var2) && var4) {
         this.IIIlllIIIllIllIlIIIIIIlII = var2 - this.y;
         this.dragClick = true;
      }

      return false;
   }

   @Override
   public void c() {
      int var1 = Mouse.getEventDWheel();
      if (var1 != 0 && this.scrollAmount >= this.height) {
         this.internalScrollAmount += var1 / 1.75F;
      }
   }

   public com.cheatbreaker.client.gui.screens.GuiElement j() {
      return this.parent;
   }

   public void e(float var1) {
      this.scrollAmount = var1;
   }

   public boolean k() {
      return this.hovered;
   }

   public boolean l() {
      return this.dragClick;
   }
}
