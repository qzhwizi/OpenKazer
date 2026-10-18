package com.twelvemonkeys.lang;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public final class Validate {
   private static final String UNSPECIFIED_PARAM_NAME = "method parameter";

   private Validate() {
   }

   public static <T> T notNull(T var0) {
      return notNull((T)var0, null);
   }

   public static <T> T notNull(T var0, String var1) {
      if (var0 == null) {
         throw new IllegalArgumentException(String.format("%s may not be null", var1 == null ? "method parameter" : var1));
      } else {
         return (T)var0;
      }
   }

   public static <T extends CharSequence> T notEmpty(T var0) {
      return notEmpty((T)var0, null);
   }

   public static <T extends CharSequence> T notEmpty(T var0, String var1) {
      if (var0 != null && var0.length() != 0 && !isOnlyWhiteSpace(var0)) {
         return (T)var0;
      } else {
         throw new IllegalArgumentException(String.format("%s may not be blank", var1 == null ? "method parameter" : var1));
      }
   }

   private static <T extends CharSequence> boolean isOnlyWhiteSpace(T var0) {
      for (int var1 = 0; var1 < var0.length(); var1++) {
         if (!Character.isWhitespace(var0.charAt(var1))) {
            return false;
         }
      }

      return true;
   }

   public static <T> T[] notEmpty(T[] var0) {
      return (T[])notEmpty(var0, null);
   }

   public static <T> T[] notEmpty(T[] var0, String var1) {
      if (var0 != null && var0.length != 0) {
         return (T[])var0;
      } else {
         throw new IllegalArgumentException(String.format("%s may not be empty", var1 == null ? "method parameter" : var1));
      }
   }

   public static <T> Collection<T> notEmpty(Collection<T> var0) {
      return notEmpty(var0, null);
   }

   public static <T> Collection<T> notEmpty(Collection<T> var0, String var1) {
      if (var0 != null && !var0.isEmpty()) {
         return var0;
      } else {
         throw new IllegalArgumentException(String.format("%s may not be empty", var1 == null ? "method parameter" : var1));
      }
   }

   public static <K, V> Map<K, V> notEmpty(Map<K, V> var0) {
      return notEmpty(var0, null);
   }

   public static <K, V> Map<K, V> notEmpty(Map<K, V> var0, String var1) {
      if (var0 != null && !var0.isEmpty()) {
         return var0;
      } else {
         throw new IllegalArgumentException(String.format("%s may not be empty", var1 == null ? "method parameter" : var1));
      }
   }

   public static <T> T[] noNullElements(T[] var0) {
      return (T[])noNullElements(var0, null);
   }

   public static <T> T[] noNullElements(T[] var0, String var1) {
      noNullElements(var0 == null ? null : Arrays.asList(var0), var1);
      return (T[])var0;
   }

   public static <T> Collection<T> noNullElements(Collection<T> var0) {
      return noNullElements(var0, null);
   }

   public static <T> Collection<T> noNullElements(Collection<T> var0, String var1) {
      notNull(var0, var1);

      for (Object var3 : var0) {
         if (var3 == null) {
            throw new IllegalArgumentException(String.format("%s may not contain null elements", var1 == null ? "method parameter" : var1));
         }
      }

      return var0;
   }

   public static <K, V> Map<K, V> noNullValues(Map<K, V> var0) {
      return noNullValues(var0, null);
   }

   public static <K, V> Map<K, V> noNullValues(Map<K, V> var0, String var1) {
      notNull(var0, var1);

      for (Object var3 : var0.values()) {
         if (var3 == null) {
            throw new IllegalArgumentException(String.format("%s may not contain null values", var1 == null ? "method parameter" : var1));
         }
      }

      return var0;
   }

   public static <K, V> Map<K, V> noNullKeys(Map<K, V> var0) {
      return noNullKeys(var0, null);
   }

   public static <K, V> Map<K, V> noNullKeys(Map<K, V> var0, String var1) {
      notNull(var0, var1);

      for (Object var3 : var0.keySet()) {
         if (var3 == null) {
            throw new IllegalArgumentException(String.format("%s may not contain null keys", var1 == null ? "method parameter" : var1));
         }
      }

      return var0;
   }

   public static boolean isTrue(boolean var0, String var1) {
      return isTrue(var0, var0, var1);
   }

   public static <T> T isTrue(boolean var0, T var1, String var2) {
      if (!var0) {
         throw new IllegalArgumentException(String.format(var2 == null ? "expression may not be %s" : var2, var1));
      } else {
         return (T)var1;
      }
   }
}
