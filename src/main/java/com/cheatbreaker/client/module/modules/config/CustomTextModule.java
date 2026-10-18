package com.cheatbreaker.client.module.modules.config;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public final class CustomTextModule extends com.cheatbreaker.client.module.Module implements com.cheatbreaker.client.module.ModuleGroup {
   private static final ResourceLocation MISSING_IMAGE = new ResourceLocation("textures/misc/unknown_pack.png");
   private final com.cheatbreaker.client.module.modules.config.CustomTextRegistry state = new com.cheatbreaker.client.module.modules.config.CustomTextRegistry();
   private final com.cheatbreaker.client.module.modules.config.CustomTextImageCache imageCache = new com.cheatbreaker.client.module.modules.config.CustomTextImageCache();
   private final Map<String, CustomTextModule.a> childUnits = new LinkedHashMap<>();
   private boolean syncing;

   public CustomTextModule() {
      super("Custom Text");
      this.b(com.cheatbreaker.client.gui.hudeditor.GuiAnchor.LEFT_TOP);
      this.b(0.0F, 24.0F);
      this.c(true);
      this.m().removeIf(var0 -> "Scale".equals(var0.f()));
      com.cheatbreaker.client.module.modules.config.CustomTextItem var1 = this.state.a();
      var1.a("Kazer Client");
      var1.anchor = "LEFT_TOP";
      var1.yTranslation = 24.0F;
      this.childUnits.put(var1.id, new CustomTextModule.a(var1));
      this.a("Text", 1.5F);
      this.a(com.cheatbreaker.client.event.event.RenderHudEvent.class, this::a);
      this.a(com.cheatbreaker.client.event.event.RenderHudEditorEvent.class, this::a);
   }

   public com.cheatbreaker.client.module.modules.config.CustomTextRegistry z() {
      return this.state;
   }

   public int a(com.cheatbreaker.client.module.modules.config.CustomTextItem var1) {
      return this.state.items.indexOf(var1);
   }

   public int b(com.cheatbreaker.client.module.modules.config.CustomTextItem var1) {
      return this.state.items.indexOf(var1);
   }

   public String c(com.cheatbreaker.client.module.modules.config.CustomTextItem var1) {
      return "Custom Text " + (this.b(var1) + 1) + ": " + var1.content;
   }

   private String d(com.cheatbreaker.client.module.modules.config.CustomTextItem var1) {
      int var2 = this.state.items.indexOf(var1);
      return var2 >= 0 ? String.valueOf(var2 + 1) : "?";
   }

   public com.cheatbreaker.client.module.modules.config.CustomTextImageCache A() {
      return this.imageCache;
   }

   public com.cheatbreaker.client.module.modules.config.CustomTextItem B() {
      com.cheatbreaker.client.module.modules.config.CustomTextItem var1 = this.state.a();
      if (var1 == null) {
         return null;
      } else {
         this.childUnits.put(var1.id, new CustomTextModule.a(var1));
         this.f(var1.content);
         return var1;
      }
   }

   public boolean a(String var1) {
      com.cheatbreaker.client.module.modules.config.CustomTextItem var2 = this.state.b(var1);
      if (var2 == null) {
         return false;
      } else {
         this.g(var2.content);
         this.state.a(var1);
         this.childUnits.remove(var1);
         return true;
      }
   }

   public void a(String var1, String var2) {
      com.cheatbreaker.client.module.modules.config.CustomTextItem var3 = this.state.b(var1);
      if (var3 != null) {
         String var4 = var3.content;
         var3.a(var2);
         if (!var4.equals(var3.content)) {
            this.g(var4);
            this.f(var3.content);
         }
      }
   }

   public List<com.cheatbreaker.client.settings.Setting> a(com.cheatbreaker.client.module.Module var1) {
      ArrayList var2 = new ArrayList();
      if (!(var1 instanceof CustomTextModule.a)) {
         return var2;
      } else {
         com.cheatbreaker.client.module.modules.config.CustomTextItem var3 = ((CustomTextModule.a)var1).z();
         var2.add(new com.cheatbreaker.client.settings.Setting("Content").a(var3.content).a(var2x -> this.a(var3.id, (String)var2x)));
         var2.add(new com.cheatbreaker.client.settings.Setting("Status").a(var3.enabled ? "ON" : "OFF").a("ON", "OFF").a(var2x -> this.a(var3.id, "ON".equals(var2x))));
         var2.add(
            new com.cheatbreaker.client.settings.Setting("Scale")
               .a(Float.valueOf(var3.scale))
               .a(0.5F, 1.5F)
               .a(var2x -> this.a(var3.id, var1xx -> var1xx.scale = ((Number)var2x).floatValue()))
         );
         var2.add(
            new com.cheatbreaker.client.settings.Setting("Show Background")
               .a(var3.showBackground ? "ON" : "OFF")
               .a("ON", "OFF")
               .a(var2x -> this.a(var3.id, var1xx -> var1xx.showBackground = "ON".equals(var2x)))
         );
         var2.add(
            new com.cheatbreaker.client.settings.Setting("Text Color")
               .a(var3.color)
               .a(Integer.MIN_VALUE, Integer.MAX_VALUE)
               .a(var2x -> this.a(var3.id, var1xx -> var1xx.color = (Integer)var2x))
         );
         var2.add(
            new com.cheatbreaker.client.settings.Setting("Background Color")
               .a(var3.backgroundColor)
               .a(Integer.MIN_VALUE, Integer.MAX_VALUE)
               .a(var2x -> this.a(var3.id, var1xx -> var1xx.backgroundColor = (Integer)var2x))
         );
         return var2;
      }
   }

   private void a(String var1, Consumer<com.cheatbreaker.client.module.modules.config.CustomTextItem> var2) {
      com.cheatbreaker.client.module.modules.config.CustomTextItem var3 = this.state.b(var1);
      if (var3 != null) {
         var2.accept(var3);
      }
   }

   public void a(String var1, boolean var2) {
      com.cheatbreaker.client.module.modules.config.CustomTextItem var3 = this.state.b(var1);
      if (var3 != null) {
         var3.enabled = var2;
         var3.renderHud = var2;
      }
   }

   public void a(String var1, int var2) {
      this.state.a(var1, var2);
   }

   private void f(String var1) {
      if (com.cheatbreaker.client.module.modules.config.CustomTextImageLoader.a(var1)) {
         this.imageCache.a(var1, System.currentTimeMillis());
      }
   }

   private void g(String var1) {
      if (com.cheatbreaker.client.module.modules.config.CustomTextImageLoader.a(var1)) {
         com.cheatbreaker.client.module.modules.config.CustomTextImageCache.aInner var2 = this.imageCache.a(var1);
         if (var2 != null && var2.textureId >= 0) {
            TextureUtil.deleteTexture(var2.textureId);
         }
      }
   }

   public String C() {
      return com.cheatbreaker.client.module.modules.config.CustomTextSerializer.a(this.state);
   }

   public void b(String var1) {
      for (com.cheatbreaker.client.module.modules.config.CustomTextItem var3 : this.state.items) {
         this.g(var3.content);
      }

      com.cheatbreaker.client.module.modules.config.CustomTextRegistry var5 = com.cheatbreaker.client.module.modules.config.CustomTextSerializer.a(var1);
      this.state.items.clear();
      this.state.items.addAll(var5.items);
      this.state.a(var5.b());
      this.childUnits.clear();

      for (com.cheatbreaker.client.module.modules.config.CustomTextItem var4 : this.state.items) {
         this.childUnits.put(var4.id, new CustomTextModule.a(var4));
         this.f(var4.content);
      }
   }

   public void D() {
      this.b("");
   }

   @Override
   public List<com.cheatbreaker.client.module.Module> a() {
      return new ArrayList<>(this.childUnits.values());
   }

   public CustomTextModule.a c(String var1) {
      return this.childUnits.get(var1);
   }

   private void a(com.cheatbreaker.client.event.event.RenderHudEvent var1) {
      if (this.q() && this.minecraft.thePlayer != null && !(this.minecraft.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.HudEditorScreen)) {
         if (this.minecraft.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.ModulePlacementScreen) {
            com.cheatbreaker.client.module.modules.config.CustomTextItem var2 = this.a(this.minecraft.currentScreen);
            if (var2 != null) {
               this.a(var1, var2);
            }
         } else {
            this.b(var1);
         }
      }
   }

   com.cheatbreaker.client.module.modules.config.CustomTextItem a(GuiScreen var1) {
      if (!(var1 instanceof com.cheatbreaker.client.gui.hudeditor.ModulePlacementScreen)) {
         return null;
      } else {
         com.cheatbreaker.client.module.Module var2 = ((com.cheatbreaker.client.gui.hudeditor.ModulePlacementScreen)var1).a();
         if (!(var2 instanceof CustomTextModule.a)) {
            return null;
         } else {
            CustomTextModule.a var3 = (CustomTextModule.a)var2;
            if (var3.a() != this) {
               return null;
            } else {
               com.cheatbreaker.client.module.modules.config.CustomTextItem var4 = var3.z();
               return var4.enabled && var4.renderHud ? var4 : null;
            }
         }
      }
   }

   private void a(com.cheatbreaker.client.event.event.RenderHudEditorEvent var1) {
      if (this.q() && this.minecraft.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.HudEditorScreen) {
         this.b(var1);
      }
   }

   private void b(com.cheatbreaker.client.event.event.RenderHudEvent var1) {
      for (int var2 = this.state.items.size() - 1; var2 >= 0; var2--) {
         com.cheatbreaker.client.module.modules.config.CustomTextItem var3 = this.state.items.get(var2);
         if (var3.enabled && var3.renderHud) {
            this.a(var1, var3);
         }
      }
   }

   private void a(com.cheatbreaker.client.event.event.RenderHudEvent var1, com.cheatbreaker.client.module.modules.config.CustomTextItem var2) {
      boolean var3 = com.cheatbreaker.client.module.modules.config.CustomTextImageLoader.a(var2.content);
      com.cheatbreaker.client.module.modules.config.CustomTextImageCache.aInner var6 = null;
      float var4;
      float var5;
      if (var3) {
         var6 = this.imageCache.c(var2.content);
         if (var6 == null) {
            var6 = this.e(var2);
         }

         BufferedImage var7 = this.imageCache.b(var2.content, System.currentTimeMillis());
         if (var7 != null) {
            var4 = var7.getWidth();
            var5 = var7.getHeight();
         } else {
            var4 = 16.0F;
            var5 = 16.0F;
         }
      } else {
         var4 = this.minecraft.fontRendererObj.getStringWidth(var2.content);
         var5 = this.minecraft.fontRendererObj.FONT_HEIGHT;
      }

      CustomTextModule.a var16 = this.childUnits.get(var2.id);
      this.c(var4, var5);
      if (var16 != null) {
         var16.width = var4;
         var16.height = var5;
         var16.A();
      }

      this.syncing = true;

      try {
         super.a(h(var2.anchor));
         super.a(var2.xTranslation, var2.yTranslation);
         this.scale.a(var2.scale, false);
      } finally {
         this.syncing = false;
      }

      GL11.glPushMatrix();

      try {
         super.a(var1.a());
         if (!var3) {
            if (var2.showBackground) {
               cn.kazer.client189.render.DrawRect.a(-2.0F, -2.0F, var4 + 2.0F, var5 + 2.0F, var2.backgroundColor);
               this.minecraft.fontRendererObj.drawString(var2.content, 0, 0, var2.color);
            } else {
               this.minecraft.fontRendererObj.drawStringWithShadow(var2.content, 0.0F, 0.0F, var2.color);
            }
         } else if (var6 != null && var6.image != null) {
            if (var6.textureId < 0) {
               var6.textureId = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), var6.image, false, false);
            }

            cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
            cn.kazer.client189.render.GlStateBridge.a(3042);
            cn.kazer.client189.render.GlStateBridge.b(770, 771);
            cn.kazer.client189.render.GlStateBridge.a(3553, var6.textureId);
            Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, (int)var4, (int)var5, var4, var5);
         } else {
            cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
            cn.kazer.client189.render.GlStateBridge.a(3042);
            cn.kazer.client189.render.GlStateBridge.b(770, 771);
            this.minecraft.getTextureManager().bindTexture(MISSING_IMAGE);
            Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, 16, 16, 16.0F, 16.0F);
         }
      } finally {
         GL11.glPopMatrix();
      }
   }

   private com.cheatbreaker.client.module.modules.config.CustomTextImageCache.aInner e(com.cheatbreaker.client.module.modules.config.CustomTextItem var1) {
      this.imageCache.a(var1.content, System.currentTimeMillis());
      return this.imageCache.c(var1.content);
   }

   private static com.cheatbreaker.client.gui.hudeditor.GuiAnchor h(String var0) {
      try {
         return com.cheatbreaker.client.gui.hudeditor.GuiAnchor.valueOf(var0);
      } catch (Exception var2) {
         return com.cheatbreaker.client.gui.hudeditor.GuiAnchor.MIDDLE_MIDDLE;
      }
   }

   private static String i(String var0) {
      return var0.length() <= 12 ? var0 : var0.substring(0, 12) + "...";
   }

   public final class a extends com.cheatbreaker.client.module.Module implements com.cheatbreaker.client.module.ModuleProxy {
      private final com.cheatbreaker.client.module.modules.config.CustomTextItem item;
      private CustomTextModule.b onPlacementSettled;

      a(com.cheatbreaker.client.module.modules.config.CustomTextItem var2) {
         super("Custom Text " + CustomTextModule.this.d(var2) + ": " + CustomTextModule.i(var2.content));
         this.item = var2;
         this.isEditable = true;
         this.scale.a(var2.scale, false);
         this.scale.a(var1x -> {
            if (!CustomTextModule.this.syncing) {
               this.item.scale = (Float)var1x;
            }
         });
      }

      @Override
      public String h() {
         return CustomTextModule.this.c(this.item);
      }

      public com.cheatbreaker.client.module.modules.config.CustomTextItem z() {
         return this.item;
      }

      void A() {
         this.scale.a(this.item.scale, false);
      }

      @Override
      public com.cheatbreaker.client.module.Module a() {
         return CustomTextModule.this;
      }

      @Override
      public com.cheatbreaker.client.gui.hudeditor.GuiAnchor r() {
         return CustomTextModule.h(this.item.anchor);
      }

      @Override
      public void a(com.cheatbreaker.client.gui.hudeditor.GuiAnchor var1) {
         this.item.anchor = var1 == null ? com.cheatbreaker.client.gui.hudeditor.GuiAnchor.MIDDLE_MIDDLE.name() : var1.name();
      }

      @Override
      public com.cheatbreaker.client.gui.hudeditor.AnchorAlignment s() {
         return com.cheatbreaker.client.gui.hudeditor.AnchorHelper.c(this.r());
      }

      @Override
      public float k() {
         return this.item.xTranslation;
      }

      @Override
      public float l() {
         return this.item.yTranslation;
      }

      @Override
      public void a(float var1, float var2) {
         this.item.xTranslation = var1;
         this.item.yTranslation = var2;
      }

      @Override
      public void a(float var1) {
         this.item.xTranslation = var1;
      }

      @Override
      public void b(float var1) {
         this.item.yTranslation = var1;
      }

      @Override
      public boolean g() {
         return CustomTextModule.this.q() && this.item.enabled;
      }

      @Override
      public boolean q() {
         return CustomTextModule.this.q() && this.item.renderHud;
      }

      @Override
      public void f(boolean var1) {
         this.item.renderHud = var1;
      }

      @Override
      public void a(boolean var1) {
         this.item.enabled = var1;
         this.item.renderHud = var1;
      }

      @Override
      public float[] a(ScaledResolution var1, boolean var2) {
         float var3 = (Float)this.scale.d();
         float var4 = this.width * var3;
         float var5 = this.height * var3;
         com.cheatbreaker.client.gui.hudeditor.GuiAnchor var6 = this.r();
         float var7;
         float var8;
         switch (var6) {
            case LEFT_TOP:
            default:
               var7 = 2.0F;
               var8 = 2.0F;
               break;
            case LEFT_MIDDLE:
               var7 = 2.0F;
               var8 = var1.getScaledHeight() / 2.0F - var5 / 2.0F;
               break;
            case LEFT_BOTTOM:
               var7 = 2.0F;
               var8 = var1.getScaledHeight() - var5 - 2.0F;
               break;
            case MIDDLE_TOP:
               var7 = var1.getScaledWidth() / 2.0F - var4 / 2.0F;
               var8 = 2.0F;
               break;
            case MIDDLE_MIDDLE:
               var7 = var1.getScaledWidth() / 2.0F - var4 / 2.0F;
               var8 = var1.getScaledHeight() / 2.0F - var5 / 2.0F;
               break;
            case MIDDLE_BOTTOM_LEFT:
               var7 = var1.getScaledWidth() / 2.0F - var4;
               var8 = var1.getScaledHeight() - var5 - 2.0F;
               break;
            case MIDDLE_BOTTOM_RIGHT:
               var7 = var1.getScaledWidth() / 2.0F;
               var8 = var1.getScaledHeight() - var5 - 2.0F;
               break;
            case RIGHT_TOP:
               var7 = var1.getScaledWidth() - var4 - 2.0F;
               var8 = 2.0F;
               break;
            case RIGHT_MIDDLE:
               var7 = var1.getScaledWidth() - var4;
               var8 = var1.getScaledHeight() / 2.0F - var5 / 2.0F;
               break;
            case RIGHT_BOTTOM:
               var7 = var1.getScaledWidth() - var4;
               var8 = var1.getScaledHeight() - var5 - 2.0F;
         }

         return new float[]{(var7 + (var2 ? this.k() : 0.0F)) / var3, (var8 + (var2 ? this.l() : 0.0F)) / var3};
      }

      @Override
      public void a(ScaledResolution var1) {
         this.a(var1, this.width, this.height);
      }

      @Override
      public void a(ScaledResolution var1, float var2, float var3) {
         float var4 = (Float)this.scale.d();
         GL11.glScalef(var4, var4, var4);
         float var5 = var2 * var4;
         float var6 = var3 * var4;
         com.cheatbreaker.client.gui.hudeditor.GuiAnchor var7 = this.r();
         float var8;
         float var9;
         switch (var7) {
            case LEFT_TOP:
            default:
               var8 = 2.0F;
               var9 = 2.0F;
               break;
            case LEFT_MIDDLE:
               var8 = 2.0F;
               var9 = var1.getScaledHeight() / 2.0F - var6 / 2.0F;
               break;
            case LEFT_BOTTOM:
               var8 = 2.0F;
               var9 = var1.getScaledHeight() - var6 - 2.0F;
               break;
            case MIDDLE_TOP:
               var8 = var1.getScaledWidth() / 2.0F - var5 / 2.0F;
               var9 = 2.0F;
               break;
            case MIDDLE_MIDDLE:
               var8 = var1.getScaledWidth() / 2.0F - var5 / 2.0F;
               var9 = var1.getScaledHeight() / 2.0F - var6 / 2.0F;
               break;
            case MIDDLE_BOTTOM_LEFT:
               var8 = var1.getScaledWidth() / 2.0F - var5;
               var9 = var1.getScaledHeight() - var6 - 2.0F;
               break;
            case MIDDLE_BOTTOM_RIGHT:
               var8 = var1.getScaledWidth() / 2.0F;
               var9 = var1.getScaledHeight() - var6 - 2.0F;
               break;
            case RIGHT_TOP:
               var8 = var1.getScaledWidth() - var5 - 2.0F;
               var9 = 2.0F;
               break;
            case RIGHT_MIDDLE:
               var8 = var1.getScaledWidth() - var5;
               var9 = var1.getScaledHeight() / 2.0F - var6 / 2.0F;
               break;
            case RIGHT_BOTTOM:
               var8 = var1.getScaledWidth() - var5;
               var9 = var1.getScaledHeight() - var6 - 2.0F;
         }

         GL11.glTranslatef(var8 / var4, var9 / var4, 0.0F);
         GL11.glTranslatef(this.k() / var4, this.l() / var4, 0.0F);
      }

      void B() {
         this.onPlacementSettled = null;
      }

      void C() {
         if (this.onPlacementSettled != null) {
            this.onPlacementSettled.a();
            this.onPlacementSettled = null;
         }
      }

      public void a(CustomTextModule.b var1) {
         this.onPlacementSettled = var1;
      }
   }

   public interface b {
      void a();
   }
}
