package com.cheatbreaker.client.kazerui.module.modules.hud;

import com.cheatbreaker.client.kazerui.module.render.TitleAccess;
import net.minecraft.client.renderer.GlStateManager;

public final class ModuleTitles extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static ModuleTitles instance;
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting preview = this.a("showInHudEditor", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showTitle = this.a("showTitle", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showSubtitle = this.a("showSubtitle", true);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting titleScale = this.a("titleScale", 4.0F, 1.0F, 6.0F);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting subtitleScale = this.a("subtitleScale", 2.0F, 1.0F, 4.0F);
   private final com.cheatbreaker.client.kazerui.module.settings.StringSetting sampleTitle = this.a(new com.cheatbreaker.client.kazerui.module.settings.StringSetting("title", "Title"));
   private final com.cheatbreaker.client.kazerui.module.settings.StringSetting sampleSubtitle = this.a(new com.cheatbreaker.client.kazerui.module.settings.StringSetting("subtitle", "Subtitle"));

   public ModuleTitles() {
      super("TITLES", true);
      instance = this;
      this.a(new ModuleTitles.a());
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(this.preview, var1x -> var1x.a(this.sampleTitle, this.sampleSubtitle));
      var1.a(this.showTitle, this.showSubtitle, this.titleScale, this.subtitleScale);
   }

   public static int vanillaTimer(int var0) {
      return instance != null && instance.a() ? 0 : var0;
   }

   public static boolean b(boolean var0) {
      return instance == null || !instance.a() || (var0 ? instance.showTitle.b() : instance.showSubtitle.b());
   }

   public static float c(boolean var0) {
      return instance == null || !instance.a() ? 1.0F : (var0 ? instance.titleScale.b() / 4.0F : instance.subtitleScale.b() / 2.0F);
   }

   private final class a extends com.cheatbreaker.client.kazerui.module.modulerender.HudElement {
      private String title = "";
      private String subtitle = "";
      private int timer;
      private long timerAt;
      private float titleWidth;
      private float subWidth;
      private float titleHeight;
      private float subHeight;
      private int alpha;

      a() {
         super(ModuleTitles.this, 0.0F, 0.0F, com.cheatbreaker.client.kazerui.hud.HudAnchor.MIDDLE_CENTER, 0.5F, 1.5F);
      }

      @Override
      public boolean l() {
         return ModuleTitles.this.preview.b();
      }

      @Override
      public boolean a(boolean var1) {
         if (var1) {
            if (!ModuleTitles.this.preview.b()) {
               return false;
            }

            this.title = ModuleTitles.this.sampleTitle.j();
            this.subtitle = ModuleTitles.this.sampleSubtitle.j();
            this.alpha = 255;
         } else {
            if (!(ModuleTitles.m().ingameGUI instanceof TitleAccess)) {
               return false;
            }

            TitleAccess var2 = (TitleAccess)ModuleTitles.m().ingameGUI;
            int var3 = var2.kazerTitleTime();
            if (var3 <= 0) {
               return false;
            }

            if (this.timer != var3) {
               this.timer = var3;
               this.timerAt = System.nanoTime();
            }

            float var4 = var3 - Math.min(1.0F, (float)(System.nanoTime() - this.timerAt) / 5.0E7F);
            int var5 = var2.kazerFadeIn();
            int var6 = var2.kazerStay();
            int var7 = var2.kazerFadeOut();
            this.alpha = 255;
            if (var3 > var7 + var6 && var5 > 0) {
               this.alpha = (int)((var5 + var6 + var7 - var4) * 255.0F / var5);
            }

            if (var3 <= var7 && var7 > 0) {
               this.alpha = (int)(var4 * 255.0F / var7);
            }

            this.alpha = Math.max(0, Math.min(255, this.alpha));
            this.title = var2.kazerTitle();
            this.subtitle = var2.kazerSubtitle();
         }

         if (this.title == null) {
            this.title = "";
         }

         if (this.subtitle == null) {
            this.subtitle = "";
         }

         this.titleWidth = ModuleTitles.this.showTitle.b()
            ? (com.cheatbreaker.client.kazerui.module.modulerender.HudTextUtils.a(this.title) + 1) * ModuleTitles.this.titleScale.b()
            : 0.0F;
         this.subWidth = ModuleTitles.this.showSubtitle.b()
            ? (com.cheatbreaker.client.kazerui.module.modulerender.HudTextUtils.a(this.subtitle) + 1) * ModuleTitles.this.subtitleScale.b()
            : 0.0F;
         this.titleHeight = ModuleTitles.this.showTitle.b() && !this.title.isEmpty()
            ? (com.cheatbreaker.client.kazerui.module.modulerender.HudTextUtils.a() + 1) * ModuleTitles.this.titleScale.b()
            : 0.0F;
         this.subHeight = ModuleTitles.this.showSubtitle.b() && !this.subtitle.isEmpty()
            ? (com.cheatbreaker.client.kazerui.module.modulerender.HudTextUtils.a() + 1) * ModuleTitles.this.subtitleScale.b()
            : 0.0F;
         this.a(Math.max(this.titleWidth, this.subWidth), this.titleHeight + this.subHeight + (this.titleHeight > 0.0F && this.subHeight > 0.0F ? 8 : 0));
         return this.f() > 0.0F && this.g() > 0.0F && this.alpha > 3;
      }

      private void a(String var1, float var2, float var3) {
         GlStateManager.pushMatrix();

         try {
            GlStateManager.translate(this.f() / 2.0F, var3, 0.0F);
            GlStateManager.scale(var2, var2, 1.0F);
            com.cheatbreaker.client.kazerui.module.modulerender.HudTextUtils.a(
               var1, -(com.cheatbreaker.client.kazerui.module.modulerender.HudTextUtils.a(var1) + 1) / 2.0F, 0.0F, this.alpha << 24 | 16777215, true
            );
         } finally {
            GlStateManager.popMatrix();
         }
      }

      @Override
      public void b(boolean var1) {
         if (this.titleHeight > 0.0F) {
            this.a(this.title, ModuleTitles.this.titleScale.b(), 0.0F);
         }

         if (this.subHeight > 0.0F) {
            this.a(this.subtitle, ModuleTitles.this.subtitleScale.b(), this.titleHeight + (this.titleHeight > 0.0F ? 8 : 0));
         }
      }
   }
}
