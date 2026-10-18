package com.twelvemonkeys.imageio.metadata;

import com.twelvemonkeys.lang.Validate;
import com.twelvemonkeys.util.CollectionUtil;
import java.lang.reflect.Array;
import java.util.Arrays;

public abstract class AbstractEntry implements Entry {
   private final Object identifier;
   private final Object value;

   protected AbstractEntry(Object var1, Object var2) {
      Validate.notNull(var1, "identifier");
      this.identifier = var1;
      this.value = var2;
   }

   @Override
   public final Object getIdentifier() {
      return this.identifier;
   }

   protected String getNativeIdentifier() {
      return String.valueOf(this.getIdentifier());
   }

   @Override
   public String getFieldName() {
      return null;
   }

   @Override
   public Object getValue() {
      return this.value;
   }

   @Override
   public String getValueAsString() {
      int var1 = this.valueCount();
      if (var1 == 0 && this.value != null && this.value.getClass().isArray() && Array.getLength(this.value) == 0) {
         return "";
      } else if (var1 > 1) {
         if (var1 < 16) {
            return arrayToString(this.value);
         } else {
            String var2 = arrayToString(CollectionUtil.subArray(this.value, 0, 4));
            String var3 = arrayToString(CollectionUtil.subArray(this.value, var1 - 4, 4));
            return String.format("%s ... %s (%d)", var2.substring(0, var2.length() - 1), var3.substring(1), var1);
         }
      } else {
         return this.value != null && this.value.getClass().isArray() && Array.getLength(this.value) == 1
            ? String.valueOf(Array.get(this.value, 0))
            : String.valueOf(this.value);
      }
   }

   private static String arrayToString(Object var0) {
      Class var1 = var0.getClass().getComponentType();
      if (var1.isPrimitive()) {
         if (var1.equals(boolean.class)) {
            return Arrays.toString((boolean[])var0);
         } else if (var1.equals(byte.class)) {
            return Arrays.toString((byte[])var0);
         } else if (var1.equals(char.class)) {
            return new String((char[])var0);
         } else if (var1.equals(double.class)) {
            return Arrays.toString((double[])var0);
         } else if (var1.equals(float.class)) {
            return Arrays.toString((float[])var0);
         } else if (var1.equals(int.class)) {
            return Arrays.toString((int[])var0);
         } else if (var1.equals(long.class)) {
            return Arrays.toString((long[])var0);
         } else if (var1.equals(short.class)) {
            return Arrays.toString((short[])var0);
         } else {
            throw new AssertionError("Unknown type: " + var1);
         }
      } else {
         return Arrays.toString((Object[])var0);
      }
   }

   @Override
   public String getTypeName() {
      return this.value == null ? null : this.value.getClass().getSimpleName();
   }

   @Override
   public int valueCount() {
      return this.value != null && this.value.getClass().isArray() ? Array.getLength(this.value) : 1;
   }

   @Override
   public int hashCode() {
      return this.identifier.hashCode() + (this.value != null ? 31 * this.value.hashCode() : 0);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof AbstractEntry)) {
         return false;
      } else {
         AbstractEntry var2 = (AbstractEntry)var1;
         return this.identifier.equals(var2.identifier) && (this.value == null && var2.value == null || this.value != null && this.valueEquals(var2));
      }
   }

   private boolean valueEquals(AbstractEntry var1) {
      return this.value.getClass().isArray() ? arrayEquals(this.value, var1.value) : this.value.equals(var1.value);
   }

   static boolean arrayEquals(Object var0, Object var1) {
      if (var0 == var1) {
         return true;
      } else if (var1 != null && var0 != null && var0.getClass() == var1.getClass()) {
         Class var2 = var0.getClass().getComponentType();
         if (!var2.isPrimitive()) {
            return Arrays.equals((Object[])var0, (Object[])var1);
         } else if (var0 instanceof byte[]) {
            return Arrays.equals((byte[])var0, (byte[])var1);
         } else if (var0 instanceof char[]) {
            return Arrays.equals((char[])var0, (char[])var1);
         } else if (var0 instanceof short[]) {
            return Arrays.equals((short[])var0, (short[])var1);
         } else if (var0 instanceof int[]) {
            return Arrays.equals((int[])var0, (int[])var1);
         } else if (var0 instanceof long[]) {
            return Arrays.equals((long[])var0, (long[])var1);
         } else if (var0 instanceof boolean[]) {
            return Arrays.equals((boolean[])var0, (boolean[])var1);
         } else if (var0 instanceof float[]) {
            return Arrays.equals((float[])var0, (float[])var1);
         } else if (var0 instanceof double[]) {
            return Arrays.equals((double[])var0, (double[])var1);
         } else {
            throw new AssertionError("Unsupported type:" + var2);
         }
      } else {
         return false;
      }
   }

   @Override
   public String toString() {
      String var1 = this.getFieldName();
      String var2 = var1 != null ? String.format("/%s", var1) : "";
      String var3 = this.getTypeName();
      String var4 = var3 != null ? String.format(" (%s)", var3) : "";
      return String.format("%s%s: %s%s", this.getNativeIdentifier(), var2, this.getValueAsString(), var4);
   }
}
