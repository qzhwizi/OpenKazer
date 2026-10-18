package com.cheatbreaker.client.module.modules.layout;

import net.minecraft.util.ResourceLocation;

public final class NotificationRenderer {
   private NotificationRenderer() {
   }

   public static void a(com.cheatbreaker.client.module.modules.layout.NotificationType var0, String var1, int var2, float var3, long var4, long var6, long var8) {
      a(var0, var1, var2, var3, var4, var6, var8, 1.0F);
   }

   public static void a(com.cheatbreaker.client.module.modules.layout.NotificationType var0, String var1, int var2, float var3, long var4, long var6, long var8, float var10) {
      var10 = Math.max(0.0F, Math.min(1.0F, var10));
      if (!(var10 <= 0.0F)) {
         com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer var11 = com.cheatbreaker.client.CheatBreakerClient.N().playRegular16px;
         float var12 = var11.a(var1);
         int var13 = (int)(var0 == com.cheatbreaker.client.module.modules.layout.NotificationType.DEFAULT ? var12 + 10.0F : var12 + 30.0F);
         float var14 = var0 == com.cheatbreaker.client.module.modules.layout.NotificationType.DEFAULT ? 16.0F : 20.0F;
         float var15 = var2 - 5.0F;
         float var16 = var15 - 5.0F - var12;
         cn.kazer.client189.render.DrawRect.a(var15 - var13, var3, var15, var3 + var14, a(-1358954496, var10));
         switch (var0) {
            case ERROR:
               cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, var10);
               com.cheatbreaker.client.gui.render.RenderUtils.a(new ResourceLocation("client/icons/error-64.png"), 6.0F, var15 - var13 + 4.0F, var3 + 4.0F);
               cn.kazer.client189.render.DrawRect.a(var16 - 4.5F, var3 + 4.0F, var16 - 4.0F, var3 + var14 - 4.0F, a(-1342177281, var10));
               break;
            case INFO:
               cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 0.65F * var10);
               com.cheatbreaker.client.gui.render.RenderUtils.a(new ResourceLocation("client/icons/info-64.png"), 6.0F, var15 - var13 + 4.0F, var3 + 4.0F);
               cn.kazer.client189.render.DrawRect.a(var16 - 4.5F, var3 + 4.0F, var16 - 4.0F, var3 + var14 - 4.0F, a(-1342177281, var10));
         }

         long var17 = Math.max(0L, Math.min(var6, var8 - var4));
         float var19 = var6 == 0L ? var12 : var12 * (float)var17 / (float)var6;
         cn.kazer.client189.render.DrawRect.a(var16, var3 + var14 - 4.4F, var16 + var12, var3 + var14 - 4.0F, a(812017254, var10));
         cn.kazer.client189.render.DrawRect.a(var16, var3 + var14 - 4.4F, var16 + var19, var3 + var14 - 4.0F, a(-1878982912, var10));
         int var20 = a(-1, var10);
         if (var20 >>> 24 < 4) {
            var20 = var20 & 16777215 | 67108864;
         }

         var11.a(var1, var16, var3 + (var0 == com.cheatbreaker.client.module.modules.layout.NotificationType.DEFAULT ? 2.0F : 4.0F), var20);
         cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   private static int a(int var0, float var1) {
      int var2 = Math.round((var0 >>> 24) * var1);
      return var0 & 16777215 | var2 << 24;
   }
}
