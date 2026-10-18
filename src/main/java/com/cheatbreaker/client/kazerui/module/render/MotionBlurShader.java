package com.cheatbreaker.client.kazerui.module.render;

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

public final class MotionBlurShader {
   private static final String VERTEX = "#version 120\nvarying vec2 uv;\nvoid main(){gl_Position=vec4(gl_Vertex.xy*2.0-1.0,0.2,1.0);uv=gl_Vertex.xy;}\n";
   private final String name;
   private int program;
   private int phosphor;
   private int diffuse;
   private int prevSampler;
   private int previous;
   private int scratch;
   private int capture;
   private int previousFbo;
   private int scratchFbo;
   private int width;
   private int height;
   private boolean ready;
   private boolean failed;

   public MotionBlurShader(String var1, String[] var2, String... var3) {
      this.name = var1;
   }

   public static boolean a() {
      return OpenGlHelper.framebufferSupported && GLContext.getCapabilities().OpenGL20;
   }

   private String e() throws IOException {
      String var16;
      try (InputStream var1 = com.cheatbreaker.client.kazerui.module.render.MotionBlurShader.class.getResourceAsStream("/assets/kazerui/shaders/post/" + this.name + ".glsl")) {
         if (var1 == null) {
            throw new FileNotFoundException(this.name);
         }

         ByteArrayOutputStream var3 = new ByteArrayOutputStream();
         byte[] var4 = new byte[4096];

      int var5;
         while ((var5 = var1.read(var4)) != -1) {
            var3.write(var4, 0, var5);
         }

         var16 = new String(var3.toByteArray(), StandardCharsets.UTF_8);
      }

      return var16;
   }

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

   private boolean f() {
      if (this.program != 0) {
         return true;
      } else if (this.failed) {
         return false;
      } else {
         int var1 = 0;
         int var2 = 0;
         int var3 = 0;

         boolean var4;
         try {
            var1 = a(35633, "#version 120\nvarying vec2 uv;\nvoid main(){gl_Position=vec4(gl_Vertex.xy*2.0-1.0,0.2,1.0);uv=gl_Vertex.xy;}\n");
            var2 = a(
               35632,
               "#version 120\nvarying vec2 uv;\nuniform sampler2D DiffuseSampler;\nuniform sampler2D PrevSampler;\nuniform vec3 Phosphor;\n#define SAMPLE texture2D\n#define OUT_COLOR gl_FragColor\n"
                  + this.e()
            );
            var3 = GL20.glCreateProgram();
            GL20.glAttachShader(var3, var1);
            GL20.glAttachShader(var3, var2);
            GL20.glLinkProgram(var3);
            if (GL20.glGetProgrami(var3, 35714) == 0) {
               throw new IllegalStateException(GL20.glGetProgramInfoLog(var3, 4096));
            }

            this.program = var3;
            var3 = 0;
            this.phosphor = GL20.glGetUniformLocation(this.program, "Phosphor");
            this.diffuse = GL20.glGetUniformLocation(this.program, "DiffuseSampler");
            this.prevSampler = GL20.glGetUniformLocation(this.program, "PrevSampler");
            var4 = true;
         } catch (Exception var9) {
            this.failed = true;
            LogManager.getLogger("KazerUi").warn("Post shader {} unavailable: {}", new Object[]{this.name, var9.toString()});
            return false;
         } finally {
            if (var3 != 0) {
               GL20.glDeleteProgram(var3);
            }

            if (var1 != 0) {
               GL20.glDeleteShader(var1);
            }

            if (var2 != 0) {
               GL20.glDeleteShader(var2);
            }
         }

         return var4;
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

   private static int a(int var0) {
      int var1 = OpenGlHelper.glGenFramebuffers();
      OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, var1);
      OpenGlHelper.glFramebufferTexture2D(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_COLOR_ATTACHMENT0, 3553, var0, 0);
      if (OpenGlHelper.glCheckFramebufferStatus(OpenGlHelper.GL_FRAMEBUFFER) != OpenGlHelper.GL_FRAMEBUFFER_COMPLETE) {
         OpenGlHelper.glDeleteFramebuffers(var1);
         throw new IllegalStateException("Incomplete motion blur framebuffer");
      } else {
         return var1;
      }
   }

   private void b(int var1, int var2) {
      if (this.width != var1 || this.height != var2 || this.previous == 0) {
         this.g();
         this.width = var1;
         this.height = var2;
         this.previous = a(var1, var2);
         this.scratch = a(var1, var2);
         this.capture = a(var1, var2);
         this.previousFbo = a(this.previous);
         this.scratchFbo = a(this.scratch);
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

   private void c(int var1, int var2) {
      OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, var2);
      GL11.glBindTexture(3553, var1);
      a(true);
   }

   public void a(Minecraft var1, float var2, int var3) {
      if (!this.failed && a() && var1.displayWidth > 0 && var1.displayHeight > 0) {
         if (this.f()) {
            int var4 = GL11.glGetInteger(36006);
            int var5 = GL11.glGetInteger(34016);
            int var6 = GL11.glGetInteger(35725);
            int var7 = GL11.glGetInteger(2976);
            GL11.glPushAttrib(1048575);
            GL11.glMatrixMode(5889);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0, 1.0, 0.0, 1.0, -1.0, 1.0);
            GL11.glMatrixMode(5888);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();

            try {
               GL13.glActiveTexture(OpenGlHelper.defaultTexUnit + 1);
               GL11.glDisable(3553);
               GL13.glActiveTexture(OpenGlHelper.defaultTexUnit + 2);
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
               GL11.glEnable(3553);
               GL11.glDisable(3089);
               GL11.glDisable(2960);
               GL11.glTexEnvi(8960, 8704, 7681);
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               GL11.glColorMask(true, true, true, true);
               OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, var4);
               int var8;
               if (OpenGlHelper.isFramebufferEnabled()) {
                  var8 = var1.getFramebuffer().framebufferTexture;
               } else {
                  GL11.glBindTexture(3553, this.capture);
                  GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, this.width, this.height);
                  var8 = this.capture;
               }

               GL20.glUseProgram(0);
               if (this.ready) {
                  OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.scratchFbo);
                  GL20.glUseProgram(this.program);
                  GL11.glBindTexture(3553, var8);
                  GL20.glUniform1i(this.diffuse, 0);
                  GL13.glActiveTexture(OpenGlHelper.defaultTexUnit + 2);
                  GL11.glBindTexture(3553, this.previous);
                  GL20.glUniform1i(this.prevSampler, 2);
                  GL13.glActiveTexture(OpenGlHelper.defaultTexUnit);
                  GL20.glUniform3f(this.phosphor, var2, var3, 0.0F);
                  a(false);
                  GL20.glUseProgram(0);
                  this.c(this.scratch, this.previousFbo);
                  this.c(this.scratch, var4);
                  return;
               }

               this.c(var8, this.previousFbo);
               this.ready = true;
            } catch (RuntimeException var12) {
               this.failed = true;
               this.g();
               LogManager.getLogger("KazerUi").warn("Motion blur stopped after a render failure", var12);
               return;
            } finally {
               GL20.glUseProgram(var6);
               GL13.glActiveTexture(var5);
               GL11.glMatrixMode(5888);
               GL11.glPopMatrix();
               GL11.glMatrixMode(5889);
               GL11.glPopMatrix();
               GL11.glMatrixMode(var7);
               OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, var4);
               GL11.glPopAttrib();
            }
         }
      }
   }

   public void b() {
      this.ready = false;
   }

   private void g() {
      if (this.previousFbo != 0) {
         OpenGlHelper.glDeleteFramebuffers(this.previousFbo);
      }

      if (this.scratchFbo != 0) {
         OpenGlHelper.glDeleteFramebuffers(this.scratchFbo);
      }

      if (this.previous != 0) {
         GL11.glDeleteTextures(this.previous);
      }

      if (this.scratch != 0) {
         GL11.glDeleteTextures(this.scratch);
      }

      if (this.capture != 0) {
         GL11.glDeleteTextures(this.capture);
      }

      this.previousFbo = this.scratchFbo = this.previous = this.scratch = this.capture = this.width = this.height = 0;
      this.ready = false;
   }

   public void c() {
      this.g();
      if (this.program != 0) {
         GL20.glDeleteProgram(this.program);
      }

      this.program = 0;
      this.failed = false;
   }

   public boolean d() {
      return this.program != 0 || this.previous != 0 || this.scratch != 0 || this.capture != 0;
   }
}
