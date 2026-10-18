package cn.kazer.client189.input;

import com.cheatbreaker.client.kazerui.input.WindowMode;
import java.lang.reflect.Method;
import net.minecraft.util.Util;
import net.minecraft.util.Util.EnumOS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

public final class WindowsFullscreenImeSupport {
   private static final Logger logger = LogManager.getLogger();
   private static boolean unavailable;
   private static boolean warned;

   private WindowsFullscreenImeSupport() {
   }

   public static void updateWindowLayer() {
      WindowsImeSupport.d();
      applyIfNeeded(shouldRemoveTopmost(Util.getOSType() == EnumOS.WINDOWS, Display.isCreated(), Display.isFullscreen() || WindowMode.active()));
   }

   public static void onClientTick() {
      applyIfNeeded(
         shouldReapplyOnTick(Util.getOSType() == EnumOS.WINDOWS, Display.isCreated(), Display.isFullscreen() || WindowMode.active(), Display.isActive())
      );
   }

   static boolean shouldRemoveTopmost(boolean var0, boolean var1, boolean var2) {
      return var0 && var1 && var2;
   }

   static boolean shouldReapplyOnTick(boolean var0, boolean var1, boolean var2, boolean var3) {
      return var0 && var1 && var2 && var3;
   }

   static boolean needsTweak(long var0, long var2) {
      return (var0 & 2147483648L) != 0L || (var2 & 8L) != 0L;
   }

   private static void applyIfNeeded(boolean var0) {
      if (!unavailable && var0) {
         try {
            if (WindowsFullscreenImeSupport.a.e()) {
               logger.info("Adjusted Windows fullscreen style for the IME candidate window");
            }
         } catch (Throwable var2) {
            unavailable = true;
            if (!warned) {
               warned = true;
               logger.warn("Could not expose the Windows IME candidate window in fullscreen", var2);
            }
         }
      }
   }

   static boolean hasLwjglWindowBridge() {
      return WindowsFullscreenImeSupport.a.d();
   }

   static long getWindowHandle() throws Exception {
      return WindowsFullscreenImeSupport.a.f();
   }

   private static final class a {
      private static final int SWP_NOSIZE = 1;
      private static final int SWP_NOMOVE = 2;
      private static final int SWP_NOACTIVATE = 16;
      private static final int SWP_FRAMECHANGED = 32;
      private static final int SWP_NOSENDCHANGING = 1024;
      private static final int GWL_STYLE = -16;
      private static final int GWL_EXSTYLE = -20;
      private static final long WS_POPUP = 2147483648L;
      private static final long WS_EX_TOPMOST = 8L;
      private static final long HWND_NOTOPMOST = -2L;
      private static final Class<?> WINDOWS_DISPLAY;
      private static final Method GET_IMPLEMENTATION;
      private static final Method GET_HWND;
      private static final Method GET_WINDOW_LONG_PTR;
      private static final Method SET_WINDOW_LONG_PTR;
      private static final Method SET_WINDOW_POS;

      private static boolean d() {
         return true;
      }

      private static boolean e() throws Exception {
         long var0 = f();
         if (var0 == 0L) {
            return false;
         } else {
            long var2 = (Long)GET_WINDOW_LONG_PTR.invoke(null, var0, -16);
            long var4 = (Long)GET_WINDOW_LONG_PTR.invoke(null, var0, -20);
            if (!WindowsFullscreenImeSupport.needsTweak(var2, var4)) {
               return false;
            } else {
               if ((var2 & 2147483648L) != 0L) {
                  SET_WINDOW_LONG_PTR.invoke(null, var0, -16, var2 & -2147483649L);
               }

               if (!(Boolean)SET_WINDOW_POS.invoke(null, var0, -2L, 0, 0, 0, 0, 1075L)) {
                  throw new IllegalStateException("LWJGL setWindowPos failed");
               } else {
                  return true;
               }
            }
         }
      }

      private static long f() throws Exception {
         Object var0 = GET_IMPLEMENTATION.invoke(null);
         return WINDOWS_DISPLAY.isInstance(var0) ? (Long)GET_HWND.invoke(var0) : 0L;
      }

      static {
         try {
            WINDOWS_DISPLAY = Class.forName("org.lwjgl.opengl.WindowsDisplay", false, Display.class.getClassLoader());
            GET_IMPLEMENTATION = Display.class.getDeclaredMethod("getImplementation");
            GET_HWND = WINDOWS_DISPLAY.getDeclaredMethod("getHwnd");
            GET_WINDOW_LONG_PTR = WINDOWS_DISPLAY.getDeclaredMethod("getWindowLongPtr", long.class, int.class);
            SET_WINDOW_LONG_PTR = WINDOWS_DISPLAY.getDeclaredMethod("setWindowLongPtr", long.class, int.class, long.class);
            SET_WINDOW_POS = WINDOWS_DISPLAY.getDeclaredMethod("setWindowPos", long.class, long.class, int.class, int.class, int.class, int.class, long.class);
            GET_IMPLEMENTATION.setAccessible(true);
            GET_HWND.setAccessible(true);
            GET_WINDOW_LONG_PTR.setAccessible(true);
            SET_WINDOW_LONG_PTR.setAccessible(true);
            SET_WINDOW_POS.setAccessible(true);
         } catch (Exception var1) {
            throw new IllegalStateException("Unsupported LWJGL Windows display bridge", var1);
         }
      }
   }
}
