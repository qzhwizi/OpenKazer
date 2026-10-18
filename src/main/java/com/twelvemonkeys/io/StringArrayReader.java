package com.twelvemonkeys.io;

import com.twelvemonkeys.lang.Validate;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class StringArrayReader extends StringReader {
   private StringReader current;
   private String[] strings;
   protected final Object finalLock;
   private int currentSting;
   private int markedString;
   private long mark;
   private long next;

   public StringArrayReader(String[] var1) {
      super("");
      Validate.notNull(var1, "strings");
      this.finalLock = this.lock = var1;
      this.strings = (String[])var1.clone();
      this.nextReader();
   }

   protected final Reader nextReader() {
      if (this.currentSting >= this.strings.length) {
         this.current = new EmptyReader();
      } else {
         this.current = new StringReader(this.strings[this.currentSting++]);
      }

      this.next = 0L;
      return this.current;
   }

   protected final void ensureOpen() throws IOException {
      if (this.strings == null) {
         throw new IOException("Stream closed");
      }
   }

   @Override
   public void close() {
      super.close();
      this.strings = null;
      this.current.close();
   }

   @Override
   public void mark(int var1) throws IOException {
      if (var1 < 0) {
         throw new IllegalArgumentException("Read limit < 0");
      } else {
         synchronized (this.finalLock) {
            this.ensureOpen();
            this.mark = this.next;
            this.markedString = this.currentSting;
            this.current.mark(var1);
         }
      }
   }

   @Override
   public void reset() throws IOException {
      synchronized (this.finalLock) {
         this.ensureOpen();
         if (this.currentSting != this.markedString) {
            this.currentSting = this.markedString - 1;
            this.nextReader();
            this.current.skip(this.mark);
         } else {
            this.current.reset();
         }

         this.next = this.mark;
      }
   }

   @Override
   public boolean markSupported() {
      return true;
   }

   @Override
   public int read() throws IOException {
      synchronized (this.finalLock) {
         int var2 = this.current.read();
         if (var2 < 0 && this.currentSting < this.strings.length) {
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
         if (var5 < 0 && this.currentSting < this.strings.length) {
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
         if (var4 == 0L && this.currentSting < this.strings.length) {
            this.nextReader();
            return this.skip(var1);
         } else {
            this.next += var4;
            return var4;
         }
      }
   }
}
