package com.cheatbreaker.client.gui.uielement.settings;

import java.math.BigDecimal;
import java.math.RoundingMode;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;

public class SliderSettingElement extends com.cheatbreaker.client.gui.uielement.UIElement {
   private static final float TRACK_START_OFFSET = 181.25F;
   private static final float TRACK_WIDTH = 130.0F;
   private static final float MARKER_SNAP_RADIUS = 6.0F;
   private static final float VALUE_LEFT_OFFSET = 130.0F;
   private static final float VALUE_RIGHT_OFFSET = 178.0F;
   private static final com.cheatbreaker.client.gui.uielement.settings.SliderSettingElement.a OPENGL_SLIDER_DRAWER = new com.cheatbreaker.client.gui.uielement.settings.SliderSettingElement.a() {
      @Override
      public void a(double var1, double var3, double var5, double var7, int var9) {
         cn.kazer.client189.render.DrawRect.a((float)var1, (float)var3, (float)var5, (float)var7, var9);
      }

      @Override
      public void a(double var1, double var3, double var5, int var7) {
         cn.kazer.client189.render.GlStateBridge.a((var7 >> 16 & 0xFF) / 255.0F, (var7 >> 8 & 0xFF) / 255.0F, (var7 & 0xFF) / 255.0F, (var7 >>> 24) / 255.0F);
         com.cheatbreaker.client.gui.render.RenderUtils.a(var1, var3, var5);
      }
   };
   private com.cheatbreaker.client.settings.Setting setting;
   private float IllIIIIIIIlIlIllllIIllIII = -1.0F;
   private float val;
   private boolean IlllIllIlIIIIlIIlIIllIIIl = false;
   private boolean editingValue;
   private boolean selectAllValue;
   private String valueBuffer;

   public SliderSettingElement(com.cheatbreaker.client.settings.Setting var1, float var2) {
      super(var2);
      this.setting = var1;
      this.height = 14;
      this.IllIIIIIIIlIlIllllIIllIII = Float.parseFloat("" + var1.d());
   }

   @Override
   public void a(int var1, int var2, int var3) {
      if (var3 == 0 && this.b(var1, var2)) {
         this.d();
      } else {
         short var4 = 170;
         short var5 = 170;
         boolean var6 = var1 > (this.x + var4) * this.scale
            && var1 < (this.x + var4 + var5 - 2) * this.scale
            && var2 > (this.y + 4 + this.yOffset) * this.scale
            && var2 < (this.y + 10 + this.yOffset) * this.scale;
         if (var3 == 0 && var6) {
            this.IlllIllIlIIIIlIIlIIllIIIl = true;
            if (this.setting == com.cheatbreaker.client.CheatBreakerClient.N().j().containerVignetteStrength
               && Minecraft.getMinecraft().currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.HudEditorScreen) {
               cn.kazer.client189.render.ClientBridge.a().b(Minecraft.getSystemTime());
            }
         }
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      short var6 = 148;
      com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.a(this.setting.g().toUpperCase(), (float)(this.x + 10), (float)(this.y + 2), -1895825408);
      if (this.IlllIllIlIIIIlIIlIIllIIIl && !Mouse.isButtonDown(0)) {
         this.IlllIllIlIIIIlIIlIIllIIIl = false;
      }

      boolean var7 = this.b(var1, var2);
      String var8 = this.editingValue ? this.valueBuffer : this.setting.d() + "";
      float var9 = com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.a(var8);
      double[] var10 = a(154.0, (double)var9);
      a(
         var7,
         this.editingValue,
         this.selectAllValue,
         this.x + var10[0],
         this.y,
         this.x + var10[1],
         this.y + 13,
         this.x + 154 - var9 / 2.0F,
         this.x + 154 + var9 / 2.0F,
         OPENGL_SLIDER_DRAWER
      );
      float var10002 = this.x + 154;
      float var10003 = this.y + 2;
      com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.b(var8, var10002, var10003, -1895825408);
      if (this.editingValue && !this.selectAllValue && System.currentTimeMillis() / 500L % 2L == 0L) {
         float var11 = this.x + 154 + var9 / 2.0F + 1.0F;
         cn.kazer.client189.render.DrawRect.a(var11, this.y + 2, var11 + 1.0F, this.y + 11, 2130706432);
      }

      boolean var24 = var1 > (this.x + 172) * this.scale
         && var1 < (this.x + 172 + var6 - 2) * this.scale
         && var2 > (this.y + 4 + this.yOffset) * this.scale
         && var2 < (this.y + 10 + this.yOffset) * this.scale;
      com.cheatbreaker.client.gui.render.RenderUtils.a(
         (double)(this.x + 174), (double)(this.y + 6), (double)(this.x + 170 + var6 - 4), (double)(this.y + 8), 1.0, var24 ? -1895825408 : 1862270976
      );
      double var12 = 130.0;
      float var14 = Float.parseFloat("" + this.setting.b());
      float var15 = Float.parseFloat("" + this.setting.c());
      if (this.IlllIllIlIIIIlIIlIIllIIIl) {
         this.val = (float)Math.round(a(var1, this.x, this.scale, var14, var15, this.setting.k()) * 100.0) / 100.0F;
         if (this.setting.l().equals(com.cheatbreaker.client.settings.Setting.SettingType.INTEGER)) {
            this.val = Math.round(this.val);
         }

         if (this.val < var14) {
            this.val = var14;
         } else if (this.val > var15) {
            this.val = var15;
         }

         switch (this.setting.l()) {
            case INTEGER:
               this.setting.a(Integer.parseInt((int)this.val + ""));
               break;
            case FLOAT:
               this.setting.a(Float.valueOf(this.val));
               break;
            case DOUBLE:
               this.setting.a(Double.parseDouble(this.val + ""));
         }

         com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.keyStrokes.a();
      }

      float var5;
      float var22;
      var5 = (var5 = Float.parseFloat(this.setting.d() + "")) < this.IllIIIIIIIlIlIllllIIllIII
         ? this.IllIIIIIIIlIlIllllIIllIII - var5
         : (var22 = var5 - this.IllIIIIIIIlIlIllllIIllIII);
      float var16 = ((var15 - var14) / 20.0F + var5 * 8.0F) / (Minecraft.getDebugFPS() + 1);
      if (var16 < 1.0E-4) {
         var16 = 1.0E-4F;
      }

      float var4;
      if (this.IllIIIIIIIlIlIllllIIllIII < (var4 = Float.parseFloat(this.setting.d() + ""))) {
         this.IllIIIIIIIlIlIllllIIllIII = this.IllIIIIIIIlIlIllllIIllIII + var16 <= var4 ? (this.IllIIIIIIIlIlIllllIIllIII += var16) : var4;
      } else if (this.IllIIIIIIIlIlIllllIIllIII > var4) {
         this.IllIIIIIIIlIlIllllIIllIII = this.IllIIIIIIIlIlIllllIIllIII - var16 >= var4 ? (this.IllIIIIIIIlIlIllllIIllIII -= var16) : var4;
      }

      double var17 = 100.0F * ((this.IllIIIIIIIlIlIllllIIllIII - var14) / (var15 - var14));
      com.cheatbreaker.client.gui.render.RenderUtils.a((double)(this.x + 174), (double)(this.y + 6), this.x + 180 + var12 * var17 / 100.0, (double)(this.y + 8), 4.0, -12418828);
      Double var19 = this.setting.k() == null ? null : this.x + 181.25F + var12 * this.setting.k().floatValue();
      double var20 = this.x + 181.25F + var12 * var17 / 100.0;
      a(var19, var20, this.y + 7.25, OPENGL_SLIDER_DRAWER);
   }

   @Override
   public boolean a(char var1, int var2) {
      if (!this.editingValue) {
         return false;
      } else if (var2 != 203 && var2 != 205 && var2 != 200 && var2 != 208) {
         if (var2 == 28 || var2 == 156) {
            this.e();
            return true;
         } else if (var2 == 14) {
            if (this.selectAllValue) {
               this.valueBuffer = "";
               this.selectAllValue = false;
            } else if (!this.valueBuffer.isEmpty()) {
               this.valueBuffer = this.valueBuffer.substring(0, this.valueBuffer.length() - 1);
            }

            return true;
         } else if (var2 == 211) {
            this.valueBuffer = "";
            this.selectAllValue = false;
            return true;
         } else if (var2 == 1 || var2 == 15) {
            this.e();
            return false;
         } else if (var1 == 1) {
            this.selectAllValue = true;
            return true;
         } else if (a(var1, this.setting.l(), this.valueBuffer, this.selectAllValue)) {
            this.valueBuffer = this.selectAllValue ? Character.toString(var1) : this.valueBuffer + var1;
            this.selectAllValue = false;
            return true;
         } else {
            return true;
         }
      } else {
         boolean var3 = var2 == 205 || var2 == 200;
         boolean var4 = var2 == 200 || var2 == 208;
         Number var5 = a(this.valueBuffer, this.setting.l(), this.f(), this.g());
         if (var5 == null) {
            var5 = (Number)this.setting.d();
         }

         Number var6 = a(var5, this.setting.l(), var3, var4, this.f(), this.g());
         this.a(var6);
         this.valueBuffer = a(var6, this.setting.l());
         this.selectAllValue = false;
         return true;
      }
   }

   @Override
   public void b(int var1, int var2, int var3) {
      if (this.editingValue && !this.b(var1, var2)) {
         this.e();
      }
   }

   @Override
   public void a() {
      this.e();
   }

   private void d() {
      this.editingValue = true;
      this.selectAllValue = true;
      this.valueBuffer = this.setting.d().toString();
   }

   private void e() {
      if (this.editingValue) {
         Number var1 = a(this.valueBuffer, this.setting.l(), this.f(), this.g());
         if (var1 != null) {
            this.a(var1);
         }

         this.editingValue = false;
         this.selectAllValue = false;
         this.valueBuffer = null;
      }
   }

   private void a(Number var1) {
      switch (this.setting.l()) {
         case INTEGER:
            this.setting.a(var1.intValue());
            break;
         case FLOAT:
            this.setting.a(Float.valueOf(var1.floatValue()));
            break;
         case DOUBLE:
            this.setting.a(var1.doubleValue());
      }

      com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.keyStrokes.a();
   }

   private double f() {
      return this.setting.b() == null ? -Double.MAX_VALUE : this.setting.b().doubleValue();
   }

   private double g() {
      return this.setting.c() == null ? Double.MAX_VALUE : this.setting.c().doubleValue();
   }

   private boolean b(int var1, int var2) {
      String var3 = this.editingValue ? this.valueBuffer : this.setting.d().toString();
      float var4 = com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.a(var3);
      double[] var5 = a(154.0, (double)var4);
      return var1 >= (this.x + var5[0]) * this.scale
         && var1 <= (this.x + var5[1]) * this.scale
         && var2 >= (this.y + this.yOffset) * this.scale
         && var2 <= (this.y + this.yOffset + 13) * this.scale;
   }

   static double[] a(double var0, double var2) {
      double var4 = var2 / 2.0 + 2.0;
      return new double[]{Math.max(130.0, var0 - var4), Math.min(178.0, var0 + var4)};
   }

   static Number a(String var0, com.cheatbreaker.client.settings.Setting.SettingType var1, double var2, double var4) {
      if (var0 != null && !var0.isEmpty() && !"-".equals(var0) && !".".equals(var0) && !"-.".equals(var0)) {
         try {
            if (var1 == com.cheatbreaker.client.settings.Setting.SettingType.INTEGER) {
               return (int)Math.max(var2, Math.min(var4, (double)Integer.parseInt(var0)));
            } else {
               double var6 = Math.max(var2, Math.min(var4, Double.parseDouble(var0)));
               return var1 == com.cheatbreaker.client.settings.Setting.SettingType.FLOAT ? (float)var6 : var6;
            }
         } catch (NumberFormatException var8) {
            return null;
         }
      } else {
         return null;
      }
   }

   static Number a(Number var0, com.cheatbreaker.client.settings.Setting.SettingType var1, boolean var2, boolean var3, double var4, double var6) {
      BigDecimal var8 = var1 == com.cheatbreaker.client.settings.Setting.SettingType.INTEGER ? BigDecimal.valueOf(var3 ? 10L : 1L) : BigDecimal.valueOf(var3 ? 0.1 : 0.01);
      BigDecimal var9 = new BigDecimal(var0.toString());
      var9 = var2 ? var9.add(var8) : var9.subtract(var8);
      double var10 = Math.max(var4, Math.min(var6, var9.doubleValue()));
      return (Number)(var1 == com.cheatbreaker.client.settings.Setting.SettingType.INTEGER
         ? (int)Math.round(var10)
         : var1 == com.cheatbreaker.client.settings.Setting.SettingType.FLOAT ? (float)var10 : var10);
   }

   static String a(Number var0, com.cheatbreaker.client.settings.Setting.SettingType var1) {
      return var1 == com.cheatbreaker.client.settings.Setting.SettingType.INTEGER
         ? Integer.toString(var0.intValue())
         : BigDecimal.valueOf(var0.doubleValue()).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
   }

   static boolean a(char var0, com.cheatbreaker.client.settings.Setting.SettingType var1, String var2, boolean var3) {
      if (var0 >= '0' && var0 <= '9') {
         return true;
      } else {
         String var4 = var3 ? "" : var2;
         return var0 == '-' ? var4.isEmpty() : var0 == '.' && var1 != com.cheatbreaker.client.settings.Setting.SettingType.INTEGER && !var4.contains(".");
      }
   }

   static double a(float var0, float var1, float var2, float var3, float var4, Float var5) {
      float var6 = (var1 + 181.25F) * var2;
      float var7 = var0 - var6;
      double var8 = var7 / (130.0 * var2);
      if (var5 != null) {
         float var10 = (var1 + 181.25F + 130.0F * var5) * var2;
         if (Math.abs(var0 - var10) <= 6.0F * var2) {
            var8 = var5.floatValue();
         }
      }

      double var12 = var3 + (var4 - var3) * var8;
      return Math.max((double)var3, Math.min((double)var4, var12));
   }

   static void a(Double var0, double var1, double var3, com.cheatbreaker.client.gui.uielement.settings.SliderSettingElement.a var5) {
      if (var0 != null) {
         var5.a(var0 - 0.5, var3 - 9.25, var0 + 0.5, var3 - 3.25, 1862270976);
      }

      var5.a(var1, var3, 4.5, -12553217);
      boolean var6 = var0 != null && Math.abs(var0 - var1) < 0.001;
      var5.a(var1, var3, 2.7, var6 ? -12553217 : -1);
   }

   static void a(
      boolean var0,
      boolean var1,
      boolean var2,
      double var3,
      double var5,
      double var7,
      double var9,
      double var11,
      double var13,
      com.cheatbreaker.client.gui.uielement.settings.SliderSettingElement.a var15
   ) {
      if (var0 || var1) {
         var15.a(var3, var5, var7, var9, var1 ? 603979776 : 536870912);
         if (var1 && var2) {
            var15.a(var11 - 1.0, var5 + 1.0, var13 + 1.0, var9 - 1.0, 943748095);
         }
      }
   }

   @Override
   public com.cheatbreaker.client.settings.Setting b() {
      return this.setting;
   }

   interface a {
      void a(double var1, double var3, double var5, double var7, int var9);

      void a(double var1, double var3, double var5, int var7);
   }
}
