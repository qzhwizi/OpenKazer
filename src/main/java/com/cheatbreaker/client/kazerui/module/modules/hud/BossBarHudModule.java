package com.cheatbreaker.client.kazerui.module.modules.hud;

import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;

public final class BossBarHudModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting bar = this.a("renderBar", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting text = this.a("renderBossText", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting custom = this.a("customBossBar", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting color = this.a("barColor", -1);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting guiScale = this.a("useMinecraftGUIScale", true);
   private final ResourceLocation vanilla = new ResourceLocation("textures/gui/icons.png");
   private final ResourceLocation kazer = new ResourceLocation("kazerui", "textures/hud/boss-icons.png");

   public BossBarHudModule() {
      super("BOSSBAR", true);
      this.a(new BossBarHudModule.a());
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(this.bar, this.text);
      var1.a(this.custom, var1x -> var1x.a(this.color));
      var1.a(this.guiScale);
   }

   public void a(com.cheatbreaker.client.kazerui.overlay.OverlayEvents.b var1) {
      if (this.a() && var1.type == com.cheatbreaker.client.kazerui.overlay.OverlayEvents.aInner.BOSSHEALTH) {
         var1.a(true);
         if (BossStatus.statusBarTime > 0) {
            BossStatus.statusBarTime--;
         }
      }
   }

   private final class a extends com.cheatbreaker.client.kazerui.module.modulerender.HudElement {
      a() {
         super(BossBarHudModule.this, 0.0F, 1.0F, com.cheatbreaker.client.kazerui.hud.HudAnchor.TOP_CENTER, 0.5F, 1.5F);
         this.a(182.0F, 18.0F);
      }

      @Override
      public boolean a(boolean var1) {
         return var1 || BossStatus.statusBarTime > 0 && BossStatus.bossName != null;
      }

      @Override
      public void b(boolean var1) {
         ResourceLocation var2 = BossBarHudModule.this.custom.b() ? BossBarHudModule.this.kazer : BossBarHudModule.this.vanilla;
         int var3 = BossBarHudModule.this.custom.b() ? BossBarHudModule.this.color.a(0.0F) : -1;
         if (BossBarHudModule.this.bar.b()) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var2, 0.0F, 9.0F, 0.0F, 74.0F, 182.0F, 5.0F, 256.0F, 256.0F, var3);
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(
               var2, 0.0F, 9.0F, 0.0F, 79.0F, Math.min(182, (int)((var1 ? 1.0F : BossStatus.healthScale) * 183.0F)), 5.0F, 256.0F, 256.0F, var3
            );
         }

         if (BossBarHudModule.this.text.b()) {
            String var4 = var1 ? "Ender Dragon" : BossStatus.bossName;
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var4, 91.0F - com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var4) / 2.0F, 0.0F, -1, true);
         }
      }
   }
}
