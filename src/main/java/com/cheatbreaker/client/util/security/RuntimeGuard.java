package com.cheatbreaker.client.util.security;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

public final class RuntimeGuard {
   private static final AtomicBoolean STARTED = new AtomicBoolean();

   private RuntimeGuard() {
   }

   public static String a(List<String> var0) {
      for (String var2 : var0) {
         String var3 = var2.toLowerCase(Locale.ROOT);
         if (var3.startsWith("-javaagent:")
            || var3.startsWith("-agentlib:")
            || var3.startsWith("-agentpath:")
            || var3.startsWith("-xrunjdwp")
            || var3.startsWith("-xdebug")
            || var3.equals("-xx:-disableattachmechanism")
            || var3.equals("-xx:+startattachlistener")
            || var3.startsWith("-dcom.sun.management.jmxremote")) {
            return "JVM instrumentation or remote attachment is not allowed";
         }
      }

      return null;
   }

   public static void a() {
      if (STARTED.compareAndSet(false, true)) {
         String var0 = a(ManagementFactory.getRuntimeMXBean().getInputArguments());
         if (var0 != null) {
            a(var0);
         }

         Thread var1 = new Thread(() -> {
            RuntimeGuard.a var0x = null;
            if (System.getProperty("os.name", "").startsWith("Windows")) {
               try {
                  var0x = new RuntimeGuard.a();
               } catch (Throwable var2) {
                  System.err.println("[Kazer Security] Native module inspection unavailable: " + var2.getClass().getSimpleName());
               }
            }

            while (true) {
               try {
                  if (var0x != null && var0x.a()) {
                     a("Native JVM instrumentation or debugger detected");
                  }

                  Thread.sleep(5000L);
               } catch (InterruptedException var3) {
                  Thread.currentThread().interrupt();
                  return;
               } catch (Throwable var4) {
                  System.err.println("[Kazer Security] Module inspection failed: " + var4.getClass().getSimpleName());
                  var0x = null;
               }
            }
         }, "Kazer Runtime Security");
         var1.setDaemon(true);
         var1.setPriority(1);
         var1.start();
      }
   }

   private static void a(String var0) {
      System.err.println("[Kazer Security] Client stopped: " + var0);
      System.err.flush();
      if (System.getProperty("os.name", "").startsWith("Windows")) {
         try {
            ((RuntimeGuard.ProcessExit)Native.loadLibrary("kernel32", RuntimeGuard.ProcessExit.class)).TerminateProcess(Pointer.createConstant(-1L), 86);
         } catch (Throwable var2) {
         }
      }

      Runtime.getRuntime().halt(86);
   }

   public interface ProcessExit extends StdCallLibrary {
      boolean TerminateProcess(Pointer var1, int var2);
   }

   public interface ProcessModules extends StdCallLibrary {
      boolean EnumProcessModules(Pointer var1, Pointer[] var2, int var3, int[] var4);

      int GetModuleBaseNameW(Pointer var1, Pointer var2, char[] var3, int var4);
   }

   private static final class a {
      private final RuntimeGuard.ProcessModules api = (RuntimeGuard.ProcessModules)Native.loadLibrary("psapi", RuntimeGuard.ProcessModules.class);
      private final Pointer process = Pointer.createConstant(-1L);
      private final Pointer[] modules = new Pointer[1024];
      private final int[] needed = new int[1];
      private final char[] name = new char[512];

      private a() {
      }

      boolean a() {
         if (!this.api.EnumProcessModules(this.process, this.modules, this.modules.length * Native.POINTER_SIZE, this.needed)) {
            return false;
         } else {
            int var1 = Math.min(this.modules.length, this.needed[0] / Native.POINTER_SIZE);

            for (int var2 = 0; var2 < var1; var2++) {
               int var3 = this.api.GetModuleBaseNameW(this.process, this.modules[var2], this.name, this.name.length);
               if (var3 > 0 && (a(this.name, var3, "instrument.dll") || a(this.name, var3, "jdwp.dll"))) {
                  return true;
               }
            }

            return false;
         }
      }

      private static boolean a(char[] var0, int var1, String var2) {
         if (var1 != var2.length()) {
            return false;
         } else {
            for (int var3 = 0; var3 < var1; var3++) {
               char var4 = var0[var3];
               if (var4 >= 'A' && var4 <= 'Z') {
                  var4 = (char)(var4 + ' ');
               }

               if (var4 != var2.charAt(var3)) {
                  return false;
               }
            }

            return true;
         }
      }
   }
}
