package com.cheatbreaker.client.module.module;

public abstract class KeybindModule extends com.cheatbreaker.client.module.Module {
   private final com.cheatbreaker.client.settings.Setting keybind = new com.cheatbreaker.client.settings.Setting(this, "Keybind").a(0);

   public com.cheatbreaker.client.settings.Setting a() {
      return this.keybind;
   }

   public KeybindModule(String var1) {
      super(var1);
   }

   public void z() {
      if (this.g()) {
         this.a(false);
      }

      this.e(false);
   }
}
