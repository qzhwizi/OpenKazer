package com.cheatbreaker.client.kazerui.module.modules.mechanic;

import java.util.List;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.util.IChatComponent;

final class ChatTextSplitter {
   static List<IChatComponent> a(IChatComponent var0, int var1, FontRenderer var2, boolean var3, boolean var4) {
      return GuiUtilRenderComponents.splitText(var0, var1, var2, var3, var4);
   }
}
