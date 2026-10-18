package cn.kazer.client189.screens;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;

public final class KazerIngameMenu extends GuiIngameMenu {
   public void initGui() {
      this.buttonList.clear();
      int var1 = this.width / 2 - 100;
      int var2 = this.height / 4 + 8;
      boolean var3 = this.mc.isSingleplayer();
      this.buttonList.add(new GuiButton(4, var1, var2, 200, 20, "Back to Game"));
      this.buttonList.add(new GuiButton(5, var1, var2 + 24, 98, 20, "Achievements"));
      this.buttonList.add(new GuiButton(6, var1 + 102, var2 + 24, 98, 20, "Statistics"));
      GuiButton var4 = new GuiButton(7, var1, var2 + 48, 200, 20, var3 ? "Host World" : "Multiplayer");
      var4.enabled = !var3 || !this.mc.getIntegratedServer().getPublic();
      this.buttonList.add(var4);
      this.buttonList.add(new GuiButton(0, var1, var2 + 72, 98, 20, "Options..."));
      this.buttonList.add(new GuiButton(10, var1 + 102, var2 + 72, 98, 20, "Kazer Options..."));
      this.buttonList.add(new GuiButton(1, var1, var2 + 96, 200, 20, var3 ? "Save and Quit to Title" : "Disconnect"));
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      switch (var1.id) {
         case 0:
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
            break;
         case 1:
            var1.enabled = false;
            this.mc.theWorld.sendQuittingDisconnectingPacket();
            this.mc.loadWorld(null);
            this.mc.displayGuiScreen(new GuiMainMenu());
         case 2:
         case 3:
         case 8:
         case 9:
         default:
            break;
         case 4:
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
            break;
         case 5:
            this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
            break;
         case 6:
            this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
            break;
         case 7:
            this.mc.displayGuiScreen((GuiScreen)(this.mc.isSingleplayer() ? new GuiShareToLan(this) : new GuiMultiplayer(this)));
            break;
         case 10:
            this.mc.displayGuiScreen(new com.cheatbreaker.client.gui.hudeditor.HudEditorScreen());
      }
   }
}
