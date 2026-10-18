package com.twelvemonkeys.io.enc;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public final class EncoderStream extends FilterOutputStream {
   private final Encoder encoder;
   private final boolean flushOnWrite;
   private final ByteBuffer buffer;

   public EncoderStream(OutputStream var1, Encoder var2) {
      this(var1, var2, false);
   }

   public EncoderStream(OutputStream var1, Encoder var2, boolean var3) {
      super(var1);
      this.encoder = var2;
      this.flushOnWrite = var3;
      this.buffer = ByteBuffer.allocate(1024);
   }

   @Override
   public void close() throws IOException {
      this.flush();
      super.close();
   }

   @Override
   public void flush() throws IOException {
      this.encodeBuffer();
      super.flush();
   }

   private void encodeBuffer() throws IOException {
      if (this.buffer.position() != 0) {
         ((Buffer)this.buffer).flip();
         this.encoder.encode(this.out, this.buffer);
         ((Buffer)this.buffer).clear();
      }
   }

   @Override
   public void write(byte[] var1) throws IOException {
      this.write(var1, 0, var1.length);
   }

   @Override
   public void write(byte[] var1, int var2, int var3) throws IOException {
      if (!this.flushOnWrite && var3 < this.buffer.remaining()) {
         this.buffer.put(var1, var2, var3);
      } else {
         this.encodeBuffer();
         this.encoder.encode(this.out, ByteBuffer.wrap(var1, var2, var3));
      }
   }

   @Override
   public void write(int var1) throws IOException {
      if (!this.buffer.hasRemaining()) {
         this.encodeBuffer();
      }

      this.buffer.put((byte)var1);
   }
}
