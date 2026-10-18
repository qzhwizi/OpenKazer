package com.twelvemonkeys.imageio.metadata.tiff;

import com.twelvemonkeys.imageio.metadata.Directory;
import com.twelvemonkeys.imageio.metadata.Entry;
import com.twelvemonkeys.imageio.metadata.MetadataReader;
import com.twelvemonkeys.lang.StringUtil;
import com.twelvemonkeys.lang.Validate;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

public final class TIFFReader extends MetadataReader {
   static final boolean DEBUG = "true".equalsIgnoreCase(System.getProperty("com.twelvemonkeys.imageio.metadata.tiff.debug"));
   private static final Collection<Integer> VALID_TOP_LEVEL_IFDS = Collections.unmodifiableCollection(Arrays.asList(330, 34665, 34853));
   private static final Map<Integer, Collection<Integer>> VALID_SUB_IFDS = createSubIFDMap();
   private final Set<Long> parsedIFDs = new TreeSet<>();
   private long inputLength;
   private boolean longOffsets;
   private int offsetSize;

   private static Map<Integer, Collection<Integer>> createSubIFDMap() {
      HashMap var0 = new HashMap<Integer, Collection<Integer>>() {
         public Collection<Integer> get(Object var1) {
            Collection var2 = (Collection)super.get(var1);
            return (Collection<Integer>)(var2 != null ? var2 : Collections.emptySet());
         }
      };
      var0.put(330, Collections.singleton(330));
      var0.put(34665, Collections.singleton(40965));
      return Collections.unmodifiableMap(var0);
   }

   @Override
   public Directory read(ImageInputStream var1) throws IOException {
      Validate.notNull(var1, "input");
      byte[] var2 = new byte[2];
      var1.readFully(var2);
      if (var2[0] == 73 && var2[1] == 73) {
         var1.setByteOrder(ByteOrder.LITTLE_ENDIAN);
      } else {
         if (var2[0] != 77 || var2[1] != 77) {
            throw new IIOException(String.format("Invalid TIFF byte order mark '%s', expected: 'II' or 'MM'", StringUtil.decode(var2, 0, var2.length, "ASCII")));
         }

         var1.setByteOrder(ByteOrder.BIG_ENDIAN);
      }

      int var3 = var1.readUnsignedShort();
      if (var3 == 42) {
         this.longOffsets = false;
         this.offsetSize = 4;
      } else {
         if (var3 != 43) {
            throw new IIOException(String.format("Wrong TIFF magic in input data: %04x, expected: %04x", var3, 42));
         }

         this.longOffsets = true;
         this.offsetSize = 8;
         int var4 = var1.readUnsignedShort();
         if (var4 != 8) {
            throw new IIOException(String.format("Unexpected BigTIFF offset size: %04x, expected: %04x", var4, 8));
         }

         int var5 = var1.readUnsignedShort();
         if (var5 != 0) {
            throw new IIOException(String.format("Unexpected BigTIFF padding: %04x, expected: %04x", var5, 0));
         }
      }

      this.inputLength = var1.length();
      return this.readLinkedIFDs(var1);
   }

   private TIFFDirectory readLinkedIFDs(ImageInputStream var1) throws IOException {
      long var2 = this.readOffset(var1);
      ArrayList var4 = new ArrayList();

      while (var2 != 0L) {
         try {
            if (this.inputLength > 0L && var2 >= this.inputLength || !this.isValidOffset(var1, var2) || !this.parsedIFDs.add(var2)) {
               if (DEBUG) {
                  System.err.println("Bad IFD offset: " + var2);
               }
               break;
            }

            var4.add(this.readIFD(var1, var2, VALID_TOP_LEVEL_IFDS));
            var2 = this.readOffset(var1);
         } catch (EOFException var6) {
            var2 = 0L;
         }
      }

      return new TIFFDirectory(var4);
   }

   private long readOffset(ImageInputStream var1) throws IOException {
      return this.longOffsets ? var1.readLong() : var1.readUnsignedInt();
   }

   private IFD readIFD(ImageInputStream var1, long var2, Collection<Integer> var4) throws IOException {
      var1.seek(var2);
      long var5 = this.readEntryCount(var1);
      ArrayList var7 = new ArrayList();

      for (int var8 = 0; var8 < var5; var8++) {
         try {
            TIFFEntry var9 = this.readEntry(var1);
            if (var9 != null) {
               var7.add(var9);
            }
         } catch (IIOException var10) {
            if (DEBUG) {
               var10.printStackTrace();
            }
            break;
         }
      }

      this.readSubIFDs(var1, var7, var4);
      return new IFD(var7);
   }

   private long readEntryCount(ImageInputStream var1) throws IOException {
      return this.longOffsets ? var1.readLong() : var1.readUnsignedShort();
   }

   private void readSubIFDs(ImageInputStream var1, List<TIFFEntry> var2, Collection<Integer> var3) throws IOException {
      if (var3 != null && !var3.isEmpty()) {
         long var4 = var1.getStreamPosition();
         int var6 = 0;

         for (int var7 = var2.size(); var6 < var7; var6++) {
            TIFFEntry var8 = (TIFFEntry)var2.get(var6);
            int var9 = (Integer)var8.getIdentifier();
            if (var3.contains(var9)) {
               try {
                  long[] var10 = this.getPointerOffsets(var8);
                  ArrayList var11 = new ArrayList(var10.length);

                  for (long var15 : var10) {
                     try {
                        if (this.inputLength > 0L && var15 >= this.inputLength || !this.isValidOffset(var1, var15) || !this.parsedIFDs.add(var15)) {
                           if (DEBUG) {
                              System.err.println("Bad IFD offset: " + var15);
                           }
                           break;
                        }

                        var11.add(this.readIFD(var1, var15, VALID_SUB_IFDS.get(var9)));
                     } catch (EOFException var18) {
                        if (DEBUG) {
                           var18.printStackTrace();
                        }
                     }
                  }

                  if (var11.size() == 1) {
                     var2.set(var6, new TIFFEntry(var9, var8.getType(), var11.get(0)));
                  } else if (!var11.isEmpty()) {
                     var2.set(var6, new TIFFEntry(var9, var8.getType(), (IFD[]) var11.toArray(new IFD[0])));
                  }
               } catch (IIOException var19) {
                  if (DEBUG) {
                     System.err.println("Error parsing sub-IFD: " + var9);
                     var19.printStackTrace();
                  }
               }
            }
         }

         var1.seek(var4);
      }
   }

   private long[] getPointerOffsets(Entry var1) throws IIOException {
      Object var3 = var1.getValue();
      long[] var2;
      if (var3 instanceof Byte) {
         var2 = new long[]{(Byte)var3 & 255};
      } else if (var3 instanceof Short) {
         var2 = new long[]{(Short)var3 & '\uffff'};
      } else if (var3 instanceof Integer) {
         var2 = new long[]{((Integer)var3).intValue() & 4294967295L};
      } else if (var3 instanceof Long) {
         var2 = new long[]{(Long)var3};
      } else {
         if (!(var3 instanceof long[])) {
            throw new IIOException(String.format("Unknown pointer type: %s", var3 != null ? var3.getClass() : null));
         }

         var2 = (long[])var3;
      }

      return var2;
   }

   private TIFFEntry readEntry(ImageInputStream var1) throws IOException {
      int var2 = var1.readUnsignedShort();
      short var3 = var1.readShort();
      int var4 = this.readValueCount(var1);
      if (var4 < 0) {
         throw new IIOException(String.format("Illegal count %d for tag %s type %s @%08x", var4, var2, var3, var1.getStreamPosition()));
      } else if (!this.isValidType(var3)) {
         var1.skipBytes(4);
         if (DEBUG) {
            long var12 = var1.getStreamPosition() - 12L;
            System.err.printf("Bad TIFF data @%08x\n", var1.getStreamPosition());
            System.err.println("tagId: " + var2 + (var2 <= 0 ? " (INVALID)" : ""));
            System.err.println("type: " + var3 + " (INVALID)");
            System.err.println("count: " + var4);
            var1.mark();

            try {
               var1.seek(var12);
               byte[] var13 = new byte[8 + Math.min(120, Math.max(24, var4))];
               int var14 = var1.read(var13);
               System.err.print(TIFFReader.HexDump.dump(var12, var13, 0, var14));
               System.err.println(var14 < var4 ? "[...]" : "");
            } finally {
               var1.reset();
            }
         }

         return null;
      } else {
         long var5 = TIFFEntry.getValueLength(var3, var4);
         Object var7;
         if (var5 > 0L && var5 <= this.offsetSize) {
            var7 = this.readValueInLine(var1, var3, var4);
            var1.skipBytes(this.offsetSize - var5);
         } else {
            long var8 = this.readOffset(var1);
            var7 = this.readValueAt(var1, var8, var5, var3, var4);
         }

         return new TIFFEntry(var2, var3, var7);
      }
   }

   private boolean isValidType(short var1) {
      return var1 > 0 && var1 < TIFF.TYPE_LENGTHS.length && TIFF.TYPE_LENGTHS[var1] > 0;
   }

   private int readValueCount(ImageInputStream var1) throws IOException {
      return this.assertIntCount(this.longOffsets ? var1.readLong() : var1.readUnsignedInt());
   }

   private int assertIntCount(long var1) throws IOException {
      if (var1 > 2147483647L) {
         throw new IIOException(String.format("Unsupported TIFF value count value: %s > Integer.MAX_VALUE", var1));
      } else {
         return (int)var1;
      }
   }

   private boolean isValidOffset(ImageInputStream var1, long var2) throws IOException {
      boolean var5;
      try {
         var1.mark();
         var1.seek(var2);
         return var1.read() >= 0;
      } catch (IOException var9) {
         var5 = false;
      } finally {
         var1.reset();
      }

      return var5;
   }

   private boolean isValidLengthAtOffset(ImageInputStream var1, long var2, long var4) throws IOException {
      return (this.inputLength < 0L || this.inputLength >= var2 + var4) && (var4 < 32767L || this.isValidOffset(var1, var2 + var4 - 1L));
   }

   private Object readValueAt(ImageInputStream var1, long var2, long var4, short var6, int var7) throws IOException {
      long var8 = var1.getStreamPosition();

      EOFException var11;
      try {
         var1.seek(var2);
         if (var7 >= Integer.MAX_VALUE || !this.isValidLengthAtOffset(var1, var2, var4)) {
            throw new EOFException(
               String.format(
                  "TIFF value offset or size too large: @%08x/%d bytes (input length: %s)",
                  var2,
                  var4,
                  this.inputLength >= 0L ? this.inputLength + " bytes" : "unknown"
               )
            );
         }

         return readValue(var1, var6, var7, this.longOffsets);
      } catch (EOFException var15) {
         if (DEBUG) {
            System.err.println(var15);
         }

         var11 = var15;
      } finally {
         var1.seek(var8);
      }

      return var11;
   }

   private Object readValueInLine(ImageInputStream var1, short var2, int var3) throws IOException {
      return readValue(var1, var2, var3, this.longOffsets);
   }

   private static Object readValue(ImageInputStream var0, short var1, int var2, boolean var3) throws IOException {
      long var4 = var0.getStreamPosition();
      switch (var1) {
         case 1:
            if (var2 == 1) {
               return var0.readUnsignedByte();
            }
         case 6:
            if (var2 == 1) {
               return var0.readByte();
            }
         case 7:
            byte[] var9 = new byte[var2];
            var0.readFully(var9);
            return var9;
         case 2:
            if (var2 == 0) {
               return "";
            }

            byte[] var6 = new byte[var2];
            var0.readFully(var6);
            int var7 = var6[var6.length - 1] == 0 ? var6.length - 1 : var6.length;
            String[] var8 = new String(var6, 0, var7, StandardCharsets.UTF_8).split("\u0000");
            return var8.length == 1 ? var8[0] : var8;
         case 3:
            if (var2 == 1) {
               return var0.readUnsignedShort();
            }
         case 8:
            if (var2 == 1) {
               return var0.readShort();
            } else {
               short[] var10 = new short[var2];
               var0.readFully(var10, 0, var10.length);
               if (var1 != 3) {
                  return var10;
               }

               int[] var18 = new int[var2];

               for (int var20 = 0; var20 < var2; var20++) {
                  var18[var20] = var10[var20] & '\uffff';
               }

               return var18;
            }
         case 4:
         case 13:
            if (var2 == 1) {
               return var0.readUnsignedInt();
            }
         case 9:
            if (var2 == 1) {
               return var0.readInt();
            } else {
               int[] var11 = new int[var2];
               var0.readFully(var11, 0, var11.length);
               if (var1 != 4 && var1 != 13) {
                  return var11;
               }

               long[] var19 = new long[var2];

               for (int var21 = 0; var21 < var2; var21++) {
                  var19[var21] = var11[var21] & 4294967295L;
               }

               return var19;
            }
         case 5:
            if (var2 == 1) {
               return createSafeRational(var0.readUnsignedInt(), var0.readUnsignedInt());
            }

            Rational[] var14 = new Rational[var2];

            for (int var22 = 0; var22 < var14.length; var22++) {
               var14[var22] = createSafeRational(var0.readUnsignedInt(), var0.readUnsignedInt());
            }

            return var14;
         case 10:
            if (var2 == 1) {
               return createSafeRational(var0.readInt(), var0.readInt());
            }

            Rational[] var15 = new Rational[var2];

            for (int var24 = 0; var24 < var15.length; var24++) {
               var15[var24] = createSafeRational(var0.readInt(), var0.readInt());
            }

            return var15;
         case 11:
            if (var2 == 1) {
               return var0.readFloat();
            }

            float[] var12 = new float[var2];
            var0.readFully(var12, 0, var12.length);
            return var12;
         case 12:
            if (var2 == 1) {
               return var0.readDouble();
            }

            double[] var13 = new double[var2];
            var0.readFully(var13, 0, var13.length);
            return var13;
         case 16:
         case 17:
         case 18:
            if (var3) {
               if (var2 == 1) {
                  long var23 = var0.readLong();
                  if (var1 != 17 && var23 < 0L) {
                     throw new IIOException(String.format("Value > %s", Long.MAX_VALUE));
                  }

                  return var23;
               }

               long[] var16 = new long[var2];

               for (int var17 = 0; var17 < var2; var17++) {
                  var16[var17] = var0.readLong();
               }

               return var16;
            }
         case 14:
         case 15:
         default:
            return new Unknown(var1, var2, var4);
      }
   }

   private static Rational createSafeRational(long var0, long var2) {
      return var2 == 0L ? Rational.NaN : new Rational(var0, var2);
   }

   public static void main(String[] var0) throws IOException {
      TIFFReader var1 = new TIFFReader();

      try (ImageInputStream var2 = ImageIO.createImageInputStream(new File(var0[0]))) {
         long var4 = 0L;
         if (var0.length > 1) {
            if (var0[1].startsWith("0x")) {
               var4 = Integer.parseInt(var0[1].substring(2), 16);
            } else {
               var4 = Long.parseLong(var0[1]);
            }

            var2.setByteOrder(var4 < 0L ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);
            var4 = Math.abs(var4);
            var2.seek(var4);
         }

         for (Entry var8 : var0.length > 1 ? var1.readIFD(var2, var4, VALID_TOP_LEVEL_IFDS) : var1.read(var2)) {
            System.err.println(var8);
            Object var9 = var8.getValue();
            if (var9 instanceof byte[]) {
               byte[] var10 = (byte[])var9;
               System.err.println(TIFFReader.HexDump.dump(0L, var10, 0, Math.min(var10.length, 128)));
            }
         }
      }
   }

   public static class HexDump {
      private static final int WIDTH = 32;

      private HexDump() {
      }

      public static String dump(byte[] var0) {
         return dump(0L, var0, 0, var0.length);
      }

      public static String dump(long var0, byte[] var2, int var3, int var4) {
         StringBuilder var5 = new StringBuilder();

         for (int var6 = 0; var6 < var4; var6++) {
            if (var6 % 32 == 0) {
               if (var6 > 0) {
                  var5.append("\n");
               }

               var5.append(String.format("%08x: ", var6 + var3 + var0));
            } else if (var6 > 0 && var6 % 2 == 0) {
               var5.append(" ");
            }

            var5.append(String.format("%02x", var2[var6 + var3]));
            int var7 = var6 + 1;
            if (var7 % 32 == 0 || var7 == var4) {
               int var8 = (32 - var7 % 32) % 32;
               if (var8 != 0) {
                  int var9 = var8 / 2;
                  if (var4 % 2 != 0) {
                     var5.append("  ");
                  }

                  for (int var10 = 0; var10 < var9; var10++) {
                     var5.append("     ");
                  }
               }

               var5.append("  ");
               var5.append(toAsciiString(var2, var7 - (32 - var8) + var3, var7 + var3));
            }
         }

         return var5.toString();
      }

      private static String toAsciiString(byte[] var0, int var1, int var2) {
         byte[] var3 = Arrays.copyOfRange(var0, var1, var2);

         for (int var4 = 0; var4 < var3.length; var4++) {
            if (var3[var4] < 32 || var3[var4] > 126) {
               var3[var4] = 46;
            }
         }

         return new String(var3, StandardCharsets.US_ASCII);
      }
   }
}
