package com.cheatbreaker.client.kazerui.module.modules.mechanic;

import com.cheatbreaker.client.kazerui.module.render.ChatLineTag;
import com.cheatbreaker.client.kazerui.module.render.KazerChatAccess;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer.EnumChatVisibility;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public final class ChatRuntime {
   private static int nextId = 1;
   private static long positionRevision = Long.MIN_VALUE;
   private static com.cheatbreaker.client.kazerui.gui.components.MenuModel positionModel;
   private static float offsetX;
   private static float offsetY;
   private static float x;
   private static float y;
   private static float height;
   private static com.cheatbreaker.client.kazerui.hud.HudAnchor anchor;
   private static final String[] EXAMPLE = new String[]{"Welcome to Kazer Client!", "Drag this chat to reposition it.", "Chat messages appear here."};

   public static boolean a() {
      return com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.instance != null;
   }

   private static Minecraft b() {
      return Minecraft.getMinecraft();
   }

   private static KazerChatAccess b(GuiNewChat var0) {
      return (KazerChatAccess)var0;
   }

   static int a(ChatLine var0) {
      return ((ChatLineTag)var0).kazerui$id();
   }

   private static void a(ChatLine var0, int var1) {
      ((ChatLineTag)var0).kazerui$setId(var1);
   }

   public static boolean insert(Object var0, IChatComponent var1, int var2, int var3, boolean var4) {
      if (!a()) {
         return false;
      } else {
         GuiNewChat var5 = (GuiNewChat)var0;
         if (!var4) {
            int var9 = c();
            com.cheatbreaker.client.kazerui.module.ChatMessageEvent var10 = com.cheatbreaker.client.kazerui.module.ChatMessageEvent.a(var1, var9);
            if (var10.cancelled) {
               return true;
            } else {
               if (var10.removeLast) {
                  c(var5);
               }

               com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatHooks.a(a(var5, var10.a(), var2, var3, false, var9));
               return true;
            }
         } else {
            int var6 = 0;

            for (ChatLine var8 : b(var5).kazerChatLines()) {
               if (var8.getChatComponent() == var1) {
                  var6 = a(var8);
                  break;
               }
            }

            a(var5, var1, var2, var3, true, var6 == 0 ? c() : var6);
            return true;
         }
      }
   }

   private static int c() {
      if (nextId == Integer.MAX_VALUE) {
         nextId = 1;
      }

      return nextId++;
   }

   private static int a(GuiNewChat var0, IChatComponent var1, int var2, int var3, boolean var4, int var5) {
      KazerChatAccess var6 = b(var0);
      List var7 = var6.kazerDrawnLines();
      List var8 = var6.kazerChatLines();
      if (!var4 && var2 != 0) {
         var7.removeIf(var1x -> ((ChatLine)var1x).getChatLineID() == var2);
         var8.removeIf(var1x -> ((ChatLine)var1x).getChatLineID() == var2);
      }

      int var9 = Math.max(1, (int)(var0.getChatWidth() / var0.getChatScale()));
      if (com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.instance.s()
         && com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.instance.heads.a(var5) != null) {
         var9 = Math.max(1, var9 - 12);
      }

      List var10 = com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTextSplitter.a(var1, var9, b().fontRendererObj, false, false);

      for (IChatComponent var12 : (Iterable<IChatComponent>) var10) {
         if (!var4 && var0.getChatOpen() && var6.kazerChatScroll() > 0) {
            var6.kazerChatScrolled(true);
            com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatLineHelper.a(var0, 1);
         }

         ChatLine var13 = com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatLineHelper.a(var3, var12, var2, var1);
         a(var13, var5);
         var7.add(0, var13);
      }

      a(var7, com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatHooks.b(100));
      if (!var4) {
         ChatLine var14 = com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatLineHelper.a(var3, var1, var2, var1);
         a(var14, var5);
         var8.add(0, var14);
         a(var8, com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatHooks.b(100));
      }

      return var10.size();
   }

   static <T> void a(List<T> var0, int var1) {
      if (var0.size() > var1) {
         var0.subList(var1, var0.size()).clear();
      }
   }

   private static void c(GuiNewChat var0) {
      List var1 = b(var0).kazerChatLines();
      if (!var1.isEmpty()) {
         int var2 = a((ChatLine)var1.remove(0));
         b(var0).kazerDrawnLines().removeIf(var1x -> a(var1x) == var2);
      }
   }

   public static boolean refresh(Object var0) {
      if (!a()) {
         return false;
      } else {
         GuiNewChat var1 = (GuiNewChat)var0;
         KazerChatAccess var2 = b(var1);
         var2.kazerDrawnLines().clear();
         var1.resetScroll();
         List var3 = var2.kazerChatLines();

         for (int var4 = var3.size() - 1; var4 >= 0; var4--) {
            ChatLine var5 = (ChatLine)var3.get(var4);
            a(var1, var5.getChatComponent(), var5.getChatLineID(), var5.getUpdatedCounter(), true, a(var5));
         }

         return true;
      }
   }

   static int a(GuiNewChat var0) {
      List var1 = b(var0).kazerDrawnLines();
      int var2 = b(var0).kazerChatScroll();
      int var3 = 0;
      int var4 = b().ingameGUI.getUpdateCounter();

      for (int var5 = 0; var5 + var2 < var1.size() && var5 < var0.getLineCount(); var5++) {
         if (var0.getChatOpen()
            || com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatHooks.e()
            || var4 - ((ChatLine)var1.get(var5 + var2)).getUpdatedCounter() < 200) {
            var3 = var5 + 1;
         }
      }

      return var3;
   }

   private static void a(GuiNewChat var0, ScaledResolution var1, boolean var2) {
      com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule var3 = com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.instance;
      var3.hud.a(var2);
      height = var3.hud.g();
      com.cheatbreaker.client.kazerui.gui.components.MenuModel var4 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.d();
      if (positionModel != var4 || positionRevision != var4.revision) {
         offsetX = var4.a("chat", "x", 0.0F);
         offsetY = var4.a("chat", "y", -26.0F);
         anchor = var3.hud.c();
         positionModel = var4;
         positionRevision = var4.revision;
      }

      float var5 = var3.hud.h();
      float var6 = var1.getScaledWidth();
      float var7 = var1.getScaledHeight();
      float var8 = var3.hud.f() * var5;
      float var9 = height * var5;
      x = com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatLayoutMath.a(offsetX, anchor.a(var6, var8), var6, var8);
      y = com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatLayoutMath.a(offsetY + var3.hud.k() * var5, anchor.b(var7, var9), var7, var9);
   }

   public static boolean draw(Object var0, int var1) {
      if (!a()) {
         return false;
      } else {
         GuiNewChat var2 = (GuiNewChat)var0;
         com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule var3 = com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.instance;
         if (b().gameSettings.chatVisibility == EnumChatVisibility.HIDDEN
            || var3.q()
            || var3.a() && !var3.c()
            || b().currentScreen instanceof com.cheatbreaker.client.kazerui.gui.HudMoveScreen) {
            return true;
         } else if (b(var2).kazerDrawnLines().isEmpty()) {
            return true;
         } else {
            Minecraft var4 = b();
            ScaledResolution var5 = new ScaledResolution(var4);
            a(var2, var5, false);
            GlStateManager.pushMatrix();

            try {
               float var6 = var3.a() ? var3.hud.h() : 1.0F;
               float var7 = var3.a() ? x : 2.0F;
               float var8 = var3.a() ? y + height * var6 : var5.getScaledHeight() - 28;
               GlStateManager.translate(var7, var8 - (var5.getScaledHeight() - 48), 0.0F);
               GlStateManager.scale(var6, var6, 1.0F);
               a(var2, var1);
            } finally {
               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
               GlStateManager.popMatrix();
            }

            return true;
         }
      }
   }

   static void a(GuiNewChat var0, float var1) {
      GlStateManager.pushMatrix();

      try {
         GlStateManager.translate(0.0F, var1, 0.0F);
         if (!b(var0).kazerDrawnLines().isEmpty()) {
            a(var0, b().ingameGUI.getUpdateCounter());
         } else {
            float var2 = var0.getChatScale();
            GlStateManager.scale(var2, var2, 1.0F);

            for (int var3 = 0; var3 < EXAMPLE.length; var3++) {
               int var4 = (var3 - EXAMPLE.length) * 9;
               com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatHooks.a(0, var4, (int)(var0.getChatWidth() / var2) + 4, var4 + 9, Integer.MIN_VALUE);
               String var10001 = EXAMPLE[var3];
               b().fontRendererObj.drawString(var10001, 0.0F, var4 + 1, -1, com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.instance.chatShadow.b());
            }
         }
      } finally {
         GlStateManager.popMatrix();
      }
   }

   private static void a(GuiNewChat var0, int var1) {
      if (!com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatHooks.c()) {
         float var2 = var0.getChatScale();
         com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatHooks.a(0.0F, 0.0F, 0.0F);
         GlStateManager.scale(var2, var2, 1.0F);
         List var3 = b(var0).kazerDrawnLines();
         int var4 = b(var0).kazerChatScroll();
         boolean var5 = var0.getChatOpen() || com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatHooks.e();
         float var6 = b().gameSettings.chatOpacity * 0.9F + 0.1F;
         int var7 = (int)Math.ceil(var0.getChatWidth() / var2);

         for (int var8 = 0; var8 + var4 < var3.size() && var8 < var0.getLineCount(); var8++) {
            ChatLine var9 = (ChatLine)var3.get(var8 + var4);
            int var10 = var1 - var9.getUpdatedCounter();
            if (var10 < 200 || var5) {
               float var11 = Math.max(0.0F, Math.min(1.0F, (1.0F - var10 / 200.0F) * 10.0F));
               int var12 = (int)((var5 ? 1.0F : var11 * var11) * var6 * 255.0F);
               if (var12 > 3) {
                  int var13 = -var8 * 9;
                  com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatHooks.a(0, var13 - 9, var7 + 4, var13, var12 / 2 << 24);
                  com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatHooks.a(
                     b().fontRendererObj, var9.getChatComponent().getFormattedText(), 0.0F, var13 - 8, 16777215 | var12 << 24
                  );
               }
            }
         }

         if (var0.getChatOpen() && !var3.isEmpty()) {
            int var15 = Math.min(var0.getLineCount(), var3.size() - var4);
            int var16 = var15 * 9;
            int var17 = var3.size() * 9;
            if (var17 > var16) {
               int var18 = var4 * var16 / var3.size();
               int var19 = var16 * var16 / var17;
               int var20 = var18 > 0 ? 170 : 96;
               int var14 = b(var0).kazerChatScrolled() ? 13382451 : 3355562;
               Gui.drawRect(-3, -var18, -1, -var18 - var19, var14 | var20 << 24);
               Gui.drawRect(-1, -var18, -2, -var18 - var19, 13421772 | var20 << 24);
            }
         }

         GlStateManager.disableBlend();
         GlStateManager.disableAlpha();
      }
   }

   public static IChatComponent a(Object var0, int var1, int var2) {
      if (!a()) {
         return null;
      } else {
         com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.lastHovered = -1;
         GuiNewChat var3 = (GuiNewChat)var0;
         com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule var4 = com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.instance;
         if (var3.getChatOpen() && !var4.q() && (!var4.a() || var4.c())) {
            Minecraft var5 = b();
            ScaledResolution var6 = new ScaledResolution(var5);
            a(var3, var6, false);
            float var7 = var4.a() ? var4.hud.h() : 1.0F;
            float var8 = var4.a() ? x : 2.0F;
            float var9 = var4.a() ? y + height * var7 : var6.getScaledHeight() - 28;
            float var10 = var3.getChatScale() * var7;
            int var11 = com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatLayoutMath.a((float)var1, var6.getScaleFactor(), var8, var10);
            int var12 = com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatLayoutMath.a((float)var2, var6.getScaleFactor(), var6.getScaledHeight() - var9, var10);
            List var13 = b(var3).kazerDrawnLines();
            int var14 = com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatLayoutMath.a(
               var11, var12, (int)(var3.getChatWidth() / var3.getChatScale()) + 4, var3.getLineCount(), b(var3).kazerChatScroll(), var13.size()
            );
            if (var14 < 0) {
               return null;
            } else {
               ChatLine var15 = (ChatLine)var13.get(var14);
               com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.lastHovered = a(var15);
               if (var4.s() && var4.heads.a(a(var15)) != null) {
                  var11 -= 12;
               }

               if (var11 < 0) {
                  return null;
               } else {
                  int var16 = 0;

                  for (Object var18 : var15.getChatComponent()) {
                     IChatComponent var19 = (IChatComponent)var18;
                     if (var19 instanceof ChatComponentText) {
                        String var20 = ((ChatComponentText)var19).getChatComponentText_TextValue();
                        if (!b().gameSettings.chatColours) {
                           var20 = EnumChatFormatting.getTextWithoutFormattingCodes(var20);
                        }

                        var16 += b().fontRendererObj.getStringWidth(var20);
                        if (var16 > var11) {
                           return var19;
                        }
                     }
                  }

                  return null;
               }
            }
         } else {
            return null;
         }
      }
   }

   private ChatRuntime() {
   }
}
