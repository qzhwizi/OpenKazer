package com.cheatbreaker.client.module.modules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.ScaledResolution;

final class KeybindListAnimator {
   private static final float TOP_MARGIN = 5.0F;
   private static final float STEP = 22.0F;
   private final Map<com.cheatbreaker.client.module.modules.ActionFeedbackState.Notification, Float> positions = new HashMap<>();
   private long lastRenderAt = -1L;

   void a(List<com.cheatbreaker.client.module.modules.ActionFeedbackState.Notification> var1, ScaledResolution var2, long var3) {
      this.positions.keySet().retainAll(var1);
      long var5 = this.lastRenderAt < 0L ? 16L : Math.max(1L, Math.min(100L, var3 - this.lastRenderAt));
      float var7 = Math.min(1.0F, (float)var5 / 100.0F);

      for (int var8 = 0; var8 < var1.size(); var8++) {
         com.cheatbreaker.client.module.modules.ActionFeedbackState.Notification var9 = (com.cheatbreaker.client.module.modules.ActionFeedbackState.Notification)var1.get(var8);
         float var10 = 5.0F + var8 * 22.0F;
         Float var11 = this.positions.get(var9);
         if (var11 == null) {
            var11 = -20.0F;
         }

         var11 = var11 + (var10 - var11) * var7;
         if (Math.abs(var10 - var11) < 0.1F) {
            var11 = var10;
         }

         this.positions.put(var9, var11);
         com.cheatbreaker.client.module.modules.layout.NotificationRenderer.a(this.a(var9.b()), var9.a(), var2.getScaledWidth(), var11, var9.d(), 1500L, var3, var9.a(var3));
      }

      this.lastRenderAt = var3;
   }

   private com.cheatbreaker.client.module.modules.layout.NotificationType a(com.cheatbreaker.client.module.modules.ActionFeedbackState.Kind var1) {
      return var1 == com.cheatbreaker.client.module.modules.ActionFeedbackState.Kind.ERROR ? com.cheatbreaker.client.module.modules.layout.NotificationType.ERROR : com.cheatbreaker.client.module.modules.layout.NotificationType.INFO;
   }
}
