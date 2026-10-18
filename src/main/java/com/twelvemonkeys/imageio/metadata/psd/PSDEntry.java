package com.twelvemonkeys.imageio.metadata.psd;

import com.twelvemonkeys.imageio.metadata.AbstractEntry;
import com.twelvemonkeys.lang.StringUtil;
import java.lang.reflect.Field;

class PSDEntry extends AbstractEntry {
   private final String name;

   public PSDEntry(int var1, String var2, Object var3) {
      super(var1, var3);
      this.name = StringUtil.isEmpty(var2) ? null : var2;
   }

   @Override
   protected String getNativeIdentifier() {
      return String.format("0x%04x", (Integer)this.getIdentifier());
   }

   @Override
   public String getFieldName() {
      Class[] var1 = new Class[]{this.getPSDClass()};

      for (Class var5 : var1) {
         Field[] var6 = var5.getDeclaredFields();

         for (Field var10 : var6) {
            try {
               if (var10.getType() == int.class && var10.getName().startsWith("RES_")) {
                  var10.setAccessible(true);
                  if (var10.get(null).equals(this.getIdentifier())) {
                     String var11 = StringUtil.lispToCamel(var10.getName().substring(4).replace("_", "-").toLowerCase(), true);
                     return this.name != null ? var11 + ": " + this.name : var11;
                  }
               }
            } catch (IllegalAccessException var12) {
               break;
            }
         }
      }

      return this.name;
   }

   private Class<?> getPSDClass() {
      try {
         return Class.forName("com.twelvemonkeys.imageio.plugins.psd.PSD");
      } catch (ClassNotFoundException var2) {
         return PSD.class;
      }
   }
}
