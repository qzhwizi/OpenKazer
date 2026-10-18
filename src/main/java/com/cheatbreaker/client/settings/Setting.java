package com.cheatbreaker.client.settings;

import java.awt.Color;
import java.util.List;
import java.util.function.Consumer;

public class Setting {
   private final String label;
   private String displayLabel;
   private Object value;
   private Number minimumValue;
   private Number maximumValue;
   private com.cheatbreaker.client.settings.Setting parent;
   private String[] acceptedValues;
   private Consumer<Object> valueConsumer;
   private com.cheatbreaker.client.module.Module container;
   private Float sliderMarker;
   public boolean rainbow;
   public int[] colorArray;

   public Setting(String var1) {
      if (var1.isEmpty()) {
         throw new IllegalStateException("Label is empty.");
      } else {
         this.label = var1;
      }
   }

   public Setting(com.cheatbreaker.client.module.Module var1, String var2) {
      this(var1.m(), var2);
      this.container = var1;
   }

   public Setting(List<com.cheatbreaker.client.settings.Setting> var1, String var2) {
      this(var2);
      var1.add(this);
   }

   public int a() {
      if (this.rainbow) {
         int var1 = (Integer)this.value >> 24 & 0xFF;
         float var2 = (float)System.nanoTime() / 1.0E10F % 1.0F;
         return var1 << 24 | Color.HSBtoRGB(var2, 1.0F, 1.0F) & 16777215;
      } else {
         return (Integer)this.value;
      }
   }

   public Number b() {
      return this.minimumValue;
   }

   public Number c() {
      return this.maximumValue;
   }

   public Object d() {
      return this.value;
   }

   public com.cheatbreaker.client.settings.Setting a(Object var1) {
      return this.a(var1, true);
   }

   public com.cheatbreaker.client.settings.Setting e() {
      return this.parent;
   }

   public com.cheatbreaker.client.settings.Setting a(com.cheatbreaker.client.settings.Setting var1) {
      if (var1.l() != com.cheatbreaker.client.settings.Setting.SettingType.BOOLEAN) {
         throw new IllegalStateException("Parent can only be boolean.");
      } else {
         this.parent = var1;
         return this;
      }
   }

   public String f() {
      return this.label;
   }

   public String g() {
      return this.displayLabel != null ? this.displayLabel : this.label;
   }

   public com.cheatbreaker.client.settings.Setting displayLabel(String var1) {
      this.displayLabel = var1;
      return this;
   }

   public String[] h() {
      return this.acceptedValues;
   }

   public boolean i() {
      return this.parent != null && (Boolean)this.parent.d();
   }

   public boolean j() {
      return this.parent == null || (Boolean)this.parent.d();
   }

   public com.cheatbreaker.client.settings.Setting a(Object var1, boolean var2) {
      com.cheatbreaker.client.CheatBreakerClient var3 = com.cheatbreaker.client.CheatBreakerClient.N();
      if (var3 != null && var3.activeProfile != null && !var3.activeProfile.b()) {
         if (var2) {
            var3.c();
         }
      } else if (this.container != null && this.value == null) {
         this.container.n().add(var1);
      }

      this.value = var1;
      if (this.valueConsumer != null) {
         this.valueConsumer.accept(var1);
      }

      return this;
   }

   public com.cheatbreaker.client.settings.Setting b(Object var1) {
      if (this.container != null) {
         List var2 = this.container.n();
         int var3 = this.container.m().indexOf(this);
         if (var3 >= 0 && var3 < var2.size()) {
            var2.set(var3, var1);
         }
      }

      this.value = var1;
      return this;
   }

   public com.cheatbreaker.client.settings.Setting a(String... var1) {
      this.acceptedValues = var1;
      return this;
   }

   public com.cheatbreaker.client.settings.Setting a(Consumer<Object> var1) {
      this.valueConsumer = var1;
      return this;
   }

   public com.cheatbreaker.client.settings.Setting a(Number var1, Number var2) {
      this.minimumValue = var1;
      this.maximumValue = var2;
      return this;
   }

   public com.cheatbreaker.client.settings.Setting sliderMarker(float var1) {
      this.sliderMarker = Math.max(0.0F, Math.min(1.0F, var1));
      return this;
   }

   public Float k() {
      return this.sliderMarker;
   }

   public com.cheatbreaker.client.settings.Setting.SettingType l() {
      if (this.value.getClass().isAssignableFrom(Boolean.class)) {
         return com.cheatbreaker.client.settings.Setting.SettingType.BOOLEAN;
      } else if (this.value.getClass().isAssignableFrom(String.class)) {
         return this.acceptedValues != null && this.acceptedValues.length != 0
            ? com.cheatbreaker.client.settings.Setting.SettingType.STRING_ARRAY
            : com.cheatbreaker.client.settings.Setting.SettingType.STRING;
      } else if (this.value.getClass().isAssignableFrom(Float.class)) {
         return com.cheatbreaker.client.settings.Setting.SettingType.FLOAT;
      } else if (this.value.getClass().isAssignableFrom(Double.class)) {
         return com.cheatbreaker.client.settings.Setting.SettingType.DOUBLE;
      } else if (this.value.getClass().isAssignableFrom(String[].class)) {
         return com.cheatbreaker.client.settings.Setting.SettingType.STRING_ARRAY;
      } else {
         return this.value.getClass().isAssignableFrom(Integer.class) ? com.cheatbreaker.client.settings.Setting.SettingType.INTEGER : null;
      }
   }

   public static enum SettingType {
      STRING,
      STRING_ARRAY,
      FLOAT,
      INTEGER,
      DOUBLE,
      BOOLEAN;
   }
}
