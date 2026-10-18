package com.twelvemonkeys.util.convert;

import com.twelvemonkeys.lang.BeanUtil;
import com.twelvemonkeys.lang.StringUtil;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;

public final class DefaultConverter implements PropertyConverter {
   @Override
   public Object toObject(String var1, Class var2, String var3) throws ConversionException {
      if (var1 == null) {
         return null;
      } else if (var2 == null) {
         throw new MissingTypeException();
      } else if (var2.isArray()) {
         return this.toArray(var1, var2, var3);
      } else {
         Class var4 = this.unBoxType(var2);

         try {
            Object var5 = BeanUtil.createInstance(var4, var1);
            if (var5 == null) {
               var5 = BeanUtil.invokeStaticMethod(var4, "valueOf", var1);
               if (var5 == null) {
                  throw new ConversionException(
                     String.format("Could not convert String to %1$s: No constructor %1$s(String) or static %1$s.valueOf(String) method found!", var4.getName())
                  );
               }
            }

            return var5;
         } catch (InvocationTargetException var6) {
            throw new ConversionException(var6.getTargetException());
         } catch (ConversionException var7) {
            throw var7;
         } catch (RuntimeException var8) {
            throw new ConversionException(var8);
         }
      }
   }

   private Object toArray(String var1, Class var2, String var3) {
      String[] var4 = StringUtil.toStringArray(var1, var3 != null ? var3 : ", \t\n\r\f");
      Class var5 = var2.getComponentType();
      if (var5 == String.class) {
         return var4;
      } else {
         Object var6 = Array.newInstance(var5, var4.length);

         try {
            for (int var7 = 0; var7 < var4.length; var7++) {
               Array.set(var6, var7, Converter.getInstance().toObject(var4[var7], var5));
            }

            return var6;
         } catch (ConversionException var8) {
            if (var3 != null) {
               throw new ConversionException(String.format("%s for string \"%s\" with format \"%s\"", var8.getMessage(), var1, var3), var8);
            } else {
               throw new ConversionException(String.format("%s for string \"%s\"", var8.getMessage(), var1), var8);
            }
         }
      }
   }

   @Override
   public String toString(Object var1, String var2) throws ConversionException {
      try {
         return var1 == null ? null : (var1.getClass().isArray() ? this.arrayToString(this.toObjectArray(var1), var2) : var1.toString());
      } catch (RuntimeException var4) {
         throw new ConversionException(var4);
      }
   }

   private String arrayToString(Object[] var1, String var2) {
      return var2 == null ? StringUtil.toCSVString(var1) : StringUtil.toCSVString(var1, var2);
   }

   private Object[] toObjectArray(Object var1) {
      Class var3 = var1.getClass().getComponentType();
      Object var2;
      if (var3.isPrimitive()) {
         if (int.class == var3) {
            var2 = new Integer[Array.getLength(var1)];

            for (int var4 = 0; var4 < ((Object[])var2).length; var4++) {
               Array.set(var2, var4, Array.get(var1, var4));
            }
         } else if (short.class == var3) {
            var2 = new Short[Array.getLength(var1)];

            for (int var5 = 0; var5 < ((Object[])var2).length; var5++) {
               Array.set(var2, var5, Array.get(var1, var5));
            }
         } else if (long.class == var3) {
            var2 = new Long[Array.getLength(var1)];

            for (int var6 = 0; var6 < ((Object[])var2).length; var6++) {
               Array.set(var2, var6, Array.get(var1, var6));
            }
         } else if (float.class == var3) {
            var2 = new Float[Array.getLength(var1)];

            for (int var7 = 0; var7 < ((Object[])var2).length; var7++) {
               Array.set(var2, var7, Array.get(var1, var7));
            }
         } else if (double.class == var3) {
            var2 = new Double[Array.getLength(var1)];

            for (int var8 = 0; var8 < ((Object[])var2).length; var8++) {
               Array.set(var2, var8, Array.get(var1, var8));
            }
         } else if (boolean.class == var3) {
            var2 = new Boolean[Array.getLength(var1)];

            for (int var9 = 0; var9 < ((Object[])var2).length; var9++) {
               Array.set(var2, var9, Array.get(var1, var9));
            }
         } else if (byte.class == var3) {
            var2 = new Byte[Array.getLength(var1)];

            for (int var10 = 0; var10 < ((Object[])var2).length; var10++) {
               Array.set(var2, var10, Array.get(var1, var10));
            }
         } else {
            if (char.class != var3) {
               throw new IllegalArgumentException("Unknown type " + var3);
            }

            var2 = new Character[Array.getLength(var1)];

            for (int var11 = 0; var11 < ((Object[])var2).length; var11++) {
               Array.set(var2, var11, Array.get(var1, var11));
            }
         }
      } else {
         var2 = (Object[])var1;
      }

      return (Object[])var2;
   }

   private Class<?> unBoxType(Class<?> var1) {
      if (var1.isPrimitive()) {
         if (var1 == boolean.class) {
            return Boolean.class;
         } else if (var1 == byte.class) {
            return Byte.class;
         } else if (var1 == char.class) {
            return Character.class;
         } else if (var1 == short.class) {
            return Short.class;
         } else if (var1 == int.class) {
            return Integer.class;
         } else if (var1 == float.class) {
            return Float.class;
         } else if (var1 == long.class) {
            return Long.class;
         } else if (var1 == double.class) {
            return Double.class;
         } else {
            throw new IllegalArgumentException("Unknown type: " + var1);
         }
      } else {
         return var1;
      }
   }
}
