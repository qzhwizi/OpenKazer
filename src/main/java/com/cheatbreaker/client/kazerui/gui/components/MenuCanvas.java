package com.cheatbreaker.client.kazerui.gui.components;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public interface MenuCanvas {
   String BOLD = "roboto-bold.ttf";
   String MEDIUM = "roboto-medium.ttf";
   String LIGHT = "roboto-light.ttf";

   void a(float var1, float var2, float var3, float var4, int var5);

   void a(float var1, float var2, float var3, float var4, float var5, int var6);

   void a(float var1, float var2, float var3, float var4, float var5, int var6, boolean var7, boolean var8, boolean var9, boolean var10);

   void b(float var1, float var2, float var3, float var4, float var5, int var6);

   void a(String var1, String var2, float var3, float var4, float var5, int var6);

   float a(String var1, String var2, float var3);

   void a(String var1, float var2, float var3, float var4, float var5, int var6);

   void a(BufferedImage var1, float var2, float var3, float var4, float var5, int var6);

   void a(String var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9);

   Graphics2D a();
}
