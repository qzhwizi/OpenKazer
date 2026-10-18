package com.twelvemonkeys.util;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class IgnoreCaseMap<V> extends AbstractDecoratedMap<String, V> implements Serializable, Cloneable {
   public IgnoreCaseMap() {
   }

   public IgnoreCaseMap(Map<String, ? extends V> var1) {
      super(var1);
   }

   public IgnoreCaseMap(Map var1, Map<String, ? extends V> var2) {
      super(var1, var2);
   }

   public V put(String var1, V var2) {
      String var3 = (String)toUpper(var1);
      return this.unwrap(this.entries.put(var3, new AbstractDecoratedMap.BasicEntry<>(var3, (V)var2)));
   }

   private V unwrap(Entry<String, V> var1) {
      return (V)(var1 != null ? var1.getValue() : null);
   }

   @Override
   public V get(Object var1) {
      return this.unwrap(this.entries.get(toUpper(var1)));
   }

   @Override
   public V remove(Object var1) {
      return this.unwrap(this.entries.remove(toUpper(var1)));
   }

   @Override
   public boolean containsKey(Object var1) {
      return this.entries.containsKey(toUpper(var1));
   }

   protected static Object toUpper(Object var0) {
      return var0 instanceof String ? ((String)var0).toUpperCase() : var0;
   }

   @Override
   protected Iterator<Entry<String, V>> newEntryIterator() {
      return (Iterator<Entry<String, V>>) (Iterator<?>) this.entries.entrySet().iterator();
   }

   @Override
   protected Iterator<String> newKeyIterator() {
      return this.entries.keySet().iterator();
   }

   @Override
   protected Iterator<V> newValueIterator() {
      return (Iterator<V>) (Iterator<?>) this.entries.values().iterator();
   }
}
