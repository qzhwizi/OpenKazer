package com.cheatbreaker.client.kazerui.input;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public final class WindowMode {
   private static final String UNDECORATED = "org.lwjgl.opengl.Window.undecorated";
   private static int windowX;
   private static int windowY;
   private static int windowWidth;
   private static int windowHeight;
   private static boolean saved;
   private static boolean borderless;
   private static String originalDecoration;

   public static boolean a() {
      return Boolean.parseBoolean(com.cheatbreaker.client.kazerui.module.ModuleRegistry.a("global", "borderlessFullscreen", "true"));
   }

   public static boolean active() {
      return borderless;
   }

   public static DisplayMode a(boolean var0, DisplayMode var1, int var2, int var3) throws LWJGLException {
      if (var0 && !saved) {
         windowX = Display.getX();
         windowY = Display.getY();
         windowWidth = Math.max(1, Display.getWidth());
         windowHeight = Math.max(1, Display.getHeight());
         if (Display.isFullscreen()) {
            windowWidth = Math.max(1, var2);
            windowHeight = Math.max(1, var3);
            Rectangle var4 = a(windowX, windowY);
            windowX = var4.x + (var4.width - windowWidth) / 2;
            windowY = var4.y + (var4.height - windowHeight) / 2;
         }

         originalDecoration = System.getProperty("org.lwjgl.opengl.Window.undecorated");
         saved = true;
      }

      boolean var10 = var0 && a();
      if (var10 && !Display.isFullscreen()) {
         DisplayMode var5 = com.cheatbreaker.client.kazerui.input.BorderlessWindow.a();
         if (var5 != null) {
            borderless = true;
            return var5;
         }
      }

      DisplayMode var11 = borderless ? com.cheatbreaker.client.kazerui.input.BorderlessWindow.b() : null;
      if (var11 != null && !var0) {
         borderless = false;
         saved = false;
         return var11;
      } else {
         int var7 = windowX;
         int var8 = windowY;
         DisplayMode var6;
         if (var10) {
            Rectangle var9 = a(windowX + windowWidth / 2, windowY + windowHeight / 2);
            var6 = new DisplayMode(var9.width, var9.height);
            var7 = var9.x;
            var8 = var9.y;
            System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
         } else {
            if (originalDecoration == null) {
               System.clearProperty("org.lwjgl.opengl.Window.undecorated");
            } else {
               System.setProperty("org.lwjgl.opengl.Window.undecorated", originalDecoration);
            }

            var6 = var0 ? var1 : new DisplayMode(saved ? windowWidth : Math.max(1, var2), saved ? windowHeight : Math.max(1, var3));
         }

         Display.setDisplayModeAndFullscreen(var6);
         if (!var0 || var10) {
            Display.setLocation(var7, var8);
         }

         borderless = var10;
         if (!var0) {
            saved = false;
         }

         return var6;
      }
   }

   private static Rectangle a(int var0, int var1) {
      GraphicsEnvironment var2 = GraphicsEnvironment.getLocalGraphicsEnvironment();

      for (GraphicsDevice var6 : var2.getScreenDevices()) {
         Rectangle var7 = var6.getDefaultConfiguration().getBounds();
         if (var7.contains(var0, var1)) {
            return var7;
         }
      }

      return var2.getDefaultScreenDevice().getDefaultConfiguration().getBounds();
   }

   private WindowMode() {
   }
}
