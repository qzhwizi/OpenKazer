package cn.kazer.client189.render;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

public final class ClientBridge {
   public static float partialTicks;
   private static final cn.kazer.client189.render.PreviewFadeAnimation VIGNETTE = new cn.kazer.client189.render.PreviewFadeAnimation();
   private static KeyBinding fallbackZoom;
   private static final FloatBuffer NAMEPLATE = BufferUtils.createFloatBuffer(16);

   private ClientBridge() {
   }

   public static cn.kazer.client189.render.PreviewFadeAnimation a() {
      return VIGNETTE;
   }

   public static void a(int var0, int var1, float var2) {
      com.cheatbreaker.client.settings.GlobalSettings var3 = com.cheatbreaker.client.CheatBreakerClient.N().globalSettings;
      if ((Boolean)var3.containerVignette.d()) {
         int var4 = (Integer)var3.containerVignetteStrength.d();
         int var5 = Math.min(255, Math.round(184 * var4 / 50.0F * Math.max(0.0F, Math.min(1.0F, var2))));
         DrawRect.a(0.0F, 0.0F, var0, var1, var5 << 24 | 1052688);
      }
   }

   public static boolean b() {
      return a(false);
   }

   public static boolean a(boolean var0) {
      boolean var1 = GL11.glIsEnabled(2884);
      GL11.glDisable(2884);
      ((Buffer)NAMEPLATE).clear();
      GL11.glGetFloat(2982, NAMEPLATE);
      float var2 = var0
         ? (float)Math.sqrt(NAMEPLATE.get(0) * NAMEPLATE.get(0) + NAMEPLATE.get(1) * NAMEPLATE.get(1) + NAMEPLATE.get(2) * NAMEPLATE.get(2))
         : 1.0F;
      if (var0 && Minecraft.getMinecraft().currentScreen instanceof GuiContainer) {
         int var3 = ReflectionHelper.<Integer, GuiContainer>getPrivateValue(
            GuiContainer.class, (GuiContainer)Minecraft.getMinecraft().currentScreen, "guiTop", "field_147009_r"
         );
         NAMEPLATE.put(13, Math.max(NAMEPLATE.get(13), var3 + 9.0F));
      }

      for (int var4 = 0; var4 < 12; var4++) {
         NAMEPLATE.put(var4, 0.0F);
      }

      NAMEPLATE.put(0, -var2);
      NAMEPLATE.put(5, var0 ? -var2 : var2);
      NAMEPLATE.put(10, var2);
      NAMEPLATE.put(15, 1.0F);
      ((Buffer)NAMEPLATE).rewind();
      GL11.glLoadMatrix(NAMEPLATE);
      return var1;
   }

   public static void b(boolean var0) {
      if (var0) {
         GL11.glEnable(2884);
      } else {
         GL11.glDisable(2884);
      }
   }

   public static void a(Session var0) {
      ReflectionHelper.setPrivateValue(Minecraft.class, Minecraft.getMinecraft(), var0, "session", "field_71449_j");
   }

   public static KeyBinding c() {
      for (KeyBinding var3 : Minecraft.getMinecraft().gameSettings.keyBindings) {
         if ("of.key.zoom".equals(var3.getKeyDescription()) || "Zoom".equals(var3.getKeyDescription())) {
            return var3;
         }
      }

      if (fallbackZoom == null) {
         fallbackZoom = new KeyBinding("of.key.zoom", 46, "key.categories.misc");
      }

      return fallbackZoom;
   }

   public static int a(int var0) {
      int[] var1 = ReflectionHelper.getPrivateValue(FontRenderer.class, Minecraft.getMinecraft().fontRendererObj, "colorCode", "field_78285_g");
      return var1[var0];
   }

   public static int a(GuiNewChat var0) {
      List var1 = ReflectionHelper.getPrivateValue(GuiNewChat.class, var0, "drawnChatLines", "field_146253_i");
      int var2 = 0;
      int var3 = Minecraft.getMinecraft().ingameGUI.getUpdateCounter();

      for (ChatLine var5 : (Iterable<ChatLine>) var1) {
         if (var2 >= var0.getLineCount()) {
            break;
         }

         if (var0.getChatOpen() || var3 - var5.getUpdatedCounter() < 200) {
            var2++;
         }
      }

      return var2;
   }

   public static void d() {
      Minecraft.getMinecraft().entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
   }

   public static Matrix4f a(javax.vecmath.Matrix4f var0) {
      Matrix4f var1 = new Matrix4f();
      var1.m00 = var0.m00;
      var1.m01 = var0.m01;
      var1.m02 = var0.m02;
      var1.m03 = var0.m03;
      var1.m10 = var0.m10;
      var1.m11 = var0.m11;
      var1.m12 = var0.m12;
      var1.m13 = var0.m13;
      var1.m20 = var0.m20;
      var1.m21 = var0.m21;
      var1.m22 = var0.m22;
      var1.m23 = var0.m23;
      var1.m30 = var0.m30;
      var1.m31 = var0.m31;
      var1.m32 = var0.m32;
      var1.m33 = var0.m33;
      return var1;
   }
}
