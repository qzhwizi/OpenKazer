package cn.kazer.client189.animation;

import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public final class OldAnimationsAdapter {
   private static final cn.kazer.client189.animation.SwingAnimationTracker SWING = new cn.kazer.client189.animation.SwingAnimationTracker();
   private static EntityPlayer lastPlayer;
   private static float eye = 1.62F;
   private static float lastEye = 1.62F;
   private static float cameraPartial;

   private OldAnimationsAdapter() {
   }

   public static void reset() {
      SWING.a();
      lastPlayer = null;
      lastEye = 1.62F;
      eye = 1.62F;
   }

   public static void tick() {
      Minecraft var0 = Minecraft.getMinecraft();
      if (var0.thePlayer != lastPlayer) {
         reset();
         lastPlayer = var0.thePlayer;
      }

      if (var0.thePlayer != null && cn.kazer.client189.animation.LegacyVisualsFlags.a(32768)) {
         boolean var1 = var0.currentScreen == null
            && cn.kazer.client189.animation.LegacyVisualsFlags.a(2048)
            && var0.gameSettings.keyBindAttack.isKeyDown()
            && var0.objectMouseOver != null
            && var0.objectMouseOver.typeOfHit == MovingObjectType.BLOCK;
         int var2 = var0.thePlayer.isPotionActive(Potion.digSpeed)
            ? 5 - var0.thePlayer.getActivePotionEffect(Potion.digSpeed).getAmplifier()
            : (var0.thePlayer.isPotionActive(Potion.digSlowdown) ? 8 + var0.thePlayer.getActivePotionEffect(Potion.digSlowdown).getAmplifier() * 2 : 6);
         SWING.a(var1, var2);
         lastEye = eye;
         if (var0.thePlayer.isSneaking()) {
            eye = 1.54F;
         } else if (!cn.kazer.client189.animation.LegacyVisualsFlags.a(2)) {
            eye = 1.62F;
         } else if (eye < 1.62F) {
            float var3 = 1.62F - eye;
            var3 = (float)(var3 * 0.4);
            eye = 1.62F - var3;
         }
      } else {
         reset();
      }
   }

   public static float useSwing(float var0, float var1) {
      Minecraft var2 = Minecraft.getMinecraft();
      return cn.kazer.client189.animation.LegacyVisualsFlags.a(16) && var2.thePlayer != null ? SWING.a(var1, var2.thePlayer.getSwingProgress(var1)) : var0;
   }

   public static boolean skipNoneTransform() {
      return cn.kazer.client189.animation.LegacyVisualsFlags.a(32);
   }

   public static int renderedUseCount(Object var0, ItemStack var1) {
      EntityPlayer var2 = (EntityPlayer)var0;
      int var3 = var2.getItemInUseCount();
      if (!cn.kazer.client189.animation.LegacyVisualsFlags.a(8192)) {
         return var3;
      } else {
         boolean var4 = false;
         if (var1 != null && cn.kazer.client189.animation.LegacyVisualsFlags.a(2048) && var3 <= 0 && Minecraft.getMinecraft().gameSettings.keyBindUseItem.isKeyDown()) {
            EnumAction var5 = var1.getItemUseAction();
            Item var6 = var1.getItem();
            boolean var7 = false;
            if (var6 instanceof ItemFood && var2.canEat(alwaysEdible((ItemFood)var6))) {
               var7 = var5 == EnumAction.EAT || var5 == EnumAction.DRINK;
            }

            var4 = var5 == EnumAction.BLOCK || var7;
         }

         return (var3 > 0 || var4) && var2.isUsingItem() ? 1 : 0;
      }
   }

   private static boolean alwaysEdible(ItemFood var0) {
      try {
         return OldAnimationsAdapter.a.VALUE.getBoolean(var0);
      } catch (IllegalAccessException var2) {
         throw new IllegalStateException(var2);
      }
   }

   public static void beforeRightClick() {
      Minecraft var0 = Minecraft.getMinecraft();
      if (cn.kazer.client189.animation.LegacyVisualsFlags.a(2048) && var0.thePlayer != null && var0.playerController != null) {
         ItemStack var1 = var0.thePlayer.getHeldItem();
         if (var0.playerController.getIsHittingBlock() && var1 != null && (var1.getItemUseAction() != EnumAction.NONE || var1.getItem() instanceof ItemBlock)) {
            var0.playerController.resetBlockRemoving();
         }
      }
   }

   public static boolean cancelBlockClick() {
      Minecraft var0 = Minecraft.getMinecraft();
      return cn.kazer.client189.animation.LegacyVisualsFlags.a(2048)
         && var0.thePlayer != null
         && var0.thePlayer.isUsingItem()
         && var0.currentScreen == null
         && var0.objectMouseOver != null
         && var0.objectMouseOver.typeOfHit == MovingObjectType.BLOCK;
   }

   public static float equipClamp(float var0, float var1, float var2, ItemStack var3, int var4, float var5) {
      Minecraft var6 = Minecraft.getMinecraft();
      if (cn.kazer.client189.animation.LegacyVisualsFlags.a(4096)
         && var6.thePlayer != null
         && var4 == var6.thePlayer.inventory.currentItem
         && ItemStack.areItemsEqual(var3, var6.thePlayer.inventory.getCurrentItem())) {
         var0 = 1.0F - var5;
      }

      return MathHelper.clamp_float(var0, var1, var2);
   }

   public static void bowScale(float var0, float var1, float var2) {
      boolean var3 = cn.kazer.client189.animation.LegacyVisualsFlags.a(256);
      if (var3) {
         GlStateManager.rotate(-335.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.rotate(-50.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.translate(0.0F, 0.5F, 0.0F);
      }

      GlStateManager.scale(var0, var1, var2);
      if (var3) {
         GlStateManager.translate(0.0F, -0.5F, 0.0F);
         GlStateManager.rotate(50.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(335.0F, 0.0F, 0.0F, 1.0F);
      }
   }

   public static void renderFirstPerson(Object var0, EntityLivingBase var1, ItemStack var2, TransformType var3) {
      if (var2 != null && cn.kazer.client189.animation.LegacyVisualsFlags.a(8192)) {
         if (var2.getItem().shouldRotateAroundWhenRendering() && (cn.kazer.client189.animation.LegacyVisualsFlags.a(32) || cn.kazer.client189.animation.LegacyVisualsFlags.a(512))) {
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
         }

         var3 = firstPersonTransform(var2) ? TransformType.FIRST_PERSON : TransformType.NONE;
      }

      ((ItemRenderer)var0).renderItem(var1, var2, var3);
   }

   private static boolean firstPersonTransform(ItemStack var0) {
      switch (var0.getItemUseAction()) {
         case BOW:
            if (!cn.kazer.client189.animation.LegacyVisualsFlags.a(256)) {
               return true;
            }
            break;
         case EAT:
         case DRINK:
            if (!cn.kazer.client189.animation.LegacyVisualsFlags.a(1024)) {
               return true;
            }
            break;
         case BLOCK:
            if (!cn.kazer.client189.animation.LegacyVisualsFlags.a(128)) {
               return true;
            }
            break;
         case NONE:
            if ((!(var0.getItem() instanceof ItemFishingRod) || !cn.kazer.client189.animation.LegacyVisualsFlags.a(512)) && !cn.kazer.client189.animation.LegacyVisualsFlags.a(32)) {
               return true;
            }
      }

      GlStateManager.translate(0.58800083F, 0.36999986F, -0.77000016F);
      GlStateManager.translate(0.0F, -0.3F, 0.0F);
      GlStateManager.scale(1.5F, 1.5F, 1.5F);
      GlStateManager.rotate(50.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(335.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.translate(-0.9375F, -0.0625F, 0.0F);
      GlStateManager.scale(-2.0F, 2.0F, -2.0F);
      if (Minecraft.getMinecraft().getRenderItem().shouldRenderItemIn3D(var0)) {
         GlStateManager.scale(0.58823526F, 0.58823526F, 0.58823526F);
         GlStateManager.rotate(-25.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.translate(0.0F, -0.25F, -0.125F);
         GlStateManager.scale(0.5F, 0.5F, 0.5F);
         return true;
      } else {
         GlStateManager.scale(0.5F, 0.5F, 0.5F);
         return false;
      }
   }

   public static float blockingArmYaw(float var0) {
      return cn.kazer.client189.animation.LegacyVisualsFlags.a(64) ? 0.0F : var0;
   }

   public static void thirdPersonPose(Object var0, TransformType var1) {
      if (cn.kazer.client189.animation.LegacyVisualsFlags.a(64) && var1 == TransformType.THIRD_PERSON && var0 instanceof EntityPlayer) {
         EntityPlayer var2 = (EntityPlayer)var0;
         ItemStack var3 = var2.getHeldItem();
         if (var3 != null && var2.getItemInUseCount() > 0 && var3.getItemUseAction() == EnumAction.BLOCK) {
            GlStateManager.translate(-0.15F, -0.2F, 0.0F);
            GlStateManager.rotate(70.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.translate(0.119F, 0.2F, -0.024F);
         }
      }
   }

   public static boolean redArmor() {
      return cn.kazer.client189.animation.LegacyVisualsFlags.a(4);
   }

   public static boolean healthFlash(boolean var0) {
      return var0 && !cn.kazer.client189.animation.LegacyVisualsFlags.a(8);
   }

   public static void cameraPartial(float var0) {
      cameraPartial = var0;
   }

   public static float cameraEye(Object var0, float var1) {
      Entity var2 = (Entity)var0;
      if (var2 == Minecraft.getMinecraft().thePlayer && cn.kazer.client189.animation.LegacyVisualsFlags.a(16384)) {
         return cn.kazer.client189.animation.LegacyVisualsFlags.a(1) ? lastEye + (eye - lastEye) * var1 : eye;
      } else {
         return var2.getEyeHeight();
      }
   }

   public static float directionEye(Object var0) {
      return cameraEye(var0, cameraPartial);
   }

   private static final class a {
      static final Field VALUE = ReflectionHelper.findField(ItemFood.class, "alwaysEdible", "field_77852_bZ");
   }
}
