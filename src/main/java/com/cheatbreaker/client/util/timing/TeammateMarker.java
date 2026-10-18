package com.cheatbreaker.client.util.timing;

import java.awt.Color;
import net.minecraft.util.Vec3;

public class TeammateMarker {
   private String lIIIIlIIllIIlIIlIIIlIIllI;
   private boolean lIIIIIIIIIlIllIIllIlIIlIl = false;
   private Vec3 IlllIIIlIlllIllIlIIlllIlI;
   private long IIIIllIlIIIllIlllIlllllIl;
   private Color IIIIllIIllIIIIllIllIIIlIl;
   private long IlIlIIIlllIIIlIlllIlIllIl;

   public void a(Color var1) {
      this.IIIIllIIllIIIIllIllIIIlIl = var1;
   }

   public TeammateMarker(String var1, boolean var2) {
      this.lIIIIlIIllIIlIIlIIIlIIllI = var1;
      this.lIIIIIIIIIlIllIIllIlIIlIl = var2;
      this.IIIIllIlIIIllIlllIlllllIl = System.currentTimeMillis();
   }

   public void a(double var1, double var3, double var5, long var7) {
      this.IlllIIIlIlllIllIlIIlllIlI = new Vec3(var1, var3, var5);
      this.IIIIllIlIIIllIlllIlllllIl = System.currentTimeMillis();
      this.IlIlIIIlllIIIlIlllIlIllIl = var7;
   }

   public Vec3 a() {
      return this.IlllIIIlIlllIllIlIIlllIlI;
   }

   public long b() {
      return this.IIIIllIlIIIllIlllIlllllIl;
   }

   public String c() {
      return this.lIIIIlIIllIIlIIlIIIlIIllI;
   }

   public long d() {
      return this.IlIlIIIlllIIIlIlllIlIllIl;
   }

   public boolean e() {
      return this.lIIIIIIIIIlIllIIllIlIIlIl;
   }

   public Color f() {
      return this.IIIIllIIllIIIIllIllIIIlIl;
   }
}
