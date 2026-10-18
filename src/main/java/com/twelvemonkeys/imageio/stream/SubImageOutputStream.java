package com.twelvemonkeys.imageio.stream;

import com.twelvemonkeys.lang.Validate;
import java.io.IOException;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.ImageOutputStreamImpl;

public final class SubImageOutputStream extends ImageOutputStreamImpl {
   private final ImageOutputStream stream;
   private final long startPos;

   public SubImageOutputStream(ImageOutputStream var1) throws IOException {
      this.stream = Validate.notNull(var1, "stream");
      this.startPos = var1.getStreamPosition();
   }

   @Override
   public void seek(long var1) throws IOException {
      super.seek(var1);
      this.stream.seek(this.startPos + var1);
   }

   @Override
   public void write(int var1) throws IOException {
      this.flushBits();
      this.stream.write(var1);
      this.streamPos++;
   }

   @Override
   public void write(byte[] var1, int var2, int var3) throws IOException {
      this.flushBits();
      this.stream.write(var1, var2, var3);
      this.streamPos += var3;
   }

   @Override
   public int read() throws IOException {
      this.bitOffset = 0;
      this.streamPos++;
      return this.stream.read();
   }

   @Override
   public int read(byte[] var1, int var2, int var3) throws IOException {
      this.bitOffset = 0;
      int var4 = this.stream.read(var1, var2, var3);
      this.streamPos += var4;
      return var4;
   }

   @Override
   public boolean isCached() {
      return this.stream.isCached();
   }

   @Override
   public boolean isCachedMemory() {
      return this.stream.isCachedMemory();
   }

   @Override
   public boolean isCachedFile() {
      return this.stream.isCachedFile();
   }
}
