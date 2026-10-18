package com.twelvemonkeys.imageio.stream;

import com.twelvemonkeys.imageio.spi.ProviderInfo;
import java.io.File;
import java.util.Locale;
import javax.imageio.spi.ImageInputStreamSpi;
import javax.imageio.stream.ImageInputStream;

public final class ByteArrayImageInputStreamSpi extends ImageInputStreamSpi {
   public ByteArrayImageInputStreamSpi() {
      this(new StreamProviderInfo());
   }

   private ByteArrayImageInputStreamSpi(ProviderInfo var1) {
      super(var1.getVendorName(), var1.getVersion(), byte[].class);
   }

   @Override
   public ImageInputStream createInputStreamInstance(Object var1, boolean var2, File var3) {
      if (var1 instanceof byte[]) {
         return new ByteArrayImageInputStream((byte[])var1);
      } else {
         throw new IllegalArgumentException("Expected input of type byte[]: " + var1);
      }
   }

   @Override
   public String getDescription(Locale var1) {
      return "Service provider that instantiates an ImageInputStream from a byte array";
   }
}
