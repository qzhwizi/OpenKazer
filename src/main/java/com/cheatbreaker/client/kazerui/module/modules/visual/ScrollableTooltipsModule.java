package com.cheatbreaker.client.kazerui.module.modules.visual;

import com.cheatbreaker.client.kazerui.module.render.TooltipHooks;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public final class ScrollableTooltipsModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static com.cheatbreaker.client.kazerui.module.modules.visual.ScrollableTooltipsModule instance;
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting tooltipScale = this.a("tooltipScale", 1.0F, 0.25F, 2.5F);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting scrollSpeed = this.a("scrollSpeed", 20, 5, 60);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting startAtTop = this.a("startAtTop", true);
   private GuiScreen screen;
   private ItemStack stack;
   private int cursorX;
   private int cursorY;
   private float offsetX;
   private float offsetY;
   private float overflowX;
   private float overflowY;
   private long lastDraw;

   public ScrollableTooltipsModule() {
      super("SCROLLABLE_TOOLTIPS", false);
      instance = this;
      this.n();
   }

   private void n() {
      for (com.cheatbreaker.client.kazerui.module.settings.AbstractSetting var2 : this.g()) {
         String var3 = var2.key;
         switch (var3) {
            case "tooltipScale":
               var2.a(() -> "Tooltip Scale (Ctrl + Wheel)");
               break;
            case "scrollSpeed":
               var2.a(() -> "Scroll Step");
               break;
            case "startAtTop":
               var2.a(() -> "Start Long Tooltips at Top");
         }
      }
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(this.tooltipScale, this.scrollSpeed, this.startAtTop);
   }

   public static float a(float var0, float var1) {
      return Math.max(0.0F, Math.min(Math.max(0.0F, var1), var0));
   }

   public static boolean a(GuiScreen var0, int var1, int var2, int var3) {
      com.cheatbreaker.client.kazerui.module.modules.visual.ScrollableTooltipsModule var4 = instance;
      if (var4 != null
         && var4.a()
         && var3 != 0
         && var0 instanceof GuiContainer
         && var4.screen == var0
         && System.nanoTime() - var4.lastDraw <= 150000000L
         && Math.abs(var1 - var4.cursorX) <= 8
         && Math.abs(var2 - var4.cursorY) <= 8) {
         int var5 = var3 > 0 ? -1 : 1;
         if (!Keyboard.isKeyDown(29) && !Keyboard.isKeyDown(157)) {
            if (!Keyboard.isKeyDown(42) && !Keyboard.isKeyDown(54)) {
               if (var4.overflowY <= 0.0F) {
                  return false;
               }

               var4.offsetY = a(var4.offsetY + var5 * var4.scrollSpeed.c(), var4.overflowY);
            } else {
               if (var4.overflowX <= 0.0F) {
                  return false;
               }

               var4.offsetX = a(var4.offsetX + var5 * var4.scrollSpeed.c(), var4.overflowX);
            }

            return true;
         } else {
            var4.tooltipScale.b(Math.max(0.25F, Math.min(2.5F, var4.tooltipScale.b() - var5 * 0.1F)));
            return true;
         }
      } else {
         return false;
      }
   }

   public static boolean a(GuiScreen var0, ItemStack var1, List<String> var2, int var3, int var4, FontRenderer var5) {
      com.cheatbreaker.client.kazerui.module.modules.visual.ScrollableTooltipsModule var6 = instance;
      if (var6 != null && var6.a() && !var2.isEmpty() && var0 instanceof GuiContainer) {
         if (var6.screen != var0 || var6.stack != var1 || System.nanoTime() - var6.lastDraw > 150000000L) {
            var6.offsetX = var6.offsetY = 0.0F;
            var6.screen = var0;
            var6.stack = var1;
         }

         var6.cursorX = var3;
         var6.cursorY = var4;
         var6.lastDraw = System.nanoTime();
         int var7 = 0;

         for (String var9 : var2) {
            var7 = Math.max(var7, var5.getStringWidth(var9));
         }

         int var29 = var2.size() == 1 ? 8 : 10 + (var2.size() - 1) * 10;
         float var30 = var6.tooltipScale.b();
         float var10 = Math.max(4, var0.width - 12);
         float var11 = Math.max(4, var0.height - 12);
         var6.overflowX = Math.max(0.0F, var7 * var30 - var10);
         var6.overflowY = Math.max(0.0F, var29 * var30 - var11);
         var6.offsetX = a(var6.offsetX, var6.overflowX);
         var6.offsetY = a(var6.offsetY, var6.overflowY);
         if (var30 == 1.0F && var6.overflowX == 0.0F && var6.overflowY == 0.0F) {
            return false;
         } else {
            float var12 = Math.min(var7 * var30, var10);
            float var13 = Math.min(var29 * var30, var11);
            int var14 = Math.round(Math.max(6.0F, Math.min(var0.width - var12 - 6.0F, (float)(var3 + 12))));
            int var15 = Math.round(Math.max(6.0F, Math.min(var0.height - var13 - 6.0F, (float)(var4 - 12))));
            if (var6.overflowY > 0.0F && var6.startAtTop.b()) {
               var15 = 6;
            }

            int var16 = Math.round(var14 + var12);
            int var17 = Math.round(var15 + var13);
            cn.kazer.client189.render.GlStateBridge.c(1048575);
            GL11.glPushMatrix();

            try {
               GL11.glDisable(2896);
               GL11.glDisable(2929);
               GL11.glDepthMask(false);
               GL11.glTranslatef(0.0F, 0.0F, 300.0F);
               Gui.drawRect(var14 - 4, var15 - 4, var16 + 4, var17 + 4, -15728624);
               Gui.drawRect(var14 - 4, var15 - 4, var16 + 4, var15 - 3, -11513696);
               Gui.drawRect(var14 - 4, var17 + 3, var16 + 4, var17 + 4, -14145456);
               Minecraft var18 = Minecraft.getMinecraft();
               double var19 = (double)var18.displayWidth / var0.width;
               double var21 = (double)var18.displayHeight / var0.height;
               GL11.glEnable(3089);
               GL11.glScissor((int)(var14 * var19), (int)((var0.height - var17) * var21), Math.max(1, (int)(var12 * var19)), Math.max(1, (int)(var13 * var21)));
               GL11.glTranslatef(var14 - var6.offsetX, var15 - var6.offsetY, 0.0F);
               GL11.glScalef(var30, var30, 1.0F);

               for (int var23 = 0; var23 < var2.size(); var23++) {
                  int var24 = var23 * 10 + (var23 > 0 ? 2 : 0);
                  float var25 = var24 * var30 - var6.offsetY;
                  if (var25 + 10.0F * var30 >= 0.0F && var25 <= var13) {
                     TooltipHooks.drawLine(var5, (String)var2.get(var23), 0.0F, var24, -1);
                  }
               }
            } finally {
               GL11.glPopMatrix();
               cn.kazer.client189.render.GlStateBridge.a();
            }

            return true;
         }
      } else {
         return false;
      }
   }
}
