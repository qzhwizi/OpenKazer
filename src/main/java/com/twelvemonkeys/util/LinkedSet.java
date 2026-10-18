package com.twelvemonkeys.util;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class LinkedSet<E> extends AbstractSet<E> implements Serializable, Cloneable, Set<E> {
   private static final Object DUMMY = new Object();
   private final Map<E, Object> map = new LinkedMap<>();

   public LinkedSet() {
   }

   public LinkedSet(Collection<E> var1) {
      this();
      this.addAll(var1);
   }

   @Override
   public boolean addAll(Collection<? extends E> var1) {
      boolean var2 = false;

      for (Object var4 : var1) {
         if (this.add((E)var4) && !var2) {
            var2 = true;
         }
      }

      return var2;
   }

   @Override
   public boolean add(E var1) {
      return this.map.put((E)var1, DUMMY) == null;
   }

   @Override
   public int size() {
      return this.map.size();
   }

   @Override
   public Iterator<E> iterator() {
      return this.map.keySet().iterator();
   }
}
