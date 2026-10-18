package com.cheatbreaker.client.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

public final class PresenceClient {
   public static final String ENDPOINT = "http://45.125.44.66:10034/v1/presence";
   public static final UUID OWNER_UUID = com.cheatbreaker.client.util.NameColorUtils.CANRX_UUID;
   private static final long REFRESH_MS = 10000L;
   private final ExecutorService executor = Executors.newSingleThreadExecutor(var0 -> {
      Thread var1 = new Thread(var0, "Kazer Presence");
      var1.setDaemon(true);
      return var1;
   });
   private final AtomicBoolean requestRunning = new AtomicBoolean();
   private volatile Map<UUID, Integer> sameServerUsers = Collections.emptyMap();
   private volatile Map<UUID, List<com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry>> sameServerCosmetics = Collections.emptyMap();
   private volatile String localPlayerId;
   private volatile String activeServer;
   private volatile UUID localPlayerUuid;
   private String lastSessionId;
   private volatile long lastSuccess;
   private volatile long nextRefresh;
   private long nextPermissionRefresh;
   private final AtomicBoolean permissionRunning = new AtomicBoolean();
   private volatile a accountPalette;

   public void a() {
      Minecraft var1 = Minecraft.getMinecraft();
      String var2 = var1.getSession().getPlayerID();
      if (!Objects.equals(var2, this.lastSessionId)) {
         String var3 = a(var2);
         this.lastSessionId = var2;
         this.localPlayerId = var3;
         this.localPlayerUuid = var3 == null ? null : b(var3);
         this.nextPermissionRefresh = 0L;
      }

      long var11 = System.currentTimeMillis();
      if (this.localPlayerId != null && var11 >= this.nextPermissionRefresh && this.permissionRunning.compareAndSet(false, true)) {
         this.nextPermissionRefresh = var11 + 60000L;
         String var5 = this.localPlayerId;
         this.executor
            .execute(
               () -> {
                  HttpURLConnection var2x = null;

                  try {
                     var2x = (HttpURLConnection)new URL(
                           "http://45.125.44.66:10034/v1/presence".replace("/v1/presence", "/v1/cosmetic-permissions?uuid=") + var5
                        )
                        .openConnection();
                     var2x.setConnectTimeout(2500);
                     var2x.setReadTimeout(2500);
                     var2x.setUseCaches(false);
                     if (var2x.getResponseCode() == 200) {
                        try (BufferedReader var3x = new BufferedReader(new InputStreamReader(var2x.getInputStream(), StandardCharsets.UTF_8))) {
                           Integer var5x = com.cheatbreaker.client.util.NameColorUtils.a(var2x.getHeaderField("X-Kazer-Badge-Color"));
                           if (var5x != null) {
                              this.accountPalette = new a(var5, var5x);
                           }

                           String var6x = var3x.readLine();
                           if (var6x != null && var6x.matches("[0-9]{1,2}")) {
                              com.cheatbreaker.client.kazerui.concurrent.CosmeticEntitlements.a(var5, Integer.parseInt(var6x));
                           }
                        }
                     }
                  } catch (Exception var25) {
                  } finally {
                     if (var2x != null) {
                        var2x.disconnect();
                     }

                     this.permissionRunning.set(false);
                  }
               }
            );
      }

      ServerData var12 = var1.getCurrentServerData();
      if (var1.theWorld != null && var1.thePlayer != null && var12 != null && var12.serverIP != null && !var12.serverIP.trim().isEmpty()) {
         long var6 = System.currentTimeMillis();
         String var8 = var12.serverIP.trim().toLowerCase(Locale.ROOT);
         if (!var8.equals(this.activeServer)) {
            this.activeServer = var8;
            this.sameServerUsers = Collections.emptyMap();
            this.sameServerCosmetics = Collections.emptyMap();
            this.nextRefresh = 0L;
         }

         if (var6 - this.lastSuccess > 25000L) {
            this.sameServerUsers = Collections.emptyMap();
            this.sameServerCosmetics = Collections.emptyMap();
         }

         if (var6 >= this.nextRefresh && this.requestRunning.compareAndSet(false, true)) {
            this.nextRefresh = var6 + 10000L;
            String var9 = a(var1.getSession().getPlayerID());
            if (var9 == null) {
               this.requestRunning.set(false);
            } else {
               this.executor.execute(() -> this.a(var9, var8));
            }
         }
      } else {
         this.sameServerUsers = Collections.emptyMap();
         this.sameServerCosmetics = Collections.emptyMap();
         this.activeServer = null;
         this.nextRefresh = 0L;
      }
   }

   public boolean a(UUID var1) {
      return var1 == null ? false : var1.equals(this.localPlayerUuid) || this.sameServerUsers.containsKey(var1);
   }

   public boolean b(UUID var1) {
      return OWNER_UUID.equals(var1);
   }

   public List<com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry> c(UUID var1) {
      List var2 = this.sameServerCosmetics.get(var1);
      return var2 == null ? Collections.emptyList() : var2;
   }

   public int d(UUID var1) {
      if (var1 == null) {
         return 16777215;
      } else {
         Integer var2 = this.sameServerUsers.get(var1);
         return var2 == null ? com.cheatbreaker.client.util.NameColorUtils.a(var1) : var2;
      }
   }

   public int b() {
      a var1 = this.accountPalette;
      return var1 != null && var1.id.equals(this.localPlayerId) ? var1.color : this.d(this.localPlayerUuid);
   }

   private void a(String var1, String var2) {
      try {
         if (!this.a(var1, var2, "3") && !this.a(var1, var2, "2")) {
            this.a(var1, var2, "1");
         }
      } finally {
         this.requestRunning.set(false);
      }
   }

   private boolean a(String var1, String var2, String var3) {
      HttpURLConnection var4 = null;

      boolean var56;
      try {
         String var5 = "uuid="
            + URLEncoder.encode(var1, "UTF-8")
            + "&server="
            + URLEncoder.encode(var2, "UTF-8")
            + ("3".equals(var3) ? "&cosmetics=" + URLEncoder.encode(com.cheatbreaker.client.kazerui.concurrent.EquippedCosmetics.b(), "UTF-8") : "");
         byte[] var6 = var5.getBytes(StandardCharsets.UTF_8);
         var4 = (HttpURLConnection)new URL("http://45.125.44.66:10034/v1/presence").openConnection();
         var4.setRequestMethod("POST");
         var4.setConnectTimeout(2500);
         var4.setReadTimeout(2500);
         var4.setDoOutput(true);
         var4.setUseCaches(false);
         var4.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
         var4.setRequestProperty("X-Kazer-Protocol", var3);
         var4.setFixedLengthStreamingMode(var6.length);

         try (OutputStream var7 = var4.getOutputStream()) {
            var7.write(var6);
         }

         int var55 = var4.getResponseCode();
         if (var55 != 400 || "1".equals(var3)) {
            if (var55 != 200) {
               return true;
            }

            HashMap var57 = new HashMap();
            HashMap var9 = new HashMap();

            try (BufferedReader var10 = new BufferedReader(new InputStreamReader(var4.getInputStream(), StandardCharsets.UTF_8))) {
               int var13 = 0;

               String var12;
               while ((var12 = var10.readLine()) != null && var13++ < 2048) {
                  if (var12.length() <= 200) {
                     String[] var14 = var12.split("\\t", -1);
                     com.cheatbreaker.client.util.NameColorUtils.a(var57, var14.length > 1 ? var14[0] + "\t" + var14[1] : var12);
                     if (var14.length == 3 && a(var14[0]) != null) {
                        var9.put(b(a(var14[0])), com.cheatbreaker.client.kazerui.concurrent.EquippedCosmetics.a(var14[2]));
                     }
                  }
               }
            }

            if (var2.equals(this.activeServer)) {
               HashMap var59 = new HashMap();

               for (Entry var61 : (Iterable<Entry>) var57.entrySet()) {
                  var59.put(b((String)var61.getKey()), var61.getValue());
               }

               this.sameServerUsers = Collections.unmodifiableMap(var59);
               this.sameServerCosmetics = Collections.unmodifiableMap(var9);
               this.lastSuccess = System.currentTimeMillis();
            }

            return true;
         }

         var56 = false;
      } catch (Exception var53) {
         return true;
      } finally {
         if (var4 != null) {
            var4.disconnect();
         }
      }

      return var56;
   }

   private static String a(String var0) {
      return com.cheatbreaker.client.util.NameColorUtils.b(var0);
   }

   private static UUID b(String var0) {
      return UUID.fromString(
         var0.substring(0, 8) + "-" + var0.substring(8, 12) + "-" + var0.substring(12, 16) + "-" + var0.substring(16, 20) + "-" + var0.substring(20)
      );
   }

   private static final class a {
      final String id;
      final int color;

      a(String var1, int var2) {
         this.id = var1;
         this.color = var2;
      }
   }
}
