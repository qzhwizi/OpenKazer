package com.twelvemonkeys.imageio.stream;

import com.twelvemonkeys.imageio.spi.ProviderInfo;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;
import javax.imageio.spi.ImageInputStreamSpi;
import javax.imageio.stream.ImageInputStream;

public final class URLImageInputStreamSpi extends ImageInputStreamSpi {
   public URLImageInputStreamSpi() {
      this(new StreamProviderInfo());
   }

   private URLImageInputStreamSpi(ProviderInfo var1) {
      super(var1.getVendorName(), var1.getVersion(), URL.class);
   }

   @Override
   public ImageInputStream createInputStreamInstance(Object var1, boolean var2, File var3) throws IOException {
      if (!(var1 instanceof URL)) {
         throw new IllegalArgumentException("Expected input of type URL: " + var1);
      } else {
         URL var4 = (URL)var1;
         if ("file".equals(var4.getProtocol())) {
            try {
               return new BufferedChannelImageInputStream(new File(var4.toURI()));
            } catch (URISyntaxException var6) {
               var6.printStackTrace();
            }
         }

         InputStream var5 = var4.openStream();
         return new BufferedChannelImageInputStream((Cache)(var2 ? new FileCache(var5, var3) : new MemoryCache(var5)));
      }
   }

   @Override
   public boolean canUseCacheFile() {
      return true;
   }

   @Override
   public String getDescription(Locale var1) {
      return "Service provider that instantiates an ImageInputStream from a URL";
   }
}
