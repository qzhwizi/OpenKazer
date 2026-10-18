package com.cheatbreaker.client.gui.hudeditor;

import net.minecraft.client.gui.ScaledResolution;

public class AnchorHelper {
   public static com.cheatbreaker.client.gui.hudeditor.GuiAnchor a(float var0, float var1, ScaledResolution var2) {
      int var3 = var2.getScaledWidth();
      int var4 = var2.getScaledHeight();
      if (var0 < var3 / 3 && var1 < var4 / 3) {
         return com.cheatbreaker.client.gui.hudeditor.GuiAnchor.LEFT_TOP;
      } else if (var0 > var3 / 3 * 2 && var1 < var4 / 3) {
         return com.cheatbreaker.client.gui.hudeditor.GuiAnchor.RIGHT_TOP;
      } else if (var1 < var4 / 3) {
         return com.cheatbreaker.client.gui.hudeditor.GuiAnchor.MIDDLE_TOP;
      } else if (var0 < var3 / 3 && var1 < var4 / 3 * 2) {
         return com.cheatbreaker.client.gui.hudeditor.GuiAnchor.LEFT_MIDDLE;
      } else if (var0 > var3 / 3 * 2 && var1 < var4 / 3 * 2) {
         return com.cheatbreaker.client.gui.hudeditor.GuiAnchor.RIGHT_MIDDLE;
      } else if (var1 < var4 / 3 * 2) {
         return com.cheatbreaker.client.gui.hudeditor.GuiAnchor.MIDDLE_MIDDLE;
      } else if (var0 < var3 / 3) {
         return com.cheatbreaker.client.gui.hudeditor.GuiAnchor.LEFT_BOTTOM;
      } else if (var0 < var3 / 3 * 2) {
         return var0 > var3 / 3 + var3 / 6 ? com.cheatbreaker.client.gui.hudeditor.GuiAnchor.MIDDLE_BOTTOM_RIGHT : com.cheatbreaker.client.gui.hudeditor.GuiAnchor.MIDDLE_BOTTOM_LEFT;
      } else {
         return com.cheatbreaker.client.gui.hudeditor.GuiAnchor.RIGHT_BOTTOM;
      }
   }

   public static float[] b(float var0, float var1, ScaledResolution var2) {
      float var3 = var2.getScaledWidth();
      float var4 = var2.getScaledHeight();
      com.cheatbreaker.client.gui.hudeditor.GuiAnchor var5 = a(var0, var1, var2);
      float var6 = 0.0F;
      float var7 = 0.0F;
      switch (var5) {
         case LEFT_TOP:
            var6 = 0.0F;
            var7 = 0.0F;
            break;
         case RIGHT_TOP:
            var6 = var3 / 3.0F * 2.0F;
            var7 = 0.0F;
            break;
         case MIDDLE_TOP:
            var6 = var3 / 3.0F;
            var7 = 0.0F;
            break;
         case LEFT_MIDDLE:
            var6 = 0.0F;
            var7 = var4 / 3.0F;
            break;
         case RIGHT_MIDDLE:
            var6 = var3 / 3.0F * 2.0F;
            var7 = var4 / 3.0F;
            break;
         case MIDDLE_MIDDLE:
            var6 = var3 / 3.0F;
            var7 = var4 / 3.0F;
            break;
         case LEFT_BOTTOM:
            var6 = 0.0F;
            var7 = var4 / 3.0F * 2.0F;
            break;
         case MIDDLE_BOTTOM_RIGHT:
            var6 = var3 / 3.0F + var3 / 6.0F;
            var7 = var4 / 3.0F * 2.0F;
            break;
         case MIDDLE_BOTTOM_LEFT:
            var6 = var3 / 3.0F;
            var7 = var4 / 3.0F * 2.0F;
            break;
         case RIGHT_BOTTOM:
            var6 = var3 / 3.0F * 2.0F;
            var7 = var4 / 3.0F * 2.0F;
      }

      return new float[]{var6, var7};
   }

   public static float[] a(com.cheatbreaker.client.gui.hudeditor.GuiAnchor var0, ScaledResolution var1, float var2, float var3, float var4) {
      float var5 = 0.0F;
      float var6 = 0.0F;
      var2 *= var4;
      var3 *= var4;
      switch (var0) {
         case LEFT_TOP:
            var5 = 2.0F;
            var6 = 2.0F;
            break;
         case RIGHT_TOP:
            var5 = var1.getScaledWidth() - var2 - 2.0F;
            var6 = 2.0F;
            break;
         case MIDDLE_TOP:
            var5 = var1.getScaledWidth() / 2 - var2 / 2.0F;
            var6 = 2.0F;
            break;
         case LEFT_MIDDLE:
            var5 = 2.0F;
            var6 = var1.getScaledHeight() / 2 - var3 / 2.0F;
            break;
         case RIGHT_MIDDLE:
            var5 = var1.getScaledWidth() - var2;
            var6 = var1.getScaledHeight() / 2 - var3 / 2.0F;
            break;
         case MIDDLE_MIDDLE:
            var5 = var1.getScaledWidth() / 2 - var2 / 2.0F;
            var6 = var1.getScaledHeight() / 2 - var3 / 2.0F;
            break;
         case LEFT_BOTTOM:
            var6 = var1.getScaledHeight() - var3 - 2.0F;
            var5 = 2.0F;
            break;
         case MIDDLE_BOTTOM_RIGHT:
            var5 = var1.getScaledWidth() / 2.0F;
            var6 = var1.getScaledHeight() - var3 - 2.0F;
            break;
         case MIDDLE_BOTTOM_LEFT:
            var5 = var1.getScaledWidth() / 2 - var2;
            var6 = var1.getScaledHeight() - var3 - 2.0F;
            break;
         case RIGHT_BOTTOM:
            var5 = var1.getScaledWidth() - var2;
            var6 = var1.getScaledHeight() - var3;
      }

      return new float[]{var5, var6};
   }

   public static float[] a(com.cheatbreaker.client.module.Module var0, float var1, float var2, ScaledResolution var3) {
      float var4 = var3.getScaledWidth();
      float var5 = var3.getScaledHeight();
      com.cheatbreaker.client.gui.hudeditor.GuiAnchor var6 = a(var1, var2, var3);
      float var7 = var0.i() * (Float)var0.scale.d();
      float var8 = var0.j() * (Float)var0.scale.d();
      float var9 = 0.0F;
      float var10 = 0.0F;
      switch (var6) {
         case LEFT_TOP:
            var9 = var7 / 2.0F;
            var10 = var8 / 2.0F;
            break;
         case RIGHT_TOP:
            var9 = var4 / 3.0F - var7 / 2.0F;
            var10 = var8 / 2.0F;
            break;
         case MIDDLE_TOP:
            var9 = var4 / 6.0F;
            var10 = var8 / 2.0F;
            break;
         case LEFT_MIDDLE:
            var9 = var7 / 2.0F;
            var10 = var5 / 6.0F;
            break;
         case RIGHT_MIDDLE:
            var9 = var4 / 3.0F - var7 / 2.0F;
            var10 = var5 / 6.0F;
            break;
         case MIDDLE_MIDDLE:
            var9 = var4 / 6.0F;
            var10 = var5 / 6.0F;
            break;
         case LEFT_BOTTOM:
         case MIDDLE_BOTTOM_RIGHT:
            var9 = var7 / 2.0F;
            var10 = var5 / 3.0F - var8 / 2.0F;
            break;
         case MIDDLE_BOTTOM_LEFT:
            var9 = var4 / 6.0F - var7 / 2.0F;
            var10 = var5 / 3.0F - var8 / 2.0F;
            break;
         case RIGHT_BOTTOM:
            var9 = var4 / 3.0F - var7 / 2.0F;
            var10 = var5 / 3.0F - var8 / 2.0F;
      }

      return new float[]{var9, var10};
   }

   public static float[] a(com.cheatbreaker.client.gui.hudeditor.GuiAnchor var0) {
      float var1 = 0.0F;
      float var2 = 0.0F;
      switch (var0) {
         case RIGHT_MIDDLE:
            var1 = -2.0F;
            break;
         case MIDDLE_MIDDLE:
            var2 = -50.0F;
            var1 = 0.0F;
            break;
         case LEFT_BOTTOM:
            var1 = 2.0F;
            var2 = -34.0F;
      }

      return new float[]{var1, var2};
   }

   public static com.cheatbreaker.client.gui.hudeditor.AnchorAlignment b(com.cheatbreaker.client.gui.hudeditor.GuiAnchor var0) {
      switch (var0) {
         case LEFT_TOP:
         case RIGHT_TOP:
         case MIDDLE_TOP:
            return com.cheatbreaker.client.gui.hudeditor.AnchorAlignment.TOP;
         case LEFT_MIDDLE:
         case RIGHT_MIDDLE:
         case MIDDLE_MIDDLE:
            return com.cheatbreaker.client.gui.hudeditor.AnchorAlignment.CENTER;
         case LEFT_BOTTOM:
         case MIDDLE_BOTTOM_RIGHT:
         case MIDDLE_BOTTOM_LEFT:
         case RIGHT_BOTTOM:
            return com.cheatbreaker.client.gui.hudeditor.AnchorAlignment.BOTTOM;
         default:
            return null;
      }
   }

   public static com.cheatbreaker.client.gui.hudeditor.AnchorAlignment c(com.cheatbreaker.client.gui.hudeditor.GuiAnchor var0) {
      switch (var0) {
         case LEFT_TOP:
         case LEFT_MIDDLE:
         case LEFT_BOTTOM:
         case MIDDLE_BOTTOM_RIGHT:
            return com.cheatbreaker.client.gui.hudeditor.AnchorAlignment.LEFT;
         case RIGHT_TOP:
         case RIGHT_MIDDLE:
         case MIDDLE_BOTTOM_LEFT:
         case RIGHT_BOTTOM:
            return com.cheatbreaker.client.gui.hudeditor.AnchorAlignment.RIGHT;
         case MIDDLE_TOP:
         case MIDDLE_MIDDLE:
            return com.cheatbreaker.client.gui.hudeditor.AnchorAlignment.CENTER;
         default:
            return null;
      }
   }
}
