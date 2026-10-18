package com.cheatbreaker.client.module.modules.shader;

public class MotionBlurModule extends com.cheatbreaker.client.module.Module {
   private final com.cheatbreaker.client.settings.Setting mode;
   private final com.cheatbreaker.client.settings.Setting amount;
   private final com.cheatbreaker.client.module.modules.shader.AccumulationState accumulation = new com.cheatbreaker.client.module.modules.shader.AccumulationState();
   private final com.cheatbreaker.client.module.modules.shader.MotionBlurPipeline pipeline = new com.cheatbreaker.client.module.modules.shader.MotionBlurPipeline();
   private final com.cheatbreaker.client.module.modules.shader.MonkeyBlurRenderer monkeyRenderer = new com.cheatbreaker.client.module.modules.shader.MonkeyBlurRenderer();
   private com.cheatbreaker.client.module.modules.shader.MotionBlurMode lastRenderedMode;

   public MotionBlurModule() {
      super("Motion Blur");
      this.c(false);
      this.g(false);
      this.isEditable = false;
      this.a("Motion Blur", 1.1F);
      this.mode = new com.cheatbreaker.client.settings.Setting(this, "Mode").a(MotionBlurMode.DEFAULT.a()).a(MotionBlurMode.b()).displayLabel("Type");
      this.amount = new com.cheatbreaker.client.settings.Setting(this, "Amount").a(60).a(0, 100).displayLabel("Value").a(var1 -> this.accumulation.a());
      this.a(com.cheatbreaker.client.event.event.WorldLoadEvent.class, var1 -> this.accumulation.b());
   }

   @Override
   public void a(boolean var1) {
      if (var1 != this.g()) {
         this.accumulation.a();
      }

      super.a(var1);
   }

   public static com.cheatbreaker.client.module.modules.shader.MotionBlurModule a() {
      com.cheatbreaker.client.module.modules.shader.MotionBlurModule var0 = com.cheatbreaker.client.CheatBreakerClient.N().k().motionBlurModule;
      return var0 != null && var0.g() && com.cheatbreaker.client.module.modules.shader.BlurAmountUtil.b(var0.z()) ? var0 : null;
   }

   public int z() {
      Object var1 = this.amount.d();
      return com.cheatbreaker.client.module.modules.shader.BlurAmountUtil.a(var1 instanceof Number ? ((Number)var1).intValue() : 50);
   }

   public com.cheatbreaker.client.module.modules.shader.MotionBlurMode A() {
      Object var1 = this.mode.d();
      return MotionBlurMode.a(var1 instanceof String ? (String)var1 : null);
   }

   private com.cheatbreaker.client.module.modules.shader.MotionBlurMode C() {
      com.cheatbreaker.client.module.modules.shader.MotionBlurMode var1 = this.A();
      if (var1 != this.lastRenderedMode) {
         if (this.lastRenderedMode != null) {
            this.accumulation.c();
         }

         this.lastRenderedMode = var1;
      }

      return var1;
   }

   public void B() {
      if (this.g() && com.cheatbreaker.client.module.modules.shader.BlurAmountUtil.b(this.z())) {
         MotionBlurMode var1 = this.C();
         if (!this.minecraft.gameSettings.anaglyph && var1 != MotionBlurMode.MONKEY) {
            this.pipeline.a(this.minecraft, this.accumulation, var1, this.z());
         }
      }
   }

   private boolean D() {
      return this.g() && com.cheatbreaker.client.module.modules.shader.BlurAmountUtil.b(this.z()) && this.C() == MotionBlurMode.MONKEY && !this.minecraft.gameSettings.anaglyph;
   }

   public void c(float var1) {
      if (this.D()) {
         this.monkeyRenderer.a(this.minecraft);
      }
   }

   public void d(float var1) {
      if (this.D()) {
         this.monkeyRenderer.a(this.minecraft, var1);
      }
   }

   public void e(float var1) {
      if (this.D()) {
         this.monkeyRenderer.a(this.minecraft, var1, this.z(), this.accumulation);
      }
   }
}
