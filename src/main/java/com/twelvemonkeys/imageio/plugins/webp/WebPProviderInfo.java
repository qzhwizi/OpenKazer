package com.twelvemonkeys.imageio.plugins.webp;

import com.twelvemonkeys.imageio.spi.ReaderWriterProviderInfo;

final class WebPProviderInfo extends ReaderWriterProviderInfo {
   WebPProviderInfo() {
      super(
         WebPProviderInfo.class,
         new String[]{"webp", "WEBP", "wbp", "WBP"},
         new String[]{"wbp", "webp"},
         new String[]{"image/webp", "image/x-webp"},
         "com.twelvemonkeys.imageio.plugins.webp.WebPImageReader",
         new String[]{"com.twelvemonkeys.imageio.plugins.webp.WebPImageReaderSpi"},
         null,
         null,
         false,
         null,
         null,
         null,
         null,
         true,
         null,
         null,
         null,
         null
      );
   }
}
