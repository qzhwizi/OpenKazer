package com.twelvemonkeys.imageio.metadata.psd;

import com.twelvemonkeys.imageio.metadata.Directory;
import com.twelvemonkeys.imageio.metadata.MetadataReader;
import com.twelvemonkeys.imageio.stream.SubImageInputStream;
import com.twelvemonkeys.lang.StringUtil;
import com.twelvemonkeys.lang.Validate;
import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.IIOException;
import javax.imageio.stream.ImageInputStream;

public final class PSDReader extends MetadataReader {
   @Override
   public Directory read(ImageInputStream var1) throws IOException {
      Validate.notNull(var1, "input");
      ArrayList var2 = new ArrayList();

      while (true) {
         try {
            int var3 = var1.readInt();
            switch (var3) {
               case 943868237:
               case 1097287783:
               case 1145262930:
               case 1298486113:
               case 1346917716:
                  short var4 = var1.readShort();
                  PSDReader.PSDResource var5 = new PSDReader.PSDResource(var4, var1);
                  var2.add(new PSDEntry(var4, var5.name(), var5.data()));
                  break;
               default:
                  throw new IIOException(String.format("Wrong image resource type, expected '8BIM': '%08x'", var3));
            }
         } catch (EOFException var6) {
            return new PSDDirectory(var2);
         }
      }
   }

   protected static class PSDResource {
      final short id;
      final String name;
      final long size;
      byte[] data;

      static String readPascalString(DataInput var0) throws IOException {
         int var1 = var0.readUnsignedByte();
         if (var1 == 0) {
            return "";
         } else {
            byte[] var2 = new byte[var1];
            var0.readFully(var2);
            return StringUtil.decode(var2, 0, var2.length, "ASCII");
         }
      }

      PSDResource(short var1, ImageInputStream var2) throws IOException {
         this.id = var1;
         this.name = readPascalString(var2);
         int var3 = this.name.length() + 1;
         if (var3 % 2 != 0) {
            var2.readByte();
         }

         this.size = var2.readUnsignedInt();
         long var4 = var2.getStreamPosition();
         this.readData(new SubImageInputStream(var2, this.size));
         if (var2.getStreamPosition() != var4 + this.size) {
            var2.seek(var4 + this.size);
         }

         if (this.size % 2L != 0L) {
            var2.read();
         }
      }

      protected void readData(ImageInputStream var1) throws IOException {
         this.data = new byte[(int)this.size];
         var1.readFully(this.data);
      }

      public final int id() {
         return this.id;
      }

      public final byte[] data() {
         return this.data;
      }

      public String name() {
         return this.name;
      }

      @Override
      public String toString() {
         StringBuilder var1 = this.toStringBuilder();
         var1.append(", data length: ");
         var1.append(this.size);
         var1.append("]");
         return var1.toString();
      }

      protected StringBuilder toStringBuilder() {
         StringBuilder var1 = new StringBuilder(this.getClass().getSimpleName());
         var1.append("[ID: 0x");
         var1.append(Integer.toHexString(this.id));
         if (this.name != null && this.name.trim().length() != 0) {
            var1.append(", name: \"");
            var1.append(this.name);
            var1.append("\"");
         }

         return var1;
      }
   }
}
