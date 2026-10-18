package com.twelvemonkeys.imageio.metadata.jpeg;

import com.twelvemonkeys.imageio.color.ColorProfiles;
import com.twelvemonkeys.imageio.metadata.Directory;
import com.twelvemonkeys.imageio.metadata.Entry;
import com.twelvemonkeys.imageio.metadata.psd.PSDReader;
import com.twelvemonkeys.imageio.metadata.tiff.TIFFReader;
import com.twelvemonkeys.imageio.metadata.xmp.XMPReader;
import com.twelvemonkeys.imageio.stream.ByteArrayImageInputStream;
import com.twelvemonkeys.lang.Validate;
import java.awt.color.ICC_Profile;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

public final class JPEGSegmentUtil {
   public static final List<String> ALL_IDS = Collections.unmodifiableList(new JPEGSegmentUtil.AllIdsList());
   public static final Map<Integer, List<String>> ALL_SEGMENTS = Collections.unmodifiableMap(new JPEGSegmentUtil.AllSegmentsMap());
   public static final Map<Integer, List<String>> APP_SEGMENTS = Collections.unmodifiableMap(new JPEGSegmentUtil.AllAppSegmentsMap());

   private JPEGSegmentUtil() {
   }

   public static List<JPEGSegment> readSegments(ImageInputStream var0, int var1, String var2) throws IOException {
      return readSegments(var0, Collections.singletonMap(var1, var2 != null ? Collections.singletonList(var2) : ALL_IDS));
   }

   public static List<JPEGSegment> readSegments(ImageInputStream var0, Map<Integer, List<String>> var1) throws IOException {
      readSOI(Validate.notNull(var0, "stream"));
      List<JPEGSegment> var2 = Collections.emptyList();

      JPEGSegment var3;
      try {
         do {
            var3 = readSegment(var0, var1);
            if (isRequested(var3, var1)) {
               if (var2 == Collections.EMPTY_LIST) {
                  var2 = new ArrayList();
               }

               var2.add(var3);
            }
         } while (!isImageDone(var3));
      } catch (EOFException var5) {
      }

      return (List<JPEGSegment>)var2;
   }

   private static boolean isRequested(JPEGSegment var0, Map<Integer, List<String>> var1) {
      return var1.containsKey(var0.marker) && (var0.identifier() == null && var1.get(var0.marker) == null || containsSafe(var0, var1));
   }

   private static boolean containsSafe(JPEGSegment var0, Map<Integer, List<String>> var1) {
      List var2 = (List)var1.get(var0.marker);
      return var2 != null && var2.contains(var0.identifier());
   }

   private static boolean isImageDone(JPEGSegment var0) {
      return var0.marker == 65498 || var0.marker == 65497 || var0.marker == 65496;
   }

   static String asNullTerminatedAsciiString(byte[] var0, int var1) {
      for (int var2 = 0; var2 < var0.length - var1; var2++) {
         if (var0[var1 + var2] < 20 || var2 > 255) {
            return asAsciiString(var0, var1, var1 + var2);
         }
      }

      return null;
   }

   static String asAsciiString(byte[] var0, int var1, int var2) {
      return new String(var0, var1, var2, StandardCharsets.US_ASCII);
   }

   static void readSOI(ImageInputStream var0) throws IOException {
      if (var0.readUnsignedShort() != 65496) {
         throw new IIOException("Not a JPEG stream");
      }
   }

   static JPEGSegment readSegment(ImageInputStream var0, Map<Integer, List<String>> var1) throws IOException {
      int var2 = var0.readUnsignedByte();

      while (!isKnownJPEGMarker(var2)) {
         while (var2 != 255) {
            var2 = var0.readUnsignedByte();
         }

         var2 = 0xFF00 | var0.readUnsignedByte();

         while (var2 == 65535) {
            var2 = 0xFF00 | var0.readUnsignedByte();
         }
      }

      if ((var2 >> 8 & 0xFF) != 255) {
         throw new IIOException(String.format("Bad marker: %04x", var2));
      } else {
         int var3 = var0.readUnsignedShort();
         byte[] var4;
         if (var1.containsKey(var2)) {
            var4 = new byte[Math.max(0, var3 - 2)];
            var0.readFully(var4);
         } else if (JPEGSegment.isAppSegmentMarker(var2)) {
            ByteArrayOutputStream var5 = new ByteArrayOutputStream(32);

            int var6;
            while ((var6 = var0.read()) > 0) {
               var5.write(var6);
            }

            var4 = var5.toByteArray();
            var0.skipBytes(var3 - 3 - var4.length);
         } else {
            var4 = null;
            var0.skipBytes(var3 - 2);
         }

         return new JPEGSegment(var2, var4, var3);
      }
   }

   public static boolean isKnownJPEGMarker(int var0) {
      switch (var0) {
         case 65281:
         case 65472:
         case 65473:
         case 65474:
         case 65475:
         case 65476:
         case 65477:
         case 65478:
         case 65479:
         case 65481:
         case 65482:
         case 65483:
         case 65484:
         case 65485:
         case 65486:
         case 65487:
         case 65496:
         case 65497:
         case 65498:
         case 65499:
         case 65500:
         case 65501:
         case 65502:
         case 65503:
         case 65504:
         case 65505:
         case 65506:
         case 65507:
         case 65508:
         case 65509:
         case 65510:
         case 65511:
         case 65512:
         case 65513:
         case 65514:
         case 65515:
         case 65516:
         case 65517:
         case 65518:
         case 65519:
         case 65527:
         case 65528:
         case 65534:
            return true;
         default:
            return false;
      }
   }

   public static void main(String[] var0) throws IOException {
      for (String var4 : var0) {
         if (var0.length > 1) {
            System.out.println("File: " + var4);
            System.out.println("------");
         }

         for (JPEGSegment var7 : readSegments(ImageIO.createImageInputStream(new File(var4)), ALL_SEGMENTS)) {
            System.err.println("segment: " + var7);
            if ("Exif".equals(var7.identifier())) {
               ByteArrayImageInputStream var8 = new ByteArrayImageInputStream(var7.data, var7.offset() + 1, var7.length() - 1);
               Directory var9 = new TIFFReader().read(var8);
               System.err.println("EXIF: " + var9);
            } else if ("http://ns.adobe.com/xap/1.0/".equals(var7.identifier())) {
               Directory var12 = new XMPReader().read(new ByteArrayImageInputStream(var7.data, var7.offset(), var7.length()));
               System.err.println("XMP: " + var12);
               System.err.println(TIFFReader.HexDump.dump(var7.data));
            } else if ("Photoshop 3.0".equals(var7.identifier())) {
               ByteArrayImageInputStream var13 = new ByteArrayImageInputStream(var7.data, var7.offset(), var7.length());
               Directory var14 = new PSDReader().read(var13);
               Entry var10 = var14.getEntryById(1039);
               if (var10 != null) {
                  ICC_Profile var11 = ColorProfiles.createProfile((byte[])var10.getValue());
                  System.err.println("ICC Profile: " + var11);
               }

               System.err.println("PSD: " + var14);
               System.err.println(TIFFReader.HexDump.dump(var7.data));
            } else if (!"ICC_PROFILE".equals(var7.identifier())) {
               System.err.println(TIFFReader.HexDump.dump(var7.data));
            }
         }

         if (var0.length > 1) {
            System.out.println("------");
            System.out.println();
         }
      }
   }

   private static class AllAppSegmentsMap extends HashMap<Integer, List<String>> {
      private AllAppSegmentsMap() {
      }

      @Override
      public String toString() {
         return "{All APPn segments}";
      }

      public List<String> get(Object var1) {
         return this.containsKey(var1) ? JPEGSegmentUtil.ALL_IDS : null;
      }

      @Override
      public boolean containsKey(Object var1) {
         return var1 instanceof Integer && JPEGSegment.isAppSegmentMarker((Integer)var1);
      }
   }

   private static class AllIdsList extends ArrayList<String> {
      private AllIdsList() {
      }

      @Override
      public String toString() {
         return "[All ids]";
      }

      @Override
      public boolean contains(Object var1) {
         return true;
      }
   }

   private static class AllSegmentsMap extends HashMap<Integer, List<String>> {
      private AllSegmentsMap() {
      }

      @Override
      public String toString() {
         return "{All segments}";
      }

      public List<String> get(Object var1) {
         return var1 instanceof Integer && JPEGSegment.isAppSegmentMarker((Integer)var1) ? JPEGSegmentUtil.ALL_IDS : null;
      }

      @Override
      public boolean containsKey(Object var1) {
         return true;
      }
   }
}
