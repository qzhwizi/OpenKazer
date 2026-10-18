package com.twelvemonkeys.imageio.stream;

import com.twelvemonkeys.imageio.spi.ProviderInfo;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.util.Iterator;
import java.util.Locale;
import javax.imageio.spi.ImageInputStreamSpi;
import javax.imageio.spi.ServiceRegistry;
import javax.imageio.spi.ServiceRegistry.Filter;
import javax.imageio.stream.ImageInputStream;

public final class BufferedInputStreamImageInputStreamSpi extends ImageInputStreamSpi {
   public BufferedInputStreamImageInputStreamSpi() {
      this(new StreamProviderInfo());
   }

   private BufferedInputStreamImageInputStreamSpi(ProviderInfo var1) {
      super(var1.getVendorName(), var1.getVersion(), InputStream.class);
   }

   @Override
   public void onRegistration(ServiceRegistry var1, Class<?> var2) {
      Iterator var3 = var1.getServiceProviders(ImageInputStreamSpi.class, new BufferedInputStreamImageInputStreamSpi.InputStreamFilter(), true);

      while (var3.hasNext()) {
         ImageInputStreamSpi var4 = (ImageInputStreamSpi)var3.next();
         if (var4 != this) {
            var1.setOrdering(ImageInputStreamSpi.class, this, var4);
         }
      }
   }

   @Override
   public ImageInputStream createInputStreamInstance(Object var1, boolean var2, File var3) throws IOException {
      if (var1 instanceof InputStream) {
         ReadableByteChannel var4 = Channels.newChannel((InputStream)var1);
         return var4 instanceof SeekableByteChannel
            ? new BufferedChannelImageInputStream((SeekableByteChannel)var4)
            : new BufferedChannelImageInputStream((Cache)(var2 ? new FileCache(var4, var3) : new MemoryCache(var4)));
      } else {
         throw new IllegalArgumentException("Expected input of type InputStream: " + var1);
      }
   }

   @Override
   public boolean canUseCacheFile() {
      return true;
   }

   @Override
   public String getDescription(Locale var1) {
      return "Service provider that instantiates an ImageInputStream from an InputStream";
   }

   private static class InputStreamFilter implements Filter {
      private InputStreamFilter() {
      }

      @Override
      public boolean filter(Object var1) {
         return ((ImageInputStreamSpi)var1).getInputClass() == InputStream.class;
      }
   }
}
