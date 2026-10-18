package com.twelvemonkeys.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

abstract class FileSystem {
   abstract long getFreeSpace(File var1);

   abstract long getTotalSpace(File var1);

   abstract String getName();

   static BufferedReader exec(String[] var0) throws IOException {
      Process var1 = Runtime.getRuntime().exec(var0);
      return new BufferedReader(new InputStreamReader(var1.getInputStream()));
   }

   static FileSystem get() {
      String var0 = System.getProperty("os.name");
      var0 = var0.toLowerCase();
      if (var0.contains("windows")) {
         return new Win32FileSystem();
      } else {
         return (FileSystem)(!var0.contains("linux")
               && !var0.contains("sun os")
               && !var0.contains("sunos")
               && !var0.contains("solaris")
               && !var0.contains("mpe/ix")
               && !var0.contains("hp-ux")
               && !var0.contains("aix")
               && !var0.contains("freebsd")
               && !var0.contains("irix")
               && !var0.contains("digital unix")
               && !var0.contains("unix")
               && !var0.contains("mac os x")
            ? new FileSystem.UnknownFileSystem(var0)
            : new UnixFileSystem());
      }
   }

   private static class UnknownFileSystem extends FileSystem {
      private final String osName;

      UnknownFileSystem(String var1) {
         this.osName = var1;
      }

      @Override
      long getFreeSpace(File var1) {
         return 0L;
      }

      @Override
      long getTotalSpace(File var1) {
         return 0L;
      }

      @Override
      String getName() {
         return "Unknown (" + this.osName + ")";
      }
   }
}
