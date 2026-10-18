package com.cheatbreaker.client.kazerui.module.modules.hud;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public final class ComboModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private int combo;
   private int target = -1;
   private int attackTick = -100;
   private int confirmedTick = -100;
   private int displayed = Integer.MIN_VALUE;
   private String cached;
   private String example;

   public ComboModule() {
      super("COMBO", false);
      this.a(
         new com.cheatbreaker.client.kazerui.module.modulerender.RichTextHudBase(
            this, 0.0F, 0.0F, com.cheatbreaker.client.kazerui.hud.HudAnchor.TOP_RIGHT, com.cheatbreaker.client.kazerui.module.modulerender.RichTextHudBase.a(10, 18, 22, 46, 56, 62)
         ) {
            @Override
            protected String c(boolean var1) {
               if (var1) {
                  if (ComboModule.this.example == null) {
                     ComboModule.this.example = ComboModule.this.a("combo", new Object[]{3});
                  }

                  return ComboModule.this.example;
               } else {
                  if (ComboModule.this.displayed != ComboModule.this.combo) {
                     ComboModule.this.displayed = ComboModule.this.combo;
                     ComboModule.this.cached = ComboModule.this.a(ComboModule.this.combo == 0 ? "noCombo" : "combo", new Object[]{ComboModule.this.combo});
                  }

                  return ComboModule.this.cached;
               }
            }
         }
      );
   }

   public void a(Entity var1) {
      if (this.a() && m().thePlayer != null) {
         this.target = var1.getEntityId();
         this.attackTick = m().thePlayer.ticksExisted;
      }
   }

   public void a(Entity var1, byte var2) {
      if (this.a() && m().thePlayer != null && var2 == 2) {
         if (var1 == m().thePlayer) {
            this.combo = 0;
         } else {
            if (var1 instanceof EntityPlayer
               && var1.getEntityId() == this.target
               && m().thePlayer.ticksExisted - this.attackTick <= 4
               && this.attackTick != this.confirmedTick) {
               this.confirmedTick = this.attackTick;
               this.combo++;
            }
         }
      }
   }

   @Override
   public void a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a var1) {
      if (var1.phase == com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b.END) {
         if (this.a() && m().thePlayer != null) {
            if (m().thePlayer.hurtTime > 0 && m().thePlayer.hurtTime == m().thePlayer.maxHurtTime
               || m().thePlayer.ticksExisted - this.attackTick >= 80) {
               this.combo = 0;
            }
         } else {
            this.combo = 0;
            this.target = -1;
         }
      }
   }
}
