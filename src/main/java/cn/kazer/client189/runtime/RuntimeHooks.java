package cn.kazer.client189.runtime;

import com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatRuntime;
import com.cheatbreaker.client.kazerui.module.modules.mechanic.ModuleTab;
import com.cheatbreaker.client.kazerui.module.modules.mechanic.ModuleZoom;
import com.cheatbreaker.client.kazerui.module.modules.visual.ModuleOverlay;
import com.cheatbreaker.client.kazerui.module.modules.visual.ModuleParticleChanger;
import com.cheatbreaker.client.kazerui.module.render.KazerChatAccess;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public final class RuntimeHooks {
   private static final FloatBuffer SKY_CLIP = clipMatrix();
   private static final Map<String, Integer> TAB_COLORS = new LinkedHashMap<>();
   private static final Set<String> TAB_OWN_NAMES = new HashSet<>();
   private static final String BADGE_PREFIX = "§r   ";

   public static boolean chunkVisible(int var0, int var1, int var2, int var3, int var4) {
      return Math.abs((long)var0 - var2) <= var4 && Math.abs((long)var1 - var3) <= var4;
   }

   private static FloatBuffer clipMatrix() {
      FloatBuffer var0 = BufferUtils.createFloatBuffer(16);
      var0.put(new float[]{1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 4.0F});
      ((Buffer)var0).flip();
      return var0;
   }

   public static void customSkyBegin() {
      GlStateManager.matrixMode(5889);
      GlStateManager.pushMatrix();
      if (Minecraft.getMinecraft().gameSettings.renderDistanceChunks <= 4) {
         GlStateManager.multMatrix(SKY_CLIP);
      }

      GlStateManager.matrixMode(5888);
   }

   public static void customSkyEnd() {
      GlStateManager.matrixMode(5889);
      GlStateManager.popMatrix();
      GlStateManager.matrixMode(5888);
   }

   public static RenderChunk visibleChunk(RenderChunk var0, BlockPos var1) {
      if (var0 == null) {
         return null;
      } else {
         BlockPos var2 = var0.getPosition();
         return chunkVisible(
               var2.getX() >> 4,
               var2.getZ() >> 4,
               var1.getX() >> 4,
               var1.getZ() >> 4,
               Minecraft.getMinecraft().gameSettings.renderDistanceChunks
            )
            ? var0
            : null;
      }
   }

   public static void clearDistanceFog(boolean var0) {
      Minecraft var1 = Minecraft.getMinecraft();
      Entity var2 = var1.getRenderViewEntity();
      if (var0
         && var1.theWorld != null
         && var2 != null
         && var1.theWorld.provider.getDimensionId() == 0
         && !var1.theWorld.provider.doesXZShowFog((int)var2.posX, (int)var2.posZ)) {
         GlStateManager.setFogStart(4096.0F);
         GlStateManager.setFogEnd(5120.0F);
      }
   }

   public static double horizonBoxHeight(double var0) {
      return Minecraft.getMinecraft().gameSettings.renderDistanceChunks <= 4 ? 0.0 : var0;
   }

   public static void resumeAfterEscape(Minecraft var0) {
      if (var0.currentScreen == null) {
         var0.setIngameFocus();
      }
   }

   public static void resourcePackScan(Object var0) {
      if (!(Minecraft.getMinecraft().currentScreen instanceof cn.kazer.client189.screens.KazerResourcePacksScreen)) {
         ((ResourcePackRepository)var0).updateRepositoryEntriesAll();
      }
   }

   public static void splash(Object var0) {
      Minecraft var1 = Minecraft.getMinecraft();
      ScaledResolution var2 = new ScaledResolution(var1);
      GlStateManager.matrixMode(5889);
      GlStateManager.loadIdentity();
      GlStateManager.ortho(0.0, var2.getScaledWidth(), var2.getScaledHeight(), 0.0, 1000.0, 3000.0);
      GlStateManager.matrixMode(5888);
      GlStateManager.loadIdentity();
      GlStateManager.translate(0.0F, 0.0F, -2000.0F);
      GlStateManager.disableDepth();
      Gui.drawRect(0, 0, var2.getScaledWidth(), var2.getScaledHeight(), -16777216);

      try (InputStream var3 = RuntimeHooks.class.getResourceAsStream("/assets/minecraft/client/kazer_logo.png")) {
         TextureManager var5 = (TextureManager)var0;
         ResourceLocation var6 = var5.getDynamicTextureLocation("kazer-startup", new DynamicTexture(ImageIO.read(var3)));
         var5.bindTexture(var6);
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         int var7 = Math.max(32, Math.min(44, var2.getScaledHeight() / 6));
         Gui.drawModalRectWithCustomSizedTexture(
            var2.getScaledWidth() / 2 - var7 / 2, var2.getScaledHeight() / 2 - var7 - 10, 0.0F, 0.0F, var7, var7, var7, var7
         );
      } catch (IOException var18) {
         LogManager.getLogger().warn("Could not draw Kazer startup logo", var18);
      }

      int var19 = var2.getScaledWidth() / 2 - 66;
      int var20 = var2.getScaledHeight() / 2 + 31;
      Gui.drawRect(var19, var20, var19 + 132, var20 + 2, -13221572);
      Gui.drawRect(var19, var20, var19 + 17, var20 + 2, -11151928);
      GlStateManager.enableAlpha();
      GlStateManager.alphaFunc(516, 0.1F);
      Display.update();
   }

   public static void optionsLoad() {
      com.cheatbreaker.client.settings.GameOptionsSync.a(Minecraft.getMinecraft().mcDataDir, "1.8.9");
   }

   public static void optionsSave() {
      com.cheatbreaker.client.settings.GameOptionsSync.b(Minecraft.getMinecraft().mcDataDir, "1.8.9");
      if (com.cheatbreaker.client.CheatBreakerClient.N() != null && com.cheatbreaker.client.CheatBreakerClient.N().configManager != null) {
         com.cheatbreaker.client.CheatBreakerClient.N().configManager.c();
      }
   }

   private RuntimeHooks() {
   }

   public static void joinedWorld(boolean var0) {
      if (Minecraft.getMinecraft().isCallingFromMinecraftThread() && com.cheatbreaker.client.CheatBreakerClient.N() != null) {
         com.cheatbreaker.client.CheatBreakerClient.N().netHandler.a(var0);
      }
   }

   public static void registerApollo() {
      Minecraft var0 = Minecraft.getMinecraft();
      if (var0.isCallingFromMinecraftThread() && var0.getNetHandler() != null) {
         byte[] var1 = "lunar:apollo\u0000apollo:json".getBytes(StandardCharsets.UTF_8);
         var0.getNetHandler().addToSendQueue(new C17PacketCustomPayload("REGISTER", new PacketBuffer(Unpooled.wrappedBuffer(var1))));
         var0.getNetHandler()
            .addToSendQueue(new C17PacketCustomPayload("lunar:apollo", new PacketBuffer(Unpooled.wrappedBuffer(com.cheatbreaker.client.network.stats.ApolloMessageCodec.a()))));
      }
   }

   public static void keepAlive() {
      if (com.cheatbreaker.client.CheatBreakerClient.N() != null) {
         com.cheatbreaker.client.CheatBreakerClient.N().eventBus.a(new com.cheatbreaker.client.event.event.KeepAliveEvent());
      }
   }

   public static void customCrosshair(ScaledResolution var0) {
      com.cheatbreaker.client.settings.GlobalSettings var1 = com.cheatbreaker.client.CheatBreakerClient.N().globalSettings;
      float var2 = 1.0F / com.cheatbreaker.client.CheatBreakerClient.N().e();
      float var3 = (int)(var0.getScaledWidth() / var2) / 2.0F;
      float var4 = (int)(var0.getScaledHeight() / var2) / 2.0F;
      float var5 = ((Number)var1.crosshairSize.d()).floatValue();
      float var6 = ((Number)var1.crosshairGap.d()).floatValue();
      float var7 = ((Number)var1.crosshairThickness.d()).floatValue() / 2.0F;
      float[][] var8 = new float[][]{
         {var3 - var6 - var5, var4 - var7, var3 - var6, var4 + var7},
         {var3 + var6, var4 - var7, var3 + var6 + var5, var4 + var7},
         {var3 - var7, var4 - var6 - var5, var3 + var7, var4 - var6},
         {var3 - var7, var4 + var6, var3 + var7, var4 + var6 + var5}
      };
      GlStateManager.pushMatrix();
      GlStateManager.scale(var2, var2, var2);

      for (float[] var12 : var8) {
         if ((Boolean)var1.crosshairOutline.d()) {
            cn.kazer.client189.render.DrawRect.b(var12[0], var12[1], var12[2], var12[3], 0.5F, -1358954496, var1.crosshairColor.a());
         } else {
            cn.kazer.client189.render.DrawRect.a(var12[0], var12[1], var12[2], var12[3], var1.crosshairColor.a());
         }
      }

      GlStateManager.popMatrix();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static String tabName(String var0, Object var1) {
      NetworkPlayerInfo var2 = (NetworkPlayerInfo)var1;
      com.cheatbreaker.client.CheatBreakerClient var3 = com.cheatbreaker.client.CheatBreakerClient.N();
      Minecraft var4 = Minecraft.getMinecraft();
      UUID var5 = var2.getGameProfile().getId();
      boolean var6 = var5 != null && var5.equals(var4.getSession().getProfile().getId());
      if (var6 && ModuleTab.highlighting()) {
         if (TAB_OWN_NAMES.size() > 256) {
            TAB_OWN_NAMES.clear();
         }

         TAB_OWN_NAMES.add(var0);
      }

      if (var3 != null && ModuleTab.logoEnabled(var3.moduleManager.nametagModule.g() && (Boolean)var3.moduleManager.nametagModule.showBadgeInTab.d())) {
         boolean var7 = var5 != null
            && (var5.equals(var4.getSession().getProfile().getId()) || var3.L().a(var5))
            && com.cheatbreaker.client.module.modules.nametag.NametagLine.a(var0, var2.getGameProfile().getName());
         if (!var7) {
            return var0;
         } else {
            String var8 = "§r   " + var0;
            if (var6 && ModuleTab.highlighting()) {
               TAB_OWN_NAMES.add(var8);
            }

            if (TAB_COLORS.size() > 256) {
               TAB_COLORS.clear();
            }

            TAB_COLORS.put(var8, var3.L().d(var5));
            return var8;
         }
      } else {
         return var0;
      }
   }

   public static int tabText(Object var0, String var1, float var2, float var3, int var4) {
      FontRenderer var5 = (FontRenderer)var0;
      String var6 = var1.startsWith("§o") ? var1.substring(2) : var1;
      var4 = ModuleTab.nameColor(var4, TAB_OWN_NAMES.contains(var6));
      boolean var7 = ModuleTab.nameShadow(false);
      if (var6.startsWith("§r   ") && TAB_COLORS.containsKey(var6)) {
         cn.kazer.client189.render.GlStateBridge.c(1048575);

         try {
            Minecraft var8 = Minecraft.getMinecraft();
            var8.getTextureManager().bindTexture(new ResourceLocation("client/kazer_badge.png"));
            int var9 = TAB_COLORS.get(var6);
            GlStateManager.color((var9 >> 16 & 0xFF) / 255.0F, (var9 >> 8 & 0xFF) / 255.0F, (var9 & 0xFF) / 255.0F, 1.0F);
            Gui.drawModalRectWithCustomSizedTexture((int)var2, (int)var3, 0.0F, 0.0F, 7, 7, 7.0F, 7.0F);
         } finally {
            cn.kazer.client189.render.GlStateBridge.a();
         }

         return var5.drawString((var1.startsWith("§o") ? "§o" : "") + var6.substring("§r   ".length()), var2 + 9.0F, var3, var4, var7);
      } else {
         return var5.drawString(var1, var2, var3, var4, var7);
      }
   }

   public static boolean tabPing(Object var0) {
      NetworkPlayerInfo var1 = (NetworkPlayerInfo)var0;
      return var1 != null && var1.getResponseTime() >= 0;
   }

   public static boolean tabPing(int var0, int var1, int var2, Object var3) {
      NetworkPlayerInfo var4 = (NetworkPlayerInfo)var3;
      return tabPing(var3) && !ModuleTab.drawPing(var1, var2, var0, var4.getResponseTime());
   }

   public static boolean listKey(KeyBinding var0) {
      boolean var1 = var0.isKeyDown();
      return var0 == Minecraft.getMinecraft().gameSettings.keyBindPlayerList ? ModuleTab.listKey(var1) : var1;
   }

   public static boolean clearGlass(Object var0) {
      com.cheatbreaker.client.CheatBreakerClient var1 = com.cheatbreaker.client.CheatBreakerClient.N();
      if (var1 == null) {
         return false;
      } else {
         Block var2 = ((IBlockState)var0).getBlock();
         if (ModuleOverlay.hideBlock(var2)) {
            return true;
         } else {
            String var3 = ModuleOverlay.glass(String.valueOf(var1.globalSettings.clearGlass.d()));
            return var2 instanceof BlockGlass && (var3.equals("REGULAR") || var3.equals("ALL")) || var2 instanceof BlockStainedGlass && var3.equals("ALL");
         }
      }
   }

   public static boolean drawGuiBackground() {
      Minecraft var0 = Minecraft.getMinecraft();
      com.cheatbreaker.client.CheatBreakerClient var1 = com.cheatbreaker.client.CheatBreakerClient.N();
      if (var1 != null
         && var0.theWorld != null
         && !(var0.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.HudEditorScreen)
         && !(var0.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.ModulePlacementScreen)
         && (Boolean)var1.globalSettings.containerVignette.d()) {
         ScaledResolution var2 = new ScaledResolution(var0);
         cn.kazer.client189.render.ClientBridge.a(var2.getScaledWidth(), var2.getScaledHeight(), cn.kazer.client189.render.ClientBridge.a().a(Minecraft.getSystemTime()));
         return true;
      } else {
         return false;
      }
   }

   public static boolean hideAchievements() {
      return com.cheatbreaker.client.CheatBreakerClient.N() != null && !(Boolean)com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.showAchievements.d();
   }

   public static boolean hideGlint() {
      return !ModuleOverlay.glint(com.cheatbreaker.client.CheatBreakerClient.N() == null || (Boolean)com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.enchantmentGlint.d(), false);
   }

   public static boolean itemGlint(Object var0) {
      ItemStack var1 = (ItemStack)var0;
      return var1.hasEffect()
         && (
            !(var1.getItem() instanceof ItemPotion)
               || com.cheatbreaker.client.CheatBreakerClient.N() == null
               || (Boolean)com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.shinyPots.d()
         );
   }

   public static int hotbarScroll(int var0) {
      return com.cheatbreaker.client.CheatBreakerClient.N() != null && (Boolean)com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.disableHotbarScrolling.d() ? 0 : var0;
   }

   public static boolean zoomKeyboard(int var0) {
      return ModuleZoom.n() && var0 == cn.kazer.client189.render.ClientBridge.c().getKeyCode() ? false : Keyboard.isKeyDown(var0);
   }

   public static boolean zoomMouse(int var0) {
      return ModuleZoom.n() && var0 == cn.kazer.client189.render.ClientBridge.c().getKeyCode() + 100 ? false : Mouse.isButtonDown(var0);
   }

   public static boolean zoomKey(KeyBinding var0) {
      return ModuleZoom.n() && var0 == cn.kazer.client189.render.ClientBridge.c() ? false : GameSettings.isKeyDown(var0);
   }

   public static void blockOutline(AxisAlignedBB var0) {
      if (!com.cheatbreaker.client.kazerui.module.modules.visual.BlockOutlineModule.a(var0)) {
         RenderGlobal.drawSelectionBoundingBox(var0);
      }
   }

   public static void centerInventory(Object var0) {
      Minecraft var1 = Minecraft.getMinecraft();
      if (com.cheatbreaker.client.CheatBreakerClient.N() != null) {
         boolean var2 = (Boolean)com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.showPotionInfo.d();
         if (!var2) {
            ReflectionHelper.setPrivateValue(InventoryEffectRenderer.class, (InventoryEffectRenderer)var0, false, "hasActivePotionEffects", "hasActivePotionEffects");
         }

         if (!var2 || (Boolean)com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.keepInventoryCentered.d()) {
            GuiContainer var3 = (GuiContainer)var0;
            int var4 = ReflectionHelper.<Integer, GuiContainer>getPrivateValue(GuiContainer.class, var3, "xSize", "xSize");
            ReflectionHelper.setPrivateValue(GuiContainer.class, var3, (var3.width - var4) / 2, "guiLeft", "guiLeft");
         }
      }
   }

   public static boolean hideEntity(Object var0) {
      Minecraft var1 = Minecraft.getMinecraft();
      com.cheatbreaker.client.CheatBreakerClient var2 = com.cheatbreaker.client.CheatBreakerClient.N();
      if (var2 != null && var1.thePlayer != null && var0 != var1.thePlayer) {
         int var3 = ((Number)var2.globalSettings.entityDistance.d()).intValue();
         return ((Entity)var0).getDistanceSqToEntity(var1.thePlayer) > com.cheatbreaker.client.settings.SettingsMathUtil.a(var3);
      } else {
         return false;
      }
   }

   public static boolean hideTile(Object var0) {
      Minecraft var1 = Minecraft.getMinecraft();
      com.cheatbreaker.client.CheatBreakerClient var2 = com.cheatbreaker.client.CheatBreakerClient.N();
      if (var2 != null && var1.thePlayer != null) {
         int var3 = ((Number)var2.globalSettings.tileEntityDistance.d()).intValue();
         return ((TileEntity)var0).getDistanceSq(var1.thePlayer.posX, var1.thePlayer.posY, var1.thePlayer.posZ)
            > com.cheatbreaker.client.settings.SettingsMathUtil.a(var3);
      } else {
         return false;
      }
   }

   public static boolean dropParticle() {
      if (ModuleParticleChanger.active()) {
         return false;
      } else {
         com.cheatbreaker.client.CheatBreakerClient var0 = com.cheatbreaker.client.CheatBreakerClient.N();
         if (var0 == null) {
            return false;
         } else {
            int var1 = ((Number)var0.globalSettings.particleAmount.d()).intValue();
            return var1 < 100 && !com.cheatbreaker.client.settings.SettingsMathUtil.a(var1, ThreadLocalRandom.current().nextInt(100));
         }
      }
   }

   public static long chunkDeadline(long var0) {
      com.cheatbreaker.client.CheatBreakerClient var2 = com.cheatbreaker.client.CheatBreakerClient.N();
      return var2 == null
         ? var0
         : cn.kazer.client189.runtime.ChunkLoadBudget.a(
            System.nanoTime(), var0, (Boolean)var2.globalSettings.enableFpsBoost.d(), ((Number)var2.globalSettings.slowChunkLoading.d()).intValue()
         );
   }

   public static int frameLimit(int var0) {
      com.cheatbreaker.client.CheatBreakerClient var1 = com.cheatbreaker.client.CheatBreakerClient.N();
      if (var1 == null) {
         return var0;
      } else {
         if (Minecraft.getMinecraft().theWorld == null) {
            int var2 = ((Number)var1.globalSettings.maxMainMenuFps.d()).intValue();
            var0 = com.cheatbreaker.client.kazerui.OwnedUi.a(Minecraft.getMinecraft().currentScreen) ? var2 : Math.min(var0, var2);
         }

         return com.cheatbreaker.client.settings.SettingsMathUtil.a(
            var0, Display.isActive(), (Boolean)var1.globalSettings.limitUnfocusedFps.d(), ((Number)var1.globalSettings.maxUnfocusedFps.d()).intValue()
         );
      }
   }

   public static int sprintTimer(int var0) {
      com.cheatbreaker.client.CheatBreakerClient var1 = com.cheatbreaker.client.CheatBreakerClient.N();
      return var1 != null && var1.ownedUi != null && !com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakModule.n() ? 0 : var0;
   }

   public static float gamma(float var0) {
      com.cheatbreaker.client.CheatBreakerClient var1 = com.cheatbreaker.client.CheatBreakerClient.N();
      return var1 == null ? var0 : com.cheatbreaker.client.kazerui.module.modules.mechanic.FullbrightModule.a(var0);
   }

   public static void lightingTexture(Object var0) {
      if (com.cheatbreaker.client.kazerui.module.modules.mechanic.FullbrightModule.n()) {
         Arrays.fill(((DynamicTexture)var0).getTextureData(), -1);
      }
   }

   private static com.cheatbreaker.client.module.modules.shader.MotionBlurModule blur() {
      return com.cheatbreaker.client.CheatBreakerClient.N() != null && com.cheatbreaker.client.CheatBreakerClient.N().ownedUi == null ? com.cheatbreaker.client.module.modules.shader.MotionBlurModule.a() : null;
   }

   public static void worldBegin(float var0) {
      if (blur() != null) {
         blur().c(var0);
      }
   }

   public static void cameraReady(float var0) {
      if (blur() != null) {
         blur().d(var0);
      }
   }

   public static void worldEnd(float var0) {
      if (blur() != null) {
         blur().e(var0);
      }
   }

   public static void motionBlur() {
      com.cheatbreaker.client.CheatBreakerClient var0 = com.cheatbreaker.client.CheatBreakerClient.N();
      if (var0 != null && var0.ownedUi != null) {
         com.cheatbreaker.client.kazerui.module.modules.visual.MotionBlurFxModule.n();
      } else if (blur() != null) {
         blur().B();
      }

      com.cheatbreaker.client.kazerui.module.modules.visual.ColorSaturationModule.n();
   }

   public static void pluginMessage(Object var0) {
      Minecraft var1 = Minecraft.getMinecraft();
      if (var1.isCallingFromMinecraftThread() && com.cheatbreaker.client.CheatBreakerClient.N() != null) {
         S3FPacketCustomPayload var2 = (S3FPacketCustomPayload)var0;
         byte[] var3 = new byte[var2.getBufferData().readableBytes()];
         var2.getBufferData().getBytes(var2.getBufferData().readerIndex(), var3);
         com.cheatbreaker.client.CheatBreakerClient.N().eventBus.a(new com.cheatbreaker.client.event.event.PluginMessageEvent(var2.getChannelName(), var3));
      }
   }

   public static boolean hideVanillaScoreboard() {
      com.cheatbreaker.client.CheatBreakerClient var0 = com.cheatbreaker.client.CheatBreakerClient.N();
      return var0 != null && (var0.ownedUi == null ? var0.moduleManager.scoreboard.g() : var0.ownedUi.a().b("scoreboard"));
   }

   public static boolean beginChat() {
      if (ChatRuntime.a()) {
         GlStateManager.pushMatrix();
         return true;
      } else {
         Minecraft var0 = Minecraft.getMinecraft();
         com.cheatbreaker.client.CheatBreakerClient var1 = com.cheatbreaker.client.CheatBreakerClient.N();
         if (var1 == null) {
            GlStateManager.pushMatrix();
            return true;
         } else {
            com.cheatbreaker.client.module.modules.ChatModule var2 = var1.moduleManager.chatModule;
            if (!var2.h(var0.ingameGUI.getChatGUI().getChatOpen())) {
               return false;
            } else {
               GlStateManager.pushMatrix();
               if (var2.g()) {
                  ScaledResolution var3 = new ScaledResolution(var0);
                  GlStateManager.translate(0.0F, -(var3.getScaledHeight() - 48), 0.0F);
                  var2.C();
                  var2.a(var3);
                  GlStateManager.translate(0.0F, var2.j() - 20.0F, 0.0F);
               }

               return true;
            }
         }
      }
   }

   public static String chatSend(String var0) {
      String var1 = com.cheatbreaker.client.kazerui.module.OutgoingChatEvent.b(var0);
      return var1 != null && !var1.isEmpty() ? var1 : null;
   }

   public static void chatClose(Minecraft var0, GuiScreen var1, GuiTextField var2, int var3) {
      if (var1 == null && (var3 == 28 || var3 == 156) && com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatHooks.f()) {
         var2.setText("");
      } else {
         var0.displayGuiScreen(var1);
      }
   }

   public static void chatInputRect(int var0, int var1, int var2, int var3, int var4) {
      com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatHooks.b(var0, var1, var2, var3, var4);
   }

   public static void endChat() {
      GlStateManager.popMatrix();
   }

   public static IChatComponent chatComponent(Object var0, int var1, int var2) {
      if (ChatRuntime.a()) {
         return ChatRuntime.a(var0, var1, var2);
      } else {
         Minecraft var3 = Minecraft.getMinecraft();
         GuiNewChat var4 = (GuiNewChat)var0;
         com.cheatbreaker.client.module.modules.ChatModule var5 = com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.chatModule;
         if (var4.getChatOpen() && var5.h(true) && var5.q()) {
            var5.C();
            ScaledResolution var6 = new ScaledResolution(var3);
            float var7 = ((Number)var5.scale.d()).floatValue();
            float[] var8 = var5.a(var6, true);
            float var9 = var7 * var4.getChatScale();
            float var10 = (var8[0] + 2.0F) * var7;
            float var11 = var6.getScaledHeight() - (var8[1] + var5.j()) * var7;
            int var12 = MathHelper.floor_float(((float)var1 / var6.getScaleFactor() - var10) / var9);
            int var13 = MathHelper.floor_float(((float)var2 / var6.getScaleFactor() - var11) / var9);
            KazerChatAccess var14 = (KazerChatAccess)var4;
            List var15 = var14.kazerDrawnLines();
            if (var12 >= 0 && var13 >= 0 && !(var12 > var4.getChatWidth() / var4.getChatScale()) && var13 < Math.min(var4.getLineCount(), var15.size()) * 9) {
               int var16 = var14.kazerChatScroll();
               int var17 = var13 / 9 + var16;
               if (var17 >= 0 && var17 < var15.size()) {
                  int var18 = 0;

                  for (IChatComponent var20 : ((ChatLine)var15.get(var17)).getChatComponent()) {
                     if (var20 instanceof ChatComponentText) {
                        var18 += var3.fontRendererObj
                           .getStringWidth(GuiUtilRenderComponents.func_178909_a(((ChatComponentText)var20).getChatComponentText_TextValue(), false));
                        if (var18 > var12) {
                           return var20;
                        }
                     }
                  }

                  return null;
               } else {
                  return null;
               }
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   public static boolean customChatHit() {
      return ChatRuntime.a() || com.cheatbreaker.client.CheatBreakerClient.N() != null && com.cheatbreaker.client.CheatBreakerClient.N().moduleManager.chatModule.g();
   }

   public static void chatRect(int var0, int var1, int var2, int var3, int var4) {
      com.cheatbreaker.client.CheatBreakerClient var5 = com.cheatbreaker.client.CheatBreakerClient.N();
      if (var5 == null || !var5.moduleManager.chatModule.g() || (Boolean)var5.moduleManager.chatModule.showBackground.d()) {
         Gui.drawRect(var0, var1, var2, var3, var4);
      }
   }
}
