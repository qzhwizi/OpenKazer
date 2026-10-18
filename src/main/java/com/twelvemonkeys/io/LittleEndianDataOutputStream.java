package com.twelvemonkeys.io;

import com.twelvemonkeys.lang.Validate;
import java.io.DataOutput;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UTFDataFormatException;

public class LittleEndianDataOutputStream extends FilterOutputStream implements DataOutput {
   protected int bytesWritten;

   public LittleEndianDataOutputStream(OutputStream var1) {
      super(Validate.notNull(var1, "stream"));
   }

   @Override
   public synchronized void write(int var1) throws IOException {
      this.out.write(var1);
      this.bytesWritten++;
   }

   @Override
   public synchronized void write(byte[] var1, int var2, int var3) throws IOException {
      this.out.write(var1, var2, var3);
      this.bytesWritten += var3;
   }

   @Override
   public void writeBoolean(boolean var1) throws IOException {
      if (var1) {
         this.write(1);
      } else {
         this.write(0);
      }
   }

   @Override
   public void writeByte(int var1) throws IOException {
      this.out.write(var1);
      this.bytesWritten++;
   }

   @Override
   public void writeShort(int var1) throws IOException {
      this.out.write(var1 & 0xFF);
      this.out.write(var1 >>> 8 & 0xFF);
      this.bytesWritten += 2;
   }

   @Override
   public void writeChar(int var1) throws IOException {
      this.out.write(var1 & 0xFF);
      this.out.write(var1 >>> 8 & 0xFF);
      this.bytesWritten += 2;
   }

   @Override
   public void writeInt(int var1) throws IOException {
      this.out.write(var1 & 0xFF);
      this.out.write(var1 >>> 8 & 0xFF);
      this.out.write(var1 >>> 16 & 0xFF);
      this.out.write(var1 >>> 24 & 0xFF);
      this.bytesWritten += 4;
   }

   @Override
   public void writeLong(long var1) throws IOException {
      this.out.write((int)var1 & 0xFF);
      this.out.write((int)(var1 >>> 8) & 0xFF);
      this.out.write((int)(var1 >>> 16) & 0xFF);
      this.out.write((int)(var1 >>> 24) & 0xFF);
      this.out.write((int)(var1 >>> 32) & 0xFF);
      this.out.write((int)(var1 >>> 40) & 0xFF);
      this.out.write((int)(var1 >>> 48) & 0xFF);
      this.out.write((int)(var1 >>> 56) & 0xFF);
      this.bytesWritten += 8;
   }

   @Override
   public final void writeFloat(float var1) throws IOException {
      this.writeInt(Float.floatToIntBits(var1));
   }

   @Override
   public final void writeDouble(double var1) throws IOException {
      this.writeLong(Double.doubleToLongBits(var1));
   }

   @Override
   public void writeBytes(String var1) throws IOException {
      int var2 = var1.length();

      for (int var3 = 0; var3 < var2; var3++) {
         this.out.write((byte)var1.charAt(var3));
      }

      this.bytesWritten += var2;
   }

   @Override
   public void writeChars(String var1) throws IOException {
      int var2 = var1.length();

      for (int var3 = 0; var3 < var2; var3++) {
         char var4 = var1.charAt(var3);
         this.out.write(var4 & 255);
         this.out.write(var4 >>> '\b' & 0xFF);
      }

      this.bytesWritten += var2 * 2;
   }

   @Override
   public void writeUTF(String var1) throws IOException {
      int var2 = var1.length();
      int var3 = 0;

      for (int var4 = 0; var4 < var2; var4++) {
         char var5 = var1.charAt(var4);
         if (var5 >= 1 && var5 <= 127) {
            var3++;
         } else if (var5 > 2047) {
            var3 += 3;
         } else {
            var3 += 2;
         }
      }

      if (var3 > 65535) {
         throw new UTFDataFormatException();
      } else {
         this.out.write(var3 >>> 8 & 0xFF);
         this.out.write(var3 & 0xFF);

         for (int var6 = 0; var6 < var2; var6++) {
            char var7 = var1.charAt(var6);
            if (var7 >= 1 && var7 <= 127) {
               this.out.write(var7);
            } else if (var7 > 2047) {
               this.out.write(224 | var7 >> '\f' & 15);
               this.out.write(128 | var7 >> 6 & 63);
               this.out.write(128 | var7 & '?');
               this.bytesWritten += 2;
            } else {
               this.out.write(192 | var7 >> 6 & 31);
               this.out.write(128 | var7 & '?');
               this.bytesWritten++;
            }
         }

         this.bytesWritten += var2 + 2;
      }
   }

   public int size() {
      return this.bytesWritten;
   }
}
