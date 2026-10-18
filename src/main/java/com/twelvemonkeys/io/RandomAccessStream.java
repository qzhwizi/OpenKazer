package com.twelvemonkeys.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.EOFException;
import java.io.IOException;

public abstract class RandomAccessStream implements Seekable, DataInput, DataOutput {
   SeekableInputStream inputView = null;
   SeekableOutputStream outputView = null;

   public int read() throws IOException {
      try {
         return this.readByte() & 0xFF;
      } catch (EOFException var2) {
         return -1;
      }
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      if (var1 == null) {
         throw new NullPointerException("bytes == null");
      } else if (var2 < 0 || var2 > var1.length || var3 < 0 || var2 + var3 > var1.length || var2 + var3 < 0) {
         throw new IndexOutOfBoundsException();
      } else if (var3 == 0) {
         return 0;
      } else {
         int var4 = this.read();
         if (var4 == -1) {
            return -1;
         } else {
            var1[var2] = (byte)var4;
            int var5 = 1;

            try {
               while (var5 < var3) {
                  var4 = this.read();
                  if (var4 == -1) {
                     break;
                  }

                  var1[var2 + var5] = (byte)var4;
                  var5++;
               }
            } catch (IOException var7) {
            }

            return var5;
         }
      }
   }

   public final int read(byte[] var1) throws IOException {
      return this.read(var1, 0, var1 != null ? var1.length : 1);
   }

   public final SeekableInputStream asInputStream() {
      if (this.inputView == null) {
         this.inputView = new RandomAccessStream.InputStreamView(this);
      }

      return this.inputView;
   }

   public final SeekableOutputStream asOutputStream() {
      if (this.outputView == null) {
         this.outputView = new RandomAccessStream.OutputStreamView(this);
      }

      return this.outputView;
   }

   static final class InputStreamView extends SeekableInputStream {
      private final RandomAccessStream mStream;

      public InputStreamView(RandomAccessStream var1) {
         if (var1 == null) {
            throw new IllegalArgumentException("stream == null");
         } else {
            this.mStream = var1;
         }
      }

      @Override
      public boolean isCached() {
         return this.mStream.isCached();
      }

      @Override
      public boolean isCachedFile() {
         return this.mStream.isCachedFile();
      }

      @Override
      public boolean isCachedMemory() {
         return this.mStream.isCachedMemory();
      }

      @Override
      protected void closeImpl() throws IOException {
         this.mStream.close();
      }

      @Override
      protected void flushBeforeImpl(long var1) throws IOException {
         this.mStream.flushBefore(var1);
      }

      @Override
      protected void seekImpl(long var1) throws IOException {
         this.mStream.seek(var1);
      }

      @Override
      public int read() throws IOException {
         return this.mStream.read();
      }

      @Override
      public int read(byte[] var1, int var2, int var3) throws IOException {
         return this.mStream.read(var1, var2, var3);
      }
   }

   static final class OutputStreamView extends SeekableOutputStream {
      private final RandomAccessStream mStream;

      public OutputStreamView(RandomAccessStream var1) {
         if (var1 == null) {
            throw new IllegalArgumentException("stream == null");
         } else {
            this.mStream = var1;
         }
      }

      @Override
      public boolean isCached() {
         return this.mStream.isCached();
      }

      @Override
      public boolean isCachedFile() {
         return this.mStream.isCachedFile();
      }

      @Override
      public boolean isCachedMemory() {
         return this.mStream.isCachedMemory();
      }

      @Override
      protected void closeImpl() throws IOException {
         this.mStream.close();
      }

      @Override
      protected void flushBeforeImpl(long var1) throws IOException {
         this.mStream.flushBefore(var1);
      }

      @Override
      protected void seekImpl(long var1) throws IOException {
         this.mStream.seek(var1);
      }

      @Override
      public void write(int var1) throws IOException {
         this.mStream.write(var1);
      }

      @Override
      public void write(byte[] var1, int var2, int var3) throws IOException {
         this.mStream.write(var1, var2, var3);
      }
   }
}
