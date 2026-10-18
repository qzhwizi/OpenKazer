package com.twelvemonkeys.util.convert;

import com.twelvemonkeys.lang.BeanUtil;
import com.twelvemonkeys.lang.StringUtil;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter extends NumberConverter {
   @Override
   public Object toObject(String var1, Class var2, String var3) throws ConversionException {
      if (StringUtil.isEmpty(var1)) {
         return null;
      } else {
         try {
            DateFormat var4;
            if (var3 == null) {
               var4 = DateFormat.getDateTimeInstance();
            } else {
               var4 = this.getDateFormat(var3);
            }

            Date var5 = StringUtil.toDate(var1, var4);
            if (var2 != Date.class) {
               try {
                  var5 = (Date) BeanUtil.createInstance(var2, new Long(var5.getTime()));
               } catch (ClassCastException var7) {
                  throw new TypeMismathException(var2);
               } catch (InvocationTargetException var8) {
                  throw new ConversionException(var8);
               }
            }

            return var5;
         } catch (RuntimeException var9) {
            throw new ConversionException(var9);
         }
      }
   }

   @Override
   public String toString(Object var1, String var2) throws ConversionException {
      if (var1 == null) {
         return null;
      } else if (!(var1 instanceof Date)) {
         throw new TypeMismathException(var1.getClass());
      } else {
         try {
            if (StringUtil.isEmpty(var2)) {
               return DateFormat.getDateTimeInstance().format(var1);
            } else {
               DateFormat var3 = this.getDateFormat(var2);
               return var3.format(var1);
            }
         } catch (RuntimeException var4) {
            throw new ConversionException(var4);
         }
      }
   }

   private DateFormat getDateFormat(String var1) {
      return (DateFormat)this.getFormat(SimpleDateFormat.class, new Object[]{var1, Locale.US});
   }
}
