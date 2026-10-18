package com.cheatbreaker.client.module.modules.camera;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MathHelper;

public class FreelookModule extends com.cheatbreaker.client.module.Module {
   private static final String THIRD = "Third";
   private static final String REVERSE = "Reverse";
   private static final String FIRST = "First";
   private final com.cheatbreaker.client.settings.Setting lookView;
   private final com.cheatbreaker.client.settings.Setting invertYaw;
   private final com.cheatbreaker.client.settings.Setting invertPitch;
   private final com.cheatbreaker.client.settings.Setting lockPitch;
   private boolean perspectiveToggled;
   private int previousPerspective;
   private float cameraYaw;
   private float cameraPitch;
   private float prevCameraYaw;
   private float prevCameraPitch;

   public FreelookModule() {
      super("Freelook");
      this.isEditable = false;
      this.c(true);
      new com.cheatbreaker.client.settings.Setting(this, "label").a("General Options");
      this.lookView = new com.cheatbreaker.client.settings.Setting(this, "Look View").a("Reverse").a("Third", "Reverse", "First");
      this.invertYaw = new com.cheatbreaker.client.settings.Setting(this, "Invert Yaw").a(false);
      this.invertPitch = new com.cheatbreaker.client.settings.Setting(this, "Invert Pitch").a(false);
      this.lockPitch = new com.cheatbreaker.client.settings.Setting(this, "Lock Pitch").a(true);
      this.a("Freelook", 1.0F);
      this.a(com.cheatbreaker.client.event.event.ClientTickEvent.class, this::a);
   }

   @Override
   public void a(boolean var1) {
      if (!var1) {
         this.h(false);
      }

      super.a(var1);
   }

   private void a(com.cheatbreaker.client.event.event.ClientTickEvent var1) {
      if (this.minecraft.thePlayer != null && this.minecraft.theWorld != null && this.minecraft.currentScreen == null && this.minecraft.inGameHasFocus) {
         this.h(this.E());
      } else {
         this.h(false);
      }
   }

   private boolean E() {
      com.cheatbreaker.client.CheatBreakerClient var1 = com.cheatbreaker.client.CheatBreakerClient.N();
      if (var1 != null && var1.globalSettings != null) {
         KeyBinding var2 = var1.globalSettings.dragLook;
         return var2 != null && GameSettings.isKeyDown(var2);
      } else {
         return false;
      }
   }

   public void h(boolean var1) {
      if (var1 != this.perspectiveToggled) {
         if (var1) {
            EntityPlayerSP var2 = this.minecraft.thePlayer;
            if (var2 == null || this.minecraft.theWorld == null || this.minecraft.currentScreen != null || !this.minecraft.inGameHasFocus) {
               return;
            }

            this.cameraYaw = var2.rotationYaw;
            this.cameraPitch = var2.rotationPitch;
            this.prevCameraYaw = var2.prevRotationYaw;
            this.prevCameraPitch = var2.prevRotationPitch;
            this.previousPerspective = this.minecraft.gameSettings.thirdPersonView;
            this.minecraft.gameSettings.thirdPersonView = this.F();
            this.perspectiveToggled = true;
         } else {
            this.minecraft.gameSettings.thirdPersonView = this.previousPerspective;
            this.perspectiveToggled = false;
         }
      }
   }

   public void d(float var1, float var2) {
      if (this.perspectiveToggled) {
         if ((Boolean)this.invertYaw.d()) {
            var1 = -var1;
         }

         if ((Boolean)this.invertPitch.d()) {
            var2 = -var2;
         }

         float var3 = this.cameraYaw;
         float var4 = this.cameraPitch;
         this.cameraYaw += var1 * 0.15F;
         this.cameraPitch -= var2 * 0.15F;
         if ((Boolean)this.lockPitch.d()) {
            this.cameraPitch = MathHelper.clamp_float(this.cameraPitch, -90.0F, 90.0F);
         }

         this.prevCameraYaw = this.prevCameraYaw + (this.cameraYaw - var3);
         this.prevCameraPitch = this.prevCameraPitch + (this.cameraPitch - var4);
      }
   }

   public boolean a() {
      return this.perspectiveToggled;
   }

   public float z() {
      return this.cameraYaw;
   }

   public float A() {
      return this.cameraPitch;
   }

   public float B() {
      return this.prevCameraYaw;
   }

   public float C() {
      return this.prevCameraPitch;
   }

   public float c(float var1) {
      return this.prevCameraYaw + (this.cameraYaw - this.prevCameraYaw) * var1;
   }

   public float d(float var1) {
      return this.prevCameraPitch + (this.cameraPitch - this.prevCameraPitch) * var1;
   }

   public static com.cheatbreaker.client.module.modules.camera.FreelookModule D() {
      com.cheatbreaker.client.CheatBreakerClient var0 = com.cheatbreaker.client.CheatBreakerClient.N();
      if (var0 != null && var0.moduleManager != null && var0.moduleManager.freelook != null) {
         com.cheatbreaker.client.module.modules.camera.FreelookModule var1 = var0.moduleManager.freelook;
         return var1.a() ? var1 : null;
      } else {
         return null;
      }
   }

   private int F() {
      String var1 = (String)this.lookView.d();
      if ("Reverse".equals(var1)) {
         return 2;
      } else {
         return "First".equals(var1) ? 0 : 1;
      }
   }
}
