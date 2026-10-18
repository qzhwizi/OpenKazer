package com.twelvemonkeys.util;

import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.Map.Entry;

public class WeakWeakMap<K, V> extends WeakHashMap<K, V> {
   public WeakWeakMap() {
   }

   public WeakWeakMap(int var1) {
      super(var1);
   }

   public WeakWeakMap(int var1, float var2) {
      super(var1, var2);
   }

   public WeakWeakMap(Map<? extends K, ? extends V> var1) {
      super(var1);
   }

   @Override
   public V put(K var1, V var2) {
      return super.put((K)var1, (V)(new WeakReference<Object>(var2)));
   }

   @Override
   public V get(Object var1) {
      WeakReference var2 = (WeakReference)super.get(var1);
      return (V)(var2 != null ? var2.get() : null);
   }

   @Override
   public V remove(Object var1) {
      WeakReference var2 = (WeakReference)super.remove(var1);
      return (V)(var2 != null ? var2.get() : null);
   }

   @Override
   public boolean containsValue(Object var1) {
      for (Object var3 : this.values()) {
         if (var1 == var3 || var3 != null && var3.equals(var1)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public void putAll(Map<? extends K, ? extends V> var1) {
      for (Entry var3 : var1.entrySet()) {
         this.put((K)var3.getKey(), (V)var3.getValue());
      }
   }

   @Override
   public Set<Entry<K, V>> entrySet() {
      return new AbstractSet<Entry<K, V>>() {
         @Override
         public Iterator<Entry<K, V>> iterator() {
            return new Iterator<Entry<K, V>>() {
               final Iterator<Entry<K, WeakReference<V>>> iterator = (Iterator<Entry<K, WeakReference<V>>>) (Iterator<?>) WeakWeakMap.super.entrySet().iterator();

               @Override
               public boolean hasNext() {
                  return this.iterator.hasNext();
               }

               public Entry<K, V> next() {
                  return new Entry<K, V>() {
                     final Entry<K, WeakReference<V>> entry = iterator.next();

                     @Override
                     public K getKey() {
                        return this.entry.getKey();
                     }

                     @Override
                     public V getValue() {
                        WeakReference var1 = this.entry.getValue();
                        return (V)var1.get();
                     }

                     @Override
                     public V setValue(V var1) {
                        WeakReference var2 = this.entry.setValue(new WeakReference<>((V)var1));
                        return (V)(var2 != null ? var2.get() : null);
                     }

                     @Override
                     public boolean equals(Object var1) {
                        return this.entry.equals(var1);
                     }

                     @Override
                     public int hashCode() {
                        return this.entry.hashCode();
                     }

                     @Override
                     public String toString() {
                        return this.entry.toString();
                     }
                  };
               }

               @Override
               public void remove() {
                  this.iterator.remove();
               }
            };
         }

         @Override
         public int size() {
            return WeakWeakMap.this.size();
         }
      };
   }

   @Override
   public Collection<V> values() {
      return new AbstractCollection<V>() {
         @Override
         public Iterator<V> iterator() {
            return new Iterator<V>() {
               Iterator<WeakReference<V>> iterator = (Iterator<WeakReference<V>>) (Iterator<?>) WeakWeakMap.super.values().iterator();

               @Override
               public boolean hasNext() {
                  return this.iterator.hasNext();
               }

               @Override
               public V next() {
                  WeakReference var1 = this.iterator.next();
                  return (V)var1.get();
               }

               @Override
               public void remove() {
                  this.iterator.remove();
               }
            };
         }

         @Override
         public int size() {
            return WeakWeakMap.this.size();
         }
      };
   }
}
