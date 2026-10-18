package com.twelvemonkeys.util.convert;

public class MissingTypeException extends ConversionException {
   public MissingTypeException() {
      super("Cannot convert, missing type");
   }

   public MissingTypeException(String var1) {
      super(var1);
   }
}
