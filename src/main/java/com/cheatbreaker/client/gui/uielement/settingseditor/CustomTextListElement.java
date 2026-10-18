package com.cheatbreaker.client.gui.uielement.settingseditor;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class CustomTextListElement extends com.cheatbreaker.client.gui.uielement.UIElement {
   private static final int ROW_HEIGHT = 18;
   private static final int ROW_STEP = 20;
   private static final long DELETE_CONFIRM_MS = 3000L;
   private static final int DRAG_THRESHOLD_PX = 6;
   private static final int RIGHT_INSET = 6;
   private static final int ICON_CELL = 16;
   private static final float ICON_SCALE = 4.0F;
   private static final int COLOUR_ROW_BASE = -657931;
   private static final int COLOUR_HOVER_FILL = -12418828;
   private static final int COLOUR_TEXT = -1895825408;
   private static final int COLOUR_TEXT_HOVERED_OR_SHADOW = -1;
   private static final int COLOUR_DOT_ON = -13916106;
   private static final int COLOUR_DOT_OFF = -723724;
   private static final int COLOUR_COUNTER = -6250336;
   private static final int COLOUR_DELETE_CONFIRM = -2129930;
   private static final ResourceLocation PENCIL_ICON = new ResourceLocation("client/icons/pencil-64.png");
   private static final ResourceLocation COG_ICON = new ResourceLocation("client/icons/cog-64.png");
   private static final ResourceLocation DELETE_ICON = new ResourceLocation("client/icons/delete-64.png");
   private static final ResourceLocation PLUS_ICON = new ResourceLocation("client/icons/plus-64.png");
   private final com.cheatbreaker.client.module.modules.config.CustomTextModule module;
   private final Map<String, Integer> hoverFill = new HashMap<>();
   private String pressedRowId;
   private com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a pressedRegion = com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a.NONE;
   private int pressStartX;
   private int pressStartY;
   private boolean dragging;
   private String draggingId;
   private int dragTargetIndex = -1;
   private String confirmDeleteId;
   private long confirmDeleteUntilMs;

   public CustomTextListElement(com.cheatbreaker.client.module.modules.config.CustomTextModule var1, float var2) {
      super(var2);
      this.module = var1;
   }

   @Override
   public int c() {
      return 20 + this.module.z().items.size() * 20 + 4;
   }

   private int d() {
      return this.y + 20;
   }

   private boolean d(int var1, int var2, int var3) {
      return var1 > this.x * this.scale
         && var1 < (this.x + this.width) * this.scale
         && var2 > (var3 + this.yOffset) * this.scale
         && var2 < (var3 + 18 + this.yOffset) * this.scale;
   }

   private static int a(Minecraft var0) {
      return a(var0, "ADD TEXT") + 20;
   }

   private static com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer e() {
      return com.cheatbreaker.client.CheatBreakerClient.N() != null ? com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px : null;
   }

   private static int a(Minecraft var0, String var1) {
      com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer var2 = e();
      return var2 != null ? var2.a(var1) : var0.fontRendererObj.getStringWidth(var1);
   }

   private static void a(Minecraft var0, String var1, float var2, float var3, int var4) {
      com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer var5 = e();
      if (var5 != null) {
         var5.b(var1, (double)var2, (double)var3, var4);
      } else {
         var0.fontRendererObj.drawString(var1, (int)var2, (int)var3, var4, false);
      }
   }

   private static String a(Minecraft var0, String var1, int var2) {
      com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer var3 = e();
      return var3 != null ? var3.a(var1, var2) : var0.fontRendererObj.trimStringToWidth(var1, var2);
   }

   private static int a(int var0, boolean var1) {
      int var2 = (int)com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.a(790.0F);
      return var1 ? Math.min(75, var0 + var2) : Math.max(0, var0 - var2);
   }

   com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a c(int var1, int var2, int var3) {
      if (!this.d(var1, var2, var3)) {
         return com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a.NONE;
      } else if (var1 <= (this.x + 14) * this.scale) {
         return com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a.TOGGLE;
      } else {
         int var4 = this.x + this.width - 6;
         int var5 = var4 - 16;
         int var6 = var5 - 16;
         int var7 = var6 - 16;
         if (var1 > var5 * this.scale && var1 <= var4 * this.scale) {
            return com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a.DELETE;
         } else if (var1 > var6 * this.scale && var1 <= var5 * this.scale) {
            return com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a.COG;
         } else {
            return var1 > var7 * this.scale && var1 <= var6 * this.scale ? com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a.PENCIL : com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a.CONTENT;
         }
      }
   }

   private int a(com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a var1) {
      int var2 = this.x + this.width - 6;
      switch (var1) {
         case DELETE:
            return var2 - 16;
         case COG:
            return var2 - 32;
         case PENCIL:
         default:
            return var2 - 48;
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      Minecraft var4 = Minecraft.getMinecraft();
      int var5 = this.x + this.width;
      String var6 = this.module.z().items.size() + " / " + 32;
      a(var4, var6, var5 - 6 - a(var4, var6), this.y + 5, -6250336);

      for (int var7 = 0; var7 < this.module.z().items.size(); var7++) {
         com.cheatbreaker.client.module.modules.config.CustomTextItem var8 = this.module.z().items.get(var7);
         int var9 = this.d() + var7 * 20;
         com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a var10 = this.draggingId == null ? this.c(var1, var2, var9) : com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a.NONE;
         boolean var11 = var10 == com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a.CONTENT || var10 == com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a.TOGGLE;
         int var12 = var8.id.equals(this.draggingId) ? -1610612736 : -657931;
         cn.kazer.client189.render.DrawRect.a(this.x, var9, var5, var9 + 18, var12);
         int var13 = a(this.hoverFill.getOrDefault(var8.id, 0), var11);
         this.hoverFill.put(var8.id, var13);
         if (var13 > 0) {
            int var14 = var13 * 18 / 75;
            cn.kazer.client189.render.DrawRect.a(this.x, var9 + 18 - var14, var5, var9 + 18, -12418828);
         }

         int var31 = this.a(var8.id, com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a.PENCIL, var10);
         int var15 = this.a(var8.id, com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a.COG, var10);
         int var16 = this.a(var8.id, com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a.DELETE, var10);
         if (var31 > 0) {
            this.a(com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a.PENCIL, var9, var31);
         }

         if (var15 > 0) {
            this.a(com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a.COG, var9, var15);
         }

         if (var16 > 0) {
            this.a(com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a.DELETE, var9, var16);
         }

         cn.kazer.client189.render.DrawRect.a(this.x + 2, var9 + 4, this.x + 12, var9 + 14, 791621423);
         cn.kazer.client189.render.DrawRect.a(this.x + 3, var9 + 5, this.x + 11, var9 + 13, var8.enabled ? -13916106 : -723724);
         String var17 = var8.content;
         int var18 = var5 - 6 - 48 - 4 - (this.x + 16);
         if (a(var4, var17) > var18) {
            var17 = a(var4, var17, Math.max(0, var18 - a(var4, "..."))) + "...";
         }

         a(var4, var17, this.x + 16, var9 + 5, var13 > 0 ? -1 : -1895825408);
         this.a(var8, var9, var31, var15, var16);
      }

      if (this.draggingId != null) {
         int var19 = (int)(var2 / this.scale - 9.0F - this.yOffset);
         cn.kazer.client189.render.DrawRect.a(this.x, var19, var5, var19 + 18, -1610612736);
         float var10002 = this.x + 16;
         a(var4, this.module.z().b(this.draggingId).content, var10002, var19 + 5, -1);
         if (this.dragTargetIndex >= 0) {
            int var21 = this.d() + this.dragTargetIndex * 20;
            cn.kazer.client189.render.DrawRect.a(this.x, var21 - 1, var5, var21 + 1, -2039808);
         }
      }

      com.cheatbreaker.client.module.modules.config.CustomTextRegistry var20 = this.module.z();
      boolean var22 = var20.items.size() < 32;
      boolean var23 = var22
         && var1 > this.x * this.scale
         && var1 < (this.x + a(var4)) * this.scale
         && var2 > (this.y + this.yOffset) * this.scale
         && var2 < (this.y + 14 + this.yOffset) * this.scale;
      a(var4, var22 ? "+ ADD TEXT" : "ADD TEXT", this.x, this.y + 5, var22 ? (var23 ? -12418828 : -6250336) : -2130706433);
      if (this.pressedRowId != null) {
         boolean var24 = Mouse.isButtonDown(0);
         if (var24 && !this.dragging && this.pressedRegion == com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a.CONTENT) {
            int var25 = Math.abs(var1 - this.pressStartX) + Math.abs(var2 - this.pressStartY);
            if (var25 >= 6) {
               this.dragging = true;
               this.draggingId = this.pressedRowId;
            }
         }

         if (!var24) {
            if (this.dragging) {
               if (this.dragTargetIndex >= 0) {
                  this.module.a(this.pressedRowId, this.dragTargetIndex);
               }

               this.f();
            } else {
               com.cheatbreaker.client.module.modules.config.CustomTextItem var26 = this.module.z().b(this.pressedRowId);
               com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a var28 = this.pressedRegion;
               int var30 = var26 != null ? this.module.z().items.indexOf(var26) : -1;
               com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a var32 = var30 >= 0 ? this.c(var1, var2, this.d() + var30 * 20) : com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a.NONE;
               this.f();
               if (var26 != null && var32 == var28) {
                  if (var28 == com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a.PENCIL) {
                     g();
                     this.b(var26);
                  } else {
                     this.a(var26);
                  }
               }
            }
         } else if (this.dragging) {
            float var27 = var2 / this.scale - this.yOffset;
            int var29 = (int)((var27 - this.d()) / 20.0F);
            this.dragTargetIndex = e(var29, 0, this.module.z().items.size() - 1);
         }
      }

      if (this.confirmDeleteId != null && Minecraft.getSystemTime() > this.confirmDeleteUntilMs) {
         this.confirmDeleteId = null;
      }
   }

   private int a(String var1, com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a var2, com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a var3) {
      String var4 = var1 + "#" + var2.name();
      int var5 = a(this.hoverFill.getOrDefault(var4, 0), var3 == var2);
      this.hoverFill.put(var4, var5);
      return var5;
   }

   private void a(com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a var1, int var2, int var3) {
      int var4 = this.a(var1);
      int var5 = var3 * 18 / 75;
      cn.kazer.client189.render.DrawRect.a(var4, var2 + 18 - var5, var4 + 16, var2 + 18, -12418828);
   }

   private void a(com.cheatbreaker.client.module.modules.config.CustomTextItem var1, int var2, int var3, int var4, int var5) {
      boolean var6 = var1.id.equals(this.confirmDeleteId);
      byte var7 = 4;
      GL11.glPushMatrix();
      this.a(PENCIL_ICON, this.a(com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a.PENCIL) + var7, var2 + 5, var3 > 0);
      this.a(COG_ICON, this.a(com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a.COG) + var7, var2 + 5, var4 > 0);
      if (var6) {
         cn.kazer.client189.render.GlStateBridge.a(1.0F, 0.3F, 0.3F, 1.0F);
      } else {
         a(var5 > 0);
      }

      com.cheatbreaker.client.gui.render.RenderUtils.a(DELETE_ICON, 4.0F, this.a(com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a.DELETE) + var7, var2 + 5);
      GL11.glPopMatrix();
   }

   private void a(ResourceLocation var1, int var2, int var3, boolean var4) {
      a(var4);
      com.cheatbreaker.client.gui.render.RenderUtils.a(var1, 4.0F, var2, var3);
   }

   private static void a(boolean var0) {
      if (var0) {
         cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
      } else {
         cn.kazer.client189.render.GlStateBridge.a(0.65F, 0.65F, 0.65F, 1.0F);
      }
   }

   private void f() {
      this.pressedRowId = null;
      this.pressedRegion = com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a.NONE;
      this.pressStartX = 0;
      this.pressStartY = 0;
      this.dragging = false;
      this.draggingId = null;
      this.dragTargetIndex = -1;
   }

   @Override
   public void a(int var1, int var2, int var3) {
      if (var3 == 0) {
         Minecraft var4 = Minecraft.getMinecraft();

         for (int var5 = 0; var5 < this.module.z().items.size(); var5++) {
            com.cheatbreaker.client.module.modules.config.CustomTextItem var6 = this.module.z().items.get(var5);
            int var7 = this.d() + var5 * 20;
            com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a var8 = this.c(var1, var2, var7);
            if (var8 != com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement.a.NONE) {
               switch (var8) {
                  case DELETE:
                     if (var6.id.equals(this.confirmDeleteId) && Minecraft.getSystemTime() <= this.confirmDeleteUntilMs) {
                        this.module.a(var6.id);
                        this.confirmDeleteId = null;
                        if (com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance != null) {
                           com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance.a();
                        }
                     } else {
                        this.confirmDeleteId = var6.id;
                        this.confirmDeleteUntilMs = Minecraft.getSystemTime() + 3000L;
                     }

                     g();
                     return;
                  case COG:
                  case PENCIL:
                  case CONTENT:
                  default:
                     this.pressedRowId = var6.id;
                     this.pressedRegion = var8;
                     this.pressStartX = var1;
                     this.pressStartY = var2;
                     this.dragging = false;
                     this.draggingId = null;
                     this.dragTargetIndex = var5;
                     return;
                  case TOGGLE:
                     this.module.a(var6.id, !var6.enabled);
                     g();
                     return;
               }
            }
         }

         com.cheatbreaker.client.module.modules.config.CustomTextRegistry var10 = this.module.z();
         boolean var11 = var10.items.size() < 32;
         boolean var12 = var11
            && var1 > this.x * this.scale
            && var1 < (this.x + a(var4)) * this.scale
            && var2 > (this.y + this.yOffset) * this.scale
            && var2 < (this.y + 14 + this.yOffset) * this.scale;
         if (var12) {
            g();
            com.cheatbreaker.client.module.modules.config.CustomTextItem var13 = this.module.B();
            if (var13 != null && com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance != null) {
               com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance.a();
               com.cheatbreaker.client.module.modules.config.CustomTextModule.a var9 = this.module.c(var13.id);
               if (var9 != null) {
                  Minecraft.getMinecraft()
                     .displayGuiScreen(new com.cheatbreaker.client.gui.hudeditor.ModulePlacementScreen(com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance, var9, () -> this.a(var9)));
               }
            }
         }
      }
   }

   private void a(com.cheatbreaker.client.module.modules.config.CustomTextItem var1) {
      com.cheatbreaker.client.module.modules.config.CustomTextModule.a var2 = this.module.c(var1.id);
      if (var2 != null && com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance != null) {
         com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance.a(var2);
      }
   }

   private void a(com.cheatbreaker.client.module.modules.config.CustomTextModule.a var1) {
      if (com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance != null) {
         com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance.a();
         com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance.a(var1);
      }
   }

   private void b(com.cheatbreaker.client.module.modules.config.CustomTextItem var1) {
      if (com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance != null) {
         Minecraft.getMinecraft()
            .displayGuiScreen(
               new com.cheatbreaker.client.gui.hudeditor.TextInputScreen(com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance, "Text: ", var1.content, var2 -> this.module.a(var1.id, var2))
            );
      }
   }

   public static int a(float var0, float var1, float var2, int var3) {
      int var4 = (int)((var1 - var0) / var2);
      return e(var4, 0, var3 - 1);
   }

   public static boolean b(int var0, int var1, int var2, int var3) {
      return Math.abs(var2 - var0) + Math.abs(var3 - var1) >= 6;
   }

   private static int e(int var0, int var1, int var2) {
      return Math.max(var1, Math.min(var2, var0));
   }

   private static void g() {
      Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
   }

   static enum a {
      NONE,
      CONTENT,
      TOGGLE,
      PENCIL,
      COG,
      DELETE;
   }
}
