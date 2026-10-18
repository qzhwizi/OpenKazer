package com.cheatbreaker.client.kazerui.module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public final class OptionTreeBuilder {
   final List<com.cheatbreaker.client.kazerui.module.OptionTreeBuilder.b> entries = new ArrayList<>();
   final List<com.cheatbreaker.client.kazerui.module.KazerModule> rows = new ArrayList<>();
   final List<com.cheatbreaker.client.kazerui.module.KazerModule> lastRows = new ArrayList<>();

   public com.cheatbreaker.client.kazerui.module.OptionTreeBuilder a(String var1, Consumer<com.cheatbreaker.client.kazerui.module.OptionTreeBuilder> var2) {
      com.cheatbreaker.client.kazerui.module.OptionTreeBuilder.b var3 = null;

      for (com.cheatbreaker.client.kazerui.module.OptionTreeBuilder.b var5 : this.entries) {
         if (var1.equals(var5.section)) {
            var3 = var5;
         }
      }

      if (var3 == null) {
         var3 = new com.cheatbreaker.client.kazerui.module.OptionTreeBuilder.b(null, var1);
         this.entries.add(var3);
      }

      var2.accept(var3.children);
      return this;
   }

   public com.cheatbreaker.client.kazerui.module.OptionTreeBuilder.a a(com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<?>[] var1) {
      ArrayList var2 = new ArrayList();

      for (com.cheatbreaker.client.kazerui.module.settings.AbstractSetting var6 : var1) {
         com.cheatbreaker.client.kazerui.module.OptionTreeBuilder.b var7 = new com.cheatbreaker.client.kazerui.module.OptionTreeBuilder.b(var6, null);
         this.entries.add(var7);
         var2.add(var7);
      }

      return new com.cheatbreaker.client.kazerui.module.OptionTreeBuilder.a(var2);
   }

   public com.cheatbreaker.client.kazerui.module.OptionTreeBuilder.b a(com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<?> var1) {
      com.cheatbreaker.client.kazerui.module.OptionTreeBuilder.b var2 = new com.cheatbreaker.client.kazerui.module.OptionTreeBuilder.b(var1, null);
      this.entries.add(0, var2);
      return var2;
   }

   public com.cheatbreaker.client.kazerui.module.OptionTreeBuilder.b a(
      com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<?> var1, Consumer<com.cheatbreaker.client.kazerui.module.OptionTreeBuilder> var2
   ) {
      com.cheatbreaker.client.kazerui.module.OptionTreeBuilder.b var3 = new com.cheatbreaker.client.kazerui.module.OptionTreeBuilder.b(var1, null);
      var2.accept(var3.children);
      this.entries.add(var3);
      return var3;
   }

   public com.cheatbreaker.client.kazerui.module.OptionTreeBuilder.a a(
      com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<?> var1, com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<?>... var2
   ) {
      com.cheatbreaker.client.kazerui.module.OptionTreeBuilder.b var3 = this.b(var1);
      return var3 == null ? null : var3.children.a(var2);
   }

   private com.cheatbreaker.client.kazerui.module.OptionTreeBuilder.b b(com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<?> var1) {
      for (com.cheatbreaker.client.kazerui.module.OptionTreeBuilder.b var3 : this.entries) {
         if (var3.setting == var1) {
            return var3;
         }

         com.cheatbreaker.client.kazerui.module.OptionTreeBuilder.b var4 = var3.children.b(var1);
         if (var4 != null) {
            return var4;
         }
      }

      return null;
   }

   List<com.cheatbreaker.client.kazerui.gui.components.OptionTree.a> a() {
      ArrayList var1 = new ArrayList();

      for (com.cheatbreaker.client.kazerui.module.KazerModule var3 : this.rows) {
         var1.add(var3.l());
      }

      for (com.cheatbreaker.client.kazerui.module.OptionTreeBuilder.b var8 : this.entries) {
         List var4 = var8.children.a();
         if (var8.section != null) {
            if (!var4.isEmpty()) {
               HashMap var5 = new HashMap();
               var5.put("type", "section");
               var5.put("key", var8.section);
               var1.add(com.cheatbreaker.client.kazerui.gui.components.OptionTree.a.a(var5, var4, var8.hide));
            }
         } else {
            var1.add(var8.setting.a(var4, var8.hide));
         }
      }

      for (com.cheatbreaker.client.kazerui.module.KazerModule var9 : this.lastRows) {
         var1.add(var9.l());
      }

      return Collections.unmodifiableList(var1);
   }

   com.cheatbreaker.client.kazerui.module.OptionTreeBuilder a(String var1) {
      for (com.cheatbreaker.client.kazerui.module.OptionTreeBuilder.b var3 : this.entries) {
         if (var1.equals(var3.section)) {
            return var3.children;
         }
      }

      com.cheatbreaker.client.kazerui.module.OptionTreeBuilder.b var4 = new com.cheatbreaker.client.kazerui.module.OptionTreeBuilder.b(null, var1);
      this.entries.add(var4);
      return var4.children;
   }

   public static final class a {
      private final List<com.cheatbreaker.client.kazerui.module.OptionTreeBuilder.b> entries;

      private a(List<com.cheatbreaker.client.kazerui.module.OptionTreeBuilder.b> var1) {
         this.entries = var1;
      }

      public com.cheatbreaker.client.kazerui.module.OptionTreeBuilder.a a(BooleanSupplier var1) {
         for (com.cheatbreaker.client.kazerui.module.OptionTreeBuilder.b var3 : this.entries) {
            var3.a(var1);
         }

         return this;
      }
   }

   public static final class b {
      final com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<?> setting;
      final String section;
      final com.cheatbreaker.client.kazerui.module.OptionTreeBuilder children = new com.cheatbreaker.client.kazerui.module.OptionTreeBuilder();
      BooleanSupplier hide;

      private b(com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<?> var1, String var2) {
         this.setting = var1;
         this.section = var2;
      }

      public com.cheatbreaker.client.kazerui.module.OptionTreeBuilder.b a(final BooleanSupplier var1) {
         final BooleanSupplier var2 = this.hide;
         this.hide = var2 == null ? var1 : new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
               return var2.getAsBoolean() || var1.getAsBoolean();
            }
         };
         return this;
      }
   }
}
