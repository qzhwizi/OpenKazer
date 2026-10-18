package com.twelvemonkeys.util;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

abstract class AbstractDecoratedMap<K, V> extends AbstractMap<K, V> implements Serializable, Cloneable, Map<K, V> {
   protected Map<K, Entry<K, V>> entries;
   protected transient volatile int modCount;
   private transient volatile Set<Entry<K, V>> entrySet = null;
   private transient volatile Set<K> keySet = null;
   private transient volatile Collection<V> values = null;

   public AbstractDecoratedMap() {
      this(new HashMap<>(), null);
   }

   public AbstractDecoratedMap(Map<? extends K, ? extends V> var1) {
      this(new HashMap<>(), var1);
   }

   public AbstractDecoratedMap(Map<K, Entry<K, V>> var1, Map<? extends K, ? extends V> var2) {
      if (var1 == null) {
         throw new IllegalArgumentException("backing == null");
      } else {
         Entry[] var3 = null;
         if (var1 == var2) {
            Set var4 = var2.entrySet();
            var3 = new Entry[var4.size()];
            var3 = (Entry[]) var4.toArray(var3);
            var2 = null;
            var1.clear();
         } else if (!var1.isEmpty()) {
            throw new IllegalArgumentException("backing must be empty");
         }

         this.entries = var1;
         this.init();
         if (var2 != null) {
            this.putAll(var2);
         } else if (var3 != null) {
            for (Entry var7 : var3) {
               this.put((K)var7.getKey(), (V)var7.getValue());
            }
         }
      }
   }

   protected void init() {
   }

   @Override
   public int size() {
      return this.entries.size();
   }

   @Override
   public void clear() {
      this.entries.clear();
      this.modCount++;
      this.init();
   }

   @Override
   public boolean isEmpty() {
      return this.entries.isEmpty();
   }

   @Override
   public boolean containsKey(Object var1) {
      return this.entries.containsKey(var1);
   }

   @Override
   public boolean containsValue(Object var1) {
      for (Object var3 : this.values()) {
         if (var3 == var1 || var3 != null && var3.equals(var1)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public Collection<V> values() {
      Collection var1 = this.values;
      return var1 != null ? var1 : (this.values = new AbstractDecoratedMap.Values());
   }

   @Override
   public Set<Entry<K, V>> entrySet() {
      Set var1 = this.entrySet;
      return var1 != null ? var1 : (this.entrySet = new AbstractDecoratedMap.EntrySet());
   }

   @Override
   public Set<K> keySet() {
      Set var1 = this.keySet;
      return var1 != null ? var1 : (this.keySet = new AbstractDecoratedMap.KeySet());
   }

   @Override
   protected Object clone() throws CloneNotSupportedException {
      AbstractDecoratedMap var1 = (AbstractDecoratedMap)super.clone();
      var1.values = null;
      var1.entrySet = null;
      var1.keySet = null;
      return var1;
   }

   protected abstract Iterator<K> newKeyIterator();

   protected abstract Iterator<V> newValueIterator();

   protected abstract Iterator<Entry<K, V>> newEntryIterator();

   @Override
   public abstract V get(Object var1);

   @Override
   public abstract V remove(Object var1);

   @Override
   public abstract V put(K var1, V var2);

   Entry<K, V> createEntry(K var1, V var2) {
      return new AbstractDecoratedMap.BasicEntry<>((K)var1, (V)var2);
   }

   Entry<K, V> getEntry(K var1) {
      return this.entries.get(var1);
   }

   protected Entry<K, V> removeEntry(Entry<K, V> var1) {
      if (var1 == null) {
         return null;
      } else {
         Entry var2 = this.getEntry((K)var1.getKey());
         if (var2 != var1 && (var2 == null || !var2.equals(var1))) {
            return null;
         } else {
            this.remove(var1.getKey());
            return var1;
         }
      }
   }

   static class BasicEntry<K, V> implements Serializable, Entry<K, V> {
      K mKey;
      V mValue;

      BasicEntry(K var1, V var2) {
         this.mKey = (K)var1;
         this.mValue = (V)var2;
      }

      protected void recordAccess(Map<K, V> var1) {
      }

      protected void recordRemoval(Map<K, V> var1) {
      }

      @Override
      public V getValue() {
         return this.mValue;
      }

      @Override
      public V setValue(V var1) {
         Object var2 = this.mValue;
         this.mValue = (V)var1;
         return (V)var2;
      }

      @Override
      public K getKey() {
         return this.mKey;
      }

      @Override
      public boolean equals(Object var1) {
         if (!(var1 instanceof Entry)) {
            return false;
         } else {
            Entry var2 = (Entry)var1;
            Object var3 = this.mKey;
            Object var4 = var2.getKey();
            if (var3 == var4 || var3 != null && var3.equals(var4)) {
               Object var5 = this.mValue;
               Object var6 = var2.getValue();
               if (var5 == var6 || var5 != null && var5.equals(var6)) {
                  return true;
               }
            }

            return false;
         }
      }

      @Override
      public int hashCode() {
         return (this.mKey == null ? 0 : this.mKey.hashCode()) ^ (this.mValue == null ? 0 : this.mValue.hashCode());
      }

      @Override
      public String toString() {
         return this.getKey() + "=" + this.getValue();
      }
   }

   protected class EntrySet extends AbstractSet<Entry<K, V>> {
      @Override
      public Iterator<Entry<K, V>> iterator() {
         return AbstractDecoratedMap.this.newEntryIterator();
      }

      @Override
      public boolean contains(Object var1) {
         if (!(var1 instanceof Entry)) {
            return false;
         } else {
            Entry var2 = (Entry)var1;
            Entry var3 = AbstractDecoratedMap.this.entries.get(var2.getKey());
            return var3 != null && var3.equals(var2);
         }
      }

      @Override
      public boolean remove(Object var1) {
         return !(var1 instanceof Entry) ? false : AbstractDecoratedMap.this.removeEntry((Entry<K, V>)var1) != null;
      }

      @Override
      public int size() {
         return AbstractDecoratedMap.this.size();
      }

      @Override
      public void clear() {
         AbstractDecoratedMap.this.clear();
      }
   }

   protected class KeySet extends AbstractSet<K> {
      @Override
      public Iterator<K> iterator() {
         return AbstractDecoratedMap.this.newKeyIterator();
      }

      @Override
      public int size() {
         return AbstractDecoratedMap.this.size();
      }

      @Override
      public boolean contains(Object var1) {
         return AbstractDecoratedMap.this.containsKey(var1);
      }

      @Override
      public boolean remove(Object var1) {
         return AbstractDecoratedMap.this.removeEntry(AbstractDecoratedMap.this.getEntry((K)var1)) != null;
      }

      @Override
      public void clear() {
         AbstractDecoratedMap.this.clear();
      }
   }

   protected class Values extends AbstractCollection<V> {
      @Override
      public Iterator<V> iterator() {
         return AbstractDecoratedMap.this.newValueIterator();
      }

      @Override
      public int size() {
         return AbstractDecoratedMap.this.size();
      }

      @Override
      public boolean contains(Object var1) {
         return AbstractDecoratedMap.this.containsValue(var1);
      }

      @Override
      public void clear() {
         AbstractDecoratedMap.this.clear();
      }
   }
}
