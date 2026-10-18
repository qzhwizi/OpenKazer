package com.cheatbreaker.client.util.colorutil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.vecmath.Vector2d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class WorldBorderManager {
   private final Minecraft minecraft = Minecraft.getMinecraft();
   private final com.cheatbreaker.client.CheatBreakerClient cheatbreaker = com.cheatbreaker.client.CheatBreakerClient.N();
   private static final ResourceLocation forceFieldTexture = new ResourceLocation("textures/misc/forcefield.png");
   private final List<com.cheatbreaker.client.util.colorutil.WorldBorder> borderList = new ArrayList<>();

   public WorldBorderManager() {
      com.cheatbreaker.client.CheatBreakerClient.N().m().a(com.cheatbreaker.client.event.event.ClientTickEvent.class, this::a);
      com.cheatbreaker.client.CheatBreakerClient.N().m().a(com.cheatbreaker.client.event.event.RenderTickEvent.class, this::a);
      com.cheatbreaker.client.CheatBreakerClient.N().m().a(com.cheatbreaker.client.event.event.BlockCollisionBoxesEvent.class, this::a);
   }

   private void a(com.cheatbreaker.client.event.event.BlockCollisionBoxesEvent var1) {
      for (com.cheatbreaker.client.util.colorutil.WorldBorder var3 : this.borderList) {
         if (!var3.a(var1.b(), var1.d())) {
            var1.a().add(new AxisAlignedBB(var1.b(), var1.c(), var1.d(), var1.b() + 1.0, var1.c() + 1.0, var1.d() + 1.0));
         }
      }
   }

   private void a(com.cheatbreaker.client.event.event.ClientTickEvent var1) {
      this.borderList.forEach(var0 -> var0.b());
   }

   private void a(com.cheatbreaker.client.event.event.RenderTickEvent var1) {
      if (!this.borderList.isEmpty()) {
         EntityPlayerSP var2 = this.minecraft.thePlayer;
         float var3 = var1.a();
         this.borderList
            .stream()
            .filter(var0 -> var0.c())
            .forEach(
               var3x -> {
                  cn.kazer.client189.render.ImmediateTessellator var4 = cn.kazer.client189.render.ImmediateTessellator.instance;
                  double var5 = this.minecraft.gameSettings.renderDistanceChunks * 16;
                  if (var2.posX >= var3x.d() - var5
                     || var2.posX <= var3x.f() + var5
                     || var2.posZ >= var3x.e() - var5
                     || var2.posZ <= var3x.g() + var5) {
                     double var13 = 1.0 - var3x.a(var2) / var5;
                     var13 = Math.pow(var13, 4.0);
                     double var15 = var2.lastTickPosX + (var2.posX - var2.lastTickPosX) * var3;
                     double var17 = var2.lastTickPosY + (var2.posY - var2.lastTickPosY) * var3;
                     double var19 = var2.lastTickPosZ + (var2.posZ - var2.lastTickPosZ) * var3;
                     cn.kazer.client189.render.GlStateBridge.a(3042);
                     cn.kazer.client189.render.GlStateBridge.b(770, 1);
                     this.minecraft.getTextureManager().bindTexture(forceFieldTexture);
                     cn.kazer.client189.render.GlStateBridge.a(false);
                     GL11.glPushMatrix();
                     boolean var21 = true;
                     float var22 = (com.cheatbreaker.client.util.colorutil.WorldBorder.c(var3x).getRed() & 0xFF) / 255.0F;
                     float var23 = (com.cheatbreaker.client.util.colorutil.WorldBorder.c(var3x).getGreen() & 0xFF) / 255.0F;
                     float var24 = (com.cheatbreaker.client.util.colorutil.WorldBorder.c(var3x).getBlue() & 0xFF) / 255.0F;
                     GL11.glPolygonOffset(-3.0F, -3.0F);
                     cn.kazer.client189.render.GlStateBridge.a(32823);
                     cn.kazer.client189.render.GlStateBridge.a(516, 0.1F);
                     cn.kazer.client189.render.GlStateBridge.a(3008);
                     cn.kazer.client189.render.GlStateBridge.b(2884);
                     float var25 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F;
                     var4.a();
                     GL11.glTranslated(-var15, -var17, -var19);
                     var4.a(var22, var23, var24, 1.0F);
                     double var26 = Math.max((double)MathHelper.floor_double(var19 - var5), var3x.g());
                     double var28 = Math.min((double)MathHelper.ceiling_double_int(var19 + var5), var3x.e());
                     if (var15 > var3x.d() - var5) {
                        float var12 = 0.0F;

                        for (double var10 = var26; var10 < var28; var12 += 0.5F) {
                           double var8 = Math.min(1.0, var28 - var10);
                           float var7 = (float)var8 * 0.5F;
                           var4.a(var3x.d(), 256.0, var10, var25 + var12, var25 + 0.0F);
                           var4.a(var3x.d(), 256.0, var10 + var8, var25 + var7 + var12, var25 + 0.0F);
                           var4.a(var3x.d(), 0.0, var10 + var8, var25 + var7 + var12, var25 + 128.0F);
                           var4.a(var3x.d(), 0.0, var10, var25 + var12, var25 + 128.0F);
                           var10++;
                        }
                     }

                     if (var15 < var3x.f() + var5) {
                        float var39 = 0.0F;

                        for (double var36 = var26; var36 < var28; var39 += 0.5F) {
                           double var33 = Math.min(1.0, var28 - var36);
                           float var30 = (float)var33 * 0.5F;
                           var4.a(var3x.f(), 256.0, var36, var25 + var39, var25 + 0.0F);
                           var4.a(var3x.f(), 256.0, var36 + var33, var25 + var30 + var39, var25 + 0.0F);
                           var4.a(var3x.f(), 0.0, var36 + var33, var25 + var30 + var39, var25 + 128.0F);
                           var4.a(var3x.f(), 0.0, var36, var25 + var39, var25 + 128.0F);
                           var36++;
                        }
                     }

                     var26 = Math.max((double)MathHelper.floor_double(var15 - var5), var3x.f());
                     var28 = Math.min((double)MathHelper.ceiling_double_int(var15 + var5), var3x.d());
                     if (var19 > var3x.e() - var5) {
                        float var40 = 0.0F;

                        for (double var37 = var26; var37 < var28; var40 += 0.5F) {
                           double var34 = Math.min(1.0, var28 - var37);
                           float var31 = (float)var34 * 0.5F;
                           var4.a(var37, 256.0, var3x.e(), var25 + var40, var25 + 0.0F);
                           var4.a(var37 + var34, 256.0, var3x.e(), var25 + var31 + var40, var25 + 0.0F);
                           var4.a(var37 + var34, 0.0, var3x.e(), var25 + var31 + var40, var25 + 128.0F);
                           var4.a(var37, 0.0, var3x.e(), var25 + var40, var25 + 128.0F);
                           var37++;
                        }
                     }

                     if (var19 < var3x.g() + var5) {
                        float var41 = 0.0F;

                        for (double var38 = var26; var38 < var28; var41 += 0.5F) {
                           double var35 = Math.min(1.0, var28 - var38);
                           float var32 = (float)var35 * 0.5F;
                           var4.a(var38, 256.0, var3x.g(), var25 + var41, var25 + 0.0F);
                           var4.a(var38 + var35, 256.0, var3x.g(), var25 + var32 + var41, var25 + 0.0F);
                           var4.a(var38 + var35, 0.0, var3x.g(), var25 + var32 + var41, var25 + 128.0F);
                           var4.a(var38, 0.0, var3x.g(), var25 + var41, var25 + 128.0F);
                           var38++;
                        }
                     }

                     var4.b();
                     GL11.glTranslated(0.0, 0.0, 0.0);
                     cn.kazer.client189.render.GlStateBridge.a(2884);
                     cn.kazer.client189.render.GlStateBridge.b(3008);
                     GL11.glPolygonOffset(0.0F, 0.0F);
                     cn.kazer.client189.render.GlStateBridge.b(32823);
                     cn.kazer.client189.render.GlStateBridge.a(3008);
                     cn.kazer.client189.render.GlStateBridge.b(3042);
                     GL11.glPopMatrix();
                     cn.kazer.client189.render.GlStateBridge.a(true);
                  }
               }
            );
      }
   }

   public void a(String var1, String var2, int var3, double var4, double var6, double var8, double var10, boolean var12, boolean var13) {
      this.borderList.add(new com.cheatbreaker.client.util.colorutil.WorldBorder(this, var1, var2, var3, var4, var6, var8, var10, var12, var13));
   }

   public void a(String var1, double var2, double var4, double var6, double var8, int var10) {
      this.borderList
         .stream()
         .filter(var1x -> Objects.equals(com.cheatbreaker.client.util.colorutil.WorldBorder.a(var1x), var1) && com.cheatbreaker.client.util.colorutil.WorldBorder.b(var1x))
         .findFirst()
         .ifPresent(var9 -> {
            com.cheatbreaker.client.util.colorutil.WorldBorder.a(var9, new Vector2d(var2, var4));
            com.cheatbreaker.client.util.colorutil.WorldBorder.b(var9, new Vector2d(var6, var8));
            com.cheatbreaker.client.util.colorutil.WorldBorder.a(var9, 0);
            com.cheatbreaker.client.util.colorutil.WorldBorder.b(var9, var10);
         });
   }

   public void a(String var1) {
      this.borderList.removeIf(var1x -> Objects.equals(com.cheatbreaker.client.util.colorutil.WorldBorder.a(var1x), var1));
   }

   public void a() {
      this.borderList.clear();
   }

   public List<com.cheatbreaker.client.util.colorutil.WorldBorder> b() {
      return this.borderList;
   }

   static com.cheatbreaker.client.CheatBreakerClient a(com.cheatbreaker.client.util.colorutil.WorldBorderManager var0) {
      return var0.cheatbreaker;
   }
}
