package com.cheatbreaker.client.kazerui.gui.components;

public final class HudStyleKeys {
   public static final String SCALE = "scale";
   public static final String TEXT_SHADOW = "textShadow";
   public static final String BRACKETS = "brackets";
   public static final String BRACKET_COLOR = "bracketColor";
   public static final String BACKGROUND = "background";
   public static final String STATIC_WIDTH = "staticBackgroundWidth";
   public static final String STATIC_HEIGHT = "staticBackgroundHeight";
   public static final String BACKGROUND_WIDTH = "backgroundWidth";
   public static final String BACKGROUND_HEIGHT = "backgroundHeight";
   public static final String BACKGROUND_COLOR = "backgroundColor";
   public static final String BORDER = "border";
   public static final String BORDER_THICKNESS = "borderThickness";
   public static final String BORDER_COLOR = "borderColor";
   public static final String TEXT_COLOR = "textColor";

   public static String a(String var0, String var1) {
      com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var2 = com.cheatbreaker.client.kazerui.gui.components.OptionTree.a(var0, var1);
      return var2 != null && !var2.value.isEmpty() ? var2.value : null;
   }

   public static float a(String var0, String var1, float var2) {
      String var3 = a(var0, var1);

      try {
         return var3 == null ? var2 : Float.parseFloat(var3);
      } catch (NumberFormatException var5) {
         return var2;
      }
   }

   private HudStyleKeys() {
   }
}
