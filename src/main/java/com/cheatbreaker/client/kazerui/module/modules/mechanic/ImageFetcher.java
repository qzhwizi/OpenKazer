package com.cheatbreaker.client.kazerui.module.modules.mechanic;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;

final class ImageFetcher {
   static final int MAX_BYTES = 4194304;
   static final int MAX_PIXELS = 1048576;
   static final List<String> HOSTS = Collections.unmodifiableList(
      Arrays.asList(
         "imgur.com",
         "i.imgur.com",
         "discordapp.com",
         "discordapp.net",
         "media.discordapp.net",
         "hypixel.net",
         "prnt.sc",
         "ibb.co",
         "wixmp.com",
         "fbcdn.net",
         "cdninstagram.com",
         "redd.it",
         "gyazo.com",
         "twimg.com",
         "mcstats.com"
      )
   );

   static boolean a(URL var0, Collection<String> var1) {
      if (var0.getProtocol().equalsIgnoreCase("https") && var0.getUserInfo() == null && (var0.getPort() == -1 || var0.getPort() == 443)) {
         String var2 = var0.getHost().toLowerCase(Locale.ROOT);

         for (String var4 : HOSTS) {
            if (a(var2, var4)) {
               return true;
            }
         }

         for (String var6 : var1) {
            if (a(var2, var6)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private static boolean a(String var0, String var1) {
      return var1.matches("[a-z0-9](?:[a-z0-9.-]*[a-z0-9])?")
         && var1.indexOf(46) > 0
         && !var1.matches("[0-9.]+")
         && (var0.equals(var1) || var0.endsWith("." + var1));
   }

   static boolean a(InetAddress var0) {
      if (!var0.isAnyLocalAddress() && !var0.isLoopbackAddress() && !var0.isLinkLocalAddress() && !var0.isSiteLocalAddress() && !var0.isMulticastAddress()) {
         byte[] var1 = var0.getAddress();
         if (var1.length == 4) {
            int var2 = var1[0] & 255;
            int var3 = var1[1] & 255;
            return var2 != 0
               && var2 != 10
               && var2 != 127
               && var2 < 224
               && (var2 != 100 || var3 < 64 || var3 > 127)
               && (var2 != 169 || var3 != 254)
               && (var2 != 172 || var3 < 16 || var3 > 31)
               && (var2 != 192 || var3 != 168);
         } else {
            return var1.length == 16 && (var1[0] & 224) == 32;
         }
      } else {
         return false;
      }
   }

   static byte[] a(InputStream var0) throws IOException {
      return a(var0, System.nanoTime() + 12000000000L);
   }

   private static byte[] a(InputStream var0, long var1) throws IOException {
      ByteArrayOutputStream var3 = new ByteArrayOutputStream(8192);
      byte[] var4 = new byte[8192];

      int var5;
      while ((var5 = var0.read(var4)) != -1) {
         if (Thread.currentThread().isInterrupted() || System.nanoTime() > var1) {
            throw new InterruptedIOException();
         }

         if (var3.size() + var5 > 4194304) {
            throw new IOException("Image response exceeds limit");
         }

         var3.write(var4, 0, var5);
      }

      return var3.toByteArray();
   }

   static BufferedImage a(byte[] var0) throws IOException {
      if (var0.length > 4194304) {
         throw new IOException("Image response exceeds limit");
      } else {
         BufferedImage var7;
         try (MemoryCacheImageInputStream var1 = new MemoryCacheImageInputStream(new ByteArrayInputStream(var0))) {
            Iterator var3 = ImageIO.getImageReaders(var1);
            if (!var3.hasNext()) {
               throw new IOException("Unsupported image");
            }

            ImageReader var4 = (ImageReader)var3.next();

            try {
               var4.setInput(var1, true, true);
               int var5 = var4.getWidth(0);
               int var6 = var4.getHeight(0);
               if (var5 <= 0 || var6 <= 0 || (long)var5 * var6 > 1048576L) {
                  throw new IOException("Image dimensions exceed limit");
               }

               var7 = var4.read(0);
            } finally {
               var4.dispose();
            }
         }

         return var7;
      }
   }

   static BufferedImage a(String var0, Collection<String> var1) throws IOException {
      URL var2 = new URL(var0);
      long var3 = System.nanoTime() + 12000000000L;

      for (int var5 = 0; var5 <= 3; var5++) {
         if (Thread.currentThread().isInterrupted() || System.nanoTime() > var3) {
            throw new InterruptedIOException();
         }

         if (!a(var2, var1)) {
            throw new IOException("Image host is not allowed");
         }

         for (InetAddress var9 : InetAddress.getAllByName(var2.getHost())) {
            if (!a(var9)) {
               throw new IOException("Non-public image host");
            }
         }

         HttpURLConnection var29 = (HttpURLConnection)var2.openConnection();
         var29.setConnectTimeout(3000);
         var29.setReadTimeout(3000);
         var29.setInstanceFollowRedirects(false);
         var29.setRequestProperty("Accept", "image/*");
         var29.setRequestProperty("User-Agent", "KazerClient-Java");

         BufferedImage var10;
         try {
            int var30 = var29.getResponseCode();
            if (var30 >= 300 && var30 < 400) {
               String var32 = var29.getHeaderField("Location");
               if (var32 == null) {
                  throw new IOException("Missing redirect target");
               }

               var2 = new URL(var2, var32);
               continue;
            }

            if (var30 != 200 || var29.getContentLengthLong() > 4194304L) {
               throw new IOException("Invalid image response");
            }

            try (InputStream var31 = var29.getInputStream()) {
               var10 = a(a(var31, var3));
            }
         } finally {
            var29.disconnect();
         }

         return var10;
      }

      throw new IOException("Too many image redirects");
   }

   private ImageFetcher() {
   }
}
