package com.cheatbreaker.client.kazerui.module;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class OutgoingChatEvent {
   private static final List<Consumer<com.cheatbreaker.client.kazerui.module.OutgoingChatEvent>> LISTENERS = new ArrayList<>();
   public static Consumer<String> sent;
   private String message;
   public boolean cancelled;

   private OutgoingChatEvent(String var1) {
      this.message = var1;
   }

   public String a() {
      return this.message;
   }

   public void a(String var1) {
      this.message = var1;
   }

   public static void a(Consumer<com.cheatbreaker.client.kazerui.module.OutgoingChatEvent> var0) {
      LISTENERS.add(var0);
   }

   public static String b(String var0) {
      com.cheatbreaker.client.kazerui.module.OutgoingChatEvent var1 = new com.cheatbreaker.client.kazerui.module.OutgoingChatEvent(var0);

      for (Consumer var3 : LISTENERS) {
         var3.accept(var1);
      }

      if (var1.cancelled) {
         return null;
      } else {
         if (sent != null) {
            sent.accept(var1.message);
         }

         return var1.message;
      }
   }
}
