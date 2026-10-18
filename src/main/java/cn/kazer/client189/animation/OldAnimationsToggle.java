package cn.kazer.client189.animation;

public final class OldAnimationsToggle {
   private static volatile boolean enabled = true;

   private OldAnimationsToggle() {
   }

   public static void a(boolean var0) {
      enabled = var0;
      if (!var0) {
         OldAnimationsAdapter.reset();
      }
   }

   public static boolean a() {
      return enabled;
   }

   static cn.kazer.client189.animation.LegacyVisualsModule b() {
      com.cheatbreaker.client.CheatBreakerClient var0 = com.cheatbreaker.client.CheatBreakerClient.N();
      return var0 != null && var0.moduleManager != null ? var0.moduleManager.blockHitAnimation : null;
   }
}
