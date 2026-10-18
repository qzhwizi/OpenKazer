package com.cheatbreaker.client.module.modules;

import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class CoordinatesModule extends com.cheatbreaker.client.module.Module {
   private final com.cheatbreaker.client.settings.Setting mode;
   private final com.cheatbreaker.client.settings.Setting showWhileTyping;
   private final com.cheatbreaker.client.settings.Setting showCoordinates;
   private final com.cheatbreaker.client.settings.Setting hideYCoordinate;
   private final com.cheatbreaker.client.settings.Setting showDirection;
   private final com.cheatbreaker.client.settings.Setting coordinatesColor;
   private final com.cheatbreaker.client.settings.Setting customLine;
   private final com.cheatbreaker.client.settings.Setting directionColor;
   private static final String[] DIRECTIONS = new String[]{"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
   private int lastX;
   private int lastY;
   private int lastZ;
   private boolean lastHideY;
   private String cachedHorizontal;
   private String cachedX;
   private String cachedY;
   private String cachedZ;

   public CoordinatesModule() {
      super("Coordinates");
      this.b(com.cheatbreaker.client.gui.hudeditor.GuiAnchor.LEFT_TOP);
this.b(158.0F, 0.0F);
      this.c(true);
      this.g(false);
      new com.cheatbreaker.client.settings.Setting(this, "label").a("General Options");
      this.showWhileTyping = new com.cheatbreaker.client.settings.Setting(this, "Show While Typing").a(true);
      this.mode = new com.cheatbreaker.client.settings.Setting(this, "Mode").a("Horizontal").a("Horizontal", "Vertical");
      this.hideYCoordinate = new com.cheatbreaker.client.settings.Setting(this, "Hide Y Coordinate").a(false);
      this.showCoordinates = new com.cheatbreaker.client.settings.Setting(this, "Show Coordinates").a(true);
      this.showDirection = new com.cheatbreaker.client.settings.Setting(this, "Direction").a(true);
      this.customLine = new com.cheatbreaker.client.settings.Setting(this, "Custom Line").a("");
      new com.cheatbreaker.client.settings.Setting(this, "label").a("Color Settings");
      this.coordinatesColor = new com.cheatbreaker.client.settings.Setting(this, "Coordinates Color").a(-1).a(Integer.MIN_VALUE, Integer.MAX_VALUE);
      this.directionColor = new com.cheatbreaker.client.settings.Setting(this, "Direction Color").a(-1).a(Integer.MIN_VALUE, Integer.MAX_VALUE);
      this.a("(16, 65, 120) NW", 1.0F);
      this.a(com.cheatbreaker.client.event.event.RenderHudEvent.class, this::a);
   }

   public void a(com.cheatbreaker.client.event.event.RenderHudEvent var1) {
      if (this.q()) {
         int var2 = MathHelper.floor_double(this.minecraft.thePlayer.posX);
         int var3 = (int)this.minecraft.thePlayer.getEntityBoundingBox().minY;
         int var4 = MathHelper.floor_double(this.minecraft.thePlayer.posZ);
         if (!this.minecraft.ingameGUI.getChatGUI().getChatOpen() || (Boolean)this.showWhileTyping.d()) {
            boolean var5 = this.mode.d().equals("Horizontal");
            boolean var6 = (Boolean)this.hideYCoordinate.d();
            boolean var7 = (Boolean)this.showCoordinates.d();
            float var8 = var5 ? 4.0F : (var6 ? 9.5F : 16.0F);
            if (this.cachedHorizontal == null || this.lastX != var2 || this.lastY != var3 || this.lastZ != var4 || this.lastHideY != var6) {
               this.cachedHorizontal = var6 ? String.format("(%1$d, %2$d) ", var2, var4) : String.format("(%1$d, %2$d, %3$d) ", var2, var3, var4);
               this.cachedX = "X: " + var2;
               this.cachedY = "Y: " + var3;
               this.cachedZ = "Z: " + var4;
               this.lastX = var2;
               this.lastY = var3;
               this.lastZ = var4;
               this.lastHideY = var6;
            }

            String var9 = var7 ? this.cachedHorizontal : "";
            String var10 = this.cachedX;
            String var11 = this.cachedY;
            String var12 = this.cachedZ;
            int var13;
            if (var5) {
               var13 = this.minecraft.fontRendererObj.getStringWidth(var9);
            } else {
               var13 = Math.max(this.minecraft.fontRendererObj.getStringWidth(var10), this.minecraft.fontRendererObj.getStringWidth(var12));
               if (!var6) {
                  var13 = Math.max(var13, this.minecraft.fontRendererObj.getStringWidth(var11));
               }
            }

String var14 = (Boolean) this.showDirection.d() ? this.a() : null;
            int var15 = var5 ? var13 : 50;
            float var16 = var14 == null ? var13 : Math.max(var13, var15 + this.minecraft.fontRendererObj.getStringWidth(var14));
            this.c(var16, a(var5, var6, this.minecraft.fontRendererObj.FONT_HEIGHT));
            GL11.glPushMatrix();
            this.a(var1.a());
            if (var5) {
               this.minecraft.fontRendererObj.drawStringWithShadow(var9, 0.0F, 0.0F, this.coordinatesColor.a());
            } else {
               this.minecraft.fontRendererObj.drawStringWithShadow(var10, 0.0F, 0.0F, this.coordinatesColor.a());
               if (!var6) {
                  this.minecraft.fontRendererObj.drawStringWithShadow(var11, 0.0F, 12.0F, this.coordinatesColor.a());
               }

               this.minecraft.fontRendererObj.drawStringWithShadow(var12, 0.0F, var6 ? 12.0F : 24.0F, this.coordinatesColor.a());
            }

            if (var14 != null) {
               this.minecraft.fontRendererObj.drawStringWithShadow(var14, var15, (int)var8 - 4, this.directionColor.a());
            }

            GL11.glPopMatrix();
         }
      }
   }

   static float a(boolean var0, boolean var1, int var2) {
      int var3 = var0 ? 1 : (var1 ? 2 : 3);
      return (var3 - 1) * 12.0F + var2;
   }

   private String a() {
      double var1 = MathHelper.wrapAngleTo180_double(this.minecraft.thePlayer.rotationYaw) + 180.0;
      var1 += 22.5;
      var1 %= 360.0;
      double var5;
      return DIRECTIONS[MathHelper.floor_double(var5 = var1 / 45.0)];
   }
}
