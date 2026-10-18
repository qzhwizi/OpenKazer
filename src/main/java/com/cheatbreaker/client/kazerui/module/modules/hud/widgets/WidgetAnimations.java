package com.cheatbreaker.client.kazerui.module.modules.hud.widgets;

import java.util.BitSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.ResourceLocation;

public final class WidgetAnimations {
   private WidgetAnimations() {
   }

   private static float b(float var0, float var1, float var2) {
      return Math.max(var1, Math.min(var2, var0));
   }

   static final class A implements com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.s {
      @Override
      public void a(float var1, float var2, float var3, int var4) {
         float var5 = var2 * var3;
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(0.0F, (var2 - var5) / 2.0F, var1, var5, var4);
      }
   }

   static final class B implements com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.s {
      @Override
      public void a(float var1, float var2, float var3, int var4) {
         boolean var5 = var1 / 1.5 > var2;
         float var6 = var5 ? var1 : var2;
         float var7 = 0.0F;
         float var8 = 0.0F;
         int var9 = (int)(8.0F + (var5 ? var1 / var2 : var2 / var1));
         float var10 = var6 / var9;
         float var11 = 1.0F / var9;

         for (int var12 = 0; var12 < var9; var12++) {
            float var13 = com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.b(var3 / (var11 * (var12 + 1)), 0.0F, 1.0F);
            if (var5) {
               float var14 = var2 * var13 / 2.0F;
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var7, var8, Math.min(var10, var6), var14, var4);
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var7, var2 - var14, Math.min(var10, var6), var14, var4);
               var7 += var10;
            } else {
               float var15 = var1 * var13 / 2.0F;
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var7, var8, var15, Math.min(var10, var6), var4);
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var1 - var15, var8, var15, Math.min(var10, var6), var4);
               var8 += var10;
            }

            var6 -= var10;
         }
      }
   }

   public static enum aInner implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
      FILL("Fill", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.h::new),
      SMOOTH_FILL("Smooth Fill", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.t::new),
      RIPPLE("Ripple", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.q::new),
      COLLAPSE("Collapse", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.c::new),
      ZIPPER("Zipper", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.B::new),
      TRIANGULATE("Triangulate", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.x::new),
      SPIRAL("Spiral", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.u::new),
      SAND("Sand", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.r::new),
      CIRCULAR_FILL("Circular Fill", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.CircularFill::new),
      MULTI_SQUARE_FILL("Multi Square Fill", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.o::new),
      HEAD_FILL("Head Fill", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.k::new),
      CROSS_COLLAPSE("Cross Collapse", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.d::new),
      CROSS_GROW("Cross Grow", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.e::new),
      HORIZONTAL_COLLAPSE("Horizontal Collapse", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.m::new),
      HORIZONTAL_GROW("Horizontal Grow", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.n::new),
      VERTICAL_COLLAPSE("Vertical Collapse", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.z::new),
      VERTICAL_GROW("Vertical Grow", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.A::new),
      DIAGONAL_COLLAPSE("Diagonal Collapse", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.f::new),
      DIAGONAL_GROW("Diagonal Grow", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.g::new);

      private final String id;
      private final Supplier<com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.s> factory;

      private aInner(String var3, Supplier<com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.s> var4) {
         this.id = var3;
         this.factory = var4;
      }

      public com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.s b() {
         return this.factory.get();
      }

      @Override
      public String a() {
         return this.id;
      }
   }

   static final class CircularFill implements com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.s {
      @Override
      public void a(float var1, float var2, float var3, int var4) {
         float var5 = var1 / 2.0F;
         float var6 = var2 / 2.0F;
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(
            var5, var6, com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.b(var1 * var3 * 4.0F, 0.0F, var1), 0.0F, 0.0F, 0.0F, var4
         );
         if (var3 > 0.25F) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(
               var5, var6, var1, com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.b(var2 * (var3 - 0.25F) * 4.0F, 0.0F, var2), var1, 0.0F, var4
            );
         }

         if (var3 > 0.5F) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(
               var5, var6, var1 - com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.b(var1 * (var3 - 0.5F) * 4.0F, 0.0F, var1), var2, var1, var2, var4
            );
         }

         if (var3 > 0.75F) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(
               var5, var6, 0.0F, var2 - com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.b(var2 * (var3 - 0.75F) * 4.0F, 0.0F, var2), 0.0F, var2, var4
            );
         }
      }
   }

   static final class c implements com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.s {
      @Override
      public void a(float var1, float var2, float var3, int var4) {
         float var5 = var1 * var3;
         float var6 = var2 * var3;
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(0.0F, 0.0F, var5 / 2.0F, var2, var4);
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var1 - var5 / 2.0F, 0.0F, var5 / 2.0F, var2, var4);
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var5 / 2.0F, 0.0F, var1 - var5, var6 / 2.0F, var4);
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var5 / 2.0F, var2 - var6 / 2.0F, var1 - var5, var6 / 2.0F, var4);
      }
   }

   static final class d implements com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.s {
      @Override
      public void a(float var1, float var2, float var3, int var4) {
         if (var3 == 1.0F) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(0.0F, 0.0F, var1, var2, var4);
         } else {
            float var5 = var1 * var3 / 2.0F;
            float var6 = var2 * var3 / 2.0F;
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(0.0F, 0.0F, var5, var6, var4);
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var1 - var5, 0.0F, var5, var6, var4);
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(0.0F, var2 - var6, var5, var6, var4);
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var1 - var5, var2 - var6, var5, var6, var4);
         }
      }
   }

   static final class e implements com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.s {
      @Override
      public void a(float var1, float var2, float var3, int var4) {
         float var5 = var1 * var3;
         float var6 = var2 * var3;
         float var7 = var1 - var5;
         float var8 = var2 - var6;
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var7 / 2.0F, 0.0F, var5, var2, var4);
         if (var3 != 1.0F) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(0.0F, var8 / 2.0F, var7 / 2.0F, var6, var4);
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var1 - var7 / 2.0F, var8 / 2.0F, var7 / 2.0F, var6, var4);
         }
      }
   }

   static final class f implements com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.s {
      @Override
      public void a(float var1, float var2, float var3, int var4) {
         float var5 = var3 * var1;
         float var6 = var3 * var2;
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(0.0F, 0.0F, 0.0F, var6, var5, 0.0F, var4);
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var1, var2 - var6, var1 - var5, var2, var1, var2, var4);
      }
   }

   static final class g implements com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.s {
      @Override
      public void a(float var1, float var2, float var3, int var4) {
         float var5 = (1.0F - var3) * var1;
         float var6 = (1.0F - var3) * var2;
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var5, 0.0F, var1, var2 - var6, var1, 0.0F, var4);
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(0.0F, var2, var1 - var5, var2, 0.0F, var6, var4);
         if (var3 != 1.0F) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var5, 0.0F, 0.0F, var6, var1, var2 - var6, var4);
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(0.0F, var6, var1 - var5, var2, var1, var2 - var6, var4);
         }
      }
   }

   static final class h implements com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.s {
      @Override
      public void a(float var1, float var2, float var3, int var4) {
         float var5 = var1 * var3;
         float var6 = var2 * var3;
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a((var1 - var5) / 2.0F, (var2 - var6) / 2.0F, var5, var6, var4);
      }
   }

   static final class i implements com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.p {
      private final long start = System.nanoTime();
      private final long length;

      i(double var1) {
         this.length = (long)(var1 * 1.0E9);
      }

      @Override
      public float a() {
         float var1 = this.b();
         return var1 > 1.0F ? 2.0F - var1 : var1;
      }

      @Override
      public float b() {
         return com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.b((float)((double)(System.nanoTime() - this.start) / this.length * 2.0), 0.0F, 2.0F);
      }

      @Override
      public void c() {
      }

      @Override
      public void d() {
      }
   }

   static final class j implements com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.p {
      private final long start = System.nanoTime();
      private final long length;

      j(double var1) {
         this.length = (long)(var1 * 1.0E9);
      }

      @Override
      public float a() {
         return com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.b((float)((double)(System.nanoTime() - this.start) / this.length), 0.0F, 1.0F);
      }

      @Override
      public float b() {
         return this.a();
      }

      @Override
      public void c() {
      }

      @Override
      public void d() {
      }

      @Override
      public boolean e() {
         return this.a() == 1.0F;
      }
   }

   static final class k implements com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.s {
      @Override
      public void a(float var1, float var2, float var3, int var4) {
         EntityPlayerSP var5 = Minecraft.getMinecraft().thePlayer;
         if (var5 != null) {
            ResourceLocation var6 = var5.getLocationSkin();
            if (var6 != null) {
               float var7 = var1 * var3;
               float var8 = var2 * var3;
               float var9 = (var1 - var7) / 2.0F;
               float var10 = (var2 - var8) / 2.0F;
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var6, var9, var10, var7, var8, 0.125F, 0.125F, 0.25F, 0.25F);
               if (var5.isWearing(EnumPlayerModelParts.HAT)) {
                  com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var6, var9, var10, var7, var8, 0.625F, 0.125F, 0.75F, 0.25F);
               }
            }
         }
      }
   }

   static final class l implements com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.p {
      private final long length;
      private long start;
      private double offset;
      private boolean pressed;

      l(double var1) {
         this.length = (long)(var1 * 1.0E9);
      }

      @Override
      public void c() {
         this.offset = this.a();
         this.pressed = true;
         this.start = System.nanoTime();
      }

      @Override
      public void d() {
         this.offset = 1.0 + (1.0F - this.a());
         this.pressed = false;
         this.start = System.nanoTime();
      }

      @Override
      public float a() {
         float var1 = this.b();
         return var1 > 1.0F ? 2.0F - var1 : var1;
      }

      @Override
      public float b() {
         double var1 = (double)(System.nanoTime() - this.start) / this.length * 2.0 + this.offset;
         if (var1 > 1.0 && this.pressed) {
            return 1.0F;
         } else {
            return var1 > 2.0 ? 2.0F : (float)var1;
         }
      }
   }

   static final class m implements com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.s {
      @Override
      public void a(float var1, float var2, float var3, int var4) {
         float var5 = var1 * var3;
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(0.0F, 0.0F, var5 / 2.0F, var2, var4);
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var1 - var5 / 2.0F, 0.0F, var5 / 2.0F, var2, var4);
      }
   }

   static final class n implements com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.s {
      @Override
      public void a(float var1, float var2, float var3, int var4) {
         float var5 = var1 * var3;
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a((var1 - var5) / 2.0F, 0.0F, var5, var2, var4);
      }
   }

   static final class o implements com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.s {
      @Override
      public void a(float var1, float var2, float var3, int var4) {
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(0.0F, 0.0F, var1 / 2.0F * var3, var2 / 2.0F, var4);
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var1 / 2.0F, 0.0F, var1 / 2.0F, var2 / 2.0F * var3, var4);
         float var5 = var1 / 2.0F * var3;
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var1 - var5, var2 / 2.0F, var5, var2 / 2.0F, var4);
         float var6 = var2 / 2.0F * var3;
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(0.0F, var2 - var6, var1 / 2.0F, var6, var4);
      }
   }

   public interface p {
      float a();

      float b();

      void c();

      void d();

      default boolean e() {
         return this.b() == 2.0F;
      }
   }

   static final class q implements com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.s {
      @Override
      public void a(float var1, float var2, float var3, int var4) {
         float var5 = var1 * var3;
         float var6 = var2 * var3;
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a((var1 - var5) / 2.0F, (var2 - var6) / 2.0F, var5, var6, Math.min(var5, var6) / 2.0F, var4);
      }
   }

   static final class r implements com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.s {
      private final BitSet grid = new BitSet(64);
      private long last = System.nanoTime();
      private int count;

      private static int a(int var0, int var1) {
         return var1 * 8 + var0;
      }

      @Override
      public void a(float var1, float var2, float var3, int var4) {
         long var5 = System.nanoTime();

         while (var5 - this.last > 10000000L) {
            this.a();
            if (this.count < 64) {
               int var7 = ThreadLocalRandom.current().nextInt(2) + 3;
               if (this.grid.get(a(var7, 7))) {
                  do {
                     var7 = ThreadLocalRandom.current().nextInt(8);
                  } while (this.grid.get(a(var7, 7)) && this.grid.cardinality() < 64);
               }

               this.grid.set(a(var7, 7));
               this.count++;
            }

            this.last += 10000000L;
         }

         GlStateManager.pushMatrix();
         GlStateManager.scale(var1 / 8.0F, var2 / 8.0F, 1.0F);
         GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.translate(-8.0F, -8.0F, 0.0F);

         for (int var9 = 0; var9 < 8; var9++) {
            for (int var8 = 0; var8 < 8; var8++) {
               if (this.grid.get(a(var9, var8))) {
                  com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var9, var8, 1.0F, 1.0F, var4);
               }
            }
         }

         GlStateManager.popMatrix();
      }

      private void a() {
         for (int var1 = 0; var1 < 8; var1++) {
            for (int var2 = 0; var2 < 8; var2++) {
               if (this.grid.get(a(var1, var2)) && var2 != 0) {
                  if (!this.grid.get(a(var1, var2 - 1))) {
                     this.grid.set(a(var1, var2 - 1));
                     this.grid.clear(a(var1, var2));
                  } else if (var1 > 0 && !this.grid.get(a(var1 - 1, var2 - 1))) {
                     this.grid.set(a(var1 - 1, var2 - 1));
                     this.grid.clear(a(var1, var2));
                  } else if (var1 < 7 && !this.grid.get(a(var1 + 1, var2 - 1))) {
                     this.grid.set(a(var1 + 1, var2 - 1));
                     this.grid.clear(a(var1, var2));
                  }
               }
            }
         }
      }
   }

   public interface s {
      void a(float var1, float var2, float var3, int var4);
   }

   static final class t implements com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.s {
      @Override
      public void a(float var1, float var2, float var3, int var4) {
         float var5 = var1 * var3;
         float var6 = var2 * var3;
         float var7 = Math.min(var5, var6);
         float var8 = var3 > 0.5 ? var7 / 2.0F * (1.0F - var3) / 0.5F : var7 / 2.0F;
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a((var1 - var5) / 2.0F, (var2 - var6) / 2.0F, var5, var6, var8, var4);
      }
   }

   static final class u implements com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.s {
      @Override
      public void a(float var1, float var2, float var3, int var4) {
         GlStateManager.pushMatrix();
         float var5 = 5 * Math.round(var1 / var2);
         float var6 = 5.0F;
         GlStateManager.scale(var1 / var5, var2 / var6, 1.0F);
         int var7 = (int)var5;
         int var8 = (int)var6;
         int var9 = var7 * var8;
         int var10 = (int)Math.ceil(var9 * var3);
         int var11 = var10;
         int var12 = 0;
         int var13 = 0;
         int var14 = 0;

         while (var10 > 0) {
            int var15 = var11 - var10;
            switch (var14 % 4) {
               case 0:
                  float var19 = com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.b((var3 - (float)var15 / var9) / ((float)var7 / var9), 0.0F, 1.0F);
                  com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var13, var12, var7 * var19, 1.0F, var4);
                  var12++;
                  var8--;
                  var10 -= var7;
                  break;
               case 1:
                  float var18 = com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.b((var3 - (float)var15 / var9) / ((float)var8 / var9), 0.0F, 1.0F);
                  com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var13 + var7 - 1, var12, 1.0F, var8 * var18, var4);
                  var7--;
                  var10 -= var8;
                  break;
               case 2:
                  float var17 = com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.b((var3 - (float)var15 / var9) / ((float)var7 / var9), 0.0F, 1.0F);
                  com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var13, var12 + var8 - 1, var7 * var17, 1.0F, var4);
                  var8--;
                  var10 -= var7;
                  break;
               default:
                  float var16 = com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.b((var3 - (float)var15 / var9) / ((float)var8 / var9), 0.0F, 1.0F);
                  com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var13, var12, 1.0F, var8 * var16, var4);
                  var13++;
                  var7--;
                  var10 -= var8;
            }

            var14++;
            if (var7 <= 0 || var8 <= 0) {
               break;
            }
         }

         GlStateManager.popMatrix();
      }
   }

   public static enum v implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
      HALF("half", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.j::new),
      FULL("complete", com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.i::new);

      private final String id;
      private final Function<Double, com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.p> factory;

      private v(String var3, Function<Double, com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.p> var4) {
         this.id = var3;
         this.factory = var4;
      }

      public com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.p a(double var1) {
         return this.factory.apply(var1);
      }

      @Override
      public String a() {
         return this.id;
      }
   }

   public static enum w implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
      EASE(0.25F, 0.1F, 0.25F, 1.0F, "ease"),
      LINEAR(0.0F, 0.0F, 1.0F, 1.0F, "linear"),
      EASE_IN(0.42F, 0.0F, 1.0F, 1.0F, "ease_in"),
      EASE_OUT(0.0F, 0.0F, 0.58F, 1.0F, "ease_out"),
      EASE_IN_OUT(0.42F, 0.0F, 0.58F, 1.0F, "ease_in_out");

      private final float x1;
      private final float y1;
      private final float x2;
      private final float y2;
      private final String id;

      private w(float var3, float var4, float var5, float var6, String var7) {
         this.x1 = var3;
         this.y1 = var4;
         this.x2 = var5;
         this.y2 = var6;
         this.id = var7;
      }

      public float a(float var1) {
         float var2 = 1.0F - var1;
         double var3 = var2 * var2 * 3.0F * var1 * this.y1 + var2 * 3.0F * var1 * var1 * this.y2 + var1 * var1 * var1;
         return com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.b((float)var3, 0.0F, 1.0F);
      }

      @Override
      public String a() {
         return this.id;
      }
   }

   static final class x implements com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.s {
      @Override
      public void a(float var1, float var2, float var3, int var4) {
         float var5 = var1 / 2.0F;
         float var6 = var2 / 2.0F;
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var5, var6, var5, var6 - var2 / 2.0F, var5 - var1 / 2.0F, var6 - var2 / 2.0F, var4);
         if (var3 > 0.125F) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var5, var6, var5 + var1 / 2.0F, var6 - var2 / 2.0F, var5, var6 - var2 / 2.0F, var4);
         }

         if (var3 > 0.25F) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var5, var6, var5 + var1 / 2.0F, var6, var5 + var1 / 2.0F, var6 - var2 / 2.0F, var4);
         }

         if (var3 > 0.375F) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var5, var6, var5 + var1 / 2.0F, var6 + var2 / 2.0F, var5 + var1 / 2.0F, var6, var4);
         }

         if (var3 > 0.5F) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var5, var6, var5, var6 + var2 / 2.0F, var5 + var1 / 2.0F, var6 + var2 / 2.0F, var4);
         }

         if (var3 > 0.675F) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var5, var6, var5 - var1 / 2.0F, var6 + var2 / 2.0F, var5, var6 + var2 / 2.0F, var4);
         }

         if (var3 > 0.75F) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var5, var6, var5 - var1 / 2.0F, var6, var5 - var1 / 2.0F, var6 + var2 / 2.0F, var4);
         }

         if (var3 > 0.875F) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var5, var6, var5 - var1 / 2.0F, var6 - var2 / 2.0F, var5 - var1 / 2.0F, var6, var4);
         }
      }
   }

   public static enum y implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
      SYNCED("synced"),
      STACKED("stacked");

      private final String id;

      private y(String var3) {
         this.id = var3;
      }

      @Override
      public String a() {
         return this.id;
      }
   }

   static final class z implements com.cheatbreaker.client.kazerui.module.modules.hud.widgets.WidgetAnimations.s {
      @Override
      public void a(float var1, float var2, float var3, int var4) {
         float var5 = var2 * var3;
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(0.0F, 0.0F, var1, var5 / 2.0F, var4);
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(0.0F, var2 - var5 / 2.0F, var1, var5 / 2.0F, var4);
      }
   }
}
