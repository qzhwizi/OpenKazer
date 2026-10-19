package cn.kazer.client189.runtime;

import com.cheatbreaker.client.kazerui.module.modules.visual.ModuleOverlay;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

public final class OverlayModelCache {
   private static final ThreadLocal<OverlayModelCache.b> CACHE = new ThreadLocal<OverlayModelCache.b>() {
      @Override
      protected OverlayModelCache.b initialValue() {
         return new OverlayModelCache.b();
      }
   };
   private static volatile IBakedModel barrier;
   private static long barrierEpoch = Long.MIN_VALUE;
   private static int barrierColor;

   public static boolean renderBarrier(IBlockState var0, BlockPos var1, IBlockAccess var2, WorldRenderer var3) {
      if (ModuleOverlay.barriers() && var0.getBlock() == Blocks.barrier && com.cheatbreaker.client.kazerui.module.modules.visual.OverlayWhiteSprite.current != null) {
         long var4 = com.cheatbreaker.client.kazerui.RenderEpoch.a();
         int var6 = ModuleOverlay.barrierColor();
         IBakedModel var7 = barrier;
         if (var7 == null || var4 != barrierEpoch || var6 != barrierColor) {
            var7 = a(var4, var6);
         }

         Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(var2, var7, var0, var1, var3, true);
         return true;
      } else {
         return false;
      }
   }

   private static synchronized IBakedModel a(long var0, int var2) {
      if (barrier != null && barrierEpoch == var0 && barrierColor == var2) {
         return barrier;
      } else {
         barrier = new OverlayModelCache.a(var2);
         barrierEpoch = var0;
         barrierColor = var2;
         return barrier;
      }
   }

   public static IBakedModel adjust(IBakedModel var0, IBlockState var1) {
      if (ModuleOverlay.blockTweaks() && var0 != null && var1 != null) {
         boolean var2 = var1.getBlock() == Blocks.fire;
         boolean var3 = ModuleOverlay.outlines(var1.getBlock());
         if (!var2 && !var3) {
            return var0;
         } else {
            float var4 = var2 ? 1.0F - ModuleOverlay.blockFireOffset() : 1.0F;
            int var5 = var3 ? ModuleOverlay.outlineColor() : 16777215;
            if (!var3 && var4 == 1.0F) {
               return var0;
            } else {
               OverlayModelCache.b var6 = CACHE.get();
               long var7 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.d().revision;
               long var9 = com.cheatbreaker.client.kazerui.RenderEpoch.a();
               if (var6.revision != var7 || var6.epoch != var9) {
                  var6.variants.clear();
                  var6.revision = var7;
                  var6.epoch = var9;
               }

               IBakedModel[] var11 = var6.variants.get(var0);
               if (var11 == null) {
                  if (var6.variants.size() >= 512) {
                     var6.variants.clear();
                  }

                  var11 = new IBakedModel[2];
                  var6.variants.put(var0, var11);
               }

               int var12 = var2 ? 0 : 1;
               if (var11[var12] == null) {
                  var11[var12] = new OverlayModelCache.c(var0, var4, var5, var3);
               }

               return var11[var12];
            }
         }
      } else {
         return var0;
      }
   }

   private static List<BakedQuad> b(List<BakedQuad> var0, float var1, int var2, boolean var3) {
      ArrayList var4 = new ArrayList(var0.size());

      for (BakedQuad var6 : var0) {
         if (var3) {
            var4.add(var6);
            b(var4, var6, var2);
         } else {
            int[] var7 = (int[])var6.getVertexData().clone();
            int var8 = var7.length / 4;
            if (var8 >= 7 && var7.length % 4 == 0) {
               int var9 = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN ? (var2 & 0xFF) << 16 | var2 & 0xFF00 | var2 >> 16 & 0xFF : var2;

               for (int var10 = 0; var10 < 4; var10++) {
                  var7[var10 * var8 + 1] = Float.floatToRawIntBits(Float.intBitsToFloat(var7[var10 * var8 + 1]) * var1);
                  if (var2 != 16777215) {
                     var7[var10 * var8 + 3] = var7[var10 * var8 + 3] & 0xFF000000 | var9;
                  }
               }

               var4.add(new BakedQuad(var7, var6.getTintIndex(), var6.getFace()));
            } else {
               var4.add(var6);
            }
         }
      }

      return Collections.unmodifiableList(var4);
   }

   private static void b(List<BakedQuad> var0, BakedQuad var1, int var2) {
      com.cheatbreaker.client.kazerui.module.modules.visual.OverlayWhiteSprite var3 = com.cheatbreaker.client.kazerui.module.modules.visual.OverlayWhiteSprite.current;
      if (var3 != null) {
         int[] var4 = var1.getVertexData();
         int var5 = var4.length / 4;
         if (var5 >= 7 && var4.length % 4 == 0) {
            float var6 = 0.0F;
            float var7 = 0.0F;
            float var8 = 0.0F;

            for (int var9 = 0; var9 < 4; var9++) {
               var6 += Float.intBitsToFloat(var4[var9 * var5]);
               var7 += Float.intBitsToFloat(var4[var9 * var5 + 1]);
               var8 += Float.intBitsToFloat(var4[var9 * var5 + 2]);
            }

            var6 /= 4.0F;
            var7 /= 4.0F;
            var8 /= 4.0F;
            EnumFacing var24 = var1.getFace();
            int var10 = 0xFF000000 | (var2 & 0xFF) << 16 | var2 & 0xFF00 | var2 >> 16 & 0xFF;

            for (int var11 = 0; var11 < 4; var11++) {
               int var12 = (var11 + 1) % 4;
               int[] var13 = (int[])var4.clone();

               for (int var14 = 0; var14 < 4; var14++) {
                  int var15 = (var14 != 0 && var14 != 3 ? var12 : var11) * var5;
                  int var16 = var14 * var5;
                  System.arraycopy(var4, var15, var13, var16, var5);
                  float var17 = var14 >= 2 ? 0.05F : 0.0F;
                  float var18 = Float.intBitsToFloat(var4[var15]);
                  float var19 = Float.intBitsToFloat(var4[var15 + 1]);
                  float var20 = Float.intBitsToFloat(var4[var15 + 2]);
                  var13[var16] = Float.floatToRawIntBits(var18 + (var6 - var18) * var17 + var24.getFrontOffsetX() * 2.0E-4F);
                  var13[var16 + 1] = Float.floatToRawIntBits(var19 + (var7 - var19) * var17 + var24.getFrontOffsetY() * 2.0E-4F);
                  var13[var16 + 2] = Float.floatToRawIntBits(var20 + (var8 - var20) * var17 + var24.getFrontOffsetZ() * 2.0E-4F);
                  var13[var16 + 3] = var10;
                  var13[var16 + 4] = Float.floatToRawIntBits(var3.getInterpolatedU(8.0));
                  var13[var16 + 5] = Float.floatToRawIntBits(var3.getInterpolatedV(8.0));
               }

               var0.add(new BakedQuad(var13, -1, var24));
            }
         }
      }
   }

   private OverlayModelCache() {
   }

   private static final class a implements IBakedModel {
      final List<BakedQuad>[] faces = new List[6];

      a(int var1) {
         for (EnumFacing var5 : EnumFacing.values()) {
            int[] var6 = new int[28];

            for (int var7 = 0; var7 < 4; var7++) {
               float var8 = var7 != 1 && var7 != 2 ? 0.0F : 1.0F;
               float var9 = var7 >= 2 ? 1.0F : 0.0F;
               float var10;
               float var11;
               float var12;
               switch (var5) {
                  case DOWN:
                     var10 = var8;
                     var11 = 0.0F;
                     var12 = var9;
                     break;
                  case UP:
                     var10 = var8;
                     var11 = 1.0F;
                     var12 = 1.0F - var9;
                     break;
                  case NORTH:
                     var10 = 1.0F - var8;
                     var11 = var9;
                     var12 = 0.0F;
                     break;
                  case SOUTH:
                     var10 = var8;
                     var11 = var9;
                     var12 = 1.0F;
                     break;
                  case WEST:
                     var10 = 0.0F;
                     var11 = var9;
                     var12 = var8;
                     break;
                  default:
                     var10 = 1.0F;
                     var11 = var9;
                     var12 = 1.0F - var8;
               }

               var6[var7 * 7] = Float.floatToRawIntBits(var10);
               var6[var7 * 7 + 1] = Float.floatToRawIntBits(var11);
               var6[var7 * 7 + 2] = Float.floatToRawIntBits(var12);
               var6[var7 * 7 + 3] = -1;
            }

            ArrayList var13 = new ArrayList(4);
            OverlayModelCache.b(var13, new BakedQuad(var6, -1, var5), var1);
            this.faces[var5.ordinal()] = Collections.unmodifiableList(var13);
         }
      }

      public List<BakedQuad> getFaceQuads(EnumFacing var1) {
         return this.faces[var1.ordinal()];
      }

      public List<BakedQuad> getGeneralQuads() {
         return Collections.emptyList();
      }

      public boolean isAmbientOcclusion() {
         return false;
      }

      public boolean isGui3d() {
         return true;
      }

      public boolean isBuiltInRenderer() {
         return false;
      }

      public TextureAtlasSprite getParticleTexture() {
         return com.cheatbreaker.client.kazerui.module.modules.visual.OverlayWhiteSprite.current;
      }

      public ItemCameraTransforms getItemCameraTransforms() {
         return ItemCameraTransforms.DEFAULT;
      }
   }

   private static final class b {
      long revision = Long.MIN_VALUE;
      long epoch = Long.MIN_VALUE;
      final IdentityHashMap<IBakedModel, IBakedModel[]> variants = new IdentityHashMap<>();

      private b() {
      }
   }

   private static final class c implements IBakedModel {
      final IBakedModel original;
      final List<BakedQuad>[] faces;
      final List<BakedQuad> general;

      c(IBakedModel var1, float var2, int var3, boolean var4) {
         this.original = var1;
         this.faces = new List[6];

         for (EnumFacing var8 : EnumFacing.values()) {
            this.faces[var8.ordinal()] = OverlayModelCache.b(var1.getFaceQuads(var8), var2, var3, var4);
         }

         this.general = OverlayModelCache.b(var1.getGeneralQuads(), var2, var3, var4);
      }

      public List<BakedQuad> getFaceQuads(EnumFacing var1) {
         return this.faces[var1.ordinal()];
      }

      public List<BakedQuad> getGeneralQuads() {
         return this.general;
      }

      public boolean isAmbientOcclusion() {
         return this.original.isAmbientOcclusion();
      }

      public boolean isGui3d() {
         return this.original.isGui3d();
      }

      public boolean isBuiltInRenderer() {
         return this.original.isBuiltInRenderer();
      }

      public TextureAtlasSprite getParticleTexture() {
         return this.original.getParticleTexture();
      }

      public ItemCameraTransforms getItemCameraTransforms() {
         return this.original.getItemCameraTransforms();
      }
   }
}
