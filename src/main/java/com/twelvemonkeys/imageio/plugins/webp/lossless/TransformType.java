package com.twelvemonkeys.imageio.plugins.webp.lossless;

interface TransformType {
   int PREDICTOR_TRANSFORM = 0;
   int COLOR_TRANSFORM = 1;
   int SUBTRACT_GREEN = 2;
   int COLOR_INDEXING_TRANSFORM = 3;
}
