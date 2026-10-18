package com.cheatbreaker.client.kazerui.module.modules.mechanic;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

final class HoverImagePreview {
   private static final ThreadPoolExecutor DOWNLOADER = new ThreadPoolExecutor(1, 1, 30L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(4), var0 -> {
      Thread var1 = new Thread(var0, "Kazer chat image downloader");
      var1.setDaemon(true);
      return var1;
   });
   private static final ResourceLocation TEXTURE = new ResourceLocation("kazerui", "hover_image");
   private static final Pattern URL_PATTERN = Pattern.compile("https://[^\\s\"']+");
   private final LoadingCache<String, Future<BufferedImage>> cache = CacheBuilder.newBuilder()
      .maximumSize(10L)
      .expireAfterAccess(5L, TimeUnit.MINUTES)
      .build(new CacheLoader<String, Future<BufferedImage>>() {
         public Future<BufferedImage> load(String var1) {
            ArrayList var2 = new ArrayList<>(HoverImagePreview.this.customHosts);

            try {
               return com.cheatbreaker.client.kazerui.module.modules.mechanic.HoverImagePreview.DOWNLOADER.submit(() -> {
                  try {
                     return com.cheatbreaker.client.kazerui.module.modules.mechanic.ImageFetcher.a(var1, var2);
                  } catch (Exception var3) {
                     return null;
                  }
               });
            } catch (RejectedExecutionException var4) {
               return CompletableFuture.completedFuture(null);
            }
         }
      });
   private final List<String> customHosts = new ArrayList<>();
   private final List<String> urls = new ArrayList<>(2);
   private DynamicTexture texture;
   private Future<BufferedImage> image;
   private String url;
   private boolean showing;
   private boolean shiftDown;
   private boolean expanded;
   private long resourceEpoch = -1L;
   private long animationStart;
   private float expansion;
   private float fromExpansion;
   private int cycle;

   void a(String[] var1) {
      this.customHosts.clear();

      for (String var5 : var1) {
         if (!var5.trim().isEmpty() && this.customHosts.size() < 32) {
            this.customHosts.add(var5.trim().toLowerCase(Locale.ROOT));
         }
      }
   }

   void a(com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule var1, int var2, int var3, int var4, int var5) {
      if (var1.hoverImagePreview.b() && this.showing && this.image != null && this.image.isDone()) {
         if (!(Minecraft.getMinecraft().currentScreen instanceof GuiChat)) {
            this.a();
         } else {
            try {
               BufferedImage var6 = this.image.get();
               if (var6 == null) {
                  return;
               }

               long var7 = com.cheatbreaker.client.kazerui.RenderEpoch.a();
               if (var7 != this.resourceEpoch) {
                  this.d();
                  this.resourceEpoch = var7;
               }

               double var9 = var6.getHeight();
               double var11 = var6.getWidth();
               double var13 = var1.minImageSize.b();
               double var15 = var1.maxImageSize.b();
               boolean var17 = Keyboard.isKeyDown(42);
               long var18 = System.nanoTime();
               if (var17 && !this.shiftDown && var1.fullscreenImage.b() && var15 < 100.0) {
                  this.expanded = !this.expanded;
                  this.fromExpansion = this.expansion;
                  this.animationStart = var18;
               }

               this.shiftDown = var17;
               float var20 = Math.min(1.0F, (float)(var18 - this.animationStart) / 2.5E8F);
               this.expansion = this.fromExpansion + ((this.expanded ? 1 : 0) - this.fromExpansion) * (1.0F - (1.0F - var20) * (1.0F - var20));
               if (var1.fullscreenImage.b()) {
                  var15 += (100.0 - var15) * this.expansion;
               }

               var13 = Math.min(var13, var15);
               double var21 = var11 / var9;
               if (var9 / var5 > var11 / var4) {
                  var9 = a(var9, var13 / 100.0 * var5, var15 / 100.0 * var5);
                  var11 = var9 * var21;
               } else {
                  var11 = a(var11, var13 / 100.0 * var4, var15 / 100.0 * var4);
                  var9 = var11 / var21;
               }

               double var23 = Math.max(0.0, Math.min(var4 - var11, (double)var2));
               double var25 = Math.max(0.0, Math.min(var5 - var9, var3 - var9));
               if (this.texture == null) {
                  this.texture = new DynamicTexture(var6);
                  Minecraft.getMinecraft().getTextureManager().loadTexture(TEXTURE, this.texture);
               }

               GlStateManager.pushMatrix();

               try {
                  GlStateManager.translate(0.0F, 0.0F, 200.0F);
                  com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(TEXTURE, (float)var23, (float)var25, (float)var11, (float)var9, -1);
               } finally {
                  GlStateManager.popMatrix();
               }
            } catch (Exception var31) {
               this.a();
            }
         }
      }
   }

   private static double a(double var0, double var2, double var4) {
      return Math.max(var2, Math.min(var4, var0));
   }

   private static String a(String var0) {
      return var0.startsWith("https://imgur.com/") ? var0.replace("https://imgur.com/", "https://i.imgur.com/") + ".png" : var0;
   }

   boolean a(com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTweaksModule var1, String var2, IChatComponent var3) {
      if (!var1.hoverImagePreview.b()) {
         return false;
      } else {
         this.urls.clear();
         if (var2 != null) {
            this.urls.add(a(var2));
         } else if (var3 != null) {
            Matcher var4 = URL_PATTERN.matcher(com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatTextUtils.a(var3));

            while (this.urls.size() < 16 && var4.find()) {
               this.urls.add(a(var4.group()));
            }
         }

         if (this.urls.isEmpty()) {
            this.a();
            return false;
         } else {
            String var7 = this.urls.get(Math.floorMod(this.cycle, this.urls.size()));

            try {
               if (!com.cheatbreaker.client.kazerui.module.modules.mechanic.ImageFetcher.a(new URL(var7), this.customHosts)) {
                  this.a();
                  return false;
               } else {
                  Future var5 = (Future)this.cache.get(var7);
                  if (var5.isDone() && var5.get() == null) {
                     this.a();
                     return false;
                  } else if (this.showing && var7.equals(this.url)) {
                     return true;
                  } else if (this.urls.size() > 1 && this.showing && this.image != null && this.image.isDone() && !var5.isDone()) {
                     return true;
                  } else {
                     this.d();
                     this.image = var5;
                     this.showing = true;
                     this.url = var7;
                     return true;
                  }
               }
            } catch (Exception var6) {
               this.a();
               return false;
            }
         }
      }
   }

   void a(int var1, boolean var2) {
      if (!var2 && var1 == 29) {
         this.cycle = this.cycle == Integer.MAX_VALUE ? 0 : this.cycle + 1;
      }
   }

   private void d() {
      if (this.texture != null) {
         Minecraft.getMinecraft().getTextureManager().deleteTexture(TEXTURE);
         this.texture = null;
      }
   }

   void a() {
      this.d();
      this.showing = false;
      this.image = null;
      this.url = null;
      this.expanded = this.shiftDown = false;
      this.expansion = this.fromExpansion = 0.0F;
      this.animationStart = 0L;
   }

   void b() {
      this.a();

      for (Future var2 : this.cache.asMap().values()) {
         if (!var2.isDone()) {
            var2.cancel(true);
         }
      }

      this.cache.invalidateAll();
   }
}
