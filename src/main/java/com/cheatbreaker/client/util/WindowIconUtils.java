package com.cheatbreaker.client.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import org.lwjgl.opengl.Display;

public final class WindowIconUtils {
   public static void a() {
      if (Display.isCreated()) {
         try {
            Display.setIcon(new ByteBuffer[]{a("icon-1.png"), a("icon-2.png")});
         } catch (Exception var1) {
            System.err.println("[Kazer] Could not set Minecraft window icon: " + var1.getClass().getSimpleName());
         }
      }
   }

   private static ByteBuffer a(String var0) throws Exception {
      ByteBuffer var17;
      try (InputStream var1 = WindowIconUtils.class.getResourceAsStream("/assets/minecraft/client/" + var0)) {
         if (var1 == null) {
            throw new IOException("Missing vanilla icon");
         }

         BufferedImage var3 = ImageIO.read(var1);
         if (var3 == null) {
            throw new IOException("Unreadable vanilla icon");
         }

         ByteBuffer var4 = ByteBuffer.allocateDirect(var3.getWidth() * var3.getHeight() * 4);

         for (int var5 = 0; var5 < var3.getHeight(); var5++) {
            for (int var6 = 0; var6 < var3.getWidth(); var6++) {
               int var7 = var3.getRGB(var6, var5);
               var4.put((byte)(var7 >> 16)).put((byte)(var7 >> 8)).put((byte)var7).put((byte)(var7 >> 24));
            }
         }

         ((Buffer)var4).flip();
         var17 = var4;
      }

      return var17;
   }

   private WindowIconUtils() {
   }
}
