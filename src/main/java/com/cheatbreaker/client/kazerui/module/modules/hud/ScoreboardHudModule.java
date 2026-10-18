package com.cheatbreaker.client.kazerui.module.modules.hud;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;

public final class ScoreboardHudModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting numbers = this.a("numbers", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting hide = this.a("hideScoreboard", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting shadow = this.a("textShadow", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting border = this.a("border", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting message = this.a("displayToggleMessage", true);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting thickness = this.a("borderThickness", 0.5F, 0.5F, 3.0F);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting background = this.a("backgroundColor", 1342177280);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting header = this.a("headerColor", 1342177280);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting borderColor = this.a("borderColor", -1627389952);
   private final com.cheatbreaker.client.kazerui.module.settings.KeybindSetting toggle = this.b("toggleHideScoreboard");
   private boolean hidden;
   private boolean down;

   public ScoreboardHudModule() {
      super("SCOREBOARD", true);
      this.a(new com.cheatbreaker.client.kazerui.module.modules.hud.ScoreboardHudModule.a());
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a("generalOptions", var1x -> var1x.a(this.numbers, this.hide, this.shadow, this.thickness, this.border));
      var1.a("toggle", var1x -> var1x.a(this.toggle, this.message));
      var1.a("colorOptions", var1x -> var1x.a(this.background, this.header, this.borderColor));
   }

   public static boolean n() {
      com.cheatbreaker.client.kazerui.module.KazerModule var0 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.a("scoreboard");
      return var0 != null && var0.a();
   }

   @Override
   public void a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a var1) {
      if (var1.phase == com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b.START) {
         boolean var2 = this.a() && m().theWorld != null && m().currentScreen == null && this.toggle.b();
         if (var2 && !this.down) {
            this.hidden = !this.hidden;
            if (this.message.b()) {
               com.cheatbreaker.client.kazerui.gui.ToastNotifications.a(
                  com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("popups", this.hidden ? "scoreboardHidden" : "scoreboardDisplayed")
               );
            }
         }

         this.down = var2;
      }
   }

   private final class a extends com.cheatbreaker.client.kazerui.module.modulerender.HudElement {
      private String title;
      private final List<String> names = new ArrayList<>();
      private final List<String> scores = new ArrayList<>();
      private final List<Score> rows = new ArrayList<>();
      private final float[] scoreWidths = new float[15];
      private float titleWidth;
      private ScoreObjective cachedObjective;
      private Object cachedWorld;
      private Object cachedPlayer;
      private Object cachedFont;
      private int cachedTick = Integer.MIN_VALUE;
      private long cachedEpoch = Long.MIN_VALUE;
      private long cachedRevision = Long.MIN_VALUE;
      private boolean cachedPreview;
      private boolean prepared;

      a() {
         super(ScoreboardHudModule.this, 0.0F, 0.0F, com.cheatbreaker.client.kazerui.hud.HudAnchor.MIDDLE_RIGHT);
      }

      @Override
      public boolean l() {
         return !ScoreboardHudModule.this.hide.b() && !ScoreboardHudModule.this.hidden;
      }

      @Override
      public boolean a(boolean var1) {
         if (!ScoreboardHudModule.this.hide.b() && !ScoreboardHudModule.this.hidden) {
            ScoreObjective var2 = null;
            if (com.cheatbreaker.client.kazerui.module.modules.hud.ScoreboardHudModule.m().theWorld != null
               && com.cheatbreaker.client.kazerui.module.modules.hud.ScoreboardHudModule.m().thePlayer != null) {
               Scoreboard var3 = com.cheatbreaker.client.kazerui.module.modules.hud.ScoreboardHudModule.m().theWorld.getScoreboard();
               ScorePlayerTeam var4 = var3.getPlayersTeam(com.cheatbreaker.client.kazerui.module.modules.hud.ScoreboardHudModule.m().thePlayer.getName());
               int var5 = var4 == null ? -1 : var4.getChatFormat().getColorIndex();
               if (var5 >= 0) {
                  var2 = var3.getObjectiveInDisplaySlot(3 + var5);
               }

               if (var2 == null) {
                  var2 = var3.getObjectiveInDisplaySlot(1);
               }
            }

            int var13 = com.cheatbreaker.client.kazerui.module.modules.hud.ScoreboardHudModule.m().thePlayer == null
               ? 0
               : com.cheatbreaker.client.kazerui.module.modules.hud.ScoreboardHudModule.m().thePlayer.ticksExisted;
            long var14 = com.cheatbreaker.client.kazerui.RenderEpoch.a();
            com.cheatbreaker.client.kazerui.gui.components.MenuModel var6 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.d();
            long var7 = var6 == null ? 0L : var6.revision;
            FontRenderer var9 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a();
            if (this.prepared
               && this.cachedObjective == var2
               && this.cachedWorld == com.cheatbreaker.client.kazerui.module.modules.hud.ScoreboardHudModule.m().theWorld
               && this.cachedPlayer == com.cheatbreaker.client.kazerui.module.modules.hud.ScoreboardHudModule.m().thePlayer
               && this.cachedFont == var9
               && this.cachedTick == var13
               && this.cachedEpoch == var14
               && this.cachedRevision == var7
               && this.cachedPreview == var1) {
               return var2 != null || var1;
            } else {
               this.cachedObjective = var2;
               this.cachedWorld = com.cheatbreaker.client.kazerui.module.modules.hud.ScoreboardHudModule.m().theWorld;
               this.cachedPlayer = com.cheatbreaker.client.kazerui.module.modules.hud.ScoreboardHudModule.m().thePlayer;
               this.cachedFont = var9;
               this.cachedTick = var13;
               this.cachedEpoch = var14;
               this.cachedRevision = var7;
               this.cachedPreview = var1;
               this.prepared = true;
               this.names.clear();
               this.scores.clear();
               this.rows.clear();
               if (var2 == null) {
                  if (!var1) {
                     return false;
                  }

                  this.title = "§bKazer§r §lClient";

                  for (String var11 : Arrays.asList("Steve", "Alex", "Villager", "Enderman")) {
                     this.names.add(var11);
                     this.scores.add("§c0");
                  }
               } else {
                  this.title = var2.getDisplayName();
                  Scoreboard var15 = var2.getScoreboard();

                  for (Score var12 : var15.getSortedScores(var2)) {
                     if (var12.getPlayerName() != null && !var12.getPlayerName().startsWith("#")) {
                        this.rows.add(var12);
                     }
                  }

                  for (int var18 = Math.max(0, this.rows.size() - 15); var18 < this.rows.size(); var18++) {
                     Score var20 = this.rows.get(var18);
                     this.names.add(ScorePlayerTeam.formatPlayerName(var15.getPlayersTeam(var20.getPlayerName()), var20.getPlayerName()));
                     this.scores.add("§c" + var20.getScorePoints());
                  }
               }

               this.titleWidth = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(this.title);
               float var16 = this.titleWidth;
               float var19 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(": ");

               for (int var21 = 0; var21 < this.names.size(); var21++) {
                  this.scoreWidths[var21] = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(this.scores.get(var21));
                  var16 = Math.max(var16, com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(this.names.get(var21)) + var19 + this.scoreWidths[var21]);
               }

               if (ScoreboardHudModule.this.numbers.b() && var16 > this.titleWidth) {
                  var16 -= Math.min(var16 - this.titleWidth, 9.0F);
               } else if (ScoreboardHudModule.this.numbers.b()) {
                  var16 += 4.0F;
               }

               this.a(var16 + 2.0F, this.names.size() * com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.b() + 10);
               return true;
            }
         } else {
            return false;
         }
      }

      @Override
      public void b(boolean var1) {
         int var2 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.b();
         if (!this.names.isEmpty()) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(ScoreboardHudModule.this.header, 0.0F, 1.0F, this.f(), var2);
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(ScoreboardHudModule.this.background, 0.0F, var2 + 1, this.f(), this.g() - var2);
            if (ScoreboardHudModule.this.border.b()) {
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(ScoreboardHudModule.this.borderColor, 0.0F, 0.0F, this.f(), this.g(), ScoreboardHudModule.this.thickness.b());
            }
         }

         for (int var3 = 0; var3 < this.names.size(); var3++) {
            float var4 = this.g() - (var3 + 1) * var2;
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(this.names.get(var3), 2.0F, var4, -1, ScoreboardHudModule.this.shadow.b());
            if (!ScoreboardHudModule.this.numbers.b()) {
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(this.scores.get(var3), this.f() - this.scoreWidths[var3], var4, -1, ScoreboardHudModule.this.shadow.b());
            }
         }

         if (!this.names.isEmpty()) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(
               this.title, (this.f() - this.titleWidth) / 2.0F, this.g() - (this.names.size() + 1) * var2, -1, ScoreboardHudModule.this.shadow.b()
            );
         }
      }
   }
}
