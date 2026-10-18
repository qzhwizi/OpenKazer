package com.cheatbreaker.client.kazerui.hud;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public final class HudFeatureManager implements com.cheatbreaker.client.kazerui.gui.components.SettingsStore {
   private final List<com.cheatbreaker.client.kazerui.hud.HudFeature> features = new ArrayList<>();
   private final com.cheatbreaker.client.kazerui.notifications.UiSettings config;
   private final com.cheatbreaker.client.kazerui.gui.components.MenuModel model;
   private final Map<com.cheatbreaker.client.kazerui.hud.HudFeature, com.cheatbreaker.client.kazerui.hud.HudFeatureManager.a> renderLayouts = new IdentityHashMap<>();

   public HudFeatureManager(File var1) {
      this.config = new com.cheatbreaker.client.kazerui.notifications.UiSettings(var1);
      this.config.a();
      com.cheatbreaker.client.kazerui.notifications.LegacyConfigMigration.a(var1.getParentFile(), this);
      if (!this.config.a("ui", "profiles")) {
         this.b("profiles", "Default");
         this.b("activeProfile", "Default");

         for (String var5 : new String[]{"fps", "coordinates"}) {
            this.b("profile.Default." + var5 + ".enabled", "" + this.config.a(var5, "features", true, "Show this HUD feature"));
         }
      }

      this.model = new com.cheatbreaker.client.kazerui.gui.components.MenuModel(this);
      com.cheatbreaker.client.kazerui.module.ModuleRegistry.a(this.model);

      for (com.cheatbreaker.client.kazerui.module.modulerender.HudElement var7 : com.cheatbreaker.client.kazerui.module.ModuleRegistry.c()) {
         this.features.add(var7);
      }

      com.cheatbreaker.client.kazerui.module.ModuleRegistry.hudListener = new com.cheatbreaker.client.kazerui.module.ModuleRegistry.a() {
         @Override
         public void a(com.cheatbreaker.client.kazerui.module.modulerender.HudElement var1) {
            if (!HudFeatureManager.this.features.contains(var1)) {
               HudFeatureManager.this.features.add(var1);
            }
         }

         @Override
         public void b(com.cheatbreaker.client.kazerui.module.modulerender.HudElement var1) {
            HudFeatureManager.this.features.remove(var1);
            HudFeatureManager.this.renderLayouts.remove(var1);
         }
      };
   }

   @Override
   public String a(String var1, String var2) {
      return this.config.a("ui", var1) ? this.config.b("ui").a(var1).a() : var2;
   }

   @Override
   public void b(String var1, String var2) {
      this.config.a("ui", var1, var2).a(var2);
   }

   @Override
   public void a(String var1) {
      if (this.config.a("ui")) {
         this.config.b("ui").b(var1);
      }
   }

   @Override
   public Collection<String> a() {
      return (Collection<String>)(this.config.a("ui") ? new ArrayList<>(this.config.b("ui").a()) : Collections.emptyList());
   }

   @Override
   public void b() {
      if (this.config.b()) {
         this.config.c();
      }
   }

   public com.cheatbreaker.client.kazerui.gui.components.MenuModel c() {
      return this.model;
   }

   public void a(com.cheatbreaker.client.CheatBreakerClient var1) {
      String var2 = "migration.runtime.1.8.9.v1";
      if (!"true".equals(this.a(var2, null))) {
         StringBuilder var3 = new StringBuilder();

         for (com.cheatbreaker.client.module.Module var5 : var1.moduleManager.modules) {
            var3.append('[').append(var5.h()).append("]\n-State=").append(var5.g());
            var3.append("\n-RenderHUD=").append(var5.q());
            var3.append("\n-xTranslation=").append(var5.k());
            var3.append("\n-yTranslation=").append(var5.l());
            if (var5.r() != null) {
               var3.append("\n-Anchor=").append(var5.r().a());
            }

            var3.append('\n');

            for (com.cheatbreaker.client.settings.Setting var7 : var5.m()) {
               if (!var7.f().equalsIgnoreCase("label") && var7.d() != null) {
                  var3.append(var7.f()).append('=').append(var7.d());
                  if (var7.rainbow) {
                     var3.append(";rainbow");
                  }

                  var3.append('\n');
               }
            }
         }

         try {
            com.cheatbreaker.client.kazerui.notifications.LegacyConfigMigration.a(new StringReader(var3.toString()), this.model.a(), this);
            this.b(var2, "true");
            this.model.revision++;
         } catch (IOException var8) {
            throw new IllegalStateException(var8);
         }
      }

      com.cheatbreaker.client.kazerui.notifications.LegacyConfigMigration.a(this, this.model.profiles);
      this.model.revision++;

      for (com.cheatbreaker.client.module.Module var10 : var1.moduleManager.modules) {
         com.cheatbreaker.client.kazerui.module.ModuleRegistry.a(var10);
      }
   }

   public void a(com.cheatbreaker.client.kazerui.hud.HudFeature var1) {
      this.features.add(var1);
   }

   public List<com.cheatbreaker.client.kazerui.hud.HudFeature> d() {
      return Collections.unmodifiableList(this.features);
   }

   public int e() {
      return this.features.size();
   }

   public boolean f() {
      return true;
   }

   public boolean b(String var1) {
      com.cheatbreaker.client.kazerui.module.KazerModule var2 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.a(var1);
      return var2 != null ? var2.a() : this.model.a(var1, "enabled", com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a(var1));
   }

   public boolean b(com.cheatbreaker.client.kazerui.hud.HudFeature var1) {
      if (!this.b(var1.a())) {
         return false;
      } else if (!(var1 instanceof com.cheatbreaker.client.kazerui.module.modulerender.HudElement)) {
         return true;
      } else {
         com.cheatbreaker.client.kazerui.module.modulerender.HudElement var2 = (com.cheatbreaker.client.kazerui.module.modulerender.HudElement)var1;
         if (!var2.module.c()) {
            return false;
         } else {
            boolean var3 = g();
            return var3 && !var2.l() ? false : var2.a(var3) && (var2.i() || var2.f() > 0.0F && var2.g() > 0.0F);
         }
      }
   }

   private static boolean g() {
      return Minecraft.getMinecraft().currentScreen instanceof com.cheatbreaker.client.kazerui.gui.HudMoveScreen;
   }

   public void c(String var1) {
      this.model.h(var1);
   }

   public String c(com.cheatbreaker.client.kazerui.hud.HudFeature var1) {
      String var2 = var1.b();
      if (var1.a().equals("coordinates") && !this.model.a("coordinates", "labels", true)) {
         var2 = var2.replace("XYZ: ", "");
      }

      return var2;
   }

   public float d(String var1) {
      com.cheatbreaker.client.kazerui.module.modulerender.HudElement var2 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.b(var1);
      return var2 != null
         ? var2.h()
         : Math.max(0.25F, Math.min(5.0F, this.model.a(var1, "scale", com.cheatbreaker.client.kazerui.gui.components.HudStyleKeys.a(var1, "scale", 1.0F))));
   }

   private String c(String var1, String var2) {
      String var3 = com.cheatbreaker.client.kazerui.gui.components.HudStyleKeys.a(var1, var2);
      return this.model.a(var1, var2, var3 == null ? "false" : var3);
   }

   private boolean d(String var1, String var2) {
      return Boolean.parseBoolean(this.c(var1, var2));
   }

   private float[] d(com.cheatbreaker.client.kazerui.hud.HudFeature var1) {
      if (var1 instanceof com.cheatbreaker.client.kazerui.module.modulerender.HudElement) {
         com.cheatbreaker.client.kazerui.module.modulerender.HudElement var6 = (com.cheatbreaker.client.kazerui.module.modulerender.HudElement)var1;
         var6.a(g());
         return new float[]{var6.f(), var6.g()};
      } else {
         String var2 = var1.a();
         float var3 = Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.e(var1));
         float var4 = this.d(var2, "staticBackgroundWidth")
            ? Math.max(this.model.a(var2, "backgroundWidth", com.cheatbreaker.client.kazerui.gui.components.HudStyleKeys.a(var2, "backgroundWidth", 56.0F)), var3 + 4.0F)
            : var3 + 8.0F;
         float var5 = this.d(var2, "staticBackgroundHeight")
            ? this.model.a(var2, "backgroundHeight", com.cheatbreaker.client.kazerui.gui.components.HudStyleKeys.a(var2, "backgroundHeight", 18.0F))
            : 13.0F;
         return new float[]{var4, var5};
      }
   }

   private String e(com.cheatbreaker.client.kazerui.hud.HudFeature var1) {
      return this.d(var1.a(), "brackets") ? "[" + this.c(var1) + "]" : this.c(var1);
   }

   public float[] a(com.cheatbreaker.client.kazerui.hud.HudFeature var1, ScaledResolution var2) {
      String var3 = var1.a();
      float var4 = this.d(var3);
      float[] var5 = this.d(var1);
      float var6 = var5[0] * var4;
      float var7 = var5[1] * var4;
      float var8 = var2.getScaledWidth();
      float var9 = var2.getScaledHeight();
      float var10 = this.model.a(var3, "x", Float.parseFloat(com.cheatbreaker.client.kazerui.gui.components.MenuModel.b(var3, "x")));
      float var11 = this.model.a(var3, "y", Float.parseFloat(com.cheatbreaker.client.kazerui.gui.components.MenuModel.b(var3, "y")));
      com.cheatbreaker.client.kazerui.hud.HudAnchor var12 = this.e(var3);
      if (var12 != null) {
         var10 += var12.a(var8, var6);
         var11 += var12.b(var9, var7);
      }

      if (var1 instanceof com.cheatbreaker.client.kazerui.module.modulerender.HudElement) {
         var11 += ((com.cheatbreaker.client.kazerui.module.modulerender.HudElement)var1).k() * var4;
      }

      var10 = Math.max(0.0F, Math.min(var8 - var6, var10));
      var11 = Math.max(0.0F, Math.min(var9 - var7, var11));
      return new float[]{var10, var11, var6, var7};
   }

   public com.cheatbreaker.client.kazerui.hud.HudAnchor e(String var1) {
      return com.cheatbreaker.client.kazerui.hud.HudAnchor.a(this.model.a(var1, "anchor", com.cheatbreaker.client.kazerui.gui.components.MenuModel.b(var1, "anchor")));
   }

   public void a(com.cheatbreaker.client.kazerui.hud.HudFeature var1, float var2, float var3, ScaledResolution var4) {
      String var5 = var1.a();
      float[] var6 = this.a(var1, var4);
      com.cheatbreaker.client.kazerui.hud.HudAnchor var7 = this.e(var5);
      if (var7 != null) {
         var2 -= var7.a(var4.getScaledWidth(), var6[2]);
         var3 -= var7.b(var4.getScaledHeight(), var6[3]);
      }

      if (var1 instanceof com.cheatbreaker.client.kazerui.module.modulerender.HudElement) {
         var3 -= ((com.cheatbreaker.client.kazerui.module.modulerender.HudElement)var1).k() * this.d(var1.a());
      }

      this.model.b(var5, "x", "" + var2);
      this.model.b(var5, "y", "" + var3);
   }

   public void b(com.cheatbreaker.client.kazerui.hud.HudFeature var1, ScaledResolution var2) {
      float[] var3 = this.a(var1, var2);
      com.cheatbreaker.client.kazerui.hud.HudAnchor var4 = com.cheatbreaker.client.kazerui.hud.HudAnchor.a(
         var3[0] + var3[2] / 2.0F, var3[1] + var3[3] / 2.0F, var2.getScaledWidth(), var2.getScaledHeight()
      );
      this.model.b(var1.a(), "anchor", var4.id);
      this.a(var1, var3[0], var3[1], var2);
   }

   public void a(ScaledResolution var1) {
      Minecraft var2 = Minecraft.getMinecraft();
      if (var2.thePlayer != null && (!var2.gameSettings.showDebugInfo || !this.model.a("global", "hideDebug", true))) {
         boolean var3 = g();

         for (com.cheatbreaker.client.kazerui.hud.HudFeature var5 : this.features) {
            if ((var3 || !(var5 instanceof com.cheatbreaker.client.kazerui.module.modulerender.HudElement) || !((com.cheatbreaker.client.kazerui.module.modulerender.HudElement)var5).j())
               && this.b(var5)) {
               this.d(var5, var1);
            }
         }
      }
   }

   public void c(com.cheatbreaker.client.kazerui.hud.HudFeature var1, ScaledResolution var2) {
      if (var1 instanceof com.cheatbreaker.client.kazerui.module.modulerender.HudElement) {
         ((com.cheatbreaker.client.kazerui.module.modulerender.HudElement)var1).a(g());
      }

      this.d(var1, var2);
   }

   private void d(com.cheatbreaker.client.kazerui.hud.HudFeature var1, ScaledResolution var2) {
      com.cheatbreaker.client.kazerui.overlay.OverlayLayout.a(var2);
      Minecraft var3 = Minecraft.getMinecraft();
      String var4 = var1.a();
      if (!(var1 instanceof com.cheatbreaker.client.kazerui.module.modulerender.HudElement)) {
         float[] var17 = this.a(var1, var2);
         float[] var18 = this.d(var1);
         GlStateManager.pushMatrix();
         GlStateManager.translate(var17[0], var17[1], 0.0F);
         GlStateManager.scale(this.d(var4), this.d(var4), 1.0F);
         if (this.d(var4, "background")) {
            com.cheatbreaker.client.kazerui.gui.UiGraphics.a(0.0F, 0.0F, var18[0], var18[1], this.e(var4, "backgroundColor"));
         }

         if (this.d(var4, "border")) {
            float var19 = Math.max(
               0.5F, Math.min(3.0F, this.model.a(var4, "borderThickness", com.cheatbreaker.client.kazerui.gui.components.HudStyleKeys.a(var4, "borderThickness", 0.5F)))
            );
            com.cheatbreaker.client.kazerui.gui.UiGraphics.b(0.0F, 0.0F, var18[0], var18[1], var19, this.e(var4, "borderColor"));
         }

         boolean var20 = this.d(var4, "textShadow");
         boolean var21 = this.d(var4, "brackets");
         String var22 = this.c(var1);
         float var24 = (var18[0] - var3.fontRendererObj.getStringWidth(this.e(var1))) / 2.0F;
         float var28 = (var18[1] - 8.0F) / 2.0F;
         if (var21) {
            var24 = var3.fontRendererObj.drawString("[", var24, var28, this.e(var4, "bracketColor"), var20);
         }

         var24 = var3.fontRendererObj.drawString(var22, var24, var28, this.e(var4, "textColor"), var20);
         if (var21) {
            var3.fontRendererObj.drawString("]", var24, var28, this.e(var4, "bracketColor"), var20);
         }

         GlStateManager.popMatrix();
      } else {
         com.cheatbreaker.client.kazerui.module.modulerender.HudElement var5 = (com.cheatbreaker.client.kazerui.module.modulerender.HudElement)var1;
         com.cheatbreaker.client.kazerui.hud.HudFeatureManager.a var6 = this.renderLayouts.get(var1);
         if (var6 == null) {
            var6 = new com.cheatbreaker.client.kazerui.hud.HudFeatureManager.a();
            this.renderLayouts.put(var1, var6);
         }

         if (var6.revision != this.model.revision) {
            var6.x = this.model.a(var4, "x", Float.parseFloat(com.cheatbreaker.client.kazerui.gui.components.MenuModel.b(var4, "x")));
            var6.y = this.model.a(var4, "y", Float.parseFloat(com.cheatbreaker.client.kazerui.gui.components.MenuModel.b(var4, "y")));
            var6.anchor = this.e(var4);
            var6.revision = this.model.revision;
         }

         float var7 = var5.h();
         float var8 = var5.f() * var7;
         float var9 = var5.g() * var7;
         float var10 = var6.x;
         float var11 = var6.y;
         float var12 = var2.getScaledWidth();
         float var13 = var2.getScaledHeight();
         if (var6.anchor != null) {
            var10 += var6.anchor.a(var12, var8);
            var11 += var6.anchor.b(var13, var9);
         }

         var11 += var5.k() * var7;
         var10 = Math.max(0.0F, Math.min(var12 - var8, var10));
         var11 = Math.max(0.0F, Math.min(var13 - var9, var11));
         GlStateManager.pushMatrix();

         try {
            if (!var5.i()) {
               GlStateManager.translate(var10, var11, 0.0F);
               GlStateManager.scale(var7, var7, 1.0F);
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            var5.b(g());
         } finally {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
         }
      }
   }

   private int e(String var1, String var2) {
      String var3 = com.cheatbreaker.client.kazerui.gui.components.HudStyleKeys.a(var1, var2);
      int var4 = this.a(var1, var2, var3 == null ? -1 : (int)Long.parseLong(var3, 16));
      com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var5 = com.cheatbreaker.client.kazerui.gui.components.OptionTree.a(var1, var2);
      if (!this.model.a(var1, var2 + "Chroma", var5 != null && var5.chromaOn)) {
         return var4;
      } else {
         float var6 = Math.max(1.0F, Math.min(100.0F, this.model.a(var1, var2 + "ChromaSpeed", 40.0F)));
         float var7 = (float)(System.currentTimeMillis() % (long)(200000.0F / var6)) / (200000.0F / var6);
         return var4 & 0xFF000000 | Color.HSBtoRGB(var7, 1.0F, 1.0F) & 16777215;
      }
   }

   private int a(String var1, String var2, int var3) {
      try {
         return (int)Long.parseLong(this.model.a(var1, var2, String.format("%08X", var3)), 16);
      } catch (NumberFormatException var5) {
         return var3;
      }
   }

   private static final class a {
      long revision = Long.MIN_VALUE;
      float x;
      float y;
      com.cheatbreaker.client.kazerui.hud.HudAnchor anchor;

      private a() {
      }
   }
}
