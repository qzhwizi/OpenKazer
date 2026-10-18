package com.twelvemonkeys.imageio.stream;

import com.twelvemonkeys.imageio.spi.ProviderInfo;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.Locale;
import javax.imageio.spi.ImageInputStreamSpi;
import javax.imageio.spi.ServiceRegistry;
import javax.imageio.spi.ServiceRegistry.Filter;
import javax.imageio.stream.ImageInputStream;

public final class BufferedRAFImageInputStreamSpi extends ImageInputStreamSpi {
   public BufferedRAFImageInputStreamSpi() {
      this(new StreamProviderInfo());
   }

   private BufferedRAFImageInputStreamSpi(ProviderInfo var1) {
      super(var1.getVendorName(), var1.getVersion(), RandomAccessFile.class);
   }

   @Override
   public void onRegistration(ServiceRegistry var1, Class<?> var2) {
      Iterator var3 = var1.getServiceProviders(ImageInputStreamSpi.class, new BufferedRAFImageInputStreamSpi.RAFInputFilter(), true);

      while (var3.hasNext()) {
         ImageInputStreamSpi var4 = (ImageInputStreamSpi)var3.next();
         if (var4 != this) {
            var1.setOrdering(ImageInputStreamSpi.class, this, var4);
         }
      }
   }

   @Override
   public ImageInputStream createInputStreamInstance(Object var1, boolean var2, File var3) {
      if (var1 instanceof RandomAccessFile) {
         return new BufferedChannelImageInputStream((RandomAccessFile)var1);
      } else {
         throw new IllegalArgumentException("Expected input of type RandomAccessFile: " + var1);
      }
   }

   @Override
   public boolean canUseCacheFile() {
      return false;
   }

   @Override
   public String getDescription(Locale var1) {
      return "Service provider that instantiates an ImageInputStream from a RandomAccessFile";
   }

   private static class RAFInputFilter implements Filter {
      private RAFInputFilter() {
      }

      @Override
      public boolean filter(Object var1) {
         return ((ImageInputStreamSpi)var1).getInputClass() == RandomAccessFile.class;
      }
   }
}
