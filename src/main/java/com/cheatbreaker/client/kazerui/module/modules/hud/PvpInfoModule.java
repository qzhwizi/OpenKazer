package com.cheatbreaker.client.kazerui.module.modules.hud;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import net.minecraft.entity.Entity;

public final class PvpInfoModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static com.cheatbreaker.client.kazerui.module.modules.hud.PvpInfoModule instance;
   private static final List<String> PREVIEW = Arrays.asList(
      "Attacks: 12", "Observed Hits: 9", "Hits Taken: 3", "Longest Combo: 4", "Health Lost: 8.0", "Health Recovered: 4.0"
   );
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting resetWorld = this.a("resetOnWorldChange", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting melee = this.a("meleeStatistics", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting health = this.a("healthStatistics", true);
   private int attacks;
   private int hitsTaken;
   private int observedHits;
   private int combo;
   private int longest;
   private int target = -1;
   private long attackAt;
   private float lost;
   private float recovered;
   private float lastHealth = Float.NaN;
   private Object world;
   private List<String> lines = Collections.emptyList();
   private boolean dirty = true;

   public PvpInfoModule() {
      super("PVP_INFO", false);
      instance = this;
      this.melee.a(() -> this.dirty = true);
      this.health.a(() -> this.dirty = true);
      this.a(
         new com.cheatbreaker.client.kazerui.module.modulerender.TextHudBase(
            this, 0.0F, 70.0F, com.cheatbreaker.client.kazerui.hud.HudAnchor.TOP_LEFT, com.cheatbreaker.client.kazerui.module.modulerender.RichTextHudBase.a(10, 18, 32, 40, 140, 250)
         ) {
            @Override
            protected String c(boolean var1) {
               return "";
            }

            @Override
            protected List<String> d(boolean var1) {
               return var1 ? com.cheatbreaker.client.kazerui.module.modules.hud.PvpInfoModule.PREVIEW : PvpInfoModule.this.lines;
            }
         }
      );
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(this.resetWorld, this.melee, this.health);
   }

   public static void a(Entity var0) {
      com.cheatbreaker.client.kazerui.module.modules.hud.PvpInfoModule var1 = instance;
      if (var1 != null && var1.a() && var0 != null) {
         var1.attacks++;
         var1.target = var0.getEntityId();
         var1.attackAt = System.currentTimeMillis();
         var1.dirty = true;
      }
   }

   public static void a(Entity var0, byte var1) {
      com.cheatbreaker.client.kazerui.module.modules.hud.PvpInfoModule var2 = instance;
      if (var2 != null && var2.a() && var1 == 2 && var0 != null) {
         if (var0 == m().thePlayer) {
            var2.hitsTaken++;
            var2.combo = 0;
            var2.dirty = true;
         } else if (var0.getEntityId() == var2.target && System.currentTimeMillis() - var2.attackAt < 500L) {
            var2.target = -1;
            var2.observedHits++;
            var2.combo++;
            var2.longest = Math.max(var2.longest, var2.combo);
            var2.dirty = true;
         }
      }
   }

   @Override
   public void a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a var1) {
      if (var1.phase == com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b.END && this.a()) {
         if (this.world != m().theWorld) {
            this.world = m().theWorld;
            this.lastHealth = Float.NaN;
            this.target = -1;
            this.combo = 0;
            if (this.resetWorld.b()) {
               this.attacks = this.hitsTaken = this.observedHits = this.longest = 0;
               this.lost = this.recovered = 0.0F;
            }

            this.dirty = true;
         }

         if (m().thePlayer == null) {
            this.lines = Collections.emptyList();
         } else {
            float var2 = m().thePlayer.getHealth();
            if (!Float.isNaN(this.lastHealth) && var2 != this.lastHealth) {
               if (var2 < this.lastHealth) {
                  this.lost = this.lost + (this.lastHealth - var2);
               } else {
                  this.recovered = this.recovered + (var2 - this.lastHealth);
               }

               this.dirty = true;
            }

            this.lastHealth = var2;
            if (System.currentTimeMillis() - this.attackAt > 1500L) {
               this.combo = 0;
            }

            if (this.dirty) {
               ArrayList var3 = new ArrayList(6);
               if (this.melee.b()) {
                  var3.add("Attacks: " + this.attacks);
                  var3.add("Observed Hits: " + this.observedHits);
                  var3.add("Hits Taken: " + this.hitsTaken);
                  var3.add("Longest Combo: " + this.longest);
               }

               if (this.health.b()) {
                  var3.add(String.format(Locale.ROOT, "Health Lost: %.1f", this.lost));
                  var3.add(String.format(Locale.ROOT, "Health Recovered: %.1f", this.recovered));
               }

               this.lines = Collections.unmodifiableList(var3);
               this.dirty = false;
            }
         }
      }
   }
}
