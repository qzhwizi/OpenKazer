package com.cheatbreaker.client.gui.hudeditor;

import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class ModulePlacementScreen extends GuiScreen {
   private final com.cheatbreaker.client.module.Module module;
   private final com.cheatbreaker.client.gui.hudeditor.HudEditorScreen modulesGui;
   private final Runnable onPlacementSettled;

   public ModulePlacementScreen(com.cheatbreaker.client.gui.hudeditor.HudEditorScreen var1, com.cheatbreaker.client.module.Module var2) {
      this(var1, var2, null);
   }

   public ModulePlacementScreen(com.cheatbreaker.client.gui.hudeditor.HudEditorScreen var1, com.cheatbreaker.client.module.Module var2, Runnable var3) {
      var2.a(true);
      this.module = var2;
      this.modulesGui = var1;
      this.onPlacementSettled = var3;
   }

   public com.cheatbreaker.client.module.Module a() {
      return this.module;
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      com.cheatbreaker.client.gui.render.RenderUtils.a(0.0, (double)(this.height / 3.0F), (double)this.width, (double)(this.height / 3 + 0.5F), 0.0, 1862270976);
      com.cheatbreaker.client.gui.render.RenderUtils.a(0.0, (double)(this.height / 3.0F * 2.0F), (double)this.width, (double)(this.height / 3 * 2 + 0.5F), 0.0, 1862270976);
      com.cheatbreaker.client.gui.render.RenderUtils.a((double)(this.width / 3.0F), 0.0, (double)(this.width / 3 + 0.5F), (double)this.height, 0.0, 1862270976);
      com.cheatbreaker.client.gui.render.RenderUtils.a((double)(this.width / 3.0F * 2.0F), 0.0, (double)(this.width / 3 * 2 + 0.5F), (double)this.height, 0.0, 1862270976);
      com.cheatbreaker.client.gui.render.RenderUtils.a(
         (double)(this.width / 3.0F + this.width / 6.0F),
         (double)(this.height / 3.0F * 2.0F),
         (double)(this.width / 3 + this.width / 6 + 0.5F),
         (double)this.height,
         0.0,
         1862270976
      );
      float var4 = 1.0F / com.cheatbreaker.client.CheatBreakerClient.N().e();
      float var5 = (com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.a(this.module.h()) + 6) * var4;
      if (this.module.width < var5) {
         this.module.width = (int)var5;
      }

      if (this.module.height < 18.0F) {
         this.module.height = 18.0F;
      }

      ScaledResolution var6 = new ScaledResolution(this.mc);
      float[] var7 = com.cheatbreaker.client.gui.hudeditor.AnchorHelper.b(var1, var2, var6);
      com.cheatbreaker.client.gui.hudeditor.GuiAnchor var8 = com.cheatbreaker.client.gui.hudeditor.AnchorHelper.a(var1, var2, var6);
      if (var8 != com.cheatbreaker.client.gui.hudeditor.GuiAnchor.MIDDLE_MIDDLE) {
         if (var8 != com.cheatbreaker.client.gui.hudeditor.GuiAnchor.MIDDLE_BOTTOM_LEFT && var8 != com.cheatbreaker.client.gui.hudeditor.GuiAnchor.MIDDLE_BOTTOM_RIGHT) {
            cn.kazer.client189.render.DrawRect.a(var7[0], var7[1], var7[0] + var6.getScaledWidth() / 3, var7[1] + var6.getScaledHeight() / 3, 788529152);
         } else {
            cn.kazer.client189.render.DrawRect.a(var7[0], var7[1], var7[0] + var6.getScaledWidth() / 6, var7[1] + var6.getScaledHeight() / 3, 788529152);
         }
      }

      int var9 = var6.getScaledWidth();
      int var10 = var6.getScaledHeight();
      float[] var11 = com.cheatbreaker.client.gui.hudeditor.AnchorHelper.a(this.module, var1, var2, var6);
      if (var8 != this.module.r()) {
         this.module.a(var8);
         this.module.a(0.0F, 0.0F);
      }

      if (!Mouse.isButtonDown(1)) {
         com.cheatbreaker.client.gui.render.RenderUtils.a(2.0, 0.0, 2.5, (double)var10, 0.0, -15599126);
         com.cheatbreaker.client.gui.render.RenderUtils.a((double)(var9 - 2.5F), 0.0, (double)(var9 - 2), (double)var10, 0.0, -15599126);
         com.cheatbreaker.client.gui.render.RenderUtils.a(0.0, 2.0, (double)var9, 2.5, 0.0, -15599126);
         com.cheatbreaker.client.gui.render.RenderUtils.a(0.0, (double)(var10 - 3.5F), (double)var9, (double)(var10 - 3), 0.0, -15599126);
      }

      float var12 = var1 - var7[0] - var11[0];
      float var13 = var2 - var7[1] - var11[1];
      if (!Mouse.isButtonDown(1)) {
         float[] var14 = this.module.a(var6, false);
         var12 = this.a(this.module, var12, var14, (int)(this.module.width * (Float)this.module.scale.d()));
         var13 = this.b(this.module, var13, var14, (int)(this.module.height * (Float)this.module.scale.d()));
      }

      this.module.a(var12, var13);
      GL11.glPushMatrix();
      this.module.a(var6);
      com.cheatbreaker.client.gui.render.RenderUtils.a(-2.0, -2.0, (double)(this.module.width + 2.0F), (double)(this.module.height + 2.0F), 4.0, 551805923);
      GL11.glPushMatrix();
      GL11.glScalef(var4, var4, var4);
      com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.a(this.module.h(), 0.0F, -1.0F, 1862270976);
      GL11.glPopMatrix();
      GL11.glPopMatrix();
   }

   private float a(com.cheatbreaker.client.module.Module var1, float var2, float[] var3, float var4) {
      if (var2 + var3[0] < 3.0F) {
         var2 = -var3[0] + 3.0F;
      } else if (var2 + var3[0] * (Float)var1.scale.d() + var4 > this.width - 3.0F) {
         var2 = (int)(this.width - var3[0] * (Float)var1.scale.d() - var4 - 3.0F);
      }

      return var2;
   }

   private float b(com.cheatbreaker.client.module.Module var1, float var2, float[] var3, float var4) {
      if (var2 + var3[1] < 2.0F) {
         var2 = -var3[1] + 2.0F;
      } else if (var2 + var3[1] * (Float)var1.scale.d() + var4 > this.height - 2.0F) {
         var2 = (int)(this.height - var3[1] * (Float)var1.scale.d() - var4 - 2.0F);
      }

      return var2;
   }

   public void mouseClicked(int var1, int var2, int var3) throws IOException {
      if (var3 == 0) {
         ScaledResolution var4 = new ScaledResolution(this.mc);
         com.cheatbreaker.client.gui.hudeditor.GuiAnchor var5 = com.cheatbreaker.client.gui.hudeditor.AnchorHelper.a(var1, var2, var4);
         this.module.a(var5);
         Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
         this.module.a(true);
         com.cheatbreaker.client.gui.hudeditor.HudEditorScreen var6 = new com.cheatbreaker.client.gui.hudeditor.HudEditorScreen();
         this.mc.displayGuiScreen(var6);
         var6.currentScrollableElement = var6.modulesElement;
         var6.currentScrollableElement.lIIlIlIllIIlIIIlIIIlllIII = false;
         var6.currentScrollableElement.lIIIIllIIlIlIllIIIlIllIlI = this.modulesGui.modulesElement.lIIIIllIIlIlIllIIIlIllIlI;
         var6.currentScrollableElement.yOffset = 0;
         if (this.onPlacementSettled != null) {
            this.onPlacementSettled.run();
         }
      }
   }
}
