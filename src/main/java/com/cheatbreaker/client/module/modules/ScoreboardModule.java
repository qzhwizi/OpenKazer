package com.cheatbreaker.client.module.modules;

import java.util.Collection;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

public class ScoreboardModule extends com.cheatbreaker.client.module.Module {
   public static com.cheatbreaker.client.module.ScoreboardRule rule = com.cheatbreaker.client.module.ScoreboardRule.SCOREBOARD;
   public com.cheatbreaker.client.settings.Setting removeNumbers;
   private final com.cheatbreaker.client.settings.Setting hideScoreboard;
   private final com.cheatbreaker.client.settings.Setting textShadow;
   private final com.cheatbreaker.client.settings.Setting border;
   private final com.cheatbreaker.client.settings.Setting borderThickness;
   private final com.cheatbreaker.client.settings.Setting displayToggleMessage;
   private final com.cheatbreaker.client.settings.Setting backgroundColor;
   private final com.cheatbreaker.client.settings.Setting borderColor;
   private ScoreObjective previewObjective;
   private ScoreObjective cachedObjective;
   private FontRenderer cachedFont;
   private int cachedTick = Integer.MIN_VALUE;
   private com.cheatbreaker.client.module.modules.ScoreboardDisplayData cachedData;

   public ScoreboardModule() {
      super("Scoreboard");
      this.b(com.cheatbreaker.client.gui.hudeditor.GuiAnchor.RIGHT_MIDDLE);
      this.b(-1.0F, 0.0F);
      this.removeNumbers = new com.cheatbreaker.client.settings.Setting(this, "Hide Numbers").a(true);
      this.hideScoreboard = new com.cheatbreaker.client.settings.Setting(this, "Hide Scoreboard").a(false);
      this.textShadow = new com.cheatbreaker.client.settings.Setting(this, "Text Shadow").a(false);
      this.borderThickness = new com.cheatbreaker.client.settings.Setting(this, "Border Thickness").a(Float.valueOf(0.5F)).a(0.0F, 4.0F);
      this.border = new com.cheatbreaker.client.settings.Setting(this, "Border").a(false);
      new com.cheatbreaker.client.settings.Setting(this, "label").a("Toggle");
      this.displayToggleMessage = new com.cheatbreaker.client.settings.Setting(this, "Display Toggle Message").a(true);
      new com.cheatbreaker.client.settings.Setting(this, "label").a("Color");
      this.backgroundColor = new com.cheatbreaker.client.settings.Setting(this, "Btackground Color").a(1342177280).a(Integer.MIN_VALUE, Integer.MAX_VALUE);
      this.borderColor = new com.cheatbreaker.client.settings.Setting(this, "Border Color").a(-1627389952).a(Integer.MIN_VALUE, Integer.MAX_VALUE);
      this.a(com.cheatbreaker.client.event.event.RenderHudEvent.class, this::a);
      this.a(com.cheatbreaker.client.event.event.RenderHudEditorEvent.class, this::a);
      this.a(com.cheatbreaker.client.event.event.ClientTickEvent.class, var1 -> this.cachedObjective = null);
      this.c(true);
   }

   private void a(com.cheatbreaker.client.event.event.RenderHudEditorEvent var1) {
      if (this.q() && !(Boolean)this.hideScoreboard.d()) {
         if (this.minecraft.theWorld.getScoreboard().getObjectiveInDisplaySlot(1) == null) {
            if (this.previewObjective == null) {
               Scoreboard var2 = new Scoreboard();
               this.previewObjective = new ScoreObjective(var2, "KazerClient", IScoreObjectiveCriteria.DUMMY);
               this.previewObjective.setDisplayName(EnumChatFormatting.AQUA + "Kazer" + EnumChatFormatting.WHITE + EnumChatFormatting.BOLD + "Client");
               var2.getValueFromObjective("Steve", this.previewObjective);
               var2.getValueFromObjective("Alex", this.previewObjective);
            }

            this.a(this.previewObjective, var1);
         }
      }
   }

   private void a(com.cheatbreaker.client.event.event.RenderHudEvent var1) {
      if (this.q() && !(Boolean)this.hideScoreboard.d()) {
         ScoreObjective var2 = this.minecraft.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
         if (var2 != null) {
            this.a(var2, var1);
         }
      }
   }

   private void a(ScoreObjective var1, com.cheatbreaker.client.event.event.RenderHudEvent var2) {
      FontRenderer var3 = this.minecraft.fontRendererObj;
      com.cheatbreaker.client.module.modules.ScoreboardDisplayData var4 = this.a(var1, var3);
      if (var4 != null && var4.names.length != 0) {
         boolean var5 = this.a();
         this.c(var4.right - (-2 + (var5 ? 14 : 0)), var4.names.length * var3.FONT_HEIGHT + 12);
         GL11.glPushMatrix();

         try {
            this.a(var2.a());
            GL11.glTranslatef(var5 ? -12.0F : 2.0F, this.height, 0.0F);
            this.b(var1, var3);
         } finally {
            GL11.glPopMatrix();
         }
      }
   }

   private com.cheatbreaker.client.module.modules.ScoreboardDisplayData a(ScoreObjective var1, FontRenderer var2) {
      int var3 = this.minecraft.thePlayer == null ? 0 : this.minecraft.thePlayer.ticksExisted;
      if (this.cachedObjective == var1 && this.cachedFont == var2 && this.cachedTick == var3) {
         return this.cachedData;
      } else {
         this.cachedObjective = var1;
         this.cachedFont = var2;
         this.cachedTick = var3;
         Scoreboard var4 = var1.getScoreboard();
         Collection var5 = var4.getSortedScores(var1);
         if (var5.size() > 15) {
            this.cachedData = null;
            return null;
         } else {
            String[] var6 = new String[var5.size()];
            String[] var7 = new String[var5.size()];
            int var8 = 0;

            for (Score var10 : (Iterable<Score>) var5) {
               var6[var8] = ScorePlayerTeam.formatPlayerName(var4.getPlayersTeam(var10.getPlayerName()), var10.getPlayerName());
               var7[var8++] = EnumChatFormatting.RED + "" + var10.getScorePoints();
            }

            return this.cachedData = new com.cheatbreaker.client.module.modules.ScoreboardDisplayData(var1.getDisplayName(), var6, var7, var2::getStringWidth);
         }
      }
   }

   private void b(ScoreObjective var1, FontRenderer var2) {
      com.cheatbreaker.client.module.modules.ScoreboardDisplayData var3 = this.a(var1, var2);
      if (var3 != null) {
         boolean var4 = this.a();
         boolean var5 = (Boolean)this.textShadow.d();
         int var6 = this.backgroundColor.a();
         int var7 = -2 + (var4 ? 14 : 0);

         for (int var8 = 0; var8 < var3.names.length; var8++) {
            int var9 = -(var8 + 1) * var2.FONT_HEIGHT;
            cn.kazer.client189.render.DrawRect.a(var7, var9, var3.right, var9 + var2.FONT_HEIGHT, var6);
            var2.drawString(var3.names[var8], var4 ? 16.0F : 0.0F, var9, -1, var5);
            if (!var4) {
               var2.drawString(var3.numbers[var8], var3.right - var3.numberWidths[var8] - 2, var9, -1, var5);
            }

            if (var8 == var3.names.length - 1) {
               cn.kazer.client189.render.DrawRect.a(var7, var9 - var2.FONT_HEIGHT - 1, var3.right, var9 - 1, var6);
               cn.kazer.client189.render.DrawRect.a(var7, var9 - 1, var3.right, var9, var6);
               var2.drawString(var3.title, var3.width / 2 - var3.titleWidth / 2 + (var4 ? 14 : 0), var9 - var2.FONT_HEIGHT, -1, var5);
            }
         }
      }
   }

   private boolean a() {
      return rule == com.cheatbreaker.client.module.ScoreboardRule.SCOREBOARD ? (Boolean)this.removeNumbers.d() : false;
   }
}
