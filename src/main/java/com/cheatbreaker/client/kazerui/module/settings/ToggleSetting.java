package com.cheatbreaker.client.kazerui.module.settings;

public final class ToggleSetting extends com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<Boolean> {
   public ToggleSetting(String var1, boolean var2) {
      super(var1, var2);
   }

   public boolean b() {
      return this.j();
   }

   protected Boolean a(String var1) {
      return Boolean.parseBoolean(var1.trim());
   }

   @Override
   protected String a() {
      return "bool";
   }
}
