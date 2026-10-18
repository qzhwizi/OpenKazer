package com.cheatbreaker.client.kazerui.module.modules.mechanic;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public final class CameraHooks {
   private static com.cheatbreaker.client.module.modules.camera.FreelookModule a(Entity var0) {
      return var0 == Minecraft.getMinecraft().thePlayer ? com.cheatbreaker.client.module.modules.camera.FreelookModule.D() : null;
   }

   public static void turn(Entity var0, float var1, float var2) {
      com.cheatbreaker.client.module.modules.camera.FreelookModule var3 = a(var0);
      if (var3 == null) {
         var0.setAngles(var1, var2);
      } else {
         var3.d(var1, var2);
      }
   }

   public static float yaw(Entity var0) {
      com.cheatbreaker.client.module.modules.camera.FreelookModule var1 = a(var0);
      return var1 == null ? var0.rotationYaw : var1.z();
   }

   public static float pitch(Entity var0) {
      com.cheatbreaker.client.module.modules.camera.FreelookModule var1 = a(var0);
      return var1 == null ? var0.rotationPitch : var1.A();
   }

   public static float previousYaw(Entity var0) {
      com.cheatbreaker.client.module.modules.camera.FreelookModule var1 = a(var0);
      return var1 == null ? var0.prevRotationYaw : var1.B();
   }

   public static float previousPitch(Entity var0) {
      com.cheatbreaker.client.module.modules.camera.FreelookModule var1 = a(var0);
      return var1 == null ? var0.prevRotationPitch : var1.C();
   }

   private CameraHooks() {
   }
}
