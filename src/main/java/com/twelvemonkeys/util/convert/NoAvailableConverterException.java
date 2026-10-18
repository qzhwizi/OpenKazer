package com.twelvemonkeys.util.convert;

public class NoAvailableConverterException extends ConversionException {
   public NoAvailableConverterException() {
      super("Cannot convert, no converter available for given type");
   }

   public NoAvailableConverterException(String var1) {
      super(var1);
   }
}
