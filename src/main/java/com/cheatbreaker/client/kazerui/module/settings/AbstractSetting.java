package com.cheatbreaker.client.kazerui.module.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public abstract class AbstractSetting<T> {
   public final String key;
   protected final T defaultValue;
   com.cheatbreaker.client.kazerui.module.KazerModule owner;
   BooleanSupplier hidden;
   boolean noWidget;
   private long cachedRevision = Long.MIN_VALUE;
   private Object cachedModel;
   private T cachedValue;
   private final List<Runnable> listeners = new ArrayList<>();
   private boolean notifying;
   Supplier<String> label;

   protected AbstractSetting(String var1, T var2) {
      this.key = var1;
      this.defaultValue = (T)var2;
   }

   public void a(com.cheatbreaker.client.kazerui.module.KazerModule var1) {
      if (this.owner != null && this.owner != var1) {
         throw new IllegalStateException(this.key + " already belongs to " + this.owner.id);
      } else {
         this.owner = var1;
      }
   }

   public com.cheatbreaker.client.kazerui.module.KazerModule h() {
      return this.owner;
   }

   public T i() {
      return this.defaultValue;
   }

   public T j() {
      com.cheatbreaker.client.kazerui.gui.components.MenuModel var1 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.d();
      if (var1 != null && this.cachedModel == var1 && this.cachedRevision == var1.revision) {
         return this.cachedValue;
      } else {
         Object var2 = this.b();
         if (var1 != null) {
            this.cachedModel = var1;
            this.cachedRevision = var1.revision;
            this.cachedValue = (T)var2;
         }

         return (T)var2;
      }
   }

   private T b() {
      String var1 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.a(this.owner.b(), this.key, null);
      if (var1 == null) {
         return this.defaultValue;
      } else {
         try {
            Object var2 = this.a(var1);
            return (T)(var2 == null ? this.defaultValue : var2);
         } catch (RuntimeException var3) {
            return this.defaultValue;
         }
      }
   }

   public void b(T var1) {
      com.cheatbreaker.client.kazerui.module.ModuleRegistry.b(this.owner.b(), this.key, this.c((T)var1));
   }

   public void k() {
      com.cheatbreaker.client.kazerui.module.ModuleRegistry.a(this.owner.b(), this.key);
   }

   public <S extends com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<T>> S a(Runnable var1) {
      this.listeners.add(var1);
      return (S)this;
   }

   public void l() {
      this.cachedRevision = Long.MIN_VALUE;
      if (!this.notifying) {
         this.notifying = true;

         try {
            for (Runnable var2 : this.listeners) {
               var2.run();
            }
         } finally {
            this.notifying = false;
         }
      }
   }

   public boolean m() {
      return this.j().equals(this.defaultValue);
   }

   public <S extends com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<T>> S n() {
      this.noWidget = true;
      return (S)this;
   }

   public <S extends com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<T>> S a(Supplier<String> var1) {
      this.label = var1;
      return (S)this;
   }

   public <S extends com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<T>> S d(String var1) {
      return this.a(() -> com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("settings", var1));
   }

   public <S extends com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<T>> S a(BooleanSupplier var1) {
      this.hidden = var1;
      return (S)this;
   }

   protected abstract T a(String var1);

   protected String c(T var1) {
      return String.valueOf(var1);
   }

   protected abstract String a();

   protected void a(Map<String, Object> var1) {
   }

   public com.cheatbreaker.client.kazerui.gui.components.OptionTree.a a(List<com.cheatbreaker.client.kazerui.gui.components.OptionTree.a> var1, final BooleanSupplier var2) {
      HashMap var3 = new HashMap();
      var3.put("type", "option");
      var3.put("key", this.key);
      var3.put("kind", this.a());
      var3.put("default", this.c(this.defaultValue));
      if (this.noWidget) {
         var3.put("transient", Boolean.TRUE);
      }

      if (this.label != null) {
         var3.put("text", this.label.get());
      }

      this.a(var3);
      final BooleanSupplier var4 = this.hidden;
      BooleanSupplier var5 = var4 == null ? var2 : (var2 == null ? var4 : new BooleanSupplier() {
         @Override
         public boolean getAsBoolean() {
            return var4.getAsBoolean() || var2.getAsBoolean();
         }
      });
      return com.cheatbreaker.client.kazerui.gui.components.OptionTree.a.a(var3, var1, var5);
   }
}
