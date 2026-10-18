package com.cheatbreaker.client.gui.uielement.settingseditor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class AutoTextHotkeyListElement extends com.cheatbreaker.client.gui.uielement.UIElement {
   private static final int ROW_HEIGHT = 18;
   private static final int ROW_STEP = 20;
   private static final long DELETE_CONFIRM_MS = 3000L;
   private static final int DRAG_THRESHOLD_PX = 6;
   private static final int RIGHT_INSET = 6;
   private static final int ICON_CELL = 16;
   private static final int BIND_CELL = 96;
   private static final float ICON_SCALE = 4.0F;
   private static final int CONTENT_MAX_LENGTH = 100;
   private static final String MASK = "******";
   private static final int COLOUR_ROW_BASE = -657931;
   private static final int COLOUR_HOVER_FILL = -12418828;
   private static final int COLOUR_TEXT = -1895825408;
   private static final int COLOUR_TEXT_HOVERED_OR_SHADOW = -1;
   private static final int COLOUR_DOT_ON = -13916106;
   private static final int COLOUR_DOT_OFF = -723724;
   private static final int COLOUR_COUNTER = -6250336;
   private static final int COLOUR_DELETE_CONFIRM = -2129930;
   private static final int COLOUR_BAR = -723724;
   private static final int COLOUR_BAR_BORDER = 791621423;
   private static final int COLOUR_BAR_TEXT = 1862270976;
   private static final ResourceLocation PENCIL_ICON = new ResourceLocation("client/icons/pencil-64.png");
   private static final ResourceLocation EYE_OPEN_ICON = new ResourceLocation("client/icons/eye-64.png");
   private static final ResourceLocation EYE_CLOSED_ICON = new ResourceLocation("client/icons/eye-closed-64.png");
   private static final ResourceLocation DELETE_ICON = new ResourceLocation("client/icons/delete-64.png");
   private final com.cheatbreaker.client.module.modules.keybinds.AutoTextHotkeyModule module;
   private final Map<String, Integer> hoverFill = new HashMap<>();
   private String pressedRowId;
   private com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner pressedRegion = com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.NONE;
   private int pressStartX;
   private int pressStartY;
   private boolean dragging;
   private String draggingId;
   private int dragTargetIndex = -1;
   private String confirmDeleteId;
   private long confirmDeleteUntilMs;
   private final com.cheatbreaker.client.module.modules.keybinds.KeyCaptureSession capture = new com.cheatbreaker.client.module.modules.keybinds.KeyCaptureSession();
   private String capturingRowId;
   private boolean swallowLocalPress;
   private String pendingAddRowId;

   public AutoTextHotkeyListElement(com.cheatbreaker.client.module.modules.keybinds.AutoTextHotkeyModule var1, float var2) {
      super(var2);
      this.module = var1;
   }

   @Override
   public int c() {
      return 20 + this.module.a().items.size() * 20 + 4;
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
      return a(var0, "ADD HOTKEY") + 20;
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

   com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner c(int var1, int var2, int var3) {
      if (!this.d(var1, var2, var3)) {
         return com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.NONE;
      } else if (var1 <= (this.x + 14) * this.scale) {
         return com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.TOGGLE;
      } else {
         int var4 = this.x + this.width - 6;
         int var5 = var4 - 16;
         int var6 = var5 - 16;
         int var7 = var6 - 16;
         int var8 = var7 - 96;
         if (var1 > var5 * this.scale && var1 <= var4 * this.scale) {
            return com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.DELETE;
         } else if (var1 > var6 * this.scale && var1 <= var5 * this.scale) {
            return com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.EYE;
         } else if (var1 > var7 * this.scale && var1 <= var6 * this.scale) {
            return com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.PENCIL;
         } else {
            return var1 > var8 * this.scale && var1 <= var7 * this.scale ? com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.BIND : com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.CONTENT;
         }
      }
   }

   private int a(com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner var1) {
      int var2 = this.x + this.width - 6;
      switch (var1) {
         case DELETE:
            return var2 - 16;
         case EYE:
            return var2 - 32;
         case PENCIL:
            return var2 - 48;
         case BIND:
         default:
            return var2 - 48 - 96;
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      Minecraft var4 = Minecraft.getMinecraft();
      int var5 = this.x + this.width;
      this.g();
      String var6 = this.module.a().items.size() + " / " + 32;
      a(var4, var6, var5 - 6 - a(var4, var6), this.y + 5, -6250336);

      for (int var7 = 0; var7 < this.module.a().items.size(); var7++) {
         com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry var8 = this.module.a().items.get(var7);
         int var9 = this.d() + var7 * 20;
         com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner var10 = this.draggingId == null ? this.c(var1, var2, var9) : com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.NONE;
         boolean var11 = var10 == com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.CONTENT || var10 == com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.TOGGLE;
         int var12 = var8.id.equals(this.draggingId) ? -1610612736 : -657931;
         cn.kazer.client189.render.DrawRect.a(this.x, var9, var5, var9 + 18, var12);
         int var13 = a(this.hoverFill.getOrDefault(var8.id, 0), var11);
         this.hoverFill.put(var8.id, var13);
         if (var13 > 0) {
            int var14 = var13 * 18 / 75;
            cn.kazer.client189.render.DrawRect.a(this.x, var9 + 18 - var14, var5, var9 + 18, -12418828);
         }

         int var34 = this.a(var8.id, com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.BIND, var10);
         int var15 = this.a(var8.id, com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.PENCIL, var10);
         int var16 = this.a(var8.id, com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.EYE, var10);
         int var17 = this.a(var8.id, com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.DELETE, var10);
         if (var15 > 0) {
            this.a(com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.PENCIL, var9, var15);
         }

         if (var16 > 0) {
            this.a(com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.EYE, var9, var16);
         }

         if (var17 > 0) {
            this.a(com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.DELETE, var9, var17);
         }

         cn.kazer.client189.render.DrawRect.a(this.x + 2, var9 + 4, this.x + 12, var9 + 14, 791621423);
         cn.kazer.client189.render.DrawRect.a(this.x + 3, var9 + 5, this.x + 11, var9 + 13, var8.enabled ? -13916106 : -723724);
         String var18 = var8.hidden ? "******" : var8.content;
         int var19 = var5 - 6 - 48 - 96 - 4 - (this.x + 16);
         if (a(var4, var18) > var19) {
            var18 = a(var4, var18, Math.max(0, var19 - a(var4, "..."))) + "...";
         }

         a(var4, var18, this.x + 16, var9 + 5, var13 > 0 ? -1 : -1895825408);
         this.a(var8, var9, var34, var15, var16, var17);
      }

      if (this.draggingId != null) {
         int var20 = (int)(var2 / this.scale - 9.0F - this.yOffset);
         cn.kazer.client189.render.DrawRect.a(this.x, var20, var5, var20 + 18, -1610612736);
         com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry var22 = this.module.a().b(this.draggingId);
         String var24 = var22 == null ? "" : (var22.hidden ? "******" : var22.content);
         a(var4, var24, this.x + 16, var20 + 5, -1);
         if (this.dragTargetIndex >= 0) {
            int var26 = this.d() + this.dragTargetIndex * 20;
            cn.kazer.client189.render.DrawRect.a(this.x, var26 - 1, var5, var26 + 1, -2039808);
         }
      }

      com.cheatbreaker.client.module.modules.keybinds.AutoTextState var21 = this.module.a();
      boolean var23 = var21.items.size() < 32;
      boolean var25 = var23
         && var1 > this.x * this.scale
         && var1 < (this.x + a(var4)) * this.scale
         && var2 > (this.y + this.yOffset) * this.scale
         && var2 < (this.y + 14 + this.yOffset) * this.scale;
      a(var4, var23 ? "+ ADD HOTKEY" : "ADD HOTKEY", this.x, this.y + 5, var23 ? (var25 ? -12418828 : -6250336) : -2130706433);
      if (this.pressedRowId != null) {
         boolean var27 = Mouse.isButtonDown(0);
         if (var27 && !this.dragging && this.pressedRegion == com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.CONTENT) {
            int var28 = Math.abs(var1 - this.pressStartX) + Math.abs(var2 - this.pressStartY);
            if (var28 >= 6) {
               this.dragging = true;
               this.draggingId = this.pressedRowId;
            }
         }

         if (!var27) {
            if (this.dragging) {
               if (this.dragTargetIndex >= 0) {
                  this.module.a(this.pressedRowId, this.dragTargetIndex);
               }

               this.f();
            } else {
               com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry var29 = this.module.a().b(this.pressedRowId);
               com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner var31 = this.pressedRegion;
               int var33 = var29 != null ? this.module.a().items.indexOf(var29) : -1;
               com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner var35 = var33 >= 0 ? this.c(var1, var2, this.d() + var33 * 20) : com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.NONE;
               this.f();
               if (var29 != null && var35 == var31) {
                  if (var31 == com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.PENCIL) {
                     i();
                     this.a(var29);
                  } else if (var31 == com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.BIND) {
                     i();
                     this.c(var29);
                  }
               }
            }
         } else if (this.dragging) {
            float var30 = var2 / this.scale - this.yOffset;
            int var32 = (int)((var30 - this.d()) / 20.0F);
            this.dragTargetIndex = e(var32, 0, this.module.a().items.size() - 1);
         }
      }

      if (this.confirmDeleteId != null && Minecraft.getSystemTime() > this.confirmDeleteUntilMs) {
         this.confirmDeleteId = null;
      }
   }

   private int a(String var1, com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner var2, com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner var3) {
      String var4 = var1 + "#" + var2.name();
      int var5 = a(this.hoverFill.getOrDefault(var4, 0), var3 == var2);
      this.hoverFill.put(var4, var5);
      return var5;
   }

   private void a(com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner var1, int var2, int var3) {
      int var4 = this.a(var1);
      int var5 = var1 == com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.BIND ? 96 : 16;
      int var6 = var3 * 18 / 75;
      cn.kazer.client189.render.DrawRect.a(var4, var2 + 18 - var6, var4 + var5, var2 + 18, -12418828);
   }

   private void a(com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry var1, int var2, int var3, int var4, int var5, int var6) {
      Minecraft var7 = Minecraft.getMinecraft();
      byte var8 = 4;
      GL11.glPushMatrix();
      int var9 = this.a(com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.BIND) + 1;
      int var10 = var2 + 3;
      byte var11 = 94;
      byte var12 = 12;
      cn.kazer.client189.render.DrawRect.a(var9, var10, var9 + var11, var10 + var12, 791621423);
      cn.kazer.client189.render.DrawRect.a(var9 + 1, var10 + 1, var9 + var11 - 1, var10 + var12 - 1, -723724);
      if (var3 > 0) {
         int var13 = var3 * (var12 - 2) / 75;
         cn.kazer.client189.render.DrawRect.a(var9 + 1, var10 + var12 - 1 - var13, var9 + var11 - 1, var10 + var12 - 1, -12418828);
      }

      String var15 = this.d(var1);
      int var14 = var11 - 6;
      if (a(var7, var15) > var14) {
         var15 = a(var7, var15, Math.max(0, var14 - a(var7, ".."))) + "..";
      }

      a(var7, var15, var9 + (var11 - a(var7, var15)) / 2.0F, var10 + 2, var3 > 0 ? -1 : 1862270976);
      this.a(PENCIL_ICON, this.a(com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.PENCIL) + var8, var2 + 5, var4 > 0);
      this.a(var1.hidden ? EYE_CLOSED_ICON : EYE_OPEN_ICON, this.a(com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.EYE) + var8, var2 + 5, var5 > 0);
      if (var1.id.equals(this.confirmDeleteId)) {
         cn.kazer.client189.render.GlStateBridge.a(1.0F, 0.3F, 0.3F, 1.0F);
      } else {
         a(var6 > 0);
      }

      com.cheatbreaker.client.gui.render.RenderUtils.a(DELETE_ICON, 4.0F, this.a(com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.DELETE) + var8, var2 + 5);
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
      this.pressedRegion = com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.NONE;
      this.pressStartX = 0;
      this.pressStartY = 0;
      this.dragging = false;
      this.draggingId = null;
      this.dragTargetIndex = -1;
   }

   @Override
   public void a(int var1, int var2, int var3) {
      if (this.swallowLocalPress) {
         this.swallowLocalPress = false;
      } else if (var3 == 0) {
         Minecraft var4 = Minecraft.getMinecraft();

         for (int var5 = 0; var5 < this.module.a().items.size(); var5++) {
            com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry var6 = this.module.a().items.get(var5);
            int var7 = this.d() + var5 * 20;
            com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner var8 = this.c(var1, var2, var7);
            if (var8 != com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement.aInner.NONE) {
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

                     i();
                     return;
                  case EYE:
                     this.module.b(var6.id, !var6.hidden);
                     i();
                     return;
                  case PENCIL:
                  case BIND:
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
                     i();
                     return;
               }
            }
         }

         com.cheatbreaker.client.module.modules.keybinds.AutoTextState var9 = this.module.a();
         boolean var10 = var9.items.size() < 32;
         boolean var11 = var10
            && var1 > this.x * this.scale
            && var1 < (this.x + a(var4)) * this.scale
            && var2 > (this.y + this.yOffset) * this.scale
            && var2 < (this.y + 14 + this.yOffset) * this.scale;
         if (var11) {
            i();
            com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry var12 = this.module.z();
            if (var12 != null) {
               this.b(var12);
            }
         }
      }
   }

   private void a(com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry var1) {
      if (com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance != null) {
         Minecraft.getMinecraft().displayGuiScreen(new com.cheatbreaker.client.gui.hudeditor.TextInputScreen(com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance, "Text: ", var1.content, var2 -> {
            this.module.a(var1.id, var2);
            if (com.cheatbreaker.client.module.modules.keybinds.SensitiveCommandFilter.a(var2)) {
               this.module.b(var1.id, true);
            }
         }, 1.0F, 100, null, () -> com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance.a(this.module)));
      }
   }

   private void b(com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry var1) {
      if (com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance != null) {
         this.pendingAddRowId = var1.id;
         Minecraft.getMinecraft().displayGuiScreen(new com.cheatbreaker.client.gui.hudeditor.TextInputScreen(com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance, "Text: ", "", var2 -> {
            this.module.a(var1.id, var2);
            if (com.cheatbreaker.client.module.modules.keybinds.SensitiveCommandFilter.a(var2)) {
               this.module.b(var1.id, true);
            }

            this.pendingAddRowId = null;
         }, 1.0F, 100, () -> this.module.a(this.pendingAddRowId), () -> com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance.a(this.module)));
      }
   }

   private void c(com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry var1) {
      this.capture.a(var1.keys);
      this.capturingRowId = var1.id;
   }

   private String d(com.cheatbreaker.client.module.modules.keybinds.AutoTextEntry var1) {
      boolean var2 = var1.id.equals(this.capturingRowId);
      return com.cheatbreaker.client.module.modules.keybinds.KeyNameFormatter.a(a(var1.keys), var2);
   }

   private static int[] a(List<Integer> var0) {
      int[] var1 = new int[var0.size()];

      for (int var2 = 0; var2 < var0.size(); var2++) {
         var1[var2] = (Integer)var0.get(var2);
      }

      return var1;
   }

   @Override
   public boolean a(char var1, int var2) {
      if (this.capturingRowId != null && var2 != 0) {
         this.capture.a(var2);
         this.h();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void b(int var1, int var2, int var3) {
      if (this.capturingRowId != null) {
         this.capture.b(var3 - 100);
         this.h();
         this.swallowLocalPress = true;
      } else {
         super.b(var1, var2, var3);
      }
   }

   @Override
   public void a() {
      if (this.capturingRowId != null) {
         this.capturingRowId = null;
      }
   }

   private void g() {
      if (this.capturingRowId != null && this.capture.a() == com.cheatbreaker.client.module.modules.keybinds.KeyCaptureSession.a.CAPTURING) {
         for (int var2 : this.capture.b()) {
            boolean var3 = var2 >= 0 ? Keyboard.isKeyDown(var2) : Mouse.isButtonDown(var2 + 100);
            if (!var3) {
               this.capture.c(var2);
               break;
            }
         }

         this.h();
      }
   }

   private void h() {
      if (this.capturingRowId != null) {
         switch (this.capture.a()) {
            case FINISHED:
            case CLEARED:
               this.module.a(this.capturingRowId, this.capture.b());
               this.capturingRowId = null;
               break;
            case CANCELLED:
               this.capturingRowId = null;
         }
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

   private static void i() {
      Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
   }

   static enum aInner {
      NONE,
      CONTENT,
      TOGGLE,
      BIND,
      PENCIL,
      EYE,
      DELETE;
   }
}
