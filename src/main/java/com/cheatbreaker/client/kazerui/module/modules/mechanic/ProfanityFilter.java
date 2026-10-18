package com.cheatbreaker.client.kazerui.module.modules.mechanic;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;

final class ProfanityFilter {
   private static List<Pattern> normal;
   private static List<Pattern> high;
   private static Pattern normalWords;
   private static Pattern highWords;
   private List<Pattern> patterns;
   private Pattern custom = Pattern.compile("(?!)");
   private static long resourceEpoch = Long.MIN_VALUE;
   private long selectedEpoch = Long.MIN_VALUE;
   private String playerName;
   private Pattern playerPattern;
   private Future<com.cheatbreaker.client.kazerui.module.modules.mechanic.ProfanityFilter.a> pending;
   private boolean loadSucceeded;
   private static final Pattern EMPTY = Pattern.compile("(?!)");
   private static final int MAX_CUSTOM_BYTES = 65536;
   private static final ThreadPoolExecutor FILES = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(2), var0 -> {
      Thread var1 = new Thread(var0, "Kazer-Chat-Filter");
      var1.setDaemon(true);
      return var1;
   }, new AbortPolicy());

   void a(File var1, com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.b var2) {
      if (this.pending != null) {
         this.pending.cancel(true);
      }

      FILES.purge();
      this.custom = EMPTY;
      this.loadSucceeded = false;
      this.b(var2);

      try {
         this.pending = FILES.submit(() -> a(var1));
      } catch (RejectedExecutionException var4) {
         this.pending = CompletableFuture.completedFuture(new com.cheatbreaker.client.kazerui.module.modules.mechanic.ProfanityFilter.a(EMPTY, false));
      }
   }

   boolean a(com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.b var1) {
      if (this.pending != null && this.pending.isDone()) {
         try {
            com.cheatbreaker.client.kazerui.module.modules.mechanic.ProfanityFilter.a var2 = this.pending.get();
            this.custom = var2.pattern;
            this.loadSucceeded = var2.success;
         } catch (Exception var3) {
            this.custom = EMPTY;
            this.loadSucceeded = false;
         }

         this.pending = null;
         this.b(var1);
         return true;
      } else {
         return false;
      }
   }

   boolean a() {
      return this.loadSucceeded;
   }

   private static synchronized void b() {
      long var0 = com.cheatbreaker.client.kazerui.RenderEpoch.a();
      if (normal == null || resourceEpoch != var0) {
         resourceEpoch = var0;
         highWords = null;
         normalWords = null;
         normal = new ArrayList<>();
         high = new ArrayList<>();

         try (InputStream var2 = Minecraft.getMinecraft()
               .getResourceManager()
               .getResource(new ResourceLocation("kazerui", "profanity/profanity_filter.json"))
               .getInputStream()) {
            JsonObject var4 = new JsonParser().parse(new InputStreamReader(var2, StandardCharsets.UTF_8)).getAsJsonObject();
            JsonObject var5 = var4.getAsJsonObject("regex");
            JsonObject var6 = var4.getAsJsonObject("words");

            for (JsonElement var8 : var5.getAsJsonArray("normal")) {
               normal.add(Pattern.compile(var8.getAsString(), 2));
            }

            for (JsonElement var21 : var5.getAsJsonArray("high")) {
               high.add(Pattern.compile(var21.getAsString(), 2));
            }

            normalWords = Pattern.compile("\\b(" + a(var6.getAsJsonArray("normal")) + ")\\b", 2);
            highWords = Pattern.compile("\\b(" + a(var6.getAsJsonArray("high")) + ")\\b", 2);
         } catch (Exception var19) {
            LogManager.getLogger("KazerForge").warn("Could not find profanity filter json file: kazer:profanity/profanity_filter.json");
         }
      }
   }

   private static String a(JsonArray var0) {
      StringBuilder var1 = new StringBuilder();

      for (JsonElement var3 : var0) {
         if (var1.length() > 0) {
            var1.append('|');
         }

         var1.append(var3.getAsString());
      }

      return var1.toString();
   }

   static boolean a(String var0) {
      b();

      for (Pattern var2 : high) {
         if (var2.matcher(var0).find()) {
            return true;
         }
      }

      return highWords != null && highWords.matcher(var0).find();
   }

   boolean a(com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule var1, com.cheatbreaker.client.kazerui.module.ChatMessageEvent var2) {
      if (this.selectedEpoch != com.cheatbreaker.client.kazerui.RenderEpoch.a()) {
         this.b(var1.profanity.j());
      }

      com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.a var3 = var1.chatNameColor.j();
      boolean var4 = var1.chatNameBold.b();
      boolean var5 = var1.chatNameItalic.b();
      boolean var6 = var1.chatNameUnderline.b();
      boolean var7 = var1.chatNameStrikethrough.b();
      boolean var8 = var1.chatNameObfuscated.b();
      if (var3 != com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.a.OFF || var4 || var5 || var6 || var7 || var8) {
         String var9 = Minecraft.getMinecraft().thePlayer.getName();
         IChatComponent var10 = var2.a();
         if (!var9.equals(this.playerName)) {
            this.playerName = var9;
            this.playerPattern = Pattern.compile(Pattern.quote(var9));
         }

         IChatComponent var11 = com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTextUtils.a(var10, this.playerPattern, (var6x, var7x) -> {
            ChatStyle var8x = com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTextUtils.b(var7x);
            if (var3.formatting != null) {
               var8x.setColor(var3.formatting);
            }

            if (var4) {
               var8x.setBold(true);
            }

            if (var5) {
               var8x.setItalic(true);
            }

            if (var6) {
               var8x.setUnderlined(true);
            }

            if (var7) {
               var8x.setStrikethrough(true);
            }

            if (var8) {
               var8x.setObfuscated(true);
            }

            ChatComponentText var9x = new ChatComponentText(var6x);
            var9x.setChatStyle(var8x);
            return var9x;
         });
         if (var11 != var10) {
            var2.a(var11);
         }
      }

      IChatComponent var12 = this.a(var2.a());
      if (var12 != null) {
         var2.filtered = true;
         var2.a(var12);
      }

      if (var2.filtered && var1.stopProfaneMessages.b()) {
         var2.cancelled = true;
         return true;
      } else {
         return false;
      }
   }

   private IChatComponent a(IChatComponent var1) {
      if (this.patterns == null) {
         return null;
      } else {
         IChatComponent var2 = var1;

         for (Pattern var4 : this.patterns) {
            var2 = com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTextUtils.a(var2, var4, "*");
         }

         return var2 == var1 ? null : var2;
      }
   }

   private static List<Pattern> a(List<Pattern> var0) {
      ArrayList var1 = new ArrayList();

      for (Pattern var3 : var0) {
         if (!var3.toString().isEmpty() && !var3.toString().contains("()")) {
            var1.add(var3);
         }
      }

      return var1;
   }

   void b(com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.b var1) {
      this.selectedEpoch = com.cheatbreaker.client.kazerui.RenderEpoch.a();
      if (var1 == com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.b.NORMAL
         || var1 == com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.b.HIGH) {
         b();
      }

      ArrayList var2;
      switch (var1) {
         case NORMAL:
            var2 = new ArrayList<>(normal);
            if (normalWords != null) {
               var2.add(normalWords);
            }

            var2.add(this.custom);
            break;
         case HIGH:
            var2 = new ArrayList<>(high);
            if (highWords != null) {
               var2.add(highWords);
            }

            var2.add(this.custom);
            break;
         case CUSTOM:
            var2 = new ArrayList();
            var2.add(this.custom);
            break;
         default:
            var2 = null;
      }

      this.patterns = var2 == null ? null : a(var2);
   }

   void b(File var1, com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule.b var2) {
      com.cheatbreaker.client.kazerui.module.modules.mechanic.ProfanityFilter.a var3 = a(var1);
      this.custom = var3.pattern;
      this.loadSucceeded = var3.success;
      this.b(var2);
   }

   static String a(InputStream var0) throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      byte[] var2 = new byte[4096];
      int var4 = 0;

      int var3;
      while ((var3 = var0.read(var2)) != -1) {
         var4 += var3;
         if (var4 > 65536) {
            throw new IOException("Custom chat filter exceeds 64 KiB");
         }

         if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedIOException("Filter loading cancelled");
         }

         var1.write(var2, 0, var3);
      }

      return new String(var1.toByteArray(), StandardCharsets.UTF_8);
   }

   private static com.cheatbreaker.client.kazerui.module.modules.mechanic.ProfanityFilter.a a(File var0) {
      try {
         if (!var0.exists()) {
            if (var0.getParentFile() != null) {
               var0.getParentFile().mkdirs();
            }

            var0.createNewFile();
         }

         if (var0.length() > 65536L) {
            throw new IOException("Custom chat filter exceeds 64 KiB");
         } else {
            String var1;
            try (FileInputStream var2 = new FileInputStream(var0)) {
               var1 = a(var2);
            }

            StringBuilder var16 = new StringBuilder();

            for (String var6 : var1.split("\\r?\\n")) {
               if (!var6.trim().isEmpty()) {
                  if (var16.length() > 0) {
                     var16.append('|');
                  }

                  var16.append('(').append(Pattern.quote(var6)).append(')');
               }
            }

            return new com.cheatbreaker.client.kazerui.module.modules.mechanic.ProfanityFilter.a(var16.length() == 0 ? EMPTY : Pattern.compile("(" + var16 + ")", 2), true);
         }
      } catch (Exception var15) {
         LogManager.getLogger("KazerForge").warn("Loading ChatMod", var15);
         return new com.cheatbreaker.client.kazerui.module.modules.mechanic.ProfanityFilter.a(EMPTY, false);
      }
   }

   private static final class a {
      final Pattern pattern;
      final boolean success;

      a(Pattern var1, boolean var2) {
         this.pattern = var1;
         this.success = var2;
      }
   }
}
