package com.cheatbreaker.client.kazerui.concurrent;

public enum CosmeticDisplayPose {
   FRONT(new float[0][], 0.5F, 0.0F, 1.0F),
   BACK(new float[][]{{180.0F, 0.0F, 1.0F, 0.0F}, {0.0F, 0.0F, 0.0F, 0.0F}}, 0.5F, 0.0F, 1.0F),
   DEFAULT(new float[][]{{20.0F, 1.0F, 0.0F, 0.0F}, {-90.0F, 0.0F, 1.0F, 0.0F}, {135.0F, 0.0F, 1.0F, 0.0F}}, 0.5F, 0.0F, 0.9F),
   SUITS(new float[][]{{20.0F, 1.0F, 0.0F, 0.0F}, {-90.0F, 0.0F, 1.0F, 0.0F}, {135.0F, 0.0F, 1.0F, 0.0F}}, 0.5F, 0.0F, 0.75F),
   HAT(new float[][]{{20.0F, 1.0F, 0.0F, 0.0F}, {-90.0F, 0.0F, 1.0F, 0.0F}, {135.0F, 0.0F, 1.0F, 0.0F}}, 0.5F, 0.4F, 1.35F),
   CLOAK(new float[][]{{20.0F, 1.0F, 0.0F, 0.0F}, {135.0F, 0.0F, 1.0F, 0.0F}}, 0.55F, -0.15F, 1.35F),
   WING(new float[][]{{20.0F, 1.0F, 0.0F, 0.0F}, {135.0F, 0.0F, 1.0F, 0.0F}}, 0.6F, 0.0F, 0.8F),
   SHOES(new float[][]{{20.0F, 1.0F, 0.0F, 0.0F}, {-90.0F, 0.0F, 1.0F, 0.0F}, {115.0F, 0.0F, 1.0F, 0.0F}}, 0.5F, -0.75F, 1.6F),
   BELT(new float[][]{{20.0F, 1.0F, 0.0F, 0.0F}, {-90.0F, 0.0F, 1.0F, 0.0F}, {115.0F, 0.0F, 1.0F, 0.0F}}, 0.5F, -0.275F, 1.75F),
   NECK(new float[0][], 0.5F, 0.0F, 1.0F),
   BUST(new float[][]{{20.0F, 1.0F, 0.0F, 0.0F}, {-90.0F, 0.0F, 1.0F, 0.0F}, {115.0F, 0.0F, 1.0F, 0.0F}}, 0.5F, 0.2F, 1.65F),
   DUMMY(new float[][]{{20.0F, 1.0F, 0.0F, 0.0F}, {-180.0F, 0.0F, 1.0F, 0.0F}, {145.0F, 0.0F, 1.0F, 0.0F}}, 0.5F, 0.0F, 0.9F),
   WRIST(new float[][]{{70.5F, 0.0F, 1.0F, 0.0F}}, 0.65F, -0.25F, 2.0F),
   ITEM(new float[][]{{70.5F, 0.0F, 1.0F, 0.0F}}, 0.35F, 0.0F, 0.9F);

   public final float[][] rotation;
   public final float xOffset;
   public final float yOffset;
   public final float zoom;

   private CosmeticDisplayPose(float[][] var3, float var4, float var5, float var6) {
      this.rotation = var3;
      this.xOffset = var4;
      this.yOffset = var5;
      this.zoom = var6;
   }
}
