package com.cheatbreaker.client.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public abstract class Module {
   private final String name;
   private final List<Object> defaultSettingsValues;
   private final Map<Class<? extends com.cheatbreaker.client.event.EventBus.aInner>, Consumer> eventMap;
   public boolean defaultState = false;
   public com.cheatbreaker.client.gui.hudeditor.GuiAnchor defaultGuiAnchor;
   public float defaultXTranslation = 0.0F;
   public float defaultYTranslation = 0.0F;
   public boolean defaultRenderHud = true;
   public float width = 0.0F;
   public float height = 0.0F;
   public boolean isEditable = true;
   public com.cheatbreaker.client.settings.Setting scale;
   protected Minecraft minecraft;
   private boolean staffModule = false;
   private boolean staffModuleEnabled = false;
   private boolean enabled = false;
   private boolean applyingSharedUiState;
   private boolean sharedUiOwnsEvents;
   private com.cheatbreaker.client.gui.hudeditor.GuiAnchor guiAnchor;
   private float xTranslation = 0.0F;
   private float yTranslation = 0.0F;
   private boolean renderHud = true;
   private List<com.cheatbreaker.client.settings.Setting> settingsList;
   private com.cheatbreaker.client.module.Module.aInner previewType;
   private ResourceLocation previewIconLocation;
   private float previewIconWidth;
   private float previewIconHeight;
   private float previewLabelSize;
   private String previewLabel;

   public Module(String var1) {
      this.name = var1;
      this.eventMap = new HashMap<>();
      this.minecraft = Minecraft.getMinecraft();
      this.settingsList = new ArrayList<>();
      this.defaultSettingsValues = new ArrayList<>();
      this.scale = new com.cheatbreaker.client.settings.Setting(this, "Scale").a(Float.valueOf(1.0F)).a(0.5F, 1.5F);
   }

   protected <T extends com.cheatbreaker.client.event.EventBus.aInner> void a(Class<T> var1, Consumer<T> var2) {
      this.eventMap.put(var1, var2);
      if (this.enabled && !this.sharedUiOwnsEvents && com.cheatbreaker.client.CheatBreakerClient.N() != null) {
         com.cheatbreaker.client.CheatBreakerClient.N().eventBus.a(var1, var2);
      }
   }

   protected void b() {
      if (!this.sharedUiOwnsEvents && com.cheatbreaker.client.CheatBreakerClient.N() != null) {
         for (Entry var2 : this.eventMap.entrySet()) {
            com.cheatbreaker.client.CheatBreakerClient.N().eventBus.a((Class)var2.getKey(), (Consumer)var2.getValue());
         }
      }
   }

   protected void c() {
      if (com.cheatbreaker.client.CheatBreakerClient.N() != null) {
         for (Entry var2 : this.eventMap.entrySet()) {
            com.cheatbreaker.client.CheatBreakerClient.N().eventBus.b((Class)var2.getKey(), (Consumer)var2.getValue());
         }
      }
   }

   public void a(boolean var1) {
      if (!this.applyingSharedUiState && var1 != this.defaultState) {
         com.cheatbreaker.client.CheatBreakerClient.N().c();
      }

      if (var1) {
         if (!this.enabled) {
            this.enabled = true;
            this.b();
         }
      } else if (this.enabled) {
         this.enabled = false;
         this.c();
      }
   }

   public final void b(boolean var1) {
      boolean var2 = this.applyingSharedUiState;
      this.applyingSharedUiState = true;

      try {
         this.a(var1);
      } finally {
         this.applyingSharedUiState = var2;
      }
   }

   public final void d() {
      this.c();
      this.sharedUiOwnsEvents = true;
   }

   public void c(boolean var1) {
      if (var1) {
         if (!this.enabled) {
            this.enabled = true;
            this.b();
         }
      } else if (this.enabled) {
         this.enabled = false;
         this.c();
      }

      this.defaultState = this.enabled;
   }

   public void a(float var1, float var2) {
      this.xTranslation = var1;
      this.yTranslation = var2;
   }

   public void b(float var1, float var2) {
      this.xTranslation = var1;
      this.yTranslation = var2;
      this.defaultXTranslation = var1;
      this.defaultYTranslation = var2;
   }

   public void c(float var1, float var2) {
      this.width = var1;
      this.height = var2;
   }

   public boolean e() {
      return this.staffModule;
   }

   public void d(boolean var1) {
      this.staffModule = var1;
   }

   public boolean f() {
      return this.staffModuleEnabled;
   }

   public void e(boolean var1) {
      this.staffModuleEnabled = var1;
      if (!var1 && this.g()) {
         this.a(false);
      }
   }

   public boolean g() {
      return this.enabled;
   }

   public String h() {
      return this.name;
   }

   public float i() {
      return this.width;
   }

   public float j() {
      return this.height;
   }

   public float k() {
      return this.xTranslation;
   }

   public void a(float var1) {
      this.xTranslation = var1;
   }

   public float l() {
      return this.yTranslation;
   }

   public void b(float var1) {
      this.yTranslation = var1;
   }

   public List<com.cheatbreaker.client.settings.Setting> m() {
      return this.settingsList;
   }

   public void a(List<com.cheatbreaker.client.settings.Setting> var1) {
      this.settingsList = var1;
   }

   public List<Object> n() {
      return this.defaultSettingsValues;
   }

   public void o() {
      int var1 = Math.min(this.settingsList.size(), this.defaultSettingsValues.size());

      for (int var2 = 0; var2 < var1; var2++) {
         this.settingsList.get(var2).a(this.defaultSettingsValues.get(var2));
      }
   }

   protected float p() {
      return this.width;
   }

   public void a(ScaledResolution var1) {
      this.a(var1, this.p(), this.height);
   }

   public void a(ScaledResolution var1, float var2, float var3) {
      float var4 = 0.0F;
      float var5 = 0.0F;
      float var6 = (Float)this.scale.d();
      GL11.glScalef(var6, var6, var6);
      var2 *= var6;
      var3 *= var6;
      switch (this.guiAnchor) {
         case LEFT_TOP:
            var4 = 2.0F;
            var5 = 2.0F;
            break;
         case LEFT_MIDDLE:
            var4 = 2.0F;
            var5 = var1.getScaledHeight() / 2 - var3 / 2.0F;
            break;
         case LEFT_BOTTOM:
            var5 = var1.getScaledHeight() - var3 - 2.0F;
            var4 = 2.0F;
            break;
         case MIDDLE_TOP:
            var4 = var1.getScaledWidth() / 2 - var2 / 2.0F;
            var5 = 2.0F;
            break;
         case MIDDLE_MIDDLE:
            var4 = var1.getScaledWidth() / 2 - var2 / 2.0F;
            var5 = var1.getScaledHeight() / 2 - var3 / 2.0F;
            break;
         case MIDDLE_BOTTOM_LEFT:
            var4 = var1.getScaledWidth() / 2 - var2;
            var5 = var1.getScaledHeight() - var3 - 2.0F;
            break;
         case MIDDLE_BOTTOM_RIGHT:
            var4 = var1.getScaledWidth() / 2.0F;
            var5 = var1.getScaledHeight() - var3 - 2.0F;
            break;
         case RIGHT_TOP:
            var4 = var1.getScaledWidth() - var2 - 2.0F;
            var5 = 2.0F;
            break;
         case RIGHT_MIDDLE:
            var4 = var1.getScaledWidth() - var2;
            var5 = var1.getScaledHeight() / 2 - var3 / 2.0F;
            break;
         case RIGHT_BOTTOM:
            var4 = var1.getScaledWidth() - var2;
            var5 = var1.getScaledHeight() - var3;
      }

      GL11.glTranslatef(var4 / var6, var5 / var6, 0.0F);
      GL11.glTranslatef(this.xTranslation / var6, this.yTranslation / var6, 0.0F);
   }

   public float[] a(ScaledResolution var1, boolean var2) {
      float var3 = 0.0F;
      float var4 = 0.0F;
      float var5 = (Float)this.scale.d();
      float var6 = this.p() * var5;
      float var7 = this.height * var5;
      switch (this.guiAnchor) {
         case LEFT_TOP:
            var3 = 2.0F;
            var4 = 2.0F;
            break;
         case LEFT_MIDDLE:
            var3 = 2.0F;
            var4 = var1.getScaledHeight() / 2 - var7 / 2.0F;
            break;
         case LEFT_BOTTOM:
            var4 = var1.getScaledHeight() - var7 - 2.0F;
            var3 = 2.0F;
            break;
         case MIDDLE_TOP:
            var3 = var1.getScaledWidth() / 2 - var6 / 2.0F;
            var4 = 2.0F;
            break;
         case MIDDLE_MIDDLE:
            var3 = var1.getScaledWidth() / 2 - var6 / 2.0F;
            var4 = var1.getScaledHeight() / 2 - var7 / 2.0F;
            break;
         case MIDDLE_BOTTOM_LEFT:
            var3 = var1.getScaledWidth() / 2 - var6;
            var4 = var1.getScaledHeight() - var7 - 2.0F;
            break;
         case MIDDLE_BOTTOM_RIGHT:
            var3 = var1.getScaledWidth() / 2.0F;
            var4 = var1.getScaledHeight() - var7 - 2.0F;
            break;
         case RIGHT_TOP:
            var3 = var1.getScaledWidth() - var6 - 2.0F;
            var4 = 2.0F;
            break;
         case RIGHT_MIDDLE:
            var3 = var1.getScaledWidth() - var6;
            var4 = var1.getScaledHeight() / 2 - var7 / 2.0F;
            break;
         case RIGHT_BOTTOM:
            var3 = var1.getScaledWidth() - var6;
            var4 = var1.getScaledHeight() - var7;
      }

      return new float[]{(var3 + (var2 ? this.xTranslation : 0.0F)) / var5, (var4 + (var2 ? this.yTranslation : 0.0F)) / var5};
   }

   public boolean q() {
      return this.renderHud;
   }

   public void f(boolean var1) {
      if (var1 != this.defaultRenderHud) {
         com.cheatbreaker.client.CheatBreakerClient.N().c();
      }

      this.renderHud = var1;
   }

   public com.cheatbreaker.client.gui.hudeditor.GuiAnchor r() {
      return this.guiAnchor;
   }

   public void a(com.cheatbreaker.client.gui.hudeditor.GuiAnchor var1) {
      if (var1 != this.defaultGuiAnchor) {
         com.cheatbreaker.client.CheatBreakerClient.N().c();
      }

      this.guiAnchor = var1;
   }

   public void b(com.cheatbreaker.client.gui.hudeditor.GuiAnchor var1) {
      this.guiAnchor = var1;
      this.defaultGuiAnchor = var1;
   }

   public com.cheatbreaker.client.gui.hudeditor.AnchorAlignment s() {
      return com.cheatbreaker.client.gui.hudeditor.AnchorHelper.c(this.guiAnchor);
   }

   protected void a(ResourceLocation var1, int var2, int var3) {
      this.previewType = com.cheatbreaker.client.module.Module.aInner.ICON;
      this.previewIconLocation = var1;
      this.previewIconWidth = var2;
      this.previewIconHeight = var3;
   }

   protected void a(String var1, float var2) {
      this.previewType = com.cheatbreaker.client.module.Module.aInner.LABEL;
      this.previewLabel = var1;
      this.previewLabelSize = var2;
   }

   public com.cheatbreaker.client.module.Module.aInner t() {
      return this.previewType;
   }

   public ResourceLocation u() {
      return this.previewIconLocation;
   }

   public float v() {
      return this.previewIconWidth;
   }

   public float w() {
      return this.previewIconHeight;
   }

   public String x() {
      return this.previewLabel;
   }

   public float y() {
      return this.previewLabelSize;
   }

   public void g(boolean var1) {
      this.renderHud = var1;
      this.defaultRenderHud = var1;
   }

   public static enum aInner {
      LABEL,
      ICON;
   }
}
