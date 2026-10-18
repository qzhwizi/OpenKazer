package com.twelvemonkeys.imageio.metadata.iptc;

import com.twelvemonkeys.imageio.metadata.Directory;
import com.twelvemonkeys.imageio.metadata.Entry;
import com.twelvemonkeys.imageio.metadata.MetadataWriter;
import com.twelvemonkeys.lang.Validate;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.imageio.stream.ImageOutputStream;

public final class IPTCWriter extends MetadataWriter {
   @Override
   public boolean write(Directory var1, ImageOutputStream var2) throws IOException {
      Validate.notNull(var1, "directory");
      Validate.notNull(var2, "stream");

      for (Entry var4 : var1) {
         int var5 = (Integer)var4.getIdentifier();
         Object var6 = var4.getValue();
         if (IPTC.Tags.isArray((short)var5)) {
            Object[] var7 = (Object[])var6;

            for (Object var11 : var7) {
               var2.write(28);
               var2.writeShort(var5);
               this.writeValue(var2, var11);
            }
         } else {
            var2.write(28);
            var2.writeShort(var5);
            this.writeValue(var2, var6);
         }
      }

      return false;
   }

   private void writeValue(ImageOutputStream var1, Object var2) throws IOException {
      if (var2 instanceof String) {
         byte[] var3 = ((String)var2).getBytes(StandardCharsets.UTF_8);
         var1.writeShort(var3.length);
         var1.write(var3);
      } else if (var2 instanceof byte[]) {
         byte[] var4 = (byte[])var2;
         var1.writeShort(var4.length);
         var1.write(var4);
      } else if (var2 instanceof Integer) {
         var1.writeShort(2);
         var1.writeShort((Integer)var2);
      }
   }
}
