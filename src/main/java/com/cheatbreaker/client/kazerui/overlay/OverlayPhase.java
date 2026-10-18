package com.cheatbreaker.client.kazerui.overlay;

public final class OverlayPhase {
   public static final com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a START = new com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b.START);
   public static final com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a END = new com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b.END);

   private OverlayPhase() {
   }

   public static final class a {
      public final com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b phase;

      public a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b var1) {
         this.phase = var1;
      }
   }

   public static enum b {
      START,
      END;
   }
}
