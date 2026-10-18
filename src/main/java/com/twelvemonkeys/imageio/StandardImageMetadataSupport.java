package com.twelvemonkeys.imageio;

import com.twelvemonkeys.lang.Validate;
import java.awt.color.ColorSpace;
import java.awt.image.BandedSampleModel;
import java.awt.image.ColorModel;
import java.awt.image.ComponentSampleModel;
import java.awt.image.IndexColorModel;
import java.awt.image.SampleModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;

public class StandardImageMetadataSupport extends AbstractMetadata {
   private final ImageTypeSpecifier type;
   protected final StandardImageMetadataSupport.ColorSpaceType colorSpaceType;
   protected final boolean blackIsZero;
   private final IndexColorModel palette;
   protected final String compressionName;
   protected final boolean compressionLossless;
   protected final StandardImageMetadataSupport.PlanarConfiguration planarConfiguration;
   private final int[] bitsPerSample;
   private final int[] significantBits;
   private final int[] sampleMSB;
   protected final Double pixelAspectRatio;
   protected final StandardImageMetadataSupport.ImageOrientation orientation;
   protected final String formatVersion;
   protected final StandardImageMetadataSupport.SubimageInterpretation subimageInterpretation;
   private final Calendar documentCreationTime;
   private final Collection<StandardImageMetadataSupport.TextEntry> textEntries;

   protected StandardImageMetadataSupport(StandardImageMetadataSupport.Builder var1) {
      Validate.notNull(var1, "builder");
      this.type = var1.type;
      this.colorSpaceType = var1.colorSpaceType;
      this.blackIsZero = var1.blackIsZero;
      this.palette = var1.palette;
      this.compressionName = var1.compressionName;
      this.compressionLossless = var1.compressionLossless;
      this.planarConfiguration = var1.planarConfiguration;
      this.bitsPerSample = var1.bitsPerSample;
      this.significantBits = var1.significantBits;
      this.sampleMSB = var1.sampleMSB;
      this.orientation = var1.orientation;
      this.pixelAspectRatio = var1.pixelAspectRatio;
      this.formatVersion = var1.formatVersion;
      this.documentCreationTime = var1.documentCreationTime;
      this.subimageInterpretation = var1.subimageInterpretation;
      this.textEntries = var1.textEntries;
   }

   protected static StandardImageMetadataSupport.Builder builder(ImageTypeSpecifier var0) {
      return new StandardImageMetadataSupport.Builder(var0);
   }

   @Override
   protected IIOMetadataNode getStandardChromaNode() {
      IIOMetadataNode var1 = new IIOMetadataNode("Chroma");
      ColorModel var2 = this.colorSpaceType != null ? null : this.type.getColorModel();
      StandardImageMetadataSupport.ColorSpaceType var3 = this.colorSpaceType != null ? this.colorSpaceType : colorSpaceType(var2.getColorSpace());
      int var4 = this.colorSpaceType != null ? this.colorSpaceType.numChannels : var2.getNumComponents();
      IIOMetadataNode var5 = new IIOMetadataNode("ColorSpaceType");
      var1.appendChild(var5);
      var5.setAttribute("name", var3.toString());
      IIOMetadataNode var6 = new IIOMetadataNode("NumChannels");
      var6.setAttribute("value", String.valueOf(var4));
      var1.appendChild(var6);
      IIOMetadataNode var7 = new IIOMetadataNode("BlackIsZero");
      var7.setAttribute("value", booleanString(this.blackIsZero));
      var1.appendChild(var7);
      if (var2 instanceof IndexColorModel || this.palette != null) {
         IndexColorModel var8 = this.palette != null ? this.palette : (IndexColorModel)var2;
         IIOMetadataNode var9 = new IIOMetadataNode("Palette");
         var1.appendChild(var9);

         for (int var10 = 0; var10 < var8.getMapSize(); var10++) {
            IIOMetadataNode var11 = new IIOMetadataNode("PaletteEntry");
            var9.appendChild(var11);
            var11.setAttribute("index", Integer.toString(var10));
            var11.setAttribute("red", Integer.toString(var8.getRed(var10)));
            var11.setAttribute("green", Integer.toString(var8.getGreen(var10)));
            var11.setAttribute("blue", Integer.toString(var8.getBlue(var10)));
            if (var8.getTransparency() == 3) {
               var11.setAttribute("alpha", Integer.toString(var8.getAlpha(var10)));
            }
         }

         if (var8.getTransparentPixel() != -1) {
            IIOMetadataNode var12 = new IIOMetadataNode("BackgroundIndex");
            var1.appendChild(var12);
            var12.setAttribute("value", Integer.toString(var8.getTransparentPixel()));
         }
      }

      return var1;
   }

   private static StandardImageMetadataSupport.ColorSpaceType colorSpaceType(ColorSpace var0) {
      switch (var0.getType()) {
         case 0:
            return StandardImageMetadataSupport.ColorSpaceType.XYZ;
         case 1:
            return StandardImageMetadataSupport.ColorSpaceType.Lab;
         case 2:
            return StandardImageMetadataSupport.ColorSpaceType.Luv;
         case 3:
            return StandardImageMetadataSupport.ColorSpaceType.YCbCr;
         case 4:
            return StandardImageMetadataSupport.ColorSpaceType.Yxy;
         case 5:
            return StandardImageMetadataSupport.ColorSpaceType.RGB;
         case 6:
            return StandardImageMetadataSupport.ColorSpaceType.GRAY;
         case 7:
            return StandardImageMetadataSupport.ColorSpaceType.HSV;
         case 8:
            return StandardImageMetadataSupport.ColorSpaceType.HLS;
         case 9:
            return StandardImageMetadataSupport.ColorSpaceType.CMYK;
         case 10:
         default:
            int var1 = var0.getNumComponents();
            if (var1 == 1) {
               return StandardImageMetadataSupport.ColorSpaceType.GRAY;
            } else {
               if (var1 < 16) {
                  return StandardImageMetadataSupport.ColorSpaceType.valueOf("GENERIC_" + Integer.toHexString(var1) + "CLR");
               }

               throw new IllegalArgumentException("Unknown ColorSpace type: " + var0);
            }
         case 11:
            return StandardImageMetadataSupport.ColorSpaceType.CMY;
      }
   }

   @Override
   protected IIOMetadataNode getStandardCompressionNode() {
      if (this.compressionName == null) {
         return null;
      } else {
         IIOMetadataNode var1 = new IIOMetadataNode("Compression");
         IIOMetadataNode var2 = new IIOMetadataNode("CompressionTypeName");
         var2.setAttribute("value", this.compressionName);
         var1.appendChild(var2);
         IIOMetadataNode var3 = new IIOMetadataNode("Lossless");
         var3.setAttribute("value", booleanString(this.compressionLossless));
         var1.appendChild(var3);
         return var1;
      }
   }

   protected static String booleanString(boolean var0) {
      return var0 ? "TRUE" : "FALSE";
   }

   @Override
   protected IIOMetadataNode getStandardDataNode() {
      IIOMetadataNode var1 = new IIOMetadataNode("Data");
      IIOMetadataNode var2 = new IIOMetadataNode("PlanarConfiguration");
      var1.appendChild(var2);
      var2.setAttribute(
         "value",
         this.planarConfiguration != null
            ? this.planarConfiguration.toString()
            : (this.type.getSampleModel() instanceof BandedSampleModel ? "PlaneInterleaved" : "PixelInterleaved")
      );
      String var3 = this.colorSpaceType == null && this.type.getColorModel() instanceof IndexColorModel ? "Index" : sampleFormat(this.type.getSampleModel());
      if (var3 != null) {
         IIOMetadataNode var4 = new IIOMetadataNode("SampleFormat");
         var4.setAttribute("value", var3);
         var1.appendChild(var4);
      }

      int[] var8 = this.bitsPerSample != null ? this.bitsPerSample : this.type.getSampleModel().getSampleSize();
      IIOMetadataNode var5 = new IIOMetadataNode("BitsPerSample");
      var5.setAttribute("value", createListValue(var8.length, var8));
      var1.appendChild(var5);
      if (this.significantBits != null) {
         String var6 = createListValue(this.type.getNumBands(), this.significantBits);
         if (!var6.equals(var5.getAttribute("value"))) {
            IIOMetadataNode var7 = new IIOMetadataNode("SignificantBitsPerSample");
            var7.setAttribute("value", var6);
            var1.appendChild(var7);
         }
      }

      if (this.sampleMSB != null) {
         IIOMetadataNode var9 = new IIOMetadataNode("SampleMSB");
         var9.setAttribute("value", createListValue(this.type.getNumBands(), this.sampleMSB));
         var1.appendChild(var9);
      }

      return var1;
   }

   private static String createListValue(int var0, int... var1) {
      StringBuilder var2 = new StringBuilder();

      for (int var3 = 0; var3 < var0; var3++) {
         if (var2.length() > 0) {
            var2.append(' ');
         }

         var2.append(var1[var3 % var1.length]);
      }

      return var2.toString();
   }

   private static String sampleFormat(SampleModel var0) {
      switch (var0.getDataType()) {
         case 2:
         case 3:
            if (var0 instanceof ComponentSampleModel) {
               return "SignedIntegral";
            }
         case 0:
         case 1:
            return "UnsignedIntegral";
         case 4:
         case 5:
            return "Real";
         default:
            return null;
      }
   }

   @Override
   protected IIOMetadataNode getStandardDimensionNode() {
      IIOMetadataNode var1 = new IIOMetadataNode("Dimension");
      if (this.pixelAspectRatio != null) {
         IIOMetadataNode var2 = new IIOMetadataNode("PixelAspectRatio");
         var2.setAttribute("value", String.valueOf(this.pixelAspectRatio));
         var1.appendChild(var2);
      }

      IIOMetadataNode var3 = new IIOMetadataNode("ImageOrientation");
      var3.setAttribute("value", this.orientation.toString());
      var1.appendChild(var3);
      return var1.hasChildNodes() ? var1 : null;
   }

   @Override
   protected IIOMetadataNode getStandardDocumentNode() {
      IIOMetadataNode var1 = new IIOMetadataNode("Document");
      if (this.formatVersion != null) {
         IIOMetadataNode var2 = new IIOMetadataNode("FormatVersion");
         var1.appendChild(var2);
         var2.setAttribute("value", this.formatVersion);
      }

      if (this.subimageInterpretation != null) {
         IIOMetadataNode var3 = new IIOMetadataNode("SubimageInterpretation");
         var1.appendChild(var3);
         var3.setAttribute("value", this.subimageInterpretation.toString());
      }

      if (this.documentCreationTime != null) {
         IIOMetadataNode var4 = new IIOMetadataNode("ImageCreationTime");
         var1.appendChild(var4);
         var4.setAttribute("year", String.valueOf(this.documentCreationTime.get(1)));
         var4.setAttribute("month", String.valueOf(this.documentCreationTime.get(2) + 1));
         var4.setAttribute("day", String.valueOf(this.documentCreationTime.get(5)));
         var4.setAttribute("hour", String.valueOf(this.documentCreationTime.get(11)));
         var4.setAttribute("minute", String.valueOf(this.documentCreationTime.get(12)));
         var4.setAttribute("second", String.valueOf(this.documentCreationTime.get(13)));
      }

      return var1.hasChildNodes() ? var1 : null;
   }

   @Override
   protected IIOMetadataNode getStandardTextNode() {
      if (this.textEntries.isEmpty()) {
         return null;
      } else {
         IIOMetadataNode var1 = new IIOMetadataNode("Text");

         for (StandardImageMetadataSupport.TextEntry var3 : this.textEntries) {
            IIOMetadataNode var4 = new IIOMetadataNode("TextEntry");
            var1.appendChild(var4);
            if (var3.keyword != null) {
               var4.setAttribute("keyword", var3.keyword);
            }

            var4.setAttribute("value", var3.value);
            if (var3.language != null) {
               var4.setAttribute("language", var3.language);
            }

            if (var3.encoding != null) {
               var4.setAttribute("encoding", var3.encoding);
            }

            if (var3.compression != null) {
               var4.setAttribute("compression", var3.compression);
            }
         }

         return var1;
      }
   }

   @Override
   protected IIOMetadataNode getStandardTransparencyNode() {
      IIOMetadataNode var1 = new IIOMetadataNode("Transparency");
      ColorModel var2 = this.type.getColorModel();
      IIOMetadataNode var3 = new IIOMetadataNode("Alpha");
      var1.appendChild(var3);
      var3.setAttribute("value", var2.hasAlpha() ? (var2.isAlphaPremultiplied() ? "premultiplied" : "nonpremultiplied") : "none");
      if (var2 instanceof IndexColorModel) {
         IndexColorModel var4 = (IndexColorModel)var2;
         if (var4.getTransparentPixel() != -1) {
            IIOMetadataNode var5 = new IIOMetadataNode("TransparentIndex");
            var1.appendChild(var5);
            var5.setAttribute("value", Integer.toString(var4.getTransparentPixel()));
         }
      }

      return var1;
   }

   protected static class Builder {
      private final ImageTypeSpecifier type;
      private StandardImageMetadataSupport.ColorSpaceType colorSpaceType;
      private boolean blackIsZero = true;
      private IndexColorModel palette;
      private String compressionName;
      private boolean compressionLossless = true;
      private StandardImageMetadataSupport.PlanarConfiguration planarConfiguration;
      public int[] bitsPerSample;
      private int[] significantBits;
      private int[] sampleMSB;
      private Double pixelAspectRatio;
      private StandardImageMetadataSupport.ImageOrientation orientation = StandardImageMetadataSupport.ImageOrientation.Normal;
      private String formatVersion;
      private StandardImageMetadataSupport.SubimageInterpretation subimageInterpretation;
      private Calendar documentCreationTime;
      private final Collection<StandardImageMetadataSupport.TextEntry> textEntries = new ArrayList<>();

      protected Builder(ImageTypeSpecifier var1) {
         this.type = Validate.notNull(var1, "type");
      }

      public StandardImageMetadataSupport.Builder withColorSpaceType(StandardImageMetadataSupport.ColorSpaceType var1) {
         this.colorSpaceType = var1;
         return this;
      }

      public StandardImageMetadataSupport.Builder withBlackIsZero(boolean var1) {
         this.blackIsZero = var1;
         return this;
      }

      public StandardImageMetadataSupport.Builder withPalette(IndexColorModel var1) {
         this.palette = var1;
         return this;
      }

      public StandardImageMetadataSupport.Builder withCompressionTypeName(String var1) {
         this.compressionName = Validate.notNull(var1, "compressionName").equalsIgnoreCase("none") ? null : var1;
         return this;
      }

      public StandardImageMetadataSupport.Builder withCompressionLossless(boolean var1) {
         this.compressionLossless = Validate.isTrue(var1 || this.compressionName != null, var1, "Lossy compression requires compression name");
         return this;
      }

      public StandardImageMetadataSupport.Builder withPlanarConfiguration(StandardImageMetadataSupport.PlanarConfiguration var1) {
         this.planarConfiguration = var1;
         return this;
      }

      public StandardImageMetadataSupport.Builder withBitsPerSample(int... var1) {
         this.bitsPerSample = var1;
         return this;
      }

      public StandardImageMetadataSupport.Builder withSignificantBitsPerSample(int... var1) {
         this.significantBits = Validate.isTrue(
            var1.length == 1 || var1.length == this.type.getNumBands(), var1, String.format("single value or %d values expected", this.type.getNumBands())
         );
         return this;
      }

      public StandardImageMetadataSupport.Builder withSampleMSB(int... var1) {
         this.sampleMSB = Validate.isTrue(
            var1.length == 1 || var1.length == this.type.getNumBands(), var1, String.format("single value or %d values expected", this.type.getNumBands())
         );
         return this;
      }

      public StandardImageMetadataSupport.Builder withPixelAspectRatio(Double var1) {
         this.pixelAspectRatio = var1;
         return this;
      }

      public StandardImageMetadataSupport.Builder withOrientation(StandardImageMetadataSupport.ImageOrientation var1) {
         this.orientation = Validate.notNull(var1, "orientation");
         return this;
      }

      public StandardImageMetadataSupport.Builder withFormatVersion(String var1) {
         this.formatVersion = Validate.notNull(var1, "formatVersion");
         return this;
      }

      public StandardImageMetadataSupport.Builder withSubimageInterpretation(StandardImageMetadataSupport.SubimageInterpretation var1) {
         this.subimageInterpretation = var1;
         return this;
      }

      public StandardImageMetadataSupport.Builder withDocumentCreationTime(Calendar var1) {
         this.documentCreationTime = var1;
         return this;
      }

      public StandardImageMetadataSupport.Builder withTextEntries(Map<String, String> var1) {
         return this.withTextEntries(this.toTextEntries(Validate.notNull(var1, "entries").entrySet()));
      }

      private Collection<StandardImageMetadataSupport.TextEntry> toTextEntries(Collection<Entry<String, String>> var1) {
         StandardImageMetadataSupport.TextEntry[] var2 = new StandardImageMetadataSupport.TextEntry[var1.size()];
         int var3 = 0;

         for (Entry var5 : var1) {
            var2[var3++] = new StandardImageMetadataSupport.TextEntry((String)var5.getKey(), (String)var5.getValue());
         }

         return Arrays.asList(var2);
      }

      public StandardImageMetadataSupport.Builder withTextEntries(Collection<StandardImageMetadataSupport.TextEntry> var1) {
         this.textEntries.addAll(Validate.notNull(var1, "entries"));
         return this;
      }

      public StandardImageMetadataSupport.Builder withTextEntry(String var1, String var2) {
         if (var2 != null && !var2.isEmpty()) {
            this.textEntries.add(new StandardImageMetadataSupport.TextEntry(Validate.notNull(var1, "keyword"), var2));
         }

         return this;
      }

      public IIOMetadata build() {
         return new StandardImageMetadataSupport(this);
      }
   }

   protected static enum ColorSpaceType {
      XYZ(3),
      Lab(3),
      Luv(3),
      YCbCr(3),
      Yxy(3),
      YCCK(4),
      PhotoYCC(3),
      RGB(3),
      GRAY(1),
      HSV(3),
      HLS(3),
      CMYK(3),
      CMY(3),
      GENERIC_2CLR(2, "2CLR"),
      GENERIC_3CLR(3, "3CLR"),
      GENERIC_4CLR(4, "4CLR"),
      GENERIC_5CLR(5, "5CLR"),
      GENERIC_6CLR(6, "6CLR"),
      GENERIC_7CLR(7, "7CLR"),
      GENERIC_8CLR(8, "8CLR"),
      GENERIC_9CLR(9, "9CLR"),
      GENERIC_ACLR(10, "ACLR"),
      GENERIC_BCLR(11, "BCLR"),
      GENERIC_CCLR(12, "CCLR"),
      GENERIC_DCLR(13, "DCLR"),
      GENERIC_ECLR(14, "ECLR"),
      GENERIC_FCLR(15, "FCLR");

      final int numChannels;
      private final String nameOverride;

      private ColorSpaceType(int var3) {
         this(var3, null);
      }

      private ColorSpaceType(int var3, String var4) {
         this.numChannels = var3;
         this.nameOverride = var4;
      }

      @Override
      public String toString() {
         return this.nameOverride != null ? this.nameOverride : super.toString();
      }
   }

   protected static enum ImageOrientation {
      Normal,
      Rotate90,
      Rotate180,
      Rotate270,
      FlipH,
      FlipV,
      FlipHRotate90,
      FlipVRotate90;
   }

   protected static enum PlanarConfiguration {
      PixelInterleaved,
      PlaneInterleaved,
      LineInterleaved,
      TileInterleaved;
   }

   protected static enum SubimageInterpretation {
      Standalone,
      SinglePage,
      FullResolution,
      ReducedResolution,
      PyramidLayer,
      Preview,
      VolumeSlice,
      ObjectView,
      Panorama,
      AnimationFrame,
      TransparencyMask,
      CompositingLayer,
      SpectralSlice,
      Unknown;
   }

   protected static final class TextEntry {
      static final List<String> COMPRESSIONS = Arrays.asList("none", "lzw", "zip", "bzip", "other");
      final String keyword;
      final String value;
      final String language;
      final String encoding;
      final String compression;

      public TextEntry(String var1, String var2) {
         this(var1, var2, null, null, null);
      }

      public TextEntry(String var1, String var2, String var3, String var4, String var5) {
         this.keyword = var1;
         this.value = Validate.notNull(var2, "value");
         this.language = var3;
         this.encoding = var4;
         this.compression = Validate.isTrue(
            var5 == null || COMPRESSIONS.contains(var5), var5, String.format("Unknown compression: %s (expected: %s)", var5, COMPRESSIONS)
         );
      }
   }
}
