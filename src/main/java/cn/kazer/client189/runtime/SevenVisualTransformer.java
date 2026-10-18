package cn.kazer.client189.runtime;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class SevenVisualTransformer implements IClassTransformer {
   private static final String VISUAL = "com/cheatbreaker/client/kazerui/module/modules/visual/";
   private static final String WEATHER = "com/cheatbreaker/client/kazerui/module/modules/mechanic/ModuleWeatherChanger";
   private static final String HOOK = "cn/kazer/client189/runtime/SevenVisualHooks";

   public byte[] transform(String var1, String var2, byte[] var3) {
      if (var3 != null && var2 != null) {
         boolean var4 = var2.startsWith("net.minecraft.client.particle.");
         if (!var4
            && !var2.equals("net.minecraft.client.renderer.EntityRenderer")
            && !var2.equals("net.minecraft.client.renderer.RenderGlobal")
            && !var2.equals("net.minecraft.client.renderer.entity.RenderItem")
            && !var2.equals("net.minecraft.client.renderer.entity.RenderEntityItem")
            && !var2.equals("net.minecraft.client.renderer.entity.layers.LayerArmorBase")
            && !var2.equals("net.minecraft.client.gui.GuiScreen")
            && !var2.equals("net.minecraft.client.audio.SoundHandler")) {
            return var3;
         } else {
            ClassNode var5 = new ClassNode();
            new ClassReader(var3).accept(var5, 0);

            for (MethodNode var7 : var5.methods) {
               for (AbstractInsnNode var11 : var7.instructions.toArray()) {
                  if (var11 instanceof MethodInsnNode) {
                     String var12 = ((MethodInsnNode)var11).owner;
                     if (var12.equals("cn/kazer/client189/runtime/SevenVisualHooks")
                        || var12.equals("com/cheatbreaker/client/kazerui/module/modules/mechanic/ModuleWeatherChanger")
                        || var12.equals("com/cheatbreaker/client/kazerui/module/modules/visual/ModuleParticleChanger")
                        || var12.equals("com/cheatbreaker/client/kazerui/module/modules/visual/ModuleGlintColorizer")) {
                        return var3;
                     }
                  }
               }
            }

            int var20 = 0;

            for (MethodNode var23 : var5.methods) {
               if (var2.endsWith("SoundHandler") && is(var23.name, "playSound", "playSound") && var23.desc.equals("(Lnet/minecraft/client/audio/ISound;)V")
                  )
                {
                  InsnList var24 = new InsnList();
                  LabelNode var27 = new LabelNode();
                  var24.add(new VarInsnNode(25, 1));
                  var24.add(
                     new MethodInsnNode(184, "cn/kazer/client189/runtime/SevenVisualHooks", "muteSound", "(Lnet/minecraft/client/audio/ISound;)Z", false)
                  );
                  var24.add(new JumpInsnNode(153, var27));
                  var24.add(new InsnNode(177));
                  var24.add(var27);
                  if (var5.version >= 51) {
                     var24.add(new FrameNode(3, 0, null, 0, null));
                  }

                  var23.instructions.insert(var24);
                  var20++;
               }

               if (var2.endsWith("EffectRenderer")
                  && is(var23.name, "addEffect", "addEffect")
                  && var23.desc.equals("(Lnet/minecraft/client/particle/EntityFX;)V")) {
                  InsnList var25 = new InsnList();
                  LabelNode var28 = new LabelNode();
                  var25.add(new VarInsnNode(25, 1));
                  var25.add(
                     new MethodInsnNode(
                        184, "com/cheatbreaker/client/kazerui/module/modules/visual/ModuleParticleChanger", "keep", "(Ljava/lang/Object;)Z", false
                     )
                  );
                  var25.add(new JumpInsnNode(154, var28));
                  var25.add(new InsnNode(177));
                  var25.add(var28);
                  if (var5.version >= 51) {
                     var25.add(new FrameNode(3, 0, null, 0, null));
                  }

                  var23.instructions.insert(var25);
                  var20++;
               }

               boolean var26 = var4
                  && is(var23.name, "renderParticle", "renderParticle")
                  && var23.desc.equals("(Lnet/minecraft/client/renderer/WorldRenderer;Lnet/minecraft/entity/Entity;FFFFFF)V");
               boolean var29 = var2.endsWith("RenderEntityItem")
                  && is(var23.name, "func_177077_a", "func_177077_a")
                  && var23.desc.equals("(Lnet/minecraft/entity/item/EntityItem;DDDFLnet/minecraft/client/resources/model/IBakedModel;)I");
               int var30 = 0;

               for (AbstractInsnNode var15 : var23.instructions.toArray()) {
                  if (var26 && var15 instanceof FieldInsnNode && var15.getOpcode() == 180) {
                     FieldInsnNode var16 = (FieldInsnNode)var15;
                     int var17 = is(var16.name, "particleRed", "particleRed")
                        ? 16
                        : (is(var16.name, "particleGreen", "particleGreen") ? 8 : (is(var16.name, "particleBlue", "particleBlue") ? 0 : -1));
                     boolean var18 = is(var16.name, "particleScale", "particleScale");
                     if (var16.desc.equals("F") && (var18 || var17 >= 0)) {
                        InsnList var19 = new InsnList();
                        var19.add(new VarInsnNode(25, 0));
                        if (!var18) {
                           var19.add(new IntInsnNode(16, var17));
                        }

                        var19.add(
                           new MethodInsnNode(
                              184,
                              "com/cheatbreaker/client/kazerui/module/modules/visual/ModuleParticleChanger",
                              var18 ? "scale" : "channel",
                              var18 ? "(FLjava/lang/Object;)F" : "(FLjava/lang/Object;I)F",
                              false
                           )
                        );
                        var23.instructions.insert(var15, var19);
                        var20++;
                     }
                  }

                  if (var2.endsWith("RenderItem")
                     && is(var23.name, "renderEffect", "renderEffect")
                     && var15 instanceof LdcInsnNode
                     && Integer.valueOf(-8372020).equals(((LdcInsnNode)var15).cst)) {
                     var23.instructions
                        .insert(
                           var15,
                           new MethodInsnNode(184, "com/cheatbreaker/client/kazerui/module/modules/visual/ModuleGlintColorizer", "itemColor", "(I)I", false)
                        );
                     var20++;
                  }

                  if (var15 instanceof MethodInsnNode) {
                     MethodInsnNode var32 = (MethodInsnNode)var15;
                     if (var2.endsWith("GuiScreen")
                        && is(var23.name, "renderToolTip", "renderToolTip")
                        && is(var32.name, "drawHoveringText", "drawHoveringText")
                        && var32.desc.equals("(Ljava/util/List;IILnet/minecraft/client/gui/FontRenderer;)V")) {
                        var23.instructions.insertBefore(var32, new VarInsnNode(25, 1));
                        var23.instructions
                           .set(
                              var32,
                              new MethodInsnNode(
                                 184,
                                 "cn/kazer/client189/runtime/SevenVisualHooks",
                                 "tooltip",
                                 "(Lnet/minecraft/client/gui/GuiScreen;Ljava/util/List;IILnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/item/ItemStack;)V",
                                 false
                              )
                           );
                        var20++;
                     }

                     if (var2.endsWith("LayerArmorBase")
                        && is(var23.name, "renderGlint", "renderGlint")
                        && var32.owner.equals("net/minecraft/client/renderer/GlStateManager")
                        && is(var32.name, "color", "color")
                        && var32.desc.equals("(FFFF)V")) {
                        var23.instructions.set(var32, new MethodInsnNode(184, "cn/kazer/client189/runtime/SevenVisualHooks", "armorColor", var32.desc, false));
                        var20++;
                     }

                     if (var29 && var32.owner.equals("net/minecraft/client/renderer/GlStateManager")) {
                        if (is(var32.name, "translate", "translate") && var32.desc.equals("(FFF)V") && var30++ == 0) {
                           InsnList var33 = new InsnList();
                           var33.add(new VarInsnNode(25, 1));
                           var33.add(new VarInsnNode(25, 9));
                           var33.add(new VarInsnNode(24, 4));
                           var23.instructions.insertBefore(var32, var33);
                           var23.instructions
                              .set(
                                 var32,
                                 new MethodInsnNode(
                                    184,
                                    "cn/kazer/client189/runtime/SevenVisualHooks",
                                    "itemTranslate",
                                    "(FFFLnet/minecraft/entity/item/EntityItem;Lnet/minecraft/client/resources/model/IBakedModel;D)V",
                                    false
                                 )
                              );
                           var20++;
                        }

                        if (is(var32.name, "rotate", "rotate") && var32.desc.equals("(FFFF)V")) {
                           InsnList var34 = new InsnList();
                           var34.add(new VarInsnNode(25, 1));
                           var34.add(new VarInsnNode(23, 8));
                           var34.add(new VarInsnNode(25, 9));
                           var23.instructions.insertBefore(var32, var34);
                           var23.instructions
                              .set(
                                 var32,
                                 new MethodInsnNode(
                                    184,
                                    "cn/kazer/client189/runtime/SevenVisualHooks",
                                    "itemRotate",
                                    "(FFFFLnet/minecraft/entity/item/EntityItem;FLnet/minecraft/client/resources/model/IBakedModel;)V",
                                    false
                                 )
                              );
                           var20++;
                        }
                     }

                     if (var2.endsWith("EntityRenderer") || var2.endsWith("RenderGlobal")) {
                        String var35 = null;
                        String var36 = null;
                        String var37 = null;
                        if (is(var32.name, "getThunderStrength", "getThunderStrength") && var32.desc.equals("(F)F")) {
                           var35 = "com/cheatbreaker/client/kazerui/module/modules/mechanic/ModuleWeatherChanger";
                           var36 = "thunder";
                           var37 = "(F)F";
                        }

                        if (is(var32.name, "getRainStrength", "getRainStrength") && var32.desc.equals("(F)F")) {
                           var35 = is(var23.name, "addRainParticles", "addRainParticles")
                              ? "cn/kazer/client189/runtime/SevenVisualHooks"
                              : "com/cheatbreaker/client/kazerui/module/modules/mechanic/ModuleWeatherChanger";
                           var36 = var35.equals("cn/kazer/client189/runtime/SevenVisualHooks") ? "rainSoundStrength" : "strength";
                           var37 = "(F)F";
                        }

                        if (is(var23.name, "renderRainSnow", "renderRainSnow") || is(var23.name, "addRainParticles", "addRainParticles")) {
                           if ((is(var32.name, "canRain", "canRain") || is(var32.name, "getEnableSnow", "getEnableSnow")) && var32.desc.equals("()Z")) {
                              var35 = "com/cheatbreaker/client/kazerui/module/modules/mechanic/ModuleWeatherChanger";
                              var36 = "precipitation";
                              var37 = "(Z)Z";
                           }

                           if ((is(var32.name, "getFloatTemperature", "getFloatTemperature") || is(var32.name, "getTemperatureAtHeight", "getTemperatureAtHeight"))
                              && Type.getReturnType(var32.desc).equals(Type.FLOAT_TYPE)) {
                              var35 = "com/cheatbreaker/client/kazerui/module/modules/mechanic/ModuleWeatherChanger";
                              var36 = "temperature";
                              var37 = "(F)F";
                           }
                        }

                        if (var36 != null) {
                           var23.instructions.insert(var32, new MethodInsnNode(184, var35, var36, var37, false));
                           var20++;
                        }
                     }
                  }
               }
            }

            if (var20 == 0) {
               return var3;
            } else {
               ClassWriter var22 = new ClassWriter(1);
               var5.accept(var22);
               System.out.println("[Kazer Visual Modules] Adapted " + var2 + " (" + var20 + ")");
               return var22.toByteArray();
            }
         }
      } else {
         return var3;
      }
   }

   private static boolean is(String var0, String var1, String var2) {
      return var0.equals(var1) || var0.equals(var2);
   }
}
