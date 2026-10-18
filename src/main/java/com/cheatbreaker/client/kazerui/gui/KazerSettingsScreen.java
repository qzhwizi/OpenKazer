package com.cheatbreaker.client.kazerui.gui;

import cn.kazer.client189.input.WindowsImeSupport;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.Locale;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public final class KazerSettingsScreen extends GuiScreen {
   private static final float PAGE_X = 110.0F;
   private static final float PAGE_Y = 42.5F;
   private static final float PAGE_W = 377.0F;
   private static final float PAGE_H = 255.0F;
   private final com.cheatbreaker.client.kazerui.hud.HudFeatureManager features;
   private final KeyBinding openKey;
   private final com.cheatbreaker.client.kazerui.gui.components.MenuModel model;
   private final com.cheatbreaker.client.kazerui.gui.components.MenuRenderer renderer = new com.cheatbreaker.client.kazerui.gui.components.MenuRenderer();
   private final com.cheatbreaker.client.kazerui.gui.components.MenuViewport viewport = new com.cheatbreaker.client.kazerui.gui.components.MenuViewport();
   private DynamicTexture texture;
   private ResourceLocation textureLocation;
   private com.cheatbreaker.client.kazerui.gui.components.MenuRenderer.b drag;
   private long paintedRevision = Long.MIN_VALUE;
   private float paintedX;
   private float paintedY;
   private float paintedScroll;
   private boolean paintedBlink;
   private boolean openingKeyHeld;
   private boolean selectAll;
   private long lastPaint;
   private int rasterScale;
   private boolean previewDragging;
   private float previewX;
   private final GuiScreen background;
   private long toastSeen;

   public KazerSettingsScreen(com.cheatbreaker.client.kazerui.hud.HudFeatureManager var1, KeyBinding var2) {
      this(var1, var2, null);
   }

   public KazerSettingsScreen(com.cheatbreaker.client.kazerui.hud.HudFeatureManager var1, KeyBinding var2, GuiScreen var3) {
      this.features = var1;
      this.openKey = var2;
      this.model = var1.c();
      this.background = var3;
      this.model.b();
   }

   private void a() {
      this.mc.displayGuiScreen(this.mc.theWorld == null ? this.background : null);
   }

   public void updateScreen() {
      if (this.mc.theWorld == null) {
         com.cheatbreaker.client.kazerui.gui.menus.HomeScreen.d();
      }
   }

   public void initGui() {
      this.viewport.a(this.width, this.height);
      this.rasterScale = Math.max(2, Math.min(4, (int)Math.ceil(this.viewport.scale * new ScaledResolution(this.mc).getScaleFactor())));
      this.e();
      this.model.closeRequested = false;
      this.model.editHudRequested = false;
      this.model.captureKey = false;
      this.model.inWorld = this.mc.theWorld != null;
      this.model.store.b("menuKeyName", Keyboard.getKeyName(this.openKey.getKeyCode()) == null ? "NONE" : Keyboard.getKeyName(this.openKey.getKeyCode()));
      com.cheatbreaker.client.settings.KeybindRegistry var1 = com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.keyBindings;

      for (String var5 : new String[]{"freelook", "zoom"}) {
         KeyBinding var6 = var1.a(var5.equals("freelook") ? "Freelook" : "Zoom");
         if (var6 != null) {
            this.model.store.b(var5 + "KeyName", a(var6.getKeyCode()));
         }
      }

      Keyboard.enableRepeatEvents(true);
      this.openingKeyHeld = this.openKey.getKeyCode() > 0 && Keyboard.isKeyDown(this.openKey.getKeyCode());
   }

   private boolean b() {
      return this.model.searchFocused || !this.model.editorFocus.isEmpty() || !this.model.textEditing.isEmpty() || !this.model.hexEditing.isEmpty();
   }

   private static String a(int var0) {
      return GameSettings.getKeyDisplayString(var0);
   }

   private void a(float var1, float var2, boolean var3) {
      boolean var4 = System.currentTimeMillis() / 500L % 2L == 0L;
      com.cheatbreaker.client.kazerui.gui.components.ScrollPhysics var5 = this.model.scroller;
      boolean var6 = this.b() && var4;
      long var7 = System.nanoTime();
      boolean var9 = var5.b() || var5.dragging || this.drag != null;
      boolean var10 = this.paintedRevision == this.model.revision
         && this.paintedX == var1
         && this.paintedY == var2
         && this.paintedScroll == var5.offset
         && this.paintedBlink == var6
         && !this.renderer.animating
         && !var9;
      if (var3 || !var10 && var7 - this.lastPaint >= 8333333L) {
         BufferedImage var11 = this.renderer.a(this.model, var1, var2, this.rasterScale, var4);
         if (this.texture == null) {
            this.texture = new DynamicTexture(var11.getWidth(), var11.getHeight());
            this.texture.setBlurMipmap(true, false);
         }

         int[] var12 = ((DataBufferInt)var11.getRaster().getDataBuffer()).getData();
         System.arraycopy(var12, 0, this.texture.getTextureData(), 0, var12.length);
         this.texture.updateDynamicTexture();
         this.paintedRevision = this.model.revision;
         this.paintedX = var1;
         this.paintedY = var2;
         this.paintedScroll = var5.offset;
         this.paintedBlink = var6;
         this.lastPaint = var7;
      }
   }

   public void drawScreen(int var1, int var2, float var3) {
      if (!this.model.resetBindingRequested.isEmpty()) {
         String var4 = this.model.resetBindingRequested;
         this.model.resetBindingRequested = "";
         com.cheatbreaker.client.CheatBreakerClient var5 = com.cheatbreaker.client.CheatBreakerClient.N();
         KeyBinding var6 = var4.equals("menu") ? this.openKey : var5.globalSettings.keyBindings.a(var4.equals("freelook") ? "Freelook" : "Zoom");
         if (var6 != null) {
            this.a(var4, var6, var6.getKeyCodeDefault());
         }
      }

      if (this.openingKeyHeld && !Keyboard.isKeyDown(this.openKey.getKeyCode())) {
         this.openingKeyHeld = false;
      }

      if (this.model.toastUntil != this.toastSeen) {
         this.toastSeen = this.model.toastUntil;
         if (!this.model.toast.isEmpty() && System.currentTimeMillis() < this.model.toastUntil) {
            com.cheatbreaker.client.kazerui.gui.ToastNotifications.a(this.model.toast);
         }
      }

      if (this.mc.theWorld == null) {
         com.cheatbreaker.client.kazerui.gui.menus.HomeScreen.a(this.background, this, var3);
      }

      if (this.model.a("global", "menuDim", true)) {
         drawRect(0, 0, this.width, this.height, 1694498816);
      }

      float var18 = this.viewport.a(var1);
      float var19 = this.viewport.b(var2);
      com.cheatbreaker.client.kazerui.gui.components.ScrollPhysics var20 = this.model.scroller;
      if (var20.dragging) {
         if (Mouse.isButtonDown(0)) {
            var20.a(var19 - this.renderer.scrollTop);
         } else {
            var20.dragging = false;
         }
      }

      if (this.drag != null && this.drag.action.startsWith("slider:")) {
         if (Mouse.isButtonDown(0)) {
            this.c(var18, var19);
         } else {
            this.mouseReleased(var1, var2, 0);
         }
      }

      var20.a(this.a(var18, var19));
      if (this.previewDragging && Mouse.isButtonDown(0) && this.model.c()) {
         com.cheatbreaker.client.kazerui.concurrent.rendercache.CosmeticPreviewRenderer.a(-(var18 - this.previewX) * 2.0F);
         this.previewX = var18;
      } else if (!Mouse.isButtonDown(0)) {
         this.previewDragging = false;
      }

      boolean var7 = this.model.c() && (this.previewDragging || this.b(var18, var19));
      this.a(var7 && this.texture != null ? this.paintedX : var18, var7 && this.texture != null ? this.paintedY : var19, this.texture == null);
      if (this.b() && this.model.confirm.isEmpty() && !this.model.captureKey && this.model.keyListening.isEmpty() && this.renderer.caretOwner != null) {
         WindowsImeSupport.a(
            this.renderer.caretOwner,
            Math.round(this.viewport.left + this.renderer.caretX * this.viewport.scale),
            Math.round(this.viewport.top + this.renderer.caretY * this.viewport.scale),
            Math.max(1, Math.round(this.renderer.caretHeight * this.viewport.scale))
         );
      }

      GlStateManager.pushMatrix();
      GlStateManager.translate(this.viewport.left, this.viewport.top, 0.0F);
      GlStateManager.scale(this.viewport.scale, this.viewport.scale, 1.0F);
      GlStateManager.disableDepth();
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.bindTexture(this.texture.getGlTextureId());
      byte var8 = 2;
      int var9 = 492 + var8 * 2;
      int var10 = 303 + var8 * 2;
      Gui.drawScaledCustomSizeModalRect(
         -var8, -var8, 0.0F, 0.0F, var9 * this.rasterScale, var10 * this.rasterScale, var9, var10, var9 * this.rasterScale, var10 * this.rasterScale
      );
      if (this.model.c()) {
         cn.kazer.client189.render.GlStateBridge.c(524544);

         try {
            float var11 = (float)this.mc.displayWidth / this.width;
            float var12 = (float)this.mc.displayHeight / this.height;
            int var13 = (int)Math.floor((this.viewport.left + 366.0F * this.viewport.scale) * var11);
            int var14 = (int)Math.floor(this.mc.displayHeight - (this.viewport.top + 258.0F * this.viewport.scale) * var12);
            cn.kazer.client189.render.GlStateBridge.a(3089);
            GL11.glScissor(var13, var14, (int)Math.ceil(110.0F * this.viewport.scale * var11), (int)Math.ceil(167.0F * this.viewport.scale * var12));
            GlStateManager.depthMask(true);
            GL11.glClearDepth(1.0);
            GL11.glClear(256);
            com.cheatbreaker.client.kazerui.concurrent.rendercache.CosmeticPreviewRenderer.a(com.cheatbreaker.client.kazerui.concurrent.CosmeticCatalog.a(this.model.cosmeticPreview));
         } finally {
            cn.kazer.client189.render.GlStateBridge.a();
         }
      }

      if (!this.model.keybindPopup.isEmpty() && this.model.editor.isEmpty()) {
         int var21 = 100000;
         int var22 = -805306368;
         drawRect(-var21, -var21, var21, -var8, var22);
         drawRect(-var21, var10 - var8, var21, var21, var22);
         drawRect(-var21, -var8, -var8, var10 - var8, var22);
         drawRect(var9 - var8, -var8, var21, var10 - var8, var22);
      }

      GlStateManager.disableBlend();
      GlStateManager.enableDepth();
      GlStateManager.popMatrix();
   }

   private boolean a(float var1, float var2) {
      return this.model.editor.isEmpty() && this.model.keybindPopup.isEmpty() && var1 >= 110.0F && var1 <= 487.0F && var2 > 42.5F && var2 < 297.5F;
   }

   private boolean b(float var1, float var2) {
      return this.model.c() && var1 >= 366.0F && var1 < 476.0F && var2 >= 91.0F && var2 < 258.0F;
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      float var4 = this.viewport.a(var1);
      float var5 = this.viewport.b(var2);
      this.a(var4, var5, true);
      if (var3 == 0 && this.b(var4, var5)) {
         this.previewDragging = true;
         this.previewX = var4;
      } else {
         this.model.clickX = var4;
         this.model.clickY = var5;
         com.cheatbreaker.client.kazerui.gui.components.MenuRenderer.b var6 = this.renderer.a(var4, var5);
         String var7 = var6 == null ? "" : var6.action;
         if (!this.model.hexEditing.isEmpty() && !var7.startsWith("hex:" + this.model.hexEditing)) {
            this.c();
         }

         if (!this.model.textEditing.isEmpty() && !var7.startsWith("editText:" + this.model.textEditing)) {
            this.d();
         }

         if (!this.model.keyListening.isEmpty() && var3 >= 2 && var3 <= 5) {
            String[] var11 = this.model.keyListening.split(":");
            this.model.b(var11[0], var11[1], "MOUSE" + (var3 + 1));
            this.model.keyListening = "";
            this.model.store.b();
         } else if (!this.model.editor.isEmpty()) {
            if (var3 == 0) {
               this.model.i(var6 == null ? "editorNone" : var7);
            }
         } else if (var6 == null) {
            this.model.searchFocused = false;
            this.model.revision++;
         } else if (var7.startsWith("widget:")) {
            int var10 = Integer.parseInt(var7.substring(7));
            if (var10 < this.renderer.shownWidgets.size()) {
               this.renderer.shownWidgets.get(var10).a(var4, var5, var3);
            }

            this.model.revision++;
         } else if (var3 != 1) {
            if (var3 == 0) {
               if (var7.startsWith("listenKey:") && var7.substring(10).equals(this.model.keyListening)) {
                  this.model.keyListening = "";
                  this.model.revision++;
               } else if (var7.equals("scrollbar")) {
                  this.model.scroller.dragging = true;
                  this.model.scroller.a(var5 - this.renderer.scrollTop);
                  this.model.revision++;
               } else {
                  if (var7.startsWith("slider:")) {
                     String[] var9 = var7.split(":");
                     this.model.sliderHeld = var9[1] + "." + var9[2];
                  }

                  if (var7.startsWith("slider:") || var7.startsWith("sb:") || var7.startsWith("hue:") || var7.startsWith("alpha:")) {
                     this.drag = var6;
                     this.c(var4, var5);
                  } else if (var7.equals("videoSettings")) {
                     this.mc.displayGuiScreen(new GuiVideoSettings(this, this.mc.gameSettings));
                  } else if (var7.equals("gameControls")) {
                     this.mc.displayGuiScreen(new GuiControls(this, this.mc.gameSettings));
                  } else {
                     this.model.i(var7);
                     this.selectAll = false;
                     this.model.searchSelected = false;
                     if (this.model.closeRequested) {
                        this.a();
                     } else if (this.model.editHudRequested) {
                        this.model.editHudRequested = false;
                        this.mc.displayGuiScreen(new com.cheatbreaker.client.kazerui.gui.HudMoveScreen(this.features, this.openKey, this.background));
                     }
                  }
               }
            }
         } else {
            if (var7.equals("search")) {
               this.model.n("");
            } else if (var7.startsWith("listenKey:") && !var7.substring(10).equals(this.model.keyListening)) {
               String[] var8 = var7.substring(10).split(":");
               this.model.b(var8[0], var8[1], "NONE");
               this.model.store.b();
            } else if (var7.startsWith("keybindPopup:")) {
               this.model.b(var7.substring(13), "toggleKeybind", "NONE");
               this.model.store.b();
            } else if (var7.startsWith("listenKey:")) {
               this.model.keyListening = "";
               this.model.revision++;
            }
         }
      }
   }

   private void c(float var1, float var2) {
      if (!this.drag.action.startsWith("sb:") && !this.drag.action.startsWith("hue:") && !this.drag.action.startsWith("alpha:")) {
         String[] var12 = this.drag.action.split(":");
         float var13 = Float.parseFloat(var12[3]);
         float var14 = Float.parseFloat(var12[4]);
         float var15 = Float.parseFloat(var12[6]);
         boolean var16 = var12[5].equals("int");
         double var17 = com.cheatbreaker.client.kazerui.gui.components.SliderMath.a(var1, this.drag.rect.x, this.drag.rect.width, var15, var13, var14, var16, isShiftKeyDown());
         String var18 = var16 ? "" + Math.round(var17) : "" + (float)var17;
         if (!var18.equals(this.model.a(var12[1], var12[2], null))) {
            this.model.b(var12[1], var12[2], var18);
         }
      } else {
         String[] var3 = this.drag.action.split(":");
         String var4 = var3[1];
         String var5 = var3[2];
         int var6 = (int)Long.parseLong(this.model.c(var4, var5, "FFFFFFFF"), 16);
         float[] var7 = Color.RGBtoHSB(var6 >> 16 & 0xFF, var6 >> 8 & 0xFF, var6 & 0xFF, null);
         float var8 = this.model.a(var4, var5, var6);
         float var9 = Math.max(0.0F, Math.min(1.0F, (var1 - this.drag.rect.x) / this.drag.rect.width));
         float var10 = Math.max(0.0F, Math.min(1.0F, (var2 - this.drag.rect.y) / this.drag.rect.height));
         int var11 = var6 >>> 24;
         if (var3[0].equals("sb")) {
            this.model.a(var4, var5, var8, var9, 1.0F - var10, var11);
         } else if (var3[0].equals("hue")) {
            this.model.a(var4, var5, var10, var7[1], var7[2], var11);
         } else {
            this.model.a(var4, var5, var8, var7[1], var7[2], Math.round((1.0F - var10) * 255.0F));
         }
      }
   }

   protected void mouseClickMove(int var1, int var2, int var3, long var4) {
      if (!this.previewDragging) {
         if (this.drag != null) {
            this.c(this.viewport.a(var1), this.viewport.b(var2));
         }
      }
   }

   protected void mouseReleased(int var1, int var2, int var3) {
      this.previewDragging = false;
      this.model.scroller.dragging = false;
      if (!this.model.sliderHeld.isEmpty()) {
         this.model.sliderHeld = "";
         this.model.revision++;
      }

      if (this.drag != null) {
         String[] var4 = this.drag.action.split(":");
         if (var4[0].equals("sb") || var4[0].equals("hue") || var4[0].equals("alpha")) {
            this.model.m(this.model.c(var4[1], var4[2], "FFFFFFFF"));
         }

         this.drag = null;
         this.model.store.b();
      }
   }

   private void c() {
      String[] var1 = this.model.hexEditing.split(":");
      String var2 = this.model.input.toUpperCase(Locale.ROOT).replaceAll("[^0-9A-F]", "");
      if (!var2.isEmpty()) {
         while (var2.length() < 8) {
            var2 = var2 + "0";
         }

         if (!this.model.hexAlpha) {
            var2 = "FF" + var2.substring(2);
         }

         this.model.d(var1[0], var1[1], var2);
         this.model.m(var2);
      }

      this.model.hexEditing = "";
      this.model.input = "";
      this.model.revision++;
      this.model.store.b();
   }

   private void d() {
      String[] var1 = this.model.textEditing.split(":");
      this.model.b(var1[0], var1[1], this.model.input);
      this.model.textEditing = "";
      this.model.input = "";
      this.model.revision++;
      this.model.store.b();
   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      int var1 = Mouse.getEventDWheel();
      if (var1 != 0) {
         float var2 = this.viewport.a(Mouse.getEventX() * this.width / this.mc.displayWidth);
         float var3 = this.viewport.b(this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1);
         if (this.b(var2, var3)) {
            com.cheatbreaker.client.kazerui.concurrent.rendercache.CosmeticPreviewRenderer.a(Integer.signum(var1));
         } else if (var2 >= 0.0F && var2 <= 105.0F && var3 >= 42.0F && var3 < 260.0F && this.model.editor.isEmpty()) {
            this.model.a(var1 > 0 ? -1 : 1);
         } else if (this.a(var2, var3) && this.model.scroller.a(var1)) {
            this.model.revision++;
         }
      }
   }

   private String a(String var1, char var2, int var3, int var4) {
      if (isCtrlKeyDown() && var3 == 30) {
         this.selectAll = true;
         return var1;
      } else if (isCtrlKeyDown() && var3 == 46) {
         setClipboardString(var1);
         return var1;
      } else {
         String var5;
         if (isCtrlKeyDown() && var3 == 45) {
            setClipboardString(var1);
            var5 = "";
         } else if (isCtrlKeyDown() && var3 == 47) {
            var5 = (this.selectAll ? "" : var1) + getClipboardString().replaceAll("[\\r\\n\\t]", "");
         } else if (var3 != 14 && var3 != 211) {
            if (var2 < ' ' || var2 == 127 || isCtrlKeyDown()) {
               return null;
            }

            var5 = (this.selectAll ? "" : var1) + var2;
         } else {
            var5 = this.selectAll ? "" : (var1.isEmpty() ? "" : var1.substring(0, var1.length() - 1));
         }

         this.selectAll = false;
         return var4 > 0 && var5.length() > var4 ? var5.substring(0, var4) : var5;
      }
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if (this.model.captureKey) {
         this.model.captureKey = false;
         if (var2 != 1 && var2 != 0) {
            com.cheatbreaker.client.CheatBreakerClient var9 = com.cheatbreaker.client.CheatBreakerClient.N();
            KeyBinding var12 = this.model.captureTarget.equals("menu")
               ? this.openKey
               : var9.globalSettings.keyBindings.a(this.model.captureTarget.equals("freelook") ? "Freelook" : "Zoom");
            if (var12 != null) {
               this.a(this.model.captureTarget, var12, var2);
            }
         }

         this.model.revision++;
      } else if (!this.model.editor.isEmpty()) {
         if (var2 == 1) {
            this.model.g();
            this.model.store.b();
         } else if (!this.model.editorFocus.isEmpty() && this.model.confirm.isEmpty()) {
            boolean var8 = this.model.editorFocus.equals("name");
            String var11 = this.a(var8 ? this.model.editorName : this.model.editorServer, var1, var2, var8 ? 20 : 60);
            if (var11 != null) {
               this.model.k(var11);
            }
         }
      } else if (!this.model.keyListening.isEmpty()) {
         String[] var7 = this.model.keyListening.split(":");
         String var10 = var2 != 1 && var2 != 0 ? Keyboard.getKeyName(var2) : "NONE";
         this.model.b(var7[0], var7[1], var10 == null ? "NONE" : var10);
         this.model.keyListening = "";
         this.model.store.b();
      } else if (!this.model.hexEditing.isEmpty()) {
         if (var2 == 1) {
            this.model.hexEditing = "";
            this.model.input = "";
            this.model.revision++;
         } else if (var2 != 28 && var2 != 156) {
            if (var2 == 14) {
               if (!this.model.input.isEmpty()) {
                  this.model.input = this.model.input.substring(0, this.model.input.length() - 1);
               }
            } else if (Character.digit(var1, 16) >= 0 && this.model.input.length() < 8) {
               this.model.input = this.model.input + Character.toUpperCase(var1);
            }

            this.model.revision++;
         } else {
            this.c();
         }
      } else if (!this.model.textEditing.isEmpty()) {
         if (var2 == 1) {
            this.model.textEditing = "";
            this.model.input = "";
            this.model.revision++;
         } else if (var2 != 28 && var2 != 156) {
            String var6 = this.a(this.model.input, var1, var2, this.model.textMax);
            if (var6 != null) {
               this.model.input = var6;
               this.model.revision++;
            }
         } else {
            this.d();
         }
      } else {
         if (var2 != 1 && !this.model.searchFocused) {
            for (com.cheatbreaker.client.kazerui.gui.components.MenuWidget var4 : this.renderer.shownWidgets) {
               if (var4.a(var1, var2)) {
                  this.model.revision++;
                  return;
               }
            }
         }

         if (var2 == 1) {
            if (!this.model.keybindPopup.isEmpty()) {
               this.model.i("keybindPopupDone");
            } else if (!this.model.selected.isEmpty()) {
               this.model.i("back");
            } else {
               this.a();
            }
         } else if (var2 == this.openKey.getKeyCode() && !this.model.searchFocused) {
            if (!this.openingKeyHeld && !Keyboard.isRepeatEvent()) {
               this.a();
            }
         } else if (isCtrlKeyDown() && var2 == 33) {
            this.model.searchFocused = true;
            this.model.revision++;
         } else {
            if (this.model.searchFocused) {
               if (var2 == 28 || var2 == 156) {
                  this.model.searchFocused = false;
                  this.model.revision++;
                  return;
               }

               String var5 = this.a(this.model.search, var1, var2, 64);
               this.model.searchSelected = this.selectAll;
               if (var5 != null) {
                  this.model.n(var5);
               }
            }
         }
      }
   }

   private void a(String var1, KeyBinding var2, int var3) {
      var2.setKeyCode(var3);
      KeyBinding.resetKeyBindingArrayAndHash();
      this.model.store.b(var1 + "KeyName", a(var3));
      this.model.store.b(var1 + "Key", Integer.toString(var3));
      if (var1.equals("zoom")) {
         this.model.b("zoom", "zoomKeybind", a(var3));
      }

      this.model.revision++;
      this.model.store.b();
      com.cheatbreaker.client.CheatBreakerClient.N().configManager.c();
   }

   private void e() {
      if (this.texture != null) {
         this.texture.deleteGlTexture();
      }

      this.texture = null;
      this.textureLocation = null;
      this.paintedRevision = Long.MIN_VALUE;
   }

   public void onGuiClosed() {
      this.previewDragging = false;
      if (!this.model.hexEditing.isEmpty()) {
         this.c();
      }

      if (!this.model.textEditing.isEmpty()) {
         this.d();
      }

      this.model.keyListening = "";
      this.model.keybindPopup = "";
      this.model.scroller.velocity = 0.0;
      Keyboard.enableRepeatEvents(false);
      this.model.store.b();
      this.e();
   }

   public boolean doesGuiPauseGame() {
      return false;
   }
}
