package cn.kazer.client189.input;

import com.cheatbreaker.client.kazerui.input.WindowMode;
import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;
import net.minecraft.util.Util.EnumOS;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public final class FullscreenTransition {
   private static Field fullscreen;
   private static Field windowWidth;
   private static Field windowHeight;
   private static boolean resolved;
   private static boolean unavailable;

   private static boolean a() {
      if (resolved) {
         return !unavailable;
      } else {
         resolved = true;

         for (String var3 : new String[]{"fullscreen", "fullscreen"}) {
            try {
               fullscreen = Minecraft.class.getDeclaredField(var3);
               fullscreen.setAccessible(true);
               windowWidth = a("tempDisplayWidth", "tempDisplayWidth");
               windowHeight = a("tempDisplayHeight", "tempDisplayHeight");
               return true;
            } catch (NoSuchFieldException var5) {
            }
         }

         unavailable = true;
         return false;
      }
   }

   private static Field a(String... var0) {
      for (String var4 : var0) {
         try {
            Field var5 = Minecraft.class.getDeclaredField(var4);
            var5.setAccessible(true);
            return var5;
         } catch (NoSuchFieldException var6) {
         }
      }

      return null;
   }

   public static boolean toggle(Minecraft var0) {
      if (a() && Util.getOSType() != EnumOS.OSX) {
         try {
            a(var0, !var0.isFullScreen());
         } catch (Exception var2) {
            LogManager.getLogger().warn("Could not toggle fullscreen", var2);
         }

         return true;
      } else {
         return false;
      }
   }

   public static void applyCurrent() {
      Minecraft var0 = Minecraft.getMinecraft();
      if (var0.isFullScreen() && Display.isCreated() && a()) {
         try {
            a(var0, true);
         } catch (Exception var2) {
            LogManager.getLogger().warn("Could not apply fullscreen window mode", var2);
         }
      }
   }

   private static void a(Minecraft var0, boolean var1) throws Exception {
      DisplayMode var2 = WindowMode.a(
         var1,
         Display.getDesktopDisplayMode(),
         windowWidth == null ? var0.displayWidth : windowWidth.getInt(var0),
         windowHeight == null ? var0.displayHeight : windowHeight.getInt(var0)
      );
      fullscreen.setBoolean(var0, var1);
      var0.gameSettings.fullScreen = var1;
      var0.resize(var2.getWidth(), var2.getHeight());
      WindowsFullscreenImeSupport.updateWindowLayer();
      Display.setVSyncEnabled(var0.gameSettings.enableVsync);
      Display.update();
   }

   private FullscreenTransition() {
   }
}
