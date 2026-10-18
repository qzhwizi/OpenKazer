package com.twelvemonkeys.imageio.plugins.webp;

final class VP8xChunk extends RIFFChunk {
   int width;
   int height;
   boolean isLossless;
   boolean containsICCP;
   boolean containsALPH;
   boolean containsEXIF;
   boolean containsXMP_;
   boolean containsANIM;

   VP8xChunk(int var1, long var2, long var4) {
      super(var1, var2, var4);
   }

   @Override
   public String toString() {
      return super.toString()
         + "[width="
         + this.width
         + ", height="
         + this.height
         + ", lossless="
         + (this.isLossless ? "RGB" : "")
         + (this.containsALPH ? "A" : (this.isLossless ? "" : "false"))
         + ", flags="
         + (this.containsICCP ? "I" : "")
         + (this.containsALPH ? "L" : "")
         + (this.containsEXIF ? "E" : "")
         + (this.containsXMP_ ? "X" : "")
         + (this.containsANIM ? "A" : "")
         + ']';
   }
}
