package cn.kazer.client189.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public final class KeystrokesHudModule {
   private boolean enabled = true;
   private boolean renderHud;
   private boolean showClicks = true;
   private boolean showMovementKeys = true;
   private boolean showSpacebar;
   private boolean arrows;
   private boolean textShadow;
   private boolean border;
   private int borderThickness = 1;
   private int boxSize = 18;
   private int gap = 1;
   private int textColor = -1;
   private int textColorPressed = -1;
   private int backgroundColor = 1862270976;
   private int backgroundColorPressed = 1879048191;
   private String anchor = "LEFT_TOP";
   private float xTranslation = 1.0F;
   private float yTranslation = 18.5F;
   private float scale = 1.0F;

   public void a(cn.kazer.client189.config.IniConfigFile var1) {
      if (var1 != null) {
         String var2 = "Key Strokes";
         this.enabled = a(var1.a("Key Strokes", "-State"), this.enabled);
         this.renderHud = a(var1.a("Key Strokes", "-RenderHUD"), this.renderHud);
         this.showClicks = a(var1.a("Key Strokes", "Show clicks"), this.showClicks);
         this.showMovementKeys = a(var1.a("Key Strokes", "Show movement keys"), this.showMovementKeys);
         this.showSpacebar = a(var1.a("Key Strokes", "Show spacebar"), this.showSpacebar);
         this.arrows = a(var1.a("Key Strokes", "Replace names with arrows"), this.arrows);
         this.textShadow = a(var1.a("Key Strokes", "Text Shadow"), this.textShadow);
         this.border = a(var1.a("Key Strokes", "Border"), this.border);
         this.borderThickness = a(a(var1.a("Key Strokes", "Border Thickness"), this.borderThickness), 0, 4);
         this.boxSize = a(a(var1.a("Key Strokes", "Box size"), this.boxSize), 10, 32);
         this.gap = a(a(var1.a("Key Strokes", "Gap"), this.gap), 1, 4);
         this.textColor = a(var1.a("Key Strokes", "Text Color"), this.textColor);
         this.textColorPressed = a(var1.a("Key Strokes", "Text Color (Pressed)"), this.textColorPressed);
         this.backgroundColor = a(var1.a("Key Strokes", "Background Color"), this.backgroundColor);
         this.backgroundColorPressed = a(var1.a("Key Strokes", "Background Color (Pressed)"), this.backgroundColorPressed);
         String var3 = var1.a("Key Strokes", "-Anchor");
         if (var3 != null) {
            this.anchor = var3;
         }

         this.xTranslation = a(var1.a("Key Strokes", "-xTranslation"), this.xTranslation);
         this.yTranslation = a(var1.a("Key Strokes", "-yTranslation"), this.yTranslation);
         this.scale = a(var1.a("Key Strokes", "Scale"), this.scale);
         if (this.scale <= 0.0F) {
            this.scale = 1.0F;
         }
      }
   }

   @SubscribeEvent
   public void onOverlay(Post var1) {
      if (var1.type == ElementType.ALL && this.enabled && this.renderHud) {
         Minecraft var2 = Minecraft.getMinecraft();
         if (var2.thePlayer != null && var2.theWorld != null && !var2.gameSettings.hideGUI) {
            int var3 = this.boxSize * 3 + this.gap * 2;
            int var4 = 0;
            if (this.showMovementKeys) {
               var4 += this.boxSize * 2 + this.gap;
            }

            if (this.showClicks) {
               var4 += (var4 == 0 ? 0 : this.gap) + this.boxSize;
            }

            if (this.showSpacebar) {
               var4 += (var4 == 0 ? 0 : this.gap) + this.boxSize / 2;
            }

            if (var4 != 0) {
               float[] var5 = cn.kazer.client189.hud.HudAnchorLayout.a(
                  this.anchor,
                  var1.resolution.getScaledWidth(),
                  var1.resolution.getScaledHeight(),
                  var3,
                  var4,
                  this.scale,
                  this.xTranslation,
                  this.yTranslation
               );
               GlStateManager.pushMatrix();
               GlStateManager.translate(var5[0], var5[1], 0.0F);
               GlStateManager.scale(this.scale, this.scale, 1.0F);
               int var6 = 0;
               if (this.showMovementKeys) {
                  this.a(
                     var2,
                     var2.gameSettings.keyBindForward.getKeyCode(),
                     this.arrows ? "▲" : b(var2.gameSettings.keyBindForward.getKeyCode()),
                     this.boxSize + this.gap,
                     var6,
                     this.boxSize,
                     this.boxSize
                  );
                  var6 += this.boxSize + this.gap;
                  this.a(
                     var2,
                     var2.gameSettings.keyBindLeft.getKeyCode(),
                     this.arrows ? "◀" : b(var2.gameSettings.keyBindLeft.getKeyCode()),
                     0,
                     var6,
                     this.boxSize,
                     this.boxSize
                  );
                  this.a(
                     var2,
                     var2.gameSettings.keyBindBack.getKeyCode(),
                     this.arrows ? "▼" : b(var2.gameSettings.keyBindBack.getKeyCode()),
                     this.boxSize + this.gap,
                     var6,
                     this.boxSize,
                     this.boxSize
                  );
                  this.a(
                     var2,
                     var2.gameSettings.keyBindRight.getKeyCode(),
                     this.arrows ? "▶" : b(var2.gameSettings.keyBindRight.getKeyCode()),
                     (this.boxSize + this.gap) * 2,
                     var6,
                     this.boxSize,
                     this.boxSize
                  );
                  var6 += this.boxSize + this.gap;
               }

               if (this.showClicks) {
                  int var7 = (var3 - this.gap) / 2;
                  this.a(var2, var2.gameSettings.keyBindAttack.getKeyCode(), this.boxSize < 14 ? "L" : "LMB", 0, var6, var7, this.boxSize);
                  this.a(
                     var2,
                     var2.gameSettings.keyBindUseItem.getKeyCode(),
                     this.boxSize < 14 ? "R" : "RMB",
                     var7 + this.gap,
                     var6,
                     var3 - var7 - this.gap,
                     this.boxSize
                  );
                  var6 += this.boxSize + this.gap;
               }

               if (this.showSpacebar) {
                  this.a(var2, var2.gameSettings.keyBindJump.getKeyCode(), "", 0, var6, var3, this.boxSize / 2);
               }

               GlStateManager.popMatrix();
            }
         }
      }
   }

   private void a(Minecraft var1, int var2, String var3, int var4, int var5, int var6, int var7) {
      boolean var8 = var1.currentScreen == null && a(var2);
      int var9 = var8 ? this.backgroundColorPressed : this.backgroundColor;
      if (this.border && this.borderThickness > 0) {
         Gui.drawRect(var4, var5, var4 + var6, var5 + var7, this.textColor);
         Gui.drawRect(var4 + this.borderThickness, var5 + this.borderThickness, var4 + var6 - this.borderThickness, var5 + var7 - this.borderThickness, var9);
      } else {
         Gui.drawRect(var4, var5, var4 + var6, var5 + var7, var9);
      }

      int var10 = var8 ? this.textColorPressed : this.textColor;
      if (var3.isEmpty()) {
         Gui.drawRect(var4 + var6 * 3 / 8, var5 + var7 / 2, var4 + var6 * 5 / 8, var5 + var7 / 2 + 1, var10);
      } else {
         int var11 = var1.fontRendererObj.getStringWidth(var3);
         var1.fontRendererObj.drawString(var3, var4 + (var6 - var11) / 2, var5 + (var7 - var1.fontRendererObj.FONT_HEIGHT) / 2, var10, this.textShadow);
      }
   }

   private static boolean a(int var0) {
      if (var0 < 0) {
         int var1 = var0 + 100;
         return var1 >= 0 && Mouse.isButtonDown(var1);
      } else {
         return var0 > 0 && Keyboard.isKeyDown(var0);
      }
   }

   private static String b(int var0) {
      String var1 = var0 < 0 ? "M" + (var0 + 101) : Keyboard.getKeyName(var0);
      return var1 != null && !var1.isEmpty() ? (var1.length() > 2 ? var1.substring(0, 1) : var1) : "?";
   }

   private static boolean a(String var0, boolean var1) {
      return var0 == null ? var1 : Boolean.parseBoolean(var0);
   }

   private static int a(String var0, int var1) {
      if (var0 == null) {
         return var1;
      } else {
         try {
            return (int)Float.parseFloat(var0.split(";", 2)[0]);
         } catch (NumberFormatException var3) {
            return var1;
         }
      }
   }

   private static float a(String var0, float var1) {
      if (var0 == null) {
         return var1;
      } else {
         try {
            return Float.parseFloat(var0);
         } catch (NumberFormatException var3) {
            return var1;
         }
      }
   }

   private static int a(int var0, int var1, int var2) {
      return Math.max(var1, Math.min(var2, var0));
   }
}
