package com.twelvemonkeys.imageio.plugins.webp;

import com.twelvemonkeys.imageio.ImageReaderBase;
import com.twelvemonkeys.imageio.color.ColorProfiles;
import com.twelvemonkeys.imageio.color.ColorSpaces;
import com.twelvemonkeys.imageio.metadata.Directory;
import com.twelvemonkeys.imageio.metadata.tiff.TIFFReader;
import com.twelvemonkeys.imageio.metadata.xmp.XMPReader;
import com.twelvemonkeys.imageio.plugins.webp.lossless.VP8LDecoder;
import com.twelvemonkeys.imageio.plugins.webp.vp8.VP8Frame;
import com.twelvemonkeys.imageio.stream.SubImageInputStream;
import com.twelvemonkeys.imageio.util.IIOUtil;
import com.twelvemonkeys.imageio.util.ImageTypeSpecifiers;
import com.twelvemonkeys.imageio.util.ProgressListenerBase;
import com.twelvemonkeys.imageio.util.RasterUtils;
import java.awt.Rectangle;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_Profile;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.imageio.IIOException;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.spi.ImageReaderSpi;

final class WebPImageReader extends ImageReaderBase {
   private static final int MAX_EXPANSION_RATIO = 2048;
   static final boolean DEBUG = "true".equalsIgnoreCase(System.getProperty("com.twelvemonkeys.imageio.plugins.webp.debug"));
   private LSBBitReader lsbBitReader;
   private long fileSize;
   private VP8xChunk header;
   private ICC_Profile containedICCP;
   private ICC_Profile iccProfile;
   private final List<AnimationFrame> frames = new ArrayList<>();

   WebPImageReader(ImageReaderSpi var1) {
      super(var1);
   }

   @Override
   protected void resetMembers() {
      this.fileSize = -1L;
      this.header = null;
      this.containedICCP = null;
      this.iccProfile = null;
      this.lsbBitReader = null;
      this.frames.clear();
   }

   @Override
   public void setInput(Object var1, boolean var2, boolean var3) {
      super.setInput(var1, var2, var3);
      if (this.imageInput != null) {
         this.lsbBitReader = new LSBBitReader(this.imageInput);
      }
   }

   private void readHeader(int var1) throws IOException {
      this.checkBounds(var1);
      this.readHeader();
      if (this.header.containsANIM) {
         this.readFrame(var1);
      }
   }

   private void readFrame(int var1) throws IOException {
      if (!this.header.containsANIM) {
         throw new IndexOutOfBoundsException("imageIndex >= 1 for non-animated WebP: " + var1);
      } else if (var1 >= this.frames.size()) {
         Object var2 = this.frames.isEmpty() ? this.header : this.frames.get(this.frames.size() - 1);
         this.imageInput.seek(((RIFFChunk)var2).offset + ((RIFFChunk)var2).length);

         while (this.imageInput.getStreamPosition() < this.fileSize) {
            int var3 = this.imageInput.readInt();
            long var4 = this.imageInput.readUnsignedInt();
            long var6 = this.imageInput.getStreamPosition();
            if (DEBUG) {
               System.out.printf("chunk: '%s'\n", fourCC(var3));
               System.out.println("chunkStart: " + var6);
               System.out.println("chunkLength: " + var4);
            }

            switch (var3) {
               case 1179471425:
                  int var8 = 2 * (int)this.lsbBitReader.readBits(24);
                  int var9 = 2 * (int)this.lsbBitReader.readBits(24);
                  int var10 = 1 + (int)this.lsbBitReader.readBits(24);
                  int var11 = 1 + (int)this.lsbBitReader.readBits(24);
                  Rectangle var12 = new Rectangle(var8, var9, var10, var11);
                  int var13 = (int)this.lsbBitReader.readBits(24);
                  int var14 = this.imageInput.readUnsignedByte();
                  this.frames.add(new AnimationFrame(var4, var6, var12, var13, var14));
               case 1296649793:
               default:
                  if (var1 < this.frames.size()) {
                     return;
                  }

                  this.imageInput.seek(var6 + var4 + (var4 & 1L));
            }
         }

         throw new IndexOutOfBoundsException(String.format("imageIndex > %d: %d", this.frames.size(), var1));
      }
   }

   private void readHeader() throws IOException {
      if (this.header == null) {
         this.imageInput.setByteOrder(ByteOrder.LITTLE_ENDIAN);
         this.imageInput.seek(0L);
         int var1 = this.imageInput.readInt();
         if (var1 != 1179011410) {
            throw new IIOException(String.format("Not a WebP file, invalid 'RIFF' magic: '%s'", fourCC(var1)));
         } else {
            this.fileSize = 8L + this.imageInput.readUnsignedInt();
            int var2 = this.imageInput.readInt();
            if (var2 != 1346520407) {
               throw new IIOException(String.format("Not a WebP file, invalid 'WEBP' magic: '%s'", fourCC(var2)));
            } else {
               int var3 = this.imageInput.readInt();
               long var4 = this.imageInput.readUnsignedInt();
               this.header = new VP8xChunk(var3, var4, this.imageInput.getStreamPosition());
               switch (var3) {
                  case 540561494:
                     int var6 = this.lsbBitReader.readBit();
                     if (var6 != 0) {
                        throw new IIOException("Unexpected WebP frame type, expected key frame (0): " + var6);
                     }

                     int var7 = (int)this.lsbBitReader.readBits(3);
                     int var8 = this.lsbBitReader.readBit();
                     if (DEBUG) {
                        System.out.println("versionNumber: " + var7);
                        System.out.println("showFrame: " + var8);
                     }

                     this.lsbBitReader.readBits(19);
                     this.imageInput.readUnsignedByte();
                     this.imageInput.readUnsignedByte();
                     this.imageInput.readUnsignedByte();
                     int var9 = this.imageInput.readUnsignedShort();
                     this.header.width = var9 & 16383;
                     int var10 = this.imageInput.readUnsignedShort();
                     this.header.height = var10 & 16383;
                     break;
                  case 1278758998:
                     byte var11 = this.imageInput.readByte();
                     if (var11 != 47) {
                        throw new IIOException(String.format("Unexpected 'VP8L' signature, expected 0x0x%2x: 0x%2x", (byte)47, var11));
                     }

                     this.header.isLossless = true;
                     this.header.width = 1 + (int)this.lsbBitReader.readBits(14);
                     this.header.height = 1 + (int)this.lsbBitReader.readBits(14);
                     this.header.containsALPH = this.lsbBitReader.readBit() == 1;
                     int var12 = (int)this.lsbBitReader.readBits(3);
                     if (var12 != 0) {
                        throw new IIOException(String.format("Unexpected 'VP8L' version, expected 0: %d", var12));
                     }
                     break;
                  case 1480085590:
                     if (var4 != 10L) {
                        throw new IIOException("Unexpected 'VP8X' chunk length, expected 10: " + var4);
                     }

                     int var13 = this.lsbBitReader.readBit();
                     if (var13 != 0) {
                        throw new IIOException(String.format("Unexpected 'VP8X' chunk reserved value, expected 0: %d", var13));
                     }

                     this.header.containsANIM = this.lsbBitReader.readBit() == 1;
                     this.header.containsXMP_ = this.lsbBitReader.readBit() == 1;
                     this.header.containsEXIF = this.lsbBitReader.readBit() == 1;
                     this.header.containsALPH = this.lsbBitReader.readBit() == 1;
                     this.header.containsICCP = this.lsbBitReader.readBit() == 1;
                     var13 = (int)this.lsbBitReader.readBits(26);
                     if (var13 != 0) {
                        throw new IIOException(String.format("Unexpected 'VP8X' chunk reserved value, expected 0: %d", var13));
                     }

                     this.header.width = 1 + (int)this.lsbBitReader.readBits(24);
                     this.header.height = 1 + (int)this.lsbBitReader.readBits(24);
                     if (this.header.containsICCP) {
                        while (this.containedICCP == null && this.imageInput.getStreamPosition() < this.fileSize) {
                           int var14 = this.imageInput.readInt();
                           long var15 = this.imageInput.readUnsignedInt();
                           long var17 = this.imageInput.getStreamPosition();
                           if (var14 == 1346585417) {
                              this.containedICCP = ColorProfiles.readProfile(IIOUtil.createStreamAdapter(this.imageInput, var15));
                              if (this.containedICCP.getColorSpaceType() == 5) {
                                 this.iccProfile = this.containedICCP;
                              } else {
                                 this.processWarningOccurred("Encountered non-RGB ICC Profile, ignoring color profile, colors may appear incorrect");
                              }
                           } else {
                              this.processWarningOccurred(String.format("Expected 'ICCP' chunk, '%s' chunk encountered", fourCC(var14)));
                           }

                           this.imageInput.seek(var17 + var15 + (var15 & 1L));
                        }
                     }
                     break;
                  default:
                     throw new IIOException(String.format("Unknown WebP chunk: '%s'", fourCC(var3)));
               }

               if (DEBUG) {
                  System.out.println("file size: " + this.fileSize + " (stream length: " + this.imageInput.length() + ")");
                  System.out.println("header: " + this.header);
               }
            }
         }
      }
   }

   static String fourCC(int var0) {
      return new String(
         new byte[]{(byte)(var0 & 0xFF), (byte)((var0 & 0xFF00) >> 8), (byte)((var0 & 0xFF0000) >> 16), (byte)((var0 & 0xFF000000) >>> 24)},
         StandardCharsets.US_ASCII
      );
   }

   @Override
   public int getNumImages(boolean var1) throws IOException {
      this.assertInput();
      this.readHeader();
      if (!this.header.containsANIM || !var1) {
         return this.header.containsANIM ? -1 : 1;
      } else if (this.isSeekForwardOnly()) {
         throw new IllegalStateException("Illegal combination of allowSearch with seekForwardOnly");
      } else {
         this.readAllFrames();
         return this.frames.size();
      }
   }

   private void readAllFrames() throws IOException {
      try {
         this.readFrame(Integer.MAX_VALUE);
      } catch (IndexOutOfBoundsException var2) {
      }
   }

   @Override
   public int getWidth(int var1) throws IOException {
      this.readHeader(var1);
      return this.header.containsANIM ? this.frames.get(var1).bounds.width : this.header.width;
   }

   @Override
   public int getHeight(int var1) throws IOException {
      this.readHeader(var1);
      return this.header.containsANIM ? this.frames.get(var1).bounds.height : this.header.height;
   }

   @Override
   public ImageTypeSpecifier getRawImageType(int var1) throws IOException {
      this.readHeader(var1);
      if (this.iccProfile != null && !ColorProfiles.isCS_sRGB(this.iccProfile)) {
         ICC_ColorSpace var2 = ColorSpaces.createColorSpace(this.iccProfile);
         int[] var3 = this.header.containsALPH ? new int[]{0, 1, 2, 3} : new int[]{0, 1, 2};
         return ImageTypeSpecifiers.createInterleaved(var2, var3, 0, this.header.containsALPH, false);
      } else {
         return ImageTypeSpecifiers.createFromBufferedImageType(this.header.containsALPH ? 6 : 5);
      }
   }

   @Override
   public Iterator<ImageTypeSpecifier> getImageTypes(int var1) throws IOException {
      ImageTypeSpecifier var2 = this.getRawImageType(var1);
      ArrayList var3 = new ArrayList();
      if (var2.getBufferedImageType() == 0) {
         var3.add(ImageTypeSpecifiers.createFromBufferedImageType(this.header.containsALPH ? 6 : 5));
      }

      var3.add(var2);
      var3.add(ImageTypeSpecifiers.createFromBufferedImageType(this.header.containsALPH ? 2 : 1));
      var3.add(ImageTypeSpecifiers.createFromBufferedImageType(this.header.containsALPH ? 3 : 4));
      return var3.iterator();
   }

   @Override
   public BufferedImage read(int var1, ImageReadParam var2) throws IOException {
      int var3 = this.getWidth(var1);
      int var4 = this.getHeight(var1);
      validateSourceSize(this.getRawImageType(var1), var3, var4, this.imageInput.length(), 2048);
      BufferedImage var5 = getDestination(var2, this.getImageTypes(var1), var3, var4);
      this.processImageStarted(var1);
      switch (this.header.fourCC) {
         case 540561494:
            this.imageInput.seek(this.header.offset);
            this.readVP8(RasterUtils.asByteRaster(var5.getRaster()), var2);
            break;
         case 1278758998:
            this.imageInput.seek(this.header.offset);
            this.readVP8Lossless(RasterUtils.asByteRaster(var5.getRaster()), var2);
            break;
         case 1480085590:
            if (this.header.containsANIM) {
               AnimationFrame var6 = this.frames.get(var1);
               this.imageInput.seek(var6.offset + 16L);
               this.readVP8Extended(var5, var2, var6.offset + var6.length, var6.bounds.width, var6.bounds.height);
            } else {
               this.imageInput.seek(this.header.offset + this.header.length);
               this.readVP8Extended(var5, var2, this.fileSize);
            }
            break;
         default:
            throw new IIOException("Unknown first chunk for WebP: " + fourCC(this.header.fourCC));
      }

      this.applyICCProfileIfNeeded(var5);
      if (this.abortRequested()) {
         this.processReadAborted();
      } else {
         this.processImageComplete();
      }

      return var5;
   }

   private void readVP8Extended(BufferedImage var1, ImageReadParam var2, long var3) throws IOException {
      this.readVP8Extended(var1, var2, var3, this.header.width, this.header.height);
   }

   private void readVP8Extended(BufferedImage var1, ImageReadParam var2, long var3, int var5, int var6) throws IOException {
      boolean var7 = false;

      while (this.imageInput.getStreamPosition() < var3) {
         int var8 = this.imageInput.readInt();
         long var9 = this.imageInput.readUnsignedInt();
         long var11 = this.imageInput.getStreamPosition();
         if (DEBUG) {
            System.out.printf("chunk: '%s'\n", fourCC(var8));
            System.out.println("chunkStart: " + var11);
            System.out.println("chunkLength: " + var9);
         }

         switch (var8) {
            case 540561494:
               this.readVP8(
                  RasterUtils.asByteRaster(var1.getRaster()).createWritableChild(0, 0, var1.getWidth(), var1.getHeight(), 0, 0, new int[]{0, 1, 2}), var2
               );
               if (this.header.containsALPH && !var7) {
                  this.opaqueAlpha(var1.getAlphaRaster());
               }
            case 542133592:
            case 1179211845:
            case 1346585417:
               break;
            case 1179471425:
            case 1296649793:
               if (!this.header.containsANIM) {
                  this.processWarningOccurred("Ignoring unsupported chunk: " + fourCC(var8));
               }
               break;
            case 1213221953:
               var7 = true;
               this.readAlpha(var1, var2, var5, var6);
               break;
            case 1278758998:
               this.readVP8Lossless(RasterUtils.asByteRaster(var1.getRaster()), var2, var5, var6);
               break;
            default:
               this.processWarningOccurred("Ignoring unexpected chunk: " + fourCC(var8));
         }

         this.imageInput.seek(var11 + var9 + (var9 & 1L));
      }
   }

   private void readAlpha(BufferedImage var1, ImageReadParam var2, int var3, int var4) throws IOException {
      int var5 = (int)this.lsbBitReader.readBits(2);
      int var6 = (int)this.lsbBitReader.readBits(2);
      int var7 = (int)this.lsbBitReader.readBits(2);
      int var8 = (int)this.lsbBitReader.readBits(2);
      if (var8 != 0) {
         this.processWarningOccurred(String.format("Unexpected 'ALPH' chunk reserved value, expected 0: %d", var8));
      }

      if (DEBUG) {
         System.out.println("preProcessing: " + var7);
         System.out.println("filtering: " + var6);
         System.out.println("compression: " + var5);
      }

      WritableRaster var9 = var1.getAlphaRaster();
      switch (var5) {
         case 0:
            this.readUncompressedAlpha(var9);
            break;
         case 1:
            this.imageInput.seek(this.imageInput.getStreamPosition() - 5L);
            WritableRaster var10 = Raster.createInterleavedRaster(0, var3, var4, 4, null);
            this.readVP8Lossless(var10, null, var3, var4);
            WritableRaster var11 = var10.createWritableChild(0, 0, var3, var4, 0, 0, new int[]{1});
            this.alphaFilter(var11, var6);
            VP8LDecoder.copyIntoRasterWithParams(var11, var9, var2);
            break;
         default:
            this.processWarningOccurred("Unknown WebP alpha compression: " + var5);
            this.opaqueAlpha(var9);
      }
   }

   private void alphaFilter(WritableRaster var1, int var2) {
      if (var2 != 0) {
         for (int var3 = 0; var3 < var1.getHeight(); var3++) {
            for (int var4 = 0; var4 < var1.getWidth(); var4++) {
               int var5 = this.getPredictorAlpha(var1, var2, var3, var4);
               var1.setSample(var4, var3, 0, var1.getSample(var4, var3, 0) + var5 % 256);
            }
         }
      }
   }

   private int getPredictorAlpha(WritableRaster var1, int var2, int var3, int var4) {
      switch (var2) {
         case 0:
            return 0;
         case 1:
            if (var4 == 0) {
               return var3 == 0 ? 0 : var1.getSample(0, var3 - 1, 0);
            }

            return var1.getSample(var4 - 1, var3, 0);
         case 2:
            if (var3 == 0) {
               return var4 == 0 ? 0 : var1.getSample(var4 - 1, 0, 0);
            }

            return var1.getSample(var4, var3 - 1, 0);
         case 3:
            if (var4 == 0) {
               return var3 == 0 ? 0 : var1.getSample(0, var3 - 1, 0);
            } else {
               if (var3 == 0) {
                  return var1.getSample(var4 - 1, 0, 0);
               }

               int var5 = var1.getSample(var4 - 1, var3, 0);
               int var6 = var1.getSample(var4, var3 - 1, 0);
               int var7 = var1.getSample(var4 - 1, var3 - 1, 0);
               return Math.max(0, Math.min(var5 + var6 - var7, 255));
            }
         default:
            this.processWarningOccurred("Unknown WebP alpha filtering: " + var2);
            return 0;
      }
   }

   private void applyICCProfileIfNeeded(BufferedImage var1) {
      if (this.iccProfile != null) {
         ColorModel var2 = var1.getColorModel();
         Object var3 = ((ICC_ColorSpace)var2.getColorSpace()).getProfile();
         if (!this.iccProfile.equals(var3)) {
            if (DEBUG) {
               System.err.println("Converting from " + this.iccProfile + " to " + (ColorProfiles.isCS_sRGB((ICC_Profile)var3) ? "sRGB" : var3));
            }

            WritableRaster var4 = var2.hasAlpha()
               ? var1.getRaster().createWritableChild(0, 0, var1.getWidth(), var1.getHeight(), 0, 0, new int[]{0, 1, 2})
               : var1.getRaster();
            new ColorConvertOp(new ICC_Profile[]{this.iccProfile, (ICC_Profile)var3}, null).filter(var4, var4);
         }
      }
   }

   private void opaqueAlpha(WritableRaster var1) {
      int var2 = var1.getHeight();
      int var3 = var1.getWidth();

      for (int var4 = 0; var4 < var2; var4++) {
         for (int var5 = 0; var5 < var3; var5++) {
            var1.setSample(var5, var4, 0, 255);
         }
      }
   }

   private void readUncompressedAlpha(WritableRaster var1) throws IOException {
      this.processWarningOccurred("Uncompressed WebP alpha not implemented");
      this.opaqueAlpha(var1);
   }

   private void readVP8Lossless(WritableRaster var1, ImageReadParam var2) throws IOException {
      this.readVP8Lossless(var1, var2, this.header.width, this.header.height);
   }

   private void readVP8Lossless(WritableRaster var1, ImageReadParam var2, int var3, int var4) throws IOException {
      VP8LDecoder var5 = new VP8LDecoder(this.imageInput, DEBUG);
      var5.readVP8Lossless(var1, true, var2, var3, var4);
   }

   private void readVP8(WritableRaster var1, ImageReadParam var2) throws IOException {
      VP8Frame var3 = new VP8Frame(this.imageInput, DEBUG);
      var3.setProgressListener(new ProgressListenerBase() {
         @Override
         public void imageProgress(ImageReader var1, float var2x) {
            WebPImageReader.this.processImageProgress(var2x);
         }
      });
      if (!var3.decode(var1, var2)) {
         this.processWarningOccurred("Nothing to decode");
      }
   }

   @Override
   public IIOMetadata getImageMetadata(int var1) throws IOException {
      return new WebPImageMetadata(this.getRawImageType(var1), this.header);
   }

   private void readMeta() throws IOException {
      if (this.header.containsEXIF || this.header.containsXMP_) {
         this.imageInput.seek(this.header.offset + this.header.length);

         while (this.imageInput.getStreamPosition() < this.fileSize) {
            int var1 = this.imageInput.readInt();
            long var2 = this.imageInput.readUnsignedInt();
            long var4 = this.imageInput.getStreamPosition();
            switch (var1) {
               case 542133592:
                  Directory var10 = new XMPReader().read(new SubImageInputStream(this.imageInput, var2));
                  if (DEBUG) {
                     System.out.println("xmp: " + var10);
                  }
                  break;
               case 1179211845:
                  byte var6 = 0;
                  byte[] var7 = new byte[6];
                  this.imageInput.readFully(var7);
                  if (var7[0] == 69 && var7[1] == 120 && var7[2] == 105 && var7[3] == 102 && var7[4] == 0 && var7[5] == 0) {
                     var6 = 6;
                  } else {
                     this.imageInput.seek(var4);
                  }

                  SubImageInputStream var8 = new SubImageInputStream(this.imageInput, var2 - var6);
                  Directory var9 = new TIFFReader().read(var8);
                  if (DEBUG) {
                     System.out.println("exif: " + var9);
                  }
            }

            this.imageInput.seek(var4 + var2 + (var2 & 1L));
         }
      }
   }
}
