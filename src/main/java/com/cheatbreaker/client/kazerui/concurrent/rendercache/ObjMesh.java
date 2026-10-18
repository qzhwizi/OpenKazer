package com.cheatbreaker.client.kazerui.concurrent.rendercache;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public final class ObjMesh {
   private final float[] data;
   private int list = -1;

   private ObjMesh(float[] var1) {
      this.data = var1;
   }

   public static com.cheatbreaker.client.kazerui.concurrent.rendercache.ObjMesh a(byte[] var0) throws IOException {
      ArrayList var1 = new ArrayList();
      ArrayList var2 = new ArrayList();
      ArrayList var3 = new ArrayList();
      ArrayList var4 = new ArrayList();
      BufferedReader var5 = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(var0), StandardCharsets.UTF_8));

      String var6;
      while ((var6 = var5.readLine()) != null) {
         var6 = var6.trim();
         if (!var6.isEmpty() && var6.charAt(0) != '#') {
            String[] var7 = var6.split("\\s+");
            String var8 = var7[0];
            switch (var8) {
               case "v":
                  var1.add(new float[]{a(var7[1]), a(var7[2]), a(var7[3])});
                  break;
               case "vt":
                  var2.add(new float[]{a(var7[1]), 1.0F - a(var7[2])});
                  break;
               case "vn":
                  var3.add(new float[]{a(var7[1]), a(var7[2]), a(var7[3])});
                  break;
               case "f":
                  int var10 = var7.length - 1;

                  for (int var11 = 1; var11 + 1 < var10; var11++) {
                     a(var4, var7[1], var1, var2, var3);
                     a(var4, var7[1 + var11], var1, var2, var3);
                     a(var4, var7[2 + var11], var1, var2, var3);
                  }
            }
         }
      }

      float[] var13 = new float[var4.size()];

      for (int var14 = 0; var14 < var13.length; var14++) {
         var13[var14] = (Float)var4.get(var14);
      }

      return new com.cheatbreaker.client.kazerui.concurrent.rendercache.ObjMesh(var13);
   }

   private static float a(String var0) {
      return Float.parseFloat(var0);
   }

   private static void a(List<Float> var0, String var1, List<float[]> var2, List<float[]> var3, List<float[]> var4) {
      String[] var5 = var1.split("/", -1);
      float[] var6 = (float[])var2.get(a(var5[0], var2.size()));
      float[] var7 = var5.length > 1 && !var5[1].isEmpty() ? (float[])var3.get(a(var5[1], var3.size())) : new float[]{0.0F, 0.0F};
      float[] var8 = var5.length > 2 && !var5[2].isEmpty() ? (float[])var4.get(a(var5[2], var4.size())) : new float[]{0.0F, 1.0F, 0.0F};
      var0.add(var6[0]);
      var0.add(var6[1]);
      var0.add(var6[2]);
      var0.add(var7[0]);
      var0.add(var7[1]);
      var0.add(var8[0]);
      var0.add(var8[1]);
      var0.add(var8[2]);
   }

   private static int a(String var0, int var1) {
      int var2 = Integer.parseInt(var0);
      return var2 < 0 ? var1 + var2 : var2 - 1;
   }

   public void a() {
      if (this.list < 0) {
         this.list = GLAllocation.generateDisplayLists(1);
         GL11.glNewList(this.list, 4864);
         WorldRenderer var1 = Tessellator.getInstance().getWorldRenderer();
         var1.begin(4, DefaultVertexFormats.OLDMODEL_POSITION_TEX_NORMAL);

         for (byte var2 = 0; var2 < this.data.length; var2 += 8) {
            var1.pos(this.data[var2], this.data[var2 + 1], this.data[var2 + 2])
               .tex(this.data[var2 + 3], this.data[var2 + 4])
               .normal(this.data[var2 + 5], this.data[var2 + 6], this.data[var2 + 7])
               .endVertex();
         }

         Tessellator.getInstance().draw();
         GL11.glEndList();
      }

      GL11.glCallList(this.list);
   }

   public void b() {
      if (this.list >= 0) {
         GLAllocation.deleteDisplayLists(this.list);
         this.list = -1;
      }
   }
}
