package com.cheatbreaker.client.kazerui.overlay;

import net.minecraft.client.gui.ScaledResolution;

public final class OverlayEvents {
   private OverlayEvents() {
   }

   public static enum aInner {
      ARMOR,
      BOSSHEALTH;
   }

   public static final class b {
      public final com.cheatbreaker.client.kazerui.overlay.OverlayEvents.aInner type;
      public final ScaledResolution resolution;
      private boolean canceled;

      public b(com.cheatbreaker.client.kazerui.overlay.OverlayEvents.aInner var1, ScaledResolution var2) {
         this.type = var1;
         this.resolution = var2;
      }

      public void a(boolean var1) {
         this.canceled = var1;
      }

      public boolean a() {
         return this.canceled;
      }
   }
}
