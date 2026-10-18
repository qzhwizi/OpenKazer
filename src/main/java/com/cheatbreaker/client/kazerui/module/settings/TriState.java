package com.cheatbreaker.client.kazerui.module.settings;

public enum TriState implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
   FALSE("false"),
   DEFAULT("minecraftDefault"),
   TRUE("true");

   private final String lang;

   private TriState(String var3) {
      this.lang = var3;
   }

   @Override
   public String a() {
      return this.lang;
   }

   public boolean a(boolean var1) {
      return this == DEFAULT ? var1 : this == TRUE;
   }
}
