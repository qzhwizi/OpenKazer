package com.cheatbreaker.client.settings;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

public class GlobalSettings {
   public static final boolean DEFAULT_CONTAINER_VIGNETTE_ENABLED = true;
   public static final int DEFAULT_CONTAINER_VIGNETTE_STRENGTH_PERCENT = 50;
   public KeyBinding openMenu;
   public KeyBinding dragLook;
   public final com.cheatbreaker.client.settings.KeybindRegistry keyBindings = new com.cheatbreaker.client.settings.KeybindRegistry();
   public final List<com.cheatbreaker.client.settings.Setting> settingsList = new ArrayList<>();
   public final com.cheatbreaker.client.settings.FavoriteModules favoriteModules = new com.cheatbreaker.client.settings.FavoriteModules();
   public List<String[]> pinnedServers;
   public List<String> warnedServers;
   public boolean isDebug = true;
   public String crashReportURL = "";
   public String debugUploadURL = "";
   public String cosmeticApiURL = "";
   public String cosmeticAllApiURL = "";
   public String mojangStatusURL = "https://status.mojang.com/check";
   public int reconnectTime = 60;
   public boolean IIIlllIIIllIllIlIIIIIIlII = true;
   public com.cheatbreaker.client.settings.Setting muteCheatBreakerSounds;
   private com.cheatbreaker.client.settings.Setting fpsBoostLabel;
   public com.cheatbreaker.client.settings.Setting enableFpsBoost;
   public com.cheatbreaker.client.settings.Setting slowChunkLoading;
   public com.cheatbreaker.client.settings.Setting fullBright;
   public com.cheatbreaker.client.settings.Setting enchantmentGlint;
   private com.cheatbreaker.client.settings.Setting teamViewLabel;
   public com.cheatbreaker.client.settings.Setting enableTeamView;
   public com.cheatbreaker.client.settings.Setting showDistance;
   public com.cheatbreaker.client.settings.Setting showOffScreenMarker;
   private com.cheatbreaker.client.settings.Setting generalSettingsLabel;
   public com.cheatbreaker.client.settings.Setting guiBlur;
   public com.cheatbreaker.client.settings.Setting worldTime;
   public com.cheatbreaker.client.settings.Setting snapModules;
   private com.cheatbreaker.client.settings.Setting renderSettingsLabel;
   public com.cheatbreaker.client.settings.Setting showHudInDebug;
   public com.cheatbreaker.client.settings.Setting showChatBackground;
   public com.cheatbreaker.client.settings.Setting shinyPots;
   public com.cheatbreaker.client.settings.Setting showPotionInfo;
   public com.cheatbreaker.client.settings.Setting clearGlass;
   public com.cheatbreaker.client.settings.Setting redString;
   public com.cheatbreaker.client.settings.Setting transparentBackground;
   public com.cheatbreaker.client.settings.Setting containerVignette;
   public com.cheatbreaker.client.settings.Setting containerVignetteStrength;
   private com.cheatbreaker.client.settings.Setting crosshairSettingsLabel;
   public com.cheatbreaker.client.settings.Setting customCrosshair;
   public com.cheatbreaker.client.settings.Setting crosshairOutline;
   public com.cheatbreaker.client.settings.Setting crosshairColor;
   public com.cheatbreaker.client.settings.Setting crosshairThickness;
   public com.cheatbreaker.client.settings.Setting crosshairSize;
   public com.cheatbreaker.client.settings.Setting crosshairGap;
   private com.cheatbreaker.client.settings.Setting colorOptionsLabel;
   public com.cheatbreaker.client.settings.Setting defaultColor;
   public com.cheatbreaker.client.settings.Setting particleAmount;
   public com.cheatbreaker.client.settings.Setting limitUnfocusedFps;
   public com.cheatbreaker.client.settings.Setting maxUnfocusedFps;
   public com.cheatbreaker.client.settings.Setting maxMainMenuFps;
   public com.cheatbreaker.client.settings.Setting entityDistance;
   public com.cheatbreaker.client.settings.Setting tileEntityDistance;
   public com.cheatbreaker.client.settings.Setting keepInventoryCentered;
   public com.cheatbreaker.client.settings.Setting disableHotbarScrolling;
   public com.cheatbreaker.client.settings.Setting showAchievements;
   public com.cheatbreaker.client.settings.Setting showCrosshairThirdPerson;
   public com.cheatbreaker.client.settings.Setting scrollingSpeed;
   public List<com.cheatbreaker.client.gui.uielement.settings.ColorSwatchElement> favouriteColors = new ArrayList<>();
   public List<com.cheatbreaker.client.gui.uielement.settings.ColorSwatchElement> IlIIlIIlIllIIIIllIIllIlIl = new ArrayList<>();

   public boolean a(int var1) {
      for (com.cheatbreaker.client.gui.uielement.settings.ColorSwatchElement var3 : this.favouriteColors) {
         if (var3.color == var1) {
            return true;
         }
      }

      return false;
   }

   public void b(int var1) {
      this.favouriteColors.removeIf(var1x -> var1x.color == var1);
   }

   public GlobalSettings() {
      this.muteCheatBreakerSounds = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Mute Kazer sounds").a(false);
      this.fpsBoostLabel = new com.cheatbreaker.client.settings.Setting(this.settingsList, "label").a("FPS Boost");
      this.enableFpsBoost = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Enable FPS Boost").a(true);
      this.slowChunkLoading = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Slow chunk loading (%)").a(5, 100).a(30);
      this.fullBright = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Fullbright").a(true);
      this.enchantmentGlint = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Enchantment Glint").a(true);
      this.teamViewLabel = new com.cheatbreaker.client.settings.Setting("label").a("Team View Settings");
      this.enableTeamView = new com.cheatbreaker.client.settings.Setting("Enable Team View").a(false);
      this.showOffScreenMarker = new com.cheatbreaker.client.settings.Setting("Show off-screen marker").a(false);
      this.showDistance = new com.cheatbreaker.client.settings.Setting("Show distance").a(false);
      this.generalSettingsLabel = new com.cheatbreaker.client.settings.Setting(this.settingsList, "label").a("General Settings");
      this.guiBlur = new com.cheatbreaker.client.settings.Setting(this.settingsList, "GUI Blur").a(false);
      this.worldTime = new com.cheatbreaker.client.settings.Setting(this.settingsList, "World Time").a(-14490).a(-22880, -6100);
      this.snapModules = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Snap mods to other mods (GUI)").a(true);
      this.renderSettingsLabel = new com.cheatbreaker.client.settings.Setting(this.settingsList, "label").a("Render Settings");
      this.showPotionInfo = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Show Potion info in inventory").a(false);
      this.showChatBackground = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Show chat background").a(true);
      this.showHudInDebug = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Show HUD while in debug view").displayLabel("Show HUD in F3").a(false);
      this.shinyPots = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Shiny Pots").a(false);
      this.clearGlass = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Clear Glass").a("REGULAR").a("OFF", "REGULAR", "ALL");
      this.redString = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Red String").a(true);
      this.transparentBackground = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Transparent background").a(true);
      this.containerVignette = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Vignette").a(true);
      this.containerVignetteStrength = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Vignette Strength").a(50).a(0, 100).sliderMarker(0.5F);
      this.crosshairSettingsLabel = new com.cheatbreaker.client.settings.Setting(this.settingsList, "label").a("Crosshair Settings");
      this.customCrosshair = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Custom crosshair").a(false);
      this.crosshairOutline = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Outline").a(false);
      this.crosshairColor = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Color").a(-1).a(Integer.MIN_VALUE, Integer.MAX_VALUE);
      this.crosshairThickness = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Thickness").a(Float.valueOf(2.0F)).a(1.0F, 2.5F);
      this.crosshairSize = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Size").a(Float.valueOf(4.0F)).a(1.0F, 10.0F);
      this.crosshairGap = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Gap").a(Float.valueOf(1.75F)).a(0.0F, 7.5F);
      this.colorOptionsLabel = new com.cheatbreaker.client.settings.Setting(this.settingsList, "label").a("Color Options");
      this.defaultColor = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Default color").a(-1).a(Integer.MIN_VALUE, Integer.MAX_VALUE);
      this.particleAmount = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Particle Amount (%)").a(100).a(0, 100);
      this.limitUnfocusedFps = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Limit Unfocused FPS").a(false);
      this.maxUnfocusedFps = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Max Unfocused FPS").a(15).a(5, 120).a(this.limitUnfocusedFps);
      this.maxMainMenuFps = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Max Main Menu FPS").a(90).a(30, 240);
      this.entityDistance = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Entity Distance").displayLabel("Entity Dist. (0=MC)").a(0).a(0, 256);
      this.tileEntityDistance = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Tile Entity Distance").displayLabel("Tile Dist. (0=MC)").a(0).a(0, 256);
      this.keepInventoryCentered = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Keep Inventory Centered").a(false);
      this.disableHotbarScrolling = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Disable Hotbar Scrolling").a(false);
      this.showAchievements = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Show Achievements").a(true);
      this.showCrosshairThirdPerson = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Show Crosshair in Third Person").a(true);
      this.scrollingSpeed = new com.cheatbreaker.client.settings.Setting(this.settingsList, "Scrolling Speed (%)").a(100).a(25, 200);
      this.pinnedServers = new ArrayList<>();
      this.pinnedServers.add(new String[]{"Kazer Network", "kazer.cn"});
      this.warnedServers = new ArrayList<>();
      this.warnedServers.add("xyz.com");
      GameSettings var1 = Minecraft.getMinecraft().gameSettings;
      this.openMenu = new KeyBinding("Open Menu", 54, "key.categories.misc");
      this.dragLook = new KeyBinding("Drag to look", 56, "key.categories.misc");
      this.keyBindings.a("Open Menu", this.openMenu);
      this.keyBindings.a("Freelook", this.dragLook, "Freelook");
      this.keyBindings.a("Zoom", cn.kazer.client189.render.ClientBridge.c(), "of.key.zoom");
   }

   public com.cheatbreaker.client.settings.Setting a() {
      return this.crosshairSettingsLabel;
   }
}
