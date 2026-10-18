package com.cheatbreaker.client.module.modules.keystrokes;

import java.util.ArrayList;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class KeyStrokesModule extends com.cheatbreaker.client.module.Module {
   private final com.cheatbreaker.client.settings.Setting showClicks;
   private final com.cheatbreaker.client.settings.Setting showMovementKeys;
   private final com.cheatbreaker.client.settings.Setting showSpacebar;
   public final com.cheatbreaker.client.settings.Setting replaceNamesWithArrows;
   private final com.cheatbreaker.client.settings.Setting textColor;
   private final com.cheatbreaker.client.settings.Setting textColorPressed;
   private final com.cheatbreaker.client.settings.Setting backgroundColor;
   private final com.cheatbreaker.client.settings.Setting backgroundColorPressed;
   private final com.cheatbreaker.client.settings.Setting boxSize;
   private final com.cheatbreaker.client.settings.Setting gap;
   public final com.cheatbreaker.client.settings.Setting fadeTime;
   private com.cheatbreaker.client.module.modules.keystrokes.KeystrokeKey upKey;
   private com.cheatbreaker.client.module.modules.keystrokes.KeystrokeKey leftKey;
   private com.cheatbreaker.client.module.modules.keystrokes.KeystrokeKey rightKey;
   private com.cheatbreaker.client.module.modules.keystrokes.KeystrokeKey downkey;
   private com.cheatbreaker.client.module.modules.keystrokes.KeystrokeKey leftMouseKey;
   private com.cheatbreaker.client.module.modules.keystrokes.KeystrokeKey rightMouseKey;
   private com.cheatbreaker.client.module.modules.keystrokes.KeystrokeKey spaceBarKey;

   public KeyStrokesModule() {
      super("Key Strokes");
      this.b(com.cheatbreaker.client.gui.hudeditor.GuiAnchor.LEFT_TOP);
      this.b(1.0F, 18.5F);
      this.c(true);
      this.g(false);
      new com.cheatbreaker.client.settings.Setting(this, "Mode").a("Grouped").a("Grouped", "Separated");
      this.showClicks = new com.cheatbreaker.client.settings.Setting(this, "Show clicks").a(true);
      this.showMovementKeys = new com.cheatbreaker.client.settings.Setting(this, "Show movement keys").a(true);
      this.showSpacebar = new com.cheatbreaker.client.settings.Setting(this, "Show spacebar").a(false);
      this.replaceNamesWithArrows = new com.cheatbreaker.client.settings.Setting(this, "Replace names with arrows").a(false);
      new com.cheatbreaker.client.settings.Setting(this, "Text Shadow").a(false);
      new com.cheatbreaker.client.settings.Setting(this, "Border").a(false);
      new com.cheatbreaker.client.settings.Setting(this, "Animate").a(false);
      new com.cheatbreaker.client.settings.Setting(this, "Border Thickness").a(Float.valueOf(1.0F)).a(0.0F, 4.0F);
      this.boxSize = new com.cheatbreaker.client.settings.Setting(this, "Box size").a(18).a(10, 32);
      this.gap = new com.cheatbreaker.client.settings.Setting(this, "Gap").a(1).a(1, 4);
      this.fadeTime = new com.cheatbreaker.client.settings.Setting(this, "Fade Time").a(75).a(0, 100);
      this.textColor = new com.cheatbreaker.client.settings.Setting(this, "Text Color").a(-1).a(Integer.MIN_VALUE, Integer.MAX_VALUE);
      this.textColorPressed = new com.cheatbreaker.client.settings.Setting(this, "Text Color (Pressed)").a(-1).a(Integer.MIN_VALUE, Integer.MAX_VALUE);
      this.backgroundColor = new com.cheatbreaker.client.settings.Setting(this, "Background Color").a(1862270976).a(Integer.MIN_VALUE, Integer.MAX_VALUE);
      this.backgroundColorPressed = new com.cheatbreaker.client.settings.Setting(this, "Background Color (Pressed)").a(1879048191).a(Integer.MIN_VALUE, Integer.MAX_VALUE);
      this.a();
      this.a(new ResourceLocation("client/icons/mods/wasd.png"), 55, 37);
      this.a(com.cheatbreaker.client.event.event.RenderHudEvent.class, this::a);
   }

   private void a(com.cheatbreaker.client.event.event.RenderHudEvent var1) {
      if (this.q()) {
         GL11.glPushMatrix();
         this.a(var1.a());
         float var2 = 0.0F;
         float var3 = 0.0F;
         int var4 = this.textColor.a();
         int var5 = this.textColorPressed.a();
         int var6 = this.backgroundColor.a();
         int var7 = this.backgroundColorPressed.a();
         int var8 = (Integer)this.gap.d();
         ArrayList<float[]> var9 = new ArrayList<>();
         if ((Boolean)this.showMovementKeys.d()) {
            this.upKey.a(this.leftKey.b() + var8, 0.0F, var4, var5, var6, var7);
            this.leftKey.a(0.0F, this.upKey.c() + var8, var4, var5, var6, var7);
            this.downkey.a(this.leftKey.b() + var8, this.upKey.c() + var8, var4, var5, var6, var7);
            this.rightKey.a(this.leftKey.b() + this.downkey.b() + var8 * 2, this.upKey.c() + var8, var4, var5, var6, var7);
            var9.add(new float[]{this.leftKey.b() + this.downkey.b() + this.rightKey.b() + var8 * 2, this.upKey.c() + var8 + this.downkey.c()});
            var3 += this.upKey.c() + var8 * 2 + this.downkey.c();
         }

         if ((Boolean)this.showClicks.d()) {
            this.leftMouseKey.a(0.0F, var3, var4, var5, var6, var7);
            this.rightMouseKey.a(this.leftMouseKey.b() + var8, var3, var4, var5, var6, var7);
            var9.add(new float[]{this.leftMouseKey.b() + this.rightMouseKey.b() + var8, this.rightMouseKey.c()});
            var3 += this.rightMouseKey.c() + var8;
         }

         if ((Boolean)this.showSpacebar.d()) {
            this.spaceBarKey.a(0.0F, var3, var4, var5, var6, var7);
            var9.add(new float[]{this.spaceBarKey.b(), this.spaceBarKey.c()});
            var3 += this.spaceBarKey.c() + var8;
         }

         float[] var10 = a(var9.toArray(new float[0][]), var8);
         this.c(var10[0], var10[1]);
         GL11.glPopMatrix();
      }
   }

   static float[] a(float[][] var0, int var1) {
      float var2 = 0.0F;
      float var3 = 0.0F;

      for (int var4 = 0; var4 < var0.length; var4++) {
         var2 = Math.max(var2, var0[var4][0]);
         var3 += var0[var4][1];
         if (var4 > 0) {
            var3 += var1;
         }
      }

      return new float[]{var2, var3};
   }

   public void a() {
      if (this.minecraft != null) {
         int var1 = this.minecraft.gameSettings.keyBindForward.getKeyCode();
         int var2 = this.minecraft.gameSettings.keyBindLeft.getKeyCode();
         int var3 = this.minecraft.gameSettings.keyBindBack.getKeyCode();
         int var4 = this.minecraft.gameSettings.keyBindRight.getKeyCode();
         int var5 = (Integer)this.boxSize.d();
         int var6 = (Integer)this.gap.d();
         float var7 = ((Integer)this.fadeTime.d()).intValue();
         String var8 = Keyboard.getKeyName(var1);
         String var9 = Keyboard.getKeyName(var2);
         String var10 = Keyboard.getKeyName(var3);
         String var11 = Keyboard.getKeyName(var4);
         float var12 = this.minecraft.fontRendererObj.getStringWidth(var8) * (Float)this.scale.d();
         float var13 = this.minecraft.fontRendererObj.getStringWidth(var9) * (Float)this.scale.d();
         float var14 = this.minecraft.fontRendererObj.getStringWidth(var10) * (Float)this.scale.d();
         float var15 = this.minecraft.fontRendererObj.getStringWidth(var11) * (Float)this.scale.d();
         int var16 = this.minecraft.gameSettings.keyBindJump.getKeyCode();
         int var17 = this.minecraft.gameSettings.keyBindAttack.getKeyCode();
         int var18 = this.minecraft.gameSettings.keyBindUseItem.getKeyCode();
         boolean var19 = (Boolean)this.replaceNamesWithArrows.d();
         this.upKey = new com.cheatbreaker.client.module.modules.keystrokes.KeystrokeKey(var19 ? "▲" : (var12 > var5 ? var8.substring(0, 1) : var8), var1, var5, var5, var7);
         this.leftKey = new com.cheatbreaker.client.module.modules.keystrokes.KeystrokeKey(var19 ? "◀" : (var13 > var5 ? var9.substring(0, 1) : var9), var2, var5, var5, var7);
         this.downkey = new com.cheatbreaker.client.module.modules.keystrokes.KeystrokeKey(var19 ? "▼" : (var14 > var5 ? var10.substring(0, 1) : var10), var3, var5, var5, var7);
         this.rightKey = new com.cheatbreaker.client.module.modules.keystrokes.KeystrokeKey(var19 ? "▶" : (var15 > var5 ? var11.substring(0, 1) : var11), var4, var5, var5, var7);
         float var20 = (this.leftKey.b() + this.downkey.b() + this.rightKey.b() + var6) / 2.0F;
         this.leftMouseKey = new com.cheatbreaker.client.module.modules.keystrokes.KeystrokeKey(var5 < 14 ? "L" : "LMB", var17, var20, var5, var7);
         this.rightMouseKey = new com.cheatbreaker.client.module.modules.keystrokes.KeystrokeKey(var5 < 14 ? "R" : "RMB", var18, var20, var5, var7);
         this.spaceBarKey = new com.cheatbreaker.client.module.modules.keystrokes.KeystrokeKey(
            Keyboard.getKeyName(var16), var16, this.leftKey.b() + this.downkey.b() + this.rightKey.b() + var6 * 2, var5 / 2.0F, var7
         );
      }
   }
}
