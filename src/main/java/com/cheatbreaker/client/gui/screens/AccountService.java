package com.cheatbreaker.client.gui.screens;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jna.NativeLibrary;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public final class AccountService {
   private static Session pendingSession;

   public static void a() {
      Minecraft var0 = Minecraft.getMinecraft();
      if (pendingSession != null && var0.theWorld == null) {
         cn.kazer.client189.render.ClientBridge.a(pendingSession);
         pendingSession = null;
      }
   }

   public static void a(String var0, Consumer<JsonObject> var1, Consumer<String> var2) {
      if ("/login".equals(var0)) {
         b();
      }

      Thread var3 = new Thread(() -> {
         HttpURLConnection var3x = null;

         try {
            String var4 = System.getProperty("kazer.accounts.url", "");
            String var53 = System.getProperty("kazer.accounts.secret", "");
            if (!var4.matches("http://127\\.0\\.0\\.1:[0-9]{1,5}") || !var53.matches("[a-f0-9]{64}")) {
               throw new IllegalStateException("Start this client from Kazer Launcher to manage accounts.");
            }

            var3x = (HttpURLConnection)new URL(var4 + var0).openConnection();
            var3x.setRequestMethod(var0.equals("/accounts") ? "GET" : "POST");
            var3x.setRequestProperty("Authorization", "Bearer " + var53);
            var3x.setConnectTimeout(5000);
            var3x.setReadTimeout(300000);

            try (
               InputStream var6 = var3x.getInputStream();
               InputStreamReader var8 = new InputStreamReader(var6, StandardCharsets.UTF_8);
            ) {
               JsonObject var10 = new JsonParser().parse(var8).getAsJsonObject();
               Minecraft.getMinecraft().addScheduledTask(() -> var1.accept(var10));
            }
         } catch (Exception var51) {
            String var5 = var51 instanceof IllegalStateException ? var51.getMessage() : "Login failed or was cancelled. Please try again.";
            Minecraft.getMinecraft().addScheduledTask(() -> var2.accept(var5));
         } finally {
            if (var3x != null) {
               var3x.disconnect();
            }
         }
      }, "Kazer account login");
      var3.setDaemon(true);
      var3.start();
   }

   private static void b() {
      if (System.getProperty("os.name", "").startsWith("Windows")) {
         try {
            String var0 = System.getProperty("kazer.accounts.url", "");
            String var1 = System.getProperty("kazer.accounts.secret", "");
            if (!var0.matches("http://127\\.0\\.0\\.1:[0-9]{1,5}") || !var1.matches("[a-f0-9]{64}")) {
               return;
            }

            int var2 = Integer.parseInt(System.getProperty("kazer.accounts.pid", "0"));
            if (var2 <= 0) {
               return;
            }

            NativeLibrary.getInstance("user32").getFunction("AllowSetForegroundWindow", 1).invokeInt(new Object[]{var2});
         } catch (RuntimeException var3) {
         } catch (LinkageError var4) {
         }
      }
   }

   public static String a(JsonObject var0) {
      Session var1 = new Session(var0.get("name").getAsString(), var0.get("id").getAsString(), var0.get("accessToken").getAsString(), "mojang");
      if (Minecraft.getMinecraft().theWorld == null) {
         cn.kazer.client189.render.ClientBridge.a(var1);
         return "Account added and synced to Kazer Launcher.";
      } else {
         pendingSession = var1;
         return "Account synced. It will switch after you leave this world.";
      }
   }
}
