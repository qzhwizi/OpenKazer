package com.twelvemonkeys.imageio.stream;

import com.twelvemonkeys.lang.Validate;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.NonWritableChannelException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

final class FileCache implements Cache {
   static final int BLOCK_SIZE = 8192;
   private final FileChannel cache;
   private final ReadableByteChannel channel;

   FileCache(InputStream var1, File var2) throws IOException {
      this(Channels.newChannel(Validate.notNull(var1, "stream")), var2);
   }

   public FileCache(ReadableByteChannel var1, File var2) throws IOException {
      this.channel = Validate.notNull(var1, "channel");
      Validate.isTrue(var2 == null || var2.isDirectory(), var2, "%s is not a directory");
      Path var3 = var2 == null ? Files.createTempFile("imageio", ".tmp") : Files.createTempFile(var2.toPath(), "imageio", ".tmp");
      this.cache = FileChannel.open(var3, StandardOpenOption.DELETE_ON_CLOSE, StandardOpenOption.READ, StandardOpenOption.WRITE);
   }

   void fetch() throws IOException {
      while (
         this.cache.position() >= this.cache.size()
            && this.cache.transferFrom(this.channel, this.cache.size(), Math.max(this.cache.position() - this.cache.size(), 8192L)) > 0L
      ) {
      }
   }

   @Override
   public boolean isOpen() {
      return this.channel.isOpen();
   }

   @Override
   public void close() throws IOException {
      this.cache.close();
   }

   @Override
   public int read(ByteBuffer var1) throws IOException {
      this.fetch();
      return this.cache.position() >= this.cache.size() ? -1 : this.cache.read(var1);
   }

   @Override
   public long position() throws IOException {
      return this.cache.position();
   }

   @Override
   public SeekableByteChannel position(long var1) throws IOException {
      this.cache.position(var1);
      return this;
   }

   @Override
   public long size() {
      return -1L;
   }

   @Override
   public int write(ByteBuffer var1) {
      throw new NonWritableChannelException();
   }

   @Override
   public SeekableByteChannel truncate(long var1) {
      throw new NonWritableChannelException();
   }

   @Override
   public void flushBefore(long var1) {
   }
}
