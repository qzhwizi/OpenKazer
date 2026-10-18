package com.twelvemonkeys.imageio.stream;

import com.twelvemonkeys.lang.Validate;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.stream.ImageInputStreamImpl;

public final class DirectImageInputStream extends ImageInputStreamImpl {
   private final InputStream stream;
   private final long length;

   public DirectImageInputStream(InputStream var1) {
      this(var1, -1L);
   }

   public DirectImageInputStream(InputStream var1, long var2) {
      this.stream = Validate.notNull(var1, "stream");
      this.length = Validate.isTrue(var2 >= 0L || var2 == -1L, var2, "negative length: %d");
   }

   @Override
   public int read() throws IOException {
      this.bitOffset = 0;
      this.streamPos++;
      return this.stream.read();
   }

   @Override
   public int read(byte[] var1, int var2, int var3) throws IOException {
      this.bitOffset = 0;
      int var4 = this.stream.read(var1, var2, var3);
      if (var4 > 0) {
         this.streamPos += var4;
      }

      return var4;
   }

   @Override
   public void seek(long var1) throws IOException {
      this.checkClosed();
      if (var1 < this.streamPos) {
         throw new IndexOutOfBoundsException("pos < flushedPos");
      } else {
         this.bitOffset = 0;

         while (this.streamPos < var1) {
            long var3 = this.stream.skip(var1 - this.streamPos);
            if (var3 <= 0L) {
               break;
            }

            this.streamPos += var3;
         }
      }
   }

   @Override
   public long getFlushedPosition() {
      return this.streamPos;
   }

   @Override
   public long length() {
      return this.length;
   }

   @Override
   public int readBit() throws IOException {
      throw new UnsupportedOperationException("Bit reading not supported");
   }

   @Override
   public long readBits(int var1) throws IOException {
      throw new UnsupportedOperationException("Bit reading not supported");
   }

   @Override
   public void close() throws IOException {
      this.stream.close();
      super.close();
   }
}
