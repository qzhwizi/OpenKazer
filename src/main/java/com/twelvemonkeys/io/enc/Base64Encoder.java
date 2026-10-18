package com.twelvemonkeys.io.enc;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class Base64Encoder implements Encoder {
   @Override
   public void encode(OutputStream var1, ByteBuffer var2) throws IOException {
      while (var2.hasRemaining()) {
         int var3 = Math.min(3, var2.remaining());
         switch (var3) {
            case 1:
               byte var8 = var2.get();
               byte var10 = 0;
               var1.write(Base64Decoder.PEM_ARRAY[var8 >>> 2 & 63]);
               var1.write(Base64Decoder.PEM_ARRAY[(var8 << 4 & 48) + (var10 >>> 4 & 15)]);
               var1.write(61);
               var1.write(61);
               break;
            case 2:
               byte var7 = var2.get();
               byte var9 = var2.get();
               byte var11 = 0;
               var1.write(Base64Decoder.PEM_ARRAY[var7 >>> 2 & 63]);
               var1.write(Base64Decoder.PEM_ARRAY[(var7 << 4 & 48) + (var9 >>> 4 & 15)]);
               var1.write(Base64Decoder.PEM_ARRAY[(var9 << 2 & 60) + (var11 >>> 6 & 3)]);
               var1.write(61);
               break;
            default:
               byte var4 = var2.get();
               byte var5 = var2.get();
               byte var6 = var2.get();
               var1.write(Base64Decoder.PEM_ARRAY[var4 >>> 2 & 63]);
               var1.write(Base64Decoder.PEM_ARRAY[(var4 << 4 & 48) + (var5 >>> 4 & 15)]);
               var1.write(Base64Decoder.PEM_ARRAY[(var5 << 2 & 60) + (var6 >>> 6 & 3)]);
               var1.write(Base64Decoder.PEM_ARRAY[var6 & 63]);
         }
      }
   }
}
