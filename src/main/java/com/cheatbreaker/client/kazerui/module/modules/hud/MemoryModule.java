package com.cheatbreaker.client.kazerui.module.modules.hud;

import java.util.Locale;

public final class MemoryModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.hud.MemoryModule.a> displayMode = this.a(
      "displayMode", com.cheatbreaker.client.kazerui.module.modules.hud.MemoryModule.a.PERCENTAGE
   );
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting colorBasedOnUsage = this.a("colorBasedOnUsage", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting lowMemColor = this.a("lowMemColor", -65536);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting medMemColor = this.a("medMemColor", -256);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting highMemColor = this.a("highMemColor", -16711936);

   public MemoryModule() {
      super("MEMORY", false);
      this.a(new com.cheatbreaker.client.kazerui.module.modules.hud.MemoryModule.b());
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a("settings", var1x -> var1x.a(this.displayMode));
      var1.a("colorOptions", var1x -> var1x.a(this.colorBasedOnUsage, var1xx -> var1xx.a(this.lowMemColor, this.medMemColor, this.highMemColor)));
   }

   public static enum a implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
      PERCENTAGE("percentage"),
      MEGABYTES("megabytes"),
      GIGABYTES("gigabytes");

      private final String id;

      private a(String var3) {
         this.id = var3;
      }

      @Override
      public String a() {
         return this.id;
      }
   }

   private final class b extends com.cheatbreaker.client.kazerui.module.modulerender.RichTextHudBase {
      private int memoryUsage;
      private long nextSample;
      private com.cheatbreaker.client.kazerui.module.modules.hud.MemoryModule.a lastMode;
      private String cachedText;

      b() {
         super(MemoryModule.this, 0.0F, 0.0F, com.cheatbreaker.client.kazerui.hud.HudAnchor.TOP_RIGHT, a(10, 18, 22, 46, 56, 62));
      }

      @Override
      protected String c(boolean var1) {
         long var2 = System.currentTimeMillis();
         com.cheatbreaker.client.kazerui.module.modules.hud.MemoryModule.a var4 = MemoryModule.this.displayMode.j();
         if (this.cachedText != null && var2 < this.nextSample && this.lastMode == var4) {
            return this.cachedText;
         } else {
            this.nextSample = var2 + 500L;
            this.lastMode = var4;
            Runtime var5 = Runtime.getRuntime();
            long var6 = var5.totalMemory() - var5.freeMemory();
            long var8 = var5.maxMemory();
            this.memoryUsage = (int)(var6 * 100L / var8);
            String var10;
            switch (var4) {
               case MEGABYTES:
                  var10 = var6 / 1048576L + "/" + var8 / 1048576L + " MB";
                  break;
               case GIGABYTES:
                  var10 = String.format(Locale.ROOT, "%.2f/%.2f GB", var6 / 1.0737418E9F, var8 / 1.0737418E9F);
                  break;
               default:
                  var10 = this.memoryUsage + "%";
            }

            return this.cachedText = MemoryModule.this.a("memory", new Object[]{var10});
         }
      }

      @Override
      protected Boolean b(String var1) {
         return Boolean.FALSE;
      }

      @Override
      protected boolean u() {
         return MemoryModule.this.colorBasedOnUsage.b();
      }

      @Override
      protected void a(String var1, float var2, float var3, boolean var4, boolean var5) {
         com.cheatbreaker.client.kazerui.module.settings.ColorSetting var6 = this.textColor;
         if (MemoryModule.this.colorBasedOnUsage.b()) {
            var6 = this.memoryUsage >= 75 ? MemoryModule.this.lowMemColor : (this.memoryUsage >= 50 ? MemoryModule.this.medMemColor : MemoryModule.this.highMemColor);
         }

         if (var4) {
            var2 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(this.bracketColor, "[", var2, var3, var5);
         }

         var2 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var6, var1, var2, var3, var5);
         if (var4) {
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(this.bracketColor, "]", var2, var3, var5);
         }
      }
   }
}
