package com.cheatbreaker.client.kazerui.module.modules.mechanic;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

final class MessageStacker {
   private final Map<IChatComponent, com.cheatbreaker.client.kazerui.module.modules.mechanic.MessageStacker.a> stacks = new LinkedHashMap<>();
   private IChatComponent last;
   private int lastCount;

   void a() {
      this.last = null;
      this.lastCount = 0;
      this.stacks.clear();
   }

   void a(com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule var1, com.cheatbreaker.client.kazerui.module.ChatMessageEvent var2) {
      IChatComponent var3 = var2.a();
      int var4 = var2.id;
      long var5 = var1.timeBasedStackMessagesTimeframe.c() * 1000L;
      Iterator var7 = this.stacks.values().iterator();

      while (var7.hasNext()) {
         if (((com.cheatbreaker.client.kazerui.module.modules.mechanic.MessageStacker.a)var7.next()).a(var5)) {
            var7.remove();
         }
      }

      if (var1.chatStackIgnoreBlank.b() || var1.chatStackIgnoreBreak.b()) {
         String var9 = com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTextUtils.a(var3);
         if (var1.chatStackIgnoreBlank.b() && var9.isEmpty()) {
            return;
         }

         if (var1.chatStackIgnoreBreak.b() && a(var9)) {
            return;
         }
      }

      com.cheatbreaker.client.kazerui.module.modules.mechanic.MessageStacker.a var10 = this.stacks.get(var3);
      if (var3.equals(this.last)) {
         int var12;
         if (var10 != null) {
            this.lastCount = var12 = var10.a(var4);
         } else {
            var12 = ++this.lastCount;
         }

         var2.a(a(var3, var12));
         var2.removeLast = true;
      } else {
         this.last = var3;
         this.lastCount = 1;
         if (var1.stackMessagesTimeBased.b()) {
            if (var10 != null) {
               com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatHooks.c(var10.id);
               int var8 = var10.a(var4) + (this.lastCount - 1);
               var2.a(a(var3, var8));
               this.stacks.put(var3, var10);
            } else {
               this.stacks.put(var3, new com.cheatbreaker.client.kazerui.module.modules.mechanic.MessageStacker.a(var4));
               if (this.stacks.size() > 1000) {
                  Iterator var11 = this.stacks.keySet().iterator();
                  var11.next();
                  var11.remove();
               }
            }
         }
      }
   }

   private static IChatComponent a(IChatComponent var0, int var1) {
      ChatComponentText var2 = new ChatComponentText(" [x" + var1 + "]");
      var2.getChatStyle().setColor(EnumChatFormatting.GRAY);
      return var0.createCopy().appendSibling(var2);
   }

   private static boolean a(String var0) {
      char var1 = ' ';

      for (char var5 : var0.toCharArray()) {
         if (var1 == ' ') {
            if (var5 != '-' && var5 != 9644) {
               return false;
            }

            var1 = var5;
         }

         if (var5 != var1) {
            return false;
         }
      }

      return true;
   }

   private static final class a {
      int size = 1;
      int id;
      long timestamp = System.currentTimeMillis();

      a(int var1) {
         this.id = var1;
      }

      int a(int var1) {
         this.timestamp = System.currentTimeMillis();
         this.id = var1;
         return ++this.size;
      }

      boolean a(long var1) {
         return System.currentTimeMillis() > this.timestamp + var1;
      }
   }
}
