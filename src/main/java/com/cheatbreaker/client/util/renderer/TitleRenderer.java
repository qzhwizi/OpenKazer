package com.cheatbreaker.client.util.renderer;

import com.cheatbreaker.client.kazerui.module.modules.hud.ModuleTitles;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

public class TitleRenderer {
   private final Minecraft lIIIIlIIllIIlIIlIIIlIIllI = Minecraft.getMinecraft();
   private final com.cheatbreaker.client.CheatBreakerClient lIIIIIIIIIlIllIIllIlIIlIl = com.cheatbreaker.client.CheatBreakerClient.N();
   private final List<com.cheatbreaker.client.util.renderer.TitleMessage> IlllIIIlIlllIllIlIIlllIlI = Lists.newArrayList();

   public void a(com.cheatbreaker.client.event.event.RenderHudEvent var1) {
      com.cheatbreaker.client.CheatBreakerClient var2 = com.cheatbreaker.client.CheatBreakerClient.N();
      if (!this.IlllIIIlIlllIllIlIIlllIlI.isEmpty() || !var2.B().e().b().isEmpty()) {
         ArrayList var3 = new ArrayList<>(this.IlllIIIlIlllIllIlIIlllIlI);
         var3.addAll(var2.B().e().b());
         if (!var3.isEmpty()) {
            int var4 = GL11.glGetInteger(2976);
            int var5 = GL11.glGetInteger(34016);
            cn.kazer.client189.render.GlStateBridge.c(1048575);
            GL11.glMatrixMode(5888);

            try {
               cn.kazer.client189.render.GlStateBridge.a(3042);
               cn.kazer.client189.render.GlStateBridge.b(3008);
               cn.kazer.client189.render.GlStateBridge.b(2929);
               cn.kazer.client189.render.GlStateBridge.a(false);
               cn.kazer.client189.render.GlStateBridge.b(2896);
               OpenGlHelper.glBlendFunc(770, 771, 1, 0);
               FontRenderer var6 = this.lIIIIlIIllIIlIIlIIIlIIllI.fontRendererObj;
               long var7 = System.currentTimeMillis();

               for (com.cheatbreaker.client.util.renderer.TitleMessage var10 : (Iterable<com.cheatbreaker.client.util.renderer.TitleMessage>) var3) {
                  boolean var11 = var10.c() == com.cheatbreaker.client.util.renderer.TitleMessage.aInner.title;
                  if (ModuleTitles.b(var11)) {
                     float var12 = var10.b(var7);
                     float var13 = var10.c(var7) * (var10.c() == com.cheatbreaker.client.util.renderer.TitleMessage.aInner.title ? 4.0F : 1.5F);
                     var13 *= ModuleTitles.c(var11);
                     if (!(var12 <= 0.0F) && !(var13 <= 0.0F) && !Float.isNaN(var13) && !Float.isInfinite(var13)) {
                        int var14 = Math.max(4, (int)(255.0F * var12)) << 24 | 16777215;
                        float var15 = var10.c() == com.cheatbreaker.client.util.renderer.TitleMessage.aInner.title ? -30.0F : 10.0F;
                        GL11.glPushMatrix();

                        try {
                           GL11.glTranslatef(var1.a().getScaledWidth() / 2.0F, var1.a().getScaledHeight() / 2.0F + var15, 0.0F);
                           GL11.glScalef(var13, var13, var13);
                           var6.drawStringWithShadow(var10.d(), -var6.getStringWidth(var10.d()) / 2.0F, -var6.FONT_HEIGHT / 2.0F, var14);
                        } finally {
                           GL11.glPopMatrix();
                        }
                     }
                  }
               }
            } finally {
               cn.kazer.client189.render.GlStateBridge.a();
               OpenGlHelper.setActiveTexture(var5);
               GL11.glMatrixMode(var4);
            }
         }
      }
   }

   public void a(com.cheatbreaker.client.event.event.ClientTickEvent var1) {
      if (!this.IlllIIIlIlllIllIlIIlllIlI.isEmpty()) {
         this.IlllIIIlIlllIllIlIIlllIlI.removeIf(var0 -> var0.a(System.currentTimeMillis()));
      }

      com.cheatbreaker.client.CheatBreakerClient.N().B().e().a(System.currentTimeMillis());
   }

   public List<com.cheatbreaker.client.util.renderer.TitleMessage> a() {
      return this.IlllIIIlIlllIllIlIIlllIlI;
   }
}
