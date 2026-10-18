package com.cheatbreaker.client.settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;

public class ConfigManager {
   public static final File configDir = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "config" + File.separator + "KazerClient");
   public static final File profilesDir = new File(configDir + File.separator + "profiles");
   private static final File globalConfig = new File(configDir + File.separator + "global.cfg");

   public void a() {
      try {
         if (this.d()) {
            this.b(globalConfig);
            this.b(com.cheatbreaker.client.CheatBreakerClient.N().activeProfile.c());
         }
      } catch (IOException var2) {
         var2.printStackTrace();
      }
   }

   public void b() {
      try {
         if (this.d()) {
            this.a(globalConfig);
            if (com.cheatbreaker.client.CheatBreakerClient.N().activeProfile == null) {
               com.cheatbreaker.client.CheatBreakerClient.N().activeProfile = com.cheatbreaker.client.CheatBreakerClient.N().profiles.get(0);
            }

            this.a(com.cheatbreaker.client.CheatBreakerClient.N().activeProfile.c());
         }
      } catch (IOException var2) {
         var2.printStackTrace();
      }
   }

   private boolean d() throws IOException {
      return (configDir.exists() || configDir.mkdirs()) && (globalConfig.exists() || globalConfig.createNewFile());
   }

   public void a(File var1) {
      if (!var1.exists()) {
         this.b(var1);
      } else {
         try {
            ArrayList var3 = new ArrayList();
            BufferedReader var4 = new BufferedReader(new FileReader(var1));

            String var2;
            while ((var2 = var4.readLine()) != null) {
               try {
                  String[] var6;
                  if (!var2.startsWith("#") && var2.length() != 0 && (var6 = var2.split("=", 2)).length == 2) {
                     if (var6[0].equalsIgnoreCase("ProfileIndexes")) {
                        for (String var21 : var6[1].split("]\\[")) {
                           var21 = var21.replaceFirst("\\[", "");
                           String[] var11 = var21.split(",", 2);

                           try {
                              int var12 = Integer.parseInt(var11[1]);

                              for (com.cheatbreaker.client.settings.Profile var14 : com.cheatbreaker.client.CheatBreakerClient.N().profiles) {
                                 if (var12 != 0 && var14.c().equalsIgnoreCase(var11[0])) {
                                    var14.a(var12);
                                 }
                              }
                           } catch (NumberFormatException var15) {
                           }
                        }
                     } else if (!var6[0].equalsIgnoreCase("ActiveProfile")) {
                        if (var6[0].equalsIgnoreCase("FavoriteModules")) {
                           com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.favoriteModules.c(var6[1]);
                        } else {
                           var3.add(var2);
                        }
                     } else {
                        File var5 = null;
                        File var7 = new File(configDir + File.separator + "profiles");
                        if (var7.exists() || var7.mkdirs()) {
                           var5 = new File(var7 + File.separator + var6[1] + ".cfg");
                        }

                        if (var5 != null && var5.exists()) {
                           com.cheatbreaker.client.settings.Profile var8 = null;

                           for (com.cheatbreaker.client.settings.Profile var10 : com.cheatbreaker.client.CheatBreakerClient.N().profiles) {
                              if (var6[1].equalsIgnoreCase(var10.c())) {
                                 var8 = var10;
                              }
                           }

                           if (var8 != null && var8.b()) {
                              com.cheatbreaker.client.CheatBreakerClient.N().activeProfile = var8;
                           }
                        }
                     }
                  }
               } catch (Exception var16) {
                  var16.printStackTrace();
               }
            }

            var4.close();
            LegacySettingsMigrator.a(var3, com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.settingsList);
            com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.keyBindings.a(var3, e());
         } catch (IOException var17) {
            var17.printStackTrace();
         }

         this.b(var1);
      }
   }

   private static ArrayList<String> e() {
      ArrayList var0 = new ArrayList();
      File var1 = new File(Minecraft.getMinecraft().mcDataDir, "options.txt");
      if (!var1.exists()) {
         return var0;
      } else {
         String var4;
         try (BufferedReader var2 = new BufferedReader(new FileReader(var1))) {
            while ((var4 = var2.readLine()) != null) {
               var0.add(var4);
            }
         } catch (Exception var15) {
         }

         return var0;
      }
   }

   public void b(File var1) {
      try {
         BufferedWriter var2 = new BufferedWriter(new FileWriter(var1));
         var2.write("################################");
         var2.newLine();
         var2.write("# Kazer Client: GLOBAL SETTINGS");
         var2.newLine();
         var2.write("################################");
         var2.newLine();
         var2.newLine();
         if (com.cheatbreaker.client.CheatBreakerClient.N().activeProfile != null && com.cheatbreaker.client.CheatBreakerClient.N().activeProfile.b()) {
            var2.write("ActiveProfile=" + com.cheatbreaker.client.CheatBreakerClient.N().activeProfile.c());
            var2.newLine();
         }

         for (com.cheatbreaker.client.settings.Setting var4 : com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.settingsList) {
            if (!var4.f().equalsIgnoreCase("label")) {
               if (var4.rainbow) {
                  var2.write(var4.f() + "=" + var4.d() + ";rainbow");
               } else {
                  var2.write(var4.f() + "=" + var4.d());
               }

               var2.newLine();
            }
         }

         var2.write("FavoriteModules=" + com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.favoriteModules.a());
         var2.newLine();

         for (String var8 : com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.keyBindings.a()) {
            var2.write(var8);
            var2.newLine();
         }

         var2.newLine();
         var2.write("ProfileIndexes=");

         for (com.cheatbreaker.client.settings.Profile var9 : com.cheatbreaker.client.CheatBreakerClient.N().profiles) {
            var2.write("[" + var9.c() + "," + var9.a() + "]");
         }

         var2.newLine();
         var2.close();
      } catch (IOException var5) {
         var5.printStackTrace();
      }
   }

   public void c() {
      this.b(globalConfig);
   }

   public boolean a(com.cheatbreaker.client.settings.Profile var1) {
      if (var1 != null && var1.b() && com.cheatbreaker.client.settings.FilenameValidator.a(var1.c())) {
         File var2 = new File(profilesDir, var1.c() + ".cfg");

         try {
            File var3 = profilesDir.getCanonicalFile();
            File var4 = var2.getCanonicalFile();
            return !var4.getParentFile().equals(var3) ? false : !var4.exists() || var4.delete();
         } catch (IOException var5) {
            return false;
         }
      } else {
         return false;
      }
   }

   public void a(String var1) {
      if (var1.equalsIgnoreCase("default")) {
         System.out.println("[KC] setting up default profile");

         for (com.cheatbreaker.client.module.Module var23 : com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.modules) {
            if (!(var23 instanceof cn.kazer.client189.animation.LegacyVisualsModule)) {
               var23.a(var23.defaultState);
               var23.a(var23.defaultGuiAnchor);
               var23.a(var23.defaultXTranslation, var23.defaultYTranslation);
               var23.f(var23.defaultRenderHud);

               for (int var25 = 0; var25 < var23.m().size(); var25++) {
                  try {
                     var23.m().get(var25).a(var23.n().get(var25), false);
                  } catch (Exception var17) {
                     var17.printStackTrace();
                  }
               }

               if (var23 instanceof com.cheatbreaker.client.module.modules.ItemCountModule) {
                  ((com.cheatbreaker.client.module.modules.ItemCountModule)var23).D();
               }
            }
         }
      } else {
         ArrayList var2 = new ArrayList<>(com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.modules);
         var2.removeIf(var0 -> var0 instanceof cn.kazer.client189.animation.LegacyVisualsModule);

         for (com.cheatbreaker.client.module.Module var4 : (Iterable<com.cheatbreaker.client.module.Module>) var2) {
            if (var4 instanceof com.cheatbreaker.client.module.modules.ItemCountModule) {
               ((com.cheatbreaker.client.module.modules.ItemCountModule)var4).D();
            }

            if (var4 instanceof com.cheatbreaker.client.module.modules.config.CustomTextModule) {
               ((com.cheatbreaker.client.module.modules.config.CustomTextModule)var4).D();
            }

            if (var4 instanceof com.cheatbreaker.client.module.modules.keybinds.AutoTextHotkeyModule) {
               ((com.cheatbreaker.client.module.modules.keybinds.AutoTextHotkeyModule)var4).B();
            }
         }

         File var22 = new File(configDir + File.separator + "profiles");
         File var24 = !var22.exists() && !var22.mkdirs() ? null : new File(var22 + File.separator + var1 + ".cfg");
         if (var24 != null && var24.exists()) {
            try {
               BufferedReader var6 = new BufferedReader(new FileReader(var24));
               com.cheatbreaker.client.module.Module var7 = null;
               boolean var8 = false;

               String var5;
               while ((var5 = var6.readLine()) != null) {
                  try {
                     if (!var5.startsWith("#") && var5.length() != 0) {
                        if (var5.startsWith("[")) {
                           var7 = null;

                           for (com.cheatbreaker.client.module.Module var32 : (Iterable<com.cheatbreaker.client.module.Module>) var2) {
                              if (("[" + var32.h() + "]").equalsIgnoreCase(var5)) {
                                 var7 = var32;
                              }
                           }
                        } else if (var7 != null) {
                           if (var5.startsWith("-")) {
                              String[] var26 = var5.replaceFirst("-", "").split("=", 2);
                              if (var26.length == 2) {
                                 if (var26[0].equalsIgnoreCase("ItemCountState") && var7 instanceof com.cheatbreaker.client.module.modules.ItemCountModule) {
                                    ((com.cheatbreaker.client.module.modules.ItemCountModule)var7).a(var26[1]);
                                 } else if (var26[0].equalsIgnoreCase("CustomTextState") && var7 instanceof com.cheatbreaker.client.module.modules.config.CustomTextModule) {
                                    ((com.cheatbreaker.client.module.modules.config.CustomTextModule)var7).b(var26[1]);
                                 } else if (var26[0].equalsIgnoreCase("AutoTextHotkeyState") && var7 instanceof com.cheatbreaker.client.module.modules.keybinds.AutoTextHotkeyModule) {
                                    ((com.cheatbreaker.client.module.modules.keybinds.AutoTextHotkeyModule)var7).b(var26[1]);
                                 } else {
                                    try {
                                       String var28 = var26[0];
                                       switch (var28) {
                                          case "State":
                                             if (!var7.e()) {
                                                var7.a(Boolean.parseBoolean(var26[1]));
                                             }
                                             break;
                                          case "RenderHUD":
                                             var7.f(Boolean.parseBoolean(var26[1]));
                                             break;
                                          case "xTranslation":
                                             var7.a(Float.parseFloat(var26[1]));
                                             break;
                                          case "yTranslation":
                                             var7.b(Float.parseFloat(var26[1]));
                                             break;
                                          case "Anchor":
                                             var7.a(com.cheatbreaker.client.gui.hudeditor.GuiAnchor.valueOf(var26[1]));
                                             if (var7 instanceof com.cheatbreaker.client.module.modules.ChatModule) {
                                                var8 = true;
                                             }
                                       }
                                    } catch (Exception var18) {
                                       var18.printStackTrace();
                                    }
                                 }
                              }
                           } else {
                              String[] var9 = var5.split("=", 2);
                              if (var9.length == 2) {
                                 for (com.cheatbreaker.client.settings.Setting var11 : var7.m()) {
                                    if (!var11.f().equalsIgnoreCase("label") && var11.f().equalsIgnoreCase(var9[0])) {
                                       switch (var11.l()) {
                                          case BOOLEAN:
                                             var11.a(Boolean.parseBoolean(var9[1]));
                                             break;
                                          case INTEGER:
                                             com.cheatbreaker.client.settings.SettingValueParser.a(var11, var9[1]);
                                             break;
                                          case FLOAT:
                                             com.cheatbreaker.client.settings.SettingValueParser.a(var11, var9[1]);
                                             break;
                                          case DOUBLE:
                                             com.cheatbreaker.client.settings.SettingValueParser.a(var11, var9[1]);
                                             break;
                                          case STRING_ARRAY:
                                             boolean var12 = false;

                                             for (String var16 : var11.h()) {
                                                if (var16.equalsIgnoreCase(var9[1])) {
                                                   var12 = true;
                                                }
                                             }

                                             if (var12) {
                                                var11.a(var9[1]);
                                             }
                                             break;
                                          case STRING:
                                             if (!var11.f().equalsIgnoreCase("label")) {
                                                var11.a(var9[1].replaceAll("&([abcdefghijklmrABCDEFGHIJKLMNR0-9])|(&$)", "§$1"));
                                             }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  } catch (Exception var19) {
                     var19.printStackTrace();
                  }
               }

               var6.close();
               if (!var8) {
                  for (com.cheatbreaker.client.module.Module var30 : (Iterable<com.cheatbreaker.client.module.Module>) var2) {
                     if (var30 instanceof com.cheatbreaker.client.module.modules.ChatModule) {
                        var30.a(var30.defaultGuiAnchor);
                        var30.a(var30.defaultXTranslation, var30.defaultYTranslation);
                        var30.f(var30.defaultRenderHud);
                     }
                  }

                  this.b(var1);
               }
            } catch (IOException var20) {
               var20.printStackTrace();
            }
         } else {
            this.b(var1);
         }
      }
   }

   public void b(String var1) {
      if (!var1.equalsIgnoreCase("default")) {
         File var2 = new File(configDir + File.separator + "profiles");
         File var3 = !var2.exists() && !var2.mkdirs() ? null : new File(var2 + File.separator + var1 + ".cfg");
         if (var3 == null) {
            System.err.println("[KC] Config manager panic!");
         } else {
            ArrayList var4 = new ArrayList<>(com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.modules);
            var4.removeIf(var0 -> var0 instanceof cn.kazer.client189.animation.LegacyVisualsModule);

            try {
               BufferedWriter var5 = new BufferedWriter(new FileWriter(var3));
               var5.write("################################");
               var5.newLine();
               var5.write("# Kazer Client: MODULE SETTINGS");
               var5.newLine();
               var5.write("################################");
               var5.newLine();
               var5.newLine();

               for (com.cheatbreaker.client.module.Module var7 : (Iterable<com.cheatbreaker.client.module.Module>) var4) {
                  var5.write("[" + var7.h() + "]");
                  var5.newLine();
                  var5.write("-State=" + var7.g());
                  var5.newLine();
                  var5.write("-xTranslation=" + var7.k());
                  var5.newLine();
                  var5.write("-yTranslation=" + var7.l());
                  var5.newLine();
                  if (var7.r() != null) {
                     var5.write("-Anchor=" + var7.r().a());
                     var5.newLine();
                  }

                  var5.write("-RenderHUD=" + var7.q());
                  var5.newLine();

                  for (com.cheatbreaker.client.settings.Setting var9 : var7.m()) {
                     if (!var9.f().equalsIgnoreCase("label")) {
                        if (var9.l() == com.cheatbreaker.client.settings.Setting.SettingType.STRING) {
                           var5.write(var9.f() + "=" + (var9.d() + "").replaceAll("§", "&"));
                        } else if (var9.rainbow) {
                           var5.write(var9.f() + "=" + var9.d() + ";rainbow");
                        } else {
                           var5.write(var9.f() + "=" + var9.d());
                        }

                        var5.newLine();
                     }
                  }

                  if (var7 instanceof com.cheatbreaker.client.module.modules.ItemCountModule) {
                     var5.write("-ItemCountState=" + ((com.cheatbreaker.client.module.modules.ItemCountModule)var7).C());
                     var5.newLine();
                  }

                  if (var7 instanceof com.cheatbreaker.client.module.modules.config.CustomTextModule) {
                     var5.write("-CustomTextState=" + ((com.cheatbreaker.client.module.modules.config.CustomTextModule)var7).C());
                     var5.newLine();
                  }

                  if (var7 instanceof com.cheatbreaker.client.module.modules.keybinds.AutoTextHotkeyModule) {
                     var5.write("-AutoTextHotkeyState=" + ((com.cheatbreaker.client.module.modules.keybinds.AutoTextHotkeyModule)var7).A());
                     var5.newLine();
                  }

                  var5.newLine();
               }

               var5.close();
            } catch (IOException var10) {
               var10.printStackTrace();
            }
         }
      }
   }
}
