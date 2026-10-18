package com.twelvemonkeys.util;

import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

public class LinkedMap<K, V> extends AbstractDecoratedMap<K, V> implements Serializable {
   transient LinkedMap.LinkedEntry<K, V> head;
   protected final boolean accessOrder;

   public LinkedMap() {
      this(null, false);
   }

   public LinkedMap(boolean var1) {
      this(null, var1);
   }

   public LinkedMap(Map<? extends K, ? extends V> var1) {
      this(var1, false);
   }

   public LinkedMap(Map<? extends K, ? extends V> var1, boolean var2) {
      super(var1);
      this.accessOrder = var2;
   }

   public LinkedMap(Map<K, Entry<K, V>> var1, Map<? extends K, ? extends V> var2) {
      this(var1, var2, false);
   }

   public LinkedMap(Map<K, Entry<K, V>> var1, Map<? extends K, ? extends V> var2, boolean var3) {
      super(var1, var2);
      this.accessOrder = var3;
   }

   @Override
   protected void init() {
      this.head = new LinkedMap.LinkedEntry<K, V>(null, null, null) {
         @Override
         void addBefore(LinkedMap.LinkedEntry var1) {
            throw new Error();
         }

         @Override
         void remove() {
            throw new Error();
         }

         @Override
         public void recordAccess(Map var1) {
            throw new Error();
         }

         @Override
         public void recordRemoval(Map var1) {
            throw new Error();
         }

         public void recordRemoval() {
            throw new Error();
         }

         @Override
         public V getValue() {
            throw new Error();
         }

         @Override
         public V setValue(V var1) {
            throw new Error();
         }

         @Override
         public K getKey() {
            throw new Error();
         }

         @Override
         public String toString() {
            return "head";
         }
      };
      this.head.previous = this.head.next = this.head;
   }

   @Override
   public boolean containsValue(Object var1) {
      if (var1 == null) {
         for (LinkedMap.LinkedEntry var2 = this.head.next; var2 != this.head; var2 = var2.next) {
            if (var2.mValue == null) {
               return true;
            }
         }
      } else {
         for (LinkedMap.LinkedEntry var3 = this.head.next; var3 != this.head; var3 = var3.next) {
            if (var1.equals(var3.mValue)) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   protected Iterator<K> newKeyIterator() {
      return new LinkedMap.KeyIterator();
   }

   @Override
   protected Iterator<V> newValueIterator() {
      return new LinkedMap.ValueIterator();
   }

   @Override
   protected Iterator<Entry<K, V>> newEntryIterator() {
      return new LinkedMap.EntryIterator();
   }

   @Override
   public V get(Object var1) {
      LinkedMap.LinkedEntry var2 = (LinkedMap.LinkedEntry)this.entries.get(var1);
      if (var2 != null) {
         var2.recordAccess(this);
         return (V) var2.mValue;
      } else {
         return null;
      }
   }

   @Override
   public V remove(Object var1) {
      LinkedMap.LinkedEntry var2 = (LinkedMap.LinkedEntry)this.entries.remove(var1);
      if (var2 != null) {
         var2.remove();
         this.modCount++;
         return (V) var2.mValue;
      } else {
         return null;
      }
   }

   @Override
   public V put(K var1, V var2) {
      LinkedMap.LinkedEntry var3 = (LinkedMap.LinkedEntry)this.entries.get(var1);
      Object var4;
      if (var3 == null) {
         var4 = null;
         LinkedMap.LinkedEntry var5 = this.head.next;
         if (this.removeEldestEntry(var5)) {
            this.removeEntry(var5);
         }

         var3 = this.createEntry((K)var1, (V)var2);
         var3.addBefore(this.head);
         this.entries.put((K)var1, var3);
      } else {
         var4 = var3.mValue;
         var3.mValue = (V)var2;
         var3.recordAccess(this);
      }

      this.modCount++;
      return (V)var4;
   }

   LinkedMap.LinkedEntry<K, V> createEntry(K var1, V var2) {
      return new LinkedMap.LinkedEntry<>((K)var1, (V)var2, null);
   }

   @Override
   public Object clone() throws CloneNotSupportedException {
      return (LinkedMap)super.clone();
   }

   protected boolean removeEldestEntry(Entry<K, V> var1) {
      return false;
   }

   private class EntryIterator extends LinkedMap<K, V>.LinkedMapIterator<Entry<K, V>> {
      private EntryIterator() {
      }

      public Entry<K, V> next() {
         return this.nextEntry();
      }
   }

   private class KeyIterator extends LinkedMap<K, V>.LinkedMapIterator<K> {
      private KeyIterator() {
      }

      @Override
      public K next() {
         return this.nextEntry().mKey;
      }
   }

   protected static class LinkedEntry<K, V> extends AbstractDecoratedMap.BasicEntry<K, V> implements Serializable {
      LinkedMap.LinkedEntry<K, V> previous;
      LinkedMap.LinkedEntry<K, V> next;

      LinkedEntry(K var1, V var2, LinkedMap.LinkedEntry<K, V> var3) {
         super((K)var1, (V)var2);
         this.next = var3;
      }

      void addBefore(LinkedMap.LinkedEntry<K, V> var1) {
         this.next = var1;
         this.previous = var1.previous;
         this.previous.next = this;
         this.next.previous = this;
      }

      void remove() {
         this.previous.next = this.next;
         this.next.previous = this.previous;
      }

      @Override
      protected void recordAccess(Map<K, V> var1) {
         LinkedMap var2 = (LinkedMap)var1;
         if (var2.accessOrder) {
            var2.modCount++;
            this.remove();
            this.addBefore(var2.head);
         }
      }

      @Override
      protected void recordRemoval(Map<K, V> var1) {
         this.remove();
      }
   }

   private abstract class LinkedMapIterator<E> implements Iterator<E> {
      LinkedMap.LinkedEntry<K, V> mNextEntry = LinkedMap.this.head.next;
      LinkedMap.LinkedEntry<K, V> mLastReturned = null;
      int mExpectedModCount = LinkedMap.this.modCount;

      private LinkedMapIterator() {
      }

      @Override
      public boolean hasNext() {
         return this.mNextEntry != LinkedMap.this.head;
      }

      @Override
      public void remove() {
         if (this.mLastReturned == null) {
            throw new IllegalStateException();
         } else if (LinkedMap.this.modCount != this.mExpectedModCount) {
            throw new ConcurrentModificationException();
         } else {
            LinkedMap.this.remove(this.mLastReturned.mKey);
            this.mLastReturned = null;
            this.mExpectedModCount = LinkedMap.this.modCount;
         }
      }

      LinkedMap.LinkedEntry<K, V> nextEntry() {
         if (LinkedMap.this.modCount != this.mExpectedModCount) {
            throw new ConcurrentModificationException();
         } else if (this.mNextEntry == LinkedMap.this.head) {
            throw new NoSuchElementException();
         } else {
            LinkedMap.LinkedEntry var1 = this.mLastReturned = this.mNextEntry;
            this.mNextEntry = var1.next;
            return var1;
         }
      }
   }

   private class ValueIterator extends LinkedMap<K, V>.LinkedMapIterator<V> {
      private ValueIterator() {
      }

      @Override
      public V next() {
         return this.nextEntry().mValue;
      }
   }
}
