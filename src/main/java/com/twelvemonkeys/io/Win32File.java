package com.twelvemonkeys.io;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;

final class Win32File extends File {
   private static final boolean IS_WINDOWS = isWindows();

   private static boolean isWindows() {
      try {
         String var0 = System.getProperty("os.name");
         return var0.toLowerCase().indexOf("windows") >= 0;
      } catch (Throwable var1) {
         return false;
      }
   }

   private Win32File(File var1) {
      super(var1.getPath());
   }

   public static void main(String[] var0) {
      int var1 = 0;

      boolean var2;
      for (var2 = false; var0.length > var1 + 1 && var0[var1].charAt(0) == '-' && var0[var1].length() > 1; var1++) {
         if (var0[var1].charAt(1) != 'R' && !var0[var1].equals("--recursive")) {
            System.err.println("Unknown option: " + var0[var1]);
         } else {
            var2 = true;
         }
      }

      File var3 = wrap(new File(var0[var1]));
      System.out.println("file: " + var3);
      System.out.println("file.getClass(): " + var3.getClass());
      listFiles(var3, 0, var2);
   }

   private static void listFiles(File var0, int var1, boolean var2) {
      if (var0.isDirectory()) {
         File[] var3 = var0.listFiles();

         for (int var4 = 0; var4 < var1; var4++) {
            System.out.print(" ");
         }

         System.out.println("Contents of " + var0 + ": ");

         for (File var7 : var3) {
            for (int var8 = 0; var8 < var1; var8++) {
               System.out.print(" ");
            }

            System.out.println("  " + var7);
            if (var2) {
               listFiles(var7, var1 + 1, var1 < 4);
            }
         }
      }
   }

   public static File wrap(File var0) {
      if (var0 == null) {
         return null;
      } else if (!IS_WINDOWS) {
         return var0;
      } else if (!(var0 instanceof Win32File) && !(var0 instanceof Win32Lnk)) {
         if (var0.exists() && var0.getName().endsWith(".lnk")) {
            try {
               return new Win32Lnk(var0);
            } catch (IOException var2) {
               var2.printStackTrace();
            }
         }

         return new Win32File(var0);
      } else {
         return var0;
      }
   }

   public static File[] wrap(File[] var0) {
      if (IS_WINDOWS) {
         for (int var1 = 0; var0 != null && var1 < var0.length; var1++) {
            var0[var1] = wrap(var0[var1]);
         }
      }

      return var0;
   }

   @Override
   public File getAbsoluteFile() {
      return wrap(super.getAbsoluteFile());
   }

   @Override
   public File getCanonicalFile() throws IOException {
      return wrap(super.getCanonicalFile());
   }

   @Override
   public File getParentFile() {
      return wrap(super.getParentFile());
   }

   @Override
   public File[] listFiles() {
      return wrap(super.listFiles());
   }

   @Override
   public File[] listFiles(FileFilter var1) {
      return wrap(super.listFiles(var1));
   }

   @Override
   public File[] listFiles(FilenameFilter var1) {
      return wrap(super.listFiles(var1));
   }
}
