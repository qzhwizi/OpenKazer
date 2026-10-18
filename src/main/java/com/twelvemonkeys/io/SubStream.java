package com.twelvemonkeys.io;

import com.twelvemonkeys.lang.Validate;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class SubStream extends FilterInputStream {
   private long bytesLeft;
   private int markLimit;

   public SubStream(InputStream var1, long var2) {
      super(Validate.notNull(var1, "stream"));
      this.bytesLeft = Validate.isTrue(var2 >= 0L, var2, "length < 0: %s");
   }

   @Override
   public void close() throws IOException {
      while (this.bytesLeft > 0L && (this.skip(this.bytesLeft) > 0L || this.read() >= 0)) {
      }
   }

   @Override
   public int available() throws IOException {
      return (int)this.findMaxLen(super.available());
   }

   @Override
   public void mark(int var1) {
      super.mark(var1);
      this.markLimit = var1;
   }

   @Override
   public void reset() throws IOException {
      super.reset();
      this.bytesLeft = this.bytesLeft + this.markLimit;
   }

   @Override
   public int read() throws IOException {
      return this.bytesLeft-- <= 0L ? -1 : super.read();
   }

   @Override
   public int read(byte[] var1) throws IOException {
      return this.read(var1, 0, var1.length);
   }

   @Override
   public int read(byte[] var1, int var2, int var3) throws IOException {
      if (this.bytesLeft <= 0L) {
         return -1;
      } else {
         int var4 = super.read(var1, var2, (int)this.findMaxLen(var3));
         this.bytesLeft = var4 < 0 ? 0L : this.bytesLeft - var4;
         return var4;
      }
   }

   @Override
   public long skip(long var1) throws IOException {
      long var3 = super.skip(this.findMaxLen(var1));
      this.bytesLeft -= var3;
      return var3;
   }

   private long findMaxLen(long var1) {
      return this.bytesLeft < var1 ? Math.max(this.bytesLeft, 0L) : var1;
   }
}
