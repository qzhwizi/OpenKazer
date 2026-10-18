package com.twelvemonkeys.imageio.util;

import com.twelvemonkeys.lang.Validate;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.stream.ImageInputStream;

class IIOInputStreamAdapter extends InputStream {
   private ImageInputStream input;
   private final boolean hasLength;
   private long left;
   private long markPosition;

   public IIOInputStreamAdapter(ImageInputStream var1) {
      this(var1, -1L, false);
   }

   public IIOInputStreamAdapter(ImageInputStream var1, long var2) {
      this(var1, var2, true);
   }

   private IIOInputStreamAdapter(ImageInputStream var1, long var2, boolean var4) {
      Validate.notNull(var1, "stream");
      Validate.isTrue(!var4 || var2 >= 0L, var2, "length < 0: %d");
      this.input = var1;
      this.left = var2;
      this.hasLength = var4;
   }

   @Override
   public void close() throws IOException {
      if (this.hasLength) {
         this.input.seek(this.input.getStreamPosition() + this.left);
      }

      this.left = 0L;
      this.input = null;
   }

   @Override
   public int available() throws IOException {
      if (this.hasLength) {
         return this.left > 0L ? (int)Math.min(2147483647L, this.left) : 0;
      } else {
         return 0;
      }
   }

   @Override
   public boolean markSupported() {
      return true;
   }

   @Override
   public void mark(int var1) {
      try {
         this.markPosition = this.input.getStreamPosition();
      } catch (IOException var3) {
         throw new IllegalStateException("Could not read stream position: " + var3.getMessage(), var3);
      }
   }

   @Override
   public void reset() throws IOException {
      long var1 = this.input.getStreamPosition() - this.markPosition;
      this.input.seek(this.markPosition);
      this.left += var1;
   }

   @Override
   public int read() throws IOException {
      if (this.hasLength && this.left-- <= 0L) {
         this.left = 0L;
         return -1;
      } else {
         return this.input.read();
      }
   }

   @Override
   public final int read(byte[] var1) throws IOException {
      return this.read(var1, 0, var1.length);
   }

   @Override
   public int read(byte[] var1, int var2, int var3) throws IOException {
      if (this.hasLength && this.left <= 0L) {
         return -1;
      } else {
         int var4 = this.input.read(var1, var2, (int)this.findMaxLen(var3));
         if (this.hasLength) {
            this.left = var4 < 0 ? 0L : this.left - var4;
         }

         return var4;
      }
   }

   private long findMaxLen(long var1) {
      return this.hasLength && this.left < var1 ? Math.max(this.left, 0L) : Math.max(var1, 0L);
   }

   @Override
   public long skip(long var1) throws IOException {
      long var3 = this.input.skipBytes(this.findMaxLen(var1));
      this.left -= var3;
      return var3;
   }
}
