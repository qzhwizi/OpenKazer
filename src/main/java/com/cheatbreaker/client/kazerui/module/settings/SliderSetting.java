package com.cheatbreaker.client.kazerui.module.settings;

import java.util.Map;

public final class SliderSetting extends com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<Float> {
   public final float min;
   public final float max;
   public final boolean integer;
   private int roundTo;

   private SliderSetting(String var1, float var2, float var3, float var4, boolean var5) {
      super(var1, var2);
      this.min = var3;
      this.max = var4;
      this.integer = var5;
   }

   public static com.cheatbreaker.client.kazerui.module.settings.SliderSetting a(String var0, int var1, int var2, int var3) {
      return new com.cheatbreaker.client.kazerui.module.settings.SliderSetting(var0, var1, var2, var3, true);
   }

   public static com.cheatbreaker.client.kazerui.module.settings.SliderSetting a(String var0, float var1, float var2, float var3) {
      return new com.cheatbreaker.client.kazerui.module.settings.SliderSetting(var0, var1, var2, var3, false);
   }

   public com.cheatbreaker.client.kazerui.module.settings.SliderSetting a(int var1) {
      this.roundTo = var1;
      return this;
   }

   public float b() {
      return Math.max(this.min, Math.min(this.max, this.j()));
   }

   public int c() {
      return Math.round(this.b());
   }

   protected Float a(String var1) {
      float var2 = Float.parseFloat(var1.trim());
      return !Float.isNaN(var2) && !Float.isInfinite(var2) ? var2 : null;
   }

   protected String c(Float var1) {
      return this.integer ? String.valueOf(Math.round(var1)) : String.valueOf(var1);
   }

   @Override
   protected String a() {
      return "number";
   }

   @Override
   protected void a(Map<String, Object> var1) {
      var1.put("min", this.min);
      var1.put("max", this.max);
      var1.put("integer", this.integer);
      if (this.roundTo != 0) {
         var1.put("roundTo", this.roundTo);
      }
   }
}
