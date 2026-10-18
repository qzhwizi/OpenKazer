package com.cheatbreaker.client.kazerui.module.modules.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public final class CompassModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static final ResourceLocation LEGACY_TEXTURE = new ResourceLocation("kazerui", "ui/icons/compass.png");
   private static final ResourceLocation MODERN_TEXTURE = new ResourceLocation("kazerui", "ui/icons/compass-modern.png");
   private static final ResourceLocation ROUND = new ResourceLocation("kazerui", "ui/icons/round-compass.png");
   private static final ResourceLocation POINTER = new ResourceLocation("kazerui", "ui/icons/round-compass-pointer.png");
   private static final String[] DIRECTIONS = new String[]{"S", "SW", "W", "NW", "N", "NE", "E", "SE"};
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting borderThickness = this.a("borderThickness", 0.5F, 0.5F, 3.0F);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting background = this.a("background", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting border = this.a("border", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showWithTab = this.a("showWithTab", false);
   private final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.c> hudStyle = this.a(
      "hudStyle", com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.c.NORMAL
   );
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting width = this.a("width", 280.0F, 168.0F, 448.0F);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting textShadow = this.a("textShadow", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting boldDirections = this.a("boldDirections", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showMarker = this.a("showMarker", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showMarkerValue = this.a("showMarkerValue", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting borderColor = this.a("borderColor", -1627389952);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting backgroundColor = this.a("backgroundColor", 1862270976);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting directionColor = this.a("directionColor", -1);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting markerColor = this.a("markerColor", -1);
   private final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.b> teammates = this.a(
      "teammates", com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.b.ABOVE
   );
   private final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.b> waypoints = this.a(
      "waypoints", com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.b.ABOVE
   );
   private final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.b> externalMarkers = this.a(
      "externalMarkers", com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.b.BELOW
   );
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showTeammates = this.a("showTeammates", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showWaypoints = this.a("showWaypoints", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showExternalMarkers = this.a("showExternalMarkers", true);

   public CompassModule() {
      super("DIRECTION_HUD", true);
      this.a(new com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.a());
   }

   private boolean a(com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.c var1) {
      return this.hudStyle.a(var1);
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(
         "generalOptions",
         var1x -> {
            var1x.a(this.width).a(() -> !this.a(com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.c.NORMAL));
            var1x.a(this.textShadow)
               .a(
                  () -> this.a(com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.c.LEGACY)
                     || this.a(com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.c.COMPASS)
               );
            var1x.a(this.boldDirections).a(() -> !this.a(com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.c.NORMAL));
            var1x.a(this.background, var1xx -> var1xx.a(this.border, this.borderThickness))
               .a(() -> this.a(com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.c.LEGACY));
         }
      );
      var1.a(
         "renderOptions",
         var1x -> {
            var1x.a(this.hudStyle, this.showWithTab);
            var1x.a(this.showMarker)
               .a(
                  () -> this.a(com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.c.SIMPLE)
                     || this.a(com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.c.COMPASS)
               );
            var1x.a(this.showMarkerValue, this.waypoints, this.teammates, this.externalMarkers)
               .a(() -> !this.a(com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.c.NORMAL));
            var1x.a(this.showWaypoints, this.showTeammates, this.showExternalMarkers)
               .a(() -> !this.a(com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.c.COMPASS));
         }
      );
      var1.a(
         "colorOptions",
         var1x -> {
            var1x.a(this.backgroundColor, this.borderColor).a(() -> this.a(com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.c.LEGACY));
            var1x.a(this.markerColor)
               .a(
                  () -> !this.a(com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.c.LEGACY)
                     && !this.a(com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.c.NORMAL)
               );
            var1x.a(this.directionColor)
               .a(
                  () -> !this.a(com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.c.LEGACY)
                     && !this.a(com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.c.SIMPLE)
               );
         }
      );
   }

   private static boolean x() {
      Minecraft var0 = Minecraft.getMinecraft();
      if (var0.thePlayer != null && var0.gameSettings.keyBindPlayerList.isKeyDown()) {
         boolean var1 = var0.thePlayer.sendQueue.getPlayerInfoMap().size() > 1
            || var0.theWorld != null && var0.theWorld.getScoreboard().getObjectiveInDisplaySlot(0) != null;
         return !var0.isIntegratedServerRunning() || var1;
      } else {
         return false;
      }
   }

   private final class a extends com.cheatbreaker.client.kazerui.module.modulerender.HudElement {
      private int shownHeading = -1;
      private String headingText;

      a() {
         super(CompassModule.this, 0.0F, 0.0F, com.cheatbreaker.client.kazerui.hud.HudAnchor.TOP_CENTER);
      }

      @Override
      public boolean a(boolean var1) {
         switch ((com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.c)CompassModule.this.hudStyle.j()) {
            case LEGACY:
               this.a(65.0F, 12.0F);
               break;
            case SIMPLE:
               this.a(24.0F, 24.0F);
               break;
            case COMPASS:
               this.a(128.0F, 128.0F);
               break;
            default:
               this.a(CompassModule.this.width.b(), 26.0F - this.m());
         }

         return !var1 && !CompassModule.this.showWithTab.b() ? !com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.x() : true;
      }

      @Override
      public void b(boolean var1) {
         double var2 = 180.0;
         if (!var1) {
            Entity var4 = com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.m().getRenderViewEntity();
            if (var4 != null) {
               var2 = var4.rotationYaw;
            } else if (com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.m().thePlayer != null) {
               var2 = com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.m().thePlayer.rotationYaw;
            }
         }

         com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.c var8 = CompassModule.this.hudStyle.j();
         if (var8 != com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.c.LEGACY && CompassModule.this.background.b()) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(CompassModule.this.backgroundColor, 0.0F, 0.0F, this.f(), this.g());
            if (CompassModule.this.border.b()) {
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(CompassModule.this.borderColor, 0.0F, 0.0F, this.f(), this.g(), CompassModule.this.borderThickness.b());
            }
         }

         GlStateManager.enableBlend();
         switch (var8) {
            case LEGACY:
               this.a(var2);
               break;
            case SIMPLE:
               this.b(var2);
               break;
            case COMPASS:
               this.c(var2);
               break;
            default:
               GlStateManager.pushMatrix();

               try {
                  GlStateManager.translate(0.0F, -this.m(), 0.0F);
                  this.d(var2);
               } finally {
                  GlStateManager.popMatrix();
               }
         }

         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      }

      private void a(double var1) {
         float var3 = MathHelper.floor_float((float)(var1 * 256.0 / 360.0 + 0.5)) & 0xFF;
         int var4 = CompassModule.this.directionColor.a(0.0F) & 16777215 | 0xFF000000;
         if (var3 < 128.0F) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(
               com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.LEGACY_TEXTURE, 0.0F, 0.0F, (int)var3, 0.0F, 65.0F, 12.0F, 256.0F, 256.0F, -1
            );
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(
               com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.LEGACY_TEXTURE, 0.0F, 0.0F, (int)var3, 24.0F, 65.0F, 12.0F, 256.0F, 256.0F, var4
            );
         } else {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(
               com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.LEGACY_TEXTURE, 0.0F, 0.0F, (int)(var3 - 128.0F), 12.0F, 65.0F, 12.0F, 256.0F, 256.0F, -1
            );
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(
               com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.LEGACY_TEXTURE, 0.0F, 0.0F, (int)(var3 - 128.0F), 36.0F, 65.0F, 12.0F, 256.0F, 256.0F, var4
            );
         }

         if (CompassModule.this.showMarker.b()) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(CompassModule.this.markerColor, "|", 32.0F, 1.0F, false);
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(CompassModule.this.markerColor, "|", 32.0F, 5.0F, false);
         }
      }

      private void b(double var1) {
         String var3 = com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.DIRECTIONS[(int)Math.floor(var1 * 4.0 / 180.0 + 0.5) & 7];
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(
            CompassModule.this.directionColor,
            var3,
            this.f() / 2.0F - com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var3) / 2.0F,
            this.g() / 2.0F - com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.b() / 2.0F + 1.0F,
            CompassModule.this.textShadow.b()
         );
      }

      private void c(double var1) {
         float var3 = this.f();
         float var4 = this.g();
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(
            com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.ROUND, 0.0F, 0.0F, 0.0F, 0.0F, var3, var4, var3, var4, -1
         );
         GlStateManager.pushMatrix();
         GlStateManager.translate(var3 / 2.0F, var4 / 2.0F, 0.0F);
         GlStateManager.rotate((float)var1, 0.0F, 0.0F, 1.0F);
         GlStateManager.translate(-var3 / 2.0F, -var4 / 2.0F, 0.0F);
         com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(
            com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.POINTER, 0.0F, 0.0F, 0.0F, 0.0F, var3, var4, var3, var4, -1
         );
         GlStateManager.popMatrix();
      }

      private void d(double var1) {
         float var3 = (float)var1 - MathHelper.floor_float((float)var1 / 360.0F) * 360.0F;
         if (var3 == 360.0F) {
            var3 = 0.0F;
         }

         float var4 = CompassModule.this.width.b();
         if (CompassModule.this.showMarkerValue.b()) {
            if (this.shownHeading != (int)var3) {
               this.shownHeading = (int)var3;
               this.headingText = Integer.toString(this.shownHeading);
            }

            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.b(
               CompassModule.this.markerColor, this.headingText, var4 / 2.0F, -com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.b() - 7, CompassModule.this.textShadow.b()
            );
         }

         int var5 = CompassModule.this.boldDirections.b() ? 23 : 69;
         if (CompassModule.this.textShadow.b()) {
            var5 -= 23;
         }

         if (CompassModule.this.showMarker.b()) {
            float var6 = var4 / 2.0F;
            float var7 = 6.0F;
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(
               var6, -6.0F + var7 / 1.5F, var6 + var7 / 2.0F, -6.0F, var6 - var7 / 2.0F, -6.0F, CompassModule.this.markerColor.a(0.0F)
            );
         }

         float var9 = var4 * 0.25F;
         float var10 = var4 - var9;
         float var8 = (var3 - 0.0F) / 360.0F * 748.0F + 218.0F - (var10 - var9) / 2.0F - var9 + 1.0F;
         this.a(0.0F, 3.0F, var8, var5, var9, 23.0F, 16777215, -1, -1, 16777215);
         this.a(var9 + var10 - var9, 3.0F, var8 + var10, var5, var9, 23.0F, -1, 16777215, 16777215, -1);
         this.a(var9, 3.0F, var8 + var9, var5, var10 - var9, 23.0F, -1, -1, -1, -1);
      }

      private void a(float var1, float var2, float var3, float var4, float var5, float var6, int var7, int var8, int var9, int var10) {
         com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.m()
            .getTextureManager()
            .bindTexture(com.cheatbreaker.client.kazerui.module.modules.hud.CompassModule.MODERN_TEXTURE);
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.shadeModel(7425);
         float var11 = 8.4317033E-4F;
         float var12 = 0.008196721F;
         WorldRenderer var13 = Tessellator.getInstance().getWorldRenderer();
         var13.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
         this.a(var13, var1, var2 + var6, var3 * var11, (var4 + var6) * var12, var7);
         this.a(var13, var1 + var5, var2 + var6, (var3 + var5) * var11, (var4 + var6) * var12, var8);
         this.a(var13, var1 + var5, var2, (var3 + var5) * var11, var4 * var12, var9);
         this.a(var13, var1, var2, var3 * var11, var4 * var12, var10);
         Tessellator.getInstance().draw();
         GlStateManager.shadeModel(7424);
      }

      private float m() {
         return CompassModule.this.showMarkerValue.b() ? -com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.b() - 7 : (CompassModule.this.showMarker.b() ? -6.0F : 3.0F);
      }

      private void a(WorldRenderer var1, float var2, float var3, float var4, float var5, int var6) {
         var1.pos(var2, var3, 0.0).tex(var4, var5).color(var6 >> 16 & 0xFF, var6 >> 8 & 0xFF, var6 & 0xFF, var6 >>> 24).endVertex();
      }
   }

   public static enum b implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
      ABOVE,
      BELOW,
      NONE;

      @Override
      public String a() {
         return this.name();
      }
   }

   public static enum c implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
      NORMAL("normal"),
      LEGACY("legacy"),
      SIMPLE("simple"),
      COMPASS("realCompass");

      private final String id;

      private c(String var3) {
         this.id = var3;
      }

      @Override
      public String a() {
         return this.id;
      }
   }
}
