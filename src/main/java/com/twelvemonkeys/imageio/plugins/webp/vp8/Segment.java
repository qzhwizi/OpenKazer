package com.twelvemonkeys.imageio.plugins.webp.vp8;

final class Segment {
   int P0;
   int P1;
   int P2;
   int P3;
   int Q0;
   int Q1;
   int Q2;
   int Q3;

   @Override
   public String toString() {
      return Globals.toHex(this.P3)
         + " "
         + Globals.toHex(this.P2)
         + " "
         + Globals.toHex(this.P1)
         + " "
         + Globals.toHex(this.P0)
         + " "
         + Globals.toHex(this.Q0)
         + " "
         + Globals.toHex(this.Q1)
         + " "
         + Globals.toHex(this.Q2)
         + " "
         + Globals.toHex(this.Q3);
   }
}
