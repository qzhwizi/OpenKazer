package com.cheatbreaker.client.kazerui.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public final class NativeModuleAdapter extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private final com.cheatbreaker.client.module.Module nativeModule;
   private final List<com.cheatbreaker.client.kazerui.module.NativeModuleAdapter.a> values = new ArrayList<>();
   private long appliedRevision = Long.MIN_VALUE;

   public NativeModuleAdapter(com.cheatbreaker.client.module.Module var1) {
      super(Objects.requireNonNull(com.cheatbreaker.client.kazerui.notifications.LegacyConfigMigration.a(var1.h())), var1.defaultState);
      this.nativeModule = var1;

      for (int var2 = 0; var2 < var1.m().size(); var2++) {
         com.cheatbreaker.client.settings.Setting var3 = var1.m().get(var2);
         if (!var3.f().equalsIgnoreCase("label")) {
            Object var4 = var2 < var1.n().size() ? var1.n().get(var2) : var3.d();
            if (var4 != null && !(var4 instanceof String[])) {
               com.cheatbreaker.client.kazerui.module.NativeModuleAdapter.a var5 = this.a(new com.cheatbreaker.client.kazerui.module.NativeModuleAdapter.a(var3, var4));
               var5.a(var3::g);
               var5.a((Runnable) var5::b);
               if (var3.e() != null) {
                  var5.a(() -> !var3.j());
               }

               this.values.add(var5);
            }
         }
      }

      this.n();
   }

   private void n() {
      com.cheatbreaker.client.kazerui.gui.components.MenuModel var1 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.d();
      String var2 = "migration.nativeSettings." + this.b() + ".v1";
      if (!"true".equals(var1.store.a(var2, null))) {
         for (String var4 : var1.profiles) {
            String var5 = "profile." + var4 + "." + this.b() + ".";
            if (var1.store.a(var5 + "enabled", null) == null) {
               var1.store.b(var5 + "enabled", String.valueOf(var4.equals(var1.a()) ? this.nativeModule.g() : this.nativeModule.defaultState));
            }

            for (com.cheatbreaker.client.kazerui.module.NativeModuleAdapter.a var7 : this.values) {
               if (var1.store.a(var5 + var7.key, null) == null) {
                  String var8 = var1.store.a("legacy.profile." + var4 + "." + this.nativeModule.h() + "." + var7.nativeSetting.f(), null);
                  if (var8 != null) {
                     try {
                        var1.store.b(var5 + var7.key, var7.c(var7.b(var8)));
                     } catch (RuntimeException var10) {
                     }
                  }
               }
            }
         }

         var1.store.b(var2, "true");
      }
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      for (com.cheatbreaker.client.kazerui.module.NativeModuleAdapter.a var3 : this.values) {
         if (!this.b().equals("nametags") || !var3.key.equals("legacy_show_kazer_icon_in_tab") && !var3.key.equals("legacy_show_nametag_icon")) {
            var1.a(var3);
         }
      }
   }

   @Override
   protected void e() {
      this.o();
   }

   @Override
   protected void f() {
      this.o();
   }

   private void o() {
      this.nativeModule.b(this.a());

      for (com.cheatbreaker.client.kazerui.module.NativeModuleAdapter.a var2 : this.values) {
         var2.b();
      }

      this.appliedRevision = com.cheatbreaker.client.kazerui.module.ModuleRegistry.d().revision;
   }

   @Override
   public void a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a var1) {
      if (var1.phase == com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b.START && this.appliedRevision != com.cheatbreaker.client.kazerui.module.ModuleRegistry.d().revision) {
         this.o();
      }
   }

   static final class a extends com.cheatbreaker.client.kazerui.module.settings.AbstractSetting<Object> {
      final com.cheatbreaker.client.settings.Setting nativeSetting;
      final boolean color;

      a(com.cheatbreaker.client.settings.Setting var1, Object var2) {
         super("legacy_" + var1.f().toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "_"), var2);
         this.nativeSetting = var1;
         this.color = var2 instanceof Integer
            && var1.b() != null
            && var1.c() != null
            && var1.b().intValue() == Integer.MIN_VALUE
            && var1.c().intValue() == Integer.MAX_VALUE;
      }

      @Override
      protected Object a(String var1) {
         if (this.defaultValue instanceof Boolean) {
            return Boolean.parseBoolean(var1);
         } else if (this.color) {
            return (int)Long.parseLong(var1, 16);
         } else if (this.defaultValue instanceof Number) {
            double var2 = Double.parseDouble(var1);
            if (!Double.isNaN(var2) && !Double.isInfinite(var2)) {
               if (this.nativeSetting.b() != null) {
                  var2 = Math.max(var2, this.nativeSetting.b().doubleValue());
               }

               if (this.nativeSetting.c() != null) {
                  var2 = Math.min(var2, this.nativeSetting.c().doubleValue());
               }

               if (this.defaultValue instanceof Integer) {
                  return (int)Math.round(var2);
               } else {
                  return this.defaultValue instanceof Float ? (float)var2 : var2;
               }
            } else {
               throw new IllegalArgumentException("Non-finite option");
            }
         } else {
            return this.nativeSetting.h() != null && !Arrays.asList(this.nativeSetting.h()).contains(var1) ? this.defaultValue : var1;
         }
      }

      Object b(String var1) {
         return this.color ? Integer.parseInt(var1.split(";", 2)[0]) : this.a(var1);
      }

      @Override
      protected String c(Object var1) {
         return this.color ? String.format(Locale.ROOT, "%08X", (Integer)var1) : String.valueOf(var1);
      }

      @Override
      protected String a() {
         if (this.color) {
            return "color";
         } else if (this.defaultValue instanceof Boolean) {
            return "bool";
         } else if (this.defaultValue instanceof Number) {
            return "number";
         } else {
            return this.nativeSetting.h() == null ? "string" : "enum";
         }
      }

      @Override
      protected void a(Map<String, Object> var1) {
         if (this.defaultValue instanceof Number && !this.color) {
            var1.put("min", this.nativeSetting.b() == null ? -100000 : this.nativeSetting.b());
            var1.put("max", this.nativeSetting.c() == null ? 100000 : this.nativeSetting.c());
            var1.put("integer", this.defaultValue instanceof Integer);
         }

         if (this.nativeSetting.h() != null) {
            ArrayList var2 = new ArrayList();

            for (String var6 : this.nativeSetting.h()) {
               var2.add(Arrays.asList(var6, var6));
            }

            var1.put("values", var2);
         }

         if (this.color) {
            var1.put("chroma", false);
         }
      }

      void b() {
         Object var1 = this.j();
         if (!Objects.equals(var1, this.nativeSetting.d())) {
            this.nativeSetting.a(var1, false);
         }
      }
   }
}
