package com.cheatbreaker.client.event.event;

public class MouseClickEvent extends com.cheatbreaker.client.event.EventBus.aInner {
   private final int mouseButton;

   public MouseClickEvent(int var1) {
      this.mouseButton = var1;
   }

   public int a() {
      return this.mouseButton;
   }
}
