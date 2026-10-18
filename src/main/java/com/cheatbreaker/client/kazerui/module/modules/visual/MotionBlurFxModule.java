package com.cheatbreaker.client.kazerui.module.modules.visual;

public final class MotionBlurFxModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static com.cheatbreaker.client.kazerui.module.modules.visual.MotionBlurFxModule instance;
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting value = this.a("value", 5, 1, 10);
   private final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.visual.MotionBlurFxModule.a> type = this.a(
      "type", com.cheatbreaker.client.kazerui.module.modules.visual.MotionBlurFxModule.a.V3
   );
   private final com.cheatbreaker.client.kazerui.module.render.MotionBlurShader shader = new com.cheatbreaker.client.kazerui.module.render.MotionBlurShader(
      "motion_blur", new String[]{"vec3 Phosphor"}, "PrevSampler"
   );
   private Object world;
   private com.cheatbreaker.client.kazerui.module.modules.visual.MotionBlurFxModule.a lastType;
   private int lastValue;

   public MotionBlurFxModule() {
      super("MOTION_BLUR", false);
      instance = this;
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(this.type, this.value);
   }

   public static float a(com.cheatbreaker.client.kazerui.module.modules.visual.MotionBlurFxModule.a var0, int var1) {
      var1 = Math.max(1, Math.min(10, var1));
      float var2 = var1 / 10.0F;
      if (var0 == com.cheatbreaker.client.kazerui.module.modules.visual.MotionBlurFxModule.a.V1) {
         var2 = 0.7F + var1 / 100.0F * 3.0F - 0.01F;
      } else if (var0 == com.cheatbreaker.client.kazerui.module.modules.visual.MotionBlurFxModule.a.V2) {
         if (var2 >= 1.0F) {
            var2 = 0.99F;
         }

         var2 = 1.0F - var2;
      }

      return var2;
   }

   public static void n() {
      com.cheatbreaker.client.kazerui.module.modules.visual.MotionBlurFxModule var0 = instance;
      if (var0 != null) {
         if (var0.a() && m().theWorld != null && !m().gameSettings.anaglyph) {
            com.cheatbreaker.client.kazerui.module.modules.visual.MotionBlurFxModule.a var1 = var0.type.j();
            int var2 = var0.value.c();
            if (var0.world != m().theWorld || var1 != var0.lastType || var2 != var0.lastValue) {
               var0.shader.b();
               var0.world = m().theWorld;
               var0.lastType = var1;
               var0.lastValue = var2;
            }

            var0.shader.a(m(), a(var1, var2), var1.id);
         } else {
            var0.q();
         }
      }
   }

   @Override
   public void a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a var1) {
      if (var1.phase == com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b.END && (!this.a() || m().theWorld == null)) {
         this.q();
      }
   }

   private void q() {
      this.shader.c();
      this.world = null;
      this.lastType = null;
   }

   @Override
   protected void f() {
      this.q();
   }

   public static void o() {
      if (instance != null) {
         instance.q();
      }
   }

   public static boolean p() {
      return instance != null && instance.shader.d();
   }

   public static enum a implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
      V1("v1", 0),
      V2("v2", 1),
      V3("v3", 2);

      final String lang;
      final int id;

      private a(String var3, int var4) {
         this.lang = var3;
         this.id = var4;
      }

      @Override
      public String a() {
         return this.lang;
      }
   }
}
