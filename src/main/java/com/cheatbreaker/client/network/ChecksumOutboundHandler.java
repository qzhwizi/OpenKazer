package com.cheatbreaker.client.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import javax.crypto.SecretKey;

public class ChecksumOutboundHandler extends ChannelOutboundHandlerAdapter {
   private long long1 = 1L;
   private long long2 = 0L;
   private long long3;
   private final byte[] IIIIllIlIIIllIlllIlllllIl = "ZB9hEJy5l+u8QARAlX9T0w".getBytes();

   public ChecksumOutboundHandler(SecretKey var1) {
      for (byte var5 : var1.getEncoded()) {
         this.long1 = (this.long1 + (var5 & 255)) % 65521L;
         this.long2 = (this.long2 + this.long1) % 65521L;
      }
   }

   public void write(ChannelHandlerContext var1, Object var2, ChannelPromise var3) throws Exception {
      ByteBuf var4 = (ByteBuf)var2;

      while (var4.readableBytes() > 0) {
         int var5 = var4.readByte() & 255;
         this.long1 = (this.long1 + var5) % 65521L;
         this.long2 = (this.long2 + this.long1) % 65521L;
      }

      var4.readerIndex(0);

      for (byte var8 : this.IIIIllIlIIIllIlllIlllllIl) {
         this.long1 = (this.long1 + (var8 & 255)) % 65521L;
         this.long2 = (this.long2 + this.long1) % 65521L;
      }

      this.long3 = this.long2 << 16 | this.long1;
      super.write(var1, var2, var3);
   }

   public long a() {
      return this.long3;
   }
}
