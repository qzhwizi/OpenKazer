package com.twelvemonkeys.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public final class NullMap<K, V> implements Serializable, Map<K, V> {
   @Override
   public final int size() {
      return 0;
   }

   @Override
   public final void clear() {
   }

   @Override
   public final boolean isEmpty() {
      return true;
   }

   @Override
   public final boolean containsKey(Object var1) {
      return false;
   }

   @Override
   public final boolean containsValue(Object var1) {
      return false;
   }

   @Override
   public final Collection<V> values() {
      return Collections.emptyList();
   }

   @Override
   public final void putAll(Map var1) {
   }

   @Override
   public final Set<Entry<K, V>> entrySet() {
      return Collections.emptySet();
   }

   @Override
   public final Set<K> keySet() {
      return Collections.emptySet();
   }

   @Override
   public final V get(Object var1) {
      return null;
   }

   @Override
   public final V remove(Object var1) {
      return null;
   }

   @Override
   public final V put(Object var1, Object var2) {
      return null;
   }

   @Override
   public boolean equals(Object var1) {
      return var1 instanceof Map && ((Map)var1).isEmpty();
   }

   @Override
   public int hashCode() {
      return 0;
   }
}
