package com.cheatbreaker.client.util.hologram;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import org.lwjgl.opengl.GL11;

public class Hologram {
   private final UUID lIIIIlIIllIIlIIlIIIlIIllI;
   private String[] lIIIIIIIIIlIllIIllIlIIlIl;
   private final double IlllIIIlIlllIllIlIIlllIlI;
   private final double IIIIllIlIIIllIlllIlllllIl;
   private final double IIIIllIIllIIIIllIllIIIlIl;
   private static final List<com.cheatbreaker.client.util.hologram.Hologram> holograms = new ArrayList<>();

   public Hologram(UUID var1, double var2, double var4, double var6) {
      this.lIIIIlIIllIIlIIlIIIlIIllI = var1;
      this.IlllIIIlIlllIllIlIIlllIlI = var2;
      this.IIIIllIlIIIllIlllIlllllIl = var4;
      this.IIIIllIIllIIIIllIllIIIlIl = var6;
   }

   public static void a() {
      FontRenderer var0 = Minecraft.getMinecraft().fontRendererObj;
      RenderManager var1 = Minecraft.getMinecraft().getRenderManager();

      for (com.cheatbreaker.client.util.hologram.Hologram var3 : holograms) {
         if (var3.c() != null && var3.c().length > 0) {
            for (int var4 = var3.c().length - 1; var4 >= 0; var4--) {
               String var5 = var3.c()[var3.c().length - var4 - 1];
               float var6 = (float)(var3.d() - (float)Minecraft.getMinecraft().getRenderManager().viewerPosX);
               float var7 = (float)(var3.e() + 1.0 - (float)Minecraft.getMinecraft().getRenderManager().viewerPosY);
               float var8 = (float)(var3.f() - (float)Minecraft.getMinecraft().getRenderManager().viewerPosZ);
               float var9 = 1.6F;
               float var10 = 0.016666668F * var9;
               GL11.glPushMatrix();
               GL11.glTranslatef(var6, var7, var8);
               GL11.glNormal3f(0.0F, 1.0F, 0.0F);
               boolean var11 = cn.kazer.client189.render.ClientBridge.b();
               GL11.glScalef(-var10, -var10, var10);
               cn.kazer.client189.render.GlStateBridge.b(2896);
               cn.kazer.client189.render.GlStateBridge.a(false);
               cn.kazer.client189.render.GlStateBridge.b(2929);
               cn.kazer.client189.render.GlStateBridge.a(3042);
               OpenGlHelper.glBlendFunc(770, 771, 1, 0);
               cn.kazer.client189.render.ImmediateTessellator var12 = cn.kazer.client189.render.ImmediateTessellator.instance;
               int var13 = -var4 * 10;
               cn.kazer.client189.render.GlStateBridge.b(3553);
               var12.a();
               int var14 = var0.getStringWidth(var5) / 2;
               var12.a(0.0F, 0.0F, 0.0F, 0.25F);
               var12.a((double)(-var14 - 1), (double)(-1 + var13), 0.0);
               var12.a((double)(-var14 - 1), (double)(8 + var13), 0.0);
               var12.a((double)(var14 + 1), (double)(8 + var13), 0.0);
               var12.a((double)(var14 + 1), (double)(-1 + var13), 0.0);
               var12.b();
               cn.kazer.client189.render.GlStateBridge.a(3553);
               var0.drawString(var5, -var0.getStringWidth(var5) / 2, var13, 553648127);
               cn.kazer.client189.render.GlStateBridge.a(2929);
               cn.kazer.client189.render.GlStateBridge.a(true);
               var0.drawString(var5, -var0.getStringWidth(var5) / 2, var13, -1);
               cn.kazer.client189.render.GlStateBridge.a(2896);
               cn.kazer.client189.render.GlStateBridge.b(3042);
               cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
               cn.kazer.client189.render.ClientBridge.b(var11);
               GL11.glPopMatrix();
            }
         }
      }
   }

   public UUID b() {
      return this.lIIIIlIIllIIlIIlIIIlIIllI;
   }

   public String[] c() {
      return this.lIIIIIIIIIlIllIIllIlIIlIl;
   }

   public void a(String[] var1) {
      this.lIIIIIIIIIlIllIIllIlIIlIl = var1;
   }

   public double d() {
      return this.IlllIIIlIlllIllIlIIlllIlI;
   }

   public double e() {
      return this.IIIIllIlIIIllIlllIlllllIl;
   }

   public double f() {
      return this.IIIIllIIllIIIIllIllIIIlIl;
   }

   public static List<com.cheatbreaker.client.util.hologram.Hologram> g() {
      return holograms;
   }
}
