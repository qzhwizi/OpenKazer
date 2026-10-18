package com.cheatbreaker.client.module.modules.config;

public final class CustomTextItem {
   public static final int DEFAULT_COLOR = -1;
   public static final int DEFAULT_BACKGROUND_COLOR = 1610612736;
   public static final float DEFAULT_SCALE = 1.0F;
   public static final String DEFAULT_ANCHOR = "MIDDLE_MIDDLE";
   public String id;
   public String content = "Text";
   public int color = -1;
   public boolean showBackground;
   public int backgroundColor = 1610612736;
   public float scale = 1.0F;
   public String anchor = "MIDDLE_MIDDLE";
   public float xTranslation;
   public float yTranslation;
   public boolean enabled = true;
   public boolean renderHud = true;

   public void a(String var1) {
      this.content = b(var1);
   }

   static String b(String var0) {
      return var0 != null && !var0.isEmpty() ? var0.replace("\r", "").replace("\n", "") : "Text";
   }

   @Override
   public String toString() {
      return this.id + "(" + this.content + ")";
   }
}
