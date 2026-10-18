package com.twelvemonkeys.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.EOFException;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UTFDataFormatException;
import java.nio.channels.FileChannel;

public class LittleEndianRandomAccessFile implements DataInput, DataOutput {
   private RandomAccessFile file;

   public LittleEndianRandomAccessFile(String var1, String var2) throws FileNotFoundException {
      this(FileUtil.resolve(var1), var2);
   }

   public LittleEndianRandomAccessFile(File var1, String var2) throws FileNotFoundException {
      this.file = new RandomAccessFile(var1, var2);
   }

   public void close() throws IOException {
      this.file.close();
   }

   public FileChannel getChannel() {
      return this.file.getChannel();
   }

   public FileDescriptor getFD() throws IOException {
      return this.file.getFD();
   }

   public long getFilePointer() throws IOException {
      return this.file.getFilePointer();
   }

   public long length() throws IOException {
      return this.file.length();
   }

   public int read() throws IOException {
      return this.file.read();
   }

   public int read(byte[] var1) throws IOException {
      return this.file.read(var1);
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      return this.file.read(var1, var2, var3);
   }

   @Override
   public void readFully(byte[] var1) throws IOException {
      this.file.readFully(var1);
   }

   @Override
   public void readFully(byte[] var1, int var2, int var3) throws IOException {
      this.file.readFully(var1, var2, var3);
   }

   @Override
   public String readLine() throws IOException {
      return this.file.readLine();
   }

   @Override
   public boolean readBoolean() throws IOException {
      int var1 = this.file.read();
      if (var1 < 0) {
         throw new EOFException();
      } else {
         return var1 != 0;
      }
   }

   @Override
   public byte readByte() throws IOException {
      int var1 = this.file.read();
      if (var1 < 0) {
         throw new EOFException();
      } else {
         return (byte)var1;
      }
   }

   @Override
   public int readUnsignedByte() throws IOException {
      int var1 = this.file.read();
      if (var1 < 0) {
         throw new EOFException();
      } else {
         return var1;
      }
   }

   @Override
   public short readShort() throws IOException {
      int var1 = this.file.read();
      int var2 = this.file.read();
      if (var2 < 0) {
         throw new EOFException();
      } else {
         return (short)((var2 << 24 >>> 16) + (var1 << 24) >>> 24);
      }
   }

   @Override
   public int readUnsignedShort() throws IOException {
      int var1 = this.file.read();
      int var2 = this.file.read();
      if (var2 < 0) {
         throw new EOFException();
      } else {
         return (var2 << 8) + var1;
      }
   }

   @Override
   public char readChar() throws IOException {
      int var1 = this.file.read();
      int var2 = this.file.read();
      if (var2 < 0) {
         throw new EOFException();
      } else {
         return (char)((var2 << 24 >>> 16) + (var1 << 24 >>> 24));
      }
   }

   @Override
   public int readInt() throws IOException {
      int var1 = this.file.read();
      int var2 = this.file.read();
      int var3 = this.file.read();
      int var4 = this.file.read();
      if (var4 < 0) {
         throw new EOFException();
      } else {
         return (var4 << 24) + (var3 << 24 >>> 8) + (var2 << 24 >>> 16) + (var1 << 24 >>> 24);
      }
   }

   @Override
   public long readLong() throws IOException {
      long var1 = this.file.read();
      long var3 = this.file.read();
      long var5 = this.file.read();
      long var7 = this.file.read();
      long var9 = this.file.read();
      long var11 = this.file.read();
      long var13 = this.file.read();
      long var15 = this.file.read();
      if (var15 < 0L) {
         throw new EOFException();
      } else {
         return (var15 << 56)
            + (var13 << 56 >>> 8)
            + (var11 << 56 >>> 16)
            + (var9 << 56 >>> 24)
            + (var7 << 56 >>> 32)
            + (var5 << 56 >>> 40)
            + (var3 << 56 >>> 48)
            + (var1 << 56 >>> 56);
      }
   }

   @Override
   public String readUTF() throws IOException {
      int var1 = this.file.read();
      int var2 = this.file.read();
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

   public void seek(long var1) throws IOException {
      this.file.seek(var1);
   }

   public void setLength(long var1) throws IOException {
      this.file.setLength(var1);
   }

   @Override
   public int skipBytes(int var1) throws IOException {
      return this.file.skipBytes(var1);
   }

   @Override
   public void write(byte[] var1) throws IOException {
      this.file.write(var1);
   }

   @Override
   public void write(byte[] var1, int var2, int var3) throws IOException {
      this.file.write(var1, var2, var3);
   }

   @Override
   public void write(int var1) throws IOException {
      this.file.write(var1);
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
      this.file.write(var1);
   }

   @Override
   public void writeShort(int var1) throws IOException {
      this.file.write(var1 & 0xFF);
      this.file.write(var1 >>> 8 & 0xFF);
   }

   @Override
   public void writeChar(int var1) throws IOException {
      this.file.write(var1 & 0xFF);
      this.file.write(var1 >>> 8 & 0xFF);
   }

   @Override
   public void writeInt(int var1) throws IOException {
      this.file.write(var1 & 0xFF);
      this.file.write(var1 >>> 8 & 0xFF);
      this.file.write(var1 >>> 16 & 0xFF);
      this.file.write(var1 >>> 24 & 0xFF);
   }

   @Override
   public void writeLong(long var1) throws IOException {
      this.file.write((int)var1 & 0xFF);
      this.file.write((int)(var1 >>> 8) & 0xFF);
      this.file.write((int)(var1 >>> 16) & 0xFF);
      this.file.write((int)(var1 >>> 24) & 0xFF);
      this.file.write((int)(var1 >>> 32) & 0xFF);
      this.file.write((int)(var1 >>> 40) & 0xFF);
      this.file.write((int)(var1 >>> 48) & 0xFF);
      this.file.write((int)(var1 >>> 56) & 0xFF);
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
         this.file.write((byte)var1.charAt(var3));
      }
   }

   @Override
   public void writeChars(String var1) throws IOException {
      int var2 = var1.length();

      for (int var3 = 0; var3 < var2; var3++) {
         char var4 = var1.charAt(var3);
         this.file.write(var4 & 255);
         this.file.write(var4 >>> '\b' & 0xFF);
      }
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
         this.file.write(var3 >>> 8 & 0xFF);
         this.file.write(var3 & 0xFF);

         for (int var6 = 0; var6 < var2; var6++) {
            char var7 = var1.charAt(var6);
            if (var7 >= 1 && var7 <= 127) {
               this.file.write(var7);
            } else if (var7 > 2047) {
               this.file.write(224 | var7 >> '\f' & 15);
               this.file.write(128 | var7 >> 6 & 63);
               this.file.write(128 | var7 & '?');
            } else {
               this.file.write(192 | var7 >> 6 & 31);
               this.file.write(128 | var7 & '?');
            }
         }
      }
   }
}
