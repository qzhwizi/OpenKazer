package com.cheatbreaker.client.kazerui.concurrent.easing;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public final class BedrockModel {
   public final List<com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel.a> roots = new ArrayList<>();
   public final Map<String, com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel.a> bones = new HashMap<>();

   public static com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel a(JsonObject var0) {
      com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel var1 = new com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel();
      JsonObject var2 = null;
      float var3 = 64.0F;
      float var4 = 64.0F;
      if (var0.has("minecraft:geometry")) {
         var2 = var0.getAsJsonArray("minecraft:geometry").get(0).getAsJsonObject();
         JsonObject var5 = var2.getAsJsonObject("description");
         if (var5 != null) {
            if (var5.has("texture_width")) {
               var3 = var5.get("texture_width").getAsFloat();
            }

            if (var5.has("texture_height")) {
               var4 = var5.get("texture_height").getAsFloat();
            }
         }
      } else {
         for (Entry var6 : var0.entrySet()) {
            if (((String)var6.getKey()).startsWith("geometry.") && ((JsonElement)var6.getValue()).isJsonObject()) {
               var2 = ((JsonElement)var6.getValue()).getAsJsonObject();
               break;
            }
         }

         if (var2 != null) {
            if (var2.has("texturewidth")) {
               var3 = var2.get("texturewidth").getAsFloat();
            }

            if (var2.has("textureheight")) {
               var4 = var2.get("textureheight").getAsFloat();
            }
         }
      }

      if (var2 != null && var2.has("bones")) {
         LinkedHashMap var16 = new LinkedHashMap();

         for (JsonElement var7 : var2.getAsJsonArray("bones")) {
            JsonObject var8 = var7.getAsJsonObject();
            String var9 = var8.get("name").getAsString();
            com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel.a var10 = new com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel.a(var9, a(var8, "pivot", 0.0F), a(var8, "rotation", 0.0F));
            boolean var11 = var8.has("mirror") && var8.get("mirror").getAsBoolean();
            float var12 = var8.has("inflate") ? var8.get("inflate").getAsFloat() : 0.0F;
            if (var8.has("cubes")) {
               for (JsonElement var14 : var8.getAsJsonArray("cubes")) {
                  a(var10, var14.getAsJsonObject(), var3, var4, var11, var12);
               }
            }

            if (var8.has("neverRender") && var8.get("neverRender").getAsBoolean()) {
               var10.hidden = true;
            }

            var1.bones.put(var9, var10);
            var16.put(var9, var8.has("parent") ? var8.get("parent").getAsString() : null);
         }

         for (Entry var19 : (Iterable<Entry>) var16.entrySet()) {
            com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel.a var20 = var1.bones.get(var19.getKey());
            com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel.a var21 = var19.getValue() == null ? null : var1.bones.get(var19.getValue());
            if (var21 != null && var21 != var20) {
               var21.children.add(var20);
            } else {
               var1.roots.add(var20);
            }
         }

         return var1;
      } else {
         return var1;
      }
   }

   private static float[] a(JsonObject var0, String var1, float var2) {
      if (var0.has(var1) && var0.get(var1).isJsonArray()) {
         JsonArray var3 = var0.getAsJsonArray(var1);
         return new float[]{var3.get(0).getAsFloat(), var3.get(1).getAsFloat(), var3.get(2).getAsFloat()};
      } else {
         return new float[]{var2, var2, var2};
      }
   }

   private static void a(com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel.a var0, JsonObject var1, float var2, float var3, boolean var4, float var5) {
      float[] var6 = a(var1, "origin", 0.0F);
      float[] var7 = a(var1, "size", 0.0F);
      float var8 = (var1.has("inflate") ? var1.get("inflate").getAsFloat() : var5) / 16.0F;
      boolean var9 = var1.has("mirror") ? var1.get("mirror").getAsBoolean() : var4;
      float var10 = -(var6[0] + var7[0]) / 16.0F;
      float var11 = var6[1] / 16.0F;
      float var12 = var6[2] / 16.0F;
      float var13 = var7[0] / 16.0F;
      float var14 = var7[1] / 16.0F;
      float var15 = var7[2] / 16.0F;
      float var16 = var10 - var8;
      float var17 = var11 - var8;
      float var18 = var12 - var8;
      float var19 = var10 + var13 + var8;
      float var20 = var11 + var14 + var8;
      float var21 = var12 + var15 + var8;
      float[] var22 = new float[]{var16, var17, var18};
      float[] var23 = new float[]{var16, var17, var21};
      float[] var24 = new float[]{var16, var20, var18};
      float[] var25 = new float[]{var16, var20, var21};
      float[] var26 = new float[]{var19, var17, var18};
      float[] var27 = new float[]{var19, var17, var21};
      float[] var28 = new float[]{var19, var20, var18};
      float[] var29 = new float[]{var19, var20, var21};
      float[][] var30 = new float[6][];
      JsonElement var31 = var1.get("uv");
      if (var31 != null && var31.isJsonArray()) {
         float var54 = var31.getAsJsonArray().get(0).getAsFloat();
         float var56 = var31.getAsJsonArray().get(1).getAsFloat();
         float var58 = (float)Math.floor(var7[0]);
         float var60 = (float)Math.floor(var7[1]);
         float var62 = (float)Math.floor(var7[2]);
         var30[0] = new float[]{var54 + var62 + var58, var56 + var62, var62, var60};
         var30[1] = new float[]{var54, var56 + var62, var62, var60};
         var30[2] = new float[]{var54 + var62, var56 + var62, var58, var60};
         var30[3] = new float[]{var54 + var62 + var58 + var62, var56 + var62, var58, var60};
         var30[4] = new float[]{var54 + var62, var56, var58, var62};
         var30[5] = new float[]{var54 + var62 + var58, var56 + var62, var58, -var62};
      } else if (var31 != null && var31.isJsonObject()) {
         String[] var32 = new String[]{"west", "east", "north", "south", "up", "down"};

         for (int var33 = 0; var33 < 6; var33++) {
            JsonObject var34 = var31.getAsJsonObject().getAsJsonObject(var32[var33]);
            if (var34 != null && var34.has("uv")) {
               JsonArray var35 = var34.getAsJsonArray("uv");
               JsonArray var36 = var34.has("uv_size") ? var34.getAsJsonArray("uv_size") : null;
               var30[var33] = new float[]{
                  var35.get(0).getAsFloat(),
                  var35.get(1).getAsFloat(),
                  var36 == null ? 0.0F : var36.get(0).getAsFloat(),
                  var36 == null ? 0.0F : var36.get(1).getAsFloat()
               };
            }
         }
      }

      float[][][] var55 = new float[][][]{
         {var25, var24, var22, var23},
         {var28, var29, var27, var26},
         {var24, var28, var26, var22},
         {var29, var25, var23, var27},
         {var25, var29, var28, var24},
         {var22, var26, var27, var23}
      };
      float[][] var57 = new float[][]{
         {-1.0F, 0.0F, 0.0F}, {1.0F, 0.0F, 0.0F}, {0.0F, 0.0F, -1.0F}, {0.0F, 0.0F, 1.0F}, {0.0F, 1.0F, 0.0F}, {0.0F, -1.0F, 0.0F}
      };
      float[] var59 = a(var1, "pivot", 0.0F);
      float[] var61 = a(var1, "rotation", 0.0F);
      float var63 = -var59[0] / 16.0F;
      float var37 = var59[1] / 16.0F;
      float var38 = var59[2] / 16.0F;
      float var39 = (float)Math.toRadians(-var61[0]);
      float var40 = (float)Math.toRadians(-var61[1]);
      float var41 = (float)Math.toRadians(var61[2]);

      for (int var42 = 0; var42 < 6; var42++) {
         float[] var43 = var30[var42];
         if (var43 != null) {
            float var44 = var43[0] / var2;
            float var45 = var43[1] / var3;
            float var46 = (var43[0] + var43[2]) / var2;
            float var47 = (var43[1] + var43[3]) / var3;
            float[][] var48 = (float[][])var55[var42].clone();
            float[][] var49 = new float[][]{{var46, var45}, {var44, var45}, {var44, var47}, {var46, var47}};
            float[] var50 = (float[])var57[var42].clone();
            if (var9) {
               var49 = new float[][]{{var44, var45}, {var46, var45}, {var46, var47}, {var44, var47}};
               var50[0] = -var50[0];
            }

            float[] var51 = new float[23];

            for (int var52 = 0; var52 < 4; var52++) {
               float[] var53 = a(var48[var52], var63, var37, var38, var39, var40, var41);
               var51[var52 * 5] = var53[0];
               var51[var52 * 5 + 1] = var53[1];
               var51[var52 * 5 + 2] = var53[2];
               var51[var52 * 5 + 3] = var49[var52][0];
               var51[var52 * 5 + 4] = var49[var52][1];
            }

            float[] var64 = a(var50, 0.0F, 0.0F, 0.0F, var39, var40, var41);
            var51[20] = var64[0];
            var51[21] = var64[1];
            var51[22] = var64[2];
            var0.quads.add(var51);
         }
      }
   }

   private static float[] a(float[] var0, float var1, float var2, float var3, float var4, float var5, float var6) {
      double var7 = var0[0] - var1;
      double var9 = var0[1] - var2;
      double var11 = var0[2] - var3;
      if (var4 != 0.0F) {
         double var13 = Math.cos(var4);
         double var15 = Math.sin(var4);
         double var17 = var9 * var13 - var11 * var15;
         double var19 = var9 * var15 + var11 * var13;
         var9 = var17;
         var11 = var19;
      }

      if (var5 != 0.0F) {
         double var21 = Math.cos(var5);
         double var23 = Math.sin(var5);
         double var25 = var7 * var21 + var11 * var23;
         double var27 = -var7 * var23 + var11 * var21;
         var7 = var25;
         var11 = var27;
      }

      if (var6 != 0.0F) {
         double var22 = Math.cos(var6);
         double var24 = Math.sin(var6);
         double var26 = var7 * var22 - var9 * var24;
         double var28 = var7 * var24 + var9 * var22;
         var7 = var26;
         var9 = var28;
      }

      return new float[]{(float)(var7 + var1), (float)(var9 + var2), (float)(var11 + var3)};
   }

   public float[] a() {
      float[] var1 = new float[]{Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE};

      for (com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel.a var3 : this.bones.values()) {
         for (float[] var5 : var3.quads) {
            for (int var6 = 0; var6 < 4; var6++) {
               for (int var7 = 0; var7 < 3; var7++) {
                  var1[var7] = Math.min(var1[var7], var5[var6 * 5 + var7]);
                  var1[var7 + 3] = Math.max(var1[var7 + 3], var5[var6 * 5 + var7]);
               }
            }
         }
      }

      return var1[0] > var1[3] ? new float[6] : var1;
   }

   public boolean a(String var1) {
      com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel.a var2 = this.bones.get(var1);
      return var2 != null && a(var2);
   }

   private static boolean a(com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel.a var0) {
      if (!var0.quads.isEmpty()) {
         return true;
      } else {
         for (com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel.a var2 : var0.children) {
            if (a(var2)) {
               return true;
            }
         }

         return false;
      }
   }

   public void b() {
      for (com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel.a var2 : this.bones.values()) {
         var2.a();
      }
   }

   public void c() {
      for (com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel.a var2 : this.roots) {
         b(var2);
      }
   }

   private static void b(com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel.a var0) {
      if (!var0.hidden) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(-var0.posX / 16.0F, var0.posY / 16.0F, var0.posZ / 16.0F);
         GlStateManager.translate(var0.pivotX / 16.0F, var0.pivotY / 16.0F, var0.pivotZ / 16.0F);
         if (var0.rotZ != 0.0F) {
            GlStateManager.rotate((float)Math.toDegrees(var0.rotZ), 0.0F, 0.0F, 1.0F);
         }

         if (var0.rotY != 0.0F) {
            GlStateManager.rotate((float)Math.toDegrees(var0.rotY), 0.0F, 1.0F, 0.0F);
         }

         if (var0.rotX != 0.0F) {
            GlStateManager.rotate((float)Math.toDegrees(var0.rotX), 1.0F, 0.0F, 0.0F);
         }

         GlStateManager.scale(var0.scaleX, var0.scaleY, var0.scaleZ);
         GlStateManager.translate(-var0.pivotX / 16.0F, -var0.pivotY / 16.0F, -var0.pivotZ / 16.0F);
         if (!var0.quads.isEmpty()) {
            if (var0.list < 0) {
               var0.list = GLAllocation.generateDisplayLists(1);
               GL11.glNewList(var0.list, 4864);
               WorldRenderer var1 = Tessellator.getInstance().getWorldRenderer();
               var1.begin(7, DefaultVertexFormats.OLDMODEL_POSITION_TEX_NORMAL);

               for (float[] var3 : var0.quads) {
                  for (int var4 = 0; var4 < 4; var4++) {
                     var1.pos(var3[var4 * 5], var3[var4 * 5 + 1], var3[var4 * 5 + 2])
                        .tex(var3[var4 * 5 + 3], var3[var4 * 5 + 4])
                        .normal(var3[20], var3[21], var3[22])
                        .endVertex();
                  }
               }

               Tessellator.getInstance().draw();
               GL11.glEndList();
            }

            GL11.glCallList(var0.list);
         }

         for (com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel.a var6 : var0.children) {
            b(var6);
         }

         GlStateManager.popMatrix();
      }
   }

   public void d() {
      for (com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel.a var2 : this.bones.values()) {
         if (var2.list >= 0) {
            GLAllocation.deleteDisplayLists(var2.list);
            var2.list = -1;
         }
      }
   }

   public static final class a {
      public final String name;
      final float pivotX;
      final float pivotY;
      final float pivotZ;
      final float initRotX;
      final float initRotY;
      final float initRotZ;
      final List<com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel.a> children = new ArrayList<>();
      final List<float[]> quads = new ArrayList<>();
      int list = -1;
      public float rotX;
      public float rotY;
      public float rotZ;
      public float posX;
      public float posY;
      public float posZ;
      public float scaleX = 1.0F;
      public float scaleY = 1.0F;
      public float scaleZ = 1.0F;
      public boolean hidden;

      a(String var1, float[] var2, float[] var3) {
         this.name = var1;
         this.pivotX = -var2[0];
         this.pivotY = var2[1];
         this.pivotZ = var2[2];
         this.initRotX = (float)Math.toRadians(-var3[0]);
         this.initRotY = (float)Math.toRadians(-var3[1]);
         this.initRotZ = (float)Math.toRadians(var3[2]);
         this.a();
      }

      public void a() {
         this.rotX = this.initRotX;
         this.rotY = this.initRotY;
         this.rotZ = this.initRotZ;
         this.posX = this.posY = this.posZ = 0.0F;
         this.scaleX = this.scaleY = this.scaleZ = 1.0F;
      }

      public float b() {
         return this.initRotX;
      }

      public float c() {
         return this.initRotY;
      }

      public float d() {
         return this.initRotZ;
      }
   }
}
