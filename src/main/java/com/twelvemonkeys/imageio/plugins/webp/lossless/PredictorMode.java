package com.twelvemonkeys.imageio.plugins.webp.lossless;

interface PredictorMode {
   int BLACK = 0;
   int L = 1;
   int T = 2;
   int TR = 3;
   int TL = 4;
   int AVG_L_TR_T = 5;
   int AVG_L_TL = 6;
   int AVG_L_T = 7;
   int AVG_TL_T = 8;
   int AVG_T_TR = 9;
   int AVG_L_TL_T_TR = 10;
   int SELECT = 11;
   int CLAMP_ADD_SUB_FULL = 12;
   int CLAMP_ADD_SUB_HALF = 13;
}
