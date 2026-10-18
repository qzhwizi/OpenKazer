package com.cheatbreaker.client;

import com.cheatbreaker.client.module.ModuleManager;
import com.cheatbreaker.client.util.security.RuntimeGuard;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.Stack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.SkinManager.SkinAvailableCallback;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;

public class CheatBreakerClient implements SkinAvailableCallback {
   public static com.cheatbreaker.client.CheatBreakerClient instance;
   public static byte[] processBytesAuth = "Decencies".getBytes();
   public List<com.cheatbreaker.client.settings.Profile> profiles;
   public com.cheatbreaker.client.settings.Profile activeProfile;
   public com.cheatbreaker.client.settings.GlobalSettings globalSettings;
   public ModuleManager moduleManager;
   public com.cheatbreaker.client.settings.ConfigManager configManager;
   public com.cheatbreaker.client.event.EventBus eventBus;
   public com.cheatbreaker.client.kazerui.OwnedUi ownedUi;
   public List<com.cheatbreaker.client.util.ServerStatusEntry> statusServers;
   public List<ResourceLocation> presetLocations;
   public com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer playBold22px;
   public com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer playRegular22px;
   public com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer ubuntuMedium16px;
   public com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer playBold18px;
   public com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer robotoRegular24px;
   public com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer playRegular18px;
   public com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer playRegular14px;
   public com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer playRegular16px;
   public com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer robotoRegular13px;
   public com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer robotoBold14px;
   public com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer playRegular12px;
   private static final ResourceLocation playRegular = new ResourceLocation("client/font/Play-Regular.ttf");
   private static final ResourceLocation playBold = new ResourceLocation("client/font/Play-Bold.ttf");
   private static final ResourceLocation robotoRegular = new ResourceLocation("client/font/Roboto-Regular.ttf");
   private static final ResourceLocation robotoBold = new ResourceLocation("client/font/Roboto-Bold.ttf");
   private static final ResourceLocation ubuntuMedium = new ResourceLocation("client/font/Ubuntu-M.ttf");
   public com.cheatbreaker.client.network.ServerDataHandler netHandler;
   public com.cheatbreaker.client.util.renderer.TitleRenderer titleManager;
   public com.cheatbreaker.client.util.colorutil.WorldBorderManager borderManager;
   public long startTime;
   public List<Session> sessions;
   private List<com.cheatbreaker.client.util.playerskin.PlayerSkin> cosmetics = new ArrayList<>();
   private String gitCommit = "?";
   private String gitCommitId = "?";
   private String gitBranch = "?";
   private boolean consoleAllowed;
   private List<String> consoleLines;
   private final com.cheatbreaker.client.util.PresenceClient presenceClient;
   private final Map<String, ResourceLocation> playerSkins = new HashMap<>();

   public boolean a() {
      return true;
   }

   public CheatBreakerClient() {
      RuntimeGuard.a();
      this.presetLocations = new ArrayList<>();
      this.cosmetics = new ArrayList<>();
      this.sessions = new ArrayList<>();
      this.statusServers = new ArrayList<>();
      this.profiles = new ArrayList<>();
      this.consoleLines = new ArrayList<>();
      this.startTime = System.currentTimeMillis();
      System.out.println("[Kazer] Starting Kazer Client setup");
      this.P();
      System.out.println("[KC] Created default configuration presets");
      instance = this;
      this.globalSettings = new com.cheatbreaker.client.settings.GlobalSettings();
      System.out.println("[KC] Created settings");
      this.eventBus = new com.cheatbreaker.client.event.EventBus();
      System.out.println("[KC] Created EventBus");
      this.moduleManager = new ModuleManager();
      System.out.println("[KC] Created Mod Manager");
      this.netHandler = new com.cheatbreaker.client.network.ServerDataHandler();
      System.out.println("[KC] Created Net Handler");
      this.titleManager = new com.cheatbreaker.client.util.renderer.TitleRenderer();
      this.cosmetics.add(new com.cheatbreaker.client.util.playerskin.PlayerSkin("Steve", "Kazer Cape", 1.0F, true, "client/defaults/cb.png"));
      this.cosmetics.add(new com.cheatbreaker.client.util.playerskin.PlayerSkin("Steve", "Kazer Black Cape", 1.0F, false, "client/defaults/cb_black.png"));
      this.presenceClient = new com.cheatbreaker.client.util.PresenceClient();
   }

   public <T> void a(Queue<T> var1) {
      Stack<T> var2 = new Stack<>();

      while (!var1.isEmpty()) {
         var2.push(var1.poll());
      }

      while (!var2.isEmpty()) {
         var1.add(var2.pop());
      }
   }

   public void b() {
      Map var1 = com.cheatbreaker.client.kazerui.OwnedUi.a(this);
      this.O();
      System.out.println("[KC] Loaded all fonts");
      this.Q();
      System.out.println("[KC] Loaded " + this.profiles.size() + " custom profiles");
      (this.configManager = new com.cheatbreaker.client.settings.ConfigManager()).b();
      this.ownedUi = new com.cheatbreaker.client.kazerui.OwnedUi(this, var1);
      this.eventBus.a(com.cheatbreaker.client.event.event.PluginMessageEvent.class, this.netHandler::a);
      this.eventBus.a(com.cheatbreaker.client.event.event.RenderHudEvent.class, this.titleManager::a);
      this.eventBus.a(com.cheatbreaker.client.event.event.ClientTickEvent.class, this.titleManager::a);
      this.eventBus.a(com.cheatbreaker.client.event.event.ClientTickEvent.class, var1x -> this.presenceClient.a());
      this.eventBus.a(com.cheatbreaker.client.event.event.KeyPressEvent.class, var1x -> {
         if (var1x.a() == this.globalSettings.openMenu.getKeyCode() && Minecraft.getMinecraft().currentScreen == null) {
            Minecraft.getMinecraft().displayGuiScreen(this.ownedUi.c(null));
         }
      });
      new com.cheatbreaker.client.util.serverstatus.ServerStatusPoller().start();
      this.R();
      this.borderManager = new com.cheatbreaker.client.util.colorutil.WorldBorderManager();
      LogManager.getLogger()
         .info(
            "[KC] EventBus consumers: GuiDrawEvent={} TickEvent={} KeyboardEvent={} (profile {})",
            new Object[]{
               this.eventBus.a(com.cheatbreaker.client.event.event.RenderHudEvent.class),
               this.eventBus.a(com.cheatbreaker.client.event.event.ClientTickEvent.class),
               this.eventBus.a(com.cheatbreaker.client.event.event.KeyPressEvent.class),
               this.activeProfile == null ? "<none>" : this.activeProfile.c()
            }
         );
   }

   private void O() {
      this.playBold22px = new com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer(playBold, 22.0F);
      this.playRegular22px = new com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer(playRegular, 22.0F);
      this.playRegular18px = new com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer(playRegular, 18.0F);
      this.playRegular14px = new com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer(playRegular, 14.0F);
      this.playRegular12px = new com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer(playRegular, 12.0F);
      this.playRegular16px = new com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer(playRegular, 16.0F);
      this.playBold18px = new com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer(playBold, 18.0F);
      this.ubuntuMedium16px = new com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer(ubuntuMedium, 16.0F);
      this.robotoRegular13px = new com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer(robotoRegular, 13.0F);
      this.robotoBold14px = new com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer(robotoBold, 14.0F);
      this.robotoRegular24px = new com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer(robotoRegular, 24.0F);
   }

   private void P() {
      File var1 = com.cheatbreaker.client.settings.ConfigManager.profilesDir;
      if (var1.exists() || var1.mkdirs()) {
         for (ResourceLocation var3 : this.presetLocations) {
            File var4 = new File(var1, var3.getResourcePath().replaceAll("([a-zA-Z0-9/]+)/", ""));
            if (!var4.exists()) {
               try {
                  InputStream var5 = Minecraft.getMinecraft().getResourceManager().getResource(var3).getInputStream();
                  Files.copy(var5, var4.toPath());
                  var5.close();
               } catch (IOException var6) {
                  var6.printStackTrace();
               }
            }
         }
      }
   }

   private void Q() {
      this.profiles.add(new com.cheatbreaker.client.settings.Profile("default", false));
      File var1 = com.cheatbreaker.client.settings.ConfigManager.profilesDir;
      File[] var2;
      if (var1.exists() && var1.isDirectory() && (var2 = var1.listFiles()) != null) {
         for (File var6 : var2) {
            if (var6.getName().endsWith(".cfg")) {
               this.profiles.add(new com.cheatbreaker.client.settings.Profile(var6.getName().replace(".cfg", ""), true));
            }
         }
      }
   }

   private String c(String var1) {
      File var2 = com.cheatbreaker.client.settings.ConfigManager.profilesDir;
      return (var2.exists() || var2.mkdirs()) && new File(var2 + File.separator + var1 + ".cfg").exists() ? this.c(var1 + "1") : var1;
   }

   public void c() {
      if (this.activeProfile == this.profiles.get(0)) {
         com.cheatbreaker.client.settings.Profile var1 = new com.cheatbreaker.client.settings.Profile(this.c("Profile 1"), true);
         this.activeProfile = var1;
         this.profiles.add(var1);
         this.configManager.a();
      }
   }

   public boolean d() {
      for (com.cheatbreaker.client.module.Module var2 : this.moduleManager.staffModules) {
         if (var2.f()) {
            return true;
         }
      }

      return false;
   }

   public float e() {
      switch (Minecraft.getMinecraft().gameSettings.guiScale) {
         case 0:
            return 2.0F;
         case 1:
            return 0.5F;
         case 2:
         default:
            return 1.0F;
         case 3:
            return 1.5F;
      }
   }

   public void a(String var1) {
      this.a(var1, 1.0F);
   }

   public void a(String var1, float var2) {
      if (!(Boolean)this.globalSettings.muteCheatBreakerSounds.d()) {
         Minecraft.getMinecraft().getSoundHandler().playSound(new PositionedSoundRecord(new ResourceLocation(var1), var2, 1.0F, 0.0F, 0.0F, 0.0F));
      }
   }

   public ResourceLocation a(String var1, String var2) {
      ResourceLocation var3 = this.playerSkins.getOrDefault(var1, new ResourceLocation("client/heads/" + var1 + ".png"));
      if (!this.playerSkins.containsKey(var1)) {
         this.playerSkins.put(var1, var3);
      }

      return var3;
   }

   public String f() {
      return "Kazer-Client";
   }

   public String g() {
      return "Kazer-Binary";
   }

   public void skinAvailable(Type var1, ResourceLocation var2, MinecraftProfileTexture var3) {
   }

   private void R() {
      try {
         ResourceLocation var1 = new ResourceLocation("client/properties/app.properties");
         Properties var2 = new Properties();
         InputStream var3 = Minecraft.getMinecraft().getResourceManager().getResource(var1).getInputStream();
         if (var3 == null) {
            this.gitCommit = "?";
            this.gitCommitId = "?";
            this.gitBranch = "?";
            return;
         }

         var2.load(var3);
         this.gitCommit = var2.getProperty("git.commit.id.abbrev");
         this.gitCommitId = var2.getProperty("git.commit.id");
         this.gitBranch = var2.getProperty("git.branch");
      } catch (IOException var4) {
         var4.printStackTrace();
      }
   }

   public void b(String var1) {
      this.cosmetics.removeIf(var1x -> var1x.d().equals(var1));
      this.cosmetics.removeIf(var1x -> var1x.d().equals(var1));
   }

   public List<com.cheatbreaker.client.settings.Profile> h() {
      return this.profiles;
   }

   public com.cheatbreaker.client.settings.Profile i() {
      return this.activeProfile;
   }

   public com.cheatbreaker.client.settings.GlobalSettings j() {
      return this.globalSettings;
   }

   public ModuleManager k() {
      return this.moduleManager;
   }

   public com.cheatbreaker.client.settings.ConfigManager l() {
      return this.configManager;
   }

   public com.cheatbreaker.client.event.EventBus m() {
      return this.eventBus;
   }

   public com.cheatbreaker.client.kazerui.OwnedUi n() {
      return this.ownedUi;
   }

   public List<com.cheatbreaker.client.util.ServerStatusEntry> o() {
      return this.statusServers;
   }

   public List<ResourceLocation> p() {
      return this.presetLocations;
   }

   public com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer q() {
      return this.playBold22px;
   }

   public com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer r() {
      return this.playRegular22px;
   }

   public com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer s() {
      return this.ubuntuMedium16px;
   }

   public com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer t() {
      return this.playBold18px;
   }

   public com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer u() {
      return this.robotoRegular24px;
   }

   public com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer v() {
      return this.playRegular18px;
   }

   public com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer w() {
      return this.playRegular14px;
   }

   public com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer x() {
      return this.playRegular16px;
   }

   public com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer y() {
      return this.robotoRegular13px;
   }

   public com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer z() {
      return this.robotoBold14px;
   }

   public com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer A() {
      return this.playRegular12px;
   }

   public com.cheatbreaker.client.network.ServerDataHandler B() {
      return this.netHandler;
   }

   public com.cheatbreaker.client.util.renderer.TitleRenderer C() {
      return this.titleManager;
   }

   public com.cheatbreaker.client.util.colorutil.WorldBorderManager D() {
      return this.borderManager;
   }

   public long E() {
      return this.startTime;
   }

   public List<Session> F() {
      return this.sessions;
   }

   public List<com.cheatbreaker.client.util.playerskin.PlayerSkin> G() {
      return this.cosmetics;
   }

   public String H() {
      return this.gitCommit;
   }

   public String I() {
      return this.gitCommitId;
   }

   public String J() {
      return this.gitBranch;
   }

   public List<String> K() {
      return this.consoleLines;
   }

   public com.cheatbreaker.client.util.PresenceClient L() {
      return this.presenceClient;
   }

   public Map<String, ResourceLocation> M() {
      return this.playerSkins;
   }

   public static com.cheatbreaker.client.CheatBreakerClient N() {
      return instance;
   }

   public void a(boolean var1) {
      this.consoleAllowed = var1;
   }
}
