package com.cheatbreaker.client.module.modules.togglesprint;

import net.minecraft.MinecraftMovementInputHelper;
import net.minecraft.client.gui.GuiChat;
import org.lwjgl.opengl.GL11;

public class ToggleSprintModule extends com.cheatbreaker.client.module.Module {
   public static com.cheatbreaker.client.settings.Setting toggleSprint;
   public static com.cheatbreaker.client.settings.Setting toggleSneak;
   public static com.cheatbreaker.client.settings.Setting showHudText;
   private com.cheatbreaker.client.settings.Setting textColor;
   public static com.cheatbreaker.client.settings.Setting doubleTap;
   public static com.cheatbreaker.client.settings.Setting flyBoost;
   public static com.cheatbreaker.client.settings.Setting showWhileTyping;
   private com.cheatbreaker.client.settings.Setting flyBoostLabel;
   public static com.cheatbreaker.client.settings.Setting flyBoostAmount;
   public com.cheatbreaker.client.settings.Setting flyBoostString;
   public com.cheatbreaker.client.settings.Setting flyString;
   public com.cheatbreaker.client.settings.Setting ridingString;
   public com.cheatbreaker.client.settings.Setting decendString;
   public com.cheatbreaker.client.settings.Setting dismountString;
   public com.cheatbreaker.client.settings.Setting sneakHeldString;
   public com.cheatbreaker.client.settings.Setting sprintHeldString;
   public com.cheatbreaker.client.settings.Setting sprintVanillaString;
   public com.cheatbreaker.client.settings.Setting sprintToggledString;
   public com.cheatbreaker.client.settings.Setting sneakToggledString;
   public static boolean buggedSprint = false;

   public ToggleSprintModule() {
      super("ToggleSprint");
      this.b(com.cheatbreaker.client.gui.hudeditor.GuiAnchor.LEFT_TOP);
      this.b(0.0F, 0.0F);
      this.c(true);
      this.isEditable = false;
      toggleSprint = new com.cheatbreaker.client.settings.Setting(this, "Toggle Sprint").a(true);
      toggleSneak = new com.cheatbreaker.client.settings.Setting(this, "Toggle Sneak").a(false);
      showHudText = new com.cheatbreaker.client.settings.Setting(this, "Show HUD Text").a(true);
      this.textColor = new com.cheatbreaker.client.settings.Setting(this, "Text Color").a(-1).a(Integer.MIN_VALUE, Integer.MAX_VALUE);
      doubleTap = new com.cheatbreaker.client.settings.Setting(this, "Double Tap").a(false);
      showWhileTyping = new com.cheatbreaker.client.settings.Setting(this, "Show While Typing").a(true);
      this.flyBoostLabel = new com.cheatbreaker.client.settings.Setting(this, "label").a("Fly Boost");
      flyBoost = new com.cheatbreaker.client.settings.Setting(this, "Fly Boost").a(true);
      flyBoostAmount = new com.cheatbreaker.client.settings.Setting(this, "Fly Boost Amount").a(8).a(2, 8).a(flyBoost);
      this.flyBoostString = new com.cheatbreaker.client.settings.Setting(this, "Fly Boost String").a("[Flying (%BOOST%x boost)]");
      this.flyString = new com.cheatbreaker.client.settings.Setting(this, "Fly String").a("[Flying]");
      this.ridingString = new com.cheatbreaker.client.settings.Setting(this, "Riding String").a("[Riding]");
      this.decendString = new com.cheatbreaker.client.settings.Setting(this, "Descend String").a("[Descending]");
      this.dismountString = new com.cheatbreaker.client.settings.Setting(this, "Dismount String").a("[Dismounting]");
      this.sneakHeldString = new com.cheatbreaker.client.settings.Setting(this, "Sneaking String").a("[Sneaking (Key Held)]");
      this.sprintHeldString = new com.cheatbreaker.client.settings.Setting(this, "Sprinting Held String").a("[Sprinting (Key Held)]");
      this.sprintVanillaString = new com.cheatbreaker.client.settings.Setting(this, "Sprinting Vanilla String").a("[Sprinting (Vanilla)]");
      this.sprintToggledString = new com.cheatbreaker.client.settings.Setting(this, "Sprinting Toggle String").a("[Sprinting (Toggled)]");
      this.sneakToggledString = new com.cheatbreaker.client.settings.Setting(this, "Sneaking Toggle String").a("[Sneaking (Toggled)]");
      this.a("[Sprinting (Toggled)]", 1.0F);
      this.a(com.cheatbreaker.client.event.event.RenderHudEvent.class, this::a);
   }

   private void a(com.cheatbreaker.client.event.event.RenderHudEvent var1) {
      if (this.q()) {
         int var2 = this.minecraft.fontRendererObj.FONT_HEIGHT;
         if (!(Boolean)showHudText.d() || !(Boolean)showWhileTyping.d() && this.minecraft.currentScreen instanceof GuiChat) {
            float[] var5 = this.a(50, var2);
            this.c(var5[0], var5[1]);
         } else {
            GL11.glPushMatrix();
            int var3 = this.minecraft.fontRendererObj.getStringWidth(MinecraftMovementInputHelper.toggleSprintString);
            float[] var4 = this.a(var3, var2);
            this.c(var4[0], var4[1]);
            this.a(var1.a());
            this.minecraft.fontRendererObj.drawStringWithShadow(MinecraftMovementInputHelper.toggleSprintString, 0.0F, 0.0F, this.textColor.a());
            GL11.glPopMatrix();
         }
      }
   }

   float[] a(int var1, int var2) {
      return new float[]{var1, var2};
   }
}
