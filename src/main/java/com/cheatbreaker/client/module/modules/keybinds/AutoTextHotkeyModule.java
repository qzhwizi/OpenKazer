package com.cheatbreaker.client.module.modules.keybinds;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public final class AutoTextHotkeyModule extends com.cheatbreaker.client.module.Module {
   private final com.cheatbreaker.client.module.modules.keybinds.AutoTextState state = new com.cheatbreaker.client.module.modules.keybinds.AutoTextState();
   private final com.cheatbreaker.client.module.modules.keybinds.HotkeyTrigger trigger = new com.cheatbreaker.client.module.modules.keybinds.HotkeyTrigger();

   public AutoTextHotkeyModule() {
      super("Auto Text Hotkey");
      this.c(true);
      this.g(false);
      this.isEditable = false;
      this.a("Auto Text Hotkey", 1.0F);
      this.a(com.cheatbreaker.client.event.event.ClientTickEvent.class, this::a);
   }

   public com.cheatbreaker.client.module.modules.keybinds.AutoTextState a() {
      return this.state;
   }

   public com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry z() {
      return this.state.a();
   }

   public boolean a(String var1) {
      return this.state.a(var1);
   }

   public void a(String var1, String var2) {
      com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry var3 = this.state.b(var1);
      if (var3 != null) {
         var3.a(var2);
      }
   }

   public void a(String var1, boolean var2) {
      com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry var3 = this.state.b(var1);
      if (var3 != null) {
         var3.enabled = var2;
      }
   }

   public void b(String var1, boolean var2) {
      com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry var3 = this.state.b(var1);
      if (var3 != null) {
         var3.hidden = var2;
      }
   }

   public void a(String var1, List<Integer> var2) {
      com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry var3 = this.state.b(var1);
      if (var3 != null) {
         var3.keys.clear();
         var3.keys.addAll(var2);
      }
   }

   public void a(String var1, int var2) {
      this.state.a(var1, var2);
   }

   public String A() {
      return com.cheatbreaker.client.module.modules.keybinds.AutoTextSerializer.a(this.state);
   }

   public void b(String var1) {
      com.cheatbreaker.client.module.modules.keybinds.AutoTextState var2 = com.cheatbreaker.client.module.modules.keybinds.AutoTextSerializer.a(var1);
      this.state.items.clear();
      this.state.items.addAll(var2.items);
      this.state.a(var2.b());
   }

   public void B() {
      this.b("");
   }

   private void a(com.cheatbreaker.client.event.event.ClientTickEvent var1) {
      if (this.minecraft.currentScreen == null && this.minecraft.thePlayer != null) {
         this.a(var0 -> var0 >= 0 ? Keyboard.isKeyDown(var0) : Mouse.isButtonDown(var0 + 100), this.minecraft.thePlayer::sendChatMessage);
      }
   }

   public void a(com.cheatbreaker.client.module.modules.keybinds.AutoTextHotkeyModule.a var1, com.cheatbreaker.client.module.modules.keybinds.AutoTextHotkeyModule.b var2) {
      HashSet var3 = new HashSet();
      ArrayList var4 = new ArrayList();

      for (com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry var6 : this.state.items) {
         if (var6.enabled && !var6.keys.isEmpty()) {
            var4.add(var6);
            var3.addAll(var6.keys);
         }
      }

      if (!var4.isEmpty()) {
         HashSet var8 = new HashSet();

         for (Integer var7 : (Iterable<Integer>) var3) {
            if (var1.isDown(var7)) {
               var8.add(var7);
            }
         }

         for (com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry var11 : (Iterable<com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry>) this.trigger.a(var4, var8)) {
            var2.send(var11.content);
         }
      }
   }

   public interface a {
      boolean isDown(int var1);
   }

   public interface b {
      void send(String var1);
   }
}
