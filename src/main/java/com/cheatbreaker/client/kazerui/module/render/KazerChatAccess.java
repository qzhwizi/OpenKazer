package com.cheatbreaker.client.kazerui.module.render;

import java.util.List;
import net.minecraft.client.gui.ChatLine;

public interface KazerChatAccess {
   List<ChatLine> kazerDrawnLines();

   List<ChatLine> kazerChatLines();

   int kazerChatScroll();

   boolean kazerChatScrolled();

   void kazerChatScrolled(boolean var1);
}
