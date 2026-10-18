package com.cheatbreaker.client.util.colorutil;

import java.awt.Color;
import javax.vecmath.Vector2d;
import net.minecraft.entity.Entity;

public class WorldBorder {
   private String lIIIIIIIIIlIllIIllIlIIlIl;
   private String world;
   private Color IIIIllIlIIIllIlllIlllllIl;
   private Vector2d IIIIllIIllIIIIllIllIIIlIl;
   private Vector2d IlIlIIIlllIIIlIlllIlIllIl;
   private Vector2d IIIllIllIlIlllllllIlIlIII;
   private Vector2d IllIIIIIIIlIlIllllIIllIII;
   private Vector2d lIIIIllIIlIlIllIIIlIllIlI;
   private Vector2d IlllIllIlIIIIlIIlIIllIIIl;
   private boolean IlIlllIIIIllIllllIllIIlIl;
   private boolean llIIlllIIIIlllIllIlIlllIl;
   private int lIIlIlIllIIlIIIlIIIlllIII;
   private int IIIlllIIIllIllIlIIIIIIlII;
   final com.cheatbreaker.client.util.colorutil.WorldBorderManager worldBorder;

   public WorldBorder(
      com.cheatbreaker.client.util.colorutil.WorldBorderManager var1,
      String var2,
      String var3,
      int var4,
      double var5,
      double var7,
      double var9,
      double var11,
      boolean var13,
      boolean var14
   ) {
      this.worldBorder = var1;
      this.lIIIIIIIIIlIllIIllIlIIlIl = var2;
      this.world = var3;
      this.IIIIllIlIIIllIlllIlllllIl = new Color(var4, true);
      this.IlIlllIIIIllIllllIllIIlIl = var13;
      this.llIIlllIIIIlllIllIlIlllIl = var14;
      this.IIIllIllIlIlllllllIlIlIII = this.IIIIllIIllIIIIllIllIIIlIl = new Vector2d(var5, var7);
      this.IllIIIIIIIlIlIllllIIllIII = this.IlIlIIIlllIIIlIlllIlIllIl = new Vector2d(var9, var11);
   }

   public boolean a() {
      return this.IlIlllIIIIllIllllIllIIlIl && this.IIIlllIIIllIllIlIIIIIIlII != 0 && this.lIIlIlIllIIlIIIlIIIlllIII < this.IIIlllIIIllIllIlIIIIIIlII;
   }

   public boolean a(double var1, double var3) {
      return !this.llIIlllIIIIlllIllIlIlllIl
         || !this.world.equals(com.cheatbreaker.client.util.colorutil.WorldBorderManager.a(this.worldBorder).B().c())
         || var1 + 1.0 > this.f() && var1 < this.d() && var3 + 1.0 > this.g() && var3 < this.e();
   }

   public void b() {
      if (this.a()) {
         double var1 = this.IIIIllIIllIIIIllIllIIIlIl.x - this.lIIIIllIIlIlIllIIIlIllIlI.x;
         double var3 = this.IlIlIIIlllIIIlIlllIlIllIl.x - this.IlllIllIlIIIIlIIlIIllIIIl.x;
         double var5 = this.IIIIllIIllIIIIllIllIIIlIl.y - this.lIIIIllIIlIlIllIIIlIllIlI.y;
         double var7 = this.IlIlIIIlllIIIlIlllIlIllIl.y - this.IlllIllIlIIIIlIIlIIllIIIl.y;
         double var9 = (float)this.lIIlIlIllIIlIIIlIIIlllIII / this.IIIlllIIIllIllIlIIIIIIlII;
         double var11 = this.IIIIllIIllIIIIllIllIIIlIl.x - var1 * var9;
         double var13 = this.IlIlIIIlllIIIlIlllIlIllIl.x - var3 * var9;
         double var15 = this.IIIIllIIllIIIIllIllIIIlIl.y - var5 * var9;
         double var17 = this.IlIlIIIlllIIIlIlllIlIllIl.y - var7 * var9;
         this.IIIllIllIlIlllllllIlIlIII = new Vector2d(var11, var15);
         this.IllIIIIIIIlIlIllllIIllIII = new Vector2d(var13, var17);
         this.lIIlIlIllIIlIIIlIIIlllIII++;
      } else if (this.IlIlIIIlllIIIlIlllIlIllIl != this.IllIIIIIIIlIlIllllIIllIII || this.IIIIllIIllIIIIllIllIIIlIl != this.IIIllIllIlIlllllllIlIlIII) {
         this.IIIIllIIllIIIIllIllIIIlIl = this.IIIllIllIlIlllllllIlIlIII;
         this.IlIlIIIlllIIIlIlllIlIllIl = this.IllIIIIIIIlIlIllllIIllIII;
         this.IllIIIIIIIlIlIllllIIllIII = this.IlllIllIlIIIIlIIlIIllIIIl;
         this.IIIllIllIlIlllllllIlIlIII = this.lIIIIllIIlIlIllIIIlIllIlI;
         this.IIIlllIIIllIllIlIIIIIIlII = 0;
         this.lIIlIlIllIIlIIIlIIIlllIII = 0;
      }
   }

   public boolean c() {
      return com.cheatbreaker.client.CheatBreakerClient.N().B().c().equals(this.world);
   }

   public double d() {
      return this.IllIIIIIIIlIlIllllIIllIII.x;
   }

   public double e() {
      return this.IllIIIIIIIlIlIllllIIllIII.y;
   }

   public double f() {
      return this.IIIllIllIlIlllllllIlIlIII.x;
   }

   public double g() {
      return this.IIIllIllIlIlllllllIlIlIII.y;
   }

   public double a(Entity var1) {
      return this.b(var1.posX, var1.posZ);
   }

   public double b(double var1, double var3) {
      double var5 = var3 - this.g();
      double var7 = this.e() - var3;
      double var9 = var1 - this.f();
      double var11 = this.d() - var1;
      double var13 = Math.min(var9, var11);
      var13 = Math.min(var13, var5);
      return Math.min(var13, var7);
   }

   public double c(double var1, double var3) {
      double var5 = var3 - this.g();
      double var7 = this.e() - var3;
      return Math.min(var5, var7);
   }

   public double d(double var1, double var3) {
      double var5 = var1 - this.f();
      double var7 = this.d() - var1;
      return Math.min(var5, var7);
   }

   public String h() {
      return this.lIIIIIIIIIlIllIIllIlIIlIl;
   }

   public String i() {
      return this.world;
   }

   public Color j() {
      return this.IIIIllIlIIIllIlllIlllllIl;
   }

   public Vector2d k() {
      return this.IIIIllIIllIIIIllIllIIIlIl;
   }

   public Vector2d l() {
      return this.IlIlIIIlllIIIlIlllIlIllIl;
   }

   public Vector2d m() {
      return this.IIIllIllIlIlllllllIlIlIII;
   }

   public Vector2d n() {
      return this.IllIIIIIIIlIlIllllIIllIII;
   }

   public Vector2d o() {
      return this.lIIIIllIIlIlIllIIIlIllIlI;
   }

   public Vector2d p() {
      return this.IlllIllIlIIIIlIIlIIllIIIl;
   }

   public boolean q() {
      return this.IlIlllIIIIllIllllIllIIlIl;
   }

   public boolean r() {
      return this.llIIlllIIIIlllIllIlIlllIl;
   }

   public int s() {
      return this.lIIlIlIllIIlIIIlIIIlllIII;
   }

   public int t() {
      return this.IIIlllIIIllIllIlIIIIIIlII;
   }

   public static String a(com.cheatbreaker.client.util.colorutil.WorldBorder var0) {
      return var0.lIIIIIIIIIlIllIIllIlIIlIl;
   }

   public static Vector2d a(com.cheatbreaker.client.util.colorutil.WorldBorder var0, Vector2d var1) {
      var0.lIIIIllIIlIlIllIIIlIllIlI = var1;
      return var0.lIIIIllIIlIlIllIIIlIllIlI;
   }

   public static Vector2d b(com.cheatbreaker.client.util.colorutil.WorldBorder var0, Vector2d var1) {
      var0.IlllIllIlIIIIlIIlIIllIIIl = var1;
      return var0.IlllIllIlIIIIlIIlIIllIIIl;
   }

   public static int a(com.cheatbreaker.client.util.colorutil.WorldBorder var0, int var1) {
      var0.lIIlIlIllIIlIIIlIIIlllIII = var1;
      return var0.lIIlIlIllIIlIIIlIIIlllIII;
   }

   public static int b(com.cheatbreaker.client.util.colorutil.WorldBorder var0, int var1) {
      var0.IIIlllIIIllIllIlIIIIIIlII = var1;
      return var0.IIIlllIIIllIllIlIIIIIIlII;
   }

   public static boolean b(com.cheatbreaker.client.util.colorutil.WorldBorder var0) {
      return var0.IlIlllIIIIllIllllIllIIlIl;
   }

   public static Color c(com.cheatbreaker.client.util.colorutil.WorldBorder var0) {
      return var0.IIIIllIlIIIllIlllIlllllIl;
   }
}
