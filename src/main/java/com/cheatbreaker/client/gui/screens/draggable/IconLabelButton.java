package com.cheatbreaker.client.gui.screens.draggable;

import net.minecraft.util.ResourceLocation;

public class IconLabelButton extends com.cheatbreaker.client.gui.screens.GuiElement {
   private ResourceLocation IIIllIllIlIlllllllIlIlIII;
   private String IllIIIIIIIlIlIllllIIllIII;
   private boolean lIIIIllIIlIlIllIIIlIllIlI;
   private final com.cheatbreaker.client.gui.animation.ColorAnimation IlllIllIlIIIIlIIlIIllIIIl;
   private final com.cheatbreaker.client.gui.animation.ColorAnimation IlIlllIIIIllIllllIllIIlIl;
   private final com.cheatbreaker.client.gui.animation.ColorAnimation llIIlllIIIIlllIllIlIlllIl;
   private float lIIlIlIllIIlIIIlIIIlllIII = 4.0F;

   public IconLabelButton(ResourceLocation var1) {
      this.IIIllIllIlIlllllllIlIlIII = var1;
      this.IlllIllIlIIIIlIIlIIllIIIl = new com.cheatbreaker.client.gui.animation.ColorAnimation(1342177279, -1353670564);
      this.IlIlllIIIIllIllllIllIIlIl = new com.cheatbreaker.client.gui.animation.ColorAnimation(444958085, 1063565678);
      this.llIIlllIIIIlllIllIlIlllIl = new com.cheatbreaker.client.gui.animation.ColorAnimation(444958085, 1062577506);
   }

   public IconLabelButton(float var1, ResourceLocation var2) {
      this.IIIllIllIlIlllllllIlIlIII = var2;
      this.lIIlIlIllIIlIIIlIIIlllIII = var1;
      this.IlllIllIlIIIIlIIlIIllIIIl = new com.cheatbreaker.client.gui.animation.ColorAnimation(1342177279, -1353670564);
      this.IlIlllIIIIllIllllIllIIlIl = new com.cheatbreaker.client.gui.animation.ColorAnimation(444958085, 1063565678);
      this.llIIlllIIIIlllIllIlIlllIl = new com.cheatbreaker.client.gui.animation.ColorAnimation(444958085, 1062577506);
   }

   public IconLabelButton(String var1) {
      this.IllIIIIIIIlIlIllllIIllIII = var1;
      this.lIIIIllIIlIlIllIIIlIllIlI = true;
      this.IlllIllIlIIIIlIIlIIllIIIl = new com.cheatbreaker.client.gui.animation.ColorAnimation(1342177279, -1353670564);
      this.IlIlllIIIIllIllllIllIIlIl = new com.cheatbreaker.client.gui.animation.ColorAnimation(444958085, 1063565678);
      this.llIIlllIIIIlllIllIlIlllIl = new com.cheatbreaker.client.gui.animation.ColorAnimation(444958085, 1062577506);
   }

   @Override
   protected void b(float var1, float var2, boolean var3) {
      boolean var4 = var3 && this.a(var1, var2);
      com.cheatbreaker.client.gui.render.RenderUtils.a(
         this.x,
         this.y,
         this.x + this.width,
         this.y + this.height,
         this.IlllIllIlIIIIlIIlIIllIIIl.b(var4).getRGB(),
         this.IlIlllIIIIllIllllIllIIlIl.b(var4).getRGB(),
         this.llIIlllIIIIlllIllIlIlllIl.b(var4).getRGB()
      );
      if (this.lIIIIllIIlIlIllIIIlIllIlI) {
         float var10002 = this.x + this.width / 2.0F;
         float var10003 = this.y + 2.0F;
         com.cheatbreaker.client.CheatBreakerClient.N().robotoRegular13px.a(this.IllIIIIIIIlIlIllllIIllIII, var10002, var10003, -1);
      } else {
         cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 0.8F);
         com.cheatbreaker.client.gui.render.RenderUtils.a(
            this.IIIllIllIlIlllllllIlIlIII,
            this.lIIlIlIllIIlIIIlIIIlllIII,
            this.x + this.width / 2.0F - this.lIIlIlIllIIlIIIlIIIlllIII,
            this.y + this.height / 2.0F - this.lIIlIlIllIIlIIIlIIIlllIII
         );
      }
   }

   public float h() {
      return 22 + com.cheatbreaker.client.CheatBreakerClient.N().robotoRegular13px.a(this.IllIIIIIIIlIlIllllIIllIII) + 6;
   }

   @Override
   public boolean a(float var1, float var2, int var3, boolean var4) {
      return false;
   }

   public void a(ResourceLocation var1) {
      this.IIIllIllIlIlllllllIlIlIII = var1;
   }

   public void a(String var1) {
      this.IllIIIIIIIlIlIllllIIllIII = var1;
   }
}
