package com.cheatbreaker.client.kazerui.module;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.scoreboard.ScoreObjective;
import org.apache.commons.lang3.text.WordUtils;

public final class HypixelUtils {
   private static final Pattern BRAND_PATTERN = Pattern.compile("(.+) (?:<- .+)?");
   private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)§[0-9A-FK-OR]");

   private HypixelUtils() {
   }

   public static boolean a() {
      EntityPlayerSP var0 = Minecraft.getMinecraft().thePlayer;
      if (var0 == null) {
         return false;
      } else {
         String var1 = var0.getClientBrand();
         if (var1 == null) {
            return false;
         } else {
            Matcher var2 = BRAND_PATTERN.matcher(var1);
            return var2.find() && var2.group(1).startsWith("Hypixel BungeeCord");
         }
      }
   }

   public static String b() {
      Minecraft var0 = Minecraft.getMinecraft();
      if (var0.thePlayer == null || var0.getCurrentServerData() == null || var0.theWorld == null) {
         return null;
      } else if (!a()) {
         return null;
      } else {
         ScoreObjective var1 = var0.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
         if (var1 == null) {
            return null;
         } else {
            String var2 = STRIP_COLOR_PATTERN.matcher(var1.getDisplayName()).replaceAll("");
            if (var2.equalsIgnoreCase("skyblock co-op")) {
               var2 = "SKYBLOCK";
            }

            return WordUtils.capitalize(var2.toLowerCase(), null);
         }
      }
   }

   public static boolean c() {
      return "Skyblock".equals(b());
   }

   public static String a(String var0) {
      return var0 == null ? null : STRIP_COLOR_PATTERN.matcher(var0).replaceAll("");
   }
}
