package com.twelvemonkeys.imageio.plugins.webp;

import java.awt.Rectangle;

final class AnimationFrame extends RIFFChunk {
   final Rectangle bounds;
   final int duration;
   final boolean blend;
   final boolean dispose;

   AnimationFrame(long var1, long var3, Rectangle var5, int var6, int var7) {
      super(1179471425, var1, var3);
      this.bounds = var5.getBounds();
      this.duration = var6;
      this.blend = (var7 & 2) == 0;
      this.dispose = (var7 & 1) != 0;
   }
}
