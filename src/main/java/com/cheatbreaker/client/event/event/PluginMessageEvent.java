package com.cheatbreaker.client.event.event;

public class PluginMessageEvent extends com.cheatbreaker.client.event.EventBus.aInner {
   private final String channel;
   private final byte[] payload;

   public PluginMessageEvent(String var1, byte[] var2) {
      this.channel = var1;
      this.payload = var2;
   }

   public byte[] a() {
      return this.payload;
   }

   public String b() {
      return this.channel;
   }
}
