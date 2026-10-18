package com.cheatbreaker.client.module.modules.shader;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import javax.vecmath.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.shader.ShaderManager;
import net.minecraft.entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public final class MonkeyBlurRenderer {
   private static final Logger LOGGER = LogManager.getLogger(com.cheatbreaker.client.module.modules.shader.MonkeyBlurRenderer.class);
   private boolean unsupported;
   private boolean engaged;
   private ShaderManager shader;
   private int framebuffer = -1;
   private int colorTexture = -1;
   private int depthTexture = -1;
   private int width = -1;
   private int height = -1;
   private final com.cheatbreaker.client.module.modules.shader.CameraState camera = new com.cheatbreaker.client.module.modules.shader.CameraState();
   private final FloatBuffer projectionBuffer = BufferUtils.createFloatBuffer(16);
   private final FloatBuffer modelViewBuffer = BufferUtils.createFloatBuffer(16);
   private final float[] projectionArray = new float[16];
   private final float[] modelViewArray = new float[16];

   public void a(Minecraft var1) {
      if (!this.unsupported) {
         if (!OpenGlHelper.framebufferSupported || !OpenGlHelper.shadersSupported) {
            this.unsupported = true;
         } else if (var1.displayWidth > 0 && var1.displayHeight > 0) {
            try {
               if (var1.displayWidth != this.width || var1.displayHeight != this.height) {
                  this.a();
                  this.a(var1.displayWidth, var1.displayHeight);
               }

               if (this.shader == null) {
                  this.shader = new ShaderManager(var1.getResourceManager(), "motionblur_monkey");
               }

               this.camera.a(var1.gameSettings.thirdPersonView);
               OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.framebuffer);
               this.engaged = true;
            } catch (Throwable var3) {
               this.a(var3);
            }
         }
      }
   }

   public void a(Minecraft var1, float var2) {
      if (this.engaged) {
         this.b(var1, var2);
      }
   }

   public void a(Minecraft var1, float var2, int var3, com.cheatbreaker.client.module.modules.shader.AccumulationState var4) {
      if (this.engaged) {
         this.engaged = false;
         com.cheatbreaker.client.module.modules.shader.MotionBlurPipeline.a var5 = new com.cheatbreaker.client.module.modules.shader.MotionBlurPipeline.a();

         try {
            if (this.camera.a()) {
               this.b(var1, var2);
               this.camera.b();
            }

            if (OpenGlHelper.isFramebufferEnabled()) {
               OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, var1.getFramebuffer().framebufferObject);
            } else {
               OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
            }

            GL11.glViewport(0, 0, this.width, this.height);
            if (var4.d()) {
               var4.e();
               this.camera.b();
            }

            this.a(var3);
         } catch (Throwable var10) {
            this.a(var10);
         } finally {
            var5.a();
            OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.isFramebufferEnabled() ? var1.getFramebuffer().framebufferObject : 0);
         }
      }
   }

   private void b(Minecraft var1, float var2) {
      Entity var3 = var1.getRenderViewEntity();
      if (var3 != null) {
         ((Buffer)this.projectionBuffer).clear();
         GL11.glGetFloat(2983, this.projectionBuffer);
         ((Buffer)this.modelViewBuffer).clear();
         GL11.glGetFloat(2982, this.modelViewBuffer);

         for (int var4 = 0; var4 < 16; var4++) {
            this.projectionArray[var4] = this.projectionBuffer.get(var4);
            this.modelViewArray[var4] = this.modelViewBuffer.get(var4);
         }

         Matrix4f var9 = com.cheatbreaker.client.module.modules.shader.CameraState.a(this.projectionArray);
         Matrix4f var5 = com.cheatbreaker.client.module.modules.shader.CameraState.a(this.modelViewArray);
         float var6 = (float)(var3.lastTickPosX + (var3.posX - var3.lastTickPosX) * var2);
         float var7 = (float)(var3.lastTickPosY + (var3.posY - var3.lastTickPosY) * var2);
         float var8 = (float)(var3.lastTickPosZ + (var3.posZ - var3.lastTickPosZ) * var2);
         this.camera.a(var9, var5, var6, var7, var8);
      }
   }

   private void a(int var1) {
      ShaderManager var2 = this.shader;
      var2.addSamplerTexture("ColorTex", this.colorTexture);
      var2.addSamplerTexture("DepthTex", this.depthTexture);
      var2.getShaderUniformOrDefault("ProjMat").set(cn.kazer.client189.render.ClientBridge.a(com.cheatbreaker.client.module.modules.shader.MotionBlurPipeline.a(this.width, this.height)));
      var2.getShaderUniformOrDefault("InSize").set(this.width, this.height);
      var2.getShaderUniformOrDefault("OutSize").set(this.width, this.height);
      var2.getShaderUniformOrDefault("projectionInverse").set(cn.kazer.client189.render.ClientBridge.a(this.camera.c()));
      var2.getShaderUniformOrDefault("modelViewInverse").set(cn.kazer.client189.render.ClientBridge.a(this.camera.d()));
      var2.getShaderUniformOrDefault("previousProjection").set(cn.kazer.client189.render.ClientBridge.a(this.camera.e()));
      var2.getShaderUniformOrDefault("previousModelView").set(cn.kazer.client189.render.ClientBridge.a(this.camera.f()));
      var2.getShaderUniformOrDefault("cameraPosition").set(this.camera.g(), this.camera.h(), this.camera.i());
      var2.getShaderUniformOrDefault("previousCameraPosition").set(this.camera.j(), this.camera.k(), this.camera.l());
      var2.getShaderUniformOrDefault("strength").set(com.cheatbreaker.client.module.modules.shader.BlurAmountUtil.e(var1));
      cn.kazer.client189.render.GlStateBridge.a(true, true, true, false);
      cn.kazer.client189.render.GlStateBridge.b(2929);
      cn.kazer.client189.render.GlStateBridge.a(false);
      var2.useShader();
      com.cheatbreaker.client.module.modules.shader.MotionBlurPipeline.b(this.width, this.height);
      var2.endShader();
      cn.kazer.client189.render.GlStateBridge.a(true);
      cn.kazer.client189.render.GlStateBridge.a(true, true, true, true);
      cn.kazer.client189.render.GlStateBridge.a(2929);
      cn.kazer.client189.render.GlStateBridge.a(2884);
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
   }

   private void a(int var1, int var2) {
      this.width = var1;
      this.height = var2;
      this.colorTexture = a(var1, var2, 32856, 6408);
      this.depthTexture = a(var1, var2, 6402, 6402);
      this.framebuffer = OpenGlHelper.glGenFramebuffers();
      OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.framebuffer);
      OpenGlHelper.glFramebufferTexture2D(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_COLOR_ATTACHMENT0, 3553, this.colorTexture, 0);
      OpenGlHelper.glFramebufferTexture2D(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_DEPTH_ATTACHMENT, 3553, this.depthTexture, 0);
      int var3 = OpenGlHelper.glCheckFramebufferStatus(OpenGlHelper.GL_FRAMEBUFFER);
      OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
      if (var3 != OpenGlHelper.GL_FRAMEBUFFER_COMPLETE) {
         throw new IllegalStateException("Monkey blur framebuffer incomplete, status " + var3);
      }
   }

   private static int a(int var0, int var1, int var2, int var3) {
      int var4 = TextureUtil.glGenTextures();
      cn.kazer.client189.render.GlStateBridge.a(3553, var4);
      GL11.glTexParameteri(3553, 10241, 9728);
      GL11.glTexParameteri(3553, 10240, 9728);
      GL11.glTexParameteri(3553, 10242, 10496);
      GL11.glTexParameteri(3553, 10243, 10496);
      GL11.glTexImage2D(3553, 0, var2, var0, var1, 0, var3, 5121, (ByteBuffer)null);
      cn.kazer.client189.render.GlStateBridge.a(3553, 0);
      return var4;
   }

   private void a() {
      if (this.framebuffer >= 0) {
         OpenGlHelper.glDeleteFramebuffers(this.framebuffer);
         this.framebuffer = -1;
      }

      if (this.colorTexture >= 0) {
         TextureUtil.deleteTexture(this.colorTexture);
         this.colorTexture = -1;
      }

      if (this.depthTexture >= 0) {
         TextureUtil.deleteTexture(this.depthTexture);
         this.depthTexture = -1;
      }

      this.width = -1;
      this.height = -1;
   }

   private void a(Throwable var1) {
      LOGGER.warn("Monkey motion blur disabled after a render failure.", var1);
      this.unsupported = true;
      this.engaged = false;
      this.a();
      this.shader = null;
   }
}
