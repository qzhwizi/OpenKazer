package com.cheatbreaker.client.kazerui.concurrent.rendercache;

import java.util.Collections;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public final class CosmeticPreviewRenderer {
   private static com.cheatbreaker.client.kazerui.concurrent.rendercache.PreviewPlayer wearer;
   private static World world;
   private static int selection = -1;
   private static String profile = "";
   private static float yaw = 20.0F;
   private static float zoom = 1.0F;
   private static final long START = System.nanoTime();

   public static void a(float var0) {
      yaw = (yaw + var0) % 360.0F;
   }

   public static void a(int var0) {
      zoom = Math.max(0.55F, Math.min(1.65F, zoom + var0 * 0.08F));
   }

   public static void a(com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var0) {
      Minecraft var1 = Minecraft.getMinecraft();
      String var2 = var1.getSession().getPlayerID();
      if (wearer == null || world != var1.theWorld || !profile.equals(var2)) {
         wearer = com.cheatbreaker.client.kazerui.concurrent.rendercache.PreviewPlayer.b();
         world = var1.theWorld;
         profile = var2;
         selection = -1;
      }

      int var3 = var0 == null ? -1 : var0.id;
      if (selection != var3) {
         selection = var3;
         wearer.a(var0 == null ? Collections.emptyList() : Collections.singletonList(var0));
      }

      float var4 = (float)(System.nanoTime() - START) / 1.0E9F * 4.0F;
      wearer.limbSwing = var4;
      wearer.prevLimbSwingAmount = wearer.limbSwingAmount = 0.65F;
      wearer.ticksExisted = (int)(var4 * 5.0F);
      RenderManager var5 = var1.getRenderManager();
      World var6 = var5.worldObj;
      float var7 = var5.playerViewY;
      Entity var8 = var5.livingPlayer;
      GameSettings var9 = var5.options;
      cn.kazer.client189.render.GlStateBridge.c(1048575);

      try {
         var5.worldObj = wearer.worldObj;
         var5.livingPlayer = wearer;
         var5.options = var1.gameSettings;
         a(var5);
      } finally {
         var5.worldObj = var6;
         var5.playerViewY = var7;
         var5.livingPlayer = var8;
         var5.options = var9;
         cn.kazer.client189.render.GlStateBridge.a();
      }
   }

   private static void a(RenderManager var0) {
      GL11.glPushMatrix();

      try {
         cn.kazer.client189.render.GlStateBridge.a(2903);
         cn.kazer.client189.render.GlStateBridge.a(2929);
         GL11.glTranslatef(421.0F, 250.0F, 50.0F);
         GL11.glScalef(-62.0F * zoom, 62.0F * zoom, 62.0F * zoom);
         GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
         RenderHelper.enableStandardItemLighting();
         GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
         wearer.renderYawOffset = wearer.prevRenderYawOffset = yaw;
         wearer.rotationYaw = wearer.prevRotationYaw = yaw;
         wearer.rotationYawHead = wearer.prevRotationYawHead = yaw;
         wearer.prevChasingPosX = wearer.chasingPosX = wearer.posX;
         wearer.prevChasingPosY = wearer.chasingPosY = wearer.posY;
         wearer.prevChasingPosZ = wearer.chasingPosZ = wearer.posZ;
         wearer.prevDistanceWalkedModified = wearer.distanceWalkedModified = 0.0F;
         wearer.prevCameraYaw = wearer.cameraYaw = 0.0F;
         var0.playerViewY = 180.0F;
         var0.renderEntityWithPosYaw(wearer, 0.0, 0.0, 0.0, 0.0F, 1.0F);
      } finally {
         RenderHelper.disableStandardItemLighting();
         GL11.glPopMatrix();
      }
   }

   private CosmeticPreviewRenderer() {
   }
}
