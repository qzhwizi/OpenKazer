package com.twelvemonkeys.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class LRUHashMap<K, V> extends LinkedHashMap<K, V> implements ExpiringMap<K, V> {
   private int maxSize = 1000;
   private float trimFactor = 0.01F;

   public LRUHashMap() {
      super(16, 0.75F, true);
   }

   public LRUHashMap(int var1) {
      super(16, 0.75F, true);
      this.setMaxSize(var1);
   }

   public LRUHashMap(Map<? extends K, ? extends V> var1) {
      super(16, 0.75F, true);
      this.putAll(var1);
   }

   public LRUHashMap(Map<? extends K, ? extends V> var1, int var2) {
      super(16, 0.75F, true);
      this.setMaxSize(var2);
      this.putAll(var1);
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
   protected boolean removeEldestEntry(Entry<K, V> var1) {
      if (this.size() >= this.maxSize) {
         this.removeLRU();
      }

      return false;
   }

   @Override
   public void processRemoved(Entry<K, V> var1) {
   }

   public void removeLRU() {
      int var1 = (int)Math.max(this.size() * this.trimFactor, 1.0F);
      Iterator var2 = this.entrySet().iterator();

      while (var1-- > 0 && var2.hasNext()) {
         var2.next();
         var2.remove();
      }
   }
}
