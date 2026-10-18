package com.cheatbreaker.client.kazerui.concurrent;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.twelvemonkeys.imageio.plugins.webp.WebPImageReaderSpi;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public final class CosmeticTextures {
   private static final int MAX_FRAME = 256;
   private static final int CACHE = 16;
   private static final long PIXEL_BUDGET = 33554432L;
   private static final long ENTRY_PIXEL_BUDGET = 8388608L;
   private static final ExecutorService DECODE = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(16), var0 -> {
      Thread var1 = new Thread(var0, "Kazer cosmetic decode");
      var1.setDaemon(true);
      return var1;
   });
   private static final Map<String, com.cheatbreaker.client.kazerui.concurrent.CosmeticTextures.a> ENTRIES = new LinkedHashMap<>(16, 0.75F, true);
   private static final ResourceLocation CAPE = new ResourceLocation("kazerui", "cosmetics/kazer-cape.png");
   private static final ResourceLocation WINGS = new ResourceLocation("textures/entity/enderdragon/dragon.png");
   private static final ResourceLocation MICET = new ResourceLocation("kazerui", "cosmetics/micet-cape.png");

   private CosmeticTextures() {
   }

   public static ResourceLocation a(com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var0) {
      if (var0.resource.equals("builtin:cape")) {
         return CAPE;
      } else if (var0.resource.equals("builtin:wings")) {
         return WINGS;
      } else {
         return var0.resource.equals("builtin:micet") ? MICET : a(var0.resource, var0.type == com.cheatbreaker.client.kazerui.concurrent.CosmeticType.CLOAK);
      }
   }

   public static ResourceLocation a(String var0, boolean var1) {
      com.cheatbreaker.client.kazerui.concurrent.CosmeticTextures.a var2 = ENTRIES.get(var0);
      if (var2 == null) {
         var2 = new com.cheatbreaker.client.kazerui.concurrent.CosmeticTextures.a();
         ENTRIES.put(var0, var2);
         Future var3 = com.cheatbreaker.client.kazerui.concurrent.CosmeticAssetStore.b(var0);
         Future var4 = com.cheatbreaker.client.kazerui.concurrent.CosmeticAssetStore.a(var0 + ".mcmeta") ? com.cheatbreaker.client.kazerui.concurrent.CosmeticAssetStore.b(var0 + ".mcmeta") : null;

         try {
            var2.loading = DECODE.submit(() -> a(var1, (byte[])var3.get(), var4 == null ? null : (byte[])var4.get()));
         } catch (RejectedExecutionException var7) {
            ENTRIES.remove(var0);
            return null;
         }

         b(var2);
      }

      if (var2.failed) {
         if (System.nanoTime() < var2.retryAt) {
            return null;
         } else {
            ENTRIES.remove(var0);
            return a(var0, var1);
         }
      } else {
         if (var2.frames == null) {
            if (!var2.loading.isDone()) {
               return null;
            }

            try {
               var2.frames = var2.loading.get();
            } catch (Exception var6) {
               var2.frames = null;
            }

            var2.loading = null;
            if (var2.frames == null) {
               var2.failed = true;
               var2.retryAt = System.nanoTime() + 30000000000L;
               return null;
            }

            b(var2);
         }

         if (var2.texture == null) {
            var2.texture = new DynamicTexture(var2.frames.width, var2.frames.height);
            var2.location = Minecraft.getMinecraft()
               .getTextureManager()
               .getDynamicTextureLocation("kazer_cosmetic_" + Integer.toHexString(var0.hashCode()), var2.texture);
         }

         int var8 = a(
            Minecraft.getMinecraft().theWorld == null ? System.currentTimeMillis() / 50L : Minecraft.getMinecraft().theWorld.getTotalWorldTime(),
            var2.frames.frameTime,
            var2.frames.pixels.length
         );
         if (var8 != var2.shownFrame) {
            System.arraycopy(var2.frames.pixels[var8], 0, var2.texture.getTextureData(), 0, var2.frames.pixels[var8].length);
            var2.texture.updateDynamicTexture();
            var2.shownFrame = var8;
         }

         return var2.location;
      }
   }

   static int a(long var0, int var2, int var3) {
      return (int)Math.floorMod(var0 / Math.max(1, var2), (long)var3);
   }

   private static long a(com.cheatbreaker.client.kazerui.concurrent.CosmeticTextures.a var0) {
      return var0.frames == null ? 0L : (long)var0.frames.width * var0.frames.height * 4L * (var0.frames.pixels.length + 1);
   }

   private static void b(com.cheatbreaker.client.kazerui.concurrent.CosmeticTextures.a var0) {
      long var1 = 0L;

      for (com.cheatbreaker.client.kazerui.concurrent.CosmeticTextures.a var4 : ENTRIES.values()) {
         if (var4.frames == null && var4.loading != null && var4.loading.isDone()) {
            try {
               var4.frames = var4.loading.get();
            } catch (Exception var6) {
               var4.failed = true;
            }

            var4.loading = null;
            if (var4.frames == null) {
               var4.failed = true;
               var4.retryAt = System.nanoTime() + 30000000000L;
            }
         }

         var1 += a(var4);
      }

      Iterator var7 = ENTRIES.entrySet().iterator();

      while ((ENTRIES.size() > 16 || var1 > 33554432L) && var7.hasNext()) {
         com.cheatbreaker.client.kazerui.concurrent.CosmeticTextures.a var8 = (com.cheatbreaker.client.kazerui.concurrent.CosmeticTextures.a)((Entry)var7.next()).getValue();
         if (var8 != var0) {
            if (var8.location != null) {
               Minecraft.getMinecraft().getTextureManager().deleteTexture(var8.location);
            }

            if (var8.loading != null) {
               var8.loading.cancel(true);
            }

            var1 -= a(var8);
            var7.remove();
         }
      }
   }

   private static com.cheatbreaker.client.kazerui.concurrent.CosmeticTextures.b a(boolean var0, byte[] var1, byte[] var2) throws Exception {
      if (var1 == null) {
         return null;
      } else {
         BufferedImage var3 = a(var1);
         if (var3 == null) {
            return null;
         } else {
            int var4 = var3.getWidth();
            int var5 = var3.getHeight();
            int var6 = 1;
            if (var4 <= 4096 && var5 <= 4096) {
               int[] var7 = null;
               if (var2 != null) {
                  JsonObject var8 = new JsonParser().parse(new String(var2, StandardCharsets.UTF_8)).getAsJsonObject().getAsJsonObject("animation");
                  if (var8 != null) {
                     if (var8.has("frametime")) {
                        var6 = Math.max(1, var8.get("frametime").getAsInt());
                     }

                     if (var8.has("width") && var8.has("height")) {
                        var5 = Math.round(var4 * var8.get("height").getAsFloat() / var8.get("width").getAsFloat());
                     } else {
                        var5 = var4;
                     }

                     if (var8.has("frames")) {
                        if (var8.getAsJsonArray("frames").size() > 64) {
                           return null;
                        }

                        ArrayList var9 = new ArrayList();

                        for (JsonElement var11 : var8.getAsJsonArray("frames")) {
                           var9.add(var11.isJsonObject() ? var11.getAsJsonObject().get("index").getAsInt() : var11.getAsInt());
                        }

                        var7 = new int[var9.size()];

                        for (int var23 = 0; var23 < var7.length; var23++) {
                           var7[var23] = (Integer)var9.get(var23);
                        }
                     }
                  }
               }

               var5 = Math.max(1, Math.min(var5, var3.getHeight()));
               int var20 = Math.max(1, var3.getHeight() / var5);
               if (var20 <= 64 && (var7 == null || var7.length != 0 && var7.length <= 64)) {
                  if (var7 == null) {
                     var7 = new int[var20];
                     int var21 = 0;

                     while (var21 < var20) {
                        var7[var21] = var21++;
                     }
                  }

                  BufferedImage[] var22 = new BufferedImage[var20];

                  for (int var24 = 0; var24 < var20; var24++) {
                     var22[var24] = var3.getSubimage(0, var24 * var5, var4, var5);
                  }

                  int var25;
                  int var26;
                  if (var0) {
                     boolean var12 = Math.abs((float)var4 / var5 - 2.0F) < 0.01F;
                     var25 = var12 ? var4 : Math.round(var4 * 64.0F / 22.0F);
                     var26 = var12 ? var5 : Math.round(var5 * 32.0F / 17.0F);
                  } else {
                     var25 = var4;
                     var26 = var5;
                  }

                  float var27 = Math.min(1.0F, 256.0F * (var0 ? 2.909091F : 1.0F) / Math.max(var25, var26));
                  int var13 = Math.max(1, Math.round(var25 * var27));
                  int var14 = Math.max(1, Math.round(var26 * var27));
                  if ((long)var13 * var14 * 4L * (var7.length + 1) > 8388608L) {
                     return null;
                  } else {
                     for (int var18 : var7) {
                        if (var18 < 0 || var18 >= var20) {
                           return null;
                        }
                     }

                     int[][] var28 = new int[var7.length][];

                     for (int var29 = 0; var29 < var7.length; var29++) {
                        BufferedImage var30 = new BufferedImage(var13, var14, 2);
                        Graphics2D var31 = var30.createGraphics();
                        var31.setRenderingHint(
                           RenderingHints.KEY_INTERPOLATION,
                           var27 < 1.0F ? RenderingHints.VALUE_INTERPOLATION_BILINEAR : RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR
                        );
                        var31.drawImage(var22[Math.min(var7[var29], var20 - 1)], 0, 0, Math.round(var4 * var27), Math.round(var5 * var27), null);
                        var31.dispose();
                        var28[var29] = var30.getRGB(0, 0, var13, var14, null, 0, var13);
                     }

                     return new com.cheatbreaker.client.kazerui.concurrent.CosmeticTextures.b(var13, var14, var6, var28);
                  }
               } else {
                  return null;
               }
            } else {
               return null;
            }
         }
      }
   }

   static BufferedImage a(byte[] var0) throws Exception {
      try (MemoryCacheImageInputStream var1 = new MemoryCacheImageInputStream(new ByteArrayInputStream(var0))) {
         boolean var3 = var0.length > 12
            && var0[0] == 82
            && var0[1] == 73
            && var0[2] == 70
            && var0[3] == 70
            && var0[8] == 87
            && var0[9] == 69
            && var0[10] == 66
            && var0[11] == 80;
         ImageReader var4;
         if (var3) {
            var4 = new WebPImageReaderSpi().createReaderInstance(null);
         } else {
            Iterator var5 = ImageIO.getImageReaders(var1);
            if (!var5.hasNext()) {
               return null;
            }

            var4 = (ImageReader)var5.next();
         }

         try {
            var4.setInput(var1, true, true);
            int var27 = var4.getWidth(0);
            int var6 = var4.getHeight(0);
            return var27 >= 1 && var6 >= 1 && var27 <= 4096 && var6 <= 4096 && (long)var27 * var6 <= 4194304L ? var4.read(0) : null;
         } finally {
            var4.dispose();
         }
      }
   }

   private static final class a {
      volatile Future<com.cheatbreaker.client.kazerui.concurrent.CosmeticTextures.b> loading;
      com.cheatbreaker.client.kazerui.concurrent.CosmeticTextures.b frames;
      boolean failed;
      long retryAt;
      DynamicTexture texture;
      ResourceLocation location;
      int shownFrame = -1;

      private a() {
      }
   }

   static final class b {
      final int width;
      final int height;
      final int frameTime;
      final int[][] pixels;

      b(int var1, int var2, int var3, int[][] var4) {
         this.width = var1;
         this.height = var2;
         this.frameTime = var3;
         this.pixels = var4;
      }
   }
}
