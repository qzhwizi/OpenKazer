package cn.kazer.client189.input;

public final class ImeCandidatePositionCache {
   private final cn.kazer.client189.input.ImeCandidatePositionCache.a positioner;
   private cn.kazer.client189.input.ImeCandidatePlacement lastPosition;
   private int x;
   private int top;
   private int height;
   private int guiWidth;
   private int guiHeight;
   private int clientWidth;
   private int clientHeight;
   private boolean above;
   private boolean cachedGui;

   public ImeCandidatePositionCache(cn.kazer.client189.input.ImeCandidatePositionCache.a var1) {
      this.positioner = var1;
   }

   public void a(cn.kazer.client189.input.ImeCandidatePlacement var1) {
      if (!var1.equals(this.lastPosition) && this.positioner.a(var1)) {
         this.lastPosition = var1;
      }
   }

   public void a() {
      this.lastPosition = null;
      this.cachedGui = false;
   }

   public void a(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
      if (!this.cachedGui
         || this.x != var1
         || this.top != var2
         || this.height != var3
         || this.guiWidth != var4
         || this.guiHeight != var5
         || this.clientWidth != var6
         || this.clientHeight != var7
         || this.above != var8) {
         cn.kazer.client189.input.ImeCandidatePlacement var9 = var8
            ? cn.kazer.client189.input.ImeCandidatePlacement.b(var1, var2, var3, var4, var5, var6, var7)
            : cn.kazer.client189.input.ImeCandidatePlacement.a(var1, var2, var3, var4, var5, var6, var7);
         this.a(var9);
         if (var9.equals(this.lastPosition)) {
            this.x = var1;
            this.top = var2;
            this.height = var3;
            this.guiWidth = var4;
            this.guiHeight = var5;
            this.clientWidth = var6;
            this.clientHeight = var7;
            this.above = var8;
            this.cachedGui = true;
         }
      }
   }

   public interface a {
      boolean a(cn.kazer.client189.input.ImeCandidatePlacement var1);
   }
}
