package com.twelvemonkeys.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Stack;

public abstract class SeekableOutputStream extends OutputStream implements Seekable {
   long position;
   long flushedPosition;
   boolean closed;
   protected Stack<Long> markedPositions = new Stack<>();

   @Override
   public final void write(byte[] var1) throws IOException {
      this.write(var1, 0, var1 != null ? var1.length : 1);
   }

   @Override
   public final void seek(long var1) throws IOException {
      this.checkOpen();
      if (var1 < this.flushedPosition) {
         throw new IndexOutOfBoundsException("position < flushedPosition!");
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
            throw new IOException("Previous marked position has been discarded!");
         }

         this.seek(var1);
      }
   }

   @Override
   public final void flushBefore(long var1) throws IOException {
      if (var1 < this.flushedPosition) {
         throw new IndexOutOfBoundsException("position < flushedPosition!");
      } else if (var1 > this.getStreamPosition()) {
         throw new IndexOutOfBoundsException("position > getStreamPosition()!");
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
}
