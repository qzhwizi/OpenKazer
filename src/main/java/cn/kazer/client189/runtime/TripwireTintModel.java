package cn.kazer.client189.runtime;

import com.cheatbreaker.client.kazerui.module.modules.visual.ModuleOverlay;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.RegistrySimple;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class TripwireTintModel implements IBakedModel {
   private final IBakedModel original;
   private final ThreadLocal<cn.kazer.client189.runtime.TripwireTintModel.a> local = new ThreadLocal<cn.kazer.client189.runtime.TripwireTintModel.a>() {
      protected cn.kazer.client189.runtime.TripwireTintModel.a a() {
         return new cn.kazer.client189.runtime.TripwireTintModel.a();
      }
   };

   private TripwireTintModel(IBakedModel var1) {
      this.original = var1;
   }

   public static void a(ModelBakeEvent var0) {
      if (var0.modelRegistry instanceof RegistrySimple) {
         for (ModelResourceLocation var2 : (Iterable<ModelResourceLocation>) ((RegistrySimple)var0.modelRegistry).getKeys()) {
            if (var2.getResourceDomain().equals("minecraft") && var2.getResourcePath().equals("tripwire")) {
               IBakedModel var3 = (IBakedModel)var0.modelRegistry.getObject(var2);
               if (!(var3 instanceof cn.kazer.client189.runtime.TripwireTintModel)) {
                  var0.modelRegistry.putObject(var2, new cn.kazer.client189.runtime.TripwireTintModel(var3));
               }
            }
         }
      }
   }

   private List<BakedQuad> a(List<BakedQuad> var1) {
      com.cheatbreaker.client.CheatBreakerClient var2 = com.cheatbreaker.client.CheatBreakerClient.N();
      int var3 = ModuleOverlay.stringColor(var2 != null && (Boolean)var2.globalSettings.redString.d() ? 16711680 : 16777215);
      if (var3 == 16777215) {
         return var1;
      } else {
         cn.kazer.client189.runtime.TripwireTintModel.a var4 = this.local.get();
         if (var4.color != var3) {
            var4.lists.clear();
            var4.color = var3;
         }

         List var5 = var4.lists.get(var1);
         if (var5 != null) {
            return var5;
         } else {
            ArrayList var6 = new ArrayList(var1.size());

            for (BakedQuad var8 : var1) {
               int[] var9 = (int[])var8.getVertexData().clone();
               int var10 = var9.length / 4;
               int var11 = (var3 & 0xFF) << 16 | var3 & 0xFF00 | var3 >> 16 & 0xFF;
               if (var10 >= 7 && var9.length % 4 == 0) {
                  for (int var12 = 0; var12 < 4; var12++) {
                     var9[var12 * var10 + 3] = var9[var12 * var10 + 3] & 0xFF000000 | var11;
                  }

                  BakedQuad var14 = new BakedQuad(var9, var8.getTintIndex(), var8.getFace());
                  var6.add(var14);
               } else {
                  var6.add(var8);
               }
            }

            List var13 = Collections.unmodifiableList(var6);
            if (var4.lists.size() >= 64) {
               var4.lists.clear();
            }

            var4.lists.put(var1, var13);
            return var13;
         }
      }
   }

   public List<BakedQuad> getFaceQuads(EnumFacing var1) {
      return this.a(this.original.getFaceQuads(var1));
   }

   public List<BakedQuad> getGeneralQuads() {
      return this.a(this.original.getGeneralQuads());
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

   private static final class a {
      final Map<List<BakedQuad>, List<BakedQuad>> lists = new IdentityHashMap<>();
      int color = -1;

      private a() {
      }
   }

   public static final class b {
      @SubscribeEvent
      public void bake(ModelBakeEvent var1) {
         cn.kazer.client189.runtime.TripwireTintModel.a(var1);
      }
   }
}
