package com.twelvemonkeys.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public final class FastByteArrayOutputStream extends ByteArrayOutputStream {
   public FastByteArrayOutputStream(int var1) {
      super(var1);
   }

   public FastByteArrayOutputStream(byte[] var1) {
      super(0);
      this.buf = var1;
      this.count = var1.length;
   }

   @Override
   public void write(byte[] var1, int var2, int var3) {
      if (var2 < 0 || var2 > var1.length || var3 < 0 || var2 + var3 > var1.length || var2 + var3 < 0) {
         throw new IndexOutOfBoundsException();
      } else if (var3 != 0) {
         int var4 = this.count + var3;
         this.growIfNeeded(var4);
         System.arraycopy(var1, var2, this.buf, this.count, var3);
         this.count = var4;
      }
   }

   @Override
   public void write(int var1) {
      int var2 = this.count + 1;
      this.growIfNeeded(var2);
      this.buf[this.count] = (byte)var1;
      this.count = var2;
   }

   private void growIfNeeded(int var1) {
      if (var1 > this.buf.length) {
         int var2 = Math.max(this.buf.length << 1, var1);
         this.buf = Arrays.copyOf(this.buf, var2);
      }
   }

   @Override
   public void writeTo(OutputStream var1) throws IOException {
      var1.write(this.buf, 0, this.count);
   }

   @Override
   public byte[] toByteArray() {
      return Arrays.copyOf(this.buf, this.count);
   }

   public ByteArrayInputStream createInputStream() {
      return new ByteArrayInputStream(this.buf, 0, this.count);
   }
}
