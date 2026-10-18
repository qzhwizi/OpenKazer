package com.cheatbreaker.client.event;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EventBus {
   private static final Logger LOGGER = LogManager.getLogger();
   private final ConcurrentHashMap<Class<? extends com.cheatbreaker.client.event.EventBus.aInner>, CopyOnWriteArrayList<Consumer>> map = new ConcurrentHashMap<>();

   public <T extends com.cheatbreaker.client.event.EventBus.aInner> boolean a(Class<T> var1, Consumer<T> var2) {
      return this.map.computeIfAbsent(var1, var0 -> new CopyOnWriteArrayList<>()).add(var2);
   }

   public <T extends com.cheatbreaker.client.event.EventBus.aInner> boolean b(Class<T> var1, Consumer<T> var2) {
      CopyOnWriteArrayList var3 = this.map.get(var1);
      return var3 != null && var3.remove(var2);
   }

   public void a(com.cheatbreaker.client.event.EventBus.aInner var1) {
      for (Class var2 = var1.getClass(); var2 != null && var2 != com.cheatbreaker.client.event.EventBus.aInner.class; var2 = var2.getSuperclass()) {
         CopyOnWriteArrayList var3 = this.map.get(var2);
         if (var3 != null) {
            for (Consumer var5 : (Iterable<Consumer>) var3) {
               try {
                  var5.accept(var1);
               } catch (Exception var7) {
                  LOGGER.error("EventBus consumer failed on " + var1.getClass().getSimpleName() + " (handler " + var5.getClass().getName() + ")", var7);
               }
            }
         }
      }
   }

   public int a(Class<? extends com.cheatbreaker.client.event.EventBus.aInner> var1) {
      CopyOnWriteArrayList var2 = this.map.get(var1);
      return var2 == null ? 0 : var2.size();
   }

   public abstract static class aInner {
   }
}
