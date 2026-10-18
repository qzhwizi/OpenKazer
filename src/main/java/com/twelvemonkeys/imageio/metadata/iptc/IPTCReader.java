package com.twelvemonkeys.imageio.metadata.iptc;

import com.twelvemonkeys.imageio.metadata.Directory;
import com.twelvemonkeys.imageio.metadata.Entry;
import com.twelvemonkeys.imageio.metadata.MetadataReader;
import com.twelvemonkeys.lang.StringUtil;
import com.twelvemonkeys.lang.Validate;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.LinkedHashMap;
import javax.imageio.IIOException;
import javax.imageio.stream.ImageInputStream;

public final class IPTCReader extends MetadataReader {
   private static final int ENCODING_UNKNOWN = -1;
   private static final int ENCODING_UNSPECIFIED = 0;
   private static final int ENCODING_UTF_8 = 1779015;
   private int encoding = 0;

   @Override
   public Directory read(ImageInputStream var1) throws IOException {
      Validate.notNull(var1, "input");
      LinkedHashMap var2 = new LinkedHashMap();

      while (var1.read() == 28) {
         short var3 = var1.readShort();
         int var4 = var1.readUnsignedShort();
         boolean var5 = IPTC.Tags.isArray(var3);
         IPTCEntry var6 = this.readEntry(var1, var3, var4, var5, var5 ? (Entry)var2.get(var3) : null);
         if (var6 != null) {
            var2.put(var3, var6);
         }
      }

      return new IPTCDirectory(var2.values());
   }

   private IPTCEntry mergeEntries(short var1, Object var2, Entry var3) {
      Object[] var4 = var3 != null ? (Object[])var3.getValue() : null;
      Object var5;
      if (var2 instanceof String) {
         if (var4 == null) {
            var5 = new String[]{(String)var2};
         } else {
            String[] var6 = (String[])var4;
            var5 = Arrays.copyOf(var6, var6.length + 1);
            ((Object[])var5)[((Object[])var5).length - 1] = var2;
         }
      } else if (var4 == null) {
         var5 = new Object[]{var2};
      } else {
         var5 = Arrays.copyOf(var4, var4.length + 1);
         ((Object[])var5)[((Object[])var5).length - 1] = var2;
      }

      return new IPTCEntry(var1, var5);
   }

   private IPTCEntry readEntry(ImageInputStream var1, short var2, int var3, boolean var4, Entry var5) throws IOException {
      Object var6;
      switch (var2) {
         case 346:
            this.encoding = this.parseEncoding(var1, var3);
            return null;
         case 512:
            var6 = var1.readUnsignedShort();
            break;
         default:
            if ((var2 & '\uff00') == 512) {
               if (var3 < 1) {
                  var6 = null;
               } else {
                  var6 = this.parseString(var1, var3);
               }
            } else {
               byte[] var7 = new byte[var3];
               var1.readFully(var7);
               var6 = var7;
            }
      }

      return var4 ? this.mergeEntries(var2, var6, var5) : new IPTCEntry(var2, var6);
   }

   private int parseEncoding(ImageInputStream var1, int var2) throws IOException {
      return var2 == 3 && (var1.readUnsignedByte() << 16 | var1.readUnsignedByte() << 8 | var1.readUnsignedByte()) == 1779015 ? 1779015 : -1;
   }

   private String parseString(ImageInputStream var1, int var2) throws IOException {
      byte[] var3 = new byte[var2];
      var1.readFully(var3);
      Charset var4 = Charset.forName("UTF-8");
      CharsetDecoder var5 = var4.newDecoder();

      try {
         CharBuffer var6 = var5.onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT).decode(ByteBuffer.wrap(var3));
         return var6.toString();
      } catch (CharacterCodingException var7) {
         if (this.encoding == 1779015) {
            throw new IIOException("Wrong encoding of IPTC data, explicitly set to UTF-8 in DataSet 1:90", var7);
         } else {
            return StringUtil.decode(var3, 0, var3.length, "ISO8859_1");
         }
      }
   }
}
