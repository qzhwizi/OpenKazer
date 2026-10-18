package com.cheatbreaker.client.kazerui.module.modules.mechanic;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;

public final class FovModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static com.cheatbreaker.client.kazerui.module.modules.mechanic.FovModule instance;
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting staticFOV = this.a("staticFOV", false);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting defaultFov = this.a("defaultFov", 70, 30, 110);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting smoothFov = this.a("smoothFov", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting dynamicFlying = this.a("dynamicFlying", true);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting flyingFov = this.a("flyingFov", 70, 30, 110);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting flyingModifier = this.a("flyingModifier", 1.0F, 0.0F, 5.0F).a(2);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting flyingMin = this.a("flyingMin", -10.0F, -200.0F, 200.0F).a(2);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting flyingMax = this.a("flyingMax", 10.0F, -200.0F, 200.0F).a(2);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting dynamicEffects = this.a("dynamicEffects", true);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting slownessFOV = this.a("slownessFOV", 70, 30, 110);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting speedFOV = this.a("speedFOV", 70, 30, 110);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting speedTwoFOV = this.a("speedTwoFOV", 70, 30, 110);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting movementModifier = this.a("movementModifier", 1.0F, 0.0F, 5.0F).a(2);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting movementMin = this.a("movementMin", -10.0F, -200.0F, 200.0F).a(2);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting movementMax = this.a("movementMax", 10.0F, -200.0F, 200.0F).a(2);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting dynamicSprint = this.a("dynamicSprint", true);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting sprintingFOV = this.a("sprintingFOV", 70, 30, 110);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting sprintModifier = this.a("sprintModifier", 1.0F, 0.0F, 5.0F).a(2);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting sprintMin = this.a("sprintMin", -10.0F, -200.0F, 200.0F).a(2);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting sprintMax = this.a("sprintMax", 10.0F, -200.0F, 200.0F).a(2);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting dynamicBow = this.a("dynamicBow", true);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting aimingModifier = this.a("aimingModifier", 1.0F, 0.0F, 5.0F).a(2);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting aimingMin = this.a("aimingMin", -10.0F, -200.0F, 200.0F).a(2);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting aimingMax = this.a("aimingMax", 10.0F, -200.0F, 200.0F).a(2);
   private long lastUpdate = -1L;
   private float field_6255 = 70.0F;
   private float field_6256;
   private boolean handPass;

   public FovModule() {
      super("FOV", false);
      instance = this;
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a("generalOptions", var1x -> {
         var1x.a(this.staticFOV, this.defaultFov);
         var1x.a(this.smoothFov).a(this.staticFOV::b);
      });
      var1.a(this.dynamicBow, var1x -> var1x.a(this.aimingModifier, this.aimingMin, this.aimingMax)).a(this.staticFOV::b);
      var1.a(
            this.dynamicEffects, var1x -> var1x.a(this.speedFOV, this.speedTwoFOV, this.slownessFOV, this.movementModifier, this.movementMin, this.movementMax)
         )
         .a(this.staticFOV::b);
      var1.a(this.dynamicSprint, var1x -> var1x.a(this.sprintingFOV, this.sprintModifier, this.sprintMin, this.sprintMax)).a(this.staticFOV::b);
      var1.a(this.dynamicFlying, var1x -> var1x.a(this.flyingFov, this.flyingModifier, this.flyingMin, this.flyingMax)).a(this.staticFOV::b);
   }

   public static float a(float var0) {
      com.cheatbreaker.client.kazerui.module.modules.mechanic.FovModule var1 = instance;
      return var1 != null && var1.a() ? (var1.n() ? 1.0F : var1.p()) : var0;
   }

   public static float b(float var0) {
      com.cheatbreaker.client.kazerui.module.modules.mechanic.FovModule var1 = instance;
      if (var1 != null && var1.a()) {
         float var2 = var1.o();
         float var3 = m().gameSettings.fovSetting;
         return var3 <= 0.0F ? var2 : var0 * (var2 / var3);
      } else {
         return var0;
      }
   }

   private boolean n() {
      return this.staticFOV.b();
   }

   private float o() {
      float var1 = m().gameSettings.fovSetting;
      if (this.n()) {
         var1 = this.defaultFov.c();
      } else {
         boolean var2 = false;
         EntityPlayerSP var3 = m().thePlayer;
         if (var3 != null) {
            if (var3.isSprinting() && this.dynamicSprint.b()) {
               var1 = this.sprintingFOV.c();
               var2 = true;
            }

            if (var3.capabilities.isFlying && this.dynamicFlying.b()) {
               var1 = this.flyingFov.c();
               var2 = true;
            }

            if (var3.isPotionActive(Potion.moveSlowdown) && this.dynamicEffects.b()) {
               var1 = this.slownessFOV.c();
               var2 = true;
            }

            if (var3.isPotionActive(Potion.moveSpeed) && this.dynamicEffects.b()) {
               int var4 = var3.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
               var1 = (var4 == 0 ? this.speedFOV : this.speedTwoFOV).c();
               var2 = true;
            }
         }

         if (!var2) {
            var1 = this.defaultFov.c();
         }
      }

      if (this.field_6256 != var1) {
         this.field_6256 = var1;
         this.lastUpdate = -1L;
      }

      if (!this.staticFOV.b() && this.smoothFov.b() && this.field_6255 != this.field_6256) {
         long var6 = Minecraft.getSystemTime();
         long var7 = this.lastUpdate == -1L ? 1L : Math.max(1L, var6 - this.lastUpdate);
         if (q()) {
            var7 = 0L;
         }

         this.lastUpdate = var6;
         this.field_6255 = this.field_6255 + (this.field_6256 - this.field_6255) * MathHelper.clamp_float(0.005F * (float)var7, 0.0F, 1.0F);
         if (Math.abs(this.field_6256 - this.field_6255) < 1.0E-4F) {
            this.field_6255 = this.field_6256;
         }
      } else {
         this.field_6255 = this.field_6256;
      }

      return this.field_6255;
   }

   private float p() {
      EntityPlayerSP var1 = m().thePlayer;
      if (var1 == null) {
         return 1.0F;
      } else {
         boolean var2 = var1.capabilities.isFlying;
         boolean var3 = var1.isSprinting();
         double var4 = var1.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
         float var6 = var1.capabilities.getWalkSpeed();
         float var7 = 1.0F;
         if (var2 && this.dynamicFlying.b()) {
            var7 *= 1.0F + MathHelper.clamp_float(0.1F * this.flyingModifier.b(), this.flyingMin.b(), this.flyingMax.b());
         }

         float var8 = (float)((var4 / var6 + 1.0) / 2.0);
         float var9 = (float)var4;
         float var10 = Math.abs((var9 / (var3 ? 1.3F : 1.0F) - var6) / (var9 - var6));
         double var11 = !this.dynamicEffects.b()
            ? 0.0
            : MathHelper.clamp_float(var10 * (var8 - 1.0F) * this.movementModifier.b(), this.movementMin.b(), this.movementMax.b());
         if (var3 && this.dynamicSprint.b()) {
            var11 += MathHelper.clamp_float((1.0F - var10) * (var8 - 1.0F) * this.sprintModifier.b(), this.sprintMin.b(), this.sprintMax.b());
         }

         var7 = (float)(var7 * (1.0 + var11));
         if (var6 == 0.0F || Float.isNaN(var7) || Float.isInfinite(var7)) {
            var7 = 1.0F;
         }

         if (this.dynamicBow.b()) {
            ItemStack var13 = !var1.isUsingItem() ? null : var1.getItemInUse();
            if (var13 != null && var13.getItem() == Items.bow) {
               float var14 = var1.getItemInUseDuration() / 20.0F;
               float var16;
               var14 = var14 > 1.0F ? 1.0F : (var16 = var14 * var14);
               var7 *= 1.0F - MathHelper.clamp_float(var14 * 0.15F * this.aimingModifier.b(), this.aimingMin.b(), this.aimingMax.b());
            }
         }

         return var7;
      }
   }

   private static boolean q() {
      com.cheatbreaker.client.kazerui.module.KazerModule var0 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.a("rewind");
      return var0 == null ? false : false;
   }
}
