package com.cheatbreaker.client.kazerui.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.RoundRectangle2D.Float;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public final class UiGraphics {
   public static final String ROBOTO_LIGHT = "roboto-light.ttf";
   public static final String ROBOTO_MEDIUM = "roboto-medium.ttf";
   public static final String RALEWAY_EXTRABOLD = "raleway-extrabold.ttf";
   public static final String RALEWAY_LIGHT = "raleway-light.ttf";
   private static final FontRenderContext FRC = new FontRenderContext(null, true, true);
   private static final Map<String, Font> FONTS = new HashMap<>();
   private final Map<String, com.cheatbreaker.client.kazerui.gui.UiGraphics.a> cache = new HashMap<>();
   private final String name;
   private float dsf = 1.0F;

   public UiGraphics(String var1) {
      this.name = var1;
   }

   public void a(float var1) {
      if (var1 != this.dsf) {
         this.a();
         this.dsf = var1;
      }
   }

   private static Font b(String var0, float var1) {
      Font var2 = FONTS.get(var0);
      if (var2 == null) {
         try (InputStream var3 = com.cheatbreaker.client.kazerui.gui.UiGraphics.class.getResourceAsStream("/assets/kazerui/ui/fonts/" + var0)) {
            var2 = Font.createFont(0, var3);
         } catch (Exception var16) {
            throw new IllegalStateException("Missing Kazer font " + var0, var16);
         }

         FONTS.put(var0, var2);
      }

      return var2.deriveFont(var1 / 2.0F);
   }

   public float a(String var1, String var2, float var3) {
      return (float)b(var2, var3).getStringBounds(var1, FRC).getWidth();
   }

   public float a(String var1, float var2) {
      LineMetrics var3 = b(var1, var2).getLineMetrics("Ag", FRC);
      return var3.getAscent() + var3.getDescent();
   }

   public void a(String var1, String var2, float var3, float var4, float var5, int var6) {
      if (!var1.isEmpty() && var6 >>> 24 != 0) {
         com.cheatbreaker.client.kazerui.gui.UiGraphics.a var7 = this.b(var1, var2, var3);
         this.a(var7, var4, var5, var7.width, var7.height, var6);
      }
   }

   private com.cheatbreaker.client.kazerui.gui.UiGraphics.a b(String var1, String var2, float var3) {
      String var4 = "t|" + var2 + "|" + var3 + "|" + var1;
      com.cheatbreaker.client.kazerui.gui.UiGraphics.a var5 = this.cache.get(var4);
      if (var5 != null) {
         return var5;
      } else {
         Font var6 = b(var2, var3);
         LineMetrics var7 = var6.getLineMetrics(var1, FRC);
         float var8 = (float)var6.getStringBounds(var1, FRC).getWidth();
         float var9 = var7.getAscent() + var7.getDescent();
         int var10 = Math.max(1, (int)Math.ceil(var8 * this.dsf) + 2);
         int var11 = Math.max(1, (int)Math.ceil(var9 * this.dsf) + 1);
         BufferedImage var12 = new BufferedImage(var10, var11, 2);
         Graphics2D var13 = var12.createGraphics();
         var13.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
         var13.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
         var13.setFont(var6.deriveFont(var6.getSize2D() * this.dsf));
         var13.setColor(Color.WHITE);
         var13.drawString(var1, 0.0F, var7.getAscent() * this.dsf);
         var13.dispose();
         return this.a(var4, var12, var7.getAscent());
      }
   }

   public void a(float var1, float var2, float var3, float var4, float var5, int var6) {
      if (var6 >>> 24 != 0 && !(var3 <= 0.0F) && !(var4 <= 0.0F)) {
         String var7 = "r|" + var3 + "|" + var4 + "|" + var5;
         com.cheatbreaker.client.kazerui.gui.UiGraphics.a var8 = this.cache.get(var7);
         if (var8 == null) {
            BufferedImage var9 = this.a(var3, var4);
            Graphics2D var10 = a(var9);
            float var11 = Math.min(var5, Math.min(var3, var4) / 2.0F) * this.dsf * 2.0F;
            var10.fill(new Float(0.0F, 0.0F, var9.getWidth(), var9.getHeight(), var11, var11));
            var10.dispose();
            var8 = this.a(var7, var9, 0.0F);
         }

         this.a(var8, var1, var2, var3, var4, var6);
      }
   }

   public void a(float var1, float var2, float var3, float var4, float var5, float var6, int var7) {
      if (var7 >>> 24 != 0 && !(var3 <= 0.0F) && !(var4 <= 0.0F)) {
         String var8 = "o|" + var3 + "|" + var4 + "|" + var5 + "|" + var6;
         com.cheatbreaker.client.kazerui.gui.UiGraphics.a var9 = this.cache.get(var8);
         if (var9 == null) {
            BufferedImage var10 = this.a(var3, var4);
            Graphics2D var11 = a(var10);
            float var12 = var6 * this.dsf;
            float var13 = Math.max(0.0F, Math.min(var5, Math.min(var3, var4) / 2.0F) * this.dsf * 2.0F - var12);
            var11.setStroke(new BasicStroke(var12));
            var11.draw(new Float(var12 / 2.0F, var12 / 2.0F, var10.getWidth() - var12, var10.getHeight() - var12, var13, var13));
            var11.dispose();
            var9 = this.a(var8, var10, 0.0F);
         }

         this.a(var9, var1, var2, var3, var4, var7);
      }
   }

   public void a(String var1, float var2, float var3, float var4, float var5, int var6) {
      if (var6 >>> 24 != 0) {
         String var7 = "i|" + var1;
         com.cheatbreaker.client.kazerui.gui.UiGraphics.a var8 = this.cache.get(var7);
         if (var8 == null) {
            try (InputStream var9 = com.cheatbreaker.client.kazerui.gui.UiGraphics.class.getResourceAsStream("/assets/kazerui/" + var1)) {
               BufferedImage var11 = ImageIO.read(var9);
               var8 = this.a(var7, var11, 0.0F);
            } catch (Exception var22) {
               throw new IllegalStateException("Missing Kazer texture " + var1, var22);
            }
         }

         this.a(var8, var2, var3, var4, var5, var6);
      }
   }

   public static void a(float var0, float var1, float var2, float var3, int var4) {
      if (var4 >>> 24 != 0) {
         GlStateManager.enableBlend();
         GlStateManager.disableTexture2D();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         a(var4);
         WorldRenderer var5 = Tessellator.getInstance().getWorldRenderer();
         var5.begin(7, DefaultVertexFormats.POSITION);
         var5.pos(var0, var3, 0.0).endVertex();
         var5.pos(var2, var3, 0.0).endVertex();
         var5.pos(var2, var1, 0.0).endVertex();
         var5.pos(var0, var1, 0.0).endVertex();
         Tessellator.getInstance().draw();
         GlStateManager.enableTexture2D();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   public static void b(float var0, float var1, float var2, float var3, float var4, int var5) {
      a(var0, var1, var0 + var2, var1 + var4, var5);
      a(var0, var1 + var3 - var4, var0 + var2, var1 + var3, var5);
      a(var0, var1 + var4, var0 + var4, var1 + var3 - var4, var5);
      a(var0 + var2 - var4, var1 + var4, var0 + var2, var1 + var3 - var4, var5);
   }

   public static int a(int var0, float var1) {
      int var2 = Math.round((var0 >>> 24) * Math.max(0.0F, Math.min(1.0F, var1)));
      return var2 << 24 | var0 & 16777215;
   }

   public static int a(int var0, int var1, float var2) {
      var2 = Math.max(0.0F, Math.min(1.0F, var2));
      int var3 = 0;

      for (byte var4 = 0; var4 < 32; var4 += 8) {
         int var5 = var0 >>> var4 & 0xFF;
         int var6 = var1 >>> var4 & 0xFF;
         var3 |= Math.round(var5 + (var6 - var5) * var2) << var4;
      }

      return var3;
   }

   private static void a(int var0) {
      GlStateManager.color((var0 >> 16 & 0xFF) / 255.0F, (var0 >> 8 & 0xFF) / 255.0F, (var0 & 0xFF) / 255.0F, (var0 >>> 24) / 255.0F);
   }

   private void a(com.cheatbreaker.client.kazerui.gui.UiGraphics.a var1, float var2, float var3, float var4, float var5, int var6) {
      GlStateManager.enableBlend();
      GlStateManager.enableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.bindTexture(var1.texture.getGlTextureId());
      a(var6);
      WorldRenderer var7 = Tessellator.getInstance().getWorldRenderer();
      var7.begin(7, DefaultVertexFormats.POSITION_TEX);
      var7.pos(var2, var3 + var5, 0.0).tex(0.0, 1.0).endVertex();
      var7.pos(var2 + var4, var3 + var5, 0.0).tex(1.0, 1.0).endVertex();
      var7.pos(var2 + var4, var3, 0.0).tex(1.0, 0.0).endVertex();
      var7.pos(var2, var3, 0.0).tex(0.0, 0.0).endVertex();
      Tessellator.getInstance().draw();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private BufferedImage a(float var1, float var2) {
      return new BufferedImage(Math.max(1, Math.round(var1 * this.dsf)), Math.max(1, Math.round(var2 * this.dsf)), 2);
   }

   private static Graphics2D a(BufferedImage var0) {
      Graphics2D var1 = var0.createGraphics();
      var1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      var1.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
      var1.setColor(Color.WHITE);
      return var1;
   }

   private com.cheatbreaker.client.kazerui.gui.UiGraphics.a a(String var1, BufferedImage var2, float var3) {
      DynamicTexture var4 = new DynamicTexture(var2);
      GlStateManager.bindTexture(var4.getGlTextureId());
      var4.setBlurMipmap(true, false);
      com.cheatbreaker.client.kazerui.gui.UiGraphics.a var5 = new com.cheatbreaker.client.kazerui.gui.UiGraphics.a(
         var4, var2.getWidth() / this.dsf, var2.getHeight() / this.dsf, var3
      );
      this.cache.put(var1, var5);
      return var5;
   }

   public void a() {
      for (com.cheatbreaker.client.kazerui.gui.UiGraphics.a var2 : this.cache.values()) {
         var2.texture.deleteGlTexture();
      }

      this.cache.clear();
   }

   public static final class a {
      final DynamicTexture texture;
      public final float width;
      public final float height;
      public final float ascent;

      a(DynamicTexture var1, float var2, float var3, float var4) {
         this.texture = var1;
         this.width = var2;
         this.height = var3;
         this.ascent = var4;
      }
   }
}
