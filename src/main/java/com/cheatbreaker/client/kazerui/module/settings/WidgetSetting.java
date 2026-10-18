package com.cheatbreaker.client.kazerui.module.settings;

import java.util.Map;

public final class WidgetSetting extends com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<String> {
   private final com.cheatbreaker.client.kazerui.gui.components.MenuWidget widget;

   public WidgetSetting(String var1, String var2, com.cheatbreaker.client.kazerui.gui.components.MenuWidget var3) {
      super(var1, var2);
      this.widget = var3;
   }

   public com.cheatbreaker.client.kazerui.gui.components.MenuWidget b() {
      return this.widget;
   }

   public int c() {
      try {
         return Integer.parseInt(this.j().trim());
      } catch (NumberFormatException var2) {
         return Integer.parseInt(this.defaultValue);
      }
   }

   protected String a(String var1) {
      return var1;
   }

   @Override
   protected String a() {
      return "widget";
   }

   @Override
   protected void a(Map<String, Object> var1) {
      var1.put("widget", this.widget);
   }
}
