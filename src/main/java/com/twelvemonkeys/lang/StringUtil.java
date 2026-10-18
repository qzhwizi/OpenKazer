package com.twelvemonkeys.lang;

import com.twelvemonkeys.util.StringTokenIterator;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.UnsupportedCharsetException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public final class StringUtil {
   public static final String DELIMITER_STRING = ", \t\n\r\f";

   private StringUtil() {
   }

   public static String decode(byte[] var0, int var1, int var2, String var3) {
      try {
         return new String(var0, var1, var2, var3);
      } catch (UnsupportedEncodingException var5) {
         throw new UnsupportedCharsetException(var3);
      }
   }

   public static String valueOf(Object var0) {
      return var0 != null ? var0.toString() : null;
   }

   public static String toUpperCase(String var0) {
      return var0 != null ? var0.toUpperCase() : null;
   }

   public static String toLowerCase(String var0) {
      return var0 != null ? var0.toLowerCase() : null;
   }

   public static boolean isEmpty(String var0) {
      return var0 == null || var0.trim().length() == 0;
   }

   public static boolean isEmpty(String[] var0) {
      if (var0 == null) {
         return true;
      } else {
         for (String var4 : var0) {
            if (!isEmpty(var4)) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean contains(String var0, String var1) {
      return var0 != null && var1 != null && var0.indexOf(var1) >= 0;
   }

   public static boolean containsIgnoreCase(String var0, String var1) {
      return indexOfIgnoreCase(var0, var1, 0) >= 0;
   }

   public static boolean contains(String var0, int var1) {
      return var0 != null && var0.indexOf(var1) >= 0;
   }

   public static boolean containsIgnoreCase(String var0, int var1) {
      return var0 != null && (var0.indexOf(Character.toLowerCase((char)var1)) >= 0 || var0.indexOf(Character.toUpperCase((char)var1)) >= 0);
   }

   public static int indexOfIgnoreCase(String var0, String var1) {
      return indexOfIgnoreCase(var0, var1, 0);
   }

   public static int indexOfIgnoreCase(String var0, String var1, int var2) {
      if (var0 != null && var1 != null) {
         if (var1.length() == 0) {
            return var2;
         } else if (var1.length() > var0.length()) {
            return -1;
         } else {
            char var3 = Character.toLowerCase(var1.charAt(0));
            char var4 = Character.toUpperCase(var1.charAt(0));
            int var5 = 0;
            int var6 = 0;

            for (int var8 = var2; var8 <= var0.length() - var1.length(); var8++) {
               var5 = var5 >= 0 && var5 <= var8 ? var0.indexOf(var3, var8) : var5;
               var6 = var6 >= 0 && var6 <= var8 ? var0.indexOf(var4, var8) : var6;
               if (var5 < 0) {
                  if (var6 < 0) {
                     return -1;
                  }

                  var8 = var6;
               } else if (var6 < 0) {
                  var8 = var5;
               } else {
                  var8 = var5 < var6 ? var5 : var6;
               }

               if (var1.length() == 1) {
                  return var8;
               }

               if (var8 > var0.length() - var1.length()) {
                  return -1;
               }

               if ((
                     var0.charAt(var8 + var1.length() - 1) == Character.toLowerCase(var1.charAt(var1.length() - 1))
                        || var0.charAt(var8 + var1.length() - 1) == Character.toUpperCase(var1.charAt(var1.length() - 1))
                  )
                  && (var1.length() <= 2 || var0.regionMatches(true, var8 + 1, var1, 1, var1.length() - 2))) {
                  return var8;
               }
            }

            return -1;
         }
      } else {
         return -1;
      }
   }

   public static int lastIndexOfIgnoreCase(String var0, String var1) {
      return lastIndexOfIgnoreCase(var0, var1, var0 != null ? var0.length() - 1 : -1);
   }

   public static int lastIndexOfIgnoreCase(String var0, String var1, int var2) {
      if (var0 == null || var1 == null) {
         return -1;
      } else if (var1.length() == 0) {
         return var2;
      } else if (var1.length() > var0.length()) {
         return -1;
      } else {
         char var3 = Character.toLowerCase(var1.charAt(0));
         char var4 = Character.toUpperCase(var1.charAt(0));
         int var5 = var2;
         int var6 = var2;

         for (int var8 = var2; var8 >= 0; var8--) {
            var5 = var5 >= 0 && var5 >= var8 ? var0.lastIndexOf(var3, var8) : var5;
            var6 = var6 >= 0 && var6 >= var8 ? var0.lastIndexOf(var4, var8) : var6;
            if (var5 < 0) {
               if (var6 < 0) {
                  return -1;
               }

               var8 = var6;
            } else if (var6 < 0) {
               var8 = var5;
            } else {
               var8 = var5 > var6 ? var5 : var6;
            }

            if (var1.length() == 1) {
               return var8;
            }

            if (var8 <= var0.length() - var1.length()
               && (
                  var0.charAt(var8 + var1.length() - 1) == Character.toLowerCase(var1.charAt(var1.length() - 1))
                     || var0.charAt(var8 + var1.length() - 1) == Character.toUpperCase(var1.charAt(var1.length() - 1))
               )
               && (var1.length() <= 2 || var0.regionMatches(true, var8 + 1, var1, 1, var1.length() - 2))) {
               return var8;
            }
         }

         return -1;
      }
   }

   public static int indexOfIgnoreCase(String var0, int var1) {
      return indexOfIgnoreCase(var0, var1, 0);
   }

   public static int indexOfIgnoreCase(String var0, int var1, int var2) {
      if (var0 == null) {
         return -1;
      } else {
         char var3 = Character.toLowerCase((char)var1);
         char var4 = Character.toUpperCase((char)var1);
         int var5 = var0.indexOf(var3, var2);
         int var6 = var0.indexOf(var4, var2);
         if (var5 < 0) {
            return var6;
         } else if (var6 < 0) {
            return var5;
         } else {
            return var5 < var6 ? var5 : var6;
         }
      }
   }

   public static int lastIndexOfIgnoreCase(String var0, int var1) {
      return lastIndexOfIgnoreCase(var0, var1, var0 != null ? var0.length() : -1);
   }

   public static int lastIndexOfIgnoreCase(String var0, int var1, int var2) {
      if (var0 == null) {
         return -1;
      } else {
         char var3 = Character.toLowerCase((char)var1);
         char var4 = Character.toUpperCase((char)var1);
         int var5 = var0.lastIndexOf(var3, var2);
         int var6 = var0.lastIndexOf(var4, var2);
         if (var5 < 0) {
            return var6;
         } else if (var6 < 0) {
            return var5;
         } else {
            return var5 > var6 ? var5 : var6;
         }
      }
   }

   public static String ltrim(String var0) {
      if (var0 != null && var0.length() != 0) {
         for (int var1 = 0; var1 < var0.length(); var1++) {
            if (!Character.isWhitespace(var0.charAt(var1))) {
               if (var1 == 0) {
                  return var0;
               }

               return var0.substring(var1);
            }
         }

         return "";
      } else {
         return var0;
      }
   }

   public static String rtrim(String var0) {
      if (var0 != null && var0.length() != 0) {
         for (int var1 = var0.length(); var1 > 0; var1--) {
            if (!Character.isWhitespace(var0.charAt(var1 - 1))) {
               if (var1 == var0.length()) {
                  return var0;
               }

               return var0.substring(0, var1);
            }
         }

         return "";
      } else {
         return var0;
      }
   }

   public static String replace(String var0, String var1, String var2) {
      if (var1.length() == 0) {
         return var0;
      } else {
         int var4 = 0;
         StringBuilder var5 = new StringBuilder();

         int var3;
         while ((var3 = var0.indexOf(var1, var4)) != -1) {
            var5.append(var0.substring(var4, var3));
            var5.append(var2);
            var4 = var3 + var1.length();
         }

         var5.append(var0.substring(var4));
         return var5.toString();
      }
   }

   public static String replaceIgnoreCase(String var0, String var1, String var2) {
      if (var1.length() == 0) {
         return var0;
      } else {
         int var4 = 0;
         StringBuilder var5 = new StringBuilder();

         int var3;
         while ((var3 = indexOfIgnoreCase(var0, var1, var4)) != -1) {
            var5.append(var0.substring(var4, var3));
            var5.append(var2);
            var4 = var3 + var1.length();
         }

         var5.append(var0.substring(var4));
         return var5.toString();
      }
   }

   public static String cut(String var0, int var1, String var2) {
      if (var0 == null) {
         return null;
      } else {
         if (var2 == null) {
            var2 = "";
         }

         int var3 = var0.length();
         if (var3 > var1) {
            var3 = var0.lastIndexOf(32, var1 - var2.length());
            return var0.substring(0, var3) + var2;
         } else {
            return var0;
         }
      }
   }

   public static String capitalize(String var0, int var1) {
      if (var1 < 0) {
         throw new IndexOutOfBoundsException("Negative index not allowed: " + var1);
      } else if (var0 == null || var0.length() <= var1) {
         return var0;
      } else if (Character.isUpperCase(var0.charAt(var1))) {
         return var0;
      } else {
         char[] var2 = var0.toCharArray();
         var2[var1] = Character.toUpperCase(var2[var1]);
         return new String(var2);
      }
   }

   public static String capitalize(String var0) {
      return capitalize(var0, 0);
   }

   @Deprecated
   static String formatNumber(long var0, int var2) throws IllegalArgumentException {
      StringBuilder var3 = new StringBuilder();
      if (var0 >= Math.pow(10.0, var2)) {
         throw new IllegalArgumentException("The number to format cannot contain more digits than the length argument specifies!");
      } else {
         for (int var4 = var2; var4 > 1 && var0 < Math.pow(10.0, var4 - 1); var4--) {
            var3.append('0');
         }

         var3.append(var0);
         return var3.toString();
      }
   }

   public static String pad(String var0, int var1, String var2, boolean var3) {
      if (var2 != null && var2.length() != 0) {
         if (var0.length() >= var1) {
            return var0;
         } else {
            int var4 = var1 - var0.length();
            StringBuilder var5 = new StringBuilder(var2);

            while (var5.length() < var4) {
               var5.append((CharSequence)var5);
            }

            if (var5.length() > var4) {
               var5.delete(var4, var5.length());
            }

            return var3 ? var5.append(var0).toString() : var5.insert(0, var0).toString();
         }
      } else {
         throw new IllegalArgumentException("Pad string: \"" + var2 + "\"");
      }
   }

   public static Date toDate(String var0) {
      return toDate(var0, DateFormat.getInstance());
   }

   public static Date toDate(String var0, String var1) {
      return toDate(var0, new SimpleDateFormat(var1));
   }

   public static Date toDate(String var0, DateFormat var1) {
      try {
         synchronized (var1) {
            return var1.parse(var0);
         }
      } catch (ParseException var5) {
         throw new IllegalArgumentException(var5.getMessage() + " at pos " + var5.getErrorOffset());
      }
   }

   public static Timestamp toTimestamp(String var0) {
      return Timestamp.valueOf(var0);
   }

   public static String[] toStringArray(String var0, String var1) {
      if (isEmpty(var0)) {
         return new String[0];
      } else {
         StringTokenIterator var2 = new StringTokenIterator(var0, var1);
         ArrayList var3 = new ArrayList();

         while (var2.hasMoreElements()) {
            var3.add(var2.nextToken());
         }

         return (String[]) var3.toArray(new String[var3.size()]);
      }
   }

   public static String[] toStringArray(String var0) {
      return toStringArray(var0, ", \t\n\r\f");
   }

   public static int[] toIntArray(String var0, String var1, int var2) {
      if (isEmpty(var0)) {
         return new int[0];
      } else {
         String[] var3 = toStringArray(var0, var1);
         int[] var4 = new int[var3.length];

         for (int var5 = 0; var5 < var4.length; var5++) {
            var4[var5] = Integer.parseInt(var3[var5], var2);
         }

         return var4;
      }
   }

   public static int[] toIntArray(String var0) {
      return toIntArray(var0, ", \t\n\r\f", 10);
   }

   public static int[] toIntArray(String var0, String var1) {
      return toIntArray(var0, var1, 10);
   }

   public static long[] toLongArray(String var0, String var1) {
      if (isEmpty(var0)) {
         return new long[0];
      } else {
         String[] var2 = toStringArray(var0, var1);
         long[] var3 = new long[var2.length];

         for (int var4 = 0; var4 < var3.length; var4++) {
            var3[var4] = Long.parseLong(var2[var4]);
         }

         return var3;
      }
   }

   public static long[] toLongArray(String var0) {
      return toLongArray(var0, ", \t\n\r\f");
   }

   public static double[] toDoubleArray(String var0, String var1) {
      if (isEmpty(var0)) {
         return new double[0];
      } else {
         String[] var2 = toStringArray(var0, var1);
         double[] var3 = new double[var2.length];

         for (int var4 = 0; var4 < var3.length; var4++) {
            var3[var4] = Double.valueOf(var2[var4]);
         }

         return var3;
      }
   }

   public static double[] toDoubleArray(String var0) {
      return toDoubleArray(var0, ", \t\n\r\f");
   }

   public static Color toColor(String var0) {
      if (var0 == null) {
         return null;
      } else if (var0.charAt(0) == '#') {
         int var11 = 0;
         int var12 = 0;
         int var13 = 0;
         int var4 = -1;
         if (var0.length() >= 7) {
            byte var5 = 1;
            if (var0.length() >= 9) {
               var4 = Integer.parseInt(var0.substring(var5, var5 + 2), 16);
               var5 += 2;
            }

            var11 = Integer.parseInt(var0.substring(var5, var5 + 2), 16);
            var12 = Integer.parseInt(var0.substring(var5 + 2, var5 + 4), 16);
            var13 = Integer.parseInt(var0.substring(var5 + 4, var5 + 6), 16);
         } else if (var0.length() >= 4) {
            int var14 = 1;
            if (var0.length() >= 5) {
               var4 = Integer.parseInt(var0.substring(var14++, var14), 16) * 16;
            }

            var11 = Integer.parseInt(var0.substring(var14++, var14), 16) * 16;
            var12 = Integer.parseInt(var0.substring(var14++, var14), 16) * 16;
            var13 = Integer.parseInt(var0.substring(var14++, var14), 16) * 16;
         }

         return var4 != -1 ? new Color(var11, var12, var13, var4) : new Color(var11, var12, var13);
      } else {
         try {
            Class<Color> var1 = Color.class;
            Field var2 = null;

            try {
               var2 = var1.getField(var0);
            } catch (Exception var6) {
            }

            if (var2 == null) {
               var2 = var1.getField(var0.toLowerCase());
            }

            int var3 = var2.getModifiers();
            if (Modifier.isPublic(var3) && Modifier.isStatic(var3)) {
               return (Color)var2.get(null);
            }
         } catch (NoSuchFieldException var7) {
            throw new IllegalArgumentException("No such color: " + var0);
         } catch (SecurityException var8) {
         } catch (IllegalAccessException var9) {
         } catch (IllegalArgumentException var10) {
         }

         return null;
      }
   }

   public static String toColorString(Color var0) {
      if (var0 == null) {
         return null;
      } else {
         StringBuilder var1 = new StringBuilder(Integer.toHexString(var0.getRGB()));

         for (int var2 = var1.length(); var2 < 8; var2++) {
            var1.insert(0, '0');
         }

         if (var1.charAt(0) == 'f' && var1.charAt(1) == 'f') {
            var1.delete(0, 2);
         }

         return var1.insert(0, '#').toString();
      }
   }

   public static boolean isNumber(String var0) {
      if (isEmpty(var0)) {
         return false;
      } else {
         char var1 = var0.charAt(0);
         if (var1 != '-' && !Character.isDigit(var1)) {
            return false;
         } else {
            for (int var2 = 1; var2 < var0.length(); var2++) {
               if (!Character.isDigit(var0.charAt(var2))) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   static String ensureIncludesAt(String var0, String var1, int var2) {
      StringBuilder var3 = new StringBuilder(var0);

      try {
         String var4 = var0.substring(var2, var2 + var1.length());
         if (!var4.equalsIgnoreCase(var1)) {
            var3.insert(var2, var1);
         }
      } catch (Exception var5) {
      }

      return var3.toString();
   }

   static String ensureExcludesAt(String var0, String var1, int var2) {
      StringBuilder var3 = new StringBuilder(var0);

      try {
         String var4 = var0.substring(var2 + 1, var2 + var1.length() + 1);
         if (!var4.equalsIgnoreCase(var1)) {
            var3.delete(var2, var2 + var1.length());
         }
      } catch (Exception var5) {
      }

      return var3.toString();
   }

   public static String substring(String var0, String var1, String var2, int var3) {
      int var4 = var3 < 0 ? 0 : var3;
      int var5 = var0.indexOf(var1, var4) + var1.length();
      if (var5 < 0) {
         return null;
      } else {
         int var6 = var0.indexOf(var2, var5);
         return var6 < 0 ? null : var0.substring(var5, var6);
      }
   }

   @Deprecated
   static String removeSubstring(String var0, char var1, char var2, int var3) {
      StringBuilder var4 = new StringBuilder();
      boolean var5 = false;
      char[] var6 = var0.toCharArray();

      for (char var10 : var6) {
         if (!var5) {
            if (var10 == var1) {
               var5 = true;
            } else {
               var4.append(var10);
            }
         } else if (var10 == var2) {
            var5 = false;
         }
      }

      return var4.toString();
   }

   static String removeSubstrings(String var0, char var1, char var2) {
      StringBuilder var3 = new StringBuilder();
      boolean var4 = false;
      char[] var5 = var0.toCharArray();

      for (char var9 : var5) {
         if (!var4) {
            if (var9 == var1) {
               var4 = true;
            } else {
               var3.append(var9);
            }
         } else if (var9 == var2) {
            var4 = false;
         }
      }

      return var3.toString();
   }

   public static String getFirstElement(String var0, String var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("delimiter == null");
      } else if (isEmpty(var0)) {
         return var0;
      } else {
         int var2 = var0.indexOf(var1);
         return var2 >= 0 ? var0.substring(0, var2) : var0;
      }
   }

   public static String getLastElement(String var0, String var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("delimiter == null");
      } else if (isEmpty(var0)) {
         return var0;
      } else {
         int var2 = var0.lastIndexOf(var1);
         return var2 >= 0 ? var0.substring(var2 + 1) : var0;
      }
   }

   public static String toCSVString(Object[] var0) {
      return toCSVString(var0, ", ");
   }

   public static String toCSVString(Object[] var0, String var1) {
      if (var0 == null) {
         return "";
      } else if (var1 == null) {
         throw new IllegalArgumentException("delimiter == null");
      } else {
         StringBuilder var2 = new StringBuilder();

         for (int var3 = 0; var3 < var0.length; var3++) {
            if (var3 > 0) {
               var2.append(var1);
            }

            var2.append(var0[var3]);
         }

         return var2.toString();
      }
   }

   public static String deepToString(Object var0) {
      return deepToString(var0, false, 1);
   }

   public static String deepToString(Object var0, boolean var1, int var2) {
      if (var0 == null) {
         return null;
      } else if (!var1 && !isIdentityToString(var0)) {
         return var0.toString();
      } else {
         StringBuilder var3 = new StringBuilder();
         if (var0.getClass().isArray()) {
            Class var4;
            for (var4 = var0.getClass(); var4.isArray(); var4 = var4.getComponentType()) {
               var3.append('[');
               var3.append(Array.getLength(var0));
               var3.append(']');
            }

            var3.insert(0, var4);
            var3.append(" {hashCode=");
            var3.append(Integer.toHexString(var0.hashCode()));
            var3.append("}");
         } else {
            if (isIdentityToString(var0)) {
               var3.append(" {");
            } else {
               var3.append(" {toString=");
               var3.append(var0.toString());
               var3.append(", ");
            }

            var3.append("hashCode=");
            var3.append(Integer.toHexString(var0.hashCode()));
            Method[] var16 = var0.getClass().getMethods();

            for (Method var8 : var16) {
               if (Modifier.isPublic(var8.getModifiers())) {
                  String var9 = var8.getName();
                  String var10 = null;
                  if (!var9.equals("getClass") && var9.length() > 3 && var9.startsWith("get") && Character.isUpperCase(var9.charAt(3))) {
                     var10 = var9.substring(3);
                  } else if (var9.length() > 2 && var9.startsWith("is") && Character.isUpperCase(var9.charAt(2))) {
                     var10 = var9.substring(2);
                  }

                  if (var10 != null) {
                     if (var10.length() > 1 && Character.isLowerCase(var10.charAt(1))) {
                        var10 = Character.toLowerCase(var10.charAt(0)) + var10.substring(1);
                     }

                     Class[] var11 = var8.getParameterTypes();
                     boolean var12 = var11 != null && var11.length > 0;
                     boolean var13 = void.class.equals(var8.getReturnType());
                     if (!var13 && !var12) {
                        try {
                           Object var14 = var8.invoke(var0);
                           var3.append(", ");
                           var3.append(var10);
                           var3.append('=');
                           if (var2 != 0 && var14 != null && isIdentityToString(var14)) {
                              var3.append(deepToString(var14, var1, var2 > 0 ? var2 - 1 : -1));
                           } else {
                              var3.append(var14);
                           }
                        } catch (Exception var15) {
                        }
                     }
                  }
               }
            }

            var3.append('}');
            var3.insert(0, var0.getClass().getName());
         }

         return var3.toString();
      }
   }

   private static boolean isIdentityToString(Object var0) {
      try {
         Method var1 = var0.getClass().getMethod("toString");
         if (var1.getDeclaringClass() == Object.class) {
            return true;
         }
      } catch (Exception var2) {
      }

      return false;
   }

   public static String identityToString(Object var0) {
      return var0 == null ? null : var0.getClass().getName() + '@' + Integer.toHexString(System.identityHashCode(var0));
   }

   public boolean matches(String var1, String var2) throws PatternSyntaxException {
      return Pattern.matches(var2, var1);
   }

   public String replaceFirst(String var1, String var2, String var3) {
      return Pattern.compile(var2).matcher(var1).replaceFirst(var3);
   }

   public String replaceAll(String var1, String var2, String var3) {
      return Pattern.compile(var2).matcher(var1).replaceAll(var3);
   }

   public String[] split(String var1, String var2, int var3) {
      return Pattern.compile(var2).split(var1, var3);
   }

   public String[] split(String var1, String var2) {
      return this.split(var1, var2, 0);
   }

   public static String camelToLisp(String var0) {
      if (var0 == null) {
         throw new IllegalArgumentException("string == null");
      } else if (var0.length() == 0) {
         return var0;
      } else {
         StringBuilder var1 = null;
         int var2 = 0;
         boolean var3 = false;
         boolean var4 = false;

         for (int var5 = 1; var5 < var0.length(); var5++) {
            char var6 = var0.charAt(var5);
            if (Character.isUpperCase(var6)) {
               if (var1 == null) {
                  var1 = new StringBuilder(var0.length() + 3);
               }

               if (var4) {
                  var4 = false;
                  var1.append(var0.substring(var2, var5));
                  if (var6 != '-') {
                     var1.append('-');
                  }

                  var2 = var5;
               } else {
                  char var7 = var0.charAt(var5 - 1);
                  if (var5 != var2 && !Character.isUpperCase(var7)) {
                     var1.append(var0.substring(var2, var5).toLowerCase());
                     if (var7 != '-') {
                        var1.append('-');
                     }

                     var1.append(Character.toLowerCase(var6));
                     var2 = var5 + 1;
                  } else {
                     var3 = true;
                  }
               }
            } else if (Character.isDigit(var6)) {
               if (var1 == null) {
                  var1 = new StringBuilder(var0.length() + 3);
               }

               if (var3) {
                  var3 = false;
                  var1.append(var0.substring(var2, var5).toLowerCase());
                  if (var6 != '-') {
                     var1.append('-');
                  }

                  var2 = var5;
               } else {
                  char var8 = var0.charAt(var5 - 1);
                  if (var5 != var2 && !Character.isDigit(var8)) {
                     var1.append(var0.substring(var2, var5).toLowerCase());
                     if (var8 != '-') {
                        var1.append('-');
                     }

                     var1.append(Character.toLowerCase(var6));
                     var2 = var5 + 1;
                  } else {
                     var4 = true;
                  }
               }
            } else if (var4) {
               var4 = false;
               var1.append(var0.substring(var2, var5));
               if (var6 != '-') {
                  var1.append('-');
               }

               var2 = var5;
            } else if (var3) {
               var3 = false;
               var1.append(var0.substring(var2, var5 - 1).toLowerCase());
               if (var6 != '-') {
                  var1.append('-');
               }

               var2 = var5 - 1;
            }
         }

         if (var1 != null) {
            var1.append(var0.substring(var2).toLowerCase());
            return var1.toString();
         } else {
            return Character.isUpperCase(var0.charAt(0)) ? var0.toLowerCase() : var0;
         }
      }
   }

   public static String lispToCamel(String var0) {
      return lispToCamel(var0, false);
   }

   public static String lispToCamel(String var0, boolean var1) {
      if (var0 == null) {
         throw new IllegalArgumentException("string == null");
      } else if (var0.length() == 0) {
         return var0;
      } else {
         StringBuilder var2 = null;
         int var3 = 0;

         for (int var4 = 0; var4 < var0.length(); var4++) {
            char var5 = var0.charAt(var4);
            if (var5 == '-') {
               if (var2 == null) {
                  var2 = new StringBuilder(var0.length() - 1);
               }

               if (var3 != 0 || var1) {
                  var2.append(Character.toUpperCase(var0.charAt(var3)));
                  var3++;
               }

               var2.append(var0.substring(var3, var4).toLowerCase());
               var3 = var4 + 1;
            }
         }

         if (var2 != null) {
            var2.append(Character.toUpperCase(var0.charAt(var3)));
            var2.append(var0.substring(var3 + 1).toLowerCase());
            return var2.toString();
         } else if (var1 && !Character.isUpperCase(var0.charAt(0))) {
            return capitalize(var0, 0);
         } else {
            return !var1 && Character.isUpperCase(var0.charAt(0)) ? Character.toLowerCase(var0.charAt(0)) + var0.substring(1) : var0;
         }
      }
   }

   public static String reverse(String var0) {
      char[] var1 = new char[var0.length()];
      var0.getChars(0, var1.length, var1, 0);

      for (int var2 = 0; var2 < var1.length / 2; var2++) {
         char var3 = var1[var2];
         var1[var2] = var1[var1.length - 1 - var2];
         var1[var1.length - 1 - var2] = var3;
      }

      return new String(var1);
   }
}
