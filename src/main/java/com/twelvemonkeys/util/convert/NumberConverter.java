package com.twelvemonkeys.util.convert;

import com.twelvemonkeys.lang.BeanUtil;
import com.twelvemonkeys.lang.StringUtil;
import com.twelvemonkeys.util.LRUHashMap;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

public class NumberConverter implements PropertyConverter {
   private static final DecimalFormatSymbols SYMBOLS = new DecimalFormatSymbols(Locale.US);
   private static final NumberFormat sDefaultFormat = new DecimalFormat("#0.#", SYMBOLS);
   private static final Map<String, Format> sFormats = new LRUHashMap<>(50);

   @Override
   public Object toObject(String var1, Class var2, String var3) throws ConversionException {
      if (StringUtil.isEmpty(var1)) {
         return null;
      } else {
         try {
            if (var2.equals(BigInteger.class)) {
               return new BigInteger(var1);
            } else if (var2.equals(BigDecimal.class)) {
               return new BigDecimal(var1);
            } else {
               NumberFormat var4;
               if (var3 == null) {
                  var4 = sDefaultFormat;
               } else {
                  var4 = this.getNumberFormat(var3);
               }

               Number var5;
               synchronized (var4) {
                  var5 = var4.parse(var1);
               }

               if (var2 == int.class || var2 == Integer.class) {
                  return var5.intValue();
               } else if (var2 == long.class || var2 == Long.class) {
                  return var5.longValue();
               } else if (var2 == double.class || var2 == Double.class) {
                  return var5.doubleValue();
               } else if (var2 == float.class || var2 == Float.class) {
                  return var5.floatValue();
               } else if (var2 == byte.class || var2 == Byte.class) {
                  return var5.byteValue();
               } else {
                  return var2 != short.class && var2 != Short.class ? var5 : var5.shortValue();
               }
            }
         } catch (ParseException var9) {
            throw new ConversionException(var9);
         } catch (RuntimeException var10) {
            throw new ConversionException(var10);
         }
      }
   }

   @Override
   public String toString(Object var1, String var2) throws ConversionException {
      if (var1 == null) {
         return null;
      } else if (!(var1 instanceof Number)) {
         throw new TypeMismathException(var1.getClass());
      } else {
         try {
            if (StringUtil.isEmpty(var2)) {
               return sDefaultFormat.format(var1);
            } else {
               NumberFormat var3 = this.getNumberFormat(var2);
               synchronized (var3) {
                  return var3.format(var1);
               }
            }
         } catch (RuntimeException var7) {
            throw new ConversionException(var7);
         }
      }
   }

   private NumberFormat getNumberFormat(String var1) {
      return (NumberFormat)this.getFormat(DecimalFormat.class, var1, SYMBOLS);
   }

   protected final Format getFormat(Class var1, Object... var2) {
      synchronized (sFormats) {
         String var4 = var1.getName() + ":" + Arrays.toString(var2);
         Format var5 = sFormats.get(var4);
         if (var5 == null) {
            try {
               var5 = (Format) BeanUtil.createInstance(var1, var2);
            } catch (Exception var8) {
               var8.printStackTrace();
               return null;
            }

            sFormats.put(var4, var5);
         }

         return var5;
      }
   }
}
