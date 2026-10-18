package com.cheatbreaker.client.module.modules.shader;

import java.nio.ByteBuffer;
import javax.vecmath.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

public final class MotionBlurPipeline {
   private static final Logger LOGGER = LogManager.getLogger(com.cheatbreaker.client.module.modules.shader.MotionBlurPipeline.class);
   private boolean unsupported;
   private ShaderManager shaderMax;
   private ShaderManager shaderMix;
   private int prevTexture = -1;
   private int prevFramebuffer = -1;
   private int swapTexture = -1;
   private int swapFramebuffer = -1;
   private int currentTexture = -1;
   static final int GL_CONSTANT_ALPHA_BLEND = 32771;
   static final int GL_ONE_MINUS_CONSTANT_ALPHA_BLEND = 32772;

   public void a(Minecraft var1, com.cheatbreaker.client.module.modules.shader.AccumulationState var2, com.cheatbreaker.client.module.modules.shader.MotionBlurMode var3, int var4) {
      if (var3 == MotionBlurMode.MONKEY) {
         var3 = MotionBlurMode.OLD;
      }

      if (!this.unsupported) {
         if (OpenGlHelper.framebufferSupported && OpenGlHelper.shadersSupported) {
            int var5 = var1.displayWidth;
            int var6 = var1.displayHeight;
            if (var5 > 0 && var6 > 0) {
               com.cheatbreaker.client.module.modules.shader.MotionBlurPipeline.a var7 = new com.cheatbreaker.client.module.modules.shader.MotionBlurPipeline.a();

               try {
                  if (var2.a(var5, var6)) {
                     this.c();
                  }

                  if (this.prevTexture < 0) {
                     this.c(var5, var6);
                  }

                  if (this.shaderMax == null) {
                     this.shaderMax = new ShaderManager(var1.getResourceManager(), "motionblur");
                     this.shaderMix = new ShaderManager(var1.getResourceManager(), "motionblur_mix");
                  }

                  if (!var2.d()) {
                     if (var3 == MotionBlurMode.MOULBERRY) {
                        this.a(var1, var5, var6, com.cheatbreaker.client.module.modules.shader.BlurAmountUtil.f(var4));
                        return;
                     } else {
                        boolean var8 = var3 == MotionBlurMode.PHOSPHOR;
                        float var9 = var8 ? com.cheatbreaker.client.module.modules.shader.BlurAmountUtil.d(var4) : com.cheatbreaker.client.module.modules.shader.BlurAmountUtil.c(var4);
                        if (OpenGlHelper.isFramebufferEnabled()) {
                           this.a(var1, var5, var6, var8, var9);
                        } else {
                           this.a(var5, var6, var8, var9);
                        }

                        return;
                     }
                  }

                  this.a(var1, var5, var6);
                  var2.e();
               } catch (Throwable var13) {
                  LOGGER.warn("Motion blur disabled after a render failure.", var13);
                  this.unsupported = true;
                  this.c();
                  this.shaderMax = null;
                  this.shaderMix = null;
                  return;
               } finally {
                  var7.a();
               }
            }
         } else {
            this.unsupported = true;
         }
      }
   }

   private void a(Minecraft var1, int var2, int var3, boolean var4, float var5) {
      Framebuffer var6 = var1.getFramebuffer();
      this.a(this.swapFramebuffer, var2, var3);
      this.a(var6.framebufferTexture, var2, var3, var4, var5);
      this.a(this.prevFramebuffer, var2, var3);
      b(this.swapTexture, var2, var3, false, 1.0F);
      OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, var6.framebufferObject);
      GL11.glViewport(0, 0, var2, var3);
      b(this.swapTexture, var2, var3, false, 1.0F);
   }

   private void a(int var1, int var2, boolean var3, float var4) {
      OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
      GL11.glViewport(0, 0, var1, var2);
      cn.kazer.client189.render.GlStateBridge.a(3553, this.currentTexture);
      GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, var1, var2);
      this.a(this.currentTexture, var1, var2, var3, var4);
      cn.kazer.client189.render.GlStateBridge.a(3553, this.prevTexture);
      GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, var1, var2);
      cn.kazer.client189.render.GlStateBridge.a(3553, 0);
   }

   static Matrix4f a(int var0, int var1) {
      Matrix4f var2 = new Matrix4f();
      var2.setIdentity();
      var2.m00 = 2.0F / var0;
      var2.m11 = 2.0F / -var1;
      var2.m22 = -0.0020001999F;
      var2.m33 = 1.0F;
      var2.m03 = -1.0F;
      var2.m13 = 1.0F;
      var2.m23 = -1.0001999F;
      return var2;
   }

   static void b(int var0, int var1) {
      Tessellator var2 = Tessellator.getInstance();
      WorldRenderer var3 = var2.getWorldRenderer();
      var3.begin(7, DefaultVertexFormats.POSITION_COLOR);
      var3.pos(0.0, var1, 500.0).color(255, 255, 255, 255).endVertex();
      var3.pos(var0, var1, 500.0).color(255, 255, 255, 255).endVertex();
      var3.pos(var0, 0.0, 500.0).color(255, 255, 255, 255).endVertex();
      var3.pos(0.0, 0.0, 500.0).color(255, 255, 255, 255).endVertex();
      var2.draw();
   }

   private void a(int var1, int var2, int var3, boolean var4, float var5) {
      ShaderManager var6 = var4 ? this.shaderMix : this.shaderMax;
      Matrix4f var7 = a(var2, var3);
      var6.addSamplerTexture("DiffuseSampler", var1);
      var6.addSamplerTexture("PrevSampler", this.prevTexture);
      var6.getShaderUniformOrDefault("ProjMat").set(cn.kazer.client189.render.ClientBridge.a(var7));
      var6.getShaderUniformOrDefault("InSize").set(var2, var3);
      var6.getShaderUniformOrDefault("OutSize").set(var2, var3);
      if (var4) {
         var6.getShaderUniformOrDefault("Weight").set(var5);
      } else {
         var6.getShaderUniformOrDefault("Phosphor").set(var5, var5, var5);
      }

      cn.kazer.client189.render.GlStateBridge.a(true, true, true, false);
      cn.kazer.client189.render.GlStateBridge.b(2929);
      cn.kazer.client189.render.GlStateBridge.a(false);
      GL11.glViewport(0, 0, var2, var3);
      var6.useShader();
      b(var2, var3);
      var6.endShader();
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GlStateManager.disableTexture2D();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      cn.kazer.client189.render.GlStateBridge.a(true);
      cn.kazer.client189.render.GlStateBridge.a(true, true, true, true);
   }

   private static void b(int var0, int var1, int var2, boolean var3, float var4) {
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GlStateManager.disableTexture2D();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      cn.kazer.client189.render.GlStateBridge.a(true, true, true, false);
      cn.kazer.client189.render.GlStateBridge.b(2929);
      cn.kazer.client189.render.GlStateBridge.a(false);
      GL11.glMatrixMode(5889);
      GL11.glLoadIdentity();
      GL11.glOrtho(0.0, var1, var2, 0.0, 1000.0, 3000.0);
      GL11.glMatrixMode(5888);
      GL11.glLoadIdentity();
      GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
      GL11.glViewport(0, 0, var1, var2);
      cn.kazer.client189.render.GlStateBridge.b(2884);
      cn.kazer.client189.render.GlStateBridge.a(3553);
      cn.kazer.client189.render.GlStateBridge.b(2896);
      cn.kazer.client189.render.GlStateBridge.b(3008);
      if (var3) {
         cn.kazer.client189.render.GlStateBridge.a(3042);
         GL14.glBlendColor(1.0F, 1.0F, 1.0F, var4);
         OpenGlHelper.glBlendFunc(a(), b(), 1, 1);
         cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
      } else {
         cn.kazer.client189.render.GlStateBridge.b(3042);
         cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
      }

      cn.kazer.client189.render.GlStateBridge.a(3553, var0);
      cn.kazer.client189.render.ImmediateTessellator var5 = cn.kazer.client189.render.ImmediateTessellator.instance;
      var5.a();
      var5.b(-1);
      var5.a(0.0, var2, 0.0, 0.0, 0.0);
      var5.a(var1, var2, 0.0, 1.0, 0.0);
      var5.a(var1, 0.0, 0.0, 1.0, 1.0);
      var5.a(0.0, 0.0, 0.0, 0.0, 1.0);
      var5.b();
      cn.kazer.client189.render.GlStateBridge.a(3553, 0);
      cn.kazer.client189.render.GlStateBridge.b(3042);
      cn.kazer.client189.render.GlStateBridge.a(2884);
      cn.kazer.client189.render.GlStateBridge.a(true);
      cn.kazer.client189.render.GlStateBridge.a(true, true, true, true);
   }

   static int a() {
      return 32771;
   }

   static int b() {
      return 32772;
   }

   private void a(Minecraft var1, int var2, int var3) {
      if (OpenGlHelper.isFramebufferEnabled()) {
         this.a(this.prevFramebuffer, var2, var3);
         b(var1.getFramebuffer().framebufferTexture, var2, var3, false, 1.0F);
      } else {
         OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
         cn.kazer.client189.render.GlStateBridge.a(3553, this.prevTexture);
         GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, var2, var3);
         cn.kazer.client189.render.GlStateBridge.a(3553, 0);
      }
   }

   private void a(Minecraft var1, int var2, int var3, float var4) {
      boolean var5 = OpenGlHelper.isFramebufferEnabled();
      int var6;
      if (var5) {
         var6 = var1.getFramebuffer().framebufferTexture;
      } else {
         OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
         GL11.glViewport(0, 0, var2, var3);
         cn.kazer.client189.render.GlStateBridge.a(3553, this.currentTexture);
         GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, var2, var3);
         var6 = this.currentTexture;
      }

      this.a(this.swapFramebuffer, var2, var3);
      b(var6, var2, var3, false, 1.0F);
      b(this.prevTexture, var2, var3, true, var4);
      if (var5) {
         OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, var1.getFramebuffer().framebufferObject);
         GL11.glViewport(0, 0, var2, var3);
      } else {
         OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
         GL11.glViewport(0, 0, var2, var3);
      }

      b(this.swapTexture, var2, var3, false, 1.0F);
      int var7 = this.prevTexture;
      this.prevTexture = this.swapTexture;
      this.swapTexture = var7;
      int var8 = this.prevFramebuffer;
      this.prevFramebuffer = this.swapFramebuffer;
      this.swapFramebuffer = var8;
   }

   private void a(int var1, int var2, int var3) {
      OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, var1);
      GL11.glViewport(0, 0, var2, var3);
   }

   private void c(int var1, int var2) {
      this.prevTexture = d(var1, var2);
      this.prevFramebuffer = a(this.prevTexture);
      this.swapTexture = d(var1, var2);
      this.swapFramebuffer = a(this.swapTexture);
      this.currentTexture = d(var1, var2);
      OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
   }

   private static int d(int var0, int var1) {
      int var2 = TextureUtil.glGenTextures();
      cn.kazer.client189.render.GlStateBridge.a(3553, var2);
      GL11.glTexParameteri(3553, 10241, 9728);
      GL11.glTexParameteri(3553, 10240, 9728);
      GL11.glTexParameteri(3553, 10242, 10496);
      GL11.glTexParameteri(3553, 10243, 10496);
      GL11.glTexImage2D(3553, 0, 32856, var0, var1, 0, 6408, 5121, (ByteBuffer)null);
      cn.kazer.client189.render.GlStateBridge.a(3553, 0);
      return var2;
   }

   private static int a(int var0) {
      int var1 = OpenGlHelper.glGenFramebuffers();
      OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, var1);
      OpenGlHelper.glFramebufferTexture2D(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_COLOR_ATTACHMENT0, 3553, var0, 0);
      int var2 = OpenGlHelper.glCheckFramebufferStatus(OpenGlHelper.GL_FRAMEBUFFER);
      if (var2 != OpenGlHelper.GL_FRAMEBUFFER_COMPLETE) {
         throw new IllegalStateException("Motion blur framebuffer incomplete, status " + var2);
      } else {
         return var1;
      }
   }

   private void c() {
      if (this.prevFramebuffer >= 0) {
         OpenGlHelper.glDeleteFramebuffers(this.prevFramebuffer);
         this.prevFramebuffer = -1;
      }

      if (this.swapFramebuffer >= 0) {
         OpenGlHelper.glDeleteFramebuffers(this.swapFramebuffer);
         this.swapFramebuffer = -1;
      }

      if (this.prevTexture >= 0) {
         TextureUtil.deleteTexture(this.prevTexture);
         this.prevTexture = -1;
      }

      if (this.swapTexture >= 0) {
         TextureUtil.deleteTexture(this.swapTexture);
         this.swapTexture = -1;
      }

      if (this.currentTexture >= 0) {
         TextureUtil.deleteTexture(this.currentTexture);
         this.currentTexture = -1;
      }
   }

   static final class a {
      private final int framebuffer = GL11.glGetInteger(36006);
      private final int matrixMode = GL11.glGetInteger(2976);
      private final boolean blend = GL11.glIsEnabled(3042);
      private final boolean alphaTest = GL11.glIsEnabled(3008);
      private final boolean depthTest = GL11.glIsEnabled(2929);
      private final boolean lighting = GL11.glIsEnabled(2896);
      private final boolean cullFace = GL11.glIsEnabled(2884);
      private final boolean depthMask = GL11.glGetBoolean(2930);
      private final int blendSrcRgb = GL11.glGetInteger(32969);
      private final int blendDstRgb = GL11.glGetInteger(32968);
      private final int blendSrcAlpha = GL11.glGetInteger(32971);
      private final int blendDstAlpha = GL11.glGetInteger(32970);
      private final int activeTexture = GL11.glGetInteger(34016);

      a() {
         cn.kazer.client189.render.GlStateBridge.c(1048575);
         GL11.glMatrixMode(5889);
         GL11.glPushMatrix();
         GL11.glMatrixMode(5888);
         GL11.glPushMatrix();
         GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      }

      void a() {
         OpenGlHelper.glBlendFunc(this.blendSrcRgb, this.blendDstRgb, this.blendSrcAlpha, this.blendDstAlpha);
         cn.kazer.client189.render.GlStateBridge.a(this.depthMask);
         a(3042, this.blend);
         a(3008, this.alphaTest);
         a(2929, this.depthTest);
         a(2896, this.lighting);
         a(2884, this.cullFace);
         GL11.glMatrixMode(5889);
         GL11.glPopMatrix();
         GL11.glMatrixMode(5888);
         GL11.glPopMatrix();
         GL11.glMatrixMode(this.matrixMode);
         cn.kazer.client189.render.GlStateBridge.a();
         OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.framebuffer);
         GlStateManager.setActiveTexture(this.activeTexture);
      }

      private static void a(int var0, boolean var1) {
         if (var1) {
            cn.kazer.client189.render.GlStateBridge.a(var0);
         } else {
            cn.kazer.client189.render.GlStateBridge.b(var0);
         }
      }
   }
}
