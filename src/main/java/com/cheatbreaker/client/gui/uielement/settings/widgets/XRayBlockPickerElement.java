package com.cheatbreaker.client.gui.uielement.settings.widgets;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class XRayBlockPickerElement extends com.cheatbreaker.client.gui.uielement.UIElement {
   private String lIIIIlIIllIIlIIlIIIlIIllI;
   private List IllIIIIIIIlIlIllllIIllIII;
   private cn.kazer.client189.render.RenderItemBridge lIIIIllIIlIlIllIIIlIllIlI = new cn.kazer.client189.render.RenderItemBridge();

   public XRayBlockPickerElement(List var1, String var2, float var3) {
      super(var3);
      this.height = 220;
      this.IllIIIIIIIlIlIllllIIllIII = var1;
      this.lIIIIlIIllIIlIIlIIIlIIllI = var2;
   }

   @Override
   public void a(int var1, int var2, float var3) {
      com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.a(this.lIIIIlIIllIIlIIlIIIlIIllI.toUpperCase(), (float)(this.x + 10), (float)(this.y + 2), -1895825408);
      Minecraft var4 = Minecraft.getMinecraft();
      List var5 = com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.xray.A();
      cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
      cn.kazer.client189.render.GlStateBridge.a(32826);
      RenderHelper.enableStandardItemLighting();
      RenderHelper.enableGUIStandardItemLighting();
      int var6 = 0;
      int var7 = 0;

      for (Block var9 : Block.blockRegistry) {
         Item var11 = Item.getItemFromBlock(var9);
         if (var11 != null) {
            if (var6 >= 15) {
               var6 = 0;
               var7++;
            }

            int var12 = this.x + 12 + var6 * 20;
            int var13 = this.y + 14 + var7 * 20;
            boolean var10 = var1 > (var12 - 2) * this.scale
               && var1 < (var12 + 18) * this.scale
               && var2 > (var13 - 2 + this.yOffset) * this.scale
               && var2 < (var13 + 18 + this.yOffset) * this.scale;
            if (var5.contains(Item.getIdFromItem(var11))) {
               cn.kazer.client189.render.DrawRect.a(var12 - 2, var13 - 2, var12 + 18, var13 + 18, 2130771712);
            } else if (var10) {
               cn.kazer.client189.render.DrawRect.a(var12 - 2, var13 - 2, var12 + 18, var13 + 18, 1325400319);
            }

            this.lIIIIllIIlIlIllIIIlIllIlI.b(var4.fontRendererObj, var4.getTextureManager(), new ItemStack(var11), var12, var13);
            var6++;
         }
      }

      RenderHelper.disableStandardItemLighting();
      cn.kazer.client189.render.GlStateBridge.b(32826);
      cn.kazer.client189.render.GlStateBridge.b(3042);
   }

   @Override
   public void a(int var1, int var2, int var3) {
      try {
         List var4 = com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.xray.A();
         int var5 = 0;
         int var6 = 0;

         for (Block var8 : Block.blockRegistry) {
            Item var10 = Item.getItemFromBlock(var8);
            if (var10 != null) {
               if (var5 >= 15) {
                  var5 = 0;
                  var6++;
               }

               int var11 = this.x + 12 + var5 * 20;
               int var12 = this.y + 14 + var6 * 20;
               boolean var9 = var1 > (var11 - 2) * this.scale
                  && var1 < (var11 + 18) * this.scale
                  && var2 > (var12 - 2 + this.yOffset) * this.scale
                  && var2 < (var12 + 18 + this.yOffset) * this.scale;
               if (var9 && var3 == 0) {
                  int var14 = Item.getIdFromItem(var10);
                  if (var4.contains(var14)) {
                     com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.xray.A().removeIf(var1x -> var1x == var14);
                  } else {
                     com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.xray.A().add(var14);
                  }

                  if (com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.xray.g()) {
                     Minecraft.getMinecraft().renderGlobal.loadRenderers();
                  }

                  Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
               }

               var5++;
            }
         }
      } catch (Exception var15) {
         var15.printStackTrace();
      }
   }
}
