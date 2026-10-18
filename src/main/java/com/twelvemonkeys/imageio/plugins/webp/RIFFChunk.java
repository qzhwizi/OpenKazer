package com.twelvemonkeys.imageio.plugins.webp;

abstract class RIFFChunk {
   final int fourCC;
   final long length;
   final long offset;

   RIFFChunk(int var1, long var2, long var4) {
      this.fourCC = var1;
      this.length = var2;
      this.offset = var4;
   }

   @Override
   public String toString() {
      return WebPImageReader.fourCC(this.fourCC).replace(' ', '_') + "Chunk@" + this.offset + "|" + this.length;
   }
}
