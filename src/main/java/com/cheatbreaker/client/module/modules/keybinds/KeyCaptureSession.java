package com.cheatbreaker.client.module.modules.keybinds;

import java.util.ArrayList;
import java.util.List;

public final class KeyCaptureSession {
   public static final int MOUSE_LEFT = -100;
   public static final int MOUSE_RIGHT = -99;
   public static final int KEY_ESCAPE = 1;
   private com.cheatbreaker.client.module.modules.keybinds.KeyCaptureSession.a phase = com.cheatbreaker.client.module.modules.keybinds.KeyCaptureSession.a.IDLE;
   private final List<Integer> captured = new ArrayList<>();
   private final List<Integer> original = new ArrayList<>();

   public void a(List<Integer> var1) {
      this.phase = com.cheatbreaker.client.module.modules.keybinds.KeyCaptureSession.a.CAPTURING;
      this.captured.clear();
      this.original.clear();
      if (var1 != null) {
         this.original.addAll(var1);
      }
   }

   public com.cheatbreaker.client.module.modules.keybinds.KeyCaptureSession.a a() {
      return this.phase;
   }

   public List<Integer> b() {
      switch (this.phase) {
         case FINISHED:
            return new ArrayList<>(this.captured);
         case CLEARED:
            return new ArrayList<>();
         case CANCELLED:
            return new ArrayList<>(this.original);
         default:
            return new ArrayList<>(this.captured);
      }
   }

   public void a(int var1) {
      if (this.phase == com.cheatbreaker.client.module.modules.keybinds.KeyCaptureSession.a.CAPTURING) {
         if (var1 == 1) {
            this.captured.clear();
            this.phase = com.cheatbreaker.client.module.modules.keybinds.KeyCaptureSession.a.CLEARED;
         } else {
            if (!this.captured.contains(var1)) {
               this.captured.add(var1);
            }
         }
      }
   }

   public void b(int var1) {
      if (this.phase == com.cheatbreaker.client.module.modules.keybinds.KeyCaptureSession.a.CAPTURING) {
         if (var1 != -100 && var1 != -99) {
            if (!this.captured.contains(var1)) {
               this.captured.add(var1);
            }
         } else {
            this.captured.clear();
            this.phase = com.cheatbreaker.client.module.modules.keybinds.KeyCaptureSession.a.CANCELLED;
         }
      }
   }

   public void c(int var1) {
      this.e(var1);
   }

   public void d(int var1) {
      this.e(var1);
   }

   private void e(int var1) {
      if (this.phase == com.cheatbreaker.client.module.modules.keybinds.KeyCaptureSession.a.CAPTURING) {
         if (this.captured.contains(var1)) {
            this.phase = com.cheatbreaker.client.module.modules.keybinds.KeyCaptureSession.a.FINISHED;
         }
      }
   }

   public static enum a {
      IDLE,
      CAPTURING,
      FINISHED,
      CLEARED,
      CANCELLED;
   }
}
