package com.cheatbreaker.client.gui.uielement.settings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

public class BooleanSettingElement extends com.cheatbreaker.client.gui.uielement.UIElement {
   private com.cheatbreaker.client.settings.Setting setting;
   private ResourceLocation IllIIIIIIIlIlIllllIIllIII = new ResourceLocation("client/icons/left.png");
   private ResourceLocation lIIIIllIIlIlIllIIIlIllIlI = new ResourceLocation("client/icons/right.png");
   private int IlllIllIlIIIIlIIlIIllIIIl = 0;
   private float IlIlllIIIIllIllllIllIIlIl = 0.0F;
   private String displayString;

   public BooleanSettingElement(com.cheatbreaker.client.settings.Setting var1, float var2) {
      super(var2);
      this.setting = var1;
      this.height = 12;
   }

   @Override
   public void a(int var1, int var2, float var3) {
      int var4 = this.x + this.width - 40;
      boolean var5 = var1 >= var4 * this.scale
         && var1 < (var4 + 30) * this.scale
         && var2 >= (this.y + this.yOffset) * this.scale
         && var2 < (this.y + this.yOffset + 10) * this.scale;
      com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.a(this.setting.g(), (float)(this.x + 10), (float)(this.y + 2), var5 ? -1182993 : -4801096);
      boolean var6 = (Boolean)this.setting.d();
      int var7 = var6 ? -13193870 : -4903339;
      if (var5) {
         var7 = var6 ? -12400254 : -3196830;
      }

      com.cheatbreaker.client.gui.render.RenderUtils.a((double)var4, (double)(this.y + 1), (double)(var4 + 30), (double)(this.y + 12), 2.0, -8880775);
      com.cheatbreaker.client.gui.render.RenderUtils.a((double)(var4 + 1), (double)(this.y + 2), (double)(var4 + 29), (double)(this.y + 11), 2.0, -12235705);
      int var8 = var4 + (var6 ? 10 : 1);
      com.cheatbreaker.client.gui.render.RenderUtils.a((double)var8, (double)(this.y + 2), (double)(var8 + 19), (double)(this.y + 11), 2.0, var7);
      String var10001 = var6 ? "ON" : "OFF";
      float var10002 = var8;
      float var10003 = this.y + 2;
      com.cheatbreaker.client.CheatBreakerClient.N().robotoBold14px.a(var10001, var10002, var10003, 19.0F, 9.0F, -1);
   }

   @Override
   public void a(int var1, int var2, int var3) {
      int var4 = this.x + this.width - 40;
      boolean var5 = var1 >= var4 * this.scale
         && var1 < (var4 + 30) * this.scale
         && var2 >= (this.y + this.yOffset) * this.scale
         && var2 < (this.y + this.yOffset + 10) * this.scale;
      if (var5) {
         Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
         this.setting.a(!(Boolean)this.setting.d());
         if (this.setting == com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.keyStrokes.replaceNamesWithArrows) {
            com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.keyStrokes.a();
         } else if (this.setting == com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.enableTeamView
            && !(Boolean)com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.enableTeamView.d()) {
            com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.enableTeamView.a(false);
         }
      }
   }

   @Override
   public com.cheatbreaker.client.settings.Setting b() {
      return this.setting;
   }
}
