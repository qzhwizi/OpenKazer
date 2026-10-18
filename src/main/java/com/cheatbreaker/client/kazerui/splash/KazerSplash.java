package com.cheatbreaker.client.kazerui.splash;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;

public final class KazerSplash {
   private static final Logger LOG = LogManager.getLogger("KazerSplash");
   private static final String ASSETS = "/assets/kazerui/";
   private static final long FRAME_NANOS = TimeUnit.SECONDS.toNanos(1L) / 60L;
   private static final float BAR_EASE_SECONDS = 0.15F;
   private static final float MAX_FRAME_SECONDS = 0.05F;
   private static final float LOGO_WIDTH = 128.0F;
   private static final float LOGO_HEIGHT = 117.0F;
   private static final float TEXT_SIZE = 18.0F;
   private static final float BAR_WIDTH = 300.0F;
   private static final float BAR_HEIGHT = 12.0F;
   private static final int LOGO_SHADOW = 855638016;
   private static final int BAR_TRACK = 1621139616;
   private static final int BAR_FILL = -1342177281;
   private static boolean failed;
   private static boolean finished;
   private static long lastFrame;
   private static int background = -1;
   private static int logo = -1;
   private static int text = -1;
   private static String textValue;
   private static int textWidth;
   private static int textHeight;
   private static Font font;
   private static boolean fontFailed;
   private static Object topBar;
   private static int completedStages;
   private static int expectedStages = -1;
   private static float progress;
   private static float shownProgress;
   private static long lastBarFrame;
   private static String status = "Initializing Minecraft Engine";
   private static final List<com.cheatbreaker.client.kazerui.splash.LoadingProgress.b> bars = new ArrayList<>();

   private KazerSplash() {
   }

   public static boolean a() {
      return true;
   }

   public static boolean b() {
      return finished || failed;
   }

   public static void a(String var0) {
      if (!b()) {
         status = var0;
         a(true);
      }
   }

   public static boolean c() {
      if (!failed && !finished) {
         a(true);
         return !failed;
      } else {
         return false;
      }
   }

   public static void d() {
      if (!failed && !finished) {
         try {
            if (g()) {
               a(true);
            } else {
               a(false);
            }
         } catch (Throwable var1) {
            a(var1);
         }
      }
   }

   public static void e() {
      if (!finished) {
         finished = true;
         if (topBar != null) {
            completedStages++;
         }

         if (completedStages > 0 && completedStages != expectedStages) {
            b(completedStages);
         }

         int[] var0 = new int[]{background, logo, text};

         for (int var4 : var0) {
            if (var4 != -1) {
               GL11.glDeleteTextures(var4);
            }
         }

         text = -1;
         logo = -1;
         background = -1;
         com.cheatbreaker.client.kazerui.splash.LoadingProgress.b();
         bars.clear();
         font = null;
         f();
      }
   }

   private static void a(int var0, int var1) {
   }

   private static void f() {
      try {
         Minecraft var0 = Minecraft.getMinecraft();
         if (var0.isFullScreen() || !Display.isCreated()) {
            return;
         }

         int var1 = Display.getWidth();
         int var2 = Display.getHeight();
         if (var1 > 0 && var2 > 0 && (var1 != var0.displayWidth || var2 != var0.displayHeight)) {
            a(var1, var2);
         }
      } catch (Throwable var3) {
         LOG.error("Could not apply a window resize made while loading", var3);
      }
   }

   private static boolean g() {
      bars.clear();
      Iterator var0 = com.cheatbreaker.client.kazerui.splash.LoadingProgress.a();

      while (var0.hasNext()) {
         bars.add((com.cheatbreaker.client.kazerui.splash.LoadingProgress.b)var0.next());
      }

      if (bars.isEmpty()) {
         if (topBar != null) {
            completedStages++;
            topBar = null;
         }
      } else if (bars.get(0) != topBar) {
         if (topBar != null) {
            completedStages++;
         }

         topBar = bars.get(0);
      }

      float var5 = 0.0F;

      for (int var1 = bars.size() - 1; var1 >= 0; var1--) {
         com.cheatbreaker.client.kazerui.splash.LoadingProgress.b var2 = bars.get(var1);
         int var3 = Math.max(1, var2.a());
         float var4 = var1 == bars.size() - 1 ? var2.b() : Math.max(0, var2.b() - 1) + var5;
         var5 = Math.min(1.0F, var4 / var3);
      }

      int var6 = bars.isEmpty() ? 0 : 1;
      int var7 = Math.max(i(), completedStages + var6);
      progress = Math.max(progress, Math.min(1.0F, (completedStages + var5) / var7));
      if (bars.isEmpty()) {
         return false;
      } else {
         com.cheatbreaker.client.kazerui.splash.LoadingProgress.b var8 = bars.get(0);
         String var9 = var8.c() != null && !var8.c().isEmpty() ? var8.c() : var8.d();
         if (var9 != null && !var9.equals(status)) {
            status = var9;
            return true;
         } else {
            return false;
         }
      }
   }

   private static void a(boolean var0) {
      long var1 = System.nanoTime();
      if (var0 || lastFrame == 0L || var1 - lastFrame >= FRAME_NANOS) {
         try {
            if (!Display.isCreated()) {
               return;
            }

            ContextCapabilities var3;
            try {
               var3 = GLContext.getCapabilities();
            } catch (RuntimeException var5) {
               return;
            }

            lastFrame = var1;
            a(var1);
            a(var3);
            Display.update();
         } catch (Throwable var6) {
            a(var6);
         }
      }
   }

   private static void a(long var0) {
      float var2 = lastBarFrame == 0L ? 0.0F : Math.min(0.05F, (float)(var0 - lastBarFrame) / 1.0E9F);
      lastBarFrame = var0;
      shownProgress = shownProgress + (progress - shownProgress) * (1.0F - (float)Math.exp(-var2 / 0.15F));
      if (progress - shownProgress < 0.001F) {
         shownProgress = progress;
      }
   }

   private static void a(ContextCapabilities var0) {
      int var1 = Display.getWidth();
      int var2 = Display.getHeight();
      if (var1 > 0 && var2 > 0) {
         int var3 = GL11.glGetInteger(34016);
         GL13.glActiveTexture(33984);
         GL11.glPushAttrib(1048575);
         GL11.glPushClientAttrib(-1);
         GL11.glMatrixMode(5890);
         GL11.glPushMatrix();
         GL11.glLoadIdentity();
         GL11.glMatrixMode(5889);
         GL11.glPushMatrix();
         GL11.glLoadIdentity();
         GL11.glOrtho(0.0, var1, var2, 0.0, -1.0, 1.0);
         GL11.glMatrixMode(5888);
         GL11.glPushMatrix();
         GL11.glLoadIdentity();
         int var4 = a(var0, 0);

         try {
            GL11.glViewport(0, 0, var1, var2);
            GL11.glDisable(2929);
            GL11.glDisable(2896);
            GL11.glDisable(2912);
            GL11.glDisable(2884);
            GL11.glDisable(3008);
            GL11.glDisable(3089);
            GL11.glColorMask(true, true, true, true);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glPixelStorei(3317, 4);
            GL11.glPixelStorei(3314, 0);
            GL11.glPixelStorei(3315, 0);
            GL11.glPixelStorei(3316, 0);
            GL11.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
            GL11.glClear(16384);
            if (logo == -1) {
               logo = c("ui/logo/kazer.png");
            }

            float var5 = (int)(var1 / 2.0F - 64.0F);
            float var6 = Math.round(var2 / 2.0F - 58.5F - 16.0F);
            a(logo, var5 + 1.0F, var6 + 1.0F, 128.0F, 117.0F, 855638016);
            a(logo, var5, var6, 128.0F, 117.0F, -1);
            float var7 = var2 / 2.0F + 48.0F;
            float var8 = var1 / 2.0F - 150.0F;
            float var9 = var7 + 48.0F;
            if (b(status)) {
               a(text, (float)Math.round(var1 / 2.0F - textWidth / 2.0F), (float)Math.round(var7 + 20.0F), (float)textWidth, (float)textHeight, -1);
            }

            a(var8, var9, 300.0F, 12.0F, 12.0F, 1621139616);
            if (shownProgress > 0.0F) {
               a(var8, var9, Math.max(5.0F, 300.0F * shownProgress), 12.0F, 12.0F, -1342177281);
            }
         } finally {
            a(var0, var4);
            GL11.glMatrixMode(5888);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5889);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5890);
            GL11.glPopMatrix();
            GL11.glPopClientAttrib();
            GL11.glPopAttrib();
            GL13.glActiveTexture(var3);
         }
      }
   }

   private static int a(ContextCapabilities var0, int var1) {
      if (var1 < 0) {
         return -1;
      } else if (var0.OpenGL30) {
         int var4 = GL11.glGetInteger(36006);
         GL30.glBindFramebuffer(36160, var1);
         return var4;
      } else if (var0.GL_ARB_framebuffer_object) {
         int var3 = GL11.glGetInteger(36006);
         ARBFramebufferObject.glBindFramebuffer(36160, var1);
         return var3;
      } else if (var0.GL_EXT_framebuffer_object) {
         int var2 = GL11.glGetInteger(36006);
         EXTFramebufferObject.glBindFramebufferEXT(36160, var1);
         return var2;
      } else {
         return -1;
      }
   }

   private static void a(int var0, float var1, float var2, float var3, float var4, int var5) {
      GL11.glEnable(3553);
      GL11.glBindTexture(3553, var0);
      a(var5);
      GL11.glBegin(7);
      GL11.glTexCoord2f(0.0F, 0.0F);
      GL11.glVertex2f(var1, var2);
      GL11.glTexCoord2f(0.0F, 1.0F);
      GL11.glVertex2f(var1, var2 + var4);
      GL11.glTexCoord2f(1.0F, 1.0F);
      GL11.glVertex2f(var1 + var3, var2 + var4);
      GL11.glTexCoord2f(1.0F, 0.0F);
      GL11.glVertex2f(var1 + var3, var2);
      GL11.glEnd();
   }

   private static void a(float var0, float var1, float var2, float var3, float var4, int var5) {
      float var6 = Math.min(var4, Math.min(var2, var3) / 2.0F);
      GL11.glDisable(3553);
      a(var5);
      GL11.glBegin(6);
      GL11.glVertex2f(var0 + var2 / 2.0F, var1 + var3 / 2.0F);
      a(var0, var1, var2, var3, var6);
      GL11.glEnd();
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glLineWidth(1.0F);
      GL11.glBegin(2);
      a(var0, var1, var2, var3, var6);
      GL11.glEnd();
      GL11.glDisable(2848);
   }

   private static void a(float var0, float var1, float var2, float var3, float var4) {
      byte var5 = 12;
      float[][] var6 = new float[][]{
         {var0 + var2 - var4, var1 + var4, -90.0F},
         {var0 + var2 - var4, var1 + var3 - var4, 0.0F},
         {var0 + var4, var1 + var3 - var4, 90.0F},
         {var0 + var4, var1 + var4, 180.0F}
      };

      for (float[] var10 : var6) {
         for (int var11 = 0; var11 <= 12; var11++) {
            double var12 = Math.toRadians(var10[2] + 90.0 * var11 / 12.0);
            GL11.glVertex2d(var10[0] + Math.cos(var12) * var4, var10[1] + Math.sin(var12) * var4);
         }
      }

      GL11.glVertex2f(var0 + var2 - var4, var1);
   }

   private static void a(int var0) {
      GL11.glColor4f((var0 >> 16 & 0xFF) / 255.0F, (var0 >> 8 & 0xFF) / 255.0F, (var0 & 0xFF) / 255.0F, (var0 >>> 24) / 255.0F);
   }

   private static boolean b(String var0) {
      if (var0 == null || var0.isEmpty() || fontFailed) {
         return false;
      } else if (var0.equals(textValue) && text != -1) {
         return true;
      } else {
         try {
            if (font == null) {
               InputStream var1 = com.cheatbreaker.client.kazerui.splash.KazerSplash.class.getResourceAsStream("/assets/kazerui/ui/fonts/roboto-medium.ttf");

               try {
                  font = Font.createFont(0, var1).deriveFont(18.0F);
               } finally {
                  var1.close();
               }
            }

            Graphics2D var11 = new BufferedImage(1, 1, 2).createGraphics();
            var11.setFont(font);
            FontMetrics var2 = var11.getFontMetrics();
            int var3 = Math.max(1, var2.stringWidth(var0) + 2);
            int var4 = Math.max(1, var2.getAscent() + var2.getDescent());
            var11.dispose();
            BufferedImage var5 = new BufferedImage(var3, var4, 2);
            Graphics2D var6 = var5.createGraphics();
            var6.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            var6.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            var6.setFont(font);
            var6.setColor(Color.WHITE);
            var6.drawString(var0, 1, var2.getAscent());
            var6.dispose();
            if (text == -1) {
               text = GL11.glGenTextures();
            }

            a(text, var5);
            textValue = var0;
            textWidth = var3;
            textHeight = var4;
            return true;
         } catch (Throwable var10) {
            LOG.warn("Loading screen text disabled", var10);
            fontFailed = true;
            return false;
         }
      }
   }

   private static int c(String var0) {
      try {
         InputStream var1 = com.cheatbreaker.client.kazerui.splash.KazerSplash.class.getResourceAsStream(var0.startsWith("/") ? var0 : "/assets/kazerui/" + var0);
         if (var1 == null) {
            throw new IllegalStateException("Missing /assets/kazerui/" + var0);
         } else {
            BufferedImage var2;
            try {
               var2 = ImageIO.read(var1);
            } finally {
               var1.close();
            }

            int var3 = GL11.glGenTextures();
            a(var3, var2);
            return var3;
         }
      } catch (Exception var7) {
         throw new IllegalStateException("Could not load loading screen texture " + var0, var7);
      }
   }

   private static void a(int var0, BufferedImage var1) {
      int var2 = var1.getWidth();
      int var3 = var1.getHeight();
      int[] var4 = var1.getRGB(0, 0, var2, var3, null, 0, var2);
      ByteBuffer var5 = BufferUtils.createByteBuffer(var2 * var3 * 4);

      for (int var9 : var4) {
         var5.put((byte)(var9 >> 16)).put((byte)(var9 >> 8)).put((byte)var9).put((byte)(var9 >>> 24));
      }

      ((Buffer)var5).flip();
      GL11.glBindTexture(3553, var0);
      GL11.glTexParameteri(3553, 10241, 9729);
      GL11.glTexParameteri(3553, 10240, 9729);
      GL11.glTexParameteri(3553, 10242, 33071);
      GL11.glTexParameteri(3553, 10243, 33071);
      GL11.glTexImage2D(3553, 0, 32856, var2, var3, 0, 6408, 5121, var5);
   }

   private static void a(Throwable var0) {
      failed = true;
      LOG.error("Kazer loading screen failed; the rest of loading continues without it", var0);
   }

   private static File h() {
      return new File(new File(Minecraft.getMinecraft().mcDataDir, "config"), "kazerui-splash.properties");
   }

   private static int i() {
      if (expectedStages != -1) {
         return expectedStages;
      } else {
         expectedStages = 1;
         File var0 = h();
         if (var0.isFile()) {
            Properties var1 = new Properties();

            try {
               FileInputStream var2 = new FileInputStream(var0);

               try {
                  var1.load(var2);
               } finally {
                  var2.close();
               }

               expectedStages = Math.max(1, Integer.parseInt(var1.getProperty("stages", "1").trim()));
            } catch (Exception var7) {
               LOG.debug("Ignoring unreadable {}", new Object[]{var0, var7});
            }
         }

         return expectedStages;
      }
   }

   private static void b(int var0) {
      File var1 = h();
      Properties var2 = new Properties();
      var2.setProperty("stages", Integer.toString(var0));

      try {
         var1.getParentFile().mkdirs();
         FileOutputStream var3 = new FileOutputStream(var1);

         try {
            var2.store(var3, "Top-level loading stages seen last launch; sizes the Kazer loading bar");
         } finally {
            var3.close();
         }
      } catch (Exception var8) {
         LOG.debug("Could not save {}", new Object[]{var1, var8});
      }
   }
}
