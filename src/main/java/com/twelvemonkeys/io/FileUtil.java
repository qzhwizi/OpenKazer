package com.twelvemonkeys.io;

import com.twelvemonkeys.lang.StringUtil;
import com.twelvemonkeys.lang.Validate;
import com.twelvemonkeys.util.Visitor;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.URL;
import java.text.NumberFormat;

public final class FileUtil {
   public static final int BUF_SIZE = 1024;
   private static String TEMP_DIR = null;
   private static final FileSystem FS = FileSystem.get();
   private static ThreadLocal<NumberFormat> sNumberFormat = new ThreadLocal<NumberFormat>() {
      protected NumberFormat initialValue() {
         NumberFormat var1 = NumberFormat.getNumberInstance();
         var1.setMaximumFractionDigits(0);
         return var1;
      }
   };

   public static void main(String[] var0) throws IOException {
      File var1;
      if (var0[0].startsWith("file:")) {
         var1 = toFile(new URL(var0[0]));
         System.out.println(var1);
      } else {
         var1 = new File(var0[0]);
         System.out.println(var1.toURL());
      }

      System.out.println("Free space: " + getFreeSpace(var1) + "/" + getTotalSpace(var1) + " bytes");
   }

   private FileUtil() {
   }

   public static boolean copy(String var0, String var1) throws IOException {
      return copy(new File(var0), new File(var1), false);
   }

   public static boolean copy(String var0, String var1, boolean var2) throws IOException {
      return copy(new File(var0), new File(var1), var2);
   }

   public static boolean copy(File var0, File var1) throws IOException {
      return copy(var0, var1, false);
   }

   public static boolean copy(File var0, File var1, boolean var2) throws IOException {
      if (var0.isDirectory()) {
         return copyDir(var0, var1, var2);
      } else {
         if (var1.isDirectory()) {
            var1 = new File(var1, var0.getName());
         }

         if (!var2 && var1.exists()) {
            return false;
         } else {
            FileInputStream var3 = null;
            FileOutputStream var4 = null;

            try {
               var3 = new FileInputStream(var0);
               var4 = new FileOutputStream(var1);
               copy(var3, var4);
            } finally {
               close(var3);
               close(var4);
            }

            return true;
         }
      }
   }

   public static void close(InputStream var0) {
      try {
         if (var0 != null) {
            var0.close();
         }
      } catch (IOException var2) {
      }
   }

   public static void close(OutputStream var0) {
      try {
         if (var0 != null) {
            var0.close();
         }
      } catch (IOException var2) {
      }
   }

   static void close(Reader var0) {
      try {
         if (var0 != null) {
            var0.close();
         }
      } catch (IOException var2) {
      }
   }

   static void close(Writer var0) {
      try {
         if (var0 != null) {
            var0.close();
         }
      } catch (IOException var2) {
      }
   }

   private static boolean copyDir(File var0, File var1, boolean var2) throws IOException {
      if (var1.exists() && !var1.isDirectory()) {
         throw new IOException("A directory may only be copied to another directory, not to a file");
      } else {
         var1.mkdirs();
         boolean var3 = true;
         File[] var4 = var0.listFiles();

         for (File var8 : var4) {
            if (!copy(var8, new File(var1, var8.getName()), var2)) {
               var3 = false;
            }
         }

         return var3;
      }
   }

   public static boolean copy(InputStream var0, OutputStream var1) throws IOException {
      Validate.notNull(var0, "from");
      Validate.notNull(var1, "to");
      BufferedInputStream var2 = new BufferedInputStream(var0, 2048);
      BufferedOutputStream var3 = new BufferedOutputStream(var1, 2048);
      byte[] var4 = new byte[1024];

      int var5;
      while ((var5 = var2.read(var4)) != -1) {
         var3.write(var4, 0, var5);
      }

      var3.flush();
      return true;
   }

   public static String getExtension(String var0) {
      return getExtension0(getFilename(var0));
   }

   public static String getExtension(File var0) {
      return getExtension0(var0.getName());
   }

   private static String getExtension0(String var0) {
      int var1 = var0.lastIndexOf(46);
      return var1 >= 0 ? var0.substring(var1 + 1) : null;
   }

   public static String getBasename(String var0) {
      return getBasename0(getFilename(var0));
   }

   public static String getBasename(File var0) {
      return getBasename0(var0.getName());
   }

   public static String getBasename0(String var0) {
      int var1 = var0.lastIndexOf(46);
      return var1 >= 0 ? var0.substring(0, var1) : var0;
   }

   public static String getDirectoryname(String var0) {
      return getDirectoryname(var0, File.separatorChar);
   }

   public static String getDirectoryname(String var0, char var1) {
      int var2 = var0.lastIndexOf(var1);
      return var2 < 0 ? "" : var0.substring(0, var2);
   }

   public static String getFilename(String var0) {
      return getFilename(var0, File.separatorChar);
   }

   public static String getFilename(String var0, char var1) {
      int var2 = var0.lastIndexOf(var1);
      return var2 < 0 ? var0 : var0.substring(var2 + 1);
   }

   public static boolean isEmpty(File var0) {
      return var0.isDirectory() ? var0.list().length == 0 : var0.length() == 0L;
   }

   public static File getTempDirFile() {
      return new File(getTempDir());
   }

   public static String getTempDir() {
      synchronized (FileUtil.class) {
         if (TEMP_DIR == null) {
            String var1 = System.getProperty("java.io.tmpdir");
            if (StringUtil.isEmpty(var1)) {
               if (new File("/temp").exists()) {
                  var1 = "/temp";
               } else {
                  var1 = "/tmp";
               }
            }

            TEMP_DIR = var1;
         }
      }

      return TEMP_DIR;
   }

   public static byte[] read(String var0) throws IOException {
      return read(new File(var0));
   }

   public static byte[] read(File var0) throws IOException {
      if (!var0.exists()) {
         throw new FileNotFoundException(var0.toString());
      } else {
         byte[] var1 = new byte[(int)var0.length()];
         BufferedInputStream var2 = null;

         try {
            var2 = new BufferedInputStream(new FileInputStream(var0), 2048);
            int var3 = 0;

            int var4;
            while ((var4 = var2.read(var1, var3, var2.available())) != -1 && var3 < var1.length) {
               var3 += var4;
            }
         } finally {
            close(var2);
         }

         return var1;
      }
   }

   public static byte[] read(InputStream var0) throws IOException {
      FastByteArrayOutputStream var1 = new FastByteArrayOutputStream(1024);
      copy(var0, var1);
      return var1.toByteArray();
   }

   public static boolean write(OutputStream var0, byte[] var1) throws IOException {
      var0.write(var1);
      return true;
   }

   public static boolean write(File var0, byte[] var1) throws IOException {
      boolean var2 = false;
      BufferedOutputStream var3 = null;

      try {
         var3 = new BufferedOutputStream(new FileOutputStream(var0));
         var2 = write(var3, var1);
      } finally {
         close(var3);
      }

      return var2;
   }

   public static boolean write(String var0, byte[] var1) throws IOException {
      return write(new File(var0), var1);
   }

   public static boolean delete(File var0, boolean var1) throws IOException {
      return var1 && var0.isDirectory() ? deleteDir(var0) : var0.exists() && var0.delete();
   }

   private static boolean deleteDir(File var0) throws IOException {
      class DeleteFilesVisitor implements Visitor<File> {
         private int failedCount = 0;
         private IOException exception = null;

         public void visit(File var1) {
            try {
               if (!FileUtil.delete(var1, true)) {
                  this.failedCount++;
               }
            } catch (IOException var3) {
               this.failedCount++;
               if (this.exception == null) {
                  this.exception = var3;
               }
            }
         }

         boolean succeeded() throws IOException {
            if (this.exception != null) {
               throw this.exception;
            } else {
               return this.failedCount == 0;
            }
         }
      }

      DeleteFilesVisitor var1 = new DeleteFilesVisitor();
      visitFiles(var0, null, var1);
      return var1.succeeded() && var0.delete();
   }

   public static boolean delete(String var0, boolean var1) throws IOException {
      return delete(new File(var0), var1);
   }

   public static boolean delete(File var0) throws IOException {
      return delete(var0, false);
   }

   public static boolean delete(String var0) throws IOException {
      return delete(new File(var0), false);
   }

   public static boolean rename(File var0, File var1, boolean var2) throws IOException {
      if (!var0.exists()) {
         throw new FileNotFoundException(var0.getAbsolutePath());
      } else {
         if (var0.isFile() && var1.isDirectory()) {
            var1 = new File(var1, var0.getName());
         }

         return (var2 || !var1.exists()) && var0.renameTo(var1);
      }
   }

   public static boolean rename(File var0, File var1) throws IOException {
      return rename(var0, var1, false);
   }

   public static boolean rename(File var0, String var1, boolean var2) throws IOException {
      return rename(var0, new File(var1), var2);
   }

   public static boolean rename(File var0, String var1) throws IOException {
      return rename(var0, new File(var1), false);
   }

   public static boolean rename(String var0, String var1, boolean var2) throws IOException {
      return rename(new File(var0), new File(var1), var2);
   }

   public static boolean rename(String var0, String var1) throws IOException {
      return rename(new File(var0), new File(var1), false);
   }

   public static File[] list(String var0) throws FileNotFoundException {
      return list(var0, null);
   }

   public static File[] list(String var0, String var1) throws FileNotFoundException {
      if (StringUtil.isEmpty(var0)) {
         return null;
      } else {
         File var2 = resolve(var0);
         if (!var2.isDirectory() || !var2.canRead()) {
            throw new FileNotFoundException("\"" + var0 + "\" is not a directory or is not readable.");
         } else if (StringUtil.isEmpty(var1)) {
            return var2.listFiles();
         } else {
            FilenameMaskFilter var3 = new FilenameMaskFilter(var1);
            return var2.listFiles(var3);
         }
      }
   }

   public static File toFile(URL var0) {
      if (var0 == null) {
         throw new NullPointerException("URL == null");
      } else if (!"file".equals(var0.getProtocol())) {
         throw new IllegalArgumentException("URL scheme is not \"file\"");
      } else if (var0.getAuthority() != null) {
         throw new IllegalArgumentException("URL has an authority component");
      } else if (var0.getRef() != null) {
         throw new IllegalArgumentException("URI has a fragment component");
      } else if (var0.getQuery() != null) {
         throw new IllegalArgumentException("URL has a query component");
      } else {
         String var1 = var0.getPath();
         if (!var1.startsWith("/")) {
            throw new IllegalArgumentException("URI is not hierarchical");
         } else if (var1.isEmpty()) {
            throw new IllegalArgumentException("URI path component is empty");
         } else {
            if (File.separatorChar != '/') {
               var1 = var1.replace('/', File.separatorChar);
            }

            return resolve(var1);
         }
      }
   }

   public static File resolve(String var0) {
      return Win32File.wrap(new File(var0));
   }

   public static File resolve(File var0) {
      return Win32File.wrap(var0);
   }

   public static File resolve(File var0, String var1) {
      return Win32File.wrap(new File(var0, var1));
   }

   public static File[] resolve(File[] var0) {
      return Win32File.wrap(var0);
   }

   public static long getFreeSpace(File var0) {
      File var1 = var0 != null ? var0 : new File(".");
      Long var2 = getSpace16("getFreeSpace", var1);
      return var2 != null ? var2 : FS.getFreeSpace(var1);
   }

   public static long getUsableSpace(File var0) {
      File var1 = var0 != null ? var0 : new File(".");
      Long var2 = getSpace16("getUsableSpace", var1);
      return var2 != null ? var2 : getTotalSpace(var1);
   }

   public static long getTotalSpace(File var0) {
      File var1 = var0 != null ? var0 : new File(".");
      Long var2 = getSpace16("getTotalSpace", var1);
      return var2 != null ? var2 : FS.getTotalSpace(var1);
   }

   private static Long getSpace16(String var0, File var1) {
      try {
         Method var2 = File.class.getMethod(var0);
         return (Long)var2.invoke(var1);
      } catch (NoSuchMethodException var4) {
      } catch (IllegalAccessException var5) {
      } catch (InvocationTargetException var6) {
         Throwable var3 = var6.getTargetException();
         if (var3 instanceof SecurityException) {
            throw (SecurityException)var3;
         }

         throw new UndeclaredThrowableException(var3);
      }

      return null;
   }

   public static String toHumanReadableSize(long var0) {
      if (var0 < 1024L) {
         return var0 + " Bytes";
      } else if (var0 < 1048576L) {
         return getSizeFormat().format(var0 / 1024.0) + " KB";
      } else if (var0 < 1073741824L) {
         return getSizeFormat().format(var0 / 1048576.0) + " MB";
      } else if (var0 < 1099511627776L) {
         return getSizeFormat().format(var0 / 1.0737418E9F) + " GB";
      } else {
         return var0 < 1125899906842624L ? getSizeFormat().format(var0 / 1.0995116E12F) + " TB" : getSizeFormat().format(var0 / 1.1258999E15F) + " PB";
      }
   }

   private static NumberFormat getSizeFormat() {
      return sNumberFormat.get();
   }

   public static void visitFiles(File var0, final FileFilter var1, final Visitor<File> var2) {
      Validate.notNull(var0, "directory");
      Validate.notNull(var2, "visitor");
      var0.listFiles(new FileFilter() {
         @Override
         public boolean accept(File var1x) {
            if (var1 == null || var1.accept(var1x)) {
               var2.visit(var1x);
            }

            return false;
         }
      });
   }
}
