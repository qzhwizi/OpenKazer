package com.cheatbreaker.client.gui.render.font;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public final class TrueTypeFontRenderer extends com.cheatbreaker.client.gui.render.font.FontGlyphAtlas {
   protected final com.cheatbreaker.client.gui.render.font.FontGlyphAtlas.aInner[] boldItalicChars = new com.cheatbreaker.client.gui.render.font.FontGlyphAtlas.aInner[256];
   protected final com.cheatbreaker.client.gui.render.font.FontGlyphAtlas.aInner[] italicChars = new com.cheatbreaker.client.gui.render.font.FontGlyphAtlas.aInner[256];
   protected final com.cheatbreaker.client.gui.render.font.FontGlyphAtlas.aInner[] boldChars = new com.cheatbreaker.client.gui.render.font.FontGlyphAtlas.aInner[256];
   private final int[] colorCode = new int[32];
   private char COLOR_CODE_START = 167;
   protected DynamicTexture texBold;
   protected DynamicTexture texItalic;
   protected DynamicTexture texItalicBold;

   public TrueTypeFontRenderer(ResourceLocation var1, float var2) {
      super(var1, var2);
      this.g();
      this.f();
   }

   public float a(String var1, double var2, double var4, int var6, int var7) {
      float var8 = this.a(var1, var2 + 1.0, var4 + 1.0, var7, false);
      return Math.max(var8, this.a(var1, var2, var4, var6, false));
   }

   public float a(String var1, double var2, double var4, int var6) {
      float var7 = this.a(var1, var2 + 1.0, var4 + 1.0, var6, true);
      return Math.max(var7, this.a(var1, var2, var4, var6, false));
   }

   public float a(String var1, float var2, float var3, int var4) {
      return this.a(var1, var2, var3, var4, false);
   }

   public float b(String var1, float var2, float var3, int var4) {
      return this.a(var1, var2 - this.a(var1) / 2.0F, var3, var4);
   }

   public void a(String var1, float var2, float var3, float var4, float var5, int var6) {
      float var7 = Float.MAX_VALUE;
      float var8 = -Float.MAX_VALUE;
      String var9 = EnumChatFormatting.getTextWithoutFormattingCodes(var1);

      for (char var13 : var9.toCharArray()) {
         if (!Character.isWhitespace(var13)) {
            if (var13 < this.charData.length) {
               var7 = Math.min(var7, this.charData[var13].inkTop);
               var8 = Math.max(var8, this.charData[var13].inkBottom);
            } else {
               var7 = Math.min(var7, 0.0F);
               var8 = Math.max(var8, 9.0F);
            }
         }
      }

      if (var7 != Float.MAX_VALUE) {
         this.b(var1, (double)(var2 + (var4 - this.b(var1)) / 2.0F), (double)(var3 + (var5 - var8 + var7) / 2.0F - var7), var6);
      }
   }

   public float c(String var1, float var2, float var3, int var4) {
      this.a(var1, var2 - this.a(var1) / 2.0F + 1.0, var3 + 1.0, var4, true);
      return this.a(var1, var2 - this.a(var1) / 2.0F, var3, var4);
   }

   public float a(String var1, double var2, double var4, int var6, boolean var7) {
      var2--;
      if (var1 == null) {
         return 0.0F;
      } else {
         if (var6 == 553648127) {
            var6 = 16777215;
         }

         if ((var6 & -67108864) == 0) {
            var6 |= -16777216;
         }

         if (var7) {
            var6 = (var6 & 16579836) >> 2 | var6 & 0xFF000000;
         }

         FontRenderer var8 = this.e(var1);
         if (var8 != null) {
            return var8.drawString(var1, (float)var2, (float)var4, var6, false);
         } else {
            com.cheatbreaker.client.gui.render.font.FontGlyphAtlas.aInner[] var9 = this.charData;
            float var10 = (var6 >> 24 & 0xFF) / 255.0F;
            boolean var11 = false;
            boolean var12 = false;
            boolean var13 = false;
            boolean var14 = false;
            boolean var15 = true;
            var2 *= 2.0;
            var4 = (var4 - 3.0) * 2.0;
            if (var15) {
               GL11.glPushMatrix();
               cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
               GL11.glScaled(0.5, 0.5, 0.5);
               cn.kazer.client189.render.GlStateBridge.a(3042);
               cn.kazer.client189.render.GlStateBridge.b(770, 771);
               cn.kazer.client189.render.GlStateBridge.a((var6 >> 16 & 0xFF) / 255.0F, (var6 >> 8 & 0xFF) / 255.0F, (var6 & 0xFF) / 255.0F, var10);
               int var16 = var1.length();
               cn.kazer.client189.render.GlStateBridge.a(3553);
               cn.kazer.client189.render.GlStateBridge.a(3553, this.tex.getGlTextureId());
               boolean var17 = false;

               for (int var18 = 0; var18 < var16; var18++) {
                  char var19 = var1.charAt(var18);
                  if (var19 == this.COLOR_CODE_START && var18 < var16 - 1) {
                     if (var17) {
                        GL11.glEnd();
                        var17 = false;
                     }

                     int var20 = 21;

                     try {
                        var20 = "0123456789abcdefklmnor".indexOf(var1.charAt(var18 + 1));
                     } catch (Exception var22) {
                        var22.printStackTrace();
                     }

                     if (var20 >= 16) {
                        if (var20 != 16) {
                           if (var20 == 17) {
                              var11 = true;
                              if (var12) {
                                 cn.kazer.client189.render.GlStateBridge.a(3553, this.texItalicBold.getGlTextureId());
                                 var9 = this.boldItalicChars;
                              } else {
                                 cn.kazer.client189.render.GlStateBridge.a(3553, this.texBold.getGlTextureId());
                                 var9 = this.boldChars;
                              }
                           } else if (var20 == 18) {
                              var13 = true;
                           } else if (var20 == 19) {
                              var14 = true;
                           } else if (var20 == 20) {
                              var12 = true;
                              if (var11) {
                                 cn.kazer.client189.render.GlStateBridge.a(3553, this.texItalicBold.getGlTextureId());
                                 var9 = this.boldItalicChars;
                              } else {
                                 cn.kazer.client189.render.GlStateBridge.a(3553, this.texItalic.getGlTextureId());
                                 var9 = this.italicChars;
                              }
                           } else if (var20 == 21) {
                              var11 = false;
                              var12 = false;
                              var14 = false;
                              var13 = false;
                              cn.kazer.client189.render.GlStateBridge.a((var6 >> 16 & 0xFF) / 255.0F, (var6 >> 8 & 0xFF) / 255.0F, (var6 & 0xFF) / 255.0F, var10);
                              cn.kazer.client189.render.GlStateBridge.a(3553, this.tex.getGlTextureId());
                              var9 = this.charData;
                           }
                        }
                     } else {
                        var11 = false;
                        var12 = false;
                        var14 = false;
                        var13 = false;
                        cn.kazer.client189.render.GlStateBridge.a(3553, this.tex.getGlTextureId());
                        var9 = this.charData;
                        if (var20 < 0 || var20 > 15) {
                           var20 = 15;
                        }

                        if (var7) {
                           var20 += 16;
                        }

                        int var21 = this.colorCode[var20];
                        cn.kazer.client189.render.GlStateBridge.a((var21 >> 16 & 0xFF) / 255.0F, (var21 >> 8 & 0xFF) / 255.0F, (var21 & 0xFF) / 255.0F, var10);
                     }

                     var18++;
                  } else if (var19 < var9.length && var19 >= 0) {
                     if (!var17) {
                        GL11.glBegin(4);
                        var17 = true;
                     }

                     this.a(var9, var19, (float)var2, (float)var4 + 6.0F);
                     if (var13 || var14) {
                        GL11.glEnd();
                        var17 = false;
                     }

                     if (var13) {
                        this.a(var2, var4 + var9[var19].height / 2, var2 + var9[var19].width - 8.0, var4 + var9[var19].height / 2);
                     }

                     if (var14) {
                        this.a(var2, var4 + var9[var19].height - 2.0, var2 + var9[var19].width - 8.0, var4 + var9[var19].height - 2.0);
                     }

                     var2 += var9[var19].width - 8 + this.charOffset;
                  }
               }

               if (var17) {
                  GL11.glEnd();
               }

               GL11.glHint(3155, 4352);
               GL11.glPopMatrix();
            }

            return (float)var2 / 2.0F;
         }
      }
   }

   @Override
   public int a(String var1) {
      if (var1 == null) {
         return 0;
      } else {
         FontRenderer var2 = this.e(var1);
         if (var2 != null) {
            return var2.getStringWidth(var1);
         } else {
            int var3 = 0;
            com.cheatbreaker.client.gui.render.font.FontGlyphAtlas.aInner[] var4 = this.charData;
            boolean var5 = false;
            boolean var6 = false;
            int var7 = var1.length();

            for (int var8 = 0; var8 < var7; var8++) {
               char var9 = var1.charAt(var8);
               if (var9 == this.COLOR_CODE_START) {
                  int var10 = "0123456789abcdefklmnor".indexOf(var9);
                  if (var10 < 16) {
                     var5 = false;
                     var6 = false;
                  } else if (var10 == 17) {
                     var5 = true;
                     if (var6) {
                        var4 = this.boldItalicChars;
                     } else {
                        var4 = this.boldChars;
                     }
                  } else if (var10 == 20) {
                     var6 = true;
                     if (var5) {
                        var4 = this.boldItalicChars;
                     } else {
                        var4 = this.italicChars;
                     }
                  } else if (var10 == 21) {
                     var5 = false;
                     var6 = false;
                     var4 = this.charData;
                  }

                  var8++;
               } else if (var9 < var4.length) {
                  var3 += var4[var9].width - 8 + this.charOffset;
               }
            }

            return var3 / 2;
         }
      }
   }

   public float b(String var1, double var2, double var4, int var6) {
      if (!c(var1)) {
         this.a(var1, var2, var4, var6, false);
         return this.a(var1);
      } else {
         double var7 = var2;

         for (com.cheatbreaker.client.gui.render.font.UnicodeTextSplitter.a var10 : com.cheatbreaker.client.gui.render.font.UnicodeTextSplitter.a(var1)) {
            if (var10.unicode) {
               FontRenderer var11 = e();
               if (var11 != null) {
                  var11.drawString(var10.text, (int)var7, (int)var4, var6, false);
               }

               var7 += this.d(var10.text);
            } else {
               this.a(var10.text, var7, var4, var6, false);
               var7 += this.a(var10.text);
            }
         }

         return (float)(var7 - var2);
      }
   }

   public float c(String var1, double var2, double var4, int var6) {
      if (!c(var1)) {
         this.a(var1, var2, var4, var6);
         return this.a(var1);
      } else {
         double var7 = var2;

         for (com.cheatbreaker.client.gui.render.font.UnicodeTextSplitter.a var10 : com.cheatbreaker.client.gui.render.font.UnicodeTextSplitter.a(var1)) {
            if (var10.unicode) {
               FontRenderer var11 = e();
               if (var11 != null) {
                  var11.drawStringWithShadow(var10.text, (int)var7, (int)var4, var6);
               }

               var7 += this.d(var10.text);
            } else {
               this.a(var10.text, var7 + 1.0, var4 + 1.0, var6, true);
               this.a(var10.text, var7, var4, var6, false);
               var7 += this.a(var10.text);
            }
         }

         return (float)(var7 - var2);
      }
   }

   public int b(String var1) {
      if (!c(var1)) {
         return this.a(var1);
      } else {
         int var2 = 0;

         for (com.cheatbreaker.client.gui.render.font.UnicodeTextSplitter.a var4 : com.cheatbreaker.client.gui.render.font.UnicodeTextSplitter.a(var1)) {
            var2 += var4.unicode ? this.d(var4.text) : this.a(var4.text);
         }

         return var2;
      }
   }

   private static FontRenderer e() {
      Minecraft var0 = Minecraft.getMinecraft();
      return var0 == null ? null : var0.fontRendererObj;
   }

   private int d(String var1) {
      FontRenderer var2 = e();
      return var2 != null ? var2.getStringWidth(var1) : this.a(var1);
   }

   static boolean c(String var0) {
      if (var0 == null) {
         return false;
      } else {
         for (int var1 = 0; var1 < var0.length(); var1++) {
            char var2 = var0.charAt(var1);
            if (var2 >= 256 && !Character.isSurrogate(var2)) {
               return true;
            }
         }

         return false;
      }
   }

   private FontRenderer e(String var1) {
      if (!c(var1)) {
         return null;
      } else {
         Minecraft var2 = Minecraft.getMinecraft();
         return var2 == null ? null : var2.fontRendererObj;
      }
   }

   public String a(String var1, double var2) {
      return this.a(var1, var2, false);
   }

   public String a(String var1, double var2, boolean var4) {
      StringBuilder var5 = new StringBuilder();
      float var6 = 0.0F;
      int var7 = var4 ? var1.length() - 1 : 0;
      int var8 = var4 ? -1 : 1;
      boolean var9 = false;
      boolean var10 = false;

      for (int var11 = var7; var11 >= 0 && var11 < var1.length() && var6 < (float)var2; var11 += var8) {
         char var12 = var1.charAt(var11);
         double var13 = this.a(String.valueOf(var12));
         if (var9) {
            var9 = false;
            if (var12 == 'l' || var12 == 'L') {
               var10 = true;
            } else if (var12 == 'r' || var12 == 'R') {
               var10 = false;
            }
         } else if (var13 < 0.0) {
            var9 = true;
         } else {
            var6 = (float)(var6 + var13);
            if (var10) {
               var6++;
            }
         }

         if (var6 > (float)var2) {
            break;
         }

         if (var4) {
            var5.insert(0, var12);
         } else {
            var5.append(var12);
         }
      }

      return var5.toString();
   }

   @Override
   public void a(boolean var1) {
      super.a(var1);
      this.f();
   }

   @Override
   public void b(boolean var1) {
      super.b(var1);
      this.f();
   }

   private void f() {
      this.texBold = this.a(this.font.deriveFont(1), this.antiAlias, this.fractionalMetrics, this.boldChars);
      this.texItalic = this.a(this.font.deriveFont(2), this.antiAlias, this.fractionalMetrics, this.italicChars);
      this.texItalicBold = this.a(this.font.deriveFont(3), this.antiAlias, this.fractionalMetrics, this.boldItalicChars);
   }

   private void a(double var1, double var3, double var5, double var7) {
      cn.kazer.client189.render.GlStateBridge.b(3553);
      GL11.glLineWidth(1.0F);
      GL11.glBegin(1);
      GL11.glVertex2d(var1, var3);
      GL11.glVertex2d(var5, var7);
      GL11.glEnd();
      cn.kazer.client189.render.GlStateBridge.a(3553);
   }

   public String b(String var1, double var2) {
      StringBuilder var4 = new StringBuilder();
      StringBuilder var5 = new StringBuilder();
      boolean var6 = false;

      for (char var10 : var1.toCharArray()) {
         if (var6) {
            var4.append(var10);
            var6 = false;
         } else if (var10 == 167) {
            var4.append(var10);
            var6 = true;
         } else {
            var4.append(var10);
            int var13 = this.a(var4.toString());
            if (var13 >= var2) {
               String var14 = var4.toString();
               String var11;
               String var12;
               if (var14.contains(" ")) {
                  var12 = var14.substring(0, var14.lastIndexOf(" "));
                  var11 = var14.substring(var14.lastIndexOf(" "));
                  if (var11.startsWith(" ")) {
                     var11 = var11.replaceFirst(" ", "");
                  }
               } else {
                  var12 = var14.substring(0, var14.length() - 1);
                  var11 = var14.substring(var14.length() - 1);
               }

               var5.append(var12).append("\n");
               String var15 = EnumChatFormatting.getTextWithoutFormattingCodes(var4.toString());
               var4.setLength(0);
               var4.append(var11).append(var15);
            }
         }
      }

      var5.append((CharSequence)var4);
      return var5.length() == 0 ? var1 : var5.toString();
   }

   public List<String> c(String var1, double var2) {
      ArrayList var4 = new ArrayList();
      if (this.a(var1) > var2) {
         String[] var5 = var1.split(" ");
         StringBuilder var6 = new StringBuilder();
         char var7 = '\uffff';

         for (String var11 : var5) {
            for (int var12 = 0; var12 < var11.toCharArray().length; var12++) {
               char var13 = var11.toCharArray()[var12];
               if (var13 == this.COLOR_CODE_START && var12 < var11.toCharArray().length - 1) {
                  var7 = var11.toCharArray()[var12 + 1];
               }
            }

            if (this.a(var6 + var11 + " ") < var2) {
               var6.append((String)var11).append(" ");
            } else {
               var4.add(var6.toString());
               var6 = new StringBuilder(this.COLOR_CODE_START + var7 + var11 + " ");
            }
         }

         if (var6.length() > 0) {
            if (this.a(var6.toString()) < var2) {
               var4.add(this.COLOR_CODE_START + var7 + var6.toString() + " ");
            } else {
               var4.addAll(this.d(var6.toString(), var2));
            }
         }
      } else {
         var4.add(var1);
      }

      return var4;
   }

   public List<String> d(String var1, double var2) {
      ArrayList var4 = new ArrayList();
      StringBuilder var5 = new StringBuilder();
      char var6 = '\uffff';
      char[] var7 = var1.toCharArray();

      for (int var8 = 0; var8 < var7.length; var8++) {
         char var9 = var7[var8];
         if (var9 == this.COLOR_CODE_START && var8 < var7.length - 1) {
            var6 = var7[var8 + 1];
         }

         if (this.a(var5.toString() + var9) < var2) {
            var5.append(var9);
         } else {
            var4.add(var5.toString());
            var5 = new StringBuilder(this.COLOR_CODE_START + var6 + String.valueOf(var9));
         }
      }

      if (var5.length() > 0) {
         var4.add(var5.toString());
      }

      return var4;
   }

   private void g() {
      for (int var1 = 0; var1 < 32; var1++) {
         int var2 = (var1 >> 3 & 1) * 85;
         int var3 = (var1 >> 2 & 1) * 170 + var2;
         int var4 = (var1 >> 1 & 1) * 170 + var2;
         int var5 = (var1 & 1) * 170 + var2;
         if (var1 == 6) {
            var3 += 85;
         }

         if (var1 >= 16) {
            var3 /= 4;
            var4 /= 4;
            var5 /= 4;
         }

         this.colorCode[var1] = (var3 & 0xFF) << 16 | (var4 & 0xFF) << 8 | var5 & 0xFF;
      }
   }
}
