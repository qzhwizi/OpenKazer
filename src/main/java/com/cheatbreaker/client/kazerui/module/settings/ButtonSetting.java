package com.cheatbreaker.client.kazerui.module.settings;

import java.util.Map;

public final class ButtonSetting extends com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<String> {
   private final Runnable action;
   private float width = 70.0F;

   public ButtonSetting(String var1, Runnable var2) {
      super(var1, "");
      this.action = var2;
   }

   public com.cheatbreaker.client.kazerui.module.settings.ButtonSetting a(float var1) {
      this.width = var1;
      return this;
   }

   public void b() {
      this.action.run();
   }

   protected String a(String var1) {
      return var1;
   }

   @Override
   protected String a() {
      return "button";
   }

   @Override
   protected void a(Map<String, Object> var1) {
      var1.put("width", this.width);
   }
}
