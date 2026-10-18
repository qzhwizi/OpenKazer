package com.cheatbreaker.client.kazerui.module.settings;

import java.util.Map;

public final class StringSetting extends com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<String> {
   private int maxLength;

   public StringSetting(String var1, String var2) {
      super(var1, var2);
   }

   public StringSetting(String var1) {
      this(var1, "");
   }

   public com.cheatbreaker.client.kazerui.module.settings.StringSetting a(int var1) {
      this.maxLength = var1;
      return this;
   }

   public boolean b() {
      return this.j().isEmpty();
   }

   protected String a(String var1) {
      return var1;
   }

   @Override
   protected String a() {
      return "string";
   }

   @Override
   protected void a(Map<String, Object> var1) {
      if (this.maxLength > 0) {
         var1.put("maxLength", this.maxLength);
      }
   }
}
