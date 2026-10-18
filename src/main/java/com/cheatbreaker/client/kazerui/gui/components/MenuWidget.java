package com.cheatbreaker.client.kazerui.gui.components;

public interface MenuWidget {
   float a(float var1);

   void a(com.cheatbreaker.client.kazerui.gui.components.MenuCanvas var1, float var2, float var3, float var4, float var5, float var6, float var7);

   void a(float var1, float var2, int var3);

   default boolean a(char var1, int var2) {
      return false;
   }

   default boolean a() {
      return false;
   }
}
