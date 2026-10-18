package com.twelvemonkeys.imageio.metadata.xmp;

import com.twelvemonkeys.imageio.util.IIOUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

public final class XMPScanner {
   private static final byte[] XMP_PACKET_BEGIN = new byte[]{60, 63, 120, 112, 97, 99, 107, 101, 116, 32, 98, 101, 103, 105, 110, 61};
   private static final byte[] XMP_PACKET_END = new byte[]{60, 63, 120, 112, 97, 99, 107, 101, 116, 32, 101, 110, 100, 61};

   public static Reader scanForXMPPacket(Object var0) throws IOException {
      ImageInputStream var1 = var0 instanceof ImageInputStream ? (ImageInputStream)var0 : ImageIO.createImageInputStream(var0);
      long var2 = scanForSequence(var1, XMP_PACKET_BEGIN);
      if (var2 >= 0L) {
         byte var4 = var1.readByte();
         if (var4 == 39 || var4 == 34) {
            Charset var5 = null;
            byte[] var6 = new byte[4];
            var1.readFully(var6);
            if ((var6[0] != -17 || var6[1] != -69 || var6[2] != -65 || var6[3] != var4) && var6[0] != var4) {
               if (var6[0] == -2 && var6[1] == -1 && var6[2] == 0 && var6[3] == var4) {
                  var5 = StandardCharsets.UTF_16BE;
               } else if (var6[0] == 0 && var6[1] == -1 && var6[2] == -2 && var6[3] == var4) {
                  var1.skipBytes(1);
                  var5 = StandardCharsets.UTF_16LE;
               } else if (var6[0] == 0 && var6[1] == 0 && var6[2] == -2 && var6[3] == -1) {
                  var5 = Charset.forName("UTF-32BE");
               } else if (var6[0] == 0 && var6[1] == 0 && var6[2] == 0 && var6[3] == -1 && var1.read() == 254) {
                  var1.skipBytes(2);
                  var5 = Charset.forName("UTF-32LE");
               }
            } else {
               var5 = StandardCharsets.UTF_8;
            }

            if (var5 != null) {
               var1.mark();
               long var7 = scanForSequence(var1, XMP_PACKET_END);
               var1.reset();
               long var9 = var7 - var1.getStreamPosition();
               InputStreamReader var11 = new InputStreamReader(IIOUtil.createStreamAdapter(var1, var9), var5);

               while (var11.read() != 62) {
               }

               return var11;
            }
         }
      }

      return null;
   }

   private static long scanForSequence(ImageInputStream var0, byte[] var1) throws IOException {
      long var2 = -1L;
      int var4 = 0;
      int var5 = 0;

      int var6;
      while ((var6 = var0.read()) >= 0) {
         if (var1[var4] == (byte)var6) {
            if (var2 == -1L) {
               var2 = var0.getStreamPosition() - 1L;
            }

            if (var5 == 1 || var5 == 3) {
               var0.skipBytes(var5);
            }

            if (++var4 == var1.length) {
               return var2;
            }
         } else if (var4 == 1 && var6 == 0 && var5 < 3) {
            var5++;
         } else if (var4 != 0) {
            var4 = 0;
            var2 = -1L;
            var5 = 0;
         }
      }

      return -1L;
   }

   public static void main(String[] var0) throws IOException {
      ImageInputStream var1 = ImageIO.createImageInputStream(new File(var0[0]));

      Reader var2;
      while ((var2 = scanForXMPPacket(var1)) != null) {
         BufferedReader var3 = new BufferedReader(var2);

         String var4;
         while ((var4 = var3.readLine()) != null) {
            System.out.println(var4);
         }
      }

      var1.close();
   }
}
