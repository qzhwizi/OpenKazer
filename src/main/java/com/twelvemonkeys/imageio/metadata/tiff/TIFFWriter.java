package com.twelvemonkeys.imageio.metadata.tiff;

import com.twelvemonkeys.imageio.metadata.CompoundDirectory;
import com.twelvemonkeys.imageio.metadata.Directory;
import com.twelvemonkeys.imageio.metadata.Entry;
import com.twelvemonkeys.imageio.metadata.MetadataWriter;
import com.twelvemonkeys.lang.Validate;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import javax.imageio.IIOException;
import javax.imageio.stream.ImageOutputStream;

public final class TIFFWriter extends MetadataWriter {
   private static final int WORD_LENGTH = 2;
   private static final int LONGWORD_LENGTH = 4;
   private final boolean longOffsets;
   private final int offsetSize;
   private final long entryLength;
   private final int directoryCountLength;

   public TIFFWriter() {
      this(4);
   }

   public TIFFWriter(int var1) {
      this.offsetSize = Validate.isTrue(var1 == 4 || var1 == 8, var1, "offsetSize must be 4 for TIFF or 8 for BigTIFF");
      this.longOffsets = var1 == 8;
      this.directoryCountLength = this.longOffsets ? 8 : 2;
      this.entryLength = 4 + 2 * var1;
   }

   public boolean write(Collection<? extends Entry> var1, ImageOutputStream var2) throws IOException {
      return this.write(new IFD(var1), var2);
   }

   @Override
   public boolean write(Directory var1, ImageOutputStream var2) throws IOException {
      Validate.notNull(var1);
      Validate.notNull(var2);
      this.writeTIFFHeader(var2);
      if (var1 instanceof CompoundDirectory) {
         CompoundDirectory var3 = (CompoundDirectory)var1;

         for (int var4 = 0; var4 < var3.directoryCount(); var4++) {
            this.writeIFD(var3.getDirectory(var4), var2, false);
         }
      } else {
         this.writeIFD(var1, var2, false);
      }

      this.writeOffset(var2, 0L);
      return true;
   }

   public void writeTIFFHeader(ImageOutputStream var1) throws IOException {
      ByteOrder var2 = var1.getByteOrder();
      var1.writeShort(var2 == ByteOrder.BIG_ENDIAN ? 19789 : 18761);
      var1.writeShort(this.longOffsets ? 43 : 42);
      if (this.longOffsets) {
         var1.writeShort(this.offsetSize);
         var1.writeShort(0);
      }
   }

   public long writeIFD(Collection<Entry> var1, ImageOutputStream var2) throws IOException {
      Validate.notNull(var1);
      Validate.notNull(var2);
      return this.writeIFD(new IFD(var1), var2, false);
   }

   private long writeIFD(Directory var1, ImageOutputStream var2, boolean var3) throws IOException {
      Directory var4 = this.ensureOrderedDirectory(var1);
      long var5 = var2.getStreamPosition();
      long var7 = this.computeDataSize(var4);
      long var9 = var2.getStreamPosition() + var7 + this.offsetSize;
      if (!var3) {
         this.writeOffset(var2, var9);
         var5 += this.offsetSize;
         var2.seek(var9);
      } else {
         var5 += this.directoryCountLength + var4.size() * this.entryLength;
      }

      this.writeDirectoryCount(var2, var4.size());

      for (Entry var12 : var4) {
         var2.writeShort((Integer)var12.getIdentifier());
         var2.writeShort(TIFFEntry.getType(var12));
         this.writeValueCount(var2, this.getCount(var12));
         Object var13 = var12.getValue();
         if (var13 instanceof Directory) {
            if (var13 instanceof CompoundDirectory) {
               throw new AssertionError("SubIFD cannot contain linked IFDs");
            }

            long var14 = var2.getStreamPosition() + this.offsetSize;
            this.writeValueInline(var5, TIFFEntry.getType(var12), var2);
            var2.seek(var5);
            Directory var16 = (Directory)var13;
            this.writeIFD(var16, var2, true);
            var5 += this.computeDataSize(var16) + this.directoryCountLength + var16.size() * this.entryLength;
            var2.seek(var14);
         } else {
            var5 += this.writeValue(var12, var5, var2);
         }
      }

      return var9;
   }

   private void writeDirectoryCount(ImageOutputStream var1, int var2) throws IOException {
      if (this.longOffsets) {
         var1.writeLong(var2);
      } else {
         var1.writeShort(var2);
      }
   }

   private void writeValueCount(ImageOutputStream var1, int var2) throws IOException {
      if (this.longOffsets) {
         var1.writeLong(var2);
      } else {
         var1.writeInt(var2);
      }
   }

   public long computeIFDSize(Collection<? extends Entry> var1) {
      return this.directoryCountLength + this.computeDataSize(new IFD(var1)) + var1.size() * this.entryLength;
   }

   private long computeDataSize(Directory var1) {
      long var2 = 0L;

      for (Entry var5 : var1) {
         long var6 = TIFFEntry.getValueLength(TIFFEntry.getType(var5), this.getCount(var5));
         if (var6 < 0L) {
            throw new IllegalArgumentException(String.format("Unknown size for entry %s", var5));
         }

         if (var6 > this.offsetSize) {
            var2 += var6;
         }

         if (var5.getValue() instanceof Directory) {
            Directory var8 = (Directory)var5.getValue();
            long var9 = this.directoryCountLength + this.computeDataSize(var8) + var8.size() * this.entryLength;
            var2 += var9;
         }
      }

      return var2;
   }

   private Directory ensureOrderedDirectory(Directory var1) {
      if (this.isSorted(var1)) {
         return var1;
      } else {
         ArrayList var2 = new ArrayList(var1.size());

         for (Entry var4 : var1) {
            var2.add(var4);
         }

         Collections.sort(var2, new Comparator<Entry>() {
            public int compare(Entry var1, Entry var2x) {
               return (Integer)var1.getIdentifier() - (Integer)var2x.getIdentifier();
            }
         });
         return new IFD(var2);
      }
   }

   private boolean isSorted(Directory var1) {
      int var2 = 0;

      for (Entry var4 : var1) {
         int var5 = (Integer)var4.getIdentifier() & 65535;
         if (var5 < var2) {
            return false;
         }

         var2 = var5;
      }

      return true;
   }

   private long writeValue(Entry var1, long var2, ImageOutputStream var4) throws IOException {
      short var5 = TIFFEntry.getType(var1);
      long var6 = TIFFEntry.getValueLength(var5, this.getCount(var1));
      if (var6 > this.offsetSize) {
         this.writeValueAt(var2, var1.getValue(), var5, var4);
         return var6;
      } else {
         this.writeValueInline(var1.getValue(), var5, var4);

         for (long var8 = var6; var8 < this.offsetSize; var8++) {
            var4.write(0);
         }

         return 0L;
      }
   }

   private int getCount(Entry var1) {
      Object var2 = var1.getValue();
      if (var2 instanceof String) {
         return this.computeStringLength((String)var2);
      } else {
         return var2 instanceof String[] ? this.computeStringLength((String[])var2) : var1.valueCount();
      }
   }

   private int computeStringLength(String... var1) {
      int var2 = 0;

      for (String var6 : var1) {
         var2 += var6.getBytes(StandardCharsets.UTF_8).length + 1;
      }

      return var2;
   }

   private void writeValueInline(Object var1, short var2, ImageOutputStream var3) throws IOException {
      if (var1.getClass().isArray()) {
         switch (var2) {
            case 1:
            case 6:
            case 7:
               var3.write((byte[])var1);
               break;
            case 3:
            case 8:
               short[] var11;
               if (var1 instanceof short[]) {
                  var11 = (short[])var1;
               } else if (var1 instanceof int[]) {
                  int[] var13 = (int[])var1;
                  var11 = new short[var13.length];

                  for (int var16 = 0; var16 < var13.length; var16++) {
                     var11[var16] = (short)var13[var16];
                  }
               } else {
                  if (!(var1 instanceof long[])) {
                     throw new IllegalArgumentException("Unsupported type for TIFF SHORT: " + var1.getClass());
                  }

                  long[] var12 = (long[])var1;
                  var11 = new short[var12.length];

                  for (int var15 = 0; var15 < var12.length; var15++) {
                     var11[var15] = (short)var12[var15];
                  }
               }

               var3.writeShorts(var11, 0, var11.length);
               break;
            case 4:
            case 9:
               int[] var5;
               if (var1 instanceof int[]) {
                  var5 = (int[])var1;
               } else {
                  if (!(var1 instanceof long[])) {
                     throw new IllegalArgumentException("Unsupported type for TIFF LONG: " + var1.getClass());
                  }

                  long[] var14 = (long[])var1;
                  var5 = new int[var14.length];

                  for (int var18 = 0; var18 < var14.length; var18++) {
                     var5[var18] = (int)var14[var18];
                  }
               }

               var3.writeInts(var5, 0, var5.length);
               break;
            case 5:
            case 10:
               Rational[] var6 = (Rational[])var1;

               for (Rational var10 : var6) {
                  var3.writeInt((int)var10.numerator());
                  var3.writeInt((int)var10.denominator());
               }
               break;
            case 11:
               if (!(var1 instanceof float[])) {
                  throw new IllegalArgumentException("Unsupported type for TIFF FLOAT: " + var1.getClass());
               }

               float[] var7 = (float[])var1;
               var3.writeFloats(var7, 0, var7.length);
               break;
            case 12:
               if (!(var1 instanceof double[])) {
                  throw new IllegalArgumentException("Unsupported type for TIFF DOUBLE: " + var1.getClass());
               }

               double[] var8 = (double[])var1;
               var3.writeDoubles(var8, 0, var8.length);
               break;
            case 13:
            case 14:
            case 15:
            default:
               throw new IllegalArgumentException("Unsupported TIFF type: " + var2);
            case 16:
            case 17:
               if (this.longOffsets) {
                  if (!(var1 instanceof long[])) {
                     throw new IllegalArgumentException("Unsupported type for TIFF LONG8: " + var1.getClass());
                  }

                  long[] var9 = (long[])var1;
                  var3.writeLongs(var9, 0, var9.length);
                  break;
               }
            case 2:
               this.writeStrings(var3, (String[])var1);
         }
      } else {
         switch (var2) {
            case 1:
            case 6:
            case 7:
               var3.writeByte(((Number)var1).intValue());
               break;
            case 2:
               this.writeStrings(var3, (String)var1);
               break;
            case 3:
            case 8:
               var3.writeShort(((Number)var1).intValue());
               break;
            case 4:
            case 9:
            case 13:
               var3.writeInt(((Number)var1).intValue());
               break;
            case 5:
            case 10:
               Rational var4 = (Rational)var1;
               var3.writeInt((int)var4.numerator());
               var3.writeInt((int)var4.denominator());
               break;
            case 11:
               var3.writeFloat(((Number)var1).floatValue());
               break;
            case 12:
               var3.writeDouble(((Number)var1).doubleValue());
               break;
            case 14:
            case 15:
            default:
               throw new IllegalArgumentException("Unsupported TIFF type: " + var2);
            case 16:
            case 17:
            case 18:
               if (!this.longOffsets) {
                  throw new IllegalArgumentException("Unsupported TIFF type: " + var2);
               }

               var3.writeLong(((Number)var1).longValue());
         }
      }
   }

   private void writeStrings(ImageOutputStream var1, String... var2) throws IOException {
      for (String var6 : var2) {
         var1.write(var6.getBytes(StandardCharsets.UTF_8));
         var1.write(0);
      }
   }

   private void writeValueAt(long var1, Object var3, short var4, ImageOutputStream var5) throws IOException {
      this.writeOffset(var5, var1);
      long var6 = var5.getStreamPosition();
      var5.seek(var1);
      this.writeValueInline(var3, var4, var5);
      var5.seek(var6);
   }

   public void writeOffset(ImageOutputStream var1, long var2) throws IOException {
      if (this.longOffsets) {
         var1.writeLong(this.assertLongOffset(var2));
      } else {
         var1.writeInt(this.assertIntegerOffset(var2));
      }
   }

   public int offsetSize() {
      return this.offsetSize;
   }

   private int assertIntegerOffset(long var1) throws IIOException {
      if (var1 >= 0L && var1 <= 4294967295L) {
         return (int)var1;
      } else {
         throw new IIOException("Integer overflow for TIFF stream");
      }
   }

   private long assertLongOffset(long var1) throws IIOException {
      if (var1 < 0L) {
         throw new IIOException("Long overflow for BigTIFF stream");
      } else {
         return var1;
      }
   }
}
