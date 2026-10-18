package com.twelvemonkeys.lang;

import java.util.Properties;

public final class Platform {
   final Platform.OperatingSystem os;
   final String version;
   final Platform.Architecture architecture;
   private static final Platform INSTANCE = new Platform();

   private Platform() {
      this(System.getProperties());
   }

   Platform(Properties var1) {
      this.os = normalizeOperatingSystem(var1.getProperty("os.name"));
      this.version = var1.getProperty("os.version");
      this.architecture = normalizeArchitecture(this.os, var1.getProperty("os.arch"));
   }

   static Platform.OperatingSystem normalizeOperatingSystem(String var0) {
      if (var0 == null) {
         throw new IllegalStateException("System property \"os.name\" == null");
      } else {
         String var1 = var0.toLowerCase();
         if (var1.startsWith("windows")) {
            return Platform.OperatingSystem.Windows;
         } else if (var1.startsWith("linux")) {
            return Platform.OperatingSystem.Linux;
         } else if (var1.startsWith("mac os") || var1.startsWith("darwin")) {
            return Platform.OperatingSystem.MacOS;
         } else {
            return !var1.startsWith("solaris") && !var1.startsWith("sunos") ? Platform.OperatingSystem.Unknown : Platform.OperatingSystem.Solaris;
         }
      }
   }

   static Platform.Architecture normalizeArchitecture(Platform.OperatingSystem var0, String var1) {
      if (var1 == null) {
         throw new IllegalStateException("System property \"os.arch\" == null");
      } else {
         String var2 = var1.toLowerCase();
         if (var0 != Platform.OperatingSystem.Windows || !var2.startsWith("x86") && !var2.startsWith("i386")) {
            if (var0 == Platform.OperatingSystem.Linux) {
               if (var2.startsWith("x86") || var2.startsWith("i386")) {
                  return Platform.Architecture.I386;
               }

               if (var2.startsWith("i686")) {
                  return Platform.Architecture.I686;
               }

               if (var2.startsWith("power") || var2.startsWith("ppc")) {
                  return Platform.Architecture.PPC;
               }
            } else if (var0 == Platform.OperatingSystem.MacOS) {
               if (var2.startsWith("power") || var2.startsWith("ppc")) {
                  return Platform.Architecture.PPC;
               }

               if (var2.startsWith("x86")) {
                  return Platform.Architecture.X86;
               }

               if (var2.startsWith("i386")) {
                  return Platform.Architecture.X86;
               }
            } else if (var0 == Platform.OperatingSystem.Solaris) {
               if (var2.startsWith("sparc")) {
                  return Platform.Architecture.SPARC;
               }

               if (var2.startsWith("x86")) {
                  return Platform.Architecture.X86;
               }
            }

            return Platform.Architecture.Unknown;
         } else {
            return Platform.Architecture.X86;
         }
      }
   }

   public static Platform get() {
      return INSTANCE;
   }

   public Platform.OperatingSystem getOS() {
      return this.os;
   }

   public String getVersion() {
      return this.version;
   }

   public Platform.Architecture getArchitecture() {
      return this.architecture;
   }

   public static Platform.OperatingSystem os() {
      return INSTANCE.os;
   }

   public static String version() {
      return INSTANCE.version;
   }

   public static Platform.Architecture arch() {
      return INSTANCE.architecture;
   }

   public static enum Architecture {
      X86("x86"),
      I386("i386"),
      I686("i686"),
      PPC("ppc"),
      SPARC("sparc"),
      Unknown(System.getProperty("os.arch"));

      final String name;

      private Architecture(String var3) {
         this.name = var3;
      }

      @Override
      public String toString() {
         return this.name;
      }
   }

   public static enum OperatingSystem {
      Windows("Windows", "win"),
      Linux("Linux", "lnx"),
      Solaris("Solaris", "sun"),
      MacOS("Mac OS", "osx"),
      Unknown(System.getProperty("os.name"), null);

      final String id;
      final String name;

      private OperatingSystem(String var3, String var4) {
         this.name = var3;
         this.id = var4 != null ? var4 : var3.toLowerCase();
      }

      public String getName() {
         return this.name;
      }

      public String id() {
         return this.id;
      }

      @Override
      public String toString() {
         return String.format("%s (%s)", this.id, this.name);
      }
   }
}
