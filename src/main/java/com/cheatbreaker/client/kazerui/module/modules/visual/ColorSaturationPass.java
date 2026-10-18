package com.cheatbreaker.client.kazerui.module.modules.visual;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;

final class ColorSaturationPass {
   private int program;
   private int diffuse;
   private int hue;
   private int saturation;
   private int brightness;
   private int contrast;
   private int texture;
   private int capture;
   private int fbo;
   private int width;
   private int height;
   private boolean failed;

   private static int a(int var0, String var1) {
      int var2 = GL20.glCreateShader(var0);
      GL20.glShaderSource(var2, var1);
      GL20.glCompileShader(var2);
      if (GL20.glGetShaderi(var2, 35713) == 0) {
         String var3 = GL20.glGetShaderInfoLog(var2, 4096);
         GL20.glDeleteShader(var2);
         throw new IllegalStateException(var3);
      } else {
         return var2;
      }
   }

   private void b() throws IOException {
      if (this.program == 0) {
         String var1;
         try (InputStream var2 = com.cheatbreaker.client.kazerui.module.modules.visual.ColorSaturationPass.class
               .getResourceAsStream("/assets/kazerui/shaders/post/color_saturation.glsl")) {
            if (var2 == null) {
               throw new FileNotFoundException("color_saturation.glsl");
            }

            ByteArrayOutputStream var4 = new ByteArrayOutputStream();
            byte[] var5 = new byte[4096];

            int var6;
            while ((var6 = var2.read(var5)) != -1) {
               var4.write(var5, 0, var6);
            }

            var1 = new String(var4.toByteArray(), StandardCharsets.UTF_8);
         }

         int var24 = 0;
         int var25 = 0;
         int var26 = 0;

         try {
            var24 = a(35633, "#version 120\nvarying vec2 uv;void main(){gl_Position=vec4(gl_Vertex.xy*2.0-1.0,0.2,1.0);uv=gl_Vertex.xy;}");
            var26 = a(
               35632,
               "#version 120\nvarying vec2 uv;uniform sampler2D DiffuseSampler;uniform float Hue,Saturation,Brightness,Contrast;\n#define SAMPLE texture2D\n#define OUT_COLOR gl_FragColor\n"
                  + var1
            );
            var25 = GL20.glCreateProgram();
            GL20.glAttachShader(var25, var24);
            GL20.glAttachShader(var25, var26);
            GL20.glLinkProgram(var25);
            if (GL20.glGetProgrami(var25, 35714) == 0) {
               throw new IllegalStateException(GL20.glGetProgramInfoLog(var25, 4096));
            }

            this.program = var25;
            var25 = 0;
            this.diffuse = GL20.glGetUniformLocation(this.program, "DiffuseSampler");
            this.hue = GL20.glGetUniformLocation(this.program, "Hue");
            this.saturation = GL20.glGetUniformLocation(this.program, "Saturation");
            this.brightness = GL20.glGetUniformLocation(this.program, "Brightness");
            this.contrast = GL20.glGetUniformLocation(this.program, "Contrast");
         } finally {
            if (var25 != 0) {
               GL20.glDeleteProgram(var25);
            }

            if (var24 != 0) {
               GL20.glDeleteShader(var24);
            }

            if (var26 != 0) {
               GL20.glDeleteShader(var26);
            }
         }
      }
   }

   private static int a(int var0, int var1) {
      int var2 = GL11.glGenTextures();
      GL11.glBindTexture(3553, var2);
      GL11.glTexParameteri(3553, 10241, 9729);
      GL11.glTexParameteri(3553, 10240, 9729);
      GL11.glTexParameteri(3553, 10242, 33071);
      GL11.glTexParameteri(3553, 10243, 33071);
      GL11.glTexImage2D(3553, 0, 32856, var0, var1, 0, 6408, 5121, (ByteBuffer)null);
      return var2;
   }

   private void b(int var1, int var2) {
      if (this.width != var1 || this.height != var2 || this.texture == 0) {
         this.c();
         this.width = var1;
         this.height = var2;
         this.texture = a(var1, var2);
         this.capture = a(var1, var2);
         this.fbo = OpenGlHelper.glGenFramebuffers();
         OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.fbo);
         OpenGlHelper.glFramebufferTexture2D(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_COLOR_ATTACHMENT0, 3553, this.texture, 0);
         if (OpenGlHelper.glCheckFramebufferStatus(OpenGlHelper.GL_FRAMEBUFFER) != OpenGlHelper.GL_FRAMEBUFFER_COMPLETE) {
            throw new IllegalStateException("Color framebuffer incomplete");
         }
      }
   }

   private static void a(boolean var0) {
      GL11.glBegin(7);
      if (var0) {
         GL11.glTexCoord2f(0.0F, 0.0F);
      }

      GL11.glVertex2f(0.0F, 0.0F);
      if (var0) {
         GL11.glTexCoord2f(1.0F, 0.0F);
      }

      GL11.glVertex2f(1.0F, 0.0F);
      if (var0) {
         GL11.glTexCoord2f(1.0F, 1.0F);
      }

      GL11.glVertex2f(1.0F, 1.0F);
      if (var0) {
         GL11.glTexCoord2f(0.0F, 1.0F);
      }

      GL11.glVertex2f(0.0F, 1.0F);
      GL11.glEnd();
   }

   void a(Minecraft var1, float var2, float var3, float var4, float var5) {
      if (!this.failed && OpenGlHelper.framebufferSupported && GLContext.getCapabilities().OpenGL20 && var1.displayWidth > 0 && var1.displayHeight > 0) {
         int var6 = GL11.glGetInteger(36006);
         int var7 = GL11.glGetInteger(34016);
         int var8 = GL11.glGetInteger(35725);
         int var9 = GL11.glGetInteger(2976);
         GL11.glPushAttrib(1048575);
         GL11.glMatrixMode(5889);
         GL11.glPushMatrix();
         GL11.glLoadIdentity();
         GL11.glOrtho(0.0, 1.0, 0.0, 1.0, -1.0, 1.0);
         GL11.glMatrixMode(5888);
         GL11.glPushMatrix();
         GL11.glLoadIdentity();

         try {
            this.b();
            GL13.glActiveTexture(OpenGlHelper.defaultTexUnit + 1);
            GL11.glDisable(3553);
            GL13.glActiveTexture(OpenGlHelper.defaultTexUnit);
            this.b(var1.displayWidth, var1.displayHeight);
            GL11.glViewport(0, 0, this.width, this.height);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glDisable(3042);
            GL11.glDisable(3008);
            GL11.glDisable(2884);
            GL11.glDisable(2896);
            GL11.glDisable(2912);
            GL11.glDisable(3089);
            GL11.glDisable(2960);
            GL11.glEnable(3553);
            GL11.glColorMask(true, true, true, true);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, var6);
            int var10;
            if (OpenGlHelper.isFramebufferEnabled()) {
               var10 = var1.getFramebuffer().framebufferTexture;
            } else {
               var10 = this.capture;
               GL11.glBindTexture(3553, var10);
               GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, this.width, this.height);
            }

            OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.fbo);
            GL20.glUseProgram(this.program);
            GL11.glBindTexture(3553, var10);
            GL20.glUniform1i(this.diffuse, 0);
            GL20.glUniform1f(this.hue, var2);
            GL20.glUniform1f(this.saturation, var3);
            GL20.glUniform1f(this.brightness, var4);
            GL20.glUniform1f(this.contrast, var5);
            a(false);
            GL20.glUseProgram(0);
            OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, var6);
            GL11.glBindTexture(3553, this.texture);
            GL11.glTexEnvi(8960, 8704, 7681);
            a(true);
         } catch (Exception var14) {
            this.failed = true;
            this.c();
            LogManager.getLogger("Kazer").warn("Color Saturation unavailable", var14);
         } finally {
            GL20.glUseProgram(var8);
            GL13.glActiveTexture(var7);
            GL11.glMatrixMode(5888);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5889);
            GL11.glPopMatrix();
            GL11.glMatrixMode(var9);
            OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, var6);
            GL11.glPopAttrib();
         }
      }
   }

   private void c() {
      if (this.fbo != 0) {
         OpenGlHelper.glDeleteFramebuffers(this.fbo);
      }

      if (this.texture != 0) {
         GL11.glDeleteTextures(this.texture);
      }

      if (this.capture != 0) {
         GL11.glDeleteTextures(this.capture);
      }

      this.fbo = this.texture = this.capture = this.width = this.height = 0;
   }

   void a() {
      this.c();
      if (this.program != 0) {
         GL20.glDeleteProgram(this.program);
      }

      this.program = 0;
      this.failed = false;
   }
}
