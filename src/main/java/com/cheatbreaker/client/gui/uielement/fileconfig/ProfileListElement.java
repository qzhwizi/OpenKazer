package com.cheatbreaker.client.gui.uielement.fileconfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

public class ProfileListElement extends com.cheatbreaker.client.gui.uielement.ScrollableUIElement {
   private final int IIIlllIIIllIllIlIIIIIIlII;
   public final List<com.cheatbreaker.client.gui.uielement.fileconfig.ProfileRowElement> lIIIIlIIllIIlIIlIIIlIIllI;
   private final ResourceLocation plusIcon = new ResourceLocation("client/icons/plus-64.png");

   public ProfileListElement(float var1, int var2, int var3, int var4, int var5) {
      super(var1, var2, var3, var4, var5);
      this.IIIlllIIIllIllIlIIIIIIlII = -12418828;
      this.lIIIIlIIllIIlIIlIIIlIIllI = new ArrayList<>();
      this.d();
   }

   @Override
   public void a(int var1, int var2, float var3) {
      com.cheatbreaker.client.gui.render.RenderUtils.a((double)this.x, (double)this.y, (double)(this.x + this.width), (double)(this.y + this.height + 2), 8.0, -657931);
      this.b(var1, var2);
      this.IlllIllIlIIIIlIIlIIllIIIl = 15;

      for (int var5 = 0; var5 < this.lIIIIlIIllIIlIIlIIIlIIllI.size(); var5++) {
         Object var4 = this.lIIIIlIIllIIlIIlIIIlIIllI.get(var5);
         ((com.cheatbreaker.client.gui.uielement.UIElement)var4).a(this.x + 4, this.y + 4 + var5 * 18, this.width - 12, 18);
         ((com.cheatbreaker.client.gui.uielement.fileconfig.ProfileRowElement)var4).yOffset = this.lIIIIllIIlIlIllIIIlIllIlI;
         ((com.cheatbreaker.client.gui.uielement.fileconfig.ProfileRowElement)var4).a(var1, var2, var3);
         this.IlllIllIlIIIIlIIlIIllIIIl = this.IlllIllIlIIIIlIIlIIllIIIl + ((com.cheatbreaker.client.gui.uielement.UIElement)var4).c();
      }

      boolean var7 = var1 > (this.x + this.width - 92) * this.scale
         && var1 < (this.x + this.width - 6) * this.scale
         && var2 > (this.y + this.IlllIllIlIIIIlIIlIIllIIIl - 10 + this.lIIIIllIIlIlIllIIIlIllIlI) * this.scale
         && var2 < (this.y + this.IlllIllIlIIIIlIIlIIllIIIl + 3 + this.lIIIIllIIlIlIllIIIlIllIlI) * this.scale;
      cn.kazer.client189.render.GlStateBridge.a(var7 ? 0.0F : 0.25F, var7 ? 0.8F : 0.25F, var7 ? 0.0F : 0.25F, 0.65F);
      com.cheatbreaker.client.gui.render.RenderUtils.a(this.plusIcon, 3.5F, this.x + this.width - 15, this.y + this.IlllIllIlIIIIlIIlIIllIIIl - 6.5F);
      String var6 = (var7 ? "(COPIES CURRENT PROFILE) " : "") + "ADD NEW PROFILE";
      com.cheatbreaker.client.CheatBreakerClient.N()
         .ubuntuMedium16px
         .a(
            var6,
            (float)(this.x + this.width - 17 - com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.a(var6)),
            this.y + this.IlllIllIlIIIIlIIlIIllIIIl - 7.5F,
            var7 ? 2130738944 : 2130706432
         );
      this.IlllIllIlIIIIlIIlIIllIIIl += 10;
      this.c(var1, var2);
   }

   @Override
   public void a(int var1, int var2, int var3) {
      for (com.cheatbreaker.client.gui.uielement.fileconfig.ProfileRowElement var5 : this.lIIIIlIIllIIlIIlIIIlIIllI) {
         if (var5.a(var1, var2)) {
            var5.a(var1, var2, var3);
            return;
         }
      }

      boolean var6 = var1 > (this.x + this.width - 92) * this.scale
         && var1 < (this.x + this.width - 6) * this.scale
         && var2 > (this.y + this.IlllIllIlIIIIlIIlIIllIIIl - 20 + this.lIIIIllIIlIlIllIIIlIllIlI) * this.scale
         && var2 < (this.y + this.IlllIllIlIIIIlIIlIIllIIIl - 7 + this.lIIIIllIIlIlIllIIIlIllIlI) * this.scale;
      if (var6) {
         Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
         Minecraft.getMinecraft()
            .displayGuiScreen(new com.cheatbreaker.client.gui.hudeditor.ProfileEditScreen(com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance, this, this.IIIlllIIIllIllIlIIIIIIlII, this.scale));
      }
   }

   @Override
   public boolean a(com.cheatbreaker.client.module.Module var1) {
      return true;
   }

   @Override
   public void b(com.cheatbreaker.client.module.Module var1) {
   }

   public void d() {
      new Thread(() -> {
         this.lIIIIlIIllIIlIIlIIIlIIllI.clear();
         File var1 = com.cheatbreaker.client.settings.ConfigManager.profilesDir;
         if (var1.exists()) {
            for (File var5 : var1.listFiles()) {
               if (var5.getName().endsWith(".cfg")) {
                  com.cheatbreaker.client.settings.Profile var6 = null;

                  for (com.cheatbreaker.client.settings.Profile var8 : com.cheatbreaker.client.CheatBreakerClient.N().profiles) {
                     if (var5.getName().equals(var8.c() + ".cfg")) {
                        var6 = var8;
                     }
                  }

                  if (var6 == null) {
                     com.cheatbreaker.client.CheatBreakerClient.N().profiles.add(new com.cheatbreaker.client.settings.Profile(var5.getName().replace(".cfg", ""), false));
                  }
               }
            }
         }

         for (com.cheatbreaker.client.settings.Profile var10 : com.cheatbreaker.client.CheatBreakerClient.N().profiles) {
            this.lIIIIlIIllIIlIIlIIIlIIllI.add(new com.cheatbreaker.client.gui.uielement.fileconfig.ProfileRowElement(this, this.IIIlllIIIllIllIlIIIIIIlII, var10, this.scale));
         }

         this.lIIIIlIIllIIlIIlIIIlIIllI.sort((var0, var1x) -> {
            if (var0.profile.c().equalsIgnoreCase("default")) {
               return 0;
            } else {
               return var0.profile.a() < var1x.profile.a() ? -1 : 1;
            }
         });
      }).start();
   }
}
