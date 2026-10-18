package com.cheatbreaker.client.kazerui.module.modules.visual;

import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public final class BlockOutlineModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static com.cheatbreaker.client.kazerui.module.modules.visual.BlockOutlineModule instance;
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting outline = this.a("outline", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting fill = this.a("overlay", false);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting width = this.a("outlineThickness", 2.0F, 1.0F, 5.0F);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting lineColor = this.a("outlineColor", -855638017);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting fillColor = this.a("overlayColor", 810920703);

   public BlockOutlineModule() {
      super("BLOCK_OUTLINE", false);
      instance = this;
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(this.outline, var1x -> var1x.a(this.width, this.lineColor));
      var1.a(this.fill, var1x -> var1x.a(this.fillColor));
   }

   public static boolean a(AxisAlignedBB var0) {
      com.cheatbreaker.client.kazerui.module.modules.visual.BlockOutlineModule var1 = instance;
      if (var1 != null && var1.a()) {
         GL11.glPushAttrib(1048575);

         try {
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDepthMask(false);
            if (var1.fill.b()) {
               GL11.glDisable(2884);
               com.cheatbreaker.client.kazerui.module.modules.visual.BoxRenderHelper.a(var1.fillColor.a(0.0F));
               com.cheatbreaker.client.kazerui.module.modules.visual.BoxRenderHelper.b(var0.minX, var0.minY, var0.minZ, var0.maxX, var0.maxY, var0.maxZ);
            }

            if (var1.outline.b()) {
               GL11.glLineWidth(var1.width.b());
               com.cheatbreaker.client.kazerui.module.modules.visual.BoxRenderHelper.a(var1.lineColor.a(0.0F));
               com.cheatbreaker.client.kazerui.module.modules.visual.BoxRenderHelper.a(var0.minX, var0.minY, var0.minZ, var0.maxX, var0.maxY, var0.maxZ);
            }
         } finally {
            GL11.glPopAttrib();
         }

         return true;
      } else {
         return false;
      }
   }
}
