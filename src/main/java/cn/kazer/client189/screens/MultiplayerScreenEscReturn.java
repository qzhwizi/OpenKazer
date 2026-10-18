package cn.kazer.client189.screens;

import java.io.IOException;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;

public final class MultiplayerScreenEscReturn extends GuiMultiplayer {
   private final GuiScreen parent;

   public MultiplayerScreenEscReturn(GuiScreen var1) {
      super(var1);
      this.parent = var1;
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if (var2 == 1) {
         this.mc.displayGuiScreen(this.parent);
      } else {
         super.keyTyped(var1, var2);
      }
   }
}
