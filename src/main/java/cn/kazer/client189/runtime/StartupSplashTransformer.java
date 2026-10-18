package cn.kazer.client189.runtime;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class StartupSplashTransformer implements IClassTransformer {
   private static final String SPLASH = "net.minecraftforge.fml.client.SplashProgress";
   private static final String PROGRESS = "net.minecraftforge.fml.common.ProgressManager";
   private static final String PROGRESS_BAR = "net.minecraftforge.fml.common.ProgressManager$ProgressBar";
   private static final String HOOKS = "cn/kazer/client189/runtime/StartupSplashHooks";
   private static final boolean ENABLED = !"false".equalsIgnoreCase(System.getProperty("kazer.splash"));

   public byte[] transform(String var1, String var2, byte[] var3) {
      if (!ENABLED || var3 == null || !FMLLaunchHandler.side().isClient()) {
         return var3;
      } else if ("net.minecraftforge.fml.client.SplashProgress".equals(var2)) {
         return patch(var3, StartupSplashTransformer.a.SPLASH);
      } else if ("net.minecraftforge.fml.common.ProgressManager".equals(var2)) {
         return patch(var3, StartupSplashTransformer.a.PROGRESS);
      } else {
         return "net.minecraftforge.fml.common.ProgressManager$ProgressBar".equals(var2) ? patch(var3, StartupSplashTransformer.a.PROGRESS_BAR) : var3;
      }
   }

   private static byte[] patch(byte[] var0, StartupSplashTransformer.a var1) {
      ClassNode var2 = new ClassNode();
      new ClassReader(var0).accept(var2, 0);
      int var3 = 0;

      for (MethodNode var5 : var2.methods) {
         switch (var1) {
            case SPLASH:
               if (var5.name.equals("start") && var5.desc.equals("()V")) {
                  returnEarlyIf(var5, "takeOverFromFml");
                  var3++;
               } else if (var5.name.equals("drawVanillaScreen")) {
                  returnEarlyIf(var5, "onVanillaScreen");
                  var3++;
               }
               break;
            case PROGRESS:
               if (var5.name.equals("push") || var5.name.equals("pop")) {
                  redrawBeforeReturns(var5);
                  var3++;
               }
               break;
            case PROGRESS_BAR:
               if (var5.name.equals("step")) {
                  redrawBeforeReturns(var5);
                  var3++;
               }
         }
      }

      if (var3 == 0) {
         return var0;
      } else {
         ClassWriter var6 = new ClassWriter(1);
         var2.accept(var6);
         return var6.toByteArray();
      }
   }

   private static void returnEarlyIf(MethodNode var0, String var1) {
      LabelNode var2 = new LabelNode();
      InsnList var3 = new InsnList();
      var3.add(new MethodInsnNode(184, "cn/kazer/client189/runtime/StartupSplashHooks", var1, "()Z", false));
      var3.add(new JumpInsnNode(153, var2));
      var3.add(new InsnNode(177));
      var3.add(var2);
      var3.add(new FrameNode(3, 0, null, 0, null));
      var0.instructions.insert(var3);
   }

   private static void redrawBeforeReturns(MethodNode var0) {
      for (AbstractInsnNode var4 : var0.instructions.toArray()) {
         int var5 = var4.getOpcode();
         if (var5 >= 172 && var5 <= 177) {
            var0.instructions.insertBefore(var4, new MethodInsnNode(184, "cn/kazer/client189/runtime/StartupSplashHooks", "onProgress", "()V", false));
         }
      }
   }

   private static enum a {
      SPLASH,
      PROGRESS,
      PROGRESS_BAR;
   }
}
