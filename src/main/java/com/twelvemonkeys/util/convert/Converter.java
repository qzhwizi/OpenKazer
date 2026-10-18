package com.twelvemonkeys.util.convert;

import com.twelvemonkeys.util.Time;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

public abstract class Converter implements PropertyConverter {
   protected static final Converter sInstance = new ConverterImpl();
   protected final Map<Class, PropertyConverter> converters = new Hashtable<>();

   protected Converter() {
   }

   public static Converter getInstance() {
      return sInstance;
   }

   public static void registerConverter(Class<?> var0, PropertyConverter var1) {
      getInstance().converters.put(var0, var1);
   }

   public static void unregisterConverter(Class<?> var0) {
      getInstance().converters.remove(var0);
   }

   public Object toObject(String var1, Class var2) throws ConversionException {
      return this.toObject(var1, var2, null);
   }

   @Override
   public abstract Object toObject(String var1, Class var2, String var3) throws ConversionException;

   public String toString(Object var1) throws ConversionException {
      return this.toString(var1, null);
   }

   @Override
   public abstract String toString(Object var1, String var2) throws ConversionException;

   static {
      DefaultConverter var0 = new DefaultConverter();
      registerConverter(Object.class, var0);
      registerConverter(boolean.class, var0);
      NumberConverter var1 = new NumberConverter();
      registerConverter(Number.class, var1);
      registerConverter(byte.class, var1);
      registerConverter(double.class, var1);
      registerConverter(float.class, var1);
      registerConverter(int.class, var1);
      registerConverter(long.class, var1);
      registerConverter(short.class, var1);
      registerConverter(Date.class, new DateConverter());
      registerConverter(Time.class, new TimeConverter());
   }
}
