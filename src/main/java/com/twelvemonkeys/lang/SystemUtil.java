package com.twelvemonkeys.lang;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class SystemUtil {
   public static String XML_PROPERTIES = ".xml";
   public static String STD_PROPERTIES = ".properties";

   private SystemUtil() {
   }

   private static InputStream getResourceAsStream(ClassLoader var0, String var1, boolean var2) {
      Object var3;
      if (!var2) {
         var3 = var0.getResourceAsStream(var1);
         if (var3 != null && var1.endsWith(XML_PROPERTIES)) {
            var3 = new SystemUtil.XMLPropertiesInputStream((InputStream)var3);
         }
      } else {
         var3 = var0.getResourceAsStream(var1 + STD_PROPERTIES);
         if (var3 == null) {
            var3 = var0.getResourceAsStream(var1 + XML_PROPERTIES);
            if (var3 != null) {
               var3 = new SystemUtil.XMLPropertiesInputStream((InputStream)var3);
            }
         }
      }

      return (InputStream)var3;
   }

   private static InputStream getFileAsStream(String var0, boolean var1) {
      Object var2 = null;

      try {
         if (!var1) {
            File var3 = new File(var0);
            if (var3.exists()) {
               var2 = new FileInputStream(var3);
               if (var0.endsWith(XML_PROPERTIES)) {
                  var2 = new SystemUtil.XMLPropertiesInputStream((InputStream)var2);
               }
            }
         } else {
            File var6 = new File(var0 + STD_PROPERTIES);
            if (var6.exists()) {
               var2 = new FileInputStream(var6);
            } else {
               var6 = new File(var0 + XML_PROPERTIES);
               if (var6.exists()) {
                  var2 = new SystemUtil.XMLPropertiesInputStream(new FileInputStream(var6));
               }
            }
         }
      } catch (FileNotFoundException var5) {
      }

      return (InputStream)var2;
   }

   public static Properties loadProperties(Class var0, String var1) throws IOException {
      String var2 = !StringUtil.isEmpty(var1) ? var1 : var0.getName().replace('.', '/');
      boolean var3 = var1 == null || var1.indexOf(46) < 0;
      InputStream var4;
      if ((var0 == null || (var4 = getResourceAsStream(var0.getClassLoader(), var2, var3)) == null)
         && (var4 = getResourceAsStream(ClassLoader.getSystemClassLoader(), var2, var3)) == null
         && (var4 = getFileAsStream(var2, var3)) == null) {
         if (var3) {
            throw new FileNotFoundException(var2 + ".properties or " + var2 + ".xml");
         } else {
            throw new FileNotFoundException(var2);
         }
      } else {
         Properties var5;
         try {
            var5 = loadProperties(var4);
         } finally {
            try {
               var4.close();
            } catch (IOException var12) {
            }
         }

         return var5;
      }
   }

   public static Properties loadProperties(Class var0) throws IOException {
      return loadProperties(var0, null);
   }

   public static Properties loadProperties(String var0) throws IOException {
      return loadProperties(null, var0);
   }

   private static Properties loadProperties(InputStream var0) throws IOException {
      if (var0 == null) {
         throw new IllegalArgumentException("InputStream == null!");
      } else {
         Properties var1 = new Properties();
         var1.load(var0);
         return var1;
      }
   }

   public static Object clone(Cloneable var0) throws CloneNotSupportedException {
      if (var0 == null) {
         return null;
      } else if (var0 instanceof Object[]) {
         return ((Object[])var0).clone();
      } else if (var0.getClass().isArray()) {
         int var8 = Array.getLength(var0);
         Object var10 = Array.newInstance(var0.getClass().getComponentType(), var8);
         System.arraycopy(var0, 0, var10, 0, var8);
         return var10;
      } else {
         try {
            Method var1 = null;
            Class var9 = var0.getClass();

            while (true) {
               try {
                  var1 = var9.getDeclaredMethod("clone");
                  break;
               } catch (NoSuchMethodException var4) {
                  if ((var9 = var9.getSuperclass()) == null) {
                     break;
                  }
               }
            }

            if (var1 == null) {
               throw new CloneNotSupportedException(var0.getClass().getName());
            } else {
               if (!var1.isAccessible()) {
                  var1.setAccessible(true);
               }

               return var1.invoke(var0);
            }
         } catch (SecurityException var5) {
            CloneNotSupportedException var2 = new CloneNotSupportedException(var0.getClass().getName());
            var2.initCause(var5);
            throw var2;
         } catch (IllegalAccessException var6) {
            throw new CloneNotSupportedException(var0.getClass().getName());
         } catch (InvocationTargetException var7) {
            if (var7.getTargetException() instanceof CloneNotSupportedException) {
               throw (CloneNotSupportedException)var7.getTargetException();
            } else if (var7.getTargetException() instanceof RuntimeException) {
               throw (RuntimeException)var7.getTargetException();
            } else if (var7.getTargetException() instanceof Error) {
               throw (Error)var7.getTargetException();
            } else {
               throw new CloneNotSupportedException(var0.getClass().getName());
            }
         }
      }
   }

   public static void main(String[] var0) throws CloneNotSupportedException {
      System.out.println("clone: " + ((String[])var0.clone()).length + " (" + var0.length + ")");
      System.out.println("copy: " + ((String[])clone(var0)).length + " (" + var0.length + ")");
      int[] var1 = new int[]{1, 2, 3};
      int[] var2 = (int[])clone(var1);
      System.out.println("Copies: " + var2.length + " (" + var1.length + ")");
      int[][] var3 = new int[][]{{1}, {2, 3}, {4, 5, 6}};
      int[][] var4 = (int[][])clone(var3);
      System.out.println("Copies: " + var4.length + " (" + var3.length + ")");
      System.out.println("Copies0: " + var4[0].length + " (" + var3[0].length + ")");
      System.out.println("Copies1: " + var4[1].length + " (" + var3[1].length + ")");
      System.out.println("Copies2: " + var4[2].length + " (" + var3[2].length + ")");
      HashMap var5 = new HashMap();

      for (String var9 : var0) {
         var5.put(var9, var9);
      }

      Map var10 = (Map)clone(var5);
      System.out.println("Map : " + var5);
      System.out.println("Copy: " + var10);
      Cloneable var11 = new Cloneable() {};
      Cloneable var12 = (Cloneable)clone(var11);
      System.out.println("cloneable: " + var11);
      System.out.println("clone: " + var12);
      AccessController.doPrivileged(new PrivilegedAction<Void>() {
         public Void run() {
            return null;
         }
      }, AccessController.getContext());
   }

   public static boolean isClassAvailable(String var0) {
      return isClassAvailable(var0, (ClassLoader)null);
   }

   public static boolean isClassAvailable(String var0, Class var1) {
      ClassLoader var2 = var1 != null ? var1.getClassLoader() : null;
      return isClassAvailable(var0, var2);
   }

   private static boolean isClassAvailable(String var0, ClassLoader var1) {
      try {
         getClass(var0, true, var1);
         return true;
      } catch (SecurityException var3) {
      } catch (ClassNotFoundException var4) {
      } catch (LinkageError var5) {
      }

      return false;
   }

   public static boolean isFieldAvailable(String var0, String var1) {
      return isFieldAvailable(var0, var1, (ClassLoader)null);
   }

   public static boolean isFieldAvailable(String var0, String var1, Class var2) {
      ClassLoader var3 = var2 != null ? var2.getClassLoader() : null;
      return isFieldAvailable(var0, var1, var3);
   }

   private static boolean isFieldAvailable(String var0, String var1, ClassLoader var2) {
      try {
         Class var3 = getClass(var0, false, var2);
         Field var4 = var3.getField(var1);
         if (var4 != null) {
            return true;
         }
      } catch (ClassNotFoundException var5) {
      } catch (LinkageError var6) {
      } catch (NoSuchFieldException var7) {
      }

      return false;
   }

   public static boolean isMethodAvailable(String var0, String var1) {
      return isMethodAvailable(var0, var1, null, (ClassLoader)null);
   }

   public static boolean isMethodAvailable(String var0, String var1, Class[] var2) {
      return isMethodAvailable(var0, var1, var2, (ClassLoader)null);
   }

   public static boolean isMethodAvailable(String var0, String var1, Class[] var2, Class var3) {
      ClassLoader var4 = var3 != null ? var3.getClassLoader() : null;
      return isMethodAvailable(var0, var1, var2, var4);
   }

   private static boolean isMethodAvailable(String var0, String var1, Class[] var2, ClassLoader var3) {
      try {
         Class var4 = getClass(var0, false, var3);
         Method var5 = var4.getMethod(var1, var2);
         if (var5 != null) {
            return true;
         }
      } catch (ClassNotFoundException var6) {
      } catch (LinkageError var7) {
      } catch (NoSuchMethodException var8) {
      }

      return false;
   }

   private static Class getClass(String var0, boolean var1, ClassLoader var2) throws ClassNotFoundException {
      ClassLoader var3 = var2 != null ? var2 : Thread.currentThread().getContextClassLoader();
      return Class.forName(var0, var1, var3);
   }

   private static class XMLPropertiesInputStream extends FilterInputStream {
      public XMLPropertiesInputStream(InputStream var1) {
         super(var1);
      }
   }
}
