package cn.kazer.client189.render;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

public final class ImmediateTessellator {
   public static final cn.kazer.client189.render.ImmediateTessellator instance = new cn.kazer.client189.render.ImmediateTessellator();
   private double xOffset;
   private double yOffset;
   private double zOffset;
   private int mode;
   private boolean drawing;
   private final List<cn.kazer.client189.render.ImmediateTessellator.a> vertices = new ArrayList<>();
   private int vertexCount;
   private float red = 1.0F;
   private float green = 1.0F;
   private float blue = 1.0F;
   private float alpha = 1.0F;
   private float nx;
   private float ny;
   private float nz;
   private boolean colored;
   private boolean normal;

   public void a() {
      this.a(7);
   }

   public void a(int var1) {
      if (this.drawing) {
         throw new IllegalStateException("Already drawing");
      } else {
         this.mode = var1;
         this.vertexCount = 0;
         this.drawing = true;
         this.colored = false;
         this.normal = false;
      }
   }

   public int b() {
      if (!this.drawing) {
         throw new IllegalStateException("Not drawing");
      } else {
         this.drawing = false;
         GL11.glBegin(this.mode);

         for (int var1 = 0; var1 < this.vertexCount; var1++) {
            cn.kazer.client189.render.ImmediateTessellator.a var2 = this.vertices.get(var1);
            if (var2.colored) {
               GL11.glColor4f(var2.r, var2.g, var2.b, var2.a);
            }

            if (var2.normal) {
               GL11.glNormal3f(var2.nx, var2.ny, var2.nz);
            }

            if (var2.textured) {
               GL11.glTexCoord2d(var2.u, var2.v);
            }

            GL11.glVertex3d(var2.x, var2.y, var2.z);
         }

         GL11.glEnd();
         if (this.colored) {
            GlStateManager.resetColor();
         }

         return this.vertexCount;
      }
   }

   public void a(double var1, double var3, double var5) {
      this.a(var1, var3, var5, 0.0, 0.0, false);
   }

   public void a(double var1, double var3, double var5, double var7, double var9) {
      this.a(var1, var3, var5, var7, var9, true);
   }

   private void a(double var1, double var3, double var5, double var7, double var9, boolean var11) {
      if (!this.drawing) {
         throw new IllegalStateException("Not drawing");
      } else {
         if (this.vertexCount == this.vertices.size()) {
            this.vertices.add(new cn.kazer.client189.render.ImmediateTessellator.a());
         }

         cn.kazer.client189.render.ImmediateTessellator.a var12 = this.vertices.get(this.vertexCount++);
         var12.x = var1 + this.xOffset;
         var12.y = var3 + this.yOffset;
         var12.z = var5 + this.zOffset;
         var12.u = var7;
         var12.v = var9;
         var12.textured = var11;
         var12.r = this.red;
         var12.g = this.green;
         var12.b = this.blue;
         var12.a = this.alpha;
         var12.colored = this.colored;
         var12.nx = this.nx;
         var12.ny = this.ny;
         var12.nz = this.nz;
         var12.normal = this.normal;
      }
   }

   public void a(float var1, float var2, float var3, float var4) {
      this.red = var1;
      this.green = var2;
      this.blue = var3;
      this.alpha = var4;
      this.colored = true;
   }

   public void a(float var1, float var2, float var3) {
      this.a(var1, var2, var3, 1.0F);
   }

   public void a(int var1, int var2, int var3, int var4) {
      this.a(var1 / 255.0F, var2 / 255.0F, var3 / 255.0F, var4 / 255.0F);
   }

   public void a(int var1, int var2) {
      this.a(var1 >> 16 & 0xFF, var1 >> 8 & 0xFF, var1 & 0xFF, var2);
   }

   public void b(int var1) {
      this.a(var1, 255);
   }

   public void a(int var1, int var2, int var3) {
      this.a(var1, var2, var3, 255);
   }

   public void b(float var1, float var2, float var3) {
      this.nx = var1;
      this.ny = var2;
      this.nz = var3;
      this.normal = true;
   }

   public void b(double var1, double var3, double var5) {
      this.xOffset = var1;
      this.yOffset = var3;
      this.zOffset = var5;
   }

   public void c(int var1) {
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var1 & 65535, var1 >>> 16);
   }

   public void c() {
   }

   private static final class a {
      double x;
      double y;
      double z;
      double u;
      double v;
      float r;
      float g;
      float b;
      float a;
      float nx;
      float ny;
      float nz;
      boolean colored;
      boolean normal;
      boolean textured;

      private a() {
      }
   }
}
