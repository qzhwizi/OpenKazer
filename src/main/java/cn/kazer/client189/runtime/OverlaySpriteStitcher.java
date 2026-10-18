package cn.kazer.client189.runtime;

import java.lang.reflect.Method;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class OverlaySpriteStitcher {
   private static Method setTextureEntry;

   @SubscribeEvent
   public void stitch(Pre var1) {
      try {
         if (setTextureEntry == null) {
            setTextureEntry = var1.map.getClass().getMethod("setTextureEntry", String.class, TextureAtlasSprite.class);
         }

         setTextureEntry.invoke(var1.map, "kazerui:overlay_white", new com.cheatbreaker.client.kazerui.module.modules.visual.OverlayWhiteSprite());
      } catch (ReflectiveOperationException var3) {
         throw new IllegalStateException("Could not register the owned Overlay atlas tile", var3);
      }
   }
}
