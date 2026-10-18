package com.cheatbreaker.client.kazerui.module.modules.mechanic;

public final class FullbrightModule extends com.cheatbreaker.client.kazerui.module.LegacyToggleModule {
   private static com.cheatbreaker.client.kazerui.module.modules.mechanic.FullbrightModule instance;
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting full = this.a("fullBright", true);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting boost = this.a("brightnessBoost", 1.0F, 1.0F, 10.0F);

   public FullbrightModule() {
      super("LIGHTING", "legacy_fullbright", true);
      instance = this;
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(this.full, this.boost);
   }

   public static float a(float var0) {
      com.cheatbreaker.client.kazerui.module.modules.mechanic.FullbrightModule var1 = instance;
      return var1 == null || !var1.a() ? var0 : (var1.full.b() ? 100.0F : Math.min(100.0F, var0 + var1.boost.b() - 1.0F));
   }

   public static boolean n() {
      com.cheatbreaker.client.kazerui.module.modules.mechanic.FullbrightModule var0 = instance;
      return var0 != null && var0.a() && var0.full.b();
   }
}
