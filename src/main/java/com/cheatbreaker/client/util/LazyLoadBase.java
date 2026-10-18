package com.cheatbreaker.client.util;

public abstract class LazyLoadBase<T> {
   protected T cache;
   protected Thread thread;

   public T a() {
      if (this.cache == null) {
         (this.thread = new Thread(this::b, "LazyLoadBase")).start();
      }

      return this.cache;
   }

   protected abstract void b();

   public void c() {
      synchronized (this.thread) {
         this.cache = null;
      }
   }
}
