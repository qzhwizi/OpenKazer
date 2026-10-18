package com.cheatbreaker.client.kazerui.module.modules.hud;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public final class ReachDisplayModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting reverse = this.a("reverseOrder", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting hideZero = this.a("hideZero", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting highlight = this.a("highlightAttackablePlayers", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting highlightColor = this.a("highlightColor", 1291910912);
   private final DecimalFormat format = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.ENGLISH));
   private int target = -1;
   private int attackTick = -100;
   private double pending;
   private double distance;
   private double displayed = Double.NaN;
   private boolean displayedReverse;
   private String cached;
   private String blocks;
   private String label;

   public ReachDisplayModule() {
      super("REACH_DISPLAY", false);
      this.a(
         new com.cheatbreaker.client.kazerui.module.modulerender.RichTextHudBase(
            this, 0.0F, 0.0F, com.cheatbreaker.client.kazerui.hud.HudAnchor.TOP_LEFT, com.cheatbreaker.client.kazerui.module.modulerender.RichTextHudBase.a(10, 18, 22, 40, 65, 72)
         ) {
            @Override
            protected String c(boolean var1) {
               if (ReachDisplayModule.this.hideZero.b() && ReachDisplayModule.this.distance == 0.0) {
                  return null;
               } else {
                  if (ReachDisplayModule.this.displayed != ReachDisplayModule.this.distance || ReachDisplayModule.this.displayedReverse != ReachDisplayModule.this.reverse.b()) {
                     if (ReachDisplayModule.this.blocks == null) {
                        ReachDisplayModule.this.blocks = com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("shared_info", "blocks");
                        String var2 = ReachDisplayModule.this.blocks.trim();
                        ReachDisplayModule.this.label = var2.isEmpty() ? "Blocks" : Character.toUpperCase(var2.charAt(0)) + var2.substring(1);
                     }

                     ReachDisplayModule.this.displayed = ReachDisplayModule.this.distance;
                     ReachDisplayModule.this.displayedReverse = ReachDisplayModule.this.reverse.b();
                     String var3 = ReachDisplayModule.this.format.format(ReachDisplayModule.this.distance);
                     ReachDisplayModule.this.cached = ReachDisplayModule.this.displayedReverse ? ReachDisplayModule.this.label + ": " + var3 : var3 + ReachDisplayModule.this.blocks;
                  }

                  return ReachDisplayModule.this.cached;
               }
            }
         }
      );
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a("generalOptions", var1x -> {
         var1x.a(this.reverse, this.hideZero);
         var1x.a(this.highlight, var1xx -> var1xx.a(this.highlightColor));
      });
   }

   public void a(Entity var1) {
      if (this.a() && m().thePlayer != null) {
         Vec3 var2 = m().thePlayer.getPositionEyes(1.0F);
         if (m().objectMouseOver != null && m().objectMouseOver.entityHit == var1 && m().objectMouseOver.hitVec != null) {
            this.target = var1.getEntityId();
            this.attackTick = m().thePlayer.ticksExisted;
            this.pending = var2.distanceTo(m().objectMouseOver.hitVec);
         }
      }
   }

   public void a(Entity var1, byte var2) {
      if (this.a() && m().thePlayer != null && var2 == 2 && var1.getEntityId() == this.target && m().thePlayer.ticksExisted - this.attackTick <= 4) {
         this.distance = Math.min(3.0, this.pending);
      }
   }

   @Override
   public void a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a var1) {
      if (var1.phase == com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b.END
         && (m().thePlayer == null || !this.a() || m().thePlayer.ticksExisted - this.attackTick >= 80)) {
         this.distance = 0.0;
         this.target = -1;
      }
   }
}
