package com.cheatbreaker.client.kazerui.module.modules.hud.elements;

import net.minecraft.client.Minecraft;

public final class ArmorStatusSlotChildModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   public final com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.f slot;
   private final com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule status;
   final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.d> durabilityPosition = this.a(
      "durabilityPosition", com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.d.RIGHT
   );
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting background = this.a("background", false);
   final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting border = this.a("border", false);
   final com.cheatbreaker.client.kazerui.module.settings.SliderSetting borderThickness = this.a("borderThickness", 0.5F, 0.5F, 3.0F);
   final com.cheatbreaker.client.kazerui.module.settings.ColorSetting backgroundColor = this.a("backgroundColor", 1862270976);
   final com.cheatbreaker.client.kazerui.module.settings.ColorSetting borderColor = this.a("borderColor", -1627389952);
   private final ArmorStatusSlotChildModule.a hud;

   public ArmorStatusSlotChildModule(com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule var1, com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.f var2) {
      super("ARMORSTATUS_" + var2.name() + "_CHILD", true);
      this.status = var1;
      this.slot = var2;
      this.hud = this.a(new ArmorStatusSlotChildModule.a());
   }

   private boolean o() {
      return !this.status.moveArmorIndividually.b() || this.status.vanillaMode.b();
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(this.hud.scale, this.durabilityPosition).a(this::o);
      var1.a(this.background, var1x -> {
         var1x.a(this.backgroundColor);
         var1x.a(this.border, var1xx -> var1xx.a(this.borderThickness, this.borderColor));
      }).a(this::o);
   }

   com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.d n() {
      return com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.b(this.b(), "durabilityPosition") == null
            && com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.a(this.b(), this.hud.anchor).horizontal == com.cheatbreaker.client.kazerui.hud.HudAnchor.a.END
         ? com.cheatbreaker.client.kazerui.module.modules.hud.ArmorStatusModule.d.LEFT
         : this.durabilityPosition.j();
   }

   private final class a extends com.cheatbreaker.client.kazerui.module.modulerender.HudElement {
      private com.cheatbreaker.client.kazerui.module.modules.hud.elements.ArmorStatusItemElement element;

      a() {
         super(ArmorStatusSlotChildModule.this, 0.0F, 0.0F, com.cheatbreaker.client.kazerui.hud.HudAnchor.BOTTOM_RIGHT);
      }

      @Override
      public void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      }

      @Override
      public boolean l() {
         return ArmorStatusSlotChildModule.this.status.moveArmorIndividually.b();
      }

      @Override
      public boolean a(boolean var1) {
         boolean var2 = Minecraft.getMinecraft().ingameGUI != null && Minecraft.getMinecraft().ingameGUI.getChatGUI().getChatOpen();
         this.element = ArmorStatusSlotChildModule.this.status.b(var1).get(ArmorStatusSlotChildModule.this.slot);
         boolean var3 = !ArmorStatusSlotChildModule.this.status.vanillaMode.b()
            && ArmorStatusSlotChildModule.this.status.moveArmorIndividually.b()
            && (ArmorStatusSlotChildModule.this.status.showWhileTyping.b() || !var2)
            && this.element != null;
         if (!var3) {
            this.a(0.0F, 0.0F);
            return false;
         } else {
            this.element.a(ArmorStatusSlotChildModule.this.n());
            this.a(this.element.a() + 4, this.element.b() + 4);
            return true;
         }
      }

      @Override
      public void b(boolean var1) {
         if (this.element != null) {
            this.element.a(ArmorStatusSlotChildModule.this.n());
            float var2 = this.f();
            float var3 = this.g();
            if (ArmorStatusSlotChildModule.this.background.b()) {
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(ArmorStatusSlotChildModule.this.backgroundColor, 0.0F, 0.0F, var2, var3);
               if (ArmorStatusSlotChildModule.this.border.b()) {
                  com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(ArmorStatusSlotChildModule.this.borderColor, 0.0F, 0.0F, var2, var3, ArmorStatusSlotChildModule.this.borderThickness.b());
               }
            }

            this.element.a(2.0F, 2.5F);
         }
      }
   }
}
