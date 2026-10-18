package com.twelvemonkeys.io;

import com.twelvemonkeys.lang.Validate;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UTFDataFormatException;

public class LittleEndianDataInputStream extends FilterInputStream implements DataInput {
   public LittleEndianDataInputStream(InputStream var1) {
      super(Validate.notNull(var1, "stream"));
   }

   @Override
   public boolean readBoolean() throws IOException {
      int var1 = this.in.read();
      if (var1 < 0) {
         throw new EOFException();
      } else {
         return var1 != 0;
      }
   }

   @Override
   public byte readByte() throws IOException {
      int var1 = this.in.read();
      if (var1 < 0) {
         throw new EOFException();
      } else {
         return (byte)var1;
      }
   }

   @Override
   public int readUnsignedByte() throws IOException {
      int var1 = this.in.read();
      if (var1 < 0) {
         throw new EOFException();
      } else {
         return var1;
      }
   }

   @Override
   public short readShort() throws IOException {
      int var1 = this.in.read();
      int var2 = this.in.read();
      if (var2 < 0) {
         throw new EOFException();
      } else {
         return (short)(var2 << 24 >>> 16 | var1 << 24 >>> 24);
      }
   }

   @Override
   public int readUnsignedShort() throws IOException {
      int var1 = this.in.read();
      int var2 = this.in.read();
      if (var2 < 0) {
         throw new EOFException();
      } else {
         return (var2 << 8) + var1;
      }
   }

   @Override
   public char readChar() throws IOException {
      int var1 = this.in.read();
      int var2 = this.in.read();
      if (var2 < 0) {
         throw new EOFException();
      } else {
         return (char)(var2 << 24 >>> 16 | var1 << 24 >>> 24);
      }
   }

   @Override
   public int readInt() throws IOException {
      int var1 = this.in.read();
      int var2 = this.in.read();
      int var3 = this.in.read();
      int var4 = this.in.read();
      if (var4 < 0) {
         throw new EOFException();
      } else {
         return var4 << 24 | var3 << 24 >>> 8 | var2 << 24 >>> 16 | var1 << 24 >>> 24;
      }
   }

   @Override
   public long readLong() throws IOException {
      long var1 = this.in.read();
      long var3 = this.in.read();
      long var5 = this.in.read();
      long var7 = this.in.read();
      long var9 = this.in.read();
      long var11 = this.in.read();
      long var13 = this.in.read();
      long var15 = this.in.read();
      if (var15 < 0L) {
         throw new EOFException();
      } else {
         return var15 << 56
            | var13 << 56 >>> 8
            | var11 << 56 >>> 16
            | var9 << 56 >>> 24
            | var7 << 56 >>> 32
            | var5 << 56 >>> 40
            | var3 << 56 >>> 48
            | var1 << 56 >>> 56;
      }
   }

   @Override
   public String readUTF() throws IOException {
      int var1 = this.in.read();
      int var2 = this.in.read();
      if (var2 < 0) {
         throw new EOFException();
      } else {
         int var3 = (var1 << 8) + var2;
         char[] var4 = new char[var3];
         int var5 = 0;
         int var6 = 0;

         while (var5 < var3) {
            int var7 = this.readUnsignedByte();
            int var10 = var7 >> 4;
            if (var10 >= 8) {
               if (var10 != 12 && var10 != 13) {
                  if (var10 == 14) {
                     var5 += 3;
                     if (var5 > var3) {
                        throw new UTFDataFormatException();
                     }

                     int var11 = this.readUnsignedByte();
                     int var9 = this.readUnsignedByte();
                     if ((var11 & 192) == 128 && (var9 & 192) == 128) {
                        var4[var6++] = (char)((var7 & 15) << 12 | (var11 & 63) << 6 | var9 & 63);
                        continue;
                     }

                     throw new UTFDataFormatException();
                  }

                  throw new UTFDataFormatException();
               } else {
                  var5 += 2;
                  if (var5 > var3) {
                     throw new UTFDataFormatException();
                  }

                  int var8 = this.readUnsignedByte();
                  if ((var8 & 192) != 128) {
                     throw new UTFDataFormatException();
                  }

                  var4[var6++] = (char)((var7 & 31) << 6 | var8 & 63);
               }
            } else {
               var5++;
               var4[var6++] = (char)var7;
            }
         }

         return new String(var4, 0, var6);
      }
   }

   @Override
   public final double readDouble() throws IOException {
      return Double.longBitsToDouble(this.readLong());
   }

   @Override
   public final float readFloat() throws IOException {
      return Float.intBitsToFloat(this.readInt());
   }

   @Override
   public final int skipBytes(int var1) throws IOException {
      int var2 = 0;

      int var3;
      while (var2 < var1 && (var3 = (int)this.in.skip(var1 - var2)) > 0) {
         var2 += var3;
      }

      return var2;
   }

   @Override
   public final void readFully(byte[] var1) throws IOException {
      this.readFully(var1, 0, var1.length);
   }

   @Override
   public final void readFully(byte[] var1, int var2, int var3) throws IOException {
      if (var3 < 0) {
         throw new IndexOutOfBoundsException();
      } else {
         int var4 = 0;

         while (var4 < var3) {
            int var5 = this.in.read(var1, var2 + var4, var3 - var4);
            if (var5 < 0) {
               throw new EOFException();
            }

            var4 += var5;
         }
      }
   }

   @Deprecated
   @Override
   public String readLine() throws IOException {
      DataInputStream var1 = new DataInputStream(this.in);
      return var1.readLine();
   }
}
