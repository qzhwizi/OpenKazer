package com.cheatbreaker.client.module.modules.shader;

import javax.vecmath.Matrix4f;
import javax.vecmath.SingularMatrixException;

public final class CameraState {
   private final Matrix4f projection = new Matrix4f();
   private final Matrix4f projectionInverse = new Matrix4f();
   private final Matrix4f modelView = new Matrix4f();
   private final Matrix4f modelViewInverse = new Matrix4f();
   private final Matrix4f previousProjection = new Matrix4f();
   private final Matrix4f previousModelView = new Matrix4f();
   private float cameraX;
   private float cameraY;
   private float cameraZ;
   private float previousCameraX;
   private float previousCameraY;
   private float previousCameraZ;
   private int perspective = -1;
   private boolean changedPerspectiveThisFrame;

   public void a(int var1) {
      this.previousProjection.set(this.projection);
      this.previousModelView.set(this.modelView);
      this.previousCameraX = this.cameraX;
      this.previousCameraY = this.cameraY;
      this.previousCameraZ = this.cameraZ;
      this.changedPerspectiveThisFrame = this.perspective >= 0 && var1 != this.perspective;
      this.perspective = var1;
   }

   public boolean a() {
      return this.changedPerspectiveThisFrame;
   }

   public void a(Matrix4f var1, Matrix4f var2, float var3, float var4, float var5) {
      this.projection.set(var1);
      this.modelView.set(var2);
      this.cameraX = var3;
      this.cameraY = var4;
      this.cameraZ = var5;
      a(var1, this.projectionInverse);
      a(var2, this.modelViewInverse);
   }

   public void b() {
      this.a(this.perspective);
      this.changedPerspectiveThisFrame = false;
   }

   public static Matrix4f a(float[] var0) {
      Matrix4f var1 = new Matrix4f();

      for (int var2 = 0; var2 < 16; var2++) {
         var1.setElement(var2 % 4, var2 / 4, var0[var2]);
      }

      return var1;
   }

   static void a(Matrix4f var0, Matrix4f var1) {
      try {
         var1.invert(var0);
      } catch (SingularMatrixException var3) {
         var1.setIdentity();
      }
   }

   public Matrix4f c() {
      return this.projectionInverse;
   }

   public Matrix4f d() {
      return this.modelViewInverse;
   }

   public Matrix4f e() {
      return this.previousProjection;
   }

   public Matrix4f f() {
      return this.previousModelView;
   }

   public float g() {
      return this.cameraX;
   }

   public float h() {
      return this.cameraY;
   }

   public float i() {
      return this.cameraZ;
   }

   public float j() {
      return this.previousCameraX;
   }

   public float k() {
      return this.previousCameraY;
   }

   public float l() {
      return this.previousCameraZ;
   }
}
