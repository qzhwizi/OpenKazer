package com.cheatbreaker.client.kazerui.module;

public class LegacyToggleModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private final String option;
   private final boolean fallback;
   private com.cheatbreaker.client.kazerui.gui.components.MenuModel cachedModel;
   private long revision = Long.MIN_VALUE;
   private boolean enabled;

   public LegacyToggleModule(String var1, String var2, boolean var3) {
      super(var1, var3);
      this.option = var2;
      this.fallback = var3;
   }

   @Override
   public boolean a() {
      com.cheatbreaker.client.kazerui.gui.components.MenuModel var1 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.d();
      if (var1 == null) {
         return this.fallback;
      } else {
         if (this.cachedModel != var1 || this.revision != var1.revision) {
            this.enabled = var1.a("global", this.option, this.fallback);
            this.cachedModel = var1;
            this.revision = var1.revision;
         }

         return this.enabled;
      }
   }

   @Override
   public void a(boolean var1) {
      if (com.cheatbreaker.client.kazerui.module.ModuleRegistry.d() != null) {
         com.cheatbreaker.client.kazerui.module.ModuleRegistry.d().b("global", this.option, Boolean.toString(var1));
      }
   }
}
