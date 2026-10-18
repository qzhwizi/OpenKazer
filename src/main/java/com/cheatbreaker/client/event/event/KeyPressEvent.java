package com.cheatbreaker.client.event.event;

public class KeyPressEvent extends com.cheatbreaker.client.event.EventBus.aInner {
   private final int keyboardKey;

   public KeyPressEvent(int var1) {
      this.keyboardKey = var1;
   }

   public int a() {
      return this.keyboardKey;
   }
}
