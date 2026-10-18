package com.cheatbreaker.client.gui.serverlist;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiListExtended.IGuiListEntry;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

public class KazerServerListEntry implements IGuiListEntry {
   private static final ResourceLocation SERVER_SELECTION_BUTTONS = new ResourceLocation("textures/gui/resource_packs.png");
   private static final ResourceLocation UNKNOWN_SERVER = new ResourceLocation("textures/misc/unknown_pack.png");
   private static final Logger logger = LogManager.getLogger();
   private final GuiMultiplayer guiMultiplayer;
   private final Minecraft mc;
   private final ServerData server;
   private long field_148298_f;
   private String lastIconB64;
   private DynamicTexture icon;
   private ResourceLocation serverIcon;
   private ResourceLocation starIcon = new ResourceLocation("client/icons/star-64.png");
   private ResourceLocation cbIcon = new ResourceLocation("client/icons/cb.png");

   public KazerServerListEntry(GuiMultiplayer var1, ServerData var2) {
      this.guiMultiplayer = var1;
      this.server = var2;
      this.mc = Minecraft.getMinecraft();
      this.serverIcon = new ResourceLocation("servers/" + var2.serverIP + "/icon");
      this.icon = (DynamicTexture)this.mc.getTextureManager().getTexture(this.serverIcon);
   }

   public void drawEntry(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
      cn.kazer.client189.render.GlStateBridge.a(1.0F, 0.9F, 0.0F, 1.0F);
      com.cheatbreaker.client.gui.render.RenderUtils.a(this.starIcon, 5.0F, var2 - 17, var3 + 12);
      cn.kazer.client189.render.GlStateBridge.a(0.85F, 0.85F, 0.85F, 1.0F);
      if ("kazer.cn".equalsIgnoreCase(this.server.serverIP)) {
         float var11 = 16.0F;
         float var12 = 8.0F;
         float var13 = 0.0F;
         float var14 = 0.0F;
         float var15 = var2 - 20;
         float var16 = var3 + 20;
         cn.kazer.client189.render.GlStateBridge.a(3042);
         Minecraft.getMinecraft().renderEngine.bindTexture(this.cbIcon);
         GL11.glBegin(7);
         GL11.glTexCoord2d(var13 / 5.0F, var14 / 5.0F);
         GL11.glVertex2d(var15, var16);
         GL11.glTexCoord2d(var13 / 5.0F, (var14 + 5.0F) / 5.0F);
         GL11.glVertex2d(var15, var16 + var12);
         GL11.glTexCoord2d((var13 + 5.0F) / 5.0F, (var14 + 5.0F) / 5.0F);
         GL11.glVertex2d(var15 + var11, var16 + var12);
         GL11.glTexCoord2d((var13 + 5.0F) / 5.0F, var14 / 5.0F);
         GL11.glVertex2d(var15 + var11, var16);
         GL11.glEnd();
         cn.kazer.client189.render.GlStateBridge.b(3042);
      }

      boolean var23 = this.server.version > 5;
      boolean var24 = this.server.version < 5;
      boolean var25 = (var23 || var24) && this.server.version != -1332;
      this.mc.fontRendererObj.drawString(this.server.serverName, var2 + 32 + 3, var3 + 1, 16777215);
      List var26 = this.mc.fontRendererObj.listFormattedStringToWidth(this.server.serverMOTD, var4 - 32 - 2);

      for (int var27 = 0; var27 < Math.min(var26.size(), 2); var27++) {
         this.mc.fontRendererObj.drawString((String)var26.get(var27), var2 + 32 + 3, var3 + 12 + this.mc.fontRendererObj.FONT_HEIGHT * var27, 8421504);
      }

      String var28 = var25 ? EnumChatFormatting.DARK_RED + this.server.gameVersion : this.server.populationInfo;
      int var29 = this.mc.fontRendererObj.getStringWidth(var28);
      this.mc.fontRendererObj.drawString(var28, var2 + var4 - var29 - 15 - 2, var3 + 1, 8421504);
      byte var17 = 0;
      String var18 = null;
      String var9;
      int var10;
      if (var25) {
         var10 = 5;
         var9 = var23 ? "Client out of date!" : "Server out of date!";
         var18 = this.server.populationInfo;
      } else if (this.server.field_78841_f && this.server.pingToServer != -2L) {
         var10 = this.server.pingToServer < 0L
            ? 5
            : (
               this.server.pingToServer < 150L
                  ? 0
                  : (this.server.pingToServer < 300L ? 1 : (this.server.pingToServer < 600L ? 2 : (this.server.pingToServer < 1000L ? 3 : 4)))
            );
         if (this.server.pingToServer < 0L) {
            var9 = "(no connection)";
         } else {
            var9 = this.server.pingToServer + "ms";
            var18 = this.server.populationInfo;
         }
      } else {
         var17 = 1;
         var10 = (int)(Minecraft.getSystemTime() / 100L + var1 * 2 & 7L);
         if (var10 > 4) {
            var10 = 8 - var10;
         }

         var9 = "Pinging...";
      }

      cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(Gui.icons);
      Gui.drawModalRectWithCustomSizedTexture(var2 + var4 - 15, var3, var17 * 10, 176 + var10 * 8, 10, 8, 256.0F, 256.0F);
      if (this.server.getBase64EncodedIconData() != null && !this.server.getBase64EncodedIconData().equals(this.lastIconB64)) {
         this.lastIconB64 = this.server.getBase64EncodedIconData();
         this.b();
         this.guiMultiplayer.getServerList().saveServerList();
      }

      if (this.icon != null) {
         this.mc.getTextureManager().bindTexture(this.serverIcon);
      } else {
         this.mc.getTextureManager().bindTexture(UNKNOWN_SERVER);
      }

      Gui.drawModalRectWithCustomSizedTexture(var2, var3, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
      int var19 = var6 - var2;
      int var20 = var7 - var3;
      if (var19 >= var4 - 15 && var19 <= var4 - 5 && var20 >= 0 && var20 <= 8) {
         this.guiMultiplayer.setHoveringText(var9);
      } else if (var19 >= var4 - var29 - 15 - 2 && var19 <= var4 - 15 - 2 && var20 >= 0 && var20 <= 8) {
         this.guiMultiplayer.setHoveringText(var18);
      }

      Minecraft var21 = Minecraft.getMinecraft();
      if (var21.gameSettings.touchscreen || var8) {
         var21.getTextureManager().bindTexture(SERVER_SELECTION_BUTTONS);
         cn.kazer.client189.render.DrawRect.a(var2, var3, var2 + 32, var3 + 32, -1601138544);
         cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
         int var22 = var6 - var2;
         if (var22 < 32) {
            Gui.drawModalRectWithCustomSizedTexture(var2, var3, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
         } else {
            Gui.drawModalRectWithCustomSizedTexture(var2, var3, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
         }
      }
   }

   private void b() {
      if (this.server.getBase64EncodedIconData() == null) {
         this.mc.getTextureManager().deleteTexture(this.serverIcon);
         this.icon = null;
      } else {
         ByteBuf var2 = Unpooled.copiedBuffer(this.server.getBase64EncodedIconData(), Charsets.UTF_8);
         ByteBuf var3 = Base64.decode(var2);

         BufferedImage var1;
         label63: {
            try {
               var1 = ImageIO.read(new ByteBufInputStream(var3));
               Validate.validState(var1.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
               Validate.validState(var1.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
               break label63;
            } catch (Exception var8) {
               logger.error("Invalid icon for server " + this.server.serverName + " (" + this.server.serverIP + ")", var8);
               this.server.setBase64EncodedIconData(null);
            } finally {
               var2.release();
               var3.release();
            }

            return;
         }

         if (this.icon == null) {
            this.icon = new DynamicTexture(var1.getWidth(), var1.getHeight());
            this.mc.getTextureManager().loadTexture(this.serverIcon, this.icon);
         }

         var1.getRGB(0, 0, var1.getWidth(), var1.getHeight(), this.icon.getTextureData(), 0, var1.getWidth());
         this.icon.updateDynamicTexture();
      }
   }

   public boolean mousePressed(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.guiMultiplayer.selectServer(var1);
      if (Minecraft.getSystemTime() - this.field_148298_f < 250L) {
         this.guiMultiplayer.connectToSelected();
      }

      this.field_148298_f = Minecraft.getSystemTime();
      if (var5 <= 32 && var5 < 32) {
         this.guiMultiplayer.connectToSelected();
         return true;
      } else {
         return false;
      }
   }

   public void mouseReleased(int var1, int var2, int var3, int var4, int var5, int var6) {
   }

   public ServerData a() {
      return this.server;
   }

   public void setSelected(int var1, int var2, int var3) {
   }
}
