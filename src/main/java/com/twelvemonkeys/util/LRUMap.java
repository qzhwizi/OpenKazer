package com.twelvemonkeys.util;

import java.util.Map;
import java.util.Map.Entry;

public class LRUMap<K, V> extends LinkedMap<K, V> implements ExpiringMap<K, V> {
   private int maxSize = 1000;
   private float trimFactor = 0.01F;

   public LRUMap() {
      super(null, true);
   }

   public LRUMap(int var1) {
      super(null, true);
      this.setMaxSize(var1);
   }

   public LRUMap(Map<? extends K, ? extends V> var1) {
      super(var1, true);
   }

   public LRUMap(Map<? extends K, ? extends V> var1, int var2) {
      super(var1, true);
      this.setMaxSize(var2);
   }

   public LRUMap(Map<K, Entry<K, V>> var1, Map<? extends K, ? extends V> var2, int var3) {
      super(var1, var2, true);
      this.setMaxSize(var3);
   }

   public int getMaxSize() {
      return this.maxSize;
   }

   public void setMaxSize(int var1) {
      if (var1 < 0) {
         throw new IllegalArgumentException("max size must be positive");
      } else {
         this.maxSize = var1;

         while (this.size() > this.maxSize) {
            this.removeLRU();
         }
      }
   }

   public float getTrimFactor() {
      return this.trimFactor;
   }

   public void setTrimFactor(float var1) {
      if (!(var1 < 0.0F) && !(var1 >= 1.0F)) {
         this.trimFactor = var1;
      } else {
         throw new IllegalArgumentException("trim factor must be between 0 and 1");
      }
   }

   @Override
   protected boolean removeEldestEntry(Entry var1) {
      if (this.size() >= this.maxSize) {
         this.removeLRU();
      }

      return false;
   }

   @Override
   protected Entry<K, V> removeEntry(Entry<K, V> var1) {
      Entry var2 = super.removeEntry(var1);
      this.processRemoved(var1);
      return var2;
   }

   @Override
   public void processRemoved(Entry<K, V> var1) {
   }

   public void removeLRU() {
      int var1 = (int)Math.max(this.size() * this.trimFactor, 1.0F);

      while (var1-- > 0) {
         this.removeEntry(this.head.next);
      }
   }
}
