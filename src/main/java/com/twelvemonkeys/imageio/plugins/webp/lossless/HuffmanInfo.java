package com.twelvemonkeys.imageio.plugins.webp.lossless;

import java.awt.image.Raster;

final class HuffmanInfo {
   public final Raster huffmanMetaCodes;
   public final int metaCodeBits;
   public final HuffmanCodeGroup[] huffmanGroups;

   public HuffmanInfo(Raster var1, int var2, HuffmanCodeGroup[] var3) {
      this.huffmanMetaCodes = var1;
      this.metaCodeBits = var2;
      this.huffmanGroups = var3;
   }
}
