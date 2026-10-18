package com.twelvemonkeys.imageio.stream;

import com.twelvemonkeys.lang.Validate;
import java.io.IOException;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageInputStreamImpl;

public final class SubImageInputStream extends ImageInputStreamImpl {
   private final ImageInputStream stream;
   private final long startPos;
   private final long length;

   public SubImageInputStream(ImageInputStream var1, long var2) throws IOException {
      Validate.notNull(var1, "stream");
      Validate.isTrue(var2 >= 0L, var2, "length < 0: %d");
      this.stream = var1;
      this.startPos = var1.getStreamPosition();
      this.length = var2;
   }

   @Override
   public int read() throws IOException {
      if (this.streamPos >= this.length) {
         return -1;
      } else {
         int var1 = this.stream.read();
         if (var1 >= 0) {
            this.streamPos++;
         }

         return var1;
      }
   }

   @Override
   public int read(byte[] var1, int var2, int var3) throws IOException {
      if (this.streamPos >= this.length) {
         return -1;
      } else {
         int var4 = (int)Math.min((long)var3, this.length - this.streamPos);
         int var5 = this.stream.read(var1, var2, var4);
         if (var5 >= 0) {
            this.streamPos += var5;
         }

         return var5;
      }
   }

   @Override
   public long length() {
      try {
         long var1 = this.stream.length();
         return var1 < 0L ? -1L : Math.min(var1 - this.startPos, this.length);
      } catch (IOException var3) {
         return -1L;
      }
   }

   @Override
   public void seek(long var1) throws IOException {
      if (var1 < this.getFlushedPosition()) {
         throw new IndexOutOfBoundsException("pos < flushedPosition");
      } else {
         this.stream.seek(this.startPos + var1);
         this.streamPos = var1;
      }
   }

   @Override
   protected void finalize() {
   }
}
