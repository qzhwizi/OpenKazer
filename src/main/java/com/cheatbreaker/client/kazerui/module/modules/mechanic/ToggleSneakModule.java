package com.cheatbreaker.client.kazerui.module.modules.mechanic;

import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovementInputFromOptions;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public final class ToggleSneakModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakModule instance;
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting toggleSprint = this.a("toggleSprint", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting alwaysSprint = this.a("alwaysSprint", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting sprintKeybindOverride = this.a("sprintKeybindOverride", false);
   private final com.cheatbreaker.client.kazerui.module.settings.KeybindSetting keybindSprint = this.a("keybindSprint");
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting toggleSneak = this.a("toggleSneak", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting sneakKeybindOverride = this.a("sneakKeybindOverride", false);
   private final com.cheatbreaker.client.kazerui.module.settings.KeybindSetting keybindSneak = this.a("keybindSneak");
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting toggleSneakContainer = this.a("toggleSneakContainer", false);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting flyBoost = this.a("flyBoost", true);
   final com.cheatbreaker.client.kazerui.module.settings.SliderSetting flyBoostAmount = this.a("flyBoostAmount", 4, 2, 8);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting restrictFlyBoost = this.a("restrictFlyBoost", true).n();
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting doubleTap = this.a("doubleTap", true);
   boolean sprinting;
   boolean sneaking;
   boolean sprintHeld;
   private boolean wasSprint;
   private boolean wasSneak;
   private boolean active;
   private long sprintDown;
   private long sneakDown;
   private boolean sprintGesture;
   private boolean sneakGesture;
   private EntityPlayerSP player;
   private Float flySpeed;

   public ToggleSneakModule() {
      super("TOGGLE_SNEAK", true);
      instance = this;
      this.a(new com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule(this), "generalOptions");
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a("generalOptions", var1x -> {
         var1x.a(this.toggleSprint, var1xx -> {
            var1xx.a(this.alwaysSprint);
            var1xx.a(this.sprintKeybindOverride, var1xxx -> var1xxx.a(this.keybindSprint));
         });
         var1x.a(this.toggleSneak, var1xx -> {
            var1xx.a(this.sneakKeybindOverride, var1xxx -> var1xxx.a(this.keybindSneak));
            var1xx.a(this.toggleSneakContainer);
         });
         var1x.a(this.doubleTap);
      });
      var1.a("flyBoostOptions", var1x -> var1x.a(this.flyBoost, this.flyBoostAmount));
      var1.a(this.restrictFlyBoost);
   }

   public static boolean n() {
      return instance == null || !instance.a() || instance.doubleTap.b();
   }

   private static GameSettings o() {
      return m().gameSettings;
   }

   private boolean p() {
      boolean var1 = m().getCurrentServerData() != null && m().getCurrentServerData().serverIP.toLowerCase(Locale.ROOT).contains("hypixel.net");
      return this.toggleSneakContainer.b() && !var1;
   }

   private static void a(KeyBinding var0, boolean var1) {
      ((KazerKeyState)var0).kazerSetPressed(var1);
   }

   static boolean a(KeyBinding var0) {
      return a(var0.getKeyCode());
   }

   private static boolean a(int var0) {
      return var0 < 0
         ? var0 >= -100 && var0 + 100 < Mouse.getButtonCount() && Mouse.isButtonDown(var0 + 100)
         : var0 > 0 && var0 < 256 && Keyboard.isKeyDown(var0);
   }

   private void q() {
      if (this.flySpeed != null && this.player != null) {
         this.player.capabilities.setFlySpeed(this.flySpeed);
         this.flySpeed = null;
      }
   }

   private void r() {
      this.q();
      this.sprinting = this.sneaking = this.sprintHeld = this.wasSprint = this.wasSneak = false;
      this.active = false;
      if (Minecraft.getMinecraft().gameSettings != null) {
         a(o().keyBindSprint, a(o().keyBindSprint));
         a(o().keyBindSneak, a(o().keyBindSneak));
      }
   }

   @Override
   protected void f() {
      this.r();
   }

   @Override
   public void a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a var1) {
      if (var1.phase == com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b.END && this.active && (!this.a() || m().thePlayer != this.player)) {
         this.r();
      }
   }

   private void a(EntityPlayerSP var1) {
      if (this.player != var1) {
         this.r();
         this.player = var1;
      }

      if (!this.a()) {
         if (this.active) {
            this.r();
         }
      } else {
         if (!this.active) {
            this.sprinting = this.toggleSprint.b();
            this.active = true;
         }

         boolean var2 = a(this.sprintKeybindOverride.b() ? this.keybindSprint.c() : o().keyBindSprint.getKeyCode());
         boolean var3 = a(this.sneakKeybindOverride.b() ? this.keybindSneak.c() : o().keyBindSneak.getKeyCode());
         boolean var4 = m().currentScreen != null;
         long var5 = System.currentTimeMillis();
         if (var4) {
            this.sprintHeld = false;
            this.sprintDown = this.sneakDown = 0L;
            this.sprintGesture = this.sneakGesture = false;
         } else {
            if (var2 && !this.wasSprint) {
               this.sprintDown = var5;
               this.sprintHeld = true;
               this.sprintGesture = true;
            }

            if (var3 && !this.wasSneak) {
               this.sneakDown = var5;
               this.sneakGesture = true;
            }

            if (!var2 && this.wasSprint) {
               this.sprintHeld = false;
               if (!var1.capabilities.isFlying && this.toggleSprint.b() && a(this.sprintGesture, this.sprintDown, var5, 400L) && !this.alwaysSprint.b()) {
                  this.sprinting = !this.sprinting;
               }

               this.sprintGesture = false;
            }

            if (!var3 && this.wasSneak) {
               if (!var1.capabilities.isFlying && this.toggleSneak.b() && a(this.sneakGesture, this.sneakDown, var5, 200L)) {
                  this.sneaking = !this.sneaking;
               }

               this.sneakGesture = false;
            }
         }

         this.wasSprint = var2;
         this.wasSneak = var3;
         if (this.toggleSprint.b()) {
            boolean var10001 = !var4 && (this.sprinting || this.alwaysSprint.b() || var2);
            a(o().keyBindSprint, var10001);
         } else {
            a(o().keyBindSprint, !var4 && a(o().keyBindSprint));
         }

         if (this.toggleSneak.b()) {
            boolean var7 = (!var4 || m().currentScreen instanceof GuiChat || this.p()) && (this.sneaking || var3);
            a(o().keyBindSneak, var7);
         } else {
            a(o().keyBindSneak, !var4 && a(o().keyBindSneak));
         }
      }
   }

   public static boolean a(boolean var0, long var1, long var3, long var5) {
      return var0 && var3 >= var1 && var3 - var1 <= var5;
   }

   private void b(EntityPlayerSP var1, MovementInputFromOptions var2) {
      if (this.a()) {
         boolean var3 = !this.restrictFlyBoost.b() || var1.capabilities.isCreativeMode;
         boolean var4 = this.flyBoost.b() && var3 && var1.capabilities.isFlying && a(o().keyBindSprint);
         if (var4) {
            if (this.flySpeed == null) {
               this.flySpeed = var1.capabilities.getFlySpeed();
            }

            int var5 = this.flyBoostAmount.c();
            var1.capabilities.setFlySpeed(0.05F * var5);
            if (var2.sneak) {
               var1.motionY -= 0.15 * var5;
            }

            if (var2.jump) {
               var1.motionY += 0.15 * var5;
            }
         } else {
            this.q();
         }
      }
   }

   public static void a(EntityPlayerSP var0, MovementInputFromOptions var1) {
      if (instance == null) {
         var1.updatePlayerMoveState();
      } else {
         instance.a(var0);
         GameSettings var2 = o();
         var1.moveForward = var1.moveStrafe = 0.0F;
         if (var2.keyBindForward.isKeyDown()) {
            var1.moveForward++;
         }

         if (var2.keyBindBack.isKeyDown()) {
            var1.moveForward--;
         }

         if (var2.keyBindLeft.isKeyDown()) {
            var1.moveStrafe++;
         }

         if (var2.keyBindRight.isKeyDown()) {
            var1.moveStrafe--;
         }

         var1.jump = var2.keyBindJump.isKeyDown();
         var1.sneak = var2.keyBindSneak.isKeyDown();
         if (var1.sneak) {
            var1.moveForward *= 0.3F;
            var1.moveStrafe *= 0.3F;
         }

         instance.b(var0, var1);
      }
   }
}
