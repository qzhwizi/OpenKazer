package com.cheatbreaker.client.module.modules.options;

import java.util.List;

public class DamageColorThreshold implements Comparable<com.cheatbreaker.client.module.modules.options.DamageColorThreshold> {
   public int percent;
   public String colorCode;

   public DamageColorThreshold(int var1, String var2) {
      this.percent = var1;
      this.colorCode = var2;
   }

   @Override
   public String toString() {
      return this.percent + ", " + this.colorCode;
   }

   public int a(com.cheatbreaker.client.module.modules.options.DamageColorThreshold var1) {
      return Integer.compare(this.percent, var1.percent);
   }

   public static String a(List<com.cheatbreaker.client.module.modules.options.DamageColorThreshold> var0, int var1) {
      for (com.cheatbreaker.client.module.modules.options.DamageColorThreshold var3 : var0) {
         if (var1 <= var3.percent) {
            return var3.colorCode;
         }
      }

      return "f";
   }

   public int b(com.cheatbreaker.client.module.modules.options.DamageColorThreshold var1) {
      return this.a(var1);
   }

   @Override
   public int compareTo(com.cheatbreaker.client.module.modules.options.DamageColorThreshold var1) {
      return this.a(var1);
   }
}
