package com.twelvemonkeys.util;

import com.twelvemonkeys.lang.Validate;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

public final class CollectionUtil {
   public static void main(String[] var0) {
      int var1 = 1000;
      if (var0.length > 0) {
         var1 = Integer.parseInt(var0[0]);
      }

      String[] var10000 = new String[]{"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
      String[] var6 = new String[]{
         "zero",
         "one",
         "two",
         "three",
         "four",
         "five",
         "six",
         "seven",
         "eight",
         "nine",
         "ten",
         "eleven",
         "twelve",
         "thirteen",
         "fourteen",
         "fifteen",
         "sixteen",
         "seventeen",
         "eighteen",
         "nineteen"
      };
      System.out.println("\nFilterIterators:\n");
      List var7 = Arrays.asList(var6);
      FilterIterator var8 = new FilterIterator(var7.iterator(), new FilterIterator.Filter() {
         @Override
         public boolean accept(Object var1) {
            return ((String)var1).length() > 5;
         }
      });

      while (var8.hasNext()) {
         String var9 = (String)var8.next();
         System.out.println(var9 + " has more than 5 letters!");
      }

      var8 = new FilterIterator(var7.iterator(), new FilterIterator.Filter() {
         @Override
         public boolean accept(Object var1) {
            return ((String)var1).length() <= 5;
         }
      });

      while (var8.hasNext()) {
         String var12 = (String)var8.next();
         System.out.println(var12 + " has less than, or exactly 5 letters!");
      }

      long var2 = System.currentTimeMillis();

      for (int var13 = 0; var13 < var1; var13++) {
         var8 = new FilterIterator(var7.iterator(), new FilterIterator.Filter() {
            @Override
            public boolean accept(Object var1) {
               return ((String)var1).length() <= 5;
            }
         });

         while (var8.hasNext()) {
            var8.next();
            System.out.print("");
         }
      }
   }

   private CollectionUtil() {
   }

   public static Object mergeArrays(Object var0, Object var1) {
      return mergeArrays(var0, 0, Array.getLength(var0), var1, 0, Array.getLength(var1));
   }

   public static Object mergeArrays(Object var0, int var1, int var2, Object var3, int var4, int var5) {
      Class var6 = var0.getClass();
      Class var7 = var6.getComponentType();
      Object var8 = Array.newInstance(var7, var2 + var5);
      System.arraycopy(var0, var1, var8, 0, var2);
      System.arraycopy(var3, var4, var8, var2, var5);
      return var8;
   }

   public static Object subArray(Object var0, int var1) {
      return subArray(var0, var1, -1);
   }

   public static <T> T[] subArray(T[] var0, int var1) {
      return (T[])subArray(var0, var1, -1);
   }

   public static Object subArray(Object var0, int var1, int var2) {
      Validate.notNull(var0, "array");
      if (var1 < 0) {
         throw new ArrayIndexOutOfBoundsException(var1 + " < 0");
      } else {
         Class var3;
         if ((var3 = var0.getClass().getComponentType()) == null) {
            throw new IllegalArgumentException("Not an array: " + var0);
         } else {
            int var4 = Array.getLength(var0);
            int var5 = var2 < 0 ? Math.max(0, var4 - var1) : Math.min(var2, Math.max(0, var4 - var1));
            Object var6;
            if (var5 < var4) {
               var6 = Array.newInstance(var3, var5);
               System.arraycopy(var0, var1, var6, 0, var5);
            } else {
               var6 = var0;
            }

            return var6;
         }
      }
   }

   public static <T> T[] subArray(T[] var0, int var1, int var2) {
      return (T[])((Object[])subArray(var0, var1, var2));
   }

   public static <T> Iterator<T> iterator(final Enumeration<T> var0) {
      Validate.notNull(var0, "enumeration");
      return new Iterator<T>() {
         @Override
         public boolean hasNext() {
            return var0.hasMoreElements();
         }

         @Override
         public T next() {
            return (T)var0.nextElement();
         }

         @Override
         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }

   public static <E> void addAll(Collection<E> var0, Iterator<? extends E> var1) {
      while (var1.hasNext()) {
         var0.add(var1.next());
      }
   }

   public static <E> ListIterator<E> iterator(E[] var0) {
      return iterator((E[])var0, 0, Validate.notNull(var0).length);
   }

   public static <E> ListIterator<E> iterator(E[] var0, int var1, int var2) {
      return new CollectionUtil.ArrayIterator<>((E[])var0, var1, var2);
   }

   public static <K, V> Map<V, K> invert(Map<K, V> var0) {
      return invert(var0, null, null);
   }

   public static <K, V> Map<V, K> invert(Map<K, V> var0, Map<V, K> var1, DuplicateHandler<K> var2) {
      if (var0 == null) {
         throw new IllegalArgumentException("source == null");
      } else {
         Map var3 = var1;
         if (var1 == null) {
            try {
               var3 = (Map)var0.getClass().newInstance();
            } catch (InstantiationException var9) {
            } catch (IllegalAccessException var10) {
            }

            if (var3 == null) {
               throw new IllegalArgumentException("result == null and source class " + var0.getClass() + " cannot be instantiated.");
            }
         }

         for (Entry var6 : var0.entrySet()) {
            Object var7 = var6.getValue();
            Object var8 = var6.getKey();
            if (var3.containsKey(var7)) {
               if (var2 == null) {
                  throw new IllegalArgumentException("Result would include duplicate keys, but no DuplicateHandler specified.");
               }

               var8 = (K) var2.resolve((K) var3.get(var7), (K) var8);
            }

            var3.put(var7, var8);
         }

         return var3;
      }
   }

   public static <T> Comparator<T> reverseOrder(Comparator<T> var0) {
      return new CollectionUtil.ReverseComparator<>(var0);
   }

   static <T extends Iterator<? super E>, E> T generify(Iterator<?> var0, Class<E> var1) {
      return (T)var0;
   }

   static <T extends Collection<? super E>, E> T generify(Collection<?> var0, Class<E> var1) {
      return (T)var0;
   }

   static <T extends Map<? super K, ? super V>, K, V> T generify(Map<?, ?> var0, Class<K> var1, Class<V> var2) {
      return (T)var0;
   }

   static <T extends Collection<? super E>, E> T generify2(Collection<?> var0) {
      return (T)var0;
   }

   private static class ArrayIterator<E> implements ListIterator<E> {
      private int next;
      private final int start;
      private final int length;
      private final E[] array;

      public ArrayIterator(E[] var1, int var2, int var3) {
         this.array = (E[])Validate.notNull(var1, "array");
         this.start = Validate.isTrue(var2 >= 0, var2, "start < 0: %d");
         this.length = Validate.isTrue(var3 <= var1.length - var2, var3, "length > array.length - start: %d");
         this.next = this.start;
      }

      @Override
      public boolean hasNext() {
         return this.next < this.length + this.start;
      }

      @Override
      public E next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            try {
               return this.array[this.next++];
            } catch (ArrayIndexOutOfBoundsException var3) {
               NoSuchElementException var2 = new NoSuchElementException(var3.getMessage());
               var2.initCause(var3);
               throw var2;
            }
         }
      }

      @Override
      public void remove() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void add(E var1) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean hasPrevious() {
         return this.next > this.start;
      }

      @Override
      public int nextIndex() {
         return this.next - this.start;
      }

      @Override
      public E previous() {
         if (!this.hasPrevious()) {
            throw new NoSuchElementException();
         } else {
            try {
               return this.array[--this.next];
            } catch (ArrayIndexOutOfBoundsException var3) {
               NoSuchElementException var2 = new NoSuchElementException(var3.getMessage());
               var2.initCause(var3);
               throw var2;
            }
         }
      }

      @Override
      public int previousIndex() {
         return this.nextIndex() - 1;
      }

      @Override
      public void set(E var1) {
         this.array[this.next - 1] = (E)var1;
      }
   }

   private static class ReverseComparator<T> implements Comparator<T> {
      private final Comparator<T> comparator;

      public ReverseComparator(Comparator<T> var1) {
         this.comparator = Validate.notNull(var1);
      }

      @Override
      public int compare(T var1, T var2) {
         int var3 = this.comparator.compare((T)var1, (T)var2);
         return -(var3 | var3 >>> 1);
      }
   }
}
