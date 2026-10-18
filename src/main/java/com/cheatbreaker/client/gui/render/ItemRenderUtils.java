package com.cheatbreaker.client.gui.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public final class ItemRenderUtils {
   private static final int[] colorCodes = new int[]{
      0,
      170,
      43520,
      43690,
      11141120,
      11141290,
      16755200,
      11184810,
      5592405,
      5592575,
      5635925,
      5636095,
      16733525,
      16733695,
      16777045,
      16777215,
      0,
      42,
      10752,
      10794,
      2752512,
      2752554,
      2763264,
      2763306,
      1381653,
      1381695,
      1392405,
      1392447,
      4134165,
      4134207,
      4144917,
      4144959
   };

   public static int a(char var0, boolean var1) {
      return colorCodes[var1 ? "0123456789abcdef".indexOf(var0) : "0123456789abcdef".indexOf(var0) + 16];
   }

   public static void a(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, float var9) {
      a(var0, var1, var2, var3, var4, var5, var6, var7, var8, var8, var8, var8, var9);
   }

   public static void a(ResourceLocation var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, float var10) {
      a(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var9, var9, var9, var10);
   }

   public static void a(
      ResourceLocation var0,
      int var1,
      int var2,
      int var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      int var9,
      int var10,
      int var11,
      int var12,
      float var13
   ) {
      Minecraft.getMinecraft().getTextureManager().bindTexture(var0);
      a(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
   }

   public static void a(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, float var12) {
      cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
      cn.kazer.client189.render.GlStateBridge.a(3042);
      OpenGlHelper.glBlendFunc(770, 771, 1, 0);
      cn.kazer.client189.render.GlStateBridge.b(770, 771);
      int var13 = var6 - var10 - var11;
      int var14 = var7 - var8 - var9;
      int var15 = var4 - var10 - var11;
      int var16 = var5 - var8 - var9;
      int var17 = var15 / var13;
      int var18 = var15 % var13;
      int var19 = var16 / var14;
      int var20 = var16 % var14;
      a(var0, var1, var2, var3, var10, var8, var12);
      a(var0 + var10 + var15, var1, var2 + var10 + var13, var3, var11, var8, var12);
      a(var0, var1 + var8 + var16, var2, var3 + var8 + var14, var10, var9, var12);
      a(var0 + var10 + var15, var1 + var8 + var16, var2 + var10 + var13, var3 + var8 + var14, var11, var9, var12);

      for (int var21 = 0; var21 < var17 + (var18 > 0 ? 1 : 0); var21++) {
         a(var0 + var10 + var21 * var13, var1, var2 + var10, var3, var21 == var17 ? var18 : var13, var8, var12);
         a(var0 + var10 + var21 * var13, var1 + var8 + var16, var2 + var10, var3 + var8 + var14, var21 == var17 ? var18 : var13, var9, var12);

         for (int var22 = 0; var22 < var19 + (var20 > 0 ? 1 : 0); var22++) {
            a(
               var0 + var10 + var21 * var13,
               var1 + var8 + var22 * var14,
               var2 + var10,
               var3 + var8,
               var21 == var17 ? var18 : var13,
               var22 == var19 ? var20 : var14,
               var12
            );
         }
      }

      for (int var23 = 0; var23 < var19 + (var20 > 0 ? 1 : 0); var23++) {
         a(var0, var1 + var8 + var23 * var14, var2, var3 + var8, var10, var23 == var19 ? var20 : var14, var12);
         a(var0 + var10 + var15, var1 + var8 + var23 * var14, var2 + var10 + var13, var3 + var8, var11, var23 == var19 ? var20 : var14, var12);
      }
   }

   public static void a(int var0, int var1, int var2, int var3, int var4, int var5, float var6) {
      float var7 = 0.00390625F;
      float var8 = 0.00390625F;
      cn.kazer.client189.render.ImmediateTessellator var9 = cn.kazer.client189.render.ImmediateTessellator.instance;
      var9.a();
      var9.a(var0 + 0, var1 + var5, var6, (var2 + 0) * var7, (var3 + var5) * var8);
      var9.a(var0 + var4, var1 + var5, var6, (var2 + var4) * var7, (var3 + var5) * var8);
      var9.a(var0 + var4, var1 + 0, var6, (var2 + var4) * var7, (var3 + 0) * var8);
      var9.a(var0 + 0, var1 + 0, var6, (var2 + 0) * var7, (var3 + 0) * var8);
      var9.b();
   }

   public static void a(FontRenderer var0, ItemStack var1, int var2, int var3) {
      a(var0, var1, var2, var3, true, true);
   }

   public static void a(FontRenderer var0, ItemStack var1, int var2, int var3, boolean var4, boolean var5) {
      a(var0, var1, var2, var3, var4, var5, 0);
   }

   public static void a(FontRenderer var0, ItemStack var1, int var2, int var3, boolean var4, boolean var5, int var6) {
      if (var1 != null && (var4 || var5)) {
         if (var1.isItemDamaged() && var4) {
            int var7 = (int)Math.round(13.0 - var1.getItemDamage() * 13.0 / var1.getMaxDamage());
            int var8 = (int)Math.round(255.0 - var1.getItemDamage() * 255.0 / var1.getMaxDamage());
            cn.kazer.client189.render.GlStateBridge.b(2896);
            cn.kazer.client189.render.GlStateBridge.b(2929);
            cn.kazer.client189.render.GlStateBridge.b(3553);
            cn.kazer.client189.render.ImmediateTessellator var9 = cn.kazer.client189.render.ImmediateTessellator.instance;
            int var10 = 255 - var8 << 16 | var8 << 8;
            int var11 = (255 - var8) / 4 << 16 | 16128;
            int var12 = var3 + 13 + var6;
            a(var9, var2 + 2, var12, 13, 2, 0);
            a(var9, var2 + 2, var12, 12, 1, var11);
            a(var9, var2 + 2, var12, var7, 1, var10);
            cn.kazer.client189.render.GlStateBridge.a(3553);
            cn.kazer.client189.render.GlStateBridge.a(2896);
            cn.kazer.client189.render.GlStateBridge.a(2929);
            cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
         }

         if (var5) {
            int var13 = 0;
            if (var1.getMaxStackSize() > 1) {
               var13 = a(Minecraft.getMinecraft().thePlayer, var1.getItem(), var1.getItemDamage());
            } else if (var1.getItem().equals(Items.bow)) {
               var13 = a(Minecraft.getMinecraft().thePlayer, Items.arrow);
            }

            if (var13 > 1) {
               String var14 = "" + var13;
               cn.kazer.client189.render.GlStateBridge.b(2896);
               cn.kazer.client189.render.GlStateBridge.b(2929);
               var0.drawStringWithShadow(var14, var2 + 19 - 2 - var0.getStringWidth(var14), var3 + 6 + 3, 16777215);
               cn.kazer.client189.render.GlStateBridge.a(2896);
               cn.kazer.client189.render.GlStateBridge.a(2929);
            }
         }
      }
   }

   public static void a(cn.kazer.client189.render.ImmediateTessellator var0, int var1, int var2, int var3, int var4, int var5) {
      var0.a();
      var0.b(var5);
      var0.a((double)(var1 + 0), (double)(var2 + 0), 0.0);
      var0.a((double)(var1 + 0), (double)(var2 + var4), 0.0);
      var0.a((double)(var1 + var3), (double)(var2 + var4), 0.0);
      var0.a((double)(var1 + var3), (double)(var2 + 0), 0.0);
      var0.b();
   }

   public static int a(EntityPlayer var0, Item var1) {
      return a(var0, var1, -1);
   }

   public static int a(EntityPlayer var0, Item var1, int var2) {
      int var3 = 0;

      for (int var4 = 0; var4 < var0.inventory.mainInventory.length; var4++) {
         if (var0.inventory.mainInventory[var4] != null
            && var1.equals(var0.inventory.mainInventory[var4].getItem())
            && (var2 == -1 || var0.inventory.mainInventory[var4].getItemDamage() == var2)) {
            var3 += var0.inventory.mainInventory[var4].stackSize;
         }
      }

      return var3;
   }
}
