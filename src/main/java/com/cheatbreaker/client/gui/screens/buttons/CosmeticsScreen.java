package com.cheatbreaker.client.gui.screens.buttons;


import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.ResourceLocation;

public class CosmeticsScreen extends com.cheatbreaker.client.gui.screens.PanoramaScreen {
   private final List<com.cheatbreaker.client.gui.screens.buttons.checkbox.CosmeticToggleElement> IIIIllIlIIIllIlllIlllllIl = new ArrayList<>();
   private ResourceLocation leftIcon = new ResourceLocation("client/icons/left.png");
   private ResourceLocation rightIcon = new ResourceLocation("client/icons/right.png");
   private final com.cheatbreaker.client.gui.screens.GradientButton backButton = new com.cheatbreaker.client.gui.screens.GradientButton("BACK");
   private int IllIIIIIIIlIlIllllIIllIII = 0;

   public CosmeticsScreen() {
      for (com.cheatbreaker.client.util.playerskin.PlayerSkin var2 : com.cheatbreaker.client.CheatBreakerClient.N().G()) {
         this.IIIIllIlIIIllIlllIlllllIl.add(new com.cheatbreaker.client.gui.screens.buttons.checkbox.CosmeticToggleElement(var2, 1.0F));
      }
   }

   @Override
   public void a(float var1, float var2) {
      super.a(var1, var2);
      cn.kazer.client189.render.DrawRect.a(this.f() / 2.0F - 80.0F, this.g() / 2.0F - 78.0F, this.f() / 2.0F + 80.0F, this.g() / 2.0F + 100.0F, 788529152);
      this.backButton.a(this.f() / 2.0F - 30.0F, this.g() / 2.0F + 105.0F, 60.0F, 12.0F);
      this.backButton.a(var1, var2, true);
      if (this.IIIIllIlIIIllIlllIlllllIl.isEmpty()) {
         com.cheatbreaker.client.CheatBreakerClient.N().playRegular16px.b("You don't own any cosmetics.", this.f() / 2.0F, this.g() / 2.0F + 4.0F, -6381922);
      } else {
         float var3 = this.g() / 2.0F - 68.0F;
         float var4 = this.g() / 2.0F + 92.0F;
         float var5 = this.f() / 2.0F + 68.0F;
         float var6 = this.f() / 2.0F + 74.0F;
         com.cheatbreaker.client.CheatBreakerClient.N()
            .playBold18px
            .b("Cosmetics (" + this.IIIIllIlIIIllIlllIlllllIl.size() + ")", this.f() / 2.0F, this.g() / 2.0F - 90.0F, -1);
         int var7 = 0;
         float var8 = 0.0F;

         for (com.cheatbreaker.client.gui.screens.buttons.checkbox.CosmeticToggleElement var10 : this.IIIIllIlIIIllIlllIlllllIl) {
            var7++;
            if (var7 - 1 >= this.IllIIIIIIIlIlIllllIIllIII * 5 && var7 - 1 < (this.IllIIIIIIIlIlIllllIIllIII + 1) * 5) {
               var10.a((int)this.f() / 2 - 76, (int)(this.g() / 2.0F - 72.0F + var8), 152, var10.c());
               var10.a((int)var1, (int)var2, 1.0F);
               var8 += var10.c();
            }
         }

         if (this.IIIIllIlIIIllIlllIlllllIl.size() > 5) {
            boolean var11 = var1 > this.f() / 2.0F - 40.0F
               && var1 < this.f() / 2.0F - 1.0F
               && var2 > this.g() / 2.0F + 80.0F
               && var2 < this.g() / 2.0F + 100.0F;
            cn.kazer.client189.render.GlStateBridge.a(0.0F, 0.0F, 0.0F, var11 ? 0.45F : 0.25F);
            com.cheatbreaker.client.gui.render.RenderUtils.a(this.leftIcon, 4.0F, this.f() / 2.0F - 10.0F, this.g() / 2.0F + 84.0F);
            boolean var12 = var1 > this.f() / 2.0F + 1.0F
               && var1 < this.f() / 2.0F + 40.0F
               && var2 > this.g() / 2.0F + 80.0F
               && var2 < this.g() / 2.0F + 100.0F;
            cn.kazer.client189.render.GlStateBridge.a(0.0F, 0.0F, 0.0F, var12 ? 0.45F : 0.25F);
            com.cheatbreaker.client.gui.render.RenderUtils.a(this.rightIcon, 4.0F, this.f() / 2.0F + 10.0F, this.g() / 2.0F + 84.0F);
         }
      }
   }

   @Override
   public void a(float var1, float var2, int var3) {
      super.a(var1, var2, var3);
      if (this.backButton.a(var1, var2)) {
         Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
         this.mc.displayGuiScreen(new GuiMainMenu());
      } else {
         if (this.IIIIllIlIIIllIlllIlllllIl.size() > 5) {
            boolean var4 = var1 > this.f() / 2.0F - 40.0F && var1 < this.f() / 2.0F - 1.0F && var2 > this.g() / 2.0F + 80.0F && var2 < this.g() / 2.0F + 100.0F;
            boolean var5 = var1 > this.f() / 2.0F + 1.0F && var1 < this.f() / 2.0F + 40.0F && var2 > this.g() / 2.0F + 80.0F && var2 < this.g() / 2.0F + 100.0F;
            if (this.IllIIIIIIIlIlIllllIIllIII > 0 && var4) {
               this.IllIIIIIIIlIlIllllIIllIII--;
               Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            } else if (var5 && this.IllIIIIIIIlIlIllllIIllIII + 1 < this.IIIIllIlIIIllIlllIlllllIl.size() / 5.0F) {
               this.IllIIIIIIIlIlIllllIIllIII++;
               Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            }
         }

         int var7 = 0;

         for (com.cheatbreaker.client.gui.screens.buttons.checkbox.CosmeticToggleElement var6 : this.IIIIllIlIIIllIlllIlllllIl) {
            var7++;
            if (var7 - 1 >= this.IllIIIIIIIlIlIllllIIllIII * 5 && var7 - 1 < (this.IllIIIIIIIlIlIllllIIllIII + 1) * 5) {
               var6.a((int)var1, (int)var2, var3);
            }
         }
      }
   }
}
