package com.twelvemonkeys.util.service;

import com.twelvemonkeys.lang.Validate;
import com.twelvemonkeys.util.FilterIterator;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.spi.CharsetProvider;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.spi.ImageReaderWriterSpi;
import javax.imageio.spi.ImageWriterSpi;

public class ServiceRegistry {
   public static final String SERVICES = "META-INF/services/";
   private final Map<Class<?>, ServiceRegistry.CategoryRegistry> categoryMap;

   public ServiceRegistry(Iterator<? extends Class<?>> var1) {
      Validate.notNull(var1, "categories");
      LinkedHashMap var2 = new LinkedHashMap();

      while (var1.hasNext()) {
         this.putCategory(var2, (Class)var1.next());
      }

      this.categoryMap = Collections.unmodifiableMap(var2);
   }

   private <T> void putCategory(Map<Class<?>, ServiceRegistry.CategoryRegistry> var1, Class<T> var2) {
      ServiceRegistry.CategoryRegistry var3 = new ServiceRegistry.CategoryRegistry(var2);
      var1.put(var2, var3);
   }

   public void registerApplicationClasspathSPIs() {
      ClassLoader var1 = Thread.currentThread().getContextClassLoader();
      Iterator var2 = this.categories();

      while (var2.hasNext()) {
         Class var3 = (Class)var2.next();

         try {
            String var4 = "META-INF/services/" + var3.getName();
            Enumeration var5 = var1.getResources(var4);

            while (var5.hasMoreElements()) {
               URL var6 = (URL)var5.nextElement();
               this.registerSPIs(var6, var3, var1);
            }
         } catch (IOException var7) {
            throw new ServiceConfigurationError(var7);
         }
      }
   }

   <T> void registerSPIs(URL var1, Class<T> var2, ClassLoader var3) {
      Properties var4 = new Properties();

      try {
         var4.load(var1.openStream());
      } catch (IOException var16) {
         throw new ServiceConfigurationError(var16);
      }

      if (!var4.isEmpty()) {
         ServiceRegistry.CategoryRegistry var5 = this.categoryMap.get(var2);

         for (Object var8 : var4.keySet()) {
            String var9 = (String)var8;

            try {
               Class var10 = Class.forName(var9, true, var3);
               Object var11 = var10.newInstance();
               var5.register((T)var11);
            } catch (ClassNotFoundException var12) {
               throw new ServiceConfigurationError(var12);
            } catch (IllegalAccessException var13) {
               throw new ServiceConfigurationError(var13);
            } catch (InstantiationException var14) {
               throw new ServiceConfigurationError(var14);
            } catch (IllegalArgumentException var15) {
               throw new ServiceConfigurationError(var15);
            }
         }
      }
   }

   protected <T> Iterator<T> providers(Class<T> var1) {
      return this.<T>getRegistry(var1).providers();
   }

   protected Iterator<Class<?>> categories() {
      return this.categoryMap.keySet().iterator();
   }

   protected Iterator<Class<?>> compatibleCategories(final Object var1) {
      return new FilterIterator<>(this.categories(), new FilterIterator.Filter<Class<?>>() {
         public boolean accept(Class<?> var1x) {
            return var1x.isInstance(var1);
         }
      });
   }

   protected Iterator<Class<?>> containingCategories(final Object var1) {
      return new FilterIterator<Class<?>>(this.categories(), new FilterIterator.Filter<Class<?>>() {
         public boolean accept(Class<?> var1x) {
            return ServiceRegistry.this.getRegistry(var1x).contains(var1);
         }
      }) {
         Class<?> current;

         public Class next() {
            return this.current = (Class<?>)super.next();
         }

         @Override
         public void remove() {
            if (this.current == null) {
               throw new IllegalStateException("No current element");
            } else {
               ServiceRegistry.this.getRegistry(this.current).deregister(var1);
               this.current = null;
            }
         }
      };
   }

   private <T> ServiceRegistry.CategoryRegistry<T> getRegistry(Class<T> var1) {
      ServiceRegistry.CategoryRegistry var2 = this.categoryMap.get(var1);
      if (var2 == null) {
         throw new IllegalArgumentException("No such category: " + var1.getName());
      } else {
         return var2;
      }
   }

   public boolean register(Object var1) {
      Iterator var2 = this.compatibleCategories(var1);
      boolean var3 = false;

      while (var2.hasNext()) {
         Class var4 = (Class)var2.next();
         if (this.registerImpl(var1, var4) && !var3) {
            var3 = true;
         }
      }

      return var3;
   }

   private <T> boolean registerImpl(Object var1, Class<T> var2) {
      return this.getRegistry(var2).register((T)var2.cast(var1));
   }

   public <T> boolean register(T var1, Class<? super T> var2) {
      return this.registerImpl(var1, var2);
   }

   public boolean deregister(Object var1) {
      Iterator var2 = this.containingCategories(var1);
      boolean var3 = false;

      while (var2.hasNext()) {
         Class var4 = (Class)var2.next();
         if (this.deregister(var1, var4) && !var3) {
            var3 = true;
         }
      }

      return var3;
   }

   public boolean deregister(Object var1, Class<?> var2) {
      return this.getRegistry(var2).deregister(var1);
   }

   public static void main(String[] var0) {
      abstract class Spi {
      }

      ServiceRegistry var1 = new ServiceRegistry(
         Arrays.asList(CharsetProvider.class, SelectorProvider.class, ImageReaderSpi.class, ImageWriterSpi.class, Spi.class).iterator()
      );
      var1.registerApplicationClasspathSPIs();

      class One extends Spi {
      }

      One var2 = new One();

      class Two extends Spi {
      }

      Two var3 = new Two();
      var1.register(var2, Spi.class);
      var1.register(var3, Spi.class);
      var1.deregister(var2);
      var1.deregister(var2, Spi.class);
      var1.deregister(var3, Spi.class);
      var1.deregister(var3);
      Iterator var4 = var1.categories();
      System.out.println("Categories: ");

      while (var4.hasNext()) {
         Class var5 = (Class)var4.next();
         System.out.println("  " + var5.getName() + ":");
         Iterator var6 = var1.providers(var5);
         Object var7 = null;

         while (var6.hasNext()) {
            var7 = var6.next();
            System.out.println("    " + var7);
            if (var7 instanceof ImageReaderWriterSpi) {
               System.out.println("    - " + ((ImageReaderWriterSpi)var7).getDescription(null));
            }

            if (var6.hasNext()) {
               var6.remove();
            }
         }

         if (var7 != null) {
            Iterator var8 = var1.containingCategories(var7);
            int var9 = 0;

            while (var8.hasNext()) {
               if (var5 == var8.next()) {
                  var8.remove();
                  var9++;
               }
            }

            if (var9 != 1) {
               System.err.println("Removed " + var7 + " from " + var9 + " categories");
            }
         }

         var6 = var1.providers(var5);
         if (!var6.hasNext()) {
            System.out.println("All providers successfully deregistered");
         }

         while (var6.hasNext()) {
            System.err.println("Not removed: " + var6.next());
         }
      }
   }

   class CategoryRegistry<T> {
      private final Class<T> category;
      private final Map<Class, T> providers = new LinkedHashMap<>();

      CategoryRegistry(Class<T> var2) {
         Validate.notNull(var2, "category");
         this.category = var2;
      }

      private void checkCategory(Object var1) {
         if (!this.category.isInstance(var1)) {
            throw new IllegalArgumentException(var1 + " not instance of category " + this.category.getName());
         }
      }

      public boolean register(T var1) {
         this.checkCategory(var1);
         if (!this.contains(var1)) {
            this.providers.put(var1.getClass(), (T)var1);
            this.processRegistration((T)var1);
            return true;
         } else {
            return false;
         }
      }

      void processRegistration(T var1) {
         if (var1 instanceof RegisterableService) {
            RegisterableService var2 = (RegisterableService)var1;
            var2.onRegistration(ServiceRegistry.this, this.category);
         }
      }

      public boolean deregister(Object var1) {
         this.checkCategory(var1);
         Object var2 = this.providers.remove(var1.getClass());
         if (var2 != null) {
            this.processDeregistration((T)var2);
            return true;
         } else {
            return false;
         }
      }

      void processDeregistration(T var1) {
         if (var1 instanceof RegisterableService) {
            RegisterableService var2 = (RegisterableService)var1;
            var2.onDeregistration(ServiceRegistry.this, this.category);
         }
      }

      public boolean contains(Object var1) {
         return this.providers.containsKey(var1 != null ? var1.getClass() : null);
      }

      public Iterator<T> providers() {
         final Iterator var1 = this.providers.values().iterator();
         return new Iterator<T>() {
            T current;

            @Override
            public boolean hasNext() {
               return var1.hasNext();
            }

            @Override
            public T next() {
               return this.current = (T)var1.next();
            }

            @Override
            public void remove() {
               var1.remove();
               CategoryRegistry.this.processDeregistration(this.current);
            }
         };
      }
   }
}
