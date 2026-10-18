package com.cheatbreaker.client.module.modules;

import java.io.IOException;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.ResourcePackRepository.Entry;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public final class PackDisplayModule extends com.cheatbreaker.client.module.Module {
   private final com.cheatbreaker.client.settings.Setting showPackImage;
   private final com.cheatbreaker.client.settings.Setting showBackground;
   private final com.cheatbreaker.client.settings.Setting backgroundColor;
   private ResourceLocation defaultIconLocation;

   public PackDisplayModule() {
      super("Pack Display");
      this.b(com.cheatbreaker.client.gui.hudeditor.GuiAnchor.LEFT_TOP);
      this.b(0.0F, 12.0F);
      this.c(true);
      this.showPackImage = new com.cheatbreaker.client.settings.Setting(this, "Show Pack Image").a(false);
      this.showBackground = new com.cheatbreaker.client.settings.Setting(this, "Show Background").a(false);
      this.backgroundColor = new com.cheatbreaker.client.settings.Setting(this, "Background Color")
         .a(1862270976)
         .a(Integer.MIN_VALUE, Integer.MAX_VALUE)
         .a(this.showBackground);
      this.a("[Default]", 1.5F);
      this.a(com.cheatbreaker.client.event.event.RenderHudEvent.class, this::a);
      this.a(com.cheatbreaker.client.event.event.RenderHudEditorEvent.class, this::a);
   }

   com.cheatbreaker.client.settings.Setting a() {
      return this.showPackImage;
   }

   private void a(com.cheatbreaker.client.event.event.RenderHudEvent var1) {
      if (this.q()
         && this.minecraft.thePlayer != null
         && !(this.minecraft.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.HudEditorScreen)
         && !(this.minecraft.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.ModulePlacementScreen)) {
         this.b(var1);
      }
   }

   private void a(com.cheatbreaker.client.event.event.RenderHudEditorEvent var1) {
      if (this.q()) {
         if (this.minecraft.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.ModulePlacementScreen || this.minecraft.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.HudEditorScreen) {
            this.b(var1);
         }
      }
   }

   private void b(com.cheatbreaker.client.event.event.RenderHudEvent var1) {
      boolean var2 = (Boolean)this.showPackImage.d();
      boolean var3 = (Boolean)this.showBackground.d();
      Entry var4 = com.cheatbreaker.client.module.modules.PackDisplayUtil.a(this.minecraft.getResourcePackRepository().getRepositoryEntries());
      String var5 = com.cheatbreaker.client.module.modules.PackDisplayUtil.a(var4 == null ? null : var4.getResourcePackName());
      int var6 = this.minecraft.fontRendererObj.getStringWidth(var5);
      float[] var7 = com.cheatbreaker.client.module.modules.PackDisplayUtil.a(var2, true, var3, var6, this.minecraft.fontRendererObj.FONT_HEIGHT);
      this.c(var7[0], var7[1]);
      boolean var8 = GL11.glIsEnabled(2929);
      boolean var9 = GL11.glIsEnabled(3042);
      GL11.glPushMatrix();
      cn.kazer.client189.render.GlStateBridge.b(2929);

      try {
         this.a(var1.a());
         if (var3) {
            cn.kazer.client189.render.DrawRect.a(0.0F, 0.0F, var7[0], var7[1], this.backgroundColor.a());
         }

         if (var2) {
            this.a(var4);
            cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
            cn.kazer.client189.render.GlStateBridge.a(3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
         }

         int var10 = com.cheatbreaker.client.module.modules.PackDisplayUtil.b(var2, var3);
         int var11 = com.cheatbreaker.client.module.modules.PackDisplayUtil.a(var2, this.minecraft.fontRendererObj.FONT_HEIGHT, var3);
         cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
         if (var3) {
            this.minecraft.fontRendererObj.drawString(var5, var10, var11, -1);
         } else {
            this.minecraft.fontRendererObj.drawString(var5, var10, var11, -1, true);
         }
      } finally {
         GL11.glPopMatrix();
         if (var8) {
            cn.kazer.client189.render.GlStateBridge.a(2929);
         }

         if (!var9) {
            cn.kazer.client189.render.GlStateBridge.b(3042);
         }
      }
   }

   private void a(Entry var1) {
      TextureManager var2 = this.minecraft.getTextureManager();
      if (var1 != null) {
         var1.bindTexturePackIcon(var2);
      } else {
         if (this.defaultIconLocation == null) {
            DynamicTexture var3;
            try {
               var3 = new DynamicTexture(this.minecraft.getResourcePackRepository().rprDefaultResourcePack.getPackImage());
            } catch (IOException var5) {
               var3 = TextureUtil.missingTexture;
            }

            this.defaultIconLocation = var2.getDynamicTextureLocation("texturepackicon", var3);
         }

         var2.bindTexture(this.defaultIconLocation);
      }
   }
}
