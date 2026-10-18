package cn.kazer.client189.runtime;

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

public final class OverlayTransformer implements IClassTransformer {
   private static final String MODULE = "com/cheatbreaker/client/kazerui/module/modules/visual/ModuleOverlay";
   private static final String HOOK = "cn/kazer/client189/runtime/OverlayHooks";

   public byte[] transform(String var1, String var2, byte[] var3) {
      if (var3 == null || var2 == null) {
         return var3;
      } else if (!var2.equals("net.minecraft.client.renderer.entity.Render")
         && !var2.equals("net.minecraft.client.renderer.entity.RenderArrow")
         && !var2.equals("net.minecraft.entity.projectile.EntityArrow")
         && !var2.equals("net.minecraft.client.renderer.entity.layers.LayerArrow")
         && !var2.equals("net.minecraft.client.renderer.entity.layers.LayerArmorBase")
         && !var2.equals("net.minecraft.client.renderer.entity.layers.LayerCustomHead")
         && !var2.equals("net.minecraft.client.renderer.entity.RendererLivingEntity")
         && !var2.equals("net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer")
         && !var2.equals("net.minecraft.client.renderer.tileentity.TileEntityEndPortalRenderer")
         && !var2.equals("net.minecraft.client.gui.GuiIngame")
         && !var2.equals("net.minecraftforge.client.GuiIngameForge")
         && !var2.equals("net.minecraft.client.renderer.EntityRenderer")
         && !var2.equals("net.minecraft.client.renderer.ItemRenderer")
         && !var2.equals("net.minecraft.client.model.ModelHorse")
         && !var2.equals("net.minecraft.client.resources.ResourcePackListEntry")
         && !var2.equals("net.minecraft.client.renderer.BlockRendererDispatcher")
         && !var2.equals("net.minecraft.client.renderer.texture.SimpleTexture")
         && !var2.equals("net.minecraft.client.renderer.entity.RenderFish")
         && !var2.equals("net.minecraft.block.BlockBarrier")) {
         return var3;
      } else {
         ClassNode var4 = new ClassNode();
         new ClassReader(var3).accept(var4, 0);
         int var5 = 0;

         for (MethodNode var7 : var4.methods) {
            for (AbstractInsnNode var11 : var7.instructions.toArray()) {
               if (var11 instanceof MethodInsnNode) {
                  String var12 = ((MethodInsnNode)var11).owner;
                  if (var12.equals("com/cheatbreaker/client/kazerui/module/modules/visual/ModuleOverlay")
                     || var12.equals("cn/kazer/client189/runtime/OverlayHooks")
                     || var12.equals("cn/kazer/client189/runtime/OverlayModelCache")
                     || var12.equals("com/cheatbreaker/client/kazerui/module/modules/visual/OverlayTexturePalette")) {
                     return var3;
                  }
               }
            }
         }

         if (var2.endsWith("EntityArrow")) {
            FieldNode var17 = null;

            for (FieldNode var23 : var4.fields) {
               if (is(var23.name, "inGround", "inGround") && var23.desc.equals("Z")) {
                  var17 = var23;
               }
            }

            if (var17 != null && !var4.interfaces.contains("cn/kazer/client189/runtime/ArrowGroundState")) {
               var4.interfaces.add("cn/kazer/client189/runtime/ArrowGroundState");
               MethodNode var21 = new MethodNode(1, "kazerInGround", "()Z", null, null);
               var21.instructions.add(new VarInsnNode(25, 0));
               var21.instructions.add(new FieldInsnNode(180, var4.name, var17.name, "Z"));
               var21.instructions.add(new InsnNode(172));
               var4.methods.add(var21);
               var5++;
            }
         }

         for (MethodNode var22 : var4.methods) {
            if (var2.endsWith("LayerCustomHead")
               && is(var22.name, "doRenderLayer", "doRenderLayer")
               && var22.desc.startsWith("(Lnet/minecraft/entity/EntityLivingBase;")) {
               for (AbstractInsnNode var44 : var22.instructions.toArray()) {
                  if (var44 instanceof MethodInsnNode) {
                     MethodInsnNode var49 = (MethodInsnNode)var44;
                     if (is(var49.name, "getCurrentArmor", "getCurrentArmor") && var49.desc.equals("(I)Lnet/minecraft/item/ItemStack;")) {
                        InsnList var13 = new InsnList();
                        var13.add(new VarInsnNode(25, 1));
                        var13.add(
                           new MethodInsnNode(
                              184,
                              "cn/kazer/client189/runtime/OverlayHooks",
                              "headArmor",
                              "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityLivingBase;)Lnet/minecraft/item/ItemStack;",
                              false
                           )
                        );
                        var22.instructions.insert(var49, var13);
                        var5++;
                     }
                  }
               }
            }

            if (var2.endsWith("BlockBarrier") && is(var22.name, "getRenderType", "getRenderType") && var22.desc.equals("()I")) {
               for (AbstractInsnNode var45 : var22.instructions.toArray()) {
                  if (var45.getOpcode() == 172) {
                     var22.instructions.insertBefore(var45, new MethodInsnNode(184, "cn/kazer/client189/runtime/OverlayHooks", "barrierType", "(I)I", false));
                     var5++;
                  }
               }
            }

            if (var2.endsWith("BlockRendererDispatcher")
               && is(var22.name, "renderBlock", "renderBlock")
               && var22.desc
                  .equals(
                     "(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/WorldRenderer;)Z"
                  )) {
               InsnList var26 = new InsnList();
               LabelNode var33 = new LabelNode();

               for (int var40 = 1; var40 <= 4; var40++) {
                  var26.add(new VarInsnNode(25, var40));
               }

               var26.add(new MethodInsnNode(184, "cn/kazer/client189/runtime/OverlayModelCache", "renderBarrier", var22.desc, false));
               var26.add(new JumpInsnNode(153, var33));
               var26.add(new InsnNode(4));
               var26.add(new InsnNode(172));
               var26.add(var33);
               var22.instructions.insert(var26);
               var5++;
            }

            if (var2.endsWith("BlockRendererDispatcher")
               && var22.desc.startsWith("(Lnet/minecraft/block/state/IBlockState;")
               && var22.desc.endsWith(")Lnet/minecraft/client/resources/model/IBakedModel;")) {
               for (AbstractInsnNode var46 : var22.instructions.toArray()) {
                  if (var46.getOpcode() == 176) {
                     InsnList var50 = new InsnList();
                     var50.add(new VarInsnNode(25, 1));
                     var50.add(
                        new MethodInsnNode(
                           184,
                           "cn/kazer/client189/runtime/OverlayModelCache",
                           "adjust",
                           "(Lnet/minecraft/client/resources/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/client/resources/model/IBakedModel;",
                           false
                        )
                     );
                     var22.instructions.insertBefore(var46, var50);
                     var5++;
                  }
               }
            }

            if (var2.endsWith(".Render") && is(var22.name, "renderShadow", "renderShadow") && var22.desc.endsWith("FF)V")) {
               guard(var22, "cn/kazer/client189/runtime/OverlayHooks", "skipShadow");
               var5++;
            }

            if (var2.endsWith(".Render") && is(var22.name, "renderEntityOnFire", "renderEntityOnFire") && var22.desc.endsWith("F)V")) {
               guard(var22, "com/cheatbreaker/client/kazerui/module/modules/visual/ModuleOverlay", "fire");
               var5++;
            }

            if (var2.endsWith("LayerArrow")
               && is(var22.name, "doRenderLayer", "doRenderLayer")
               && var22.desc.startsWith("(Lnet/minecraft/entity/EntityLivingBase;")) {
               guard(var22, "cn/kazer/client189/runtime/OverlayHooks", "skipArrows");
               var5++;
            }

            if (var2.endsWith("TileEntitySkullRenderer")
               && is(var22.name, "renderTileEntityAt", "renderTileEntityAt")
               && var22.desc.equals("(Lnet/minecraft/tileentity/TileEntitySkull;DDDFI)V")) {
               guard(var22, "com/cheatbreaker/client/kazerui/module/modules/visual/ModuleOverlay", "hideSkulls");
               var5++;
            }

            if (var2.endsWith("TileEntityEndPortalRenderer")
               && is(var22.name, "renderTileEntityAt", "renderTileEntityAt")
               && var22.desc.equals("(Lnet/minecraft/tileentity/TileEntityEndPortal;DDDFI)V")) {
               guard(var22, "com/cheatbreaker/client/kazerui/module/modules/visual/ModuleOverlay", "hidePortals");
               var5++;
            }

            if (var2.endsWith("LayerArmorBase") && is(var22.name, "renderGlint", "renderGlint") && var22.desc.endsWith("FFFFFFF)V")) {
               guard(var22, "cn/kazer/client189/runtime/OverlayHooks", "skipArmorGlint");
               var5++;
            }

            if (var2.endsWith("RenderArrow")
               && is(var22.name, "doRender", "doRender")
               && var22.desc.startsWith("(Lnet/minecraft/entity/projectile/EntityArrow;")) {
               InsnList var28 = new InsnList();
               LabelNode var35 = new LabelNode();
               var28.add(new VarInsnNode(25, 1));
               var28.add(new MethodInsnNode(184, "cn/kazer/client189/runtime/OverlayHooks", "skipGroundArrow", "(Lnet/minecraft/entity/Entity;)Z", false));
               var28.add(new JumpInsnNode(153, var35));
               var28.add(new InsnNode(177));
               var28.add(var35);
               var22.instructions.insert(var28);
               var5++;
            }

            if (var2.endsWith("LayerArmorBase")
               && is(var22.name, "getCurrentArmor", "getCurrentArmor")
               && var22.desc.equals("(Lnet/minecraft/entity/EntityLivingBase;I)Lnet/minecraft/item/ItemStack;")) {
               for (AbstractInsnNode var47 : var22.instructions.toArray()) {
                  if (var47.getOpcode() == 176) {
                     InsnList var51 = new InsnList();
                     var51.add(new VarInsnNode(25, 1));
                     var51.add(new VarInsnNode(21, 2));
                     var51.add(
                        new MethodInsnNode(
                           184,
                           "cn/kazer/client189/runtime/OverlayHooks",
                           "armor",
                           "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityLivingBase;I)Lnet/minecraft/item/ItemStack;",
                           false
                        )
                     );
                     var22.instructions.insertBefore(var47, var51);
                     var5++;
                  }
               }
            }

            boolean var30 = is(var22.name, "renderPumpkinOverlay", "renderPumpkinOverlay");
            if (var30 && var22.desc.equals("(Lnet/minecraft/client/gui/ScaledResolution;)V")) {
               guard(var22, "cn/kazer/client189/runtime/OverlayHooks", "skipPumpkin");
               var5++;
            }

            for (AbstractInsnNode var52 : var22.instructions.toArray()) {
               if ((var2.endsWith("GuiIngame") || var2.endsWith("GuiIngameForge"))
                  && var52 instanceof LdcInsnNode
                  && Integer.valueOf(8453920).equals(((LdcInsnNode)var52).cst)) {
                  var22.instructions
                     .insert(var52, new MethodInsnNode(184, "com/cheatbreaker/client/kazerui/module/modules/visual/ModuleOverlay", "xpColor", "(I)I", false));
                  var5++;
               }

               if (var52 instanceof FieldInsnNode && var52.getOpcode() == 180 && var2.endsWith("RendererLivingEntity")) {
                  FieldInsnNode var53 = (FieldInsnNode)var52;
                  String var14 = is(var53.name, "deathTime", "deathTime")
                     ? (is(var22.name, "rotateCorpse", "rotateCorpse") ? "renderDeathTime" : "renderDamageDeathTime")
                     : (is(var53.name, "hurtTime", "hurtTime") ? "renderHurtTime" : null);
                  if (var14 != null && var53.desc.equals("I") && var53.owner.equals("net/minecraft/entity/EntityLivingBase")) {
                     var22.instructions
                        .set(
                           var53,
                           new MethodInsnNode(
                              184,
                              "com/cheatbreaker/client/kazerui/module/modules/visual/ModuleOverlay",
                              var14,
                              "(Lnet/minecraft/entity/EntityLivingBase;)I",
                              false
                           )
                        );
                     var5++;
                  }
               }

               if (var52 instanceof MethodInsnNode) {
                  MethodInsnNode var54 = (MethodInsnNode)var52;
                  if (var2.endsWith("RenderFish")
                     && is(var22.name, "doRender", "doRender")
                     && var54.owner.equals("net/minecraft/client/renderer/WorldRenderer")
                     && is(var54.name, "color", "color")
                     && var54.desc.equals("(IIII)Lnet/minecraft/client/renderer/WorldRenderer;")) {
                     var22.instructions
                        .set(
                           var54,
                           new MethodInsnNode(
                              184,
                              "cn/kazer/client189/runtime/OverlayHooks",
                              "lineColor",
                              "(Lnet/minecraft/client/renderer/WorldRenderer;IIII)Lnet/minecraft/client/renderer/WorldRenderer;",
                              false
                           )
                        );
                     var5++;
                  }

                  if (var2.endsWith("SimpleTexture")
                     && is(var22.name, "loadTexture", "loadTexture")
                     && var54.desc.equals("(Ljava/io/InputStream;)Ljava/awt/image/BufferedImage;")) {
                     FieldNode var55 = null;

                     for (FieldNode var16 : var4.fields) {
                        if (is(var16.name, "textureLocation", "textureLocation") && var16.desc.equals("Lnet/minecraft/util/ResourceLocation;")) {
                           var55 = var16;
                        }
                     }

                     if (var55 != null) {
                        InsnList var59 = new InsnList();
                        var59.add(new VarInsnNode(25, 0));
                        var59.add(new FieldInsnNode(180, var4.name, var55.name, var55.desc));
                        var59.add(
                           new MethodInsnNode(
                              184,
                              "com/cheatbreaker/client/kazerui/module/modules/visual/OverlayTexturePalette",
                              "tint",
                              "(Ljava/awt/image/BufferedImage;Lnet/minecraft/util/ResourceLocation;)Ljava/awt/image/BufferedImage;",
                              false
                           )
                        );
                        var22.instructions.insert(var54, var59);
                        var5++;
                     }
                  }

                  if (var2.endsWith("ResourcePackListEntry")
                     && is(var22.name, "drawEntry", "func_148279_a")
                     && var54.owner.equals("net/minecraft/client/gui/Gui")
                     && is(var54.name, "drawModalRectWithCustomSizedTexture", "drawModalRectWithCustomSizedTexture")
                     && var54.desc.equals("(IIFFIIFF)V")) {
                     var22.instructions.set(var54, new MethodInsnNode(184, "cn/kazer/client189/runtime/OverlayHooks", "packIcon", var54.desc, false));
                     var5++;
                  }

                  if (var2.endsWith("ModelHorse")
                     && is(var22.name, "render", "render")
                     && var54.owner.equals("net/minecraft/client/model/ModelRenderer")
                     && is(var54.name, "render", "render")
                     && var54.desc.equals("(F)V")) {
                     AbstractInsnNode var56 = var54.getPrevious();

                     while (var56 != null && !(var56 instanceof FieldInsnNode)) {
                        var56 = var56.getPrevious();
                     }

                     if (var56 instanceof FieldInsnNode && horseHead(((FieldInsnNode)var56).name)) {
                        var22.instructions
                           .set(
                              var54,
                              new MethodInsnNode(
                                 184, "cn/kazer/client189/runtime/OverlayHooks", "horseHead", "(Lnet/minecraft/client/model/ModelRenderer;F)V", false
                              )
                           );
                        var5++;
                     }
                  }

                  if (var2.endsWith("EntityRenderer")
                     && is(var22.name, "setupViewBobbing", "setupViewBobbing")
                     && var54.owner.equals("net/minecraft/client/renderer/GlStateManager")) {
                     String var57 = var54.desc.equals("(FFF)V") && is(var54.name, "translate", "translate")
                        ? "bobTranslate"
                        : (var54.desc.equals("(FFFF)V") && is(var54.name, "rotate", "rotate") ? "bobRotate" : null);
                     if (var57 != null) {
                        var22.instructions.set(var54, new MethodInsnNode(184, "cn/kazer/client189/runtime/OverlayHooks", var57, var54.desc, false));
                        var5++;
                     }
                  }

                  if (var2.endsWith("ItemRenderer")
                     && is(var22.name, "renderFireInFirstPerson", "renderFireInFirstPerson")
                     && var54.owner.equals("net/minecraft/client/renderer/GlStateManager")
                     && is(var54.name, "translate", "translate")
                     && var54.desc.equals("(FFF)V")) {
                     var22.instructions.set(var54, new MethodInsnNode(184, "cn/kazer/client189/runtime/OverlayHooks", "fireTranslate", var54.desc, false));
                     var5++;
                  }

                  if (var30
                     && var54.owner.equals("net/minecraft/client/renderer/GlStateManager")
                     && is(var54.name, "color", "color")
                     && var54.desc.equals("(FFFF)V")) {
                     AbstractInsnNode var58 = var54.getNext();

                     while (var58 != null && var58.getOpcode() < 0) {
                        var58 = var58.getNext();
                     }

                     if (var58 != null && var58.getOpcode() != 177) {
                        var22.instructions.set(var54, new MethodInsnNode(184, "cn/kazer/client189/runtime/OverlayHooks", "pumpkinColor", var54.desc, false));
                        var5++;
                     }
                  }
               }
            }
         }

         if (var5 == 0) {
            return var3;
         } else {
            ClassWriter var19 = new ClassWriter(3);
            var4.accept(var19);
            System.out.println("[Kazer Overlay] Adapted " + var2 + " (" + var5 + ")");
            return var19.toByteArray();
         }
      }
   }

   private static boolean is(String var0, String var1, String var2) {
      return var0.equals(var1) || var0.equals(var2);
   }

   private static boolean horseHead(String var0) {
      switch (var0) {
         case "head":
         case "neck":
         case "mane":
         case "horseFaceRopes":
         case "horseLeftFaceMetal":
         case "horseRightFaceMetal":
         case "horseLeftRein":
         case "horseRightRein":
         case "muleLeftEar":
         case "muleRightEar":
         case "horseLeftEar":
         case "horseRightEar":
            return true;
         default:
            return false;
      }
   }

   private static void guard(MethodNode var0, String var1, String var2) {
      InsnList var3 = new InsnList();
      LabelNode var4 = new LabelNode();
      var3.add(new MethodInsnNode(184, var1, var2, "()Z", false));
      var3.add(new JumpInsnNode(153, var4));
      var3.add(new InsnNode(177));
      var3.add(var4);
      var0.instructions.insert(var3);
   }
}
