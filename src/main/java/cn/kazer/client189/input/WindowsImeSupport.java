package cn.kazer.client189.input;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.Util;
import net.minecraft.util.Util.EnumOS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

public final class WindowsImeSupport implements cn.kazer.client189.input.ImeCandidatePositionCache.a {
   private static final Logger logger = LogManager.getLogger();
   private static final boolean WINDOWS = Util.getOSType() == EnumOS.WINDOWS;
   private static boolean warned;
   private static boolean unavailable;
   private final WindowsImeSupport.b imeApi;

   public WindowsImeSupport(WindowsImeSupport.b var1) {
      this.imeApi = var1;
   }

   @Override
   public boolean a(cn.kazer.client189.input.ImeCandidatePlacement var1) {
      return this.imeApi.a(var1.a(), var1.d(), var1.e(), var1.f(), var1.g(), var1.h());
   }

   private static boolean g() {
      return !unavailable && WINDOWS;
   }

   public static void a() {
      if (g()) {
         WindowsImeSupport.a.FOCUS.a();
      }
   }

   public static void b() {
      if (g() && Display.isCreated()) {
         WindowsImeSupport.a.FOCUS.a();
         c();
      }
   }

   public static void c() {
      if (g() && Display.isCreated()) {
         try {
            if (WindowsImeSupport.a.FOCUS.b()) {
               WindowsImeSupport.a.TRACKER.a();
            }
         } catch (Throwable var1) {
            a(var1);
         }
      }
   }

   public static void d() {
      if (g() && Display.isCreated()) {
         try {
            WindowsImeSupport.a.API.b();
            WindowsImeSupport.a.FOCUS.c();
            WindowsImeSupport.a.TRACKER.a();
         } catch (Throwable var1) {
            a(var1);
         }
      }
   }

   public static void a(int var0, int var1, int var2) {
      a(Minecraft.getMinecraft().currentScreen, var0, var1, var2);
   }

   public static void a(Object var0, int var1, int var2, int var3) {
      if (g()) {
         Minecraft var4 = Minecraft.getMinecraft();
         GuiScreen var5 = var4 == null ? null : var4.currentScreen;
         if (Display.isCreated()
            && Display.isActive()
            && var5 != null
            && var0 != null
            && var5.width > 0
            && var5.height > 0
            && var4.displayWidth > 0
            && var4.displayHeight > 0) {
            try {
               if (WindowsImeSupport.a.FOCUS.a(var0)) {
                  WindowsImeSupport.a.TRACKER.a();
               }

               WindowsImeSupport.a.TRACKER.a(var1, var2, var3, var5.width, var5.height, var4.displayWidth, var4.displayHeight, var5 instanceof GuiChat);
            } catch (Throwable var7) {
               a(var7);
            }
         }
      }
   }

   private static void a(Throwable var0) {
      try {
         WindowsImeSupport.a.API.a(true);
      } catch (Throwable var2) {
      }

      unavailable = true;
      if (!warned) {
         warned = true;
         logger.warn("Could not update the Windows IME input window", var0);
      }
   }

   public static boolean e() {
      return WindowsFullscreenImeSupport.hasLwjglWindowBridge();
   }

   public static boolean f() {
      try {
         NativeLibrary var0 = NativeLibrary.getInstance("imm32");

         for (String var4 : new String[]{
            "ImmGetContext",
            "ImmSetCompositionWindow",
            "ImmSetCandidateWindow",
            "ImmReleaseContext",
            "ImmAssociateContext",
            "ImmAssociateContextEx",
            "ImmNotifyIME"
         }) {
            var0.getFunction(var4);
         }

         return true;
      } catch (Throwable var5) {
         return false;
      }
   }

   private interface Imm32 extends StdCallLibrary {
      WindowsImeSupport.Imm32 INSTANCE = (WindowsImeSupport.Imm32)Native.loadLibrary("imm32", WindowsImeSupport.Imm32.class);

      Pointer ImmGetContext(Pointer var1);

      int ImmSetCompositionWindow(Pointer var1, Pointer var2);

      int ImmSetCandidateWindow(Pointer var1, Pointer var2);

      int ImmReleaseContext(Pointer var1, Pointer var2);

      Pointer ImmAssociateContext(Pointer var1, Pointer var2);

      int ImmAssociateContextEx(Pointer var1, Pointer var2, int var3);

      int ImmNotifyIME(Pointer var1, int var2, int var3, int var4);
   }

   private interface User32 extends StdCallLibrary {
      WindowsImeSupport.User32 INSTANCE = (WindowsImeSupport.User32)Native.loadLibrary("user32", WindowsImeSupport.User32.class);

      int CreateCaret(Pointer var1, Pointer var2, int var3, int var4);

      int DestroyCaret();

      int SetCaretPos(int var1, int var2);
   }

   private static final class a {
      static final WindowsImeSupport.c API = new WindowsImeSupport.c();
      static final cn.kazer.client189.input.ImeCandidatePositionCache TRACKER = new cn.kazer.client189.input.ImeCandidatePositionCache(new WindowsImeSupport(API));
      static final cn.kazer.client189.input.ImeFocusTracker FOCUS = new cn.kazer.client189.input.ImeFocusTracker(API);
   }

   public interface b {
      boolean a(int var1, int var2, int var3, int var4, int var5, int var6);
   }

   private static final class c implements WindowsImeSupport.b, ImeFocusTracker.a {
      private final Memory composition = new Memory(28L);
      private final Memory candidate = new Memory(32L);
      private Pointer window;
      private Pointer detached;
      private boolean disabled;
      private boolean caret;

      private c() {
      }

      private Pointer c() {
         try {
            if (this.window == null) {
               long var1 = WindowsFullscreenImeSupport.getWindowHandle();
               if (var1 != 0L) {
                  this.window = new Pointer(var1);
               }
            }

            return this.window;
         } catch (Exception var3) {
            throw new IllegalStateException(var3);
         }
      }

      void b() {
         try {
            long var1 = WindowsFullscreenImeSupport.getWindowHandle();
            if (this.window != null && Pointer.nativeValue(this.window) == var1 && this.disabled) {
               this.a(true);
            }
         } catch (Exception var3) {
            throw new IllegalStateException(var3);
         }

         this.d();
         this.window = null;
         this.detached = null;
         this.disabled = false;
      }

      @Override
      public void a(boolean var1) {
         Pointer var2 = this.c();
         if (var2 != null) {
            if (var1) {
               if (this.disabled) {
                  if (this.detached != null) {
                     WindowsImeSupport.Imm32.INSTANCE.ImmAssociateContext(var2, this.detached);
                  } else {
                     WindowsImeSupport.Imm32.INSTANCE.ImmAssociateContextEx(var2, null, 16);
                  }

                  this.disabled = false;
                  this.detached = null;
               }

               if (!this.caret) {
                  this.caret = WindowsImeSupport.User32.INSTANCE.CreateCaret(var2, null, 1, 1) != 0;
               }
            } else if (!this.disabled) {
               this.a();
               this.d();
               this.detached = WindowsImeSupport.Imm32.INSTANCE.ImmAssociateContext(var2, null);
               this.disabled = true;
            }
         }
      }

      private void d() {
         if (this.caret) {
            WindowsImeSupport.User32.INSTANCE.DestroyCaret();
            this.caret = false;
         }
      }

      @Override
      public void a() {
         Pointer var1 = this.c();
         if (var1 != null) {
            Pointer var2 = WindowsImeSupport.Imm32.INSTANCE.ImmGetContext(var1);
            if (var2 != null) {
               try {
                  WindowsImeSupport.Imm32.INSTANCE.ImmNotifyIME(var2, 21, 4, 0);
               } finally {
                  WindowsImeSupport.Imm32.INSTANCE.ImmReleaseContext(var1, var2);
               }
            }
         }
      }

      @Override
      public boolean a(int var1, int var2, int var3, int var4, int var5, int var6) {
         Pointer var7 = this.c();
         if (var7 == null) {
            return false;
         } else {
            Pointer var8 = WindowsImeSupport.Imm32.INSTANCE.ImmGetContext(var7);
            if (var8 == null) {
               return false;
            } else {
               boolean var11;
               try {
                  if (this.caret) {
                     WindowsImeSupport.User32.INSTANCE.SetCaretPos(var1, var2);
                  }

                  this.composition.clear();
                  this.composition.setInt(0L, 2);
                  this.composition.setInt(4L, var1);
                  this.composition.setInt(8L, var2);
                  this.candidate.clear();
                  this.candidate.setInt(4L, 128);
                  this.candidate.setInt(8L, var1);
                  this.candidate.setInt(12L, var2);
                  this.candidate.setInt(16L, var3);
                  this.candidate.setInt(20L, var4);
                  this.candidate.setInt(24L, var5);
                  this.candidate.setInt(28L, var6);
                  boolean var9 = WindowsImeSupport.Imm32.INSTANCE.ImmSetCompositionWindow(var8, this.composition) != 0;
                  boolean var10 = WindowsImeSupport.Imm32.INSTANCE.ImmSetCandidateWindow(var8, this.candidate) != 0;
                  var11 = var9 && var10;
               } finally {
                  WindowsImeSupport.Imm32.INSTANCE.ImmReleaseContext(var7, var8);
               }

               return var11;
            }
         }
      }
   }
}
