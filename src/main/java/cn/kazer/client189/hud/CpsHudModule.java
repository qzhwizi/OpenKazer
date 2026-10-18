package cn.kazer.client189.hud;

import java.util.ArrayDeque;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class CpsHudModule {
   private final ArrayDeque<Long> clicks = new ArrayDeque<>();
   private boolean enabled = true;
   private boolean renderHud;
   private boolean rightClickCps;
   private boolean showCpsText = true;
   private boolean reverseText;
   private boolean ignoreCancelledClicks;
   private boolean textShadow;
   private boolean showBackground = true;
   private int textColor = -1;
   private int backgroundColor = 1862270976;
   private String anchor = "RIGHT_BOTTOM";
   private float xTranslation = -170.0F;
   private float yTranslation = -2.0F;
   private float scale = 1.0F;

   public void a(cn.kazer.client189.config.IniConfigFile var1) {
      if (var1 != null) {
         this.enabled = a(var1.a("CPS", "-State"), this.enabled);
         this.renderHud = a(var1.a("CPS", "-RenderHUD"), this.renderHud);
         this.rightClickCps = a(var1.a("CPS", "Right Click CPS"), this.rightClickCps);
         this.showCpsText = a(var1.a("CPS", "Show CPS Text"), this.showCpsText);
         this.reverseText = a(var1.a("CPS", "Reverse Text"), this.reverseText);
         this.ignoreCancelledClicks = a(var1.a("CPS", "Ignore Cancelled Clicks"), this.ignoreCancelledClicks);
         this.textShadow = a(var1.a("CPS", "Text Shadow"), this.textShadow);
         this.showBackground = a(var1.a("CPS", "Show Background"), this.showBackground);
         this.textColor = a(var1.a("CPS", "Text Color"), this.textColor);
         this.backgroundColor = a(var1.a("CPS", "Background Color"), this.backgroundColor);
         String var2 = var1.a("CPS", "-Anchor");
         if (var2 != null) {
            this.anchor = var2;
         }

         this.xTranslation = a(var1.a("CPS", "-xTranslation"), this.xTranslation);
         this.yTranslation = a(var1.a("CPS", "-yTranslation"), this.yTranslation);
         this.scale = a(var1.a("CPS", "Scale"), this.scale);
         if (this.scale <= 0.0F) {
            this.scale = 1.0F;
         }
      }
   }

   @SubscribeEvent(
      receiveCanceled = true
   )
   public void onMouse(MouseEvent var1) {
      if (this.enabled
         && var1.buttonstate
         && (var1.button == 0 || this.rightClickCps && var1.button == 1)
         && (!this.ignoreCancelledClicks || !var1.isCanceled())) {
         Minecraft var2 = Minecraft.getMinecraft();
         if (var2.theWorld != null && var2.currentScreen == null) {
            this.clicks.addLast(System.currentTimeMillis());
            this.a(System.currentTimeMillis());
         }
      }
   }

   @SubscribeEvent
   public void onOverlay(Post var1) {
      if (var1.type == ElementType.ALL && this.enabled && this.renderHud) {
         Minecraft var2 = Minecraft.getMinecraft();
         if (var2.thePlayer != null && var2.theWorld != null && !var2.gameSettings.hideGUI) {
            this.a(System.currentTimeMillis());
            String var3 = this.showCpsText ? "CPS" : "";
            String var4 = this.reverseText ? var3 + " " + this.clicks.size() : this.clicks.size() + " " + var3;
            String var5 = this.showBackground ? var4.trim() : "[" + var4.trim() + "]";
            int var6 = var2.fontRendererObj.getStringWidth(var5);
            float var7 = this.showBackground ? 56.0F : var6;
            float var8 = this.showBackground ? var2.fontRendererObj.FONT_HEIGHT + 4.0F : var2.fontRendererObj.FONT_HEIGHT;
            float[] var9 = cn.kazer.client189.hud.HudAnchorLayout.a(
               this.anchor, var1.resolution.getScaledWidth(), var1.resolution.getScaledHeight(), var7, var8, this.scale, this.xTranslation, this.yTranslation
            );
            GlStateManager.pushMatrix();
            GlStateManager.translate(var9[0], var9[1], 0.0F);
            GlStateManager.scale(this.scale, this.scale, 1.0F);
            if (this.showBackground) {
               Gui.drawRect(0, 0, (int)var7, (int)var8, this.backgroundColor);
               var2.fontRendererObj.drawString(var5, (int)(var7 / 2.0F - var6 / 2.0F), 3, this.textColor);
            } else {
               var2.fontRendererObj.drawString(var5, 0.0F, 0.0F, this.textColor, this.textShadow);
            }

            GlStateManager.popMatrix();
         }
      }
   }

   private void a(long var1) {
      while (!this.clicks.isEmpty() && this.clicks.peekFirst() < var1 - 1000L) {
         this.clicks.removeFirst();
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
