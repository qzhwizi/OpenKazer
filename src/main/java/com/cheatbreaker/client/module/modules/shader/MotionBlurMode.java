package com.cheatbreaker.client.module.modules.shader;

public enum MotionBlurMode {
   PHOSPHOR("Phosphor"),
   MOULBERRY("Moulberry"),
   MONKEY("Monkey"),
   OLD("Old");

   public static final MotionBlurMode DEFAULT = PHOSPHOR;
   private final String label;

   private MotionBlurMode(String var3) {
      this.label = var3;
   }

   public String a() {
      return this.label;
   }

   public static String[] b() {
      MotionBlurMode[] var0 = values();
      String[] var1 = new String[var0.length];

      for (int var2 = 0; var2 < var0.length; var2++) {
         var1[var2] = var0[var2].label;
      }

      return var1;
   }

   public static MotionBlurMode a(String var0) {
      if (var0 != null) {
         for (MotionBlurMode var4 : values()) {
            if (var4.label.equals(var0)) {
               return var4;
            }
         }
      }

      return DEFAULT;
   }
}
