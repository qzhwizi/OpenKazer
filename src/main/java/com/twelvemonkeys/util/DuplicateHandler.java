package com.twelvemonkeys.util;

public interface DuplicateHandler<T> {
   DuplicateHandler<?> USE_FIRST_VALUE = new DuplicateHandler() {
      @Override
      public Object resolve(Object var1, Object var2) {
         return var1;
      }
   };
   DuplicateHandler<?> USE_LAST_VALUE = new DuplicateHandler() {
      @Override
      public Object resolve(Object var1, Object var2) {
         return var2;
      }
   };
   DuplicateHandler<?> DUPLICATES_AS_ARRAY = new DuplicateHandler() {
      @Override
      public Object resolve(Object var1, Object var2) {
         Object[] var3;
         if (var1 instanceof Object[]) {
            Object[] var4 = (Object[])var1;
            var3 = new Object[var4.length + 1];
            System.arraycopy(var4, 0, var3, 0, var4.length);
            var3[var4.length] = var2;
         } else {
            var3 = new Object[]{var1, var2};
         }

         return var3;
      }
   };
   DuplicateHandler<String> DUPLICATES_AS_CSV = new DuplicateHandler<String>() {
      public String resolve(String var1, String var2) {
         StringBuilder var3 = new StringBuilder(String.valueOf(var1));
         var3.append(',');
         var3.append(var2);
         return var3.toString();
      }
   };

   T resolve(T var1, T var2);
}
