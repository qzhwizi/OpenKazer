package com.cheatbreaker.client.kazerui.module.modules.mechanic;

import java.io.File;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.Future;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.apache.commons.lang3.text.WordUtils;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public final class ChatTweaksModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   static com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule instance;
   final com.cheatbreaker.client.kazerui.module.modulerender.HudElement hud;
   private static final ResourceLocation PING = new ResourceLocation("random.orb");
   private static final String[] JOINS = new String[]{"spooked into the lobby!", "joined the lobby!", "has joined"};
   static int lastHovered = -1;
   private static String skipPing;
   final com.cheatbreaker.client.kazerui.module.modules.mechanic.MessageStacker stacker = new com.cheatbreaker.client.kazerui.module.modules.mechanic.MessageStacker();
   final com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatHeadTracker heads = new com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatHeadTracker();
   final com.cheatbreaker.client.kazerui.module.modules.mechanic.ProfanityFilter filter = new com.cheatbreaker.client.kazerui.module.modules.mechanic.ProfanityFilter();
   final com.cheatbreaker.client.kazerui.module.modules.mechanic.HoverImagePreview preview = new com.cheatbreaker.client.kazerui.module.modules.mechanic.HoverImagePreview();
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting unlimitedChat = this.a("unlimitedChat", true);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting stackMessages = this.a("stackMessages", false);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting chatHeight = this.a("chatHeight", false);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting stackMessagesTimeBased = this.a("stackMessagesTimeBased", true);
   final com.cheatbreaker.client.kazerui.module.settings.SliderSetting timeBasedStackMessagesTimeframe = this.a("timeBasedStackMessagesTimeframe", 10, 1, 60);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting chatStackIgnoreBlank = this.a("chatStackIgnoreBlank", false);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting chatStackIgnoreBreak = this.a("chatStackIgnoreBreak", false);
   final com.cheatbreaker.client.kazerui.module.settings.SliderSetting chatBackgroundOpacity = this.a("chatBackgroundOpacity", 1.0F, 0.0F, 1.0F);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting chatShadow = this.a("chatShadow", true);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showBackground = this.a("showBackground", true);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting disableChat = this.a("disableChat", false);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting noCloseMyChat = this.a("noCloseMyChat", true);
   final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.a> chatNameColor = this.a(
      "chatNameColor", com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.a.OFF
   );
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting chatNameBold = this.a("chatNameBold", false);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting chatNameItalic = this.a("chatNameItalic", false);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting chatNameUnderline = this.a("chatNameUnderline", false);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting chatNameStrikethrough = this.a("chatNameStrikethrough", false);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting chatNameObfuscated = this.a("chatNameObfuscated", false);
   final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.b> profanity = this.a(
      "profanity", com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.b.OFF
   );
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting stopProfaneMessages = this.a("stopProfaneMessages", false);
   final com.cheatbreaker.client.kazerui.module.settings.SliderSetting inputFieldOpacity = this.a("inputFieldOpacity", 5.0F, 0.0F, 10.0F);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting chatPingSound = this.a("chatPingSound", true);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting chatPingExactMatch = this.a("chatPingExactMatch", false);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting smoothChat = this.a("smoothChat", true);
   final com.cheatbreaker.client.kazerui.module.settings.SliderSetting smoothChatSpeed = this.a("smoothChatSpeed", 3, 1, 10);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting chatTimestamps = this.a("chatTimestamps", false);
   final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.a> timestampColor = this.a(
      "timestampColor", com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.a.GRAY
   );
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showBrackets = this.a("showBrackets", true);
   final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.a> bracketsColor = this.a(
      "bracketsColor", com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.a.GRAY
   );
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting twelveHourClock = this.a("twelveHourClock", true);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showAmPm = this.a("showAmPm", false);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showSeconds = this.a("showSeconds", false);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting timestampItalics = this.a("timestampItalics", true);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting timestampBold = this.a("timestampBold", false);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting copyChat = this.a("copyChat", false);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting copyChatRightClick = this.a("copyChatRightClick", true);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showCopyNotifications = this.a("showCopyNotifications", true);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showVisibilityNotifications = this.a("showVisibilityNotifications", true);
   final com.cheatbreaker.client.kazerui.module.settings.KeybindSetting copyChatBind = this.a(new com.cheatbreaker.client.kazerui.module.settings.KeybindSetting("copyChatBind", "LCONTROL", true));
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting hoverImagePreview = this.a("hoverImagePreview", false);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting fullscreenImage = this.a("fullscreenImage", true);
   final com.cheatbreaker.client.kazerui.module.settings.StringSetting customWhitelistedDomains = this.a(
      new com.cheatbreaker.client.kazerui.module.settings.StringSetting("customWhitelistedDomains")
   );
   final com.cheatbreaker.client.kazerui.module.settings.SliderSetting minImageSize = this.a("minImageSize", 0.0F, 0.0F, 100.0F);
   final com.cheatbreaker.client.kazerui.module.settings.SliderSetting maxImageSize = this.a("maxImageSize", 30.0F, 0.0F, 100.0F);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting longChatSingleplayer = this.a("longChatSingleplayer", false);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting modernChatLengthHypixel = this.a("modernChatLengthHypixel", false);
   final com.cheatbreaker.client.kazerui.module.settings.KeybindSetting chatVisibilityKeybind = this.a("chatVisibilityKeybind");
   final com.cheatbreaker.client.kazerui.module.settings.KeybindSetting chatPeekKeybind = this.a("chatPeekKeybind");
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting chatHeads = this.a("chatHeads", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ButtonSetting openFilter = this.a(
      new com.cheatbreaker.client.kazerui.module.settings.ButtonSetting("openFilter", () -> Sys.openURL(this.u().toURI().toString()))
   );
   private final com.cheatbreaker.client.kazerui.module.settings.ButtonSetting reloadFilter = this.a(new com.cheatbreaker.client.kazerui.module.settings.ButtonSetting("reloadFilter", () -> {
      this.filter.a(this.u(), this.profanity.j());
      this.filterNotification = true;
   }));
   private boolean filterNotification;
   private DateTimeFormatter timestamp;
   private String loadedProfile;
   private boolean chatHidden;
   private World loadedWorld;
   private boolean wasEnabled;
   private Future<Boolean> clipboard;
   private String clipboardText;

   public ChatTweaksModule() {
      super("CHAT", true);
      instance = this;
      this.hud = this.a(new com.cheatbreaker.client.kazerui.module.modulerender.HudElement(this, 0.0F, -26.0F, com.cheatbreaker.client.kazerui.hud.HudAnchor.BOTTOM_LEFT) {
         @Override
         public boolean a(boolean var1) {
            Minecraft var2 = Minecraft.getMinecraft();
            if (var2.ingameGUI != null && (var1 || !ChatTweaksModule.this.q())) {
               GuiNewChat var3 = var2.ingameGUI.getChatGUI();
               float var4 = var3.getChatScale();
               int var5 = ChatRuntime.a(var3);
               if (var1 && var5 == 0) {
                  var5 = 3;
               }

               this.a(((float)Math.ceil(var3.getChatWidth() / var4) + 4.0F) * var4, var5 * 9 * var4);
               return var5 > 0;
            } else {
               return false;
            }
         }

         @Override
         public void b(boolean var1) {
            if (var1) {
               ChatRuntime.a(Minecraft.getMinecraft().ingameGUI.getChatGUI(), this.g());
            }
         }

         @Override
         public boolean j() {
            return true;
         }

         @Override
         public float k() {
            Minecraft var1 = Minecraft.getMinecraft();
            return ChatTweaksModule.this.a() && ChatTweaksModule.this.chatHeight.b() && var1.ingameGUI != null ? -12.0F * var1.ingameGUI.getChatGUI().getChatScale() : 0.0F;
         }
      });
      this.a(new com.cheatbreaker.client.kazerui.module.modules.mechanic.CommandAliasesModule(), "generalOptions");
      com.cheatbreaker.client.kazerui.module.OutgoingChatEvent.sent = var0 -> skipPing = var0;
      this.profanity.a(() -> this.filter.b(this.profanity.j()));
      this.twelveHourClock.a(this::v);
      this.showAmPm.a(this::v);
      this.showSeconds.a(this::v);
      this.customWhitelistedDomains.a(() -> {
         this.preview.b();
         this.preview.a(this.customWhitelistedDomains.j().split("[ ,]+"));
      });
      this.chatHeads.a(() -> {
         if (Minecraft.getMinecraft().ingameGUI != null) {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().refreshChat();
         }
      });
      this.v();
      this.preview.a(this.customWhitelistedDomains.j().split("[ ,]+"));
      com.cheatbreaker.client.kazerui.module.ChatMessageEvent.a(this::a);
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(
         "generalOptions",
         var1x -> {
            var1x.a(this.unlimitedChat);
            var1x.a(
               this.stackMessages,
               var1xx -> var1xx.a(this.stackMessagesTimeBased, this.timeBasedStackMessagesTimeframe, this.chatStackIgnoreBlank, this.chatStackIgnoreBreak)
            );
            var1x.a(this.chatHeight);
            var1x.a(this.showBackground, this.chatShadow, this.disableChat);
            var1x.a(this.chatPingSound, var1xx -> var1xx.a(this.chatPingExactMatch));
            var1x.a(this.chatHeads);
            var1x.a(this.copyChat, var1xx -> var1xx.a(this.copyChatBind, this.copyChatRightClick, this.showCopyNotifications));
            var1x.a(this.hoverImagePreview, var1xx -> {
               var1xx.a(this.customWhitelistedDomains, this.minImageSize, this.maxImageSize);
               var1xx.a(this.fullscreenImage).a(() -> this.maxImageSize.b() == 100.0F);
            });
            var1x.a(this.smoothChat, var1xx -> var1xx.a(this.smoothChatSpeed));
            var1x.a(this.chatPeekKeybind);
            var1x.a(this.chatVisibilityKeybind);
            var1x.a(this.showVisibilityNotifications);
         }
      );
      var1.a(
         "nameOptions",
         var1x -> var1x.a(
            this.chatNameColor, this.chatNameBold, this.chatNameItalic, this.chatNameUnderline, this.chatNameStrikethrough, this.chatNameObfuscated
         )
      );
      var1.a("opacityOptions", var1x -> var1x.a(this.chatBackgroundOpacity, this.inputFieldOpacity));
      var1.a("filterOptions", var1x -> var1x.a(this.profanity, this.openFilter, this.reloadFilter, this.stopProfaneMessages));
      var1.a("timestampOptions", var1x -> var1x.a(this.chatTimestamps, var1xx -> {
         var1xx.a(this.timestampColor, this.timestampItalics, this.timestampBold, this.twelveHourClock, this.showAmPm, this.showSeconds);
         var1xx.a(this.showBrackets, var1xxx -> var1xxx.a(this.bracketsColor));
      }));
   }

   private File u() {
      String var1 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.d() == null
         ? "Default"
         : com.cheatbreaker.client.kazerui.module.ModuleRegistry.d().store.a("activeProfile", "Default");
      return com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatFilterPaths.a(com.cheatbreaker.client.settings.ConfigManager.configDir, var1);
   }

   private void v() {
      String var1 = this.twelveHourClock.b() ? "h" : "HH";
      var1 = var1 + ":mm";
      if (this.showSeconds.b()) {
         var1 = var1 + ":ss";
      }

      if (this.showAmPm.b()) {
         var1 = var1 + " a";
      }

      this.timestamp = DateTimeFormatter.ofPattern(var1, Locale.ENGLISH);
   }

   boolean n() {
      return this.a() && this.smoothChat.b();
   }

   float o() {
      return a(this.smoothChatSpeed.c());
   }

   static float a(int var0) {
      return (var0 - 1.0F) / 9.0F * 0.009F + 0.001F;
   }

   float p() {
      return this.a() ? this.inputFieldOpacity.b() / 10.0F * 2.0F : 1.0F;
   }

   boolean q() {
      return this.a() && this.chatHidden && !this.r();
   }

   boolean r() {
      return this.a() && this.chatPeekKeybind.c() != 0 && Minecraft.getMinecraft().currentScreen == null && this.chatPeekKeybind.b();
   }

   boolean s() {
      return this.a() && this.chatHeads.b();
   }

   int a(boolean var1, int var2) {
      return var2;
   }

   private void a(com.cheatbreaker.client.kazerui.module.ChatMessageEvent var1) {
      if (this.a() && !var1.cancelled && Minecraft.getMinecraft().thePlayer != null) {
         if (!com.cheatbreaker.client.kazerui.module.HypixelUtils.a() || !var1.legacy.startsWith("{")) {
            if (!this.filter.a(this, var1)) {
               if (this.disableChat.b()) {
                  var1.cancelled = true;
               } else {
                  if (this.chatPingSound.b()) {
                     this.b(var1);
                  }

                  if (this.chatTimestamps.b()) {
                     this.c(var1);
                  }

                  if (this.stackMessages.b()) {
                     this.stacker.a(this, var1);
                  }

                  if (this.s()) {
                     this.heads.a(var1);
                  }
               }
            }
         }
      }
   }

   private void b(com.cheatbreaker.client.kazerui.module.ChatMessageEvent var1) {
      Minecraft var2 = Minecraft.getMinecraft();
      if (var2.thePlayer != null) {
         if (com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTextUtils.a(var1.a(), skipPing)) {
            skipPing = null;
         } else {
            String var3 = var2.thePlayer.getName();
            Object var4 = null;
            boolean var5 = this.chatPingExactMatch.b()
               ? com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTextUtils.b(var1.a(), var3, (String)var4)
               : com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTextUtils.a(var1.a(), var3, (String)var4);
            if (var5 && !com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTextUtils.a(var1.a(), JOINS)) {
               var2.getSoundHandler().playSound(PositionedSoundRecord.create(PING, 1.0F));
            }
         }
      }
   }

   private void c(com.cheatbreaker.client.kazerui.module.ChatMessageEvent var1) {
      ChatComponentText var2 = new ChatComponentText(LocalTime.now().format(this.timestamp));
      var2.getChatStyle().setColor(this.timestampColor.j().formatting);
      ChatComponentText var3;
      if (this.showBrackets.b()) {
         var3 = new ChatComponentText("");
         EnumChatFormatting var4 = this.bracketsColor.j().formatting;
         var3.appendSibling(a("[", var4)).appendSibling(var2).appendSibling(a("]", var4));
      } else {
         var3 = var2;
      }

      var3.appendSibling(new ChatComponentText(" "));
      ChatStyle var6 = var3.getChatStyle();
      if (this.timestampItalics.b()) {
         var6.setItalic(true);
      }

      if (this.timestampBold.b()) {
         var6.setBold(true);
      }

      ChatComponentText var5 = new ChatComponentText("");
      var5.appendSibling(var3).appendSibling(var1.a());
      var1.a(var5);
   }

   private static IChatComponent a(String var0, EnumChatFormatting var1) {
      ChatComponentText var2 = new ChatComponentText(var0);
      var2.getChatStyle().setColor(var1);
      return var2;
   }

   private boolean w() {
      if (lastHovered == -1) {
         return false;
      } else {
         IChatComponent var1 = com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatHooks.d(lastHovered);
         if (var1 == null) {
            return false;
         } else {
            String var2 = com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTextUtils.a(var1);
            if (this.clipboard != null && !this.clipboard.isDone()) {
               return true;
            } else {
               this.clipboardText = var2;
               this.clipboard = com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatClipboard.a(var2);
               return true;
            }
         }
      }
   }

   private void x() {
      if (this.clipboard != null && this.clipboard.isDone()) {
         try {
            boolean var1 = this.clipboard.get();
            if (this.a() && this.copyChat.b() && (!var1 || this.showCopyNotifications.b())) {
               com.cheatbreaker.client.kazerui.gui.ToastNotifications.a(
                  var1 ? com.cheatbreaker.client.kazerui.gui.ToastNotifications.b.INFO : com.cheatbreaker.client.kazerui.gui.ToastNotifications.b.ERROR,
                  var1 ? com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("popups", "copiedMessage") : "Copy failed",
                  var1 ? this.clipboardText : "Clipboard is unavailable."
               );
            }
         } catch (Exception var5) {
            if (this.a() && this.copyChat.b()) {
               com.cheatbreaker.client.kazerui.gui.ToastNotifications.a(com.cheatbreaker.client.kazerui.gui.ToastNotifications.b.ERROR, "Copy failed", "Clipboard is unavailable.");
            }
         } finally {
            this.clipboard = null;
            this.clipboardText = null;
         }
      }
   }

   private void y() {
      if (this.clipboard != null) {
         this.clipboard.cancel(true);
      }

      this.clipboard = null;
      this.clipboardText = null;
   }

   public boolean a(GuiScreen var1) {
      if (this.a() && this.copyChat.b() && var1 instanceof GuiChat && Mouse.getEventButtonState()) {
         int var2 = Mouse.getEventButton();
         boolean var3 = var2 == 0 && this.copyChatBind.b();
         boolean var4 = var2 == 1 && this.copyChatRightClick.b();
         if (!var3 && !var4) {
            return false;
         } else {
            com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatHooks.a(Mouse.getX(), Mouse.getY());
            return this.w();
         }
      } else {
         return false;
      }
   }

   public void b(GuiScreen var1) {
      if (this.a() && var1 instanceof GuiChat && Keyboard.getEventKeyState()) {
         this.preview.a(Keyboard.getEventKey(), Keyboard.isRepeatEvent());
      }
   }

   public void a(GuiScreen var1, int var2, int var3) {
      if (this.a() && var1 instanceof GuiChat) {
         this.preview.a(this, var2, var3, var1.width, var1.height);
      }
   }

   @Override
   public void a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a var1) {
      if (var1.phase == com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b.END) {
         WorldClient var2 = Minecraft.getMinecraft().theWorld;
         if (var2 != this.loadedWorld) {
            this.t();
            this.loadedWorld = var2;
         }

         if (this.wasEnabled && !this.a()) {
            this.t();
         }

         this.wasEnabled = this.a();
         String var3 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.d() == null ? "Default" : com.cheatbreaker.client.kazerui.module.ModuleRegistry.d().a();
         if (!var3.equals(this.loadedProfile)) {
            this.loadedProfile = var3;
            this.y();
            this.stacker.a();
            this.heads.a();
            this.preview.b();
            this.filterNotification = false;
            this.filter.a(this.u(), this.profanity.j());
         }

         if (this.filter.a(this.profanity.j()) && this.filterNotification) {
            this.filterNotification = false;
            if (this.filter.a()) {
               com.cheatbreaker.client.kazerui.gui.ToastNotifications.a("Reloaded custom filter!");
            } else {
               com.cheatbreaker.client.kazerui.gui.ToastNotifications.a(
                  com.cheatbreaker.client.kazerui.gui.ToastNotifications.b.ERROR, "Filter reload failed", "Check the custom filter file (maximum 64 KiB)."
               );
            }
         }

         if (!this.a()) {
            this.y();
         } else {
            this.x();
         }

         if (this.chatVisibilityKeybind.a(this.a() && Minecraft.getMinecraft().currentScreen == null)) {
            this.chatHidden = !this.chatHidden;
            if (this.showVisibilityNotifications.b()) {
               com.cheatbreaker.client.kazerui.gui.ToastNotifications.a(this.chatHidden ? "Chat hidden" : "Chat shown");
            }
         }

         if (!this.a() || !(Minecraft.getMinecraft().currentScreen instanceof GuiChat)) {
            this.preview.a();
         }
      }
   }

   public void t() {
      this.stacker.a();
      this.heads.a();
      this.preview.b();
      this.y();
      this.chatHidden = false;
      skipPing = null;
      lastHovered = -1;
      com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatHooks.a();
   }

   public static enum a implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
      OFF,
      DARK_BLUE,
      DARK_GREEN,
      DARK_AQUA,
      DARK_RED,
      DARK_PURPLE,
      GOLD,
      GRAY,
      DARK_GRAY,
      BLUE,
      GREEN,
      AQUA,
      RED,
      LIGHT_PURPLE,
      YELLOW,
      WHITE;

      final EnumChatFormatting formatting = this.name().equals("OFF") ? null : EnumChatFormatting.valueOf(this.name());

      @Override
      public String a() {
         return WordUtils.capitalize(this.name().toLowerCase().replace("_", " "));
      }
   }

   public static enum b implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
      CUSTOM("custom"),
      OFF("off"),
      NORMAL("normal"),
      HIGH("high");

      private final String id;

      private b(String var3) {
         this.id = var3;
      }

      @Override
      public String a() {
         return this.id;
      }
   }
}
