package com.twelvemonkeys.util.convert;

import com.twelvemonkeys.lang.StringUtil;
import com.twelvemonkeys.util.Time;
import com.twelvemonkeys.util.TimeFormat;

public class TimeConverter extends NumberConverter {
   @Override
   public Object toObject(String var1, Class var2, String var3) throws ConversionException {
      if (StringUtil.isEmpty(var1)) {
         return null;
      } else {
         try {
            TimeFormat var4;
            if (var3 == null) {
               var4 = TimeFormat.getInstance();
            } else {
               var4 = this.getTimeFormat(var3);
            }

            return var4.parse(var1);
         } catch (RuntimeException var6) {
            throw new ConversionException(var6);
         }
      }
   }

   @Override
   public String toString(Object var1, String var2) throws ConversionException {
      if (var1 == null) {
         return null;
      } else if (!(var1 instanceof Time)) {
         throw new TypeMismathException(var1.getClass());
      } else {
         try {
            if (StringUtil.isEmpty(var2)) {
               return var1.toString();
            } else {
               TimeFormat var3 = this.getTimeFormat(var2);
               return var3.format((Time)var1);
            }
         } catch (RuntimeException var4) {
            throw new ConversionException(var4);
         }
      }
   }

   private TimeFormat getTimeFormat(String var1) {
      return (TimeFormat)this.getFormat(TimeFormat.class, new Object[]{var1});
   }
}
