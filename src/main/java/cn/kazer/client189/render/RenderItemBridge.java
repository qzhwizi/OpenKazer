package cn.kazer.client189.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;

public final class RenderItemBridge {
   public float zLevel;

   public void a(FontRenderer var1, TextureManager var2, ItemStack var3, int var4, int var5) {
      RenderItem var6 = Minecraft.getMinecraft().getRenderItem();
      float var7 = var6.zLevel;
      var6.zLevel = this.zLevel;

      try {
         var6.renderItemAndEffectIntoGUI(var3, var4, var5);
      } finally {
         var6.zLevel = var7;
      }
   }

   public void b(FontRenderer var1, TextureManager var2, ItemStack var3, int var4, int var5) {
      this.a(var1, var2, var3, var4, var5);
   }
}
