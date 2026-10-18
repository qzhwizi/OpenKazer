package com.cheatbreaker.client.kazerui.input;

import com.sun.jna.Function;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import java.lang.reflect.Method;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

final class BorderlessWindow {
   private static boolean unavailable;
   private static boolean saved;
   private static long savedHandle;
   private static long savedStyle;
   private static int x;
   private static int y;
   private static int width;
   private static int height;
   private static int clientWidth;
   private static int clientHeight;
   private static final Logger LOG = LogManager.getLogger();

   static DisplayMode a() {
      if (!unavailable && System.getProperty("os.name", "").startsWith("Windows") && !Display.isFullscreen()) {
         try {
            long var0 = com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.a();
            if (var0 == 0L) {
               return null;
            } else {
               Pointer var2 = new Pointer(var0);
               if (!saved) {
                  if (com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.RECT.invokeInt(new Object[]{var2, com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.bounds}) == 0) {
                     throw new IllegalStateException("GetWindowRect failed");
                  }

                  savedStyle = com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.a(var2);
                  savedHandle = var0;
                  x = com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.bounds.getInt(0L);
                  y = com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.bounds.getInt(4L);
                  width = com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.bounds.getInt(8L) - x;
                  height = com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.bounds.getInt(12L) - y;
                  saved = true;
                  if (com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.CLIENT_RECT.invokeInt(new Object[]{var2, com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.bounds})
                     == 0) {
                     throw new IllegalStateException("GetClientRect failed");
                  }

                  clientWidth = com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.bounds.getInt(8L);
                  clientHeight = com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.bounds.getInt(12L);
               }

               Pointer var3 = com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.MONITOR.invokePointer(new Object[]{var2, 2});
               com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.monitorInfo.setInt(0L, 40);
               if (com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.INFO.invokeInt(new Object[]{var3, com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.monitorInfo}) == 0) {
                  throw new IllegalStateException("GetMonitorInfo failed");
               } else {
                  int var4 = com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.monitorInfo.getInt(4L);
                  int var5 = com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.monitorInfo.getInt(8L);
                  int var6 = com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.monitorInfo.getInt(12L) - var4;
                  int var7 = com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.monitorInfo.getInt(16L) - var5;
                  long var8 = savedStyle & -29818881L | 2147483648L;
                  com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.a(var2, var8);
                  com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.a(var2, var4, var5, var6, var7);
                  Display.processMessages();
                  return new DisplayMode(var6, var7);
               }
            }
         } catch (Throwable var10) {
            unavailable = true;
            LOG.warn("In-place borderless mode unavailable; using LWJGL fallback", var10);
            return null;
         }
      } else {
         return null;
      }
   }

   static DisplayMode b() {
      if (!saved) {
         return null;
      } else {
         try {
            long var0 = com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.a();
            if (var0 != savedHandle) {
               saved = false;
               return null;
            } else {
               Pointer var2 = new Pointer(var0);
               com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.a(var2, savedStyle);
               com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.a(var2, x, y, width, height);
               Display.processMessages();
               if (com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.CLIENT_RECT.invokeInt(new Object[]{var2, com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.bounds}) == 0) {
                  throw new IllegalStateException("GetClientRect failed");
               } else {
                  int var3 = com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.bounds.getInt(8L);
                  int var4 = com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.bounds.getInt(12L);
                  if (var3 != clientWidth || var4 != clientHeight) {
                     com.cheatbreaker.client.kazerui.input.BorderlessWindow.a.a(var2, x, y, width + clientWidth - var3, height + clientHeight - var4);
                     Display.processMessages();
                  }

                  saved = false;
                  return new DisplayMode(clientWidth, clientHeight);
               }
            }
         } catch (Throwable var5) {
            unavailable = true;
            LOG.warn("Could not restore borderless window bounds", var5);
            return null;
         }
      }
   }

   private BorderlessWindow() {
   }

   private static final class a {
      static final NativeLibrary USER = NativeLibrary.getInstance("user32");
      static final Function GET = USER.getFunction(Native.POINTER_SIZE == 8 ? "GetWindowLongPtrW" : "GetWindowLongW", 1);
      static final Function SET = USER.getFunction(Native.POINTER_SIZE == 8 ? "SetWindowLongPtrW" : "SetWindowLongW", 1);
      static final Function RECT = USER.getFunction("GetWindowRect", 1);
      static final Function CLIENT_RECT = USER.getFunction("GetClientRect", 1);
      static final Function POS = USER.getFunction("SetWindowPos", 1);
      static final Function MONITOR = USER.getFunction("MonitorFromWindow", 1);
      static final Function INFO = USER.getFunction("GetMonitorInfoW", 1);
      static final Memory bounds = new Memory(16L);
      static final Memory monitorInfo = new Memory(40L);
      static final Method implementation;
      static final Method hwnd;

      static long a() throws Exception {
         return ((Number)hwnd.invoke(implementation.invoke(null))).longValue();
      }

      static long a(Pointer var0) {
         Object[] var1 = new Object[]{var0, -16};
         return Native.POINTER_SIZE == 8 ? GET.invokeLong(var1) : GET.invokeInt(var1) & 4294967295L;
      }

      static void a(Pointer var0, long var1) {
         if (Native.POINTER_SIZE == 8) {
            SET.invokeLong(new Object[]{var0, -16, var1});
         } else {
            SET.invokeInt(new Object[]{var0, -16, (int)var1});
         }
      }

      static void a(Pointer var0, int var1, int var2, int var3, int var4) {
         if (POS.invokeInt(new Object[]{var0, Pointer.createConstant(-2), var1, var2, var3, var4, 1072}) == 0) {
            throw new IllegalStateException("SetWindowPos failed");
         }
      }

      static {
         try {
            implementation = Display.class.getDeclaredMethod("getImplementation");
            implementation.setAccessible(true);
            Class var0 = Class.forName("org.lwjgl.opengl.WindowsDisplay");
            hwnd = var0.getDeclaredMethod("getHwnd");
            hwnd.setAccessible(true);
         } catch (Exception var1) {
            throw new ExceptionInInitializerError(var1);
         }
      }
   }
}
