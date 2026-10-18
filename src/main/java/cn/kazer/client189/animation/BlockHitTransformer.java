package cn.kazer.client189.animation;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class BlockHitTransformer implements IClassTransformer {
   private static final String BRIDGE = "cn/kazer/client189/animation/OldAnimationsAdapter";
   private static final String STACK = "Lnet/minecraft/item/ItemStack;";
   private static final String TRANSFORM = "Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;";

   public byte[] transform(String var1, String var2, byte[] var3) {
      if (var3 != null
         && (
            var2.equals("net.minecraft.client.renderer.ItemRenderer")
               || var2.equals("net.minecraft.client.Minecraft")
               || var2.equals("net.minecraft.client.model.ModelBiped")
               || var2.equals("net.minecraft.client.renderer.entity.RenderItem")
               || var2.equals("net.minecraft.client.renderer.entity.layers.LayerArmorBase")
               || var2.equals("net.minecraft.client.renderer.EntityRenderer")
               || var2.equals("net.minecraftforge.client.GuiIngameForge")
         )) {
         ClassNode var4 = new ClassNode();
         new ClassReader(var3).accept(var4, 0);
         int var5 = 0;
         if (var2.endsWith("RenderItem")) {
            var4.fields.add(new FieldNode(2, "kazer$lastEntity", "Lnet/minecraft/entity/EntityLivingBase;", null, null));
         }

         for (MethodNode var7 : var4.methods) {
            if (var2.endsWith("Minecraft") && is(var7, "rightClickMouse", "rightClickMouse")) {
               var7.instructions.insert(call("beforeRightClick", "()V"));
               var5++;
            }

            if (var2.endsWith("Minecraft") && (is(var7, "clickMouse", "clickMouse") || is(var7, "sendClickBlockToController", "sendClickBlockToController"))) {
               guard(var7, "cancelBlockClick", 177);
               var5++;
            }

            if (var2.endsWith("LayerArmorBase") && is(var7, "shouldCombineTextures", "shouldCombineTextures")) {
               guard(var7, "redArmor", 172);
               var5++;
            }

            if (var2.endsWith("RenderItem") && is(var7, "renderItemModelForEntity", "renderItemModelForEntity")) {
               InsnList var8 = new InsnList();
               var8.add(new VarInsnNode(25, 0));
               var8.add(new VarInsnNode(25, 2));
               var8.add(new FieldInsnNode(181, var4.name, "kazer$lastEntity", "Lnet/minecraft/entity/EntityLivingBase;"));
               var7.instructions.insert(var8);
               var5++;
            }

            if (var2.endsWith("EntityRenderer") && is(var7, "orientCamera", "orientCamera")) {
               InsnList var19 = new InsnList();
               var19.add(new VarInsnNode(23, 1));
               var19.add(call("cameraPartial", "(F)V"));
               var7.instructions.insert(var19);
               var5++;
            }

            int var20 = 0;
            int var9 = 0;

            for (AbstractInsnNode var13 : var7.instructions.toArray()) {
               if (var2.endsWith("ModelBiped")
                  && is(var7, "setRotationAngles", "setRotationAngles")
                  && var13 instanceof LdcInsnNode
                  && Float.valueOf((float) (-Math.PI / 6)).equals(((LdcInsnNode)var13).cst)) {
                  var7.instructions.insert(var13, call("blockingArmYaw", "(F)F"));
                  var5++;
               }

               if (var2.endsWith("GuiIngameForge")
                  && var7.name.equals("renderHealth")
                  && var13 instanceof VarInsnNode
                  && var13.getOpcode() == 21
                  && ((VarInsnNode)var13).var == 5) {
                  if (++var9 == 2) {
                     InsnList var14 = new InsnList();
                     var14.add(new VarInsnNode(21, 5));
                     var14.add(call("healthFlash", "(Z)Z"));
                     var14.add(new VarInsnNode(54, 5));
                     var7.instructions.insertBefore(var13, var14);
                     var5++;
                  }
               }

               if (var13 instanceof MethodInsnNode) {
                  MethodInsnNode var21 = (MethodInsnNode)var13;
                  if (var2.endsWith("ItemRenderer")) {
                     if (is(var7, "renderItemInFirstPerson", "renderItemInFirstPerson")) {
                        if (is(var21, "transformFirstPersonItem", "transformFirstPersonItem")) {
                           if (++var20 == 1) {
                              AbstractInsnNode var22 = previous(previous(previous(var21)));
                              LabelNode var16 = new LabelNode();
                              InsnList var17 = new InsnList();
                              var17.add(call("skipNoneTransform", "()Z"));
                              var17.add(new JumpInsnNode(154, var16));
                              var7.instructions.insertBefore(var22, var17);
                              var7.instructions.insert(var21, var16);
                              var5++;
                           } else if (var20 <= 4) {
                              InsnList var23 = new InsnList();
                              var23.add(new VarInsnNode(23, 1));
                              var23.add(call("useSwing", "(FF)F"));
                              var7.instructions.insertBefore(var21, var23);
                              var5++;
                           }
                        } else if (is(var21, "getItemInUseCount", "getItemInUseCount")) {
                           InsnList var24 = new InsnList();
                           var24.add(new VarInsnNode(25, 0));
                           var24.add(field(var4, "itemToRender", "itemToRender", "Lnet/minecraft/item/ItemStack;"));
                           var7.instructions.insertBefore(var21, var24);
                           var7.instructions.set(var21, call("renderedUseCount", "(Ljava/lang/Object;Lnet/minecraft/item/ItemStack;)I"));
                           var5++;
                        } else if (is(var21, "renderItem", "renderItem")) {
                           var7.instructions
                              .set(
                                 var21,
                                 call(
                                    "renderFirstPerson",
                                    "(Ljava/lang/Object;Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"
                                 )
                              );
                           var5++;
                        }
                     } else if (is(var7, "doBowTransformations", "doBowTransformations") && is(var21, "scale", "scale") && var21.desc.equals("(FFF)V")) {
                        var7.instructions.set(var21, call("bowScale", "(FFF)V"));
                        var5++;
                     } else if (is(var7, "updateEquippedItem", "updateEquippedItem") && is(var21, "clamp_float", "clamp_float")) {
                        InsnList var15 = new InsnList();
                        var15.add(new VarInsnNode(25, 0));
                        var15.add(field(var4, "itemToRender", "itemToRender", "Lnet/minecraft/item/ItemStack;"));
                        var15.add(new VarInsnNode(25, 0));
                        var15.add(field(var4, "equippedItemSlot", "equippedItemSlot", "I"));
                        var15.add(new VarInsnNode(25, 0));
                        var15.add(field(var4, "equippedProgress", "equippedProgress", "F"));
                        var7.instructions.insertBefore(var21, var15);
                        var7.instructions.set(var21, call("equipClamp", "(FFFLnet/minecraft/item/ItemStack;IF)F"));
                        var5++;
                     }
                  }

                  if (var2.endsWith("RenderItem")
                     && is(var7, "renderItemModelTransform", "renderItemModelTransform")
                     && is(var21, "renderItem", "renderItem")
                     && var21.desc.equals("(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V")) {
                     InsnList var25 = new InsnList();
                     var25.add(new VarInsnNode(25, 0));
                     var25.add(new FieldInsnNode(180, var4.name, "kazer$lastEntity", "Lnet/minecraft/entity/EntityLivingBase;"));
                     var25.add(new VarInsnNode(25, 3));
                     var25.add(call("thirdPersonPose", "(Ljava/lang/Object;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"));
                     var7.instructions.insertBefore(var21, var25);
                     var5++;
                  }

                  if (var2.endsWith("EntityRenderer") && is(var21, "getEyeHeight", "getEyeHeight")) {
                     if (is(var7, "orientCamera", "orientCamera")) {
                        var7.instructions.insertBefore(var21, new VarInsnNode(23, 1));
                        var7.instructions.set(var21, call("cameraEye", "(Ljava/lang/Object;F)F"));
                        var5++;
                     } else if (is(var7, "renderWorldDirections", "renderWorldDirections")) {
                        var7.instructions.set(var21, call("directionEye", "(Ljava/lang/Object;)F"));
                        var5++;
                     }
                  }
               }
            }
         }

         if (var5 == 0) {
            return var3;
         } else {
            ClassWriter var18 = new ClassWriter(1);
            var4.accept(var18);
            System.out.println("[KC 1.8.9] Sk1er OldAnimations hooks: " + var2 + " (" + var5 + ")");
            return var18.toByteArray();
         }
      } else {
         return var3;
      }
   }

   private static MethodInsnNode call(String var0, String var1) {
      return new MethodInsnNode(184, "cn/kazer/client189/animation/OldAnimationsAdapter", var0, var1, false);
   }

   private static boolean is(MethodNode var0, String var1, String var2) {
      return var0.name.equals(var1) || var0.name.equals(var2);
   }

   private static boolean is(MethodInsnNode var0, String var1, String var2) {
      return var0.name.equals(var1) || var0.name.equals(var2);
   }

   private static AbstractInsnNode previous(AbstractInsnNode var0) {
      do {
         var0 = var0.getPrevious();
      } while (var0 != null && var0.getOpcode() < 0);

      return var0;
   }

   private static FieldInsnNode field(ClassNode var0, String var1, String var2, String var3) {
      for (FieldNode var5 : var0.fields) {
         if (var5.name.equals(var1) || var5.name.equals(var2)) {
            return new FieldInsnNode(180, var0.name, var5.name, var3);
         }
      }

      throw new IllegalStateException("Missing OldAnimations field: " + var0.name + "." + var1);
   }

   private static void guard(MethodNode var0, String var1, int var2) {
      InsnList var3 = new InsnList();
      LabelNode var4 = new LabelNode();
      var3.add(call(var1, "()Z"));
      var3.add(new JumpInsnNode(153, var4));
      if (var2 == 172) {
         var3.add(new InsnNode(4));
      }

      var3.add(new InsnNode(var2));
      var3.add(var4);
      var0.instructions.insert(var3);
   }
}
