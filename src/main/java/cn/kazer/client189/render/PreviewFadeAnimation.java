package cn.kazer.client189.render;

public final class PreviewFadeAnimation {
   public static final long FADE_DURATION_MILLIS = 170L;
   private long worldGuiStartedAt = -1L;
   private long previewPhaseStartedAt = -1L;
   private long previewPositionAtPhaseStart;
   private boolean previewActive;
   private boolean previewHeld;

   public float a(long var1) {
      if (this.worldGuiStartedAt < 0L) {
         this.worldGuiStartedAt = var1;
      }

      return g(var1 - this.worldGuiStartedAt);
   }

   public void a() {
      this.worldGuiStartedAt = -1L;
   }

   public void b(long var1) {
      this.previewPositionAtPhaseStart = this.f(var1);
      this.previewPhaseStartedAt = var1;
      this.previewActive = true;
      this.previewHeld = true;
   }

   public void c(long var1) {
      this.previewPositionAtPhaseStart = this.f(var1);
      this.previewPhaseStartedAt = var1;
      this.previewHeld = false;
      this.previewActive = this.previewPositionAtPhaseStart > 0L;
   }

   public boolean d(long var1) {
      if (this.previewActive && !this.previewHeld && this.f(var1) <= 0L) {
         this.previewActive = false;
      }

      return this.previewActive;
   }

   public float e(long var1) {
      return this.d(var1) ? g(this.f(var1)) : 0.0F;
   }

   public void b() {
      this.previewPhaseStartedAt = -1L;
      this.previewPositionAtPhaseStart = 0L;
      this.previewActive = false;
      this.previewHeld = false;
   }

   private long f(long var1) {
      if (this.previewActive && this.previewPhaseStartedAt >= 0L) {
         long var3 = Math.max(0L, var1 - this.previewPhaseStartedAt);
         return this.previewHeld ? Math.min(170L, this.previewPositionAtPhaseStart + var3) : Math.max(0L, this.previewPositionAtPhaseStart - var3);
      } else {
         return 0L;
      }
   }

   private static float g(long var0) {
      float var2 = Math.max(0.0F, Math.min(1.0F, (float)var0 / 170.0F));
      return 1.0F - (1.0F - var2) * (1.0F - var2);
   }
}
