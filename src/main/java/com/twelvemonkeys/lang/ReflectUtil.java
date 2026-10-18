package com.twelvemonkeys.lang;

public final class ReflectUtil {
   private ReflectUtil() {
   }

   public static Class unwrapType(Class var0) {
      if (var0 == Boolean.class) {
         return boolean.class;
      } else if (var0 == Byte.class) {
         return byte.class;
      } else if (var0 == Character.class) {
         return char.class;
      } else if (var0 == Double.class) {
         return double.class;
      } else if (var0 == Float.class) {
         return float.class;
      } else if (var0 == Integer.class) {
         return int.class;
      } else if (var0 == Long.class) {
         return long.class;
      } else if (var0 == Short.class) {
         return short.class;
      } else {
         throw new IllegalArgumentException("Not a primitive wrapper: " + var0);
      }
   }

   public static Class wrapType(Class var0) {
      if (var0 == boolean.class) {
         return Boolean.class;
      } else if (var0 == byte.class) {
         return Byte.class;
      } else if (var0 == char.class) {
         return Character.class;
      } else if (var0 == double.class) {
         return Double.class;
      } else if (var0 == float.class) {
         return Float.class;
      } else if (var0 == int.class) {
         return Integer.class;
      } else if (var0 == long.class) {
         return Long.class;
      } else if (var0 == short.class) {
         return Short.class;
      } else {
         throw new IllegalArgumentException("Not a primitive type: " + var0);
      }
   }

   public static boolean isPrimitiveWrapper(Class var0) {
      return var0 == Boolean.class
         || var0 == Byte.class
         || var0 == Character.class
         || var0 == Double.class
         || var0 == Float.class
         || var0 == Integer.class
         || var0 == Long.class
         || var0 == Short.class;
   }
}
