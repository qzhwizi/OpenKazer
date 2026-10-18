package com.twelvemonkeys.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Map.Entry;

@Deprecated
public class TimeoutMap<K, V> extends AbstractDecoratedMap<K, V> implements ExpiringMap<K, V>, Serializable, Cloneable {
   private boolean initialized;
   protected long expiryTime = 60000L;
   private volatile long nextExpiryTime;

   public TimeoutMap() {
   }

   public TimeoutMap(Map<? extends K, ? extends V> var1) {
      super(var1);
   }

   public TimeoutMap(long var1) {
      this();
      this.expiryTime = var1;
      this.initialized = true;
   }

   public TimeoutMap(Map<K, Entry<K, V>> var1, Map<? extends K, ? extends V> var2, long var3) {
      super(var1, var2);
      this.expiryTime = var3;
      this.initialized = true;
   }

   public long getExpiryTime() {
      return this.expiryTime;
   }

   public void setExpiryTime(long var1) {
      long var3 = this.expiryTime;
      this.expiryTime = var1;
      if (this.expiryTime < var3) {
         this.nextExpiryTime = 0L;
         this.removeExpiredEntries();
      }
   }

   @Override
   public int size() {
      this.removeExpiredEntries();
      return this.entries.size();
   }

   @Override
   public boolean isEmpty() {
      return this.size() <= 0;
   }

   @Override
   public boolean containsKey(Object var1) {
      this.removeExpiredEntries();
      return this.entries.containsKey(var1);
   }

   @Override
   public V get(Object var1) {
      TimeoutMap.TimedEntry var2 = (TimeoutMap.TimedEntry)this.entries.get(var1);
      if (var2 == null) {
         return null;
      } else if (var2.isExpired()) {
         this.entries.remove(var1);
         this.processRemoved(var2);
         return null;
      } else {
         return (V)var2.getValue();
      }
   }

   @Override
   public V put(K var1, V var2) {
      TimeoutMap.TimedEntry var3 = (TimeoutMap.TimedEntry)this.entries.get(var1);
      Object var4;
      if (var3 == null) {
         var4 = null;
         var3 = this.createEntry((K)var1, (V)var2);
         this.entries.put((K)var1, var3);
      } else {
         var4 = var3.mValue;
         var3.setValue((V)var2);
         var3.recordAccess(this);
      }

      this.removeExpiredEntries();
      this.modCount++;
      return (V)var4;
   }

   @Override
   public V remove(Object var1) {
      TimeoutMap.TimedEntry var2 = (TimeoutMap.TimedEntry)this.entries.remove(var1);
      return (V)(var2 != null ? var2.getValue() : null);
   }

   @Override
   public void clear() {
      this.entries.clear();
      this.init();
   }

   TimeoutMap<K, V>.TimedEntry createEntry(K var1, V var2) {
      return new TimeoutMap.TimedEntry(var1, var2);
   }

   protected void removeExpiredEntries() {
      if (this.initialized) {
         long var1 = System.currentTimeMillis();
         if (var1 > this.nextExpiryTime) {
            this.removeExpiredEntriesSynced(var1);
         }
      }
   }

   private synchronized void removeExpiredEntriesSynced(long var1) {
      if (var1 > this.nextExpiryTime) {
         long var3 = Long.MAX_VALUE;
         this.nextExpiryTime = var3;
         TimeoutMap.EntryIterator var5 = new TimeoutMap.EntryIterator();

         while (var5.hasNext()) {
            TimeoutMap.TimedEntry var6 = (TimeoutMap.TimedEntry)var5.next();
            long var7 = var6.expires();
            if (var7 < var3) {
               var3 = var7;
            }
         }

         this.nextExpiryTime = var3;
      }
   }

   @Override
   public Collection<V> values() {
      this.removeExpiredEntries();
      return super.values();
   }

   @Override
   public Set<Entry<K, V>> entrySet() {
      this.removeExpiredEntries();
      return super.entrySet();
   }

   @Override
   public Set<K> keySet() {
      this.removeExpiredEntries();
      return super.keySet();
   }

   @Override
   protected Iterator<K> newKeyIterator() {
      return new TimeoutMap.KeyIterator();
   }

   @Override
   protected Iterator<V> newValueIterator() {
      return new TimeoutMap.ValueIterator();
   }

   @Override
   protected Iterator<Entry<K, V>> newEntryIterator() {
      return new TimeoutMap.EntryIterator();
   }

   @Override
   public void processRemoved(Entry var1) {
   }

   private class EntryIterator extends TimeoutMap<K, V>.TimeoutMapIterator<Entry<K, V>> {
      private EntryIterator() {
      }

      public Entry<K, V> next() {
         return this.nextEntry();
      }
   }

   private class KeyIterator extends TimeoutMap<K, V>.TimeoutMapIterator<K> {
      private KeyIterator() {
      }

      @Override
      public K next() {
         return this.nextEntry().mKey;
      }
   }

   private class TimedEntry extends AbstractDecoratedMap.BasicEntry<K, V> {
      private long mTimestamp;

      TimedEntry(K var2, V var3) {
         super((K)var2, (V)var3);
         this.updateTimestamp();
      }

      @Override
      public V setValue(V var1) {
         this.updateTimestamp();
         return (V)super.setValue(var1);
      }

      private void updateTimestamp() {
         this.mTimestamp = System.currentTimeMillis();
         long var1 = this.expires();
         if (var1 < TimeoutMap.this.nextExpiryTime) {
            TimeoutMap.this.nextExpiryTime = var1;
         }
      }

      final boolean isExpired() {
         return this.isExpiredBy(System.currentTimeMillis());
      }

      final boolean isExpiredBy(long var1) {
         return var1 > this.expires();
      }

      final long expires() {
         return this.mTimestamp + TimeoutMap.this.expiryTime;
      }
   }

   private abstract class TimeoutMapIterator<E> implements Iterator<E> {
      Iterator<Entry<K, Entry<K, V>>> mIterator = TimeoutMap.this.entries.entrySet().iterator();
      AbstractDecoratedMap.BasicEntry<K, V> mNext;
      long mNow = System.currentTimeMillis();

      private TimeoutMapIterator() {
      }

      @Override
      public void remove() {
         this.mNext = null;
         this.mIterator.remove();
      }

      @Override
      public boolean hasNext() {
         if (this.mNext != null) {
            return true;
         } else {
            while (this.mNext == null && this.mIterator.hasNext()) {
               Entry var1 = this.mIterator.next();
               TimeoutMap.TimedEntry var2 = (TimeoutMap.TimedEntry)var1.getValue();
               if (!var2.isExpiredBy(this.mNow)) {
                  this.mNext = var2;
                  return true;
               }

               this.mIterator.remove();
               TimeoutMap.this.processRemoved(var2);
            }

            return false;
         }
      }

      AbstractDecoratedMap.BasicEntry<K, V> nextEntry() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            AbstractDecoratedMap.BasicEntry var1 = this.mNext;
            this.mNext = null;
            return var1;
         }
      }
   }

   private class ValueIterator extends TimeoutMap<K, V>.TimeoutMapIterator<V> {
      private ValueIterator() {
      }

      @Override
      public V next() {
         return this.nextEntry().mValue;
      }
   }
}
