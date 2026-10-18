package com.cheatbreaker.client.module.modules.render;

import com.cheatbreaker.client.module.modules.options.ArmourStatusModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class CooldownEntry {
   private final String name;
   private final int itemId;
   private long duration;
   private long time;
   private final Minecraft IIIIllIIllIIIIllIllIIIlIl = Minecraft.getMinecraft();
   private final ItemStack item;

   public CooldownEntry(String var1, int var2, long var3) {
      this.name = var1;
      this.itemId = var2;
      this.duration = var3;
      this.time = System.currentTimeMillis();
      this.item = new ItemStack(Item.getItemById(var2));
   }

   public void a(com.cheatbreaker.client.settings.Setting var1, float var2, float var3, int var4) {
      byte var6 = 17;
      GL11.glPushMatrix();
      float var7 = ArmourStatusModule.renderItem.zLevel;
      ArmourStatusModule.renderItem.zLevel = -200.0F;
      float var8 = 1.35F;
      GL11.glTranslatef(-0.5F, -1.0F, 0.0F);
      GL11.glScalef(var8, var8, var8);
      RenderHelper.enableStandardItemLighting();
      ArmourStatusModule.renderItem
         .a(
            this.IIIIllIIllIIIIllIllIIIlIl.fontRendererObj,
            this.IIIIllIIllIIIIllIllIIIlIl.getTextureManager(),
            this.item,
            (int)((var2 + var6 / 2) / var8),
            (int)((var3 + var6 / 2) / var8)
         );
      RenderHelper.disableStandardItemLighting();
      GL11.glPopMatrix();
      ArmourStatusModule.renderItem.zLevel = var7;
      double var9 = this.duration - (System.currentTimeMillis() - this.time);
      if (!(var9 <= 0.0)) {
         if (((String)var1.d()).equalsIgnoreCase("Bright")) {
            cn.kazer.client189.render.GlStateBridge.a(0.0F, 0.0F, 0.0F, 0.2F);
            com.cheatbreaker.client.gui.render.RenderUtils.a(
               (double)(var2 + var6), (double)(var3 + var6), (double)var6, 0.0, (double)((float)this.duration / 3.95F), (int)this.duration, var9
            );
            cn.kazer.client189.render.GlStateBridge.a(0.9F, 0.9F, 0.9F, 1.0F);
            com.cheatbreaker.client.gui.render.RenderUtils.a(
               (double)(var2 + var6),
               (double)(var3 + var6),
               (double)(var6 + 0.1F),
               (double)(var6 - 2),
               (double)((float)this.duration / 3.95F),
               (int)this.duration,
               (double)this.duration
            );
            cn.kazer.client189.render.GlStateBridge.a(0.35F, 0.35F, 0.35F, 0.6F);
            com.cheatbreaker.client.gui.render.RenderUtils.a(
               (double)(var2 + var6),
               (double)(var3 + var6),
               (double)(var6 + 0.1F),
               (double)(var6 - 2),
               (double)((float)this.duration / 3.95F),
               (int)this.duration,
               var9
            );
         } else if (((String)var1.d()).equalsIgnoreCase("Dark")) {
            cn.kazer.client189.render.GlStateBridge.a(0.0F, 0.0F, 0.0F, 0.2F);
            com.cheatbreaker.client.gui.render.RenderUtils.a(var2 + var6, var3 + var6, var6);
            cn.kazer.client189.render.GlStateBridge.a(0.0F, 0.0F, 0.0F, 0.2F);
            com.cheatbreaker.client.gui.render.RenderUtils.a(
               (double)(var2 + var6), (double)(var3 + var6), (double)var6, 0.0, (double)((float)this.duration / 3.95F), (int)this.duration, var9
            );
            cn.kazer.client189.render.GlStateBridge.a(0.0F, 0.9F, 0.0F, 1.0F);
            com.cheatbreaker.client.gui.render.RenderUtils.a(
               (double)(var2 + var6),
               (double)(var3 + var6),
               (double)(var6 + 0.1F),
               (double)(var6 - 2),
               (double)((float)this.duration / 3.95F),
               (int)this.duration,
               (double)this.duration
            );
            cn.kazer.client189.render.GlStateBridge.a(0.0F, 0.5F, 0.0F, 1.0F);
            com.cheatbreaker.client.gui.render.RenderUtils.a(
               (double)(var2 + var6),
               (double)(var3 + var6),
               (double)(var6 + 0.1F),
               (double)(var6 - 2),
               (double)((float)this.duration / 3.95F),
               (int)this.duration,
               var9
            );
         } else if (((String)var1.d()).equalsIgnoreCase("Colored")) {
            float var11 = (var4 >> 24 & 0xFF) / 255.0F;
            float var5 = (var4 >> 16 & 0xFF) / 255.0F;
            float var12 = (var4 >> 8 & 0xFF) / 255.0F;
            float var13 = (var4 & 0xFF) / 255.0F;
            cn.kazer.client189.render.GlStateBridge.a(var5, var12, var13, 0.15F);
            com.cheatbreaker.client.gui.render.RenderUtils.a(var2 + var6, var3 + var6, var6);
            cn.kazer.client189.render.GlStateBridge.a(var5, var12, var13, 0.25F);
            com.cheatbreaker.client.gui.render.RenderUtils.a(
               (double)(var2 + var6), (double)(var3 + var6), (double)var6, 0.0, (double)((float)this.duration / 3.95F), (int)this.duration, var9
            );
            cn.kazer.client189.render.GlStateBridge.a(var5, var12, var13, 1.0F);
            com.cheatbreaker.client.gui.render.RenderUtils.a(
               (double)(var2 + var6),
               (double)(var3 + var6),
               (double)(var6 + 0.1F),
               (double)(var6 - 2),
               (double)((float)this.duration / 3.95F),
               (int)this.duration,
               (double)this.duration
            );
            cn.kazer.client189.render.GlStateBridge.a(var5, var12, var13, 0.15F);
            com.cheatbreaker.client.gui.render.RenderUtils.a(
               (double)(var2 + var6),
               (double)(var3 + var6),
               (double)(var6 + 0.1F),
               (double)(var6 - 2),
               (double)((float)this.duration / 3.95F),
               (int)this.duration,
               var9
            );
         }

         String var14 = String.format("%.1f", var9 / 1000.0);
         com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.c(var14, var2 + var6, var3 + var6 / 2 + 4.0F, -1);
      }
   }

   public boolean a() {
      return this.time < System.currentTimeMillis() - this.duration;
   }

   public void a(long var1) {
      this.duration = var1;
   }

   public void b() {
      this.time = System.currentTimeMillis();
   }

   public String c() {
      return this.name;
   }

   public long d() {
      return this.duration;
   }

   public int e() {
      return this.itemId;
   }
}
