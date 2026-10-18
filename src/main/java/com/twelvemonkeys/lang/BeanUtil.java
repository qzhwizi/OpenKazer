package com.twelvemonkeys.lang;

import com.twelvemonkeys.util.convert.ConversionException;
import com.twelvemonkeys.util.convert.Converter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

public final class BeanUtil {
   private BeanUtil() {
   }

   public static Object getPropertyValue(Object var0, String var1) {
      if (var0 != null && var1 != null && var1.length() >= 1) {
         Class var2 = var0.getClass();
         Object var3 = var0;
         int var5 = 0;

         for (int var6 = var5; var5 < var1.length() && var5 >= 0; var2 = var3.getClass()) {
            var6 = var1.indexOf(46, var6 + 1);
            String var4;
            if (var6 > 0) {
               var4 = var1.substring(var5, var6);
               var5 = var6 + 1;
            } else {
               var4 = var1.substring(var5);
               var5 = -1;
            }

            Object[] var7 = null;
            Class[] var8 = new Class[0];
            int var9;
            if ((var9 = var4.indexOf(91)) > 0) {
               if (!var4.endsWith("]")) {
                  return null;
               }

               String var10 = var4.substring(var9 + 1, var4.length() - 1);
               var4 = var4.substring(0, var9);
               var7 = new Object[1];
               var8 = new Class[1];
               if (StringUtil.isNumber(var10)) {
                  try {
                     var7[0] = Integer.valueOf(var10);
                     var8[0] = int.class;
                  } catch (NumberFormatException var13) {
                  }
               } else {
                  var7[0] = var10.toLowerCase();
                  var8[0] = String.class;
               }
            }

            String var11 = "get" + StringUtil.capitalize(var4);

            Method var18;
            try {
               var18 = var2.getMethod(var11, var8);
            } catch (NoSuchMethodException var17) {
               System.err.print("No method named \"" + var11 + "()\"");
               if (var8.length > 0 && var8[0] != null) {
                  System.err.print(" with the parameter " + var8[0].getName());
               }

               System.err.println(" in class " + var2.getName() + "!");
               return null;
            }

            if (var18 == null) {
               return null;
            }

            try {
               var3 = var18.invoke(var3, var7);
            } catch (InvocationTargetException var14) {
               System.err.println("property=" + var1 + " & result=" + var3 + " & param=" + Arrays.toString(var7));
               var14.getTargetException().printStackTrace();
               var14.printStackTrace();
               return null;
            } catch (IllegalAccessException var15) {
               var15.printStackTrace();
               return null;
            } catch (NullPointerException var16) {
               System.err.println(var2.getName() + "." + var18.getName() + "(" + (var8.length > 0 && var8[0] != null ? var8[0].getName() : "") + ")");
               var16.printStackTrace();
               return null;
            }

            if (var3 == null) {
               return null;
            }
         }

         return var3;
      } else {
         return null;
      }
   }

   public static void setPropertyValue(Object var0, String var1, Object var2) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
      Class var3 = var2 != null ? var2.getClass() : Object.class;
      Object var4 = var0;
      String var5 = var1;
      int var6 = var1.indexOf(46);
      if (var6 >= 0) {
         var4 = getPropertyValue(var0, var1.substring(0, var6));
         var5 = var1.substring(var6 + 1);
      }

      Object[] var7 = new Object[]{var2};
      Method var8 = getMethodMayModifyParams(var4, "set" + StringUtil.capitalize(var5), new Class[]{var3}, var7);
      var8.invoke(var4, var7);
   }

   private static Method getMethodMayModifyParams(Object var0, String var1, Class[] var2, Object[] var3) throws NoSuchMethodException {
      Method var4 = null;
      Class var5 = var2[0];

      try {
         var4 = var0.getClass().getMethod(var1, var2);
      } catch (NoSuchMethodException var17) {
         if (ReflectUtil.isPrimitiveWrapper(var5)) {
            var2[0] = ReflectUtil.unwrapType(var5);
         }

         try {
            var4 = var0.getClass().getMethod(var1, var2);
         } catch (Throwable var14) {
         }

         if (var4 == null) {
            while ((var5 = var5.getSuperclass()) != null) {
               var2[0] = var5;

               try {
                  var4 = var0.getClass().getMethod(var1, var2);
                  break;
               } catch (Throwable var16) {
               }
            }
         }

         label43:
         if (var4 == null) {
            Method[] var7 = var0.getClass().getMethods();
            Method[] var8 = var7;
            int var9 = var7.length;
            int var10 = 0;

            Method var11;
            while (true) {
               if (var10 >= var9) {
                  break label43;
               }

               var11 = var8[var10];
               if (Modifier.isPublic(var11.getModifiers())
                  && var11.getName().equals(var1)
                  && var11.getReturnType() == void.class
                  && var11.getParameterTypes().length == 1) {
                  Class var12 = var11.getParameterTypes()[0];

                  try {
                     var3[0] = convertValueToType(var3[0], var12);
                     break;
                  } catch (Throwable var15) {
                  }
               }

               var10++;
            }

            var4 = var11;
         }

         if (var4 == null) {
            throw var17;
         }
      }

      return var4;
   }

   private static Object convertValueToType(Object var0, Class<?> var1) throws ConversionException {
      if (var1.isPrimitive()) {
         if (var1 == boolean.class && var0 instanceof Boolean) {
            return var0;
         }

         if (var1 == byte.class && var0 instanceof Byte) {
            return var0;
         }

         if (var1 == char.class && var0 instanceof Character) {
            return var0;
         }

         if (var1 == double.class && var0 instanceof Double) {
            return var0;
         }

         if (var1 == float.class && var0 instanceof Float) {
            return var0;
         }

         if (var1 == int.class && var0 instanceof Integer) {
            return var0;
         }

         if (var1 == long.class && var0 instanceof Long) {
            return var0;
         }

         if (var1 == short.class && var0 instanceof Short) {
            return var0;
         }
      }

      if (var0 instanceof String) {
         Converter var3 = Converter.getInstance();
         return var3.toObject((String)var0, var1);
      } else if (var1 == String.class) {
         Converter var2 = Converter.getInstance();
         return var2.toString(var0);
      } else {
         throw new ConversionException("Cannot convert " + var0.getClass().getName() + " to " + var1.getName());
      }
   }

   public static <T> T createInstance(Class<T> var0, Object var1) throws InvocationTargetException {
      return createInstance(var0, var1);
   }

   public static <T> T createInstance(Class<T> var0, Object... var1) throws InvocationTargetException {
      try {
         Class[] var3 = null;
         if (var1 != null && var1.length > 0) {
            var3 = new Class[var1.length];

            for (int var4 = 0; var4 < var1.length; var4++) {
               var3[var4] = var1[var4].getClass();
            }
         }

         Constructor var10 = var0.getConstructor(var3);
         return (T)var10.newInstance(var1);
      } catch (NoSuchMethodException var5) {
         return null;
      } catch (IllegalAccessException var6) {
         return null;
      } catch (IllegalArgumentException var7) {
         return null;
      } catch (InstantiationException var8) {
         return null;
      } catch (ExceptionInInitializerError var9) {
         return null;
      }
   }

   public static Object invokeStaticMethod(Class<?> var0, String var1, Object var2) throws InvocationTargetException {
      return invokeStaticMethod(var0, var1, var2);
   }

   public static Object invokeStaticMethod(Class<?> var0, String var1, Object... var2) throws InvocationTargetException {
      Object var3 = null;

      try {
         Class[] var4 = new Class[var2.length];

         for (int var5 = 0; var5 < var2.length; var5++) {
            var4[var5] = var2[var5].getClass();
         }

         Method var9 = var0.getMethod(var1, var4);
         if (Modifier.isPublic(var9.getModifiers()) && Modifier.isStatic(var9.getModifiers())) {
            var3 = var9.invoke(null, var2);
         }

         return var3;
      } catch (NoSuchMethodException var6) {
         return null;
      } catch (IllegalAccessException var7) {
         return null;
      } catch (IllegalArgumentException var8) {
         return null;
      }
   }

   public static void configure(Object var0, Map<String, ?> var1) throws InvocationTargetException {
      configure(var0, var1, false);
   }

   public static void configure(Object var0, Map<String, ?> var1, boolean var2) throws InvocationTargetException {
      for (Entry var4 : var1.entrySet()) {
         try {
            String var5 = StringUtil.valueOf(var4.getKey());

            try {
               setPropertyValue(var0, var5, var4.getValue());
            } catch (NoSuchMethodException var7) {
               if (var2 && var5.indexOf(45) > 0) {
                  setPropertyValue(var0, StringUtil.lispToCamel(var5, false), var4.getValue());
               }
            }
         } catch (NoSuchMethodException var8) {
         } catch (IllegalAccessException var9) {
         }
      }
   }
}
