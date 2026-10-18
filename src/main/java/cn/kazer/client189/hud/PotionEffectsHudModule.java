package cn.kazer.client189.hud;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class PotionEffectsHudModule {
   private static final ResourceLocation INVENTORY = new ResourceLocation("textures/gui/container/inventory.png");
   private boolean enabled = true;
   private boolean renderHud = true;
   private boolean showWhileTyping = true;
   private boolean showName = true;
   private boolean showDuration = true;
   private boolean showAmplifier = true;
   private boolean showInInventory = true;
   private boolean hidePotionStatus;
   private boolean uppercase;
   private boolean reversed;
   private boolean blink = true;
   private boolean showBackground;
   private boolean textShadow = true;
   private boolean colorNameBasedOnEffect;
   private boolean colorInfoBasedOnEffect;
   private int blinkDuration = 10;
   private int nameColor = -1;
   private int durationColor = -1;
   private String mode = "Normal";
   private String anchor = "LEFT_MIDDLE";
   private float xTranslation;
   private float yTranslation = 4.0F;
   private float scale = 1.0F;

   public void a(cn.kazer.client189.config.IniConfigFile var1) {
      if (var1 != null) {
         String var2 = "Potion Effects";
         this.enabled = a(var1.a(var2, "-State"), this.enabled);
         this.renderHud = a(var1.a(var2, "-RenderHUD"), this.renderHud);
         this.showWhileTyping = a(var1.a(var2, "Show While Typing"), this.showWhileTyping);
         this.showName = a(var1.a(var2, "Show Effect Name"), this.showName);
         this.showDuration = a(var1.a(var2, "Show Effect Duration"), this.showDuration);
         this.showAmplifier = a(var1.a(var2, "Show Effect Amplifier"), this.showAmplifier);
         this.showInInventory = a(var1.a(var2, "Show In Inventory"), this.showInInventory);
         this.hidePotionStatus = a(var1.a(var2, "Hide Potion Status"), this.hidePotionStatus);
         this.uppercase = a(var1.a(var2, "Uppercase Potion Names"), this.uppercase);
         this.reversed = a(var1.a(var2, "Reversed Text"), this.reversed);
         this.blink = a(var1.a(var2, "Blink"), this.blink);
         this.showBackground = a(var1.a(var2, "Show Background"), this.showBackground);
         this.textShadow = a(var1.a(var2, "Text Shadow"), this.textShadow);
         this.colorNameBasedOnEffect = a(var1.a(var2, "Color Name Based on Effect"), this.colorNameBasedOnEffect);
         this.colorInfoBasedOnEffect = a(var1.a(var2, "Color Info Based on Effect"), this.colorInfoBasedOnEffect);
         this.blinkDuration = a(var1.a(var2, "Blink Duration"), this.blinkDuration);
         this.nameColor = a(var1.a(var2, "Name Color"), this.nameColor);
         this.durationColor = a(var1.a(var2, "Duration Color"), this.durationColor);
         String var3 = var1.a(var2, "Mode");
         if (var3 != null) {
            this.mode = var3;
         }

         String var4 = var1.a(var2, "-Anchor");
         if (var4 != null) {
            this.anchor = var4;
         }

         this.xTranslation = a(var1.a(var2, "-xTranslation"), this.xTranslation);
         this.yTranslation = a(var1.a(var2, "-yTranslation"), this.yTranslation);
         this.scale = a(var1.a(var2, "Scale"), this.scale);
         if (this.scale <= 0.0F) {
            this.scale = 1.0F;
         }
      }
   }

   @SubscribeEvent
   public void onOverlay(Post var1) {
      if (var1.type == ElementType.ALL && this.enabled && this.renderHud && !this.hidePotionStatus) {
         Minecraft var2 = Minecraft.getMinecraft();
         if (var2.thePlayer != null && var2.theWorld != null && !var2.gameSettings.hideGUI) {
            if (this.showWhileTyping || !(var2.currentScreen instanceof GuiChat)) {
               if (this.showInInventory || !(var2.currentScreen instanceof GuiInventory)) {
                  Collection var3 = var2.thePlayer.getActivePotionEffects();
                  if (!var3.isEmpty()) {
                     ArrayList var4 = new ArrayList();
                     int var5 = 18;

                     for (PotionEffect var7 : (Iterable<PotionEffect>) var3) {
                        Potion var8 = Potion.potionTypes[var7.getPotionID()];
                        if (var8 != null) {
                           String var9 = I18n.format(var7.getEffectName(), new Object[0]);
                           if (this.uppercase) {
                              var9 = var9.toUpperCase(Locale.ROOT);
                           }

                           if (this.showAmplifier && var7.getAmplifier() > 0) {
                              var9 = var9 + " " + a(var7.getAmplifier() + 1);
                           }

                           String var10 = Potion.getDurationString(var7);
                           if (!this.showName) {
                              var9 = "";
                           }

                           if (!this.showDuration) {
                              var10 = "";
                           }

                           if (this.reversed) {
                              String var11 = var9;
                              var9 = var10;
                              var10 = var11;
                           }

                           var5 = Math.max(var5, 20 + Math.max(var2.fontRendererObj.getStringWidth(var9), var2.fontRendererObj.getStringWidth(var10)));
                           var4.add(new cn.kazer.client189.hud.PotionEffectsHudModule.a(var8, var7, var9, var10));
                        }
                     }

                     if (!var4.isEmpty()) {
                        int var18 = this.mode.equalsIgnoreCase("Compact") ? 18 : 22;
                        int var19 = var4.size() * var18;
                        float[] var20 = cn.kazer.client189.hud.HudAnchorLayout.a(
                           this.anchor,
                           var1.resolution.getScaledWidth(),
                           var1.resolution.getScaledHeight(),
                           var5,
                           var19,
                           this.scale,
                           this.xTranslation,
                           this.yTranslation
                        );
                        boolean var21 = this.anchor.toUpperCase(Locale.ROOT).startsWith("RIGHT");
                        GlStateManager.pushMatrix();
                        GlStateManager.translate(var20[0], var20[1], 0.0F);
                        GlStateManager.scale(this.scale, this.scale, 1.0F);
                        if (this.showBackground) {
                           Gui.drawRect(0, 0, var5, var19, 1862270976);
                        }

                        for (int var22 = 0; var22 < var4.size(); var22++) {
                           cn.kazer.client189.hud.PotionEffectsHudModule.a var23 = (cn.kazer.client189.hud.PotionEffectsHudModule.a)var4.get(var22);
                           int var12 = var22 * var18;
                           if (var23.potion.hasStatusIcon()) {
                              GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                              var2.getTextureManager().bindTexture(INVENTORY);
                              int var13 = var23.potion.getStatusIconIndex();
                              Gui.drawModalRectWithCustomSizedTexture(
                                 var21 ? var5 - 18 : 0, var12, var13 % 8 * 18, 198 + var13 / 8 * 18, 18, 18, 256.0F, 256.0F
                              );
                           }

                           int var24 = 0xFF000000 | var23.potion.getLiquidColor();
                           int var14 = this.colorNameBasedOnEffect ? var24 : this.nameColor;
                           int var15 = this.colorInfoBasedOnEffect ? var24 : this.durationColor;
                           int var16 = var21 ? var5 - 20 - var2.fontRendererObj.getStringWidth(var23.name) : 20;
                           if (!var23.name.isEmpty()) {
                              var2.fontRendererObj.drawString(var23.name, var16, var12, var14, this.textShadow);
                           }

                           if (!var23.duration.isEmpty() && this.a(var23.effect)) {
                              int var17 = var21 ? var5 - 20 - var2.fontRendererObj.getStringWidth(var23.duration) : 20;
                              var2.fontRendererObj.drawString(var23.duration, var17, var12 + (var23.name.isEmpty() ? 4 : 10), var15, this.textShadow);
                           }
                        }

                        GlStateManager.popMatrix();
                     }
                  }
               }
            }
         }
      }
   }

   private boolean a(PotionEffect var1) {
      return this.blink && var1.getDuration() <= this.blinkDuration * 20 ? System.currentTimeMillis() / 400L % 2L == 0L : true;
   }

   private static String a(int var0) {
      String[] var1 = new String[]{"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
      return var0 < var1.length ? var1[var0] : Integer.toString(var0);
   }

   private static boolean a(String var0, boolean var1) {
      return var0 == null ? var1 : Boolean.parseBoolean(var0);
   }

   private static int a(String var0, int var1) {
      if (var0 == null) {
         return var1;
      } else {
         try {
            return Integer.parseInt(var0.split(";", 2)[0]);
         } catch (NumberFormatException var3) {
            return var1;
         }
      }
   }

   private static float a(String var0, float var1) {
      if (var0 == null) {
         return var1;
      } else {
         try {
            return Float.parseFloat(var0);
         } catch (NumberFormatException var3) {
            return var1;
         }
      }
   }

   private static final class a {
      private final Potion potion;
      private final PotionEffect effect;
      private final String name;
      private final String duration;

      private a(Potion var1, PotionEffect var2, String var3, String var4) {
         this.potion = var1;
         this.effect = var2;
         this.name = var3;
         this.duration = var4;
      }
   }
}
