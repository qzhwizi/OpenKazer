package com.cheatbreaker.client.module.modules;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.settings.GameSettings.Options;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class TeamViewRenderer {
   public FloatBuffer modelViewMatrixBuffer = BufferUtils.createFloatBuffer(16);
   public FloatBuffer projectionMatrixBuffer = BufferUtils.createFloatBuffer(16);
   private List<com.cheatbreaker.client.util.timing.TeammateMarker> teammates;
   private final int[] IIIIllIlIIIllIlllIlllllIl = new int[]{-15007996, -43234, -3603713, -16580641, -8912129, -16601345, -2786, -64828, -15629042, -10744187};
   private boolean IIIIllIIllIIIIllIllIIIlIl = false;
   private Minecraft minecraft = Minecraft.getMinecraft();

   public TeamViewRenderer() {
      this.teammates = new ArrayList<>();
   }

   public double a(double var1, double var3, double var5) {
      double var7 = var1 - this.minecraft.thePlayer.posX;
      double var9 = var3 - this.minecraft.thePlayer.posY;
      double var11 = var5 - this.minecraft.thePlayer.posZ;
      return Math.sqrt(var7 * var7 + var9 * var9 + var11 * var11);
   }

   private void a(com.cheatbreaker.client.event.event.RenderHudEvent var1) {
      if (!this.teammates.isEmpty()) {
         IntBuffer var2 = BufferUtils.createIntBuffer(16);
         GL11.glGetInteger(2978, var2);
         float var3 = (float)(
               this.minecraft.thePlayer.lastTickPosX
                  + (this.minecraft.thePlayer.posX - this.minecraft.thePlayer.lastTickPosX) * cn.kazer.client189.render.ClientBridge.partialTicks
            )
            - (float)Minecraft.getMinecraft().getRenderManager().viewerPosX;
         float var4 = (float)(
               this.minecraft.thePlayer.lastTickPosY
                  + (this.minecraft.thePlayer.posY - this.minecraft.thePlayer.lastTickPosY) * cn.kazer.client189.render.ClientBridge.partialTicks
            )
            - (float)Minecraft.getMinecraft().getRenderManager().viewerPosY;
         float var5 = (float)(
               this.minecraft.thePlayer.lastTickPosZ
                  + (this.minecraft.thePlayer.posZ - this.minecraft.thePlayer.lastTickPosZ) * cn.kazer.client189.render.ClientBridge.partialTicks
            )
            - (float)Minecraft.getMinecraft().getRenderManager().viewerPosZ;
         double var6 = (this.minecraft.thePlayer.rotationPitch + 90.0F) * Math.PI / 180.0;
         double var8 = (this.minecraft.thePlayer.rotationYaw + 90.0F) * Math.PI / 180.0;
         Vec3 var10 = new Vec3(Math.sin(var6) * Math.cos(var8), Math.cos(var6), Math.sin(var6) * Math.sin(var8));
         if (this.minecraft.gameSettings.thirdPersonView == 2) {
            var10 = new Vec3(var10.xCoord * -1.0, var10.yCoord * -1.0, var10.zCoord * -1.0);
         }

         for (com.cheatbreaker.client.util.timing.TeammateMarker var12 : this.teammates) {
            EntityPlayer var13 = this.minecraft.theWorld.getPlayerEntityByName(var12.c());
            if (var13 == null) {
               if (System.currentTimeMillis() - var12.b() <= var12.d()) {
                  double var16 = var12.a().xCoord - var3;
                  double var18 = var12.a().yCoord - var4;
                  double var20 = var12.a().zCoord - var5;
                  double var22 = this.a(var12.a().xCoord, var12.a().yCoord, var12.a().zCoord);
                  double var14;
                  if (var22 > (var14 = this.minecraft.gameSettings.getOptionFloatValue(Options.RENDER_DISTANCE) * 16.0F)) {
                     var16 = var16 / var22 * var14;
                     var18 = var18 / var22 * var14;
                     var20 = var20 / var22 * var14;
                  }

                  this.a(var1.a(), var12, (float)var16, (float)var18, (float)var20, var2, var10, (int)var22);
               }
            } else if (var13 != this.minecraft.thePlayer) {
               float var24 = (float)(var13.lastTickPosX + (var13.posX - var13.lastTickPosX) * cn.kazer.client189.render.ClientBridge.partialTicks - var3);
               float var15 = (float)(var13.lastTickPosY + (var13.posY - var13.lastTickPosY) * cn.kazer.client189.render.ClientBridge.partialTicks - var4)
                  + var13.height
                  + 1.0F;
               float var25 = (float)(var13.lastTickPosZ + (var13.posZ - var13.lastTickPosZ) * cn.kazer.client189.render.ClientBridge.partialTicks - var5);
               double var17 = this.a(var13.posX, var13.posY, var13.posZ);
               this.a(var1.a(), var12, var24, var15, var25, var2, var10, (int)var17);
            }
         }
      }
   }

   private void a(ScaledResolution var1, com.cheatbreaker.client.util.timing.TeammateMarker var2, float var3, float var4, float var5, IntBuffer var6, Vec3 var7, int var8) {
      Vec3 var9 = new Vec3(var3, var4, var5);
      double var10 = var9.lengthVector();
      if (var9.dotProduct(var9 = var9.normalize()) <= 0.02) {
         double var12 = 1.5533430342749535;
         double var14 = Math.sin(1.5533430342749535);
         double var16 = Math.cos(1.5533430342749535);
         Vec3 var18 = var9.crossProduct(var9);
         double var19 = var18.xCoord;
         double var21 = var18.yCoord;
         double var23 = var18.zCoord;
         double var25 = var16 + var19 * var19 * (1.0 - var16);
         double var27 = var19 * var21 * (1.0 - var16) - var23 * var14;
         double var29 = var19 * var23 * (1.0 - var16) + var21 * var14;
         double var31 = var21 * var19 * (1.0 - var16) + var23 * var14;
         double var33 = var16 + var21 * var21 * (1.0 - var16);
         double var35 = var21 * var23 * (1.0 - var16) - var19 * var14;
         double var37 = var23 * var19 * (1.0 - var16) - var21 * var14;
         double var39 = var23 * var21 * (1.0 - var16) + var19 * var14;
         double var41 = var16 + var23 * var23 * (1.0 - var16);
         var3 = (float)(var10 * (var25 * var9.xCoord + var27 * var9.yCoord + var29 * var9.zCoord));
         var4 = (float)(var10 * (var31 * var9.xCoord + var33 * var9.yCoord + var35 * var9.zCoord));
         var5 = (float)(var10 * (var37 * var9.xCoord + var39 * var9.yCoord + var41 * var9.zCoord));
      }

      FloatBuffer var44 = BufferUtils.createFloatBuffer(3);
      GLU.gluProject(var3, var4, var5, this.modelViewMatrixBuffer, this.projectionMatrixBuffer, var6, var44);
      float var13 = var44.get(0) / var1.getScaleFactor();
      float var45 = var44.get(1) / var1.getScaleFactor();
      com.cheatbreaker.client.util.timing.ScreenEdge var15 = null;
      byte var46 = 8;
      byte var17 = 10;
      int var47 = -4 - var17;
      float var48 = var1.getScaledHeight() - var45;
      if (var48 < 0.0F) {
         var15 = com.cheatbreaker.client.util.timing.ScreenEdge.lIIIIlIIllIIlIIlIIIlIIllI;
         var45 = var1.getScaledHeight() - 6;
      } else if (var48 > var1.getScaledHeight() - var17) {
         var15 = com.cheatbreaker.client.util.timing.ScreenEdge.IlllIIIlIlllIllIlIIlllIlI;
         var45 = 6.0F;
      }

      if (var13 - var46 < 0.0F) {
         var15 = com.cheatbreaker.client.util.timing.ScreenEdge.lIIIIIIIIIlIllIIllIlIIlIl;
         var13 = 6.0F;
      } else if (var13 > var1.getScaledWidth() - var46) {
         var15 = com.cheatbreaker.client.util.timing.ScreenEdge.IIIIllIlIIIllIlllIlllllIl;
         var13 = var1.getScaledWidth() - 6;
      }

      GL11.glPushMatrix();
      GL11.glTranslatef(var13, var1.getScaledHeight() - var45, 0.0F);
      if (var15 != null) {
         if ((Boolean)com.cheatbreaker.client.CheatBreakerClient.N().j().showOffScreenMarker.d()) {
            this.a(var2, var15, 0.0F, 0.0F);
         }
      } else {
         this.a(var2, var46, var47, var17);
         if (var8 > 40 && (Boolean)com.cheatbreaker.client.CheatBreakerClient.N().j().showDistance.d()) {
            this.minecraft.fontRendererObj.drawString("(" + var8 + "m)", 0, 10, -1);
         }
      }

      GL11.glPopMatrix();
   }

   private void a(com.cheatbreaker.client.util.timing.TeammateMarker var1, com.cheatbreaker.client.util.timing.ScreenEdge var2, float var3, float var4) {
      cn.kazer.client189.render.ImmediateTessellator var5 = cn.kazer.client189.render.ImmediateTessellator.instance;
      cn.kazer.client189.render.GlStateBridge.a(3042);
      cn.kazer.client189.render.GlStateBridge.b(3553);
      OpenGlHelper.glBlendFunc(770, 771, 1, 0);
      if (var1.e()) {
         cn.kazer.client189.render.GlStateBridge.a(0.0F, 0.0F, 1.0F, 0.66F);
      } else {
         Color var6 = var1.f();
         cn.kazer.client189.render.GlStateBridge.a(var6.getRed() / 255.0F, var6.getGreen() / 255.0F, var6.getBlue() / 255.0F, 0.66F);
      }

      float var8 = 8.0F;
      float var7 = 10.0F;
      GL11.glPushMatrix();
      GL11.glTranslatef(var3, var4, 0.0F);
      switch (var2) {
         case lIIIIIIIIIlIllIIllIlIIlIl:
            var5.a();
            var5.a((double)(var8 / 2.0F), (double)(var7 / 2.0F), 0.0);
            var5.a((double)(-var8 / 2.0F), 0.0, 0.0);
            var5.a((double)(var8 / 2.0F), (double)(-var7 / 2.0F), 0.0);
            var5.a((double)(-var8 / 2.0F), 0.0, 0.0);
            var5.b();
            break;
         case IIIIllIlIIIllIlllIlllllIl:
            var5.a();
            var5.a((double)(-var8 / 2.0F), (double)(var7 / 2.0F), 0.0);
            var5.a((double)(var8 / 2.0F), 0.0, 0.0);
            var5.a((double)(-var8 / 2.0F), (double)(-var7 / 2.0F), 0.0);
            var5.a((double)(var8 / 2.0F), 0.0, 0.0);
            var5.b();
            break;
         case IlllIIIlIlllIllIlIIlllIlI:
            var5.a();
            var5.a((double)(-var8 / 2.0F), (double)(-var7 / 2.0F), 0.0);
            var5.a(0.0, (double)(var7 / 2.0F), 0.0);
            var5.a((double)(var8 / 2.0F), (double)(-var7 / 2.0F), 0.0);
            var5.a(0.0, (double)(var7 / 2.0F), 0.0);
            var5.b();
            break;
         case lIIIIlIIllIIlIIlIIIlIIllI:
            var5.a();
            var5.a((double)(-var8 / 2.0F), (double)(var7 / 2.0F), 0.0);
            var5.a(0.0, (double)(-var7 / 2.0F), 0.0);
            var5.a((double)(var8 / 2.0F), (double)(var7 / 2.0F), 0.0);
            var5.a(0.0, (double)(-var7 / 2.0F), 0.0);
            var5.b();
      }

      GL11.glPopMatrix();
      cn.kazer.client189.render.GlStateBridge.a(3553);
      cn.kazer.client189.render.GlStateBridge.b(3042);
   }

   private void a(com.cheatbreaker.client.util.timing.TeammateMarker var1, float var2, float var3, float var4) {
      cn.kazer.client189.render.ImmediateTessellator var5 = cn.kazer.client189.render.ImmediateTessellator.instance;
      cn.kazer.client189.render.GlStateBridge.a(3042);
      cn.kazer.client189.render.GlStateBridge.b(3553);
      OpenGlHelper.glBlendFunc(770, 771, 1, 0);
      if (var1.e()) {
         cn.kazer.client189.render.GlStateBridge.a(0.0F, 0.0F, 1.0F, 0.66F);
      } else {
         Color var6 = var1.f();
         cn.kazer.client189.render.GlStateBridge.a(var6.getRed() / 255.0F, var6.getGreen() / 255.0F, var6.getBlue() / 255.0F, 0.66F);
      }

      GL11.glPushMatrix();
      GL11.glScalef(0.6F, 0.6F, 0.6F);
      GL11.glRotatef(45.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef(var2 * 2.0F, 0.0F, 0.0F);
      GL11.glRotatef(90.0F, 0.0F, 0.0F, -1.0F);
      var5.a();
      var5.a((double)(-var2), (double)var3, 0.0);
      var5.a((double)(-var2), (double)(var3 + var4 / 2.0F), 0.0);
      var5.a((double)var2, (double)(var3 + var4 / 2.0F), 0.0);
      var5.a((double)var2, (double)var3, 0.0);
      var5.b();
      GL11.glRotatef(90.0F, 0.0F, 0.0F, -1.0F);
      GL11.glTranslatef(var2 * 2.0F + 1.0F, var4 / 2.0F + 1.0F, 0.0F);
      var5.a();
      var5.a((double)(-var2 / 2.0F + 1.0F), (double)var3, 0.0);
      var5.a((double)(-var2 / 2.0F + 1.0F), (double)(var3 + var4 / 2.0F), 0.0);
      var5.a((double)var2, (double)(var3 + var4 / 2.0F), 0.0);
      var5.a((double)var2, (double)var3, 0.0);
      var5.b();
      GL11.glPopMatrix();
      cn.kazer.client189.render.GlStateBridge.a(3553);
      cn.kazer.client189.render.GlStateBridge.b(3042);
   }

   public List<com.cheatbreaker.client.util.timing.TeammateMarker> a() {
      return this.teammates;
   }

   private void a(com.cheatbreaker.client.event.event.ClearTeammatesEvent var1) {
      this.teammates.clear();
   }

   public com.cheatbreaker.client.util.timing.TeammateMarker a(String var1) {
      for (com.cheatbreaker.client.util.timing.TeammateMarker var3 : this.teammates) {
         if (var3.c().equals(var1)) {
            return var3;
         }
      }

      return null;
   }

   public int[] b() {
      return this.IIIIllIlIIIllIlllIlllllIl;
   }

   public boolean c() {
      return this.IIIIllIIllIIIIllIllIIIlIl;
   }

   public void a(boolean var1) {
      if (var1 && !this.IIIIllIIllIIIIllIllIIIlIl) {
         this.IIIIllIIllIIIIllIllIIIlIl = true;
         com.cheatbreaker.client.CheatBreakerClient.N().m().a(com.cheatbreaker.client.event.event.RenderHudEvent.class, this::a);
         com.cheatbreaker.client.CheatBreakerClient.N().m().a(com.cheatbreaker.client.event.event.ClearTeammatesEvent.class, this::a);
      } else if (!var1 && this.IIIIllIIllIIIIllIllIIIlIl) {
         this.IIIIllIIllIIIIllIllIIIlIl = false;
         com.cheatbreaker.client.CheatBreakerClient.N().m().b(com.cheatbreaker.client.event.event.RenderHudEvent.class, this::a);
         com.cheatbreaker.client.CheatBreakerClient.N().m().b(com.cheatbreaker.client.event.event.ClearTeammatesEvent.class, this::a);
      }
   }
}
