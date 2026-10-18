package com.cheatbreaker.client.kazerui.gui.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.font.TextAttribute;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

final class MenuGraphics {
   private static final float[] ROUND_OFFSETS = c();
   private final Map<String, com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner> cache = new LinkedHashMap<String, com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner>(
      64, 0.75F, true
   ) {
      @Override
      protected boolean removeEldestEntry(Entry<String, com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner> var1) {
         if (this.size() <= 512) {
            return false;
         } else {
            ((com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner)var1.getValue()).texture.deleteGlTexture();
            return true;
         }
      }
   };
   private static final Map<String, Font> FONTS = new HashMap<>();
   private static final FontRenderContext FONT_CONTEXT = new FontRenderContext(null, true, true);
   private final Map<String, Float> widths = new LinkedHashMap<String, Float>(64, 0.75F, true) {
      @Override
      protected boolean removeEldestEntry(Entry<String, Float> var1) {
         return this.size() > 512;
      }
   };
   private final Font medium;
   private final Font bold;
   private Font legacy;
   float dsf = 1.0F;
   private final Set<ResourceLocation> missing = new HashSet<>();

   private static float[] c() {
      float[] var0 = new float[104];

      for (int var1 = 0; var1 < 4; var1++) {
         for (int var2 = 0; var2 <= 12; var2++) {
            double var3 = Math.toRadians(-90 + var1 * 90 + var2 * 90 / 12.0);
            int var5 = (var1 * 13 + var2) * 2;
            var0[var5] = (float)Math.cos(var3);
            var0[var5 + 1] = (float)Math.sin(var3);
         }
      }

      return var0;
   }

   MenuGraphics() {
      this.medium = a("DMSans-Medium.ttf");
      this.bold = a("DMSans-Bold.ttf");
   }

   private static synchronized Font a(String var0) {
      Font var1 = FONTS.get(var0);
      if (var1 != null) {
         return var1;
      } else {
         try (InputStream var2 = com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.class.getResourceAsStream("/assets/kazerui/ui/fonts/" + var0)) {
            HashMap var4 = new HashMap();
            var4.put(TextAttribute.KERNING, TextAttribute.KERNING_ON);
            var4.put(TextAttribute.LIGATURES, TextAttribute.LIGATURES_ON);
            Font var5 = Font.createFont(0, var2).deriveFont(var4);
            FONTS.put(var0, var5);
            return var5;
         } catch (Exception var18) {
            throw new IllegalStateException("Missing Kazer font " + var0, var18);
         }
      }
   }

   Font a(boolean var1) {
      return var1 ? this.bold : this.medium;
   }

   private Font d() {
      if (this.legacy == null) {
         this.legacy = a("roboto-bold.ttf");
      }

      return this.legacy;
   }

   float a(String var1, float var2) {
      return (float)this.d().deriveFont(var2).getStringBounds(var1, e()).getWidth();
   }

   com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner b(String var1, float var2) {
      String var3 = "lt|" + var2 + "|" + this.dsf + "|" + var1;
      com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner var4 = this.cache.get(var3);
      if (var4 != null) {
         return var4;
      } else {
         Font var5 = this.d().deriveFont(var2);
         LineMetrics var6 = var5.getLineMetrics(var1, e());
         float var7 = Math.round(var6.getAscent());
         float var8 = var7 + Math.round(var6.getDescent());
         float var9 = (float)var5.getStringBounds(var1, e()).getWidth();
         int var10 = Math.max(1, (int)Math.ceil(var9 * this.dsf) + 2);
         int var11 = Math.max(1, (int)Math.ceil(var8 * this.dsf));
         BufferedImage var12 = new BufferedImage(var10, var11, 2);
         Graphics2D var13 = var12.createGraphics();
         var13.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
         var13.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
         var13.setFont(this.d().deriveFont(var2 * this.dsf));
         var13.setColor(Color.WHITE);
         var13.drawString(var1, 0.0F, var7 * this.dsf);
         var13.dispose();
         var4 = new com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner(var12, var10 / this.dsf, var11 / this.dsf, Integer.toHexString(var3.hashCode()));
         this.cache.put(var3, var4);
         return var4;
      }
   }

   com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner a(float var1, float var2, float var3, float var4, float var5) {
      String var6 = "r|" + var1 + "|" + var2 + "|" + var3 + "|" + var4 + "|" + var5 + "|" + this.dsf;
      com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner var7 = this.cache.get(var6);
      if (var7 != null) {
         return var7;
      } else {
         int var8 = Math.max(1, Math.round(var1 * this.dsf));
         int var9 = Math.max(1, Math.round(var2 * this.dsf));
         BufferedImage var10 = new BufferedImage(var8, var9, 2);
         Graphics2D var11 = var10.createGraphics();
         var11.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         var11.setColor(Color.WHITE);
         float var12 = var4 * this.dsf;
         float var13 = var5 * this.dsf;
         float var14 = Math.max(0.0F, var3 * this.dsf - var12);
         float var15 = Math.max(0.0F, var14 - var13);
         Area var16 = new Area(new java.awt.geom.RoundRectangle2D.Float(var12, var12, var8 - 2.0F * var12, var9 - 2.0F * var12, var14 * 2.0F, var14 * 2.0F));
         var16.subtract(
            new Area(
               new java.awt.geom.RoundRectangle2D.Float(
                  var12 + var13, var12 + var13, var8 - 2.0F * (var12 + var13), var9 - 2.0F * (var12 + var13), var15 * 2.0F, var15 * 2.0F
               )
            )
         );
         var11.fill(var16);
         var11.dispose();
         var7 = new com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner(var10, var8 / this.dsf, var9 / this.dsf, Integer.toHexString(var6.hashCode()));
         this.cache.put(var6, var7);
         return var7;
      }
   }

   float a(String var1, boolean var2, float var3) {
      String var4 = var2 + "|" + var3 + "|" + var1;
      Float var5 = this.widths.get(var4);
      if (var5 != null) {
         return var5;
      } else {
         float var6 = (float)this.a(var2).deriveFont(var3).getStringBounds(var1, e()).getWidth();
         this.widths.put(var4, var6);
         return var6;
      }
   }

   private static FontRenderContext e() {
      return FONT_CONTEXT;
   }

   com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner b(String var1, boolean var2, float var3) {
      String var4 = "t|" + var2 + "|" + var3 + "|" + this.dsf + "|" + var1;
      com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner var5 = this.cache.get(var4);
      if (var5 != null) {
         return var5;
      } else {
         Font var6 = this.a(var2).deriveFont(var3);
         LineMetrics var7 = var6.getLineMetrics(var1, e());
         float var8 = Math.round(var7.getAscent());
         float var9 = var8 + Math.round(var7.getDescent());
         float var10 = (float)var6.getStringBounds(var1, e()).getWidth();
         int var11 = Math.max(1, (int)Math.ceil(var10 * this.dsf) + 2);
         int var12 = Math.max(1, (int)Math.ceil(var9 * this.dsf));
         BufferedImage var13 = new BufferedImage(var11, var12, 2);
         Graphics2D var14 = var13.createGraphics();
         var14.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
         var14.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
         var14.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         var14.setFont(this.a(var2).deriveFont(var3 * this.dsf));
         var14.setColor(Color.WHITE);
         var14.drawString(var1, 0.0F, var8 * this.dsf);
         var14.dispose();
         var5 = new com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner(var13, var11 / this.dsf, var12 / this.dsf, Integer.toHexString(var4.hashCode()));
         this.cache.put(var4, var5);
         return var5;
      }
   }

   com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner a(float var1, float var2, float var3) {
      String var4 = "b|" + var1 + "|" + var2 + "|" + var3 + "|" + this.dsf;
      com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner var5 = this.cache.get(var4);
      if (var5 != null) {
         return var5;
      } else {
         int var6 = Math.max(1, Math.round(var1 * this.dsf));
         int var7 = Math.max(1, Math.round(var2 * this.dsf));
         BufferedImage var8 = new BufferedImage(var6, var7, 2);
         Graphics2D var9 = var8.createGraphics();
         var9.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         var9.setColor(Color.WHITE);
         float var10 = Math.min(var3, Math.min(var1, var2) / 2.0F) * this.dsf * 2.0F;
         var9.fill(new java.awt.geom.RoundRectangle2D.Float(0.0F, 0.0F, var6, var7, var10, var10));
         var9.dispose();
         var5 = new com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner(var8, var6 / this.dsf, var7 / this.dsf, Integer.toHexString(var4.hashCode()));
         this.cache.put(var4, var5);
         return var5;
      }
   }

   com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner a(float var1, float var2, float var3, float var4, float var5, float var6) {
      String var7 = "s|" + var1 + "|" + var2 + "|" + var3 + "|" + var4 + "|" + var5 + "|" + var6 + "|" + this.dsf;
      com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner var8 = this.cache.get(var7);
      if (var8 != null) {
         return var8;
      } else {
         BufferedImage var9 = com.cheatbreaker.client.kazerui.gui.ShadowRenderer.a(var1, var2, var3, var4, var5, var6, this.dsf);
         var8 = new com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner(
            var9, var9.getWidth() / this.dsf, var9.getHeight() / this.dsf, Integer.toHexString(var7.hashCode())
         );
         this.cache.put(var7, var8);
         return var8;
      }
   }

   static float a(float var0) {
      return com.cheatbreaker.client.kazerui.gui.ShadowRenderer.a(var0);
   }

   float b(float var1) {
      return Math.round(var1 * this.dsf) / this.dsf;
   }

   void a(com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner var1, float var2, float var3, int var4) {
      this.a(var1.texture, this.b(var2), this.b(var3), var1.width, var1.height, var4);
   }

   void a(float var1, float var2, float var3, float var4, int var5) {
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 771);
      GlStateManager.color((var5 >> 16 & 0xFF) / 255.0F, (var5 >> 8 & 0xFF) / 255.0F, (var5 & 0xFF) / 255.0F, (var5 >>> 24 & 0xFF) / 255.0F);
      Tessellator var6 = Tessellator.getInstance();
      WorldRenderer var7 = var6.getWorldRenderer();
      var7.begin(7, DefaultVertexFormats.POSITION);
      var7.pos(var1, var2 + var4, 0.0).endVertex();
      var7.pos(var1 + var3, var2 + var4, 0.0).endVertex();
      var7.pos(var1 + var3, var2, 0.0).endVertex();
      var7.pos(var1, var2, 0.0).endVertex();
      var6.draw();
      GlStateManager.enableTexture2D();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   void a(ResourceLocation var1, float var2, float var3, float var4, float var5, int var6) {
      com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner var7 = this.a(var1, var4, var5);
      if (var7 == null) {
         this.b(var1, var2, var3, var4, var5, var6);
      } else {
         this.a(var7.texture, this.b(var2), this.b(var3), var7.width, var7.height, var6);
      }
   }

   private com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner a(ResourceLocation var1, float var2, float var3) {
      String var4 = "i|" + var1 + "|" + var2 + "|" + var3 + "|" + this.dsf;
      com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner var5 = this.cache.get(var4);
      if (var5 == null && !this.missing.contains(var1)) {
         BufferedImage var6;
         try (InputStream var7 = Minecraft.getMinecraft().getResourceManager().getResource(var1).getInputStream()) {
            var6 = ImageIO.read(var7);
         } catch (Exception var20) {
            this.missing.add(var1);
            return null;
         }

         int var22 = Math.max(1, Math.round(var2 * this.dsf));
         int var23 = Math.max(1, Math.round(var3 * this.dsf));
         var5 = new com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner(a(var6, var22, var23), var22 / this.dsf, var23 / this.dsf, Integer.toHexString(var4.hashCode()));
         this.cache.put(var4, var5);
         return var5;
      } else {
         return var5;
      }
   }

   com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner a(String var1, BufferedImage var2, float var3, int var4) {
      String var5 = "c|" + var1 + "|" + var3 + "|" + this.dsf;
      com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner var6 = this.cache.get(var5);
      if (var6 != null) {
         return var6;
      } else {
         int var7 = Math.max(1, Math.round(var3 * this.dsf));
         BufferedImage var8 = a(var2, var7, var7);
         BufferedImage var9 = new BufferedImage(var7, var7, 2);
         Graphics2D var10 = var9.createGraphics();
         var10.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         var10.setColor(Color.WHITE);
         var10.fill(new java.awt.geom.Ellipse2D.Float(0.0F, 0.0F, var7, var7));
         var10.dispose();
         float var11 = (var4 >>> 24) / 255.0F;

         for (int var12 = 0; var12 < var7; var12++) {
            for (int var13 = 0; var13 < var7; var13++) {
               int var14 = var8.getRGB(var13, var12);
               float var15 = (var14 >>> 24) / 255.0F;
               float var16 = var15 + var11 * (1.0F - var15);
               float var17 = (var9.getRGB(var13, var12) >>> 24) / 255.0F;
               if (!(var16 <= 0.0F) && !(var17 <= 0.0F)) {
                  int var18 = 0;

                  for (byte var19 = 0; var19 < 24; var19 += 8) {
                     var18 |= Math.min(255, Math.round(((var14 >> var19 & 0xFF) * var15 + (var4 >> var19 & 0xFF) * var11 * (1.0F - var15)) / var16)) << var19;
                  }

                  var8.setRGB(var13, var12, Math.round(var16 * var17 * 255.0F) << 24 | var18);
               } else {
                  var8.setRGB(var13, var12, 0);
               }
            }
         }

         var6 = new com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner(var8, var7 / this.dsf, var7 / this.dsf, Integer.toHexString(var5.hashCode()));
         this.cache.put(var5, var6);
         return var6;
      }
   }

   private static BufferedImage a(BufferedImage var0, int var1, int var2) {
      int var3 = var0.getWidth();
      int var4 = var0.getHeight();
      float[] var5 = new float[var3 * var4];
      float[] var6 = new float[var3 * var4];
      float[] var7 = new float[var3 * var4];
      float[] var8 = new float[var3 * var4];

      for (int var9 = 0; var9 < var4; var9++) {
         for (int var10 = 0; var10 < var3; var10++) {
            int var11 = var0.getRGB(var10, var9);
            int var12 = var9 * var3 + var10;
            float var13 = (var11 >>> 24) / 255.0F;
            var5[var12] = var13;
            var6[var12] = (var11 >> 16 & 0xFF) * var13;
            var7[var12] = (var11 >> 8 & 0xFF) * var13;
            var8[var12] = (var11 & 0xFF) * var13;
         }
      }

      BufferedImage var28 = new BufferedImage(var1, var2, 2);
      float var29 = (float)var3 / var1;
      float var30 = (float)var4 / var2;

      for (int var31 = 0; var31 < var2; var31++) {
         float var32 = var31 * var30;
         float var14 = var32 + var30;

         for (int var15 = 0; var15 < var1; var15++) {
            float var16 = var15 * var29;
            float var17 = var16 + var29;
            float var18 = 0.0F;
            float var19 = 0.0F;
            float var20 = 0.0F;
            float var21 = 0.0F;
            float var22 = 0.0F;

            for (int var23 = (int)var32; var23 < Math.min(var4, (int)Math.ceil(var14)); var23++) {
               float var24 = Math.min(var14, (float)(var23 + 1)) - Math.max(var32, (float)var23);

               for (int var25 = (int)var16; var25 < Math.min(var3, (int)Math.ceil(var17)); var25++) {
                  float var26 = var24 * (Math.min(var17, (float)(var25 + 1)) - Math.max(var16, (float)var25));
                  int var27 = var23 * var3 + var25;
                  var18 += var5[var27] * var26;
                  var19 += var6[var27] * var26;
                  var20 += var7[var27] * var26;
                  var21 += var8[var27] * var26;
                  var22 += var26;
               }
            }

            if (!(var22 <= 0.0F) && !(var18 <= 0.0F)) {
               int var33 = Math.min(255, Math.round(var18 / var22 * 255.0F));
               var28.setRGB(
                  var15,
                  var31,
                  var33 << 24
                     | Math.min(255, Math.round(var19 / var18)) << 16
                     | Math.min(255, Math.round(var20 / var18)) << 8
                     | Math.min(255, Math.round(var21 / var18))
               );
            }
         }
      }

      return var28;
   }

   private void b(ResourceLocation var1, float var2, float var3, float var4, float var5, int var6) {
      Minecraft.getMinecraft().getTextureManager().bindTexture(var1);
      GL11.glTexParameteri(3553, 10241, 9729);
      GL11.glTexParameteri(3553, 10240, 9729);
      this.b(var2, var3, var4, var5, var6);
   }

   private void a(DynamicTexture var1, float var2, float var3, float var4, float var5, int var6) {
      GlStateManager.bindTexture(var1.getGlTextureId());
      this.b(var2, var3, var4, var5, var6);
   }

   private void b(float var1, float var2, float var3, float var4, int var5) {
      GlStateManager.enableBlend();
      GlStateManager.enableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 771);
      GlStateManager.color((var5 >> 16 & 0xFF) / 255.0F, (var5 >> 8 & 0xFF) / 255.0F, (var5 & 0xFF) / 255.0F, (var5 >>> 24 & 0xFF) / 255.0F);
      Tessellator var6 = Tessellator.getInstance();
      WorldRenderer var7 = var6.getWorldRenderer();
      var7.begin(7, DefaultVertexFormats.POSITION_TEX);
      var7.pos(var1, var2 + var4, 0.0).tex(0.0, 1.0).endVertex();
      var7.pos(var1 + var3, var2 + var4, 0.0).tex(1.0, 1.0).endVertex();
      var7.pos(var1 + var3, var2, 0.0).tex(1.0, 0.0).endVertex();
      var7.pos(var1, var2, 0.0).tex(0.0, 0.0).endVertex();
      var6.draw();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   void a(ResourceLocation var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      this.a(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, 1.0F);
   }

   void a(ResourceLocation var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11) {
      Minecraft.getMinecraft().getTextureManager().bindTexture(var1);
      GlStateManager.enableBlend();
      GlStateManager.enableTexture2D();
      GlStateManager.disableCull();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 771);
      GlStateManager.color(1.0F, 1.0F, 1.0F, var11);
      float var12 = Math.min(var6, Math.min(var4, var5) / 2.0F);
      Tessellator var13 = Tessellator.getInstance();
      WorldRenderer var14 = var13.getWorldRenderer();
      var14.begin(6, DefaultVertexFormats.POSITION_TEX);
      var14.pos(var2 + var4 / 2.0F, var3 + var5 / 2.0F, 0.0).tex((var7 + var9) / 2.0F, (var8 + var10) / 2.0F).endVertex();

      for (int var15 = 0; var15 <= 52; var15++) {
         int var16 = var15 % 52;
         int var17 = var16 / 13;
         int var18 = var16 * 2;
         float var19 = var17 < 2 ? var2 + var4 - var12 : var2 + var12;
         float var20 = var17 != 0 && var17 != 3 ? var3 + var5 - var12 : var3 + var12;
         float var21 = var19 + ROUND_OFFSETS[var18] * var12;
         float var22 = var20 + ROUND_OFFSETS[var18 + 1] * var12;
         var14.pos(var21, var22, 0.0).tex(var7 + (var21 - var2) / var4 * (var9 - var7), var8 + (var22 - var3) / var5 * (var10 - var8)).endVertex();
      }

      var13.draw();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.enableCull();
   }

   static void a() {
      GlStateManager.disableTexture2D();
      GlStateManager.enableTexture2D();
   }

   void b() {
      for (com.cheatbreaker.client.kazerui.gui.menus.MenuGraphics.aInner var2 : this.cache.values()) {
         var2.texture.deleteGlTexture();
      }

      this.cache.clear();
      this.widths.clear();
   }

   static final class aInner {
      final DynamicTexture texture;
      final float width;
      final float height;

      aInner(BufferedImage var1, float var2, float var3, String var4) {
         this.texture = new DynamicTexture(var1);
         GlStateManager.bindTexture(this.texture.getGlTextureId());
         this.texture.setBlurMipmap(true, false);
         this.width = var2;
         this.height = var3;
      }
   }
}
