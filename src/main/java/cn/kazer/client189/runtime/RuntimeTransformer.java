package cn.kazer.client189.runtime;

import java.util.ArrayList;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TryCatchBlockNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class RuntimeTransformer implements IClassTransformer {
   private static final String HOOK = "cn/kazer/client189/runtime/RuntimeHooks";
   private static final String PINNED = "cn/kazer/client189/runtime/KazerPinnedServers";

   public byte[] transform(String var1, String var2, byte[] var3) {
      if (var3 != null
         && (
            var2.equals("net.minecraft.client.renderer.ItemRenderer")
               || var2.equals("net.minecraft.client.renderer.entity.layers.LayerHeldItem")
               || var2.equals("net.minecraft.client.renderer.entity.layers.LayerCape")
               || var2.equals("net.minecraft.client.renderer.entity.layers.LayerArmorBase")
               || var2.equals("net.minecraft.client.model.ModelBiped")
               || var2.equals("net.minecraft.client.gui.GuiIngame")
               || var2.equals("net.minecraft.client.gui.GuiNewChat")
               || var2.equals("net.minecraft.client.gui.GuiChat")
               || var2.equals("net.minecraft.client.gui.ChatLine")
               || var2.equals("net.minecraft.client.network.NetHandlerPlayClient")
               || var2.equals("net.minecraft.client.renderer.EntityRenderer")
               || var2.equals("net.minecraft.client.renderer.RenderGlobal")
               || var2.equals("net.minecraft.client.particle.EffectRenderer")
               || var2.equals("net.minecraft.client.Minecraft")
               || var2.equals("net.minecraft.client.entity.EntityPlayerSP")
               || var2.equals("net.minecraft.client.settings.GameSettings")
               || var2.equals("net.minecraft.client.settings.KeyBinding")
               || var2.equals("net.minecraft.client.renderer.entity.RendererLivingEntity")
               || var2.equals("net.minecraftforge.client.GuiIngameForge")
               || var2.equals("net.minecraft.client.gui.GuiScreen")
               || var2.equals("net.minecraft.client.gui.GuiKeyBindingList")
               || var2.equals("net.minecraftforge.fml.client.config.GuiUtils")
               || var2.equals("net.minecraft.client.gui.GuiScreenResourcePacks")
               || var2.equals("net.minecraft.client.gui.inventory.GuiContainer")
               || var2.equals("net.minecraft.client.gui.GuiTextField")
               || var2.equals("net.minecraft.client.renderer.InventoryEffectRenderer")
               || var2.equals("net.minecraft.client.gui.achievement.GuiAchievement")
               || var2.equals("net.minecraft.entity.player.InventoryPlayer")
               || var2.equals("net.minecraft.client.renderer.entity.RenderManager")
               || var2.equals("net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher")
               || var2.equals("net.minecraft.client.renderer.entity.RenderItem")
               || var2.equals("net.minecraft.client.renderer.entity.RenderEntityItem")
               || var2.equals("net.minecraft.client.gui.GuiPlayerTabOverlay")
               || var2.equals("net.minecraft.client.renderer.BlockRendererDispatcher")
               || var2.equals("net.minecraft.client.multiplayer.ServerList")
               || var2.equals("net.minecraft.client.gui.ServerSelectionList")
               || var2.equals("net.minecraft.client.gui.GuiMultiplayer")
               || var2.equals("net.optifine.CustomSky")
               || var2.equals("optifine.CustomSky")
         )) {
         ClassNode var4 = new ClassNode();
         new ClassReader(var3).accept(var4, 0);
         int var5 = 0;
         if (var2.endsWith("GuiKeyBindingList")) {
            for (MethodNode var7 : var4.methods) {
               if (var7.name.equals("<init>")) {
                  for (AbstractInsnNode var11 : var7.instructions.toArray()) {
                     if (var11 instanceof MethodInsnNode) {
                        MethodInsnNode var12 = (MethodInsnNode)var11;
                        if (var12.owner.equals("net/minecraft/client/settings/KeyBinding") && var12.desc.equals("()Ljava/util/Set;")) {
                           var7.instructions
                              .set(
                                 var12,
                                 new MethodInsnNode(184, "com/cheatbreaker/client/kazerui/gui/NativeControlBindings", "categories", "()Ljava/util/Set;", false)
                              );
                           var5++;
                        }
                     }
                  }
               }
            }
         }

         if (var2.endsWith("GuiKeyBindingList")) {
            for (MethodNode var27 : var4.methods) {
               if (var27.name.equals("<init>")) {
                  for (AbstractInsnNode var162 : var27.instructions.toArray()) {
                     if (var162 instanceof FieldInsnNode) {
                        FieldInsnNode var211 = (FieldInsnNode)var162;
                        if (var211.getOpcode() == 180
                           && is(var211.name, "keyBindings", "field_74324_K")
                           && var211.desc.equals("[Lnet/minecraft/client/settings/KeyBinding;")) {
                           var27.instructions
                              .insert(
                                 var211,
                                 new MethodInsnNode(
                                    184,
                                    "com/cheatbreaker/client/kazerui/gui/NativeControlBindings",
                                    "visible",
                                    "([Lnet/minecraft/client/settings/KeyBinding;)[Lnet/minecraft/client/settings/KeyBinding;",
                                    false
                                 )
                              );
                           var5++;
                        }
                     }
                  }
               }
            }
         }

         if (var2.endsWith("GuiScreen")) {
            for (MethodNode var28 : var4.methods) {
               if (is(var28.name, "keyTyped", "func_73869_a")) {
                  for (AbstractInsnNode var163 : var28.instructions.toArray()) {
                     if (var163 instanceof MethodInsnNode) {
                        MethodInsnNode var212 = (MethodInsnNode)var163;
                        if (var212.owner.equals("net/minecraft/client/Minecraft") && is(var212.name, "setIngameFocus", "func_71381_h")) {
                           var28.instructions
                              .set(
                                 var212,
                                 new MethodInsnNode(
                                    184, "cn/kazer/client189/runtime/RuntimeHooks", "resumeAfterEscape", "(Lnet/minecraft/client/Minecraft;)V", false
                                 )
                              );
                           var5++;
                        }
                     }
                  }
               }
            }
         }

         if (var2.endsWith("GuiScreenResourcePacks")) {
            for (MethodNode var29 : var4.methods) {
               if (is(var29.name, "initGui", "func_73866_w_")) {
                  for (AbstractInsnNode var164 : var29.instructions.toArray()) {
                     if (var164 instanceof MethodInsnNode) {
                        MethodInsnNode var213 = (MethodInsnNode)var164;
                        if (is(var213.name, "updateRepositoryEntriesAll", "func_110611_a")) {
                           var29.instructions
                              .set(
                                 var213, new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "resourcePackScan", "(Ljava/lang/Object;)V", false)
                              );
                           var5++;
                        }
                     }
                  }
               }
            }
         }

         if (var2.endsWith("GuiScreen") || var2.endsWith("GuiUtils")) {
            for (MethodNode var30 : var4.methods) {
               if (is(var30.name, "drawHoveringText", "func_146283_a")) {
                  for (AbstractInsnNode var165 : var30.instructions.toArray()) {
                     if (var165 instanceof MethodInsnNode) {
                        MethodInsnNode var214 = (MethodInsnNode)var165;
                        if (is(var214.name, "drawStringWithShadow", "func_175063_a") && var214.desc.equals("(Ljava/lang/String;FFI)I")) {
                           var30.instructions
                              .set(
                                 var214,
                                 new MethodInsnNode(
                                    184,
                                    "com/cheatbreaker/client/kazerui/module/render/TooltipHooks",
                                    "drawLine",
                                    "(Ljava/lang/Object;Ljava/lang/String;FFI)I",
                                    false
                                 )
                              );
                           var5++;
                        }
                     }
                  }
               }
            }
         }

         if (var2.equals("net.minecraft.client.gui.GuiIngame")) {
            var4.interfaces.add("com/cheatbreaker/client/kazerui/module/render/TitleAccess");
            addChatGetter(var4, "kazerTitleTime", "titlesTimer", "field_175195_w", "I", 172);
            addChatGetter(var4, "kazerTitle", "displayedTitle", "field_175201_x", "Ljava/lang/String;", 176);
            addChatGetter(var4, "kazerSubtitle", "displayedSubTitle", "field_175200_y", "Ljava/lang/String;", 176);
            addChatGetter(var4, "kazerFadeIn", "titleFadeIn", "field_175199_z", "I", 172);
            addChatGetter(var4, "kazerStay", "titleDisplayTime", "field_175192_A", "I", 172);
            addChatGetter(var4, "kazerFadeOut", "titleFadeOut", "field_175193_B", "I", 172);
            var5++;
         }

         if (var2.equals("net.minecraft.client.gui.inventory.GuiContainer")) {
            var4.interfaces.add("com/cheatbreaker/client/kazerui/gui/ContainerBounds");
            addChatGetter(var4, "kazerLeft", "guiLeft", "field_147003_i", "I", 172);
            addChatGetter(var4, "kazerTop", "guiTop", "field_147009_r", "I", 172);
            addChatGetter(var4, "kazerWidth", "xSize", "field_146999_f", "I", 172);
            var5++;
         }

         if (var2.equals("net.minecraft.client.gui.GuiNewChat")) {
            String var23 = "com/cheatbreaker/client/kazerui/module/render/KazerChatAccess";
            if (!var4.interfaces.contains(var23)) {
               var4.interfaces.add(var23);
               addChatGetter(var4, "kazerDrawnLines", "drawnChatLines", "field_146253_i", "Ljava/util/List;", 176);
               addChatGetter(var4, "kazerChatLines", "chatLines", "field_146252_h", "Ljava/util/List;", 176);
               addChatGetter(var4, "kazerChatScroll", "scrollPos", "field_146250_j", "I", 172);
               addChatGetter(var4, "kazerChatScrolled", "isScrolled", "field_146251_k", "Z", 172);
               FieldNode var31 = null;

               for (FieldNode var49 : var4.fields) {
                  if (is(var49.name, "isScrolled", "field_146251_k") && var49.desc.equals("Z")) {
                     var31 = var49;
                  }
               }

               if (var31 == null) {
                  throw new IllegalStateException("Native chat scrolled flag was not found");
               }

               MethodNode var41 = new MethodNode(1, "kazerChatScrolled", "(Z)V", null, null);
               var41.instructions.add(new VarInsnNode(25, 0));
               var41.instructions.add(new VarInsnNode(21, 1));
               var41.instructions.add(new FieldInsnNode(181, var4.name, var31.name, "Z"));
               var41.instructions.add(new InsnNode(177));
               var4.methods.add(var41);
               var5++;
            }
         }

         if (var2.equals("net.minecraft.client.gui.ChatLine")) {
            String var24 = "com/cheatbreaker/client/kazerui/module/render/ChatLineTag";
            if (!var4.interfaces.contains(var24)) {
               var4.interfaces.add(var24);
               var4.fields.add(new FieldNode(2, "kazerMessageId", "I", null, null));
               addChatGetter(var4, "kazerui$id", "kazerMessageId", "kazerMessageId", "I", 172);
               MethodNode var32 = new MethodNode(1, "kazerui$setId", "(I)V", null, null);
               var32.instructions.add(new VarInsnNode(25, 0));
               var32.instructions.add(new VarInsnNode(21, 1));
               var32.instructions.add(new FieldInsnNode(181, var4.name, "kazerMessageId", "I"));
               var32.instructions.add(new InsnNode(177));
               var4.methods.add(var32);
               var5++;
            }
         }

         if (var2.equals("net.minecraft.client.settings.KeyBinding")) {
            String var25 = "com/cheatbreaker/client/kazerui/module/modules/mechanic/KazerKeyState";
            String var33 = null;

            for (FieldNode var50 : var4.fields) {
               if (var50.desc.equals("Z") && is(var50.name, "pressed", "field_74513_e")) {
                  var33 = var50.name;
               }
            }

            if (var33 == null) {
               throw new IllegalStateException("Native key-state field was not found");
            }

            if (!var4.interfaces.contains(var25)) {
               var4.interfaces.add(var25);
               MethodNode var43 = new MethodNode(1, "kazerSetPressed", "(Z)V", null, null);
               var43.instructions.add(new VarInsnNode(25, 0));
               var43.instructions.add(new VarInsnNode(21, 1));
               var43.instructions.add(new FieldInsnNode(181, var4.name, var33, "Z"));
               var43.instructions.add(new InsnNode(177));
               var4.methods.add(var43);
               var5++;
            }
         }

         ArrayList var26 = new ArrayList();

         for (MethodNode var44 : var4.methods) {
            if (var2.endsWith("EntityRenderer") && is(var44.name, "setupFog", "func_78468_a") && var44.desc.equals("(IF)V")) {
               boolean var51 = false;

               for (AbstractInsnNode var13 : var44.instructions.toArray()) {
                  if (var13 instanceof MethodInsnNode && ((MethodInsnNode)var13).name.equals("clearDistanceFog")) {
                     var51 = true;
                  }
               }

               if (!var51) {
                  var4.fields.add(new FieldNode(2, "kazerDistanceFog", "Z", null, null));
                  InsnList var110 = new InsnList();
                  var110.add(new VarInsnNode(25, 0));
                  var110.add(new InsnNode(3));
                  var110.add(new FieldInsnNode(181, var4.name, "kazerDistanceFog", "Z"));
                  var44.instructions.insert(var110);

                  for (AbstractInsnNode var14 : var44.instructions.toArray()) {
                     if (var14 instanceof MethodInsnNode
                        && is(((MethodInsnNode)var14).name, "doesXZShowFog", "func_76568_b")
                        && ((MethodInsnNode)var14).desc.equals("(II)Z")) {
                        InsnList var15 = new InsnList();
                        var15.add(new VarInsnNode(25, 0));
                        var15.add(new InsnNode(4));
                        var15.add(new FieldInsnNode(181, var4.name, "kazerDistanceFog", "Z"));
                        var44.instructions.insertBefore(var14, var15);
                     }

                     if (var14.getOpcode() == 177) {
                        InsnList var311 = new InsnList();
                        var311.add(new VarInsnNode(25, 0));
                        var311.add(new FieldInsnNode(180, var4.name, "kazerDistanceFog", "Z"));
                        var311.add(new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "clearDistanceFog", "(Z)V", false));
                        var44.instructions.insertBefore(var14, var311);
                        var5++;
                     }
                  }
               }
            }

            if (var2.endsWith("RenderGlobal")
               && is(var44.name, "getRenderChunkOffset", "func_181562_a")
               && var44.desc
                  .equals(
                     "(Lnet/minecraft/util/BlockPos;Lnet/minecraft/client/renderer/chunk/RenderChunk;Lnet/minecraft/util/EnumFacing;ZI)Lnet/minecraft/client/renderer/chunk/RenderChunk;"
                  )) {
               boolean var52 = false;
               boolean var111 = false;

               for (AbstractInsnNode var292 : var44.instructions.toArray()) {
                  if (var292 instanceof MethodInsnNode && ((MethodInsnNode)var292).name.equals("visibleChunk")) {
                     var52 = true;
                  }

                  if (var292 instanceof FieldInsnNode && ((FieldInsnNode)var292).name.equals("renderDistanceSq")) {
                     var111 = true;
                  }
               }

               if (!var52 && var111) {
                  for (AbstractInsnNode var293 : var44.instructions.toArray()) {
                     if (var293 instanceof VarInsnNode
                        && var293.getOpcode() == 21
                        && ((VarInsnNode)var293).var == 4
                        && nextOpcode(var293) instanceof JumpInsnNode
                        && nextOpcode(var293).getOpcode() == 153) {
                        var44.instructions.set(var293, new InsnNode(3));
                     }
                  }

                  for (AbstractInsnNode var294 : var44.instructions.toArray()) {
                     if (var294.getOpcode() == 176) {
                        InsnList var312 = new InsnList();
                        var312.add(new VarInsnNode(25, 1));
                        var312.add(
                           new MethodInsnNode(
                              184,
                              "cn/kazer/client189/runtime/RuntimeHooks",
                              "visibleChunk",
                              "(Lnet/minecraft/client/renderer/chunk/RenderChunk;Lnet/minecraft/util/BlockPos;)Lnet/minecraft/client/renderer/chunk/RenderChunk;",
                              false
                           )
                        );
                        var44.instructions.insertBefore(var294, var312);
                        var5++;
                     }
                  }
               }
            }

            if (var2.endsWith("EntityRenderer")) {
               for (AbstractInsnNode var220 : var44.instructions.toArray()) {
                  if (var220 instanceof MethodInsnNode) {
                     MethodInsnNode var261 = (MethodInsnNode)var220;
                     if ((var261.owner.equals("Config") || var261.owner.equals("net/optifine/Config"))
                        && var261.name.equals("checkDisplayMode")
                        && var261.desc.equals("()V")
                        && var261.getOpcode() == 184) {
                        LabelNode var295 = new LabelNode();
                        InsnList var313 = new InsnList();
                        var313.add(new MethodInsnNode(184, "com/cheatbreaker/client/kazerui/input/WindowMode", "active", "()Z", false));
                        var313.add(new JumpInsnNode(154, var295));
                        var44.instructions.insertBefore(var261, var313);
                        InsnList var16 = new InsnList();
                        var16.add(var295);
                        var16.add(new FrameNode(3, 0, null, 0, null));
                        var44.instructions.insert(var261, var16);
                        var5++;
                     }
                  }
               }
            }

            if (var2.endsWith("RenderGlobal") && is(var44.name, "renderSky", "func_174976_a")) {
               for (AbstractInsnNode var221 : var44.instructions.toArray()) {
                  if (var221 instanceof MethodInsnNode) {
                     MethodInsnNode var262 = (MethodInsnNode)var221;
                     if (var262.getOpcode() == 184
                        && var262.owner.equals("net/optifine/CustomSky")
                        && is(var262.name, "renderSky", "func_174976_a")
                        && var262.desc.equals("(Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/texture/TextureManager;F)V")) {
                        var26.add(customSkyWrapper(var262));
                        var262.owner = var4.name;
                        var262.name = "kazerCustomSky";
                        var5++;
                     }

                     if (is(var262.name, "getHorizon", "func_72919_O") && var262.desc.equals("()D")) {
                        AbstractInsnNode var296 = nextOpcode(var221);
                        AbstractInsnNode var314 = nextOpcode(var296);
                        AbstractInsnNode var323 = nextOpcode(var314);
                        AbstractInsnNode var17 = nextOpcode(var323);
                        AbstractInsnNode var18 = nextOpcode(var17);
                        if (var296 != null
                           && var296.getOpcode() == 103
                           && var314 instanceof VarInsnNode
                           && var314.getOpcode() == 57
                           && var323 instanceof VarInsnNode
                           && var323.getOpcode() == 24
                           && ((VarInsnNode)var323).var == ((VarInsnNode)var314).var
                           && var17 != null
                           && var17.getOpcode() == 14
                           && var18 != null
                           && var18.getOpcode() == 152) {
                           var44.instructions
                              .insert(var323, new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "horizonBoxHeight", "(D)D", false));
                           var5++;
                        }
                     }
                  }
               }
            }

            if ((var2.endsWith("GuiIngame") || var2.endsWith("GuiIngameForge")) && (var44.name.startsWith("render") || is(var44.name, "renderGameOverlay", "func_175180_a"))) {
               for (AbstractInsnNode var222 : var44.instructions.toArray()) {
                  if (var222 instanceof FieldInsnNode && var222.getOpcode() == 180) {
                     FieldInsnNode var263 = (FieldInsnNode)var222;
                     if (is(var263.name, "titlesTimer", "field_175195_w") && var263.desc.equals("I")) {
                        var44.instructions
                           .insert(
                              var222, new MethodInsnNode(184, "com/cheatbreaker/client/kazerui/module/modules/hud/ModuleTitles", "vanillaTimer", "(I)I", false)
                           );
                        var5++;
                     }
                  }

                  if (var222 instanceof MethodInsnNode) {
                     MethodInsnNode var264 = (MethodInsnNode)var222;
                     if (var264.owner.equals("net/minecraft/client/settings/KeyBinding")
                        && is(var264.name, "isKeyDown", "func_151470_d")
                        && var264.desc.equals("()Z")) {
                        var264.setOpcode(184);
                        var264.owner = "cn/kazer/client189/runtime/RuntimeHooks";
                        var264.name = "listKey";
                        var264.desc = "(Lnet/minecraft/client/settings/KeyBinding;)Z";
                        var264.itf = false;
                        var5++;
                     }
                  }
               }
            }

            if (var2.endsWith("RenderManager")) {
               for (AbstractInsnNode var223 : var44.instructions.toArray()) {
                  if (is(var44.name, "doRenderEntity", "func_147939_a") && var223 instanceof FieldInsnNode && var223.getOpcode() == 180) {
                     FieldInsnNode var265 = (FieldInsnNode)var223;
                     if (is(var265.name, "debugBoundingBox", "field_85095_o") && var265.desc.equals("Z")) {
                        var44.instructions
                           .insert(
                              var223, new MethodInsnNode(184, "com/cheatbreaker/client/kazerui/module/modules/visual/ModuleHitbox", "enabled", "(Z)Z", false)
                           );
                        var5++;
                     }
                  }
               }

               if (is(var44.name, "renderDebugBoundingBox", "func_85094_b") && var44.desc.equals("(Lnet/minecraft/entity/Entity;DDDFF)V")) {
                  LabelNode var57 = new LabelNode();
                  InsnList var116 = new InsnList();
                  var116.add(new VarInsnNode(25, 1));
                  var116.add(new VarInsnNode(24, 2));
                  var116.add(new VarInsnNode(24, 4));
                  var116.add(new VarInsnNode(24, 6));
                  var116.add(new VarInsnNode(23, 8));
                  var116.add(new VarInsnNode(23, 9));
                  var116.add(
                     new MethodInsnNode(
                        184, "com/cheatbreaker/client/kazerui/module/modules/visual/ModuleHitbox", "render", "(Lnet/minecraft/entity/Entity;DDDFF)Z", false
                     )
                  );
                  var116.add(new JumpInsnNode(153, var57));
                  var116.add(new InsnNode(177));
                  var116.add(var57);
                  var116.add(new FrameNode(3, 0, null, 0, null));
                  var44.instructions.insert(var116);
                  var5++;
               }
            }

            if (var2.endsWith("RenderGlobal") && is(var44.name, "drawSelectionBox", "func_72731_b")) {
               for (AbstractInsnNode var224 : var44.instructions.toArray()) {
                  if (var224 instanceof MethodInsnNode) {
                     MethodInsnNode var266 = (MethodInsnNode)var224;
                     if (var266.owner.equals("net/minecraft/client/renderer/RenderGlobal")
                        && is(var266.name, "drawSelectionBoundingBox", "func_181561_a")
                        && var266.desc.equals("(Lnet/minecraft/util/AxisAlignedBB;)V")) {
                        var266.owner = "cn/kazer/client189/runtime/RuntimeHooks";
                        var266.name = "blockOutline";
                        var5++;
                     }
                  }
               }
            }

            if (var2.endsWith("RendererLivingEntity")
               && is(var44.name, "setBrightness", "func_177092_a")
               && var44.desc.equals("(Lnet/minecraft/entity/EntityLivingBase;FZ)Z")) {
               String[] var59 = new String[]{"red", "green", "blue", "alpha"};
               int var118 = 0;

               for (AbstractInsnNode var297 : var44.instructions.toArray()) {
                  if (var297 instanceof MethodInsnNode) {
                     MethodInsnNode var315 = (MethodInsnNode)var297;
                     if (var118 < 4
                        && var315.owner.equals("java/nio/FloatBuffer")
                        && var315.name.equals("put")
                        && var315.desc.equals("(F)Ljava/nio/FloatBuffer;")) {
                        var44.instructions
                           .insertBefore(
                              var297,
                              new MethodInsnNode(184, "com/cheatbreaker/client/kazerui/module/modules/visual/ModuleHitColor", var59[var118++], "(F)F", false)
                           );
                        var5++;
                     }
                  }
               }
            }

            if (var2.endsWith("LayerArmorBase") && is(var44.name, "shouldCombineTextures", "func_177142_b") && var44.desc.equals("()Z")) {
               for (AbstractInsnNode var226 : var44.instructions.toArray()) {
                  if (var226.getOpcode() == 172) {
                     var44.instructions
                        .insertBefore(
                           var226,
                           new MethodInsnNode(184, "com/cheatbreaker/client/kazerui/module/modules/visual/ModuleHitColor", "combineArmor", "(Z)Z", false)
                        );
                     var5++;
                  }
               }
            }

            if (var2.endsWith("RenderEntityItem")
               && is(var44.name, "doRender", "func_76986_a")
               && var44.desc.equals("(Lnet/minecraft/entity/item/EntityItem;DDDFF)V")) {
               LabelNode var61 = new LabelNode();
               InsnList var120 = new InsnList();
               var120.add(new VarInsnNode(25, 1));
               var120.add(new VarInsnNode(24, 2));
               var120.add(new VarInsnNode(24, 4));
               var120.add(new VarInsnNode(24, 6));
               var120.add(new VarInsnNode(23, 9));
               var120.add(
                  new MethodInsnNode(
                     184,
                     "com/cheatbreaker/client/kazerui/module/modules/visual/ModuleItems2d",
                     "render",
                     "(Lnet/minecraft/entity/item/EntityItem;DDDF)Z",
                     false
                  )
               );
               var120.add(new JumpInsnNode(153, var61));
               var120.add(new InsnNode(177));
               var120.add(var61);
               var120.add(new FrameNode(3, 0, null, 0, null));
               var44.instructions.insert(var120);
               var5++;
            }

            if (var2.endsWith("EntityRenderer")) {
               String var62 = "com/cheatbreaker/client/kazerui/module/modules/mechanic/CameraHooks";

               for (AbstractInsnNode var268 : var44.instructions.toArray()) {
                  if (var268 instanceof MethodInsnNode) {
                     MethodInsnNode var298 = (MethodInsnNode)var268;
                     if (var298.owner.equals("org/lwjgl/input/Keyboard") && var298.name.equals("isKeyDown") && var298.desc.equals("(I)Z")) {
                        var298.owner = "cn/kazer/client189/runtime/RuntimeHooks";
                        var298.name = "zoomKeyboard";
                        var5++;
                     } else if (var298.owner.equals("org/lwjgl/input/Mouse") && var298.name.equals("isButtonDown") && var298.desc.equals("(I)Z")) {
                        var298.owner = "cn/kazer/client189/runtime/RuntimeHooks";
                        var298.name = "zoomMouse";
                        var5++;
                     } else if (var298.owner.equals("net/minecraft/client/settings/GameSettings")
                        && is(var298.name, "isKeyDown", "func_100015_a")
                        && var298.desc.equals("(Lnet/minecraft/client/settings/KeyBinding;)Z")) {
                        var298.owner = "cn/kazer/client189/runtime/RuntimeHooks";
                        var298.name = "zoomKey";
                        var5++;
                     }

                     if (is(var298.name, "setAngles", "func_70082_c") && var298.desc.equals("(FF)V")) {
                        var298.setOpcode(184);
                        var298.owner = "com/cheatbreaker/client/kazerui/module/modules/mechanic/CameraHooks";
                        var298.name = "turn";
                        var298.desc = "(Lnet/minecraft/entity/Entity;FF)V";
                        var298.itf = false;
                        var5++;
                     }
                  }

                  if (var268 instanceof FieldInsnNode && var268.getOpcode() == 180) {
                     FieldInsnNode var299 = (FieldInsnNode)var268;
                     if (var299.owner.equals("net/minecraft/client/settings/GameSettings")
                        && is(var299.name, "mouseSensitivity", "field_74341_c")
                        && var299.desc.equals("F")) {
                        var44.instructions
                           .insert(
                              var268,
                              new MethodInsnNode(184, "com/cheatbreaker/client/kazerui/module/modules/mechanic/ModuleZoom", "sensitivity", "(F)F", false)
                           );
                        var5++;
                     }
                  }

                  if (is(var44.name, "orientCamera", "func_78467_g") && var268 instanceof FieldInsnNode && var268.getOpcode() == 180) {
                     FieldInsnNode var300 = (FieldInsnNode)var268;
                     String var316 = null;
                     if (is(var300.name, "rotationYaw", "field_70177_z")) {
                        var316 = "yaw";
                     } else if (is(var300.name, "rotationPitch", "field_70125_A")) {
                        var316 = "pitch";
                     } else if (is(var300.name, "prevRotationYaw", "field_70126_B")) {
                        var316 = "previousYaw";
                     } else if (is(var300.name, "prevRotationPitch", "field_70127_C")) {
                        var316 = "previousPitch";
                     }

                     if (var316 != null && var300.desc.equals("F")) {
                        var44.instructions
                           .set(
                              var268,
                              new MethodInsnNode(
                                 184, "com/cheatbreaker/client/kazerui/module/modules/mechanic/CameraHooks", var316, "(Lnet/minecraft/entity/Entity;)F", false
                              )
                           );
                        var5++;
                     }
                  }
               }
            }

            if (var2.endsWith("NetHandlerPlayClient") && is(var44.name, "handleEntityStatus", "func_147236_a")) {
               for (AbstractInsnNode var228 : var44.instructions.toArray()) {
                  if (var228 instanceof MethodInsnNode) {
                     MethodInsnNode var269 = (MethodInsnNode)var228;
                     if (var269.desc.equals("(B)V") && is(var269.name, "handleStatusUpdate", "func_70103_a")) {
                        InsnList var301 = new InsnList();
                        var301.add(new InsnNode(92));
                        var301.add(
                           new MethodInsnNode(
                              184, "com/cheatbreaker/client/kazerui/module/modules/hud/CombatHud", "status", "(Lnet/minecraft/entity/Entity;B)V", false
                           )
                        );
                        var44.instructions.insertBefore(var228, var301);
                        var5++;
                     }
                  }
               }
            }

            if (var2.endsWith("LayerCape")
               && is(var44.name, "doRenderLayer", "func_177141_a")
               && var44.desc.startsWith("(Lnet/minecraft/client/entity/AbstractClientPlayer;")) {
               LabelNode var64 = new LabelNode();
               InsnList var123 = new InsnList();
               var123.add(new VarInsnNode(25, 1));
               var123.add(
                  new MethodInsnNode(
                     184, "cn/kazer/client189/runtime/KazerCosmeticLayer", "customCape", "(Lnet/minecraft/client/entity/AbstractClientPlayer;)Z", false
                  )
               );
               var123.add(new JumpInsnNode(153, var64));
               var123.add(new InsnNode(177));
               var123.add(var64);
               var44.instructions.insert(var123);
               var5++;
            }

            if (var2.endsWith("GuiChat") && is(var44.name, "keyTyped", "func_73869_a")) {
               String var65 = null;

               for (FieldNode var180 : var4.fields) {
                  if (is(var180.name, "inputField", "field_146415_a")) {
                     var65 = var180.name;
                  }
               }

               if (var65 == null) {
                  throw new IllegalStateException("Native chat input field was not found");
               }

               for (AbstractInsnNode var270 : var44.instructions.toArray()) {
                  if (var270 instanceof MethodInsnNode) {
                     MethodInsnNode var302 = (MethodInsnNode)var270;
                     if (var302.owner.equals("net/minecraft/client/Minecraft") && is(var302.name, "displayGuiScreen", "func_147108_a")) {
                        InsnList var317 = new InsnList();
                        var317.add(new VarInsnNode(25, 0));
                        var317.add(new FieldInsnNode(180, var4.name, var65, "Lnet/minecraft/client/gui/GuiTextField;"));
                        var317.add(new VarInsnNode(21, 2));
                        var44.instructions.insertBefore(var302, var317);
                        var44.instructions
                           .set(
                              var302,
                              new MethodInsnNode(
                                 184,
                                 "cn/kazer/client189/runtime/RuntimeHooks",
                                 "chatClose",
                                 "(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/gui/GuiScreen;Lnet/minecraft/client/gui/GuiTextField;I)V",
                                 false
                              )
                           );
                     }
                  }
               }

               var5++;
            }

            if (var2.endsWith("GuiChat") && is(var44.name, "drawScreen", "func_73863_a")) {
               for (AbstractInsnNode var230 : var44.instructions.toArray()) {
                  if (var230 instanceof MethodInsnNode) {
                     MethodInsnNode var271 = (MethodInsnNode)var230;
                     if (is(var271.name, "drawRect", "func_73734_a") && var271.desc.equals("(IIIII)V")) {
                        var44.instructions.set(var271, new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "chatInputRect", "(IIIII)V", false));
                     }
                  }
               }

               var5++;
            }

            if (var2.endsWith("GuiScreen") && is(var44.name, "sendChatMessage", "func_175281_b") && var44.desc.equals("(Ljava/lang/String;Z)V")) {
               InsnList var67 = new InsnList();
               LabelNode var127 = new LabelNode();
               var67.add(new VarInsnNode(25, 1));
               var67.add(new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "chatSend", "(Ljava/lang/String;)Ljava/lang/String;", false));
               var67.add(new VarInsnNode(58, 1));
               var67.add(new VarInsnNode(25, 1));
               var67.add(new JumpInsnNode(199, var127));
               var67.add(new InsnNode(177));
               var67.add(var127);
               var44.instructions.insert(var67);
               var5++;
            }

            if (var2.endsWith("EntityPlayerSP") && is(var44.name, "onLivingUpdate", "func_70636_d")) {
               for (AbstractInsnNode var231 : var44.instructions.toArray()) {
                  if (var231 instanceof FieldInsnNode && var231.getOpcode() == 181 && is(((FieldInsnNode)var231).name, "sprintToggleTimer", "field_71156_d")) {
                     var44.instructions.insertBefore(var231, new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "sprintTimer", "(I)I", false));
                     var5++;
                  }
               }
            }

            if (var2.endsWith("Minecraft") && is(var44.name, "runTick", "func_71407_l")) {
               for (AbstractInsnNode var232 : var44.instructions.toArray()) {
                  if (var232 instanceof MethodInsnNode && is(((MethodInsnNode)var232).name, "handleKeyboardInput", "func_146282_l")) {
                     for (AbstractInsnNode var272 = var232.getPrevious(); var272 != null; var272 = var272.getPrevious()) {
                        if (var272 instanceof MethodInsnNode
                           && ((MethodInsnNode)var272).owner.equals("org/lwjgl/input/Keyboard")
                           && ((MethodInsnNode)var272).name.equals("getEventKeyState")) {
                           var44.instructions.set(var272, new MethodInsnNode(184, "cn/kazer/client189/input/ImeHooks", "shouldDispatchInGui", "()Z", false));
                           var5++;
                           break;
                        }
                     }
                  }
               }
            }

            if (var2.endsWith("GuiScreen") && is(var44.name, "handleKeyboardInput", "func_146282_l")) {
               for (AbstractInsnNode var233 : var44.instructions.toArray()) {
                  if (var233 instanceof MethodInsnNode) {
                     MethodInsnNode var273 = (MethodInsnNode)var233;
                     if (var273.owner.equals("org/lwjgl/input/Keyboard") && var273.name.equals("getEventKeyState")) {
                        var44.instructions.set(var273, new MethodInsnNode(184, "cn/kazer/client189/input/ImeHooks", "shouldDispatch", "()Z", false));
                        var5++;
                     }
                  }
               }
            }

            if (var2.endsWith("GuiTextField") && is(var44.name, "drawTextBox", "func_146194_f")) {
               for (AbstractInsnNode var234 : var44.instructions.toArray()) {
                  if (var234.getOpcode() == 177) {
                     InsnList var274 = new InsnList();
                     var274.add(new VarInsnNode(25, 0));
                     var274.add(new MethodInsnNode(184, "cn/kazer/client189/input/ImeHooks", "textField", "(Ljava/lang/Object;)V", false));
                     var44.instructions.insertBefore(var234, var274);
                     var5++;
                  }
               }
            }

            if (var2.endsWith("Minecraft") && is(var44.name, "toggleFullscreen", "func_71352_k")) {
               int var72 = 0;

               for (AbstractInsnNode var275 : var44.instructions.toArray()) {
                  if (var275 instanceof MethodInsnNode) {
                     MethodInsnNode var303 = (MethodInsnNode)var275;
                     if (var303.owner.equals("org/lwjgl/opengl/Display") && var303.name.equals("setFullscreen") && var303.desc.equals("(Z)V")) {
                        var72++;
                     }
                  }
               }

               if (var72 == 1 && var44.desc.equals("()V")) {
                  LabelNode var133 = new LabelNode();
                  InsnList var188 = new InsnList();
                  var188.add(new VarInsnNode(25, 0));
                  var188.add(new MethodInsnNode(184, "cn/kazer/client189/input/FullscreenTransition", "toggle", "(Lnet/minecraft/client/Minecraft;)Z", false));
                  var188.add(new JumpInsnNode(153, var133));
                  var188.add(new InsnNode(177));
                  var188.add(var133);
                  var44.instructions.insert(var188);
                  var5++;
               }

               for (AbstractInsnNode var276 : var44.instructions.toArray()) {
                  if (var276.getOpcode() == 177) {
                     var44.instructions
                        .insertBefore(
                           var276, new MethodInsnNode(184, "cn/kazer/client189/input/WindowsFullscreenImeSupport", "updateWindowLayer", "()V", false)
                        );
                     var5++;
                  }
               }
            }

            if (var2.endsWith("RenderGlobal") && is(var44.name, "updateChunks", "func_174967_a") && var44.desc.equals("(J)V")) {
               InsnList var73 = new InsnList();
               var73.add(new VarInsnNode(22, 1));
               var73.add(new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "chunkDeadline", "(J)J", false));
               var73.add(new VarInsnNode(55, 1));
               var44.instructions.insert(var73);
               var5++;
            }

            if (var2.endsWith("ServerList") && is(var44.name, "loadServerList", "func_78853_a")) {
               for (AbstractInsnNode var237 : var44.instructions.toArray()) {
                  if (var237.getOpcode() == 177) {
                     InsnList var277 = new InsnList();
                     var277.add(new VarInsnNode(25, 0));
                     var277.add(new MethodInsnNode(184, "cn/kazer/client189/runtime/KazerPinnedServers", "loaded", "(Ljava/lang/Object;)V", false));
                     var44.instructions.insertBefore(var237, var277);
                     var5++;
                  }
               }
            }

            if (var2.endsWith("ServerList") && is(var44.name, "saveServerList", "func_78855_b")) {
               for (AbstractInsnNode var238 : var44.instructions.toArray()) {
                  if (var238 instanceof MethodInsnNode) {
                     MethodInsnNode var278 = (MethodInsnNode)var238;
                     if (is(var278.name, "getNBTCompound", "func_78836_a")) {
                        var44.instructions
                           .insertBefore(
                              var278,
                              new MethodInsnNode(
                                 184, "cn/kazer/client189/runtime/KazerPinnedServers", "serverNbt", "(Ljava/lang/Object;)Ljava/lang/Object;", false
                              )
                           );
                        var44.instructions.set(var278, new TypeInsnNode(192, Type.getReturnType(var278.desc).getInternalName()));
                        var5++;
                     } else if (is(var278.name, "appendTag", "func_74742_a")) {
                        var44.instructions
                           .set(
                              var278,
                              new MethodInsnNode(
                                 184, "cn/kazer/client189/runtime/KazerPinnedServers", "appendNbt", "(Ljava/lang/Object;Ljava/lang/Object;)V", false
                              )
                           );
                        var5++;
                     }
                  }
               }
            }

            if (var2.endsWith("ServerList") && is(var44.name, "swapServers", "func_78857_a")) {
               InsnList var76 = new InsnList();
               LabelNode var137 = new LabelNode();
               var76.add(new VarInsnNode(25, 0));
               var76.add(new VarInsnNode(21, 1));
               var76.add(new VarInsnNode(21, 2));
               var76.add(new MethodInsnNode(184, "cn/kazer/client189/runtime/KazerPinnedServers", "blockSwap", "(Ljava/lang/Object;II)Z", false));
               var76.add(new JumpInsnNode(153, var137));
               var76.add(new InsnNode(177));
               var76.add(var137);
               var44.instructions.insert(var76);
               var5++;
            }

            if (var2.endsWith("ServerSelectionList") && var44.name.equals("func_148195_a")) {
               TypeInsnNode var77 = null;

               for (AbstractInsnNode var279 : var44.instructions.toArray()) {
                  if (var279 instanceof TypeInsnNode && var279.getOpcode() == 187 && ((TypeInsnNode)var279).desc.endsWith("ServerListEntryNormal")) {
                     var77 = (TypeInsnNode)var279;
                  }

                  if (var77 != null
                     && var279 instanceof MethodInsnNode
                     && ((MethodInsnNode)var279).name.equals("<init>")
                     && ((MethodInsnNode)var279).owner.endsWith("ServerListEntryNormal")) {
                     AbstractInsnNode var304 = var77.getNext();

                     while (var304 != null && var304.getOpcode() < 0) {
                        var304 = var304.getNext();
                     }

                     if (var304 == null || var304.getOpcode() != 89) {
                        throw new IllegalStateException("Unexpected server row constructor");
                     }

                     String var318 = ((MethodInsnNode)var279).owner;
                     var44.instructions.remove(var304);
                     var44.instructions.remove(var77);
                     var44.instructions
                        .insertBefore(
                           var279,
                           new MethodInsnNode(
                              184, "cn/kazer/client189/runtime/KazerPinnedServers", "entry", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", false
                           )
                        );
                     var44.instructions.set(var279, new TypeInsnNode(192, var318));
                     var77 = null;
                     var5++;
                  }
               }
            }

            if (var2.endsWith("GuiMultiplayer") && is(var44.name, "selectServer", "func_146790_a")) {
               for (AbstractInsnNode var240 : var44.instructions.toArray()) {
                  if (var240.getOpcode() == 177) {
                     InsnList var280 = new InsnList();
                     var280.add(new VarInsnNode(25, 0));
                     var280.add(new VarInsnNode(21, 1));
                     var280.add(new MethodInsnNode(184, "cn/kazer/client189/runtime/KazerPinnedServers", "selected", "(Ljava/lang/Object;I)V", false));
                     var44.instructions.insertBefore(var240, var280);
                     var5++;
                  }
               }
            }

            if (var2.endsWith("GuiMultiplayer") && (var44.name.equals("func_175392_a") || var44.name.equals("func_175394_b"))) {
               InsnList var79 = new InsnList();
               var79.add(new VarInsnNode(25, 0));
               var79.add(new VarInsnNode(25, 1));
               var79.add(new VarInsnNode(21, 2));
               var79.add(new InsnNode(var44.name.equals("func_175392_a") ? 4 : 3));
               var79.add(
                  new MethodInsnNode(184, "cn/kazer/client189/runtime/KazerPinnedServers", "canMove", "(Ljava/lang/Object;Ljava/lang/Object;IZ)Z", false)
               );
               var79.add(new InsnNode(172));
               var44.instructions.insert(var79);
               var5++;
            }

            if (var2.equals("net.minecraft.client.Minecraft")) {
               for (AbstractInsnNode var241 : var44.instructions.toArray()) {
                  if (var241 instanceof MethodInsnNode) {
                     MethodInsnNode var281 = (MethodInsnNode)var241;
                     if (var281.owner.equals("org/lwjgl/opengl/Display") && var281.name.equals("setTitle") && var281.desc.equals("(Ljava/lang/String;)V")) {
                        InsnList var305 = new InsnList();
                        var305.add(new InsnNode(87));
                        var305.add(new LdcInsnNode("1.8.9"));
                        var305.add(
                           new MethodInsnNode(
                              184, "com/cheatbreaker/client/util/KazerBuildInfo", "windowTitle", "(Ljava/lang/String;)Ljava/lang/String;", false
                           )
                        );
                        var44.instructions.insertBefore(var281, var305);
                        var5++;
                     }
                  }
               }
            }

            if (var2.endsWith("Minecraft") && is(var44.name, "drawSplashScreen", "func_180510_a")) {
               InsnList var81 = new InsnList();
               var81.add(new VarInsnNode(25, 1));
               var81.add(new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "splash", "(Ljava/lang/Object;)V", false));
               var81.add(new InsnNode(177));
               var44.instructions.insert(var81);
               var5++;
            }

            if (var2.endsWith("GameSettings") && is(var44.name, "loadOptions", "func_74300_a")) {
               var44.instructions.insert(new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "optionsLoad", "()V", false));
               var5++;
            }

            if (var2.endsWith("GameSettings") && is(var44.name, "saveOptions", "func_74303_b")) {
               for (AbstractInsnNode var242 : var44.instructions.toArray()) {
                  if (var242.getOpcode() == 177) {
                     var44.instructions.insertBefore(var242, new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "optionsSave", "()V", false));
                     var5++;
                  }
               }
            }

            if (var2.endsWith("BlockRendererDispatcher") && is(var44.name, "renderBlock", "func_175018_a")) {
               InsnList var83 = new InsnList();
               LabelNode var142 = new LabelNode();
               var83.add(new VarInsnNode(25, 1));
               var83.add(new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "clearGlass", "(Ljava/lang/Object;)Z", false));
               var83.add(new JumpInsnNode(153, var142));
               var83.add(new InsnNode(4));
               var83.add(new InsnNode(172));
               var83.add(var142);
               var44.instructions.insert(var83);
               var5++;
            }

            if (var2.endsWith("GuiPlayerTabOverlay")) {
               if (is(var44.name, "drawPing", "func_175245_a")) {
                  InsnList var84 = new InsnList();
                  LabelNode var143 = new LabelNode();
                  var84.add(new VarInsnNode(21, 1));
                  var84.add(new VarInsnNode(21, 2));
                  var84.add(new VarInsnNode(21, 3));
                  var84.add(new VarInsnNode(25, 4));
                  var84.add(new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "tabPing", "(IIILjava/lang/Object;)Z", false));
                  var84.add(new JumpInsnNode(154, var143));
                  var84.add(new InsnNode(177));
                  var84.add(var143);
                  var44.instructions.insert(var84);
                  var5++;
               }

               if (is(var44.name, "getPlayerName", "func_175243_a")) {
                  for (AbstractInsnNode var243 : var44.instructions.toArray()) {
                     if (var243.getOpcode() == 176) {
                        InsnList var282 = new InsnList();
                        var282.add(new VarInsnNode(25, 1));
                        var282.add(
                           new MethodInsnNode(
                              184, "cn/kazer/client189/runtime/RuntimeHooks", "tabName", "(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;", false
                           )
                        );
                        var44.instructions.insertBefore(var243, var282);
                        var5++;
                     }
                  }
               }

               if (is(var44.name, "renderPlayerlist", "func_175249_a") || is(var44.name, "drawScoreboardValues", "func_175247_a")) {
                  for (AbstractInsnNode var244 : var44.instructions.toArray()) {
                     if (var244 instanceof FieldInsnNode && var244.getOpcode() == 180) {
                        FieldInsnNode var283 = (FieldInsnNode)var244;
                        String var306 = is(var283.name, "header", "field_175256_i")
                           ? "header"
                           : (is(var283.name, "footer", "field_175255_h") ? "footer" : null);
                        if (var306 != null && var283.desc.equals("Lnet/minecraft/util/IChatComponent;")) {
                           var44.instructions
                              .insert(
                                 var244,
                                 new MethodInsnNode(
                                    184,
                                    "com/cheatbreaker/client/kazerui/module/modules/mechanic/ModuleTab",
                                    var306,
                                    "(Lnet/minecraft/util/IChatComponent;)Lnet/minecraft/util/IChatComponent;",
                                    false
                                 )
                              );
                           var5++;
                        }
                     }
                  }

                  for (AbstractInsnNode var245 : var44.instructions.toArray()) {
                     if (var245 instanceof MethodInsnNode) {
                        MethodInsnNode var284 = (MethodInsnNode)var245;
                        if (is(var284.name, "drawRect", "func_73734_a") && var284.desc.equals("(IIIII)V")) {
                           var44.instructions
                              .insertBefore(
                                 var245,
                                 new MethodInsnNode(184, "com/cheatbreaker/client/kazerui/module/modules/mechanic/ModuleTab", "boxColor", "(I)I", false)
                              );
                           var5++;
                        }

                        if (is(var284.name, "drawStringWithShadow", "func_175063_a") && var284.desc.equals("(Ljava/lang/String;FFI)I")) {
                           var44.instructions
                              .set(
                                 var284,
                                 new MethodInsnNode(
                                    184, "cn/kazer/client189/runtime/RuntimeHooks", "tabText", "(Ljava/lang/Object;Ljava/lang/String;FFI)I", false
                                 )
                              );
                           var5++;
                        }
                     }
                  }
               }
            }

            if (var2.endsWith("GuiScreen") && is(var44.name, "drawWorldBackground", "func_146270_b")) {
               guard(var44, "drawGuiBackground");
               var5++;
            }

            if (var2.endsWith("GuiAchievement") && is(var44.name, "updateAchievementWindow", "func_146254_a")) {
               guard(var44, "hideAchievements");
               var5++;
            }

            if (var2.endsWith("InventoryPlayer") && is(var44.name, "changeCurrentItem", "func_70453_c")) {
               InsnList var88 = new InsnList();
               var88.add(new VarInsnNode(21, 1));
               var88.add(new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "hotbarScroll", "(I)I", false));
               var88.add(new VarInsnNode(54, 1));
               var44.instructions.insert(var88);
               var5++;
            }

            if (var2.endsWith("InventoryEffectRenderer") && is(var44.name, "updateActivePotionEffects", "func_175378_g")) {
               for (AbstractInsnNode var246 : var44.instructions.toArray()) {
                  if (var246.getOpcode() == 177) {
                     InsnList var285 = new InsnList();
                     var285.add(new VarInsnNode(25, 0));
                     var285.add(new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "centerInventory", "(Ljava/lang/Object;)V", false));
                     var44.instructions.insertBefore(var246, var285);
                     var5++;
                  }
               }
            }

            if (var2.endsWith("RenderManager") && is(var44.name, "shouldRender", "func_178635_a")
               || var2.endsWith("TileEntityRendererDispatcher") && is(var44.name, "renderTileEntity", "func_180546_a")) {
               boolean var90 = var2.endsWith("RenderManager");
               InsnList var148 = new InsnList();
               LabelNode var200 = new LabelNode();
               var148.add(new VarInsnNode(25, 1));
               var148.add(new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", var90 ? "hideEntity" : "hideTile", "(Ljava/lang/Object;)Z", false));
               var148.add(new JumpInsnNode(153, var200));
               if (var90) {
                  var148.add(new InsnNode(3));
               }

               var148.add(new InsnNode(var90 ? 172 : 177));
               var148.add(var200);
               var44.instructions.insert(var148);
               var5++;
            }

            if (var2.endsWith("RenderItem") && is(var44.name, "renderEffect", "func_180451_a")) {
               guard(var44, "hideGlint");
               var5++;
            }

            if (var2.endsWith("RenderItem")) {
               for (AbstractInsnNode var247 : var44.instructions.toArray()) {
                  if (var247 instanceof MethodInsnNode) {
                     MethodInsnNode var286 = (MethodInsnNode)var247;
                     if (is(var286.name, "hasEffect", "func_77962_s") && var286.desc.equals("()Z")) {
                        var44.instructions
                           .set(var286, new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "itemGlint", "(Ljava/lang/Object;)Z", false));
                        var5++;
                     }
                  }
               }
            }

            if (var2.endsWith("CustomSky") && var44.name.equals("renderSky")) {
               for (AbstractInsnNode var248 : var44.instructions.toArray()) {
                  if (var248 instanceof IntInsnNode
                     && ((IntInsnNode)var248).operand == 8
                     && var248.getNext() instanceof JumpInsnNode
                     && var248.getNext().getOpcode() == 161) {
                     ((IntInsnNode)var248).operand = 0;
                     var5++;
                  }
               }
            }

            if (var2.endsWith("EffectRenderer")
               && (
                  is(var44.name, "addEffect", "func_78873_a")
                     || var44.name.equals("a") && Type.getArgumentTypes(var44.desc).length == 1 && var44.desc.endsWith(";)V")
               )) {
               guard(var44, "dropParticle");
               var5++;
            }

            if (var2.equals("net.minecraft.client.Minecraft")
               && (is(var44.name, "getLimitFramerate", "func_90020_K") || var44.name.equals("j") && var44.desc.equals("()I"))) {
               for (AbstractInsnNode var249 : var44.instructions.toArray()) {
                  if (var249.getOpcode() == 172) {
                     var44.instructions.insertBefore(var249, new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "frameLimit", "(I)I", false));
                  }
               }

               var5++;
            }

            if (var2.endsWith("EntityRenderer")) {
               if (is(var44.name, "renderWorldPass", "func_175068_a") || var44.name.equals("a") && var44.desc.equals("(IFJ)V")) {
                  InsnList var94 = new InsnList();
                  var94.add(new VarInsnNode(23, 2));
                  var94.add(new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "worldBegin", "(F)V", false));
                  var44.instructions.insert(var94);
                  var5++;

                  for (AbstractInsnNode var287 : var44.instructions.toArray()) {
                     if (var287 instanceof MethodInsnNode) {
                        MethodInsnNode var307 = (MethodInsnNode)var287;
                        if (is(var307.name, "setupCameraTransform", "func_78479_a") || var307.name.equals("a") && var307.desc.equals("(FI)V")) {
                           InsnList var319 = new InsnList();
                           var319.add(new VarInsnNode(23, 2));
                           var319.add(new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "cameraReady", "(F)V", false));
                           var44.instructions.insert(var307, var319);
                           var5++;
                        }

                        if (is(var307.name, "renderHand", "field_175074_C") || var307.name.equals("b") && var307.desc.equals("(FI)V")) {
                           InsnList var320 = new InsnList();
                           var320.add(new VarInsnNode(23, 2));
                           var320.add(new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "worldEnd", "(F)V", false));
                           var44.instructions.insertBefore(var307, var320);
                           var5++;
                        }
                     }
                  }
               }

               if (is(var44.name, "updateCameraAndRender", "func_181560_a") || var44.name.equals("a") && var44.desc.equals("(FJ)V")) {
                  for (AbstractInsnNode var251 : var44.instructions.toArray()) {
                     if (var251 instanceof MethodInsnNode
                        && (
                           is(((MethodInsnNode)var251).name, "renderWorld", "func_78471_a")
                              || ((MethodInsnNode)var251).name.equals("b") && ((MethodInsnNode)var251).desc.equals("(FJ)V")
                        )) {
                        var44.instructions.insert(var251, new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "motionBlur", "()V", false));
                        var5++;
                     }
                  }
               }

               if (is(var44.name, "updateLightmap", "func_78472_g") || var44.name.equals("g") && var44.desc.equals("(F)V")) {
                  for (AbstractInsnNode var252 : var44.instructions.toArray()) {
                     if (var252 instanceof MethodInsnNode) {
                        MethodInsnNode var288 = (MethodInsnNode)var252;
                        if (is(var288.name, "updateDynamicTexture", "func_110564_a") && var288.desc.equals("()V")) {
                           InsnList var308 = new InsnList();
                           var308.add(new InsnNode(89));
                           var308.add(new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "lightingTexture", "(Ljava/lang/Object;)V", false));
                           var44.instructions.insertBefore(var288, var308);
                           var5++;
                        }
                     }
                  }

                  for (AbstractInsnNode var253 : var44.instructions.toArray()) {
                     if (var253 instanceof FieldInsnNode
                        && (is(((FieldInsnNode)var253).name, "gammaSetting", "field_74333_Y") || ((FieldInsnNode)var253).name.equals("aJ"))) {
                        var44.instructions.insert(var253, new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "gamma", "(F)F", false));
                        var5++;
                     }
                  }
               }
            }

            if (var2.endsWith("NetHandlerPlayClient") && is(var44.name, "handleCustomPayload", "func_147240_a")) {
               InsnList var98 = new InsnList();
               var98.add(new VarInsnNode(25, 1));
               var98.add(new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "pluginMessage", "(Ljava/lang/Object;)V", false));
               var44.instructions.insert(var98);
               var5++;
            }

            if (var2.endsWith("NetHandlerPlayClient")
               && (is(var44.name, "handleJoinGame", "func_147282_a") || is(var44.name, "handleRespawn", "func_147280_a"))) {
               InsnList var99 = new InsnList();
               var99.add(new InsnNode(is(var44.name, "handleJoinGame", "func_147282_a") ? 4 : 3));
               var99.add(new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "joinedWorld", "(Z)V", false));
               var44.instructions.insert(var99);
               var5++;
               if (is(var44.name, "handleJoinGame", "func_147282_a")) {
                  for (AbstractInsnNode var289 : var44.instructions.toArray()) {
                     if (var289.getOpcode() == 177) {
                        var44.instructions
                           .insertBefore(var289, new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "registerApollo", "()V", false));
                     }
                  }
               }
            }

            if (var2.endsWith("NetHandlerPlayClient") && is(var44.name, "handleKeepAlive", "func_147272_a")) {
               for (AbstractInsnNode var255 : var44.instructions.toArray()) {
                  if (var255.getOpcode() == 177) {
                     var44.instructions.insertBefore(var255, new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "keepAlive", "()V", false));
                  }
               }

               var5++;
            }

            if (var2.endsWith("GuiIngame") && is(var44.name, "renderScoreboard", "func_180475_a")) {
               guard(var44, "hideVanillaScoreboard");
               var5++;
            }

            if (var2.endsWith("GuiNewChat") && is(var44.name, "getChatComponent", "func_146236_a")) {
               InsnList var101 = new InsnList();
               LabelNode var158 = new LabelNode();
               var101.add(new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "customChatHit", "()Z", false));
               var101.add(new JumpInsnNode(153, var158));
               var101.add(new VarInsnNode(25, 0));
               var101.add(new VarInsnNode(21, 1));
               var101.add(new VarInsnNode(21, 2));
               var101.add(
                  new MethodInsnNode(
                     184, "cn/kazer/client189/runtime/RuntimeHooks", "chatComponent", "(Ljava/lang/Object;II)Lnet/minecraft/util/IChatComponent;", false
                  )
               );
               var101.add(new InsnNode(176));
               var101.add(var158);
               var44.instructions.insert(var101);
               var5++;
            }

            if (var2.endsWith("GuiNewChat") && is(var44.name, "setChatLine", "func_146237_a")) {
               InsnList var102 = new InsnList();
               LabelNode var159 = new LabelNode();
               var102.add(new VarInsnNode(25, 0));
               var102.add(new VarInsnNode(25, 1));
               var102.add(new VarInsnNode(21, 2));
               var102.add(new VarInsnNode(21, 3));
               var102.add(new VarInsnNode(21, 4));
               var102.add(
                  new MethodInsnNode(
                     184,
                     "com/cheatbreaker/client/kazerui/module/modules/mechanic/ChatRuntime",
                     "insert",
                     "(Ljava/lang/Object;Lnet/minecraft/util/IChatComponent;IIZ)Z",
                     false
                  )
               );
               var102.add(new JumpInsnNode(153, var159));
               var102.add(new InsnNode(177));
               var102.add(var159);
               var44.instructions.insert(var102);
               var5++;
            }

            if (var2.endsWith("GuiNewChat") && is(var44.name, "refreshChat", "func_146245_b")) {
               InsnList var103 = new InsnList();
               LabelNode var160 = new LabelNode();
               var103.add(new VarInsnNode(25, 0));
               var103.add(
                  new MethodInsnNode(184, "com/cheatbreaker/client/kazerui/module/modules/mechanic/ChatRuntime", "refresh", "(Ljava/lang/Object;)Z", false)
               );
               var103.add(new JumpInsnNode(153, var160));
               var103.add(new InsnNode(177));
               var103.add(var160);
               var44.instructions.insert(var103);
               var5++;
            }

            if (var2.endsWith("GuiNewChat") && is(var44.name, "drawChat", "func_146230_a")) {
               InsnList var104 = new InsnList();
               LabelNode var161 = new LabelNode();
               var104.add(new VarInsnNode(25, 0));
               var104.add(new VarInsnNode(21, 1));
               var104.add(
                  new MethodInsnNode(184, "com/cheatbreaker/client/kazerui/module/modules/mechanic/ChatRuntime", "draw", "(Ljava/lang/Object;I)Z", false)
               );
               var104.add(new JumpInsnNode(153, var161));
               var104.add(new InsnNode(177));
               var104.add(var161);
               var44.instructions.insert(var104);
               InsnList var210 = new InsnList();
               LabelNode var256 = new LabelNode();
               var210.add(new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "beginChat", "()Z", false));
               var210.add(new JumpInsnNode(154, var256));
               var210.add(new InsnNode(177));
               var210.add(var256);
               var44.instructions.insert(var210);

               for (AbstractInsnNode var324 : var44.instructions.toArray()) {
                  if (var324.getOpcode() == 177 && var324 != var210.getFirst()) {
                     boolean var326 = false;

                     for (AbstractInsnNode var328 = var256.getNext(); var328 != null; var328 = var328.getNext()) {
                        if (var328 == var324) {
                           var326 = true;
                           break;
                        }
                     }

                     if (var326) {
                        var44.instructions.insertBefore(var324, new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "endChat", "()V", false));
                     }
                  }
               }

               for (AbstractInsnNode var325 : var44.instructions.toArray()) {
                  if (var325 instanceof MethodInsnNode) {
                     MethodInsnNode var327 = (MethodInsnNode)var325;
                     if (is(var327.name, "drawRect", "func_73734_a") && var327.desc.equals("(IIIII)V")) {
                        var44.instructions.set(var327, new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "chatRect", "(IIIII)V", false));
                     }
                  }
               }

               var5++;
            }
         }

         var4.methods.addAll(var26);
         if (var5 == 0) {
            return var3;
         } else {
            ClassWriter var35 = new ClassWriter(1);
            var4.accept(var35);
            System.out.println("[KC 1.8.9] Runtime adapters: " + var2 + " (" + var5 + ")");
            return var35.toByteArray();
         }
      } else {
         return var3;
      }
   }

   private static void addChatGetter(ClassNode var0, String var1, String var2, String var3, String var4, int var5) {
      FieldNode var6 = null;

      for (FieldNode var8 : var0.fields) {
         if (is(var8.name, var2, var3) && var8.desc.equals(var4)) {
            var6 = var8;
         }
      }

      if (var6 == null) {
         throw new IllegalStateException("Native chat field was not found: " + var0.name + "." + var3);
      } else {
         MethodNode var9 = new MethodNode(1, var1, "()" + var4, null, null);
         var9.instructions.add(new VarInsnNode(25, 0));
         var9.instructions.add(new FieldInsnNode(180, var0.name, var6.name, var4));
         var9.instructions.add(new InsnNode(var5));
         var0.methods.add(var9);
      }
   }

   private static boolean is(String var0, String var1, String var2) {
      return var1.equals(var0) || var2.equals(var0);
   }

   private static MethodNode customSkyWrapper(MethodInsnNode var0) {
      MethodNode var1 = new MethodNode(10, "kazerCustomSky", var0.desc, null, null);
      LabelNode var2 = new LabelNode();
      LabelNode var3 = new LabelNode();
      LabelNode var4 = new LabelNode();
      var1.instructions.add(new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "customSkyBegin", "()V", false));
      var1.instructions.add(var2);
      var1.instructions.add(new VarInsnNode(25, 0));
      var1.instructions.add(new VarInsnNode(25, 1));
      var1.instructions.add(new VarInsnNode(23, 2));
      var1.instructions.add(new MethodInsnNode(184, var0.owner, var0.name, var0.desc, false));
      var1.instructions.add(var3);
      var1.instructions.add(new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "customSkyEnd", "()V", false));
      var1.instructions.add(new InsnNode(177));
      var1.instructions.add(var4);
      var1.instructions
         .add(
            new FrameNode(
               0,
               3,
               new Object[]{"net/minecraft/world/World", "net/minecraft/client/renderer/texture/TextureManager", Opcodes.FLOAT},
               1,
               new Object[]{"java/lang/Throwable"}
            )
         );
      var1.instructions.add(new MethodInsnNode(184, "cn/kazer/client189/runtime/RuntimeHooks", "customSkyEnd", "()V", false));
      var1.instructions.add(new InsnNode(191));
      var1.tryCatchBlocks.add(new TryCatchBlockNode(var2, var3, var4, null));
      var1.maxStack = 3;
      var1.maxLocals = 3;
      return var1;
   }

   private static AbstractInsnNode nextOpcode(AbstractInsnNode var0) {
      if (var0 == null) {
         return null;
      } else {
         do {
            var0 = var0.getNext();
         } while (var0 != null && var0.getOpcode() < 0);

         return var0;
      }
   }

   private static void guard(MethodNode var0, String var1) {
      guardOwner(var0, "cn/kazer/client189/runtime/RuntimeHooks", var1);
   }

   private static void guardOwner(MethodNode var0, String var1, String var2) {
      InsnList var3 = new InsnList();
      LabelNode var4 = new LabelNode();
      var3.add(new MethodInsnNode(184, var1, var2, "()Z", false));
      var3.add(new JumpInsnNode(153, var4));
      var3.add(new InsnNode(177));
      var3.add(var4);
      var0.instructions.insert(var3);
   }
}
