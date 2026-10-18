package com.cheatbreaker.client.gui.render;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D.Double;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public final class GlyphTextureRenderer {
   private static final Map<String, ResourceLocation> TEXTURES = new HashMap<>();

   private GlyphTextureRenderer() {
   }

   public static BufferedImage a(String var0) {
      BufferedImage var1 = new BufferedImage(128, 128, 2);
      Graphics2D var2 = var1.createGraphics();

      try {
         var2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         var2.scale(5.333333333333333, 5.333333333333333);
         var2.setColor(Color.WHITE);
         if ("CHAT".equals(var0)) {
            var2.fill(new Double(2.0, 3.0, 20.0, 15.0, 4.0, 4.0));
            java.awt.geom.Path2D.Double var3 = new java.awt.geom.Path2D.Double();
            var3.moveTo(4.0, 16.0);
            var3.lineTo(4.0, 21.0);
            var3.quadTo(4.0, 22.0, 5.0, 21.3);
            var3.lineTo(10.0, 17.0);
            var3.closePath();
            var2.fill(var3);
            var2.setComposite(AlphaComposite.Clear);

            for (int var4 = 0; var4 < 3; var4++) {
               var2.fill(new java.awt.geom.Ellipse2D.Double(5.5 + var4 * 5, 9.0, 2.5, 2.5));
            }
         } else if ("STAR".equals(var0)) {
            double[][] var22 = new double[10][2];

            for (int var24 = 0; var24 < 10; var24++) {
               double var5 = (-Math.PI / 2) + var24 * Math.PI / 5.0;
               double var7 = var24 % 2 == 0 ? 10.0 : 4.9;
               var22[var24][0] = 12.0 + Math.cos(var5) * var7;
               var22[var24][1] = 12.0 + Math.sin(var5) * var7;
            }

            java.awt.geom.Path2D.Double var25 = new java.awt.geom.Path2D.Double();

            for (int var27 = 0; var27 < 10; var27++) {
               double[] var6 = var22[(var27 + 9) % 10];
               double[] var29 = var22[var27];
               double[] var8 = var22[(var27 + 1) % 10];
               double var9 = var29[0] + (var6[0] - var29[0]) * 0.18;
               double var11 = var29[1] + (var6[1] - var29[1]) * 0.18;
               double var13 = var29[0] + (var8[0] - var29[0]) * 0.18;
               double var15 = var29[1] + (var8[1] - var29[1]) * 0.18;
               if (var27 == 0) {
                  var25.moveTo(var9, var11);
               } else {
                  var25.lineTo(var9, var11);
               }

               var25.quadTo(var29[0], var29[1], var13, var15);
            }

            var25.closePath();
            var2.fill(var25);
         } else {
            var2.setStroke(new BasicStroke(1.7F, 1, 1));

            for (int var23 = 0; var23 < 3; var23++) {
               var2.draw(new java.awt.geom.Arc2D.Double(2.5, 2.5, 19.0, 19.0, var23 * 120 + 20, 88.0, 0));
               double var26 = Math.toRadians(var23 * 120 + 108);
               double var28 = 12.0 + 9.5 * Math.cos(var26);
               double var30 = 12.0 - 9.5 * Math.sin(var26);
               double var10 = -Math.sin(var26);
               double var12 = -Math.cos(var26);
               double var14 = Math.cos(var26);
               double var16 = -Math.sin(var26);
               java.awt.geom.Path2D.Double var18 = new java.awt.geom.Path2D.Double();
               var18.moveTo(var28 - var10 * 3.0 + var14 * 2.0, var30 - var12 * 3.0 + var16 * 2.0);
               var18.lineTo(var28, var30);
               var18.lineTo(var28 - var10 * 3.0 - var14 * 2.0, var30 - var12 * 3.0 - var16 * 2.0);
               var2.draw(var18);
            }

            var2.draw(new java.awt.geom.Ellipse2D.Double(7.5, 7.5, 9.0, 9.0));
            var2.fill(new java.awt.geom.Ellipse2D.Double(10.75, 10.75, 2.5, 2.5));
         }
      } finally {
         var2.dispose();
      }

      return var1;
   }

   public static void a(String var0, float var1, float var2, float var3, int var4) {
      cn.kazer.client189.render.GlStateBridge.c(1048575);

      try {
         ResourceLocation var5 = TEXTURES.get(var0);
         if (var5 == null) {
            var5 = Minecraft.getMinecraft()
               .getTextureManager()
               .getDynamicTextureLocation("kazer-glyph-" + var0.toLowerCase(Locale.ROOT), new DynamicTexture(a(var0)));
            TEXTURES.put(var0, var5);
         }

         cn.kazer.client189.render.GlStateBridge.a(3553);
         cn.kazer.client189.render.GlStateBridge.b(2896);
         cn.kazer.client189.render.GlStateBridge.b(2884);
         cn.kazer.client189.render.GlStateBridge.b(2929);
         cn.kazer.client189.render.GlStateBridge.a(false);
         cn.kazer.client189.render.GlStateBridge.b(3008);
         cn.kazer.client189.render.GlStateBridge.a(3042);
         cn.kazer.client189.render.GlStateBridge.b(770, 771);
         Minecraft.getMinecraft().getTextureManager().bindTexture(var5);
         GL11.glTexParameteri(3553, 10241, 9729);
         GL11.glTexParameteri(3553, 10240, 9729);
         cn.kazer.client189.render.GlStateBridge.a((var4 >> 16 & 0xFF) / 255.0F, (var4 >> 8 & 0xFF) / 255.0F, (var4 & 0xFF) / 255.0F, (var4 >>> 24) / 255.0F);
         GL11.glBegin(7);
         GL11.glTexCoord2f(0.0F, 0.0F);
         GL11.glVertex2f(var1, var2);
         GL11.glTexCoord2f(0.0F, 1.0F);
         GL11.glVertex2f(var1, var2 + var3);
         GL11.glTexCoord2f(1.0F, 1.0F);
         GL11.glVertex2f(var1 + var3, var2 + var3);
         GL11.glTexCoord2f(1.0F, 0.0F);
         GL11.glVertex2f(var1 + var3, var2);
         GL11.glEnd();
      } finally {
         cn.kazer.client189.render.GlStateBridge.a();
      }
   }
}
