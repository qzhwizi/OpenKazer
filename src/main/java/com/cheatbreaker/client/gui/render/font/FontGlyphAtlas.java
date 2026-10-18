package com.cheatbreaker.client.gui.render.font;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class FontGlyphAtlas {
   protected com.cheatbreaker.client.gui.render.font.FontGlyphAtlas.aInner[] charData = new com.cheatbreaker.client.gui.render.font.FontGlyphAtlas.aInner[256];
   protected DynamicTexture tex;
   protected Font font;
   protected boolean antiAlias;
   protected boolean fractionalMetrics;
   protected int fontHeight = -1;
   protected int charOffset = 0;
   private float imgSize = 1048.0F;

   public FontGlyphAtlas(ResourceLocation var1, float var2) {
      Font var3;
      try {
         InputStream var4 = Minecraft.getMinecraft().getResourceManager().getResource(var1).getInputStream();
         var3 = Font.createFont(0, var4).deriveFont(var2);
      } catch (FontFormatException | IOException var5) {
         var3 = new Font("Arial", 0, (int)var2);
         var5.printStackTrace();
      }

      this.font = var3;
      this.antiAlias = true;
      this.fractionalMetrics = true;
      this.tex = this.a(this.font, true, true, this.charData);
   }

   protected DynamicTexture a(Font var1, boolean var2, boolean var3, com.cheatbreaker.client.gui.render.font.FontGlyphAtlas.aInner[] var4) {
      BufferedImage var5 = this.b(var1, var2, var3, var4);

      try {
         return new DynamicTexture(var5);
      } catch (Exception var7) {
         var7.printStackTrace();
         return null;
      }
   }

   private BufferedImage b(Font var1, boolean var2, boolean var3, com.cheatbreaker.client.gui.render.font.FontGlyphAtlas.aInner[] var4) {
      int var5 = (int)this.imgSize;
      BufferedImage var6 = new BufferedImage(var5, var5, 2);
      Graphics2D var7 = (Graphics2D)var6.getGraphics();
      var7.setFont(var1);
      var7.setColor(new Color(255, 255, 255, 0));
      var7.fillRect(0, 0, var5, var5);
      var7.setColor(Color.WHITE);
      var7.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, var3 ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
      var7.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, var2 ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
      var7.setRenderingHint(RenderingHints.KEY_ANTIALIASING, var2 ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
      FontMetrics var8 = var7.getFontMetrics();
      int var9 = 0;
      int var10 = 0;
      int var11 = 1;

      for (int var12 = 0; var12 < var4.length; var12++) {
         char var13 = (char)var12;
         com.cheatbreaker.client.gui.render.font.FontGlyphAtlas.aInner var14 = new com.cheatbreaker.client.gui.render.font.FontGlyphAtlas.aInner();
         Rectangle2D var15 = var8.getStringBounds(String.valueOf(var13), var7);
         var14.width = var15.getBounds().width + 8;
         var14.height = var15.getBounds().height;
         Rectangle2D var16 = var1.createGlyphVector(var7.getFontRenderContext(), String.valueOf(var13)).getVisualBounds();
         var14.inkTop = (float)(var8.getAscent() + var16.getY()) / 2.0F;
         var14.inkBottom = (float)(var8.getAscent() + var16.getMaxY()) / 2.0F;
         if (var10 + var14.width >= var5) {
            var10 = 0;
            var11 += var9;
            var9 = 0;
         }

         if (var14.height > var9) {
            var9 = var14.height;
         }

         var14.storedX = var10;
         var14.storedY = var11;
         if (var14.height > this.fontHeight) {
            this.fontHeight = var14.height;
         }

         var4[var12] = var14;
         var7.drawString(String.valueOf(var13), var10 + 2, var11 + var8.getAscent());
         var10 += var14.width;
      }

      return var6;
   }

   protected void a(com.cheatbreaker.client.gui.render.font.FontGlyphAtlas.aInner[] var1, char var2, float var3, float var4) throws ArrayIndexOutOfBoundsException {
      try {
         this.a(var3, var4, var1[var2].width, var1[var2].height, var1[var2].storedX, var1[var2].storedY, var1[var2].width, var1[var2].height);
      } catch (Exception var6) {
         var6.printStackTrace();
      }
   }

   private void a(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float var9 = var5 / this.imgSize;
      float var10 = var6 / this.imgSize;
      float var11 = var7 / this.imgSize;
      float var12 = var8 / this.imgSize;
      GL11.glTexCoord2f(var9 + var11, var10);
      GL11.glVertex2d(var1 + var3, var2);
      GL11.glTexCoord2f(var9, var10);
      GL11.glVertex2d(var1, var2);
      GL11.glTexCoord2f(var9, var10 + var12);
      GL11.glVertex2d(var1, var2 + var4);
      GL11.glTexCoord2f(var9, var10 + var12);
      GL11.glVertex2d(var1, var2 + var4);
      GL11.glTexCoord2f(var9 + var11, var10 + var12);
      GL11.glVertex2d(var1 + var3, var2 + var4);
      GL11.glTexCoord2f(var9 + var11, var10);
      GL11.glVertex2d(var1 + var3, var2);
   }

   public int a() {
      return (this.fontHeight - 8) / 2;
   }

   public int a(String var1) {
      int var2 = 0;

      for (char var6 : var1.toCharArray()) {
         if (var6 < this.charData.length) {
            var2 += this.charData[var6].width - 8 + this.charOffset;
         }
      }

      return var2 / 2;
   }

   public boolean b() {
      return this.antiAlias;
   }

   public void a(boolean var1) {
      if (this.antiAlias != var1) {
         this.antiAlias = var1;
         this.tex = this.a(this.font, var1, this.fractionalMetrics, this.charData);
      }
   }

   public boolean c() {
      return this.fractionalMetrics;
   }

   public void b(boolean var1) {
      if (this.fractionalMetrics != var1) {
         this.fractionalMetrics = var1;
         this.tex = this.a(this.font, this.antiAlias, var1, this.charData);
      }
   }

   public Font d() {
      return this.font;
   }

   public void a(Font var1) {
      this.font = var1;
      this.tex = this.a(var1, this.antiAlias, this.fractionalMetrics, this.charData);
   }

   protected class aInner {
      public int width;
      public int height;
      public int storedX;
      public int storedY;
      public float inkTop;
      public float inkBottom;
   }
}
