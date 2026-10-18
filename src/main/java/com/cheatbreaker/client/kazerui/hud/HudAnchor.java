package com.cheatbreaker.client.kazerui.hud;

public enum HudAnchor {
   TOP_LEFT("topLeft", com.cheatbreaker.client.kazerui.hud.HudAnchor.a.START, com.cheatbreaker.client.kazerui.hud.HudAnchor.a.START),
   TOP_CENTER("topCenter", com.cheatbreaker.client.kazerui.hud.HudAnchor.a.MIDDLE, com.cheatbreaker.client.kazerui.hud.HudAnchor.a.START),
   TOP_RIGHT("topRight", com.cheatbreaker.client.kazerui.hud.HudAnchor.a.END, com.cheatbreaker.client.kazerui.hud.HudAnchor.a.START),
   MIDDLE_LEFT("middleLeft", com.cheatbreaker.client.kazerui.hud.HudAnchor.a.START, com.cheatbreaker.client.kazerui.hud.HudAnchor.a.MIDDLE),
   MIDDLE_CENTER("middleCenter", com.cheatbreaker.client.kazerui.hud.HudAnchor.a.MIDDLE, com.cheatbreaker.client.kazerui.hud.HudAnchor.a.MIDDLE),
   MIDDLE_RIGHT("middleRight", com.cheatbreaker.client.kazerui.hud.HudAnchor.a.END, com.cheatbreaker.client.kazerui.hud.HudAnchor.a.MIDDLE),
   BOTTOM_LEFT("bottomLeft", com.cheatbreaker.client.kazerui.hud.HudAnchor.a.START, com.cheatbreaker.client.kazerui.hud.HudAnchor.a.END),
   BOTTOM_CENTER_L("bottomCenterLeft", com.cheatbreaker.client.kazerui.hud.HudAnchor.a.END, com.cheatbreaker.client.kazerui.hud.HudAnchor.a.END),
   BOTTOM_CENTER_R("bottomCenterRight", com.cheatbreaker.client.kazerui.hud.HudAnchor.a.START, com.cheatbreaker.client.kazerui.hud.HudAnchor.a.END),
   BOTTOM_RIGHT("bottomRight", com.cheatbreaker.client.kazerui.hud.HudAnchor.a.END, com.cheatbreaker.client.kazerui.hud.HudAnchor.a.END);

   public static final float PAD = 2.0F;
   private static final float BOTTOM_CENTER_REACH = 30.0F;
   private static final com.cheatbreaker.client.kazerui.hud.HudAnchor[] ALL = values();
   public final String id;
   public final com.cheatbreaker.client.kazerui.hud.HudAnchor.a horizontal;
   public final com.cheatbreaker.client.kazerui.hud.HudAnchor.a vertical;

   private HudAnchor(String var3, com.cheatbreaker.client.kazerui.hud.HudAnchor.a var4, com.cheatbreaker.client.kazerui.hud.HudAnchor.a var5) {
      this.id = var3;
      this.horizontal = var4;
      this.vertical = var5;
   }

   public static com.cheatbreaker.client.kazerui.hud.HudAnchor a(String var0) {
      for (com.cheatbreaker.client.kazerui.hud.HudAnchor var4 : ALL) {
         if (var4.id.equals(var0)) {
            return var4;
         }
      }

      return null;
   }

   public static com.cheatbreaker.client.kazerui.hud.HudAnchor a(float var0, float var1, float var2, float var3) {
      float var4 = var2 / 3.0F;
      float var5 = var3 / 3.0F;
      if (var1 < var5) {
         return var0 < var4 ? TOP_LEFT : (var0 > var4 * 2.0F ? TOP_RIGHT : TOP_CENTER);
      } else if (var1 < var5 * 2.0F) {
         return var0 < var4 ? MIDDLE_LEFT : (var0 > var4 * 2.0F ? MIDDLE_RIGHT : MIDDLE_CENTER);
      } else if (var0 < var4) {
         return BOTTOM_LEFT;
      } else if (var0 < var4 * 2.0F) {
         return var0 > var4 + var4 / 2.0F ? BOTTOM_CENTER_R : BOTTOM_CENTER_L;
      } else {
         return BOTTOM_RIGHT;
      }
   }

   public float a(float var1, float var2) {
      switch (this) {
         case TOP_CENTER:
         case MIDDLE_CENTER:
            return var1 / 2.0F - var2 / 2.0F;
         case BOTTOM_CENTER_L:
            return var1 / 2.0F - var2 + 30.0F;
         case BOTTOM_CENTER_R:
            return var1 / 2.0F - 30.0F;
         case TOP_RIGHT:
         case MIDDLE_RIGHT:
         case BOTTOM_RIGHT:
            return var1 - var2 - 2.0F;
         default:
            return 2.0F;
      }
   }

   public float b(float var1, float var2) {
      switch (this.vertical) {
         case MIDDLE:
            return var1 / 2.0F - var2 / 2.0F;
         case END:
            return var1 - var2 - 2.0F;
         default:
            return 2.0F;
      }
   }

   public static enum a {
      START,
      MIDDLE,
      END;
   }
}
