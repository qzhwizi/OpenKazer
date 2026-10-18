package com.cheatbreaker.client.kazerui.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public final class ToastNotifications {
   private static final long DURATION = 2500L;
   private static final long ENTER = 350L;
   private static final long EXIT = 400L;
   private static final float MARGIN = 8.0F;
   private static final float GAP = 4.0F;
   private static final float PADDING = 5.0F;
   private static final float ICON = 8.0F;
   private static final float TEXT_X = 18.0F;
   private static final String FONT = "DMSans-Regular.ttf";
   private static final com.cheatbreaker.client.kazerui.gui.UiGraphics GFX = new com.cheatbreaker.client.kazerui.gui.UiGraphics("popup");
   private static final ConcurrentLinkedQueue<com.cheatbreaker.client.kazerui.gui.ToastNotifications.a> PENDING = new ConcurrentLinkedQueue<>();
   private static final List<com.cheatbreaker.client.kazerui.gui.ToastNotifications.a> POPUPS = new ArrayList<>();

   private ToastNotifications() {
   }

   public static void a(com.cheatbreaker.client.kazerui.gui.ToastNotifications.b var0, String var1, String var2) {
      a(var0, var1, var2, 2500L);
   }

   public static void a(com.cheatbreaker.client.kazerui.gui.ToastNotifications.b var0, String var1, String var2, long var3) {
      PENDING.add(new com.cheatbreaker.client.kazerui.gui.ToastNotifications.a(var0, var1, var2, var3));
   }

   public static void a(String var0) {
      a(com.cheatbreaker.client.kazerui.gui.ToastNotifications.b.INFO, null, var0);
   }

   public static void a() {
      if (!PENDING.isEmpty() || !POPUPS.isEmpty()) {
         ScaledResolution var0 = new ScaledResolution(Minecraft.getMinecraft());
         GFX.a(var0.getScaleFactor());
         long var1 = System.nanoTime() / 1000000L;

         com.cheatbreaker.client.kazerui.gui.ToastNotifications.a var3;
         while ((var3 = PENDING.poll()) != null) {
            var3.shownAt = var1;
            POPUPS.add(0, var3);
            if (POPUPS.size() > 20) {
               POPUPS.remove(20);
            }
         }

         boolean var4 = GL11.glIsEnabled(2929);
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, 0.0F, 500.0F);
         GlStateManager.disableDepth();

         try {
            float var5 = 0.0F;
            Iterator var6 = POPUPS.iterator();

            while (var6.hasNext()) {
               com.cheatbreaker.client.kazerui.gui.ToastNotifications.a var7 = (com.cheatbreaker.client.kazerui.gui.ToastNotifications.a)var6.next();
               long var8 = var1 - var7.shownAt;
               if (var8 >= var7.duration + 400L) {
                  var6.remove();
               } else {
                  if (var7.lines == null || var7.layoutWidth != var0.getScaledWidth()) {
                     a(var7, var0.getScaledWidth());
                  }

                  if (var5 != var7.targetOffset) {
                     var7.fromOffset = var7.offset;
                     var7.targetOffset = var5;
                     var7.movedAt = var1;
                  }

                  var7.offset = var7.fromOffset + (var7.targetOffset - var7.fromOffset) * a((float)(var1 - var7.movedAt) / 230.0F, 0.21F, 1.02F, 0.73F, 1.0F);
                  float var10 = 1.0F;
                  float var11 = 1.0F;
                  float var12 = 0.0F;
                  if (var8 < 350L && var8 < var7.duration) {
                     float var23 = a((float)var8 / 350.0F, 0.21F, 1.02F, 0.73F, 1.0F);
                     var10 = 0.5F + 0.5F * var23;
                     var11 = 0.6F + 0.4F * var23;
                     var12 = -2.0F * var7.height * (1.0F - var23);
                  } else if (var8 >= var7.duration) {
                     float var13 = a((float)(var8 - var7.duration) / 400.0F, 0.06F, 0.71F, 0.55F, 1.0F);
                     var10 = 1.0F - var13;
                     var11 = 1.0F - 0.4F * var13;
                     var12 = -1.5F * var7.height * var13;
                  }

                  float var24 = var0.getScaledWidth() - 8.0F - 5.0F - var7.width;
                  float var14 = 8.0F + var7.offset + 2.0F + var12;
                  GlStateManager.pushMatrix();

                  try {
                     GlStateManager.translate(var24 + var7.width / 2.0F, var14 + var7.height / 2.0F, 0.0F);
                     GlStateManager.scale(var11, var11, 1.0F);
                     a(var7, -var7.width / 2.0F, -var7.height / 2.0F, var10);
                  } finally {
                     GlStateManager.popMatrix();
                  }

                  if (var8 < var7.duration) {
                     var5 += var7.height + 4.0F + 4.0F;
                  }
               }
            }
         } finally {
            if (var4) {
               GlStateManager.enableDepth();
            }

            GlStateManager.popMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         }

         if (POPUPS.isEmpty()) {
            GFX.a();
         }
      }
   }

   private static float a(float var0, float var1, float var2, float var3, float var4) {
      if (var0 <= 0.0F) {
         return 0.0F;
      } else if (var0 >= 1.0F) {
         return 1.0F;
      } else {
         float var5 = 0.0F;
         float var6 = 1.0F;
         float var7 = var0;

         for (int var8 = 0; var8 < 16; var8++) {
            var7 = (var5 + var6) / 2.0F;
            if (a(var7, var1, var3) < var0) {
               var5 = var7;
            } else {
               var6 = var7;
            }
         }

         return a(var7, var2, var4);
      }
   }

   private static float a(float var0, float var1, float var2) {
      return 3.0F * (1.0F - var0) * (1.0F - var0) * var0 * var1 + 3.0F * (1.0F - var0) * var0 * var0 * var2 + var0 * var0 * var0;
   }

   private static void a(com.cheatbreaker.client.kazerui.gui.ToastNotifications.a var0, int var1) {
      var0.layoutWidth = var1;
      float var2 = Math.max(1.0F, Math.min(175.0F, var1 - 16.0F) - 10.0F - 18.0F - 5.0F);
      var0.titles = a(var0.title, 14.0F, var2);
      var0.lines = a(var0.message, 16.0F, var2);
      float var3 = 0.0F;

      for (String var5 : var0.titles) {
         var3 = Math.max(var3, GFX.a(var5, "DMSans-Regular.ttf", 14.0F));
      }

      for (String var7 : var0.lines) {
         var3 = Math.max(var3, GFX.a(var7, "DMSans-Regular.ttf", 16.0F));
      }

      var0.width = 18.0F + var3 + 5.0F;
      var0.height = 10.0F + Math.max(8.0F, var0.titles.size() * 9.1F + var0.lines.size() * 10.4F);
   }

   private static List<String> a(String var0, float var1, float var2) {
      ArrayList var3 = new ArrayList();
      if (var0.isEmpty()) {
         return var3;
      } else {
         for (String var7 : var0.replace("\r", "").split("\n", -1)) {
            String var8 = var7;

            while (GFX.a(var8, "DMSans-Regular.ttf", var1) > var2) {
               int var9 = 0;
               int var10 = -1;

               while (var9 < var8.length()) {
                  int var11 = var8.offsetByCodePoints(var9, 1);
                  if (var9 > 0 && GFX.a(var8.substring(0, var11), "DMSans-Regular.ttf", var1) > var2) {
                     break;
                  }

                  if (var8.charAt(var9) == ' ') {
                     var10 = var9;
                  }

                  var9 = var11;
               }

               if (var10 > 0) {
                  var9 = var10;
               }

               var3.add(var8.substring(0, var9));
               var8 = var8.substring(var9);
               if (var8.startsWith(" ")) {
                  var8 = var8.substring(1);
               }
            }

            var3.add(var8);
         }

         return var3;
      }
   }

   private static int a(int var0, float var1) {
      return Math.round(Math.max(0.0F, Math.min(1.0F, var1)) * 255.0F) << 24 | var0;
   }

   private static void a(com.cheatbreaker.client.kazerui.gui.ToastNotifications.a var0, float var1, float var2, float var3) {
      GFX.a(var1, var2, var0.width, var0.height, 3.5F, a(1710104, 0.7F * var3));
      GFX.a(var0.type.icon, var1 + 5.0F, var2 + (var0.height - 8.0F) / 2.0F, 8.0F, 8.0F, a(var0.type.color, var3));
      float var4 = var2 + 5.0F;

      for (String var6 : var0.titles) {
         GFX.a(var6, "DMSans-Regular.ttf", 14.0F, var1 + 18.0F, var4, a(15658736, var3));
         var4 += 9.1F;
      }

      for (String var8 : var0.lines) {
         GFX.a(var8, "DMSans-Regular.ttf", 16.0F, var1 + 18.0F, var4, a(15658736, var3 * (var0.title.isEmpty() ? 1.0F : 0.8F)));
         var4 += 10.4F;
      }
   }

   private static final class a {
      final com.cheatbreaker.client.kazerui.gui.ToastNotifications.b type;
      final String title;
      final String message;
      final long duration;
      long shownAt;
      List<String> titles;
      List<String> lines;
      float width;
      float height;
      float offset;
      float fromOffset;
      float targetOffset;
      long movedAt;
      int layoutWidth;

      a(com.cheatbreaker.client.kazerui.gui.ToastNotifications.b var1, String var2, String var3, long var4) {
         this.type = var1 == null ? com.cheatbreaker.client.kazerui.gui.ToastNotifications.b.INFO : var1;
         this.title = var2 == null ? "" : var2;
         this.message = var3 == null ? "" : var3;
         this.duration = Math.max(1L, var4);
      }
   }

   public static enum b {
      INFO("info", 49919),
      SUCCESS("success", 3915348),
      WARNING("warning", 16372515),
      ERROR("error", 16731471);

      final String icon;
      final int color;

      private b(String var3, int var4) {
         this.icon = "ui/icons/notifications/web-" + var3 + ".png";
         this.color = var4;
      }
   }
}
