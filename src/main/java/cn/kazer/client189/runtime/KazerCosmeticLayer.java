package cn.kazer.client189.runtime;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderLivingEvent.Pre;

public final class KazerCosmeticLayer implements LayerRenderer<AbstractClientPlayer> {
   private static final Map<RenderPlayer, int[]> VISIBILITY = new IdentityHashMap<>();
   private final RenderPlayer renderer;
   private final Runnable drawCape;

   private KazerCosmeticLayer(RenderPlayer var1) {
      this.renderer = var1;
      this.drawCape = () -> var1.getMainModel().renderCape(0.0625F);
   }

   public static void a() {
      RenderManager var0 = Minecraft.getMinecraft().getRenderManager();

      try {
         Map var1 = (Map)var0.getClass().getMethod("getSkinMap").invoke(var0);

         for (Object var3 : var1.values()) {
            RenderPlayer var4 = (RenderPlayer)var3;
            VISIBILITY.put(var4, new int[2]);
            var4.addLayer(new KazerCosmeticLayer(var4));
         }
      } catch (ReflectiveOperationException var5) {
         throw new IllegalStateException("Forge player skin renderers unavailable", var5);
      }
   }

   public static boolean customCape(AbstractClientPlayer var0) {
      return com.cheatbreaker.client.kazerui.concurrent.rendercache.PlayerCosmeticRenderer.b(var0) != null;
   }

   public static void a(Pre var0) {
      if (var0.renderer instanceof RenderPlayer && var0.entity instanceof AbstractClientPlayer) {
         RenderPlayer var1 = (RenderPlayer)var0.renderer;
         int[] var2 = VISIBILITY.get(var1);
         if (var2 != null) {
            a(var1);
            List var3 = com.cheatbreaker.client.kazerui.concurrent.rendercache.PlayerCosmeticRenderer.a((AbstractClientPlayer)var0.entity);
            if (!var3.isEmpty() || com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayers3DModule.n()) {
               var2[0] = com.cheatbreaker.client.kazerui.concurrent.rendercache.GeckoCosmeticRenderer.a(var1.getMainModel());
               var2[1] = 1;
               com.cheatbreaker.client.kazerui.concurrent.rendercache.GeckoCosmeticRenderer.a((AbstractClientPlayer)var0.entity, var1.getMainModel(), var3);
               com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayers3DModule.a((AbstractClientPlayer)var0.entity, var1.getMainModel());
            }
         }
      }
   }

   public static void a(RenderPlayer var0) {
      int[] var1 = VISIBILITY.get(var0);
      if (var1 != null && var1[1] != 0) {
         com.cheatbreaker.client.kazerui.concurrent.rendercache.GeckoCosmeticRenderer.a(var0.getMainModel(), var1[0]);
         var1[1] = 0;
      }
   }

   @Override
   public void doRenderLayer(AbstractClientPlayer var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      a(this.renderer);
      if (!var1.isInvisible() && (!com.cheatbreaker.client.kazerui.concurrent.rendercache.PlayerCosmeticRenderer.a(var1).isEmpty() || com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayers3DModule.n())) {
         cn.kazer.client189.render.GlStateBridge.c(1048575);

         try {
            ResourceLocation var9 = com.cheatbreaker.client.kazerui.concurrent.rendercache.PlayerCosmeticRenderer.b(var1);
            if (var9 != null) {
               Minecraft.getMinecraft().getTextureManager().bindTexture(var9);
               com.cheatbreaker.client.kazerui.concurrent.rendercache.PlayerCosmeticRenderer.a(var1, var4, this.drawCape);
            }

            com.cheatbreaker.client.kazerui.concurrent.rendercache.PlayerCosmeticRenderer.a(var1, this.renderer.getMainModel(), var4);
         } finally {
            cn.kazer.client189.render.GlStateBridge.a();
         }
      }
   }

   public boolean shouldCombineTextures() {
      return false;
   }
}
