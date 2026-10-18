package com.cheatbreaker.client.kazerui.module.modules.visual;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.BlockPos.MutableBlockPos;
import net.minecraft.world.EnumSkyBlock;
import org.lwjgl.opengl.GL11;

public final class LightOverlayModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static com.cheatbreaker.client.kazerui.module.modules.visual.LightOverlayModule instance;
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting radius = this.a("renderRadius", 8, 1, 16);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting threshold = this.a("lightThreshold", 7, 0, 15);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting includeSky = this.a("includeSkyLight", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting darkOnly = this.a("onlyDarkMarkers", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting bright = this.a("brightColor", -11141291).b().c();
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting dark = this.a("darkColor", -43691).b().c();
   private final com.cheatbreaker.client.kazerui.module.modules.visual.LightOverlayCache cache = new com.cheatbreaker.client.kazerui.module.modules.visual.LightOverlayCache();
   private final MutableBlockPos position = new MutableBlockPos();
   private final com.cheatbreaker.client.kazerui.module.modules.visual.LightOverlayCache.a reader = this::a;

   public LightOverlayModule() {
      super("LIGHT_OVERLAY", false);
      instance = this;
      this.n();
   }

   private void n() {
      for (com.cheatbreaker.client.kazerui.module.settings.AbstractSetting var2 : this.g()) {
         String var3 = var2.key;
         switch (var3) {
            case "renderRadius":
               var2.a(() -> "Marker Radius (Blocks)");
               break;
            case "lightThreshold":
               var2.a(() -> "Dark Light Threshold");
               break;
            case "includeSkyLight":
               var2.a(() -> "Include Sky Light");
               break;
            case "onlyDarkMarkers":
               var2.a(() -> "Show Only Dark Markers");
               break;
            case "brightColor":
               var2.a(() -> "Bright Marker Color");
               break;
            case "darkColor":
               var2.a(() -> "Dark Marker Color");
         }
      }
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(this.radius, this.includeSky, this.threshold, this.darkOnly, this.bright, this.dark);
   }

   @Override
   protected void f() {
      this.cache.a();
   }

   @Override
   public void a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a var1) {
      if (var1.phase == com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b.END) {
         if (this.a() && m().theWorld != null && m().thePlayer != null) {
            EntityPlayerSP var2 = m().thePlayer;
            this.cache
               .a(
                  m().theWorld,
                  MathHelper.floor_double(var2.posX),
                  MathHelper.floor_double(var2.posY),
                  MathHelper.floor_double(var2.posZ),
                  this.radius.c(),
                  this.includeSky.b(),
                  this.reader
               );
         } else {
            this.cache.a();
         }
      }
   }

   private int a(int var1, int var2, int var3, boolean var4) {
      this.position.set(var1, var2, var3);
      if (!m().theWorld.isBlockLoaded(this.position)) {
         return -1;
      } else {
         this.position.set(var1, var2 - 1, var3);
         if (!m().theWorld.getBlockState(this.position).getBlock().isOpaqueCube()) {
            return -1;
         } else {
            this.position.set(var1, var2, var3);
            if (!m().theWorld.isAirBlock(this.position)) {
               return -1;
            } else {
               this.position.set(var1, var2 + 1, var3);
               if (!m().theWorld.isAirBlock(this.position)) {
                  return -1;
               } else {
                  this.position.set(var1, var2, var3);
                  int var5 = m().theWorld.getLightFor(EnumSkyBlock.BLOCK, this.position);
                  return var4 ? Math.max(var5, m().theWorld.getLightFor(EnumSkyBlock.SKY, this.position) - m().theWorld.getSkylightSubtracted()) : var5;
               }
            }
         }
      }
   }

   public static void a(float var0) {
      com.cheatbreaker.client.kazerui.module.modules.visual.LightOverlayModule var1 = instance;
      if (var1 != null && var1.a() && var1.cache.b() != 0 && m().theWorld != null && m().thePlayer != null) {
         Entity var2 = m().getRenderViewEntity();
         if (var2 != null) {
            double var3 = var2.lastTickPosX + (var2.posX - var2.lastTickPosX) * var0;
            double var5 = var2.lastTickPosY + (var2.posY - var2.lastTickPosY) * var0;
            double var7 = var2.lastTickPosZ + (var2.posZ - var2.lastTickPosZ) * var0;
            cn.kazer.client189.render.GlStateBridge.c(1048575);
            GL11.glPushMatrix();

            try {
               GL11.glDisable(3553);
               GL11.glDisable(2896);
               GL11.glDisable(3042);
               GL11.glDisable(2912);
               GL11.glDepthMask(false);
               GL11.glLineWidth(1.0F);
               GL11.glTranslated(-var3, -var5, -var7);
               WorldRenderer var9 = Tessellator.getInstance().getWorldRenderer();
               var9.begin(1, DefaultVertexFormats.POSITION_COLOR);
               int var10 = var1.threshold.c();
               int var11 = var1.bright.e();
               int var12 = var1.dark.e();
               boolean var13 = var1.darkOnly.b();

               for (int var14 = 0; var14 < var1.cache.b(); var14++) {
                  boolean var15 = var1.cache.d(var14) <= var10;
                  if (var15 || !var13) {
                     double var16 = var1.cache.a(var14);
                     double var18 = var1.cache.b(var14) + 0.015;
                     double var20 = var1.cache.c(var14);
                     int var22 = var15 ? var12 : var11;
                     int var23 = var22 >>> 16 & 0xFF;
                     int var24 = var22 >>> 8 & 0xFF;
                     int var25 = var22 & 0xFF;
                     var9.pos(var16 + 0.2, var18, var20 + 0.2).color(var23, var24, var25, 255).endVertex();
                     var9.pos(var16 + 0.8, var18, var20 + 0.8).color(var23, var24, var25, 255).endVertex();
                     var9.pos(var16 + 0.8, var18, var20 + 0.2).color(var23, var24, var25, 255).endVertex();
                     var9.pos(var16 + 0.2, var18, var20 + 0.8).color(var23, var24, var25, 255).endVertex();
                  }
               }

               Tessellator.getInstance().draw();
            } finally {
               GL11.glPopMatrix();
               cn.kazer.client189.render.GlStateBridge.a();
            }
         }
      }
   }
}
