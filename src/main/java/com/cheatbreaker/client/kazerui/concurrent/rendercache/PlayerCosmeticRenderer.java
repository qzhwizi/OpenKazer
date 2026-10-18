package com.cheatbreaker.client.kazerui.concurrent.rendercache;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public final class PlayerCosmeticRenderer {
   private static final com.cheatbreaker.client.kazerui.concurrent.rendercache.DragonWingModel WINGS = new com.cheatbreaker.client.kazerui.concurrent.rendercache.DragonWingModel();

   public static List<com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry> a(AbstractClientPlayer var0) {
      Minecraft var1 = Minecraft.getMinecraft();
      if (var0 instanceof com.cheatbreaker.client.kazerui.concurrent.rendercache.PreviewPlayer) {
         return ((com.cheatbreaker.client.kazerui.concurrent.rendercache.PreviewPlayer)var0).d();
      } else if (var0 == var1.thePlayer) {
         return com.cheatbreaker.client.kazerui.concurrent.EquippedCosmetics.a();
      } else {
         if (var0.getUniqueID().getLeastSignificantBits() == 0L) {
            List var2 = com.cheatbreaker.client.CheatBreakerClient.N().B().e().a(var0.getUniqueID());
            if (var2 != null) {
               return var2;
            }
         }

         return com.cheatbreaker.client.CheatBreakerClient.N().L().c(var0.getUniqueID());
      }
   }

   public static ResourceLocation b(AbstractClientPlayer var0) {
      if (var0.isInvisible()) {
         return null;
      } else if (!(var0 instanceof com.cheatbreaker.client.kazerui.concurrent.rendercache.PreviewPlayer) && !a(com.cheatbreaker.client.kazerui.concurrent.CosmeticCategory.CLOAKS)) {
         return null;
      } else {
         for (com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var2 : a(var0)) {
            if (var2.type == com.cheatbreaker.client.kazerui.concurrent.CosmeticType.CLOAK) {
               return com.cheatbreaker.client.kazerui.concurrent.CosmeticTextures.a(var2);
            }
         }

         return null;
      }
   }

   public static void a(AbstractClientPlayer var0, ModelBiped var1) {
      a(var0, var1, 0.0F);
   }

   public static void a(AbstractClientPlayer var0, ModelBiped var1, float var2) {
      if (!var0.isInvisible()) {
         com.cheatbreaker.client.kazerui.module.modules.visual.SkinLayers3DModule.b(var0, var1);
         List var3 = a(var0);
         if (!var3.isEmpty()) {
            for (com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var5 : (Iterable<com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry>) var3) {
               if (var5.type != com.cheatbreaker.client.kazerui.concurrent.CosmeticType.CLOAK
                  && (var0 instanceof com.cheatbreaker.client.kazerui.concurrent.rendercache.PreviewPlayer || a(com.cheatbreaker.client.kazerui.concurrent.CosmeticCategory.a(var5.type)))) {
                  if (var5.geckolib) {
                     GeckoCosmeticRenderer.a(var0, var1, var5, var2);
                  } else if (var5.type == com.cheatbreaker.client.kazerui.concurrent.CosmeticType.WINGS) {
                     ResourceLocation var6 = com.cheatbreaker.client.kazerui.concurrent.CosmeticTextures.a(var5);
                     if (var6 != null) {
                        GlStateManager.pushMatrix();

                        try {
                           var1.bipedBody.postRender(0.0625F);
                           if (var0.isSneaking()) {
                              GlStateManager.translate(0.0F, 0.2F, 0.0F);
                           }

                           WINGS.a(0.13F, 0.0625F, var6);
                        } finally {
                           GlStateManager.popMatrix();
                        }
                     }
                  } else {
                     com.cheatbreaker.client.kazerui.concurrent.rendercache.MeshCosmeticRenderer.a(var0, var1, var5);
                  }
               }
            }
         }
      }
   }

   private static boolean a(com.cheatbreaker.client.kazerui.concurrent.CosmeticCategory var0) {
      return com.cheatbreaker.client.kazerui.module.GlobalSettingsModule.a(var0);
   }

   public static void a(AbstractClientPlayer var0, float var1, Runnable var2) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.pushMatrix();

      try {
         GlStateManager.translate(0.0F, 0.0F, 0.125F);
         double var3 = var0.prevChasingPosX
            + (var0.chasingPosX - var0.prevChasingPosX) * var1
            - (var0.prevPosX + (var0.posX - var0.prevPosX) * var1);
         double var5 = var0.prevChasingPosY
            + (var0.chasingPosY - var0.prevChasingPosY) * var1
            - (var0.prevPosY + (var0.posY - var0.prevPosY) * var1);
         double var7 = var0.prevChasingPosZ
            + (var0.chasingPosZ - var0.prevChasingPosZ) * var1
            - (var0.prevPosZ + (var0.posZ - var0.prevPosZ) * var1);
         float var9 = var0.prevRenderYawOffset + (var0.renderYawOffset - var0.prevRenderYawOffset) * var1;
         double var10 = MathHelper.sin(var9 * (float) Math.PI / 180.0F);
         double var12 = -MathHelper.cos(var9 * (float) Math.PI / 180.0F);
         float var14 = MathHelper.clamp_float((float)var5 * 10.0F, -6.0F, 32.0F);
         float var15 = Math.max(0.0F, (float)(var3 * var10 + var7 * var12) * 100.0F);
         float var16 = (float)(var3 * var12 - var7 * var10) * 100.0F;
         float var17 = var0.prevCameraYaw + (var0.cameraYaw - var0.prevCameraYaw) * var1;
         var14 += MathHelper.sin((var0.prevDistanceWalkedModified + (var0.distanceWalkedModified - var0.prevDistanceWalkedModified) * var1) * 6.0F) * 32.0F * var17;
         if (var0.isSneaking()) {
            var14 += 25.0F;
         }

         GlStateManager.rotate(6.0F + var15 / 2.0F + var14, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(var16 / 2.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.rotate(-var16 / 2.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
         var2.run();
      } finally {
         GlStateManager.popMatrix();
      }
   }

   private PlayerCosmeticRenderer() {
   }
}
