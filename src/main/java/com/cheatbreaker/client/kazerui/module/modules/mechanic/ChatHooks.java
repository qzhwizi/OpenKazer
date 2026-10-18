package com.cheatbreaker.client.kazerui.module.modules.mechanic;

import com.cheatbreaker.client.kazerui.module.render.ChatLineTag;
import com.cheatbreaker.client.kazerui.module.render.KazerChatAccess;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public final class ChatHooks {
   private static float percent;
   private static long lastUpdate = -1L;
   private static float eased;
   private static int newLines;
   private static int idCounter = 1;
   private static boolean hoverHasHead;
   private static boolean hoverPendingWidth;

   static void a() {
      percent = 1.0F;
      eased = 1.0F;
      lastUpdate = -1L;
      newLines = 0;
   }

   private ChatHooks() {
   }

   private static com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule g() {
      return com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.instance;
   }

   private static Minecraft h() {
      return Minecraft.getMinecraft();
   }

   private static GuiNewChat i() {
      return h().ingameGUI == null ? null : h().ingameGUI.getChatGUI();
   }

   private static List<ChatLine> a(GuiNewChat var0) {
      return ((KazerChatAccess)var0).kazerDrawnLines();
   }

   private static List<ChatLine> b(GuiNewChat var0) {
      return ((KazerChatAccess)var0).kazerChatLines();
   }

   private static int c(GuiNewChat var0) {
      return ((KazerChatAccess)var0).kazerChatScroll();
   }

   private static boolean d(GuiNewChat var0) {
      return ((KazerChatAccess)var0).kazerChatScrolled();
   }

   private static int c(Object var0) {
      return var0 instanceof ChatLineTag ? ((ChatLineTag)var0).kazerui$id() : 0;
   }

   public static IChatComponent a(GuiNewChat var0, IChatComponent var1, boolean var2) {
      IChatComponent var3 = var1;
      if (!var2) {
         com.cheatbreaker.client.kazerui.module.ChatMessageEvent var4 = com.cheatbreaker.client.kazerui.module.ChatMessageEvent.a(var1, idCounter);
         if (var4.cancelled) {
            return null;
         }

         percent = 0.0F;
         if (var4.removeLast) {
            e(var0);
         }

         if (var4.changed) {
            var3 = var4.a();
         }
      }

      int var5 = MathHelper.floor_float(var0.getChatWidth() / var0.getChatScale());
      newLines = com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTextSplitter.a(var3, var5, h().fontRendererObj, false, false).size() - 1;
      return var3;
   }

   public static void b() {
      idCounter++;
   }

   public static void a(GuiNewChat var0, IChatComponent var1) {
      percent = 0.0F;
      int var2 = Math.max(1, MathHelper.floor_float(var0.getChatWidth() / var0.getChatScale()));
      newLines = com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTextSplitter.a(var1, var2, h().fontRendererObj, false, false).size() - 1;
   }

   public static void a(int var0) {
      percent = 0.0F;
      newLines = Math.max(0, var0 - 1);
   }

   public static int b(int var0) {
      com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule var1 = g();
      return var1 != null && var1.a() && var1.unlimitedChat.b() ? 1000 : var0;
   }

   public static void a(Object var0) {
      if (var0 instanceof ChatLineTag) {
         ((ChatLineTag)var0).kazerui$setId(idCounter);
      }
   }

   public static List<IChatComponent> a(IChatComponent var0, int var1, FontRenderer var2, boolean var3, boolean var4) {
      com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule var5 = g();
      if (var5 != null && var5.s() && var5.heads.a(idCounter) != null) {
         var1 -= 12;
      }

      return com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTextSplitter.a(var0, var1, var2, var3, var4);
   }

   private static void e(GuiNewChat var0) {
      List var1 = b(var0);
      List var2 = a(var0);
      if (!var1.isEmpty() && !var2.isEmpty()) {
         int var3 = c(var1.get(0));
         var1.remove(0);

         while (!var2.isEmpty() && c(var2.get(0)) == var3) {
            var2.remove(0);
         }
      }
   }

   static void c(int var0) {
      GuiNewChat var1 = i();
      if (var1 != null) {
         b(var1).removeIf(var1x -> c(var1x) == var0);
         a(var1).removeIf(var1x -> c(var1x) == var0);
      }
   }

   static IChatComponent d(int var0) {
      GuiNewChat var1 = i();
      if (var1 == null) {
         return null;
      } else {
         for (ChatLine var3 : b(var1)) {
            if (c(var3) == var0) {
               return var3.getChatComponent();
            }
         }

         return null;
      }
   }

   public static boolean c() {
      com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule var0 = g();
      if (var0 != null && var0.q()) {
         return true;
      } else {
         long var1 = Minecraft.getSystemTime();
         if (lastUpdate == -1L) {
            lastUpdate = var1;
            percent = 1.0F;
         }

         long var3 = var1 - lastUpdate;
         lastUpdate = var1;
         if (percent < 1.0F) {
            percent = percent + (var0 != null ? var0.o() : com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.a(3)) * (float)var3;
         }

         percent = Math.max(0.0F, Math.min(1.0F, percent));
         float var5 = percent - 1.0F;
         eased = Math.max(0.0F, Math.min(1.0F, 1.0F - var5 * var5 * var5 * var5));
         return false;
      }
   }

   private static boolean j() {
      com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule var0 = g();
      return var0 != null && var0.n();
   }

   private static boolean k() {
      com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule var0 = g();
      return var0 != null && var0.a() && var0.chatHeight.b();
   }

   private static float l() {
      com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule var0 = g();
      return var0 != null && var0.a() ? var0.chatBackgroundOpacity.b() : 1.0F;
   }

   public static void a(float var0, float var1, float var2) {
      GlStateManager.translate(var0, var1, var2);
      GuiNewChat var3 = i();
      if (var3 != null && j() && !var3.getChatOpen() && !e() && !d(var3)) {
         GlStateManager.translate(0.0F, (9.0F - 9.0F * percent) * var3.getChatScale(), 0.0F);
      }
   }

   public static void a(float var0, float var1, float var2, int var3) {
      GlStateManager.scale(var0, var1, var2);
      GuiNewChat var4 = i();
      if (var4 != null) {
         int var5 = var4.getLineCount();
         boolean var6 = var4.getChatOpen();
         int var7 = MathHelper.ceiling_float_int(var4.getChatWidth() / var4.getChatScale());
         float var8 = l();
         boolean var9 = k();
         if (!(var8 <= 0.0F)) {
            int var10 = 0;
            List var11 = a(var4);
            int var12 = c(var4);

            for (int var13 = 0; var13 + var12 < var11.size() && var13 < var5; var13++) {
               ChatLine var14 = (ChatLine)var11.get(var13 + var12);
               if (var14 != null) {
                  double var15 = (var3 - var14.getUpdatedCounter()) / 200.0;
                  var15 = MathHelper.clamp_double((1.0 - var15) * 10.0, 0.0, 1.0);
                  var15 *= var15;
                  if ((int)(255.0 * var15) >= 254 || var6) {
                     var10 -= h().fontRendererObj.FONT_HEIGHT;
                  }
               }
            }

            Gui.drawRect(0, var9 ? -12 : 0, var7 + 4, var10 + (var9 ? -12 : 0), (int)(127.0F * var8) << 24);
         }
      }
   }

   public static void a(int var0, int var1, int var2, int var3, int var4) {
      com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule var5 = g();
      if (var5 == null || !var5.a() || var5.showBackground.b()) {
         float var6 = l();
         if (!(var6 <= 0.0F)) {
            int var7 = (byte)(var4 >> 24) & 255;
            Gui.drawRect(var0, var1, var2, var3, (int)(var6 * var7) << 24);
         }
      }
   }

   public static int a(FontRenderer var0, String var1, float var2, float var3, int var4) {
      GuiNewChat var5 = i();
      com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule var6 = g();
      float var7 = (var3 + 8.0F) / -9.0F;
      boolean var8 = var5 != null && d(var5);
      if (j() && var5 != null && !var5.getChatOpen() && !e() && var7 <= newLines && !var8) {
         var4 = var4 & 16777215 | (int)((var4 >>> 24) * eased) << 24;
      }

      if (var4 >>> 24 <= 3) {
         return 0;
      } else {
         boolean var9 = k();
         boolean var10 = var6 == null || !var6.a() || var6.chatShadow.b();
         if (var6 != null && var6.s() && var5 != null) {
            int var11 = Math.round(var7) + c(var5);
            ResourceLocation var12 = a(var5, var11);
            if (var12 != null) {
               if (b(var5, var11)) {
                  float var13 = var3 - 1.0F;
                  if (var10) {
                     a(var12, var2 + 1.0F, var13 + 1.0F, com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.b(var4));
                  }

                  a(var12, var2, var13, 16777215 | var4 & 0xFF000000);
               }

               var2 += 12.0F;
            }
         }

         GlStateManager.enableBlend();
         return var0.drawString(var1, var2, var3, var4, var10);
      }
   }

   private static void a(ResourceLocation var0, float var1, float var2, int var3) {
      com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var0, (int)var1, (int)var2, 8.0F, 8.0F, 8.0F, 8.0F, 64.0F, 64.0F, var3);
      com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var0, (int)var1, (int)var2, 40.0F, 8.0F, 8.0F, 8.0F, 64.0F, 64.0F, var3);
   }

   private static ResourceLocation a(GuiNewChat var0, int var1) {
      List var2 = a(var0);
      return var1 >= 0 && var1 < var2.size() ? g().heads.a(c(var2.get(var1))) : null;
   }

   private static boolean b(GuiNewChat var0, int var1) {
      List var2 = a(var0);
      return var1 + 1 >= var2.size() || c(var2.get(var1 + 1)) != c(var2.get(var1));
   }

   public static void d() {
      com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.lastHovered = -1;
   }

   public static int a(float var0) {
      return MathHelper.floor_float(var0) - (k() ? 12 : 0);
   }

   public static Object a(List<?> var0, int var1) {
      com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule var2 = g();
      GuiNewChat var3 = i();
      hoverHasHead = var2 != null && var2.s() && var3 != null && a(var3, var1) != null;
      hoverPendingWidth = true;
      return var0.get(var1);
   }

   public static int a(FontRenderer var0, String var1) {
      int var2 = var0.getStringWidth(var1);
      if (hoverPendingWidth) {
         hoverPendingWidth = false;
         if (hoverHasHead) {
            var2 += 12;
         }
      }

      return var2;
   }

   public static void b(Object var0) {
      com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.lastHovered = c(var0);
   }

   public static boolean e() {
      com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule var0 = g();
      return var0 != null && var0.r();
   }

   public static void b(int var0, int var1, int var2, int var3, int var4) {
      com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule var5 = g();
      float var6 = var5 != null ? var5.p() : 1.0F;
      if (var6 != 1.0F) {
         var4 = Math.max(0, Math.min(255, (int)((var4 >>> 24) * var6))) << 24 | var4 & 16777215;
      }

      Gui.drawRect(var0, var1, var2, var3, var4);
   }

   public static IChatComponent a(IChatComponent var0) {
      com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule var1 = g();
      if (var1 == null) {
         return var0;
      } else if (var1.a() && var1.hoverImagePreview.b() && var0 != null) {
         ClickEvent var2 = var0.getChatStyle() == null ? null : var0.getChatStyle().getChatClickEvent();
         if (var2 != null && var2.getAction() == Action.OPEN_URL) {
            return var1.preview.a(var1, var2.getValue(), null) ? null : var0;
         } else {
            return var1.preview.a(var1, null, d(com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.lastHovered)) ? null : var0;
         }
      } else {
         var1.preview.a();
         return var0;
      }
   }

   public static int e(int var0) {
      com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule var1 = g();
      return var1 == null ? var0 : var1.a(h().isSingleplayer(), var0);
   }

   public static String a(String var0, int var1, int var2) {
      com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule var3 = g();
      int var4 = var3 == null ? var2 : var3.a(h().isSingleplayer(), var2);
      return var0.length() > var4 ? var0.substring(0, var4) : var0;
   }

   public static boolean f() {
      return false;
   }

   public static boolean a(GuiScreen var0) {
      com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule var1 = g();
      return var1 != null && var1.a(var0);
   }

   public static void b(GuiScreen var0) {
      com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule var1 = g();
      if (var1 != null) {
         var1.b(var0);
      }
   }

   public static void a(GuiScreen var0, int var1, int var2) {
      com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule var3 = g();
      if (var3 != null) {
         if (var0 instanceof GuiChat) {
            a(a(Mouse.getX(), Mouse.getY()));
         }

         var3.a(var0, var1, var2);
      }
   }

   public static IChatComponent a(int var0, int var1) {
      return i() == null ? null : ChatRuntime.a(i(), var0, var1);
   }
}
