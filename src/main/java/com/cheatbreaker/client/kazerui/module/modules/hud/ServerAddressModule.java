package com.cheatbreaker.client.kazerui.module.modules.hud;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import javax.imageio.ImageIO;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public final class ServerAddressModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static final ResourceLocation UNKNOWN_SERVER = new ResourceLocation("minecraft", "textures/misc/unknown_server.png");
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting serverIcon = this.a("serverIcon", true);
   private ResourceLocation iconTexture;
   private BufferedImage iconImage;
   private ServerData lastServer;

   public ServerAddressModule() {
      super("SERVER_ADDRESS", false);
      this.a(new com.cheatbreaker.client.kazerui.module.modules.hud.ServerAddressModule.a());
   }

   @Override
   protected void e() {
      this.q();
   }

   @Override
   public void a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a var1) {
      if (var1.phase == com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b.END) {
         ServerData var2 = m().theWorld == null ? null : m().getCurrentServerData();
         if (var2 != this.lastServer) {
            this.lastServer = var2;
            this.p();
            if (var2 != null && this.a()) {
               this.q();
            }
         }

         if (!this.a()) {
            if (this.iconTexture != null) {
               this.p();
            }
         } else if (this.iconImage != null) {
            if (this.iconTexture != null) {
               m().getTextureManager().deleteTexture(this.iconTexture);
            }

            this.iconTexture = m().getTextureManager().getDynamicTextureLocation("server-icon-thumbnail", new DynamicTexture(this.iconImage));
            this.iconImage = null;
         }
      }
   }

   private void p() {
      this.iconImage = null;
      if (this.iconTexture != null) {
         m().getTextureManager().deleteTexture(this.iconTexture);
         this.iconTexture = null;
      }
   }

   private void q() {
      ServerData var1 = m().getCurrentServerData();
      if (var1 != null && var1.getBase64EncodedIconData() != null && !var1.getBase64EncodedIconData().isEmpty()) {
         d(var1.getBase64EncodedIconData()).ifPresent(var1x -> this.iconImage = var1x);
      } else {
         this.iconTexture = null;
      }
   }

   private static Optional<BufferedImage> d(String var0) {
      if (var0 != null && var0.length() <= 262144) {
         ByteBuf var1 = Unpooled.copiedBuffer(var0, Charsets.UTF_8);
         ByteBuf var2 = null;

         Optional var4;
         try {
            var2 = Base64.decode(var1);
            BufferedImage var3 = ImageIO.read(new ByteBufInputStream(var2));
            return var3 != null && var3.getWidth() == 64 && var3.getHeight() == 64 ? Optional.of(var3) : Optional.empty();
         } catch (IllegalArgumentException | IOException var8) {
            var4 = Optional.empty();
         } finally {
            var1.release();
            if (var2 != null) {
               var2.release();
            }
         }

         return var4;
      } else {
         return Optional.empty();
      }
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a("settings", var1x -> var1x.a(this.serverIcon));
   }

   private final class a extends com.cheatbreaker.client.kazerui.module.modulerender.RichTextHudBase {
      private String line;
      private String shownLine;
      private boolean brackets;

      a() {
         super(ServerAddressModule.this, 0.0F, 0.0F, com.cheatbreaker.client.kazerui.hud.HudAnchor.TOP_LEFT, a(10, 18, 22, 40, 56, 62));
      }

      @Override
      protected Boolean b(String var1) {
         return Boolean.FALSE;
      }

      private String y() {
         return this.shownLine;
      }

      @Override
      protected String c(boolean var1) {
         try {
            ServerData var2 = com.cheatbreaker.client.kazerui.module.modules.hud.ServerAddressModule.m().getCurrentServerData();
            return var2 == null ? (var1 ? "kazer.cn" : "") : var2.serverIP;
         } catch (NullPointerException var3) {
            return var1 ? "kazer.cn" : "";
         }
      }

      @Override
      public boolean a(boolean var1) {
         String var2 = this.c(var1);
         boolean var3 = this.r();
         if (!Objects.equals(this.line, var2) || this.brackets != var3) {
            this.line = var2;
            this.brackets = var3;
            this.shownLine = this.brackets ? "[" + this.line + "]" : this.line;
         }

         if (this.line != null && !this.line.isEmpty()) {
            this.a(
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(this.y()) + 8.0F + (ServerAddressModule.this.serverIcon.b() ? this.backgroundHeight.c() : 0),
               this.backgroundHeight.c()
            );
            return true;
         } else {
            return false;
         }
      }

      @Override
      public void b(boolean var1) {
         if (this.line != null && !this.line.isEmpty()) {
            boolean var2 = this.q();
            boolean var3 = this.r();
            float var4 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(this.y());
            float var5 = this.f();
            float var6 = this.g();
            float var7 = 0.0F;
            float var8 = 0.0F;
            boolean var9 = ServerAddressModule.this.serverIcon.b();
            if (var9) {
               var7 += var6;
               var5 -= var6;
            }

            if (var2) {
               float var10 = var9 ? var6 : 0.0F;
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(this.backgroundColor, var7 - var10, var8, var5 + var10, var6);
               if (this.border.b()) {
                  com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(this.borderColor, var7 - var10, var8, var5 + var10, var6, this.borderThickness.b());
               }
            }

            if (var9) {
               com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(
                  ServerAddressModule.this.iconTexture == null ? com.cheatbreaker.client.kazerui.module.modules.hud.ServerAddressModule.UNKNOWN_SERVER : ServerAddressModule.this.iconTexture,
                  (float)((int)(var7 - var6)),
                  (float)((int)var8),
                  (float)((int)var6),
                  (float)((int)var6),
                  -1
               );
            }

            float var11 = var8 + (var6 / 1.88F - com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.b() / 2.0F + 0.5F);
            this.a(this.line, this.a(var7, var5, var4, var2), var11, var3, this.textShadow.b());
         }
      }
   }
}
