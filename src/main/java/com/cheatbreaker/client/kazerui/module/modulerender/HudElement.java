package com.cheatbreaker.client.kazerui.module.modulerender;

public abstract class HudElement implements com.cheatbreaker.client.kazerui.hud.HudFeature {
   public final com.cheatbreaker.client.kazerui.module.KazerModule module;
   public final com.cheatbreaker.client.kazerui.hud.HudAnchor anchor;
   private com.cheatbreaker.client.kazerui.gui.components.MenuModel anchorModel;
   private long anchorRevision = Long.MIN_VALUE;
   private com.cheatbreaker.client.kazerui.hud.HudAnchor cachedAnchor;
   private final float defaultX;
   private final float defaultY;
   public final com.cheatbreaker.client.kazerui.module.settings.SliderSetting scale;
   private float width;
   private float height;

   protected HudElement(com.cheatbreaker.client.kazerui.module.KazerModule var1, float var2, float var3, com.cheatbreaker.client.kazerui.hud.HudAnchor var4) {
      this(var1, var2, var3, var4, 0.25F, 5.0F);
   }

   protected HudElement(com.cheatbreaker.client.kazerui.module.KazerModule var1, float var2, float var3, com.cheatbreaker.client.kazerui.hud.HudAnchor var4, float var5, float var6) {
      this.module = var1;
      this.defaultX = var2;
      this.defaultY = var3;
      this.anchor = var4;
      this.scale = var1.a(com.cheatbreaker.client.kazerui.module.settings.SliderSetting.a("scale", 1.0F, var5, var6));
   }

   @Override
   public String a() {
      return this.module.b();
   }

   public com.cheatbreaker.client.kazerui.hud.HudAnchor c() {
      com.cheatbreaker.client.kazerui.gui.components.MenuModel var1 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.d();
      if (var1 == null) {
         return this.anchor;
      } else {
         if (var1 != this.anchorModel || var1.revision != this.anchorRevision) {
            com.cheatbreaker.client.kazerui.hud.HudAnchor var2 = com.cheatbreaker.client.kazerui.hud.HudAnchor.a(
               var1.a(this.a(), "anchor", com.cheatbreaker.client.kazerui.gui.components.MenuModel.b(this.a(), "anchor"))
            );
            this.cachedAnchor = var2 == null ? this.anchor : var2;
            this.anchorModel = var1;
            this.anchorRevision = var1.revision;
         }

         return this.cachedAnchor;
      }
   }

   public float d() {
      return this.defaultX;
   }

   public float e() {
      return this.defaultY;
   }

   @Override
   public String b() {
      return "";
   }

   public abstract boolean a(boolean var1);

   public abstract void b(boolean var1);

   protected void a(float var1, float var2) {
      this.width = var1;
      this.height = var2;
   }

   public float f() {
      return this.width;
   }

   public float g() {
      return this.height;
   }

   public float h() {
      return this.scale.b();
   }

   public boolean i() {
      return false;
   }

   public boolean j() {
      return false;
   }

   public float k() {
      return 0.0F;
   }

   public boolean l() {
      return true;
   }

   public void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(this.scale);
   }
}
