package cn.kazer.client189.runtime;

import com.cheatbreaker.client.kazerui.module.modules.mechanic.ModuleWeatherChanger;
import com.cheatbreaker.client.kazerui.module.modules.visual.ModuleGlintColorizer;
import java.util.List;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.config.GuiUtils;

public final class SevenVisualHooks {
   private SevenVisualHooks() {
   }

   public static void armorColor(float var0, float var1, float var2, float var3) {
      if (var0 != 1.0F || var1 != 1.0F || var2 != 1.0F) {
         var0 = ModuleGlintColorizer.channel(var0, true, 16);
         var1 = ModuleGlintColorizer.channel(var1, true, 8);
         var2 = ModuleGlintColorizer.channel(var2, true, 0);
      }

      GlStateManager.color(var0, var1, var2, var3);
   }

   public static float rainSoundStrength(float var0) {
      return ModuleWeatherChanger.strength(var0);
   }

   public static boolean muteSound(ISound var0) {
      return var0 != null && ModuleWeatherChanger.muteSound(var0.getSoundLocation().getResourcePath());
   }

   public static void itemTranslate(float var0, float var1, float var2, EntityItem var3, IBakedModel var4, double var5) {
      if (com.cheatbreaker.client.kazerui.module.modules.visual.ItemPhysicsModule.n()) {
         var1 = (float)var5 + (var4.isGui3d() ? 0.25F * var4.getItemCameraTransforms().ground.scale.y : 0.065F);
      }

      GlStateManager.translate(var0, var1, var2);
   }

   public static void itemRotate(float var0, float var1, float var2, float var3, EntityItem var4, float var5, IBakedModel var6) {
      if (!com.cheatbreaker.client.kazerui.module.modules.visual.ItemPhysicsModule.n()) {
         GlStateManager.rotate(var0, var1, var2, var3);
      } else {
         GlStateManager.rotate(var4.rotationYaw, 0.0F, 1.0F, 0.0F);
         if (!var6.isGui3d()) {
            GlStateManager.rotate(com.cheatbreaker.client.kazerui.module.modules.visual.ItemPhysicsModule.a(var4, var4.getAge() + var5), 1.0F, 0.0F, 0.0F);
         }
      }
   }

   public static void tooltip(GuiScreen var0, List<String> var1, int var2, int var3, FontRenderer var4, ItemStack var5) {
      if (!com.cheatbreaker.client.kazerui.module.modules.visual.ScrollableTooltipsModule.a(var0, var5, var1, var2, var3, var4)) {
         GuiUtils.drawHoveringText(var1, var2, var3, var0.width, var0.height, -1, var4);
      }
   }
}
