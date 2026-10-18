package com.cheatbreaker.client.kazerui.module.modules.visual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.opengl.GL11;

final class SkinLayerVoxelFactory {
   private SkinLayerVoxelFactory() {
   }

   static com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.d a(
      com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.c var0, int var1, int var2, int var3, int var4, int var5, boolean var6, float var7
   ) {
      ArrayList var8 = new ArrayList();
      float var9 = -var1 / 2.0F;
      float var10 = var6 ? var7 : -var2 + var7;
      float var11 = -var3 / 2.0F;

      try {
         for (com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a var15 : com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a.values()) {
            com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f var16 = a(var1, var2, var3, var15);

            for (int var17 = 0; var17 < var16.u; var17++) {
               for (int var18 = 0; var18 < var16.v; var18++) {
                  a(
                     var0,
                     var8,
                     var9,
                     var10,
                     var11,
                     var15,
                     var1,
                     var2,
                     var3,
                     new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f(var17, var18),
                     new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f(var4, var5),
                     var16
                  );
               }
            }
         }
      } catch (Exception var19) {
         LogManager.getLogger("Kazer").warn("SkinLayers3D: " + var19.getMessage());
         return new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.d(new ArrayList<>());
      }

      return new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.d(var8);
   }

   private static com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f a(
      int var0, int var1, int var2, com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a var3
   ) {
      switch (var3) {
         case UP:
         case DOWN:
            return new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f(var0, var2);
         case NORTH:
         case SOUTH:
            return new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f(var0, var1);
         default:
            return new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f(var2, var1);
      }
   }

   private static com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f a(
      com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f var0,
      com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f var1,
      int var2,
      int var3,
      int var4,
      com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a var5
   ) {
      switch (var5) {
         case UP:
            return new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f(var0.u + var2 + var4 + var1.u, var0.v + var1.v);
         case DOWN:
            return new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f(var0.u + var4 + var1.u, var0.v + var1.v);
         case NORTH:
            return new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f(var0.u + var4 + var1.u, var0.v + var4 + var1.v);
         case SOUTH:
            return new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f(var0.u + var4 + var2 + var4 + var1.u, var0.v + var4 + var1.v);
         case EAST:
         default:
            return new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f(var0.u + var4 + var2 + var1.u, var0.v + var4 + var1.v);
         case WEST:
            return new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f(var0.u + var1.u, var0.v + var4 + var1.v);
      }
   }

   private static com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.e a(
      com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f var0,
      int var1,
      int var2,
      int var3,
      com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a var4
   ) {
      switch (var4) {
         case UP:
            return new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.e(var0.u, var2 - 1, var3 - 1 - var0.v);
         case DOWN:
            return new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.e(var0.u, 0, var3 - 1 - var0.v);
         case NORTH:
            return new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.e(var0.u, var0.v, 0);
         case SOUTH:
            return new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.e(var1 - 1 - var0.u, var0.v, var3 - 1);
         case EAST:
         default:
            return new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.e(var1 - 1, var0.v, var0.u);
         case WEST:
            return new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.e(0, var0.v, var3 - 1 - var0.u);
      }
   }

   private static com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f a(
      com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.e var0,
      int var1,
      int var2,
      int var3,
      com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a var4
   ) {
      switch (var4) {
         case UP:
         case DOWN:
            return new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f(var0.x, var3 - 1 - var0.z);
         case NORTH:
            return new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f(var0.x, var0.y);
         case SOUTH:
            return new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f(var1 - 1 - var0.x, var0.y);
         case EAST:
         default:
            return new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f(var0.z, var0.y);
         case WEST:
            return new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f(var3 - 1 - var0.z, var0.y);
      }
   }

   private static boolean a(com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f var0, com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f var1) {
      return var0.u >= 0 && var0.u < var1.u && var0.v >= 0 && var0.v < var1.v;
   }

   private static boolean a(com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.c var0, com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f var1) {
      return var0.a(var1.u, var1.v);
   }

   private static boolean b(com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.c var0, com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f var1) {
      return var0.b(var1.u, var1.v);
   }

   private static void a(
      com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.c var0,
      List<com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.h> var1,
      float var2,
      float var3,
      float var4,
      com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a var5,
      int var6,
      int var7,
      int var8,
      com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f var9,
      com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f var10,
      com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f var11
   ) {
      com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f var12 = a(var10, var9, var6, var7, var8, var5);
      if (a(var0, var12)) {
         com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.e var13 = a(var9, var6, var7, var8, var5);
         float var14 = var2 + var13.x;
         float var15 = var3 + var13.y;
         float var16 = var4 + var13.z;
         boolean var17 = b(var0, var12);
         HashSet var18 = new HashSet();
         ArrayList var19 = new ArrayList();
         boolean var20 = false;
         boolean var21 = false;

         for (com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a var25 : com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a.values()) {
            if (var25.axis != var5.axis) {
               com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.e var26 = new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.e(
                  var13.x + var25.a(), var13.y + var25.b(), var13.z + var25.c()
               );
               com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f var27 = a(var26, var6, var7, var8, var5);
               if (a(var27, var11)) {
                  com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f var28 = a(var10, var27, var6, var7, var8, var5);
                  if (a(var0, var28)) {
                     if (!var17 || b(var0, var28)) {
                        var18.add(var25);
                     }
                  } else {
                     com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.e var29 = new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.e(
                        var26.x + var25.a(), var26.y + var25.b(), var26.z + var25.c()
                     );
                     com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f var30 = a(var29, var6, var7, var8, var5);
                     if (!a(var30, var11)) {
                        var30 = a(var29, var6, var7, var8, var25);
                        com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f var31 = a(var10, var30, var6, var7, var8, var25);
                        if (a(var0, var31) && (!var17 || b(var0, var31))) {
                           var18.add(var25);
                        }
                     }
                  }
               } else {
                  var20 = true;
                  com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f var32 = a(var13, var6, var7, var8, var25);
                  if (a(var0, a(var10, var32, var6, var7, var8, var25))) {
                     var21 = true;
                     var18.add(var25);
                     var19.add(new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a[]{var5.d(), var25});
                  } else {
                     com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.f var33 = a(
                        new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.e(var13.x - var5.a(), var13.y - var5.b(), var13.z - var5.c()),
                        var6,
                        var7,
                        var8,
                        var25
                     );
                     if (a(var0, a(var10, var33, var6, var7, var8, var25))) {
                        var21 = true;
                     }
                  }
               }
            }
         }

         if (!var20 || var21) {
            var18.add(var5.d());
         }

         var1.add(
            new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.h(
               var12.u, var12.v, var14, var15, var16, 1.0F, 1.0F, 1.0F, false, var0.a(), var0.b(), var18, var19
            )
         );
      }
   }

   static com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.d[] a(com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.c var0, boolean var1) {
      com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.d[] var2 = new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.d[]{
         a(var0, 4, 12, 4, 0, 48, true, 0.0F), a(var0, 4, 12, 4, 0, 32, true, 0.0F), null, null, null
      };
      int var3 = var1 ? 3 : 4;
      var2[2] = a(var0, var3, 12, 4, 48, 48, true, -2.5F);
      var2[3] = a(var0, var3, 12, 4, 40, 32, true, -2.5F);
      var2[4] = a(var0, 8, 12, 4, 16, 32, true, -0.8F);
      return var2;
   }

   static com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.d a(com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.c var0) {
      return a(var0, 8, 8, 8, 32, 0, false, 0.6F);
   }

   static enum a {
      DOWN(1, -1),
      UP(1, 1),
      NORTH(2, -1),
      SOUTH(2, 1),
      WEST(0, -1),
      EAST(0, 1);

      final int axis;
      final int sign;

      private a(int var3, int var4) {
         this.axis = var3;
         this.sign = var4;
      }

      int a() {
         return this.axis == 0 ? this.sign : 0;
      }

      int b() {
         return this.axis == 1 ? this.sign : 0;
      }

      int c() {
         return this.axis == 2 ? this.sign : 0;
      }

      com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a d() {
         switch (this) {
            case UP:
               return DOWN;
            case DOWN:
               return UP;
            case NORTH:
               return SOUTH;
            case SOUTH:
               return NORTH;
            case EAST:
               return WEST;
            default:
               return EAST;
         }
      }

      static double a(int var0, double var1, double var3, double var5) {
         return var0 == 0 ? var1 : (var0 == 1 ? var3 : var5);
      }
   }

   private static final class b {
      final com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g[] vertices;
      final float nx;
      final float ny;
      final float nz;

      b(
         com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g[] var1,
         float var2,
         float var3,
         float var4,
         float var5,
         float var6,
         float var7,
         boolean var8,
         com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a var9
      ) {
         this.vertices = var1;
         var1[0] = var1[0].a(var4 / var6, var3 / var7);
         var1[1] = var1[1].a(var2 / var6, var3 / var7);
         var1[2] = var1[2].a(var2 / var6, var5 / var7);
         var1[3] = var1[3].a(var4 / var6, var5 / var7);
         if (var8) {
            for (int var10 = 0; var10 < var1.length / 2; var10++) {
               com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g var11 = var1[var10];
               var1[var10] = var1[var1.length - 1 - var10];
               var1[var1.length - 1 - var10] = var11;
            }
         }

         this.nx = var8 ? -var9.a() : var9.a();
         this.ny = var9.b();
         this.nz = var9.c();
      }
   }

   interface c {
      int a();

      int b();

      boolean a(int var1, int var2);

      boolean b(int var1, int var2);
   }

   static final class d {
      float x;
      float y;
      float z;
      float xRot;
      float yRot;
      float zRot;
      boolean visible = true;
      private final List<com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.h> voxels;
      private int list = -1;
      private float listScale;

      d(List<com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.h> var1) {
         this.voxels = var1;
      }

      void a(ModelRenderer var1) {
         this.xRot = var1.rotateAngleX;
         this.yRot = var1.rotateAngleY;
         this.zRot = var1.rotateAngleZ;
         this.x = var1.rotationPointX;
         this.y = var1.rotationPointY;
         this.z = var1.rotationPointZ;
      }

      void a(float var1, float var2, float var3) {
         this.x = var1;
         this.y = var2;
         this.z = var3;
      }

      void b(float var1, float var2, float var3) {
         this.xRot = var1;
         this.yRot = var2;
         this.zRot = var3;
      }

      void a(float var1) {
         this.a(var1, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      }

      void a(float var1, float var2, float var3, float var4, float var5, float var6) {
         if (this.visible && !this.voxels.isEmpty()) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(this.x * var1, this.y * var1, this.z * var1);
            if (this.yRot != 0.0F) {
               GlStateManager.rotate(this.yRot * (float) (180.0 / Math.PI), 0.0F, 1.0F, 0.0F);
            }

            if (this.xRot != 0.0F) {
               GlStateManager.rotate(this.xRot * (float) (180.0 / Math.PI), 1.0F, 0.0F, 0.0F);
            }

            if (this.zRot != 0.0F) {
               GlStateManager.rotate(this.zRot * (float) (180.0 / Math.PI), 0.0F, 0.0F, 1.0F);
            }

            GlStateManager.translate(var2 * var1, var3 * var1, 0.0F);
            GlStateManager.scale(var4, var5, var6);
            if (this.list < 0 || var1 != this.listScale) {
               if (this.list >= 0) {
                  GLAllocation.deleteDisplayLists(this.list);
               }

               this.list = GLAllocation.generateDisplayLists(1);
               this.listScale = var1;
               GL11.glNewList(this.list, 4864);
               WorldRenderer var7 = Tessellator.getInstance().getWorldRenderer();
               var7.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);

               for (com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.h var9 : this.voxels) {
                  var9.a(var7, var1);
               }

               Tessellator.getInstance().draw();
               GL11.glEndList();
            }

            GlStateManager.callList(this.list);
            GlStateManager.popMatrix();
         }
      }

      void a() {
         if (this.list >= 0) {
            GLAllocation.deleteDisplayLists(this.list);
         }

         this.list = -1;
      }
   }

   private static final class e {
      final int x;
      final int y;
      final int z;

      e(int var1, int var2, int var3) {
         this.x = var1;
         this.y = var2;
         this.z = var3;
      }
   }

   private static final class f {
      final int u;
      final int v;

      f(int var1, int var2) {
         this.u = var1;
         this.v = var2;
      }
   }

   private static final class g {
      final float x;
      final float y;
      final float z;
      final float u;
      final float v;

      g(float var1, float var2, float var3, float var4, float var5) {
         this.x = var1;
         this.y = var2;
         this.z = var3;
         this.u = var4;
         this.v = var5;
      }

      com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g a(float var1, float var2) {
         return new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g(this.x, this.y, this.z, var1, var2);
      }

      float a(int var1) {
         return var1 == 0 ? this.x : (var1 == 1 ? this.y : this.z);
      }
   }

   static final class h {
      private final com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.b[] faces;

      h(
         int var1,
         int var2,
         float var3,
         float var4,
         float var5,
         float var6,
         float var7,
         float var8,
         boolean var9,
         float var10,
         float var11,
         Set<com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a> var12,
         List<com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a[]> var13
      ) {
         float var14 = var3 + var6;
         float var15 = var4 + var7;
         float var16 = var5 + var8;
         if (var9) {
            float var17 = var14;
            var14 = var3;
            var3 = var17;
         }

         com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g var35 = new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g(
            var3, var4, var5, 0.0F, 0.0F
         );
         com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g var18 = new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g(
            var14, var4, var5, 0.0F, 8.0F
         );
         com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g var19 = new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g(
            var14, var15, var5, 8.0F, 8.0F
         );
         com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g var20 = new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g(
            var3, var15, var5, 8.0F, 0.0F
         );
         com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g var21 = new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g(
            var3, var4, var16, 0.0F, 0.0F
         );
         com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g var22 = new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g(
            var14, var4, var16, 0.0F, 8.0F
         );
         com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g var23 = new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g(
            var14, var15, var16, 8.0F, 8.0F
         );
         com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g var24 = new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g(
            var3, var15, var16, 8.0F, 0.0F
         );
         float var25 = var1 + 1.0F;
         float var26 = var2 + 1.0F;
         HashMap var27 = new HashMap();

         for (com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a[] var29 : var13) {
            int var30 = 0;

            label58:
            while (var30 < 3) {
               for (com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a var34 : var29) {
                  if (var34.axis == var30) {
                     var30++;
                     continue label58;
                  }
               }

               var27.put(var30, var29);
               break;
            }
         }

         ArrayList var36 = new ArrayList();
         if (!var12.contains(com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a.DOWN)) {
            var36.add(
               new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.b(
                  a(
                     new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g[]{var22, var21, var35, var18},
                     (com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a[])var27.get(1)
                  ),
                  var1,
                  var2,
                  var25,
                  var26,
                  var10,
                  var11,
                  var9,
                  com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a.DOWN
               )
            );
         }

         if (!var12.contains(com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a.UP)) {
            var36.add(
               new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.b(
                  a(
                     new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g[]{var19, var20, var24, var23},
                     (com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a[])var27.get(1)
                  ),
                  var1,
                  var2,
                  var25,
                  var26,
                  var10,
                  var11,
                  var9,
                  com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a.UP
               )
            );
         }

         if (!var12.contains(com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a.NORTH)) {
            var36.add(
               new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.b(
                  a(
                     new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g[]{var18, var35, var20, var19},
                     (com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a[])var27.get(2)
                  ),
                  var1,
                  var2,
                  var25,
                  var26,
                  var10,
                  var11,
                  var9,
                  com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a.NORTH
               )
            );
         }

         if (!var12.contains(com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a.SOUTH)) {
            var36.add(
               new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.b(
                  a(
                     new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g[]{var21, var22, var23, var24},
                     (com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a[])var27.get(2)
                  ),
                  var1,
                  var2,
                  var25,
                  var26,
                  var10,
                  var11,
                  var9,
                  com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a.SOUTH
               )
            );
         }

         if (!var12.contains(com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a.WEST)) {
            var36.add(
               new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.b(
                  a(
                     new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g[]{var35, var21, var24, var20},
                     (com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a[])var27.get(0)
                  ),
                  var1,
                  var2,
                  var25,
                  var26,
                  var10,
                  var11,
                  var9,
                  com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a.WEST
               )
            );
         }

         if (!var12.contains(com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a.EAST)) {
            var36.add(
               new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.b(
                  a(
                     new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g[]{var22, var18, var19, var23},
                     (com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a[])var27.get(0)
                  ),
                  var1,
                  var2,
                  var25,
                  var26,
                  var10,
                  var11,
                  var9,
                  com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a.EAST
               )
            );
         }

         this.faces = (com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.b[]) var36.toArray(new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.b[0]);
      }

      private static com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g[] a(
         com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g[] var0, com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a[] var1
      ) {
         if (var1 == null) {
            return var0;
         } else {
            com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g var2 = var0[0];

            for (int var3 = 1; var3 < 4; var3++) {
               var2 = a(var2, var0[var3], var1);
            }

            int var5 = 0;

            for (int var4 = 0; var4 < 4; var4++) {
               if (var0[var4] != var2) {
                  var0[var5++] = var0[var4];
               }
            }

            var0[3] = var0[2];
            return var0;
         }
      }

      private static com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g a(
         com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g var0,
         com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g var1,
         com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a[] var2
      ) {
         for (com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a var6 : var2) {
            double var7 = (var0.a(var6.axis) - var1.a(var6.axis)) * var6.sign;
            if (var7 > 0.0) {
               return var0;
            }

            if (var7 < 0.0) {
               return var1;
            }
         }

         return var0;
      }

      void a(WorldRenderer var1, float var2) {
         for (com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.b var6 : this.faces) {
            for (int var7 = 0; var7 < 4; var7++) {
               com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.g var8 = var6.vertices[var7];
               var1.pos(var8.x * var2, var8.y * var2, var8.z * var2).tex(var8.u, var8.v).normal(var6.nx, var6.ny, var6.nz).endVertex();
            }
         }
      }
   }
}
