package com.cheatbreaker.client.kazerui.module.modules.visual;

public final class ColorSaturationModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static com.cheatbreaker.client.kazerui.module.modules.visual.ColorSaturationModule instance;
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting hue = this.a("colorSaturationHue", 0.0F, 0.0F, 10.0F);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting saturation = this.a("colorSaturationSaturation", 5.0F, 0.0F, 10.0F);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting brightness = this.a("colorSaturationBrightness", 5.0F, 0.0F, 10.0F);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting contrast = this.a("colorSaturationContrast", 5.0F, 0.0F, 10.0F);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting grayscale = this.a("grayscale", false);
   private final com.cheatbreaker.client.kazerui.module.modules.visual.ColorSaturationPass pass = new com.cheatbreaker.client.kazerui.module.modules.visual.ColorSaturationPass();
   private long epoch = -1L;

   public ColorSaturationModule() {
      super("COLOR_SATURATION", false);
      instance = this;
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(this.hue, this.saturation, this.brightness, this.contrast, this.grayscale);
   }

   @Override
   protected void f() {
      this.pass.a();
   }

   public static void n() {
      com.cheatbreaker.client.kazerui.module.modules.visual.ColorSaturationModule var0 = instance;
      if (var0 != null && var0.a() && m().theWorld != null) {
         long var1 = com.cheatbreaker.client.kazerui.RenderEpoch.a();
         if (var0.epoch != var1) {
            var0.pass.a();
            var0.epoch = var1;
         }

         var0.pass
            .a(m(), var0.hue.b() / 10.0F, var0.grayscale.b() ? 0.0F : var0.saturation.b() / 10.0F, var0.brightness.b() / 10.0F, var0.contrast.b() / 10.0F);
      }
   }
}
