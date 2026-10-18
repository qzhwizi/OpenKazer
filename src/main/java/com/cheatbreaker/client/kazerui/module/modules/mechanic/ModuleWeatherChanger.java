package com.cheatbreaker.client.kazerui.module.modules.mechanic;

public final class ModuleWeatherChanger extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static ModuleWeatherChanger instance;
   private final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<ModuleWeatherChanger.a> weatherMode = this.a("weatherMode", ModuleWeatherChanger.a.CLEAR);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting rainStrength = this.a("rainStrength", 1.0F, 0.0F, 1.0F);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting weatherSounds = this.a("weatherSounds", true);

   public ModuleWeatherChanger() {
      super("WEATHER_CHANGER", false);
      instance = this;
      this.n();
   }

   private void n() {
      for (com.cheatbreaker.client.kazerui.module.settings.AbstractSetting var2 : this.g()) {
         String var3 = var2.key;
         switch (var3) {
            case "weatherMode":
               var2.a(() -> "Weather Mode");
               break;
            case "rainStrength":
               var2.a(() -> "Precipitation Strength");
               break;
            case "weatherSounds":
               var2.a(() -> "Weather Sounds");
         }
      }
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(this.weatherMode);
      var1.a(this.rainStrength).a(() -> this.weatherMode.j() == ModuleWeatherChanger.a.CLEAR || this.weatherMode.j() == ModuleWeatherChanger.a.NATURAL);
      var1.a(this.weatherSounds);
   }

   private static ModuleWeatherChanger.a o() {
      return instance != null && instance.a() ? instance.weatherMode.j() : ModuleWeatherChanger.a.NATURAL;
   }

   public static float strength(float var0) {
      ModuleWeatherChanger.a var1 = o();
      return var1 == ModuleWeatherChanger.a.NATURAL ? var0 : (var1 == ModuleWeatherChanger.a.CLEAR ? 0.0F : instance.rainStrength.b());
   }

   public static boolean precipitation(boolean var0) {
      ModuleWeatherChanger.a var1 = o();
      return var1 == ModuleWeatherChanger.a.NATURAL ? var0 : var1 != ModuleWeatherChanger.a.CLEAR;
   }

   public static float temperature(float var0) {
      ModuleWeatherChanger.a var1 = o();
      return var1 == ModuleWeatherChanger.a.SNOW ? 0.0F : (var1 == ModuleWeatherChanger.a.RAIN ? 0.5F : var0);
   }

   public static boolean sounds() {
      return instance == null || !instance.a() || instance.weatherSounds.b();
   }

   public static float thunder(float var0) {
      return o() == ModuleWeatherChanger.a.NATURAL ? var0 : 0.0F;
   }

   public static boolean muteSound(String var0) {
      return instance != null
         && instance.a()
         && (!instance.weatherSounds.b() || o() == ModuleWeatherChanger.a.CLEAR)
         && var0 != null
         && var0.startsWith("ambient.weather.");
   }

   public static enum a implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
      NATURAL,
      CLEAR,
      RAIN,
      SNOW;

      @Override
      public String a() {
         return null;
      }
   }
}
