package com.twelvemonkeys.util.convert;

public class TypeMismathException extends ConversionException {
   public TypeMismathException(Class var1) {
      super("Wrong type for conversion: " + var1.getName());
   }
}
