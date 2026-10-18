package com.cheatbreaker.client.module.modules.options;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

public class ArmourItemDisplay {
   private static final int DAMAGE_BAR_Y_OFFSET = 1;
   public final ItemStack lIIIIlIIllIIlIIlIIIlIIllI;
   public final int lIIIIIIIIIlIllIIllIlIIlIl;
   public final int IlllIIIlIlllIllIlIIlllIlI;
   public final int IIIIllIlIIIllIlllIlllllIl;
   private int IIIIllIIllIIIIllIllIIIlIl;
   private int IlIlIIIlllIIIlIlllIlIllIl;
   private String IIIllIllIlIlllllllIlIlIII = "";
   private int IllIIIIIIIlIlIllllIIllIII;
   private String lIIIIllIIlIlIllIIIlIllIlI = "";
   private int IlllIllIlIIIIlIIlIIllIIIl;
   private final boolean IlIlllIIIIllIllllIllIIlIl;
   private Minecraft llIIlllIIIIlllIllIlIlllIl = Minecraft.getMinecraft();

   public static String a(String var0) {
      return var0.replaceAll("(?i)§[0-9a-fklmnor]", "");
   }

   public ArmourItemDisplay(ItemStack var1, int var2, int var3, int var4, boolean var5) {
      this.lIIIIlIIllIIlIIlIIIlIIllI = var1;
      this.lIIIIIIIIIlIllIIllIlIIlIl = var2;
      this.IlllIIIlIlllIllIlIIlllIlI = var3;
      this.IIIIllIlIIIllIlllIlllllIl = var4;
      this.IlIlllIIIIllIllllIllIIlIl = var5;
      this.c();
   }

   public int a() {
      return this.IIIIllIIllIIIIllIllIIIlIl;
   }

   public int b() {
      return this.IlIlIIIlllIIIlIlllIlIllIl;
   }

   private void c() {
      int var1 = this.IlIlIIIlllIIIlIlllIlIllIl = (Boolean) ArmourStatusModule.itemName.d()
         ? Math.max(Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT * 2, this.IlllIIIlIlllIllIlIIlllIlI)
         : Math.max(this.llIIlllIIIIlllIllIlIlllIl.fontRendererObj.FONT_HEIGHT, this.IlllIIIlIlllIllIlIIlllIlI);
      if (this.lIIIIlIIllIIlIIlIIIlIIllI != null) {
         int var2 = 1;
         int var3 = 1;
         if ((this.IlIlllIIIIllIllllIllIIlIl && (Boolean)ArmourStatusModule.showArmourDamage.d() || !this.IlIlllIIIIllIllllIllIIlIl && (Boolean)ArmourStatusModule.showItemDamage.d())
            && this.lIIIIlIIllIIlIIlIIIlIIllI.isItemStackDamageable()) {
            var3 = this.lIIIIlIIllIIlIIlIIIlIIllI.getMaxDamage() + 1;
            var2 = var3 - this.lIIIIlIIllIIlIIlIIIlIIllI.getItemDamage();
            if (((String)ArmourStatusModule.damageDisplayType.d()).equalsIgnoreCase("value")) {
               this.lIIIIllIIlIlIllIIIlIllIlI = "§"
                  + com.cheatbreaker.client.module.modules.options.DamageColorThreshold.a(ArmourStatusModule.damageColors, ((String)ArmourStatusModule.damageThreshold.d()).equalsIgnoreCase("percent") ? var2 * 100 / var3 : var2)
                  + var2
                  + ((Boolean) ArmourStatusModule.showMaxDamage.d() ? "/" + var3 : "");
            } else if (((String)ArmourStatusModule.damageDisplayType.d()).equalsIgnoreCase("percent")) {
               this.lIIIIllIIlIlIllIIIlIllIlI = "§"
                  + com.cheatbreaker.client.module.modules.options.DamageColorThreshold.a(ArmourStatusModule.damageColors, ((String)ArmourStatusModule.damageThreshold.d()).equalsIgnoreCase("percent") ? var2 * 100 / var3 : var2)
                  + var2 * 100 / var3
                  + "%";
            }
         }

         this.IlllIllIlIIIIlIIlIIllIIIl = this.llIIlllIIIIlllIllIlIlllIl.fontRendererObj.getStringWidth(a(this.lIIIIllIIlIlIllIIIlIllIlI));
         this.IIIIllIIllIIIIllIllIIIlIl = this.IIIIllIlIIIllIlllIlllllIl
            + this.lIIIIIIIIIlIllIIllIlIIlIl
            + this.IIIIllIlIIIllIlllIlllllIl
            + this.IlllIllIlIIIIlIIlIIllIIIl;
         if ((Boolean)ArmourStatusModule.itemName.d()) {
            this.IIIllIllIlIlllllllIlIlIII = this.lIIIIlIIllIIlIIlIIIlIIllI.getDisplayName();
            this.IIIIllIIllIIIIllIllIIIlIl = this.IIIIllIlIIIllIlllIlllllIl
               + this.lIIIIIIIIIlIllIIllIlIIlIl
               + this.IIIIllIlIIIllIlllIlllllIl
               + Math.max(this.llIIlllIIIIlllIllIlIlllIl.fontRendererObj.getStringWidth(a(this.IIIllIllIlIlllllllIlIlIII)), this.IlllIllIlIIIIlIIlIIllIIIl);
         }

         this.IllIIIIIIIlIlIllllIIllIII = this.llIIlllIIIIlllIllIlIlllIl.fontRendererObj.getStringWidth(a(this.IIIllIllIlIlllllllIlIlIII));
      }
   }

   public void a(float var1, float var2) {
      cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
      cn.kazer.client189.render.GlStateBridge.a(32826);
      RenderHelper.enableStandardItemLighting();
      RenderHelper.enableGUIStandardItemLighting();
      ArmourStatusModule.renderItem.zLevel = -10.0F;
      com.cheatbreaker.client.gui.hudeditor.GuiAnchor var3 = com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.armourStatus.r();
      boolean var4 = com.cheatbreaker.client.gui.hudeditor.AnchorHelper.c(var3) == com.cheatbreaker.client.gui.hudeditor.AnchorAlignment.RIGHT;
      int var5 = (int)var2 + ((Boolean) ArmourStatusModule.itemName.d() ? this.IlIlIIIlllIIIlIlllIlIllIl / 2 : this.IlIlIIIlllIIIlIlllIlIllIl / 4);
      if (var4) {
         ArmourStatusModule.renderItem
            .a(
               this.llIIlllIIIIlllIllIlIlllIl.fontRendererObj,
               this.llIIlllIIIIlllIllIlIlllIl.getTextureManager(),
               this.lIIIIlIIllIIlIIlIIIlIIllI,
               (int)(var1 - (this.lIIIIIIIIIlIllIIllIlIIlIl + this.IIIIllIlIIIllIlllIlllllIl)),
               (int)var2
            );
         com.cheatbreaker.client.gui.render.ItemRenderUtils.a(
            this.llIIlllIIIIlllIllIlIlllIl.fontRendererObj,
            this.lIIIIlIIllIIlIIlIIIlIIllI,
            (int)(var1 - (this.lIIIIIIIIIlIllIIllIlIIlIl + this.IIIIllIlIIIllIlllIlllllIl)),
            (int)var2,
            (Boolean) ArmourStatusModule.damageOverlay.d(),
            (Boolean) ArmourStatusModule.itemCount.d(),
            1
         );
         RenderHelper.disableStandardItemLighting();
         cn.kazer.client189.render.GlStateBridge.b(32826);
         cn.kazer.client189.render.GlStateBridge.b(3042);
         this.llIIlllIIIIlllIllIlIlllIl
            .fontRendererObj
            .drawStringWithShadow(
               this.IIIllIllIlIlllllllIlIlIII + "§r",
               (int)var1 - (this.IIIIllIlIIIllIlllIlllllIl + this.lIIIIIIIIIlIllIIllIlIIlIl + this.IIIIllIlIIIllIlllIlllllIl) - this.IllIIIIIIIlIlIllllIIllIII,
               (int)var2,
               16777215
            );
         this.llIIlllIIIIlllIllIlIlllIl
            .fontRendererObj
            .drawStringWithShadow(
               this.lIIIIllIIlIlIllIIIlIllIlI + "§r",
               (int)var1 - (this.IIIIllIlIIIllIlllIlllllIl + this.lIIIIIIIIIlIllIIllIlIIlIl + this.IIIIllIlIIIllIlllIlllllIl) - this.IlllIllIlIIIIlIIlIIllIIIl,
               var5,
               16777215
            );
      } else {
         ArmourStatusModule.renderItem
            .a(
               this.llIIlllIIIIlllIllIlIlllIl.fontRendererObj,
               this.llIIlllIIIIlllIllIlIlllIl.getTextureManager(),
               this.lIIIIlIIllIIlIIlIIIlIIllI,
               (int)var1,
               (int)var2
            );
         com.cheatbreaker.client.gui.render.ItemRenderUtils.a(
            this.llIIlllIIIIlllIllIlIlllIl.fontRendererObj,
            this.lIIIIlIIllIIlIIlIIIlIIllI,
            (int)var1,
            (int)var2,
            (Boolean) ArmourStatusModule.damageOverlay.d(),
            (Boolean) ArmourStatusModule.itemCount.d(),
            1
         );
         RenderHelper.disableStandardItemLighting();
         cn.kazer.client189.render.GlStateBridge.b(32826);
         cn.kazer.client189.render.GlStateBridge.b(3042);
         this.llIIlllIIIIlllIllIlIlllIl
            .fontRendererObj
            .drawStringWithShadow(
               this.IIIllIllIlIlllllllIlIlIII + "§r", (int)var1 + this.lIIIIIIIIIlIllIIllIlIIlIl + this.IIIIllIlIIIllIlllIlllllIl, (int)var2, 16777215
            );
         this.llIIlllIIIIlllIllIlIlllIl
            .fontRendererObj
            .drawStringWithShadow(
               this.lIIIIllIIlIlIllIIIlIllIlI + "§r", (int)var1 + this.lIIIIIIIIIlIllIIllIlIIlIl + this.IIIIllIlIIIllIlllIlllllIl, var5, 16777215
            );
      }

      cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
   }
}
