package com.cheatbreaker.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

public abstract class ScaledGuiScreen extends GuiScreen {
   protected ScaledResolution resolution;
   protected float scaledWidth;
   protected float scaledHeight;
   protected List<com.cheatbreaker.client.gui.screens.GuiElement> elements;
   protected int elementListSize = 0;

   public void setWorldAndResolution(Minecraft var1, int var2, int var3) {
      this.mc = var1;
      this.fontRendererObj = var1.fontRendererObj;
      this.width = var2;
      this.height = var3;
      this.buttonList.clear();
      this.resolution = new ScaledResolution(this.mc);
      float var4 = this.d();
      this.scaledWidth = this.width / var4;
      this.scaledHeight = this.height / var4;
      this.initGui();
   }

   public void drawScreen(int var1, int var2, float var3) {
      float var4 = this.d();
      GL11.glPushMatrix();
      GL11.glScalef(var4, var4, var4);
      this.a(var1 / var4, var2 / var4);
      GL11.glPopMatrix();
   }

   protected abstract void a(float var1, float var2);

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      float var4 = this.d();
      this.a(var1 / var4, var2 / var4, var3);
   }

   protected abstract void a(float var1, float var2, int var3);

   protected void mouseReleased(int var1, int var2, int var3) {
      float var4 = this.d();
      this.b(var1 / var4, var2 / var4, var3);
   }

   protected abstract void b(float var1, float var2, int var3);

   protected void a(com.cheatbreaker.client.gui.screens.GuiElement... var1) {
      this.elements = new ArrayList<>();
      this.elements.addAll(Arrays.asList(var1));
      this.elementListSize = this.elements.size();
   }

   public void b(com.cheatbreaker.client.gui.screens.GuiElement... var1) {
      this.elements.addAll(Arrays.asList(var1));
      this.initGui();
   }

   public void c(com.cheatbreaker.client.gui.screens.GuiElement... var1) {
      this.elements.removeAll(Arrays.asList(var1));
      this.initGui();
   }

   protected void a() {
      this.elements.forEach(com.cheatbreaker.client.gui.screens.GuiElement::c);
   }

   protected void b() {
      this.elements.forEach(com.cheatbreaker.client.gui.screens.GuiElement::b);
   }

   protected void c() {
      this.elements.forEach(com.cheatbreaker.client.gui.screens.GuiElement::a);
   }

   protected void a(char var1, int var2) {
      this.elements.forEach(var2x -> var2x.a(var1, var2));
   }

   protected void a(float var1, float var2, com.cheatbreaker.client.gui.screens.GuiElement... var3) {
      List var4 = Arrays.asList(var3);

      for (com.cheatbreaker.client.gui.screens.GuiElement var6 : this.elements) {
         if (!var4.contains(var6)) {
            var6.a(var1, var2, this.a(var6, var1, var2));
         }
      }
   }

   protected void c(float var1, float var2, int var3) {
      Iterator var5 = this.elements.iterator();

      while (var5.hasNext()) {
         com.cheatbreaker.client.gui.screens.GuiElement var4;
         if ((var4 = (com.cheatbreaker.client.gui.screens.GuiElement)var5.next()).a(var1, var2) && var4.b(var1, var2, var3, this.a(var4, var1, var2))) {
            break;
         }
      }
   }

   protected void a(float var1, float var2, int var3, com.cheatbreaker.client.gui.screens.GuiElement... var4) {
      List var5 = Arrays.asList(var4);
      com.cheatbreaker.client.gui.screens.GuiElement var6 = null;
      boolean var7 = false;

      for (com.cheatbreaker.client.gui.screens.GuiElement var9 : this.elements) {
         if (!var5.contains(var9) && var9.a(var1, var2)) {
            if (!this.elements.contains(var9)) {
               var6 = var9;
            }

            if (var9.a(var1, var2, var3, this.a(var9, var1, var2, var4))) {
               var7 = true;
               break;
            }
         }
      }

      if (!var7) {
         if (var6 != null) {
            this.elements.add(this.elements.remove(this.elements.indexOf(var6)));
         }

         Iterator var10 = this.elements.iterator();

         while (var10.hasNext() && !((com.cheatbreaker.client.gui.screens.GuiElement)var10.next()).a(var1, var2, var3)) {
         }
      }
   }

   protected boolean a(com.cheatbreaker.client.gui.screens.GuiElement var1, float var2, float var3, com.cheatbreaker.client.gui.screens.GuiElement... var4) {
      List var6 = Arrays.asList(var4);
      boolean var7 = true;

      com.cheatbreaker.client.gui.screens.GuiElement var5;
      for (int var8 = this.elements.size() - 1; var8 >= 0 && (var5 = this.elements.get(var8)) != var1; var8--) {
         if (!var6.contains(var5) && var5.a(var2, var3)) {
            var7 = false;
            break;
         }
      }

      return var7;
   }

   public float d() {
      float var1;
      switch (this.resolution.getScaleFactor()) {
         case 1:
            var1 = 0.5F;
            break;
         case 2:
         default:
            var1 = 1.0F;
            break;
         case 3:
            var1 = 1.5F;
            break;
         case 4:
            var1 = 2.0F;
      }

      return 1.0F / var1;
   }

   public List<com.cheatbreaker.client.gui.screens.GuiElement> e() {
      return this.elements;
   }

   protected void d(com.cheatbreaker.client.gui.screens.GuiElement... var1) {
      this.elements = new ArrayList<>();
      this.elements.addAll(Arrays.asList(var1));
   }

   public float f() {
      return this.scaledWidth;
   }

   public float g() {
      return this.scaledHeight;
   }

   public ScaledResolution h() {
      return this.resolution;
   }
}
