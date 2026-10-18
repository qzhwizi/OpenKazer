package com.twelvemonkeys.imageio.stream;

import com.twelvemonkeys.imageio.spi.ProviderInfo;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Iterator;
import java.util.Locale;
import javax.imageio.spi.ImageInputStreamSpi;
import javax.imageio.spi.ServiceRegistry;
import javax.imageio.spi.ServiceRegistry.Filter;
import javax.imageio.stream.ImageInputStream;

public final class BufferedFileImageInputStreamSpi extends ImageInputStreamSpi {
   public BufferedFileImageInputStreamSpi() {
      this(new StreamProviderInfo());
   }

   private BufferedFileImageInputStreamSpi(ProviderInfo var1) {
      super(var1.getVendorName(), var1.getVersion(), File.class);
   }

   @Override
   public void onRegistration(ServiceRegistry var1, Class<?> var2) {
      Iterator var3 = var1.getServiceProviders(ImageInputStreamSpi.class, new BufferedFileImageInputStreamSpi.FileInputFilter(), true);

      while (var3.hasNext()) {
         ImageInputStreamSpi var4 = (ImageInputStreamSpi)var3.next();
         if (var4 != this) {
            var1.setOrdering(ImageInputStreamSpi.class, this, var4);
         }
      }
   }

   @Override
   public ImageInputStream createInputStreamInstance(Object var1, boolean var2, File var3) throws IOException {
      if (var1 instanceof File) {
         try {
            return new BufferedChannelImageInputStream((File)var1);
         } catch (NoSuchFileException | FileNotFoundException var5) {
            return null;
         }
      } else {
         throw new IllegalArgumentException("Expected input of type File: " + var1);
      }
   }

   @Override
   public boolean canUseCacheFile() {
      return false;
   }

   @Override
   public String getDescription(Locale var1) {
      return "Service provider that instantiates an ImageInputStream from a File";
   }

   private static class FileInputFilter implements Filter {
      private FileInputFilter() {
      }

      @Override
      public boolean filter(Object var1) {
         return ((ImageInputStreamSpi)var1).getInputClass() == File.class;
      }
   }
}
