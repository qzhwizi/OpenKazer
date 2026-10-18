package com.cheatbreaker.client.module;

import com.cheatbreaker.client.module.modules.ScreenshotModule;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
   public List<com.cheatbreaker.client.module.Module> modules = new ArrayList<>();
   public List<com.cheatbreaker.client.module.Module> staffModules = new ArrayList<>();
   public com.cheatbreaker.client.module.Module llIIlllIIIIlllIllIlIlllIl;
   public com.cheatbreaker.client.module.modules.togglesprint.ToggleSprintModule toggleSprint;
   public com.cheatbreaker.client.module.modules.layout.NotificationsModule notifications;
   public com.cheatbreaker.client.module.modules.options.ArmourStatusModule armourStatus;
   public com.cheatbreaker.client.module.modules.render.CooldownsModule cooldowns;
   public com.cheatbreaker.client.module.modules.ScoreboardModule scoreboard;
   public com.cheatbreaker.client.module.module.XrayModule xray;
   public com.cheatbreaker.client.module.modules.PotionStatusModule potionStatus;
   public com.cheatbreaker.client.module.modules.hud.BossBarModule bossBar;
   public com.cheatbreaker.client.module.modules.DirectionHudModule directionHud;
   public com.cheatbreaker.client.module.modules.keystrokes.KeyStrokesModule keyStrokes;
   public com.cheatbreaker.client.module.modules.FpsModule fpsModule;
   public com.cheatbreaker.client.module.modules.ScreenshotModule cpsModule;
   public com.cheatbreaker.client.module.modules.SaturationModule saturationModule;
   public com.cheatbreaker.client.module.modules.ClockModule clockModule;
   public com.cheatbreaker.client.module.modules.PackDisplayModule packDisplayModule;
   public com.cheatbreaker.client.module.modules.ItemCountModule itemCountModule;
   public com.cheatbreaker.client.module.modules.config.CustomTextModule customTextModule;
   public com.cheatbreaker.client.module.modules.keybinds.AutoTextHotkeyModule autoTextHotkeyModule;
   public com.cheatbreaker.client.module.modules.CoordinatesModule coordinatesModule;
   public com.cheatbreaker.client.module.modules.camera.FreelookModule freelook;
   public com.cheatbreaker.client.module.modules.ChatModule chatModule;
   public com.cheatbreaker.client.module.modules.animation.BlockhitSoundModule blockhitSound;
   public com.cheatbreaker.client.module.modules.shader.MotionBlurModule motionBlurModule;
   public com.cheatbreaker.client.module.modules.nametag.NametagsModule nametagModule;
   public cn.kazer.client189.animation.LegacyVisualsModule blockHitAnimation;
   public ScreenshotModule screenshotModule;

   public ModuleManager() {
      this.modules.add(this.armourStatus = new com.cheatbreaker.client.module.modules.options.ArmourStatusModule());
      this.modules.add(this.toggleSprint = new com.cheatbreaker.client.module.modules.togglesprint.ToggleSprintModule());
      this.modules.add(this.potionStatus = new com.cheatbreaker.client.module.modules.PotionStatusModule());
      this.modules.add(this.keyStrokes = new com.cheatbreaker.client.module.modules.keystrokes.KeyStrokesModule());
      this.modules.add(this.fpsModule = new com.cheatbreaker.client.module.modules.FpsModule());
      this.modules.add(this.cpsModule = new com.cheatbreaker.client.module.modules.ScreenshotModule());
      this.modules.add(this.nametagModule = new com.cheatbreaker.client.module.modules.nametag.NametagsModule());
      this.modules.add(this.bossBar = new com.cheatbreaker.client.module.modules.hud.BossBarModule());
      this.modules.add(this.motionBlurModule = new com.cheatbreaker.client.module.modules.shader.MotionBlurModule());
      this.modules.add(this.scoreboard = new com.cheatbreaker.client.module.modules.ScoreboardModule());
      this.modules.add(this.chatModule = new com.cheatbreaker.client.module.modules.ChatModule());
      this.modules.add(this.blockHitAnimation = new cn.kazer.client189.animation.LegacyVisualsModule());
      this.modules.add(this.freelook = new com.cheatbreaker.client.module.modules.camera.FreelookModule());
      this.packDisplayModule = a(new com.cheatbreaker.client.module.modules.PackDisplayModule());
      this.saturationModule = a(new com.cheatbreaker.client.module.modules.SaturationModule());
      this.clockModule = a(new com.cheatbreaker.client.module.modules.ClockModule());
      this.autoTextHotkeyModule = a(new com.cheatbreaker.client.module.modules.keybinds.AutoTextHotkeyModule());
      this.itemCountModule = a(new com.cheatbreaker.client.module.modules.ItemCountModule());
      this.coordinatesModule = a(new com.cheatbreaker.client.module.modules.CoordinatesModule());
      this.directionHud = a(new com.cheatbreaker.client.module.modules.DirectionHudModule());
      this.customTextModule = a(new com.cheatbreaker.client.module.modules.config.CustomTextModule());
      this.notifications = a(new com.cheatbreaker.client.module.modules.layout.NotificationsModule());
      this.cooldowns = a(new com.cheatbreaker.client.module.modules.render.CooldownsModule());
      this.screenshotModule = a(new ScreenshotModule());
      this.blockhitSound = a(new com.cheatbreaker.client.module.modules.animation.BlockhitSoundModule());
      this.xray = new com.cheatbreaker.client.module.module.XrayModule();
      this.xray.e(false);
   }

   private static <T extends com.cheatbreaker.client.module.Module> T a(T var0) {
      var0.c(false);
      var0.g(false);
      return (T)var0;
   }
}
