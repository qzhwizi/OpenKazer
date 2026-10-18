package com.twelvemonkeys.imageio.metadata.tiff;

import com.twelvemonkeys.imageio.metadata.AbstractEntry;
import com.twelvemonkeys.imageio.metadata.Entry;
import com.twelvemonkeys.lang.Validate;
import java.lang.reflect.Array;

public final class TIFFEntry extends AbstractEntry {
   private final short type;

   public TIFFEntry(int var1, Object var2) {
      this(var1, guessType(var2), var2);
   }

   public TIFFEntry(int var1, short var2, Object var3) {
      super(var1, var3);
      if (var2 >= 1 && var2 < TIFF.TYPE_NAMES.length) {
         this.type = var2;
      } else {
         throw new IllegalArgumentException(String.format("Illegal TIFF type: %s", var2));
      }
   }

   public short getType() {
      return this.type;
   }

   @Override
   public String getFieldName() {
      switch ((Integer) this.getIdentifier()) {
         case 254:
            return "SubfileType";
         case 256:
            return "ImageWidth";
         case 257:
            return "ImageHeight";
         case 258:
            return "BitsPerSample";
         case 259:
            return "Compression";
         case 262:
            return "PhotometricInterpretation";
         case 266:
            return "FillOrder";
         case 269:
            return "DocumentName";
         case 270:
            return "ImageDescription";
         case 271:
            return "Make";
         case 272:
            return "Model";
         case 273:
            return "StripOffsets";
         case 274:
            return "Orientation";
         case 277:
            return "SamplesPerPixel";
         case 278:
            return "RowsPerStrip";
         case 279:
            return "StripByteCounts";
         case 282:
            return "XResolution";
         case 283:
            return "YResolution";
         case 284:
            return "PlanarConfiguration";
         case 285:
            return "PageName";
         case 296:
            return "ResolutionUnit";
         case 297:
            return "PageNumber";
         case 305:
            return "Software";
         case 306:
            return "DateTime";
         case 315:
            return "Artist";
         case 316:
            return "HostComputer";
         case 317:
            return "Predictor";
         case 320:
            return "ColorMap";
         case 322:
            return "TileWidth";
         case 323:
            return "TileHeight";
         case 324:
            return "TileOffsets";
         case 325:
            return "TileByteCounts";
         case 330:
            return "SubIFD";
         case 332:
            return "InkSet";
         case 333:
            return "InkNames";
         case 338:
            return "ExtraSamples";
         case 339:
            return "SampleFormat";
         case 347:
            return "JPEGTables";
         case 513:
            return "JPEGInterchangeFormat";
         case 514:
            return "JPEGInterchangeFormatLength";
         case 529:
            return "YCbCrCoefficients";
         case 530:
            return "YCbCrSubSampling";
         case 531:
            return "YCbCrPositioning";
         case 532:
            return "ReferenceBlackWhite";
         case 700:
            return "XMP";
         case 33432:
            return "Copyright";
         case 33434:
            return "ExposureTime";
         case 33437:
            return "FNUmber";
         case 33723:
            return "IPTC";
         case 34377:
            return "Adobe";
         case 34665:
            return "EXIF";
         case 34675:
            return "ICCProfile";
         case 34850:
            return "ExposureProgram";
         case 34853:
            return "GPS";
         case 34855:
            return "ISOSpeedRatings";
         case 36864:
            return "ExifVersion";
         case 36867:
            return "DateTimeOriginal";
         case 36868:
            return "DateTimeDigitized";
         case 37121:
            return "ComponentsConfiguration";
         case 37122:
            return "CompressedBitsPerPixel";
         case 37377:
            return "ShutterSpeedValue";
         case 37378:
            return "ApertureValue";
         case 37379:
            return "BrightnessValue";
         case 37380:
            return "ExposureBiasValue";
         case 37381:
            return "MaxApertureValue";
         case 37382:
            return "SubjectDistance";
         case 37383:
            return "MeteringMode";
         case 37384:
            return "LightSource";
         case 37385:
            return "Flash";
         case 37386:
            return "FocalLength";
         case 37393:
            return "ImageNumber";
         case 37500:
            return "MakerNote";
         case 37510:
            return "UserComment";
         case 37724:
            return "ImageSourceData";
         case 40960:
            return "FlashpixVersion";
         case 40961:
            return "ColorSpace";
         case 40962:
            return "PixelXDimension";
         case 40963:
            return "PixelYDimension";
         case 40965:
            return "Interoperability";
         case 41495:
            return "SensingMethod";
         case 41728:
            return "FileSource";
         case 41729:
            return "SceneType";
         case 41730:
            return "CFAPattern";
         case 41985:
            return "CustomRendered";
         case 41986:
            return "ExposureMode";
         case 41987:
            return "WhiteBalance";
         case 41988:
            return "DigitalZoomRatio";
         case 41989:
            return "FocalLengthIn35mmFilm";
         case 41990:
            return "SceneCaptureType";
         case 41991:
            return "GainControl";
         case 41992:
            return "Contrast";
         case 41993:
            return "Saturation";
         case 41994:
            return "Sharpness";
         case 42016:
            return "ImageUniqueID";
         default:
            return null;
      }
   }

   @Override
   public String getTypeName() {
      return TIFF.TYPE_NAMES[this.type];
   }

   static short getType(Entry var0) {
      if (var0 instanceof TIFFEntry) {
         TIFFEntry var3 = (TIFFEntry)var0;
         return var3.getType();
      } else {
         Validate.notNull(var0, "entry");
         String var1 = var0.getTypeName();
         if (var1 != null) {
            for (int var2 = 1; var2 < TIFF.TYPE_NAMES.length; var2++) {
               if (var1.equals(TIFF.TYPE_NAMES[var2])) {
                  return (short)var2;
               }
            }
         }

         return guessType(var0.getValue());
      }
   }

   private static short guessType(Object var0) {
      Object var1 = Validate.notNull(var0);
      boolean var2 = var1.getClass().isArray();
      if (var2) {
         var1 = Array.get(var1, 0);
      }

      if (var1 instanceof Byte) {
         return 1;
      } else if (var1 instanceof Short) {
         return (short)(!var2 && (Short)var1 < 127 ? 1 : 3);
      } else if (var1 instanceof Integer) {
         return (short)(!var2 && (Integer)var1 < 32767 ? 3 : 4);
      } else if (var1 instanceof Long && !var2 && (Long)var1 < 2147483647L) {
         return 4;
      } else if (var1 instanceof Rational) {
         return 5;
      } else if (var1 instanceof String) {
         return 2;
      } else {
         throw new UnsupportedOperationException(String.format("Method guessType not implemented for type %s", var1.getClass()));
      }
   }

   static long getValueLength(int var0, long var1) {
      return var0 > 0 && var0 < TIFF.TYPE_LENGTHS.length ? TIFF.TYPE_LENGTHS[var0] * var1 : -1L;
   }
}
