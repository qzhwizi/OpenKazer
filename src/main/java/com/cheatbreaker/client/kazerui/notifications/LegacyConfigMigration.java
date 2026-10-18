package com.cheatbreaker.client.kazerui.notifications;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;

public final class LegacyConfigMigration {
   private static final Map<String, String> MODULE_IDS = new LinkedHashMap<>();
   private static final Map<String, String> OPTIONS = new HashMap<>();
   private static final Map<String, String> ANCHORS = new HashMap<>();

   private static void a(String var0, String var1) {
      MODULE_IDS.put(var0.toLowerCase(Locale.ROOT), var1);
   }

   private static void b(String var0, String var1) {
      OPTIONS.put(var0.toLowerCase(Locale.ROOT), var1);
   }

   public static String a(String var0) {
      return MODULE_IDS.get(var0.toLowerCase(Locale.ROOT));
   }

   private static void a(com.cheatbreaker.client.kazerui.gui.components.SettingsStore var0, String var1, String var2) {
      if (var0.a(var1, null) == null) {
         var0.b(var1, var2);
      }
   }

   public static void a(File var0, com.cheatbreaker.client.kazerui.gui.components.SettingsStore var1) {
      if (var0 != null && var0.isDirectory()) {
         if (!"true".equals(var1.a("migration.legacy.v1", null))) {
            try {
               File var2 = var0.getCanonicalFile();
               File var3 = new File(var2, "global.cfg");
               if (var3.isFile() && var3.getCanonicalFile().getParentFile().equals(var2)) {
                  try (InputStreamReader var4 = new InputStreamReader(new FileInputStream(var3), StandardCharsets.UTF_8)) {
                     a(var4, var1);
                  }
               }

               File var42 = new File(var2, "profiles");
               if (!var42.getCanonicalFile().getParentFile().equals(var2)) {
                  return;
               }

               File[] var43 = var42.listFiles();
               if (var43 == null) {
                  var1.b("migration.legacy.v1", "true");
                  return;
               }

               Arrays.sort(var43, Comparator.comparing(File::getName));
               File var6 = var42.getCanonicalFile();

               for (File var10 : var43) {
                  String var11 = var10.getName();
                  if (var10.isFile() && var11.endsWith(".cfg") && var10.getCanonicalFile().getParentFile().equals(var6)) {
                     String var12 = var11.substring(0, var11.length() - 4);
                     if (!var12.isEmpty() && !var12.contains("|")) {
                        if (var12.equalsIgnoreCase("default")) {
                           var12 = "Default";
                        }

                        try (InputStreamReader var13 = new InputStreamReader(new FileInputStream(var10), StandardCharsets.UTF_8)) {
                           a(var13, var12, var1);
                        }
                     }
                  }
               }

               var1.b("migration.legacy.v1", "true");
            } catch (IOException var41) {
               LogManager.getLogger().warn("[KC] Could not read legacy UI configuration; originals remain untouched", var41);
            }
         }
      }
   }

   public static void a(Reader var0, String var1, com.cheatbreaker.client.kazerui.gui.components.SettingsStore var2) throws IOException {
      if (var1 != null && !var1.isEmpty() && !var1.contains("|")) {
         LinkedHashSet var3 = new LinkedHashSet<>(Arrays.asList(var2.a("profiles", "Default").split("\\|")));
         var3.add(var1);
         var2.b("profiles", String.join("|", var3));
         BufferedReader var4 = var0 instanceof BufferedReader ? (BufferedReader)var0 : new BufferedReader(var0);
         HashSet var5 = new HashSet();
         HashMap var6 = new HashMap();
         String var8 = "";
         String var9 = null;

         String var7;
         while ((var7 = var4.readLine()) != null) {
            if (!var7.isEmpty() && !var7.startsWith("#")) {
               if (var7.startsWith("[") && var7.endsWith("]")) {
                  var8 = var7.substring(1, var7.length() - 1);
                  var9 = a(var8);
               } else {
                  int var10 = var7.indexOf(61);
                  if (var10 >= 1 && !var8.isEmpty()) {
                     String var11 = var7.substring(0, var10);
                     String var12 = var7.substring(var10 + 1);
                     a(var2, "legacy.profile." + var1 + "." + var8 + "." + var11, var12);
                     if (var9 != null) {
                        String var13 = "profile." + var1 + "." + var9 + ".";
                        Object var14 = null;
                        String var15 = var12;
                        if (var11.equals("-State")) {
                           var14 = "enabled";
                        } else if (var11.equals("-RenderHUD")) {
                           var14 = "renderHud";
                        } else if (var11.equals("-xTranslation")) {
                           var14 = "x";
                        } else if (var11.equals("-yTranslation")) {
                           var14 = "y";
                        } else if (var11.equals("-Anchor")) {
                           var14 = "anchor";
                           var15 = ANCHORS.get(var12);
                        } else if (var9.equals("motion_blur") && var11.equals("Amount")) {
                           var14 = "value";

                           try {
                              double var27 = Double.parseDouble(var12);
                              var15 = !Double.isNaN(var27) && !Double.isInfinite(var27)
                                 ? String.valueOf(Math.max(1, Math.min(10, (int)Math.round(var27 / 10.0))))
                                 : null;
                           } catch (NumberFormatException var19) {
                              var15 = null;
                           }
                        } else if (var9.equals("motion_blur") && var11.equals("Mode")) {
                           var14 = "type";
                           var15 = var12.equals("Old") ? "V1" : (var12.equals("Phosphor") ? "V2" : "V3");
                        } else {
                           var14 = OPTIONS.get(var11.toLowerCase(Locale.ROOT));
                           if (var14 != null && ((String)var14).endsWith("Color")) {
                              String[] var16 = var12.split(";", 2);

                              try {
                                 var15 = String.format(Locale.ROOT, "%08X", Integer.parseInt(var16[0]));
                              } catch (NumberFormatException var18) {
                                 var15 = null;
                              }

                              if (var15 != null && var16.length > 1 && var16[1].equalsIgnoreCase("rainbow")) {
                                 a(var2, var13 + var14 + "Chroma", "true");
                              }
                           }
                        }

                        if (var14 != null && var15 != null) {
                           if ((var14.equals("x") || var14.equals("y")) && var2.a(var13 + var14, null) == null) {
                              var5.add(var13 + var14);
                           }

                           if (var14.equals("anchor")) {
                              var6.put(var13, var12);
                           }

                           a(var2, var13 + var14, var15);
                        }
                     }
                  }
               }
            }
         }

         for (Entry var21 : (Iterable<Entry>) var6.entrySet()) {
            float var22 = 0.0F;
            float var23 = 0.0F;
            String var25 = (String)var21.getValue();
            switch (var25) {
               case "MIDDLE_BOTTOM_LEFT":
                  var22 = -30.0F;
                  break;
               case "MIDDLE_BOTTOM_RIGHT":
                  var22 = 30.0F;
                  break;
               case "RIGHT_MIDDLE":
                  var22 = 2.0F;
                  break;
               case "RIGHT_BOTTOM":
                  var22 = 2.0F;
                  var23 = 2.0F;
            }

            a(var2, var5, (String)var21.getKey() + "x", var22);
            a(var2, var5, (String)var21.getKey() + "y", var23);
         }
      } else {
         throw new IllegalArgumentException("Invalid profile name");
      }
   }

   private static void a(com.cheatbreaker.client.kazerui.gui.components.SettingsStore var0, Set<String> var1, String var2, float var3) {
      if (var3 != 0.0F && var1.contains(var2)) {
         try {
            float var4 = Float.parseFloat(var0.a(var2, "0"));
            if (!Float.isNaN(var4) && !Float.isInfinite(var4)) {
               var0.b(var2, String.valueOf(var4 + var3));
            }
         } catch (NumberFormatException var5) {
         }
      }
   }

   public static void a(Reader var0, com.cheatbreaker.client.kazerui.gui.components.SettingsStore var1) throws IOException {
      BufferedReader var2 = var0 instanceof BufferedReader ? (BufferedReader)var0 : new BufferedReader(var0);

      String var3;
      while ((var3 = var2.readLine()) != null) {
         int var4 = var3.indexOf(61);
         if (var4 >= 1 && !var3.startsWith("#")) {
            String var5 = var3.substring(0, var4);
            String var6 = var3.substring(var4 + 1);
            a(var1, "legacy.global." + var5, var6);
            if (var5.equals("FavoriteModules")) {
               for (String var10 : var6.split(",")) {
                  String var11 = a(var10.trim());
                  if (var11 != null) {
                     a(var1, "favorite." + var11, "true");
                  }
               }
            } else if (var5.equals("ActiveProfile")) {
               a(var1, "activeProfile", var6.equalsIgnoreCase("default") ? "Default" : var6);
            }
         }
      }
   }

   public static void a(com.cheatbreaker.client.kazerui.gui.components.SettingsStore var0, List<String> var1) {
      c(var0, var1);
      b(var0, var1);
      if (!"true".equals(var0.a("migration.replacements.v2", null))) {
         String[][] var2 = new String[][]{
            {"Toggle Sprint", "toggleSprint"},
            {"Toggle Sneak", "toggleSneak"},
            {"Double Tap", "doubleTap"},
            {"Fly Boost", "flyBoost"},
            {"Fly Boost Amount", "flyBoostAmount"}
         };
         String[][] var3 = new String[][]{{"Show HUD Text", "enabled"}, {"Show While Typing", "showWhileTyping"}};

         for (String var5 : var1) {
            String var6 = "legacy.profile." + var5 + ".ToggleSprint.";
            String var7 = "profile." + var5 + ".toggle_sneak.";
            String var8 = "profile." + var5 + ".toggle_sneak_hud_child.";

            for (String[] var12 : var2) {
               b(var0, var6 + var12[0], var7 + var12[1]);
            }

            for (String[] var25 : var3) {
               b(var0, var6 + var25[0], var8 + var25[1]);
            }

            String var18 = var0.a(var6 + "Fly String", null);
            if (var18 != null) {
               a(var0, var8 + "flyingText", b(var18));
            }

            for (String var13 : new String[]{"x", "y", "anchor", "scale", "textColor", "textColorChroma", "renderHud"}) {
               b(var0, var7 + var13, var8 + var13);
            }

            String var21 = "profile." + var5 + ".motion_blur.";
            String var24 = "legacy.profile." + var5 + ".Motion Blur.";
            String var27 = var0.a(var24 + "Mode", null);
            if (var27 != null) {
               a(var0, var21 + "type", var27.equals("Old") ? "V1" : (var27.equals("Phosphor") ? "V2" : "V3"));
            }

            String var28 = var0.a(var24 + "Amount", null);
            if (var28 != null) {
               try {
                  double var14 = Double.parseDouble(var28);
                  if (Double.isFinite(var14)) {
                     a(var0, var21 + "value", String.valueOf(Math.max(1, Math.min(10, (int)Math.round(var14 / 10.0)))));
                  }
               } catch (NumberFormatException var16) {
               }
            }
         }

         var0.b("migration.replacements.v2", "true");
      }
   }

   private static void b(com.cheatbreaker.client.kazerui.gui.components.SettingsStore var0, String var1, String var2) {
      String var3 = var0.a(var1, null);
      if (var3 != null) {
         a(var0, var2, var3);
      }
   }

   private static void a(com.cheatbreaker.client.kazerui.gui.components.SettingsStore var0, String var1, String var2, String[][] var3) {
      for (String[] var7 : var3) {
         b(var0, var1 + var7[0], var2 + var7[1]);
      }
   }

   private static void c(com.cheatbreaker.client.kazerui.gui.components.SettingsStore var0, String var1, String var2) {
      String var3 = var0.a(var1, null);
      if (var3 != null) {
         String[] var4 = var3.split(";", 2);

         try {
            a(var0, var2, String.format(Locale.ROOT, "%08X", Integer.parseInt(var4[0])));
            if (var4.length > 1 && var4[1].equalsIgnoreCase("rainbow")) {
               a(var0, var2 + "Chroma", "true");
            }
         } catch (NumberFormatException var6) {
         }
      }
   }

   private static void a(com.cheatbreaker.client.kazerui.gui.components.SettingsStore var0, String var1, String var2, String... var3) {
      String var4 = var0.a(var1, null);
      if (var4 != null) {
         for (byte var5 = 0; var5 < var3.length; var5 += 2) {
            if (var4.equalsIgnoreCase(var3[var5])) {
               a(var0, var2, var3[var5 + 1]);
               return;
            }
         }
      }
   }

   private static void b(com.cheatbreaker.client.kazerui.gui.components.SettingsStore var0, List<String> var1) {
      if (!"true".equals(var0.a("migration.hud.v1", null))) {
         for (String var3 : var1) {
            String var4 = "legacy.profile." + var3 + ".";
            String var5 = "profile." + var3 + ".";
            a(
               var0,
               var4 + "CPS.",
               var5 + "cps.",
               new String[][]{{"Right Click CPS", "rightClick"}, {"Reverse Text", "reverseText"}, {"Ignore Cancelled Clicks", "ignoreCancelledClicks"}}
            );
            a(
               var0,
               var4 + "Scoreboard.",
               var5 + "scoreboard.",
               new String[][]{{"Hide Numbers", "numbers"}, {"Hide Scoreboard", "hideScoreboard"}, {"Display Toggle Message", "displayToggleMessage"}}
            );
            c(var0, var4 + "Scoreboard.Background Color", var5 + "scoreboard.headerColor");
            a(
               var0,
               var4 + "Boss bar.",
               var5 + "bossbar.",
               new String[][]{
                  {"Render Health", "renderBar"},
                  {"Render Text", "renderBossText"},
                  {"Use Custom Boss Bar", "customBossBar"},
                  {"Use Minecraft GUI Scale", "useMinecraftGUIScale"}
               }
            );
            a(
               var0,
               var4 + "Potion Effects.",
               var5 + "potion_effects.",
               new String[][]{
                  {"Show While Typing", "showWhileTyping"},
                  {"Show Effect Name", "effectName"},
                  {"Show Effect Duration", "effectDuration"},
                  {"Show Effect Amplifier", "effectAmplifier"},
                  {"Formatted Durations", "formattedDurations"},
                  {"Uppercase Potion Names", "uppercasePotionNames"},
                  {"Reversed Text", "reversedText"},
                  {"Show In Inventory", "showInInventory"},
                  {"Hide Potion Status", "hidePotionStatus"},
                  {"Effect Duration Bars", "effectBars"},
                  {"Blink", "potionBlink"},
                  {"Blink Duration", "blinkDuration"},
                  {"Color Name Based on Effect", "colorNameBasedOnEffect"},
                  {"Color Info Based on Effect", "colorInfoBasedOnEffect"}
               }
            );
            c(var0, var4 + "Potion Effects.Name Color", var5 + "potion_effects.textColor");
            c(var0, var4 + "Potion Effects.Duration Color", var5 + "potion_effects.infoColor");
            a(var0, var4 + "Potion Effects.Mode", var5 + "potion_effects.potionEffectsMode", "Normal", "NORMAL", "Compact", "MINIMAL");
            a(
               var0,
               var4 + "Key Strokes.",
               var5 + "keystrokes.",
               new String[][]{
                  {"Show clicks", "keyStrokesClicks"},
                  {"Show movement keys", "keyStrokesMovement"},
                  {"Show spacebar", "keyStrokesSpacebar"},
                  {"Replace names with arrows", "useArrows"},
                  {"Animate", "animate"},
                  {"Box size", "boxSize"},
                  {"Fade Time", "keyFadeDelay"}
               }
            );
            c(var0, var4 + "Key Strokes.Text Color (Pressed)", var5 + "keystrokes.textPressedColor");
            c(var0, var4 + "Key Strokes.Background Color (Pressed)", var5 + "keystrokes.backgroundPressedColor");
            a(var0, var4 + "Key Strokes.Mode", var5 + "keystrokes.keystrokesMode", "Grouped", "GROUPED", "Separated", "INDIVIDUAL");
            a(
               var0,
               var4 + "Armor Status.",
               var5 + "armorstatus.",
               new String[][]{
                  {"Move Armor Pieces Individually", "moveArmorIndividually"},
                  {"Vanilla Mode", "vanillaMode"},
                  {"Item Name", "itemName"},
                  {"Item Count", "itemCount"},
                  {"Show While Typing", "showWhileTyping"},
                  {"Damage Overlay", "damageOverlay"},
                  {"Show Item Damage", "itemDamage"},
                  {"Show Armor Damage", "armorDamage"},
                  {"Show Max Damage", "maxDamage"}
               }
            );
            a(var0, var4 + "Armor Status.List Mode", var5 + "armorstatus.listMode", "vertical", "VERTICAL", "horizontal", "HORIZONTAL");
            a(var0, var4 + "Armor Status.Damage Display Type", var5 + "armorstatus.damageDisplay", "value", "VALUE", "percent", "PERCENT", "none", "NONE");
            a(
               var0,
               var4 + "Armor Status.",
               var5 + "armorstatus_",
               new String[][]{
                  {"Show Held Item", "held_item_child.enabled"},
                  {"Show Helmet", "helmet_child.enabled"},
                  {"Show Chestplate", "chestplate_child.enabled"},
                  {"Show Leggings", "leggings_child.enabled"},
                  {"Show Boots", "boots_child.enabled"},
                  {"Protection", "protection_child.enabled"},
                  {"Armor Bars", "bars_child.enabled"}
               }
            );
         }

         var0.b("migration.hud.v1", "true");
      }
   }

   private static void c(com.cheatbreaker.client.kazerui.gui.components.SettingsStore var0, List<String> var1) {
      if (!"true".equals(var0.a("migration.chat.v2", null))) {
         for (String var3 : var1) {
            String var4 = "legacy.profile." + var3 + ".Chat.";
            String var5 = "profile." + var3 + ".chat.";
            b(var0, var4 + "Show Background", var5 + "showBackground");
            b(var0, var5 + "legacy_show_background", var5 + "showBackground");
            b(var0, var4 + "Show Copy Notifications", var5 + "showCopyNotifications");
            b(var0, var4 + "Show Visibility Notifications", var5 + "showVisibilityNotifications");
            a(var0, var5 + "copyChat", "true");
            a(var0, var5 + "copyChatRightClick", "false");
         }

         var0.b("migration.chat.v2", "true");
      }
   }

   public static String b(String var0) {
      return var0.length() >= 2 && var0.startsWith("[") && var0.endsWith("]") ? var0.substring(1, var0.length() - 1) : var0;
   }

   private LegacyConfigMigration() {
   }

   static {
      a("Armor Status", "armorstatus");
      a("ToggleSprint", "toggle_sneak");
      a("Toggle Sprint", "toggle_sneak");
      a("Potion Effects", "potion_effects");
      a("Key Strokes", "keystrokes");
      a("Keystrokes", "keystrokes");
      a("FPS", "fps");
      a("CPS", "cps");
      a("Nametags", "nametags");
      a("Boss bar", "bossbar");
      a("Motion Blur", "motion_blur");
      a("Scoreboard", "scoreboard");
      a("Chat", "chat");
      a("1.7 Visuals", "one_seven_visuals");
      a("Freelook", "freelook");
      b("Scale", "scale");
      b("Reverse Order", "reverseOrder");
      b("Show Background", "background");
      b("Background Color", "backgroundColor");
      b("Text Color", "textColor");
      b("Text Shadow", "textShadow");
      b("Bracket Color", "bracketColor");
      b("Show Brackets", "brackets");
      b("Right Click", "rightClick");
      b("Show CPS Text", "showCPSText");
      b("Border", "border");
      b("Border Color", "borderColor");
      b("Border Thickness", "borderThickness");
      String[] var0 = new String[]{
         "LEFT_TOP",
         "MIDDLE_TOP",
         "RIGHT_TOP",
         "LEFT_MIDDLE",
         "MIDDLE_MIDDLE",
         "RIGHT_MIDDLE",
         "LEFT_BOTTOM",
         "MIDDLE_BOTTOM_LEFT",
         "MIDDLE_BOTTOM_RIGHT",
         "RIGHT_BOTTOM"
      };
      String[] var1 = new String[]{
         "topLeft", "topCenter", "topRight", "middleLeft", "middleCenter", "middleRight", "bottomLeft", "bottomCenterLeft", "bottomCenterRight", "bottomRight"
      };

      for (int var2 = 0; var2 < var0.length; var2++) {
         ANCHORS.put(var0[var2], var1[var2]);
      }
   }
}
