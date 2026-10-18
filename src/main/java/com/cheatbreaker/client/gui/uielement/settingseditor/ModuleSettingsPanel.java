package com.cheatbreaker.client.gui.uielement.settingseditor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

public class ModuleSettingsPanel extends com.cheatbreaker.client.gui.uielement.ScrollableUIElement {
   static final int PANEL_CORNER_RADIUS = 8;
   private static final int MODULE_ROW_HEIGHT = 18;
   private final int colour;
   protected final List<com.cheatbreaker.client.gui.uielement.settingseditor.ModuleRowElement> moduleSettingsElementList;
   private final boolean lIIIIIllllIIIIlIlIIIIlIlI;
   private final com.cheatbreaker.client.gui.uielement.settings.widgets.SettingsHeaderElement globalSettingsElement;
   public com.cheatbreaker.client.gui.uielement.ScrollableUIElement scrollable;
   public boolean llIlIIIlIIIIlIlllIlIIIIll = false;
   public com.cheatbreaker.client.module.Module module;
   private final com.cheatbreaker.client.gui.uielement.settingseditor.SettingsButtonElement backButton;
   private final com.cheatbreaker.client.gui.uielement.settingseditor.SettingsButtonElement applyToAllTextButton;
   private final Map<com.cheatbreaker.client.module.Module, List<com.cheatbreaker.client.gui.uielement.UIElement>> moduleElementListMap;
   private final List<com.cheatbreaker.client.gui.uielement.UIElement> elementsList;

   public ModuleSettingsPanel(List<com.cheatbreaker.client.module.Module> var1, float var2, int var3, int var4, int var5, int var6) {
      super(var2, var3, var4, var5, var6);
      this.lIIIIIllllIIIIlIlIIIIlIlI = var1 == com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.staffModules;
      this.colour = -12418828;
      this.globalSettingsElement = new com.cheatbreaker.client.gui.uielement.settings.widgets.SettingsHeaderElement(this, this.colour, var2);
      this.moduleSettingsElementList = new ArrayList<>();

      for (Object var8 : var1) {
         if (!((com.cheatbreaker.client.module.Module)var8).e() || ((com.cheatbreaker.client.module.Module)var8).f()) {
            this.moduleSettingsElementList.add(new com.cheatbreaker.client.gui.uielement.settingseditor.ModuleRowElement(this, this.colour, (com.cheatbreaker.client.module.Module)var8, var2));
         }
      }

      this.backButton = new com.cheatbreaker.client.gui.uielement.settingseditor.SettingsButtonElement(null, "arrow-64.png", this.x + 2, this.y + 4, 28, 28, -12418828, var2).b(8);
      this.module = null;
      this.moduleElementListMap = new HashMap<>();

      for (com.cheatbreaker.client.module.Module var19 : var1) {
         if (!var19.e() || var19.f()) {
            ArrayList var9 = new ArrayList();
            if (var19 instanceof com.cheatbreaker.client.module.modules.ItemCountModule) {
               com.cheatbreaker.client.module.modules.ItemCountModule var25 = (com.cheatbreaker.client.module.modules.ItemCountModule)var19;
               var9.add(new com.cheatbreaker.client.gui.uielement.settingseditor.ItemCountToggleElement(var25, this, var2));

               for (com.cheatbreaker.client.module.Module var28 : var25.a()) {
                  ArrayList var29 = new ArrayList();

                  for (com.cheatbreaker.client.settings.Setting var31 : var25.a(var28)) {
                     if (var31.l().equals(com.cheatbreaker.client.settings.Setting.SettingType.INTEGER) && var31.f().toLowerCase().contains("color")) {
                        var29.add(new com.cheatbreaker.client.gui.uielement.settings.ColorPickerSettingElement(var31, var2));
                     } else if (var31.l().equals(com.cheatbreaker.client.settings.Setting.SettingType.STRING_ARRAY)) {
                        var29.add(new com.cheatbreaker.client.gui.uielement.settings.StringArraySettingElement(var31, var2));
                     } else if (var31.l().equals(com.cheatbreaker.client.settings.Setting.SettingType.BOOLEAN)) {
                        var29.add(new com.cheatbreaker.client.gui.uielement.settings.BooleanSettingElement(var31, var2));
                     } else if (var31.l().equals(com.cheatbreaker.client.settings.Setting.SettingType.INTEGER)
                        || var31.l().equals(com.cheatbreaker.client.settings.Setting.SettingType.DOUBLE)
                        || var31.l().equals(com.cheatbreaker.client.settings.Setting.SettingType.FLOAT)) {
                        var29.add(new com.cheatbreaker.client.gui.uielement.settings.SliderSettingElement(var31, var2));
                     }
                  }

                  this.moduleElementListMap.put(var28, var29);
               }

               this.moduleElementListMap.put(var19, var9);
            } else if (var19 instanceof com.cheatbreaker.client.module.modules.keybinds.AutoTextHotkeyModule) {
               com.cheatbreaker.client.module.modules.keybinds.AutoTextHotkeyModule var24 = (com.cheatbreaker.client.module.modules.keybinds.AutoTextHotkeyModule)var19;
               var9.add(new com.cheatbreaker.client.gui.uielement.settingseditor.AutoTextHotkeyListElement(var24, var2));
               this.moduleElementListMap.put(var19, var9);
            } else if (var19 instanceof com.cheatbreaker.client.module.modules.config.CustomTextModule) {
               com.cheatbreaker.client.module.modules.config.CustomTextModule var23 = (com.cheatbreaker.client.module.modules.config.CustomTextModule)var19;
               var9.add(new com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextListElement(var23, var2));
               this.moduleElementListMap.put(var19, var9);

               for (com.cheatbreaker.client.module.Module var12 : var23.a()) {
                  ArrayList var13 = new ArrayList();
                  var13.add(new com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextContentElement(var23, (com.cheatbreaker.client.module.modules.config.CustomTextModule.a)var12, var2));

                  for (com.cheatbreaker.client.settings.Setting var15 : var23.a(var12)) {
                     if (var15.j() && !var15.l().equals(com.cheatbreaker.client.settings.Setting.SettingType.STRING)) {
                        switch (var15.l()) {
                           case BOOLEAN:
                              var13.add(new com.cheatbreaker.client.gui.uielement.settings.BooleanSettingElement(var15, var2));
                              break;
                           case INTEGER:
                           case FLOAT:
                           case DOUBLE:
                              if (var15.f().toLowerCase().contains("color")) {
                                 var13.add(new com.cheatbreaker.client.gui.uielement.settings.ColorPickerSettingElement(var15, var2));
                              } else {
                                 var13.add(new com.cheatbreaker.client.gui.uielement.settings.SliderSettingElement(var15, var2));
                              }
                              break;
                           case STRING_ARRAY:
                              var13.add(new com.cheatbreaker.client.gui.uielement.settings.StringArraySettingElement(var15, var2));
                        }
                     }
                  }

                  this.moduleElementListMap.put(var12, var13);
               }
            } else {
               for (com.cheatbreaker.client.settings.Setting var11 : var19.m()) {
                  if (var11.j()) {
                     switch (var11.l()) {
                        case BOOLEAN:
                           var9.add(new com.cheatbreaker.client.gui.uielement.settings.BooleanSettingElement(var11, var2));
                           break;
                        case INTEGER:
                        case FLOAT:
                        case DOUBLE:
                           if (var19.e() && var11 == ((com.cheatbreaker.client.module.module.KeybindModule)var19).a()
                              || var19.e() && var11 == var19.scale
                              || !var19.defaultRenderHud && var11 == var19.scale) {
                              break;
                           }

                           if (var11.l().equals(com.cheatbreaker.client.settings.Setting.SettingType.INTEGER) && var11.f().toLowerCase().contains("color")) {
                              var9.add(new com.cheatbreaker.client.gui.uielement.settings.ColorPickerSettingElement(var11, var2));
                              break;
                           }

                           var9.add(new com.cheatbreaker.client.gui.uielement.settings.SliderSettingElement(var11, var2));
                           break;
                        case STRING_ARRAY:
                           var9.add(new com.cheatbreaker.client.gui.uielement.settings.StringArraySettingElement(var11, var2));
                           break;
                        case STRING:
                           if (var11.f().equalsIgnoreCase("label")) {
                              var9.add(new com.cheatbreaker.client.gui.uielement.settings.LabelSettingElement(var11, var2));
                           }
                     }
                  }
               }

               if (var19.e()) {
                  var9.add(new com.cheatbreaker.client.gui.uielement.settings.widgets.KeybindSettingElement(((com.cheatbreaker.client.module.module.KeybindModule)var19).a(), var2));
                  if (var19 == com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.xray) {
                     var9.add(new com.cheatbreaker.client.gui.uielement.settings.widgets.XRayBlockPickerElement(com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.xray.A(), "Blocks", var2));
                  }
               }

               this.moduleElementListMap.put(var19, var9);
            }
         }
      }

      this.elementsList = new ArrayList<>();

      for (com.cheatbreaker.client.settings.Setting var20 : com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.settingsList) {
         this.elementsList.addAll(a(var20, var2));
      }

      int var18 = 25;

      for (Object var22 : this.elementsList) {
         var18 += ((com.cheatbreaker.client.gui.uielement.UIElement)var22).c();
      }

      int var10006 = this.x + var5 - 120;
      this.applyToAllTextButton = new com.cheatbreaker.client.gui.uielement.settingseditor.SettingsButtonElement(
         com.cheatbreaker.client.CheatBreakerClient.N().playBold18px, null, "Apply to all text", var10006, this.y + var18 + 4, 110, 28, -12418828, var2
      );
   }

   public void a(com.cheatbreaker.client.module.modules.config.CustomTextModule var1) {
      for (com.cheatbreaker.client.module.Module var3 : var1.a()) {
         List var4 = this.moduleElementListMap.computeIfAbsent(var3, var0 -> new ArrayList<>());
         var4.clear();

         for (com.cheatbreaker.client.settings.Setting var6 : var1.a(var3)) {
            if (var6.j()) {
               switch (var6.l()) {
                  case BOOLEAN:
                     var4.add(new com.cheatbreaker.client.gui.uielement.settings.BooleanSettingElement(var6, this.scale));
                     break;
                  case INTEGER:
                     if (var6.f().toLowerCase().contains("color")) {
                        var4.add(new com.cheatbreaker.client.gui.uielement.settings.ColorPickerSettingElement(var6, this.scale));
                     } else {
                        var4.add(new com.cheatbreaker.client.gui.uielement.settings.SliderSettingElement(var6, this.scale));
                     }
                     break;
                  case FLOAT:
                  case DOUBLE:
                     var4.add(new com.cheatbreaker.client.gui.uielement.settings.SliderSettingElement(var6, this.scale));
                     break;
                  case STRING_ARRAY:
                     var4.add(new com.cheatbreaker.client.gui.uielement.settings.StringArraySettingElement(var6, this.scale));
               }
            }
         }
      }
   }

   static String a(String var0, boolean var1) {
      return var1 ? var0 : var0 + " Settings";
   }

   static int a(com.cheatbreaker.client.gui.uielement.UIElement var0) {
      return 2 + var0.c() + (var0 instanceof com.cheatbreaker.client.gui.uielement.settings.StringArraySettingElement ? 4 : 0);
   }

   static int c(int var0, int var1, int var2) {
      return var0 + var1 * (var2 + 2) + 2;
   }

   static List<com.cheatbreaker.client.gui.uielement.UIElement> a(com.cheatbreaker.client.settings.Setting var0, float var1) {
      ArrayList var2 = new ArrayList();
      switch (var0.l()) {
         case BOOLEAN:
            var2.add(new com.cheatbreaker.client.gui.uielement.settings.BooleanSettingElement(var0, var1));
            break;
         case INTEGER:
         case FLOAT:
         case DOUBLE:
            if (var0.l().equals(com.cheatbreaker.client.settings.Setting.SettingType.INTEGER) && var0.f().toLowerCase().contains("color")) {
               var2.add(new com.cheatbreaker.client.gui.uielement.settings.ColorPickerSettingElement(var0, var1));
            } else if (var0.f().equals("World Time")) {
               var2.add(new com.cheatbreaker.client.gui.uielement.settings.widgets.WorldTimeSettingElement(var0, var1));
            } else {
               var2.add(new com.cheatbreaker.client.gui.uielement.settings.SliderSettingElement(var0, var1));
            }
            break;
         case STRING_ARRAY:
            var2.add(new com.cheatbreaker.client.gui.uielement.settings.StringArraySettingElement(var0, var1));
            break;
         case STRING:
            if (var0.f().equalsIgnoreCase("label")) {
               var2.add(new com.cheatbreaker.client.gui.uielement.settings.LabelSettingElement(var0, var1));
               if (com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.a().d().equals(var0.d())) {
                  var2.add(new com.cheatbreaker.client.gui.uielement.settings.widgets.CrosshairPreviewElement(var1));
               }
            }
      }

      return var2;
   }

   @Override
   public void a(int var1, int var2, float var3) {
      com.cheatbreaker.client.gui.render.RenderUtils.a((double)this.x, (double)this.y, (double)(this.x + this.width), (double)(this.y + this.height + 2), 8.0, -657931);
      this.b(var1, var2);
      if (this.module == null && !this.llIlIIIlIIIIlIlllIlIIIIll) {
         int var9 = this.lIIIIIllllIIIIlIlIIIIlIlI ? 4 : 24;
         this.IlllIllIlIIIIlIIlIIllIIIl = c(var9, this.moduleSettingsElementList.size(), 18);
         if (!this.lIIIIIllllIIIIlIlIIIIlIlI) {
            this.globalSettingsElement.a(this.x + 4, this.y + 4, this.width - 12, 18);
            this.globalSettingsElement.yOffset = this.lIIIIllIIlIlIllIIIlIllIlI;
            this.globalSettingsElement.a(var1, var2, var3);
         }

         for (int var11 = 0; var11 < this.moduleSettingsElementList.size(); var11++) {
            com.cheatbreaker.client.gui.uielement.settingseditor.ModuleRowElement var13 = this.moduleSettingsElementList.get(var11);
            var13.a(this.x + 4, this.y + var9 + var11 * 20, this.width - 12, 18);
            var13.yOffset = this.lIIIIllIIlIlIllIIIlIllIlI;
            var13.a(var1, var2, var3);
         }
      } else if (this.llIlIIIlIIIIlIlllIlIIIIll && !this.lIIIIIllllIIIIlIlIIIIlIlI) {
         cn.kazer.client189.render.DrawRect.a(this.x + 32, this.y + 4, this.x + 33, this.y + this.height - 4, 791621423);
         this.IlllIllIlIIIIlIIlIIllIIIl = 25;
         this.backButton.a(this.x + 2, this.y + 2, 28, 28);
         this.backButton.yOffset = this.lIIIIllIIlIlIllIIIlIllIlI;
         this.backButton.a(var1, var2, var3);
         com.cheatbreaker.client.CheatBreakerClient.N().ubuntuMedium16px.a("Kazer Client Settings".toUpperCase(), (float)(this.x + 38), (float)(this.y + 9), -1358954496);
         cn.kazer.client189.render.DrawRect.a(this.x + 38, this.y + 17, this.x + this.width - 6, this.y + 18, 791621423);
         int var8 = 0;

         for (com.cheatbreaker.client.gui.uielement.UIElement var12 : this.elementsList) {
            if (b(var12)) {
               var12.a(this.x + 38, this.y + 22 + var8, this.width - 40, var12.c());
               var12.yOffset = this.lIIIIllIIlIlIllIIIlIllIlI;
               var12.a(var1, var2, var3);
               var8 += a(var12);
               this.IlllIllIlIIIIlIIlIIllIIIl = this.IlllIllIlIIIIlIIlIIllIIIl + a(var12);
            }
         }

         this.applyToAllTextButton.yOffset = this.lIIIIllIIlIlIllIIIlIllIlI;
         this.applyToAllTextButton.a(this.x + this.width - 118, this.y + this.IlllIllIlIIIIlIIlIIllIIIl, 100, 20);
         this.applyToAllTextButton.a(var1, var2, var3);
         this.IlllIllIlIIIIlIIlIIllIIIl += 24;
      } else {
         cn.kazer.client189.render.DrawRect.a(this.x + 32, this.y + 4, this.x + 33, this.y + Math.max(this.height, this.IlllIllIlIIIIlIIlIIllIIIl) - 4, 791621423);
         this.IlllIllIlIIIIlIIlIIllIIIl = 37;
         this.backButton.a(this.x + 2, this.y + 2, 28, 28);
         this.backButton.yOffset = this.lIIIIllIIlIlIllIIIlIllIlI;
         this.backButton.a(var1, var2, var3);
         com.cheatbreaker.client.CheatBreakerClient.N()
            .ubuntuMedium16px
            .b(a(this.module.h(), this.module instanceof com.cheatbreaker.client.module.ModuleProxy).toUpperCase(), (double)(this.x + 38), (double)(this.y + 6), -1358954496);
         cn.kazer.client189.render.DrawRect.a(this.x + 38, this.y + 17, this.x + this.width - 12, this.y + 18, 791621423);
         int var4 = 0;
         if (this.module != null && this.moduleElementListMap.containsKey(this.module)) {
            for (com.cheatbreaker.client.gui.uielement.UIElement var6 : this.moduleElementListMap.get(this.module)) {
               if (b(var6)) {
                  var6.a(this.x + 38, this.y + 22 + var4, this.width - 40, var6.c());
                  var6.yOffset = this.lIIIIllIIlIlIllIIIlIllIlI;
                  var6.a(var1, var2, var3);
                  int var7 = a(var6);
                  var4 += var7;
                  this.IlllIllIlIIIIlIIlIIllIIIl += var7;
               }
            }
         }

         if (this.module == com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.freelook) {
            float var10002 = this.x + 38;
            com.cheatbreaker.client.CheatBreakerClient.N().playRegular14px.a("bind freelook hotkey in Controls", var10002, (float)(this.y + this.height - 18), -1895825408);
         }
      }

      this.c(var1, var2);
   }

   @Override
   public void a(int var1, int var2, int var3) {
      if (this.module == null && !this.llIlIIIlIIIIlIlllIlIIIIll) {
         if (!this.globalSettingsElement.a(var1, var2) || this.lIIIIIllllIIIIlIlIIIIlIlI) {
            for (com.cheatbreaker.client.gui.uielement.settingseditor.ModuleRowElement var14 : this.moduleSettingsElementList) {
               if (var14.a(var1, var2) && this.a(var14.module)) {
                  var14.a(var1, var2, var3);
               }
            }
         } else {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            this.llIlIIIlIIIIlIlllIlIIIIll = true;
            this.lIIIIllIIlIlIllIIIlIllIlI = 0;
            this.IllIIIIIIIlIlIllllIIllIII = 0.0;
            this.yOffset = 0;
         }
      } else if (!this.backButton.a(var1, var2)) {
         if (this.module != null && this.moduleElementListMap.containsKey(this.module)) {
            for (com.cheatbreaker.client.gui.uielement.UIElement var13 : this.moduleElementListMap.get(this.module)) {
               if (b(var13) && var13.a(var1, var2)) {
                  var13.a(var1, var2, var3);
               }
            }
         } else if (this.llIlIIIlIIIIlIlllIlIIIIll) {
            if (this.applyToAllTextButton.a(var1, var2)) {
               for (com.cheatbreaker.client.module.Module var5 : com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.modules) {
                  for (com.cheatbreaker.client.settings.Setting var7 : var5.m()) {
                     if (var7.l() == com.cheatbreaker.client.settings.Setting.SettingType.INTEGER
                        && var7.f().toLowerCase().contains("color")
                        && !var7.f().toLowerCase().contains("background")) {
                        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                        var7.a(com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.defaultColor.d());
                     }
                  }
               }
            } else {
               for (com.cheatbreaker.client.gui.uielement.UIElement var12 : this.elementsList) {
                  if (b(var12) && var12.a(var1, var2)) {
                     var12.a(var1, var2, var3);
                  }
               }
            }
         }
      } else {
         Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
         boolean var10 = this.module instanceof com.cheatbreaker.client.module.ModuleProxy;
         this.module = var10 ? ((com.cheatbreaker.client.module.ModuleProxy)this.module).a() : null;
         this.llIlIIIlIIIIlIlllIlIIIIll = false;
         if (!var10 && this.scrollable != null) {
            com.cheatbreaker.client.gui.hudeditor.HudEditorScreen.instance.currentScrollableElement = this.scrollable;
         }
      }
   }

   @Override
   public boolean a(char var1, int var2) {
      for (com.cheatbreaker.client.gui.uielement.UIElement var4 : this.d()) {
         if (var4.a(var1, var2)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public void b(int var1, int var2, int var3) {
      for (com.cheatbreaker.client.gui.uielement.UIElement var5 : this.d()) {
         var5.b(var1, var2, var3);
      }
   }

   @Override
   public void a() {
      for (com.cheatbreaker.client.gui.uielement.UIElement var2 : this.d()) {
         var2.a();
      }
   }

   private static boolean b(com.cheatbreaker.client.gui.uielement.UIElement var0) {
      com.cheatbreaker.client.settings.Setting var1 = var0.b();
      return var1 == null || var1.j();
   }

   private List<com.cheatbreaker.client.gui.uielement.UIElement> d() {
      if (this.module != null && this.moduleElementListMap.containsKey(this.module)) {
         ArrayList var4 = new ArrayList();

         for (com.cheatbreaker.client.gui.uielement.UIElement var6 : this.moduleElementListMap.get(this.module)) {
            if (b(var6)) {
               var4.add(var6);
            }
         }

         return var4;
      } else if (this.llIlIIIlIIIIlIlllIlIIIIll) {
         ArrayList var1 = new ArrayList();

         for (com.cheatbreaker.client.gui.uielement.UIElement var3 : this.elementsList) {
            if (b(var3)) {
               var1.add(var3);
            }
         }

         return var1;
      } else {
         return Collections.emptyList();
      }
   }

   @Override
   public boolean a(com.cheatbreaker.client.module.Module var1) {
      return !var1.m().isEmpty() || var1.h().contains("Zans") || var1 instanceof com.cheatbreaker.client.module.modules.config.CustomTextModule;
   }

   @Override
   public void b(com.cheatbreaker.client.module.Module var1) {
      Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
      this.lIIIIllIIlIlIllIIIlIllIlI = 0;
      this.IllIIIIIIIlIlIllllIIllIII = 0.0;
      this.yOffset = 0;
      this.module = var1;
      this.scrollable = null;
   }
}
