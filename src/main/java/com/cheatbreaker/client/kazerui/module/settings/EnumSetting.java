package com.cheatbreaker.client.kazerui.module.settings;

import java.util.ArrayList;
import java.util.Map;

public final class EnumSetting<E extends Enum<E> & com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a> extends com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<E> {
   private final Class<E> type;

   public EnumSetting(String var1, E var2) {
      super(var1, (E)var2);
      this.type = var2.getDeclaringClass();
   }

   public boolean a(E var1) {
      return this.j() == var1;
   }

   protected E a(String var1) {
      for (Enum var5 : (Enum[])this.type.getEnumConstants()) {
         if (var5.name().equalsIgnoreCase(var1.trim())) {
            return (E)var5;
         }
      }

      return null;
   }

   protected String c(E var1) {
      return var1.name();
   }

   @Override
   protected String a() {
      return "enum";
   }

   @Override
   protected void a(Map<String, Object> var1) {
      ArrayList var2 = new ArrayList();

      for (Enum var6 : (Enum[])this.type.getEnumConstants()) {
         ArrayList var7 = new ArrayList();
         var7.add(var6.name());
         var7.add(((com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a)var6).a());
         var2.add(var7);
      }

      var1.put("values", var2);
   }

   public interface a {
      String a();
   }
}
