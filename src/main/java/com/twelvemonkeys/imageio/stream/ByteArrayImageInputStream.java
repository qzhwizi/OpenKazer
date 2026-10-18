package com.twelvemonkeys.imageio.stream;

import com.twelvemonkeys.lang.Validate;
import java.io.IOException;
import javax.imageio.stream.ImageInputStreamImpl;

public final class ByteArrayImageInputStream extends ImageInputStreamImpl {
   private final byte[] data;
   private final int dataOffset;
   private final int dataLength;

   public ByteArrayImageInputStream(byte[] var1) {
      this(var1, 0, var1 != null ? var1.length : -1);
   }

   public ByteArrayImageInputStream(byte[] var1, int var2, int var3) {
      this.data = Validate.notNull(var1, "data");
      this.dataOffset = isMax(var1.length, var2, "offset");
      this.dataLength = isMax(var1.length - var2, var3, "length");
   }

   private static int isMax(int var0, int var1, String var2) {
      return Validate.isTrue(var1 >= 0 && var1 <= var0, var1, String.format("%s out of range [0, %d]: %d", var2, var0, var1));
   }

   @Override
   public int read() throws IOException {
      if (this.streamPos >= this.dataLength) {
         return -1;
      } else {
         this.bitOffset = 0;
         return this.data[(int)(this.streamPos++) + this.dataOffset] & 0xFF;
      }
   }

   @Override
   public int read(byte[] var1, int var2, int var3) throws IOException {
      if (this.streamPos >= this.dataLength) {
         return -1;
      } else {
         int var4 = (int)Math.min(this.dataLength - this.streamPos, (long)var3);
         this.bitOffset = 0;
         System.arraycopy(this.data, (int)this.streamPos + this.dataOffset, var1, var2, var4);
         this.streamPos += var4;
         return var4;
      }
   }

   @Override
   public long length() {
      return this.dataLength;
   }

   @Override
   public boolean isCached() {
      return true;
   }

   @Override
   public boolean isCachedMemory() {
      return true;
   }
}
