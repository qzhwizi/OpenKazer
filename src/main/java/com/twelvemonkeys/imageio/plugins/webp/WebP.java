package com.twelvemonkeys.imageio.plugins.webp;

interface WebP {
   int RIFF_MAGIC = 1179011410;
   int WEBP_MAGIC = 1346520407;
   int CHUNK_VP8_ = 540561494;
   int CHUNK_VP8L = 1278758998;
   int CHUNK_VP8X = 1480085590;
   int CHUNK_ALPH = 1213221953;
   int CHUNK_ANIM = 1296649793;
   int CHUNK_ANMF = 1179471425;
   int CHUNK_ICCP = 1346585417;
   int CHUNK_EXIF = 1179211845;
   int CHUNK_XMP_ = 542133592;
   byte LOSSLESSS_SIG = 47;
}
