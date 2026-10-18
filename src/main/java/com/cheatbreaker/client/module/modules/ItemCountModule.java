package com.cheatbreaker.client.module.modules;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public final class ItemCountModule extends com.cheatbreaker.client.module.Module implements com.cheatbreaker.client.module.ModuleGroup {
   private static final int POT_DAMAGE = 24581;
   private static final int ENCHANTED_APPLE_DAMAGE = 1;
   private final com.cheatbreaker.client.module.modules.ItemDisplayConfig state = new com.cheatbreaker.client.module.modules.ItemDisplayConfig();
   private com.cheatbreaker.client.module.modules.TrackableItem activeType = com.cheatbreaker.client.module.modules.TrackableItem.HEALING_POT;
   private boolean syncing;
   private final cn.kazer.client189.render.RenderItemBridge renderItem = new cn.kazer.client189.render.RenderItemBridge();
   private final List<com.cheatbreaker.client.module.Module> childUnits = new ArrayList<>();

   public ItemCountModule() {
      super("Items Counter");
      this.b(com.cheatbreaker.client.gui.hudeditor.GuiAnchor.RIGHT_TOP);
      this.b(-1.0F, 11.0F);
      this.c(true);

      for (com.cheatbreaker.client.module.modules.TrackableItem var4 : com.cheatbreaker.client.module.modules.TrackableItem.values()) {
         this.j(var4);
         this.childUnits.add(new com.cheatbreaker.client.module.modules.ItemCountModule.a(var4));
      }

      this.m().removeIf(var0 -> "Scale".equals(var0.f()));
      this.a("0 Pot", 1.5F);
      this.scale.a(var1 -> {
         if (!this.syncing) {
            this.k(this.activeType).scale = ((Number)var1).floatValue();
         }
      });
      this.a(com.cheatbreaker.client.event.event.RenderHudEvent.class, this::a);
      this.a(com.cheatbreaker.client.event.event.RenderHudEditorEvent.class, this::a);
      this.G();
   }

   private void j(com.cheatbreaker.client.module.modules.TrackableItem var1) {
      com.cheatbreaker.client.settings.Setting var2 = new com.cheatbreaker.client.settings.Setting(this, g(var1))
         .a(var1 == com.cheatbreaker.client.module.modules.TrackableItem.HEALING_POT)
         .displayLabel(a(g(var1), var1))
         .a(var2x -> {
            boolean var3x = (Boolean)var2x;
            com.cheatbreaker.client.module.modules.ItemDisplayEntry var4x = this.k(var1);
            boolean var5 = var4x.enabled;
            var4x.enabled = var3x;
            if (var3x && !var5) {
               var4x.renderHud = true;
            }
         });
      com.cheatbreaker.client.settings.Setting var3 = new com.cheatbreaker.client.settings.Setting(this, o(var1))
         .a(com.cheatbreaker.client.module.modules.ItemLabelUtil.a(var1).name())
         .displayLabel(a(o(var1), var1))
         .a(com.cheatbreaker.client.module.modules.ItemDisplayMode.ICON.name(), com.cheatbreaker.client.module.modules.ItemDisplayMode.CLASSIC.name())
         .a(var2);
      new com.cheatbreaker.client.settings.Setting(this, p(var1))
         .a(Float.valueOf(this.k(var1).scale))
         .displayLabel(a(p(var1), var1))
         .a(0.5F, 1.5F)
         .a(var2)
         .a(var2x -> this.k(var1).scale = ((Number)var2x).floatValue());
      com.cheatbreaker.client.settings.Setting var4 = new com.cheatbreaker.client.settings.Setting(this, l(var1))
         .a(this.k(var1).showBackground != null && this.k(var1).showBackground)
         .displayLabel(a(l(var1), var1))
         .a(var2)
         .a(var2x -> this.k(var1).showBackground = (Boolean)var2x);
      new com.cheatbreaker.client.settings.Setting(this, m(var1))
         .a(this.k(var1).textColor == null ? -1 : this.k(var1).textColor)
         .displayLabel(a(m(var1), var1))
         .a(Integer.MIN_VALUE, Integer.MAX_VALUE)
         .a(var2)
         .a(var2x -> this.k(var1).textColor = (Integer)var2x);
      new com.cheatbreaker.client.settings.Setting(this, n(var1))
         .a(this.k(var1).backgroundColor == null ? 1862270976 : this.k(var1).backgroundColor)
         .displayLabel(a(n(var1), var1))
         .a(Integer.MIN_VALUE, Integer.MAX_VALUE)
         .a(var4)
         .a(var2x -> this.k(var1).backgroundColor = (Integer)var2x);
      var3.a(var2x -> this.k(var1).displayMode = String.valueOf(var2x));
      this.k(var1).displayMode = com.cheatbreaker.client.module.modules.ItemLabelUtil.a(var1).name();
   }

   public com.cheatbreaker.client.module.modules.ItemDisplayConfig z() {
      return this.state;
   }

   @Override
   public List<com.cheatbreaker.client.module.Module> a() {
      return this.childUnits;
   }

   public com.cheatbreaker.client.module.modules.TrackableItem A() {
      return this.activeType;
   }

   public com.cheatbreaker.client.module.modules.TrackableItem B() {
      return com.cheatbreaker.client.module.modules.TrackableItem.HEALING_POT;
   }

   public void a(com.cheatbreaker.client.module.modules.TrackableItem var1) {
      if (var1 != null) {
         this.activeType = var1;
         this.G();
      }
   }

   public boolean b(com.cheatbreaker.client.module.modules.TrackableItem var1) {
      return this.k(var1).enabled;
   }

   public void a(com.cheatbreaker.client.module.modules.TrackableItem var1, boolean var2) {
      com.cheatbreaker.client.module.modules.ItemDisplayEntry var3 = this.k(var1);
      boolean var4 = var3.enabled;
      var3.enabled = var2;
      if (var2 && !var4) {
         var3.renderHud = true;
      }

      for (com.cheatbreaker.client.settings.Setting var6 : this.m()) {
         if (var6.f().equalsIgnoreCase(g(var1))) {
            var6.a(var2, false);
         }
      }
   }

   public boolean c(com.cheatbreaker.client.module.modules.TrackableItem var1) {
      return this.k(var1).renderHud;
   }

   public void b(com.cheatbreaker.client.module.modules.TrackableItem var1, boolean var2) {
      this.k(var1).renderHud = var2;
   }

   public com.cheatbreaker.client.module.modules.ItemDisplayMode d(com.cheatbreaker.client.module.modules.TrackableItem var1) {
      com.cheatbreaker.client.module.modules.ItemDisplayEntry var2 = this.k(var1);

      try {
         return com.cheatbreaker.client.module.modules.ItemDisplayMode.valueOf(var2.displayMode);
      } catch (Exception var4) {
         return com.cheatbreaker.client.module.modules.ItemLabelUtil.a(var1);
      }
   }

   public void a(com.cheatbreaker.client.module.modules.TrackableItem var1, com.cheatbreaker.client.module.modules.ItemDisplayMode var2) {
      this.k(var1).displayMode = var2.name();

      for (com.cheatbreaker.client.settings.Setting var4 : this.m()) {
         if (var4.f().equalsIgnoreCase(o(var1))) {
            var4.a(var2.name(), false);
         }
      }
   }

   public float e(com.cheatbreaker.client.module.modules.TrackableItem var1) {
      return this.k(var1).scale;
   }

   public void a(com.cheatbreaker.client.module.modules.TrackableItem var1, float var2) {
      com.cheatbreaker.client.module.modules.ItemDisplayEntry var3 = this.k(var1);
      var3.scale = var2;

      for (com.cheatbreaker.client.settings.Setting var5 : this.m()) {
         if (var5.f().equalsIgnoreCase(p(var1))) {
            var5.a(var2, false);
         }
      }
   }

   public String C() {
      return com.cheatbreaker.client.module.modules.ItemDisplaySerializer.a(this.state);
   }

   public void a(String var1) {
      com.cheatbreaker.client.module.modules.ItemDisplayConfig var2 = com.cheatbreaker.client.module.modules.ItemDisplaySerializer.a(var1);
      this.state.children = var2.children;
      this.F();
      this.G();
   }

   public void D() {
      this.state.children = com.cheatbreaker.client.module.modules.ItemDisplayConfig.a();
      this.activeType = com.cheatbreaker.client.module.modules.TrackableItem.HEALING_POT;
      this.F();
      this.G();
   }

   private void F() {
      for (com.cheatbreaker.client.settings.Setting var2 : this.m()) {
         for (com.cheatbreaker.client.module.modules.TrackableItem var6 : com.cheatbreaker.client.module.modules.TrackableItem.values()) {
            if (var2.f().equalsIgnoreCase(g(var6))) {
               var2.a(this.k(var6).enabled, false);
            } else if (var2.f().equalsIgnoreCase(o(var6))) {
               var2.a(this.k(var6).displayMode, false);
            } else if (var2.f().equalsIgnoreCase(p(var6))) {
               var2.a(this.k(var6).scale, false);
            } else if (var2.f().equalsIgnoreCase(l(var6))) {
               boolean var7 = this.k(var6).showBackground != null && this.k(var6).showBackground;
               var2.a(var7, false);
            } else if (var2.f().equalsIgnoreCase(m(var6))) {
               var2.a(this.k(var6).textColor == null ? -1 : this.k(var6).textColor, false);
            } else if (var2.f().equalsIgnoreCase(n(var6))) {
               var2.a(this.k(var6).backgroundColor == null ? 1862270976 : this.k(var6).backgroundColor, false);
            }
         }
      }
   }

   private com.cheatbreaker.client.module.modules.ItemDisplayEntry k(com.cheatbreaker.client.module.modules.TrackableItem var1) {
      for (com.cheatbreaker.client.module.modules.ItemDisplayEntry var3 : this.state.children) {
         if (var1.a().equalsIgnoreCase(var3.id)) {
            return var3;
         }
      }

      com.cheatbreaker.client.module.modules.ItemDisplayEntry var4 = new com.cheatbreaker.client.module.modules.ItemDisplayEntry(var1);
      this.state.children.add(var4);
      return var4;
   }

   public List<com.cheatbreaker.client.settings.Setting> f(com.cheatbreaker.client.module.modules.TrackableItem var1) {
      ArrayList var2 = new ArrayList();

      for (com.cheatbreaker.client.settings.Setting var4 : this.m()) {
         String var5 = var4.f();
         if (var5.equalsIgnoreCase(g(var1))
            || var5.equalsIgnoreCase(o(var1))
            || var5.equalsIgnoreCase(p(var1))
            || var5.equalsIgnoreCase(l(var1))
            || var5.equalsIgnoreCase(m(var1))
            || var5.equalsIgnoreCase(n(var1))) {
            var2.add(var4);
         }
      }

      return var2;
   }

   public static String g(com.cheatbreaker.client.module.modules.TrackableItem var0) {
      switch (var0) {
         case HEALING_POT:
            return "Potion";
         case MUSHROOM_SOUP:
            return "Soup";
         case ARROW:
            return "Arrow";
         case GOLDEN_APPLE:
            return "Gapple";
         case ENCHANTED_GOLDEN_APPLE:
            return "Enchanted Gapple";
         default:
            return var0.name();
      }
   }

   static String a(String var0, com.cheatbreaker.client.module.modules.TrackableItem var1) {
      if (var0.equalsIgnoreCase(g(var1))) {
         return "Status";
      } else {
         String var2 = g(var1) + " ";
         if (var0.regionMatches(true, 0, var2, 0, var2.length())) {
            String var3 = var0.substring(var2.length()).trim();
            if (!var3.isEmpty()) {
               return var3;
            }
         }

         return var0;
      }
   }

   private static String l(com.cheatbreaker.client.module.modules.TrackableItem var0) {
      return g(var0) + " Show Background";
   }

   private static String m(com.cheatbreaker.client.module.modules.TrackableItem var0) {
      return g(var0) + " Text Color";
   }

   private static String n(com.cheatbreaker.client.module.modules.TrackableItem var0) {
      return g(var0) + " Background Color";
   }

   private static String o(com.cheatbreaker.client.module.modules.TrackableItem var0) {
      return g(var0) + " Display Mode";
   }

   private static String p(com.cheatbreaker.client.module.modules.TrackableItem var0) {
      return g(var0) + " Scale";
   }

   private void G() {
      if (!this.syncing) {
         com.cheatbreaker.client.module.modules.ItemDisplayEntry var1 = this.k(this.activeType);
         this.syncing = true;

         try {
            try {
               super.a(com.cheatbreaker.client.gui.hudeditor.GuiAnchor.valueOf(var1.anchor));
            } catch (Exception var6) {
               super.a(this.defaultGuiAnchor);
               var1.anchor = this.defaultGuiAnchor.name();
            }

            super.a(var1.xTranslation, var1.yTranslation);
            this.scale.a(var1.scale, false);
         } finally {
            this.syncing = false;
         }
      }
   }

   @Override
   public void f(boolean var1) {
      super.f(var1);
      if (var1) {
         this.E();
      }
   }

   void E() {
      for (com.cheatbreaker.client.module.modules.ItemDisplayEntry var2 : this.state.children) {
         if (var2.enabled && !var2.renderHud) {
            var2.renderHud = true;
         }
      }
   }

   @Override
   public void a(com.cheatbreaker.client.gui.hudeditor.GuiAnchor var1) {
      super.a(var1);
      if (!this.syncing && var1 != null) {
         this.k(this.activeType).anchor = var1.name();
      }
   }

   @Override
   public void a(float var1, float var2) {
      super.a(var1, var2);
      if (!this.syncing) {
         com.cheatbreaker.client.module.modules.ItemDisplayEntry var3 = this.k(this.activeType);
         var3.xTranslation = var1;
         var3.yTranslation = var2;
      }
   }

   @Override
   public void a(float var1) {
      super.a(var1);
      if (!this.syncing) {
         this.k(this.activeType).xTranslation = var1;
      }
   }

   @Override
   public void b(float var1) {
      super.b(var1);
      if (!this.syncing) {
         this.k(this.activeType).yTranslation = var1;
      }
   }

   private void a(com.cheatbreaker.client.event.event.RenderHudEvent var1) {
      if (this.q()
         && this.minecraft.thePlayer != null
         && !(this.minecraft.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.HudEditorScreen)
         && !(this.minecraft.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.ModulePlacementScreen)) {
         this.a(var1, this.minecraft.thePlayer.inventory.mainInventory, false);
      }
   }

   private void a(com.cheatbreaker.client.event.event.RenderHudEditorEvent var1) {
      if (this.q()) {
         boolean var2 = this.minecraft.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.ModulePlacementScreen;
         if (var2 || this.minecraft.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.HudEditorScreen) {
            ItemStack[] var3 = this.minecraft.thePlayer == null ? new ItemStack[0] : this.minecraft.thePlayer.inventory.mainInventory;
            if (var2) {
               this.a(var1, this.k(this.B()), var3, true);
            } else {
               this.a(var1, var3, true);
            }
         }
      }
   }

   private void a(com.cheatbreaker.client.event.event.RenderHudEvent var1, ItemStack[] var2, boolean var3) {
      for (com.cheatbreaker.client.module.modules.TrackableItem var7 : com.cheatbreaker.client.module.modules.TrackableItem.values()) {
         com.cheatbreaker.client.module.modules.ItemDisplayEntry var8 = this.k(var7);
         if (var8.enabled && var8.renderHud) {
            this.a(var1, var8, var2, var3);
         }
      }

      this.G();
   }

   private void a(com.cheatbreaker.client.event.event.RenderHudEvent var1, com.cheatbreaker.client.module.modules.ItemDisplayEntry var2, ItemStack[] var3, boolean var4) {
      com.cheatbreaker.client.module.modules.TrackableItem var5 = com.cheatbreaker.client.module.modules.TrackableItem.a(var2.id);
      if (var5 != null) {
         int var6 = com.cheatbreaker.client.module.modules.ItemCounterUtil.a(var5, var3);
         com.cheatbreaker.client.module.modules.ItemDisplayMode var7 = this.d(var5);
         String var8 = var7 == com.cheatbreaker.client.module.modules.ItemDisplayMode.CLASSIC ? com.cheatbreaker.client.module.modules.ItemLabelUtil.a(var5, var6) : null;
         String var9 = var7 == com.cheatbreaker.client.module.modules.ItemDisplayMode.ICON ? com.cheatbreaker.client.module.modules.ItemLabelUtil.a(var6) : null;
         int var10 = var8 == null ? 0 : this.minecraft.fontRendererObj.getStringWidth(var8);
         boolean var11 = var2.showBackground != null && var2.showBackground;
         float[] var12 = com.cheatbreaker.client.module.modules.ItemLabelUtil.a(var7, 16, var10, this.minecraft.fontRendererObj.FONT_HEIGHT, var11);
         this.syncing = true;

         try {
            super.a(b(var2.anchor));
            super.a(var2.xTranslation, var2.yTranslation);
            this.scale.a(var2.scale, false);
            this.c(var12[0], var12[1]);
            com.cheatbreaker.client.module.modules.ItemCountModule.a var13 = this.q(var5);
            if (var13 != null) {
               var13.z();
               var13.width = var12[0];
               var13.height = var12[1];
            }

            boolean var14 = GL11.glIsEnabled(2929);
            boolean var15 = GL11.glIsEnabled(3042);
            boolean var16 = GL11.glIsEnabled(2896);
            boolean var17 = GL11.glIsEnabled(32826);
            GL11.glPushMatrix();

            try {
               super.a(var1.a());
               if (var7 == com.cheatbreaker.client.module.modules.ItemDisplayMode.CLASSIC) {
                  int var18 = var2.textColor == null ? -1 : var2.textColor;
                  if (var11) {
                     int var19 = var2.backgroundColor == null ? 1862270976 : var2.backgroundColor;
                     cn.kazer.client189.render.DrawRect.a(0.0F, 0.0F, var12[0], var12[1], var19);
                     this.minecraft.fontRendererObj.drawString(var8, 2, 3, var18);
                  } else {
                     this.minecraft.fontRendererObj.drawStringWithShadow(var8, 0.0F, 0.0F, var18);
                  }
               } else {
                  if (var11) {
                     int var28 = var2.backgroundColor == null ? 1862270976 : var2.backgroundColor;
                     boolean var30 = GL11.glGetBoolean(2930);
                     cn.kazer.client189.render.GlStateBridge.a(false);
                     cn.kazer.client189.render.DrawRect.a(0.0F, 0.0F, 16.0F, 18.0F, var28);
                     cn.kazer.client189.render.GlStateBridge.a(var30);
                  }

                  cn.kazer.client189.render.GlStateBridge.a(2929);
                  cn.kazer.client189.render.GlStateBridge.a(true);
                  cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
                  RenderHelper.enableGUIStandardItemLighting();
                  this.renderItem.a(this.minecraft.fontRendererObj, this.minecraft.getTextureManager(), h(var5), 0, 0);
                  RenderHelper.disableStandardItemLighting();
                  cn.kazer.client189.render.GlStateBridge.b(2896);
                  cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
                  cn.kazer.client189.render.GlStateBridge.b(2929);
                  int var29 = var2.textColor == null ? -1 : var2.textColor;
                  int var31 = this.minecraft.fontRendererObj.getStringWidth(var9);
                  this.minecraft.fontRendererObj.drawStringWithShadow(var9, 17 - var31, 9.0F, var29);
               }
            } finally {
               GL11.glPopMatrix();
               if (var14) {
                  cn.kazer.client189.render.GlStateBridge.a(2929);
               } else {
                  cn.kazer.client189.render.GlStateBridge.b(2929);
               }

               if (var15) {
                  cn.kazer.client189.render.GlStateBridge.a(3042);
               } else {
                  cn.kazer.client189.render.GlStateBridge.b(3042);
               }

               if (var16) {
                  cn.kazer.client189.render.GlStateBridge.a(2896);
               } else {
                  cn.kazer.client189.render.GlStateBridge.b(2896);
               }

               if (var17) {
                  cn.kazer.client189.render.GlStateBridge.a(32826);
               } else {
                  cn.kazer.client189.render.GlStateBridge.b(32826);
               }
            }
         } finally {
            this.syncing = false;
         }
      }
   }

   public static ItemStack h(com.cheatbreaker.client.module.modules.TrackableItem var0) {
      switch (var0) {
         case HEALING_POT:
            return new ItemStack(Items.potionitem, 1, 24581);
         case MUSHROOM_SOUP:
            return new ItemStack(Items.mushroom_stew);
         case ARROW:
            return new ItemStack(Items.arrow);
         case GOLDEN_APPLE:
            return new ItemStack(Items.golden_apple, 1, 0);
         case ENCHANTED_GOLDEN_APPLE:
            return new ItemStack(Items.golden_apple, 1, 1);
         default:
            return new ItemStack(Items.arrow);
      }
   }

   public com.cheatbreaker.client.module.Module i(com.cheatbreaker.client.module.modules.TrackableItem var1) {
      return this.q(var1);
   }

   private com.cheatbreaker.client.module.modules.ItemCountModule.a q(com.cheatbreaker.client.module.modules.TrackableItem var1) {
      for (com.cheatbreaker.client.module.Module var3 : this.childUnits) {
         if (var3 instanceof com.cheatbreaker.client.module.modules.ItemCountModule.a && ((com.cheatbreaker.client.module.modules.ItemCountModule.a)var3).type == var1) {
            return (com.cheatbreaker.client.module.modules.ItemCountModule.a)var3;
         }
      }

      return null;
   }

   private static com.cheatbreaker.client.gui.hudeditor.GuiAnchor b(String var0) {
      try {
         return com.cheatbreaker.client.gui.hudeditor.GuiAnchor.valueOf(var0);
      } catch (Exception var2) {
         return com.cheatbreaker.client.gui.hudeditor.GuiAnchor.LEFT_TOP;
      }
   }

   public List<com.cheatbreaker.client.settings.Setting> a(com.cheatbreaker.client.module.Module var1) {
      return var1 instanceof com.cheatbreaker.client.module.modules.ItemCountModule.a ? this.f(((com.cheatbreaker.client.module.modules.ItemCountModule.a)var1).type) : null;
   }

   private final class a extends com.cheatbreaker.client.module.Module implements com.cheatbreaker.client.module.ModuleProxy {
      private final com.cheatbreaker.client.module.modules.TrackableItem type;

      a(com.cheatbreaker.client.module.modules.TrackableItem var2) {
         super("Items Counter: " + com.cheatbreaker.client.module.modules.ItemCountModule.g(var2));
         this.type = var2;
         this.isEditable = true;
         this.scale.a(this.A().scale, false);
         this.scale.a(var1x -> this.A().scale = ((Number)var1x).floatValue());
      }

      @Override
      public com.cheatbreaker.client.module.Module a() {
         return ItemCountModule.this;
      }

      @Override
      public List<com.cheatbreaker.client.settings.Setting> m() {
         return this.type == null ? super.m() : ItemCountModule.this.f(this.type);
      }

      void z() {
         this.scale.a(this.A().scale, false);
      }

      private com.cheatbreaker.client.module.modules.ItemDisplayEntry A() {
         return ItemCountModule.this.k(this.type);
      }

      @Override
      public com.cheatbreaker.client.gui.hudeditor.GuiAnchor r() {
         try {
            return com.cheatbreaker.client.gui.hudeditor.GuiAnchor.valueOf(this.A().anchor);
         } catch (Exception var2) {
            return com.cheatbreaker.client.gui.hudeditor.GuiAnchor.LEFT_TOP;
         }
      }

      @Override
      public void a(com.cheatbreaker.client.gui.hudeditor.GuiAnchor var1) {
         this.A().anchor = var1 == null ? com.cheatbreaker.client.gui.hudeditor.GuiAnchor.LEFT_TOP.name() : var1.name();
      }

      @Override
      public com.cheatbreaker.client.gui.hudeditor.AnchorAlignment s() {
         return com.cheatbreaker.client.gui.hudeditor.AnchorHelper.c(this.r());
      }

      @Override
      public float k() {
         return this.A().xTranslation;
      }

      @Override
      public float l() {
         return this.A().yTranslation;
      }

      @Override
      public void a(float var1, float var2) {
         com.cheatbreaker.client.module.modules.ItemDisplayEntry var3 = this.A();
         var3.xTranslation = var1;
         var3.yTranslation = var2;
      }

      @Override
      public void a(float var1) {
         this.A().xTranslation = var1;
      }

      @Override
      public void b(float var1) {
         this.A().yTranslation = var1;
      }

      @Override
      public boolean g() {
         return ItemCountModule.this.q() && this.A().enabled;
      }

      @Override
      public boolean q() {
         return ItemCountModule.this.q() && this.A().renderHud;
      }

      @Override
      public void f(boolean var1) {
         this.A().renderHud = var1;
      }

      @Override
      public void a(boolean var1) {
         this.A().enabled = var1;
         this.A().renderHud = var1;
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
               var7 = var1.getScaledWidth() - var4 - 2.0F;
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
         var2 *= var4;
         var3 *= var4;
         com.cheatbreaker.client.gui.hudeditor.GuiAnchor var5 = this.r();
         float var6;
         float var7;
         switch (var5) {
            case LEFT_TOP:
            default:
               var6 = 2.0F;
               var7 = 2.0F;
               break;
            case LEFT_MIDDLE:
               var6 = 2.0F;
               var7 = var1.getScaledHeight() / 2.0F - var3 / 2.0F;
               break;
            case LEFT_BOTTOM:
               var6 = 2.0F;
               var7 = var1.getScaledHeight() - var3 - 2.0F;
               break;
            case MIDDLE_TOP:
               var6 = var1.getScaledWidth() / 2.0F - var2 / 2.0F;
               var7 = 2.0F;
               break;
            case MIDDLE_MIDDLE:
               var6 = var1.getScaledWidth() / 2.0F - var2 / 2.0F;
               var7 = var1.getScaledHeight() / 2.0F - var3 / 2.0F;
               break;
            case MIDDLE_BOTTOM_LEFT:
               var6 = var1.getScaledWidth() / 2.0F - var2;
               var7 = var1.getScaledHeight() - var3 - 2.0F;
               break;
            case MIDDLE_BOTTOM_RIGHT:
               var6 = var1.getScaledWidth() / 2.0F;
               var7 = var1.getScaledHeight() - var3 - 2.0F;
               break;
            case RIGHT_TOP:
               var6 = var1.getScaledWidth() - var2 - 2.0F;
               var7 = 2.0F;
               break;
            case RIGHT_MIDDLE:
               var6 = var1.getScaledWidth() - var2;
               var7 = var1.getScaledHeight() / 2.0F - var3 / 2.0F;
               break;
            case RIGHT_BOTTOM:
               var6 = var1.getScaledWidth() - var2 - 2.0F;
               var7 = var1.getScaledHeight() - var3 - 2.0F;
         }

         GL11.glTranslatef(var6 / var4, var7 / var4, 0.0F);
         GL11.glTranslatef(this.k() / var4, this.l() / var4, 0.0F);
      }
   }
}
