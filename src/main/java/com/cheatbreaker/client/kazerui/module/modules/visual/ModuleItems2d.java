package com.cheatbreaker.client.kazerui.module.modules.visual;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFireworkCharge;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public final class ModuleItems2d extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static final Random RANDOM = new Random();
   private static final ResourceLocation POTION_BOTTLE = new ResourceLocation("minecraft", "items/potion_bottle_drinkable");
   private static final ResourceLocation SPAWN_EGG_OVERLAY = new ResourceLocation("minecraft", "items/spawn_egg_overlay");
   private static final ResourceLocation FIREWORK_CHARGE_OVERLAY = new ResourceLocation("minecraft", "items/fireworks_charge_overlay");
   private static final List<ResourceLocation> LEATHER_OVERLAYS = Arrays.asList(
      new ResourceLocation("minecraft", "items/leather_helmet_overlay"),
      new ResourceLocation("minecraft", "items/leather_chestplate_overlay"),
      new ResourceLocation("minecraft", "items/leather_leggings_overlay"),
      new ResourceLocation("minecraft", "items/leather_boots_overlay")
   );
   private final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<ModuleItems2d.a> renderingOptions = this.a("renderingOptions", ModuleItems2d.a.SPRITE);

   public ModuleItems2d() {
      super("2D_ITEMS", false);
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(this.renderingOptions);
   }

   public static boolean render(EntityItem var0, double var1, double var3, double var5, float var7) {
      com.cheatbreaker.client.kazerui.module.KazerModule var8 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.a("2D_ITEMS");
      if (var8 instanceof ModuleItems2d && var8.a()) {
         com.cheatbreaker.client.kazerui.module.KazerModule var9 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.a("ITEM_PHYSICS");
         if (var9 != null && var9.a()) {
            return false;
         } else {
            cn.kazer.client189.render.GlStateBridge.c(1048575);
            GlStateManager.pushMatrix();

            try {
               ((ModuleItems2d)var8).a(var0, var1, var3, var5, var7);
            } finally {
               GlStateManager.popMatrix();
               cn.kazer.client189.render.GlStateBridge.a();
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private void a(EntityItem var1, double var2, double var4, double var6, float var8) {
      RANDOM.setSeed(187L);
      ItemStack var9 = var1.getEntityItem();
      Item var10 = var9.getItem();
      if (var10 != null) {
         if (!(var10 instanceof ItemBlock)
            && !(var10 instanceof ItemSkull)
            && !(var10 instanceof ItemBed)
            && this.renderingOptions.j() != ModuleItems2d.a.MODEL) {
            Minecraft var11 = m();
            TextureAtlasSprite var12 = var11.getRenderItem().getItemModelMesher().getParticleIcon(var10, var9.getItemDamage());
            boolean var13 = false;
            int var14 = -16777216;
            if (var10 instanceof ItemArmor || var10 instanceof ItemPotion || var10 instanceof ItemMonsterPlacer) {
               var13 = true;
               var14 = var10.getColorFromItemStack(var9, 0);
            }

            int var15 = a(var9.stackSize);
            WorldRenderer var16 = Tessellator.getInstance().getWorldRenderer();
            var11.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableColorMaterial();
            GlStateManager.enableDepth();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            RenderHelper.enableStandardItemLighting();
            float var17 = MathHelper.sin((var1.getAge() + var8) / 10.0F + var1.hoverStart) * 0.1F + 0.1F;
            GL11.glTranslated(var2, var4 + var17 + 0.1, var6);
            int var18 = var14 >> 16 & 0xFF;
            int var19 = var14 >> 8 & 0xFF;
            int var20 = var14 & 0xFF;

            for (int var21 = 0; var21 < var15; var21++) {
               GlStateManager.pushMatrix();
               b(var21);
               GL11.glScalef(0.5F, 0.5F, 0.5F);
               ResourceLocation var22 = a(var10);
               if (var22 != null) {
                  TextureAtlasSprite var23 = var11.getTextureMapBlocks().getAtlasSprite(var22.toString());
                  int var24 = var10.getColorFromItemStack(var9, 1);
                  a(var16, var23, var24 >> 16 & 0xFF, var24 >> 8 & 0xFF, var24 & 0xFF, true);
               }

               a(var16, var12, var18, var19, var20, var13);
               GlStateManager.popMatrix();
            }

            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
         } else {
            this.a(var1, var9, var2, var4, var6, var8);
         }
      }
   }

   private void a(EntityItem var1, ItemStack var2, double var3, double var5, double var7, float var9) {
      RANDOM.setSeed(187L);
      Minecraft var10 = m();
      IBakedModel var11 = var10.getRenderItem().getItemModelMesher().getItemModel(var2);
      int var12 = a(var2.stackSize);
      GlStateManager.enableRescaleNormal();
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      RenderHelper.enableStandardItemLighting();
      GlStateManager.pushMatrix();
      var10.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
      float var13 = var11.getItemCameraTransforms().ground.scale.y;
      float var14 = MathHelper.sin((var1.getAge() + var9) / 10.0F + var1.hoverStart) * 0.1F + 0.1F;
      GL11.glTranslated(var3, var5 + var14 + 0.25 * var13, var7);

      for (int var15 = 0; var15 < var12; var15++) {
         GlStateManager.pushMatrix();
         b(var15);
         if (var11.isBuiltInRenderer() && !(var2.getItem() instanceof ItemSkull)) {
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
         } else if (var2.getItem().getUnlocalizedName().contains("Fence")) {
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
         }

         if (var11.isGui3d()) {
            GlStateManager.scale(0.5, 0.5, 0.5);
         }

         var11.getItemCameraTransforms().applyTransform(TransformType.GROUND);
         var10.getRenderItem().renderItem(var2, var11);
         GlStateManager.popMatrix();
      }

      GlStateManager.popMatrix();
      GlStateManager.disableRescaleNormal();
      GlStateManager.disableBlend();
   }

   private static void a(WorldRenderer var0, TextureAtlasSprite var1, int var2, int var3, int var4, boolean var5) {
      var0.begin(7, var5 ? DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL : DefaultVertexFormats.POSITION_TEX_NORMAL);
      a(var0.pos(-0.5, -0.25, 0.0).tex(var1.getMinU(), var1.getMaxV()), var2, var3, var4, var5).normal(0.0F, 1.0F, 0.0F).endVertex();
      a(var0.pos(0.5, -0.25, 0.0).tex(var1.getMaxU(), var1.getMaxV()), var2, var3, var4, var5).normal(0.0F, 1.0F, 0.0F).endVertex();
      a(var0.pos(0.5, 0.75, 0.0).tex(var1.getMaxU(), var1.getMinV()), var2, var3, var4, var5).normal(0.0F, 1.0F, 0.0F).endVertex();
      a(var0.pos(-0.5, 0.75, 0.0).tex(var1.getMinU(), var1.getMinV()), var2, var3, var4, var5).normal(0.0F, 1.0F, 0.0F).endVertex();
      Tessellator.getInstance().draw();
   }

   private static WorldRenderer a(WorldRenderer var0, int var1, int var2, int var3, boolean var4) {
      if (var4) {
         var0.color(var1, var2, var3, 255);
      }

      return var0;
   }

   private static int a(int var0) {
      byte var1 = 1;
      if (var0 > 1) {
         var1 = 2;
      }

      if (var0 > 5) {
         var1 = 3;
      }

      if (var0 > 20) {
         var1 = 4;
      }

      if (var0 > 40) {
         var1 = 5;
      }

      return var1;
   }

   private static void b(int var0) {
      if (var0 > 0) {
         float var1 = (RANDOM.nextFloat() * 2.0F - 1.0F) * 0.3F;
         float var2 = (RANDOM.nextFloat() * 2.0F - 1.0F) * 0.3F;
         float var3 = (RANDOM.nextFloat() * 2.0F - 1.0F) * 0.3F;
         GL11.glTranslatef(var1, var2, var3);
      }

      Minecraft var4 = Minecraft.getMinecraft();
      GL11.glRotatef(180.0F - var4.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-var4.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
   }

   private static ResourceLocation a(Item var0) {
      if (var0 instanceof ItemPotion) {
         return POTION_BOTTLE;
      } else if (var0 instanceof ItemMonsterPlacer) {
         return SPAWN_EGG_OVERLAY;
      } else if (var0 instanceof ItemFireworkCharge) {
         return FIREWORK_CHARGE_OVERLAY;
      } else {
         return var0 instanceof ItemArmor && ((ItemArmor)var0).getArmorMaterial() == ArmorMaterial.LEATHER
            ? LEATHER_OVERLAYS.get(((ItemArmor)var0).armorType)
            : null;
      }
   }

   public static enum a implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
      SPRITE,
      MODEL;

      @Override
      public String a() {
         return null;
      }
   }
}
