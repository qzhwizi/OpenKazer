package com.cheatbreaker.client.gui.screens.buttons.checkbox;


import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class CosmeticToggleElement extends com.cheatbreaker.client.gui.uielement.UIElement {
   private final com.cheatbreaker.client.util.playerskin.PlayerSkin cosmetic;
   private final ResourceLocation checkmarkIcon = new ResourceLocation("client/icons/checkmark-32.png");

   public CosmeticToggleElement(com.cheatbreaker.client.util.playerskin.PlayerSkin var1, float var2) {
      super(var2);
      this.height = 30;
      this.cosmetic = var1;
   }

   @Override
   public void a(int var1, int var2, float var3) {
      boolean var4 = var1 > this.x && var1 < this.x + this.width && var2 > this.y && var2 < this.y + this.height;
      if (var4) {
         cn.kazer.client189.render.DrawRect.a(this.x, this.y, this.x + this.width, this.y + this.height, 788529152);
      }

      cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
      if (this.cosmetic.a().equals("cape")) {
         Minecraft.getMinecraft().getTextureManager().bindTexture(this.cosmetic.b());
         GL11.glPushMatrix();
         GL11.glTranslatef(this.x + 20, this.y + 7, 0.0F);
         GL11.glScalef(0.25F, 0.13F, 0.25F);
         com.cheatbreaker.client.gui.render.RenderUtils.a(0.0F, 0.0F, 2.0F, 7.0F, 44, 120);
         GL11.glPopMatrix();
      } else {
         com.cheatbreaker.client.gui.render.RenderUtils.a(this.cosmetic.e(), 8.0F, this.x + 20, this.y + 7);
      }

      com.cheatbreaker.client.CheatBreakerClient.N()
         .playRegular14px
         .a(this.cosmetic.a().replace("_", " ").toUpperCase(), (float)(this.x + 42), (float)(this.y + this.height / 2 - 5), -1342177281);
      if (this.cosmetic.f()) {
         cn.kazer.client189.render.GlStateBridge.a(0.0F, 0.8F, 0.0F, 0.45F);
      } else {
         cn.kazer.client189.render.GlStateBridge.a(0.0F, 0.0F, 0.0F, 0.25F);
      }

      com.cheatbreaker.client.gui.render.RenderUtils.a(this.x + 8, this.y + this.height / 2, 3.0);
   }

   @Override
   public void a(int var1, int var2, int var3) {
      boolean var4 = var1 > this.x && var1 < this.x + this.width && var2 > this.y && var2 < this.y + this.height;
      if (var4) {
         Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
         if (this.cosmetic.f()) {
            this.cosmetic.a(false);
         } else if (this.cosmetic.a().equals("cape")) {
            this.cosmetic.a(true);

            for (com.cheatbreaker.client.util.playerskin.PlayerSkin var7 : com.cheatbreaker.client.CheatBreakerClient.N().G()) {
               if (var7 != this.cosmetic && var7.a().equals("cape")) {
                  var7.a(false);
               }
            }

            this.cosmetic.a(true);
         } else {
            this.cosmetic.a(true);

            for (com.cheatbreaker.client.util.playerskin.PlayerSkin var9 : com.cheatbreaker.client.CheatBreakerClient.N().G()) {
               if (var9 != this.cosmetic && !var9.a().equals("cape")) {
                  var9.a(false);
               }
            }

            this.cosmetic.a(true);
         }
      }
   }
}
