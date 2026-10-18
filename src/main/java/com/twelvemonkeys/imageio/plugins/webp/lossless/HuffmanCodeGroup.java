package com.twelvemonkeys.imageio.plugins.webp.lossless;

import com.twelvemonkeys.imageio.plugins.webp.LSBBitReader;
import java.io.IOException;

final class HuffmanCodeGroup {
   public final HuffmanTable mainCode;
   public final HuffmanTable redCode;
   public final HuffmanTable blueCode;
   public final HuffmanTable alphaCode;
   public final HuffmanTable distanceCode;

   public HuffmanCodeGroup(LSBBitReader var1, int var2) throws IOException {
      this.mainCode = new HuffmanTable(var1, 280 + (var2 > 0 ? 1 << var2 : 0));
      this.redCode = new HuffmanTable(var1, 256);
      this.blueCode = new HuffmanTable(var1, 256);
      this.alphaCode = new HuffmanTable(var1, 256);
      this.distanceCode = new HuffmanTable(var1, 40);
   }
}
