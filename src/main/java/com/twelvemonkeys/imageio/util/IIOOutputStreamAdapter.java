package com.twelvemonkeys.imageio.util;

import com.twelvemonkeys.lang.Validate;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.stream.ImageOutputStream;

class IIOOutputStreamAdapter extends OutputStream {
   private ImageOutputStream output;

   public IIOOutputStreamAdapter(ImageOutputStream var1) {
      Validate.notNull(var1, "stream == null");
      this.output = var1;
   }

   @Override
   public void write(byte[] var1) throws IOException {
      this.assertOpen();
      this.output.write(var1);
   }

   @Override
   public void write(byte[] var1, int var2, int var3) throws IOException {
      this.assertOpen();
      this.output.write(var1, var2, var3);
   }

   @Override
   public void write(int var1) throws IOException {
      this.assertOpen();
      this.output.write(var1);
   }

   @Override
   public void flush() throws IOException {
      this.assertOpen();
   }

   private void assertOpen() throws IOException {
      if (this.output == null) {
         throw new IOException("stream already closed");
      }
   }

   @Override
   public void close() throws IOException {
      this.output = null;
   }
}
