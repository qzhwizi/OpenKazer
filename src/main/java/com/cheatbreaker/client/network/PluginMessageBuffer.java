package com.cheatbreaker.client.network;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import java.io.IOException;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PluginMessageBuffer {
   private final ByteBuf buf;

   public PluginMessageBuffer(ByteBuf var1) {
      this.buf = var1;
   }

   public void a(int var1) {
      while ((var1 & -128) != 0) {
         this.buf.writeByte(var1 & 127 | 128);
         var1 >>>= 7;
      }

      this.buf.writeByte(var1);
   }

   public int a() {
      int var1 = 0;
      int var2 = 0;

      byte var3;
      do {
         var3 = this.buf.readByte();
         var1 |= (var3 & 127) << var2++ * 7;
         if (var2 > 5) {
            throw new RuntimeException("VarInt too big");
         }
      } while ((var3 & 128) == 128);

      return var1;
   }

   public String b(int var1) throws IOException {
      int var2 = this.a();
      if (var2 > var1 * 4) {
         throw new IOException("The received encoded string buffer length is longer than maximum allowed (" + var2 + " > " + var1 * 4 + ")");
      } else if (var2 < 0) {
         throw new IOException("The received encoded string buffer length is less than zero! Weird string!");
      } else {
         String var3 = new String(this.buf.readBytes(var2).array(), Charsets.UTF_8);
         if (var3.length() > var1) {
            throw new IOException("The received string length is longer than maximum allowed (" + var2 + " > " + var1 + ")");
         } else {
            return var3;
         }
      }
   }

   public <T> void a(T var1, Consumer<T> var2) {
      this.buf.writeBoolean(var1 != null);
      if (var1 != null) {
         var2.accept(var1);
      }
   }

   public <T> T a(Supplier<T> var1) {
      boolean var2 = this.buf.readBoolean();
      return (T)(var2 ? var1.get() : null);
   }

   public void a(String var1) {
      byte[] var2 = var1.getBytes(Charsets.UTF_8);
      this.a(var2.length);
      this.buf.writeBytes(var2);
   }

   public String b() {
      int var1 = this.a();
      byte[] var2 = new byte[var1];
      this.buf.readBytes(var2);
      return new String(var2, Charsets.UTF_8);
   }

   public void a(UUID var1) {
      this.buf.writeLong(var1.getMostSignificantBits());
      this.buf.writeLong(var1.getLeastSignificantBits());
   }

   public UUID c() {
      long var1 = this.buf.readLong();
      long var3 = this.buf.readLong();
      return new UUID(var1, var3);
   }

   public ByteBuf d() {
      return this.buf;
   }
}
