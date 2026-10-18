package com.twelvemonkeys.util.convert;

public class ConversionException extends IllegalArgumentException {
   public ConversionException(String var1) {
      super(var1);
   }

   public ConversionException(Throwable var1) {
      super(var1 != null ? var1.getMessage() : null, var1);
   }

   public ConversionException(String var1, Throwable var2) {
      super(var1, var2);
   }
}
