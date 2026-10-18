package com.cheatbreaker.client.gui.screens;

import net.minecraft.client.Minecraft;

public abstract class GuiElement {
   protected float x;
   protected float y;
   protected float width;
   protected float height;
   protected final Minecraft mc = Minecraft.getMinecraft();
   protected final com.cheatbreaker.client.CheatBreakerClient client = com.cheatbreaker.client.CheatBreakerClient.N();

   public boolean a(float var1, float var2) {
      boolean var3 = var1 > this.x;
      boolean var4 = var1 < this.x + this.width;
      boolean var5 = var2 > this.y;
      boolean var6 = var2 < this.y + this.height;
      return var3 && var4 && var5 && var6;
   }

   public boolean a(float var1, float var2, boolean var3) {
      this.b(var1, var2, var3);
      return this.a(var1, var2);
   }

   public void a(float var1, float var2, float var3, float var4) {
      this.x = var1;
      this.y = var2;
      this.width = var3;
      this.height = var4;
   }

   public void a() {
   }

   public void b() {
   }

   public void a(char var1, int var2) {
   }

   public void c() {
   }

   protected abstract void b(float var1, float var2, boolean var3);

   public boolean a(float var1, float var2, int var3, boolean var4) {
      return false;
   }

   public boolean b(float var1, float var2, int var3, boolean var4) {
      return false;
   }

   public boolean a(float var1, float var2, int var3) {
      return false;
   }

   public void a(float var1) {
      this.x = var1;
   }

   public void b(float var1) {
      this.y = var1;
   }

   public void c(float var1) {
      this.width = var1;
   }

   public void d(float var1) {
      this.height = var1;
   }

   public float d() {
      return this.x;
   }

   public float e() {
      return this.y;
   }

   public float f() {
      return this.width;
   }

   public float g() {
      return this.height;
   }
}
