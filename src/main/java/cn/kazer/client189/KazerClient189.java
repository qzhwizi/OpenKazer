package cn.kazer.client189;

import com.cheatbreaker.client.util.KazerBuildInfo;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.Display;

@Mod(
   modid = "kazerclient",
   name = "Kazer Client",
   version = "0.1.39",
   acceptedMinecraftVersions = "[1.8.9]"
)
public final class KazerClient189 {
   public static final String MOD_ID = "kazerclient";
   public static final String VERSION = "0.1.39";

   @EventHandler
   @SideOnly(Side.CLIENT)
   public void preInit(FMLPreInitializationEvent var1) {
      MinecraftForge.EVENT_BUS.register(new cn.kazer.client189.runtime.TripwireTintModel.b());
      MinecraftForge.EVENT_BUS.register(new cn.kazer.client189.runtime.OverlaySpriteStitcher());
   }

   @EventHandler
   @SideOnly(Side.CLIENT)
   public void init(FMLInitializationEvent var1) {
      com.cheatbreaker.client.gui.screens.StartupProgressScreen var2 = new com.cheatbreaker.client.gui.screens.StartupProgressScreen(3);
      var2.a("Mods");
      Display.update();
      com.cheatbreaker.client.CheatBreakerClient var3 = new com.cheatbreaker.client.CheatBreakerClient();
      var3.b();
      var2.a("Configuration");
      Display.update();
      var3.moduleManager.blockHitAnimation.a();
      KeyBinding.resetKeyBindingArrayAndHash();
      cn.kazer.client189.runtime.RuntimeEventBridge var4 = new cn.kazer.client189.runtime.RuntimeEventBridge();
      MinecraftForge.EVENT_BUS.register(var4);
      FMLCommonHandler.instance().bus().register(var4);
      Display.setTitle(KazerBuildInfo.windowTitle("1.8.9"));
      var2.a("Ready");
      Display.update();
      cn.kazer.client189.runtime.ParitySmokeTest.a(var2);
   }
}
