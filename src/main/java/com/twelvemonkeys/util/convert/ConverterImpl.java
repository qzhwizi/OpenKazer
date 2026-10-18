package com.twelvemonkeys.util.convert;

class ConverterImpl extends Converter {
   private PropertyConverter getConverterForType(Class var1) {
      Class var3 = var1;

      Object var2;
      while ((var2 = getInstance().converters.get(var3)) == null) {
         if ((var3 = var3.getSuperclass()) == null) {
            return null;
         }
      }

      return (PropertyConverter)var2;
   }

   @Override
   public Object toObject(String var1, Class var2, String var3) throws ConversionException {
      if (var1 == null) {
         return null;
      } else if (var2 == null) {
         throw new MissingTypeException();
      } else {
         PropertyConverter var4 = this.getConverterForType(var2);
         if (var4 == null) {
            throw new NoAvailableConverterException("Cannot convert to object, no converter available for type \"" + var2.getName() + "\"");
         } else {
            return var4.toObject(var1, var2, var3);
         }
      }
   }

   @Override
   public String toString(Object var1, String var2) throws ConversionException {
      if (var1 == null) {
         return null;
      } else {
         PropertyConverter var3 = this.getConverterForType(var1.getClass());
         if (var3 == null) {
            throw new NoAvailableConverterException("Cannot object to string, no converter available for type \"" + var1.getClass().getName() + "\"");
         } else {
            return var3.toString(var1, var2);
         }
      }
   }
}
