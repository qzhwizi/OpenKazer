package com.cheatbreaker.client.kazerui.module.settings;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public final class KeybindSetting extends com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<String> {
   private final boolean combo;
   private boolean wasDown;

   public KeybindSetting(String var1, String var2, boolean var3) {
      super(var1, var2);
      this.combo = var3;
   }

   public KeybindSetting(String var1) {
      this(var1, "NONE", false);
   }

   public boolean b() {
      int var1 = this.c();
      if (var1 == 0) {
         return false;
      } else {
         return var1 < 0 ? Mouse.isButtonDown(var1 + 100) : Keyboard.isKeyDown(var1);
      }
   }

   public boolean a(boolean var1) {
      boolean var2 = this.b();
      boolean var3 = var2 && !this.wasDown && var1;
      this.wasDown = var2;
      return var3;
   }

   public int c() {
      return e(this.j());
   }

   public static int e(String var0) {
      if (var0 == null || var0.isEmpty() || var0.equals("NONE")) {
         return 0;
      } else if (var0.startsWith("MOUSE")) {
         try {
            return Integer.parseInt(var0.substring(5)) - 1 - 100;
         } catch (NumberFormatException var2) {
            return 0;
         }
      } else {
         return Keyboard.getKeyIndex(var0);
      }
   }

   protected String a(String var1) {
      return var1.trim();
   }

   @Override
   protected String a() {
      return this.combo ? "keycombo" : "key";
   }
}
