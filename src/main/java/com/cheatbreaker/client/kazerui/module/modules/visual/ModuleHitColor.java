package com.cheatbreaker.client.kazerui.module.modules.visual;

public final class ModuleHitColor extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static ModuleHitColor instance;
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting color = this.a("hitArmorColor", 1721303040);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting armor = this.a("shouldColorArmor", true);

   public ModuleHitColor() {
      super("HIT_COLOR", false);
      instance = this;
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(this.color, this.armor);
   }

   private static boolean n() {
      return instance != null && instance.a();
   }

   private static float a(float var0, int var1) {
      return n() ? (instance.color.a(0.0F) >>> var1 & 0xFF) / 255.0F : var0;
   }

   public static float red(float var0) {
      return a(var0, 16);
   }

   public static float green(float var0) {
      return a(var0, 8);
   }

   public static float blue(float var0) {
      return a(var0, 0);
   }

   public static float alpha(float var0) {
      return a(var0, 24);
   }

   public static boolean armorEnabled() {
      return !n() || instance.armor.b();
   }

   public static boolean combineArmor(boolean var0) {
      return n() ? instance.armor.b() : var0;
   }
}
