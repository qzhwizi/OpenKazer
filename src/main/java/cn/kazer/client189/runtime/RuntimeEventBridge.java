package cn.kazer.client189.runtime;

import cn.kazer.client189.animation.OldAnimationsAdapter;
import cn.kazer.client189.input.ImeHooks;
import cn.kazer.client189.input.WindowsFullscreenImeSupport;
import cn.kazer.client189.input.WindowsImeSupport;
import com.cheatbreaker.client.kazerui.module.modules.hud.CombatHud;
import com.cheatbreaker.client.kazerui.module.modules.mechanic.ModuleZoom;
import com.cheatbreaker.client.kazerui.module.render.TooltipHooks;
import net.minecraft.MinecraftMovementInputHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent.FOVModifier;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public final class RuntimeEventBridge {
   private final Minecraft mc = Minecraft.getMinecraft();
   private WorldClient world;
   private final cn.kazer.client189.runtime.ParitySmokeTest smoke = new cn.kazer.client189.runtime.ParitySmokeTest();
   private boolean ownsGuiBlur;
   private boolean cosmeticsInstalled;
   private boolean handFov;
   private boolean foodDrawn;

   private com.cheatbreaker.client.CheatBreakerClient a() {
      return com.cheatbreaker.client.CheatBreakerClient.N();
   }

   @SubscribeEvent
   public void attack(AttackEntityEvent var1) {
      if (var1.entityPlayer == this.mc.thePlayer) {
         CombatHud.a(var1.target);
      }
   }

   @SubscribeEvent
   public void models(ModelBakeEvent var1) {
      cn.kazer.client189.runtime.TripwireTintModel.a(var1);
   }

   @SubscribeEvent
   public void tick(ClientTickEvent var1) {
      if (var1.phase == Phase.END) {
         if (!this.cosmeticsInstalled) {
            this.cosmeticsInstalled = true;
            com.cheatbreaker.client.util.WindowIconUtils.a();
            KazerCosmeticLayer.a();
         }

         cn.kazer.client189.runtime.NametagBatchRenderer.a();
         WindowsFullscreenImeSupport.onClientTick();
         OldAnimationsAdapter.tick();
         if (this.world != this.mc.theWorld) {
            if (this.world != null && this.mc.theWorld == null) {
               this.a().netHandler.a();
            }

            this.world = this.mc.theWorld;
            this.a().eventBus.a(new com.cheatbreaker.client.event.event.WorldLoadEvent(this.world));
         }

         if (this.mc.thePlayer != null && !(this.mc.thePlayer.movementInput instanceof cn.kazer.client189.runtime.RuntimeEventBridge.a)) {
            this.mc.thePlayer.movementInput = new cn.kazer.client189.runtime.RuntimeEventBridge.a(this.mc.gameSettings);
         }

         this.a().eventBus.a(new com.cheatbreaker.client.event.event.ClientTickEvent());
         if (this.mc.currentScreen == null) {
            cn.kazer.client189.render.ClientBridge.a().a();
         }

         boolean var2 = this.mc.currentScreen != null && this.mc.theWorld != null && (Boolean)this.a().globalSettings.guiBlur.d();
         if (var2 && !this.ownsGuiBlur && OpenGlHelper.shadersSupported) {
            cn.kazer.client189.render.ClientBridge.d();
            this.ownsGuiBlur = true;
         } else if (!var2 && this.ownsGuiBlur) {
            this.mc.entityRenderer.stopUseShader();
            this.ownsGuiBlur = false;
         }
      }
   }

   @SubscribeEvent
   public void key(KeyInputEvent var1) {
      if (Keyboard.getEventKeyState() && this.mc.currentScreen == null) {
         this.a().eventBus.a(new com.cheatbreaker.client.event.event.KeyPressEvent(Keyboard.getEventKey()));
      }
   }

   @SubscribeEvent
   public void mouse(MouseEvent var1) {
      if (ModuleZoom.a(var1.dwheel)) {
         var1.setCanceled(true);
      }

      if (var1.button >= 0 && var1.buttonstate && this.mc.currentScreen == null) {
         this.a().eventBus.a(new com.cheatbreaker.client.event.event.MouseClickEvent(var1.button));
      }
   }

   @SubscribeEvent
   public void renderTick(RenderTickEvent var1) {
      if (var1.phase == Phase.START) {
         WindowsImeSupport.a();
         cn.kazer.client189.render.ClientBridge.partialTicks = var1.renderTickTime;
         cn.kazer.client189.runtime.NametagBatchRenderer.b();
      } else {
         WindowsImeSupport.c();
         this.handFov = false;
         this.smoke.a();
      }
   }

   @SubscribeEvent
   public void playerFov(FOVUpdateEvent var1) {
      var1.newfov = com.cheatbreaker.client.kazerui.module.modules.mechanic.FovModule.a(var1.newfov);
   }

   @SubscribeEvent
   public void handFov(RenderHandEvent var1) {
      this.handFov = true;
   }

   @SubscribeEvent
   public void cameraFov(FOVModifier var1) {
      float var2 = var1.getFOV();
      if (this.handFov) {
         this.handFov = false;
      } else {
         var2 = com.cheatbreaker.client.kazerui.module.modules.mechanic.FovModule.b(var2);
      }

      var1.setFOV(ModuleZoom.a(var2, (float)var1.renderPartialTicks));
   }

   @SubscribeEvent
   public void open(GuiOpenEvent var1) {
      WindowsImeSupport.b();
      StartupSplashHooks.finish();
      if (var1.gui != null && var1.gui.getClass() == GuiMultiplayer.class) {
         GuiScreen var2 = ReflectionHelper.getPrivateValue(GuiMultiplayer.class, (GuiMultiplayer)var1.gui, "parentScreen", "parentScreen");
         var1.gui = new cn.kazer.client189.screens.MultiplayerScreenEscReturn(var2);
      }

      if (var1.gui instanceof GuiChat) {
         com.cheatbreaker.client.kazerui.gui.OnceTracker.a(var1.gui);
      }

      if (var1.gui instanceof GuiIngameMenu && !(var1.gui instanceof cn.kazer.client189.screens.KazerIngameMenu)) {
         var1.gui = new cn.kazer.client189.screens.KazerIngameMenu();
      }

      if ((var1.gui instanceof GuiMainMenu || var1.gui instanceof com.cheatbreaker.client.gui.screens.KazerMainMenuScreen) && this.a().ownedUi != null) {
         var1.gui = this.a().ownedUi.d();
      } else if (var1.gui instanceof com.cheatbreaker.client.gui.hudeditor.HudEditorScreen && this.a().ownedUi != null) {
         GuiScreen var10002 = this.mc.theWorld == null ? this.a().ownedUi.d() : null;
         var1.gui = this.a().ownedUi.b(var10002);
      } else if (var1.gui instanceof com.cheatbreaker.client.gui.hudeditor.ModulePlacementScreen && this.a().ownedUi != null) {
         var1.gui = this.a().ownedUi.c(null);
      }

      if (var1.gui instanceof GuiScreenResourcePacks && !(var1.gui instanceof cn.kazer.client189.screens.KazerResourcePacksScreen)) {
         GuiScreen var3 = ReflectionHelper.getPrivateValue(GuiScreenResourcePacks.class, (GuiScreenResourcePacks)var1.gui, "parentScreen", "parentScreen");
         var1.gui = new cn.kazer.client189.screens.KazerResourcePacksScreen(var3);
      }
   }

   @SubscribeEvent
   public void overlay(Post var1) {
      if (var1.type == ElementType.FOOD) {
         this.foodDrawn = true;
      }

      if (var1.type == ElementType.ALL) {
         com.cheatbreaker.client.kazerui.module.modules.hud.SaturationHudModule var2 = (com.cheatbreaker.client.kazerui.module.modules.hud.SaturationHudModule)com.cheatbreaker.client.kazerui.module.ModuleRegistry.a(
            "saturation"
         );
         if (var2 != null) {
            var2.a(var1.resolution, this.foodDrawn);
         }

         this.foodDrawn = false;
      }

      if (var1.type == ElementType.ALL && this.mc.thePlayer != null) {
         if (!this.mc.gameSettings.showDebugInfo || (Boolean)this.a().globalSettings.showHudInDebug.d()) {
            this.a()
               .eventBus
               .a(
                  !(this.mc.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.HudEditorScreen)
                        && !(this.mc.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.ModulePlacementScreen)
                     ? new com.cheatbreaker.client.event.event.RenderHudEvent(var1.resolution)
                     : new com.cheatbreaker.client.event.event.RenderHudEditorEvent(var1.resolution)
               );
         }
      }
   }

   @SubscribeEvent
   public void tooltip(ItemTooltipEvent var1) {
      TooltipHooks.a(var1.itemStack, var1.toolTip);
   }

   @SubscribeEvent
   public void overlayPre(Pre var1) {
      if (var1.type == ElementType.BOSSHEALTH && this.a().ownedUi.b()) {
         var1.setCanceled(true);
      }

      if (var1.type == ElementType.ARMOR && this.a().ownedUi.a(var1.resolution, GuiIngameForge.left_height)) {
         GuiIngameForge.left_height = com.cheatbreaker.client.kazerui.overlay.OverlayLayout.left_height;
         var1.setCanceled(true);
      }

      if (var1.type == ElementType.CROSSHAIRS) {
         if (this.mc.gameSettings.thirdPersonView != 0 && !(Boolean)this.a().globalSettings.showCrosshairThirdPerson.d()) {
            var1.setCanceled(true);
         } else if ((Boolean)this.a().globalSettings.customCrosshair.d()) {
            var1.setCanceled(true);
            RuntimeHooks.customCrosshair(var1.resolution);
         }
      }
   }

   @SubscribeEvent
   public void namePre(net.minecraftforge.client.event.RenderLivingEvent.Specials.Pre var1) {
      if (var1.entity instanceof com.cheatbreaker.client.kazerui.concurrent.rendercache.PreviewPlayer) {
         var1.setCanceled(true);
      } else {
         if (var1.entity instanceof EntityPlayer && this.a().moduleManager.nametagModule.g()) {
            var1.setCanceled(true);
         }
      }
   }

   @SubscribeEvent
   public void namePost(net.minecraftforge.client.event.RenderPlayerEvent.Post var1) {
      KazerCosmeticLayer.a(var1.renderer);
      cn.kazer.client189.runtime.NametagBatchRenderer.a(var1);
   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public void cosmeticParts(net.minecraftforge.client.event.RenderLivingEvent.Pre var1) {
      KazerCosmeticLayer.a(var1);
   }

   @SubscribeEvent
   public void menuFooter(net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent.Post var1) {
      com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatHooks.a(var1.gui, var1.mouseX, var1.mouseY);
      ImeHooks.screen(var1.gui);
      com.cheatbreaker.client.gui.render.InventoryBrandRenderer.a(var1.gui.width, var1.gui.height);
      com.cheatbreaker.client.kazerui.gui.ToastNotifications.a();
   }

   @SubscribeEvent
   public void chatMouse(net.minecraftforge.client.event.GuiScreenEvent.MouseInputEvent.Pre var1) {
      if (com.cheatbreaker.client.kazerui.module.modules.visual.ScrollableTooltipsModule.a(
         var1.gui,
         Mouse.getEventX() * var1.gui.width / this.mc.displayWidth,
         var1.gui.height - Mouse.getEventY() * var1.gui.height / this.mc.displayHeight - 1,
         Mouse.getEventDWheel()
      )) {
         var1.setCanceled(true);
      } else {
         if (com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatHooks.a(var1.gui)) {
            var1.setCanceled(true);
         }
      }
   }

   @SubscribeEvent
   public void chatKey(net.minecraftforge.client.event.GuiScreenEvent.KeyboardInputEvent.Pre var1) {
      if (var1.gui instanceof GuiChat
         && Keyboard.getEventKeyState()
         && (Keyboard.getEventKey() == 28 || Keyboard.getEventKey() == 156)
         && !com.cheatbreaker.client.kazerui.gui.OnceTracker.b(var1.gui)) {
         var1.setCanceled(true);
      } else {
         com.cheatbreaker.client.kazerui.module.modules.mechanic.ChatHooks.b(var1.gui);
      }
   }

   @SubscribeEvent
   public void worldRender(RenderWorldLastEvent var1) {
      com.cheatbreaker.client.kazerui.module.modules.visual.LightOverlayModule.a(var1.partialTicks);
      this.a().eventBus.a(new com.cheatbreaker.client.event.event.RenderTickEvent(var1.partialTicks));
      cn.kazer.client189.runtime.NametagBatchRenderer.c();
   }

   private final class a extends MinecraftMovementInputHelper {
      a(GameSettings var2) {
         super(var2);
      }

      public void updatePlayerMoveState() {
         if (RuntimeEventBridge.this.mc.thePlayer == null) {
            super.updatePlayerMoveState();
         } else if (RuntimeEventBridge.this.a().ownedUi != null) {
            com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakModule.a(RuntimeEventBridge.this.mc.thePlayer, this);
         } else {
            lIIIIlIIllIIlIIlIIIlIIllI(RuntimeEventBridge.this.mc, this, RuntimeEventBridge.this.mc.thePlayer);
            if (RuntimeEventBridge.this.a().moduleManager.toggleSprint.g()
               && isSprinting
               && this.moveForward >= 0.8F
               && !this.sneak
               && !RuntimeEventBridge.this.mc.thePlayer.isUsingItem()
               && !RuntimeEventBridge.this.mc.thePlayer.isCollidedHorizontally
               && (RuntimeEventBridge.this.mc.thePlayer.getFoodStats().getFoodLevel() > 6 || RuntimeEventBridge.this.mc.thePlayer.capabilities.allowFlying)) {
               RuntimeEventBridge.this.mc.thePlayer.setSprinting(true);
            }
         }
      }
   }
}
