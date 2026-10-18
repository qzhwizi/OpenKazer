package com.twelvemonkeys.util.convert;

public interface PropertyConverter {
   Object toObject(String var1, Class var2, String var3) throws ConversionException;

   String toString(Object var1, String var2) throws ConversionException;
}
