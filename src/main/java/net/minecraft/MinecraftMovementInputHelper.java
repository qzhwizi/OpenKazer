package net.minecraft;

import java.text.DecimalFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovementInputFromOptions;

public class MinecraftMovementInputHelper extends MovementInputFromOptions {
   public static boolean lIIIIlIIllIIlIIlIIIlIIllI;
   public static boolean lIIIIIIIIIlIllIIllIlIIlIl;
   public static boolean isSprinting = true;
   public static boolean superSusBoolean = false;
   public static boolean aSusBoolean = false;
   private static long IlIlllIIIIllIllllIllIIlIl;
   private static long llIIlllIIIIlllIllIlIlllIl;
   private static boolean lIIlIlIllIIlIIIlIIIlllIII;
   private static boolean IIIlllIIIllIllIlIIIIIIlII;
   private static boolean llIlIIIlIIIIlIlllIlIIIIll;
   private static boolean IIIlIIllllIIllllllIlIIIll;
   private static boolean lllIIIIIlIllIlIIIllllllII;
   public static String toggleSprintString = "";

   public MinecraftMovementInputHelper(GameSettings var1) {
      super(var1);
   }

   public static void lIIIIlIIllIIlIIlIIIlIIllI(Minecraft var0, MovementInputFromOptions var1, EntityPlayerSP var2) {
      var1.moveStrafe = 0.0F;
      var1.moveForward = 0.0F;
      GameSettings var3 = var0.gameSettings;
      if (var3.keyBindForward.isKeyDown()) {
         var1.moveForward++;
      }

      if (var3.keyBindBack.isKeyDown()) {
         var1.moveForward--;
      }

      if (var3.keyBindLeft.isKeyDown()) {
         var1.moveStrafe++;
      }

      if (var3.keyBindRight.isKeyDown()) {
         var1.moveStrafe--;
      }

      if (var2.isRiding() && !IIIlIIllllIIllllllIlIIIll) {
         IIIlIIllllIIllllllIlIIIll = true;
         lllIIIIIlIllIlIIIllllllII = isSprinting;
      } else if (IIIlIIllllIIllllllIlIIIll && !var2.isRiding()) {
         IIIlIIllllIIllllllIlIIIll = false;
         if (lllIIIIIlIllIlIIIllllllII && !isSprinting) {
            isSprinting = true;
            llIIlllIIIIlllIllIlIlllIl = System.currentTimeMillis();
            IIIlllIIIllIllIlIIIIIIlII = true;
            superSusBoolean = false;
         }
      }

      var1.jump = var3.keyBindJump.isKeyDown();
      if ((Boolean)com.cheatbreaker.client.module.modules.togglesprint.ToggleSprintModule.toggleSneak.d() && com.cheatbreaker.client.CheatBreakerClient.N().k().toggleSprint.g()) {
         if (var3.keyBindSneak.isKeyDown() && !lIIlIlIllIIlIIIlIIIlllIII) {
            if (!var2.isRiding() && !var2.capabilities.isFlying) {
               var1.sneak = !var1.sneak;
            } else {
               var1.sneak = true;
               llIlIIIlIIIIlIlllIlIIIIll = var2.isRiding();
            }

            IlIlllIIIIllIllllIllIIlIl = System.currentTimeMillis();
            lIIlIlIllIIlIIIlIIIlllIII = true;
         }

         if (!var3.keyBindSneak.isKeyDown() && lIIlIlIllIIlIIIlIIIlllIII) {
            if (!var2.capabilities.isFlying && !llIlIIIlIIIIlIlllIlIIIIll) {
               if (System.currentTimeMillis() - IlIlllIIIIllIllllIllIIlIl > 300L) {
                  var1.sneak = false;
               }
            } else {
               var1.sneak = false;
            }

            lIIlIlIllIIlIIIlIIIlllIII = false;
         }
      } else {
         var1.sneak = var3.keyBindSneak.isKeyDown();
      }

      if (var1.sneak) {
         var1.moveStrafe = (float)(var1.moveStrafe * 0.3);
         var1.moveForward = (float)(var1.moveForward * 0.3);
      }

      boolean var4 = var2.getFoodStats().getFoodLevel() > 6.0F || var2.capabilities.isFlying;
      boolean var5 = !var1.sneak && !var2.capabilities.isFlying && var4;
      lIIIIlIIllIIlIIlIIIlIIllI = !(Boolean)com.cheatbreaker.client.module.modules.togglesprint.ToggleSprintModule.toggleSprint.d();
      lIIIIIIIIIlIllIIllIlIIlIl = (Boolean)com.cheatbreaker.client.module.modules.togglesprint.ToggleSprintModule.doubleTap.d();
      if ((var5 || lIIIIlIIllIIlIIlIIIlIIllI)
         && var3.keyBindSprint.isKeyDown()
         && !IIIlllIIIllIllIlIIIIIIlII
         && !var2.capabilities.isFlying
         && !lIIIIlIIllIIlIIlIIIlIIllI) {
         isSprinting = !isSprinting;
         llIIlllIIIIlllIllIlIlllIl = System.currentTimeMillis();
         IIIlllIIIllIllIlIIIIIIlII = true;
         superSusBoolean = false;
      }

      if ((var5 || lIIIIlIIllIIlIIlIIIlIIllI) && !var3.keyBindSprint.isKeyDown() && IIIlllIIIllIllIlIIIIIIlII) {
         if (System.currentTimeMillis() - llIIlllIIIIlllIllIlIlllIl > 300L) {
            superSusBoolean = true;
         }

         IIIlllIIIllIllIlIIIIIIlII = false;
      }

      lIIIIlIIllIIlIIlIIIlIIllI(var1, var2, var3);
   }

   public void setSprintState(boolean var1, boolean var2) {
      isSprinting = var1;
      aSusBoolean = var2;
   }

   private static void lIIIIlIIllIIlIIlIIIlIIllI(MovementInputFromOptions var0, EntityPlayerSP var1, GameSettings var2) {
      String var3 = "";
      boolean var4 = var1.capabilities.isFlying;
      boolean var5 = var1.isRiding();
      boolean var6 = var2.keyBindSneak.isKeyDown();
      boolean var7 = var2.keyBindSprint.isKeyDown();
      if (var4) {
         DecimalFormat var8 = new DecimalFormat("#.00");
         var3 = (Boolean) com.cheatbreaker.client.module.modules.togglesprint.ToggleSprintModule.flyBoost.d() && var7 && var1.capabilities.isCreativeMode
            ? var3
               + ((String)com.cheatbreaker.client.CheatBreakerClient.N().k().toggleSprint.flyBoostString.d())
                  .replaceAll("%BOOST%", var8.format(com.cheatbreaker.client.module.modules.togglesprint.ToggleSprintModule.flyBoostAmount.d()))
            : var3 + com.cheatbreaker.client.CheatBreakerClient.N().k().toggleSprint.flyString.d();
      }

      if (var5) {
         var3 = var3 + com.cheatbreaker.client.CheatBreakerClient.N().k().toggleSprint.ridingString.d();
      }

      if (var0.sneak) {
         var3 = var4
            ? com.cheatbreaker.client.CheatBreakerClient.N().k().toggleSprint.decendString.d().toString()
            : (
               var5
                  ? com.cheatbreaker.client.CheatBreakerClient.N().k().toggleSprint.dismountString.d().toString()
                  : (
                     var6
                        ? var3 + com.cheatbreaker.client.CheatBreakerClient.N().k().toggleSprint.sneakHeldString.d()
                        : var3 + com.cheatbreaker.client.CheatBreakerClient.N().k().toggleSprint.sneakToggledString.d()
                  )
            );
      } else if (isSprinting && !var4 && !var5) {
         boolean var9 = superSusBoolean || lIIIIlIIllIIlIIlIIIlIIllI || aSusBoolean;
         var3 = var7
            ? var3 + com.cheatbreaker.client.CheatBreakerClient.N().k().toggleSprint.sprintHeldString.d()
            : (
               var9
                  ? var3 + com.cheatbreaker.client.CheatBreakerClient.N().k().toggleSprint.sprintVanillaString.d()
                  : var3 + com.cheatbreaker.client.CheatBreakerClient.N().k().toggleSprint.sprintToggledString.d()
            );
      }

      toggleSprintString = (String)var3;
   }
}
