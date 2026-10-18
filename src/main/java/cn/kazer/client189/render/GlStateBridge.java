package cn.kazer.client189.render;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public final class GlStateBridge {
   private static final int[] CAPS = new int[]{3042, 3008, 2929, 2884, 2896, 2912, 32826};
   private static final FloatBuffer COLOR = BufferUtils.createFloatBuffer(16);
   private static final ByteBuffer MASK = BufferUtils.createByteBuffer(16);
   private static final List<cn.kazer.client189.render.GlStateSnapshot> SNAPSHOTS = new ArrayList<>();
   private static final List<Integer> MASKS = new ArrayList<>();
   private static int stackDepth;

   private GlStateBridge() {
   }

   public static void a(int var0) {
      a(var0, true);
   }

   public static void b(int var0) {
      a(var0, false);
   }

   private static void a(int var0, boolean var1) {
      switch (var0) {
         case 2884:
            if (var1) {
               GlStateManager.enableCull();
            } else {
               GlStateManager.disableCull();
            }
            break;
         case 2896:
            if (var1) {
               GlStateManager.enableLighting();
            } else {
               GlStateManager.disableLighting();
            }
            break;
         case 2912:
            if (var1) {
               GlStateManager.enableFog();
            } else {
               GlStateManager.disableFog();
            }
            break;
         case 2929:
            if (var1) {
               GlStateManager.enableDepth();
            } else {
               GlStateManager.disableDepth();
            }
            break;
         case 3008:
            if (var1) {
               GlStateManager.enableAlpha();
            } else {
               GlStateManager.disableAlpha();
            }
            break;
         case 3042:
            if (var1) {
               GlStateManager.enableBlend();
            } else {
               GlStateManager.disableBlend();
            }
            break;
         case 3553:
            if (var1) {
               GlStateManager.enableTexture2D();
            } else {
               GlStateManager.disableTexture2D();
            }
            break;
         case 32826:
            if (var1) {
               GlStateManager.enableRescaleNormal();
            } else {
               GlStateManager.disableRescaleNormal();
            }
            break;
         default:
            if (var1) {
               GL11.glEnable(var0);
            } else {
               GL11.glDisable(var0);
            }
      }
   }

   public static void a(float var0, float var1, float var2, float var3) {
      GlStateManager.color(var0, var1, var2, var3);
   }

   public static void a(float var0, float var1, float var2) {
      GlStateManager.color(var0, var1, var2, 1.0F);
   }

   public static void a(int var0, int var1) {
      if (var0 == 3553) {
         GlStateManager.bindTexture(var1);
      } else {
         GL11.glBindTexture(var0, var1);
      }
   }

   public static void a(boolean var0) {
      GlStateManager.depthMask(var0);
   }

   public static void b(int var0, int var1) {
      GlStateManager.blendFunc(var0, var1);
   }

   public static void a(int var0, float var1) {
      GlStateManager.alphaFunc(var0, var1);
   }

   public static void a(boolean var0, boolean var1, boolean var2, boolean var3) {
      GlStateManager.colorMask(var0, var1, var2, var3);
   }

   public static void c(int var0) {
      if (stackDepth == SNAPSHOTS.size()) {
         SNAPSHOTS.add(new cn.kazer.client189.render.GlStateSnapshot());
         MASKS.add(0);
      }

      if (MASKS.get(stackDepth) != var0) {
         MASKS.set(stackDepth, var0);
      }

      if (var0 == 1048575) {
         SNAPSHOTS.get(stackDepth).a();
      }

      stackDepth++;
      GL11.glPushAttrib(var0);
   }

   public static void a() {
      GL11.glPopAttrib();
      if (stackDepth == 0) {
         b();
      } else {
         int var0 = MASKS.get(--stackDepth);
         if (var0 == 1048575) {
            SNAPSHOTS.get(stackDepth).b();
         } else if (var0 != 0) {
            b();
         }
      }
   }

   public static void b() {
      int var0 = GL11.glGetInteger(34016);
      GlStateManager.setActiveTexture(33991);

      for (int var1 = 0; var1 < 3; var1++) {
         GlStateManager.setActiveTexture(33984 + var1);
         boolean var2 = GL11.glIsEnabled(3553);
         a(3553, !var2);
         a(3553, var2);
         int var3 = GL11.glGetInteger(32873);
         GlStateManager.bindTexture(0);
         GlStateManager.bindTexture(var3);
      }

      GlStateManager.setActiveTexture(var0);

      for (int var4 : CAPS) {
         boolean var5 = GL11.glIsEnabled(var4);
         a(var4, !var5);
         a(var4, var5);
      }

      FloatBuffer var14 = COLOR;
      ((Buffer)var14).clear();
      GL11.glGetFloat(2816, var14);
      GlStateManager.resetColor();
      GlStateManager.color(var14.get(0), var14.get(1), var14.get(2), var14.get(3));
      boolean var16 = GL11.glGetBoolean(2930);
      GlStateManager.depthMask(!var16);
      GlStateManager.depthMask(var16);
      int var18 = GL11.glGetInteger(32969);
      int var19 = GL11.glGetInteger(32968);
      int var20 = GL11.glGetInteger(32971);
      int var6 = GL11.glGetInteger(32970);
      GlStateManager.tryBlendFuncSeparate(var18 == 0 ? 1 : 0, var19 == 0 ? 1 : 0, var20 == 0 ? 1 : 0, var6 == 0 ? 1 : 0);
      GlStateManager.tryBlendFuncSeparate(var18, var19, var20, var6);
      int var7 = GL11.glGetInteger(2932);
      GlStateManager.depthFunc(var7 == 519 ? 515 : 519);
      GlStateManager.depthFunc(var7);
      ByteBuffer var8 = MASK;
      ((Buffer)var8).clear();
      GL11.glGetBoolean(3107, var8);
      boolean var9 = var8.get(0) != 0;
      boolean var10 = var8.get(1) != 0;
      boolean var11 = var8.get(2) != 0;
      boolean var12 = var8.get(3) != 0;
      GlStateManager.colorMask(!var9, !var10, !var11, !var12);
      GlStateManager.colorMask(var9, var10, var11, var12);
   }
}
