package cn.kazer.client189.animation;

import java.util.Map;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.SortingIndex;

@MCVersion("1.8.9")
@SortingIndex(1001)
public final class KazerCorePlugin implements IFMLLoadingPlugin {
   @Override
   public String[] getASMTransformerClass() {
      return new String[]{
         BlockHitTransformer.class.getName(),
         "cn.kazer.client189.runtime.RuntimeTransformer",
         "cn.kazer.client189.runtime.OverlayTransformer",
         "cn.kazer.client189.runtime.SevenVisualTransformer",
         "cn.kazer.client189.runtime.StartupSplashTransformer"
      };
   }

   @Override
   public String getModContainerClass() {
      return null;
   }

   @Override
   public String getSetupClass() {
      return null;
   }

   @Override
   public void injectData(Map<String, Object> var1) {
   }

   @Override
   public String getAccessTransformerClass() {
      return null;
   }
}
