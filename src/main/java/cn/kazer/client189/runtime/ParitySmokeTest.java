package cn.kazer.client189.runtime;

import cn.kazer.client189.animation.OldAnimationsAdapter;
import cn.kazer.client189.input.ImeHooks;
import com.cheatbreaker.client.util.KazerBuildInfo;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.imageio.ImageIO;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public final class ParitySmokeTest {
   private int frame;
   private boolean worldRequested;

   public static void a(com.cheatbreaker.client.gui.screens.StartupProgressScreen var0) {
      if (Boolean.getBoolean("kazer.test.parity")) {
         try {
            Minecraft var1 = Minecraft.getMinecraft();
            var1.getFramebuffer().unbindFramebuffer();
            GL11.glPushAttrib(32);

            try {
               GL11.glReadBuffer(1028);
               int var2 = var1.displayWidth;
               int var3 = var1.displayHeight;
               ByteBuffer var4 = BufferUtils.createByteBuffer(var2 * var3 * 4);
               GL11.glReadPixels(0, 0, var2, var3, 6408, 5121, var4);
               BufferedImage var5 = new BufferedImage(var2, var3, 2);

               for (int var6 = 0; var6 < var3; var6++) {
                  for (int var7 = 0; var7 < var2; var7++) {
                     int var8 = (var6 * var2 + var7) * 4;
                     var5.setRGB(var7, var3 - 1 - var6, 0xFF000000 | (var4.get(var8) & 255) << 16 | (var4.get(var8 + 1) & 255) << 8 | var4.get(var8 + 2) & 255);
                  }
               }

               File var14 = new File(var1.mcDataDir, "screenshots");
               var14.mkdirs();
               ImageIO.write(var5, "png", new File(var14, "00-startup.png"));
            } finally {
               GL11.glPopAttrib();
               var1.getFramebuffer().bindFramebuffer(true);
            }

            System.out.println("[KC PARITY] Shared Kazer startup screen captured");
         } catch (IOException var13) {
            throw new IllegalStateException(var13);
         }
      }
   }

   public void a() {
      if (Boolean.getBoolean("kazer.test.parity")) {
         final Minecraft var1 = Minecraft.getMinecraft();
         if (!this.worldRequested || this.frame >= 140 || var1.thePlayer != null) {
            this.frame++;
            if (this.frame == 40) {
               if (!KazerBuildInfo.windowTitle("1.8.9").equals(Display.getTitle())) {
                  throw new IllegalStateException("Incorrect startup window title");
               }

               b("01-main-menu.png");
               com.cheatbreaker.client.gui.screens.KazerMainMenuScreen var2 = (com.cheatbreaker.client.gui.screens.KazerMainMenuScreen)var1.currentScreen;
               var1.displayGuiScreen(new com.cheatbreaker.client.gui.screens.AccountManagerScreen(var2));
            }

            if (this.frame == 50) {
               b("01b-main-menu-accounts.png");
            }

            if (this.frame == 55) {
               var1.displayGuiScreen(new GuiMultiplayer(new com.cheatbreaker.client.gui.screens.KazerMainMenuScreen()));
               GuiMultiplayer var14 = (GuiMultiplayer)var1.currentScreen;
               if (!KazerPinnedServers.isPinned(var14.getServerList().getServerData(0))) {
                  throw new IllegalStateException("Pinned Kazer server missing");
               }

               var14.selectServer(0);
               if (KazerPinnedServers.canMove(var14, null, 0, false)) {
                  throw new IllegalStateException("Pinned server can move");
               }

               var14.getServerList().saveServerList();

               try {
                  NBTTagCompound var3 = CompressedStreamTools.read(new File(var1.mcDataDir, "servers.dat"));
                  NBTTagList var4 = var3.getTagList("servers", 10);
                  if (var4.tagCount() != var14.getServerList().countServers() - 1) {
                     throw new IllegalStateException("Virtual pinned server was persisted");
                  }
               } catch (IOException var13) {
                  throw new IllegalStateException(var13);
               }

               System.out.println("[KC PARITY] Pinned server is first, starred, non-movable and not persisted");
            }

            if (this.frame == 57) {
               b("01c-pinned-server.png");
            }

            if (this.frame == 60) {
               this.worldRequested = true;
               var1.launchIntegratedServer("New World", "New World", null);
            }

            if (this.worldRequested && var1.thePlayer != null && var1.theWorld != null) {
               if (this.frame >= 380 && this.frame <= 440) {
                  KeyBinding.setKeyBindState(var1.gameSettings.keyBindUseItem.getKeyCode(), true);
               }

               if (this.frame >= 442 && this.frame <= 455) {
                  KeyBinding.setKeyBindState(var1.gameSettings.keyBindUseItem.getKeyCode(), true);
                  KeyBinding.setKeyBindState(var1.gameSettings.keyBindAttack.getKeyCode(), true);
               }

               if (this.frame == 140) {
                  com.cheatbreaker.client.CheatBreakerClient var15 = com.cheatbreaker.client.CheatBreakerClient.N();
                  UUID var24 = var1.thePlayer.getUniqueID();
                  Scoreboard var34 = var1.theWorld.getScoreboard();
                  ScoreObjective var5 = var34.getObjective("kazer_v21_hp");
                  if (var5 == null) {
                     var5 = var34.addScoreObjective("kazer_v21_hp", IScoreObjectiveCriteria.DUMMY);
                  }

                  var5.setDisplayName("Health");
                  var34.setObjectiveInDisplaySlot(2, var5);
                  var34.getValueFromObjective(var1.thePlayer.getName(), var5).setScorePoints(20);
                  JsonObject var6 = new JsonObject();
                  var6.addProperty("@type", "type.googleapis.com/lunarclient.apollo.nametag.v1.OverrideNametagMessage");
                  JsonObject var7 = new JsonObject();
                  var7.addProperty("high64", Long.toUnsignedString(var24.getMostSignificantBits()));
                  var7.addProperty("low64", Long.toUnsignedString(var24.getLeastSignificantBits()));
                  var6.add("player_uuid", var7);
                  JsonArray var8 = new JsonArray();
                  var8.add(new JsonPrimitive("{\"text\":\"[Mod Mode]\",\"color\":\"gray\"}"));
                  var8.add(new JsonPrimitive("{\"text\":\"" + var1.thePlayer.getName() + "\",\"color\":\"red\"}"));
                  var6.add("adventure_json_lines", var8);
                  a(var6);
                  if (var15.netHandler.a(var24) == null || var15.netHandler.a(var24).size() != 2) {
                     throw new IllegalStateException("Official Apollo custom payload did not reach nametag state");
                  }

                  var1.displayGuiScreen(new com.cheatbreaker.client.gui.hudeditor.HudEditorScreen());
               }

               if (this.frame == 160) {
                  b("02-hud-editor.png");
               }

               if (this.frame == 180) {
                  var1.displayGuiScreen(new com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen());
               }

               if (this.frame == 200) {
                  b("03-modules.png");
               }

               if (this.frame == 220) {
                  var1.playerController.setGameType(GameType.SURVIVAL);
                  com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.nametagModule.thirdPersonNametag.a(false);
                  var1.displayGuiScreen(new GuiInventory(var1.thePlayer));
               }

               if (this.frame == 240) {
                  b("04-inventory-nametag.png");
               }

               if (this.frame == 245) {
                  var1.playerController.setGameType(GameType.CREATIVE);
                  GuiContainerCreative var16 = new GuiContainerCreative(var1.thePlayer);
                  var1.displayGuiScreen(var16);

                  try {
                     ReflectionHelper.findMethod(GuiContainerCreative.class, var16, new String[]{"setCurrentCreativeTab", "func_147050_b"}, CreativeTabs.class)
                        .invoke(var16, CreativeTabs.tabInventory);
                  } catch (ReflectiveOperationException var12) {
                     throw new IllegalStateException(var12);
                  }
               }

               if (this.frame == 255) {
                  b("04b-creative-inventory-nametag.png");
               }

               if (this.frame == 260) {
                  var1.displayGuiScreen(null);
                  var1.gameSettings.thirdPersonView = 1;
                  var1.playerController.setGameType(GameType.SURVIVAL);
                  com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.nametagModule.thirdPersonNametag.a(true);
               }

               if (this.frame == 280) {
                  b("05-third-person-nametag.png");
               }

               if (this.frame == 300) {
                  var1.thePlayer.rotationPitch = 70.0F;
               }

               if (this.frame == 320) {
                  b("06-pitched-nametag.png");
               }

               if (this.frame == 340) {
                  var1.gameSettings.thirdPersonView = 2;
                  var1.thePlayer.rotationPitch = 10.0F;
               }

               if (this.frame == 360) {
                  b("07-front-nametag.png");
               }

               if (this.frame == 380) {
                  var1.thePlayer.inventory.setInventorySlotContents(var1.thePlayer.inventory.currentItem, new ItemStack(Items.diamond_sword));
                  final UUID var17 = var1.thePlayer.getUniqueID();
                  final int var25 = var1.thePlayer.inventory.currentItem;
                  var1.getIntegratedServer().addScheduledTask(new Runnable() {
                     @Override
                     public void run() {
                        EntityPlayerMP var1x = var1.getIntegratedServer().getConfigurationManager().getPlayerByUUID(var17);
                        var1x.inventory.setInventorySlotContents(var25, new ItemStack(Items.diamond_sword));
                        var1x.inventoryContainer.detectAndSendChanges();
                     }
                  });
                  KeyBinding.setKeyBindState(var1.gameSettings.keyBindUseItem.getKeyCode(), true);
                  var1.thePlayer.swingItem();
               }

               if (this.frame == 400) {
                  int var18 = var1.thePlayer.getItemInUseCount();
                  if (!var1.thePlayer.isBlocking()
                     || OldAnimationsAdapter.renderedUseCount(var1.thePlayer, var1.thePlayer.getHeldItem()) != 1
                     || var1.thePlayer.getItemInUseCount() != var18) {
                     throw new IllegalStateException(
                        "Block-hit must preserve native item use; held="
                           + var1.thePlayer.getHeldItem()
                           + ", key="
                           + var1.gameSettings.keyBindUseItem.isKeyDown()
                           + ", screen="
                           + var1.currentScreen
                     );
                  }

                  EntityOtherPlayerMP var26 = new EntityOtherPlayerMP(var1.theWorld, new GameProfile(UUID.randomUUID(), "OtherPlayer"));
                  ModelBiped var35 = new ModelBiped();
                  var35.heldItemRight = 1;
                  if (var26.isUsingItem() || var26.getItemInUseCount() != 0 || var35.heldItemRight != 1) {
                     throw new IllegalStateException("Local block-hit changed another player's rendering");
                  }

                  System.out.println("[KC PARITY] Block-hit is visual-only; remote player use count and arm pose unchanged");
                  b("08-third-person-block-hit.png");
               }

               if (this.frame == 420) {
                  var1.gameSettings.thirdPersonView = 0;
                  var1.thePlayer.rotationPitch = 75.0F;
                  var1.thePlayer.swingItem();
               }

               if (this.frame == 440) {
                  if (var1.thePlayer.isSprinting() && !var1.thePlayer.capabilities.isFlying) {
                     throw new IllegalStateException("Native item use bypassed sprint guard");
                  }

                  if (var1.objectMouseOver == null
                     || var1.objectMouseOver.typeOfHit != MovingObjectType.BLOCK
                     || OldAnimationsAdapter.renderedUseCount(var1.thePlayer, var1.thePlayer.getHeldItem()) != 1) {
                     throw new IllegalStateException("Block-hit against blocks was not active");
                  }

                  g();
                  b("09-first-person-block-hit.png");
                  KeyBinding.setKeyBindState(var1.gameSettings.keyBindUseItem.getKeyCode(), false);
                  i();
               }

               if (this.frame == 460) {
                  var1.displayGuiScreen(new cn.kazer.client189.screens.KazerResourcePacksScreen(new com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen()));
               }

               if (this.frame == 442 || this.frame == 448) {
                  ItemStack var19 = new ItemStack((Item)(this.frame == 442 ? Items.golden_apple : Items.potionitem));
                  var1.thePlayer.inventory.setInventorySlotContents(var1.thePlayer.inventory.currentItem, var19);
                  var1.thePlayer.setItemInUse(var19, 32);
                  ReflectionHelper.setPrivateValue(ItemRenderer.class, var1.entityRenderer.itemRenderer, var19, "itemToRender", "field_78453_b");
                  KeyBinding.setKeyBindState(var1.gameSettings.keyBindUseItem.getKeyCode(), true);
                  KeyBinding.setKeyBindState(var1.gameSettings.keyBindAttack.getKeyCode(), true);
               }

               if (this.frame == 447) {
                  b("19-first-person-eating.png");
               }

               if (this.frame == 453) {
                  b("20-first-person-drinking.png");
               }

               if (this.frame == 455) {
                  var1.thePlayer.stopUsingItem();
                  KeyBinding.setKeyBindState(var1.gameSettings.keyBindUseItem.getKeyCode(), false);
                  KeyBinding.setKeyBindState(var1.gameSettings.keyBindAttack.getKeyCode(), false);
               }

               if (this.frame == 456) {
                  ItemStack var20 = new ItemStack(Items.bow);
                  var1.thePlayer.inventory.setInventorySlotContents(var1.thePlayer.inventory.currentItem, var20);
                  var1.thePlayer.setItemInUse(var20, 71980);
                  ReflectionHelper.setPrivateValue(ItemRenderer.class, var1.entityRenderer.itemRenderer, var20, "itemToRender", "field_78453_b");
                  if (OldAnimationsAdapter.renderedUseCount(var1.thePlayer, var20) != 1 || var1.thePlayer.getItemInUseCount() != 71980) {
                     throw new IllegalStateException("Bow timer modified by renderer");
                  }
               }

               if (this.frame == 457) {
                  b("23-oldanimations-bow-pullback.png");
               }

               if (this.frame == 458) {
                  var1.thePlayer.stopUsingItem();
                  ItemStack var21 = new ItemStack(Items.fishing_rod);
                  var1.thePlayer.inventory.setInventorySlotContents(var1.thePlayer.inventory.currentItem, var21);
                  ReflectionHelper.setPrivateValue(ItemRenderer.class, var1.entityRenderer.itemRenderer, var21, "itemToRender", "field_78453_b");
               }

               if (this.frame == 459) {
                  b("24-oldanimations-rod.png");
               }

               if (this.frame == 480) {
                  b("10-resource-packs.png");
               }

               if (this.frame == 500) {
                  com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen var22 = new com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen();

                  try {
                     Field var27 = com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.class.getDeclaredField("page");
                     var27.setAccessible(true);
                     var27.set(var22, Enum.valueOf((Class<? extends Enum>)var27.getType(), "SETTINGS"));
                  } catch (ReflectiveOperationException var11) {
                     throw new IllegalStateException(var11);
                  }

                  var1.displayGuiScreen(var22);
               }

               if (this.frame == 520) {
                  b("11-settings.png");
               }

               if (this.frame == 540) {
                  var1.displayGuiScreen(null);
                  var1.thePlayer.rotationPitch = 15.0F;
                  com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.motionBlurModule.a(true);
               }

               int var23 = this.frame - 540;
               if (var23 >= 0 && var23 < 160) {
                  var1.thePlayer.rotationYaw += 0.5F;
               }

               if (var23 >= 0 && var23 < 160 && var23 % 40 == 0) {
                  com.cheatbreaker.client.module.modules.shader.MotionBlurModule var28 = com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.motionBlurModule;

                  for (com.cheatbreaker.client.settings.Setting var39 : var28.m()) {
                     if (var39.f().equals("Mode")) {
                        var39.a(com.cheatbreaker.client.module.modules.shader.MotionBlurMode.values()[var23 / 40].a(), false);
                     }
                  }
               }

               if (var23 >= 0 && var23 < 160 && var23 % 40 == 20) {
                  b("12-blur-" + var23 / 40 + ".png");
               }

               if (this.frame == 710) {
                  com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.motionBlurModule.a(false);
                  JsonObject var29 = new JsonObject();
                  var29.addProperty("@type", "type.googleapis.com/lunarclient.apollo.title.v1.DisplayTitleMessage");
                  var29.addProperty("title_type", "TITLE_TYPE_TITLE");
                  var29.addProperty("scale", 1.0F);
                  var29.addProperty("adventure_json_message", "{\"text\":\"KAZER 1.8.9\",\"color\":\"aqua\"}");
                  var29.addProperty("display_time", "10s");
                  a(var29);
               }

               if (this.frame == 730) {
                  b("13-apollo-title.png");
               }

               if (this.frame == 750) {
                  h();
               }

               if (this.frame == 770) {
                  com.cheatbreaker.client.CheatBreakerClient.N().C().a().clear();
                  com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen var30 = new com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen();
                  var1.displayGuiScreen(var30);

                  try {
                     Field var37 = com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.class.getDeclaredField("selectedModule");
                     var37.setAccessible(true);
                     var37.set(var30, com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.blockHitAnimation);
                  } catch (ReflectiveOperationException var10) {
                     throw new IllegalStateException(var10);
                  }
               }

               if (this.frame == 790) {
                  b("14-legacy-visuals.png");
               }

               if (this.frame == 810) {
                  try {
                     Field var31 = com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.class.getDeclaredField("settingsScroll");
                     var31.setAccessible(true);
                     var31.setInt(var1.currentScreen, 110);
                  } catch (ReflectiveOperationException var9) {
                     throw new IllegalStateException(var9);
                  }
               }

               if (this.frame == 830) {
                  b("15-legacy-visuals-items.png");
               }

               if (this.frame == 850) {
                  e();
                  f();
                  System.out.println("[KC PARITY] Completed isolated UI, config, Apollo, four blur modes, card toggle and non-destructive block-hit sequence");
                  var1.displayGuiScreen(new GuiIngameMenu());
                  if (!(var1.currentScreen instanceof cn.kazer.client189.screens.KazerIngameMenu)) {
                     throw new IllegalStateException("Pause menu did not match 1.7");
                  }
               }

               if (this.frame == 870) {
                  b("16-pause-menu.png");
               }

               if (this.frame == 880) {
                  b();
                  c();
                  d();
                  a("1.7 Visuals");
               }

               if (this.frame == 900) {
                  b("17-visuals-icon-search.png");
               }

               if (this.frame == 910) {
                  a("Chat");
               }

               if (this.frame == 930) {
                  b("18-chat-icon-search.png");
               }

               if (this.frame == 930) {
                  GuiChat var32 = new GuiChat();
                  var1.displayGuiScreen(var32);
                  GuiTextField var38 = ReflectionHelper.getPrivateValue(GuiChat.class, var32, "inputField", "field_146415_a");
                  var38.textboxKeyTyped('中', 0);
                  var38.textboxKeyTyped('文', 0);
                  if (!"中文".equals(var38.getText())) {
                     throw new IllegalStateException("Unicode commit rejected");
                  }

                  ImeHooks.textField(var38);
                  System.out.println("[KC PARITY] Chinese commit and native candidate positioning completed");
               }

               if (this.frame == 934) {
                  b("22-chat-chinese.png");
               }

               if (this.frame == 935) {
                  com.cheatbreaker.client.network.stats.ApolloMessageCodec.a var33 = new com.cheatbreaker.client.network.stats.ApolloMessageCodec.a();
                  var33.kind = com.cheatbreaker.client.network.stats.ApolloMessageCodec.bInner.NAMETAG;
                  var33.player = var1.thePlayer.getUniqueID();
                  var33.lines = Collections.singletonList(var1.thePlayer.getName());
                  com.cheatbreaker.client.CheatBreakerClient.N().netHandler.e().a(var33);
                  var1.theWorld.getScoreboard().setObjectiveInDisplaySlot(2, null);
                  var1.displayGuiScreen(new GuiInventory(var1.thePlayer));
               }

               if (this.frame == 945) {
                  b("21-single-inventory-nametag.png");
               }

               if (this.frame == 950) {
                  System.out.println("[KC PARITY] V23_SMOKE_OK: sprint guard, inventory without labels, version footer, IME adapters and UI checks completed");
                  var1.shutdown();
               }
            }
         }
      }
   }

   private static void b() {
      Minecraft var0 = Minecraft.getMinecraft();
      com.cheatbreaker.client.module.modules.ChatModule var1 = com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.chatModule;
      float var2 = var1.k();
      float var3 = var1.l();
      Object var4 = var1.scale.d();

      try {
         var1.a(90.0F, -85.0F);
         var1.scale.a(1.5F, false);
         var0.displayGuiScreen(new GuiChat());
         ChatComponentText var5 = new ChatComponentText("Click Here");
         var5.getChatStyle().setChatClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, "/test-v22"));
         var0.ingameGUI.getChatGUI().printChatMessage(var5);
         var1.C();
         ScaledResolution var6 = new ScaledResolution(var0);
         float[] var7 = var1.a(var6, true);
         float var8 = ((Number)var1.scale.d()).floatValue();
         int var9 = Math.round(((var7[0] + 2.0F) * var8 + 5.0F) * var6.getScaleFactor());
         int var10 = Math.round((var6.getScaledHeight() - (var7[1] + var1.j()) * var8 + 4.0F) * var6.getScaleFactor());
         IChatComponent var11 = var0.ingameGUI.getChatGUI().getChatComponent(var9, var10);
         if (var11 == null || var11.getChatStyle().getChatClickEvent() == null || !"/test-v22".equals(var11.getChatStyle().getChatClickEvent().getValue())) {
            throw new IllegalStateException("Moved/scaled chat click missed");
         }

         if (var0.ingameGUI.getChatGUI().getChatComponent(8 * var6.getScaleFactor(), 31 * var6.getScaleFactor()) != null) {
            throw new IllegalStateException("Old chat coordinates are still clickable");
         }

         System.out.println("[KC PARITY] Moved chat with 1.5 scale preserves clickable server components; old location inactive");
      } finally {
         var1.a(var2, var3);
         var1.scale.a(var4, false);
         var0.displayGuiScreen(null);
      }
   }

   private static void c() {
      boolean var0 = GL11.glIsEnabled(3042);
      boolean var1 = GL11.glIsEnabled(2929);
      cn.kazer.client189.render.GlStateBridge.c(1048575);
      GlStateManager.enableBlend();
      GlStateManager.disableDepth();
      cn.kazer.client189.render.GlStateBridge.a();
      if (GL11.glIsEnabled(3042) == var0 && GL11.glIsEnabled(2929) == var1) {
         for (int var2 = 0; var2 < 2; var2++) {
            long var3 = System.nanoTime();

            for (int var5 = 0; var5 < 500; var5++) {
               if (var2 == 0) {
                  GL11.glPushAttrib(1048575);
                  GL11.glPopAttrib();
                  cn.kazer.client189.render.GlStateBridge.b();
               } else {
                  cn.kazer.client189.render.GlStateBridge.c(1048575);
                  cn.kazer.client189.render.GlStateBridge.a();
               }
            }

            System.out
               .println("[KC PERF] " + (var2 == 0 ? "GPU query fallback" : "CPU cache snapshot") + " 500 scopes ms=" + (System.nanoTime() - var3) / 1000000.0);
         }
      } else {
         throw new IllegalStateException("Attribute scope did not restore driver state");
      }
   }

   private static void d() {
      com.cheatbreaker.client.CheatBreakerClient var0 = com.cheatbreaker.client.CheatBreakerClient.N();

      try {
         Field var1 = var0.L().getClass().getDeclaredField("sameServerUsers");
         var1.setAccessible(true);
         Object var2 = var1.get(var0.L());

         try {
            HashMap var3 = new HashMap();
            UUID var4 = com.cheatbreaker.client.util.NameColorUtils.CANRX_UUID;
            var3.put(var4, 11141120);
            var1.set(var0.L(), var3);
            String var5 = RuntimeHooks.tabName("CANRX", new NetworkPlayerInfo(new GameProfile(var4, "CANRX")));
            Minecraft var6 = Minecraft.getMinecraft();
            String var7 = RuntimeHooks.tabName(
               var6.thePlayer.getName(), new NetworkPlayerInfo(new GameProfile(var6.thePlayer.getUniqueID(), var6.thePlayer.getName()))
            );
            Field var8 = RuntimeHooks.class.getDeclaredField("TAB_COLORS");
            var8.setAccessible(true);
            Map var9 = (Map)var8.get(null);
            if (!Integer.valueOf(11141120).equals(var9.get(var5)) || !Integer.valueOf(16777215).equals(var9.get(var7))) {
               throw new IllegalStateException("TAB badge colors leaked between rows");
            }
         } finally {
            var1.set(var0.L(), var2);
         }

         System.out.println("[KC PARITY] TAB keeps each UUID's badge color after another row is measured");
      } catch (ReflectiveOperationException var14) {
         throw new IllegalStateException(var14);
      }
   }

   private static void a(String var0) {
      Minecraft var1 = Minecraft.getMinecraft();
      com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen var2 = new com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen();
      var1.displayGuiScreen(var2);

      try {
         Field var3 = com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.class.getDeclaredField("search");
         var3.setAccessible(true);
         ((GuiTextField)var3.get(var2)).setText(var0);
         Field var4 = com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.class.getDeclaredField("left");
         var4.setAccessible(true);
         Field var5 = com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.class.getDeclaredField("top");
         var5.setAccessible(true);
         Mouse.setCursorPosition((var4.getInt(var2) + 145) * 2, var1.displayHeight - (var5.getInt(var2) + 90) * 2);
      } catch (ReflectiveOperationException var6) {
         throw new IllegalStateException(var6);
      }
   }

   private static void e() {
      com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen var0 = (com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen)Minecraft.getMinecraft().currentScreen;
      cn.kazer.client189.animation.LegacyVisualsModule var1 = com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.blockHitAnimation;

      try {
         Field var2 = com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.class.getDeclaredField("left");
         var2.setAccessible(true);
         Field var3 = com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.class.getDeclaredField("top");
         var3.setAccessible(true);
         Field var4 = com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.class.getDeclaredField("settingsScroll");
         var4.setAccessible(true);
         var4.setInt(var0, 0);
         Method var5 = com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.class.getDeclaredMethod("o", int.class, int.class);
         var5.setAccessible(true);
         int var6 = var2.getInt(var0) + 108 + 10;
         int var7 = var3.getInt(var0) + 40 + 8 + 58;
         var5.invoke(var0, var6 + 8, var7 + 8);
         if (!var1.a(var1.animations) && !var1.a(var1.redArmor)) {
            var5.invoke(var0, var6 + 8, var7 + 8);
            if (var1.a(var1.animations) && var1.a(var1.redArmor)) {
               var5.invoke(var0, var6 + 8, var7 + 21 + 8);
               if (var1.a(var1.redArmor)) {
                  throw new IllegalStateException("Upstream toggle did not turn off");
               } else {
                  var1.a();
                  if (var1.a(var1.redArmor)) {
                     throw new IllegalStateException("Upstream option was not persisted");
                  } else {
                     var5.invoke(var0, var6 + 8, var7 + 21 + 8);
                     if (!var1.a(var1.redArmor)) {
                        throw new IllegalStateException("Upstream toggle did not turn on");
                     } else {
                        System.out.println("[KC PARITY] Group master switches, version switches, hit testing and isolated persistence passed");
                     }
                  }
               }
            } else {
               throw new IllegalStateException("Animations switch did not restore children");
            }
         } else {
            throw new IllegalStateException("Animations switch did not hide/disable children");
         }
      } catch (ReflectiveOperationException var8) {
         throw new IllegalStateException(var8);
      }
   }

   private static void f() {
      Minecraft var0 = Minecraft.getMinecraft();
      int var1 = var0.gameSettings.keyBindForward.getKeyCode();
      int var2 = var0.gameSettings.renderDistanceChunks;

      try {
         var0.gameSettings.keyBindForward.setKeyCode(37);
         var0.gameSettings.renderDistanceChunks = 5;
         var0.gameSettings.saveOptions();
         File var3 = new File(var0.mcDataDir, "config/KazerClient/game-options.cfg");
         String var4 = new String(Files.readAllBytes(var3.toPath()), StandardCharsets.UTF_8);
         if (!var4.contains("mc/key_key.forward:37") || !var4.contains("mc/renderDistance:5")) {
            throw new IllegalStateException("Native settings export hook did not run");
         }

         var0.gameSettings.keyBindForward.setKeyCode(17);
         var0.gameSettings.renderDistanceChunks = 8;
         var0.gameSettings.loadOptions();
         if (var0.gameSettings.keyBindForward.getKeyCode() != 37 || var0.gameSettings.renderDistanceChunks != 5) {
            throw new IllegalStateException("Native settings import hook did not run");
         }

         System.out.println("[KC PARITY] Native key/options save+load exported and imported shared preferences");
      } catch (IOException var8) {
         throw new IllegalStateException(var8);
      } finally {
         var0.gameSettings.keyBindForward.setKeyCode(var1);
         var0.gameSettings.renderDistanceChunks = var2;
         var0.gameSettings.saveOptions();
         KeyBinding.resetKeyBindingArrayAndHash();
      }
   }

   private static void g() {
      Minecraft var0 = Minecraft.getMinecraft();
      MovingObjectPosition var1 = var0.objectMouseOver;
      IBlockState var2 = var0.theWorld.getBlockState(var1.getBlockPos());

      try {
         if (!OldAnimationsAdapter.cancelBlockClick()) {
            throw new IllegalStateException("Block-click guard inactive");
         }

         a(var1);
         ReflectionHelper.findMethod(Minecraft.class, var0, new String[]{"clickMouse", "func_147116_af"}).invoke(var0);
         ReflectionHelper.findMethod(Minecraft.class, var0, new String[]{"sendClickBlockToController", "func_147115_a"}, boolean.class).invoke(var0, true);
         if (var0.playerController.getIsHittingBlock() || !var2.equals(var0.theWorld.getBlockState(var1.getBlockPos()))) {
            throw new IllegalStateException("Visual block-hit started breaking a block");
         }

         KeyBinding.setKeyBindState(var0.gameSettings.keyBindAttack.getKeyCode(), true);
         OldAnimationsAdapter.tick();
         EntityOtherPlayerMP var3 = new EntityOtherPlayerMP(var0.theWorld, new GameProfile(UUID.randomUUID(), "OtherPlayer"));
         var0.objectMouseOver = new MovingObjectPosition(var3);
         if (OldAnimationsAdapter.cancelBlockClick()) {
            throw new IllegalStateException("Normal entity attacks were suppressed");
         }

         if (var3.swingProgress != 0.0F || var3.isUsingItem()) {
            throw new IllegalStateException("Local visual state leaked to remote player");
         }

         var0.objectMouseOver = null;

         for (int var4 = 0; var4 < 8; var4++) {
            OldAnimationsAdapter.tick();
         }

         if (OldAnimationsAdapter.cancelBlockClick() || OldAnimationsAdapter.useSwing(0.0F, 0.0F) != var0.thePlayer.getSwingProgress(0.0F)) {
            throw new IllegalStateException("Air-target sword swing loop is active");
         }

         System.out.println("[KC PARITY] Holding both buttons into air does not create a cosmetic sword swing loop");
         System.out.println("[KC PARITY] Native block click/dig cancelled; block unchanged; entity attacks and remote swing retained");
         var0.objectMouseOver = var1;
         ItemStack var21 = var0.thePlayer.getHeldItem();

         try {
            for (Item var8 : new Item[]{Items.golden_apple, Items.potionitem}) {
               ItemStack var9 = new ItemStack(var8);
               var0.thePlayer.inventory.setInventorySlotContents(var0.thePlayer.inventory.currentItem, var9);
               var0.thePlayer.setItemInUse(var9, 32);
               OldAnimationsAdapter.tick();
               OldAnimationsAdapter.tick();
               if (!OldAnimationsAdapter.cancelBlockClick()
                  || OldAnimationsAdapter.useSwing(0.0F, 0.5F) <= 0.0F
                  || OldAnimationsAdapter.renderedUseCount(var0.thePlayer, var9) != 1
                  || var0.thePlayer.getItemInUseCount() != 32) {
                  throw new IllegalStateException("Eating/drinking block-target swing inactive");
               }

               ReflectionHelper.findMethod(Minecraft.class, var0, new String[]{"clickMouse", "func_147116_af"}).invoke(var0);
               if (var0.playerController.getIsHittingBlock() || !var2.equals(var0.theWorld.getBlockState(var1.getBlockPos()))) {
                  throw new IllegalStateException("Eating swing damaged a block");
               }
            }
         } finally {
            var0.thePlayer.stopUsingItem();
            var0.thePlayer.inventory.setInventorySlotContents(var0.thePlayer.inventory.currentItem, var21);
         }

         System.out.println("[KC PARITY] Golden apple and potion swing locally against blocks without digging");
      } catch (ReflectiveOperationException var19) {
         throw new IllegalStateException(var19);
      } finally {
         var0.objectMouseOver = var1;
         KeyBinding.setKeyBindState(var0.gameSettings.keyBindAttack.getKeyCode(), false);
      }
   }

   private static void a(MovingObjectPosition var0) throws ReflectiveOperationException {
      Minecraft var1 = Minecraft.getMinecraft();
      cn.kazer.client189.animation.LegacyVisualsModule var2 = com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.blockHitAnimation;
      ItemStack var3 = var1.thePlayer.getHeldItem();
      var1.thePlayer.stopUsingItem();
      if (OldAnimationsAdapter.renderedUseCount(var1.thePlayer, var3) == 0 && !OldAnimationsAdapter.cancelBlockClick()) {
         for (Item var7 : new Item[]{Items.bread, Items.golden_apple}) {
            ItemStack var8 = new ItemStack(var7);
            var1.thePlayer.inventory.setInventorySlotContents(var1.thePlayer.inventory.currentItem, var8);
            if (OldAnimationsAdapter.renderedUseCount(var1.thePlayer, var8) != 0) {
               throw new IllegalStateException("Idle food manufactured consume animation");
            }
         }

         var1.thePlayer.inventory.setInventorySlotContents(var1.thePlayer.inventory.currentItem, var3);
         ReflectionHelper.setPrivateValue(PlayerControllerMP.class, var1.playerController, true, "isHittingBlock", "field_78778_j");
         ReflectionHelper.findMethod(Minecraft.class, var1, new String[]{"rightClickMouse", "func_147121_ag"}).invoke(var1);
         if (!var1.playerController.getIsHittingBlock() && var1.thePlayer.isUsingItem()) {
            var2.a(false);

            try {
               int var12 = var1.thePlayer.getItemInUseCount();
               if (OldAnimationsAdapter.renderedUseCount(var1.thePlayer, var3) != var12
                  || OldAnimationsAdapter.useSwing(0.25F, 0.5F) != 0.25F
                  || OldAnimationsAdapter.skipNoneTransform()
                  || OldAnimationsAdapter.blockingArmYaw((float) (-Math.PI / 6)) != (float) (-Math.PI / 6)
                  || !OldAnimationsAdapter.healthFlash(true)
                  || OldAnimationsAdapter.redArmor()) {
                  throw new IllegalStateException("Disabled module did not restore native animation branches");
               }
            } finally {
               var2.a(true);
            }

            int var13 = var1.thePlayer.getItemInUseCount();
            float var14 = 0.6F;
            if (!(Math.abs(OldAnimationsAdapter.equipClamp(-0.6F, -0.4F, 0.4F, var3, var1.thePlayer.inventory.currentItem, var14) - 0.4F) > 1.0E-5F)
               && var1.thePlayer.getItemInUseCount() == var13) {
               var1.objectMouseOver = var0;
               System.out.println("[KC PARITY] V25: genuine native use, dig-to-use transition, idle food, full disable and item switching passed");
            } else {
               throw new IllegalStateException("Same-slot switch animation differs from upstream");
            }
         } else {
            throw new IllegalStateException("Native right click failed to abort digging/start genuine item use");
         }
      } else {
         throw new IllegalStateException("Held right button manufactured item use");
      }
   }

   private static void h() {
      Minecraft var0 = Minecraft.getMinecraft();
      com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen var1 = new com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen();
      var0.displayGuiScreen(var1);
      com.cheatbreaker.client.module.modules.shader.MotionBlurModule var2 = com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.motionBlurModule;

      try {
         Field var3 = com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.class.getDeclaredField("search");
         var3.setAccessible(true);
         ((GuiTextField)var3.get(var1)).setText("Motion Blur");
         Field var4 = com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.class.getDeclaredField("left");
         var4.setAccessible(true);
         Field var5 = com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.class.getDeclaredField("top");
         var5.setAccessible(true);
         Method var6 = com.cheatbreaker.client.gui.hudeditor.KazerMenuScreen.class.getDeclaredMethod("n", int.class, int.class);
         var6.setAccessible(true);
         int var7 = var4.getInt(var1) + 108 + 9 + 30;
         int var8 = var5.getInt(var1) + 40 + 31 + 108 - 8;

         for (com.cheatbreaker.client.module.modules.shader.MotionBlurMode var12 : com.cheatbreaker.client.module.modules.shader.MotionBlurMode.values()) {
            for (com.cheatbreaker.client.settings.Setting var14 : var2.m()) {
               if (var14.f().equals("Mode")) {
                  var14.a(var12.a(), false);
               }
            }

            var2.a(false);
            var6.invoke(var1, var7, var8);
            if (!var2.g()) {
               throw new IllegalStateException("Blur card did not enable " + var12);
            }

            var6.invoke(var1, var7, var8);
            if (var2.g() || com.cheatbreaker.client.module.modules.shader.MotionBlurModule.a() != null) {
               throw new IllegalStateException("Blur card did not disable " + var12);
            }
         }

         System.out.println("[KC PARITY] Actual module card enabled/disabled all four blur modes; active blur cleared");
      } catch (ReflectiveOperationException var15) {
         throw new IllegalStateException(var15);
      }
   }

   private static void b(String var0) {
      Minecraft var1 = Minecraft.getMinecraft();
      ScreenShotHelper.saveScreenshot(var1.mcDataDir, var0, var1.displayWidth, var1.displayHeight, var1.getFramebuffer());
      System.out.println("[KC PARITY] Screenshot " + var0);
      int var2 = GL11.glGetError();
      if (var2 != 0) {
         throw new IllegalStateException("OpenGL error after " + var0 + ": " + var2);
      }
   }

   private static void a(JsonObject var0) {
      byte[] var1 = var0.toString().getBytes(StandardCharsets.UTF_8);
      Minecraft.getMinecraft().getNetHandler().handleCustomPayload(new S3FPacketCustomPayload("apollo:json", new PacketBuffer(Unpooled.wrappedBuffer(var1))));
   }

   private static void i() {
      com.cheatbreaker.client.CheatBreakerClient var0 = com.cheatbreaker.client.CheatBreakerClient.N();
      com.cheatbreaker.client.module.modules.FpsModule var1 = var0.moduleManager.fpsModule;
      var1.a(37.0F, 49.0F);
      var0.configManager.a();
      var1.a(0.0F, 0.0F);
      var0.configManager.b();
      if (var1.k() != 37.0F || var1.l() != 49.0F) {
         throw new IllegalStateException("Shared HUD configuration did not round-trip");
      } else if (!var0.moduleManager.blockHitAnimation.g()) {
         throw new IllegalStateException("Shared profile overwrote 1.8-only block-hit preferences");
      } else {
         System.out.println("[KC PARITY] Shared profile and isolated animation configuration round-trip passed");
      }
   }
}
