package com.cheatbreaker.client.kazerui.module.modules.visual;

public final class ModuleGlintColorizer extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static ModuleGlintColorizer instance;
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showGlint = this.a("showGlint", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting overrideItemGlint = this.a("overrideItemGlint", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting overrideArmorGlint = this.a("overrideArmorGlint", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting itemColor = this.a("itemColor", -8372020).b();
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting armorColor = this.a("armorColor", -8372020).b();

   public ModuleGlintColorizer() {
      super("GLINT_COLORIZER", false);
      instance = this;
      this.n();
   }

   private void n() {
      for (com.cheatbreaker.client.kazerui.module.settings.AbstractSetting var2 : this.g()) {
         String var3 = var2.key;
         switch (var3) {
            case "showGlint":
               var2.a(() -> "Show Glint");
               break;
            case "overrideItemGlint":
               var2.a(() -> "Override Item Glint");
               break;
            case "overrideArmorGlint":
               var2.a(() -> "Override Armor Glint");
               break;
            case "itemColor":
               var2.a(() -> "Item Glint Color");
               break;
            case "armorColor":
               var2.a(() -> "Armor Glint Color");
         }
      }
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(this.showGlint);
      var1.a(this.overrideItemGlint, var1x -> var1x.a(this.itemColor)).a(() -> !this.showGlint.b());
      var1.a(this.overrideArmorGlint, var1x -> var1x.a(this.armorColor)).a(() -> !this.showGlint.b());
   }

   public static boolean visible(boolean var0) {
      return var0 && (instance == null || !instance.a() || instance.showGlint.b());
   }

   public static boolean overrides(boolean var0) {
      return instance != null && instance.a() && instance.showGlint.b() && (var0 ? instance.overrideArmorGlint.b() : instance.overrideItemGlint.b());
   }

   public static int color(int var0, boolean var1) {
      return overrides(var1) ? (var1 ? instance.armorColor : instance.itemColor).g() : var0;
   }

   public static float channel(float var0, boolean var1, int var2) {
      return overrides(var1) ? (color(0, var1) >>> var2 & 0xFF) / 255.0F * 0.76F : var0;
   }

   public static int itemColor(int var0) {
      return color(var0, false);
   }

   public static float guiChannel(float var0, int var1) {
      return overrides(false) ? (color(0, false) >>> var1 & 0xFF) / 255.0F : var0;
   }
}
