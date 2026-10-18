package com.cheatbreaker.client.kazerui.module.settings;

import java.awt.Color;
import java.util.Locale;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public final class ColorSetting extends com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<Integer> {
   private boolean alpha = true;
   private boolean chroma = true;
   private boolean chromaOn;
   private final float[] hsb = new float[3];
   private boolean haveBase;
   private int previousBase;
   private Object optionModel;
   private long optionRevision = Long.MIN_VALUE;
   private boolean cachedChroma;
   private boolean shift;
   private float cachedSpeed = 40.0F;

   public ColorSetting(String var1, int var2) {
      super(var1, var2);
   }

   public ColorSetting b() {
      this.alpha = false;
      return this;
   }

   public ColorSetting c() {
      this.chroma = false;
      this.optionRevision = Long.MIN_VALUE;
      return this;
   }

   public ColorSetting d() {
      this.chromaOn = true;
      this.optionRevision = Long.MIN_VALUE;
      return this;
   }

   public int e() {
      int var1 = this.j();
      return this.alpha ? var1 : var1 | 0xFF000000;
   }

   public boolean f() {
      this.o();
      return this.cachedChroma;
   }

   private void o() {
      com.cheatbreaker.client.kazerui.gui.components.MenuModel var1 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.d();
      if (var1 == null || this.optionModel != var1 || this.optionRevision != var1.revision) {
         String var2 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.a(this.owner.b(), this.key + "Chroma", null);
         this.cachedChroma = this.chroma && (var2 == null ? this.chromaOn : Boolean.parseBoolean(var2));

         try {
            this.cachedSpeed = Float.parseFloat(com.cheatbreaker.client.kazerui.module.ModuleRegistry.a(this.owner.b(), this.key + "ChromaSpeed", "40"));
         } catch (NumberFormatException var4) {
            this.cachedSpeed = 40.0F;
         }

         if (Float.isNaN(this.cachedSpeed) || Float.isInfinite(this.cachedSpeed)) {
            this.cachedSpeed = 40.0F;
         }

         this.cachedSpeed = Math.max(1.0F, Math.min(100.0F, this.cachedSpeed));
         this.shift = "SHIFT".equals(com.cheatbreaker.client.kazerui.module.ModuleRegistry.a(this.owner.b(), this.key + "ChromaType", "WAVE"));
         this.optionModel = var1;
         this.optionRevision = var1 == null ? Long.MIN_VALUE : var1.revision;
      }
   }

   public int a(float var1) {
      int var2 = this.e();
      if (!this.f()) {
         return var2;
      } else {
         if (!this.haveBase || this.previousBase != var2) {
            Color.RGBtoHSB(var2 >> 16 & 0xFF, var2 >> 8 & 0xFF, var2 & 0xFF, this.hsb);
            this.previousBase = var2;
            this.haveBase = true;
         }

         double var3 = (100.1F - this.cachedSpeed) / 100.0F;
         long var5 = System.nanoTime();
         float var7;
         if (this.shift) {
            var7 = (float)(var5 / (1.0E10 * var3) % 1.0);
         } else {
            int var8 = com.cheatbreaker.client.kazerui.overlay.OverlayLayout.extent;
            if (var8 == 0) {
               ScaledResolution var9 = new ScaledResolution(Minecraft.getMinecraft());
               var8 = var9.getScaledWidth() + var9.getScaledHeight();
            }

            float var10 = var8 - var1;
            var7 = (float)((var5 + var10 * (2.0E7 * var3)) / (1.0E10 * var3) % 1.0);
         }

         return var2 & 0xFF000000 | Color.HSBtoRGB(var7, this.hsb[1], this.hsb[2]) & 16777215;
      }
   }

   public int g() {
      return this.a(0.0F);
   }

   protected Integer a(String var1) {
      String var2 = var1.trim();
      return var2.matches("[0-9A-Fa-f]{8}") ? (int)Long.parseLong(var2, 16) : null;
   }

   protected String c(Integer var1) {
      return String.format(Locale.ROOT, "%08X", var1);
   }

   @Override
   protected String a() {
      return "color";
   }

   @Override
   protected void a(Map<String, Object> var1) {
      var1.put("alpha", this.alpha);
      var1.put("chroma", this.chroma);
      var1.put("chromaOn", this.chromaOn);
   }
}
