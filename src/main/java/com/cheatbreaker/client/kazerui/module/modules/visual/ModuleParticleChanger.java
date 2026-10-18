package com.cheatbreaker.client.kazerui.module.modules.visual;

import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.client.particle.EntityCrit2FX;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.particle.EntityEnchantmentTableParticleFX;
import net.minecraft.client.particle.EntityFlameFX;
import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.client.particle.EntitySpellParticleFX;

public final class ModuleParticleChanger extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static ModuleParticleChanger instance;
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting size = this.a("particleSize", 1.0F, 0.25F, 2.0F);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting amount = this.a("particleAmount", 100, 0, 100);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting critical = this.a("showCritical", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting sharpness = this.a("showSharpness", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting blocks = this.a("showBlockParticles", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting smoke = this.a("showSmoke", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting flame = this.a("showFlame", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting potion = this.a("showPotionParticles", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting portal = this.a("showPortal", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting other = this.a("showOtherParticles", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting recolor = this.a("overrideParticleColor", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting tint = this.a("particleColor", -1).b().c();
   private final int[] emission = new int[8];
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting[] visible = new com.cheatbreaker.client.kazerui.module.settings.ToggleSetting[]{
      this.critical, this.sharpness, this.blocks, this.smoke, this.flame, this.potion, this.portal, this.other
   };

   public ModuleParticleChanger() {
      super("PARTICLE_CHANGER", false);
      instance = this;
      this.n();
   }

   private void n() {
      for (com.cheatbreaker.client.kazerui.module.settings.AbstractSetting var2 : this.g()) {
         String var3 = var2.key;
         switch (var3) {
            case "particleSize":
               var2.a(() -> "Particle Size");
               break;
            case "particleAmount":
               var2.a(() -> "Particle Amount (%)");
               break;
            case "showCritical":
               var2.a(() -> "Show Critical / Sharpness");
               break;
            case "showSharpness":
               var2.a(() -> "Show Enchanting Particles");
               break;
            case "showBlockParticles":
               var2.a(() -> "Show Block / Item Particles");
               break;
            case "showSmoke":
               var2.a(() -> "Show Smoke");
               break;
            case "showFlame":
               var2.a(() -> "Show Flame");
               break;
            case "showPotionParticles":
               var2.a(() -> "Show Potion Particles");
               break;
            case "showPortal":
               var2.a(() -> "Show Portal Particles");
               break;
            case "showOtherParticles":
               var2.a(() -> "Show Other Particles");
               break;
            case "overrideParticleColor":
               var2.a(() -> "Override Particle Color");
               break;
            case "particleColor":
               var2.a(() -> "Particle Color");
         }
      }
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(this.size, this.amount);
      var1.a(this.critical, this.sharpness, this.blocks, this.smoke, this.flame, this.potion, this.portal, this.other);
      var1.a(this.recolor, var1x -> var1x.a(this.tint));
   }

   private int a(Object var1) {
      if (var1 instanceof EntityCrit2FX) {
         return 0;
      } else if (var1 instanceof EntityEnchantmentTableParticleFX) {
         return 1;
      } else if (var1 instanceof EntityDiggingFX || var1 instanceof EntityBreakingFX) {
         return 2;
      } else if (var1 instanceof EntitySmokeFX) {
         return 3;
      } else if (var1 instanceof EntityFlameFX) {
         return 4;
      } else if (var1 instanceof EntitySpellParticleFX) {
         return 5;
      } else {
         return var1 instanceof EntityPortalFX ? 6 : 7;
      }
   }

   public static boolean keep(Object var0) {
      ModuleParticleChanger var1 = instance;
      if (var0 == null) {
         return false;
      } else if (var1 != null && var1.a()) {
         int var2 = var1.a(var0);
         if (!var1.visible[var2].b()) {
            return false;
         } else {
            int var3 = var1.amount.c();
            if (var3 >= 100) {
               return true;
            } else {
               var1.emission[var2] = (var1.emission[var2] + var3) % 100;
               return var3 > 0 && var1.emission[var2] < var3;
            }
         }
      } else {
         return true;
      }
   }

   public static boolean active() {
      return instance != null && instance.a();
   }

   public static float scale(float var0, Object var1) {
      ModuleParticleChanger var2 = instance;
      return var2 != null && var2.a() ? var0 * var2.size.b() : var0;
   }

   public static float channel(float var0, Object var1, int var2) {
      ModuleParticleChanger var3 = instance;
      return var3 != null && var3.a() && var3.recolor.b() ? (var3.tint.e() >>> var2 & 0xFF) / 255.0F : var0;
   }
}
