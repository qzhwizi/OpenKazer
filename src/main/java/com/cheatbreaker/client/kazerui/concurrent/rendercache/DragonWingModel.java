package com.cheatbreaker.client.kazerui.concurrent.rendercache;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public final class DragonWingModel extends ModelBase {
   private final ModelRenderer wing;
   private final ModelRenderer tip;

   public DragonWingModel() {
      this.textureWidth = 256;
      this.textureHeight = 256;
      this.setTextureOffset("wing.skin", -56, 88);
      this.setTextureOffset("wingtip.skin", -56, 144);
      this.setTextureOffset("wing.bone", 112, 88);
      this.setTextureOffset("wingtip.bone", 112, 136);
      this.wing = new ModelRenderer(this, "wing");
      this.wing.setRotationPoint(-12.0F, 5.0F, 2.0F);
      this.wing.addBox("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8);
      this.wing.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
      this.tip = new ModelRenderer(this, "wingtip");
      this.tip.setRotationPoint(-56.0F, 0.0F, 0.0F);
      this.tip.addBox("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4);
      this.tip.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
      this.wing.addChild(this.tip);
   }

   public void a(float var1, float var2, ResourceLocation var3) {
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 771);
      Minecraft.getMinecraft().getTextureManager().bindTexture(var3);
      GlStateManager.pushMatrix();
      GlStateManager.scale(var1, var1, var1);
      GL11.glRotatef(15.0F, 1.0F, 0.0F, 0.0F);
      GL11.glTranslatef(0.0F, 0.5F, 0.25F);
      float var4 = (float)(System.currentTimeMillis() % 2000L) / 2000.0F * (float) Math.PI * 2.0F;

      for (int var5 = 0; var5 < 2; var5++) {
         GL11.glEnable(2884);
         this.wing.rotateAngleX = -0.125F - (float)Math.cos(var4) * 0.2F;
         this.wing.rotateAngleY = 0.75F;
         this.wing.rotateAngleZ = (float)(Math.sin(var4) + 0.125) * 0.8F;
         this.tip.rotateAngleZ = (float)(Math.sin(var4 + 2.0F) + 0.5) * 0.75F;
         this.wing.render(var2);
         GlStateManager.scale(-1.0F, 1.0F, 1.0F);
         if (var5 == 0) {
            GL11.glCullFace(1028);
         }

         GlStateManager.shadeModel(7424);
      }

      GlStateManager.popMatrix();
      GlStateManager.shadeModel(7425);
      GL11.glCullFace(1029);
      GL11.glDisable(2884);
   }
}
