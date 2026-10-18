package com.cheatbreaker.client.gui.hudeditor;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class HudEditorScreen extends GuiScreen {
   private static final float MIN_MODULE_SIZE = 18.0F;
   private static final int SNAP_THRESHOLD = 2;
   static final float SNAP_GAP = 1.0F;
   private static final int GUIDE_COLOR = -3596854;
   private static final float GUIDE_LINE_WIDTH = 0.5F;
   public static com.cheatbreaker.client.gui.hudeditor.HudEditorScreen instance;
   private final ResourceLocation cogIcon = new ResourceLocation("client/icons/cog-64.png");
   private final ResourceLocation deleteIcon = new ResourceLocation("client/icons/delete-64.png");
   private final ResourceLocation landingLogo = new ResourceLocation("client/kazer_logo.png");
   private final List<com.cheatbreaker.client.gui.hudeditor.ModulePosition> positions = new ArrayList<>();
   private final List<com.cheatbreaker.client.gui.uielement.ScrollableUIElement> IllIIlIIlllllIllIIIlllIII = new ArrayList<>();
   private final List<com.cheatbreaker.client.gui.uielement.settingseditor.SettingsButtonElement> buttons = new ArrayList<>();
   private List<com.cheatbreaker.client.module.Module> modules;
   private com.cheatbreaker.client.gui.uielement.settingseditor.SettingsButtonElement showGuidesButton;
   public com.cheatbreaker.client.gui.uielement.settingseditor.SettingsButtonElement helpButton;
   private com.cheatbreaker.client.gui.uielement.settingseditor.SettingsButtonElement openModsButton;
   public com.cheatbreaker.client.gui.uielement.ScrollableUIElement IIIIllIIllIIIIllIllIIIlIl;
   public com.cheatbreaker.client.gui.uielement.ScrollableUIElement profilesElement;
   protected com.cheatbreaker.client.gui.uielement.ScrollableUIElement modulesElement;
   protected com.cheatbreaker.client.gui.uielement.ScrollableUIElement staffModulesElement;
   protected com.cheatbreaker.client.gui.uielement.ScrollableUIElement lIIIIllIIlIlIllIIIlIllIlI = null;
   public com.cheatbreaker.client.gui.uielement.ScrollableUIElement currentScrollableElement = null;
   private static com.cheatbreaker.client.module.Module draggingModule;
   private boolean IlIlIIIlllllIIIlIlIlIllII = false;
   private float IIlIIllIIIllllIIlllIllIIl;
   private float lllIlIIllllIIIIlIllIlIIII;
   private List<com.cheatbreaker.client.gui.hudeditor.LayoutSnapshot> undoList;
   private List<com.cheatbreaker.client.gui.hudeditor.LayoutSnapshot> redo;
   private int someMouseX;
   private int someMouseY;
   private boolean IlIIIIllIIIIIlllIIlIIlllI = false;
   private com.cheatbreaker.client.gui.hudeditor.ModuleDragState dataHolder;
   public static boolean IlIlllIIIIllIllllIllIIlIl = false;
   private int arrowKeyMoves;
   private int IIllIlIllIlIllIIlIllIlIII = 0;
   private com.cheatbreaker.client.module.Module pendingIconModule;
   private com.cheatbreaker.client.gui.hudeditor.ModuleIconUtils.a pendingIcon;
   private int pendingPressX;
   private int pendingPressY;
   private long pendingPressTimeMs;
   private int dragButton = -1;
   private long landingOpenedAt;

   public void onGuiClosed() {
      this.b();
      Keyboard.enableRepeatEvents(false);
      cn.kazer.client189.render.ClientBridge.a().b();
   }

   public void a(com.cheatbreaker.client.module.Module var1) {
      if (var1 != null && this.IIIIllIIllIIIIllIllIIIlIl instanceof com.cheatbreaker.client.gui.uielement.settingseditor.ModuleSettingsPanel) {
         com.cheatbreaker.client.gui.uielement.settingseditor.ModuleSettingsPanel var2 = (com.cheatbreaker.client.gui.uielement.settingseditor.ModuleSettingsPanel)this.IIIIllIIllIIIIllIllIIIlIl;
         var2.llIlIIIlIIIIlIlllIlIIIIll = false;
         var2.b(var1);
         this.currentScrollableElement = var2;
      }
   }

   public void a() {
      this.modules = new ArrayList<>();

      for (com.cheatbreaker.client.module.Module var2 : com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.modules) {
         if (var2 instanceof com.cheatbreaker.client.module.ModuleGroup) {
            this.modules.addAll(((com.cheatbreaker.client.module.ModuleGroup)var2).a());
         } else {
            this.modules.add(var2);
         }
      }

      if (draggingModule != null && !this.modules.contains(draggingModule)) {
         draggingModule = null;
      }
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      this.modules = new ArrayList<>();

      for (com.cheatbreaker.client.module.Module var2 : com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.modules) {
         if (var2 instanceof com.cheatbreaker.client.module.ModuleGroup) {
            this.modules.addAll(((com.cheatbreaker.client.module.ModuleGroup)var2).a());
         } else {
            this.modules.add(var2);
         }
      }

      this.undoList = new ArrayList<>();
      this.redo = new ArrayList<>();
      this.someMouseX = -1;
      this.someMouseY = -1;
      this.arrowKeyMoves = 0;
      instance = this;
      draggingModule = null;
      this.dragButton = -1;
      this.d();
      this.landingOpenedAt = Minecraft.getSystemTime();
      IlIlllIIIIllIllllIllIIlIl = false;
      this.lIIIIllIIlIlIllIIIlIllIlI = null;
      this.currentScrollableElement = null;
      this.dataHolder = null;
      IlIlllIIIIllIllllIllIIlIl = false;
      float var6 = 1.0F / com.cheatbreaker.client.CheatBreakerClient.N().e();
      int var7 = (int)(this.width / var6);
      int var3 = (int)(this.height / var6);
      this.IllIIlIIlllllIllIIIlllIII.clear();
      this.buttons.clear();
      List var4 = com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.modules;
      List var5 = com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.staffModules;
      this.modulesElement = new com.cheatbreaker.client.gui.uielement.settingseditor.ModuleGridPanel(var6, var7 / 2 - 565, var3 / 2 + 14, 370, var3 / 2 - 35);
      this.IllIIlIIlllllIllIIIlllIII.add(this.modulesElement);
      this.staffModulesElement = new com.cheatbreaker.client.gui.uielement.settingseditor.ModuleSettingsPanel(var5, var6, var7 / 2 + 195, var3 / 2 + 14, 370, var3 / 2 - 35);
      this.IllIIlIIlllllIllIIIlllIII.add(this.staffModulesElement);
      this.IIIIllIIllIIIIllIllIIIlIl = new com.cheatbreaker.client.gui.uielement.settingseditor.ModuleSettingsPanel(var4, var6, var7 / 2 + 195, var3 / 2 + 14, 370, var3 / 2 - 35);
      this.IllIIlIIlllllIllIIIlllIII.add(this.IIIIllIIllIIIIllIllIIIlIl);
      this.profilesElement = new com.cheatbreaker.client.gui.uielement.fileconfig.ProfileListElement(var6, var7 / 2 - 565, var3 / 2 + 14, 370, var3 / 2 - 35);
      this.IllIIlIIlllllIllIIIlllIII.add(this.profilesElement);
      this.showGuidesButton = new com.cheatbreaker.client.gui.uielement.settingseditor.SettingsButtonElement(null, "eye-64.png", 4, var3 - 32, 28, 28, -12418828, var6);
      this.helpButton = new com.cheatbreaker.client.gui.uielement.settingseditor.SettingsButtonElement(null, "?", 36, var3 - 32, 28, 28, -12418828, var6);
      this.openModsButton = new com.cheatbreaker.client.gui.uielement.settingseditor.SettingsButtonElement(
            com.cheatbreaker.client.CheatBreakerClient.N().robotoBold14px, null, "Mods", var7 / 2 - 48, var3 / 2 - 2, 96, 28, -13916106, var6
         )
         .d();
      this.buttons.add(this.openModsButton);
      IlIlllIIIIllIllllIllIIlIl = false;
      this.lIIIIllIIlIlIllIIIlIllIlI = null;
      this.IIllIlIllIlIllIIlIllIlIII = 5;
   }

   public void updateScreen() {
      this.c();
      if (this.pendingIconModule != null) {
         if (c(this.pendingIconModule) && Mouse.isButtonDown(0)) {
            int var1 = Mouse.getX() * this.width / this.mc.displayWidth;
            int var2 = this.height - Mouse.getY() * this.height / this.mc.displayHeight - 1;
            if (com.cheatbreaker.client.gui.hudeditor.ModuleIconUtils.a(this.pendingPressX, this.pendingPressY, var1, var2)
               || com.cheatbreaker.client.gui.hudeditor.ModuleIconUtils.a(this.pendingPressTimeMs, Minecraft.getSystemTime())) {
               com.cheatbreaker.client.module.Module var3 = this.pendingIconModule;
               int var4 = this.pendingPressX;
               int var5 = this.pendingPressY;
               this.d();
               this.a(var3, new ScaledResolution(this.mc), var4, var5, 0);
            }
         } else {
            this.d();
         }
      }

      float var11 = 1.0F / com.cheatbreaker.client.CheatBreakerClient.N().e();
      int var12 = (int)(this.width / var11);
      int var13 = (int)(this.height / var11);
      this.a(var12);
      if (!this.positions.isEmpty()) {
         boolean var14 = Keyboard.isKeyDown(203);
         boolean var16 = Keyboard.isKeyDown(205);
         boolean var6 = Keyboard.isKeyDown(200);
         boolean var7 = Keyboard.isKeyDown(208);
         if (var14 || var16 || var6 || var7) {
            this.arrowKeyMoves++;
            if (this.arrowKeyMoves > 10) {
               for (com.cheatbreaker.client.gui.hudeditor.ModulePosition var9 : this.positions) {
                  com.cheatbreaker.client.module.Module var10 = var9.module;
                  if (var10 != null) {
                     if (var14) {
                        var10.a((int)var10.k() - 1, (int)var10.l());
                     } else if (var16) {
                        var10.a((int)var10.k() + 1, (int)var10.l());
                     } else if (var6) {
                        var10.a((int)var10.k(), (int)var10.l() - 1);
                     } else {
                        var10.a((int)var10.k(), (int)var10.l() + 1);
                     }
                  }
               }
            }
         }
      }

      float var15 = this.IIllIlIllIlIllIIlIllIlIII + a((float)this.IIllIlIllIlIllIIlIllIlIII);
      this.IIllIlIllIlIllIIlIllIlIII = this.IIllIlIllIlIllIIlIllIlIII + var15 >= 255.0F ? 255 : (int)(this.IIllIlIllIlIllIIlIllIlIII + var15);
   }

   private float a(Rectangle var1, Rectangle var2) {
      float var3 = Math.max(Math.abs(var1.x - var2.x) - var2.width / 2, 0);
      float var4 = Math.max(Math.abs(var1.y - var2.y) - var2.height / 2, 0);
      return var3 * var3 + var4 * var4;
   }

   public void drawScreen(int var1, int var2, float var3) {
      long var7 = Minecraft.getSystemTime();
      if (cn.kazer.client189.render.ClientBridge.a().d(var7)) {
         cn.kazer.client189.render.ClientBridge.a(this.width, this.height, cn.kazer.client189.render.ClientBridge.a().e(var7));
      }

      super.drawScreen(var1, var2, var3);
      ScaledResolution var9 = new ScaledResolution(this.mc);
      float var10 = 1.0F / com.cheatbreaker.client.CheatBreakerClient.N().e();
      if (draggingModule != null) {
         if (!Mouse.isButtonDown(1)) {
            com.cheatbreaker.client.gui.render.RenderUtils.a(2.0, 0.0, 2.5, (double)this.height, 0.0, -15599126);
            com.cheatbreaker.client.gui.render.RenderUtils.a((double)(this.width - 2.5F), 0.0, (double)(this.width - 2), (double)this.height, 0.0, -15599126);
            com.cheatbreaker.client.gui.render.RenderUtils.a(0.0, 2.0, (double)this.width, 2.5, 0.0, -15599126);
            com.cheatbreaker.client.gui.render.RenderUtils.a(0.0, (double)(this.height - 3.5F), (double)this.width, (double)(this.height - 3), 0.0, -15599126);
         }

         this.modules
            .sort(
               (var2x, var3x) -> {
                  if (var2x != draggingModule && var3x != draggingModule && var2x.r() != null && var3x.r() != null) {
                     float[] var4x = var2x.a(var9, true);
                     float[] var5x = var3x.a(var9, true);
                     float[] var6x = draggingModule.a(var9, true);
                     Rectangle var7x = new Rectangle(
                        (int)(var4x[0] * (Float)var2x.scale.d()),
                        (int)(var4x[1] * (Float)var2x.scale.d()),
                        (int)(var2x.width * (Float)var2x.scale.d()),
                        (int)(var2x.height * (Float)var2x.scale.d())
                     );
                     Rectangle var8 = new Rectangle(
                        (int)(var5x[0] * (Float)var3x.scale.d()),
                        (int)(var5x[1] * (Float)var3x.scale.d()),
                        (int)(var3x.width * (Float)var3x.scale.d()),
                        (int)(var3x.height * (Float)var3x.scale.d())
                     );
                     Rectangle var9x = new Rectangle(
                        (int)(var6x[0] * (Float)draggingModule.scale.d()),
                        (int)(var6x[1] * (Float)draggingModule.scale.d()),
                        (int)(draggingModule.width * (Float)draggingModule.scale.d()),
                        (int)(draggingModule.height * (Float)draggingModule.scale.d())
                     );

                     try {
                        return this.a(var7x, var9x) > this.a(var8, var9x) ? -1 : 1;
                     } catch (Exception var11x) {
                        return 0;
                     }
                  } else {
                     return 0;
                  }
               }
            );
         com.cheatbreaker.client.gui.hudeditor.ModulePosition var11 = this.d(draggingModule);
         if (var11 != null) {
            this.positions.remove(var11);
            this.positions.add(var11);
         }

         for (com.cheatbreaker.client.gui.hudeditor.ModulePosition var13 : this.positions) {
            this.a(var13, var1, var2, var9);
            if ((Boolean)com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.snapModules.d()
               && this.IlIlIIIlllllIIIlIlIlIllII
               && !Mouse.isButtonDown(1)
               && var13.module == draggingModule) {
               boolean var14 = false;
               boolean var15 = false;

               for (com.cheatbreaker.client.module.Module var17 : this.modules) {
                  if (this.d(var17) == null && c(var17)) {
                     boolean var18 = true;
                     boolean var19 = true;
                     float[] var20 = var17.a(var9, true);
                     float[] var21 = var13.module.a(var9, true);
                     float var22 = var20[0] * (Float)var17.scale.d() - var21[0] * (Float)var13.module.scale.d();
                     float var23 = (var20[0] + var17.width) * (Float)var17.scale.d() - (var21[0] + var13.module.width) * (Float)var13.module.scale.d();
                     float var24 = (var20[0] + var17.width) * (Float)var17.scale.d() - var21[0] * (Float)var13.module.scale.d();
                     float var25 = var20[0] * (Float)var17.scale.d() - (var21[0] + var13.module.width) * (Float)var13.module.scale.d();
                     float var26 = var20[1] * (Float)var17.scale.d() - var21[1] * (Float)var13.module.scale.d();
                     float var5 = (var20[1] + var17.height) * (Float)var17.scale.d() - (var21[1] + var13.module.height) * (Float)var13.module.scale.d();
                     float var4 = (var20[1] + var17.height) * (Float)var17.scale.d() - var21[1] * (Float)var13.module.scale.d();
                     float var27 = var20[1] * (Float)var17.scale.d() - (var21[1] + var13.module.height) * (Float)var13.module.scale.d();
                     if (this.e(var22)) {
                        var18 = false;
                        var14 = true;
                        this.c(var22);
                     }

                     if (this.e(var23) && var18) {
                        var18 = false;
                        var14 = true;
                        this.c(var23);
                     }

                     if (this.e(var25) && var18) {
                        var18 = false;
                        var14 = true;
                        this.c(a(var25, -1));
                     }

                     if (this.e(var24) && var18) {
                        var14 = true;
                        this.c(a(var24, 1));
                     }

                     if (this.e(var26)) {
                        var19 = false;
                        var15 = true;
                        this.d(var26);
                     }

                     if (this.e(var5) && var19) {
                        var19 = false;
                        var15 = true;
                        this.d(var5);
                     }

                     if (this.e(var27) && var19) {
                        var19 = false;
                        var15 = true;
                        this.d(a(var27, -1));
                     }

                     if (this.e(var4) && var19) {
                        var15 = true;
                        this.d(a(var4, 1));
                     }
                  }
               }

               this.a(var13.module, var9, !var14, !var15);
            }
         }
      } else if (this.dataHolder != null) {
         float var30 = 1.0F;
         switch (this.dataHolder.unknown) {
            case RIGHT_BOTTOM:
               int var35 = var2 - this.dataHolder.mouseY + (var1 - this.dataHolder.mouseX);
               var30 = this.dataHolder.scale - var35 / 115.0F;
               break;
            case LEFT_TOP:
               int var34 = var2 - this.dataHolder.mouseY + (var1 - this.dataHolder.mouseX);
               var30 = this.dataHolder.scale + var34 / 115.0F;
               break;
            case RIGHT_TOP:
               int var33 = var1 - this.dataHolder.mouseX - (var2 - this.dataHolder.mouseY);
               var30 = this.dataHolder.scale - var33 / 115.0F;
               break;
            case LEFT_BOTTOM:
               int var32 = var1 - this.dataHolder.mouseX - (var2 - this.dataHolder.mouseY);
               var30 = this.dataHolder.scale + var32 / 115.0F;
         }

         if (var30 >= 0.5F && var30 <= 1.5F) {
            this.dataHolder.module.scale.a(Float.valueOf((float)(Math.round(var30 * 100.0) / 100.0)));
         }
      }

      this.a(var9);
      boolean var31 = true;

      for (Object var38 : this.modules) {
         boolean var40 = this.a(var10, (com.cheatbreaker.client.module.Module)var38, var9, var1, var2, var31);
         if (!var40) {
            var31 = false;
         }
      }

      GL11.glPushMatrix();
      GL11.glScalef(var10, var10, var10);
      int var37 = (int)(this.width / var10);
      int var39 = (int)(this.height / var10);
      if (this.currentScrollableElement == null && this.lIIIIllIIlIlIllIIIlIllIlI == null) {
         float var41 = Math.min(1.0F, (float)(Minecraft.getSystemTime() - this.landingOpenedAt) / 420.0F);
         var41 = 1.0F - (float)Math.pow(1.0F - var41, 3.0);
         int var46 = Math.max(4, Math.min(255, Math.round(255.0F * var41)));
         cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, var41);
         com.cheatbreaker.client.gui.render.RenderUtils.a(this.landingLogo, var37 / 2.0F - 32.0F, var39 / 2.0F - 80.0F, 64.0F, 56.0F);
         com.cheatbreaker.client.CheatBreakerClient.N().playRegular18px.b("KAZER CLIENT", var37 / 2.0F, var39 / 2.0F - 19.0F, var46 << 24 | 16777215);
         this.openModsButton.a(var41);
         cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, 1.0F);
      }

      for (com.cheatbreaker.client.gui.uielement.settingseditor.SettingsButtonElement var47 : this.buttons) {
         var47.a(var1, var2, var3);
      }

      if (draggingModule == null) {
         GL11.glPushMatrix();
         cn.kazer.client189.render.GlStateBridge.a(3089);
         com.cheatbreaker.client.gui.render.RenderUtils.a(var37 / 2 - 185, var39 / 2 + 15, var37 / 2 + 185, var39 - 20, var9.getScaleFactor() * var10, var39);

         for (com.cheatbreaker.client.gui.uielement.ScrollableUIElement var48 : this.IllIIlIIlllllIllIIIlllIII) {
            if (var48 == this.lIIIIllIIlIlIllIIIlIllIlI || var48 == this.currentScrollableElement) {
               var48.a(var1, var2, var3);
            }
         }

         cn.kazer.client189.render.GlStateBridge.b(3089);
         GL11.glPopMatrix();
      }

      GL11.glPopMatrix();
      if (this.someMouseX != -1) {
         if (Mouse.isButtonDown(0)) {
            if (this.someMouseX != var1 && this.someMouseY != var2) {
               cn.kazer.client189.render.DrawRect.a(var1, this.someMouseY, var1 + 0.5F, var2, -1358888961);
               cn.kazer.client189.render.DrawRect.a(this.someMouseX - 0.5F, var2, var1 + 0.5F, var2 + 0.5F, -1358888961);
               cn.kazer.client189.render.DrawRect.a(this.someMouseX - 0.5F, this.someMouseY, this.someMouseX, var2, -1358888961);
               cn.kazer.client189.render.DrawRect.a(this.someMouseX - 0.5F, this.someMouseY - 0.5F, var1 + 0.5F, this.someMouseY, -1358888961);
               cn.kazer.client189.render.DrawRect.a(this.someMouseX, this.someMouseY, var1, var2, 520159231);
            }
         } else {
            this.positions.clear();

            for (com.cheatbreaker.client.module.Module var49 : this.modules) {
               if (c(var49)) {
                  float[] var52 = var49.a(var9, true);
                  float var53 = var10 / (Float)var49.scale.d();
                  Rectangle var6 = new Rectangle(
                     (int)(var52[0] * (Float)var49.scale.d() - 2.0F),
                     (int)(var52[1] * (Float)var49.scale.d() - 2.0F),
                     (int)(var49.width * (Float)var49.scale.d() + 4.0F),
                     (int)(var49.height * (Float)var49.scale.d() + 4.0F)
                  );
                  int var50;
                  int var51;
                  if (var6.intersects(
                     new Rectangle(
                        var51 = Math.min(this.someMouseX, var1),
                        var50 = Math.min(this.someMouseY, var2),
                        Math.max(this.someMouseX, var1) - var51,
                        Math.max(this.someMouseY, var2) - var50
                     )
                  )) {
                     float var29 = var1 - var49.k();
                     float var28 = var2 - var49.l();
                     this.positions.add(new com.cheatbreaker.client.gui.hudeditor.ModulePosition(var49, var29, var28));
                  }
               }
            }

            this.someMouseX = -1;
            this.someMouseY = -1;
         }
      }
   }

   private void b(float var1) {
      GL11.glPushMatrix();
      GL11.glTranslatef(4.0F, this.height - 173.0F * var1, 0.0F);
      GL11.glScalef(var1, var1, var1);
      cn.kazer.client189.render.DrawRect.a(0.0F, 0.0F, 240.0F, 128.0F, -1895825408);
      com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.a("Shortcuts & Movement", 4.0F, 2.0F, -1);
      cn.kazer.client189.render.DrawRect.a(4.0F, 12.0F, 234.0F, 12.5F, 1342177279);
      int var2 = 16;
      this.a("Mouse1", 6, var2);
      com.cheatbreaker.client.CheatBreakerClient.N()
         .playRegular14px
         .a("| " + EnumChatFormatting.LIGHT_PURPLE + "HOLD" + EnumChatFormatting.LIGHT_PURPLE + " Add mods to selected region", 80.0F, var2 + 3.0F, -1);
      var2 += 12;
      this.a("Mouse1", 6, var2);
      com.cheatbreaker.client.CheatBreakerClient.N()
         .playRegular14px
         .a("| " + EnumChatFormatting.LIGHT_PURPLE + "HOLD" + EnumChatFormatting.LIGHT_PURPLE + " Select & drag mods", 80.0F, var2 + 3.0F, -1);
      var2 += 12;
      this.a("Mouse2", 6, var2);
      com.cheatbreaker.client.CheatBreakerClient.N()
         .playRegular14px
         .a("| " + EnumChatFormatting.LIGHT_PURPLE + "HOLD" + EnumChatFormatting.LIGHT_PURPLE + " Don't lock mods while dragging", 80.0F, var2 + 3.0F, -1);
      var2 += 12;
      this.a("CTRL", 6, var2);
      com.cheatbreaker.client.CheatBreakerClient.N().playRegular14px.a("+", 30.0F, var2 + 3.0F, -1);
      this.a("Mouse1", 36, var2);
      com.cheatbreaker.client.CheatBreakerClient.N().playRegular14px.a("| Toggle (multiple) mod selection", 80.0F, var2 + 3.0F, -1);
      var2 += 12;
      this.a("CTRL", 6, var2);
      com.cheatbreaker.client.CheatBreakerClient.N().playRegular14px.a("+", 30.0F, var2 + 3.0F, -1);
      this.a("Z", 36, var2);
      com.cheatbreaker.client.CheatBreakerClient.N().playRegular14px.a("| Undo mod movements", 80.0F, var2 + 3.0F, -1);
      var2 += 12;
      this.a("CTRL", 6, var2);
      com.cheatbreaker.client.CheatBreakerClient.N().playRegular14px.a("+", 30.0F, var2 + 3.0F, -1);
      this.a("Y", 36, var2);
      com.cheatbreaker.client.CheatBreakerClient.N().playRegular14px.a("| Redo mod movements", 80.0F, var2 + 3.0F, -1);
      int var8 = 100;
      this.a("Up", 31, var8);
      var8 += 12;
      this.a("Left", 6, var8);
      this.a("Down", 26, var8);
      this.a("Right", 51, var8);
      com.cheatbreaker.client.CheatBreakerClient.N().playRegular14px.a("| Move selected mod with precision", 80.0F, (float)var8, -1);
      GL11.glPopMatrix();
   }

   private void a(String var1, int var2, int var3) {
      com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer var4 = com.cheatbreaker.client.CheatBreakerClient.N().playRegular14px;
      float var5 = var4.a(var1);
      com.cheatbreaker.client.gui.render.RenderUtils.a((double)var2, (double)var3, (double)(var2 + var5 + 4.0F), (double)(var3 + 10), 2.0, -1073741825);
      var4.a(var1, (float)(var2 + 2), var3 + 3.0F, -16777216);
   }

   public void mouseClicked(int var1, int var2, int var3) throws IOException {
      if (var3 == 0 && this.openModsButton != null && this.openModsButton.a(var1, var2)) {
         Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
         Minecraft.getMinecraft().displayGuiScreen(new com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen(this));
      } else {
         this.a(this.lIIIIllIIlIlIllIIIlIllIlI, var1, var2, var3);
         if (this.currentScrollableElement != this.lIIIIllIIlIlIllIIIlIllIlI) {
            this.a(this.currentScrollableElement, var1, var2, var3);
         }

         ScaledResolution var4 = new ScaledResolution(this.mc);
         com.cheatbreaker.client.gui.uielement.ScrollableUIElement var5 = a(this.lIIIIllIIlIlIllIIIlIllIlI, this.currentScrollableElement);
         if (var5 != null && var5.a(var1, var2)) {
            var5.a(var1, var2, var3);
         } else {
            if (draggingModule == null || !this.IlIlIIIlllllIIIlIlIlIllII) {
               com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.a var6 = this.a(var4, var1, var2);
               if (var6 != null && var3 == 0) {
                  this.a(var6.module, var6.icon, var1, var2);
                  return;
               }
            }

            for (com.cheatbreaker.client.module.Module var7 : this.modules) {
               if (c(var7)) {
                  float[] var10 = var7.a(var4, true);
                  boolean var11 = var1 > var10[0] * (Float)var7.scale.d()
                     && var1 < (var10[0] + var7.width) * (Float)var7.scale.d()
                     && var2 > var10[1] * (Float)var7.scale.d()
                     && var2 < (var10[1] + var7.height) * (Float)var7.scale.d();
                  boolean var12 = this.dataHolder != null
                        && this.dataHolder.module == var7
                        && this.dataHolder.unknown == com.cheatbreaker.client.gui.hudeditor.ResizeCorner.LEFT_BOTTOM
                     || !var11
                        && var1 >= (var10[0] + var7.width - 5.0F) * (Float)var7.scale.d()
                        && var1 <= (var10[0] + var7.width + 5.0F) * (Float)var7.scale.d()
                        && var2 >= (var10[1] - 5.0F) * (Float)var7.scale.d()
                        && var2 <= (var10[1] + 5.0F) * (Float)var7.scale.d();
                  boolean var13 = this.dataHolder != null
                        && this.dataHolder.module == var7
                        && this.dataHolder.unknown == com.cheatbreaker.client.gui.hudeditor.ResizeCorner.RIGHT_TOP
                     || !var11
                        && var1 >= (var10[0] - 5.0F) * (Float)var7.scale.d()
                        && var1 <= (var10[0] + 5.0F) * (Float)var7.scale.d()
                        && var2 >= (var10[1] + var7.height - 5.0F) * (Float)var7.scale.d()
                        && var2 <= (var10[1] + var7.height + 5.0F) * (Float)var7.scale.d();
                  boolean var14 = this.dataHolder != null
                        && this.dataHolder.module == var7
                        && this.dataHolder.unknown == com.cheatbreaker.client.gui.hudeditor.ResizeCorner.RIGHT_BOTTOM
                     || !var11
                        && var1 >= (var10[0] - 5.0F) * (Float)var7.scale.d()
                        && var1 <= (var10[0] + 5.0F) * (Float)var7.scale.d()
                        && var2 >= (var10[1] - 5.0F) * (Float)var7.scale.d()
                        && var2 <= (var10[1] + 5.0F) * (Float)var7.scale.d();
                  boolean var15 = this.dataHolder != null
                        && this.dataHolder.module == var7
                        && this.dataHolder.unknown == com.cheatbreaker.client.gui.hudeditor.ResizeCorner.LEFT_TOP
                     || !var11
                        && var1 >= (var10[0] + var7.width - 5.0F) * (Float)var7.scale.d()
                        && var1 <= (var10[0] + var7.width + 5.0F) * (Float)var7.scale.d()
                        && var2 >= (var10[1] + var7.height - 5.0F) * (Float)var7.scale.d()
                        && var2 <= (var10[1] + var7.height + 5.0F) * (Float)var7.scale.d();
                  if (this.someMouseX == -1 && (var12 || var13 || var14 || var15)) {
                     com.cheatbreaker.client.gui.hudeditor.GuiAnchor var8;
                     com.cheatbreaker.client.gui.hudeditor.ResizeCorner var9;
                     if (var12) {
                        var9 = com.cheatbreaker.client.gui.hudeditor.ResizeCorner.LEFT_BOTTOM;
                        var8 = com.cheatbreaker.client.gui.hudeditor.GuiAnchor.LEFT_BOTTOM;
                     } else if (var13) {
                        var9 = com.cheatbreaker.client.gui.hudeditor.ResizeCorner.RIGHT_TOP;
                        var8 = com.cheatbreaker.client.gui.hudeditor.GuiAnchor.RIGHT_TOP;
                     } else if (var14) {
                        var9 = com.cheatbreaker.client.gui.hudeditor.ResizeCorner.RIGHT_BOTTOM;
                        var8 = com.cheatbreaker.client.gui.hudeditor.GuiAnchor.RIGHT_BOTTOM;
                     } else {
                        var9 = com.cheatbreaker.client.gui.hudeditor.ResizeCorner.LEFT_TOP;
                        var8 = com.cheatbreaker.client.gui.hudeditor.GuiAnchor.LEFT_TOP;
                     }

                     if (!this.b(var4, var1, var2)) {
                        if (var3 == 0) {
                           this.undoList.add(new com.cheatbreaker.client.gui.hudeditor.LayoutSnapshot(this, this.positions));
                           this.dataHolder = new com.cheatbreaker.client.gui.hudeditor.ModuleDragState(this, var7, var9, var1, var2);
                           this.a(var7, var8, var4);
                        } else if (var3 == 1) {
                           com.cheatbreaker.client.gui.hudeditor.GuiAnchor var16 = var7.r();
                           this.a(var7, var8, var4);
                           var7.scale.a(Float.valueOf(1.0F));
                           this.a(var7, var16, var4);
                        }

                        return;
                     }
                  }
               }
            }

            if (draggingModule == null) {
               this.a(var1, var2, var3);
               this.a(var4, var1, var2, var3);
            }

            for (Object var20 : this.buttons) {
               if (((com.cheatbreaker.client.gui.uielement.UIElement)var20).a(var1, var2)) {
                  return;
               }
            }

            boolean var19 = this.b(var4, var1, var2);
            if (var19) {
               return;
            }

            if (!this.positions.isEmpty()) {
               this.positions.clear();
               Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            }

            this.someMouseX = var1;
            this.someMouseY = var2;
         }

         if (!this.positions.isEmpty()) {
            this.arrowKeyMoves = 0;
         }
      }
   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      int var1 = Mouse.getEventDWheel();
      if (this.lIIIIllIIlIlIllIIIlIllIlI != null) {
         this.lIIIIllIIlIlIllIIIlIllIlI.a(var1);
      }
   }

   private void a(com.cheatbreaker.client.module.Module var1, com.cheatbreaker.client.gui.hudeditor.GuiAnchor var2, ScaledResolution var3) {
      if (var2 != var1.r()) {
         float[] var4 = var1.a(var3, true);
         var1.a(var2);
         float[] var5 = var1.a(var3, false);
         var1.a(var4[0] * (Float)var1.scale.d() - var5[0] * (Float)var1.scale.d(), var4[1] * (Float)var1.scale.d() - var5[1] * (Float)var1.scale.d());
         this.a(var1, var3);
      }
   }

   public void mouseReleased(int var1, int var2, int var3) {
      if (var3 == 0) {
         cn.kazer.client189.render.ClientBridge.a().c(Minecraft.getSystemTime());
      }

      ScaledResolution var4 = new ScaledResolution(this.mc);
      if (this.dataHolder != null && var3 == 0) {
         this.a(this.dataHolder.module, this.dataHolder.anchor, var4);
         this.a(this.dataHolder.module, var4);
         this.dataHolder = null;
      }

      if (var3 == 0 && this.pendingIconModule != null) {
         com.cheatbreaker.client.module.Module var5 = this.pendingIconModule;
         com.cheatbreaker.client.gui.hudeditor.ModuleIconUtils.a var6 = this.pendingIcon;
         long var7 = this.pendingPressTimeMs;
         int var9 = this.pendingPressX;
         int var10 = this.pendingPressY;
         this.d();
         boolean var11 = this.a(var5, var6, var4, var1, var2);
         if (com.cheatbreaker.client.gui.hudeditor.ModuleIconUtils.a(var7, Minecraft.getSystemTime(), var9, var10, var1, var2, var11) == com.cheatbreaker.client.gui.hudeditor.ModuleIconUtils.b.EXECUTE) {
            this.a(var5, var6);
         }
      }

      if (draggingModule != null && var3 == this.dragButton) {
         if (this.IlIlIIIlllllIIIlIlIlIllII) {
            for (com.cheatbreaker.client.gui.hudeditor.ModulePosition var15 : this.positions) {
               com.cheatbreaker.client.gui.hudeditor.GuiAnchor var18 = com.cheatbreaker.client.gui.hudeditor.AnchorHelper.a(var1, var2, var4);
               if (var18 != com.cheatbreaker.client.gui.hudeditor.GuiAnchor.MIDDLE_MIDDLE && var18 != var15.module.r() && this.IlIlIIIlllllIIIlIlIlIllII) {
                  this.a(var15.module, var18, var4);
                  var15.x = var1 - var15.module.k();
                  var15.y = var2 - var15.module.l();
               }
            }

            if (this.d(draggingModule) == null) {
               float var13 = var1 - draggingModule.k();
               float var16 = var2 - draggingModule.l();
               this.positions.add(new com.cheatbreaker.client.gui.hudeditor.ModulePosition(draggingModule, var13, var16));
            }

            for (com.cheatbreaker.client.gui.hudeditor.ModulePosition var17 : this.positions) {
               this.a(var17.module, var4);
            }

            this.a(draggingModule, var4);
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
         }

         System.out.println(draggingModule.h());
         draggingModule = null;
         this.dragButton = -1;
      }
   }

   public void keyTyped(char var1, int var2) throws IOException {
      if (!this.a(this.lIIIIllIIlIlIllIIIlIllIlI, var1, var2)
         && (this.currentScrollableElement == this.lIIIIllIIlIlIllIIIlIllIlI || !this.a(this.currentScrollableElement, var1, var2))) {
         if (var2 == 1) {
            com.cheatbreaker.client.CheatBreakerClient.N().configManager.a();
         }

         super.keyTyped(var1, var2);
         if (var2 == 44 && isCtrlKeyDown()) {
            if (!this.undoList.isEmpty()) {
               int var12 = this.undoList.size() - 1;
               com.cheatbreaker.client.gui.hudeditor.LayoutSnapshot var14 = this.undoList.get(this.undoList.size() - 1);

               for (int var16 = 0; var16 < var14.modules.size(); var16++) {
                  com.cheatbreaker.client.module.Module var17 = var14.modules.get(var16);
                  float var18 = var14.xTranslations.get(var16);
                  float var19 = var14.yTranslations.get(var16);
                  com.cheatbreaker.client.gui.hudeditor.GuiAnchor var20 = var14.anchors.get(var16);
                  Float var21 = var14.scales.get(var16);
                  var17.a(var20);
                  var17.a(var18, var19);
                  var17.scale.a(var21);
               }

               if (this.redo.size() > 50) {
                  this.redo.remove(0);
               }

               this.redo.add(var14);
               this.undoList.remove(var12);
            }
         } else if (var2 == 21 && isCtrlKeyDown()) {
            if (!this.redo.isEmpty()) {
               int var11 = this.redo.size() - 1;
               com.cheatbreaker.client.gui.hudeditor.LayoutSnapshot var13 = this.redo.get(this.redo.size() - 1);

               for (int var15 = 0; var15 < var13.modules.size(); var15++) {
                  com.cheatbreaker.client.module.Module var6 = var13.modules.get(var15);
                  float var7 = var13.xTranslations.get(var15);
                  float var8 = var13.yTranslations.get(var15);
                  com.cheatbreaker.client.gui.hudeditor.GuiAnchor var9 = var13.anchors.get(var15);
                  float var10 = var13.scales.get(var15);
                  var6.a(var9);
                  var6.a(var7, var8);
                  var6.scale.a(Float.valueOf(var10));
               }

               if (this.redo.size() > 50) {
                  this.redo.remove(0);
               }

               this.undoList.add(var13);
               this.redo.remove(var11);
            }
         } else {
            this.arrowKeyMoves = 0;

            for (com.cheatbreaker.client.gui.hudeditor.ModulePosition var4 : this.positions) {
               com.cheatbreaker.client.module.Module var5 = var4.module;
               if (var5 != null) {
                  switch (var2) {
                     case 200:
                        var5.a(var5.k(), var5.l() - 1.0F);
                     case 201:
                     case 202:
                     case 204:
                     case 206:
                     case 207:
                     default:
                        break;
                     case 203:
                        var5.a(var5.k() - 1.0F, var5.l());
                        break;
                     case 205:
                        var5.a(var5.k() + 1.0F, var5.l());
                        break;
                     case 208:
                        var5.a(var5.k(), var5.l() + 1.0F);
                  }
               }
            }
         }
      }
   }

   private boolean a(com.cheatbreaker.client.gui.uielement.ScrollableUIElement var1, char var2, int var3) {
      return var1 != null && var1.a(var2, var3);
   }

   static com.cheatbreaker.client.gui.uielement.ScrollableUIElement a(com.cheatbreaker.client.gui.uielement.ScrollableUIElement var0, com.cheatbreaker.client.gui.uielement.ScrollableUIElement var1) {
      return var1 != null ? var1 : var0;
   }

   private void a(com.cheatbreaker.client.gui.uielement.ScrollableUIElement var1, int var2, int var3, int var4) {
      if (var1 != null) {
         var1.b(var2, var3, var4);
      }
   }

   private void b() {
      if (this.lIIIIllIIlIlIllIIIlIllIlI != null) {
         this.lIIIIllIIlIlIllIIIlIllIlI.a();
      }

      if (this.currentScrollableElement != null && this.currentScrollableElement != this.lIIIIllIIlIlIllIIIlIllIlI) {
         this.currentScrollableElement.a();
      }
   }

   static float a(float var0, float var1) {
      return Math.round(var0 + var1) - var0;
   }

   private void a(com.cheatbreaker.client.module.Module var1, ScaledResolution var2) {
      float var3 = (Float)var1.scale.d();
      float[] var4 = var1.a(var2, false);
      var1.a(a(var4[0] * var3, var1.k()), a(var4[1] * var3, var1.l()));
   }

   private void c(float var1) {
      for (com.cheatbreaker.client.gui.hudeditor.ModulePosition var3 : this.positions) {
         var3.module.a(var3.module.k() + var1, var3.module.l());
      }
   }

   private void d(float var1) {
      for (com.cheatbreaker.client.gui.hudeditor.ModulePosition var3 : this.positions) {
         var3.module.a(var3.module.k(), var3.module.l() + var1);
      }
   }

   private void a(com.cheatbreaker.client.module.Module var1, ScaledResolution var2, boolean var3, boolean var4) {
      float[] var5 = this.c(var1, var2);
      if (var3 && this.e(var5[0])) {
         this.c(var5[0]);
      }

      if (var4 && this.e(var5[1])) {
         this.d(var5[1]);
      }
   }

   private void b(com.cheatbreaker.client.module.Module var1, ScaledResolution var2) {
      float[] var3 = this.c(var1, var2);
      float var4 = var2.getScaledWidth() / 2.0F;
      float var5 = var2.getScaledHeight() / 2.0F;
      if (this.e(var3[0])) {
         com.cheatbreaker.client.gui.render.RenderUtils.a((double)(var4 - 0.5F), 0.0, (double)var4, (double)this.height, 0.0, -3596854);
      }

      if (this.e(var3[1])) {
         com.cheatbreaker.client.gui.render.RenderUtils.a(0.0, (double)var5, (double)this.width, (double)(var5 + 0.5F), 0.0, -3596854);
      }
   }

   private float[] c(com.cheatbreaker.client.module.Module var1, ScaledResolution var2) {
      float var3 = (Float)var1.scale.d();
      float[] var4 = var1.a(var2, true);
      float var5 = var4[0] * var3 + var1.width * var3 / 2.0F;
      float var6 = var4[1] * var3 + var1.height * var3 / 2.0F;
      return new float[]{var2.getScaledWidth() / 2.0F - var5, var2.getScaledHeight() / 2.0F - var6};
   }

   private boolean e(float var1) {
      return Math.abs(var1) <= 2.0F;
   }

   static float a(float var0, int var1) {
      return var0 + var1 * 1.0F;
   }

   static float[] b(float var0, float var1) {
      float var2 = Math.max(var0, 18.0F);
      float var3 = Math.max(var1, 18.0F);
      float var4 = (var0 - var2) / 2.0F;
      float var5 = (var1 - var3) / 2.0F;
      return new float[]{var4, var5, var4 + var2, var5 + var3};
   }

   static boolean a(boolean var0, boolean var1, boolean var2) {
      return var0 && var1 && var2;
   }

   private static boolean c(com.cheatbreaker.client.module.Module var0) {
      return var0 != null && a(var0.r() != null, var0.g(), var0.q());
   }

   public void b(com.cheatbreaker.client.module.Module var1) {
      this.positions.removeIf(var1x -> var1x.module == var1);
      if (draggingModule == var1) {
         draggingModule = null;
         this.IlIlIIIlllllIIIlIlIlIllII = false;
      }

      if (this.dataHolder != null && this.dataHolder.module == var1) {
         this.dataHolder = null;
      }
   }

   private void c() {
      this.positions.removeIf(var0 -> !c(var0.module));
      if (draggingModule != null && !c(draggingModule)) {
         draggingModule = null;
         this.IlIlIIIlllllIIIlIlIlIllII = false;
      }

      if (this.dataHolder != null && !c(this.dataHolder.module)) {
         this.dataHolder = null;
      }
   }

   private boolean a(com.cheatbreaker.client.module.Module var1, ScaledResolution var2, int var3, int var4) {
      float[] var5 = var1.a(var2, true);
      float[] var6 = b(var1.width, var1.height);
      float var7 = (Float)var1.scale.d();
      return var3 > (var5[0] + var6[0]) * var7 && var3 < (var5[0] + var6[2]) * var7 && var4 > (var5[1] + var6[1]) * var7 && var4 < (var5[1] + var6[3]) * var7;
   }

   private void a(ScaledResolution var1, int var2, int var3, int var4) {
      for (com.cheatbreaker.client.module.Module var6 : this.modules) {
         if (c(var6) && this.a(var6, var1, var2, var3)) {
            if (var4 == 0 || var4 == 1) {
               this.a(var6, var1, var2, var3, var4);
            }
            break;
         }
      }
   }

   private void a(int var1, int var2, int var3) {
      for (com.cheatbreaker.client.gui.uielement.settingseditor.SettingsButtonElement var5 : this.buttons) {
         if (var3 == 0 && var5.a(var1, var2) && !IlIlllIIIIllIllllIllIIlIl) {
            if (var5.lIIIIllIIlIlIllIIIlIllIlI != null
               && this.lIIIIllIIlIlIllIIIlIllIlI != var5.lIIIIllIIlIlIllIIIlIllIlI
               && this.currentScrollableElement == null) {
               Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
               this.currentScrollableElement = var5.lIIIIllIIlIlIllIIIlIllIlI;
            } else if (var5.lIIIIllIIlIlIllIIIlIllIlI != null && this.currentScrollableElement == null) {
               Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
               IlIlllIIIIllIllllIllIIlIl = true;
            }
         }
      }
   }

   private com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.a a(ScaledResolution var1, int var2, int var3) {
      for (com.cheatbreaker.client.module.Module var5 : this.modules) {
         if (c(var5)) {
            float[] var6 = var5.a(var1, true);
            float var7 = (Float)var5.scale.d();
            if (!var5.m().isEmpty() && com.cheatbreaker.client.gui.hudeditor.ModuleIconUtils.a(var6[0], var6[1], var7, var5.width, var5.height, var2, var3)) {
               return new com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.a(var5, com.cheatbreaker.client.gui.hudeditor.ModuleIconUtils.a.GEAR);
            }

            if (com.cheatbreaker.client.gui.hudeditor.ModuleIconUtils.b(var6[0], var6[1], var7, var5.width, var5.height, var2, var3)) {
               return new com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.a(var5, com.cheatbreaker.client.gui.hudeditor.ModuleIconUtils.a.CLOSE);
            }
         }
      }

      return null;
   }

   private boolean a(com.cheatbreaker.client.module.Module var1, com.cheatbreaker.client.gui.hudeditor.ModuleIconUtils.a var2, ScaledResolution var3, int var4, int var5) {
      float[] var6 = var1.a(var3, true);
      float var7 = (Float)var1.scale.d();
      return var2 == com.cheatbreaker.client.gui.hudeditor.ModuleIconUtils.a.GEAR
         ? com.cheatbreaker.client.gui.hudeditor.ModuleIconUtils.a(var6[0], var6[1], var7, var1.width, var1.height, var4, var5)
         : com.cheatbreaker.client.gui.hudeditor.ModuleIconUtils.b(var6[0], var6[1], var7, var1.width, var1.height, var4, var5);
   }

   private void a(com.cheatbreaker.client.module.Module var1, com.cheatbreaker.client.gui.hudeditor.ModuleIconUtils.a var2, int var3, int var4) {
      this.pendingIconModule = var1;
      this.pendingIcon = var2;
      this.pendingPressX = var3;
      this.pendingPressY = var4;
      this.pendingPressTimeMs = Minecraft.getSystemTime();
   }

   private void d() {
      this.pendingIconModule = null;
      this.pendingIcon = null;
   }

   private void a(com.cheatbreaker.client.module.Module var1, com.cheatbreaker.client.gui.hudeditor.ModuleIconUtils.a var2) {
      Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
      if (var2 == com.cheatbreaker.client.gui.hudeditor.ModuleIconUtils.a.GEAR) {
         Minecraft.getMinecraft().displayGuiScreen(new com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen(this, var1));
      } else {
         var1.a(false);
      }
   }

   private void a(com.cheatbreaker.client.module.Module var1, ScaledResolution var2, int var3, int var4, int var5) {
      boolean var6 = true;
      if (this.d(var1) != null) {
         this.e(var1);
         var6 = false;
      }

      float var7 = var3 - var1.k() * (Float)var1.scale.d();
      float var8 = var4 - var1.l() * (Float)var1.scale.d();
      this.IIlIIllIIIllllIIlllIllIIl = var3;
      this.lllIlIIllllIIIIlIllIlIIII = var4;
      this.IlIlIIIlllllIIIlIlIlIllII = false;
      draggingModule = var1;
      this.dragButton = var5;
      if (this.d(var1) == null) {
         if (!isCtrlKeyDown() && var6) {
            this.positions.clear();
         }

         if (var6 || !isCtrlKeyDown()) {
            this.positions.add(new com.cheatbreaker.client.gui.hudeditor.ModulePosition(var1, var7, var8));
         }
      }

      this.c(var2, var3, var4);
   }

   private boolean b(ScaledResolution var1, int var2, int var3) {
      boolean var4 = false;

      for (com.cheatbreaker.client.module.Module var6 : this.modules) {
         if (c(var6)) {
            boolean var7 = this.a(var6, var1, var2, var3);
            var4 = var4 || var7;
         }
      }

      return var4;
   }

   private boolean a(float var1, com.cheatbreaker.client.module.Module var2, ScaledResolution var3, int var4, int var5, boolean var6) {
      if (!c(var2)) {
         return true;
      } else {
         boolean var13 = false;
         GL11.glPushMatrix();
         float[] var14 = var2.a(var3, true);
         var2.a(var3);
         boolean var12 = this.someMouseX != -1;
         if (var12) {
            Rectangle var15 = new Rectangle(
               (int)(var14[0] * (Float)var2.scale.d() - 2.0F),
               (int)(var14[1] * (Float)var2.scale.d() - 2.0F),
               (int)(var2.width * (Float)var2.scale.d() + 4.0F),
               (int)(var2.height * (Float)var2.scale.d() + 4.0F)
            );
            int var10 = Math.min(this.someMouseX, var4);
            int var9 = Math.min(this.someMouseY, var5);
            int var8 = Math.max(this.someMouseX, var4) - var10;
            int var7 = Math.max(this.someMouseY, var5) - var9;
            Rectangle var16 = new Rectangle(var10, var9, var8, var7);
            var12 = var15.intersects(var16);
         }

         float[] var11 = var2.a(var3, true);
         boolean var24 = this.a(var2, var3, var4, var5);
         if (!this.IlIIIIllIIIIIlllIIlIIlllI) {
            if (this.d(var2) == null && !var12) {
               cn.kazer.client189.render.DrawRect.b(0.0F, 0.0F, var2.width, var2.height, 0.5F, 1879048191, 452984831);
            } else {
               cn.kazer.client189.render.DrawRect.b(0.0F, 0.0F, var2.width, var2.height, 0.5F, -1627324417, 452984831);
            }
         }

         if (!this.IlIIIIllIIIIIlllIIlIIlllI && var24) {
            float var25 = (Float)var2.scale.d();
            boolean var23 = !var2.m().isEmpty() && com.cheatbreaker.client.gui.hudeditor.ModuleIconUtils.a(var11[0], var11[1], var25, var2.width, var2.height, var4, var5);
            boolean var20 = com.cheatbreaker.client.gui.hudeditor.ModuleIconUtils.b(var11[0], var11[1], var25, var2.width, var2.height, var4, var5);
            if (!var2.m().isEmpty()) {
               cn.kazer.client189.render.GlStateBridge.a(1.0F, 1.0F, 1.0F, var23 ? 1.0F : 0.6F);
               com.cheatbreaker.client.gui.render.RenderUtils.a(this.cogIcon, 3.0F, 2.0F, var2.height - 7.5F);
            }

            cn.kazer.client189.render.GlStateBridge.a(0.8F, 0.2F, 0.2F, var20 ? 1.0F : 0.6F);
            com.cheatbreaker.client.gui.render.RenderUtils.a(this.deleteIcon, 3.0F, var2.width - 8.0F, var2.height - 7.5F);
         }

         GL11.glPushMatrix();
         float var26 = var1 / (Float)var2.scale.d();
         GL11.glScalef(var26, var26, var26);
         if (var6) {
            boolean var21 = this.dataHolder != null && this.dataHolder.module == var2 && this.dataHolder.unknown == com.cheatbreaker.client.gui.hudeditor.ResizeCorner.LEFT_BOTTOM
               || !var24
                  && var4 >= (var11[0] + var2.width - 5.0F) * (Float)var2.scale.d()
                  && var4 <= (var11[0] + var2.width + 5.0F) * (Float)var2.scale.d()
                  && var5 >= (var11[1] - 5.0F) * (Float)var2.scale.d()
                  && var5 <= (var11[1] + 5.0F) * (Float)var2.scale.d();
            boolean var19 = this.dataHolder != null && this.dataHolder.module == var2 && this.dataHolder.unknown == com.cheatbreaker.client.gui.hudeditor.ResizeCorner.RIGHT_TOP
               || !var24
                  && var4 >= (var11[0] - 5.0F) * (Float)var2.scale.d()
                  && var4 <= (var11[0] + 5.0F) * (Float)var2.scale.d()
                  && var5 >= (var11[1] + var2.height - 5.0F) * (Float)var2.scale.d()
                  && var5 <= (var11[1] + var2.height + 5.0F) * (Float)var2.scale.d();
            boolean var27 = this.dataHolder != null && this.dataHolder.module == var2 && this.dataHolder.unknown == com.cheatbreaker.client.gui.hudeditor.ResizeCorner.RIGHT_BOTTOM
               || !var24
                  && var4 >= (var11[0] - 5.0F) * (Float)var2.scale.d()
                  && var4 <= (var11[0] + 5.0F) * (Float)var2.scale.d()
                  && var5 >= (var11[1] - 5.0F) * (Float)var2.scale.d()
                  && var5 <= (var11[1] + 5.0F) * (Float)var2.scale.d();
            boolean var17 = this.dataHolder != null && this.dataHolder.module == var2 && this.dataHolder.unknown == com.cheatbreaker.client.gui.hudeditor.ResizeCorner.LEFT_TOP
               || !var24
                  && var4 >= (var11[0] + var2.width - 5.0F) * (Float)var2.scale.d()
                  && var4 <= (var11[0] + var2.width + 5.0F) * (Float)var2.scale.d()
                  && var5 >= (var11[1] + var2.height - 5.0F) * (Float)var2.scale.d()
                  && var5 <= (var11[1] + var2.height + 5.0F) * (Float)var2.scale.d();
            GL11.glPushMatrix();
            float var18 = 4.0F;
            if (this.someMouseX == -1 && var27) {
               GL11.glTranslatef(0.0F, 0.0F, 0.0F);
               cn.kazer.client189.render.DrawRect.a(-var18 / 2.0F, -var18 / 2.0F, var18 / 2.0F, var18 / 2.0F, -16711936);
            }

            if (this.someMouseX == -1 && var21) {
               GL11.glTranslatef(var2.width / var26, 0.0F, 0.0F);
               cn.kazer.client189.render.DrawRect.a(-var18 / 2.0F, -var18 / 2.0F, var18 / 2.0F, var18 / 2.0F, -16711936);
            }

            if (this.someMouseX == -1 && var17) {
               GL11.glTranslatef(var2.width / var26, var2.height / var26, 0.0F);
               cn.kazer.client189.render.DrawRect.a(-var18 / 2.0F, -var18 / 2.0F, var18 / 2.0F, var18 / 2.0F, -16711936);
            }

            if (this.someMouseX == -1 && var19) {
               GL11.glTranslatef(0.0F, var2.height / var26, 0.0F);
               cn.kazer.client189.render.DrawRect.a(-var18 / 2.0F, -var18 / 2.0F, var18 / 2.0F, var18 / 2.0F, -16711936);
            }

            GL11.glPopMatrix();
            var13 = this.someMouseX == -1 && (var27 || var21 || var19 || var17);
         }

         boolean var22 = var14[1] - com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.a() - 6.0F < 0.0F;
         float var28 = var22 ? var2.height * (Float)var2.scale.d() / var1 : -com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.a() - 4;
         switch (var2.s()) {
            case LEFT:
               float var31 = 0.0F;
               com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.b(var2.h(), (double)var31, (double)var28, -1);
               break;
            case CENTER:
               float var30 = var2.width * (Float)var2.scale.d() / var1 / 2.0F;
               com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.b(var2.h(), (double)var30, (double)var28, -1);
               break;
            case RIGHT:
               float var29 = var2.width * (Float)var2.scale.d() / var1 - com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.b(var2.h());
               com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.b(var2.h(), (double)var29, (double)var28, -1);
         }

         GL11.glPopMatrix();
         GL11.glPopMatrix();
         return !var13;
      }
   }

   private void a(ScaledResolution var1) {
      if (!Mouse.isButtonDown(1) && draggingModule != null) {
         for (com.cheatbreaker.client.gui.hudeditor.ModulePosition var3 : this.positions) {
            if (var3.module == draggingModule && (Boolean)com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.snapModules.d()) {
               Object var4 = null;

               for (com.cheatbreaker.client.module.Module var6 : this.modules) {
                  if (this.d(var6) == null && c(var6)) {
                     float[] var7 = var6.a(var1, true);
                     float[] var8 = var3.module.a(var1, true);
                     boolean var9 = false;
                     float var10 = var7[0] * (Float)var6.scale.d() - var8[0] * (Float)var3.module.scale.d();
                     float var11 = (var7[0] + var6.width) * (Float)var6.scale.d() - (var8[0] + var3.module.width) * (Float)var3.module.scale.d();
                     float var12 = (var7[0] + var6.width) * (Float)var6.scale.d() - var8[0] * (Float)var3.module.scale.d();
                     float var13 = var7[0] * (Float)var6.scale.d() - (var8[0] + var3.module.width) * (Float)var3.module.scale.d();
                     float var14 = var7[1] * (Float)var6.scale.d() - var8[1] * (Float)var3.module.scale.d();
                     float var15 = (var7[1] + var6.height) * (Float)var6.scale.d() - (var8[1] + var3.module.height) * (Float)var3.module.scale.d();
                     float var16 = (var7[1] + var6.height) * (Float)var6.scale.d() - var8[1] * (Float)var3.module.scale.d();
                     float var17 = var7[1] * (Float)var6.scale.d() - (var8[1] + var3.module.height) * (Float)var3.module.scale.d();
                     if (this.e(var10)) {
                        var9 = true;
                        com.cheatbreaker.client.gui.render.RenderUtils.a(
                           (double)(var7[0] * (Float)var6.scale.d() - 0.5F), 0.0, (double)(var7[0] * (Float)var6.scale.d()), (double)this.height, 0.0, -3596854
                        );
                     }

                     if (this.e(var11)) {
                        var9 = true;
                        com.cheatbreaker.client.gui.render.RenderUtils.a(
                           (double)((var7[0] + var6.width) * (Float)var6.scale.d()),
                           0.0,
                           (double)((var7[0] + var6.width) * (Float)var6.scale.d() + 0.5F),
                           (double)this.height,
                           0.0,
                           -3596854
                        );
                     }

                     if (this.e(var13)) {
                        var9 = true;
                        com.cheatbreaker.client.gui.render.RenderUtils.a(
                           (double)(var7[0] * (Float)var6.scale.d()), 0.0, (double)(var7[0] * (Float)var6.scale.d() + 0.5F), (double)this.height, 0.0, -3596854
                        );
                     }

                     if (this.e(var12)) {
                        var9 = true;
                        com.cheatbreaker.client.gui.render.RenderUtils.a(
                           (double)((var7[0] + var6.width) * (Float)var6.scale.d()),
                           0.0,
                           (double)((var7[0] + var6.width) * (Float)var6.scale.d() + 0.5F),
                           (double)this.height,
                           0.0,
                           -3596854
                        );
                     }

                     if (this.e(var14)) {
                        var9 = true;
                        com.cheatbreaker.client.gui.render.RenderUtils.a(
                           0.0, (double)(var7[1] * (Float)var6.scale.d()), (double)this.width, (double)(var7[1] * (Float)var6.scale.d() + 0.5F), 0.0, -3596854
                        );
                     }

                     if (this.e(var15)) {
                        var9 = true;
                        com.cheatbreaker.client.gui.render.RenderUtils.a(
                           0.0,
                           (double)((var7[1] + var6.height) * (Float)var6.scale.d()),
                           (double)this.width,
                           (double)((var7[1] + var6.height) * (Float)var6.scale.d() + 0.5F),
                           0.0,
                           -3596854
                        );
                     }

                     if (this.e(var17)) {
                        var9 = true;
                        com.cheatbreaker.client.gui.render.RenderUtils.a(
                           0.0, (double)(var7[1] * (Float)var6.scale.d()), (double)this.width, (double)(var7[1] * (Float)var6.scale.d() + 0.5F), 0.0, -3596854
                        );
                     }

                     if (this.e(var16)) {
                        var9 = true;
                        com.cheatbreaker.client.gui.render.RenderUtils.a(
                           0.0,
                           (double)((var7[1] + var6.height) * (Float)var6.scale.d() - 0.5F),
                           (double)this.width,
                           (double)((var7[1] + var6.height) * (Float)var6.scale.d()),
                           0.0,
                           -3596854
                        );
                     }

                     if (var9) {
                        GL11.glPushMatrix();
                        var6.a(var1);
                        cn.kazer.client189.render.DrawRect.b(0.0F, 0.0F, var6.width, var6.height, 0.5F, 0, 449387978);
                        GL11.glPopMatrix();
                     }
                  }
               }

               this.b(var3.module, var1);
            }
         }
      }
   }

   private float a(com.cheatbreaker.client.module.Module var1, float var2, float[] var3, int var4) {
      float var5 = var2;
      float var6 = 2.0F;
      if (var2 + var3[0] * (Float)var1.scale.d() < var6) {
         var5 = -var3[0] * (Float)var1.scale.d() + var6;
      } else if (var2 + var3[0] * (Float)var1.scale.d() + var4 > this.width - var6) {
         var5 = this.width - var3[0] * (Float)var1.scale.d() - var4 - var6;
      }

      return var5;
   }

   private float b(com.cheatbreaker.client.module.Module var1, float var2, float[] var3, int var4) {
      float var5 = var2;
      float var6 = 2.0F;
      if (var2 + var3[1] * (Float)var1.scale.d() < var6) {
         var5 = -var3[1] * (Float)var1.scale.d() + var6;
      } else if (var2 + var3[1] * (Float)var1.scale.d() + var4 > this.height - var6) {
         var5 = this.height - var3[1] * (Float)var1.scale.d() - var4 - var6;
      }

      return var5;
   }

   private void a(com.cheatbreaker.client.gui.hudeditor.ModulePosition var1, int var2, int var3, ScaledResolution var4) {
      if (c(var1.module)) {
         float var5 = var2 - var1.x;
         float var6 = var3 - var1.y;
         if (!this.IlIlIIIlllllIIIlIlIlIllII
            && var1.module == draggingModule
            && (var2 != this.IIlIIllIIIllllIIlllIllIIl || var3 != this.lllIlIIllllIIIIlIllIlIIII)) {
            if (this.undoList.size() > 50) {
               this.undoList.remove(0);
            }

            this.undoList.add(new com.cheatbreaker.client.gui.hudeditor.LayoutSnapshot(this, this.positions));
            com.cheatbreaker.client.CheatBreakerClient.N().c();
            this.IlIlIIIlllllIIIlIlIlIllII = true;
         }

         float[] var7 = var1.module.a(var4, false);
         if (!Mouse.isButtonDown(1) && this.IlIlIIIlllllIIIlIlIlIllII && var1.module == draggingModule) {
            float var8 = var5;
            float var9 = var6;
            var5 = this.a(var1.module, var5, var7, (int)(var1.module.width * (Float)var1.module.scale.d()));
            var6 = this.b(var1.module, var6, var7, (int)(var1.module.height * (Float)var1.module.scale.d()));
            float var10 = var8 - var5;
            float var11 = var9 - var6;

            for (com.cheatbreaker.client.gui.hudeditor.ModulePosition var13 : this.positions) {
               if (var13 != var1) {
                  var7 = var13.module.a(var4, false);
                  float var14 = this.a(var13.module, var13.module.k() - var10, var7, (int)(var13.module.width * (Float)var13.module.scale.d()));
                  float var15 = this.b(var13.module, var13.module.l() - var11, var7, (int)(var13.module.height * (Float)var13.module.scale.d()));
                  var13.module.a(var14, var15);
               }
            }
         }

         if (this.IlIlIIIlllllIIIlIlIlIllII) {
            var1.module.a(var5, var6);
         }
      }
   }

   private void a(int var1) {
      if (IlIlllIIIIllIllllIllIIlIl) {
         if (this.lIIIIllIIlIlIllIIIlIllIlI != null) {
            this.a(this.lIIIIllIIlIlIllIIIlIllIlI, true, var1);
         }
      } else if (this.currentScrollableElement != null) {
         if (this.lIIIIllIIlIlIllIIIlIllIlI != null) {
            this.a(this.lIIIIllIIlIlIllIIIlIllIlI, true, var1);
         }

         this.a(this.currentScrollableElement, false, var1);
      }
   }

   private void a(com.cheatbreaker.client.gui.uielement.ScrollableUIElement var1, boolean var2, int var3) {
      if (var2) {
         var1.x = var1.IlIlllIIIIllIllllIllIIlIl;
         IlIlllIIIIllIllllIllIIlIl = false;
         this.lIIIIllIIlIlIllIIIlIllIlI = null;
      } else {
         var1.x = var3 / 2 - 185;
         this.currentScrollableElement = null;
         this.lIIIIllIIlIlIllIIIlIllIlI = var1;
      }
   }

   public static float a(float var0) {
      float var1 = var0 / (Minecraft.getDebugFPS() + 1);
      return Math.max(var1, 1.0F);
   }

   private com.cheatbreaker.client.gui.hudeditor.ModulePosition d(com.cheatbreaker.client.module.Module var1) {
      for (com.cheatbreaker.client.gui.hudeditor.ModulePosition var3 : this.positions) {
         if (var1 == var3.module) {
            return var3;
         }
      }

      return null;
   }

   private void c(ScaledResolution var1, int var2, int var3) {
      for (com.cheatbreaker.client.gui.hudeditor.ModulePosition var5 : this.positions) {
         if (var5.module != null && var5.module.r() != null) {
            var5.x = var2 - var5.module.k();
            var5.y = var3 - var5.module.l();
         }
      }
   }

   private void e(com.cheatbreaker.client.module.Module var1) {
      this.positions.removeIf(var1x -> var1x.module == var1);
   }

   private static final class a {
      final com.cheatbreaker.client.module.Module module;
      final com.cheatbreaker.client.gui.hudeditor.ModuleIconUtils.a icon;

      a(com.cheatbreaker.client.module.Module var1, com.cheatbreaker.client.gui.hudeditor.ModuleIconUtils.a var2) {
         this.module = var1;
         this.icon = var2;
      }
   }
}
