package cn.kazer.client189.runtime;

import com.cheatbreaker.client.kazerui.module.modules.visual.ModuleOverlay;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public final class OverlayHooks {
   public static boolean skipShadow() {
      return !ModuleOverlay.shadows();
   }

   public static boolean skipArrows() {
      return !ModuleOverlay.stuckArrows();
   }

   public static boolean skipPumpkin() {
      return ModuleOverlay.pumpkinAlpha() <= 0.0F;
   }

   public static boolean skipGroundArrow(Entity var0) {
      return !ModuleOverlay.groundArrows() && var0 instanceof ArrowGroundState && ((ArrowGroundState)var0).kazerInGround();
   }

   public static ItemStack armor(ItemStack var0, EntityLivingBase var1, int var2) {
      return ModuleOverlay.hideArmor(var1, 4 - var2) ? null : var0;
   }

   public static ItemStack headArmor(ItemStack var0, EntityLivingBase var1) {
      return ModuleOverlay.hideArmor(var1, 0) ? null : var0;
   }

   public static void bobTranslate(float var0, float var1, float var2) {
      float var3 = ModuleOverlay.bobScale();
      GlStateManager.translate(var0 * var3, var1 * var3, var2 * var3);
   }

   public static void bobRotate(float var0, float var1, float var2, float var3) {
      GlStateManager.rotate(var0 * ModuleOverlay.bobScale(), var1, var2, var3);
   }

   public static void fireTranslate(float var0, float var1, float var2) {
      GlStateManager.translate(var0, var1 - ModuleOverlay.fireOffset(), var2);
   }

   public static void pumpkinColor(float var0, float var1, float var2, float var3) {
      GlStateManager.color(var0, var1, var2, var3 * ModuleOverlay.pumpkinAlpha());
   }

   public static void horseHead(ModelRenderer var0, float var1) {
      if (!ModuleOverlay.headless()) {
         var0.render(var1);
      }
   }

   public static boolean armorGlint() {
      return ModuleOverlay.glint(com.cheatbreaker.client.CheatBreakerClient.N() == null || (Boolean)com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.enchantmentGlint.d(), true);
   }

   public static boolean skipArmorGlint() {
      return !armorGlint();
   }

   public static int barrierType(int var0) {
      return ModuleOverlay.barriers() ? 3 : var0;
   }

   public static WorldRenderer lineColor(WorldRenderer var0, int var1, int var2, int var3, int var4) {
      int var5 = ModuleOverlay.fishingColor(var1 << 16 | var2 << 8 | var3);
      return var0.color(var5 >> 16 & 0xFF, var5 >> 8 & 0xFF, var5 & 0xFF, var4);
   }

   public static void packIcon(int var0, int var1, float var2, float var3, int var4, int var5, float var6, float var7) {
      if (var6 != 32.0F || var7 != 32.0F || com.cheatbreaker.client.kazerui.module.GlobalSettingsModule.n()) {
         Gui.drawModalRectWithCustomSizedTexture(var0, var1, var2, var3, var4, var5, var6, var7);
      }
   }

   private OverlayHooks() {
   }
}
