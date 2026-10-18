package com.cheatbreaker.client.kazerui.module.modules.visual;

import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public final class SkinLayers3DModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static SkinLayers3DModule instance;
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting[] parts = new com.cheatbreaker.client.kazerui.module.settings.ToggleSetting[]{
      this.a("enableHat", true),
      this.a("enableLeftPants", true),
      this.a("enableRightPants", true),
      this.a("enableLeftSleeve", true),
      this.a("enableRightSleeve", true),
      this.a("enableJacket", true)
   };
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting others = this.a("showOthers", true);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting distance = this.a("renderDistanceLod", 14, 5, 40);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting headSize = this.a("headVoxelSize", 1.18F, 1.001F, 1.25F);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting bodySize = this.a("baseVoxelSize", 1.15F, 1.001F, 1.4F);
   private final LinkedHashMap<ResourceLocation, SkinLayers3DModule.a> cache = new LinkedHashMap<>(128, 0.75F, true);
   private final com.cheatbreaker.client.kazerui.module.modules.visual.SkinBuildRateLimiter builds = new com.cheatbreaker.client.kazerui.module.modules.visual.SkinBuildRateLimiter();
   private long epoch = -1L;

   public SkinLayers3DModule() {
      super("3D_SKINS", false);
      instance = this;
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a("performanceOptions", var1x -> var1x.a(this.distance, this.others));
      var1.a("playerModelOptions", var1x -> {
         var1x.a(this.parts);
         var1x.a(this.headSize, this.bodySize);
      });
   }

   public static boolean n() {
      return instance != null && instance.a();
   }

   @Override
   protected void f() {
      this.o();
   }

   private void o() {
      for (SkinLayers3DModule.a var2 : this.cache.values()) {
         var2.a();
      }

      this.cache.clear();
      this.builds.a();
   }

   private boolean a(AbstractClientPlayer var1) {
      return this.a()
         && !var1.isInvisible()
         && (this.others.b() || var1 == m().thePlayer)
         && (m().thePlayer == null || var1.getDistanceSqToEntity(m().thePlayer) <= this.distance.c() * this.distance.c());
   }

   private SkinLayers3DModule.a b(AbstractClientPlayer var1) {
      if (this.epoch != com.cheatbreaker.client.kazerui.RenderEpoch.a()) {
         this.o();
         this.epoch = com.cheatbreaker.client.kazerui.RenderEpoch.a();
      }

      ResourceLocation var2 = var1.getLocationSkin();
      ITextureObject var3 = m().getTextureManager().getTexture(var2);
      if (var3 == null) {
         return null;
      } else {
         int var4 = var3.getGlTextureId();
         boolean var5 = com.cheatbreaker.client.kazerui.module.modules.visual.PlayerModelHelper.a(var1);
         SkinLayers3DModule.a var6 = this.cache.get(var2);
         if (var6 == null || var6.texture != var4 || var6.slim != var5 || var6.head == null && System.currentTimeMillis() >= var6.retry) {
            if (!this.builds.a(System.nanoTime())) {
               return null;
            } else {
               if (var6 != null) {
                  var6.a();
               }

               com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.c var7 = null;
               cn.kazer.client189.render.GlStateBridge.c(262176);

               try {
                  m().getTextureManager().bindTexture(var2);
                  int var8 = GL11.glGetTexLevelParameteri(3553, 0, 4096);
                  final int var9 = GL11.glGetTexLevelParameteri(3553, 0, 4097);
                  if (var8 == 64 && (var9 == 32 || var9 == 64)) {
                     ByteBuffer var10 = BufferUtils.createByteBuffer(var8 * var9 * 4);
                     GL11.glGetTexImage(3553, 0, 6408, 5121, var10);
                     final byte[] var11 = new byte[var8 * var9];

                     for (int var12 = 0; var12 < var11.length; var12++) {
                        var11[var12] = var10.get(var12 * 4 + 3);
                     }

                     var7 = new com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.c() {
                        @Override
                        public int a() {
                           return 64;
                        }

                        @Override
                        public int b() {
                           return var9;
                        }

                        @Override
                        public boolean a(int var1, int var2x) {
                           return var1 >= 0 && var1 < 64 && var2x >= 0 && var2x < var9 && (var11[var2x * 64 + var1] & 255) > 0;
                        }

                        @Override
                        public boolean b(int var1, int var2x) {
                           return this.a(var1, var2x) && (var11[var2x * 64 + var1] & 255) == 255;
                        }
                     };
                  }
               } finally {
                  cn.kazer.client189.render.GlStateBridge.a();
               }

               SkinLayers3DModule.a var16 = new SkinLayers3DModule.a(
                  var4,
                  var5,
                  var7 == null ? null : com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a(var7),
                  var7 != null && var7.b() >= 64 ? com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.a(var7, var5) : null
               );
               this.cache.put(var2, var16);
               if (this.cache.size() > 128) {
                  ResourceLocation var17 = this.cache.keySet().iterator().next();
                  this.cache.remove(var17).a();
               }

               return var16;
            }
         } else {
            return var6;
         }
      }
   }

   public static void a(AbstractClientPlayer var0, ModelBiped var1) {
      SkinLayers3DModule var2 = instance;
      if (var2 != null && var2.a(var0)) {
         SkinLayers3DModule.a var3 = var2.b(var0);
         if (var3 != null && var3.head != null) {
            int var4 = 0;

            for (int var5 = 0; var5 < 6; var5++) {
               if (var2.parts[var5].b() && com.cheatbreaker.client.kazerui.module.modules.visual.PlayerModelHelper.a(var0, var5) && (var5 == 0 || var3.body != null)) {
                  var4 |= 1 << var5;
               }
            }

            com.cheatbreaker.client.kazerui.module.modules.visual.PlayerModelHelper.a(var1, var4);
         }
      }
   }

   public static void b(AbstractClientPlayer var0, ModelBiped var1) {
      SkinLayers3DModule var2 = instance;
      if (var2 != null && var2.a(var0)) {
         SkinLayers3DModule.a var3 = var2.b(var0);
         if (var3 != null && var3.head != null) {
            m().getTextureManager().bindTexture(var0.getLocationSkin());
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.pushMatrix();

            try {
               if (var0.isSneaking()) {
                  GlStateManager.translate(0.0F, 0.2F, 0.0F);
               }

               if (var2.parts[0].b() && com.cheatbreaker.client.kazerui.module.modules.visual.PlayerModelHelper.a(var0, 0)) {
                  var3.head.a(var1.bipedHead);
                  float var4 = var2.headSize.b();
                  var3.head.a(0.0625F, 0.0F, 0.0F, var4, var4, var4);
               }

               if (var3.body != null) {
                  for (int var10 = 0; var10 < 5; var10++) {
                     if (var2.parts[var10 + 1].b() && com.cheatbreaker.client.kazerui.module.modules.visual.PlayerModelHelper.a(var0, var10 + 1)) {
                        ModelRenderer var5 = var10 == 0
                           ? var1.bipedLeftLeg
                           : (var10 == 1 ? var1.bipedRightLeg : (var10 == 2 ? var1.bipedLeftArm : (var10 == 3 ? var1.bipedRightArm : var1.bipedBody)));
                        var3.body[var10].a(var5);
                        float var6 = var2.bodySize.b();
                        var3.body[var10].a(0.0625F, 0.0F, 0.0F, var6, 1.0F, var6);
                     }
                  }
               }
            } finally {
               GlStateManager.popMatrix();
            }
         }
      }
   }

   private static final class a {
      final int texture;
      final boolean slim;
      final com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.d head;
      final com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.d[] body;
      final long retry;

      a(
         int var1,
         boolean var2,
         com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.d var3,
         com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.d[] var4
      ) {
         this.texture = var1;
         this.slim = var2;
         this.head = var3;
         this.body = var4;
         this.retry = System.currentTimeMillis() + 1000L;
      }

      void a() {
         if (this.head != null) {
            this.head.a();
         }

         if (this.body != null) {
            for (com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayerVoxelFactory.d var4 : this.body) {
               var4.a();
            }
         }
      }
   }
}
