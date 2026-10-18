package com.cheatbreaker.client.kazerui.gui;

import java.io.IOException;
import java.util.Locale;
import java.util.function.Consumer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public final class ConfirmScreen extends GuiScreen {
   private final com.cheatbreaker.client.kazerui.gui.UiGraphics gfx = new com.cheatbreaker.client.kazerui.gui.UiGraphics("confirm");
   private final String name;
   private final Consumer<Boolean> answer;
   private long flip = System.currentTimeMillis();
   private boolean red;

   public ConfirmScreen(String var1, Consumer<Boolean> var2) {
      this.name = var1;
      this.answer = var2;
   }

   private String a(String var1) {
      return com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("gui." + this.name, var1);
   }

   public void initGui() {
      this.gfx.a(new ScaledResolution(this.mc).getScaleFactor());
   }

   private float[] a() {
      return new float[]{this.width / 2.0F - 100.0F, this.height / 2.0F - 50.0F + 60.0F, 200.0F, 16.0F};
   }

   private float[] b() {
      return new float[]{this.width / 2.0F - 100.0F, this.height / 2.0F - 50.0F + 80.0F, 200.0F, 16.0F};
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      long var4 = System.currentTimeMillis();
      if (var4 - this.flip >= 2000L) {
         this.flip = var4;
         this.red = !this.red;
      }

      float var6 = Math.min(1.0F, (float)(var4 - this.flip) / 2000.0F);
      int var7 = this.red ? com.cheatbreaker.client.kazerui.gui.UiGraphics.a(-1, -52429, var6) : com.cheatbreaker.client.kazerui.gui.UiGraphics.a(-52429, -1, var6);
      float var8 = this.width / 2.0F - 160.0F;
      float var9 = this.height / 2.0F - 60.0F;
      float var10 = 320.0F;
      float var11 = 118.0F;
      com.cheatbreaker.client.kazerui.gui.UiGraphics.a(var8, var9 + 24.0F, var8 + var10, var9 + 24.5F, 553648127);
      this.gfx.a(var8, var9 + 1.0F, var10, 23.0F, 5.0F, 620756992);
      this.gfx.a(var8 - 1.0F, var9, var10 + 2.0F, var11, 4.0F, 1.0F, 1073741824);
      this.gfx.a(var8, var9 + 1.0F, var10, var11 - 2.0F, 3.0F, 1.0F, 553648127);
      this.gfx.a(var8, var9 + 1.0F, var10, var11 - 2.0F, 5.0F, Integer.MIN_VALUE);
      float var12 = this.width / 2.0F;
      float var13 = this.height / 2.0F - 54.0F;
      this.a(this.a("warning"), "roboto-light.ttf", 22.0F, var12, var13, var7);
      this.a(this.a("lineOne"), "roboto-medium.ttf", 13.0F, var12, var13 + 30.0F, -1);
      if (!this.a("lineTwo").equals("lineTwo")) {
         this.a(this.a("lineTwo"), "roboto-medium.ttf", 13.0F, var12, var13 + 40.0F, -1);
      }

      this.a(this.b(), "cancel", true, var1, var2);
      this.a(this.a(), "understood", false, var1, var2);
   }

   private void a(String var1, String var2, float var3, float var4, float var5, int var6) {
      this.gfx.a(var1, var2, var3, var4 - this.gfx.a(var1, var2, var3) / 2.0F, var5, var6);
   }

   private void a(float[] var1, String var2, boolean var3, int var4, int var5) {
      boolean var6 = var3 || var4 >= var1[0] && var4 < var1[0] + var1[2] && var5 >= var1[1] && var5 < var1[1] + var1[3];
      this.gfx.a(var1[0], var1[1], var1[2], var1[3], 5.0F, var6 ? 1174405119 : 553648127);
      this.gfx.a(var1[0], var1[1], var1[2], var1[3], 4.0F, 1.0F, 1076176165);
      this.gfx.a(var1[0] + 1.0F, var1[1] + 1.0F, var1[2] - 2.0F, var1[3] - 2.0F, 2.75F, 1.0F, 553648127);
      String var7 = com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("gui.components", var2).toUpperCase(Locale.ROOT).replace("", " ").trim();
      float var8 = this.gfx.a(var7, "roboto-bold.ttf", 14.0F);
      float var9 = var1[1] + var1[3] / 2.0F - this.gfx.a("roboto-bold.ttf", 14.0F) / 2.0F;
      this.gfx.a(var7, "roboto-bold.ttf", 14.0F, var1[0] + var1[2] / 2.0F - var8 / 2.0F + 1.0F, var9 + 1.0F, 536870912);
      this.gfx.a(var7, "roboto-bold.ttf", 14.0F, var1[0] + var1[2] / 2.0F - var8 / 2.0F, var9, -4275267);
   }

   private static boolean a(float[] var0, int var1, int var2) {
      return var1 >= var0[0] && var1 < var0[0] + var0[2] && var2 >= var0[1] && var2 < var0[1] + var0[3];
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      if (var3 == 0) {
         if (a(this.b(), var1, var2)) {
            this.answer.accept(false);
         } else if (a(this.a(), var1, var2)) {
            this.answer.accept(true);
         }
      }
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if (var2 == 1) {
         this.answer.accept(false);
      }
   }

   public void onGuiClosed() {
      this.gfx.a();
   }

   public boolean doesGuiPauseGame() {
      return false;
   }
}
