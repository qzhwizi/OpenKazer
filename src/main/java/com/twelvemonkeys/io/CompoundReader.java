package com.twelvemonkeys.io;

import com.twelvemonkeys.lang.Validate;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CompoundReader extends Reader {
   private Reader current;
   private List<Reader> readers;
   protected final Object finalLock;
   protected final boolean markSupported;
   private int currentReader;
   private int markedReader;
   private long mark;
   private long next;

   public CompoundReader(Iterator<Reader> var1) {
      super(Validate.notNull(var1, "readers"));
      this.finalLock = var1;
      this.readers = new ArrayList<>();
      boolean var2 = true;

      while (var1.hasNext()) {
         Reader var3 = (Reader)var1.next();
         if (var3 == null) {
            throw new NullPointerException("readers cannot contain null-elements");
         }

         this.readers.add(var3);
         var2 = var2 && var3.markSupported();
      }

      this.markSupported = var2;
      this.current = this.nextReader();
   }

   protected final Reader nextReader() {
      if (this.currentReader >= this.readers.size()) {
         this.current = new EmptyReader();
      } else {
         this.current = this.readers.get(this.currentReader++);
      }

      this.next = 0L;
      return this.current;
   }

   protected final void ensureOpen() throws IOException {
      if (this.readers == null) {
         throw new IOException("Stream closed");
      }
   }

   @Override
   public void close() throws IOException {
      for (Reader var2 : this.readers) {
         var2.close();
      }

      this.readers = null;
   }

   @Override
   public void mark(int var1) throws IOException {
      if (var1 < 0) {
         throw new IllegalArgumentException("Read limit < 0");
      } else {
         synchronized (this.finalLock) {
            this.ensureOpen();
            this.mark = this.next;
            this.markedReader = this.currentReader;
            this.current.mark(var1);
         }
      }
   }

   @Override
   public void reset() throws IOException {
      synchronized (this.finalLock) {
         this.ensureOpen();
         if (this.currentReader != this.markedReader) {
            for (int var2 = this.currentReader; var2 >= this.markedReader; var2--) {
               this.readers.get(var2).reset();
            }

            this.currentReader = this.markedReader - 1;
            this.nextReader();
         }

         this.current.reset();
         this.next = this.mark;
      }
   }

   @Override
   public boolean markSupported() {
      return this.markSupported;
   }

   @Override
   public int read() throws IOException {
      synchronized (this.finalLock) {
         int var2 = this.current.read();
         if (var2 < 0 && this.currentReader < this.readers.size()) {
            this.nextReader();
            return this.read();
         } else {
            this.next++;
            return var2;
         }
      }
   }

   @Override
   public int read(char[] var1, int var2, int var3) throws IOException {
      synchronized (this.finalLock) {
         int var5 = this.current.read(var1, var2, var3);
         if (var5 < 0 && this.currentReader < this.readers.size()) {
            this.nextReader();
            return this.read(var1, var2, var3);
         } else {
            this.next += var5;
            return var5;
         }
      }
   }

   @Override
   public boolean ready() throws IOException {
      return this.current.ready();
   }

   @Override
   public long skip(long var1) throws IOException {
      synchronized (this.finalLock) {
         long var4 = this.current.skip(var1);
         if (var4 == 0L && this.currentReader < this.readers.size()) {
            this.nextReader();
            return this.skip(var1);
         } else {
            this.next += var4;
            return var4;
         }
      }
   }
}
