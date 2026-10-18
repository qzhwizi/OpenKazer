package cn.kazer.client189.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class FpsHudOverlay {
   private boolean enabled = true;
   private boolean renderHud = true;
   private boolean showBackground = true;
   private boolean reverseOrder;
   private boolean textShadow = true;
   private int textColor = -1;
   private int backgroundColor = 1862270976;
   private String anchor = "MIDDLE_TOP";
   private float xTranslation;
   private float yTranslation = 12.0F;
   private float scale = 1.0F;

   public void a(cn.kazer.client189.config.IniConfigFile var1) {
      if (var1 != null) {
         this.enabled = a(var1.a("FPS", "-State"), this.enabled);
         this.renderHud = a(var1.a("FPS", "-RenderHUD"), this.renderHud);
         this.showBackground = a(var1.a("FPS", "Show Background"), this.showBackground);
         this.reverseOrder = a(var1.a("FPS", "Reverse Order"), this.reverseOrder);
         this.textShadow = a(var1.a("FPS", "Text Shadow"), this.textShadow);
         this.textColor = a(var1.a("FPS", "Text Color"), this.textColor);
         this.backgroundColor = a(var1.a("FPS", "Background Color"), this.backgroundColor);
         String var2 = var1.a("FPS", "-Anchor");
         if (var2 != null) {
            this.anchor = var2;
         }

         this.xTranslation = a(var1.a("FPS", "-xTranslation"), this.xTranslation);
         this.yTranslation = a(var1.a("FPS", "-yTranslation"), this.yTranslation);
         this.scale = a(var1.a("FPS", "Scale"), this.scale);
         if (this.scale <= 0.0F) {
            this.scale = 1.0F;
         }
      }
   }

   @SubscribeEvent
   public void onOverlay(Post var1) {
      if (var1.type == ElementType.ALL && this.enabled && this.renderHud) {
         Minecraft var2 = Minecraft.getMinecraft();
         if (var2.thePlayer != null && var2.theWorld != null && !var2.gameSettings.hideGUI) {
            String var3 = this.reverseOrder ? "FPS " + Minecraft.getDebugFPS() : Minecraft.getDebugFPS() + " FPS";
            String var4 = this.showBackground ? var3 : "[" + var3 + "]";
            int var5 = var2.fontRendererObj.getStringWidth(var4);
            float var6 = this.showBackground ? 56.0F : var5;
            float var7 = this.showBackground ? var2.fontRendererObj.FONT_HEIGHT + 4.0F : var2.fontRendererObj.FONT_HEIGHT;
            float[] var8 = cn.kazer.client189.hud.HudAnchorLayout.a(
               this.anchor, var1.resolution.getScaledWidth(), var1.resolution.getScaledHeight(), var6, var7, this.scale, this.xTranslation, this.yTranslation
            );
            GlStateManager.pushMatrix();
            GlStateManager.translate(var8[0], var8[1], 0.0F);
            GlStateManager.scale(this.scale, this.scale, 1.0F);
            if (this.showBackground) {
               Gui.drawRect(0, 0, (int)var6, (int)var7, this.backgroundColor);
               var2.fontRendererObj.drawString(var4, (int)(var6 / 2.0F - var5 / 2.0F), 3, this.textColor);
            } else {
               var2.fontRendererObj.drawString(var4, 0.0F, 0.0F, this.textColor, this.textShadow);
            }

            GlStateManager.popMatrix();
         }
      }
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
}
