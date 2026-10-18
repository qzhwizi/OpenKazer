package com.twelvemonkeys.util;

import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

public final class BeanMap extends AbstractMap<String, Object> implements Serializable, Cloneable {
   private final Object bean;
   private transient Set<PropertyDescriptor> descriptors;

   public BeanMap(Object var1) throws IntrospectionException {
      if (var1 == null) {
         throw new IllegalArgumentException("bean == null");
      } else {
         this.bean = var1;
         this.descriptors = initDescriptors(var1);
      }
   }

   private static Set<PropertyDescriptor> initDescriptors(Object var0) throws IntrospectionException {
      HashSet var1 = new HashSet();
      PropertyDescriptor[] var2 = Introspector.getBeanInfo(var0.getClass()).getPropertyDescriptors();

      for (PropertyDescriptor var6 : var2) {
         if ((!"class".equals(var6.getName()) || var6.getPropertyType() != Class.class) && !(var6 instanceof IndexedPropertyDescriptor)) {
            var1.add(var6);
         }
      }

      return Collections.unmodifiableSet(var1);
   }

   @Override
   public Set<Entry<String, Object>> entrySet() {
      return new BeanMap.BeanSet();
   }

   @Override
   public Object get(Object var1) {
      return super.get(var1);
   }

   public Object put(String var1, Object var2) {
      this.checkKey(var1);

      for (Entry var4 : this.entrySet()) {
         if (((String)var4.getKey()).equals(var1)) {
            return var4.setValue(var2);
         }
      }

      return null;
   }

   @Override
   public Object remove(Object var1) {
      return super.remove(this.checkKey(var1));
   }

   @Override
   public int size() {
      return this.descriptors.size();
   }

   private String checkKey(Object var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("key == null");
      } else {
         String var2 = (String)var1;
         if (!this.containsKey(var2)) {
            throw new IllegalArgumentException("Bad key: " + var1);
         } else {
            return var2;
         }
      }
   }

   private Object readResolve() throws IntrospectionException {
      this.descriptors = initDescriptors(this.bean);
      return this;
   }

   private static Object unwrap(BeanMap.Wrapped var0) {
      try {
         return var0.run();
      } catch (IllegalAccessException var2) {
         throw new RuntimeException(var2);
      } catch (InvocationTargetException var3) {
         throw (RuntimeException)var3.getCause();
      }
   }

   private class BeanEntry implements Entry<String, Object> {
      private final PropertyDescriptor descriptor;

      public BeanEntry(PropertyDescriptor var2) {
         this.descriptor = var2;
      }

      public String getKey() {
         return this.descriptor.getName();
      }

      @Override
      public Object getValue() {
         return BeanMap.unwrap(new BeanMap.Wrapped() {
            @Override
            public Object run() throws IllegalAccessException, InvocationTargetException {
               Method var1 = BeanEntry.this.descriptor.getReadMethod();
               if (var1 == null) {
                  throw new UnsupportedOperationException("No getter: " + BeanEntry.this.descriptor.getName());
               } else {
                  return var1.invoke(BeanMap.this.bean);
               }
            }
         });
      }

      @Override
      public Object setValue(final Object var1) {
         return BeanMap.unwrap(new BeanMap.Wrapped() {
            @Override
            public Object run() throws IllegalAccessException, InvocationTargetException {
               Method var1x = BeanEntry.this.descriptor.getWriteMethod();
               if (var1x == null) {
                  throw new UnsupportedOperationException("No write method for property: " + BeanEntry.this.descriptor.getName());
               } else {
                  Object var2 = BeanEntry.this.getValue();
                  var1x.invoke(BeanMap.this.bean, var1);
                  return var2;
               }
            }
         });
      }

      @Override
      public boolean equals(Object var1) {
         if (!(var1 instanceof Entry)) {
            return false;
         } else {
            Entry var2 = (Entry)var1;
            String var3 = this.getKey();
            Object var4 = var2.getKey();
            if (var3 == var4 || var3 != null && var3.equals(var4)) {
               Object var5 = this.getValue();
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
         return (this.getKey() == null ? 0 : this.getKey().hashCode()) ^ (this.getValue() == null ? 0 : this.getValue().hashCode());
      }

      @Override
      public String toString() {
         return this.getKey() + "=" + this.getValue();
      }
   }

   private class BeanIterator implements Iterator<Entry<String, Object>> {
      private final Iterator<PropertyDescriptor> iterator;

      public BeanIterator(Iterator<PropertyDescriptor> var2) {
         this.iterator = var2;
      }

      @Override
      public boolean hasNext() {
         return this.iterator.hasNext();
      }

      public BeanMap.BeanEntry next() {
         return BeanMap.this.new BeanEntry(this.iterator.next());
      }

      @Override
      public void remove() {
         this.iterator.remove();
      }
   }

   private class BeanSet extends AbstractSet<Entry<String, Object>> {
      private BeanSet() {
      }

      @Override
      public Iterator<Entry<String, Object>> iterator() {
         return BeanMap.this.new BeanIterator(BeanMap.this.descriptors.iterator());
      }

      @Override
      public int size() {
         return BeanMap.this.descriptors.size();
      }
   }

   private interface Wrapped {
      Object run() throws IllegalAccessException, InvocationTargetException;
   }
}
