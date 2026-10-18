package com.cheatbreaker.client.gui.screens;

import java.io.IOException;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

public class PanoramaScreen extends com.cheatbreaker.client.gui.ScaledGuiScreen {
   private static int IIIllIllIlIlllllllIlIlIII = 4100;
   private final ResourceLocation[] IllIlIlIllllIlIIllllIIlll = new ResourceLocation[]{
      new ResourceLocation("client/panorama/0.png"),
      new ResourceLocation("client/panorama/1.png"),
      new ResourceLocation("client/panorama/2.png"),
      new ResourceLocation("client/panorama/3.png"),
      new ResourceLocation("client/panorama/4.png"),
      new ResourceLocation("client/panorama/5.png")
   };
   private ResourceLocation panoramaBackgroundLocation;
   private int lastPanoramaTick = -1;

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
   }

   public void updateScreen() {
      super.updateScreen();
      IIIllIllIlIlllllllIlIlIII++;
   }

   public void initGui() {
      super.initGui();
      if (this.panoramaBackgroundLocation == null) {
         DynamicTexture var1 = new DynamicTexture(256, 256);
         this.panoramaBackgroundLocation = this.mc.getTextureManager().getDynamicTextureLocation("background", var1);
      }

      this.lastPanoramaTick = -1;
   }

   @Override
   public void drawScreen(int var1, int var2, float var3) {
      cn.kazer.client189.render.GlStateBridge.b(3008);
      this.b(var1, var2, 1.0F);
      cn.kazer.client189.render.GlStateBridge.a(3008);
      super.drawScreen(var1, var2, var3);
   }

   @Override
   public void a(float var1, float var2) {
      cn.kazer.client189.render.DrawRect.a(0.0F, 0.0F, this.f(), this.g(), 1409286144, 620756992);
      cn.kazer.client189.render.DrawRect.a(0.0F, 0.0F, this.f(), 100.0F, 1879048192, 0);
   }

   @Override
   public void b(float var1, float var2, int var3) {
   }

   @Override
   public void a(float var1, float var2, int var3) {
   }

   private void a(int var1, int var2, float var3) {
      cn.kazer.client189.render.ImmediateTessellator var4 = cn.kazer.client189.render.ImmediateTessellator.instance;
      GL11.glMatrixMode(5889);
      GL11.glPushMatrix();
      GL11.glLoadIdentity();
      Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
      GL11.glMatrixMode(5888);
      GL11.glPushMatrix();
      GL11.glLoadIdentity();
      cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
      cn.kazer.client189.render.GlStateBridge.a(3042);
      cn.kazer.client189.render.GlStateBridge.b(3008);
      cn.kazer.client189.render.GlStateBridge.b(2884);
      cn.kazer.client189.render.GlStateBridge.a(false);
      OpenGlHelper.glBlendFunc(770, 771, 1, 0);
      byte var5 = 8;

      for (int var6 = 0; var6 < var5 * var5; var6++) {
         GL11.glPushMatrix();
         float var7 = ((float)(var6 % var5) / var5 - 0.5F) / 64.0F;
         float var8 = ((float)(var6 / var5) / var5 - 0.5F) / 64.0F;
         float var9 = 0.0F;
         GL11.glTranslatef(var7, var8, var9);
         GL11.glRotatef(MathHelper.sin((IIIllIllIlIlllllllIlIlIII + var3) / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(-(IIIllIllIlIlllllllIlIlIII + var3) * 0.1F, 0.0F, 1.0F, 0.0F);

         for (int var10 = 0; var10 < 6; var10++) {
            GL11.glPushMatrix();
            if (var10 == 1) {
               GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            }

            if (var10 == 2) {
               GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            }

            if (var10 == 3) {
               GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
            }

            if (var10 == 4) {
               GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            }

            if (var10 == 5) {
               GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            }

            this.mc.getTextureManager().bindTexture(this.IllIlIlIllllIlIIllllIIlll[var10]);
            var4.a();
            var4.a(16777215, 255 / (var6 + 1));
            float var11 = 0.0F;
            var4.a(-1.0, -1.0, 1.0, 0.0F + var11, 0.0F + var11);
            var4.a(1.0, -1.0, 1.0, 1.0F - var11, 0.0F + var11);
            var4.a(1.0, 1.0, 1.0, 1.0F - var11, 1.0F - var11);
            var4.a(-1.0, 1.0, 1.0, 0.0F + var11, 1.0F - var11);
            var4.b();
            GL11.glPopMatrix();
         }

         GL11.glPopMatrix();
         cn.kazer.client189.render.GlStateBridge.a(true, true, true, false);
      }

      var4.b(0.0, 0.0, 0.0);
      cn.kazer.client189.render.GlStateBridge.a(true, true, true, true);
      GL11.glMatrixMode(5889);
      GL11.glPopMatrix();
      GL11.glMatrixMode(5888);
      GL11.glPopMatrix();
      cn.kazer.client189.render.GlStateBridge.a(true);
      cn.kazer.client189.render.GlStateBridge.a(2884);
      cn.kazer.client189.render.GlStateBridge.a(2929);
   }

   private void a(float var1) {
      this.mc.getTextureManager().bindTexture(this.panoramaBackgroundLocation);
      GL11.glTexParameteri(3553, 10241, 9729);
      GL11.glTexParameteri(3553, 10240, 9729);
      GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
      cn.kazer.client189.render.GlStateBridge.a(3042);
      OpenGlHelper.glBlendFunc(770, 771, 1, 0);
      cn.kazer.client189.render.GlStateBridge.a(true, true, true, false);
      cn.kazer.client189.render.ImmediateTessellator var2 = cn.kazer.client189.render.ImmediateTessellator.instance;
      var2.a();
      cn.kazer.client189.render.GlStateBridge.b(3008);
      byte var3 = 3;

      for (int var4 = 0; var4 < var3; var4++) {
         var2.a(1.0F, 1.0F, 1.0F, 1.0F / (var4 + 1));
         int var5 = this.width;
         int var6 = this.height;
         float var7 = (var4 - var3 / 2) / 256.0F;
         var2.a(var5, var6, this.zLevel, 0.0F + var7, 1.0);
         var2.a(var5, 0.0, this.zLevel, 1.0F + var7, 1.0);
         var2.a(0.0, 0.0, this.zLevel, 1.0F + var7, 0.0);
         var2.a(0.0, var6, this.zLevel, 0.0F + var7, 0.0);
      }

      var2.b();
      cn.kazer.client189.render.GlStateBridge.a(3008);
      cn.kazer.client189.render.GlStateBridge.a(true, true, true, true);
   }

   private void b(int var1, int var2, float var3) {
      if (this.lastPanoramaTick != IIIllIllIlIlllllllIlIlIII) {
         this.lastPanoramaTick = IIIllIllIlIlllllllIlIlIII;
         this.mc.getFramebuffer().unbindFramebuffer();
         GL11.glViewport(0, 0, 256, 256);
         this.a(var1, var2, var3);
         this.a(var3);
         this.a(var3);
         this.a(var3);
         this.a(var3);
         this.a(var3);
         this.a(var3);
         this.a(var3);
         this.mc.getFramebuffer().bindFramebuffer(true);
         GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
      }

      cn.kazer.client189.render.GlStateBridge.a(3553);
      cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(this.panoramaBackgroundLocation);
      cn.kazer.client189.render.ImmediateTessellator var4 = cn.kazer.client189.render.ImmediateTessellator.instance;
      var4.a();
      float var5 = this.width > this.height ? 120.0F / this.width : 120.0F / this.height;
      float var6 = this.height * var5 / 256.0F;
      float var7 = this.width * var5 / 256.0F;
      var4.a(1.0F, 1.0F, 1.0F, 1.0F);
      int var8 = this.width;
      int var9 = this.height;
      var4.a(0.0, var9, this.zLevel, 0.5F - var6, 0.5F + var7);
      var4.a(var8, var9, this.zLevel, 0.5F - var6, 0.5F - var7);
      var4.a(var8, 0.0, this.zLevel, 0.5F + var6, 0.5F - var7);
      var4.a(0.0, 0.0, this.zLevel, 0.5F + var6, 0.5F + var7);
      var4.b();
   }
}
