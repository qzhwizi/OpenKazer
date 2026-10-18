package com.twelvemonkeys.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FilterIterator<E> implements Iterator<E> {
   protected final FilterIterator.Filter<E> filter;
   protected final Iterator<E> iterator;
   private E next = (E)null;
   private E current = (E)null;

   public FilterIterator(Iterator<E> var1, FilterIterator.Filter<E> var2) {
      if (var1 == null) {
         throw new IllegalArgumentException("iterator == null");
      } else if (var2 == null) {
         throw new IllegalArgumentException("filter == null");
      } else {
         this.iterator = var1;
         this.filter = var2;
      }
   }

   @Override
   public boolean hasNext() {
      while (this.next == null && this.iterator.hasNext()) {
         Object var1 = this.iterator.next();
         if (this.filter.accept((E)var1)) {
            this.next = (E)var1;
            break;
         }
      }

      return this.next != null;
   }

   @Override
   public E next() {
      if (this.hasNext()) {
         this.current = this.next;
         this.next = null;
         return this.current;
      } else {
         throw new NoSuchElementException("Iteration has no more elements.");
      }
   }

   @Override
   public void remove() {
      if (this.current != null) {
         this.iterator.remove();
      } else {
         throw new IllegalStateException("Iteration has no current element.");
      }
   }

   public interface Filter<E> {
      boolean accept(E var1);
   }
}
