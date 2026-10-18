package cn.kazer.client189.runtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent.Post;

public final class NametagBatchRenderer {
   private static final ResourceLocation BADGE = new ResourceLocation("client/kazer_badge.png");
   private static final List<Post> PENDING = new ArrayList<>();
   private static final Map<EntityPlayer, cn.kazer.client189.runtime.NametagBatchRenderer.a> TEXT = new WeakHashMap<>();
   private static Object textWorld;
   private static int textTick;

   public static void a() {
      textTick++;
   }

   private NametagBatchRenderer() {
   }

   public static void b() {
      PENDING.clear();
      WorldClient var0 = Minecraft.getMinecraft().theWorld;
      if (textWorld != var0) {
         TEXT.clear();
         textWorld = var0;
      }
   }

   public static void a(Post var0) {
      Minecraft var1 = Minecraft.getMinecraft();
      com.cheatbreaker.client.CheatBreakerClient var2 = com.cheatbreaker.client.CheatBreakerClient.N();
      if (var2.moduleManager.nametagModule.g() && !var0.entityPlayer.isInvisible()) {
         if (!(var1.currentScreen instanceof GuiContainer) || var0.entityPlayer != var1.thePlayer || var0.x != 0.0 || var0.y != 0.0 || var0.z != 0.0) {
            if (PENDING.size() < 512) {
               PENDING.add(var0);
            }
         }
      }
   }

   public static void c() {
      if (!PENDING.isEmpty()) {
         cn.kazer.client189.render.GlStateBridge.c(1048575);

         try {
            for (Post var1 : PENDING) {
               c(var1);
            }
         } finally {
            PENDING.clear();
            cn.kazer.client189.render.GlStateBridge.a();
         }
      }
   }

   public static List<String> a(List<String> var0, String var1, String var2) {
      return com.cheatbreaker.client.module.modules.nametag.NametagLine.a(var0, var1, var2);
   }

   public static void b(Post var0) {
      cn.kazer.client189.render.GlStateBridge.c(1048575);

      try {
         c(var0);
      } finally {
         cn.kazer.client189.render.GlStateBridge.a();
      }
   }

   private static void c(Post var0) {
      Minecraft var1 = Minecraft.getMinecraft();
      if (!(var1.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.HudEditorScreen) && !(var1.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen)) {
         com.cheatbreaker.client.CheatBreakerClient var2 = com.cheatbreaker.client.CheatBreakerClient.N();
         com.cheatbreaker.client.module.modules.nametag.NametagsModule var3 = var2.moduleManager.nametagModule;
         EntityPlayer var4 = var0.entityPlayer;
         boolean var5 = var1.currentScreen instanceof GuiContainer && var4 == var1.thePlayer && var0.x == 0.0 && var0.y == 0.0 && var0.z == 0.0;
         if (!var5) {
            if (var3.g() && !var4.isInvisible() && (var5 || var1.thePlayer != null)) {
               if (var5 || !var1.gameSettings.hideGUI || !(Boolean)var3.hideInFirstPerson.d()) {
                  if (var4 != var1.thePlayer || var5 || var1.gameSettings.thirdPersonView != 0 && (Boolean)var3.thirdPersonNametag.d()) {
                     if (var5 || !(var4.getDistanceSqToEntity(var1.thePlayer) > (var4.isSneaking() ? 1024 : 4096))) {
                        List var6 = var2.netHandler.a(var4.getUniqueID());
                        boolean var7 = var4.getDistanceSqToEntity(var1.thePlayer) < 100.0;
                        cn.kazer.client189.runtime.NametagBatchRenderer.a var8 = TEXT.get(var4);
                        if (var8 == null || var8.tick != textTick || var8.wire != var6 || var8.font != var1.fontRendererObj || var8.healthVisible != var7) {
                           List var9 = a(var6, var4.getName(), var4.getDisplayName().getFormattedText());
                           if (var7 && !var9.isEmpty()) {
                              Scoreboard var10 = var4.getWorldScoreboard();
                              ScoreObjective var11 = var10.getObjectiveInDisplaySlot(2);
                              if (var11 != null) {
                                 String var12 = var10.getValueFromObjective(var4.getName(), var11).getScorePoints() + " " + var11.getDisplayName();
                                 if (!var9.contains(var12)) {
                                    var9.add(var12);
                                 }
                              }
                           }

                           var8 = new cn.kazer.client189.runtime.NametagBatchRenderer.a(textTick, var6, var9, var1.fontRendererObj, var7, var4.getName());
                           TEXT.put(var4, var8);
                        }

                        List var23 = var8.lines;
                        if (!var23.isEmpty()) {
                           int var24 = var8.primary;
                           GlStateManager.pushMatrix();

                           try {
                              float var25 = var5 ? com.cheatbreaker.client.module.modules.nametag.NametagLine.b(var23.size()) : 0.025F;
                              double var26 = var5 ? com.cheatbreaker.client.module.modules.nametag.NametagLine.a(var23.size()) : 0.5;
                              GlStateManager.translate(var0.x, var0.y + var4.height + var26 + (var23.size() - 1) * 12 * var25, var0.z);
                              cn.kazer.client189.render.ClientBridge.a(var5);
                              GlStateManager.scale(-var25, -var25, var25);
                              if (var4 == var1.thePlayer && !var5) {
                                 GlStateManager.translate(0.0F, -com.cheatbreaker.client.module.modules.nametag.NametagLine.a(var1.getRenderManager().playerViewX), 0.0F);
                              }

                              GlStateManager.disableLighting();
                              GlStateManager.disableFog();
                              GlStateManager.enableBlend();
                              GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                              GlStateManager.depthMask(false);
                              if (var4.isSneaking()) {
                                 GlStateManager.enableDepth();
                              } else {
                                 GlStateManager.disableDepth();
                              }

                              for (int var14 = 0; var14 < var23.size(); var14++) {
                                 String var15 = (String)var23.get(var14);
                                 boolean var16 = var14 == var24
                                    && var8.nameLines[var14]
                                    && (Boolean)var3.showNametagIcon.d()
                                    && (var4 == var1.thePlayer || var2.L().a(var4.getUniqueID()));
                                 com.cheatbreaker.client.module.modules.nametag.NametagLine var17 = var16 ? var8.badged[var14] : var8.plain[var14];
                                 int var18 = var14 * 12;
                                 GlStateManager.disableTexture2D();
                                 cn.kazer.client189.render.DrawRect.a(
                                    var17.left - 1, var18 - 1, var17.right + 1, var18 + 9, (int)(63.75F * ((Number)var3.opacity.d()).floatValue()) << 24
                                 );
                                 GlStateManager.enableTexture2D();
                                 GlStateManager.enableBlend();
                                 GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                                 GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                                 if (var4.isSneaking()) {
                                    GlStateManager.enableDepth();
                                 } else {
                                    GlStateManager.disableDepth();
                                 }

                                 var1.fontRendererObj.drawString(var15, var17.textX, var18, 553648127, (Boolean)var3.textShadow.d());
                                 if (var16) {
                                    var1.getTextureManager().bindTexture(BADGE);
                                    int var19 = var2.L().d(var4.getUniqueID());
                                    GlStateManager.color((var19 >> 16 & 0xFF) / 255.0F, (var19 >> 8 & 0xFF) / 255.0F, (var19 & 0xFF) / 255.0F, 0.125F);
                                    Gui.drawModalRectWithCustomSizedTexture(var17.badgeX, var18 - 1, 0.0F, 0.0F, 9, 9, 9.0F, 9.0F);
                                 }

                                 GlStateManager.enableDepth();
                                 GlStateManager.depthMask(true);
                                 GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                                 var1.fontRendererObj
                                    .drawString(var15, var17.textX, var18, var4.isSneaking() ? 553648127 : -1, (Boolean)var3.textShadow.d());
                                 if (var16) {
                                    var1.getTextureManager().bindTexture(BADGE);
                                    int var27 = var2.L().d(var4.getUniqueID());
                                    GlStateManager.color(
                                       (var27 >> 16 & 0xFF) / 255.0F,
                                       (var27 >> 8 & 0xFF) / 255.0F,
                                       (var27 & 0xFF) / 255.0F,
                                       var4.isSneaking() ? 0.125F : 1.0F
                                    );
                                    Gui.drawModalRectWithCustomSizedTexture(var17.badgeX, var18 - 1, 0.0F, 0.0F, 9, 9, 9.0F, 9.0F);
                                 }

                                 if (var4.isSneaking()) {
                                    GlStateManager.enableDepth();
                                 } else {
                                    GlStateManager.disableDepth();
                                 }

                                 GlStateManager.depthMask(false);
                              }
                           } finally {
                              GlStateManager.popMatrix();
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static final class a {
      final int tick;
      final int primary;
      final List<String> wire;
      final List<String> lines;
      final FontRenderer font;
      final boolean healthVisible;
      final int[] widths;
      final boolean[] nameLines;
      final com.cheatbreaker.client.module.modules.nametag.NametagLine[] plain;
      final com.cheatbreaker.client.module.modules.nametag.NametagLine[] badged;

      a(int var1, List<String> var2, List<String> var3, FontRenderer var4, boolean var5, String var6) {
         this.tick = var1;
         this.wire = var2;
         this.lines = var3;
         this.font = var4;
         this.healthVisible = var5;
         this.primary = com.cheatbreaker.client.module.modules.nametag.NametagLine.a(var3, var6);
         this.widths = new int[var3.size()];
         this.nameLines = new boolean[this.widths.length];
         this.plain = new com.cheatbreaker.client.module.modules.nametag.NametagLine[this.widths.length];
         this.badged = new com.cheatbreaker.client.module.modules.nametag.NametagLine[this.widths.length];

         for (int var7 = 0; var7 < this.widths.length; var7++) {
            this.widths[var7] = var4.getStringWidth((String)var3.get(var7));
            this.nameLines[var7] = com.cheatbreaker.client.module.modules.nametag.NametagLine.a((String)var3.get(var7), var6);
            this.plain[var7] = new com.cheatbreaker.client.module.modules.nametag.NametagLine(this.widths[var7], false);
            this.badged[var7] = new com.cheatbreaker.client.module.modules.nametag.NametagLine(this.widths[var7], true);
         }
      }
   }
}
