package com.cheatbreaker.client.kazerui.module.modules.hud;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;

public final class CoordinatesHudModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static final String[] DIRECTIONS = new String[]{"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showWhileTyping = this.a("showWhileTyping", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting textShadow = this.a("textShadow", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting background = this.a("background", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting border = this.a("border", false);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting borderThickness = this.a("borderThickness", 0.5F, 0.5F, 3.0F);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting backgroundColor = this.a("backgroundColor", 1862270976);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting borderColor = this.a("borderColor", -1627389952);
   private final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.c> mode = this.a(
      "mode", com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.c.VERTICAL
   );
   private final com.cheatbreaker.client.kazerui.module.settings.KeybindSetting copyCoords = this.b("copyCoords");
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting moveChildrenIndividually = this.a("moveChildrenIndividually", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting decimalCoordinates = this.a("decimalCoordinates", false);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting decimalPlaces = this.a("decimalPlaces", 2, 0, 10);
   private final com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule x;
   private final com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule y;
   private final com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule z;
   private final com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule c;
   private final com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule direction;
   private final com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule biome;
   private boolean copyHeld;
   private String biomeName = "Plains";
   private int biomeColor = -10510539;
   private static final Map<Integer, com.cheatbreaker.client.kazerui.module.modules.hud.CoordinatesHudModule.a> BIOMES = new HashMap<>();

   public CoordinatesHudModule() {
      super("COORDINATES", true);
      this.a(new com.cheatbreaker.client.kazerui.module.modules.hud.CoordinatesHudModule.b());
      this.x = this.a(
         new com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule(
            this, com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule.b.X, "X", () -> this.a(q().posX), "500"
         ),
         "generalOptions"
      );
      this.y = this.a(
         new com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule(
            this, com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule.b.Y, "Y", () -> this.a(q().getEntityBoundingBox().minY), "62"
         ),
         "generalOptions"
      );
      this.z = this.a(
         new com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule(
            this, com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule.b.Z, "Z", () -> this.a(q().posZ), "250"
         ),
         "generalOptions"
      );
      this.c = this.a(
         new com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule(
            this, com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule.b.C, "C", () -> r() + "/" + s(), "92/4269"
         ),
         "generalOptions"
      );
      this.direction = this.a(
         new com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule(
            this, com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule.b.DIRECTION, "Direction", () -> a(q().rotationYaw), "N"
         ),
         "generalOptions"
      );
      this.biome = this.a(
         new com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule(
            this, com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule.b.BIOME, "Biome", () -> this.biomeName, "Plains"
         ),
         "generalOptions"
      );
   }

   private static EntityPlayerSP q() {
      return Minecraft.getMinecraft().thePlayer;
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a("generalOptions", var1x -> {
         var1x.a(this.mode, this.textShadow, this.showWhileTyping).a(this.moveChildrenIndividually::b);
         var1x.a(this.background, var1xx -> var1xx.a(this.border, var1xxx -> var1xxx.a(this.borderThickness))).a(this.moveChildrenIndividually::b);
         var1x.a(this.copyCoords).a(this.moveChildrenIndividually::b);
         var1x.a(this.decimalCoordinates, var1xx -> var1xx.a(this.decimalPlaces));
         var1x.a(this.moveChildrenIndividually);
      });
      var1.a("colorOptions", var1x -> var1x.a(this.backgroundColor, this.borderColor).a(this.moveChildrenIndividually::b));
   }

   private String a(double var1) {
      return this.decimalCoordinates.b()
         ? String.format(Locale.ROOT, "%." + this.decimalPlaces.c() + "f", var1)
         : String.valueOf(MathHelper.floor_double(var1));
   }

   static String a(float var0) {
      double var1 = MathHelper.wrapAngleTo180_float(var0) + 180.0;
      var1 += 22.5;
      var1 %= 360.0;
      return DIRECTIONS[MathHelper.floor_double(var1 / 45.0)];
   }

   private static int r() {
      return com.cheatbreaker.client.kazerui.module.modules.hud.ChunkRenderStats.a();
   }

   private static int s() {
      return com.cheatbreaker.client.kazerui.module.modules.hud.ChunkRenderStats.b();
   }

   int n() {
      return this.biomeColor;
   }

   @Override
   public void a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a var1) {
      if (var1.phase == com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b.END) {
         if (this.a()) {
            com.cheatbreaker.client.kazerui.module.modules.hud.ChunkRenderStats.c();
            Minecraft var2 = Minecraft.getMinecraft();
            EntityPlayerSP var3 = var2.thePlayer;
            if (var3 != null && var2.theWorld != null) {
               BiomeGenBase var4 = var2.theWorld.getBiomeGenForCoords(new BlockPos(var3));
               com.cheatbreaker.client.kazerui.module.modules.hud.CoordinatesHudModule.a var5 = var4 == null ? null : BIOMES.get(var4.biomeID);
               if (var5 != null) {
                  this.biomeName = var5.name;
                  this.biomeColor = var5.color;
               } else if (var4 != null) {
                  this.biomeName = var4.biomeName;
                  this.biomeColor = -1;
               }
            }

            boolean var6 = this.a() && var2.currentScreen == null && this.copyCoords.b();
            if (var6 && !this.copyHeld && var2.theWorld != null && var3 != null) {
               GuiScreen.setClipboardString(String.format("X: %s Y: %s Z: %s", (int)var3.posX, (int)var3.posY, (int)var3.posZ));
               com.cheatbreaker.client.kazerui.gui.ToastNotifications.a("Copied coordinates to clipboard!");
            }

            this.copyHeld = var6;
            if (var3 != null) {
               this.x.o();
               this.y.o();
               this.z.o();
               this.c.o();
               this.direction.o();
               this.biome.o();
            }
         }
      }
   }

   private static void a(int var0, String var1, int var2) {
      BIOMES.put(var0, new com.cheatbreaker.client.kazerui.module.modules.hud.CoordinatesHudModule.a(var1, var2));
   }

   static {
      a(0, "ocean", -16752657);
      a(1, "plains", -10510539);
      a(2, "desert", -1516367);
      a(3, "mountains", -7368817);
      a(4, "forest", -11629531);
      a(5, "taiga", -11637939);
      a(6, "swamp", -12295900);
      a(7, "river", -16752657);
      a(8, "nether_wastes", -7729393);
      a(9, "the_end", -9568091);
      a(10, "frozen_ocean", -6308109);
      a(11, "frozen_river", -6308109);
      a(12, "snowy_tundra", -6308109);
      a(13, "snowy_mountains", -6308109);
      a(14, "mushroom_fields", -9080700);
      a(15, "mushroom_field_shore", -9080700);
      a(16, "beach", -1516367);
      a(17, "desert_hills", -1516367);
      a(18, "wooded_hills", -11629531);
      a(19, "taiga_hills", -11637939);
      a(20, "mountain_edge", -7368817);
      a(21, "jungle", -14913786);
      a(22, "jungle_hills", -14913786);
      a(23, "jungle_edge", -14913786);
      a(24, "deep_ocean", -16752657);
      a(25, "stone_shore", -7368817);
      a(26, "snowy_beach", -6308109);
      a(27, "birch_forest", -3223858);
      a(28, "birch_forest_hills", -3223858);
      a(29, "dark_forest", -13145823);
      a(30, "snowy_taiga", -6308109);
      a(31, "snowy_taiga_hills", -6308109);
      a(32, "giant_tree_taiga", -11637939);
      a(33, "giant_tree_taiga_hills", -11637939);
      a(34, "wooded_mountains", -7368817);
      a(35, "savanna", -8225734);
      a(36, "savanna_plateau", -8225734);
      a(37, "badlands", -5022683);
      a(38, "wooded_badlands_plateau", -5022683);
      a(39, "badlands_plateau", -5022683);
      a(127, "the_void", -9568091);
      a(129, "sunflower_plains", -10240);
      a(130, "desert_lakes", -1516367);
      a(131, "gravelly_mountains", -7368817);
      a(132, "flower_forest", -32787);
      a(133, "taiga_mountains", -11637939);
      a(134, "swamp_hills", -12295900);
      a(140, "ice_spikes", -6308109);
      a(149, "modified_jungle", -14913786);
      a(151, "modified_jungle_edge", -14913786);
      a(155, "tall_birch_forest", -3223858);
      a(156, "tall_birch_hills", -3223858);
      a(157, "dark_forest_hills", -13145823);
      a(158, "snowy_taiga_mountains", -6308109);
      a(160, "giant_spruce_taiga", -11637939);
      a(161, "giant_spruce_taiga_hills", -11637939);
      a(162, "modified_gravelly_mountains", -7368817);
      a(163, "shattered_savanna", -8225734);
      a(164, "shattered_savanna_plateau", -8225734);
      a(165, "eroded_badlands", -5022683);
      a(166, "modified_wooded_badlands_plateau", -5022683);
      a(167, "modified_badlands_plateau", -5022683);
   }

   private static final class a {
      final String name;
      final int color;

      a(String var1, int var2) {
         StringBuilder var3 = new StringBuilder();

         for (String var7 : var1.split("_")) {
            var3.append(var3.length() == 0 ? "" : " ").append(Character.toUpperCase(var7.charAt(0))).append(var7.substring(1));
         }

         this.name = var3.toString();
         this.color = var2;
      }
   }

   private final class b extends com.cheatbreaker.client.kazerui.module.modulerender.HudElement {
      b() {
         super(CoordinatesHudModule.this, 0.0F, 0.0F, com.cheatbreaker.client.kazerui.hud.HudAnchor.TOP_LEFT);
      }

      @Override
      public boolean l() {
         return !CoordinatesHudModule.this.moveChildrenIndividually.b();
      }

      @Override
      public boolean a(boolean var1) {
         if (CoordinatesHudModule.this.moveChildrenIndividually.b()) {
            return false;
         } else if (!CoordinatesHudModule.this.showWhileTyping.b() && Minecraft.getMinecraft().ingameGUI.getChatGUI().getChatOpen()) {
            return false;
         } else if (!var1 && com.cheatbreaker.client.kazerui.module.modules.hud.CoordinatesHudModule.q() == null) {
            return false;
         } else if (!CoordinatesHudModule.this.c.a() && !CoordinatesHudModule.this.x.a() && !CoordinatesHudModule.this.y.a() && !CoordinatesHudModule.this.z.a() && !CoordinatesHudModule.this.biome.a() && !CoordinatesHudModule.this.direction.a()) {
            this.a(0.0F, 0.0F);
            return false;
         } else {
            this.a(var1, false);
            return this.f() > 0.0F;
         }
      }

      @Override
      public void b(boolean var1) {
         this.a(var1, true);
      }

      private float a(com.cheatbreaker.client.kazerui.module.settings.ColorSetting var1, String var2, float var3, float var4, boolean var5, boolean var6) {
         return var6 ? com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var1, var2, var3, var4, var5) : var3 + com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var2);
      }

      private void a(boolean var1, boolean var2) {
         float var3;
         if (var1) {
            var3 = 180.0F;
         } else {
            Entity var4 = com.cheatbreaker.client.kazerui.module.modules.hud.CoordinatesHudModule.m().getRenderViewEntity();
            var3 = var4 != null ? var4.rotationYaw : com.cheatbreaker.client.kazerui.module.modules.hud.CoordinatesHudModule.q().rotationYaw;
         }

         boolean var28 = CoordinatesHudModule.this.x.a();
         boolean var5 = CoordinatesHudModule.this.y.a();
         boolean var6 = CoordinatesHudModule.this.z.a();
         boolean var7 = CoordinatesHudModule.this.biome.a();
         boolean var8 = CoordinatesHudModule.this.direction.a();
         boolean var9 = CoordinatesHudModule.this.direction.directionAffect.b();
         boolean var10 = CoordinatesHudModule.this.c.a();
         boolean var11 = CoordinatesHudModule.this.background.b();
         boolean var12 = CoordinatesHudModule.this.textShadow.b();
         int var13 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.b();
         if (var2 && var11) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(CoordinatesHudModule.this.backgroundColor, 0.0F, 0.0F, this.f(), this.g());
            if (CoordinatesHudModule.this.border.b()) {
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(CoordinatesHudModule.this.borderColor, 0.0F, 0.0F, this.f(), this.g(), CoordinatesHudModule.this.borderThickness.b());
            }
         }

         String var14 = com.cheatbreaker.client.kazerui.module.modules.hud.CoordinatesHudModule.a(var3);
         float var16 = 0.0F;
         float var15;
         if (CoordinatesHudModule.this.mode.a(com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.c.HORIZONTAL)) {
            com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule var17 = var28 ? CoordinatesHudModule.this.x : (var5 ? CoordinatesHudModule.this.y : (var6 ? CoordinatesHudModule.this.z : (var10 ? CoordinatesHudModule.this.c : null)));
            float var18 = var11 ? 3.0F : 0.0F;
            var15 = 5.0F;
            if (!var11 && var17 != null) {
               var15 = this.a(var17.labelColor, "(", var15, var18, var12, var2);
            }

            if (var28) {
               if (CoordinatesHudModule.this.x.showLabel.b()) {
                  var15 = this.a(CoordinatesHudModule.this.x.labelColor, "X: ", var15, var18, var12, var2);
               }

               var15 = this.a(CoordinatesHudModule.this.x.n(), CoordinatesHudModule.this.x.b(var1), var15, var18, var12, var2);
               com.cheatbreaker.client.kazerui.module.settings.ColorSetting var19;
               if (var8 && var9 && (var14.contains("W") || var14.contains("E"))) {
                  var15 = this.a(CoordinatesHudModule.this.direction.directionAffectXColor, var14.contains("W") ? " (-)" : " (+)", var15, var18, var12, var2);
                  var19 = CoordinatesHudModule.this.direction.directionAffectXColor;
               } else {
                  var19 = CoordinatesHudModule.this.x.n();
               }

               if (var5 || var6 || var10) {
                  var15 = this.a(var19, ", ", var15, var18, var12, var2);
               }
            }

            if (var5) {
               if (CoordinatesHudModule.this.y.showLabel.b()) {
                  var15 = this.a(CoordinatesHudModule.this.y.labelColor, "Y: ", var15, var18, var12, var2);
               }

               var15 = this.a(CoordinatesHudModule.this.y.n(), CoordinatesHudModule.this.y.b(var1), var15, var18, var12, var2);
               if (var6 || var10) {
                  var15 = this.a(CoordinatesHudModule.this.y.n(), ", ", var15, var18, var12, var2);
               }
            }

            if (var6) {
               if (CoordinatesHudModule.this.z.showLabel.b()) {
                  var15 = this.a(CoordinatesHudModule.this.z.labelColor, "Z: ", var15, var18, var12, var2);
               }

               var15 = this.a(CoordinatesHudModule.this.z.n(), CoordinatesHudModule.this.z.b(var1), var15, var18, var12, var2);
               com.cheatbreaker.client.kazerui.module.settings.ColorSetting var33;
               if (var8 && var9 && (var14.contains("N") || var14.contains("S"))) {
                  var15 = this.a(CoordinatesHudModule.this.direction.directionAffectZColor, var14.contains("N") ? " (-)" : " (+)", var15, var18, var12, var2);
                  var33 = CoordinatesHudModule.this.direction.directionAffectZColor;
               } else {
                  var33 = CoordinatesHudModule.this.z.n();
               }

               if (var10) {
                  var15 = this.a(var33, ", ", var15, var18, var12, var2);
               }
            }

            if (var10) {
               if (CoordinatesHudModule.this.c.showLabel.b()) {
                  var15 = this.a(CoordinatesHudModule.this.c.labelColor, "C: ", var15, var18, var12, var2);
               }

               var15 = this.a(CoordinatesHudModule.this.c.n(), CoordinatesHudModule.this.c.b(var1), var15, var18, var12, var2);
            }

            if (!var11) {
               if (var17 != null) {
                  var15 = this.a(var17.labelColor, ")", var15, var18, var12, var2);
               }
            } else {
               var16 += 4.0F;
            }

            if (var8 && CoordinatesHudModule.this.direction.cardinalDirection.b()) {
               String var20 = !var28 && !var5 && !var6 && !var10 && !var7 ? var14 : " " + var14;
               var15 = this.a(CoordinatesHudModule.this.direction.n(), var20, var15, var18, var12, var2);
            }

            var16 += var13;
         } else {
            var15 = 0.0F;
            var16 = 5.0F;
            float var31 = 0.0F;
            float var32 = 0.0F;
            float var34 = 0.0F;
            float var35 = 0.0F;
            float var21 = 0.0F;
            boolean var23 = var8 && var9;
            if (var28) {
               var15 = Math.max(var15, this.a(CoordinatesHudModule.this.x, CoordinatesHudModule.this.x.b(var1), var16, var12, var2));
               var31 = var16;
               var16 += var13 + 2;
            }

            if (var5) {
               var15 = Math.max(var15, this.a(CoordinatesHudModule.this.y, CoordinatesHudModule.this.y.b(var1), var16, var12, var2));
               var32 = var16;
               var16 += var13 + 2;
            }

            if (var6) {
               var15 = Math.max(var15, this.a(CoordinatesHudModule.this.z, CoordinatesHudModule.this.z.b(var1), var16, var12, var2));
               var34 = var16;
               var16 += var13 + 2;
            }

            if (var10) {
               var15 = Math.max(var15, this.a(CoordinatesHudModule.this.c, CoordinatesHudModule.this.c.b(var1), var16, var12, var2));
               var35 = var16;
               var16 += var13 + 2;
            }

            if (var7) {
               float var24 = CoordinatesHudModule.this.biome.showLabel.b()
                  ? this.a(CoordinatesHudModule.this.biome.labelColor, CoordinatesHudModule.this.a("biome", new Object[0]) + ": ", 5.0F, var16, var12, var2)
                  : 5.0F;
               String var25 = var1 ? "Plains" : CoordinatesHudModule.this.biomeName;
               int var26 = CoordinatesHudModule.this.biome.presetBiomeColor.b() ? (var1 ? -10510539 : CoordinatesHudModule.this.biomeColor) : CoordinatesHudModule.this.biome.n().a(0.0F);
               if (var2) {
                  com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var25, var24, var16, var26, var12);
               }

               var15 = Math.max(var15, var24 + com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var25));
               var21 = var16;
               var16 += var13 + 2;
            }

            if (var8) {
               boolean var36 = var28 || var5 || var6 || var10 || var7;
               if (var36) {
                  var15 += 20.0F;
               } else {
                  var15 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var14);
                  var16 = var13 + 3;
               }

               float var38 = 12.0F;
               float var39 = var15 - var38 + 3.0F;
               float var27 = var36 ? var38 - com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var14) : 0.0F;
               float var22;
               if (var5) {
                  var22 = var32;
               } else if (!var28 && !var6) {
                  if (var10) {
                     var22 = var35;
                  } else if (var7) {
                     var22 = var21;
                  } else {
                     var22 = 3.0F;
                     var39 = 3.0F;
                     var23 = false;
                  }
               } else if (var28 && var6) {
                  var22 = var31 + (var34 - var31) / 2.0F;
                  var39 -= 9.0F;
               } else {
                  var22 = var28 ? var31 : var34;
                  var23 = false;
               }

               if (CoordinatesHudModule.this.direction.cardinalDirection.b() && var2) {
                  com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(CoordinatesHudModule.this.direction.n(), var14, var39 + var27 / 2.0F, var22, var12);
               }
            }

            if (var8 && var23 && var2) {
               float var37 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a("-");
               if (var28 && (var14.contains("W") || var14.contains("E"))) {
                  com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(
                     CoordinatesHudModule.this.direction.directionAffectXColor, var14.contains("W") ? "-" : "+", var15 - var37, var31, var12
                  );
               }

               if (var6 && (var14.contains("N") || var14.contains("S"))) {
                  com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(
                     CoordinatesHudModule.this.direction.directionAffectZColor, var14.contains("N") ? "-" : "+", var15 - var37, var34, var12
                  );
               }
            }

            var16 += var11 ? 2.0F : 8.0F;
         }

         this.a(var15 != 0.0F ? var15 + 5.0F : 0.0F, var15 != 0.0F ? var16 : 0.0F);
      }

      private float a(com.cheatbreaker.client.kazerui.module.modules.hud.CoordinateChildModule var1, String var2, float var3, boolean var4, boolean var5) {
         float var6 = var1.showLabel.b() ? this.a(var1.labelColor, var1.kind.name() + ": ", 5.0F, var3, var4, var5) : 5.0F;
         return this.a(var1.n(), var2, var6, var3, var4, var5);
      }
   }
}
