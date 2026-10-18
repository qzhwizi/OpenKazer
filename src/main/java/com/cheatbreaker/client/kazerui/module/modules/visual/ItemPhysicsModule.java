package com.cheatbreaker.client.kazerui.module.modules.visual;

import net.minecraft.entity.item.EntityItem;

public final class ItemPhysicsModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static com.cheatbreaker.client.kazerui.module.modules.visual.ItemPhysicsModule instance;
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting speed = this.a("rotationSpeed", 1.0F, 0.1F, 4.0F);

   public ItemPhysicsModule() {
      super("ITEM_PHYSICS", false);
      instance = this;
      this.o();
   }

   private void o() {
      for (com.cheatbreaker.client.kazerui.module.settings.AbstractSetting var2 : this.g()) {
         String var3 = var2.key;
         switch (var3) {
            case "rotationSpeed":
               var2.a(() -> "Rotation Speed");
         }
      }
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(this.speed);
   }

   public static boolean n() {
      return instance != null && instance.a();
   }

   public static float a(float var0) {
      return n() ? 0.025F : var0;
   }

   public static float a(EntityItem var0, float var1) {
      if (!n()) {
         return var1;
      } else {
         return var0.onGround ? 90.0F : var1 * instance.speed.b() * 12.0F % 360.0F;
      }
   }
}
