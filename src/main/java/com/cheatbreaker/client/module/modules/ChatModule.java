package com.cheatbreaker.client.module.modules;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiNewChat;
import org.lwjgl.opengl.GL11;

public final class ChatModule extends com.cheatbreaker.client.module.Module {
   private final com.cheatbreaker.client.module.modules.VisibilityState visibilityState = new com.cheatbreaker.client.module.modules.VisibilityState();
   private final com.cheatbreaker.client.module.modules.ActionFeedbackState actionFeedbackState = new com.cheatbreaker.client.module.modules.ActionFeedbackState();
   private final com.cheatbreaker.client.module.modules.KeybindListAnimator actionNotificationRenderer = new com.cheatbreaker.client.module.modules.KeybindListAnimator();
   public final com.cheatbreaker.client.settings.Setting showVisibilityNotifications;
   public final com.cheatbreaker.client.settings.Setting showCopyNotifications;
   public final com.cheatbreaker.client.settings.Setting showBackground = new com.cheatbreaker.client.settings.Setting(this, "Show Background").a(true);

   public ChatModule() {
      super("Chat");
      this.showVisibilityNotifications = new com.cheatbreaker.client.settings.Setting(this, "Show Visibility Notifications")
         .a(true)
         .a(var1 -> this.actionFeedbackState.b((Boolean)var1, E()));
      this.showCopyNotifications = new com.cheatbreaker.client.settings.Setting(this, "Show Copy Notifications")
         .a(true)
         .a(var1 -> this.actionFeedbackState.c((Boolean)var1, E()));
      this.a(com.cheatbreaker.client.event.event.RenderHudEvent.class, this::a);
      this.a(com.cheatbreaker.client.event.event.RenderHudEditorEvent.class, this::a);
      this.b(com.cheatbreaker.client.gui.hudeditor.GuiAnchor.LEFT_BOTTOM);
      this.b(0.0F, -26.0F);
      this.g(true);
      this.c(324.0F, 90.0F);
      this.a("Chat", 1.0F);
      this.c(true);
   }

   public boolean a() {
      if (this.g()) {
         this.visibilityState.a();
         return this.actionFeedbackState.a(this.visibilityState.b(), E());
      } else {
         return false;
      }
   }

   @Override
   public void a(boolean var1) {
      if (!var1) {
         this.visibilityState.c();
         this.actionFeedbackState.c(E());
      }

      super.a(var1);
   }

   public boolean z() {
      return this.visibilityState.b();
   }

   public long A() {
      return this.actionFeedbackState.a();
   }

   public boolean a(long var1, boolean var3) {
      return !this.g()
         ? false
         : this.actionFeedbackState.a(var3 ? com.cheatbreaker.client.module.modules.ActionFeedbackState.Result.SUCCESS : com.cheatbreaker.client.module.modules.ActionFeedbackState.Result.FAILURE, var1, E());
   }

   public boolean h(boolean var1) {
      return !this.g() || this.visibilityState.a(var1);
   }

   public int B() {
      return this.actionFeedbackState.b(E());
   }

   private void a(com.cheatbreaker.client.event.event.RenderHudEvent var1) {
      this.C();
      long var2 = E();
      this.actionNotificationRenderer.a(this.actionFeedbackState.a(var2), var1.a(), var2);
   }

   public void C() {
      if (this.minecraft.ingameGUI != null) {
         GuiNewChat var1 = this.minecraft.ingameGUI.getChatGUI();
         float var2 = var1.getChatScale();
         int var3 = cn.kazer.client189.render.ClientBridge.a(var1);
         if (var3 == 0 && this.D()) {
            var3 = 3;
         }

         this.c(((float)Math.ceil(var1.getChatWidth() / var2) + 4.0F) * var2, var3 * 9.0F * var2);
      }
   }

   private boolean D() {
      return this.minecraft.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.ModulePlacementScreen || this.minecraft.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.HudEditorScreen;
   }

   private void a(com.cheatbreaker.client.event.event.RenderHudEditorEvent var1) {
      if (this.q() && this.D() && this.minecraft.ingameGUI != null && cn.kazer.client189.render.ClientBridge.a(this.minecraft.ingameGUI.getChatGUI()) == 0) {
         this.C();
         GL11.glPushMatrix();

         try {
            this.a(var1.a());
            if ((Boolean)this.showBackground.d()) {
               cn.kazer.client189.render.DrawRect.a(0.0F, 0.0F, this.width, this.height, Integer.MIN_VALUE);
            }

            GL11.glScalef(this.minecraft.ingameGUI.getChatGUI().getChatScale(), this.minecraft.ingameGUI.getChatGUI().getChatScale(), 1.0F);
            FontRenderer var2 = this.minecraft.fontRendererObj;
            var2.drawStringWithShadow("Welcome to Kazer Client!", 0.0F, 0.0F, -1);
            var2.drawStringWithShadow("Drag this chat to reposition it.", 0.0F, 9.0F, -1);
            var2.drawStringWithShadow("Chat messages appear here.", 0.0F, 18.0F, -1);
         } finally {
            GL11.glPopMatrix();
         }
      }
   }

   private static long E() {
      return System.nanoTime() / 1000000L;
   }
}
