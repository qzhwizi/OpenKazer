package com.cheatbreaker.client.gui.uielement.settingseditor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ItemCountToggleElement extends com.cheatbreaker.client.gui.uielement.UIElement {
   private static final int CELL = 32;
   private static final int ICON = 16;
   private static final int ICON_DRAW = 32;
   private static final int BAR_HEIGHT = 10;
   private static final int ICON_TOP = 22;
   private static final int COLOUR_ENABLED = -13916106;
   private static final int COLOUR_DISABLED = -723724;
   private static final int COLOUR_HOVER_ENABLED = -12418828;
   private static final int COLOUR_HOVER_DISABLED = -13916106;
   private static final com.cheatbreaker.client.module.modules.TrackableItem[] ORDER = new com.cheatbreaker.client.module.modules.TrackableItem[]{
      com.cheatbreaker.client.module.modules.TrackableItem.HEALING_POT,
      com.cheatbreaker.client.module.modules.TrackableItem.MUSHROOM_SOUP,
      com.cheatbreaker.client.module.modules.TrackableItem.GOLDEN_APPLE,
      com.cheatbreaker.client.module.modules.TrackableItem.ENCHANTED_GOLDEN_APPLE,
      com.cheatbreaker.client.module.modules.TrackableItem.ARROW
   };
   private final com.cheatbreaker.client.module.modules.ItemCountModule module;
   private final com.cheatbreaker.client.gui.uielement.settingseditor.ModuleSettingsPanel listElement;
   private final cn.kazer.client189.render.RenderItemBridge renderItem = new cn.kazer.client189.render.RenderItemBridge();

   public ItemCountToggleElement(com.cheatbreaker.client.module.modules.ItemCountModule var1, com.cheatbreaker.client.gui.uielement.settingseditor.ModuleSettingsPanel var2, float var3) {
      super(var3);
      this.module = var1;
      this.listElement = var2;
   }

   @Override
   public int c() {
      return 76;
   }

   private int d() {
      return this.x + 14;
   }

   private float e() {
      return (float)(this.width - 28 - 32) / (ORDER.length - 1);
   }

   private int b(int var1) {
      return (int)(this.d() + var1 * this.e());
   }

   private int f() {
      return this.y + 22;
   }

   private int c(int var1) {
      return this.b(var1);
   }

   private int g() {
      return this.y + 22 + 32 + 4;
   }

   private boolean a(int var1, int var2, int var3, int var4, int var5, int var6) {
      return var1 > var3 * this.scale
         && var1 < (var3 + var5) * this.scale
         && var2 > (var4 + this.yOffset) * this.scale
         && var2 < (var4 + var6 + this.yOffset) * this.scale;
   }

   private int a(boolean var1) {
      return var1 ? -12418828 : -13916106;
   }

   private int a(boolean var1, boolean var2) {
      return var2 ? this.a(var1) : (var1 ? -13916106 : -723724);
   }

   @Override
   public void a(int var1, int var2, float var3) {
      boolean var4 = GL11.glIsEnabled(2929);
      cn.kazer.client189.render.GlStateBridge.a(2929);
      cn.kazer.client189.render.GlStateBridge.a(true);
      RenderHelper.enableGUIStandardItemLighting();

      try {
         int var5 = this.f();
         int var6 = this.g();

         for (int var7 = 0; var7 < ORDER.length; var7++) {
            com.cheatbreaker.client.module.modules.TrackableItem var8 = ORDER[var7];
            int var9 = this.b(var7);
            int var10 = this.c(var7);
            boolean var11 = this.module.b(var8);
            boolean var12 = this.a(var1, var2, var9, var5, 32, 32) || this.a(var1, var2, var10, var6, 32, 10);
            if (var12) {
               cn.kazer.client189.render.DrawRect.a(var9, var5, var9 + 32, var5 + 32, this.a(var11));
            }

            GL11.glPushMatrix();
            GL11.glTranslatef(var9, var5, 0.0F);
            GL11.glScalef(2.0F, 2.0F, 1.0F);
            this.renderItem
               .a(Minecraft.getMinecraft().fontRendererObj, Minecraft.getMinecraft().getTextureManager(), com.cheatbreaker.client.module.modules.ItemCountModule.h(var8), 0, 0);
            GL11.glPopMatrix();
            int var13 = this.a(var11, var12);
            cn.kazer.client189.render.DrawRect.a(var10, var6, var10 + 32, var6 + 10, var13);
            String var14 = var11 ? "ON" : "OFF";
            int var15 = Minecraft.getMinecraft().fontRendererObj.getStringWidth(var14);
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(var14, var10 + (32 - var15) / 2, var6 + 1, -1);
         }
      } finally {
         RenderHelper.disableStandardItemLighting();
         if (var4) {
            cn.kazer.client189.render.GlStateBridge.a(2929);
         } else {
            cn.kazer.client189.render.GlStateBridge.b(2929);
         }

         cn.kazer.client189.render.GlStateBridge.a(true);
      }
   }

   @Override
   public void a(int var1, int var2, int var3) {
      if (var3 == 0) {
         int var4 = this.f();
         int var5 = this.g();

         for (int var6 = 0; var6 < ORDER.length; var6++) {
            com.cheatbreaker.client.module.modules.TrackableItem var7 = ORDER[var6];
            int var8 = this.b(var6);
            int var9 = this.c(var6);
            if (this.a(var1, var2, var8, var4, 32, 32)) {
               h();
               this.listElement.llIlIIIlIIIIlIlllIlIIIIll = false;
               this.listElement.module = this.module.i(var7);
               return;
            }

            if (this.a(var1, var2, var9, var5, 32, 10)) {
               h();
               this.module.a(var7, !this.module.b(var7));
               return;
            }
         }
      }
   }

   private static void h() {
      Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
   }
}
