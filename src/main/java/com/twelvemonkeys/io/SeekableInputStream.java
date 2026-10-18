package com.twelvemonkeys.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

public abstract class SeekableInputStream extends InputStream implements Seekable {
   long position;
   long flushedPosition;
   boolean closed;
   protected Stack<Long> markedPositions = new Stack<>();

   @Override
   public final int read(byte[] var1) throws IOException {
      return this.read(var1, 0, var1 != null ? var1.length : 1);
   }

   @Override
   public final long skip(long var1) throws IOException {
      long var3 = this.position;
      long var5 = var3 + var1;
      if (var5 < this.flushedPosition) {
         throw new IOException("position < flushedPosition");
      } else {
         int var7 = this.available();
         if (var7 > 0) {
            this.seek(Math.min(var5, var3 + var7));
         } else {
            int var8 = (int)Math.max(Math.min(var1, 512L), -512L);

            while (var8 > 0 && this.read() >= 0) {
               var8--;
            }
         }

         return this.position - var3;
      }
   }

   @Override
   public final void mark(int var1) {
      this.mark();

      try {
         this.flushBefore(Math.max(this.position - var1, this.flushedPosition));
      } catch (IOException var3) {
      }
   }

   @Override
   public final boolean markSupported() {
      return true;
   }

   @Override
   public final void seek(long var1) throws IOException {
      this.checkOpen();
      if (var1 < this.flushedPosition) {
         throw new IndexOutOfBoundsException("position < flushedPosition");
      } else {
         this.seekImpl(var1);
         this.position = var1;
      }
   }

   protected abstract void seekImpl(long var1) throws IOException;

   @Override
   public final void mark() {
      this.markedPositions.push(this.position);
   }

   @Override
   public final void reset() throws IOException {
      this.checkOpen();
      if (!this.markedPositions.isEmpty()) {
         long var1 = this.markedPositions.pop();
         if (var1 < this.flushedPosition) {
            throw new IOException("Previous marked position has been discarded");
         }

         this.seek(var1);
      } else {
         this.seek(0L);
      }
   }

   @Override
   public final void flushBefore(long var1) throws IOException {
      if (var1 < this.flushedPosition) {
         throw new IndexOutOfBoundsException("position < flushedPosition");
      } else if (var1 > this.getStreamPosition()) {
         throw new IndexOutOfBoundsException("position > stream position");
      } else {
         this.checkOpen();
         this.flushBeforeImpl(var1);
         this.flushedPosition = var1;
      }
   }

   protected abstract void flushBeforeImpl(long var1) throws IOException;

   @Override
   public final void flush() throws IOException {
      this.flushBefore(this.flushedPosition);
   }

   @Override
   public final long getFlushedPosition() throws IOException {
      this.checkOpen();
      return this.flushedPosition;
   }

   @Override
   public final long getStreamPosition() throws IOException {
      this.checkOpen();
      return this.position;
   }

   protected final void checkOpen() throws IOException {
      if (this.closed) {
         throw new IOException("closed");
      }
   }

   @Override
   public final void close() throws IOException {
      this.checkOpen();
      this.closed = true;
      this.closeImpl();
   }

   protected abstract void closeImpl() throws IOException;

   @Override
   protected void finalize() throws Throwable {
      if (!this.closed) {
         try {
            this.close();
         } catch (IOException var2) {
         }
      }

      super.finalize();
   }
}
