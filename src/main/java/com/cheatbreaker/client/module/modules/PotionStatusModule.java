package com.cheatbreaker.client.module.modules;

import java.util.Collection;
import java.util.HashMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class PotionStatusModule extends com.cheatbreaker.client.module.Module {
   private final com.cheatbreaker.client.settings.Setting showWhileTying;
   private final com.cheatbreaker.client.settings.Setting showEffectName;
   private final com.cheatbreaker.client.settings.Setting colorOptionsLabel;
   private final com.cheatbreaker.client.settings.Setting nameColor;
   private final com.cheatbreaker.client.settings.Setting durationColor;
   private final com.cheatbreaker.client.settings.Setting blink;
   private final com.cheatbreaker.client.settings.Setting blinkDuration;
   private final ResourceLocation location = new ResourceLocation("textures/gui/container/inventory.png");
   private int ticks = 0;

   public PotionStatusModule() {
      super("Potion Effects");
      this.c(true);
      this.b(com.cheatbreaker.client.gui.hudeditor.GuiAnchor.LEFT_MIDDLE);
      this.b(0.0F, 4.0F);
      new com.cheatbreaker.client.settings.Setting(this, "label").a("General Options");
      this.showWhileTying = new com.cheatbreaker.client.settings.Setting(this, "Show While Typing").a(true);
      new com.cheatbreaker.client.settings.Setting(this, "Mode").a("Normal").a("Normal", "Compact");
      this.showEffectName = new com.cheatbreaker.client.settings.Setting(this, "Show Effect Name").a(true);
      new com.cheatbreaker.client.settings.Setting(this, "Show Effect Duration").a(true);
      new com.cheatbreaker.client.settings.Setting(this, "Show Effect Amplifier").a(true);
      new com.cheatbreaker.client.settings.Setting(this, "Formatted Durations").a(false);
      new com.cheatbreaker.client.settings.Setting(this, "Uppercase Potion Names").a(false);
      new com.cheatbreaker.client.settings.Setting(this, "Reversed Text").a(false);
      new com.cheatbreaker.client.settings.Setting(this, "Show In Inventory").a(true);
      new com.cheatbreaker.client.settings.Setting(this, "Hide Potion Status").a(false);
      new com.cheatbreaker.client.settings.Setting(this, "Effect Duration Bars").a(false);
      new com.cheatbreaker.client.settings.Setting(this, "label").a("Blink Options");
      this.blink = new com.cheatbreaker.client.settings.Setting(this, "Blink").a(true);
      this.blinkDuration = new com.cheatbreaker.client.settings.Setting(this, "Blink Duration").a(10).a(2, 20);
      new com.cheatbreaker.client.settings.Setting(this, "Show Background").a(false);
      new com.cheatbreaker.client.settings.Setting(this, "Border").a(false);
      new com.cheatbreaker.client.settings.Setting(this, "Text Shadow").a(true);
      new com.cheatbreaker.client.settings.Setting(this, "Color Name Based on Effect").a(false);
      new com.cheatbreaker.client.settings.Setting(this, "Color Info Based on Effect").a(false);
      this.colorOptionsLabel = new com.cheatbreaker.client.settings.Setting(this, "label").a("Color Options");
      this.nameColor = new com.cheatbreaker.client.settings.Setting(this, "Name Color").a(-1).a(Integer.MIN_VALUE, Integer.MAX_VALUE);
      this.durationColor = new com.cheatbreaker.client.settings.Setting(this, "Duration Color").a(-1).a(Integer.MIN_VALUE, Integer.MAX_VALUE);
      this.a(new ResourceLocation("client/icons/mods/speed_icon.png"), 28, 28);
      this.a(com.cheatbreaker.client.event.event.ClientTickEvent.class, this::a);
      this.a(com.cheatbreaker.client.event.event.RenderHudEditorEvent.class, this::a);
      this.a(com.cheatbreaker.client.event.event.RenderHudEvent.class, this::a);
   }

   private void a(com.cheatbreaker.client.event.event.ClientTickEvent var1) {
      this.ticks++;
   }

   private void a(com.cheatbreaker.client.event.event.RenderHudEvent var1) {
      GL11.glPushMatrix();
      if ((Boolean)this.showWhileTying.d() || !this.minecraft.ingameGUI.getChatGUI().getChatOpen()) {
         GL11.glPushMatrix();
         this.a(var1.a());
         com.cheatbreaker.client.gui.hudeditor.AnchorAlignment var2 = this.s();
         Collection var3 = this.minecraft.thePlayer.getActivePotionEffects();
         if (var3.isEmpty()) {
            GL11.glPopMatrix();
            GL11.glPopMatrix();
            return;
         }

         byte var4 = 0;
         int var5 = 0;
         byte var6 = 22;

         for (PotionEffect var8 : (Iterable<PotionEffect>) var3) {
            boolean var11 = this.c(var8.getDuration());
            int var12 = 0;
            if ((Boolean)this.showEffectName.d()) {
               String var10 = I18n.format(var8.getEffectName(), new Object[0]) + this.a(var8.getAmplifier());
               var12 = this.minecraft.fontRendererObj.getStringWidth(var10) + 20;
               if (var2 == com.cheatbreaker.client.gui.hudeditor.AnchorAlignment.RIGHT) {
                  this.minecraft.fontRendererObj.drawStringWithShadow(var10 + "§r", (int)this.width - var12, var4, this.nameColor.a());
               } else if (var2 == com.cheatbreaker.client.gui.hudeditor.AnchorAlignment.LEFT) {
                  this.minecraft.fontRendererObj.drawStringWithShadow(var10 + "§r", 20.0F, var4, this.nameColor.a());
               } else if (var2 == com.cheatbreaker.client.gui.hudeditor.AnchorAlignment.CENTER) {
                  this.minecraft.fontRendererObj.drawStringWithShadow(var10 + "§r", (int)this.width / 2 - var12 / 2 + 20, var4, this.nameColor.a());
               }

               if (var12 > var5) {
                  var5 = var12;
               }
            }

            String var15 = Potion.getDurationString(var8);
            int var13 = this.minecraft.fontRendererObj.getStringWidth(var15) + 20;
            if (var11) {
               if (var2 == com.cheatbreaker.client.gui.hudeditor.AnchorAlignment.RIGHT) {
                  this.minecraft
                     .fontRendererObj
                     .drawStringWithShadow(var15 + "§r", (int)this.width - var13, var4 + ((Boolean) this.showEffectName.d() ? 10 : 5), this.durationColor.a());
               } else if (var2 == com.cheatbreaker.client.gui.hudeditor.AnchorAlignment.LEFT) {
                  this.minecraft.fontRendererObj.drawStringWithShadow(var15 + "§r", 20.0F, var4 + ((Boolean) this.showEffectName.d() ? 10 : 5), this.durationColor.a());
               } else if (var2 == com.cheatbreaker.client.gui.hudeditor.AnchorAlignment.CENTER) {
                  this.minecraft
                     .fontRendererObj
                     .drawStringWithShadow(
                        var15 + "§r", (int)this.width / 2 - var13 / 2 + 20, var4 + ((Boolean) this.showEffectName.d() ? 10 : 5), this.durationColor.a()
                     );
               }
            }

            Potion var9;
            if ((var9 = Potion.potionTypes[var8.getPotionID()]).hasStatusIcon()) {
               cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
               this.minecraft.getTextureManager().bindTexture(this.location);
               int var14 = var9.getStatusIconIndex();
               if (var2 == com.cheatbreaker.client.gui.hudeditor.AnchorAlignment.RIGHT) {
                  com.cheatbreaker.client.gui.render.RenderUtils.a(this.width - 20.0F, (float)var4, (float)(var14 % 8 * 18), (float)(198 + var14 / 8 * 18), 18, 18);
               } else if (var2 == com.cheatbreaker.client.gui.hudeditor.AnchorAlignment.LEFT) {
                  com.cheatbreaker.client.gui.render.RenderUtils.a(0.0F, (float)var4, (float)(var14 % 8 * 18), (float)(198 + var14 / 8 * 18), 18, 18);
               } else if (var2 == com.cheatbreaker.client.gui.hudeditor.AnchorAlignment.CENTER) {
                  com.cheatbreaker.client.gui.render.RenderUtils.a(this.width / 2.0F - var12 / 2, (float)var4, (float)(var14 % 8 * 18), (float)(198 + var14 / 8 * 18), 18, 18);
               }
            }

            if (var13 > var5) {
               var5 = var13;
            }

            var4 += var6;
         }

         this.c(var5, var4);
         GL11.glPopMatrix();
      }

      GL11.glPopMatrix();
   }

   private void a(com.cheatbreaker.client.event.event.RenderHudEditorEvent var1) {
      if (this.q()) {
         GL11.glPushMatrix();
         Collection var2 = this.minecraft.thePlayer.getActivePotionEffects();
         if (var2.isEmpty()) {
            GL11.glPushMatrix();
            this.a(var1.a());
            HashMap var3 = new HashMap();
            PotionEffect var4 = new PotionEffect(Potion.fireResistance.id, 1200, 3);
            PotionEffect var5 = new PotionEffect(Potion.moveSpeed.id, 30, 3);
            var3.put(var4.getPotionID(), var4);
            var3.put(var5.getPotionID(), var5);
            var2 = var3.values();
            com.cheatbreaker.client.gui.hudeditor.AnchorAlignment var6 = this.s();
            byte var7 = 0;
            int var8 = 0;
            byte var9 = 22;

            for (PotionEffect var11 : (Iterable<PotionEffect>) var2) {
               boolean var14 = this.c(var11.getDuration());
               int var15 = 0;
               if ((Boolean)this.showEffectName.d()) {
                  String var13 = I18n.format(var11.getEffectName(), new Object[0]) + this.a(var11.getAmplifier());
                  var15 = this.minecraft.fontRendererObj.getStringWidth(var13) + 20;
                  if (var6 == com.cheatbreaker.client.gui.hudeditor.AnchorAlignment.RIGHT) {
                     this.minecraft.fontRendererObj.drawStringWithShadow(var13 + "§r", (int)this.width - var15, var7, this.nameColor.a());
                  } else if (var6 == com.cheatbreaker.client.gui.hudeditor.AnchorAlignment.LEFT) {
                     this.minecraft.fontRendererObj.drawStringWithShadow(var13 + "§r", 20.0F, var7, this.nameColor.a());
                  } else if (var6 == com.cheatbreaker.client.gui.hudeditor.AnchorAlignment.CENTER) {
                     this.minecraft.fontRendererObj.drawStringWithShadow(var13 + "§r", (int)this.width / 2 - var15 / 2 + 20, var7, this.nameColor.a());
                  }

                  if (var15 > var8) {
                     var8 = var15;
                  }
               }

               String var19 = Potion.getDurationString(var11);
               int var16 = this.minecraft.fontRendererObj.getStringWidth(var19) + 20;
               if (var14) {
                  if (var6 == com.cheatbreaker.client.gui.hudeditor.AnchorAlignment.RIGHT) {
                     this.minecraft
                        .fontRendererObj
                        .drawStringWithShadow(var19 + "§r", (int)this.width - var16, var7 + ((Boolean) this.showEffectName.d() ? 10 : 5), this.durationColor.a());
                  } else if (var6 == com.cheatbreaker.client.gui.hudeditor.AnchorAlignment.LEFT) {
                     this.minecraft
                        .fontRendererObj
                        .drawStringWithShadow(var19 + "§r", 20.0F, var7 + ((Boolean) this.showEffectName.d() ? 10 : 5), this.durationColor.a());
                  } else if (var6 == com.cheatbreaker.client.gui.hudeditor.AnchorAlignment.CENTER) {
                     this.minecraft
                        .fontRendererObj
                        .drawStringWithShadow(
                           var19 + "§r", (int)this.width / 2 - var16 / 2 + 20, var7 + ((Boolean) this.showEffectName.d() ? 10 : 5), this.durationColor.a()
                        );
                  }
               }

               Potion var12;
               if ((var12 = Potion.potionTypes[var11.getPotionID()]).hasStatusIcon()) {
                  cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
                  this.minecraft.getTextureManager().bindTexture(this.location);
                  int var17 = var12.getStatusIconIndex();
                  if (var6 == com.cheatbreaker.client.gui.hudeditor.AnchorAlignment.RIGHT) {
                     com.cheatbreaker.client.gui.render.RenderUtils.a(this.width - 20.0F, (float)var7, (float)(var17 % 8 * 18), (float)(198 + var17 / 8 * 18), 18, 18);
                  } else if (var6 == com.cheatbreaker.client.gui.hudeditor.AnchorAlignment.LEFT) {
                     com.cheatbreaker.client.gui.render.RenderUtils.a(0.0F, (float)var7, (float)(var17 % 8 * 18), (float)(198 + var17 / 8 * 18), 18, 18);
                  } else if (var6 == com.cheatbreaker.client.gui.hudeditor.AnchorAlignment.CENTER) {
                     com.cheatbreaker.client.gui.render.RenderUtils.a(this.width / 2.0F - var15 / 2, (float)var7, (float)(var17 % 8 * 18), (float)(198 + var17 / 8 * 18), 18, 18);
                  }
               }

               if (var16 > var8) {
                  var8 = var16;
               }

               var7 += var9;
            }

            this.c(var8, var7);
            GL11.glPopMatrix();
         }

         GL11.glPopMatrix();
      }
   }

   private boolean c(float var1) {
      if ((Boolean)this.blink.d() && var1 <= (Integer)this.blinkDuration.d() * 22) {
         if (this.ticks > 20) {
            this.ticks = 0;
         }

         return this.ticks <= 10;
      } else {
         return true;
      }
   }

   private String a(int var1) {
      switch (var1) {
         case 1:
            return " II";
         case 2:
            return " III";
         case 3:
            return " IV";
         case 4:
            return " V";
         case 5:
            return " VI";
         case 6:
            return " VII";
         case 7:
            return " VIII";
         case 8:
            return " IX";
         case 9:
            return " X";
         default:
            return var1 > 9 ? " " + var1 + 1 : "";
      }
   }
}
