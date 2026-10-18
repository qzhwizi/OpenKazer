package com.cheatbreaker.client.kazerui.module;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.util.IChatComponent;

public final class ChatMessageEvent {
   private static final List<Consumer<com.cheatbreaker.client.kazerui.module.ChatMessageEvent>> LISTENERS = new ArrayList<>();
   public final IChatComponent original;
   public final int id;
   public final String legacy;
   public final String plain;
   private IChatComponent component;
   public boolean cancelled;
   public boolean changed;
   public boolean filtered;
   public boolean removeLast;

   public ChatMessageEvent(IChatComponent var1, int var2) {
      this.original = var1;
      this.component = var1;
      this.id = var2;
      this.legacy = var1.getUnformattedText();
      this.plain = com.cheatbreaker.client.kazerui.module.HypixelUtils.a(this.legacy);
   }

   public IChatComponent a() {
      return this.component;
   }

   public void a(IChatComponent var1) {
      this.changed = true;
      this.component = var1;
   }

   public static void a(Consumer<com.cheatbreaker.client.kazerui.module.ChatMessageEvent> var0) {
      LISTENERS.add(var0);
   }

   public static com.cheatbreaker.client.kazerui.module.ChatMessageEvent a(IChatComponent var0, int var1) {
      com.cheatbreaker.client.kazerui.module.ChatMessageEvent var2 = new com.cheatbreaker.client.kazerui.module.ChatMessageEvent(var0, var1);

      for (Consumer var4 : LISTENERS) {
         var4.accept(var2);
      }

      return var2;
   }
}
