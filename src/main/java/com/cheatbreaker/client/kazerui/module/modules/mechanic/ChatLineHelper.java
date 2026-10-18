package com.cheatbreaker.client.kazerui.module.modules.mechanic;

import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.IChatComponent;

final class ChatLineHelper {
   static ChatLine a(int var0, IChatComponent var1, int var2, IChatComponent var3) {
      return new ChatLine(var0, var1, var2);
   }

   static void a(GuiNewChat var0, int var1) {
      var0.scroll(var1);
   }

   private ChatLineHelper() {
   }
}
