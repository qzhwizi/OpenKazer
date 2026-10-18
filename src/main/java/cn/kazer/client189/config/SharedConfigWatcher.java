package cn.kazer.client189.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public final class SharedConfigWatcher {
   private final Path gameDirectory;
   private cn.kazer.client189.hud.FpsHudOverlay fps;
   private cn.kazer.client189.hud.CpsHudModule cps;
   private cn.kazer.client189.hud.KeystrokesHudModule keystrokes;
   private cn.kazer.client189.hud.PotionEffectsHudModule potions;
   private long globalStamp = Long.MIN_VALUE;
   private long profileStamp = Long.MIN_VALUE;
   private long animationStamp = Long.MIN_VALUE;
   private String activeProfile;
   private int ticks;

   public SharedConfigWatcher(Path var1) {
      this.gameDirectory = var1;
   }

   @SubscribeEvent
   public void onClientTick(ClientTickEvent var1) {
      if (var1.phase == Phase.END && ++this.ticks % 100 == 0) {
         if (Minecraft.getMinecraft().thePlayer != null) {
            this.a(false);
         }
      }
   }

   public void a(boolean var1) {
      try {
         Path var2 = cn.kazer.client189.config.ClientConfigPaths.b(this.gameDirectory);
         Path var3 = cn.kazer.client189.config.ClientConfigPaths.c(this.gameDirectory);
         long var4 = a(var2);
         cn.kazer.client189.config.IniConfigFile var6 = cn.kazer.client189.config.IniConfigFile.a(var2);
         String var7 = var6.a("", "ActiveProfile");
         if (var7 == null || var7.trim().isEmpty()) {
            var7 = "default";
         }

         Path var8 = var7.equalsIgnoreCase("default") ? null : cn.kazer.client189.config.ClientConfigPaths.a(this.gameDirectory, var7);
         long var9 = var8 == null ? -1L : a(var8);
         long var11 = a(var3);
         if (var1 || var4 != this.globalStamp || var9 != this.profileStamp || !var7.equals(this.activeProfile)) {
            cn.kazer.client189.config.IniConfigFile var13 = var8 == null ? null : cn.kazer.client189.config.IniConfigFile.a(var8);
            cn.kazer.client189.hud.FpsHudOverlay var14 = new cn.kazer.client189.hud.FpsHudOverlay();
            cn.kazer.client189.hud.CpsHudModule var15 = new cn.kazer.client189.hud.CpsHudModule();
            cn.kazer.client189.hud.KeystrokesHudModule var16 = new cn.kazer.client189.hud.KeystrokesHudModule();
            cn.kazer.client189.hud.PotionEffectsHudModule var17 = new cn.kazer.client189.hud.PotionEffectsHudModule();
            var14.a(var13);
            var15.a(var13);
            var16.a(var13);
            var17.a(var13);
            if (this.fps != null) {
               MinecraftForge.EVENT_BUS.unregister(this.fps);
            }

            if (this.cps != null) {
               MinecraftForge.EVENT_BUS.unregister(this.cps);
            }

            if (this.keystrokes != null) {
               MinecraftForge.EVENT_BUS.unregister(this.keystrokes);
            }

            if (this.potions != null) {
               MinecraftForge.EVENT_BUS.unregister(this.potions);
            }

            this.fps = var14;
            this.cps = var15;
            this.keystrokes = var16;
            this.potions = var17;
            MinecraftForge.EVENT_BUS.register(this.fps);
            MinecraftForge.EVENT_BUS.register(this.cps);
            MinecraftForge.EVENT_BUS.register(this.keystrokes);
            MinecraftForge.EVENT_BUS.register(this.potions);
            this.globalStamp = var4;
            this.profileStamp = var9;
            this.activeProfile = var7;
            System.out.println("[KC 1.8.9] Shared profile: " + var7);
         }

         if (var1 || var11 != this.animationStamp) {
            String var19 = cn.kazer.client189.config.IniConfigFile.a(var3).a("", "BlockHitAnimation");
            cn.kazer.client189.animation.OldAnimationsToggle.a(var19 == null || Boolean.parseBoolean(var19));
            this.animationStamp = var11;
         }
      } catch (IllegalArgumentException | IOException var18) {
         System.err.println("[KC 1.8.9] Could not reload shared config: " + var18.getMessage());
      }
   }

   private static long a(Path var0) throws IOException {
      return Files.exists(var0) ? Files.getLastModifiedTime(var0).toMillis() : -1L;
   }
}
