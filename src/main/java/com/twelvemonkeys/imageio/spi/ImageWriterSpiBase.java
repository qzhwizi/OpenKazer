package com.twelvemonkeys.imageio.spi;

import javax.imageio.spi.ImageWriterSpi;

public abstract class ImageWriterSpiBase extends ImageWriterSpi {
   protected ImageWriterSpiBase(ReaderWriterProviderInfo var1) {
      super(
         var1.getVendorName(),
         var1.getVersion(),
         var1.formatNames(),
         var1.suffixes(),
         var1.mimeTypes(),
         var1.writerClassName(),
         var1.outputTypes(),
         var1.readerSpiClassNames(),
         var1.supportsStandardStreamMetadataFormat(),
         var1.nativeStreamMetadataFormatName(),
         var1.nativeStreamMetadataFormatClassName(),
         var1.extraStreamMetadataFormatNames(),
         var1.extraStreamMetadataFormatClassNames(),
         var1.supportsStandardImageMetadataFormat(),
         var1.nativeImageMetadataFormatName(),
         var1.nativeImageMetadataFormatClassName(),
         var1.extraImageMetadataFormatNames(),
         var1.extraImageMetadataFormatClassNames()
      );
   }
}
