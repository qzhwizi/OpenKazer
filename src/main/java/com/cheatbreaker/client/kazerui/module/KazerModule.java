package com.cheatbreaker.client.kazerui.module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.function.BooleanSupplier;
import net.minecraft.client.Minecraft;

public abstract class KazerModule {
   public final String id;
   private final String storageKey;
   private final boolean defaultEnabled;
   private final List<com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<?>> settings = new ArrayList<>();
   private final List<com.cheatbreaker.client.kazerui.module.modulerender.HudElement> huds = new ArrayList<>();
   private final List<com.cheatbreaker.client.kazerui.module.KazerModule> children = new ArrayList<>();
   protected com.cheatbreaker.client.kazerui.module.KazerModule parent;
   protected String label;
   protected boolean childRowsLast;
   private BooleanSupplier rowShown;
   protected boolean keybindAllowed = true;
   private com.cheatbreaker.client.kazerui.gui.components.MenuModel enabledModel;
   private long enabledRevision = Long.MIN_VALUE;
   private boolean ownEnabled;
   private boolean ownRenderHud = true;

   protected KazerModule(String var1, boolean var2) {
      this.id = var1;
      this.storageKey = var1.toLowerCase(Locale.ROOT);
      this.defaultEnabled = var2;
   }

   public String b() {
      return this.storageKey;
   }

   public boolean a() {
      if (this.parent != null && !this.parent.a()) {
         return false;
      } else {
         com.cheatbreaker.client.kazerui.gui.components.MenuModel var1 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.d();
         if (var1 == null) {
            return this.defaultEnabled;
         } else {
            if (this.enabledModel != var1 || this.enabledRevision != var1.revision) {
               this.ownEnabled = Boolean.parseBoolean(
                  com.cheatbreaker.client.kazerui.module.ModuleRegistry.a(this.storageKey, "enabled", this.defaultEnabled ? "true" : "false")
               );
               this.ownRenderHud = Boolean.parseBoolean(com.cheatbreaker.client.kazerui.module.ModuleRegistry.a(this.storageKey, "renderHud", "true"));
               this.enabledModel = var1;
               this.enabledRevision = var1.revision;
            }

            return this.ownEnabled;
         }
      }
   }

   public boolean c() {
      return this.a() && this.ownRenderHud && (this.parent == null || this.parent.c());
   }

   public void a(boolean var1) {
      boolean var2 = this.a();
      com.cheatbreaker.client.kazerui.module.ModuleRegistry.b(this.b(), "enabled", "" + var1);
      if (var2 != this.a()) {
         if (var1) {
            this.e();
         } else {
            this.f();
         }
      }
   }

   public boolean d() {
      return this.defaultEnabled;
   }

   protected void e() {
   }

   protected void f() {
   }

   public void a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a var1) {
   }

   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
   }

   public List<com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<?>> g() {
      return Collections.unmodifiableList(this.settings);
   }

   public List<com.cheatbreaker.client.kazerui.module.modulerender.HudElement> h() {
      return Collections.unmodifiableList(this.huds);
   }

   public List<com.cheatbreaker.client.kazerui.module.KazerModule> i() {
      return Collections.unmodifiableList(this.children);
   }

   public com.cheatbreaker.client.kazerui.module.KazerModule j() {
      return this.parent;
   }

   public <S extends com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<?>> S a(S var1) {
      var1.a(this);
      this.settings.add(var1);
      return (S)var1;
   }

   public void b(com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<?> var1) {
      this.settings.remove(var1);
   }

   public com.cheatbreaker.client.kazerui.module.settings.ToggleSetting a(String var1, boolean var2) {
      return this.a(new com.cheatbreaker.client.kazerui.module.settings.ToggleSetting(var1, var2));
   }

   public com.cheatbreaker.client.kazerui.module.settings.SliderSetting a(String var1, int var2, int var3, int var4) {
      return this.a(com.cheatbreaker.client.kazerui.module.settings.SliderSetting.a(var1, var2, var3, var4));
   }

   public com.cheatbreaker.client.kazerui.module.settings.SliderSetting a(String var1, float var2, float var3, float var4) {
      return this.a(com.cheatbreaker.client.kazerui.module.settings.SliderSetting.a(var1, var2, var3, var4));
   }

   public com.cheatbreaker.client.kazerui.module.settings.ColorSetting a(String var1, int var2) {
      return this.a(new com.cheatbreaker.client.kazerui.module.settings.ColorSetting(var1, var2));
   }

   public <E extends Enum<E> & com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a> com.cheatbreaker.client.kazerui.module.settings.EnumSetting<E> a(String var1, E var2) {
      return this.a(new com.cheatbreaker.client.kazerui.module.settings.EnumSetting<>(var1, (E)var2));
   }

   public com.cheatbreaker.client.kazerui.module.settings.KeybindSetting a(String var1) {
      return this.a(new com.cheatbreaker.client.kazerui.module.settings.KeybindSetting(var1));
   }

   public com.cheatbreaker.client.kazerui.module.settings.KeybindSetting a(String var1, String var2) {
      return this.a(new com.cheatbreaker.client.kazerui.module.settings.KeybindSetting(var1, var2, false));
   }

   public com.cheatbreaker.client.kazerui.module.settings.KeybindSetting b(String var1) {
      return this.a(new com.cheatbreaker.client.kazerui.module.settings.KeybindSetting(var1, "NONE", true));
   }

   public com.cheatbreaker.client.kazerui.module.settings.StringSetting c(String var1) {
      return this.a(new com.cheatbreaker.client.kazerui.module.settings.StringSetting(var1));
   }

   protected <H extends com.cheatbreaker.client.kazerui.module.modulerender.HudElement> H a(H var1) {
      this.huds.add(var1);
      return (H)var1;
   }

   protected <M extends com.cheatbreaker.client.kazerui.module.KazerModule> M a(M var1, String var2) {
      var1.parent = this;
      var1.label = var2;
      this.children.add(var1);
      return (M)var1;
   }

   protected <M extends com.cheatbreaker.client.kazerui.module.KazerModule> M b(M var1, String var2) {
      this.a(var1, var2);
      com.cheatbreaker.client.kazerui.module.ModuleRegistry.a(var1);
      return (M)var1;
   }

   protected void a(com.cheatbreaker.client.kazerui.module.KazerModule var1) {
      if (this.children.remove(var1)) {
         com.cheatbreaker.client.kazerui.module.ModuleRegistry.b(var1);
         var1.parent = null;
      }
   }

   public com.cheatbreaker.client.kazerui.gui.components.OptionTree.b k() {
      com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1 = new com.cheatbreaker.client.kazerui.module.OptionTreeBuilder();
      this.a(var1);

      for (com.cheatbreaker.client.kazerui.module.modulerender.HudElement var3 : this.huds) {
         var3.a(var1);
      }

      for (com.cheatbreaker.client.kazerui.module.KazerModule var7 : this.children) {
         if (var7.label != null) {
            var1.a(var7.label).rows.add(var7);
         } else if (this.childRowsLast) {
            var1.lastRows.add(var7);
         } else {
            var1.rows.add(var7);
         }
      }

      boolean var6 = !this.huds.isEmpty();

      for (com.cheatbreaker.client.kazerui.module.KazerModule var4 : this.children) {
         var6 |= !var4.huds.isEmpty();
      }

      return new com.cheatbreaker.client.kazerui.gui.components.OptionTree.b(var6, this.keybindAllowed && this.parent == null, var1.a());
   }

   com.cheatbreaker.client.kazerui.gui.components.OptionTree.a l() {
      HashMap var1 = new HashMap();
      var1.put("type", "child");
      var1.put("key", this.id);
      var1.put("hud", !this.huds.isEmpty());
      var1.put("default", "" + this.defaultEnabled);
      final BooleanSupplier var2 = this.rowShown;
      return com.cheatbreaker.client.kazerui.gui.components.OptionTree.a.a(var1, this.k().options, var2 == null ? null : new BooleanSupplier() {
         @Override
         public boolean getAsBoolean() {
            return !var2.getAsBoolean();
         }
      });
   }

   protected void a(BooleanSupplier var1) {
      this.rowShown = var1;
   }

   protected String a(String var1, Object... var2) {
      return com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("features." + this.id + ".info", var1, var2);
   }

   protected static Minecraft m() {
      return Minecraft.getMinecraft();
   }
}
