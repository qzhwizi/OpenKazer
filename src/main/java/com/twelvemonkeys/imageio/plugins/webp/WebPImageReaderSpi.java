package com.twelvemonkeys.imageio.plugins.webp;

import com.twelvemonkeys.imageio.spi.ImageReaderSpiBase;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.Locale;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public final class WebPImageReaderSpi extends ImageReaderSpiBase {
   public WebPImageReaderSpi() {
      super(new WebPProviderInfo());
   }

   @Override
   public boolean canDecodeInput(Object var1) throws IOException {
      return var1 instanceof ImageInputStream && canDecode((ImageInputStream)var1);
   }

   private static boolean canDecode(ImageInputStream var0) throws IOException {
      ByteOrder var1 = var0.getByteOrder();
      var0.mark();

      try {
         var0.setByteOrder(ByteOrder.LITTLE_ENDIAN);
         if (var0.readInt() != 1179011410) {
            return false;
         } else {
            var0.readInt();
            if (var0.readInt() != 1346520407) {
               return false;
            } else {
               int var2 = var0.readInt();
               switch (var2) {
                  case 540561494:
                  case 1278758998:
                  case 1480085590:
                     return true;
                  default:
                     return false;
               }
            }
         }
      } finally {
         var0.setByteOrder(var1);
         var0.reset();
      }
   }

   @Override
   public ImageReader createReaderInstance(Object var1) {
      return new WebPImageReader(this);
   }

   @Override
   public String getDescription(Locale var1) {
      return "Google WebP File Format (WebP) Reader";
   }
}
